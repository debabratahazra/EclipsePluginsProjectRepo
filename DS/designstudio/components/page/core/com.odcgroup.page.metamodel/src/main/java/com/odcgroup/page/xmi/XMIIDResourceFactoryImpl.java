package com.odcgroup.page.xmi;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Factory for creating XMIIDResourceImpl Objects.
 * This enables unique id's to be createrd for each element.
 * This is used to avoid cross-referencing problems between the
 * .page / .module files and the .metamodel file.
 * 
 * @author GaryHayes
 */
public class XMIIDResourceFactoryImpl extends XMIResourceFactoryImpl {
	/**
	 * Creates a new XMIIDResourceFactoryImpl Object.
	 */
	public XMIIDResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates a resource. This factory returns a XMIIDResourceImpl Object.
	 * 
	 * @param uri
	 *            The URI
	 * @return Resource The resource
	 */
	public Resource createResource(URI uri) {
		return new XMIIDResourceImpl(uri);
	}
}