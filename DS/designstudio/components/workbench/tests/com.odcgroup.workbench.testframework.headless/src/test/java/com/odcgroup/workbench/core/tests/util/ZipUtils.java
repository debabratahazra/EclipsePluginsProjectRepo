package com.odcgroup.workbench.core.tests.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	
	static private Logger log = LoggerFactory.getLogger(ZipUtils.class);
	
    static public void unzipArchive(File archive, File outputDir) {
    	try {
            ZipFile zipfile = new ZipFile(archive);
            for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                unzipEntry(zipfile, entry, outputDir);
            }
        } catch (Exception e) {
            log.error("Error while extracting file " + archive, e);
        }
    }

    static private void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir) throws IOException {

        if (entry.isDirectory()) {
            createDir(new File(outputDir, entry.getName()));
            return;
        }

        File outputFile = new File(outputDir, entry.getName());
        if (!outputFile.getParentFile().exists()){
            createDir(outputFile.getParentFile());
        }

        log.debug("Extracting: " + entry);
        BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            IOUtils.copy(inputStream, outputStream);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    static private void createDir(File dir) {
        log.debug("Creating dir "+dir.getName());
        dir.mkdirs();
        if(!dir.exists()) throw new RuntimeException("Can not create dir "+dir);
    }
}