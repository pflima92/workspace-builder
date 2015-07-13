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
package br.com.techfullit.tools.wb.view.common;

import org.eclipse.swt.widgets.Composite;

/**
 * The Class DefaultComposite.
 */
public class DefaultComposite extends Composite {
	
	/**
	 * Create the composite.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public DefaultComposite(Composite parent, int style) {
		super(parent, style);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
	}
	
	/**
	 * Prepare.
	 */
	public void prepare(){
	}

}
