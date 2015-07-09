package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;

public class CloseExternalLoader <T extends IModelDetail> implements IImportingStep<T> {

	private	IExternalLoader loader;
	
	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		if (monitor != null) {
			monitor.subTask("Disconnecting from external server");
		}
		loader.close();
		return true;	
	}
	
	public CloseExternalLoader(IImportModelReport report, IExternalLoader loader) {
		this.loader = loader;
	}
	
}

