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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;
import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.processors.ApplicationInstallerProcessor;
import br.com.techfullit.tools.wb.processors.Processor;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.ExceptionCatcher;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class InstallerComposite.
 */
public class InstallerComposite extends DefaultComposite {

	/** The splash pos. */
	private int splashPos = 0;

	/** The splash max. */
	private final int SPLASH_MAX = 100;

	/** The progress bar. */
	protected ProgressBar progressBar;
	
	/** The label application. */
	protected Label labelApplication;
	
	/**
	 * Instantiates a new installer composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public InstallerComposite(Composite parent, int style) {
		super(parent, style);
		this.setBounds(10, 10, 614, 432);

		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblTitle.setBounds(10, 10, 469, 37);
		lblTitle.setText("Instalando");

		Label lblAbaixoPossvel = new Label(this, SWT.WRAP);
		lblAbaixoPossvel.setBounds(10, 53, 594, 25);
		lblAbaixoPossvel
				.setText("Aguarde enquanto o programa de instalação está configurando seu ambiente.");

		Label lblWarning = new Label(this, SWT.WRAP);
		lblWarning.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblWarning.setBounds(10, 309, 594, 21);
		lblWarning
				.setText("*Seu computador pode ficar lento ou travar durante o processo de instalação,");

		Composite composite = new Composite(this, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		composite.setBounds(10, 82, 594, 215);
		
		Label lblOWorskpaceBuilder = new Label(composite, SWT.WRAP | SWT.CENTER);
		lblOWorskpaceBuilder.setFont(SWTResourceManager.getFont("Minion Pro SmBd", 31, SWT.NORMAL));
		lblOWorskpaceBuilder.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		lblOWorskpaceBuilder.setBounds(10, 10, 570, 191);
		lblOWorskpaceBuilder.setText("Aguarde... O Worskpace Builder está configurando seu computador.");

		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setBounds(10, 369, 594, 17);

		Label lblInstalando = new Label(this, SWT.WRAP);
		lblInstalando.setText("Instalando:");
		lblInstalando.setBounds(10, 342, 65, 21);
		
		labelApplication = new Label(this, SWT.WRAP);
		labelApplication.setBounds(81, 342, 65, 21);

	}

	/**
	 * Perform install.
	 */
	@SuppressWarnings({ "unchecked" })
	private void performInstall() {
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				
				List<Application> installSuccess = new ArrayList<Application>();
				List<Application> installFailed  = new ArrayList<Application>();
				try {
					HashMap<String, Application> applicationSelected = (HashMap<String, Application>) SessionApplication
							.getInstance().get(
									SessionApplication.APPLICATIONS_SELECTED);

					List<Processor> installerProcessors = new ArrayList<Processor>();
					for (Application application : applicationSelected.values()) {
						installerProcessors
								.add(new ApplicationInstallerProcessor(
										application));
					}

					int splashCount = SPLASH_MAX / installerProcessors.size();

					for (Processor processor : installerProcessors) {
						try {
							labelApplication.setText(((ApplicationInstallerProcessor) processor).getApplicationSelected().getTitle());
							boolean result = processor.start();
							if(result){
								installSuccess.add(((ApplicationInstallerProcessor) processor).getApplicationSelected());
							}else{
								installFailed.add(((ApplicationInstallerProcessor) processor).getApplicationSelected());
							}
						} catch (Exception e) {
							ExceptionCatcher.catcher(e);
						}
						splashPos += splashPos + splashCount;
						progressBar.setSelection(splashPos);
					}

				} catch (SessionNotFoundException e) {
					ExceptionCatcher.catcher(e);
				}
				
				SessionApplication.getInstance().put(SessionApplication.INSTALL_APPS_SUCCESS, installSuccess);
				SessionApplication.getInstance().put(SessionApplication.INSTALL_APPS_FAILED, installFailed);

				// APPLICATIONS_SELECTED
				((ApplicationShell) getParent())
						.changeContentPanel(ContextHelper
								.getScreenComposite(getParent(),
										Constants.SCREEN_RESULTS));
				
			}
		});

		while (splashPos != SPLASH_MAX) {
			if (!getDisplay().readAndDispatch()) {
				getDisplay().sleep();
			}
		}
	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.view.common.DefaultComposite#prepare()
	 */
	@Override
	public void prepare() {
		super.prepare();
		performInstall();
	}
}