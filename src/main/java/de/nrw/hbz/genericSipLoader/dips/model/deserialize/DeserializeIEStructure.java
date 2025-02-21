/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model.deserialize;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import de.nrw.hbz.genericSipLoader.dips.model.ChildStructure;
import de.nrw.hbz.genericSipLoader.dips.model.IEStructure;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)  
@JacksonXmlRootElement(localName="dips:ieStructureRoot")
public class DeserializeIEStructure implements IEStructure {

  private final String dipsXmlns =  "http://dips.bundesarchiv.de/schema/ieStructure"; 
  private String title = new String();
  private String structureIdentifier =  new String();
  private ArrayList<ChildStructure> childStructure = new ArrayList<>();
  
  /**
   * @return the badipsXmlns
   */
  @JacksonXmlProperty(localName="xmlns:dips", isAttribute = true)
  public String getDipsXmlns() {
    return dipsXmlns;
  }
  
  @JacksonXmlProperty(localName="title")
  public String getTitle() {
    return title;
  }
  @Override
  @JacksonXmlProperty(localName="structureIdentifier")
  public String getStructureIdentifier() {
    return structureIdentifier;
  }
  @Override
  @JacksonXmlProperty(localName="childStructure")
  public ArrayList<ChildStructure> getChildStructure() {
    return childStructure;
  }

  /**
   * @param title the title to set
   */
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="title")
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param structureIdentifier the structureIdentifier to set
   */
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
  public void setChildStructure(ArrayList<ChildStructure> childStructure) {
    this.childStructure = childStructure;
  }
  
  

}
