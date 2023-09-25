package de.nrw.hbz.genericSipLoader.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZipExtractor {
  
  public ZipExtractor(Set<String> fList) {
    this.fList = fList;
    extractZip();
  }

  public ZipExtractor(Set<String> fList, String basePath) {
    this.fList = fList;
    this.extractionPath = basePath;
    extractZip();
  }

  final static Logger logger = LogManager.getLogger(ZipExtractor.class);
  private Set<String> fList = null;
  private String extractionPath = System.getProperty("user.dir");
  
  public void setTargetDir(String fileName) {
    String extLoc = fileName.replace(".zip",  "");
    System.out.println(extLoc);
    File extDir = new File(extLoc);
    extDir.mkdir();
    
  }
  
  public void extractZip() {
    Iterator<String> fListIt = fList.iterator();
    ZipFile zFile = null;
    
    while(fListIt.hasNext()) {
      try {
        zFile = new ZipFile(fListIt.next());
        
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      setTargetDir(zFile.getName());
      writeExtContent(zFile);
      
    }
  }
  
  private void writeExtContent(ZipFile zFile) {

    String extLoc = zFile.getName().replace(".zip",  "").replace(extractionPath + System.getProperty("file.separator"), "");
    logger.info("Filename: " + extLoc);
    
    Enumeration<? extends ZipEntry> zEnum = zFile.entries();
    while(zEnum.hasMoreElements()) {
      ZipEntry zEntry = zEnum.nextElement();
 
      //System.out.println("Methode: " + zEntry.getMethod());
      //System.out.println(zEntry.getName());  
      File zPart = new File(extractionPath + System.getProperty("file.separator") + zEntry.getName());
      if(extractionPath.equals(zPart.getParent())){
        zPart = new File(extractionPath + System.getProperty("file.separator") + extLoc + System.getProperty("file.separator") + zEntry.getName()); 
      }
       if(!zPart.getParentFile().isDirectory() && !zPart.exists()) {
          logger.info("\nCreate sub directories: " + zPart.getParentFile().toString() + "\n");
          zPart.getParentFile().mkdirs();
        }
        
        logger.debug(zPart.getAbsolutePath().toString());
        logger.debug(zEntry.getSize());

        if(zEntry.getSize() != 0) {
        InputStream zIs;
        try {
          FileOutputStream fos = new FileOutputStream(zPart);
          zIs = zFile.getInputStream(zEntry);
                    
          BufferedOutputStream bos = new BufferedOutputStream(fos);
          int i = -1;
          while((i = zIs.read()) != -1){
            bos.write(i);
          }
          bos.flush();
          fos.close();

          
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        };
        }

    }
    
  }
  
   
}
