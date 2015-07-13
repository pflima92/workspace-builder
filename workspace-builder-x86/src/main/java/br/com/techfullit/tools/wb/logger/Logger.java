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
package br.com.techfullit.tools.wb.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Configuration;
import br.com.techfullit.tools.wb.utils.SessionApplication;

/**
 * The Class Logger.
 */
public class Logger {

	/** The Constant LOG. */
	private static final String LOG = "log";
	
	/** The Constant ERROR. */
	private static final String ERROR = "error";
	
	/** The Constant SECURITY. */
	private static final String SECURITY = "security";
	
	/** The Constant UPDATE. */
	private static final String UPDATE = "update";

	/**
	 * Log.
	 * 
	 * @param arg
	 *            the arg
	 */
	public static void debug(String arg) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssss");
		String linha = "USER:[" + System.getProperty("user.name") + "]"
				+ sdf.format(new Date()) + " - " + arg;

		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();

			if (ip != null) {
				linha = "IPADRESS:[" + ip + "] - " + linha;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(linha);
	}

	/**
	 * Error.
	 * 
	 * @param arg
	 *            the arg
	 */
	public static void error(String arg) {
		impl(arg, ERROR, true);
	}

	/**
	 * Impl.
	 *
	 * @param arg
	 *            the arg
	 * @param type
	 *            the type
	 * @param persist
	 *            the persist
	 */
	private synchronized static void impl(String arg, String type,
			boolean persist) {
		Configuration configuration = null;
		try {
			configuration = (Configuration) SessionApplication
					.getInstance().get(SessionApplication.CONFIGURATION);
		} catch (SessionNotFoundException e1) {
			e1.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssss");
		File fileLog = new File(configuration.getDefaultPath() + File.separator
				+ "logs" + File.separator + System.getProperty("user.name")
				+ File.separator + type + ".txt");
		if (!fileLog.exists()) {
			new File(fileLog.getParent()).mkdirs();
			try {
				fileLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String linha = "USER:[" + System.getProperty("user.name") + "]"
					+ sdf.format(new Date()) + " - " + arg;

			String ip = InetAddress.getLocalHost().getHostAddress();
			if (ip != null) {
				linha = "IPADRESS:[" + ip + "] - " + linha;
			}
			System.out.println(linha);
			if (persist) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						fileLog, true));

				writer.write(linha + System.getProperty("line.separator"));

				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			System.out.println("Erro ao efetuar log:\n" + e.getMessage());
		}
	}

	/**
	 * Log.
	 * 
	 * @param arg
	 *            the arg
	 */
	public static void log(String arg) {
		impl(arg, LOG, true);
	}

	/**
	 * Security.
	 * 
	 * @param arg
	 *            the arg
	 */
	public static void security(String arg) {
		impl(arg, SECURITY, true);
	}

	/**
	 * Update.
	 *
	 * @param arg
	 *            the arg
	 */
	public static void update(String arg) {
		impl(arg, UPDATE, true);
	}

}
