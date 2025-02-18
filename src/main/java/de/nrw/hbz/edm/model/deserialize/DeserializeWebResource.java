/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.ResourceAttribute;
import de.nrw.hbz.edm.model.WebResource;



/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeserializeWebResource implements WebResource{
  

  private ArrayList<String> dcDescription = new ArrayList<>();
  private ArrayList<String> dcCreator = new ArrayList<>();
  private ArrayList<String> dcType = new ArrayList<>();
  
  private String dctermsCreated = new String();
  private String dctermsIssued = new String();
  private ArrayList<String> dctermsExtent = new ArrayList<>();
  private ArrayList<DeserializeResourceAttribute> dctermsIsReferencedBy = new ArrayList<>();
  private DeserializeResourceAttribute edmIsNextInSequence = new DeserializeResourceAttribute();
  private String webRAbout = new String();

  
  
  // add to List
  /**
   * @param add item to dctermsExtent
   */
  @Override
  public void addDctermsExtend(String extent) {
    this.dctermsExtent.add(extent);
  }
  /**
   * @param add item to dctermsIsReferencedBy
   */
  @Override
  public void addDctermsIsReferencedBy(ResourceAttribute isReferencedBy) {
    this.dctermsIsReferencedBy.add((DeserializeResourceAttribute) isReferencedBy);
  }
  /**
   * @param add item to dcDescription
   */
  @Override
  public void addDcDescription(String description) {
    this.dcDescription.add(description);
  }
  /**
   * @param dcCreator the dcCreator to set
   */
  @Override
  public void addDcCreator(String creator) {
    this.dcCreator.add(creator);
  }
  /**
   * @param dcType the dcType to set
   */
  @Override
  public void addDcType(String dcType) {
    this.dcType.add(dcType);
  }

  // Getter
  /**
   * @return the dcDescription
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="dc:description")
  public ArrayList<String> getDcDescription() {
    return dcDescription;
  }
  /**
   * @return the dcCreator
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="dc:creator")
  public ArrayList<String> getDcCreator() {
    return dcCreator;
  }
  /**
   * @return the dcType
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="dc:type")
  public ArrayList<String> getDcType() {
    return dcType;
  }
  /**
   * @return the dctermsCreated
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:created")
  public String getDctermsCreated() {
    return dctermsCreated;
  }
  /**
   * @return the dctermsIssued
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:issued")
  public String getDctermsIssued() {
    return dctermsIssued;
  }
  /**
   * @return the dctermsExtent
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="dcterms:extent")
  public ArrayList<String> getDctermsExtent() {
    return dctermsExtent;
  }
  /**
   * @return the dctermsIsReferencedBy
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="dcterms:isReferencedBy")
  public ArrayList<? extends ResourceAttribute> getDctermsIsReferencedBy() {
    return dctermsIsReferencedBy;
  }
  /**
   * @return
   */
  @JacksonXmlProperty(localName="rdf:about", isAttribute = true)
  public String getWebResourceAbout() {
    return this.webRAbout;
  };
  
  // Setter
  /**
   * @param dctermsCreated the dctermsCreated to set
   */
  @Override
  @JacksonXmlProperty(localName="created")
  public void setDctermsCreated(String dctermsCreated) {
    this.dctermsCreated = dctermsCreated;
  }
  /**
   * @param dctermsIssued the dctermsIssued to set
   */
  @Override
  @JacksonXmlProperty(localName="issued")
  public void setDctermsIssued(String dctermsIssued) {
    this.dctermsIssued = dctermsIssued;
  }
  /**
   * @param dctermsExtent the dctermsExtent to set
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="extent")
  public void setDctermsExtent(ArrayList<String> dctermsExtent) {
    this.dctermsExtent = dctermsExtent;
  }
  /**
   * @param dctermsIsReferencedBy the dctermsIsReferencedBy to set
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="isReferencedBy")
  public void setDctermsIsReferencedBy(ArrayList<?extends ResourceAttribute> dctermsIsReferencedBy) {
    this.dctermsIsReferencedBy = (ArrayList<DeserializeResourceAttribute>) dctermsIsReferencedBy;
  }
  /**
   * @param dcDescription the dcDescription to set
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="description")
  public void setDcDescription(ArrayList<String> description) {
    this.dcDescription = description;
  }
  /**
   * @param dcCreator the dcCreator to set
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="creator")
  public void setDcCreator(ArrayList<String> creator) {
    if(this.dcCreator == null) {
      this.dcCreator = new ArrayList<String>();
    }
    this.dcCreator = creator;
  }
  /**
   * @param dcType the dcType to set
   */
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="type")
 public void setDcType(ArrayList<String> type) {
    this.dcType = type;
  }
  @Override
  @JacksonXmlProperty(localName="edm:isNextInSequence")
  public ResourceAttribute getEdmIsNextInSequence() {
    return this.edmIsNextInSequence;
  }
  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName="isNextInSequence")
  public void setEdmIsNextInSequence(ResourceAttribute edmIsNextInSequence) {
    this.edmIsNextInSequence = (DeserializeResourceAttribute) edmIsNextInSequence;
    
  }

  /**
   *@param webResourceAbout
   */
  @Override
  @JacksonXmlProperty(localName="about", isAttribute = true)
  public void setWebResourceAbout(String WebResourceAbout) {
    this.webRAbout = WebResourceAbout;
  }

}
