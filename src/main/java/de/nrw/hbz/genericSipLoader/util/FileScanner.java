package de.nrw.hbz.genericSipLoader.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileScanner {

	public FileScanner() {
		logger.debug("FileScanner constructor has been called.");
		createScanner(System.getProperty("user.dir"));
	}

	public FileScanner(String path) {
		createScanner(path);
	}

	final static Logger logger = LogManager.getLogger(FileScanner.class);
	private File scan;
	private String startPath;
	private String pattern = null;
	private Set<String> fList;

	public void createScanner(String path) {
		scan = new File(path);
	}

	public void processScan() {
		try {
			fList = listFiles(scan.toString());
			Iterator<String> fListIt = fList.iterator();
			while (fListIt.hasNext()) {
				String fileName = fListIt.next();
				// System.out.println(fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processScan(String pattern) {
		try {
			fList = listFilesByNamePattern(scan.toString(), pattern);
			Iterator<String> fListIt = fList.iterator();
			while (fListIt.hasNext()) {
				String fileName = fListIt.next();
				logger.debug(fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processScan(List<String> excludedMimeTypes) {
		try {
			fList = listFilesExcludeMimeType(scan.toString(),
					excludedMimeTypes);
			Iterator<String> fListIt = fList.iterator();
			while (fListIt.hasNext()) {
				String fileName = fListIt.next();
				// System.out.println(fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Set<String> listFiles(String dir) throws IOException {
		final Set<String> fileList = new TreeSet<String>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) {
				if (!Files.isDirectory(file)) {
					fileList.add(file.toAbsolutePath().toString());
				}
				return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}

	private Set<String> listFilesByNamePattern(String dir, final String pattern)
			throws IOException {
		final Set<String> fileList = new TreeSet<String>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) {
				if (!Files.isDirectory(file)
						&& file.getFileName().toString().contains(pattern)) {
					fileList.add(file.toAbsolutePath().toString());
				}
				return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}

	private Set<String> listFilesExcludeMimeType(String dir,
			final List<String> mimeTypes) throws IOException {
		final Set<String> fileList = new TreeSet<String>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) {
				try {
					if (!Files.isDirectory(file)) {
						String type = Files.probeContentType(file);
						boolean mt = true;
						for (int i = 0; i < mimeTypes.size(); i++) {
							if (type.equals(mimeTypes.get(i))) {
								mt = false;
							}
						}
						if (mt) {
							fileList.add(file.toAbsolutePath().toString());
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the fList
	 */
	public Set<String> getFileList() {
		return fList;
	}

}
