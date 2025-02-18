/**
 * 
 */
package de.nrw.hbz.edm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.nrw.hbz.edm.model.deserialize.DeserializeOaiMetadata;

/**
 * 
 */
@JsonDeserialize(as = DeserializeOaiMetadata.class)
// @JsonSerialize(as = SerializeOaiMetadata.class)
public interface OaiMetadata {

  /**
   * @return the rdf
   */
  public Rdf getRdf();

  /**
   * @param rdf the rdf to set
   */
  public void setRdf(Rdf rdf);
  

}
