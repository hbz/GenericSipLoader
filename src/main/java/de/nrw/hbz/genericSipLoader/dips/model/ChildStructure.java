/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.dips.model.deserialize.DeserializeChildStructure;

/**
 * 
 */
@JsonDeserialize(as = DeserializeChildStructure.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ChildStructure {
  
  /**
   * @return
   */
  @JacksonXmlProperty(localName="title")
  public String getTitle();
  
  /**
   * @return
   */
  @JacksonXmlProperty(localName="structureIdentifier")
  public String getStructureIdentifier();
  
  /**
   * @return
   */
  @JacksonXmlProperty(localName="item")
  public ArrayList<Item> getItem();
  


}
