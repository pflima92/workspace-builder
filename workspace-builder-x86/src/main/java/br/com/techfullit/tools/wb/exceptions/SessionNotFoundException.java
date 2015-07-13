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
package br.com.techfullit.tools.wb.exceptions;

/**
 * The Class SessionNotFoundException.
 */
public class SessionNotFoundException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -286903735877705529L;
	
	/** The key. */
	private String key;
	
	/**
	 * Instantiates a new session not found exception.
	 *
	 * @param key
	 *            the key
	 */
	public SessionNotFoundException(String key) {
		super();
		this.key = key;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "ERRO GRAVE - " + key + "NÃO ENCONTRADO NO CONTEXTO";
	}
	

}
