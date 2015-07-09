package com.odcgroup.aaa.connector.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.odcgroup.mdf.ecore.MdfDomainImpl;

/**
 * Util to get Business Types.
 * 
 * @author Michael Vorburger
 */
public class BusinessTypesUtil {

	public static MdfDomainImpl getBusinessTypesDomain(ResourceSet resourceSet) throws IOException {
		URI uri = URI.createURI(BusinessTypesUtil.class.getResource("/BusinessTypes.domain").toString());
		// If this fails with something like this, then you should run this as
		// an OSGi Plug-In test instead of standalone JUnit Test, so that the 
		// *.domain resource factory is correctly loaded by extension point:
		// "RuntimeException: Cannot create a resource for 'file:/home/vorburger/dev/DS/SRC/trunk2.switching/designstudio/components/aaa/tests/com.odcgroup.aaa.connector.tests/target/test-classes/BusinessTypes.domain'; a registered resource factory is needed"
		// Alternatively, if you want them to run as SE, then the respective tests should be adapted to use the standard Xtext JUnit infrastructure, with a DomainInjectorProvider
		
		Resource resource = resourceSet.getResource(uri, true);
		resource.load(Collections.EMPTY_MAP);
		return (MdfDomainImpl) resource.getContents().get(0);
	}
	
}