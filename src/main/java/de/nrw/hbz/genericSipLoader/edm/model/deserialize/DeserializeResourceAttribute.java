/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model.deserialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.ResourceAttribute;

/**
 * 
 */
public class DeserializeResourceAttribute implements ResourceAttribute {

  public DeserializeResourceAttribute() {
    
  }
  public DeserializeResourceAttribute(String rdfResource) {
    this.rdfResource = rdfResource;
  }
  
  private String rdfResource = null;

  /**
   * @return the rdfResource
   */
  @JacksonXmlProperty(localName = "rdf:resource", isAttribute = true)
  public String getRdfResource() {
    return rdfResource;
  }

  /**
   * @param rdfResource the rdfResource to set
   */
  @JacksonXmlProperty(localName = "resource", isAttribute = true)
  public void setRdfResource(String rdfResource) {
    this.rdfResource = rdfResource;
  }
  
  
  
  
}
