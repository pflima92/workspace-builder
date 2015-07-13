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

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Configuration;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class ContextHelper.
 */
public class ContextHelper {

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public static Configuration getConfiguration() {
		try {
			Configuration configuration = (Configuration) SessionApplication
					.getInstance().get(SessionApplication.CONFIGURATION);
			return configuration;
		} catch (SessionNotFoundException e) {
			ExceptionCatcher.catcher(e);
		}
		return null;
	}
	
	/**
	 * Gets the screen composite.
	 *
	 * @param shell
	 *            the shell
	 * @param screenKey
	 *            the screen key
	 * @return the screen composite
	 */
	@SuppressWarnings("unchecked")
	public static DefaultComposite getScreenComposite(Composite shell, String screenKey){
		try {
			HashMap<String, Composite> screens = (HashMap<String, Composite>) SessionApplication.getInstance().get(SessionApplication.SCREENS);
			DefaultComposite composite = (DefaultComposite) screens.get(screenKey);
			composite.setParent(shell);
			return composite;
		} catch (SessionNotFoundException e) {
			ExceptionCatcher.catcher(e);
		} 
		return null;
	}

}
