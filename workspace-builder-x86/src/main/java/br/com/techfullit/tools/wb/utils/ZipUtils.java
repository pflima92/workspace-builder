/** Copyright - 2015 - Paulo Henrique Ferreira de Lima - TechFull IT Services
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */
package br.com.techfullit.tools.wb.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipUtils.
 */
public class ZipUtils {

    /** The zos. */
    private static ZipOutputStream zos = null;

    /**
     * Adds the file.
     *
     * @param arquivo the arquivo
     * @param zos the zos
     * @param pastaPrincipal the pasta principal
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void addFile(File arquivo, ZipOutputStream zos, File pastaPrincipal) throws IOException {

	if (arquivo.isDirectory()) {

	    for (File a : arquivo.listFiles()) {
		addFile(a, zos, pastaPrincipal);
	    }

	} else {

	    final int TAMANHO_BUFFER = 4096; // 4kb
	    int cont;
	    byte[] dados = new byte[TAMANHO_BUFFER];

	    BufferedInputStream origem = null;
	    try {
		origem = new BufferedInputStream(new FileInputStream(arquivo), TAMANHO_BUFFER);

		String nomeEntrada = arquivo.getAbsolutePath();

		System.out.println(nomeEntrada);

		if (pastaPrincipal.isDirectory()) {
		    nomeEntrada = nomeEntrada.substring(pastaPrincipal.getAbsolutePath().length() + 1);
		} else {
		    nomeEntrada = arquivo.getName();
		}

		zos.putNextEntry(new ZipEntry(nomeEntrada));

		while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {

		    zos.write(dados, 0, cont);
		}

	    } finally {
		if (origem != null) {
		    origem.close();
		}
	    }
	}
    }

    /**
     * Unzip.
     * 
     * @param zipFile
     *            the zip file
     * @param dir
     *            the dir
     * @throws Exception
     *             the exception
     */
    public static void unzip(File zipFile, File dir) throws Exception {
	System.out.println("Descompactando arquivo [" + zipFile.getAbsolutePath() + "] para o diretorio [" + dir.getAbsolutePath()
		+ "]");
	ZipFile zip = null;
	File arquivo = null;
	InputStream is = null;
	OutputStream os = null;
	byte[] buffer = new byte[1024];
	try {
	    if (!dir.exists()) {
		dir.mkdirs();
	    }
	    if (!dir.exists() || !dir.isDirectory()) {
		throw new IOException("O diretorio " + dir.getName() + " nao é um diretorio valido");
	    }
	    zip = new ZipFile(zipFile);
	    Enumeration e = zip.entries();
	    while (e.hasMoreElements()) {
		ZipEntry entrada = (ZipEntry) e.nextElement();
		arquivo = new File(dir, entrada.getName());
		if (entrada.isDirectory() && !arquivo.exists()) {
		    arquivo.mkdirs();
		    continue;
		} else if (entrada.isDirectory() && arquivo.exists()) {
		    continue;
		}
		if (!arquivo.getParentFile().exists()) {
		    arquivo.getParentFile().mkdirs();
		}
		try {
		    is = zip.getInputStream(entrada);
		    if (arquivo.exists()) {
			System.out.println("Sobreescrita: " + arquivo.getAbsolutePath());
			if (arquivo.isDirectory()) {
			    FolderUtils.deleteDir(arquivo);
			} else {
			    arquivo.delete();
			    arquivo.createNewFile();
			}
		    }
		    System.out.println("Descompacatando: " + arquivo.getAbsolutePath());
		    os = new FileOutputStream(arquivo);
		    int bytesLidos = 0;
		    if (is == null) {
			throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
		    }
		    while ((bytesLidos = is.read(buffer)) > 0) {
			os.write(buffer, 0, bytesLidos);
		    }
		} finally {
		    if (is != null) {
			is.close();
		    }
		    if (os != null) {
			os.close();
		    }
		}
	    }
	} finally {
	    if (zip != null) {
		zip.close();
	    }
	}
    }

    /**
     * Zip.
     *
     * @param sourcePath the source path
     * @param outputPath the output path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void zip(File sourcePath, File outputPath) throws IOException {
	zos = new ZipOutputStream(new FileOutputStream(outputPath));
	addFile(sourcePath, zos, sourcePath);
	zos.flush();
	zos.close();
    }

}
