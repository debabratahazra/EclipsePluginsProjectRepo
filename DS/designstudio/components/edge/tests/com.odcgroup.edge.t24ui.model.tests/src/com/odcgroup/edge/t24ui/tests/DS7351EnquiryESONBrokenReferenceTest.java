package com.odcgroup.edge.t24ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.edge.t24ui.contextEnquiry.AppliedEnquiry;
import com.odcgroup.edge.t24ui.contextEnquiry.VersionContextEnquiry;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * DS-7351.
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(T24UIInjectorProvider.class)
public class DS7351EnquiryESONBrokenReferenceTest {

	@Inject ResourceSet resourceSet;
	EIO eio;

	@Test
	public void testESONEnquiryReference() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);
		
		eio.loadResource(eio.getURI("CUSTOMER,CREATE.version", getClass()));
		
		Enquiry directlyLoadedEnquiry = eio.load(eio.getURI("ACCT.BAL.TODAY.enquiry", getClass()), Enquiry.class);
		assertNotNull(directlyLoadedEnquiry);
		assertFalse(directlyLoadedEnquiry.eIsProxy());
		assertEquals("ACCT.BAL.TODAY", directlyLoadedEnquiry.getName());
		
		URI uri = eio.getURI("TestCtxEnq.eson", DS7351EnquiryESONBrokenReferenceTest.class);
		assertNotNull(uri);
		
		VersionContextEnquiry versionContextEnquiry = ml.getRootEObject(uri, VersionContextEnquiry.class);
		assertNotNull(versionContextEnquiry);
		assertEquals("theFirstContextEnquiry", versionContextEnquiry.getName());
		Version version = versionContextEnquiry.getAppliesTo();
		assertNotNull(version);
		assertFalse(version.eIsProxy());
		assertEquals("CREATE", version.getShortName());

		AppliedEnquiry appliedEnquiry = versionContextEnquiry.getEnquiries().get(0);
		assertNotNull(appliedEnquiry);
		Enquiry refEnquiry = appliedEnquiry.getEnquiry();
		assertNotNull(refEnquiry);
		assertFalse(refEnquiry.eIsProxy());
		assertEquals("ACCT.BAL.TODAY", refEnquiry.getName());
	}

}
