/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.Qdc;
import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeQdc;
import de.nrw.hbz.genericSipLoader.edm.model.serialize.SerializeRdf;

/**
 * class 
 */
public class QdcProvider {

  public QdcProvider(ProvidedCHO providedCho) {
   
    this.providedCho = providedCho;
    createQdc();
  }
  
  ProvidedCHO providedCho = null;
  Qdc qdc = null;
  
  private void createQdc() {
    this.qdc = new SerializeQdc();
    qdc.setDcTitle(providedCho.getDcTitle());
    qdc.setDcCreator(providedCho.getDcCreator());
    qdc.setDcLanguage(providedCho.getDcLanguage());
    qdc.setDcDescription(providedCho.getDcDescription());
    qdc.setDcContributor(providedCho.getDcContributor());
    qdc.setDcIdentifier(providedCho.getDcIdentifier()); 
    qdc.setDcDate(providedCho.getDcDate());
    
    qdc.setDctermsExtent(providedCho.getDctermsExtent());
    qdc.setDctermsIssued(providedCho.getDctermsIssued());
    qdc.setDctermsCreated(providedCho.getDctermsCreated());
    qdc.setDctermsProvenance(providedCho.getDctermsProvenance());
    qdc.setDctermsIsReferencedBy(providedCho.getDctermsIsReferencedBy());
    
  }
  
  public String getQdc() {
    return toString();
  }

  public Qdc getQdc(ProvidedCHO providedCho) {
    this.providedCho = providedCho;
    createQdc();
    return this.qdc;
  }

  /**
   * return complete EDM metadata as serialization to String
   */
  @Override
  public String toString() {
    XmlMapper xmlMapper = new XmlMapper();
    String xml = null;
    try {
      xmlMapper.writerFor(SerializeQdc.class);
      xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +  xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(qdc) + "\n";
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return xml;
  }

}
