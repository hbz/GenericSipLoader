/**
 * 
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import de.nrw.hbz.genericSipLoader.restClient.KtblClient;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.FileUtil;
import de.nrw.hbz.genericSipLoader.util.JsonFileLoader;
import de.nrw.hbz.genericSipLoader.util.PropertiesLoader;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;

import java.nio.file.Path;

/**
 * @author aquast
 *
 */
public class KtblLoaderImpl {

  final static Logger logger = LogManager.getLogger(KtblLoaderImpl.class);
  private String basePath = System.getProperty("user.dir");
  private KtblClient client = null;
  private String workDir = "unpacked";

  public KtblLoaderImpl(String basePath, String user, String passwd) {
    this.basePath = basePath;
    this.client = new KtblClient(user, passwd);
    FileUtil.makeWorkDir(basePath, workDir);

  }

  public void extractZips() {

    FileScanner fScan = new FileScanner(basePath);
    fScan.processScan(".zip");
    Set<String> fList = fScan.getFileList();

    logger.info("\nZip-extraction starts\n");

    // comment zipextractor for testing, uncomment for production
    ZipExtractor extractor = new ZipExtractor(fList, basePath);
    extractor.extractZip();

  }

  /**
   * Produces a Set of file names that will be found in the basePath directory
   * 
   * @return a Set of file names
   */
  public Set<String> scanIEs() {
    FileScanner fScan = new FileScanner(basePath);
    fScan.processScan();
    Set<String> ieList = fScan.getFileList();
    return ieList;
  }

  /*
   * The following methods call to.science.api via KtblClient Instance
   */

  /**
   * Creates an empty to.science Objects fedora representation of predefined model
   * 
   * @param type     model of to.science Object to generate. Can be Monograph,
   *                 Article, ResearchData, File, Part, ...
   * @param parentId an optional fedora ResourceId (pid) from a related superior
   *                 to.science Object.
   * @return fedora ResourceId (pid)
   */
  private String createToScienceObject(String type, String parentId) {
    return client.postToScienceObject(type, parentId);
  }

  /**
   * Uploads a file to a to.science Object
   * 
   * @param file
   * @param childId
   */
  public void uploadFile(File file, String childId) {
    client.postFileStream(childId, file);
  }

  /**
   * Uploads a json file to a to.science Object
   * 
   * @param file
   * @param parentId
   */
  public void uploadJsonFile(File file, String parentId) {
    client.postJsonFile(parentId, file);
  }

  
  
  /**
   * Method aims to persist a complete ktbl
   * ResearchData item into appropriate to.science Objects with respect to each
   * objects relation to each other
   * 
   * @param fList list of file names found by FileScanner
   */
  public void persistKtblRD(Set<String> fList) {

    LinkedHashMap<String, String> ktblDataId = new LinkedHashMap<>();

    /*
     * Sort iterator alphabetical descending As we have json files with alphabetical
     * ordered dataset names, we can predict that we create the fedora
     * representations for the datasets first and finish with the superior
     * ktblResearchData object. Due to the agreements with ktbl each Dataset by
     * itself will be acting like an independent ktblResearchData. Therefore we
     * persist a ktblResearchData representation for each Dataset
     */

    Iterator<String> fIt = ((TreeSet<String>) fList).descendingIterator();
    
    // Step 1: find json files and persist an empty ktblResearchData object for each
    // one
    while (fIt.hasNext()) {

      String pId = null;
      String fileName = fIt.next();

      if (fileName.endsWith(".json")) {
        logger.info("persist new ktblResearchData object for " + fileName);

        // create new empty ToScienceObject
        pId = createToScienceObject("researchData", null);

        /*
         * store fileName and pID into HashMap as we will create all object relations
         * prior to uploading metadata and content data to fedora
         */
        ktblDataId.put(fileName, pId);
      }
    }
    
    // Step 2: Modify json files to add
    logger.debug("search for associatedDataSets");
    addRelatededDataSetMD(ktblDataId);
    
    // Step 3: Upload modified json files into empty ktblResearchData objects
    Set<String> kSet = ktblDataId.keySet();
    Iterator<String> kIt = kSet.iterator();

    while (kIt.hasNext()) {

      String fileName = kIt.next();
      String parentId = ktblDataId.get(fileName);
      File jsonFile = new File(fileName);
      uploadJsonFile(jsonFile, parentId);
      logger.info("Uploaded JSON-File: " + fileName);
      
      File zipFile = new File(fileName.replace(".json", ".zip"));
      if (zipFile.exists()) {
        String partId = createToScienceObject("file", parentId);
        uploadFile(zipFile, partId);
        zipFile.delete();

      }

      File xlsxFile = new File(fileName.replace(".json", ".xlsx"));
      if (xlsxFile.exists()) {
        String partId = createToScienceObject("file", parentId);
        uploadFile(xlsxFile, partId);
        xlsxFile.delete();
      }

    }

    // Last step: clean up the work directory
    FileUtil.removeWorkDir(basePath, workDir);
  }

