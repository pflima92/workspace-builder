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

import br.com.techfullit.tools.wb.logger.Logger;
import br.com.techfullit.tools.wb.utils.Constants;

/**
 * The Class ClientInstallerProcessor.
 */
public class ClientInstallerProcessor {

	/**
	 * Install client.
	 *
	 * @return true, if successful
	 */
	public static boolean installClient() {
		Logger.log("Prepara instalaçãoo do Client");
		File file = new File("Constants.DEFAULT_CLIENT_REP");
		File clientDestine = null;
		Logger.log("Recupera informaçõees no repositório");
		if (System.getProperty("os.name").equals(Constants.WINDOWS7)) {
			clientDestine = new File(System.getProperty("user.home")
					+ File.separator + "Constants.CLIENT_DESTINE_7");
		} else {
			clientDestine = new File(System.getProperty("user.home")
					+ File.separator + "Constants.CLIENT_DESTINE_XP");
		}
		if (!new File(clientDestine.getAbsolutePath()).exists()) {
			clientDestine.mkdirs();
		}

		if (clientDestine.exists())
			clientDestine.delete();
		Logger.log("Faz transferência do arquivo");
		if(file.renameTo(clientDestine)){
			Logger.log("Fim do processo de instalação do Client");
			return true;
		}else{
			return false;
		}
	}

}
