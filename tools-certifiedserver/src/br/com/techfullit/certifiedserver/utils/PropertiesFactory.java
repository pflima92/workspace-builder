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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A factory for creating Properties objects.
 */
public class PropertiesFactory {

	/**
	 * Gets the prop.
	 * 
	 * @param path
	 *            the path
	 * @param key
	 *            the key
	 * @return the prop
	 */
	public static Object getProp(String path, String key) {
		Properties props = new Properties();
		try {
			InputStream resource = PropertiesFactory.class.getClass()
					.getResourceAsStream("/" + path + ".properties");
			props.load(resource);
			resource.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return props.get(key);
	}

	/** The path. */
	private Object path;

	/**
	 * Instantiates a new properties factory.
	 */
	public PropertiesFactory() {
	}

	/**
	 * Instantiates a new properties factory.
	 * 
	 * @param path
	 *            the path
	 */
	public PropertiesFactory(Object path) {
		this.path = path;
	}

	/**
	 * Gets the prop.
	 * 
	 * @param key
	 *            the key
	 * @return the prop
	 */
	public Object getProp(String key) {
		Properties props = new Properties();
		try {
			InputStream resource = this.getClass().getResourceAsStream(
					"/" + path + ".properties");
			props.load(resource);
			resource.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return props.get(key);
	}

}
