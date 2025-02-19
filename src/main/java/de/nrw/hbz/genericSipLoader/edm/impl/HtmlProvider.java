/**
 * 
 */
package de.nrw.hbz.genericSipLoader.edm.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.edm.model.Aggregation;
import de.nrw.hbz.genericSipLoader.edm.model.Rdf;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeAggregation;
import de.nrw.hbz.genericSipLoader.edm.model.deserialize.DeserializeResourceAttribute;


/**
 * 
 */
public class HtmlProvider {
  
  final static Logger logger = LogManager.getLogger(HtmlProvider.class);

  private Rdf rdf = null;
  private String filePath = System.getProperty("user.dir") 
      + System.getProperty("file.separator") + "src/main/resources/presentation-template.html";

  private String htmlHead = "<!DOCTYPE html>\n<html lang=\"de\">"
      + "<head>\n<title>DA.NRW Item Presentation</title>\n"
      + "<meta http-equiv=\"Content-Type\" \n"
      + "      content=\"text/html; charset=utf-8\">"
      + "<style>\n"
      + "\n"
      + "    h1 { \n"
      + "       background-color: green;\n"
      + "       color: white;\n"
      + "       font: sans-serif; 15px;"
      + "    }\n"
      + "    p {\n"
      + "      text-align: center; \n"
      + "      color: green;\n"
      + "    }\n"
      + "\n"
      + "    </style></head>\n" +
      "<body>";
  private String htmlFoot = "</body>\n</html>";
  private StringBuffer htmlText = new StringBuffer();
  
  public HtmlProvider() {
    appendHead();
    appendMD();
    appendFoot();
  }
  
  public HtmlProvider(Rdf rdf) {
    this.rdf = rdf;
    readHeadTemplate();
    appendHead();
    appendMD();
    appendContent();
    appendFoot();
  }
 
  
  
  /**
   * Read html head template into String in order to append this as first 
   * part to the html splash page the HtmlProvider will provide
   */
  private void readHeadTemplate() {
    File template = new File(filePath);
    StringBuffer head = new StringBuffer();
    try {
      FileReader is = new FileReader(template);
      BufferedReader bis = new BufferedReader(is);
      String line = null;
        while((line = bis.readLine()) != null) {
          head.append(line + "\n");
        }
      htmlHead = head.toString();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  private void appendHead() {
    htmlText.append(htmlHead);
    
  }
  
  private void appendMD() {
   
    htmlText.append("<h3>" + rdf.getProvidedCho().getDcTitle().get(0) + "</h3>\n");

    LinkedHashMap<String, ArrayList<String>> metadata = new LinkedHashMap<>();
    metadata.put("<span class=\"field_name\">Beschreibung: </span>", rdf.getProvidedCho().getDcDescription());
    metadata.put("<span class=\"field_name\">Personen: </span>", rdf.getProvidedCho().getDcCreator());
    metadata.put("<span class=\"field_name\">Beteiligte: </span>", rdf.getProvidedCho().getDcContributor());
    metadata.put("<span class=\"field_name\">Datum: </span>", rdf.getProvidedCho().getDcDate());
    metadata.put("<span class=\"field_name\">Sprache: </span>", rdf.getProvidedCho().getDcLanguage());
    metadata.put("<span class=\"field_name\">Umfang, Besonderheit: </span>", rdf.getProvidedCho().getDctermsExtent());
    metadata.put("<span class=\"field_name\">Dokumentenart: </span>", rdf.getProvidedCho().getDcType());
    metadata.put("<span class=\"field_name\">Identifier: </span>", rdf.getProvidedCho().getDcIdentifier());
    
    Set<String> metaData = metadata.keySet();
    
    Iterator<String> mdEnum = metaData.iterator();
    
    while(mdEnum.hasNext()) {
      String key = mdEnum.next();
      ArrayList<String> values = metadata.get(key);
      Iterator<String> valIt = values.iterator();
      while(valIt.hasNext()) {
        htmlText.append("<h5>" + key + valIt.next() + "</h5>\n");
        
      }
    }
    htmlText.append("<hr/>\n");

   // htmlText.append("<h2>" + rdf.getProvidedCho().getDcDescription().get(0) + "</h2>\n");
   // htmlText.append("<h2>" + rdf.getProvidedCho().getDcPublisher().get(0) + "</h2>\n");    
  }
  
  private void appendContent() {
   htmlText.append("<div>\n<ul>\n");
   
   appendContentItem();

   htmlText.append("</ul></div>\n");
  }
  
  private void appendContentItem() {
    for(int i = 0; i < rdf.getAggregation().size(); i++) {
      htmlText.append("<li><a href=\"" + rdf.getAggregation().get(i).getEdmObject().getRdfResource() + 
          "\">" + appendPartLabel(i) + "</a></li>\n");
    }
  }
  
  private String appendPartLabel(int i) {
    String sTitel = "Bestandteil " + i;
    
    if(rdf.getWebResource() == null || rdf.getWebResource().size()==0 ) {
      logger.debug("No WebResource provided");
      return sTitel;
    } else {
      for(int j=0; j< rdf.getWebResource().size(); j++) {
        if(rdf.getWebResource().get(j).getWebResourceAbout().equals(rdf.getAggregation().get(i).getEdmIsShownBy().getRdfResource())){

          sTitel = rdf.getWebResource().get(j).getDcDescription().get(0);
          
        }
      }
    }
    return sTitel;
  }
  
  private void appendFoot() {
    htmlText.append("<hr/>\n");
    htmlText.append("<h5><span class=\"field_name\">Besitz: </span>" + rdf.getProvidedCho().getDcPublisher().get(0) + "<br/>\n"); 
    htmlText.append("<span class=\"field_name\">Format</span>" + rdf.getProvidedCho().getEdmType() + "</h5>\n");    

    htmlText.append(htmlFoot);
  }
  
  public File toTempFile() {
    File tmpFile = null;
    BufferedOutputStream bos = null;
    try {
      tmpFile = File.createTempFile("html-", ".html");
      FileOutputStream fos = new FileOutputStream(tmpFile);
      bos = new BufferedOutputStream(fos);      
      bos.write(htmlText.toString().getBytes("UTF-8"));
      bos.flush();
      bos.close();
      return tmpFile;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      tmpFile.deleteOnExit();
    }
    return null;
  }
  
  public Rdf appendHtmlAggregation(String dsUrl) {
    // int i = rdf.getAggregation().size();
    Aggregation aggregation = new DeserializeAggregation();
    aggregation.setEdmAggregatedCHO(new DeserializeResourceAttribute("SplashPage"));
    aggregation.setEdmIsShownBy(new DeserializeResourceAttribute(dsUrl));
    aggregation.setEdmObject(new DeserializeResourceAttribute(dsUrl));
    aggregation.setEdmAboutCHOResource("SplashPage");
    aggregation.setEdmDataProvider(rdf.getAggregation().get(0).getEdmDataProvider());
    aggregation.setEdmProvider(rdf.getAggregation().get(0).getEdmProvider());
    aggregation.setEdmRights(new DeserializeResourceAttribute("http://creativecommons.org/publicdomain/zero/1.0"));
    rdf.addAggregation(aggregation);
    
    return rdf;
  }
  
  @Override
  public String toString() {
    return htmlText.toString();
  }

}
