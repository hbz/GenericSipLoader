/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.OaiMetadata;
import de.nrw.hbz.edm.model.Rdf;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)  
public class DeserializeOaiMetadata implements OaiMetadata {

  private Rdf rdf = null;

  /**
   * @return the rdf
   */
  @Override
  @JacksonXmlProperty(localName="rdf:RDF")
  public Rdf getRdf() {
    return rdf;
  }

  /**
   * @param rdf the rdf to set
   */
  @Override
  @JacksonXmlProperty(localName="RDF")
  public void setRdf(Rdf rdf) {
    this.rdf = rdf;
  }
  

}
