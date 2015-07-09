package com.odcgroup.t24.common.importer;

import org.eclipse.core.runtime.IProgressMonitor;


public interface IImportOperation {
	
	void importModels(IImportModelReport report, IProgressMonitor monitor);
	
	void setInDebug(boolean inDebug);
	
	boolean getInDebug();
	
}
