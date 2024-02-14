/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeRdf;
import de.nrw.hbz.genericSipLoader.impl.DipsLoaderImpl;
import de.nrw.hbz.genericSipLoader.edm.model.Rdf;

/**
 * 
 */
public class SerializeEdmXml {

  private Rdf rdf = new SerializeRdf();
  final static Logger logger = LogManager.getLogger(SerializeEdmXml.class);
  
  
  public String serialize() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeRdf.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rdf);
      logger.debug(xml);
      
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      logger.error("Failed to serialize EDM Object: " + rdf.toString());
    }
  return xml;
  }
  


  /**
   * @return the rdf
   */
  public Rdf getEdm() {
    return rdf;
  }


  /**
   * @param rdf the rdf to set
   */
  public void setEdm(Rdf rdf) {
    this.rdf = rdf;
  }
  
  /**
   * return complete EDM metadata as serialization to String
   */
  @Override
  public String toString() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeRdf.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rdf);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return xml;
  }


}
