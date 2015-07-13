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
import java.util.HashMap;
import java.util.regex.Pattern;

import br.com.techfullit.tools.wb.model.Application;
import br.com.techfullit.tools.wb.model.Kit;
import br.com.techfullit.tools.wb.utils.Constants;
import br.com.techfullit.tools.wb.utils.ContextHelper;
import br.com.techfullit.tools.wb.utils.PropertiesFactory;
import br.com.techfullit.tools.wb.utils.Reflection;
import br.com.techfullit.tools.wb.utils.SessionApplication;

/**
 * The Class ApplicationLoaderProcessor.
 */
public class ApplicationLoaderProcessor extends Processor {

	/**
	 * Instantiates a new application loader processor.
	 */
	public ApplicationLoaderProcessor() {
	}

	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {
		HashMap<String, Application> availableApplications = new HashMap<String, Application>();
		HashMap<String, Kit> availableKit = new HashMap<String, Kit>();
		
		String rootPath = ContextHelper.getConfiguration()
				.getDefaultPath() + Constants.CONFIG_PATH + Constants.APPS_PATH;
		
		File folder = new File(rootPath);

		for (String file : folder.list()) {
			if(file.endsWith(".properties")){
				String id = PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "id");
				if(id == null)continue;
				Application application = new Application();
				application.setId(id);
				application.setVersion(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "version"));				
				application.setTitle(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "title"));				
				application.setDescription(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "description"));				
				application.setPathInstallation(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "pathInstallation"));				
				application.setType(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "type"));				
				application.setClassInfo(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "classInfo"));				
				application.setArchitecture(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "architecture"));
				application.setSilentInstall(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "silentInstall") == "true" ? true : false);
				Reflection.toString(application);
				availableApplications.put(id, application);
			}
		}
		
		rootPath = ContextHelper.getConfiguration()
				.getDefaultPath() + Constants.CONFIG_PATH + Constants.KITS_PATH;
		folder = new File(rootPath);
		
		for (String file : folder.list()) {
			if(file.endsWith(".properties")){
				String id = PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "id");
				if(id == null)continue;
				Kit kit = new Kit();
				kit.setId(id);
				kit.setVersion(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "version"));				
				kit.setTitle(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "title"));				
				kit.setDescription(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "description"));
				kit.setAppsString(PropertiesFactory.getInstance().getValue(rootPath + File.separator + file, "apps"));
				for(String arg : kit.getAppsString().split(Pattern.quote("|"))){
					if(arg.equals(""))continue;
					String idApp = arg.split(";")[0];
					String versionApp =  arg.split(";")[1];
					
					Application app = availableApplications.get(idApp);
					if(app != null && app.getVersion().equals(versionApp)){
						System.out.println("Adicionado ao Kit [" + id + "] - APP [" + arg + "]");
						kit.getApplications().add(app);
					}else{
						System.out.println("Não encontrado no Kit [" + id + "] - APP [" + arg + "]");
					}
				}
				Reflection.toString(kit);
				availableKit.put(id, kit);
			}
		}


		SessionApplication.getInstance().put(SessionApplication.APPLICAITONS_AVAILABLE, availableApplications);
		SessionApplication.getInstance().put(SessionApplication.KIT_AVAILABLE, availableKit);
		return true;
	}
}
