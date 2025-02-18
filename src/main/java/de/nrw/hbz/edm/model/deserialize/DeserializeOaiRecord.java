/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.OaiHeader;
import de.nrw.hbz.edm.model.OaiRecord;
import de.nrw.hbz.edm.model.OaiMetadata;

/**
 * 
 */
public class DeserializeOaiRecord implements OaiRecord {

  @JacksonXmlProperty(localName="header")
  private OaiHeader header = new OaiHeader();
  private OaiMetadata metadata = new DeserializeOaiMetadata();

  /**
   * @return the metadata
   */
  @JacksonXmlProperty(localName="metadata")
  public OaiMetadata getMetadata() {
    return metadata;
  }
  
  

  /**
   * @param metadata the metadata to set
   */
  @JacksonXmlProperty(localName="metadata")
  public void setMetadata(OaiMetadata metadata) {
    this.metadata = metadata;
  }
  
  
}
