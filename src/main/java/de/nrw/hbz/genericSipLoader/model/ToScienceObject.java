package de.nrw.hbz.genericSipLoader.model;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * 
 * @author Alessio Pellerito
 *
 */

@Produces(MediaType.APPLICATION_JSON)
public class ToScienceObject {
	//@JsonProperty("contentType")
	private String contentType;

	//@JsonProperty("accessScheme")
	private String accessScheme;

	//@JsonProperty("publishScheme")
	private String publishScheme;

	//@JsonProperty("parentPid")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String parentPid;

	public ToScienceObject() {

	}
	
	
	/**
	 * Method overwrites default toString() in order to provide the to.science Object
	 * as pretty printed json-String
	 * return pretty printed json
	 */
	public String toString() {
  
	  LinkedHashMap<String, String> initObjMap = new LinkedHashMap<>();
	  initObjMap.put("contentType", contentType);
    initObjMap.put("accessScheme", accessScheme);
    initObjMap.put("publishScheme", publishScheme);
    initObjMap.put("parentPid", parentPid);
    
    JSONObject initObj = new JSONObject(initObjMap);
    initObj.toString(2);
	  
	  return initObj.toString(2);
	}
	
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

	public String getParentPid() {
		return parentPid;
	}

	public void setParentPid(String parentPid) {
		this.parentPid = parentPid;
	}
	
}
