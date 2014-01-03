

package com.cloutree.api.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.http.HttpResponse;

import com.cloutree.server.api.pojo.ActiveModel;
import com.cloutree.api.config.CloutreeApiConfiguration;
import com.cloutree.api.utils.HttpSender;
import com.cloutree.modelevaluator.ModelTypes;
import com.cloutree.modelevaluator.PredictiveModel;
import com.cloutree.modelevaluator.PredictiveModelFactory;

public class ActiveModelCache {
	
	static Logger log = Logger.getLogger(ActiveModelCache.class.getName());

	private Map<String, PredictiveModel> models;
	
	private static String CACHE_NAME = "ACTIVE_MODEL_CACHE";
	
	public static ActiveModelCache getCache(ServletContext sc) {
		
		ActiveModelCache cache = null;
		
		try {
			cache = (ActiveModelCache)sc.getAttribute(CACHE_NAME);
		} catch(ClassCastException e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		
		if(cache == null) {
			cache = new ActiveModelCache();
			cache.pushToContext(sc);
		}
		
		return cache;
		
	}
	
	public synchronized void pushToContext(ServletContext sc) {
		sc.setAttribute(CACHE_NAME, this);
	}
	
	public ActiveModelCache() {
		this.models = new HashMap<String, PredictiveModel>();
	}
	
	public synchronized void put(String key, ActiveModel model) {
		File modelFile;
		PredictiveModel pModel;
		try {
			modelFile = requestModelFile(model.getModelName());
			pModel = PredictiveModelFactory.getPredictiveModel(ModelTypes.valueOf(model.getType()), modelFile);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Can't instantiate predictive model: " + model.getModelName() + "[" + model.getFilePath() + "]");
			return;
		}
		
		pModel.setPostProcessor(model.getPostProcessor());
		pModel.setPreProcessor(model.getPreProcessor());
		this.models.put(model.getModelName(), pModel);
	}
	
	public PredictiveModel getPredictiveModel(String key) {
		return this.models.get(key);
	}
	
	public Collection<PredictiveModel> getAllPredictiveModels(){
		return this.models.values();
	}
	
	protected static File requestModelFile(String key) throws Exception {
		File file = null;
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		String user =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_USER);
		String pass =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_PASS);
		String tenant =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_TENANT);
		String instance =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_INSTANCE);
		String model =  key;
		
		String uri =  CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.SERVER_FILEDOWNLOAD_URL);
		
		parameters.put("CLOUTREE:USER", user);
		parameters.put("CLOUTREE:PASS", pass);
		parameters.put("CLOUTREE:TENANT", tenant);
		parameters.put("CLOUTREE:INSTANCE", instance);
		parameters.put("CLOUTREE:MODEL", model);
		
		HttpResponse serverResponse = HttpSender.sendHttpPost(uri, parameters);
		file = new File(CloutreeApiConfiguration.getProperty(CloutreeApiConfiguration.API_TEMPDIR) + "/" + (new Date()).getTime() + "_" + key);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		
		if(serverResponse != null) serverResponse.getEntity().writeTo(fop);
		
		return file;
	}
	
}

