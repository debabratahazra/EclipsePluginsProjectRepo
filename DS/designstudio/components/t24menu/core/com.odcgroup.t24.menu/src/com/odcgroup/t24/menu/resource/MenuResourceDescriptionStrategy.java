package com.odcgroup.t24.menu.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.odcgroup.t24.menu.menu.MenuRoot;

/**
 * filter-out the eObjectDesc & refDesc temporarily
 * @author phanikumark
 *
 */
public class MenuResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	@Override
	public boolean createEObjectDescriptions(EObject eObject,
			IAcceptor<IEObjectDescription> acceptor) {
		
		if (eObject instanceof MenuRoot) {
			super.createEObjectDescriptions(eObject, acceptor);
		}
		
		// do not traverse children
		return false;
	}

	@Override
	public boolean createReferenceDescriptions(EObject from,
			URI exportedContainerURI, IAcceptor<IReferenceDescription> acceptor) {
		return true;
	}	
	

}
