
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cloutree.api.config.CloutreeApiConfiguration;
import com.cloutree.api.utils.HttpSender;
import com.cloutree.server.api.pojo.ActiveModel;
import com.cloutree.server.api.pojo.ApiJsonObject;
import com.cloutree.server.api.pojo.InstanceActiveModels;

import flexjson.JSONDeserializer;


/**
 * Servlet implementation class ApiPushServlet
 */

public class ApiPushServlet extends HttpServlet {
	
	static Logger log = Logger.getLogger(ApiPushServlet.class.getName());
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		
		String user =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_USER);
		String pass =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_PASS);
		String tenant =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_TENANT);
		String instance =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_INSTANCE);
		String apiName =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_NAME);
		
		String uri =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_REGISTER_URL);
		
		parameters.put("CLOUTREE:USER", user);
		parameters.put("CLOUTREE:PASS", pass);
		parameters.put("CLOUTREE:TENANT", tenant);
		parameters.put("CLOUTREE:INSTANCE", instance);
		parameters.put("CLOUTREE:APINAME", apiName);
		
		HttpResponse serverResponse = HttpSender.sendHttpPost(uri, parameters);
		
		if(serverResponse != null) serverResponse.getEntity().writeTo(response.getOutputStream());
		
		// TODO store models

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.log(Level.INFO, "Received Push from " + req.getRemoteHost());
		String body;
		StringBuilder stringBuilder = new StringBuilder();
		
		BufferedReader reader = req.getReader();
		String line = reader.readLine();
		while(line != null) {
			stringBuilder.append(line);
			line = reader.readLine();
		}
		
		// Remove "json="
		body = java.net.URLDecoder.decode(stringBuilder.toString(), "UTF-8");
		if(body.startsWith("json=")){
			body = body.replaceFirst("json=", "");
		}
		
		log.log(Level.INFO, "Push Body: " + body);
		
		ApiJsonObject apiJsonObject = null;
		InstanceActiveModels activeModels = null;
		
		try {
			apiJsonObject = new JSONDeserializer<ApiJsonObject>().deserialize(body);
			activeModels = (InstanceActiveModels)apiJsonObject.getRequestBody();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to cast ApiJsonObject out of request-body " + "[" + e.getMessage() + "]: " + body);
			resp.sendError(504, "Unable to cast ApiJsonObject out of request-body " + "[" + e.getMessage() + "]: " + body);
			return;
		}
		
		if(apiJsonObject == null || activeModels == null) {
			log.log(Level.SEVERE, "Unable to cast ApiJsonObject out of request-body: " + body);
			resp.sendError(504, "Unable to cast ApiJsonObject out of request-bod: " + body);
			return;
		}
		
		ActiveModelCache cache = ActiveModelCache.getCache(this.getServletContext());
		for(ActiveModel activeModel : activeModels.getActiveModels()) {
			cache.put(activeModel.getModelName(), activeModel);
			log.log(Level.INFO, "Added " + activeModel.getModelName() + " to cache");
		}
		
		resp.setStatus(200);
	}

}
