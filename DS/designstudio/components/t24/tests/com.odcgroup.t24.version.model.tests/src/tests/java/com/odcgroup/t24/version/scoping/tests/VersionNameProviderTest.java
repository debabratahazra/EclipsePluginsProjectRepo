package com.odcgroup.t24.version.scoping.tests;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;

/**
 * DS-7351 related.
 * 
 * @see com.odcgroup.t24.enquiry.scoping.tests.EnquiryQualifiedNameProviderTest
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
public class VersionNameProviderTest {

	@Inject IQualifiedNameProvider versionQualifiedNameProvider;
	@Inject ResourceSet resourceSetJustForSimpleTest;
	
	/**
	 * In current version (DS 9.2 as of June 3rd),
	 * *.version model version file names are always with underscore instead of .  
	 */
	@Test
	public void testSimpleVersionQualifiedName() {
		checkVersionName("CUSTOMER,CREATE_2");
	}

	/**
	 * Just in case that (above) ever changes and we have dots in it,
	 * just because we've had issues with that in Enquiry in DS-7351,
	 * let's have a test for possible future changes. (This didn't
	 * used to work with how the VersionNameProvider was implemented
	 * at the time this test was written; corrected since.) 
	 */
	@Test
	public void testVersionQualifiedNameWithDot() {
		checkVersionName("CUSTOMER,CREATE.2");
	}

	private void checkVersionName(String versionT24Name) {
		Version version = VersionDSLFactory.eINSTANCE.createVersion();
		URI uri = URI.createURI(versionT24Name + ".version");
		Resource resource = resourceSetJustForSimpleTest.createResource(uri);
		resource.getContents().add(version);
		
		QualifiedName qn = versionQualifiedNameProvider.getFullyQualifiedName(version);
		assertEquals(1, qn.getSegmentCount()); // TODO huh? will this work for DS7351EnquiryESONBrokenReferenceTest??
		assertEquals(versionT24Name, qn.getFirstSegment());		
	}
	
}
