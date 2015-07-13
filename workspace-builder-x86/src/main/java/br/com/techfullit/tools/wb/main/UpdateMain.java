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
package br.com.techfullit.tools.wb.main;

import br.com.techfullit.tools.wb.processors.UpdateProcessor;
import br.com.techfullit.tools.wb.view.components.UpdaterShell;

/**
 * The Class UpdateMain.
 */
public class UpdateMain {
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		if(UpdateProcessor.verifyNeedUpdate()){
			UpdaterShell window = new UpdaterShell();
			window.open();
		}else{
		}
	}
	
}
