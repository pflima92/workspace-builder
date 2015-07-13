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
package br.spei.session.test;

/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
//package org.eclipse.swt.snippets;
/*
 * StackLayout example snippet: use a StackLayout to switch between Composites.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class StackLayoutSwitchComposites.
 */
public class StackLayoutSwitchComposites {

	/** The page num. */
	static int pageNum = -1;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setBounds(10, 10, 300, 200);
		// create the composite that the pages will share
		final Composite contentPanel = new Composite(shell, SWT.BORDER);
		contentPanel.setBounds(100, 10, 190, 90);
		final StackLayout layout = new StackLayout();
		contentPanel.setLayout(layout);

		// create the first page's content
		final Composite page0 = new Composite(contentPanel, SWT.NONE);
		page0.setLayout(new RowLayout());
		Label label = new Label(page0, SWT.NONE);
		label.setText("Label on page 1");
		label.pack();

		// create the second page's content
		final Composite page1 = new Composite(contentPanel, SWT.NONE);
		page1.setLayout(new RowLayout());
		Button button = new Button(page1, SWT.NONE);
		button.setText("Button on page 2");
		button.pack();

		// create the button that will switch between the pages
		Button pageButton = new Button(shell, SWT.PUSH);
		pageButton.setText("Push");
		pageButton.setBounds(10, 10, 80, 25);
		pageButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				pageNum = ++pageNum % 2;
				layout.topControl = pageNum == 0 ? page0 : page1;
				contentPanel.layout();
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}