package de.nrw.hbz.genericSipLoader.gui;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.awt.event.ActionEvent;
import de.nrw.hbz.genericSipLoader.impl.DipsLoaderImpl;
import de.nrw.hbz.genericSipLoader.impl.KtblLoaderImpl;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.FileUtil;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
/**
 * 
 * @author adoud
 *
 */
public class MainGui {
	final static Logger logger = LogManager.getLogger(MainGui.class);
	
	private final String LEDIT =  "xdg-open";
  private final String WEDIT =  "notepad.exe";
  private final String MEDIT =  "open -t";

	private JFrame frmGenericsiploader;
	private JTextField textFieldZipFile, textFieldName;
	private JLabel lblApiProperties, lbApiUser, lblApiPassword, lblZipFile,
			lblApiCreditianls;
	private JRadioButton rdbtnDanrw, rdbtnKtbl;
	private JTextArea textAreaApiProperties = null;
	// DANRW | KTBL
	private String radButtonMetadata = "ktbl";
	private JPasswordField passwordField;
	private JButton btnBrowse, btnReset, btnOk, btnEditProperties;
	private Path configProperitesPath = Paths
			.get(System.getProperty("user.dir"), "Properties files");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		logger.info("MainGui has been called.");
		// System.out.println(System.getProperty("user.dir"));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frmGenericsiploader.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public MainGui() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		System.getProperty("user.dir");

		initialize();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		checkAndCopyPropertiesFiles();
		frmGenericsiploader = new JFrame("GenericSipLoader");
		frmGenericsiploader.getContentPane().setBackground(SystemColor.info);
		frmGenericsiploader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGenericsiploader.getContentPane().setLayout(null);
		frmGenericsiploader.setSize(491, 420);
		frmGenericsiploader.setResizable(false);
		// Das Frame so einstellen, dass er in der Mitte des Bildschirms
		// erscheint
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		int x = (int) ((screenSize.getWidth() - frmGenericsiploader.getWidth())
				/ 2);
		int y = (int) ((screenSize.getHeight()
				- frmGenericsiploader.getHeight()) / 2);
		frmGenericsiploader.setLocation(x, y);

		// ********************************textField**************************************

		textFieldZipFile = new JTextField("");
		textFieldZipFile.setBounds(81, 111, 274, 26);
		frmGenericsiploader.getContentPane().add(textFieldZipFile);
		textFieldZipFile.setColumns(10);

		textFieldName = new JTextField("");
		textFieldName.setColumns(10);
		textFieldName.setBounds(81, 37, 274, 26);
		frmGenericsiploader.getContentPane().add(textFieldName);

		// ********************************JPasswordField**************************************

		passwordField = new JPasswordField();
		passwordField.setBounds(81, 77, 274, 26);
		frmGenericsiploader.getContentPane().add(passwordField);
		passwordField.setText(null);

		// ********************************JPasswordField**************************************

		// ********************************JLabels******************************************

		lblApiProperties = new JLabel("KTBL-Api.properties:");
		lblApiProperties.setBackground(new Color(255, 255, 255));
		lblApiProperties.setFont(new Font("Arial", Font.BOLD, 12));
		lblApiProperties.setForeground(new Color(218, 165, 32));
		lblApiProperties.setBounds(10, 195, 181, 26);
		frmGenericsiploader.getContentPane().add(lblApiProperties);

		lbApiUser = new JLabel("User");
		lbApiUser.setFont(new Font("Arial", Font.PLAIN, 11));
		lbApiUser.setBounds(10, 37, 46, 26);
		frmGenericsiploader.getContentPane().add(lbApiUser);

		lblApiPassword = new JLabel("Password");
		lblApiPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		lblApiPassword.setBounds(10, 74, 70, 26);
		frmGenericsiploader.getContentPane().add(lblApiPassword);

		lblZipFile = new JLabel("Zip File");
		lblZipFile.setFont(new Font("Arial", Font.PLAIN, 11));
		lblZipFile.setBounds(10, 111, 46, 26);
		frmGenericsiploader.getContentPane().add(lblZipFile);

