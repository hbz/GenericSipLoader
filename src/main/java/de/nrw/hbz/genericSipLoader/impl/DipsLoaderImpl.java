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
import de.nrw.hbz.genericSipLoader.edm.impl.EdmProvider;
import de.nrw.hbz.genericSipLoader.edm.impl.HtmlProvider;
import de.nrw.hbz.genericSipLoader.edm.impl.QdcProvider;
import de.nrw.hbz.genericSipLoader.edm.model.ProvidedCHO;
import de.nrw.hbz.genericSipLoader.edm.model.Rdf;
import de.nrw.hbz.genericSipLoader.restClient.Fedora38Client;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.FileUtil;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;
import jakarta.ws.rs.core.MediaType;

/**
 * @author aquast
 * A class that processes incoming DiPS.kommunal zip files.
 * 
 * The class prepares these files and delegates them to appropriate methods 
 * of FedoraClient class in order to submit the zip-file content to DA.NRW.   
 *
 */
public class DipsLoaderImpl {

  final static Logger logger = LogManager.getLogger(DipsLoaderImpl.class);
  private String basePath = System.getProperty("user.dir");
  private String user = null;
  private String passwd = null;
  private String workDir = "unpacked";

	public DipsLoaderImpl(String basePath, String user, String passwd) {
		logger.debug("DipsLoaderImpl has been called.");
		this.basePath = basePath;
		this.user = user;
		this.passwd = passwd;
		FileUtil.makeWorkDir(basePath, workDir);
	}

	/**
	 * extract content of zip into the directory where the zip resides
	 */
	public void extractZips() {

		FileScanner fScan = new FileScanner(basePath);
		fScan.processScan(".zip");
		Set<String> fList = fScan.getFileList();

		logger.debug("Zip-extraction starts");

		ZipExtractor extractor = new ZipExtractor(fList, basePath);
		extractor.extractZip();
	}

	/**
	 * DiPS sometimes provides a zip file with more then one EDM file.
	 * Per definition we view every content associated with an EDM file as 
	 * one Intellectual Entity. 
	 * 
	 * @return list of EDM files (presenting one IE) found in one zip file 
	 */
	public Set<String> scanIEs() {
		FileScanner fScan = new FileScanner(basePath);
		fScan.processScan("EDM.xml");
		Set<String> ieList = fScan.getFileList();
		return ieList;
	}

	/**
	 * create and persist Fedora Object with sourceId as pid, 
	 * using Fedora38client
	 * 
	 * @param sourceId
	 * @return
	 */
	private String createFedoraObject(String sourceId) {
		String objId = null;
		Fedora38Client client = new Fedora38Client(user, passwd);
		objId = client.postFedoraObject(sourceId);
		return objId;
	}

	/**
	 * add and persist XML Metadata to Fedora Object identified by pid, 
   * using Fedora38client
   * 
	 * @param pid
	 * @param mdSchema
	 * @param file
	 */
	public void addMetadataStream(String pid, String mdSchema, File file) {
		Fedora38Client client = new Fedora38Client(user, passwd);
		client.postXmlMetadataFile(pid, mdSchema, file);
	}

	/**
   * add and persist PayLoad to Fedora Object identified by pid, 
   * using Fedora38client
	 * 
	 * @param pid
	 * @param id
	 * @param file
	 */
	public void addPayLoadStream(String pid, int id, File file) {
		String DSId = "DS" + id;
		Fedora38Client client = new Fedora38Client(user, passwd);
		client.postPayLoadFile(pid, DSId, file);
	}
	
  /**
   * add and persist relationship in RELS-EXT of a Fedora Object identified by pid, 
   * using Fedora38client 
   * 
   * @param pid
   * @param subject
   * @param predicate
   * @param object
   */
  private void addRelationship(String pid, String subject, String predicate, String object) {
    Fedora38Client client = new Fedora38Client(user, passwd);
    client.postRelationship(pid, subject, predicate, object);;
  }

	/**
	 * create or update a FedoraObject in accordance with DANRW an DIPs
	 * specification DIPs submissions require some preparation to comply with
	 * Fedora Data Model
	 * 
	 * @param fList List of EDM-files found by scanIEs method
	 */
	public void persistFedoraObject(Set<String> fList) {

    Iterator<String> fIt = fList.iterator();
    while(fIt.hasNext()) {
      Hashtable<String, String> idReplacement = new Hashtable<>();
      
      // find correct sourceId for using this as fedora pid
      String fileName = fIt.next();
      logger.debug(fileName);
      int index = fileName.lastIndexOf("/");
      String parent = fileName.substring(0, index);
      index = parent.lastIndexOf("/");
      
      // sourceId will be defined by parent directory name 
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
        logger.debug("Payload file name: " + plFileName);
        int plIndex = plFileName.lastIndexOf("/");
        String plId = plFileName.substring(plIndex +1);
        String dsUrl = createDSUrl(pid, "DS" + id);
        idReplacement.put(plId, dsUrl);
        logger.info("Replace " + plId + " with " + idReplacement.get(plId));
      }
      logger.debug(pid);

      addMetadataStream(pid, "EDM_submitted.xml", new File(fileName));

      logger.debug("Start with creation of html structure file now");
      HtmlProvider htmlProv = new HtmlProvider(EdmProvider.deserialize(refactorEdm(idReplacement, fileName)));
      addPayLoadStream(pid, id+1, htmlProv.toTempFile());
      String edmResult = EdmProvider.serialize(htmlProv.appendHtmlAggregation(createDSUrl(pid, "DS" + (id+1))));
       
      Hashtable<String,String> xmlStreams = new Hashtable<>();
      xmlStreams.put("EDM.xml", edmResult);
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
           tmpFile.delete();
          try {
            bos.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        
      }
      
    }
 
		
  // Last step: clean up the work directory
  FileUtil.removeWorkDir(basePath, workDir);
	}
	
  private String createQDC(String edmFileName) {
    Rdf rdf = EdmProvider.deserialize(new File(edmFileName));
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
    logger.debug(edmFileName);
    Rdf rdf = EdmProvider.deserialize(new File(edmFileName));
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
