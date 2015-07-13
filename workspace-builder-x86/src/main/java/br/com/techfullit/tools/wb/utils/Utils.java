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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * Center shell.
	 * 
	 * @param shell
	 *            the shell
	 */
	public static void centerShell(final Shell shell) {
		Rectangle bounds = shell.getMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	/**
	 * Layout default container.
	 * 
	 * @param control
	 *            the control
	 * @return the form data
	 */
	public static FormData layoutDefaultContainer(final Control control) {
		FormData fd_containerApplication = new FormData();
		fd_containerApplication.bottom = new FormAttachment(0, 440);
		fd_containerApplication.right = new FormAttachment(control, 480,
				SWT.RIGHT);
		fd_containerApplication.top = new FormAttachment(0, 10);
		fd_containerApplication.left = new FormAttachment(control, 6);
		return fd_containerApplication;
	}

	/**
	 * Removes the spaces.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	public static String removeSpaces(String name) {
		return name.replaceAll(" " , "");
	}

	
}