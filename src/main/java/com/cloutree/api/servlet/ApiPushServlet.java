
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloutree.api.config.CloutreeApiConfiguration;
import com.cloutree.server.api.pojo.ActiveModel;
import com.cloutree.server.api.pojo.ApiJsonObject;
import com.cloutree.server.api.pojo.ApiModelIdentifier;
import com.cloutree.server.api.pojo.InstanceActiveModels;

import flexjson.JSONDeserializer;


/**
 * Servlet implementation class ApiPushServlet
 */

public class ApiPushServlet extends HttpServlet {
	
	static Logger log = Logger.getLogger(ApiPushServlet.class.getName());
	
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.log(Level.INFO, "Received Push from " + req.getRemoteHost());
		
		String serverIp = CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_IP);
		
		if(!req.getRemoteHost().equals(serverIp)) {
			log.log(Level.WARNING, "Received Push-Request from " + req.getRemoteHost() + " but my server should be " + serverIp);
			resp.sendError(401, "Not an authorized server!");
			return;
		}
		
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
			ApiModelIdentifier id = new ApiModelIdentifier(activeModel.getModelName(), apiJsonObject.getTenant(), String.valueOf(activeModel.getInstance()), activeModels.getSecret());
			cache.put(id, activeModel);
			log.log(Level.INFO, "Added " + activeModel.getModelName() + " to cache");
		}
		
		resp.setStatus(200);
	}

}
