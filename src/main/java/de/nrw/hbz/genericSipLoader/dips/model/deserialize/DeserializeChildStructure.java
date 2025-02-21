/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model.deserialize;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.dips.model.ChildStructure;
import de.nrw.hbz.genericSipLoader.dips.model.Item;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeserializeChildStructure implements ChildStructure {

  private String title = new String();
  private String structureIdentifier =  new String();
  private ArrayList<Item> item = new ArrayList<>();

  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="title")
  public String getTitle() {
    return title;
  }

  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="structureIdentifier")
  public String getStructureIdentifier() {
    return structureIdentifier;
  }

  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="item")
  public ArrayList<Item> getItem() {
    return item;
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="title")
  public void setTitle(String title) {
    this.title =  title;
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="structureIdentifier")
  public void setStructureIdentifier(String structureIdentifier) {
    this.structureIdentifier = structureIdentifier;
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="item")
  public void setItem(ArrayList<Item> item) {
    this.item = item;
  }

}
