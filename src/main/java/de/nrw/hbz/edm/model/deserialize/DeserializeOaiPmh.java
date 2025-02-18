/**
 * 
 */
package de.nrw.hbz.edm.model.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import de.nrw.hbz.edm.model.OaiPmh;
import de.nrw.hbz.edm.model.OaiMethod;

/**
 * 
 */
@JacksonXmlRootElement(localName="OAI-PMH")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeserializeOaiPmh implements OaiPmh {
  

  private final String oaiXmlns = "http://www.openarchives.org/OAI/2.0/"; 
  private final String xsiXmlns = "http://www.w3.org/2001/XMLSchema-instance"; 
  private final String xsiSchemaLocation = "http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd"; 
  private String ResponseDate = null; 

  @JacksonXmlProperty(localName="ListRecords")
  private DeserializeOaiMethod oaiMethod = new DeserializeOaiMethod();

  /**
   * @return the oaiXmlns
   */
  @Override
  @JacksonXmlProperty(localName="xmlns", isAttribute = true)
  public String getOaiXmlns() {
    return oaiXmlns;
  }

  /**
   * @return the xsiXmlns
   */
  @Override
  @JacksonXmlProperty(localName="xmlns:xsi", isAttribute = true)
  public String getXsiXmlns() {
    return xsiXmlns;
  }

  /**
   * @return the getRecord
   */
  @Override
  @JacksonXmlProperty(localName="ListRecords")
  public OaiMethod getOaiMethod() {
    return oaiMethod;
  }

  /**
   * @return the xsiSchemaLocation
   */
  @Override
  @JacksonXmlProperty(localName="schemaLocation", isAttribute = true)
  public String getXsiSchemaLocation() {
    return xsiSchemaLocation;
  }

  /**
   * @return the responseDate
   */
  @Override
  @JacksonXmlProperty(localName="responseDate")
  public String getResponseDate() {
    return ResponseDate;
  }

  @Override
  @JacksonXmlProperty(localName="responseDate")
  public void setResponseDate(String rDate) {
    this.ResponseDate = rDate;
  }



  /**
   * @param oMethod the oMethod to set
   */
  @Override
  @JacksonXmlProperty(localName="ListRecord")
  public void setOaiMethod(OaiMethod oaiMethod) {
    this.oaiMethod = (DeserializeOaiMethod) oaiMethod;    
  }

}
