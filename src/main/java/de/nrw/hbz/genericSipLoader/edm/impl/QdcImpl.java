/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;

/**
 * 
 */
public class QdcImpl {
  
  
  public QdcImpl() {
    
  }
  
  public QdcImpl(String filePath) {
    this.filePath = filePath;
  }
  
  private String filePath = null; 

      /**
       * @return ProvidedCHO
       */
      public ProvidedCHO deserializeXml() {
        DeserializeQdcXml dsXml = new DeserializeQdcXml(filePath);
        return dsXml.deserialize();
        
      }

      /**
       * create an XML serialization from ProvidedCHO object
       * @param ProvidedCHO
       */
      public String serializeXml(ProvidedCHO providedCho) {
        String xml = null;
        SerializeQdcXml sXml = new SerializeQdcXml();
        sXml.setProvidedCHO(providedCho);
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
