/**
 
Copyright - 2015 - Paulo Henrique Ferreira de Lima - TechFull IT Services
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 

*/
package br.com.techfullit.certifiedserver.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.com.techfullit.certifiedserver.utils.FolderUtils;

/**
 * The Class Principal.
 */
public class Principal extends Shell {

	/** The text. */
	private static Text text;
	
	/** The Constant SERVER_PATH. */
	private static final String SERVER_PATH = "E:\\env-rep\\certificados";
	
	/** The Constant SCRIPT_FILE. */
	private static final File SCRIPT_FILE = new File(
			"E:\\env-rep\\certificados\\certificadosscript.bat");
	
	/** The keytool file. */
	private static File KEYTOOL_FILE;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Principal shell = new Principal(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 *
	 * @param display the display
	 */
	public Principal(Display display) {
		super(display, SWT.CLOSE | SWT.TITLE);
		setSize(450, 331);

		Label lblObservaes = new Label(this, SWT.WRAP);
		lblObservaes.setBounds(10, 133, 414, 33);
		lblObservaes
				.setText("Observações: Este processo automatiza a importação dos certificados do ambiente de SIT do projeto HPA Replacement. ");

		Label lblAInstalaoConsiste = new Label(this, SWT.WRAP);
		lblAInstalaoConsiste.setBounds(10, 172, 415, 66);
		lblAInstalaoConsiste
				.setText("A instalação consiste em selecionar o diretório da JDK que é responsável pela VM do Servidor de Aplicação, após clicar em aplicar o processo invoca o script de importação, caso você não tenha nenhum alias já definido basta digitar \"SIM\" no console e finalizar o script. ");
		createContents();
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
		setText("Instalador de Certificado Https - 0.0.3");

		Label lblDesenvolvidoPorPaulo = new Label(getShell(), SWT.NONE);
		lblDesenvolvidoPorPaulo.setBounds(69, 268, 356, 15);
		lblDesenvolvidoPorPaulo
				.setText("Desenvolvido por Paulo Henrique Ferreira de Lima");

		Label lblSelecioneODiretrio = new Label(getShell(), SWT.NONE);
		lblSelecioneODiretrio.setBounds(10, 10, 334, 15);
		lblSelecioneODiretrio
				.setText("Selecione o diretório da instalação do JDK deafult do ambiente ");

		text = new Text(getShell(), SWT.BORDER);
		text.setBounds(10, 31, 334, 21);

		if (System.getenv("JAVA_HOME") != null) {
			System.out.println(System.getenv("JAVA_HOME"));
			text.setText(System.getenv("JAVA_HOME"));
		}

		if (System.getProperty("os.arch").equals("x86")
				&& System.getProperty("os.name").equals("Windows 7")) {
			text.setText("C:\\Tools\\jdk");
		} else {
			text.setText("C:\\Tools\\jdk");
		}

		Button btnSelecione = new Button(getShell(), SWT.NONE);
		btnSelecione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setFilterPath(text.getText());
				dlg.setText("Instalador de Certificados");
				dlg.setMessage("Selecione o diretório de instalação do JDK");

				String dir = dlg.open();
				if (dir != null) {
					KEYTOOL_FILE = new File(dir + File.separator + "bin"
							+ File.separator + "keytool.exe");
					if (KEYTOOL_FILE.exists()) {
						text.setText(dir);
					} else {
						MessageBox box = new MessageBox(getShell());
						box.setText("Erro");
						box.setMessage(dir + " não é um diretório válido.");
						box.open();
					}
				}

			}
		});
		btnSelecione.setBounds(350, 29, 75, 25);
		btnSelecione.setText("Selecione...");

		Label label = new Label(getShell(), SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 58, 414, 10);

		final Combo combo = new Combo(getShell(), SWT.BORDER);
		combo.setBounds(10, 88, 159, 21);

		File certRoot = new File(SERVER_PATH);
		List<String> list = new ArrayList<String>();
		list.add("TODOS");
		for (int i = 0; i < certRoot.listFiles().length; i++) {
			File file = certRoot.listFiles()[i];
			if (file.getName().endsWith(".cer"))
				list.add(file.getName());
		}

		String[] strarray = new String[list.size()];
		list.toArray(strarray);
		combo.setItems(strarray);

		combo.setText("TODOS");

		Label lblSelecioneOCertificado = new Label(getShell(), SWT.NONE);
		lblSelecioneOCertificado.setBounds(10, 67, 207, 15);
		lblSelecioneOCertificado
				.setText("Selecione o Certificado para Improtar");

		Button btnAplicar = new Button(getShell(), SWT.NONE);
		btnAplicar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				KEYTOOL_FILE = new File(text.getText() + File.separator + "bin"
						+ File.separator + "keytool.exe");

				if (KEYTOOL_FILE.exists()) {

					System.out.println("Selecionado: "
							+ combo.getText().toString());
					if (combo.getText().equals("TODOS")) {
						ArrayList<String> list = new ArrayList<String>();
						File certRoot = new File(SERVER_PATH);
						for (int i = 0; i < certRoot.listFiles().length; i++) {
							File file = certRoot.listFiles()[i];
							list.add(file.getName());
						}
						for (String string : list) {
							File path = new File(SERVER_PATH + File.separator
									+ string + ".cer");
							try {
								File newFile = new File(text.getText()
										+ File.separator + "bin"
										+ File.separator + string + ".cer");
								if (!newFile.exists())
									newFile.createNewFile();
								FolderUtils.copy(path, newFile, true);
								File certified = new File(text.getText()
										+ File.separator + "bin"
										+ File.separator
										+ "certifiedInstall.bat");
								FolderUtils.copy(SCRIPT_FILE, certified, true);

								String cmd = "rundll32 SHELL32.DLL,ShellExec_RunDLL  "
										+ certified.getAbsolutePath()
										+ " \""
										+ text.getText()
										+ "\" \""
										+ string
										+ ".cer\" \""
										+ string
										+ "\" \""
										+ text.getText()
										+ "\\jre\\lib\\security\\cacerts\"";
								System.out.println(cmd);
								Runtime.getRuntime().exec(cmd);

							} catch (IOException e1) {
								MessageBox box = new MessageBox(getShell());
								box.setText("ERRO");
								box.setMessage(e1.getMessage());
								box.open();
								System.exit(0);
							}
						}
					} else {
						File path = new File(SERVER_PATH + File.separator
								+ combo.getText() + ".cer");
						try {
							File newFile = new File(text.getText()
									+ File.separator + "bin" + File.separator
									+ combo.getText() + ".cer");
							if (!newFile.exists())
								newFile.createNewFile();
							FolderUtils.copy(path, newFile, true);
							File certified = new File(text.getText()
									+ File.separator + "bin" + File.separator
									+ "certifiedInstall.bat");
							FolderUtils.copy(SCRIPT_FILE, certified, true);

							String cmd = "rundll32 SHELL32.DLL,ShellExec_RunDLL  "
									+ certified.getAbsolutePath()
									+ " \""
									+ text.getText()
									+ "\" \""
									+ combo.getText()
									+ ".cer\" \""
									+ combo.getText()
									+ "\" \""
									+ text.getText()
									+ "\\jre\\lib\\security\\cacerts\"";
							System.out.println(cmd);
							Runtime.getRuntime().exec(cmd);

						} catch (IOException e1) {
							MessageBox box = new MessageBox(getShell());
							box.setText("ERRO");
							box.setMessage(e1.getMessage());
							box.open();
							System.exit(0);
						}
					}

				} else {
					MessageBox box = new MessageBox(getShell());
					box.setText("Erro");
					box.setMessage(text.getText()
							+ " não é um diretório válido.");
					box.open();
				}
			}
		});
		btnAplicar.setBounds(350, 102, 75, 25);
		btnAplicar.setText("Aplicar");

	}
}
