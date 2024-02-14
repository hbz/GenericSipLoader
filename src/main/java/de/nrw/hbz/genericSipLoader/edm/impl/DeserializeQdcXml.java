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

import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeProvidedCHO;

/**
 * 
 */
public class DeserializeQdcXml {
    
  public DeserializeQdcXml(File file) {
    this.importFile = file;
    xmlIs = loadXml();
  }

  public DeserializeQdcXml(String fileName) {
    importFile = new File(fileName);
    xmlIs = loadXml();
  }

  private ProvidedCHO providedCho = new DeserializeProvidedCHO();
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
  
  public ProvidedCHO deserialize() {
    DeserializeProvidedCHO rdf = null;
    XmlMapper xmlMapper = new XmlMapper();
    try {
      rdf = xmlMapper.readValue(xmlIs, DeserializeProvidedCHO.class);

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

    
    return rdf;
    }


  
  /**
   * @return the oaiPmh
   */
  public ProvidedCHO getProvidedCHO() {
    return providedCho;
  }


  /**
   * @param oaiPmh the oaiPmh to set
   */
  public void setProvidedCHO(ProvidedCHO providedCho) {
    this.providedCho = providedCho;
  }
  
  
}
