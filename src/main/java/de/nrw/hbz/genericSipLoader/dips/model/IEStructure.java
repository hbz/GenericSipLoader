/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.nrw.hbz.genericSipLoader.dips.model.deserialize.DeserializeIEStructure;

/**
 * Interface that provides methods to read and write XML-based representations of
 * EDM meta data
 * RDF does not mean a rdf representation. Instead it's the XML root element provided in EDM
 *  
 */
@JsonDeserialize(as = DeserializeIEStructure.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface IEStructure {
  
  /**
   * @return the baDipsXmlns
   */
  public String getDipsXmlns();
  
  /**
   * @return
   */
  public String getTitle();
  
  /**
   * @return
   */
  public String getStructureIdentifier();
  
  /**
   * @return
   */
  public ArrayList<ChildStructure> getChildStructure();
  
  
}
