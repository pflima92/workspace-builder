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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import br.com.techfullit.tools.wb.main.ApplicationLauncher;
import br.com.techfullit.tools.wb.utils.Constants;

/**
 * The Class SplashWindow.
 */
public class SplashWindow {

	/** The splash pos. */
	private int splashPos = 0;

	/** The splash max. */
	private final int SPLASH_MAX = 100;

	/**
	 * Instantiates a new splash window.
	 *
	 * @param display
	 *            the display
	 * @param applicationShell
	 *            the application shell
	 */
	public SplashWindow(Display display, final ApplicationShell applicationShell) {
		final Image image = new Image(display, System.getProperty("user.dir")
				+ Constants.IMAGES_PATH + File.separator + "Splash.jpg");

		final Shell splash = new Shell(SWT.ON_TOP);
		final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
		bar.setMaximum(SPLASH_MAX);

		Label label = new Label(splash, SWT.NONE);
		label.setImage(image);

		FormLayout layout = new FormLayout();
		splash.setLayout(layout);

		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(100, 0);
		label.setLayoutData(labelData);

		FormData progressData = new FormData();
		progressData.left = new FormAttachment(0, -5);
		progressData.right = new FormAttachment(100, 0);
		progressData.bottom = new FormAttachment(100, 0);
		bar.setLayoutData(progressData);
		splash.pack();

		Rectangle splashRect = splash.getBounds();
		Rectangle displayRect = display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		splash.setLocation(x, y);
		splash.open();

		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				ApplicationLauncher.loadSystem(applicationShell, image, splash, bar, splashPos, SPLASH_MAX);
			}
		});

		while (splashPos != SPLASH_MAX) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
}
