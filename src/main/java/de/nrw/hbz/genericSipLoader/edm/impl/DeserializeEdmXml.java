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

import de.nrw.hbz.genericSipLoader.edm.model.Rdf;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeRdf;

/**
 * 
 */
public class DeserializeEdmXml {

  private Rdf rdf = new DeserializeRdf();
  private File importFile = null;
  private InputStream xmlIs = null;
    
  public DeserializeEdmXml(File file) {
    this.importFile = file;
    xmlIs = loadXml();
  }

  public DeserializeEdmXml(String fileName) {
    importFile = new File(fileName);
    xmlIs = loadXml();
  }
  
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
  
  public Rdf deserialize() {
    DeserializeRdf rdf = null;
    XmlMapper xmlMapper = new XmlMapper();
    try {
      rdf = xmlMapper.readValue(xmlIs, DeserializeRdf.class);

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
