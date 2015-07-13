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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * A factory for creating Properties objects.
 */
public class PropertiesFactory {

	/** The Constant CONFIG. */
	public static final String CONFIG = "config.properties";

	/** The instance. */
	private static PropertiesFactory instance;

	/** The properties. */
	private static HashMap<String, Properties> properties;

	/**
	 * Gets the single instance of PropertiesFactory.
	 *
	 * @return single instance of PropertiesFactory
	 */
	public static PropertiesFactory getInstance() {
		if (instance == null)
			instance = new PropertiesFactory();
		return instance;
	}

	/**
	 * Instantiates a new properties factory.
	 */
	public PropertiesFactory() {
		if (properties == null) {
			properties = new HashMap<>();
		}
		Properties props = new Properties();
		try {
			InputStream fis = getClass().getClassLoader().getResourceAsStream(CONFIG);
			props.load(fis);
			fis.close();
			properties.put(CONFIG, props);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the prop.
	 *
	 * @param path
	 *            the path
	 * @return the prop
	 */
	public Properties getPropertie(final String path) {
		Properties props = new Properties();
		try {
			InputStream fis = new FileInputStream(new File(path));
			props.load(fis);
			fis.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return props;
	}

	/**
	 * Gets the prop.
	 * 
	 * @param path
	 *            the path
	 * @param key
	 *            the key
	 * @return the prop
	 */
	public String getValue(final String path, final String key) {
		String value = "";
		Properties props = properties.get(path);
		if (props == null) {
			props = new Properties();
			try {
				InputStream fis = new FileInputStream(new File(path));
				props.load(fis);
				fis.close();
				properties.put(path, props);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		value = (String) props.get(key);
		return value;
	}

}
