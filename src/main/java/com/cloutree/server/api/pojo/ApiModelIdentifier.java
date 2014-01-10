
/**
 * Copyright 2014, Marc Schachtel, Germany
 */

package com.cloutree.server.api.pojo;

/**
 * ApiModelIdentifier
 * 
 * @author mschachtel
 *
 */
public class ApiModelIdentifier {

	private String modelName = "";
	
	private String tenant = "";
	
	private String instanceStr = "";
	
	private String secret = "";
	
	public ApiModelIdentifier(String modelName, String tenant, String instanceStr, String secret) {
		this.modelName = modelName;
		this.tenant = tenant;
		this.instanceStr = instanceStr;
		this.secret = secret;
	}

	/**
	 * @return the tenant
	 */
	public String getTenant() {
		return tenant;
	}

	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(String tenant) {
		this.tenant = tenant;
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

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the instanceStr
	 */
	public String getInstanceStr() {
		return instanceStr;
	}

	/**
	 * @param instanceStr the instanceStr to set
	 */
	public void setInstanceStr(String instanceStr) {
		this.instanceStr = instanceStr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		ApiModelIdentifier comp;
		try {
			comp = (ApiModelIdentifier)obj;
		} catch(ClassCastException e) {
			return false;
		}
		return equalsOrBothNull(this.instanceStr, comp.instanceStr) && equalsOrBothNull(this.tenant, comp.tenant) && equalsOrBothNull(this.secret, comp.secret) && equalsOrBothNull(this.modelName, comp.modelName);
	}
	
	private static boolean equalsOrBothNull(String one, String two) {
		if(one == null && two == null) {
			return true;
		} else {
			return one.equals(two);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return strHashCode(this.instanceStr) + strHashCode(this.tenant) + strHashCode(this.secret) + strHashCode(this.modelName);
	}

	private static int strHashCode(String str) {
		if(str == null)
			return "".hashCode();
		else
			return str.hashCode();
	}
	
}

