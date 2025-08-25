/**
 * 
 */
package de.nrw.hbz.genericSipLoader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class PropertiesLoader {

  final static Logger logger = LogManager.getLogger(PropertiesLoader.class);

  private static Properties apiProps = new Properties();
  private Hashtable<String, String> config = new Hashtable<>();

  public PropertiesLoader() {
    loadProperties();
  }

  public PropertiesLoader(String propertiesFileName) {
    loadProperties(propertiesFileName);
  }
  
  private void loadProperties() {
    InputStream propStream = null;

    File propertiesFile = new File("Properties files/ktbl-api.properties");
    if (propertiesFile.exists()) {
      try {
        propStream = new FileInputStream(propertiesFile);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      propStream = this.getClass().getClassLoader()
          .getResourceAsStream("ktbl-api.properties");
    }

    if (propStream != null) {
      try {
        apiProps.load(propStream);
      } catch (IOException e) {
        e.printStackTrace();
      }

      Enumeration<Object> eProps = apiProps.keys();
      while (eProps.hasMoreElements()) {
        String key = (String) eProps.nextElement();
        config.put(key, apiProps.getProperty(key));
      }
    }
  }
  
  private void loadProperties(String propertiesFileName) {
    InputStream propStream = null;

    propStream = this.getClass().getClassLoader()
          .getResourceAsStream(propertiesFileName);
    
    if (propStream != null) {
      try {
        apiProps.load(propStream);
      } catch (IOException e) {
        e.printStackTrace();
      }

      Enumeration<Object> eProps = apiProps.keys();
      while (eProps.hasMoreElements()) {
        String key = (String) eProps.nextElement();
        config.put(key, apiProps.getProperty(key));
      }
    }
  }

  public Hashtable<String, String> getProperties() {
    loadProperties();
    return config;
  }
  
  public Properties getApiProperties() {
    return apiProps;
  }
  
}
