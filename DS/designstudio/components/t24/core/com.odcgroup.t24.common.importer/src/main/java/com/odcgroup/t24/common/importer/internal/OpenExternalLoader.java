package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class OpenExternalLoader <T extends IModelDetail> implements IImportingStep<T> {

	private IImportModelReport report;
	private	IExternalLoader loader;
	
	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		boolean success = true;
		
		if (monitor != null) {
			monitor.subTask("Connecting to external server");
		}
		
		try {
			loader.open();
		} catch (T24ServerException ex) {
			report.error(ex);
			success = false;
		}
			
		return success;	
	}
	
	public OpenExternalLoader(IImportModelReport report, IExternalLoader loader) {
		this.report = report;
		this.loader = loader;
	}
	
}

