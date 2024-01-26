package de.nrw.hbz.genericSipLoader.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import de.nrw.hbz.genericSipLoader.impl.DipsLoaderImpl;
import de.nrw.hbz.genericSipLoader.impl.KtblLoaderImpl;
import de.nrw.hbz.genericSipLoader.util.FileScanner;
import de.nrw.hbz.genericSipLoader.util.ZipExtractor;
import java.awt.SystemColor;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.*;

/**
 * 
 * @author adoud
 *
 */
public class MainGui extends JFrame {

	private JFrame frmGenericsiploader;
	private JTextField textFieldZipFile, textFieldName, textField_Pasword;
	private JLabel lblApiProperties, lbApiUser, lblApiPassword, lblZipFile,
			lblApiCreditianls;
	private JRadioButton rdbtnDanrw, rdbtnKtbl;
	private JTextArea textAreaApiProperties = null;
	// DANARW | KTBL
	private String radButtonMetadata = "danrw";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 */
	public MainGui() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGenericsiploader = new JFrame("GenericSipLoader");
		frmGenericsiploader.getContentPane().setBackground(SystemColor.info);
		frmGenericsiploader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGenericsiploader.getContentPane().setLayout(null);
		frmGenericsiploader.setSize(491, 420);
		frmGenericsiploader.setResizable(false);
		// Set the frame to appear in the center of the screen
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

		textField_Pasword = new JTextField("");
		textField_Pasword.setColumns(10);
		textField_Pasword.setBounds(81, 74, 274, 26);
		frmGenericsiploader.getContentPane().add(textField_Pasword);

		// ********************************JLabels******************************************

		lblApiProperties = new JLabel("DANRW-Api.properties:");
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
		textAreaApiProperties.setWrapStyleWord(true);
		textAreaApiProperties.setLineWrap(true);
		textAreaApiProperties.setBackground(Color.WHITE);
		textAreaApiProperties.setBounds(10, 219, 296, 153);
		frmGenericsiploader.getContentPane().add(textAreaApiProperties);
		loadContentOfProperitesFileInTextArea("fedora", textAreaApiProperties);
		textAreaApiProperties.getDocument();

		// ********************************JTextArea**************************************

		// ********************************JRadioButton**************************************
		rdbtnDanrw = new JRadioButton("DANRW");
		rdbtnDanrw.setBackground(new Color(240, 230, 140));
		rdbtnDanrw.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnDanrw.setSelected(true);
		rdbtnDanrw.setBounds(10, 168, 70, 20);
		frmGenericsiploader.getContentPane().add(rdbtnDanrw);

		rdbtnKtbl = new JRadioButton("KTBL");
		rdbtnKtbl.setBackground(new Color(240, 230, 140));
		rdbtnKtbl.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnKtbl.setBounds(90, 168, 70, 20);
		frmGenericsiploader.getContentPane().add(rdbtnKtbl);

