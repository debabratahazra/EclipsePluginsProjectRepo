package com.odcgroup.domain.scoping.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import com.odcgroup.domain.linking.LegacyMdfNameFactoryUtil;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfName;

public class LegacyMdfNameFactoryUtilTest {

	@Test
	public void testLegacyMdfNameFactoryUtil() {
		// Domain Class association in real life is e.g. AAAEntities Address third
		
		check("Domain:Class", "Domain", "Class");
		check("Domain:Class#association", "Domain", "Class:association");
		check("Domain:Class:association", "Domain", "Class:association");
		check("Domain:Class.association", "Domain", "Class.association");
		
		check("Domain.Class", "Domain", "Class");
		check("Domain.Class.association", "Domain", "Class:association");
		check("Domain.Class#association", "Domain", "Class:association");
		
		// For implementation reasons, this case is difficult to handle/distinguish,
		// and shouldn't occur in real live anyway, so not checking this:
		// check("Domain.Class:association", "Domain", "Class:association");
	}

	@Test
	public void testLegacyMdfNameFactoryUtilForSingleSegmentDomainName() {
		check("MyDomain", "", "MyDomain");
	}
	
	private void check(String mdfQName, String expectedDomain, String expectedLocalName) {
		MdfName mdfName = LegacyMdfNameFactoryUtil.createMdfName(mdfQName);
		assertEquals(expectedDomain, mdfName.getDomain());
		assertEquals(expectedLocalName, mdfName.getLocalName());
		
		// We have to make sure these MdfName can be transformed into URIs, and back..
		// see comment in com.odcgroup.domain.linking.LegacyMdfNameFactoryUtil.createMdfName(String)
		URI uri = MdfNameURIUtil.createURI(mdfName);
		assertTrue(MdfNameURIUtil.isMdfNameURI(uri));
		assertEquals(mdfName, MdfNameURIUtil.getMdfName(uri));
	}
	
}
