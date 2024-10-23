/**
 * 
 */
package de.nrw.hbz.genericSipLoader.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

/**
 * 
 */
public class JsonFileLoader {

  final static Logger logger = LogManager.getLogger(JsonFileLoader.class);
  private FileInputStream jsonStream = null;
  
  private void loadJsonFile(File jsonFile) {

    try {
      jsonStream = new FileInputStream(jsonFile);

    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * @return the jsonStream
   */
  public FileInputStream getJsonStream() {
    return jsonStream;
  }
 
  public JSONObject loadJsonObject(String fileName) {

    JSONObject tosJSONObj = new JSONObject();
    
    loadJsonFile(new File(fileName));

    try {
      InputStreamReader jsonStreamReader = new InputStreamReader(jsonStream, "UTF-8");
      BufferedReader bReader = new BufferedReader(jsonStreamReader);
      StringBuilder jsonStringBuilder = new StringBuilder();

      String inputStr;
      while ((inputStr = bReader.readLine()) != null) {
        jsonStringBuilder.append(inputStr);
      }
      tosJSONObj = new JSONObject(jsonStringBuilder.toString());

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }

    return tosJSONObj;
  }

  
}
