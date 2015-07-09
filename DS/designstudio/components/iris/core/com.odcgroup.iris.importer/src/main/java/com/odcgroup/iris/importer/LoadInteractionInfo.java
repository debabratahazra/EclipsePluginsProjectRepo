package com.odcgroup.iris.importer;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.server.external.model.internal.IInteractionLoader;

public class LoadInteractionInfo implements IImportingStep<InteractionInfo> {

	private IInteractionLoader loader;
	private IImportModelReport report;

	/**
	 * Monitor Sub-task progress bar message
	 * @param model
	 * @return
	 */
	private String getMessage(InteractionInfo model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": ");
		b.append(model.getDescription());
		b.append(": ");
		b.append("Importing ");
		return b.toString();
	}
	
	@Override
	public boolean execute(InteractionInfo model, IProgressMonitor monitor) {

		boolean success = true;
		try {
			if (monitor != null) {
				monitor.subTask(getMessage(model));
			}
			loader.getObjectAsXML(model.externalObject());
		} catch (Exception ex) {
			success = false;
			report.error(model, ex);
		}
		return success;
	}
	
	public LoadInteractionInfo(IImportModelReport report, IInteractionLoader loader) {
		this.report = report;
		this.loader = loader;
	}

}
