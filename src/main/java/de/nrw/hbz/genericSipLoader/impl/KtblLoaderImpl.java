/**
 * 
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.restClient.KtblClient;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;

/**
 * @author aquast
 *
 */
public class KtblLoaderImpl {

  public KtblLoaderImpl(String basePath, String user, String passwd) {
    this.basePath = basePath;
    this.user = user;
    this.passwd = passwd;
  }
  
  final static Logger logger = LogManager.getLogger(KtblLoaderImpl.class);
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
    fScan.processScan();
    Set<String> ieList = fScan.getFileList();
    return ieList;
  }

  public String createToScienceObject(String type) {
	  return createToScienceObjectChild(type, null);
  }
  
  public String createToScienceObjectChild(String type, String parentId) {
    KtblClient client = new KtblClient(user, passwd);
    return client.postToScienceObject(type, parentId);
  }
  
  public void uploadFile(File file, String childId) {
	    KtblClient client = new KtblClient(user, passwd);
	    client.postFileStream(childId, file);
	  }
  /*
  public void addMetadataStream(String pid, String mdSchema, File file) {
    KtblClient client = new KtblClient(user, passwd);
    client.postXmlMetadataStream(pid, mdSchema, file);
  }

  public void addPayLoadStream(String pid, int id, File file) {
    String DSId = "DS" + id;
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postPayLoadStream(pid, DSId, file);
  }
  */
  /**
   * create or update a FedoraObject in accordance with DANRW an DIPs specification
   * DIPs submissions require some preparation to comply with Fedora Data Model
   * 
   * @param fList
   */
  public void cuToScienceObject(Set<String> fList) {

	// create new empty ToScienceObject
    String parentId = createToScienceObject("researchdata");
    // create childressource from parentressource above
    String childPid = createToScienceObjectChild("file", parentId);
    
    Iterator<String> fIt = fList.iterator();
    while(fIt.hasNext()) {
      String fileName = fIt.next();
      logger.debug(fileName);
      /*
      int index = fileName.lastIndexOf("/");
      String parent = fileName.substring(0, index);
      index = parent.lastIndexOf("/");
      String sourceId = parent.substring(index +1);
      */
            
      //String pid = "danrw:2023-09-24-" + sourceId;
      
      if(parentId!=null) {
        // add EDM.xml
        //addMetadataStream(pid, "EDM", new File(fileName));
    	// add files to parent Resource
    	uploadFile(new File(fileName), childPid);
      } else {
        logger.warn("Cannot create ToScience object or upload file");
      }
      /*
      FileScanner fScan = new FileScanner(parent);
      fScan.processScan("EPICUR");
      Set<String> epicurList = fScan.getFileList();
      Iterator<String> epiIt = epicurList.iterator();
      while(epiIt.hasNext()) {
        String epiFileName = epiIt.next();
        logger.debug(epiFileName);
        addMetadataStream(pid, "EPICUR", new File(epiFileName));
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
      */

      //System.out.println(sourceId);
      
    }
  }
}
