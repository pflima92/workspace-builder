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
package br.com.techfullit.tools.wb.view.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;

/**
 * The Class UpdaterShell.
 */
public class UpdaterShell {

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			UpdaterShell window = new UpdaterShell();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();

		Shell shlAfsUpdateClient = new Shell(SWT.MIN | SWT.TITLE);
		shlAfsUpdateClient.setSize(450, 280);
		shlAfsUpdateClient.setText(ContextHelper.getConfiguration().getApplicationName());

		Label lblAfsUpdateClient = new Label(shlAfsUpdateClient, SWT.NONE);
		lblAfsUpdateClient.setFont(SWTResourceManager.getFont("Segoe UI", 13,
				SWT.BOLD));
		lblAfsUpdateClient.setBounds(10, 10, 424, 23);
		lblAfsUpdateClient.setText("AFS Update Client");

		if (AppMessageBox
				.confirmBox(
						shlAfsUpdateClient,
						"Verificamos que você possui algumas aplicações desatualizadas em seu computador, gostaria de atualizar seu ambiente?")) {
			shlAfsUpdateClient.setVisible(true);
			shlAfsUpdateClient.open();
		} else {
			System.exit(0);
		}

		shlAfsUpdateClient.layout();
		while (!shlAfsUpdateClient.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
