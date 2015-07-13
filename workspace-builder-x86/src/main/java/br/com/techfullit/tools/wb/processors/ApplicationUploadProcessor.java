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
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.Utils;

/**
 * The Class ApplicationProcessor.
 */
public class ApplicationUploadProcessor extends Processor {

	/** The local file. */
	private static File localFile;

	/** The encontrou. */
	private static boolean encontrou;

	/**
	 * Creates the registry local macinhe.
	 * 
	 * @param app
	 *            the app
	 */
	public synchronized static void createRegistryLocalMacinhe(
			Application app) {
		localFile = new File(System.getProperty("user.home") + File.separator
				+ "Constants.DEFAULT_PATH_LOCAL_CONFIG");
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
				String linha = app.getId() + "=" + app.getVersion() + "|"
						+ app.getId() + "|" + app.getPathInstallation();
				writer.write(linha + "\n\r");
				System.out.println(linha);
				writer.flush();
				writer.close();
				System.out.println("Arquivo local gerado com sucesso.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			modificarArquivo(app);
		}
	}

	/**
	 * Modificar arquivo.
	 * 
	 * @param app
	 *            the app
	 * @return true, if successful
	 */
	public static boolean modificarArquivo(Application app) {
		File file = localFile;
		encontrou = false;
		FileWriter newFile;
		try {
			newFile = new FileWriter(new File("temp.properties"));

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				linha = verifyLine(linha, app);
				if (!linha.equals(""))
					newFile.write(linha + System.getProperty("line.separator"));
			}
			if (!encontrou) {
				newFile.write(app.getId() + "=" + app.getVersion() + "|"
						+ app.getId() + "|" + app.getPathInstallation()
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
	public static void recreateFile() {
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

	/**
	 * Upload application.
	 *
	 * @param app
	 *            the app
	 * @param filepath
	 *            the filepath
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean uploadApplication(Application app, String filepath) throws IOException {
		File file = new File(filepath);
		if (!file.exists()) {
			Logger.log("Arquivo selecionado não existe.");
			return false;
		}
		
		String pathRep = ContextHelper.getConfiguration().getDefaultPath() + File.separator + Utils.removeSpaces(app.getId() + File.separator + app.getVersion());
		
		File fileRep = new File(pathRep);
		if(!fileRep.exists()){
			Logger.log("Cria diretorio: " + fileRep.getAbsolutePath());			
			if(!fileRep.mkdirs()){
				return false;
			}
		}
		pathRep += File.separator + "Constants.FILE_NAME";
		
		fileRep = new File(pathRep);	
		if(fileRep.exists()){
			fileRep.delete();
		}
		
		Logger.log("Faz transferencia do arquivo");
		
		if(file.renameTo(fileRep)){
			Logger.log("Fim do processo de instala��o do Client");
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Verify line.
	 * 
	 * @param currentLine
	 *            the current line
	 * @param app
	 *            the app
	 * @return the string
	 */
	private static String verifyLine(String currentLine, Application app) {
		String newLine = "";
		System.out.println("Current Line:[" + currentLine + "]");
		String[] array = currentLine.split("=");
		/*
		 * if (app.getId().equals(array[0])) { encontrou = true; newLine =
		 * app.getId() + "=" + app.getVersion() + "|" + app.getName() + "|" +
		 * app.getPathInstallation(); }
		 */
		if (newLine.equals("")) {
			System.out.println("Linha não modificada");
			return currentLine;
		}
		System.out.println("Current Line:[" + newLine + "]");
		return newLine.toString();
	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}
}
