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
  private ArrayList<ChildStructure> childStructure = new ArrayList<ChildStructure>();


  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="item")
  public void addItem(Item item) {
    this.item.add(item);
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="item")
  public void addChildStructure(ChildStructure cStruct) {
    this.childStructure.add(cStruct);
  }

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
  
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="childStructure")
  public ArrayList<ChildStructure> getChildStructure() {
    return childStructure;
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

  /**
   * @param childStructure the childStructure to set
   */
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="childStructure")
  public void setChildStructure(ArrayList<ChildStructure> cStruct) {
    this.childStructure = cStruct;
  }

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="item")
  public void setItem(ArrayList<Item> item) {
    this.item = item;
  }


}
