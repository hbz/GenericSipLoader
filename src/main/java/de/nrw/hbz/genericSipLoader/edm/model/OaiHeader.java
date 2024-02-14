package de.nrw.hbz.genericSipLoader.edm.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OaiHeader {

  private String identifier = null;
  private String datestamp = null;
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
  
  public String generateTimestamp() {
    Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());
    return sdf.format(timestamp);
    
  }
  
  public String generateUuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  // Getters
  /**
   * @return the identifier
   */
  public String getIdentifier() {
    identifier = generateUuid();
    return identifier;
  }
  /**
   * @return the datestamp
   */
  public String getDatestamp() {
    datestamp = generateTimestamp();
    return datestamp;
  }
  
  // Setters
  /**
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
  /**
   * @param datestamp the datestamp to set
   */
  public void setDatestamp(String datestamp) {
    this.datestamp = datestamp;
  }
  
}
