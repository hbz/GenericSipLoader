/**
 *  
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;

/**
 * @author aquast
 * 
 * Console-Starter of genericSipLoader Tool
 * 
 * Class is thought to allow usage of genericSipLoader by shell, i.E. for 
 * calling it by other scripts or crontab  
 *
 */
public class ConsoleImpl {

  final static Logger logger = LogManager.getLogger(ConsoleImpl.class);

  /**
   * @param args
   */
  public static void main(String[] args) {
    

    System.out.println("***---------------------- Console SiPLoader ----------------------------***");
    System.out.println("***        SiPLoader is part of the to.science library by hbz           ***");
    System.out.println("***             Loads your SIP into appropriate System                  ***");
    System.out.println("***---------------------------------------------------------------------***");
    System.out.println("");

    
    FileScanner fScan = null;
    String basePath = null;
    String user = null;
    String passwd = null;
    String target = null;
    if (args != null && args.length > 0) {
      target =  args[1];
      basePath = args[2];
      user = args[3];
      passwd = args[4];
      logger.info("You defined " + basePath + " as starting directory");
      logger.info("You defined " + target + " as repository to store data\n");

    }else {
      fScan = new FileScanner();
      fScan.processScan(".zip");
      Set<String> fList = fScan.getFileList();
      
      logger.debug("Zip-extraction starts with default settings");
      ZipExtractor extractor = new ZipExtractor(fList, basePath);
      extractor.extractZip();
    }
    
    if(target.equals("danrw")) {
      DipsLoaderImpl dLoader = new DipsLoaderImpl(basePath, user, passwd);
      dLoader.extractZips();
      Set<String> ieList = dLoader.scanIEs();
      dLoader.cuFedoraObject(ieList);
    }
    
    if(target.equals("ktbl")) {
    	// Constructor Initialize
        KtblLoaderImpl ktblLoader = new KtblLoaderImpl(basePath, user, passwd);
        // Unzipping the File
        ktblLoader.extractZips();
        Set<String> ieList = ktblLoader.scanIEs();
        ktblLoader.persistKtblRD(ieList);
      }
  }
    

}
