/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeProvidedCHO;
import de.nrw.hbz.genericSipLoader.impl.DipsLoaderImpl;
import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;

/**
 * 
 */
public class SerializeQdcXml {

  private ProvidedCHO providedCho = new SerializeProvidedCHO();
  final static Logger logger = LogManager.getLogger(SerializeQdcXml.class);
  
  
  public String serialize() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeProvidedCHO.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(providedCho);
      // logger.debug(xml);
      
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      logger.error("Failed to serialize QDC Object: " + providedCho.toString());
    }
  return xml;
  }
  


  /**
   * @return the rdf
   */
  public ProvidedCHO getProvidedCHO() {
    return providedCho;
  }


  /**
   * @param rdf the rdf to set
   */
  public void setProvidedCHO(ProvidedCHO providedCho) {
    this.providedCho = providedCho;
  }
  
  /**
   * return complete EDM metadata as serialization to String
   */
  @Override
  public String toString() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeProvidedCHO.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(providedCho);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return xml;
  }


}
