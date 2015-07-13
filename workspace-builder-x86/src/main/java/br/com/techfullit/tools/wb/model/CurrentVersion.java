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
 * The Class CurrentVersion.
 */
public class CurrentVersion {

	/** The apps string. */
	private String appsString;
	
	/** The kits string. */
	private String kitsString;
	
	/** The applications. */
	private List<Application> applications;
	
	/** The kits. */
	private List<Kit> kits;
	
	/** The last date. */
	private String lastDate;
	
	/** The version installer. */
	private String versionInstaller;
	
	/** The version client. */
	private String versionClient;

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
	 * Gets the kits.
	 *
	 * @return the kits
	 */
	public List<Kit> getKits() {
		if(kits == null)
			kits = new ArrayList<Kit>();
		return kits;
	}

	/**
	 * Gets the kits string.
	 *
	 * @return the kits string
	 */
	public String getKitsString() {
		return kitsString;
	}

	/**
	 * Gets the last date.
	 *
	 * @return the last date
	 */
	public String getLastDate() {
		return lastDate;
	}

	/**
	 * Gets the version client.
	 *
	 * @return the version client
	 */
	public String getVersionClient() {
		return versionClient;
	}

	/**
	 * Gets the version installer.
	 *
	 * @return the version installer
	 */
	public String getVersionInstaller() {
		return versionInstaller;
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
	 * Sets the kits.
	 *
	 * @param kits
	 *            the new kits
	 */
	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}

	/**
	 * Sets the kits string.
	 *
	 * @param kitsString
	 *            the new kits string
	 */
	public void setKitsString(String kitsString) {
		this.kitsString = kitsString;
	}

	/**
	 * Sets the last date.
	 *
	 * @param lastDate
	 *            the new last date
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * Sets the version client.
	 *
	 * @param versionClient
	 *            the new version client
	 */
	public void setVersionClient(String versionClient) {
		this.versionClient = versionClient;
	}

	/**
	 * Sets the version installer.
	 *
	 * @param versionInstaller
	 *            the new version installer
	 */
	public void setVersionInstaller(String versionInstaller) {
		this.versionInstaller = versionInstaller;
	}
	
}
