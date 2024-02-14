/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.genericSipLoader.edm.model.OaiPmh;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeOaiPmh;

/**
 * 
 */
public class DeserializeOaiPmhXml {
    
  public DeserializeOaiPmhXml(File file) {
    this.importFile = file;
    xmlIs = loadXml();
  }

  public DeserializeOaiPmhXml(String fileName) {
    importFile = new File(fileName);
    xmlIs = loadXml();
  }

  private OaiPmh oaiPmh = new DeserializeOaiPmh();
  private File importFile = null;
  private InputStream xmlIs = null;
  
  private InputStream loadXml() {
    BufferedInputStream bis = null;
    try {
      FileInputStream fis = new FileInputStream(importFile);
      bis = new BufferedInputStream(fis);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return bis;
  }
  
  public OaiPmh deserialize() {
    DeserializeOaiPmh oaiPmh = null;
    XmlMapper xmlMapper = new XmlMapper();
    try {
      oaiPmh = xmlMapper.readValue(xmlIs, DeserializeOaiPmh.class);

    } catch (StreamReadException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (DatabindException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    
    return oaiPmh;
    }


  
  /**
   * @return the oaiPmh
   */
  public OaiPmh getEdm() {
    return oaiPmh;
  }


  /**
   * @param oaiPmh the oaiPmh to set
   */
  public void setEdm(OaiPmh edm) {
    this.oaiPmh = edm;
  }
  
  
}
