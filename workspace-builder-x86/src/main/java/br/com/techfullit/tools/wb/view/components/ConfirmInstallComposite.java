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

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class ConfirmInstallComposite.
 */
public class ConfirmInstallComposite extends DefaultComposite {

	/** The tree. */
	protected Tree tree;

	/**
	 * Instantiates a new confirm install composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public ConfirmInstallComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Confirmar Instalação");

		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(10, 96, 594, 207);

		Label lblAbaixoPossvel = new Label(this, SWT.WRAP);
		lblAbaixoPossvel.setBounds(10, 53, 594, 37);
		lblAbaixoPossvel
				.setText("Os seguintes programas serão instalados em seu computador, o processo pode exigir parametros adicionais para instalação de programas especifícos.");

		Label lblWarning = new Label(this, SWT.WRAP);
		lblWarning.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblWarning.setBounds(10, 309, 594, 21);
		lblWarning
				.setText("*Seu computador pode ficar lento ou travar durante o processo de instalação,");

		final Button btnConfirmarInstalao = new Button(this, SWT.RADIO);
		btnConfirmarInstalao.setText("Confirmar instalação");
		btnConfirmarInstalao.setBounds(456, 358, 148, 16);

		Button btnNoConfirmarInstalao = new Button(this, SWT.RADIO);
		btnNoConfirmarInstalao.setText("Não confirmar instalação");
		btnNoConfirmarInstalao.setBounds(456, 380, 148, 16);

		
		Button btnModoAvanadoalguns = new Button(this, SWT.CHECK);
		btnModoAvanadoalguns.setBounds(10, 336, 526, 16);
		btnModoAvanadoalguns
				.setText("Modo avançado (Alguns programas podem ser configurados de acordo com sua necessidade).");

		Button btnContinuar = new Button(this, SWT.NONE);
		btnContinuar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnConfirmarInstalao.getSelection()) {
					((ApplicationShell) getParent())
					.changeContentPanel(ContextHelper
							.getScreenComposite(getParent(),
									Constants.SCREEN_INSTALLER));
				} else {
					AppMessageBox
							.warningBox(getShell(), Constants.ATTENTION,
									"Você precisa confirmar para conlcuir a intalação.");
				}

			}
		});
		btnContinuar.setBounds(529, 405, 75, 25);
		btnContinuar.setText("Continuar");

		
		Button btnVoltar = new Button(this, SWT.NONE);
		btnVoltar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent())
						.changeContentPanel(ContextHelper
								.getScreenComposite(getParent(),
										Constants.SCREEN_STATUS));
			}
		});
		btnVoltar.setBounds(448, 405, 75, 25);
		btnVoltar.setText("Cancelar");

	}

	/**
	 * Perform install.
	 */
	private void performInstall() {

	}

	/**
	 * Populate tree.
	 */
	@SuppressWarnings("unchecked")
	private void populateTree() {
		try {
			tree.removeAll();
			tree.setVisible(true);
			HashMap<String, Application> applicationSelected = (HashMap<String, Application>) SessionApplication
					.getInstance()
					.get(SessionApplication.APPLICATIONS_SELECTED);

			TreeItem treePrograms = new TreeItem(tree, 0);
			treePrograms.setText("Programas Selecionados");
			int count = 0;
			for (Application app : applicationSelected.values()) {
				TreeItem treeApp = new TreeItem(treePrograms, count);
				treeApp.setText(0, app.getTitle());
				treeApp.setData(app);
				count++;
			}
		} catch (SessionNotFoundException e) {
			tree.setVisible(false);
		}

	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.view.common.DefaultComposite#prepare()
	 */
	@Override
	public void prepare() {
		super.prepare();
		populateTree();
	}
}