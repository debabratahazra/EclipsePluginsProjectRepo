package com.odcgroup.t24.common.importer;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.t24.server.external.model.IExternalObject;

public interface IModelDetail {
	
	IExternalObject externalObject();
	
	EObject getModel();
	
	String getModelType();

	String getDescription();
	
	String getModelName();
	
	String getFullModelName(IFolder destinationFolder);
	
	// return the XML filename without the extension
	String getXMLFilename();
	
	void setXmlString(String value);
	
	String getXmlString();
	
	void setResource(Resource resource);
	
	Resource getResource();

}
