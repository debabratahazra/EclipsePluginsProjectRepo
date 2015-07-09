package com.odcgroup.workbench.core;

public class InvalidMetamodelVersionException extends Exception {
	
	String metamodelVersion;
	
    public InvalidMetamodelVersionException(String message, String metamodelVersion) {
        super(message);
        this.metamodelVersion = metamodelVersion;
    }

    public InvalidMetamodelVersionException(String metamodelVersion, Throwable cause) {
        super(cause);
        this.metamodelVersion = metamodelVersion;
    }

    public InvalidMetamodelVersionException(String message, String metamodelVersion, Throwable cause) {
        super(message, cause);
        this.metamodelVersion = metamodelVersion;
    }
    
    public String getMetamodelVersion() {
    	return metamodelVersion;
    }
}
