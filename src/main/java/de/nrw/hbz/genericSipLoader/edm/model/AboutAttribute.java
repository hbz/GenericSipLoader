/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeAboutAttribute;
import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeAboutAttribute;

/**
 * 
 */
@JsonDeserialize(as = DeserializeAboutAttribute.class)
@JsonSerialize(as = SerializeAboutAttribute.class)
public interface AboutAttribute {

  /**
   * @return the rdfAbout
   */
  public String getRdfAbout();

  /**
   * @param rdfAbout the rdfAbout to set
   */
  public void setRdfAbout(String rdfAbout);
  
  
  
  
}
