package com.odcgroup.ds.t24.packager.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
 
public class ZipHelper {
	
	public static interface Filter {
		public boolean keep(String entryName);
	}
	
	public static interface Relocate {
		public String relocate(String entry);
	}
	
	public static Relocate RELOCATE_INDENTITY = new Relocate() {
		public String relocate(String entry) {
			return entry;
		}
	};
	 
	public static class MappedFolder {
		public MappedFolder(File folder, String entryPrefix) {
			this.folder = folder;
			this.entryPrefix = entryPrefix;
		}
		public final File folder;
		public final String entryPrefix;
	}
	
    public static void createZip(File archive, MappedFolder... mappedFolders) throws IOException {
        ZipArchiveOutputStream archiveStream = null;
        BufferedOutputStream outputStream = null;
        try {
        	outputStream = new BufferedOutputStream(new FileOutputStream(archive));
			archiveStream = new ZipArchiveOutputStream(outputStream);
			for (MappedFolder mappedFolder : mappedFolders) {
				if (mappedFolder.folder != null &&
						mappedFolder.folder.exists() &&
						mappedFolder.folder.isDirectory()) {
					for (File file: mappedFolder.folder.listFiles()) {
						addFileToZip(archiveStream, file, mappedFolder.entryPrefix + "/");
					}
				}
			}
        	archiveStream.flush();
            archiveStream.finish();
        } finally {
        	IOUtils.closeQuietly(archiveStream);
        	IOUtils.closeQuietly(outputStream);
        }
 
    }
    
	public static void createZip(File archive, File folder) throws IOException {
        ZipArchiveOutputStream archiveStream = null;
        BufferedOutputStream outputStream = null;
        try {
        	outputStream = new BufferedOutputStream(new FileOutputStream(archive));
			archiveStream = new ZipArchiveOutputStream(outputStream);
        	for (File file: folder.listFiles()) {
        		addFileToZip(archiveStream, file, "");
        	}
        	archiveStream.flush();
            archiveStream.finish();
        } finally {
        	IOUtils.closeQuietly(archiveStream);
        	IOUtils.closeQuietly(outputStream);
        }
 
    }
 
    private static void addFileToZip(ZipArchiveOutputStream archiveStream, File file, String base) throws IOException {
    	if (StringUtils.startsWith(file.getName(), ".")) {
    		return;
    	}
        String entryName = base + file.getName();
        ZipArchiveEntry zipEntry = new ZipArchiveEntry(file, entryName);
        archiveStream.putArchiveEntry(zipEntry);
        if (file.isFile()) {
            FileInputStream fInputStream = null;
            try {
                fInputStream = new FileInputStream(file);
                IOUtils.copy(fInputStream, archiveStream);
                archiveStream.closeArchiveEntry();
            } finally {
                IOUtils.closeQuietly(fInputStream);
            }
        } else if (file.isDirectory()) {
            archiveStream.closeArchiveEntry();
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFileToZip(archiveStream, child, entryName + "/");
                }
            }
        }
    }

	public static List<String> listZipEntries(File zip) throws IOException {
		List<String> tarEntries = new ArrayList<String>();
		ZipArchiveInputStream zipInput = null;
		try {
			zipInput = new ZipArchiveInputStream(new FileInputStream(zip));
	        ZipArchiveEntry entry;
	        while (null!=(entry=zipInput.getNextZipEntry())) {
	        	tarEntries.add(entry.getName());
	        }
		} finally {
			IOUtils.closeQuietly(zipInput);
		}
        return tarEntries;
	}

	public static void unzipToFolder(File zipFile, File outputFolder) throws IOException {
		unzipToFolder(zipFile, outputFolder, new Filter() {
				public boolean keep(String entryName) {
					return true;
				}
			});
	}
	

	public static void unzipToFolder(File zipFile, File outputFolder, Filter filter) throws IOException {
		unzipToFolder(zipFile, outputFolder, filter, RELOCATE_INDENTITY);
	}

	public static void unzipToFolder(File zipFile, File outputFolder, Filter filter, Relocate relocate) throws IOException {
		FileUtils.forceMkdir(outputFolder);
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipArchiveEntry> e = zip.getEntriesInPhysicalOrder();
		while (e.hasMoreElements()) {
			ZipArchiveEntry zipEntry = e.nextElement();
			if (!filter.keep(zipEntry.getName())) {
				continue;
			}
			File targetFile = new File(outputFolder, relocate.relocate(zipEntry.getName()));
			if (zipEntry.isDirectory()) {
				FileUtils.forceMkdir(targetFile);
			} else {
				if (targetFile.exists()) {
					throw new IllegalArgumentException("Duplicate files in archive. Source: " + zipEntry + ", target: " + targetFile);
				}
				FileUtils.forceMkdir(targetFile.getParentFile());
				BufferedInputStream bis = null;
				FileOutputStream fis = null;
				try {
					bis = new BufferedInputStream(zip.getInputStream(zipEntry));
					fis = new FileOutputStream(targetFile);
					IOUtils.copy(bis, fis);
				} finally {
					IOUtils.closeQuietly(bis);
					IOUtils.closeQuietly(fis);
				}
			}
		}
		zip.close();
	}

}