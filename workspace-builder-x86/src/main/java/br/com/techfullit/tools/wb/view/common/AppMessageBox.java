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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;

/**
 * The Class AppMessageBox.
 */
public class AppMessageBox {

	/**
	 * Confirm box.
	 * 
	 * @param shell
	 *            the shell
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	public static boolean confirmBox(final Shell shell, final String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
				| SWT.YES | SWT.NO);
		messageBox.setMessage(message);
		messageBox.setText(ContextHelper.getConfiguration().getApplicationName());
		int response = messageBox.open();

		if (response == SWT.YES) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Critical box.
	 *
	 * @param title
	 *            the title
	 * @param textContent
	 *            the text content
	 */
	public static void criticalBox(final String title,
			final String textContent) {
		MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()), SWT.ICON_ERROR);
		messageBox.setText(title);
		messageBox.setMessage("Ocorreu um erro grave na execução: " + textContent);
		messageBox.open();
	}
	
	/**
	 * Erro box.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the title
	 * @param textContent
	 *            the text content
	 */
	public static void erroBox(final Shell shell, final String title,
			final String textContent) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
		messageBox.setText(title);
		messageBox.setMessage(textContent);
		messageBox.open();
	}

	/**
	 * Exit window.
	 * 
	 * @param shell
	 *            the shell
	 */
	public static void exitWindow(final Shell shell) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
				| SWT.YES | SWT.NO);
		messageBox.setMessage("Deseja sair do " + ContextHelper.getConfiguration().getApplicationName()
				+ "?");
		messageBox.setText(ContextHelper.getConfiguration().getApplicationName());
		int response = messageBox.open();
		if (response == SWT.YES) {
			System.exit(0);
		} else {
			return;
		}

	}

	/**
	 * Information box.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the title
	 * @param textContent
	 *            the text content
	 */
	public static void informationBox(final Shell shell, String title,
			final String textContent) {
		if (title == null) {
			title = Constants.ATTENTION;
		}
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		messageBox.setText(title);
		messageBox.setMessage(textContent);
		messageBox.open();
	}

	/**
	 * Warning box.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the title
	 * @param textContent
	 *            the text content
	 */
	public static void warningBox(final Shell shell, String title,
			final String textContent) {
		if (title == null) {
			title = Constants.WARNING;
		}
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
		messageBox.setText(title);
		messageBox.setMessage(textContent);
		messageBox.open();
	}
}
