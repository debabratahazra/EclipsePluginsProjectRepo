package com.odcgroup.ds.t24.packager.t24tafj;

import java.io.File;

import com.odcgroup.ds.t24.packager.log.PackagerLogger;

/**
 * Represents a Tafj project that can contains data files and public basic 
 * to package (not to compile as the compilation is done by tafj maven plugin
 * (com.temenos.tafj:tafj-maven-plugin in nexus).
 * The data can be model or public.
 * The only public basic files are referenced. 
 * @author yandenmatten
 */
public class TafjProject {
	
	private String name;
	private String version;
	private File location;
	
	private String sourcePublic;
	private String dataPublic;
	private String dataModel;

	private File outputFolder;
	
	private PackagerLogger logger;

	public TafjProject(
			String name, 
			String version, 
			File location, 
			String sourcePublic,
			String dataPublic,
			String dataModel,
			File outputFolder) {
		if (!location.exists()) {
			throw new IllegalArgumentException("the project location doesn't exist");
		}
		this.name = name;
		this.version = version;
		this.location = location;
		this.sourcePublic = sourcePublic;
		this.dataPublic = dataPublic;
		this.dataModel = dataModel;
		this.outputFolder = outputFolder;
	}
	
	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
	
	public File getLocation() {
		return location;
	}

	public File getSourcePublicFolder() {
		return new File(location, sourcePublic);
	}

	public File getDataPublicFolder() {
		return new File(location, dataPublic);
	}

	public File getDataModelFolder() {
		return new File(location, dataModel);
	}

	public File getOutputFolder() {
		return outputFolder;
	}

	public PackagerLogger getLogger() {
		return logger;
	}

	public void setLogger(PackagerLogger logger) {
		this.logger = logger;
	}


}
