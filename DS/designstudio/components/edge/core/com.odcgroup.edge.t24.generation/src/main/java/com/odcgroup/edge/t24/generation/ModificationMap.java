package com.odcgroup.edge.t24.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;

import com.odcgroup.edge.t24.generation.util.GenLogger;

public class ModificationMap {
	
	private static final Logger LOGGER = GenLogger.getLogger(ModificationMap.class);
	
	private static final String FILENAME = "EdgeModMap.properties";
	
	private Properties modMap = null;
	private File modMapFile = null;
	
	private static String getFilename(String folder) {
		return folder + File.separator + FILENAME;
	}
	
	public final Properties map() {
		return modMap;
	}
	
	public void load() {

		if (modMap != null) 
			return;
		
		modMap = new Properties();
        
        try {
        	modMap.load(new FileInputStream(modMapFile));
        } catch(FileNotFoundException fne) {
        	LOGGER.info("Modification Timestamp file not found");
        } catch(Exception e) {
        	LOGGER.error("Modification Timestamp error: " + e.getMessage());
        }
	}
	
	public void save() {
        try {
        	LOGGER.info("Writing Last mod cache to: {} ", modMapFile);
    		modMap.store(new FileOutputStream(modMapFile), "Last Generated Timestamps");
    	} catch(Exception e) {
    		LOGGER.error( "Writing output timestamp mod cache: ", e );
    	}
	}

	public ModificationMap(IProject p_project) {
		String folder = p_project.getLocation().toOSString();
        modMapFile = new File(getFilename(folder));
	}
	
	public ModificationMap(String filename) {
        modMapFile = new File(filename);
	}
	
}
