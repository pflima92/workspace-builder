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
package br.com.techfullit.tools.wb.processors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.techfullit.tools.wb.logger.Logger;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.FolderUtils;
import br.com.techfullit.tools.wb.utils.ZipUtils;

/**
 * The Class ApplicationProcessor.
 */
public class ApplicationInstallerProcessor extends Processor {

	/** The application selected. */
	protected Application applicationSelected;

	/** The local file. */
	private File localFile;

	/** The encontrou. */
	private boolean encontrou;

	/**
	 * Instantiates a new application installer processor.
	 *
	 * @param o
	 *            the o
	 */
	public ApplicationInstallerProcessor(Object... o) {
		applicationSelected = (Application) o[0];
	}

	/**
	 * Creates the registry local macinhe.
	 */
	private synchronized void createRegistryLocalMacinhe() {
		localFile = new File(System.getProperty("user.home") + File.separator
				+ ContextHelper.getConfiguration().getDefaultPathLocalConfig());
		if (!localFile.exists()) {
			try {
				new File(localFile.getParent()).mkdirs();
				localFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Primeira criação de arquivo local");
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						localFile, true));
				String linha = applicationSelected.getId() + "="
						+ applicationSelected.getId() + ";"
						+ applicationSelected.getVersion() + ";"
						+ applicationSelected.getPathInstallation();
				writer.write(linha + "\n\r");
				System.out.println(linha);
				writer.flush();
				writer.close();
				System.out.println("Arquivo local gerado com sucesso.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			modificarArquivo();
		}
	}

	/**
	 * Creates the structure folder.
	 *
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	private boolean createStructureFolder(String path) {
		Logger.log("Criando diretório de instalação...");
		File file = new File(path);
		if (file.mkdirs()) {
			Logger.log("Diretório criado com sucesso.");
			return true;
		} else {
			if (file.isDirectory()) {
				Logger.log("Diretório ja existe.");
				return true;
			} else {
				Logger.log("Não foi possível criar um diretório válido");
				return false;
			}
		}
	}

	/**
	 * Gets the application selected.
	 *
	 * @return the application selected
	 */
	public Application getApplicationSelected() {
		return applicationSelected;
	}

	/**
	 * Modificar arquivo.
	 *
	 * @return true, if successful
	 */
	private boolean modificarArquivo() {
		File file = localFile;
		encontrou = false;
		FileWriter newFile;
		try {
			newFile = new FileWriter(new File("temp.properties"));

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				linha = verifyLine(linha);
				if (!linha.equals(""))
					newFile.write(linha + System.getProperty("line.separator"));
			}
			if (!encontrou) {
				newFile.write(applicationSelected.getId() + "="
						+ applicationSelected.getId() + ";"
						+ applicationSelected.getVersion() + ";"
						+ applicationSelected.getPathInstallation()
						+ System.getProperty("line.separator"));
			}
			newFile.close();
			fileReader.close();
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Problema ao efetuar modificacao no arquivo: "
					+ localFile);
			System.out
					.println("Reveja as alteracoes anteriores pois elas foram feitas e nao poderaoo sofrer roolback.");
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Problema ao efetuar modificação no arquivo: "
					+ localFile);
			System.out
					.println("Reveja as alterações anteriores pois elas foram feitas e não poderão sofrer roolback.");
			System.out.println(e.getMessage());
			System.exit(1);
		}

		recreateFile();

		return true;
	}

	/**
	 * Recreate file.
	 */
	private void recreateFile() {
		File newFile = new File("temp.properties");
		System.out.println("Transferindo arquivo temp para diretório...");
		if (localFile.exists() && localFile.isFile()) {
			localFile.delete();
		}
		if (newFile.renameTo(localFile)) {
			System.out.println("Alteração realizada com sucesso");
		} else {
			System.out
					.println("Não foi possível deletar seu arquivo o processo sera encerrado");
		}
	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {

		Logger.log("Preparando instalação: " + applicationSelected.getTitle());
		Logger.log("Version: " + applicationSelected.getVersion());
		Logger.log("Diretório de instalação: "
				+ applicationSelected.getPathInstallation());

		if (createStructureFolder(applicationSelected.getPathInstallation())) {

			String rootPath = ContextHelper.getConfiguration().getDefaultPath()
					+ Constants.REPOSITORY_PATH + File.separator
					+ applicationSelected.getId() + File.separator
					+ applicationSelected.getVersion() + File.separator;

			File repositoryFiles = new File(rootPath);
			if (!repositoryFiles.isDirectory()) {
				return false;
			}

			for (int i = 0; i < repositoryFiles.listFiles().length; i++) {
				File file = repositoryFiles.listFiles()[i];
				if (file.isFile() && file.getAbsolutePath().endsWith("zip")) {
					Logger.log("Arquivo encontrado.");
					Logger.log(file.getAbsolutePath());
					Logger.log("Arquivo será descompactado e instalado.");
					Logger.log("Descompactando...");
					try {
						ZipUtils.unzip(
								file,
								new File(applicationSelected
										.getPathInstallation()));
					} catch (Exception e) {
						return false;
					}
				} else {
					Logger.log("Arquivo não existe no repositório");
				}
				Logger.log("FIm do processamento deste arquivo");
			}

			File shortcutFolder = new File(rootPath + File.separator
					+ "shortcuts");
			if (shortcutFolder.exists()) {

				String desktopPath = new String(System.getProperty("user.home")
						+ File.separator + "desktop");

				for (int i = 0; i < shortcutFolder.listFiles().length; i++) {
					File file = shortcutFolder.listFiles()[i];
					try {
						FolderUtils.copyFile(file, new File(desktopPath + File.separator + file.getName()));
					} catch (IOException e) {
						return false;
					}
				}
			}

			Logger.log("FIm do processamento deste arquivo");
		}
		createRegistryLocalMacinhe();
		return true;
	}

	/**
	 * Verify line.
	 *
	 * @param currentLine
	 *            the current line
	 * @return the string
	 */
	private String verifyLine(String currentLine) {
		String newLine = "";
		System.out.println("Current Line:[" + currentLine + "]");
		String[] array = currentLine.split("=");
		if (applicationSelected.getId().equals(array[0])) {
			encontrou = true;
			newLine = applicationSelected.getId() + "="
					+ applicationSelected.getId() + ";"
					+ applicationSelected.getVersion() + ";"
					+ applicationSelected.getPathInstallation();
		}
		if (newLine.equals("")) {
			System.out.println("Linha não modificada");
			return currentLine;
		}
		System.out.println("Current Line:[" + newLine + "]");
		return newLine.toString();
	}

}
