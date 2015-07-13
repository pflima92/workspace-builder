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
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import br.com.techfullit.tools.wb.main.ApplicationLauncher;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.Utils;
import br.com.techfullit.tools.wb.view.common.AppMessageBox;
import br.com.techfullit.tools.wb.view.common.DefaultComposite;

/**
 * The Class ApplicationShell.
 */
public class ApplicationShell extends Shell {
	
	/** The default composite. */
	private static Composite defaultComposite;
	
	/** The shell. */
	protected static ApplicationShell shell;
	
	/** The lbl registerfor. */
	protected static Label lblRegisterfor;
	
	/**
	 * Sets the default composite.
	 *
	 * @param defaultComposite
	 *            the new default composite
	 */
	public static void setDefaultComposite(Composite defaultComposite) {
		ApplicationShell.defaultComposite = defaultComposite;
	}
	
	/** The stack layout. */
	protected final StackLayout stackLayout;
	
	/** The current step. */
	private int currentStep;
	
	/** The lbl verso. */
	private Label lblVerso;
	
	/** The lbl version. */
	private Label lblVersion;

	/**
	 * Create the shell.
	 *
	 * @param display
	 *            the display
	 */
	public ApplicationShell(Display display) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.TITLE | SWT.APPLICATION_MODAL);
		setImage(SWTResourceManager.getImage(ApplicationShell.class, "/javax/swing/plaf/metal/icons/ocean/computer.gif"));
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				AppMessageBox.exitWindow(ApplicationLauncher.applicationShell);
			}
		});
		setMinimumSize(new Point(640, 480));
		
		stackLayout = new StackLayout();
		defaultComposite = new Composite(getShell(), SWT.NONE);
		defaultComposite.setLayout(stackLayout);
		
		Label lblRegistradoPara = new Label(this, SWT.NONE);
		lblRegistradoPara.setBounds(10, 455, 88, 15);
		lblRegistradoPara.setText("Registrado para: ");

		lblRegisterfor = new Label(this, SWT.NONE);
		lblRegisterfor.setBounds(104, 455, 246, 15);
		
		lblVerso = new Label(this, SWT.NONE);
		lblVerso.setBounds(516, 455, 47, 15);
		lblVerso.setText("Versão:");
		
		lblVersion = new Label(this, SWT.NONE);
		lblVersion.setBounds(569, 455, 55, 15);
		
		createContents();
	}
	
	/**
	 * Change content panel.
	 *
	 * @param newComposite
	 *            the new composite
	 */
	public void changeContentPanel(DefaultComposite newComposite) {
		if(stackLayout.topControl != null)stackLayout.topControl.setVisible(!stackLayout.topControl.isVisible());
		stackLayout.topControl = newComposite;
		if(!stackLayout.topControl.isVisible())stackLayout.topControl.setVisible(true);
		getDefaultComposite().layout();
		newComposite.prepare();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Decorations#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setSize(640, 508);
	}
	
	/**
	 * Gets the default composite.
	 *
	 * @return the default composite
	 */
	public Composite getDefaultComposite() {
		return defaultComposite;
	}

	/**
	 * Launch the application.
	 */
	public void initWindow() {
		try {
			Display display = Display.getDefault();
			shell = new ApplicationShell(display);
			Utils.centerShell(shell);
			shell.open();
			shell.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Shell#open()
	 */
	@Override
	public void open() {
		super.open();
		setText(ContextHelper.getConfiguration().getApplicationName() + " - " + ContextHelper.getConfiguration().getVersion());
		lblRegisterfor.setText(ContextHelper.getConfiguration().getRegister());
		lblVersion.setText(ContextHelper.getConfiguration().getVersion());
		prepareStep(0);
	}

	/**
	 * Prepare step.
	 *
	 * @param step
	 *            the step
	 */
	public void prepareStep(int step){
		switch (currentStep) {
		case 0:
			changeContentPanel(ContextHelper.getScreenComposite(this,Constants.SCREEN_TERMS));
			break;
		case 1:
			changeContentPanel(ContextHelper.getScreenComposite(this,Constants.SCREEN_STATUS));
			break;
		default:
			break;
		}
	}
}
