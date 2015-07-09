package com.odcgroup.t24.version.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;

/**
 * Integration Test for the XtextProxyUtil.
 * 
 * This test is here instead of in workbench-core-tests, because it
 * difficult to unit test XtextProxyUtil without a real example Xtext language.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
public class VersionXtextProxyUtilTest {

	// Originally published as https://github.com/vorburger/com.temenos.ds.op/search?q=XtextProxyUtilTest&ref=cmdform
	// Copy/pasted here in interim before DS moves to DS NG... (if ever)

	@Inject XtextProxyUtil proxyUtil;
	@Inject ParseHelper<Version> ph;

	@Test
	public void testGetProxyCrossRefAsString() throws Exception {
		Version version = ph.parse("Screen DOES:NOTEXIST,SHORT");
		assertEquals("SHORT", version.getShortName());

		EObject proxyRef = (EObject) version.getForApplication();
		assertTrue(proxyRef.eIsProxy());
		String text = proxyUtil.getProxyCrossRefAsString(version, proxyRef);
		assertEquals("DOES:NOTEXIST", text);
	}
}
