package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.workbench.dsl.xml.NameURISwapper;

public class ProcessEmfReferences<T extends IModelDetail> implements IImportingStep<T> {

	private IImportModelReport report;
	private NameURISwapper nameURISwapper;
	
	private String getMessage(T model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Processing EMF References"); //$NON-NLS-1$
		return b.toString();
	}

	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		boolean success = true;
		if (monitor != null) {
			monitor.subTask(getMessage(model));
		}
		EObject eObj = model.getModel();
		if (eObj != null) {
			try {
				nameURISwapper.replaceAllNameURIProxiesByReferences(eObj);
			} catch (Exception ex) {
				report.error(model, ex);
				success = false;
			}
		} else {
			report.error(model, "ECore model is empty."); //$NON-NLS-1$
			success = false;
		}
		return success;
	}

	public ProcessEmfReferences(IImportModelReport report, NameURISwapper nameURISwapper) {
		this.report = report;
		this.nameURISwapper = nameURISwapper;
	}

}
