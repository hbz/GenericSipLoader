/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeOaiMetadata;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeOaiMetadata;

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
