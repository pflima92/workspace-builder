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
package br.com.techfullit.tools.wb.view.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * The Class Images.
 */
public class Images {

	/** The back ground login. */
	private Image backGroundLogin;

	/**
	 * Instantiates a new images.
	 */
	public Images() {

		backGroundLogin = new Image(Display.getDefault(),
				Images.class
						.getResourceAsStream("/images/login-background.png"));

	}

	/**
	 * Gets the back ground login.
	 * 
	 * @return the back ground login
	 */
	public Image getBackGroundLogin() {
		return backGroundLogin;
	}

}
