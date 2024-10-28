/**
 * 
 */
package de.nrw.hbz.genericSipLoader.restClient;

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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.json.JSONObject;

import com.google.gson.Gson;

import de.nrw.hbz.genericSipLoader.model.ResponseObject;
import de.nrw.hbz.genericSipLoader.model.ToScienceObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author aquast
 *
 */
public class KtblClient {

  final static Logger logger = LogManager.getLogger(KtblClient.class);

  private static Properties apiProps = new Properties();
  private String apiHost = null;
  private String user = null;
  private String passwd = null;
  public static String MANAGED = "M";
  public static String DSSTATE = "A";
  public static String PRIVATE = "private";
  public static String PUBLIC = "public";
  private Hashtable<String, String> apiConfig = new Hashtable<>();

	public KtblClient(String user, String passwd) {
		this.user = user;
		this.passwd = passwd;
		setApi();
	}
	private void loadProperties() {
		InputStream propStream = null;

		// Prüfen, ob die Datei im "Properties files" Ordner existiert
		File propertiesFile = new File("Properties files/ktbl-api.properties");
		if (propertiesFile.exists()) {
			System.out.println(
					"propertiesFile.exists()=" + propertiesFile.exists());
			try {
				propStream = new FileInputStream(propertiesFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			// Wenn die Datei nicht im "Properties files" Ordner gefunden wurde,
			// suche sie im Classpath
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
				apiConfig.put(key, apiProps.getProperty(key));
			}
		}
	}

	// private void loadProperties() {
	// InputStream propStream = this.getClass().getClassLoader()
	// .getResourceAsStream("ktbl-api.properties");
	//
	// if (propStream != null) {
	// try {
	// apiProps.load(propStream);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// Enumeration<Object> eProps = apiProps.keys();
	// while (eProps.hasMoreElements()) {
	// String key = (String) eProps.nextElement();
	// apiConfig.put(key, apiProps.getProperty(key));
	// }
	// }
	// }

	/**
	 * Configure remote API 
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
	public void getToScienceObject(String namespace) {
		String endpoint = "resource";

		HttpAuthenticationFeature feature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newClient(new ClientConfig());
		client.register(feature);
		WebTarget webTarget = client.target(apiHost).path(endpoint)
				.path(namespace);
		logger.debug(webTarget.getUri().toString());

		Response response = webTarget.request().get();
		logger.debug(response.getStatus());
	}

	/**
	 * Create a Parent or a Child to.science Object in remote to.science
	 * fedora repository
	 * 
	 * @param sourceId
	 * @return
	 */
	public String postToScienceObject(String type, String parentIdOrNull) {
		String endpoint = "resource";
		String pid = null;
		String namespace = apiConfig.get("namespace");
		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newClient(new ClientConfig());
		client.register(basicAuthFeature);

		WebTarget webTarget = client.target(apiHost).path(endpoint)
				.path(namespace);
		logger.debug("webTarget=" + webTarget.toString());
		ToScienceObject obj = new ToScienceObject();
		obj.setAccessScheme(PRIVATE);
		obj.setPublishScheme(PRIVATE);
		obj.setContentType(type);
		obj.setParentPid(parentIdOrNull);

		Gson gson = new Gson();
		String json = gson.toJson(obj);

		Response response = webTarget.request()
				.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		logger.debug("response: " + response.toString());

		String responseBody = response.readEntity(String.class);
		ResponseObject responseObj = gson.fromJson(responseBody,
				ResponseObject.class);

		logger.debug("responseObj=" + responseObj.toString());

		if (response.getStatus() == 200) {
			pid = responseObj.getText().split(" ")[0];
		}
		return pid;
	}

	/**
	 * @param Pid
	 * @param MdString
	 */
	public void addMdAsTriple(String Pid, String MdUri, String MdString) {

		String pid = Pid;
		String mdString = "Dummy-Titel";
		String mdUri = "http://purl.org/dc/terms/title";
		if (MdString != null) {
			mdString = MdString;
		}

		if (MdUri != null) {
			mdUri = MdUri;
		}

		String endpoint = "resource";
		logger.info(pid);
		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newClient(new ClientConfig());
		client.register(basicAuthFeature);
		String mdTriple = "<" + pid + "> <" + mdUri + "> \"" + mdString
				+ "\" .";
		logger.debug(mdTriple);

		WebTarget webTarget = client.target(apiHost).path(endpoint).path(pid)
				.path("metadata2");
		Response response = webTarget.request()
				.put(Entity.entity(mdTriple, MediaType.TEXT_PLAIN));
		logger.info(response.getStatus());

	}

	/**
	 * add Xml Metadata Stream to remote Fedora Object
	 * 
	 * @param objId
	 * @param mdSchema
	 * @param xmlFile
	 */
	public void postFileStream(String childId, File file) {
		String endpoint = "resource";

		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newBuilder().register(basicAuthFeature)
				.register(MultiPartFeature.class).build();

		FileDataBodyPart filePart = new FileDataBodyPart("data", file,
				MediaType.APPLICATION_OCTET_STREAM_TYPE);

		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.bodyPart(filePart);

		WebTarget webTarget = client.target(apiHost).path(endpoint)
				.path(childId).path("data");
		logger.debug("webTarget: " + webTarget.toString());

		Response response = webTarget.request()
				.post(Entity.entity(multipart, multipart.getMediaType()));
		logger.debug("Status File Upload: " + response.getStatus());

		try {
			formDataMultiPart.close();
			multipart.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method loads a file (Json file) via Api call to the updateKtblAndTos
	 * endpoint
	 * 
	 * @param The
	 *            json file is assigned a separate resource as a parent resource
	 * @param file
	 *            Json file which contains TOSCIENCE and KTBL metadata
	 */
	public void postJsonFile(String parentId, File file) {
		
		logger.debug("PostJsonFile has been called");
		
		String endpoint = "resource";

		HttpAuthenticationFeature basicAuthFeature = HttpAuthenticationFeature
				.basic(user, passwd);
		Client client = ClientBuilder.newBuilder().register(basicAuthFeature)
				.register(MultiPartFeature.class).build();

		FileDataBodyPart filePart = new FileDataBodyPart("data", file,
				MediaType.APPLICATION_OCTET_STREAM_TYPE);

		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();

		FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
				.bodyPart(filePart);

		WebTarget webTarget = client.target(apiHost).path(endpoint)
				.path(parentId).path("ktbl");
		
		Response response = webTarget.request()
				.put(Entity.entity(multipart, multipart.getMediaType()));

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201) {
			logger.debug("File " + file.getName() + " successfully uploaded");
		} else {

			logger.warn(" Failed to upload Json file" + file.getName());

			// JOptionPane.showMessageDialog(null, "Failed to upload Json file",
			// "Report", JOptionPane.INFORMATION_MESSAGE);
		}

		try {
			formDataMultiPart.close();
			multipart.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getApiUrl() {
	  return apiHost;
	}

	/**
	 * @param objId
	 * @param DSId
	 * @param plFile
	 *
	 *            public void postPayLoadStream(String objId, String DSId, File
	 *            plFile) { String endpoint = "fedora/objects";
	 * 
	 *            int index = plFile.getName().lastIndexOf("/"); String fileName
	 *            = plFile.getName().substring(index + 1,
	 *            plFile.getName().length()); HttpAuthenticationFeature
	 *            basicAuthFeature = HttpAuthenticationFeature.basic(user,
	 *            passwd);
	 * 
	 *            Client client =
	 *            ClientBuilder.newBuilder().register(basicAuthFeature).register(MultiPartFeature.class).build();
	 *            //System.out.println(fileName);
	 * 
	 *            FileDataBodyPart filePart = new FileDataBodyPart("file",
	 *            plFile); FormDataMultiPart formDataMultiPart = new
	 *            FormDataMultiPart(); //FormDataMultiPart multipart =
	 *            (FormDataMultiPart) formDataMultiPart.field("dsContent",
	 *            plFile.getName()).bodyPart(filePart);
	 * 
	 * 
	 *            //formDataMultiPart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
	 * 
	 *            WebTarget webTarget =
	 *            client.target(apiHost).path(endpoint).path(objId).path("datastreams").path(DSId)
	 *            .queryParam("controlGroup", "M").queryParam("mimeType",
	 *            filePart.getMediaType()).queryParam("dsState", "A")
	 *            .queryParam("dsLabel", DSId);
	 *            logger.debug(formDataMultiPart.getMediaType());
	 *            logger.debug(filePart.getMediaType());
	 * 
	 * 
	 *            Response response =
	 *            webTarget.request().post(Entity.entity(formDataMultiPart,
	 *            formDataMultiPart.getMediaType()));
	 *            logger.debug(response.getStatus());
	 * 
	 *            try { //multipart.close(); formDataMultiPart.close(); } catch
	 *            (IOException e) { // TODO Auto-generated catch block
	 *            e.printStackTrace(); } }
	 */

}
