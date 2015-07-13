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
package br.com.techfullit.tools.wb.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import br.com.techfullit.tools.wb.processors.ApplicationLoaderProcessor;
import br.com.techfullit.tools.wb.processors.ClientLoaderProcessor;
import br.com.techfullit.tools.wb.processors.Processor;
import br.com.techfullit.tools.wb.processors.ScreenLoaderProcessor;
import br.com.techfullit.tools.wb.processors.StartupProcessor;
import br.com.techfullit.tools.wb.utils.ExceptionCatcher;
import br.com.techfullit.tools.wb.view.components.ApplicationShell;
import br.com.techfullit.tools.wb.view.components.SplashWindow;

/**
 * The Class ApplicationLauncher.
 */
public class ApplicationLauncher {
	// Creating static so that can access from splash window code
	// In production code, use event handling
	/** The application shell. */
	public static ApplicationShell applicationShell;

	/**
	 * Load system.
	 *
	 * @param applicationShell
	 *            the application shell
	 * @param image
	 *            the image
	 * @param splash
	 *            the splash
	 * @param bar
	 *            the bar
	 * @param splashPos
	 *            the splash pos
	 * @param splashMax
	 *            the splash max
	 */
	public static void loadSystem(final ApplicationShell applicationShell,
			final Image image, final Shell splash, final ProgressBar bar,
			int splashPos, int splashMax) {

		// Prepara os Processor de Inicialização do Sistema
		List<Processor> processors = new ArrayList<Processor>();
		processors.add(new StartupProcessor());
		processors.add(new ApplicationLoaderProcessor());
		processors.add(new ClientLoaderProcessor());
		processors.add(new ScreenLoaderProcessor(applicationShell));

		int splashCount = splashMax / processors.size();

		for (Processor processor : processors) {
			try {
				processor.start();
			} catch (Exception e) {
				splash.close();
				image.dispose();
				ExceptionCatcher.catcher(e);
			}
			splashPos += splashPos + splashCount;
			bar.setSelection(splashPos);
		}

		ApplicationLauncher.applicationShell.initWindow();
		splash.close();
		image.dispose();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new ApplicationLauncher();
	}

	/**
	 * Instantiates a new application launcher.
	 */
	@SuppressWarnings("unused")
	public ApplicationLauncher() {
		Display display = new Display();
		applicationShell = new ApplicationShell(display);
		SplashWindow splashWindow = new SplashWindow(display, applicationShell);
		while ((Display.getCurrent().getShells().length != 0)
				&& !Display.getCurrent().getShells()[0].isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
