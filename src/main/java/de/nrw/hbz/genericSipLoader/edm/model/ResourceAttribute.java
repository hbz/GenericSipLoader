/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeResourceAttribute;
/**
 * 
 */
@JsonDeserialize(as = DeserializeResourceAttribute.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ResourceAttribute {

  /**
   * @return the rdfResource
   */
  public String getRdfResource();
  
  /**
   * @param rdfResource the rdfResource to set
   */
  public void setRdfResource(String rdfResource);
}
