/**
 * 
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.restClient.Fedora38Client;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;
import jakarta.ws.rs.core.MediaType;

/**
 * @author aquast
 *
 */
public class DipsLoaderImpl {

  public DipsLoaderImpl(String basePath, String user, String passwd) {
    this.basePath = basePath;
    this.user = user;
    this.passwd = passwd;
  }
  
  final static Logger logger = LogManager.getLogger(DipsLoaderImpl.class);
  private String basePath = System.getProperty("user.dir");
  private String user = null;
  private String passwd = null;
  
  public void extractZips() {
    
    FileScanner fScan = new FileScanner(basePath);
    fScan.processScan(".zip");
    Set<String> fList = fScan.getFileList();
    
    logger.info("\nZip-extraction starts\n");
    
    ZipExtractor extractor = new ZipExtractor(fList, basePath);
    
  }
  
  public Set<String> scanIEs() {
    FileScanner fScan = new FileScanner(basePath);
    fScan.processScan("EDM.xml");
    Set<String> ieList = fScan.getFileList();
    return ieList;
  }
  
  public String createFedoraObject(String sourceId) {
    String objId = null;
    Fedora38Client client = new Fedora38Client(user, passwd);
    objId = client.postFedoraObject(sourceId);
    return objId;
  }
  
  public void addMetadataStream(String pid, String mdSchema, File file) {
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postXmlMetadataStream(pid, mdSchema, file);
  }

  public void addMetadataStream(String pid, String mdSchema, InputStream fileStream) {
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postXmlMetadataStream(pid, mdSchema, fileStream);
  }
  
  public void addRelationship(String pid, String subject, String predicate, String object) {
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postRelationship(pid, subject, predicate, object);;
  }
  
  
  public void addPayLoadStream(String pid, int id, File file) {
    String DSId = "DS" + id;
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postPayLoadStream(pid, DSId, file);
  }

  /**
   * create or update a FedoraObject in accordance with DANRW an DIPs specification
   * DIPs submissions require some preparation to comply with Fedora Data Model
   * 
   * @param fList
   */
  public void cuFedoraObject(Set<String> fList) {

    Iterator<String> fIt = fList.iterator();
    int i = 1;
    while(fIt.hasNext()) {
      // find correct sourceId
      String fileName = fIt.next();
      logger.debug(fileName);
      int index = fileName.lastIndexOf("/");
      String parent = fileName.substring(0, index);
      index = parent.lastIndexOf("/");
      String sourceId = parent.substring(index +1);
      
      // use sourceId for generation of empty Fedora Object
      String pid = createFedoraObject(sourceId);      
      //String pid = "danrw:2023-09-24-" + sourceId;

      if(pid!=null) {
        // add EDM.xml
        addMetadataStream(pid, "EDM.xml", new File(fileName));
        addRelationship(pid, "info:fedora/" + pid , "http://www.openarchives.org/OAI/2.0/identifier", "oai:" + pid);
        addRelationship(pid, "info:fedora/" + pid , "info:fedora/fedora-system:def/relations-external#isMemberOf", "info:fedora/set:ddb");
        addRelationship(pid, "info:fedora/" + pid , "info:fedora/fedora-system:def/relations-external#isMemberOf", "info:fedora/set:open-access");
      } else {
        logger.warn("Cannot create Fedora object for: " + sourceId);
      }
      
      FileScanner fScan = new FileScanner(parent);
      fScan.processScan("EPICUR");
      Set<String> epicurList = fScan.getFileList();
      Iterator<String> epiIt = epicurList.iterator();
      while(epiIt.hasNext()) {
        String epiFileName = epiIt.next();
        logger.debug(epiFileName);
        addMetadataStream(pid, "epicur.xml", new File(epiFileName));
      }
      
      List<String> mimeTypes = new ArrayList<>();
      mimeTypes.add(MediaType.APPLICATION_XML);
      //mimeTypes.add(MediaType.TEXT_PLAIN);
      fScan.processScan(mimeTypes);
      Set<String> payLoadList = fScan.getFileList();
      Iterator<String> payLoadIt = payLoadList.iterator();    
      int id = 0;
      while(payLoadIt.hasNext()) {
        id++;
        addPayLoadStream(pid, id, new File(payLoadIt.next()));
        i = i-1;
      }
      

      //System.out.println(sourceId);
      
    }
  }
}
