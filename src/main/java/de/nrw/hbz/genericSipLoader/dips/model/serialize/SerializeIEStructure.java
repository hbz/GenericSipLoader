/**
 * 
 */
package de.nrw.hbz.genericSipLoader.dips.model.serialize;

import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.nrw.hbz.genericSipLoader.dips.model.ChildStructure;
import de.nrw.hbz.genericSipLoader.dips.model.IEStructure;

/**
 * 
 */
public class SerializeIEStructure implements IEStructure {

  private final String OaiDcXmlns = "http://www.openarchives.org/OAI/2.0/oai_dc/";
  private final String dcXmlns = "http://purl.org/dc/elements/1.1/";
  private final String dctermsXmlns = "http://purl.org/dc/terms/";

  private final String xsiSchemaLocation = "http://www.openarchives.org/OAI/2.0/oai_dc http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";

  @JacksonXmlElementWrapper(useWrapping = false)
  private String title = new String();
  @JacksonXmlElementWrapper(useWrapping = false)
  private String structureIdentifier = new String();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> childStructure = new ArrayList<>();

  private String dipsXmlns = new String();; 

  @JacksonXmlProperty(localName="xmlns:dips", isAttribute = true)
  public String getDipsXmlns() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getStructureIdentifier() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<ChildStructure> getChildStructure() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setChildStructure(ArrayList<ChildStructure> childStructure) {
    // TODO Auto-generated method stub
    
  }

}
