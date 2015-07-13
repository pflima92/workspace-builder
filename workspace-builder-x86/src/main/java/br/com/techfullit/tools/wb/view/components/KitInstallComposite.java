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
import br.com.techfullit.tools.wb.model.Kit;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class KitInstallComposite.
 */
public class KitInstallComposite extends DefaultComposite {

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
	 * Instantiates a new kit install composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public KitInstallComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Instalar KIT de Ambiente");

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
		trclmnName.setWidth(220);
		trclmnName.setText("Nome");

		TreeColumn trclmnDescrio = new TreeColumn(tree, SWT.NONE);
		trclmnDescrio.setWidth(319);
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
				.setText("Selecione os kits de instalação para configurar seu ambiente.");

		Button btnContinue = new Button(this, SWT.NONE);
		btnContinue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				confirmInstall();
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
	 * Confirm install.
	 */
	private void confirmInstall() {
		HashMap<String, Application> applicationsSelected = new HashMap<String, Application>();

		for (int i = 0; i < tree.getItemCount(); i++) {
			TreeItem currentKit = tree.getItem(i);
			if (currentKit.getChecked()) {
				for (int j = 0; j < currentKit.getItemCount(); j++) {
					TreeItem currentItem = currentKit.getItem(i);
					if (currentItem.getChecked()) {
						Application application = (Application) currentItem.getData();
						applicationsSelected.put(application.getId(), application);
					}
				}
			}
		}
		
		if(applicationsSelected.isEmpty()){
			AppMessageBox.informationBox(getShell(), Constants.ATTENTION, "Selecione ao menos um item para avançar.");
			return;
		}

		
		SessionApplication.getInstance().put(SessionApplication.APPLICATIONS_SELECTED, applicationsSelected);
		
		// APPLICATIONS_SELECTED
		((ApplicationShell) getParent())
				.changeContentPanel(ContextHelper
						.getScreenComposite(getParent(),
								Constants.SCREEN_CONFIRM_INSTALL));

	}

	/**
	 * Contain kit in current version.
	 *
	 * @param kit
	 *            the kit
	 * @return true, if successful
	 */
	private boolean containKitInCurrentVersion(Kit kit) {
		try {
			CurrentVersion currentVersion = (CurrentVersion) SessionApplication
					.getInstance().get(SessionApplication.CURRENT_INFO);
			for (Kit kitCurrentVersion : currentVersion.getKits()) {
				if (kitCurrentVersion.getId().equals(kit.getId()))
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
			HashMap<String, Kit> kistAvailable = (HashMap<String, Kit>) SessionApplication
					.getInstance().get(SessionApplication.KIT_AVAILABLE);

			for (Kit kit : kistAvailable.values()) {
				if (!containKitInCurrentVersion(kit)) {

					TreeItem treeKit = new TreeItem(tree, 0);
					treeKit.setText(0, kit.getTitle());
					treeKit.setText(1, kit.getDescription());
					treeKit.setText(2, kit.getVersion());
					treeKit.setData(kit);

					for (Application app : kit.getApplications()) {
						TreeItem treeKitItem1 = new TreeItem(treeKit, 0);
						treeKitItem1.setText(0, app.getTitle());
						treeKitItem1.setText(1, app.getDescription());
						treeKitItem1.setText(2, app.getVersion());
						treeKitItem1.setText(3, app.getPathInstallation());
						treeKitItem1.setText(4, app.getType());
						treeKitItem1.setData(app);
					}
				}
			}

		} catch (SessionNotFoundException e) {
			tree.setVisible(false);
		}

	}

}