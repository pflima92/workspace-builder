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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
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
 * The Class ResultsComposite.
 */
public class ResultsComposite extends DefaultComposite {

	/** The tree. */
	protected Tree tree;
	
	/**
	 * Instantiates a new results composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public ResultsComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Instalação concluída");

		Label lblAbaixoPossvel = new Label(this, SWT.WRAP);
		lblAbaixoPossvel.setBounds(10, 53, 594, 25);
		lblAbaixoPossvel
				.setText("Seu computador foi configurado com sucesso");

		Button btnContinuar = new Button(this, SWT.NONE);
		btnContinuar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AppMessageBox.informationBox(getShell(), "Finalizar Aplicação", "A instalação foi finalizada, deseja sair do Workspace Builder.");
				System.exit(0);
			}
		});
		btnContinuar.setBounds(529, 405, 75, 25);
		btnContinuar.setText("Finalizar");
		
		tree = new Tree(this, SWT.BORDER);
		tree.setBounds(10, 84, 594, 279);
		
		Link link = new Link(this, SWT.NONE);
		link.setBounds(10, 369, 147, 15);
		link.setText("<a>Acessar logs da instalação</a>");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ApplicationShell) getParent())
				.changeContentPanel(ContextHelper
						.getScreenComposite(getParent(),
								Constants.SCREEN_STATUS));
			}
		});
		button.setText("Voltar");
		button.setBounds(448, 405, 75, 25);

	}

	/**
	 * Populate tree.
	 */
	@SuppressWarnings("unchecked")
	private void populateTree() {
		try {
			List<Application> installSuccess = (List<Application>) SessionApplication.getInstance().get(SessionApplication.INSTALL_APPS_SUCCESS);
			List<Application> installFailed = (List<Application>) SessionApplication.getInstance().get(SessionApplication.INSTALL_APPS_FAILED);
			
			
			int count = 0;
			if (!installSuccess.isEmpty()) {
				TreeItem treeKit = new TreeItem(tree, count);
				treeKit.setText("Instalados com Sucesso");
				int item = 0;
				for (Application app : installSuccess) {
					TreeItem treeKitItem1 = new TreeItem(treeKit, item);
					treeKitItem1.setExpanded(true);
					treeKitItem1.setText(0, app.getTitle());
					treeKitItem1.setText(1, app.getVersion());
					item++;
				}
				treeKit.setExpanded(true);
				count++;
			}

			if (!installFailed.isEmpty()) {
				TreeItem treePrograms = new TreeItem(tree, count);
				treePrograms.setText("Erro na Instalação");
				int item = 0;
				for (Application app : installFailed) {
					TreeItem treeKitItem1 = new TreeItem(treePrograms, item);
					treeKitItem1.setExpanded(true);
					treeKitItem1.setText(0, app.getTitle());
					treeKitItem1.setText(1, app.getVersion());
					item++;
				}
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