  private void addChildsToParent(LinkedHashMap<String, String> ktblDataId) {
    
    TreeSet<String> pIdSet = (TreeSet<String>) ktblDataId.keySet();
    NavigableSet<String> descSet = pIdSet.descendingSet();
    String parentId = descSet.pollFirst();
    
    Iterator<String> childPidIt = descSet.iterator();
    
    while (childPidIt.hasNext()) {
      String childId = childPidIt.next();
      createToScienceObject("part", parentId);
    }
    
  }

  /**
   * Adds relatedDatasets to the json files of the ktblResearchData objects
   * 
   * @param ktblDataId a set of json files, each represents one ktblResearchData
   *                   object
   */
  public void addRelatededDataSetMD(LinkedHashMap<String, String> ktblDataId) {

    logger.debug("found " + ktblDataId.size() + " Datasets");

    JSONArray ktblRelDat = null;
    ArrayList<String> localFileList = new ArrayList<>();
    ArrayList<Hashtable<String, String>> relatedDataset = new ArrayList<>();

    ArrayList<String> pids = new ArrayList<>();
    
    Set<String> keySet = ktblDataId.keySet();
    Iterator<String> pIt = keySet.iterator();

    while (pIt.hasNext()) {

      String key = pIt.next();
      localFileList.add(key);
      pids.add(ktblDataId.get(key));
      
      logger.debug("id " + ktblDataId.get(key));

      JsonFileLoader jFl = new JsonFileLoader();
      String jsonFileName = key;
      logger.debug("load file: " + jsonFileName);
      JSONObject ktblJSONObj = jFl.loadJsonObject(jsonFileName);

      if (ktblJSONObj.has("relatedDatasets")) {

        logger.debug("found related Datasets");
        ktblRelDat = ktblJSONObj.getJSONArray("relatedDatasets");
        
        logger.info("relatedDataset array has: " + ktblRelDat.length() + " items");
        logger.info("localFileList has: " + localFileList.size() + " items");

        Hashtable<String, String> related = null;
        
        Iterator<Object> kIt = ktblRelDat.iterator();

        int j = localFileList.size() -2 ;
        while (kIt.hasNext()) {

          if (localFileList != null && localFileList.size() > 0) {
            String prefLabel = kIt.next().toString();
            String id = client.getResouceUri() + ktblDataId.get(localFileList.get(j));

            related = new Hashtable<>();
            related.put("prefLabel", prefLabel);
            related.put("@id", id);
            relatedDataset.add(related);
            logger.debug("KeySet: " + localFileList.get(j) + ", " + j);
            logger.debug("@id : " + id);
            logger.debug("preLabel: " + prefLabel);

            j--;
          }
        }


      }

    }

    // Append relatedDataset and associatedDataset to ktblJSONObj
    // but prevent to add relation to itself

    for (int i = 0; i < localFileList.size(); i++) {

      String jsonFileName = localFileList.get(i);
      logger.info("load file: " + jsonFileName);
      
      JsonFileLoader jFl = new JsonFileLoader();
      JSONObject ktblJSONObj = jFl.loadJsonObject(jsonFileName);
      JSONArray tosRelDat = new JSONArray();
      JSONObject relDat = null;

      logger.info("relations count: " + relatedDataset.size());

        relDat = new JSONObject();
        relDat.put("prefLabel", "Parent publication");
        relDat.put("@id", client.getResouceUri() + ktblDataId.get(localFileList.get(localFileList.size() -1 )));
        tosRelDat.put(relDat);        

      for (int k = 0; k < relatedDataset.size(); k++) {
         if (relatedDataset.size() -1 - i !=  k) {
          relDat = new JSONObject();
          relDat.put("prefLabel", relatedDataset.get(k).get("prefLabel"));
          relDat.put("@id", relatedDataset.get(k).get("@id"));
          tosRelDat.put(relDat);
         } 
      }
      ktblJSONObj.put("relatedDatasets", tosRelDat);
      ktblJSONObj.put("associatedDataset", tosRelDat);
      
      logger.debug(ktblJSONObj.toString(2));

      FileUtil.saveStringToResultFile(jsonFileName, ktblJSONObj.toString(2));

    }

  }

}
