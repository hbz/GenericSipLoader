/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.ResourceAttribute;

/**
 * 
 */
public class DeserializeResourceAttribute implements ResourceAttribute {

  private String rdfResource = null;

  public DeserializeResourceAttribute() {
    
  }
  public DeserializeResourceAttribute(String rdfResource) {
    this.rdfResource = rdfResource;
  }
  

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
