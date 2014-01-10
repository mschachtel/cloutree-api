
/**
 * Copyright 2014, Marc Schachtel, Germany
 */

package com.cloutree.api.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import com.cloutree.api.config.CloutreeApiConfiguration;
import com.cloutree.api.utils.HttpSender;

/**
 * StartupListener
 * 
 * @author mschachtel
 *
 */
public class StartupListener implements javax.servlet.ServletContextListener {
	
	static Logger log = Logger.getLogger(StartupListener.class.getName());
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		String user =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_SERVER_USER);
		String pass =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_SERVER_PASS);
		String apiName =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_NAME);
		String tenant =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_TENANT);
		
		String uri =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_REGISTER_URL);
		
		parameters.put("CLOUTREE:USER", user);
		parameters.put("CLOUTREE:PASS", pass);
		parameters.put("CLOUTREE:APINAME", apiName);
		parameters.put("CLOUTREE:TENANT", tenant);
		
		HttpSender.sendHttpPost(uri, parameters);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Map<String, String> parameters = new HashMap<String, String>();
		
		String user =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_SERVER_USER);
		String pass =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_SERVER_PASS);
		String apiName =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_NAME);
		String tenant =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_TENANT);
		
		String uri =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_UNREGISTER_URL);
		
		parameters.put("CLOUTREE:USER", user);
		parameters.put("CLOUTREE:PASS", pass);
		parameters.put("CLOUTREE:APINAME", apiName);
		parameters.put("CLOUTREE:TENANT", tenant);
		
		HttpSender.sendHttpGet(uri, parameters);

	}
}

