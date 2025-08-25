/**
 * 
 */
package genericSipLoader;

import java.util.Properties;
import de.nrw.hbz.genericSipLoader.util.PropertiesLoader;
import de.nrw.hbz.genericSipLoader.util.md.AdHocUriProvider;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 * 
 */
public class TestAdHocProvider {

  @Test
  public void getApiProperties() {
    String propertiesFileName = "ktbl-api.properties";
    Properties apiProp = new PropertiesLoader(propertiesFileName).getApiProperties();
    System.out.println(apiProp.getProperty("host"));
    assertEquals("frl.publisso.de", apiProp.getProperty("host"));
  }
  
  @Test
  public void getAdHocUri() {
    AdHocUriProvider aHP = new AdHocUriProvider("ktbl");
    String adHocUri = aHP.encode("Goethe, Johann W.");
    System.out.println(adHocUri);
    assertEquals("https://frl.publisso.de/adhoc/uri/R29ldGhlLCBKb2hhbm4gVy4=", adHocUri);
  }


}
