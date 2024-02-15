/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;


import de.nrw.hbz.genericSipLoader.edm.model.OaiPmh;

/**
 * 
 */
public class OaiPmhImpl {
  
  private String filePath = null;

  public OaiPmh deserializeXml() {
    DeserializeOaiPmhXml dsXml = new DeserializeOaiPmhXml(filePath);
    return dsXml.deserialize();
    
  }

  public void serializeXml(OaiPmh edm) {
    SerializeOaiPmhXml sXml = new SerializeOaiPmhXml();
    sXml.setEdm(edm);
    sXml.serialize();    
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
