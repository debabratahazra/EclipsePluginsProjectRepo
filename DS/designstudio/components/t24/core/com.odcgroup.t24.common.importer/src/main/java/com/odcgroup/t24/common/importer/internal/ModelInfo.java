package com.odcgroup.t24.common.importer.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;

public abstract class ModelInfo<T extends IExternalObject> implements IModelDetail {

	private final T detail;
	private String xmlString;
	private Resource resource;
	
	protected final T getDetail() {
		return this.detail;
	}
	
	@Override
	public final IExternalObject externalObject() {
		return detail;
	}

	@Override
	public EObject getModel() {
		EObject eObj = null;
		if (resource != null && !resource.getContents().isEmpty()) {
			eObj = resource.getContents().get(0);
		}
		return eObj;
	}

	@Override
	public final void setXmlString(String value) {
		this.xmlString = value;
	}

	@Override
	public final String getXmlString() {
		return this.xmlString;
	}

	@Override
	public final void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public final Resource getResource() {
		return this.resource;
	}

	protected ModelInfo(T detail) {
		this.detail = detail;
	}

}