		lblApiCreditianls = new JLabel("Api Creditianls:");
		lblApiCreditianls.setForeground(new Color(218, 165, 32));
		lblApiCreditianls.setFont(new Font("Arial", Font.BOLD, 12));
		lblApiCreditianls.setBackground(Color.WHITE);
		lblApiCreditianls.setBounds(10, 11, 181, 26);
		frmGenericsiploader.getContentPane().add(lblApiCreditianls);

		// ********************************JLabels******************************************

		// ********************************JTextArea**************************************
		textAreaApiProperties = new JTextArea();
		textAreaApiProperties.setEditable(false);
		textAreaApiProperties.setWrapStyleWord(true);
		textAreaApiProperties.setLineWrap(true);
		textAreaApiProperties.setBackground(Color.WHITE);
		textAreaApiProperties.setBounds(10, 219, 296, 153);
		frmGenericsiploader.getContentPane().add(textAreaApiProperties);
		loadContentOfProperitesFileInTextArea("ktbl-api.properties",
				textAreaApiProperties);
		textAreaApiProperties.getDocument();

		// ********************************JTextArea**************************************

		// ********************************JRadioButton**************************************
		rdbtnDanrw = new JRadioButton("DANRW");
		rdbtnDanrw.setEnabled(false);
		rdbtnDanrw.setBackground(new Color(240, 230, 140));
		rdbtnDanrw.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnDanrw.setBounds(10, 168, 70, 20);
		frmGenericsiploader.getContentPane().add(rdbtnDanrw);

		rdbtnKtbl = new JRadioButton("KTBL");
		rdbtnKtbl.setSelected(true);
		rdbtnKtbl.setBackground(new Color(240, 230, 140));
		rdbtnKtbl.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnKtbl.setBounds(90, 168, 70, 20);

		frmGenericsiploader.getContentPane().add(rdbtnKtbl);

