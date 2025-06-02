/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.impl;

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

import de.nrw.hbz.genericSipLoader.dips.model.IEStructure;
import de.nrw.hbz.genericSipLoader.dips.model.deserialize.DeserializeIEStructure;
import de.nrw.hbz.genericSipLoader.dips.model.serialize.SerializeIEStructure;


/**
 * A class to generate appropriate Pojo-representations from serialized EDM (Europeana Data Model) metadata 
 * Makes use of the jackson-Framework 
 */
public class DipsIEStructureProvider {

  final static Logger logger = LogManager.getLogger(DipsIEStructureProvider.class);

  private IEStructure ieStruct = new DeserializeIEStructure();
  private static InputStream xmlIs = null;
  
  public DipsIEStructureProvider(IEStructure ieStruct) {
    this.ieStruct = ieStruct;
  }

  /**
   * loads File content into InputStream
   * @return InputStream representing DiPS IE-Structure metadata
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
   * @param ieStruct IE-Structure metadata as String representation
   * @return InputStream representing DiPS IE-Structure metadata
   */
  private static InputStream loadXmlString(String ieString) {
    
    BufferedInputStream bis = null;
    ByteArrayInputStream baif = new ByteArrayInputStream(ieString.getBytes());
    bis = new BufferedInputStream(baif);
    
    return bis;
  }

  /**
   * method takes serialized DiPS IE-Structure as InputStream 
   * @param is InputStream to be used
   * @return EDM as Pojos according to the jackson-Framework
   */
  public static IEStructure deserialize(InputStream is) {
    DeserializeIEStructure ieStruct = null;
    xmlIs = is;
    XmlMapper xmlMapper = new XmlMapper();
    try {
      ieStruct = xmlMapper.readValue(xmlIs, DeserializeIEStructure.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    
    return ieStruct;
    }

  /**
   * method takes serialized DiPS IE Structure as File object 
   * @param file File to be used
   * @return EDM as Pojos according to the jackson-Framework
   */
  public static IEStructure deserialize(File file) {
    DeserializeIEStructure ieStruct = null;
    xmlIs = loadXml(file);
    XmlMapper xmlMapper = new XmlMapper();
    try {
      ieStruct = xmlMapper.readValue(xmlIs, DeserializeIEStructure.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    return ieStruct;
    }

  /**
   * method takes serialized DiPS IE Structure as String 
   * @param ieStructString the String used to deserialize
   * @return EDM as Pojos according to jackson-Framework
   */
  public static IEStructure deserialize(String ieStructString) {
    DeserializeIEStructure ieStruct = null;
    xmlIs = loadXmlString(ieStructString);
    XmlMapper xmlMapper = new XmlMapper();
    try {
      ieStruct = xmlMapper.readValue(xmlIs, DeserializeIEStructure.class);

    } catch (StreamReadException e) {
      logger.error(e.getMessage());
    } catch (DatabindException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    return ieStruct;
    }
  
  /**
   * @param ieStruct
   * @return xml the IEStructure as serialized xml String
   */
  public static String serialize(IEStructure ieStruct) {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeIEStructure.class);
      xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ieStruct);
      
    } catch (JsonProcessingException e) {
      logger.error("Failed to serialize EDM Object: " + ieStruct.toString());
    }
  return xml;
  }
  
  /**
   * @return the DiPS IE-Structure
   */
  public IEStructure getDipsIEStructure() {
    return ieStruct;
  }


  /**
   * @param  DiPS IE-Structure to set
   */
  public void setDipsIEStructure(IEStructure ieStruct) {
    this.ieStruct = ieStruct;
  }
  
  /**
   * return complete EDM metadata as serialization to String
   */
  @Override
  public String toString() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeIEStructure.class);
      xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +  xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ieStruct) + "\n";
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return xml;
  }


}
