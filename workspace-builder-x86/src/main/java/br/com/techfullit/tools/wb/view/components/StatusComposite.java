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

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.model.CurrentVersion;
import br.com.techfullit.tools.wb.model.Kit;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class StatusComposite.
 */
public class StatusComposite extends DefaultComposite {

	/** The tree. */
	protected Tree tree;

	/** The composite no config. */
	protected Composite compositeNoConfig;

	/**
	 * Instantiates a new status composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public StatusComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Status do Sistema");

		tree = new Tree(this, SWT.BORDER);
		tree.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tree.setBounds(10, 124, 594, 186);

		Label lblAbaixoPossvel = new Label(this, SWT.WRAP);
		lblAbaixoPossvel.setBounds(10, 53, 594, 37);
		lblAbaixoPossvel
				.setText("Abaixo é possível visualizar todos os programas instalados pelo Workspace Builder. Em caso de dúvidas contate o administrador do Sistema.");

		Label lblWarning = new Label(this, SWT.WRAP);
		lblWarning.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblWarning.setBounds(10, 316, 594, 37);
		lblWarning
				.setText("*Caso um aplicativo não tenha sido instalado usando o Workspace Builder, o mesmo não ira aparecer nesta relação. Recomendamos a utilização do programa, sempre que for necessário montar um ambiente.");

		Link linkHelper = new Link(this, SWT.NONE);
		linkHelper.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Program.launch(System.getProperty("user.dir")
						+ Constants.FILES_PATH + File.separator
						+ "WorkspaceBuilder_SaibaMais.txt");
			}
		});
		linkHelper.setBounds(232, 103, 372, 15);
		linkHelper
				.setText("<a>Saiba mais como ajudar na construção e melhorias deste programa...</a>");

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent())
						.changeContentPanel(ContextHelper
								.getScreenComposite(getParent(),
										Constants.SCREEN_KIT_INSTALL));
			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage(
				StatusComposite.class,
				"/com/sun/java/swing/plaf/windows/icons/ListView.gif"));
		btnNewButton.setBounds(10, 359, 180, 63);
		btnNewButton.setText("Instalar Kit");

		Button btnInstadorManual = new Button(this, SWT.NONE);
		btnInstadorManual.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent())
						.changeContentPanel(ContextHelper
								.getScreenComposite(getParent(),
										Constants.SCREEN_MANUAL_INSTALL));
			}
		});
		btnInstadorManual.setText("Instalador Manual");
		btnInstadorManual.setImage(SWTResourceManager.getImage(
				StatusComposite.class,
				"/com/sun/java/swing/plaf/windows/icons/DetailsView.gif"));
		btnInstadorManual.setBounds(224, 359, 180, 63);

		Button btnDesinstalar = new Button(this, SWT.NONE);
		btnDesinstalar.setEnabled(false);
		btnDesinstalar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent())
						.changeContentPanel(ContextHelper
								.getScreenComposite(getParent(),
										Constants.SCREEN_UNINSTALL));
			}
		});
		btnDesinstalar.setText("Desinstalar");
		btnDesinstalar.setImage(SWTResourceManager.getImage(
				StatusComposite.class,
				"/javax/swing/plaf/metal/icons/ocean/paletteClose.gif"));
		btnDesinstalar.setBounds(424, 359, 180, 63);

		compositeNoConfig = new Composite(this, SWT.BORDER);
		compositeNoConfig.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		compositeNoConfig.setBounds(10, 124, 594, 186);

		Label lblNoLocalizamosNenhum = new Label(compositeNoConfig, SWT.WRAP);
		lblNoLocalizamosNenhum.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		lblNoLocalizamosNenhum.setFont(SWTResourceManager.getFont("Segoe UI",
				13, SWT.NORMAL));
		lblNoLocalizamosNenhum.setBounds(10, 10, 574, 94);
		lblNoLocalizamosNenhum
				.setText("Não localizamos nenhum programa ou Kit instalado em seu ambiente. Escolha um método abaixo para configurar seu computador.");

		compositeNoConfig.setVisible(false);

		populateTree();
	}

	/**
	 * Populate tree.
	 */
	private void populateTree() {
		try {
			CurrentVersion currentVersion = (CurrentVersion) SessionApplication
					.getInstance().get(SessionApplication.CURRENT_INFO);
			int count = 0;
			compositeNoConfig.setVisible(false);
			if (!currentVersion.getKits().isEmpty()) {
				TreeItem treeKit = new TreeItem(tree, count);
				treeKit.setText("Kit's Instalados");
				int item = 0;
				for (Kit kit : currentVersion.getKits()) {
					TreeItem treeKitItem1 = new TreeItem(treeKit, item);
					treeKitItem1.setExpanded(true);
					treeKitItem1.setText(0, kit.getId());
					treeKitItem1.setText(1, kit.getVersion());
					item++;
				}
				treeKit.setExpanded(true);
				count++;
			}

			if (!currentVersion.getApplications().isEmpty()) {
				TreeItem treePrograms = new TreeItem(tree, count);
				treePrograms.setText("Programas Instalados");
				int item = 0;
				for (Application app : currentVersion.getApplications()) {
					TreeItem treeProgramItem = new TreeItem(treePrograms, item);
					treeProgramItem.setText(0, app.getId());
					treeProgramItem.setText(1, app.getVersion());
					item++;
				}
			}

		} catch (SessionNotFoundException e) {
			tree.setVisible(false);
			compositeNoConfig.setVisible(true);

		}

	}
}