/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model.serialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.ResourceAttribute;

/**
 * 
 */
public class SerializeResourceAttribute implements ResourceAttribute {
  
  private String rdfResource = null;

  public SerializeResourceAttribute() {
    
  }
  public SerializeResourceAttribute(String rdfResource) {
    this.rdfResource = rdfResource;
  }

  /**
   * @return the rdfResource
   */
  @JacksonXmlProperty(localName = "resource", isAttribute = true)
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
