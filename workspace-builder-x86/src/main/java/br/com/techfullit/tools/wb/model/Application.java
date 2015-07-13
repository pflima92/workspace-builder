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
 * The Class ApplicationInfo.
 */
public class Application {

	/** The id. */
	private String id;

	/** The version. */
	private String version;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The type. */
	private String type;

	/** The class info. */
	private String classInfo;

	/** The architecture. */
	private String architecture;

	/** The path installation. */
	private String pathInstallation;

	/** The silent install. */
	private boolean silentInstall;

	/**
	 * Gets the architecture.
	 *
	 * @return the architecture
	 */
	public String getArchitecture() {
		return architecture;
	}

	/**
	 * Gets the class info.
	 *
	 * @return the class info
	 */
	public String getClassInfo() {
		return classInfo;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the path installation.
	 *
	 * @return the path installation
	 */
	public String getPathInstallation() {
		return pathInstallation;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
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
	 * Checks if is silent install.
	 *
	 * @return true, if is silent install
	 */
	public boolean isSilentInstall() {
		return silentInstall;
	}

	/**
	 * Sets the architecture.
	 *
	 * @param architecture
	 *            the new architecture
	 */
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}

	/**
	 * Sets the class info.
	 *
	 * @param classInfo
	 *            the new class info
	 */
	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the path installation.
	 *
	 * @param pathInstallation
	 *            the new path installation
	 */
	public void setPathInstallation(String pathInstallation) {
		this.pathInstallation = pathInstallation;
	}

	/**
	 * Sets the silent install.
	 *
	 * @param silentInstall
	 *            the new silent install
	 */
	public void setSilentInstall(boolean silentInstall) {
		this.silentInstall = silentInstall;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
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