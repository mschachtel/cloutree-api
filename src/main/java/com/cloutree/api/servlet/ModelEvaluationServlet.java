
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

import org.apache.http.protocol.HTTP;

import com.cloutree.modelevaluator.PredictiveModel;
import com.cloutree.modelevaluator.PredictiveModelResult;
import com.cloutree.server.api.pojo.ApiModelIdentifier;


/**
 * Servlet implementation class ApiPushServlet
 */

public class ModelEvaluationServlet extends HttpServlet {
	
	static Logger log = Logger.getLogger(ModelEvaluationServlet.class.getName());
	
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.log(Level.INFO, "Received Request for evaluation from " + req.getRemoteHost());
		
		String tenant = req.getParameter("CLOUTREE:TENANT");
		String instanceStr = req.getParameter("CLOUTREE:INSTANCE");
		String modelName = req.getParameter("CLOUTREE:MODEL");
		String secret = req.getParameter("CLOUTREE:SECRET");
		String javaRequest = req.getParameter("CLOUTREE:JAVA");
		Boolean isJava = javaRequest != null;
		

		log.log(Level.INFO, "Model Eval Request Details: Tenant[" + tenant + "] - Instance[" + instanceStr + "] - MODEL-NAME[" + modelName + "] - Java[" + isJava + "]");
		
		//Search for Model and send result
		ActiveModelCache cache = ActiveModelCache.getCache(this.getServletContext());
		PredictiveModel predModel = cache.getPredictiveModel(new ApiModelIdentifier(modelName, tenant, instanceStr, secret));
		
		if(predModel == null) {
			resp.sendError(404, "Model not found");
			return;
		}
		
		// TODO parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters = this.initParameterMap(req);
		} catch (Exception e) {
			log.log(Level.WARNING, "Problem reading parameters: " + e.getMessage());
		}
		
		PredictiveModelResult result = predModel.eval(parameters);
		resp.getWriter().write(result.serialize(isJava));
		resp.setContentType("application/json");
		resp.setCharacterEncoding(HTTP.UTF_8);
	}

	private Map<String, Object> initParameterMap(HttpServletRequest req) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		BufferedReader reader = req.getReader();
		String line = reader.readLine();
		while (line != null) {
			String [] split = line.split("=", 2);
			if(split.length >=2)
				result.put(split[0], split[1]);
			
			line = reader.readLine();
		}
		
		return result;
	
    }
	
}
