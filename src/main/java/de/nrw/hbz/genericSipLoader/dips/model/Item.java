/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.genericSipLoader.dips.model.deserialize.DeserializeItem;

/**
 * 
 */
@JsonDeserialize(as = DeserializeItem.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Item {
  
  /**
   * @return
   */
  public String getItemTitle();
  
  
  /**
   * @return
   */
  public String getItemID();
  
  /**
   * @param itemTitle
   */
  public void setItemTitle(String itemTitle);
  
  /**
   * @param itemID
   */
  public void setItemID(String itemID);
}
