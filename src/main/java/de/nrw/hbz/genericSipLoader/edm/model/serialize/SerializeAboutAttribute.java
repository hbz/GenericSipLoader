/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model.serialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.AboutAttribute;

/**
 * 
 */
public class SerializeAboutAttribute implements AboutAttribute  {

  public SerializeAboutAttribute() {
    
  }
  public SerializeAboutAttribute(String rdfAbout) {
    this.rdfAbout = rdfAbout;
  }
  
  private String rdfAbout = null;

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
