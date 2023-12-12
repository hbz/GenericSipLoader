/**
 * 
 */
package de.nrw.hbz.genericSipLoader.impl;

import java.io.File;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.genericSipLoader.restClient.KtblClient;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author aquast
 *
 */
public class KtblLoaderImpl {

	public KtblLoaderImpl(String basePath, String user, String passwd) {
		this.basePath = basePath;
		this.user = user;
		this.passwd = passwd;
	}

	final static Logger logger = LogManager.getLogger(KtblLoaderImpl.class);
	private String basePath = System.getProperty("user.dir");
	private String user = null;
	private String passwd = null;
	private Hashtable<String, String> parts = new Hashtable<>();

	public void extractZips() {

		FileScanner fScan = new FileScanner(basePath);
		fScan.processScan(".zip");
		Set<String> fList = fScan.getFileList();

		logger.info("\nZip-extraction starts\n");

		// comment zipextractor for testing, uncomment for production
		ZipExtractor extractor = new ZipExtractor(fList, basePath);

	}

	public Set<String> scanIEs() {
		FileScanner fScan = new FileScanner(basePath);
		fScan.processScan();
		Set<String> ieList = fScan.getFileList();
		return ieList;
	}

	public String createToScienceObject(String type, String parentId) {
		KtblClient client = new KtblClient(user, passwd);
		return client.postToScienceObject(type, parentId);
	}

	public void addMetadataAsTriples(String pid, String mdUri,
			String mdString) {
		KtblClient client = new KtblClient(user, passwd);
		client.addMdAsTriple(pid, mdUri, mdString);
	}

	public void uploadFile(File file, String childId) {
		KtblClient client = new KtblClient(user, passwd);
		client.postFileStream(childId, file);
	}
	public void uploadJsonFile(File file, String parentId) {
		KtblClient client = new KtblClient(user, passwd);
		client.postJsonFile(parentId, file);
	}
	/*
	 * public void addMetadataStream(String pid, String mdSchema, File file) {
	 * KtblClient client = new KtblClient(user, passwd);
	 * client.postXmlMetadataStream(pid, mdSchema, file); }
	 * 
	 * public void addPayLoadStream(String pid, int id, File file) { String DSId
	 * = "DS" + id; Fedora38Client client = new Fedora38Client(user, passwd);
	 * client.postPayLoadStream(pid, DSId, file); }
	 */
	/**
	 * create or update a parent ToScienceObject and one or more childs to
	 * upload files
	 * 
	 * @param fList
	 */
	public void cuToScienceObject(Set<String> fList) {

		Iterator<String> fIt = fList.iterator();
		while (fIt.hasNext()) {
			String fileName = fIt.next();
			logger.info("FileName: " + fileName);

			if (fileName.endsWith(".json")) {
				// create new empty ToScienceObject
				String parentId = createToScienceObject("researchData", null);
				// Upload of the Json-File so that 2 datastreams KTBL and
				// TOSCIENCE will be persisted.
				uploadJsonFile(new File(fileName), parentId);
				File otherFile = getFileInSameFolder(fList, fileName);
				if (otherFile != null) {
					String partId = createToScienceObject("part", parentId);
					uploadFile(otherFile, partId);
				}
			}
		}
	}

	/**
	 * This method checks if there are other file in the same folder where the
	 * json file is located
	 * @param fList List all files after unzipping
	 * @param currentFileName current Json file (TOS+KTBL)
	 * @return Returns the other file in the folder where the Json file is.
	 */
	private static File getFileInSameFolder(Set<String> fList,
			String currentFileName) {

		Path currentFileParentPath = Paths.get(currentFileName).getParent();
		for (String otherFileName : fList) {
			Path otherFileParentPath = Paths.get(otherFileName).getParent();
			if (currentFileParentPath.equals(otherFileParentPath)
					&& currentFileName != otherFileName) {
				return new File(otherFileName);
			}
		}
		return null;
	}

}
