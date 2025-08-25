/**
 * 
 */
package de.nrw.hbz.genericSipLoader.util.md;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Properties;

import de.nrw.hbz.genericSipLoader.util.PropertiesLoader;
/**
 * 
 */
public class AdHocUriProvider {
  
  private Properties apiProperties;
  
  public AdHocUriProvider(String apiName) {
    
    PropertiesLoader propertiesLoader = new PropertiesLoader(apiName + "-api.properties");
    propertiesLoader.getApiProperties();
    apiProperties = propertiesLoader.getApiProperties();
  }
  

  public String encode(String label) {
    
    String adHocUri = apiProperties.getProperty("protocol") 
        + "://"
        + apiProperties.getProperty("host") + "/adhoc/uri/";
    byte[] bytes = label.getBytes();
    
    Encoder encoder = Base64.getEncoder();
    String result = encoder.encodeToString(bytes);
    
    adHocUri = adHocUri + result;
    
    return adHocUri;
  }
}