		// Nur ein JRadioButton kann gleichzeitig ausgewählt werden
		ActionListener radioButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
				if (selectedRadioButton == rdbtnKtbl) {
					radButtonMetadata = "ktbl";
					lblApiProperties.setText("KTBL-Api.properties:");
					rdbtnDanrw.setSelected(false);
					textAreaApiProperties.setText(null);
					loadContentOfProperitesFileInTextArea("ktbl-api.properties",
							textAreaApiProperties);
				}
				// else if (selectedRadioButton == rdbtnDanrw) {
				// radButtonMetadata = "danrw";
				// rdbtnKtbl.setSelected(false);
				// lblApiProperties.setText("DANRW-Api.properties:");
				// textAreaApiProperties.setText(null);
				// loadContentOfProperitesFileInTextArea(
				// "fedora-api.properties", textAreaApiProperties);
				// }
				// if (!rdbtnKtbl.isSelected() && !rdbtnDanrw.isSelected()) {
				// radButtonMetadata = "ktbl";
				// rdbtnDanrw.setSelected(false);
				//
				// textAreaApiProperties.setText(null);
				// lblApiProperties.setText("KTBL-Api.properties:");
				// loadContentOfProperitesFileInTextArea(
				// "fedora-api.properties", textAreaApiProperties);
				// }
			}
		};
	//	rdbtnDanrw.addActionListener(radioButtonListener);
		rdbtnKtbl.addActionListener(radioButtonListener);

		// ********************************JRadioButton**************************************

		// ********************************JButtons**************************************
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Arial", Font.PLAIN, 11));
		btnReset.setBounds(316, 344, 142, 26);
		frmGenericsiploader.getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleResetButtonClick();
			}
		});

		btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Arial", Font.PLAIN, 11));
		btnBrowse.setBounds(365, 111, 93, 26);
		frmGenericsiploader.getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(frmGenericsiploader);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					textFieldZipFile.setText(selectedFile.getAbsolutePath());
				}
			}
		});

		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Arial", Font.PLAIN, 11));
		btnOk.setBounds(316, 219, 142, 51);
		frmGenericsiploader.getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleOKButtonClick();
			}
		});

		btnEditProperties = new JButton("Edit Properties File");
		btnEditProperties.setFont(new Font("Arial", Font.PLAIN, 11));
		btnEditProperties.setBounds(316, 301, 142, 32);
		frmGenericsiploader.getContentPane().add(btnEditProperties);

		btnEditProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					editPropertiesFile(radButtonMetadata);
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}
			}
		});

		// ********************************JButtons**************************************
	}

	private void handleResetButtonClick() {
		radButtonMetadata = "ktbl";
		textFieldName.setText(null);
		passwordField.setText(null);
		textFieldZipFile.setText(null);
		rdbtnDanrw.setSelected(false);
		rdbtnKtbl.setSelected(true);
		textAreaApiProperties.setText(null);
		lblApiProperties.setText("KTBL-Api.properties:");
		loadContentOfProperitesFileInTextArea("ktbl-api.properties",
				textAreaApiProperties);
	}

	private void handleOKButtonClick() {
		FileScanner fScan = null;
		String basePath = null;
		String user = null;
		String passwd = null;
		String target = null;
		try {
			target = radButtonMetadata;
			logger.debug("target=" + target);
			if (areFieldsFilled()) {
				basePath = Paths.get(textFieldZipFile.getText()).getParent()
						.toString();
				logger.debug("basePath=" + basePath);
				user = textFieldName.getText();
				passwd = new String(passwordField.getPassword());

			} else {
				fScan = new FileScanner();
				fScan.processScan(".zip");
				Set<String> fList = fScan.getFileList();
				ZipExtractor extractor = new ZipExtractor(fList, basePath);
				extractor.extractZip();
			}
			if (target.equals("danrw") && areFieldsFilled()) {
				logger.debug("danrw block has been called");
				DipsLoaderImpl dLoader = new DipsLoaderImpl(basePath, user,
						passwd);
				dLoader.extractZips();
				Set<String> ieList = dLoader.scanIEs();
				dLoader.cuFedoraObject(ieList);
			}
			if (target.equals("ktbl") && areFieldsFilled()) {
				KtblLoaderImpl ktblLoader = new KtblLoaderImpl(basePath, user,
						passwd);
				ktblLoader.extractZips();
				Set<String> ieList = ktblLoader.scanIEs();
        ktblLoader.persistKtblRD(ieList);
			}
			showMessage("Report", "Uploading is finished!");
			handleResetButtonClick();
		} catch (Exception e) {
			e.printStackTrace();
		  showMessage("Warning", "Please check your entries !!!");
		}

	}

	public boolean isTextAreaEdited(JTextArea textArea, String metadata)
			throws UnsupportedEncodingException {
		Properties properties = getProperitesFile(metadata);
		String propertiesContent = propertiesToString(properties)
				.replaceAll("\\s", "");
		String textAreaContent = textArea.getText().replaceAll("\\s", "");
		if (propertiesContent.equals(textAreaContent)) {
			return false;
		}
		return true;
	}

	private static String propertiesToString(Properties properties) {
		StringBuilder sb = new StringBuilder();
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			sb.append(key).append("=").append(value)
					.append(System.lineSeparator());
		}
		return sb.toString().trim();
	}

	public void loadContentOfProperitesFileInTextArea(String metadataName,
			JTextArea textArea) {
		if (metadataName.equals("danrw")) {
			metadataName = "fedora-api.properties";
		}

		textArea.setText(null);
		textArea.setText(readFileContent(metadataName));
	}

	public Properties getProperitesFile(String metadataName) {
		InputStream inputStream = null;
		if (metadataName.equals("danrw")) {
			metadataName = "fedora-api.properties";
		}
		Properties properties = new Properties();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			inputStream = classLoader
					.getResourceAsStream(metadataName + "-api.properties");

			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			showMessage("Warning",
					metadataName + "-api.properties" + " file not found ");
		}
		return null;
	}

	public String readFileContent(String metadataName) {
		String result = null;
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(
					configProperitesPath.toString() + "/" + metadataName);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream));
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}
			result = content.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public boolean areFieldsFilled() {
		String text1 = textFieldZipFile.getText();
		String text2 = textFieldName.getText();
		char[] passwordChar = passwordField.getPassword();
		return !text1.isEmpty() && !text2.isEmpty() && passwordChar.length != 0;
	}

	private void editPropertiesFile(String metadataName)
			throws InterruptedException {
		String editorCommand;
		if (metadataName.equals("danrw")) {
			metadataName = "fedora";
		}

		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			editorCommand = WEDIT;
		} else if (os.contains("mac")) {
			editorCommand = MEDIT;
		} else if (os.contains("nix") || os.contains("nux")) {
			editorCommand = LEDIT;
		} else {
			showMessage("Error", "Unsupported operating system.");
			return;
		}

		try {
			// Zielverzeichnis fuer Properties-Dateien
			Path targetDirectory = Paths.get(System.getProperty("user.dir"),
					"Properties files");

			// Pruefen, ob der Ordner bereits vorhanden ist
			if (!Files.exists(targetDirectory)) {
				showMessage("Error", "Properties files folder does not exist.");
				return;
			}

			// Pfad zur Properties-Datei im Zielverzeichnis
			Path targetFilePath = targetDirectory
					.resolve(metadataName + "-api.properties");

			// Properties-Datei aus dem Classpath laden
			InputStream inputStream = MainGui.class.getResourceAsStream(
					"/" + metadataName + "-api.properties");

			ProcessBuilder processBuilder = null;
			
			// Properties-Datei mit dem Standardeditor öffnen
			File file = targetFilePath.toFile();
			if(file!=null && file.isFile()) {
	      secureProcessBuilder(editorCommand, file);			  
			}


			Process process = processBuilder.start();
			// Warten auf das Beenden des Editors
			int exitCode = process.waitFor();
			// Editor geschlossen
			if (exitCode == 0) {
				// refresh textAreaApiProperties
				loadContentOfProperitesFileInTextArea(
						metadataName + "-api.properties",
						textAreaApiProperties);
			} else {
				showMessage("Error", "Error when editing the file");
			}

		} catch (IOException e) {
			showMessage("Error", "Error when editing the file");
		}
	}
	private void checkAndCopyPropertiesFiles() {
		try {
			// Zielverzeichnis fuer Properties-Dateien
			Path targetDirectory = Paths.get(System.getProperty("user.dir"),
					"Properties files");

			// Pruefen, ob der Ordner bereits vorhanden ist
			if (!Files.exists(targetDirectory)) {
				// Ordner erstellen
				Files.createDirectory(targetDirectory);

				// Pfad zum Verzeichnis mit den Properties-Dateien
				String resourceDirectory;
				File jarFile = new File(MainGui.class.getProtectionDomain()
						.getCodeSource().getLocation().toURI().getPath());
				if (jarFile.isFile()) {
					// Die Anwendung wird als JAR-Datei ausgeführt
					resourceDirectory = "/";
					java.util.jar.JarFile jar = new java.util.jar.JarFile(
							jarFile);
					java.util.Enumeration<java.util.jar.JarEntry> entries = jar
							.entries();

					while (entries.hasMoreElements()) {
						java.util.jar.JarEntry entry = entries.nextElement();

						String entryName = entry.getName();
						if (entryName.endsWith(".properties")) {
							Path targetFilePath = targetDirectory
									.resolve(entryName.substring(
											resourceDirectory.length()));

							try (InputStream inputStream = MainGui.class
									.getResourceAsStream(entryName);
									OutputStream outputStream = new FileOutputStream(
											targetFilePath.toFile())) {
								byte[] buffer = new byte[1024];
								int bytesRead;
								while ((bytesRead = inputStream
										.read(buffer)) != -1) {
									outputStream.write(buffer, 0, bytesRead);
								}
							}
						}
					}
					jar.close();
				} else {
					// Die Anwendung wird im Entwicklungsmodus(Eclipse)
					// ausgeführt
					resourceDirectory = "src/main/resources/";
					try (DirectoryStream<Path> directoryStream = Files
							.newDirectoryStream(Paths.get(resourceDirectory))) {
						for (Path entry : directoryStream) {
							if (entry.getFileName().toString()
									.endsWith(".properties")) {
								Path targetFilePath = targetDirectory
										.resolve(entry.getFileName());
								Files.copy(entry, targetFilePath,
										StandardCopyOption.REPLACE_EXISTING);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			showMessage("Error",
					"Properties files cannot be found or created.");
		}
	}

	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(frmGenericsiploader, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public ProcessBuilder secureProcessBuilder(String editorCommand, File file) {
	  
	  
	  String tmpFileName = UUID.randomUUID().toString();
    InputStream fis = FileUtil.loadFile(file);
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(tmpFileName);
      fis.transferTo(fos);
      fos.flush();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new ProcessBuilder(editorCommand, tmpFileName);
	}

}