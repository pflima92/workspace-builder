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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.main.ApplicationLauncher;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class TermComposite.
 */
public class TermComposite extends DefaultComposite {

	/**
	 * Instantiates a new term composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public TermComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTerm = new Label(this, SWT.BORDER | SWT.WRAP);
		lblTerm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTerm.setBounds(10, 53, 594, 274);

		StringBuffer termo = new StringBuffer();
		termo.append("1. Este é um software desenvolvido sem fins lucrativos, para a instalação e configuração do ambiente.\n\n");
		termo.append("2. É proipida a venda ou disponibilização sem prévia autorização deste software, sendo passível de punição o profissional que agir de má fé com este software.\n\n");
		termo.append("3. Esté é um client de configuração de ambiente automatizada para efetuar a instalação dos softwares que foram julgados necessários para o acompanhamento da equipe.\n\n");
		termo.append("4. A instalação deste software podera acarretar na perda de informações, por isso não é de responsabilidade do criador possíveis danos que possam ocorrer durante os procedimentos de instalação do mesmo.\n\n");
		termo.append("5. Este software foi desenvolvido para facilitar a configuração e instalação do ambente. Após a instalação deste software fique ciente que em sua maquina sera instalado um script de verificação de novas atualizações, e será adicionado dados ao registro do sistema operacional.\n\n");

		lblTerm.setText(termo.toString());

		final Button btnAceitoOsTermos = new Button(this, SWT.RADIO);
		btnAceitoOsTermos.setBounds(456, 339, 148, 16);
		btnAceitoOsTermos.setText("Aceito os Termos");

		final Button btnNoAceitoOs = new Button(this, SWT.RADIO);
		btnNoAceitoOs.setBounds(456, 361, 148, 16);
		btnNoAceitoOs.setText("Não aceito os Termos");

		Button btnContinuar = new Button(this, SWT.NONE);
		btnContinuar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnAceitoOsTermos.getSelection()) {
					((ApplicationShell) getParent()).changeContentPanel(ContextHelper.getScreenComposite(getParent(),Constants.SCREEN_STATUS));
				} else {
					AppMessageBox.exitWindow(ApplicationLauncher.applicationShell);
				}
			}
		});
		btnContinuar.setBounds(529, 396, 75, 25);
		btnContinuar.setText("Continuar");

		Label lblTermosDeUso = new Label(this, SWT.NONE);
		lblTermosDeUso.setFont(SWTResourceManager.getFont("Segoe UI", 18,
				SWT.NORMAL));
		lblTermosDeUso.setBounds(10, 10, 194, 37);
		lblTermosDeUso.setText("Termos de Uso:");
	}
}
