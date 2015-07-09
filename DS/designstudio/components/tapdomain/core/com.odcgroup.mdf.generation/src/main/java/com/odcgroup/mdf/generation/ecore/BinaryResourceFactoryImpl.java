package com.odcgroup.mdf.generation.ecore;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;

public class BinaryResourceFactoryImpl implements Factory {

	public Resource createResource(URI uri) {
		return new BinaryResourceImpl(uri);
	}

}
