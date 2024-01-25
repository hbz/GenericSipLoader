package de.nrw.hbz.genericSipLoader.util.fedora;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelsExtBuilder {

  final static Logger logger = LogManager.getLogger(RelsExtBuilder.class);

  private String relsext = new String("\n<rdf:RDF xmlns:rdf=\"http://www.3w.org/1999/02/22-rdf-syntax-ns#\">" +
      "\n<rdf:Description rdf:about=\"info:fedora/_pid\">"      
      + "\n\t<identifier xmlns=\"http://openarchives.org/OAI/2.0/\" rdf:resource=\"oai:_pid\"></identifier>");
  private String footer = new String("</rdf:Description>\n</rdf:RDF>");
  private String pid = null;
  private String result = null;

  private List<String> memberOf = new ArrayList<>();

  public RelsExtBuilder(String Pid) {
    pid = Pid;
    result = generateRelsExt();
    
  }
  
  private String generateRelsExt() {
    String reStr = null;
    StringBuffer reSB = new StringBuffer();
    reStr = relsext.replaceAll("_pid", pid);
    reSB.append(reStr);
    for(int i=0; i<memberOf.size(); i++) {
      reSB.append(memberOf.get(i));
    }
    reSB.append(footer);
    logger.debug(reSB.toString());
    
    return reSB.toString();
  }
  
  public InputStream getInputStream() {
    ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes());
    InputStream is = new BufferedInputStream(bais);
    return is;
  }
  
  
  public File getFile(String path) {
    File file = new File(path + System.getProperty("file.separator") + "RELS-EXT.xml");
    try {
      FileOutputStream fos = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      bos.write(result.getBytes("UTF-8"));

      bos.flush();
      fos.close();

      
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return file;
    
  }
}
