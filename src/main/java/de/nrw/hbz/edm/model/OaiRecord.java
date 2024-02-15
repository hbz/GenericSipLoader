/**
 * 
 */
package de.nrw.hbz.edm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.edm.model.deserialize.DeserializeOaiRecord;

/**
 * 
 */
@JsonDeserialize(as = DeserializeOaiRecord.class)
// @JsonSerialize(as = SerializeOaiRecord.class)
public interface OaiRecord {

   /**
   * @return the metadata
   */
  public OaiMetadata getMetadata();
  
 
  /**
   * @param metadata the metadata to set
   */
  public void setMetadata(OaiMetadata metadata);
  
}
