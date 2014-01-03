
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.api.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * CloutreeApiConfiguration
 * 
 * @author mschachtel
 *
 */
public class CloutreeApiConfiguration {

	private static Properties props;
	private static boolean initiated = false;
	
	/** PROPERTIES KEYS **/
	public static String API_VERSION = "api.version";
	public static String API_USER = "api.user";
	public static String API_PASS = "api.pass";
	public static String API_TEMPDIR = "api.tempdir";
	public static String SERVER_REGISTER_URL = "server.register.url";
	public static String SERVER_FILEDOWNLOAD_URL = "server.filedownload.url";
	public static String SERVER_TENANT = "server.tenant";
	public static String SERVER_INSTANCE = "server.instance";
	public static String API_NAME = "api.name";
	
	public static void init() throws FileNotFoundException, IOException {

		String fileName = "cloutree-api.properties" ; 
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		props = new Properties();
		props.load(stream);
		
		initiated = true;
		
	}
	
	private static void hopeInit() {
		try {
			init();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}
	
	public static String getProperty(String key) {
		if(!initiated) hopeInit();
		return props.getProperty(key);
	}
	
}

