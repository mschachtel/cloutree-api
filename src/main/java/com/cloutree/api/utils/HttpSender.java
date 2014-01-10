
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.api.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;


public class HttpSender {

	static Logger log = Logger.getLogger(HttpSender.class.getName());
	
	public static HttpResponse sendHttpPostJson(String jsonObject, String uriString) {
		
		HttpPost post = new HttpPost();
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);        
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		UrlEncodedFormEntity entity;
		
		postParams.add(new BasicNameValuePair("json", jsonObject));
		try {
			entity = new UrlEncodedFormEntity(postParams);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, e.getMessage());
			return null;
		}
		
		URI uri = null;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		}
		
		entity.setContentEncoding(HTTP.UTF_8);
	    entity.setContentType("application/json");
		
		post.setURI(uri);
		post.setEntity(entity);
		
		try {
			return httpClient.execute(post);
		} catch (ClientProtocolException e) {
			log.log(Level.SEVERE, e.getMessage());
			return null;
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
	
	public static HttpResponse sendHttpGet(String uriStr, Map<String, String> parameters) {
		
		URI uri = null;
		try {
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(uriStr);
			
			if(parameters.size() > 0) {
				strBuilder.append("?");
			}
			
			for(String key : parameters.keySet()) {
				strBuilder.append(key);
				strBuilder.append("=");
				strBuilder.append(parameters.get(key));
				strBuilder.append("&");
			}
			
			uri = new URI(strBuilder.toString());
		} catch (URISyntaxException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		}
		
		HttpGet get = new HttpGet();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);        
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		
		get.setURI(uri);

		try {
			return httpClient.execute(get);
		} catch (ClientProtocolException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		} catch (IOException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		}
		
	}
	
	public static HttpResponse sendHttpPost(String uriStr, Map<String, String> parameters) {
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);        
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		for(String key : parameters.keySet()) {
			formparams.add(new BasicNameValuePair(key, parameters.get(key)));
		}

		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		}
		HttpPost httppost = new HttpPost(uriStr);
		httppost.setEntity(entity);

		try {
			return httpClient.execute(httppost);
		} catch (ClientProtocolException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		} catch (IOException e) {
			log.log(Level.SEVERE,e.getMessage());
			return null;
		}
	}
	
}