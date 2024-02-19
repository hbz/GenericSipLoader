/**
 * 
 */
package de.nrw.hbz.edm.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.edm.model.Rdf;
import de.nrw.hbz.edm.model.deserialize.DeserializeRdf;
import de.nrw.hbz.edm.model.serialize.SerializeRdf;

/**
 * A class to generate appropriate Pojo-representations from serialized EDM (Europeana Data Model) metadata 
 * Makes use of the jackson-Framework 
 */
public class EdmProvider {

  final static Logger logger = LogManager.getLogger(EdmProvider.class);

  private Rdf rdf = new DeserializeRdf();
  private static InputStream xmlIs = null;
  private InputStream conIs = null;
    
  /**
   * Constructor takes serialized EDM as File Object 
   * @param file
   */
  public EdmProvider(File file) {
    conIs = loadXml(file);
  }

  /**
   *  Constructor takes serialized EDM as String
   * @param rdfString
   */
  public EdmProvider(String rdfString) {
    xmlIs = loadXmlString(rdfString);
  }
  
  /**
   *  Constructor takes serialized EDM as InputStream
   * @param rdfIs
   */
  public EdmProvider(InputStream rdfIs) {
    //importFile = new File(fileName);
    xmlIs = rdfIs;
  }

  /**
   * loads File content into InputStream
   * @return InputStream representing EDM metadata
   */
  private static InputStream loadXml(File file) {
    BufferedInputStream bis = null;
    try {
      FileInputStream fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage());
    }
    
    return bis;
  }
  
  /**
   * @param rdf EDM metadata as String representation
   * @return InputStream representing EDM metadata
   */
  private static InputStream loadXmlString(String rdfString) {
    
    BufferedInputStream bis = null;
    ByteArrayInputStream baif = new ByteArrayInputStream(rdfString.getBytes());
    bis = new BufferedInputStream(baif);
    
    return bis;
  }

  /**
   * method takes serialized EDM as InputStream 
   * @param is InputStream to be used
   * @return EDM as Pojos according to jackson-Framework
   */
  public static Rdf deserialize(InputStream is) {
    DeserializeRdf rdf = null;
    xmlIs = is;
    XmlMapper xmlMapper = new XmlMapper();
    try {
      rdf = xmlMapper.readValue(xmlIs, DeserializeRdf.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    
    return rdf;
    }

  /**
   * method takes serialized EDM as File object 
   * @param file File to be used
   * @return EDM as Pojos according to jackson-Framework
   */
  public static Rdf deserialize(File file) {
    DeserializeRdf rdf = null;
    xmlIs = loadXml(file);
    XmlMapper xmlMapper = new XmlMapper();
    try {
      rdf = xmlMapper.readValue(xmlIs, DeserializeRdf.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    
    return rdf;
    }

  /**
   * method takes serialized EDM as String 
   * @param edmString the String used to deserialize
   * @return EDM as Pojos according to jackson-Framework
   */
  public static Rdf deserialize(String edmString) {
    DeserializeRdf rdf = null;
    xmlIs = loadXmlString(edmString);
    XmlMapper xmlMapper = new XmlMapper();
    try {
      rdf = xmlMapper.readValue(xmlIs, DeserializeRdf.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    return rdf;
    }
  
  public static String serialize(Rdf edm) {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeRdf.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(edm);
      
    } catch (JsonProcessingException e) {
      logger.error("Failed to serialize EDM Object: " + edm.toString());
    }
  return xml;
  }
  
  /**
   * @return the oaiPmh
   */
  public Rdf getEdm() {
    return rdf;
  }


  /**
   * @param oaiPmh the oaiPmh to set
   */
  public void setEdm(Rdf rdf) {
    this.rdf = rdf;
  }
  
  
}
