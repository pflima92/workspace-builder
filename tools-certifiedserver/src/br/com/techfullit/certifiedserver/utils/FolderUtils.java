/**
 
Copyright - 2015 - Paulo Henrique Ferreira de Lima - TechFull IT Services
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 

*/
package br.com.techfullit.certifiedserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Class FolderUtils.
 */
public class FolderUtils {

	/**
	 * Copy.
	 *
	 * @param origem the origem
	 * @param destino the destino
	 * @param overwrite the overwrite
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copy(File origem, File destino, boolean overwrite)
			throws IOException {
		if (destino.exists() && !overwrite) {
			System.err.println(destino.getName() + " já existe, ignorando...");
			return;
		} else {
			String stringPath = destino.getParent() + File.separator;
			File path = new File(stringPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (path.isFile()) {
				try {
					destino.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		FileInputStream fisOrigem = new FileInputStream(origem);
		FileOutputStream fisDestino = new FileOutputStream(destino);
		FileChannel fcOrigem = fisOrigem.getChannel();
		FileChannel fcDestino = fisDestino.getChannel();
		fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
		fisOrigem.close();
		fisDestino.close();
	}

	/**
	 * Delete dir.
	 * 
	 * @param dir
	 *            the dir
	 * @return true, if successful
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * Generate file name.
	 * 
	 * @param extension
	 *            the extension
	 * @return the string
	 */
	public static String generateFileName(String extension) {
		Calendar c = new GregorianCalendar();
		String name = "BCKP-" + c.get(Calendar.DATE) + c.get(Calendar.MONTH)
				+ c.get(Calendar.YEAR) + +c.get(Calendar.HOUR)
				+ c.get(Calendar.MILLISECOND);
		return name + "." + extension;
	}

	/**
	 * Gets the files folder.
	 * 
	 * @param path
	 *            the path
	 * @return the files folder
	 */
	public static File[] getFilesFolder(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	/**
	 * Gets the folder size.
	 * 
	 * @param path
	 *            the path
	 * @return the folder size
	 */
	public static int getFolderSize(String path) {
		File folder = new File(path);
		int size = 0;
		if (folder.isDirectory()) {
			String[] dirList = folder.list();
			if (dirList != null) {
				for (int i = 0; i < dirList.length; i++) {
					String fileName = dirList[i];
					File f = new File(path, fileName);
					if (f.isDirectory()) {
						String filePath = f.getPath();
						size += getFolderSize(filePath);
						continue;
					}
					size += f.length();
				}
			}
		}
		return size;
	}

	/**
	 * Gets the path files folder.
	 *
	 * @param file the file
	 * @return the path files folder
	 */
	public static ArrayList<String> getPathFilesFolder(File file) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < file.listFiles().length; i++) {
			list.add(file.listFiles()[i].getAbsolutePath());
		}
		return list;
	}

	/**
	 * Make backup.
	 *
	 * @param targetPath the target path
	 * @param pathToBackup the path to backup
	 * @param refName the ref name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void makeBackup(String targetPath, String pathToBackup,
			String refName) throws IOException {

		File fileTarget = new File(targetPath + File.separator + refName + "-"
				+ generateFileName("zip"));
		File fileToBackup = new File(pathToBackup);

		if (!fileTarget.exists()) {
			fileTarget.createNewFile();
		}

		ZipUtils.zip(fileToBackup, fileTarget);

	}

	/**
	 * Verify folder.
	 * 
	 * @param files
	 *            the files
	 */
	public static void verifyFolder(File[] files) {
		System.out.println("Deletando conteudo que nao faz parte do deploy...");
		for (File file : files) {
			if (!file.getAbsolutePath().contains(".war")) {
				System.out.println("Deletando " + file.getAbsolutePath());
				if (file.delete()) {
					System.out.println("Arquivo deletado com sucesso.");
				} else {
					System.out.println("Arquivo n�o delatado...");
				}
			}
		}
		System.out.println("Fim do processo de verifica��o do diretorio");
	}

}
