/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model.deserialize;

import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.OaiMethod;
import de.nrw.hbz.genericSipLoader.edm.model.OaiRecord;

/**
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeserializeOaiMethod implements OaiMethod{
  
  @JacksonXmlProperty(localName="record")
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<DeserializeOaiRecord> oaiRecord = new ArrayList<DeserializeOaiRecord>();

  public DeserializeOaiMethod () {
    if(oaiRecord == null) {
      oaiRecord = new ArrayList<DeserializeOaiRecord>();
    }
    oaiRecord.add(new DeserializeOaiRecord());
    
  }

  public DeserializeOaiMethod(String methodName) {
    if(oaiRecord == null) {
      oaiRecord = new ArrayList<DeserializeOaiRecord>();
    }
    oaiRecord.add(new DeserializeOaiRecord());
  
  }

 
  /**
   * @return the oaiRecord element
   */
  @Override
  @JacksonXmlProperty(localName="record")
  public ArrayList<DeserializeOaiRecord> getRecord() {
    return oaiRecord;
  }

  /**
   * @param oaiRecord the oaiRecord to set
   */
  @Override
  @JacksonXmlProperty(localName="record")
  public void setRecord(ArrayList<? extends OaiRecord> oaiRecord) {
    this.oaiRecord = (ArrayList<DeserializeOaiRecord>) oaiRecord;
    
  }



}
