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

import br.com.techfullit.tools.wb.logger.Logger;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;

/**
 * The Class ExceptionCatcher.
 */
public class ExceptionCatcher {

	/**
	 * Catcher.
	 *
	 * @param e
	 *            the e
	 */
	public static void catcher(Exception e){
		try {
			Logger.error(e.getMessage());
		} catch (Exception e2) {
			System.out.println("Não logou");
		}
		AppMessageBox.criticalBox("Erro grave", null == e.getMessage() ? Reflection.toString(e) : e.getMessage());
		System.exit(0);
	}
	
}
