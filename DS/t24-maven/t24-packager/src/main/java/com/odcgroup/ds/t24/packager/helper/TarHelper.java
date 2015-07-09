package com.odcgroup.ds.t24.packager.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

/**
 * Helper to manipulate tar.
 * @author yandenmatten
 */
public class TarHelper {
	
	private static final String META_INF = File.separator + "META-INF" + File.separator;
	private static final String POM = File.separator + "pom.xml";

	public interface FileNameMapper {
		String targetFileName(String originalFileName);
	}
	
	public static final FileNameMapper IDENTITY_MAPPER = new FileNameMapper() {
		public String targetFileName(String originalFileName) {
			return originalFileName;
		}
	};

	public static void addFile(TarArchiveOutputStream tarStream, File file, String entryName) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(entryName);
		entry.setSize(file.length());
		tarStream.putArchiveEntry(entry);
		FileInputStream dataStream = new FileInputStream(file);
		try {
			IOUtils.copy(dataStream, tarStream);
		} finally {
			IOUtils.closeQuietly(dataStream);
		}
		tarStream.closeArchiveEntry();
	}

	public static void addContents(TarArchiveOutputStream tarStream,
			byte[] headerContents, String entryName) throws IOException {
		TarArchiveEntry dataHeaderEntry = new TarArchiveEntry(entryName);
		dataHeaderEntry.setSize(headerContents.length);
		tarStream.putArchiveEntry(dataHeaderEntry);
		IOUtils.copy(new ByteArrayInputStream(headerContents), tarStream);
		tarStream.closeArchiveEntry();
	}
	
	public static List<String> listTarEntries(File tar) throws IOException {
		List<String> tarEntries = new ArrayList<String>();
		TarArchiveInputStream tarInput = null;
		try {
			tarInput = new TarArchiveInputStream(new FileInputStream(tar));
	        TarArchiveEntry entry;
	        while (null!=(entry=tarInput.getNextTarEntry())) {
	        	tarEntries.add(entry.getName());
	        }
		} finally {
			IOUtils.closeQuietly(tarInput);
		}
        return tarEntries;
	}
	
	public static void extractZipToTar(File zipFile, TarArchiveOutputStream tarStream) throws IOException {
		extractZipToTar(zipFile, tarStream, IDENTITY_MAPPER);
	}

	public static void extractZipToTar(File zipFile, TarArchiveOutputStream tarStream, FileNameMapper mapper) throws IOException {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipArchiveEntry> e = zip.getEntriesInPhysicalOrder();
		while (e.hasMoreElements()) {
			ZipArchiveEntry zipEntry = e.nextElement();
			if (zipEntry.isDirectory()) {
				continue; // Skip directories
			}
			String targetFileName = mapper.targetFileName(zipEntry.getName());
			TarArchiveEntry entry = new TarArchiveEntry(targetFileName);
			entry.setSize(zipEntry.getSize());
			tarStream.putArchiveEntry(entry);
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(zip.getInputStream(zipEntry));
				IOUtils.copy(bis, tarStream);
			} finally {
				IOUtils.closeQuietly(bis);
			}
			tarStream.closeArchiveEntry();
		}
		zip.close();
	}

	public static void addFolderStructureToTar(TarArchiveOutputStream tarStream,
			File folder, String tarEntryPrefix) throws IOException {
		for (File file: FileHelper.listFiles(folder)) {
			if (file.getAbsolutePath().contains(META_INF) || file.getAbsolutePath().contains(POM))
				continue;

			if (file.isDirectory()) {
					addFolderStructureToTar(tarStream, file, tarEntryPrefix + "/" + file.getName());
			} else if (file.isFile()) {
				addFile(tarStream, file, tarEntryPrefix + "/" + file.getName());
			}
		}
	}

	public static void createTar(File packageFile, File packageWorkingFolder) throws IOException {
		TarArchiveOutputStream tarStream = new TarArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(packageFile)));
		tarStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
		try {
			TarHelper.addFolderStructureToTar(tarStream, packageWorkingFolder, "");
			tarStream.finish();
		} finally {
			tarStream.close();
		}
	}
	
}
