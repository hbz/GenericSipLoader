package de.nrw.hbz.genericSipLoader.edm.model.serialize;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import de.nrw.hbz.genericSipLoader.edm.model.Qdc;
import de.nrw.hbz.genericSipLoader.edm.model.ResourceAttribute;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "oai_dc:dc")
public class SerializeQdc implements Qdc {

  private final String OaiDcXmlns = "http://www.openarchives.org/OAI/2.0/oai_dc/";
  private final String dcXmlns = "http://purl.org/dc/elements/1.1/";
  private final String dctermsXmlns = "http://purl.org/dc/terms/";

  private final String xsiSchemaLocation = "http://www.openarchives.org/OAI/2.0/oai_dc http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";

  @JacksonXmlElementWrapper(useWrapping = false)
  private List<String> dcTitle = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcDescription = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcCreator = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcContributor = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcIdentifier = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcPublisher = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcLanguage = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcType = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dcDate = new ArrayList<>();

  private String dctermsCreated = new String();
  private String dctermsIssued = new String();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dctermsExtent = new ArrayList<>();
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<SerializeResourceAttribute> dctermsIsReferencedBy = null;
  @JacksonXmlElementWrapper(useWrapping = false)
  private ArrayList<String> dctermsProvenance = new ArrayList<>();

