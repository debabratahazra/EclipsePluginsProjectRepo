package com.odcgroup.edge.t24ui.tests;

import static org.junit.Assert.assertEquals;
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
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * XtextProxyUtil integration test.
 * 
 * @see http://rd.oams.com/browse/DS-7367
 * @see org.eclipse.emf.eson.util.tests.XtextProxyUtilESONTest
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(T24UIInjectorProvider.class)
public class DS7367ESONReferenceXtextProxyUtilTest {

	@Inject XtextProxyUtil xtextProxyUtil;
	@Inject ResourceSet resourceSet;
	EIO eio;
	
	@Test
	public void testESONReferenceAsStringFromProxy() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);

		// Do NOT load *.version & *.enquiry into RS here in this test
		// The point is to test what happens if cross ref. cannot be resolved
		
		URI uri = eio.getURI("TestCtxEnq.eson", DS7367ESONReferenceXtextProxyUtilTest.class);
		assertNotNull(uri);
		VersionContextEnquiry versionContextEnquiry = ml.getRootEObject(uri, VersionContextEnquiry.class);
		assertNotNull(versionContextEnquiry);
		assertEquals("theFirstContextEnquiry", versionContextEnquiry.getName());
		
		Version version = versionContextEnquiry.getAppliesTo();
		assertNotNull(version);
		String text1 = xtextProxyUtil.getProxyCrossRefAsString(versionContextEnquiry, version);
		assertEquals("CUSTOMER,CREATE", text1);
		
		AppliedEnquiry appliedEnquiry = versionContextEnquiry.getEnquiries().get(0);
		assertNotNull(appliedEnquiry);
		Enquiry enquiry = appliedEnquiry.getEnquiry();
		assertNotNull(enquiry);
		String text2 = xtextProxyUtil.getProxyCrossRefAsString(appliedEnquiry, enquiry);
		assertEquals("ACCT.BAL.TODAY", text2);
	}

}
