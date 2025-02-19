/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeRdf;

/**
 * Interface that provides methods to read and write XML-based representations of
 * EDM meta data
 * RDF does not mean a rdf representation. Instead it's the XML root element provided in EDM
 *  
 */
@JsonDeserialize(as = DeserializeRdf.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Rdf {
  
  /**
   * @return the rdfXmlns
   */
  public String getRdfXmlns();

  /**
   * @return the edmXmlns
   */
  public String getEdmXmlns();

  /**
   * @return the oreXmlns
   */
  public String getOreXmlns();

  /**
   * @return the dcXmlns
   */
  public String getDcXmlns();

  /**
   * @return the dctermsXmlns
   */
  public String getDctermsXmlns();

  /**
   * @param aggregation the aggregation to add
   */
  public void addAggregation(Aggregation aggregation);

  /**
   * @param webResource the webResource to add
   */
  public void addWebResource(WebResource webResource);

  /**
   * @return the providedCHO
   */
  public ProvidedCHO getProvidedCho();

  /**
   * @return the aggregation
   */
   public ArrayList<? extends Aggregation> getAggregation();

  /**
   * @return the WebResource
   */
  public ArrayList<? extends WebResource> getWebResource();


  /**
   * @param providedCHO the providedCHO to set
   */
  public void setProvidedCho(ProvidedCHO providedCHO);

  /**
   * @param aggregation the aggregation to set
   */
  public void setAggregation(ArrayList<? extends Aggregation> aggregation);
  
  /**
   * @param aggregation the aggregation to set
   */
  public void setWebResource(ArrayList<? extends WebResource> webResource);
  

}
