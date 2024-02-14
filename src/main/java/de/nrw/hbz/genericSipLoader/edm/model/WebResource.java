/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeResourceAttribute;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeRdf;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeWebResource;

/**
 * 
 */
@JsonDeserialize(as = DeserializeWebResource.class)
// @JsonSerialize(as = SerializeWebResource.class)
public interface WebResource {
  

  // add to List
  /**
   * @param add item to dctermsExtent
   */
  public void addDctermsExtend(String extent);
  /**
   * @param add item to dctermsIsReferencedBy
   */
  public void addDctermsIsReferencedBy(ResourceAttribute isReferencedBy);
  /**
   * @param add item to dcDescription
   */
  public void addDcDescription(String description);
  /**
   * @param dcCreator the dcCreator to set
   */
  public void addDcCreator(String creator);
  /**
   * @param dcType the dcType to set
   */
  public void addDcType(String dcType);

  // Getter
  /**
   * @return the dcDescription
   */
  public ArrayList<String> getDcDescription();
  /**
   * @return the dcCreator
   */
  public ArrayList<String> getDcCreator();
  /**
   * @return the dcType
   */
  public ArrayList<String> getDcType();
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
   * @return the edmType
   */
  public ResourceAttribute getEdmIsNextInSequence();
  
  /**
   * @return
   */
  public String getWebResourceAbout();

  
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
   * @param dcDescription the dcDescription to set
   */
  public void setDcDescription(ArrayList<String> description);
  /**
   * @param dcCreator the dcCreator to set
   */
  public void setDcCreator(ArrayList<String> creator);
  /**
   * @param dcType the dcType to set
   */
   public void setDcType(ArrayList<String> type);
   /**
    * @param dcType the edmIsNextInSequebce to set
    */
    public void setEdmIsNextInSequence(ResourceAttribute ResIdentifier);
    /**
     * @param WebResourceAbout
     */
    public void setWebResourceAbout(String WebResourceAbout);
}