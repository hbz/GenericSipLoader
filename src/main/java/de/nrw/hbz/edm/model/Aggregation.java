/**
 * 
 */
package de.nrw.hbz.edm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.edm.model.deserialize.DeserializeAggregation;

/**
 * 
 */
@JsonDeserialize(as = DeserializeAggregation.class)
// @JsonSerialize(as = SerializeAggregation.class)
public interface Aggregation {

  /**
   * @return the aggregatedCHO
   */
  public ResourceAttribute getEdmAggregatedCHO();  
  /**
   * @return the provider
   */
  public String getEdmProvider();

  /**
   * @return the dataProvider
   */
  public String getEdmDataProvider();

  /**
   * @return the isShownBy
   */
  public ResourceAttribute getEdmIsShownBy();

  /**
   * @return the isShownAt
   */
  public ResourceAttribute getEdmIsShownAt();

  /**
   * @return the object
   */
  public ResourceAttribute getEdmObject();

  /**
   * @return the rights
   */
  public ResourceAttribute getEdmRights();

  /**
   * @param aggregatedCHO the aggregatedCHO to set
   */
   public void setEdmAggregatedCHO(ResourceAttribute oreAggregatedCHO);

  /**
   * @param provider the provider to set
   */
  public void setEdmProvider(String provider);

  /**
   * @param dataProvider the dataProvider to set
   */
  public void setEdmDataProvider(String dataProvider);

  /**
   * @param isShownBy the isShownBy to set
   */
  public void setEdmIsShownBy(ResourceAttribute isShownBy);
  /**
   * @param isShownAt the isShownAt to set
   */
  public void setEdmIsShownAt(ResourceAttribute isShownBy);
  /**
   * @param object the object to set
   */
  public void setEdmObject( ResourceAttribute edmObject);

  /**
   * @param rights the rights to set
   */
  public void setEdmRights( ResourceAttribute edmRights);
  /**
   * @param resource the resource to set
   */
  public void setEdmAboutCHOResource(String CHOAbout);
  
}
