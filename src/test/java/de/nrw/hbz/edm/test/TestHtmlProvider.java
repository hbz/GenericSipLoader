/**
 * 
 */
package de.nrw.hbz.edm.test;

import java.io.File;

import de.nrw.hbz.edm.impl.EdmProvider;
import de.nrw.hbz.edm.impl.HtmlProvider;
import de.nrw.hbz.edm.model.Rdf;

/**
 * 
 */
public class TestHtmlProvider {


  /**
   * @param args
   */
  public static void main(String[] args) {

    TestHtmlProvider test = new TestHtmlProvider();
    test.createHtmlOutPut();
    
  }

  
  public void createHtmlOutPut() {
    String filePath = System.getProperty("user.dir") 
        + System.getProperty("file.separator") + "src/test/resources/EDM.xml";
    File rdfResource = new File(filePath);
    Rdf rdf = EdmProvider.deserialize(rdfResource);
    HtmlProvider htmlProv = new HtmlProvider(rdf);
    System.out.println(htmlProv.toString());
  }

}
