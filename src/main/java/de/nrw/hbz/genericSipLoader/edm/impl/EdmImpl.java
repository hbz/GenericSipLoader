/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import de.nrw.hbz.genericSipLoader.edm.model.Rdf;

/**
 * 
 */
public class EdmImpl {
  
  
  public EdmImpl() {
    
  }
  
  public EdmImpl(String filePath) {
    this.filePath = filePath;
  }
  
  private String filePath = null; 

      /**
       * @return Rdf
       */
      public Rdf deserializeXml() {
        DeserializeEdmXml dsXml = new DeserializeEdmXml(filePath);
        return dsXml.deserialize();
        
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
