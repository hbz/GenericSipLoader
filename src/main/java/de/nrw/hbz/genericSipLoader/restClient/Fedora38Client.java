/**
 * 
 */
package de.nrw.hbz.genericSipLoader.restClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import de.nrw.hbz.genericSipLoader.util.PropertiesLoader;
import de.nrw.hbz.genericSipLoader.util.TimeStampProvider;
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
  final static Logger logger = LogManager.getLogger(Fedora38Client.class);

  private static Properties apiProps = new Properties();
  private String apiHost = null;
  private String user = null;
  private String passwd = null;
  public static String MANAGED = "M";
  public static String DSSTATE = "A";
  private Hashtable<String, String> apiConfig = new Hashtable<>();

	public Fedora38Client(String user, String passwd) {
		logger.debug("Fedora38Client constructor has been called.");
		this.user = user;
		this.passwd = passwd;
		setApi();
	}
	
	
	/**
	 * method loads properties from file
	 */
	private void loadProperties() {
	    InputStream propStream = null;

	    // Prüfen, ob die Datei im "Properties files" Ordner existiert
	    File propertiesFile = new File("Properties files/fedora-api.properties");
	    if (propertiesFile.exists()) {
	    	
	        try {
	            propStream = new FileInputStream(propertiesFile);
              apiProps.load(propStream);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        apiProps = new PropertiesLoader().getApiProperties();
	    }

	}

//	private void loadProperties() {
//		InputStream propStream = this.getClass().getClassLoader()
//				.getResourceAsStream("fedora-api.properties");
//		if (propStream != null) {
//			try {
//				apiProps.load(propStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Enumeration<Object> eProps = apiProps.keys();
//			while (eProps.hasMoreElements()) {
//				String key = (String) eProps.nextElement();
//				apiConfig.put(key, apiProps.getProperty(key));
//			}
//		}
//	}

	/**
	 * Configure API using properties loaded by loadProperties method
	 */
	private void setApi() {
		loadProperties();
		apiHost = apiProps.getProperty("protocol") + "://"
				+ apiProps.getProperty("host") + ":"
				+ apiProps.getProperty("port");
		logger.debug(apiHost);
	}

	/**
	 * @param objId
	 */
	@Deprecated
	public void getFedoraObject(String objId) {
		String endpoint = "fedora/objects";

		HttpAuthenticationFeature feature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newClient(new ClientConfig());
		client.register(feature);

		WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId);
		logger.debug(webTarget.getUri().toString());

		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.TEXT_HTML);
		Response response = invocationBuilder.get();
		logger.debug(response.getStatus());
	}

	/**
	 * Create a new empty Fedora Object in remote Fedora Repository
	 * 
	 * @param sourceId
	 * @return
	 */
	public String postFedoraObject(String sourceId) {
		String endpoint = "fedora/objects";

		String objId = setPidByEnvironment(apiProps.getProperty("namespace") + sourceId);
		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		// MultiPartFeature mpFeature = MultiPartFeature.
		Client client = ClientBuilder.newClient(new ClientConfig());
		client.register(basicAuthFeature);

		WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId);
		logger.debug(webTarget.getUri().toString());

		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.TEXT_HTML);
		Response response = invocationBuilder.post(null);
		logger.debug(response.getStatus());

		if (response.getStatus() != 201) {
			if (response.getStatus() == 500) {
				logger.warn("Fedora-Object: " + objId + "already exists");
			} else {
				logger.warn("remote creation of Fedora-Object: " + objId
						+ "failed");
			}
			objId = null;
		}

		return objId;
	}

	/**
	 * Add any XML Metadata file to remote Fedora Object
	 * 
	 * @param objId
	 * @param mdSchema
	 * @param xmlFile
	 */
	public void postXmlMetadataFile(String objId, String mdSchema,
			File xmlFile) {
		String endpoint = "fedora/objects";

		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newBuilder().register(basicAuthFeature)
				.register(MultiPartFeature.class).build();

		FileDataBodyPart filePart = new FileDataBodyPart("file", xmlFile);
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.bodyPart(filePart);

		WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId)
				.path("datastreams").path(mdSchema)
				.queryParam("controlGroup", "X")
				.queryParam("mimeType", filePart.getMediaType())
				.queryParam("dsState", "A")
				.queryParam("dsLabel", mdSchema + ".xml");
		logger.debug(webTarget.getUri().toString());

		Response response = webTarget.request()
				.post(Entity.entity(multipart, multipart.getMediaType()));
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
   *  Add any XML Metadata stream to remote Fedora Object
   *  
   * @param objId
   * @param mdSchema
   * @param xmlStream Method uses InputStream instead of File
   */
  public void postXmlMetadataStream(String objId, String mdSchema, InputStream xmlStream) {
    String endpoint = "fedora/objects";
    
    HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(user, passwd);
    Client client =  ClientBuilder.newBuilder().register(basicAuthFeature).register(MultiPartFeature.class).build();
    
    StreamDataBodyPart filePart = new StreamDataBodyPart("file", xmlStream);
    FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
    FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);

    WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId).path("datastreams").path(mdSchema)
        .queryParam("controlGroup", "X").queryParam("mimeType", filePart.getMediaType()).queryParam("dsState", "A")
        .queryParam("dsLabel", mdSchema);
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
	 * Add and persist any payload file to Fedora Object
	 * @param objId
	 * @param DSId
	 * @param plFile
	 */
	public void postPayLoadFile(String objId, String DSId, File plFile) {
		String endpoint = "fedora/objects";

		int index = plFile.getName().lastIndexOf("/");
		String fileName = plFile.getName().substring(index + 1,
				plFile.getName().length());
		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);

		Client client = ClientBuilder.newBuilder().register(basicAuthFeature)
				.register(MultiPartFeature.class).build();
		// System.out.println(fileName);

		FileDataBodyPart filePart = new FileDataBodyPart("file", plFile);
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		// FormDataMultiPart multipart = (FormDataMultiPart)
		formDataMultiPart.field("dsContent", plFile.getName())
				.bodyPart(filePart);

		// formDataMultiPart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);

		WebTarget webTarget = client.target(apiHost).path(endpoint).path(objId)
				.path("datastreams").path(DSId).queryParam("controlGroup", "M")
				.queryParam("mimeType", filePart.getMediaType())
				.queryParam("dsState", "A").queryParam("dsLabel", DSId);
		logger.debug(formDataMultiPart.getMediaType());
		logger.debug(filePart.getMediaType());

		Response response = webTarget.request().post(Entity
				.entity(formDataMultiPart, formDataMultiPart.getMediaType()));
		logger.debug(response.getStatus());

		try {
			// multipart.close();
			formDataMultiPart.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
  /**
   * Add new Relationship to RELS-EXT Stream of a fedora object
   * Using fedora API-M call
   * @param pid
   * @param subject
   * @param predicate
   * @param object
   */
  public void postRelationship(String pid, String subject, String predicate, String object) {
    String endpoint = "fedora/objects/" + pid + "/relationships/new";
    
    HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature.basic(user, passwd);
    //MultiPartFeature mpFeature = MultiPartFeature.
    Client client =  ClientBuilder.newClient(new ClientConfig());
    client.register(basicAuthFeature);
    
    WebTarget webTarget = client.target(apiHost).path(endpoint).queryParam("subject", subject).queryParam("predicate", predicate)
        .queryParam("object", object).queryParam("isLiteral", "false");
    logger.debug(webTarget.getUri().toString());
    
    Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_HTML);
    Response response = invocationBuilder.post(null);
    logger.debug(response.getStatus());

    if(response.getStatus()!=200) {
      if(response.getStatus()==500) {
        logger.warn("Server error");
      } else {
        logger.warn("Could not create new relationship");
      }
    }
  }
	
  private String setPidByEnvironment(String pid) {
    
    if(apiProps.containsKey("environment") && apiProps.getProperty("environment").equals("development")) {
      logger.info("Use development environment");
      pid = pid.substring(0, 7) + TimeStampProvider.getDTShort();
    }
      
    return pid;
  }


}
