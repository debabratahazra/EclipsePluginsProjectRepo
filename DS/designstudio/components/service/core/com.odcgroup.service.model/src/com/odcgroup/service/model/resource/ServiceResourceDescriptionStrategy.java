package com.odcgroup.service.model.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

/**
 * filter-out the eObjectDesc & refDesc temporarily
 * @author phanikumark
 *
 */
public class ServiceResourceDescriptionStrategy extends
		DefaultResourceDescriptionStrategy {

	@Override
	public boolean createEObjectDescriptions(EObject eObject,
			IAcceptor<IEObjectDescription> acceptor) {
		return false;
	}

	@Override
	public boolean createReferenceDescriptions(EObject from,
			URI exportedContainerURI, IAcceptor<IReferenceDescription> acceptor) {
		return false;
	}	

}
