package com.odcgroup.ds.t24.packager.t24project;

import java.io.File;

import com.odcgroup.ds.t24.packager.log.PackagerLogger;

/**
 * Represents a T24 project that can contains basic and data files.
 * The basic can be public or private, the data can be model or public.
 * @author yandenmatten
 */
public class T24Project {
	
	private String name;
	private String version;
	private File location;
	
	private String sourcePublic;
	private String sourcePrivate;
	private String dataPublic;
	private String dataModel;

	private File outputFolder;
	
	private PackagerLogger logger;

	public T24Project(
			String name, 
			String version, 
			File location, 
			String sourcePublic,
			String sourcePrivate,
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
		this.sourcePrivate = sourcePrivate;
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

	public File getSourcePrivateFolder() {
		return new File(location, sourcePrivate);
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
