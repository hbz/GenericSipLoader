/**
 * 
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.edm.impl.AggregationElementOperator;
import de.nrw.hbz.genericSipLoader.edm.impl.EdmImpl;
import de.nrw.hbz.genericSipLoader.edm.impl.QdcProvider;
import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.Rdf;
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
   * create a FedoraObject in accordance with DANRW an DIPs specification
   * DIPs submissions require some preparation to comply with Fedora Data Model
   * 
   * @param fList
   */
  public void cuFedoraObject(Set<String> fList) {

    Iterator<String> fIt = fList.iterator();
    int i = 1;
    while(fIt.hasNext()) {
      Hashtable<String, String> idReplacement = new Hashtable<>();
     // find correct sourceId
      String fileName = fIt.next();
      logger.debug(fileName);
      int index = fileName.lastIndexOf("/");
      String parent = fileName.substring(0, index);
      index = parent.lastIndexOf("/");
      String sourceId = parent.substring(index +1);
      
      // use sourceId for generation of empty Fedora Object
      String pid = createFedoraObject(sourceId);      

      
      if(pid!=null) {
                
        String oaiId = "oai:danrw:de:" +  pid.substring(6);
        addRelationship(pid, "info:fedora/" + pid , "http://www.openarchives.org/OAI/2.0/identifier", oaiId);
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
      fScan.processScan(mimeTypes);
      Set<String> payLoadList = fScan.getFileList();
      Iterator<String> payLoadIt = payLoadList.iterator();    
      int id = 0;
      while(payLoadIt.hasNext()) {
        id++;
        String plFileName = payLoadIt.next();
        File payLoadFile = new File(plFileName);
        addPayLoadStream(pid, id, payLoadFile);
        // logger.debug("Payload file name: " + plFileName);
        int plIndex = plFileName.lastIndexOf("/");
        String plId = plFileName.substring(plIndex +1);
        String dsUrl = createDSUrl(pid, "DS" + id);
        idReplacement.put(plId, dsUrl);
        // logger.debug("Replace " + plId + " with " + idReplacement.get(plId));
        i = i-1;
      }
      logger.debug(pid);
      
      addMetadataStream(pid, "EDM_submitted.xml", new File(fileName));
      
      Hashtable<String,String> xmlStreams = new Hashtable<>();
      xmlStreams.put("EDM.xml", refactorEdm(idReplacement, fileName));
      xmlStreams.put("QDC.xml", createQDC(fileName));

      Enumeration<String> sEn = xmlStreams.keys();
      while(sEn.hasMoreElements()) {        
        String key = sEn.nextElement(); 
        File tmpFile = null;
        BufferedOutputStream bos = null;
        try {
          tmpFile = File.createTempFile("danrw-", ".xml");
          FileOutputStream fos = new FileOutputStream(tmpFile);
          bos = new BufferedOutputStream(fos);      
          bos.write(xmlStreams.get(key).getBytes("UTF-8"));
          bos.flush();
          addMetadataStream(pid, key, tmpFile);
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          // tmpFile.delete();
          try {
            bos.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        
      }
      
    }
  }
  
  
  private String createQDC(String edmFileName) {
    // TODO: implement method for getting QDC
    EdmImpl edmImpl = new EdmImpl(edmFileName);
    Rdf rdf = edmImpl.deserializeXml();
    ProvidedCHO provCho = rdf.getProvidedCho();
    QdcProvider qdcProvider = new QdcProvider(provCho);
    return qdcProvider.getQdc();
  }

  /**
   * Replace names of local files with FedoraObject dsID's in accordance with the upload of files  
   * @param edmFileName
   * @return refactored EDM as String
   */
  private String refactorEdm(Hashtable<String,String> replacements, String edmFileName) {
    EdmImpl edmImpl = new EdmImpl(edmFileName);
    Rdf rdf = edmImpl.deserializeXml();
    AggregationElementOperator ago = new AggregationElementOperator(rdf);
    ago.replaceAllIsShownBy(replacements);
    return ago.toString();
  }
  
  /**
   * Builds an URL for a datastream that is part of a FedoraObject. Method is required for replacing local filenames 
   * delivered by DIPS.kommunal with resolvable URLs in the DA.NRW Presentation Repository (Fedora Repo)   
   * @param pid
   * @param DSId
   * @return
   */
  private String createDSUrl(String pid, String DSId) {
    String DSUrl = null;
    Properties apiProps = new Properties();
    InputStream propStream = this.getClass().getClassLoader().getResourceAsStream("fedora-api.properties");
    if(propStream != null) {
      try {
        apiProps.load(propStream);
        String host = apiProps.getProperty("host");
        DSUrl = "https://" + host + "/fedora/objects/" + pid + "/datastreams/" + DSId + "/content"; 
      } catch (IOException e) {
        logger.error("Failed to load properties: " + e.getMessage());
      }
    }
    return DSUrl;    
  }
  

}
