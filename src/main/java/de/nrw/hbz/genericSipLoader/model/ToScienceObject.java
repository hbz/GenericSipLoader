package de.nrw.hbz.genericSipLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author Alessio Pellerito
 *
 */

public class ToScienceObject {
	
	private String contentType;
	private String accessScheme;
	private String publishScheme;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String parentId;

	public String getAccessScheme() {
		return accessScheme;
	}

	public void setAccessScheme(String accessScheme) {
		this.accessScheme = accessScheme;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPublishScheme() {
		return publishScheme;
	}

	public void setPublishScheme(String publishScheme) {
		this.publishScheme = publishScheme;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
