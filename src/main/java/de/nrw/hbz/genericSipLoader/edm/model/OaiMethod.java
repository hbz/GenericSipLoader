/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeOaiMethod;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeOaiMethod;
/**
 * 
 */
@JsonDeserialize(as = DeserializeOaiMethod.class)
// @JsonSerialize(as = SerializeOaiMethod.class)
public interface OaiMethod {
  
  /**
   * @return the record element
   */
  public ArrayList<? extends OaiRecord> getRecord();

  /**
   * @param record the record to set
   */
  public void setRecord(ArrayList<? extends OaiRecord> record);
  
}