		// Nur ein JRadioButton kann gleichzeitig ausgewählt werden
		ActionListener radioButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
				if (selectedRadioButton == rdbtnDanrw) {
					radButtonMetadata = "danrw";
					rdbtnKtbl.setSelected(false);
					lblApiProperties.setText("DANRW-Api.properties:");
					textAreaApiProperties.setText(null);
					loadContentOfProperitesFileInTextArea("fedora",
							textAreaApiProperties);

				} else if (selectedRadioButton == rdbtnKtbl) {
					radButtonMetadata = "ktbl";
					lblApiProperties.setText("KTBL-Api.properties:");
					rdbtnDanrw.setSelected(false);
					textAreaApiProperties.setText(null);
					loadContentOfProperitesFileInTextArea("ktbl",
							textAreaApiProperties);
				}
				if (!rdbtnKtbl.isSelected() && !rdbtnDanrw.isSelected()) {
					radButtonMetadata = "danrw";
					rdbtnDanrw.setSelected(true);
					textAreaApiProperties.setText(null);
					lblApiProperties.setText("DANRW-Api.properties:");
					loadContentOfProperitesFileInTextArea("fedora",
							textAreaApiProperties);
				}
			}
		};
		rdbtnDanrw.addActionListener(radioButtonListener);
		rdbtnKtbl.addActionListener(radioButtonListener);

		// ********************************JRadioButton**************************************

		// ********************************JButtons**************************************
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Arial", Font.PLAIN, 11));
		btnReset.setBounds(316, 346, 93, 26);
		frmGenericsiploader.getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hier wird die Methode aufgerufen, die Sie mit dem Button
				// verknüpfen möchten
				handleResetButtonClick();
			}
		});

		JButton btnBrowse = new JButton("Browse");
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

		JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Arial", Font.PLAIN, 11));
		btnOk.setBounds(316, 315, 93, 26);
		frmGenericsiploader.getContentPane().add(btnOk);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleOKButtonClick();
			}
		});

		// ********************************JButtons**************************************
	}

	private void handleResetButtonClick() {
		textFieldName.setText(null);
		textField_Pasword.setText(null);
		textFieldZipFile.setText(null);
		rdbtnDanrw.setSelected(true);
		rdbtnKtbl.setSelected(false);
		textAreaApiProperties.setText(null);
		lblApiProperties.setText("DANRW-Api.properties:");
		loadContentOfProperitesFileInTextArea("fedora", textAreaApiProperties);
	}

	private void handleOKButtonClick() {
		// handleResetButtonClick();
		FileScanner fScan = null;
		String basePath = null;
		String user = null;
		String passwd = null;
		String target = null;
		try {
			if (textFieldName.getText() != null
					&& textField_Pasword.getText() != null
					&& textFieldZipFile.getText() != null) {
				target = radButtonMetadata;
				// System.out.println("target1=" + target);
				basePath = Paths.get(textFieldZipFile.getText()).getParent()
						.toString();
				user = textFieldName.getText();
				passwd = textField_Pasword.getText();
			} else {
				fScan = new FileScanner();
				fScan.processScan(".zip");
				Set<String> fList = fScan.getFileList();
				ZipExtractor extractor = new ZipExtractor(fList, basePath);
			}

			if (target.equals("danrw")) {
				DipsLoaderImpl dLoader = new DipsLoaderImpl(basePath, user,
						passwd);
				dLoader.extractZips();
				Set<String> ieList = dLoader.scanIEs();
				dLoader.cuFedoraObject(ieList);
			}
			if (target.equals("ktbl")) {
				KtblLoaderImpl ktblLoader = new KtblLoaderImpl(basePath, user,
						passwd);
				ktblLoader.extractZips();
				Set<String> ieList = ktblLoader.scanIEs();
				ktblLoader.cuToScienceObject(ieList);
			}
			if (isTextAreaEdited(textAreaApiProperties, target)) {
				if (target.equals("danrw")) {
					target = "fedora";
				}
				saveTextAreaContentToPropertiesFile(target,
						textAreaApiProperties);
			}
			showMessage("Report", "OK");
			// handleResetButtonClick();
		} catch (Exception e) {
			showMessage("Warning", "Please check your entries !!!");
		}

	}

	public void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(frmGenericsiploader, message, title,
				JOptionPane.INFORMATION_MESSAGE);
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
		Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main",
				"resources", metadataName + "-api.properties");
		textArea.setText(null);
		textArea.setText(readFileContent(filePath.toString()));
	}

	public String readFileContent(String filePath) {
		File file = new File(filePath);
		StringBuilder stringBuilder = new StringBuilder();
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(
						fileReader)) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.contains("#")) {
					stringBuilder.append(line);
					stringBuilder.append(System.lineSeparator());
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return stringBuilder.toString();
	}

	public Properties getProperitesFile(String metadataName) {
		InputStream inputStream = null;
		if (metadataName.equals("danrw")) {
			metadataName = "fedora";
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

	public void saveTextAreaContentToPropertiesFile(String metadataName,
			JTextArea textArea) {

		File f = null;
		Path filePath = null;
		filePath = Paths.get(System.getProperty("user.dir"), "src", "main",
				"resources", metadataName + "-api.properties");
		f = new File(filePath.toString());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			writer.write(textArea.getText());

		} catch (Exception e) {
			showMessage("Warning",
					"The properties file cannot be overwritten.");
		}
	}
}
