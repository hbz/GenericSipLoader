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
 */
public class ConsoleImpl {

  final static Logger logger = LogManager.getLogger(ConsoleImpl.class);

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    FileScanner fScan = null;
    String basePath = null;
    String user = null;
    String passwd = null;
    String target = null;
    if (args != null && args.length > 0) {
      target =  args[0];
      basePath = args[1];
      user = args[2];
      passwd = args[3];
      logger.info("You defined " + basePath + " as starting directory");
      logger.info("You defined " + target + " as repository to store data\n");

    }else {
      fScan = new FileScanner();
      fScan.processScan(".zip");
      Set<String> fList = fScan.getFileList();
      
      logger.info("\nZip-extraction starts with default settings\n");
      ZipExtractor extractor = new ZipExtractor(fList, basePath);
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
