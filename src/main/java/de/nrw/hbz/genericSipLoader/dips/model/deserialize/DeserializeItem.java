/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model.deserialize;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.dips.model.Item;

/**
 * 
 */
public class DeserializeItem implements Item {

  private String itemTitle = new String();
  private String itemID = new String();

  /**
   *
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="itemTitle")
 public String getItemTitle() {
    return itemTitle;
  }

  /**
   *
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="itemID")
  public String getItemID() {
    return itemID;
  }

  // Setter
  
  /**
  *
  */
 @JacksonXmlElementWrapper(useWrapping = false)
 @JacksonXmlProperty(localName="itemTitle")
public void setItemTitle(String itemTitle) {
   this.itemTitle = itemTitle;
 }

 /**
  *
  */
 @JacksonXmlElementWrapper(useWrapping = false)
 @JacksonXmlProperty(localName="itemID")
 public void setItemID(String itemID) {
   this.itemID = itemID;
 }


}
