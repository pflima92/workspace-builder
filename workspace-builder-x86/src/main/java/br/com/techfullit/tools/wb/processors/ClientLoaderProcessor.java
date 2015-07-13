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
package br.com.techfullit.tools.wb.processors;

import java.io.File;
import java.util.Enumeration;

import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.model.CurrentVersion;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.PropertiesFactory;
import br.com.techfullit.tools.wb.utils.Reflection;
import br.com.techfullit.tools.wb.utils.SessionApplication;

/**
 * The Class ClientLoaderProcessor.
 */
public class ClientLoaderProcessor extends Processor {

	/**
	 * Instantiates a new client loader processor.
	 */
	public ClientLoaderProcessor() {
	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {

		String rootPath = System.getProperty("user.home") + File.separator + ContextHelper.getConfiguration().getDefaultPathLocalConfig();
		File file = new File(rootPath);
		if(file.exists()){
			
//			lastDate=190520152122
//			versionInstaller=1.0
//			versionClient=1.0
//			#hsc;1.0|
//			kits=
//			apps=eclipseCap;1.0|
//			teste=1.0|teste|C:\Teste\
//
//			CurrentVersion currentVersion = new CurrentVersion();
//			currentVersion.setAppsString(PropertiesFactory.getInstance().getValue(rootPath, "apps"));
//			currentVersion.setKitsString(PropertiesFactory.getInstance().getValue(rootPath, "kits"));
//			currentVersion.setLastDate(PropertiesFactory.getInstance().getValue(rootPath, "lastDate"));
//			currentVersion.setVersionInstaller(PropertiesFactory.getInstance().getValue(rootPath, "versionInstaller"));
//			currentVersion.setVersionClient(PropertiesFactory.getInstance().getValue(rootPath, "versionClient"));
//			for(String arg : currentVersion.getAppsString().split(Pattern.quote("|"))){
//				if(arg.equals(""))continue;
//				String idApp = arg.split(";")[0];
//				String versionApp =  arg.split(";")[1];
//				Application application = new Application();
//				application.setId(idApp);
//				application.setVersion(versionApp);
//				currentVersion.getApplications().add(application);
//			}
//			for(String arg : currentVersion.getKitsString().split(Pattern.quote("|"))){
//				if(arg.equals(""))continue;
//				String idKit = arg.split(";")[0];
//				String versionKit =  arg.split(";")[1];
//				Kit kit = new Kit();
//				kit.setId(idKit);
//				kit.setVersion(versionKit);
//				currentVersion.getKits().add(kit);
//			}
			CurrentVersion currentVersion = new CurrentVersion();
			Enumeration e = PropertiesFactory.getInstance().getPropertie(rootPath).propertyNames();
		    for (; e.hasMoreElements();) {
		    	try {
					String key =  (String) e.nextElement();
					String arg = PropertiesFactory.getInstance().getValue(rootPath, key);
					String idApp = arg.split(";")[0];
					String versionApp =  arg.split(";")[1];
					Application application = new Application();
					application.setId(idApp);
					application.setVersion(versionApp);
					currentVersion.getApplications().add(application);
		    	} catch (Exception e2) {
		    		continue;
				}
			};
			
			Reflection.toString(currentVersion);
			SessionApplication.getInstance().put(SessionApplication.CURRENT_INFO,currentVersion);
			
			
		}else{
			System.out.println("Nenhuma versão instalada ou encontrada.");
		}

		return true;
	}

}
