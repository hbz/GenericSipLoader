/**
 * 
 */
package de.nrw.hbz.genericSipLoader.restClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author aquast
 *
 */
public class Fedora38Client {
  
  public Fedora38Client(String user, String passwd) {
    this.user = user;
    this.passwd = passwd;
    setApi();
  }
  final static Logger logger = LogManager.getLogger(Fedora38Client.class);
  
  private static Properties apiProps = new Properties();
  private String apiHost = null;
  private String user = null;
  private String passwd = null;
  public static String MANAGED = "M";
  public static String DSSTATE = "A";
  private Hashtable<String,String> apiConfig = new Hashtable<>();
  
  
  private void loadProperties() {
    InputStream propStream = this.getClass().getClassLoader().getResourceAsStream("fedora-api.properties");
    if(propStream != null) {
      try {
        apiProps.load(propStream);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      
      Enumeration<Object> eProps = apiProps.keys();
      while(eProps.hasMoreElements()) {
        String key = (String) eProps.nextElement();
        apiConfig.put(key, apiProps.getProperty(key));
      }
    }
  }
  
  private void setApi() {
    loadProperties();
    apiHost = apiProps.getProperty("protocol") + "://" + apiProps.getProperty("host") + ":"+ apiProps.getProperty("port");
    logger.debug(apiHost);
  }

  
  /**
   * @param objId
   */
  public void getFedoraObject(String objId) {
    String endpoint = "fedora/objects";
    
    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, passwd);
    Client client =  ClientBuilder.newClient(new ClientConfig());
    client.register(feature);
    
    WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId);
    logger.debug(webTarget.getUri().toString());
    
    Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_HTML);
    Response response = invocationBuilder.get();
    logger.debug(response.getStatus());    
  }
  
  /**
   * Create new empty Fedora Object in remote Fedora Repository
   * @param sourceId
   * @return
   */
  public String postFedoraObject(String sourceId) {
    String endpoint = "fedora/objects";
    
    String objId = apiConfig.get("namespace") + sourceId; 
    HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(user, passwd);
    //MultiPartFeature mpFeature = MultiPartFeature.
    Client client =  ClientBuilder.newClient(new ClientConfig());
    client.register(basicAuthFeature);
    
    WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId);
    logger.debug(webTarget.getUri().toString());
    
    Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_HTML);
    Response response = invocationBuilder.post(null);
    logger.debug(response.getStatus());

    if(response.getStatus()!=201) {
      if(response.getStatus()==500) {
        logger.warn("Fedora-Object: " + objId + "already exists");
      } else {
        logger.warn("remote creation of Fedora-Object: " + objId + "failed");
      }
      objId = null;
    }
    
    return objId;
  }
  
  /**
   * add Xml Metadata Stream to remote Fedora Object
   * @param objId
   * @param mdSchema
   * @param xmlFile
   */
  public void postXmlMetadataStream(String objId, String mdSchema, File xmlFile) {
    String endpoint = "fedora/objects";
    
    HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(user, passwd);
    Client client =  ClientBuilder.newBuilder().register(basicAuthFeature).register(MultiPartFeature.class).build();
    
    FileDataBodyPart filePart = new FileDataBodyPart("file", xmlFile);
    FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
    FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);

    WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId).path("datastreams").path(mdSchema)
        .queryParam("controlGroup", "X").queryParam("mimeType", filePart.getMediaType()).queryParam("dsState", "A")
        .queryParam("dsLabel", mdSchema + ".xml");
    logger.debug(webTarget.getUri().toString());
    
    Response response = webTarget.request().post(Entity.entity(multipart, multipart.getMediaType()));
    logger.debug(response.getStatus());
    
    try {
      formDataMultiPart.close();
      multipart.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * @param objId
   * @param DSId
   * @param plFile
   */
  public void postPayLoadStream(String objId, String DSId, File plFile) {
    String endpoint = "fedora/objects";
    
    int index = plFile.getName().lastIndexOf("/");
    String fileName = plFile.getName().substring(index + 1, plFile.getName().length());
    HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(user, passwd);
    
    Client client =  ClientBuilder.newBuilder().register(basicAuthFeature).register(MultiPartFeature.class).build();
    //System.out.println(fileName);
    
    FileDataBodyPart filePart = new FileDataBodyPart("file", plFile);
    FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
    //FormDataMultiPart multipart = (FormDataMultiPart) 
    formDataMultiPart.field("dsContent", plFile.getName()).bodyPart(filePart);
    
    
    //formDataMultiPart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
    
    WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId).path("datastreams").path(DSId)
        .queryParam("controlGroup", "M").queryParam("mimeType", filePart.getMediaType()).queryParam("dsState", "A")
        .queryParam("dsLabel", DSId);
    logger.debug(formDataMultiPart.getMediaType());
    logger.debug(filePart.getMediaType());
    
    
    Response response = webTarget.request().post(Entity.entity(formDataMultiPart, formDataMultiPart.getMediaType()));
    logger.debug(response.getStatus());
    
    try {
      //multipart.close();
      formDataMultiPart.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  
}
