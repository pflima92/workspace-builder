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
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.model.CurrentVersion;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class UninstallComposite.
 */
public class UninstallComposite extends DefaultComposite {

	/**
	 * Check items.
	 *
	 * @param item
	 *            the item
	 * @param checked
	 *            the checked
	 */
	static void checkItems(TreeItem item, boolean checked) {
		item.setGrayed(false);
		item.setChecked(checked);
		TreeItem[] items = item.getItems();
		for (int i = 0; i < items.length; i++) {
			checkItems(items[i], checked);
		}
	}
	
	/**
	 * Check path.
	 *
	 * @param item
	 *            the item
	 * @param checked
	 *            the checked
	 * @param grayed
	 *            the grayed
	 */
	static void checkPath(TreeItem item, boolean checked, boolean grayed) {
		if (item == null)
			return;
		if (grayed) {
			checked = true;
		} else {
			int index = 0;
			TreeItem[] items = item.getItems();
			while (index < items.length) {
				TreeItem child = items[index];
				if (child.getGrayed() || checked != child.getChecked()) {
					checked = grayed = true;
					break;
				}
				index++;
			}
		}
		item.setChecked(checked);
		item.setGrayed(grayed);
		checkPath(item.getParentItem(), checked, grayed);
	}
	
	/** The tree. */
	protected Tree tree;
	
	/**
	 * Instantiates a new uninstall composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public UninstallComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Desinstalar");

		tree = new Tree(this, SWT.BORDER | SWT.CHECK | SWT.MULTI);
		tree.setLinesVisible(true);
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) e.item;
					boolean checked = item.getChecked();
					checkItems(item, checked);
					checkPath(item.getParentItem(), checked, false);
				}
			}
		});
		tree.setHeaderVisible(true);
		tree.setBounds(10, 80, 594, 311);

		TreeColumn trclmnName = new TreeColumn(tree, SWT.NONE);
		trclmnName.setWidth(146);
		trclmnName.setText("Nome");

		TreeColumn trclmnDescrio = new TreeColumn(tree, SWT.NONE);
		trclmnDescrio.setWidth(200);
		trclmnDescrio.setText("Descrição");

		TreeColumn trclmnVerso = new TreeColumn(tree, SWT.NONE);
		trclmnVerso.setWidth(49);
		trclmnVerso.setText("Versão");

		TreeColumn trclmnCaminhoDeInstalao = new TreeColumn(tree, SWT.NONE);
		trclmnCaminhoDeInstalao.setWidth(140);
		trclmnCaminhoDeInstalao.setText("Caminho de Instalação");

		TreeColumn trclmnTipoDoPacote = new TreeColumn(tree, SWT.NONE);
		trclmnTipoDoPacote.setWidth(100);
		trclmnTipoDoPacote.setText("Tipo do Pacote");

		Label lblAbaixoPossvel = new Label(this, SWT.WRAP);
		lblAbaixoPossvel.setBounds(10, 53, 594, 21);
		lblAbaixoPossvel
				.setText("Selecione os programas para remover de seu computador");

		Button btnContinue = new Button(this, SWT.NONE);
		btnContinue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent()).changeContentPanel(ContextHelper.getScreenComposite(getParent(),Constants.SCREEN_CONFIRM_INSTALL));
			}
		});
		btnContinue.setBounds(529, 397, 75, 25);
		btnContinue.setText("Continuar");

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
		btnVoltar.setBounds(448, 397, 75, 25);
		btnVoltar.setText("Voltar");

		populateTable();

	}

	/**
	 * Contain kit in current version.
	 *
	 * @param app
	 *            the app
	 * @return true, if successful
	 */
	private boolean containKitInCurrentVersion(Application app) {
		try {
			CurrentVersion currentVersion = (CurrentVersion) SessionApplication
					.getInstance().get(SessionApplication.CURRENT_INFO);
			for (Application appCurrentVersion : currentVersion.getApplications()) {
				if (appCurrentVersion.getId().equals(app.getId()))
					return true;
			}

		} catch (SessionNotFoundException e) {
			return false;
		}
		return false;
	}

	/**
	 * Populate table.
	 */
	@SuppressWarnings("unchecked")
	private void populateTable() {
		try {
			HashMap<String, Application> kistAvailable = (HashMap<String, Application>) SessionApplication
					.getInstance().get(SessionApplication.APPLICAITONS_AVAILABLE);

			for (Application app : kistAvailable.values()) {
				if (containKitInCurrentVersion(app)) {

					TreeItem treeApp = new TreeItem(tree, 0);
					treeApp.setText(0,app.getTitle());
					treeApp.setText(1,app.getDescription());
					treeApp.setText(2,app.getVersion());
					treeApp.setText(3,app.getPathInstallation());
					treeApp.setText(4,app.getType());
					treeApp.setData(app);	
					
				}
			}

		} catch (SessionNotFoundException e) {
			tree.setVisible(false);
		}

	}
}