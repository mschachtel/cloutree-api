
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.pojo;

import java.util.Date;
import java.util.List;

/**
 * InstanceActiveModels
 *
 * @author mschachtel
 *
 */

public class InstanceActiveModels implements ApiBody {

	private String instanceName;
	
	private List<ActiveModel> activeModels;
	
	private Date timestamp;
	
	private String secret;

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @return the activeModels
	 */
	public List<ActiveModel> getActiveModels() {
		return activeModels;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		InstanceActiveModels compare;
		try {
			compare = (InstanceActiveModels)obj;
		} catch(ClassCastException e) {
			return false;
		}
		
		boolean instance = (this.instanceName == null && compare.getInstanceName() == null) || this.instanceName.equals(compare.getInstanceName());
		boolean models = (this.activeModels == null && compare.getActiveModels() == null) || this.activeModels.equals(compare.getActiveModels());
		boolean timestamp = (this.timestamp == null && compare.getTimestamp() == null) || this.timestamp.equals(compare.getTimestamp());
		
		return instance && models && timestamp;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @param activeModels the activeModels to set
	 */
	public void setActiveModels(List<ActiveModel> activeModels) {
		this.activeModels = activeModels;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}

