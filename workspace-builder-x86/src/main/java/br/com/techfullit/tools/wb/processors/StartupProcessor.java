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

import br.com.techfullit.tools.wb.logger.Logger;
import br.com.techfullit.tools.wb.model.Configuration;
import br.com.techfullit.tools.wb.utils.PropertiesFactory;
import br.com.techfullit.tools.wb.utils.Reflection;
import br.com.techfullit.tools.wb.utils.SessionApplication;

/**
 * The Class StartupProcessor.
 */
public class StartupProcessor extends Processor {
	
	/* (non-Javadoc)
	 * @see br.com.techfullit.tools.wb.processors.Processor#start()
	 */
	@Override
	public boolean start() {
		
		
		Logger.debug("Init StartupProcessorProcessor");
		
		Configuration configuration = new	 Configuration();
		configuration.setApplicationName(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "APPLICATION_NAME"));
		configuration.setVersion(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "VERSION"));
		configuration.setRegister(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "REGISTER"));
		configuration.setAutoUpdate(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "AUTO_UPDATE").equals("true")?true:false);
		configuration.setDefaultPath(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "DEFAULT_PATH_REPOSITORY"));
		configuration.setDefaultPathLocalConfig(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "DEFAULT_PATH_LOCAL_CONFIG"));
		configuration.setDefaultClientEep(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "DEFAULT_CLIENT_REP"));
		configuration.setClientDestine7(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "CLIENT_DESTINE_7"));
		configuration.setClientDestineXp(PropertiesFactory.getInstance().getValue(PropertiesFactory.CONFIG, "CLIENT_DESTINE_XP"));
		configuration.setUsername(System.getProperty("user.name"));
		Logger.debug(Reflection.toString(configuration));
		SessionApplication.getInstance().put(SessionApplication.CONFIGURATION, configuration);
		
		
		return true;
	}

}
