/**
 * 
 */
package de.nrw.hbz.edm.model;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.edm.model.deserialize.DeserializeProvidedCHO;

/**
 * 
 */
@JsonDeserialize(as = DeserializeProvidedCHO.class)
// @JsonSerialize(as = SerializeProvidedCHO.class)
public interface ProvidedCHO {
  

  // add to List
  /**
   * @param add item to dctermsExtent
   */
  public void addDctermsExtent(String extent);
  /**
   * @param add item to dctermsIsReferencedBy
   */
  public void addDctermsIsReferencedBy(ResourceAttribute isReferencedBy);
  /**
   * @param add item to dctermsProvenance
   */
  public void addDctermsProvenance(String provenance);
  /**
   * @param add item to dcTitle
   */
  public void addDcTitle(String title);
  /**
   * @param add item to dcDescription
   */
  public void addDcDescription(String description);
  /**
   * @param dcCreator the dcCreator to set
   */
  public void addDcCreator(String creator);
  /**
   * @param dcContributor the dcContributor to set
   */
  public void addDcContributor(String contributor);
  /**
   * @param dcIdentifier the dcIdentifier to set
   */
  public void addDcIdentifier(String identifier);
  /**
   * @param dcPublisher the dcPublisher to set
   */
  public void addDcPublisher(String publisher);
  /**
   * @param dcLanguage the dcLanguage to set
   */
  public void addDcLanguage(String language);
  /**
   * @param dcType the dcType to set
   */
  public void addDcType(String dcType);
  /**
   * @param dcDate the dcDate to set
   */
  public void addDcDate(String date);

  // Getter
  /**
   * @return the dcTitle
   */
  public ArrayList<String> getDcTitle();
  /**
   * @return the dcDescription
   */
  public ArrayList<String> getDcDescription();
  /**
   * @return the dcCreator
   */
  public ArrayList<String> getDcCreator();
  /**
   * @return the dcContributor
   */
  public ArrayList<String> getDcContributor();
  /**
   * @return the dcIdentifier
   */
  public ArrayList<String> getDcIdentifier();
  /**
   * @return the dcPublisher
   */
  public ArrayList<String> getDcPublisher();
  /**
   * @return the dcLanguage
   */
  public ArrayList<String> getDcLanguage();
  /**
   * @return the dcType
   */
  public ArrayList<String> getDcType();
  /**
   * @return the dcDate
   */
  public ArrayList<String> getDcDate();
  /**
   * @return the dctermsCreated
   */
  public String getDctermsCreated();
  /**
   * @return the dctermsIssued
   */
  public String getDctermsIssued();
  /**
   * @return the dctermsExtent
   */
  public ArrayList<String> getDctermsExtent();
  /**
   * @return the dctermsIsReferencedBy
   */
  public ArrayList<? extends ResourceAttribute> getDctermsIsReferencedBy();
  /**
   * @return the dctermsProvenance
   */
  public ArrayList<String> getDctermsProvenance();
  /**
   * @return the edmType
   */
  public String getEdmType();
  /**
   * @return
   */
  public String getProvidedCHOAbout();
  
  // Setter
  /**
   * @param dctermsCreated the dctermsCreated to set
   */
  public void setDctermsCreated(String dctermsCreated);
  /**
   * @param dctermsIssued the dctermsIssued to set
   */
  public void setDctermsIssued(String dctermsIssued);
  /**
   * @param dctermsExtent the dctermsExtent to set
   */
  public void setDctermsExtent(ArrayList<String> dctermsExtent);
  /**
   * @param dctermsIsReferencedBy the dctermsIsReferencedBy to set
   */
  public void setDctermsIsReferencedBy(ArrayList<? extends ResourceAttribute> dctermsIsReferencedBy);
  /**
   * @param dctermsProvenance the dctermsProvenance to set
   */
  public void setDctermsProvenance(ArrayList<String> dctermsProvenance);
  /**
   * @param edmType the edmType to set
   */
  public void setEdmType(String edmType);
  /**
   * @param dcTitle the dcTitle to set
   */
  public void setDcTitle(ArrayList<String> title);
  /**
   * @param dcDescription the dcDescription to set
   */
  public void setDcDescription(ArrayList<String> description);
  /**
   * @param dcCreator the dcCreator to set
   */
  public void setDcCreator(ArrayList<String> creator);
  /**
   * @param dcContributor the dcContributor to set
   */
  public void setDcContributor(ArrayList<String> contributor);
  /**
   * @param dcIdentifier the dcIdentifier to set
   */
  public void setDcIdentifier(ArrayList<String> identifier);
  /**
   * @param dcPublisher the dcPublisher to set
   */
  public void setDcPublisher(ArrayList<String> publisher);
  /**
   * @param dcLanguage the dcLanguage to set
   */
  public void setDcLanguage(ArrayList<String> language);
  /**
   * @param dcType the dcType to set
   */
 public void setDcType(ArrayList<String> type);
  /**
   * @param dcDate the dcDate to set
   */
  public void setDcDate(ArrayList<String> date);
  /**
   * @param ProvidedCHOAbout
   */
  public void setProvidedCHOAbout(String ProvidedCHOAbout);
}