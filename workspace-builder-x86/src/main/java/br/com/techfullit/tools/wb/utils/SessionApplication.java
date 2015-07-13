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
package br.com.techfullit.tools.wb.utils;

import java.util.HashMap;

import br.com.techfullit.tools.wb.exceptions.SessionNotFoundException;

/**
 * TechFull IT - Services
 * 
 * Classe que implementa Session para ser utilizado junto ao projeto desktop.
 * */
public class SessionApplication {
	
	/** The Constant CONFIGURATION. */
	public static final String CONFIGURATION = "CONFIGURATION";
	
	/** The Constant APPLICAITONS_AVAILABLE. */
	public static final String APPLICAITONS_AVAILABLE = "APPLICAITONS_AVAILABLE";
	
	/** The Constant KIT_AVAILABLE. */
	public static final String KIT_AVAILABLE = "KIT_AVAILABLE";
	
	/** The Constant APPLICATIONS_SELECTED. */
	public static final String APPLICATIONS_SELECTED = "APPLICAITONS_SELECTED";
	
	/** The Constant CURRENT_INFO. */
	public static final String CURRENT_INFO = "CURRENT_INFO";
	
	/** The Constant SCREENS. */
	public static final Object SCREENS = "SCREENS";
	
	/** The Constant INSTALL_APPS_SUCCESS. */
	public static final Object INSTALL_APPS_SUCCESS = "INSTALL_APPS_SUCCESS";
	
	/** The Constant INSTALL_APPS_FAILED. */
	public static final Object INSTALL_APPS_FAILED = "INSTALL_APPS_FAILED";
	

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
	 * Gets the.
	 *
	 * @param name
	 *            the name
	 * @return the object
	 * @throws SessionNotFoundException
	 *             the session not found exception
	 */
	public Object get(final Object name) throws SessionNotFoundException { 
		Object obj = session.get(name);
		if(obj == null)throw new SessionNotFoundException((String) name);
		return obj;
	}

	/**
	 * Adds the.
	 * 
	 * @param name
	 *            the name
	 * @param property
	 *            the property
	 */
	public void put(final Object name, final Object property) {
		session.remove(name);
		session.put(name, property);
	}

	/**
	 * Removes the.
	 * 
	 * @param name
	 *            the name
	 */
	public void remove(final Object name) {
		session.remove(name);
	}

}
