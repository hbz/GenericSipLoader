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
   * Next methods call to.science.api via KtblClient Instance
   */
  
  /**
   * Creates an empty to.science Objects fedora representation of predefined model  
   * @param type model of to.science Object to generate. Can be Monograph, Article, ResearchData, File, Part, ...
   * @param parentId an optional fedora ResourceId (pid) from a related superior to.science Object.   
   * @return fedora ResourceId (pid) 
   */
  public String createToScienceObject(String type, String parentId) {
    return client.postToScienceObject(type, parentId);
  }

  /**
   * Adds metadata as N-Triples to the fedora representation of any to.science Object
   * @param pid the fedora ResourceId for the fedora representation
   * @param mdUri schema URI for the metadata schema 
   * @param mdString
   */
  @Deprecated
  public void addMetadataAsTriples(String pid, String mdUri, String mdString) {
    client.addMdAsTriple(pid, mdUri, mdString);
  }

  public void uploadFile(File file, String childId) {
    client.postFileStream(childId, file);
  }

  public void uploadJsonFile(File file, String parentId) {
    client.postJsonFile(parentId, file);
  }

  /*
   * public void addMetadataStream(String pid, String mdSchema, File file) {
   * KtblClient client = new KtblClient(user, passwd);
   * client.postXmlMetadataStream(pid, mdSchema, file); }
   * 
   * public void addPayLoadStream(String pid, int id, File file) { String DSId =
   * "DS" + id; Fedora38Client client = new Fedora38Client(user, passwd);
   * client.postPayLoadStream(pid, DSId, file); }
   */

  /**
   * create or update a parent ToScienceObject and one or more childs to upload
   * files
   * 
   * @param fList list of file names within a scanned directory
   */
  @Deprecated
  public void cuToScienceObject(Set<String> fList) {

    String parentId = null;
    // Set 1:  
    Iterator<String> fIt = ((TreeSet<String>) fList).descendingIterator();

    while (fIt.hasNext()) {
      String fileName = fIt.next();
      if(fileName.endsWith(".json")) {
        
        // create new empty ToScienceObject
        parentId = createToScienceObject("researchData", null);
        File jsonFile = new File(fileName);
        uploadJsonFile(jsonFile, parentId);
        jsonFile.delete();

        File zipFile = new File(fileName.replace(".json", ".zip"));
        if(zipFile.exists()) {
          String partId = createToScienceObject("file", parentId);
          uploadFile(zipFile, partId);
          zipFile.delete();
        }

        File xlsxFile = new File(fileName.replace(".json", ".xlsx"));
        if(xlsxFile.exists()) {
          String partId = createToScienceObject("file", parentId);
          uploadFile(xlsxFile, partId);
          xlsxFile.delete();
        }
        // try to remove parent directory
        File parentDir = Paths.get(fileName).getParent().toFile();
        File[] delFile = parentDir.listFiles();
        for(int i=0; i< delFile.length; i++) {
          delFile[i].delete();
        }
        
        parentDir.delete();
      }
      
      if(fileName.contains("SUPPORT")) {
        new File(fileName).delete();
      }      
    }
        
  }    
 
  
  
  /**
   * Method (enhanced from cuToScienceObject) aims to persist a complete ktbl ResearchData item into 
   * appropriate to.science Objects with respect to each objects relation to each other  
   * 
   * @param fList
   */
  public void persistKtblRD(Set<String> fList) {
      
    LinkedHashMap<String, String> ktblDataId = new LinkedHashMap<>();
    
    /*
     *     Sort iterator alphabetical descending
     *     As we have json files with alphabetical ordered dataset names, we can predict that 
     *     we create the fedora representations for the datasets first and finish with the superior ktblResearchData object.
     *     Due to the agreements with ktbl each Dataset by itself will be acting like an independent ktblResearchData. 
     *     Therefore we persist a ktblResearchData representation for each Dataset   
     */
    Iterator<String> fIt = ((TreeSet<String>) fList).descendingIterator();
    
    // Step 1: find json files and persist an empty ktblResearchData object for each one
    while (fIt.hasNext()) {

      String pId = null;
      String fileName = fIt.next();
      
      if(fileName.endsWith(".json")) {
        logger.info("persist new ktblResearchData object for " + fileName);

        // create new empty ToScienceObject
        pId = createToScienceObject("researchData", null);
        ktblDataId.put(fileName, pId);
        //File jsonFile = new File(fileName);
        //uploadJsonFile(jsonFile, parentId);
        //jsonFile.delete();
        
       }
    }
    
    // Step 2: Modify json files to add 
    logger.debug("look for associatedDataSets");

    addRelatededDataSetMD(ktblDataId);
    
    // Step 3: Upload modified json files into empty ktblResearchData objects  
    Set<String> kSet = ktblDataId.keySet();
    Iterator<String> kIt = kSet.iterator();
    
    while(kIt.hasNext()) {
      
      String fileName = kIt.next();
      String parentId = ktblDataId.get(fileName);
      File jsonFile = new File(fileName);
      uploadJsonFile(jsonFile, parentId);
      
      
      
      File zipFile = new File(fileName.replace(".json", ".zip"));
      if(zipFile.exists()) {
        String partId = createToScienceObject("file", parentId);
        uploadFile(zipFile, partId);
        zipFile.delete();
        
      }

      File xlsxFile = new File(fileName.replace(".json", ".xlsx"));
      if(xlsxFile.exists()) {
        String partId = createToScienceObject("file", parentId);
        uploadFile(xlsxFile, partId);
        xlsxFile.delete();
      }
      
    }
    
    // Last step: clean up the work directory
    FileUtil.removeWorkDir(basePath, "unpacked");
  }

  /**
   * @param ktblDataId
   */
  public void addRelatededDataSetMD(LinkedHashMap<String,String> ktblDataId) {

    Set<String> keySet = ktblDataId.keySet();
    Iterator<String> pIt = keySet.iterator();
    while(pIt.hasNext()) {
      
      String key = pIt.next();
      
      logger.debug("found " + ktblDataId.size() + " related Datasets");
      logger.debug("id " + ktblDataId.get(key));
      
      JsonFileLoader jFl = new JsonFileLoader();
      String jsonFileName = key;
      logger.info("load file: " + jsonFileName);
      JSONObject ktblJSONObj = jFl.loadJsonObject(jsonFileName);
      // System.out.println(ktblJSONObj.toString());
      
      if(ktblJSONObj.has("relatedDatasets")) {
      
        logger.debug("found related Datasets");
        JSONArray ktblRelDat = ktblJSONObj.getJSONArray("relatedDatasets");
        logger.info("array has: " + ktblRelDat.length() + " items");
        Iterator<Object> kIt = ktblRelDat.iterator();

        JSONArray tosRelDat = new JSONArray();
        ArrayList<String> resId = new ArrayList<>();
        Iterator<String> rIt = keySet.iterator();
        while(rIt.hasNext()) {
          String fName = rIt.next();
          resId.add(fName);
          logger.info("Key: " + fName);
          
        }

        int j = resId.size()-2;
        while(kIt.hasNext()) {
         JSONObject relDat = new JSONObject();
         if(resId != null && resId.size() > 0) {
           relDat.put("@id", client.getResouceUri() + ktblDataId.get(resId.get(j)));
           relDat.put("prefLabel", kIt.next().toString());
           tosRelDat.put(relDat);
           logger.info("KeySet: " + resId.get(j) + ", " + j);
           j--;
         }
        }
        
        ktblJSONObj.put("relatedDatasets", tosRelDat);
        ktblJSONObj.put("associatedDataset", tosRelDat);
        
        FileUtil.saveStringToResultFile(jsonFileName, ktblJSONObj.toString(2));
       
        System.out.println(ktblJSONObj.toString(2));
      }
    }
  }

  /**
   * This method checks if there are other file in the same folder where the json
   * file is located
   * 
   * @param fList           List all files after unzipping
   * @param currentFileName current Json file (TOS+KTBL)
   * @return Returns the other file in the folder where the Json file is.
   */
  private static File getFileInSameFolder(Set<String> fList, String currentFileName) {

    Path currentFileParentPath = Paths.get(currentFileName).getParent();
    for (String otherFileName : fList) {
      Path otherFileParentPath = Paths.get(otherFileName).getParent();
      if (currentFileParentPath.equals(otherFileParentPath) && !currentFileName.equals(otherFileName)) {
        return new File(otherFileName);
      }
    }
    return null;
  }
  
  

}
