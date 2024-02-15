/**
 * 
 */
package de.nrw.hbz.edm.model.serialize;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.edm.model.Aggregation;
import de.nrw.hbz.edm.model.ResourceAttribute;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SerializeAggregation implements Aggregation {

  private SerializeResourceAttribute aggregatedCHO = null;
  
  private String provider = "Digitales Archiv Nordrhein-Westfalen";
  
  private String dataProvider = new String();

  private SerializeResourceAttribute isShownBy = null;

  private SerializeResourceAttribute isShownAt = null;

  @JacksonXmlProperty(localName = "edm:object")
  private SerializeResourceAttribute object = null;
  
  @JacksonXmlProperty(localName = "edm:rights")
  private SerializeResourceAttribute rights = null;

  @JacksonXmlProperty(localName = "rdf:about", isAttribute = true)
  private String cHOAbout = generateUuid();

  private String generateUuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }


  
  
  
  /**
   * @return the aggregatedCHO
   */
  @Override
  @JacksonXmlProperty(localName = "edm:aggregatedCHO")
  public ResourceAttribute getEdmAggregatedCHO() {
    return aggregatedCHO;
  }

  /**
   * @return the provider
   */
  @Override
  @JacksonXmlProperty(localName = "edm:provider")
  public String getEdmProvider() {
    return provider;
  }

  /**
   * @return the dataProvider
   */
  @Override
  @JacksonXmlProperty(localName = "edm:dataProvider")
  public String getEdmDataProvider() {
    return dataProvider;
  }

  /**
   * @return the isShownBy
   */
  @Override
  @JacksonXmlProperty(localName = "edm:isShownBy")
  public SerializeResourceAttribute getEdmIsShownBy() {
    return isShownBy;
  }

  /**
   * @return the isShownAt
   */
  @Override
  @JacksonXmlProperty(localName = "edm:isShownAt")
  public SerializeResourceAttribute getEdmIsShownAt() {
    return isShownAt;
  }

  /**
   * @return the object
   */
  @Override
  @JacksonXmlProperty(localName = "edm:object")
  public ResourceAttribute getEdmObject() {
    return object;
  }

  /**
   * @return the rights
   */
  @Override
  @JacksonXmlProperty(localName = "edm:rights")
  public ResourceAttribute getEdmRights() {
    return rights;
  }

  /**
   * @param aggregatedCHO the aggregatedCHO to set
   */
   @Override
   @JacksonXmlProperty(localName = "edm:aggregatedCHO")
   public void setEdmAggregatedCHO(ResourceAttribute aggregatedCHO) {
    this.aggregatedCHO = (SerializeResourceAttribute) aggregatedCHO;
  }

  /**
   * @param provider the provider to set
   */
   @Override
   @JacksonXmlProperty(localName = "edm:provider")
  public void setEdmProvider(String provider) {
    this.provider = provider;
  }

  /**
   * @param dataProvider the dataProvider to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:dataProvider")
  public void setEdmDataProvider(String dataProvider) {
    this.dataProvider = dataProvider;
  }

  /**
   * @param isShownBy the isShownBy to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:isShownBy")
  public void setEdmIsShownBy(ResourceAttribute isShownBy) {
    this.isShownBy = (SerializeResourceAttribute) isShownBy;
  }

  /**
   * @param isShownAt the isShownAt to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:isShownAt")
  public void setEdmIsShownAt(ResourceAttribute isShownAt) {
    this.isShownAt = (SerializeResourceAttribute) isShownAt;
  }

  /**
   * @param object the object to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:object")
  public void setEdmObject(ResourceAttribute object) {
    this.object = (SerializeResourceAttribute) object;
  }

  /**
   * @param rights the rights to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:rights")
  public void setEdmRights(ResourceAttribute rights) {
    this.rights = (SerializeResourceAttribute) rights;
  }
  
  /**
   * @param resource the resource to set
   */
  @Override
  @JacksonXmlProperty(localName = "rdf:about", isAttribute = true)
  public void setEdmAboutCHOResource(String CHOAbout) {
    this.cHOAbout = CHOAbout;
  }


}
