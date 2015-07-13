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

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Kit.
 */
public class Kit {

	/** The id. */
	private String id;
	
	/** The version. */
	private String version;
	
	/** The title. */
	private String title;
	
	/** The description. */
	private String description;
	
	/** The apps string. */
	private String appsString;
	
	/** The applications. */
	private List<Application> applications;
	
	/**
	 * Gets the applications.
	 *
	 * @return the applications
	 */
	public List<Application> getApplications() {
		if(applications == null)
			applications = new ArrayList<Application>();
		return applications;
	}
	
	/**
	 * Gets the apps string.
	 *
	 * @return the apps string
	 */
	public String getAppsString() {
		return appsString;
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
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
	 * Sets the applications.
	 *
	 * @param applications
	 *            the new applications
	 */
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	/**
	 * Sets the apps string.
	 *
	 * @param appsString
	 *            the new apps string
	 */
	public void setAppsString(String appsString) {
		this.appsString = appsString;
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
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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
