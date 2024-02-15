/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.AboutAttribute;

/**
 * 
 */
public class DeserializeAboutAttribute implements AboutAttribute {

  private String rdfAbout = null;

  public DeserializeAboutAttribute() {
    
  }
  public DeserializeAboutAttribute(String rdfAbout) {
    this.rdfAbout = rdfAbout;
  }

  /**
   * @return the rdfAbout
   */
  @JacksonXmlProperty(localName = "rdf:about", isAttribute = true)
  public String getRdfAbout() {
    return rdfAbout;
  }

  /**
   * @param rdfAbout the rdfAbout to set
   */
  @JacksonXmlProperty(localName = "about", isAttribute = true)
  public void setRdfAbout(String rdfAbout) {
    this.rdfAbout = rdfAbout;
  }
  
  
  
  
}
