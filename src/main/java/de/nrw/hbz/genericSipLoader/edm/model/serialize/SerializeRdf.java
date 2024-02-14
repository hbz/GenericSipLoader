package de.nrw.hbz.genericSipLoader.edm.model.serialize;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import de.nrw.hbz.genericSipLoader.edm.model.Aggregation;
import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.Rdf;
import de.nrw.hbz.genericSipLoader.edm.model.WebResource;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "rdf:RDF")
public class SerializeRdf implements Rdf {

  private final String rdfXmlns = "http://www.w3.org/1999/02/22-rdf-sysntax-ns#";
  private final String edmXmlns = "http://www.europeana.eu/schemas/edm/";
  private final String oreXmlns = "http://www.openarchives.org/ore/terms/";
  private final String dcXmlns = "http://purl.org/dc/elements/1.1/";
  private final String dctermsXmlns = "http://purl.org/dc/terms/";

  private SerializeProvidedCHO providedCHO = new SerializeProvidedCHO();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<SerializeAggregation> aggregation = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<SerializeWebResource> webResource = new ArrayList<>();

  /**
   * @return the rdfXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:rdf", isAttribute = true)
  public String getRdfXmlns() {
    return rdfXmlns;
  }

  /**
   * @return the edmXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:edm", isAttribute = true)
  public String getEdmXmlns() {
    return edmXmlns;
  }

  /**
   * @return the oreXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:ore", isAttribute = true)
  public String getOreXmlns() {
    return oreXmlns;
  }

  /**
   * @return the dcXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:dc", isAttribute = true)
  public String getDcXmlns() {
    return dcXmlns;
  }

  /**
   * @return the dctermsXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:dcterms", isAttribute = true)
  public String getDctermsXmlns() {
    return dctermsXmlns;
  }

  /**
   * @param aggregation the aggregation to add
   */
  @Override
  public void addAggregation(Aggregation aggregation) {
    if (this.aggregation == null) {
      this.aggregation = new ArrayList<SerializeAggregation>();
    }
    this.aggregation.add((SerializeAggregation) aggregation);
  }

  /**
   * @param webResource the webResource to add
   */
  @Override
  public void addWebResource(WebResource webResource) {
    if (this.webResource == null) {
      this.webResource = new ArrayList<SerializeWebResource>();
    }
    this.webResource.add((SerializeWebResource) webResource);
  }

  // Element Getter
  /**
   * @return the providedCHO
   */
  @JacksonXmlProperty(localName = "edm:ProvidedCHO")
  public ProvidedCHO getProvidedCho() {
    return (SerializeProvidedCHO) providedCHO;
  }

  /**
   * @return the aggregation
   */
  @JacksonXmlProperty(localName = "ore:Aggregation")
  public ArrayList<? extends Aggregation> getAggregation() {
    return aggregation;
  }

  @Override
  @JacksonXmlProperty(localName = "edm:WebResource")
  public ArrayList<? extends WebResource> getWebResource() {
    return this.webResource;
  }

  // Element Setter
  /**
   * @param aggregation the aggregation to set
   */
  @Override
  @JacksonXmlProperty(localName = "ore:Aggregation")
  public void setAggregation(ArrayList<? extends Aggregation> aggregation) {
    if (this.aggregation == null) {
      this.aggregation = new ArrayList<SerializeAggregation>();
    }
    this.aggregation = (ArrayList<SerializeAggregation>) aggregation;
  }

  /**
   * @param providedCHO the providedCHO to set
   */
  @Override
  @JacksonXmlProperty(localName = "edm:ProvidedCHO")
  public void setProvidedCho(ProvidedCHO providedCHO) {
    this.providedCHO = (SerializeProvidedCHO) providedCHO;
  }

  @Override
  @JacksonXmlProperty(localName = "edm:WebResource")
  public void setWebResource(ArrayList<? extends WebResource> webResource) {
    if (this.webResource == null) {
      this.webResource = new ArrayList<SerializeWebResource>();
    }
    this.webResource = (ArrayList<SerializeWebResource>) webResource;

  }

}
