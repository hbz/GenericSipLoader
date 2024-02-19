/**
 * 
 */
package de.nrw.hbz.edm.impl;

import java.io.File;

import de.nrw.hbz.edm.model.Rdf;

/**
 * 
 */
public class EdmImpl {
  
  private String filePath = null; 
  
  public EdmImpl() {
    
  }
  
  public EdmImpl(String filePath) {
    this.filePath = filePath;
  }
  

      /**
       * @return Rdf
       */
      public Rdf deserializeXml() {
        return EdmProvider.deserialize(new File(filePath));
        
      }

      /**
       * create an XML serialization from Rdf object
       * @param Rdf
       */
      public String serializeXml(Rdf rdf) {
        String xml = null;
        SerializeEdmXml sXml = new SerializeEdmXml();
        sXml.setEdm(rdf);
        xml = sXml.serialize(); 
        return xml;
      }

      /**
       * @return the filePath
       */
      public String getFilePath() {
        return filePath;
      }

      /**
       * @param filePath the filePath to set
       */
      public void setFilePath(String filePath) {
        this.filePath = filePath;
      }

}
