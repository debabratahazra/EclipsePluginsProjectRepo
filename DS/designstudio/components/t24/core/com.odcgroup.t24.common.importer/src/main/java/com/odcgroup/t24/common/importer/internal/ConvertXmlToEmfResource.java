package com.odcgroup.t24.common.importer.internal;

import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;

public class ConvertXmlToEmfResource<T extends IModelDetail> implements IImportingStep<T> {

	private IImportModelReport report;
	private ResourceSet resourceSet;

	private String getMessage(T model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Create Resource"); //$NON-NLS-1$
		return b.toString();
	}
	
	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		if (monitor != null) {
			monitor.subTask(getMessage(model));
		}
		URI dummyURI = URI.createURI("http:///"+model.getModelName()+".xml"); //$NON-NLS-1$
		Resource resource = null;
		try {
			resource = resourceSet.createResource(dummyURI);
			if (model.getXmlString()!=null) {
				InputStream inputStream = new URIConverter.ReadableInputStream(model.getXmlString(), null);
				resource.load(inputStream, null);
			}
			model.setResource(resource);
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		return true;
	}

	public ConvertXmlToEmfResource(IImportModelReport report, ResourceSet resourceSet) {
		this.report = report;
		this.resourceSet = resourceSet;
	}

}
