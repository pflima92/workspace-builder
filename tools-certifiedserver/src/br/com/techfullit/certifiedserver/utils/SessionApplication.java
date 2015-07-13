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
package br.com.techfullit.certifiedserver.utils;

import java.util.HashMap;

/**
 * TechFull IT - Services
 * 
 * Classe que implementa Session para ser utilizado junto ao projeto desktop.
 * */
public class SessionApplication {

	/** The instance. */
	private static SessionApplication instance;

	/**
	 * Gets the single instance of SessionApplication.
	 * 
	 * @return single instance of SessionApplication
	 */
	public static SessionApplication getInstance() {
		if (instance == null) {
			instance = new SessionApplication();
			return instance;
		} else {
			return instance;
		}
	}

	/** The session. */
	private HashMap<Object, Object> session;

	/**
	 * Instantiates a new session application.
	 */
	public SessionApplication() {
		session = new HashMap<Object, Object>();
	}

	/**
	 * Adds the.
	 * 
	 * @param name
	 *            the name
	 * @param property
	 *            the property
	 */
	public void add(Object name, Object property) {
		session.put(name, property);
	}

	/**
	 * Gets the.
	 * 
	 * @param name
	 *            the name
	 * @return the object
	 */
	public Object get(Object name) {
		return session.get(name);
	}

	/**
	 * Removes the.
	 * 
	 * @param name
	 *            the name
	 */
	public void remove(Object name) {
		session.remove(name);
	}

}
