
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.pojo;

/**
 * ActiveModel
 *
 * @author mschachtel
 *
 */

public class ActiveModel {

	private Integer modelId;
	
	private String modelName;
	
	private String revisionId;
	
	private Integer revision;
	
	private Integer instance;
	
	private String type;
	
	private String preProcessor;
	
	private String postProcessor;
	
	private String filePath;

	public ActiveModel() {
		
	}
	
	/**
	 * @return the modelId
	 */
	public Integer getModelId() {
		return modelId;
	}

	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
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
	 * @return the revisionId
	 */
	public String getRevisionId() {
		return revisionId;
	}

	/**
	 * @param revisionId the revisionId to set
	 */
	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}

	/**
	 * @return the revision
	 */
	public Integer getRevision() {
		return revision;
	}

	/**
	 * @param revision the revision to set
	 */
	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	/**
	 * @return the instance
	 */
	public Integer getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Integer instance) {
		this.instance = instance;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the preProcessor
	 */
	public String getPreProcessor() {
		return preProcessor;
	}

	/**
	 * @param preProcessor the preProcessor to set
	 */
	public void setPreProcessor(String preProcessor) {
		this.preProcessor = preProcessor;
	}

	/**
	 * @return the postProcessor
	 */
	public String getPostProcessor() {
		return postProcessor;
	}

	/**
	 * @param postProcessor the postProcessor to set
	 */
	public void setPostProcessor(String postProcessor) {
		this.postProcessor = postProcessor;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
}