  /**
   * @return the OaiDcXmlns
   */
  @JacksonXmlProperty(localName = "xmlns:oai_dc", isAttribute = true)
  public String getOaiDcXmlns() {
    return OaiDcXmlns;
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
   * @return the xsiSchemaLocation
   */
  public String getXsiSchemaLocation() {
    return this.xsiSchemaLocation;
  }

  // add to List
  /**
   * @param add item to dctermsExtent
   */
  @Override
  public void addDctermsExtent(String extent) {
    this.dctermsExtent.add(extent);
  }
  /**
   * @param add item to dctermsIsReferencedBy
   */
  @Override
  public void addDctermsIsReferencedBy(ResourceAttribute isReferencedBy) {
    this.dctermsIsReferencedBy.add((SerializeResourceAttribute) isReferencedBy);
  }
  /**
   * @param add item to dctermsProvenance
   */
  @Override
  public void addDctermsProvenance(String provenance) {
    this.dctermsProvenance.add(provenance);
  }
  /**
   * @param add item to dcTitle
   */
  @Override
  public void addDcTitle(String title) {
    this.dcTitle.add(title);
  }
  /**
   * @param add item to dcDescription
   */
  @Override
  public void addDcDescription(String description) {
    this.dcDescription.add(description);
  }
  /**
   * @param dcCreator the dcCreator to set
   */
  @Override
  public void addDcCreator(String creator) {
    this.dcCreator.add(creator);
  }
  /**
   * @param dcContributor the dcContributor to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:contributor")
  public void addDcContributor(String contributor) {
    this.dcContributor.add(contributor);
  }
  /**
   * @param dcIdentifier the dcIdentifier to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:identifier")
  public void addDcIdentifier(String identifier) {
    this.dcIdentifier.add(identifier);
  }
  /**
   * @param dcPublisher the dcPublisher to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:publisher")
  public void addDcPublisher(String publisher) {
    this.dcPublisher.add(publisher);
  }
  /**
   * @param dcLanguage the dcLanguage to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:language")
  public void addDcLanguage(String language) {
    this.dcLanguage.add(language);
  }
  /**
   * @param dcType the dcType to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:date")
  public void addDcType(String dcType) {
    this.dcType.add(dcType);
  }
  /**
   * @param dcDate the dcDate to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:date")
  public void addDcDate(String date) {
    this.dcDate.add(date);
  }

  // Getter
  /**
   * @return the dcTitle
   */
  @Override
  @JacksonXmlProperty(localName="dc:title")
  public List<String> getDcTitle() {
    return dcTitle;
  }
  /**
   * @return the dcDescription
   */
  @Override
  @JacksonXmlProperty(localName="dc:description")
  public ArrayList<String> getDcDescription() {
    return dcDescription;
  }
  /**
   * @return the dcCreator
   */
  @Override
  @JacksonXmlProperty(localName="dc:creator")
  public ArrayList<String> getDcCreator() {
    return dcCreator;
  }
  /**
   * @return the dcContributor
   */
  @Override
  @JacksonXmlProperty(localName="dc:contributor")
  public ArrayList<String> getDcContributor() {
    return dcContributor;
  }
  /**
   * @return the dcIdentifier
   */
  @Override
  @JacksonXmlProperty(localName="dc:identifier")
  public ArrayList<String> getDcIdentifier() {
    return dcIdentifier;
  }
  /**
   * @return the dcPublisher
   */
  @Override
  @JacksonXmlProperty(localName="dc:publisher")
  public ArrayList<String> getDcPublisher() {
    return dcPublisher;
  }
  /**
   * @return the dcLanguage
   */
  @Override
  @JacksonXmlProperty(localName="dc:language")
  public ArrayList<String> getDcLanguage() {
    return dcLanguage;
  }
  /**
   * @return the dcType
   */
  @Override
  @JacksonXmlProperty(localName="dc:type")
  public ArrayList<String> getDcType() {
    return dcType;
  }
  /**
   * @return the dcDate
   */
  @Override
  @JacksonXmlProperty(localName="dc:date")
  public ArrayList<String> getDcDate() {
    return dcDate;
  }
  /**
   * @return the dctermsCreated
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:created")
  public String getDctermsCreated() {
    return dctermsCreated;
  }
  /**
   * @return the dctermsIssued
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:issued")
  public String getDctermsIssued() {
    return dctermsIssued;
  }
  /**
   * @return the dctermsExtent
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:extent")
  public ArrayList<String> getDctermsExtent() {
    return dctermsExtent;
  }
  /**
   * @return the dctermsIsReferencedBy
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:isReferencedBy")
  public ArrayList<? extends ResourceAttribute> getDctermsIsReferencedBy() {
    return dctermsIsReferencedBy;
  }
  /**
   * @return the dctermsProvenance
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:provenance")
  public ArrayList<String> getDctermsProvenance() {
    return dctermsProvenance;
  }

  // Setter
  /**
   * @param dctermsCreated the dctermsCreated to set
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:created")
  public void setDctermsCreated(String dctermsCreated) {
    this.dctermsCreated = dctermsCreated;
  }
  /**
   * @param dctermsIssued the dctermsIssued to set
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:issued")
  public void setDctermsIssued(String dctermsIssued) {
    this.dctermsIssued = dctermsIssued;
  }
  /**
   * @param dctermsExtent the dctermsExtent to set
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:extent")
  public void setDctermsExtent(ArrayList<String> dctermsExtent) {
    this.dctermsExtent = dctermsExtent;
  }
  /**
   * @param dctermsIsReferencedBy the dctermsIsReferencedBy to set
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:isReferencedBy")
  public void setDctermsIsReferencedBy(ArrayList<? extends ResourceAttribute> dctermsIsReferencedBy) {
    this.dctermsIsReferencedBy = (ArrayList<SerializeResourceAttribute>) dctermsIsReferencedBy;
  }
  /**
   * @param dctermsProvenance the dctermsProvenance to set
   */
  @Override
  @JacksonXmlProperty(localName="dcterms:provenance")
  public void setDctermsProvenance(ArrayList<String> dctermsProvenance) {
    this.dctermsProvenance = dctermsProvenance;
  }
  /**
   * @param dcTitle the dcTitle to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:title")
  public void setDcTitle(ArrayList<String> title) {
    this.dcTitle = title;
  }
  /**
   * @param dcDescription the dcDescription to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:description")
  public void setDcDescription(ArrayList<String> description) {
    this.dcDescription = description;
  }
  /**
   * @param dcCreator the dcCreator to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:creator")
  public void setDcCreator(ArrayList<String> creator) {
    if(this.dcCreator == null) {
      this.dcCreator = new ArrayList<String>();
    }
    this.dcCreator = creator;
  }
  /**
   * @param dcContributor the dcContributor to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:contributor")
  public void setDcContributor(ArrayList<String> contributor) {
    this.dcContributor = contributor;
  }
  /**
   * @param dcIdentifier the dcIdentifier to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:identifier")
  public void setDcIdentifier(ArrayList<String> identifier) {
    this.dcIdentifier = identifier;
  }
  /**
   * @param dcPublisher the dcPublisher to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:publisher")
  public void setDcPublisher(ArrayList<String> publisher) {
    this.dcPublisher = publisher;
  }
  /**
   * @param dcLanguage the dcLanguage to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:language")
  public void setDcLanguage(ArrayList<String> language) {
    this.dcLanguage = language;
  }
  /**
   * @param dcType the dcType to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:type")
 public void setDcType(ArrayList<String> type) {
    this.dcType = type;
  }
  /**
   * @param dcDate the dcDate to set
   */
  @Override
  @JacksonXmlProperty(localName="dc:date")
  public void setDcDate(ArrayList<String> date) {
    this.dcDate = date;
  }
  
}
