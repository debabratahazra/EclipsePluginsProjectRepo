package com.odcgroup.workbench.core;

import org.eclipse.core.runtime.CoreException;

public interface IMetaModelVersioned {

	public boolean needsMigration() throws CoreException, InvalidMetamodelVersionException;
	
	public String getMetaModelVersion() throws CoreException;
	
	public String getCurrentMetaModelVersion();
		
}
