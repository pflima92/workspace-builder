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
package br.com.techfullit.tools.wb.processors;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.SessionApplication;
import br.com.techfullit.tools.wb.view.components.ApplicationShell;
import br.com.techfullit.tools.wb.view.components.ConfirmInstallComposite;
import br.com.techfullit.tools.wb.view.components.InstallerComposite;
import br.com.techfullit.tools.wb.view.components.KitInstallComposite;
import br.com.techfullit.tools.wb.view.components.ManualInstallComposite;
import br.com.techfullit.tools.wb.view.components.ResultsComposite;
import br.com.techfullit.tools.wb.view.components.StatusComposite;
import br.com.techfullit.tools.wb.view.components.TermComposite;
import br.com.techfullit.tools.wb.view.components.UninstallComposite;

/**
 * The Class ScreenLoaderProcessor.
 */
public class ScreenLoaderProcessor extends Processor {
	
	/** The application shell. */
	private ApplicationShell applicationShell;
	
	/**
	 * Instantiates a new screen loader processor.
	 *
	 * @param args
	 *            the args
	 */
	public ScreenLoaderProcessor(Object... args) {
		setApplicationShell((ApplicationShell) args[0]);
	}
	
	
	/**
	 * Gets the application shell.
	 *
	 * @return the application shell
	 */
	public ApplicationShell getApplicationShell() {
		return applicationShell;
	}


	/**
	 * Sets the application shell.
	 *
	 * @param applicationShell
	 *            the new application shell
	 */
	public void setApplicationShell(ApplicationShell applicationShell) {
		this.applicationShell = applicationShell;
	}


	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {
		HashMap<String, Composite> screens = new HashMap<String, Composite>();
		screens.put(Constants.SCREEN_TERMS, new TermComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_STATUS, new StatusComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_KIT_INSTALL, new KitInstallComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_MANUAL_INSTALL, new ManualInstallComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_UNINSTALL, new UninstallComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_CONFIRM_INSTALL, new ConfirmInstallComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_INSTALLER, new InstallerComposite(applicationShell, SWT.NONE));
		screens.put(Constants.SCREEN_RESULTS, new ResultsComposite(applicationShell, SWT.NONE));
		SessionApplication.getInstance().put(SessionApplication.SCREENS, screens);
		return true;
	}

}
