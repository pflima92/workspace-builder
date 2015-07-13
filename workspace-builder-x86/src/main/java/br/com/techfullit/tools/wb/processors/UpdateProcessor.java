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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Shell;

import br.com.techfullit.tools.wb.logger.Logger;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.model.Configuration;
import br.com.techfullit.tools.wb.utils.FolderUtils;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.utils.ZipUtils;

/**
 * The Class UpdateProcessor.
 */
public class UpdateProcessor extends Shell {

	/** The apps. */
	private static ArrayList<Application> appsRepository;

	/** The apps local user. */
	private static ArrayList<Application> appsLocalUser;

	/**
	 * Creates the structure folder.
	 * 
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	private static boolean createStructureFolder(String path) {
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
	 * Load application base.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static void loadApplicationBase() throws IOException,
			ClassNotFoundException, SQLException {

		Properties prop = new Properties();

		FileInputStream fis;

		appsLocalUser = new ArrayList<>();
		fis = new FileInputStream(new File(System.getProperty("user.home")
				+ File.separator + "Constants.DEFAULT_PATH_LOCAL_CONFIG"));
		prop = new Properties();
		prop.load(fis);

		for (Object entry : prop.keySet()) {
			String key = (String) entry;
			appsLocalUser.add(parseApplicationInfo(prop.getProperty(key), key));
		}

	}

	/**
	 * Map to update.
	 *
	 * @return the hash map
	 */
	public static HashMap<String, ArrayList<Application>> mapToUpdate() {
		try {
			loadApplicationBase();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return verifyApps();
	}

	/**
	 * Parses the application info.
	 * 
	 * @param value
	 *            the value
	 * @param id
	 *            the id
	 * @return the application info
	 */
	private static Application parseApplicationInfo(String value, String id) {
		Application app = new Application();
		String[] values = value.split(Pattern.quote("|"));
		app.setId(id);
		app.setVersion(values[0]);
		app.setTitle(values[1]);
		app.setPathInstallation(values[2]);
		return app;
	}

	/**
	 * Process app.
	 * 
	 * @param app
	 *            the app
	 * @throws Exception
	 *             the exception
	 */
	private static void processApp(Application app) throws Exception {

		Configuration conf = (Configuration) SessionApplication.getInstance()
				.get(SessionApplication.CONFIGURATION);

		Logger.log("Preparando instalação: " + app.getId());
		Logger.log("Version: " + app.getVersion());
		Logger.log("Diretório de instalação: " + app.getPathInstallation());

		if (createStructureFolder(app.getPathInstallation())) {

			String fileName = conf.getDefaultPath() + File.separator
					+ app.getId() + File.separator + app.getVersion()
					+ File.separator + "file.zip";

			File file = new File(fileName);
			if (file.isFile()) {
				Logger.log("Arquivo encontrado.");
				Logger.log(file.getAbsolutePath());
				Logger.log("Tamanho do arquivo: "
						+ FolderUtils.getFolderSize(file.getAbsolutePath())
						+ "bytes");
				Logger.log("Arquivo será descompactado e instalado.");
				Logger.log("Descompactando...");

				ZipUtils.unzip(file, new File(app.getPathInstallation()));

			} else {
				Logger.log("Arquivo não existe no repositório");
				Logger.log("FIm do processamento deste arquivo");
			}

		} else {
			Logger.log("FIm do processamento deste arquivo");
		}
		Logger.log("FIm do processamento deste arquivo");
	}

	/**
	 * Verify apps.
	 *
	 * @return the hash map
	 */
	public static HashMap<String, ArrayList<Application>> verifyApps() {
		ArrayList<Application> listUpdate = new ArrayList<Application>();
		ArrayList<Application> listInstall = new ArrayList<Application>();
		Logger.update("Verificando Aplicativos Instalados.");
		for (Application item : appsRepository) {
			Logger.update("Programa:" + item.getId());
			Logger.update("Verifica se é necessário efetuar atualização.");
			Logger.update("Versão instalada no repositório:"
					+ item.getVersion());
			boolean installed = false;
			for (Application localItem : appsLocalUser) {
				if (item.getId() == localItem.getId()) {
					installed = true;
					Logger.update("Versão Instalada no Cliente: "
							+ localItem.getVersion());
					if (!localItem.getVersion().equals(item.getVersion())) {
						Logger.update(item.getId()
								+ " adicionado à lista de aplicativos que deverão ser atualizados.");
						listUpdate.add(item);
					} else {
						Logger.update(item.getId() + " está atualizado.");
					}
					break;
				} else {
					installed = false;
				}
			}
			if (!installed) {
				Logger.update("Programa não está instalado no cliente.");
				listInstall.add(item);
			}
			Logger.update("Fim da verificação do programa.");
		}
		Logger.update("Fim da validação.");

		HashMap<String, ArrayList<Application>> map = new HashMap<String, ArrayList<Application>>();
		map.put("update", listUpdate);
		map.put("install", listInstall);

		return map;
	}

	/**
	 * Verify need update.
	 *
	 * @return true, if successful
	 */
	public static boolean verifyNeedUpdate() {
		try {
			loadApplicationBase();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		HashMap<String, ArrayList<Application>> map = verifyApps();

		if (map.get("update").size() == 0 && map.get("install").size() == 0) {
			Logger.update("Não é necessário atualizações.");
			return false;
		} else {
			return true;
		}
	}

}