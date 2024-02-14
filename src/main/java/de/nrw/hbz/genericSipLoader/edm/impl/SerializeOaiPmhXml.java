/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeOaiPmh;
import de.nrw.hbz.genericSipLoader.edm.model.OaiPmh;

/**
 * 
 */
public class SerializeOaiPmhXml {

  private OaiPmh edm = new SerializeOaiPmh();
  
  
  public void serialize() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml;
    try {
      xmlMapper.writerFor(SerializeOaiPmh.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(edm);
      System.out.println(xml);
      
      //System.out.println("XML Deserializing....");
      //SerializeOaiPmh edm = xmlMapper.readValue(xml, SerializeOaiPmh.class);
      //System.out.println(xmlMapper.writeValueAsString(edm));
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }


  /**
   * @return the edm
   */
  public OaiPmh getEdm() {
    return edm;
  }


  /**
   * @param edm the edm to set
   */
  public void setEdm(OaiPmh edm) {
    this.edm = edm;
  }
  

}
