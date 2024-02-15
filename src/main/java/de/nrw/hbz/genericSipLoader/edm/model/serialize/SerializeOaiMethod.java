/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.model.serialize;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.edm.model.OaiMethod;
import de.nrw.hbz.genericSipLoader.edm.model.OaiRecord;

/**
 * 
 */
public class SerializeOaiMethod implements OaiMethod{


  
  @JacksonXmlProperty(localName="record")
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<SerializeOaiRecord> oaiRecord = new ArrayList<SerializeOaiRecord>();

  public SerializeOaiMethod () {
    
  }
  
  public SerializeOaiMethod(String methodName) {
    if(oaiRecord == null) {
      oaiRecord = new ArrayList<SerializeOaiRecord>();
    }
    oaiRecord.add(new SerializeOaiRecord());
  
  }

  
  /**
   * @return the record element
   */
  @Override
  @JacksonXmlProperty(localName="record")
  public ArrayList<SerializeOaiRecord> getRecord() {
    return oaiRecord;
  }

  /**
   * @param record the record to set
   */
  @Override
  @JacksonXmlProperty(localName="record")
  public void setRecord(ArrayList<? extends OaiRecord> OaiRecord) {
    this.oaiRecord = (ArrayList<SerializeOaiRecord>) OaiRecord;
  }



}
