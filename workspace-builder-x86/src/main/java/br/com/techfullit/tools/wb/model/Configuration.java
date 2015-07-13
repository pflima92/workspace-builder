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
package br.com.techfullit.tools.wb.model;

/**
 * The Class Configuration.
 */
public class Configuration {

	/** The application name. */
	private String applicationName;
	
	/** The username. */
	private String username;
	
	/** The version. */
	private String version;
	
	/** The auto update. */
	private boolean autoUpdate;
	
	/** The register. */
	private String register;
	
	/** The default path. */
	private String defaultPath;
	
	/** The default client eep. */
	private String defaultClientEep;
	
	/** The default path local config. */
	private String defaultPathLocalConfig;
	
	/** The client destine7. */
	private String clientDestine7;
	
	/** The client destine xp. */
	private String clientDestineXp;

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Gets the client destine7.
	 *
	 * @return the client destine7
	 */
	public String getClientDestine7() {
		return clientDestine7;
	}

	/**
	 * Gets the client destine xp.
	 *
	 * @return the client destine xp
	 */
	public String getClientDestineXp() {
		return clientDestineXp;
	}

	/**
	 * Gets the default client eep.
	 *
	 * @return the default client eep
	 */
	public String getDefaultClientEep() {
		return defaultClientEep;
	}

	/**
	 * Gets the default path.
	 *
	 * @return the default path
	 */
	public String getDefaultPath() {
		return defaultPath;
	}

	/**
	 * Gets the default path local config.
	 *
	 * @return the default path local config
	 */
	public String getDefaultPathLocalConfig() {
		return defaultPathLocalConfig;
	}

	/**
	 * Gets the register.
	 *
	 * @return the register
	 */
	public String getRegister() {
		return register;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Checks if is auto update.
	 *
	 * @return true, if is auto update
	 */
	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	/**
	 * Sets the application name.
	 *
	 * @param applicationName
	 *            the new application name
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Sets the auto update.
	 *
	 * @param autoUpdate
	 *            the new auto update
	 */
	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	/**
	 * Sets the client destine7.
	 *
	 * @param clientDestine7
	 *            the new client destine7
	 */
	public void setClientDestine7(String clientDestine7) {
		this.clientDestine7 = clientDestine7;
	}

	/**
	 * Sets the client destine xp.
	 *
	 * @param clientDestineXp
	 *            the new client destine xp
	 */
	public void setClientDestineXp(String clientDestineXp) {
		this.clientDestineXp = clientDestineXp;
	}

	/**
	 * Sets the default client eep.
	 *
	 * @param defaultClientEep
	 *            the new default client eep
	 */
	public void setDefaultClientEep(String defaultClientEep) {
		this.defaultClientEep = defaultClientEep;
	}

	/**
	 * Sets the default path.
	 *
	 * @param defaultPath
	 *            the new default path
	 */
	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	/**
	 * Sets the default path local config.
	 *
	 * @param defaultPathLocalConfig
	 *            the new default path local config
	 */
	public void setDefaultPathLocalConfig(String defaultPathLocalConfig) {
		this.defaultPathLocalConfig = defaultPathLocalConfig;
	}

	/**
	 * Sets the register.
	 *
	 * @param register
	 *            the new register
	 */
	public void setRegister(String register) {
		this.register = register;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
