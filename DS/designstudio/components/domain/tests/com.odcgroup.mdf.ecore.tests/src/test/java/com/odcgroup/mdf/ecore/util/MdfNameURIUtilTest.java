package com.odcgroup.mdf.ecore.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * Unit Test, incl. performance check, for MdfNameURIUtil.
 *
 * @author Michael Vorburger
 */
public class MdfNameURIUtilTest {

	@Test
	public void testMdfNameURIUtil() {
		checkMdfNameURIUtil(PrimitivesDomain.DATE_TIME.getQualifiedName());
		checkMdfNameURIUtil(MdfNameFactory.createMdfName("TestDomain", "Something"));
	}
	
	private void checkMdfNameURIUtil(MdfName mdfName) {
		URI uri = MdfNameURIUtil.createURI(mdfName);
		assertTrue(MdfNameURIUtil.isMdfNameURI(uri));
		assertEquals(mdfName, MdfNameURIUtil.getMdfName(uri));		
	}

	@Test
	public void testMdfDomainName() {
		checkMdfNameURIUtil(MdfNameFactory.createMdfName("MyDomain"));
	}
	
	/**
	 * See links to background about EMF performance bug inside MdfNameURIUtil.  
	 */
	@Test
	public void testMdfNameURIUtilPerformance() throws IOException {
		URL url = Resources.getResource(this.getClass(), "mdfWithManyStrings.txt");
		List<String> mdfNames = Resources.readLines(url, Charsets.UTF_8);
		
		long startTime = System.currentTimeMillis();
		for (String qMdfName : mdfNames) {
			MdfName mdfName = MdfNameFactory.createMdfName(qMdfName);
			checkMdfNameURIUtil(mdfName);
		}
		long duration = System.currentTimeMillis() - startTime;
		//System.out.println("It took " + duration + "ms to run " + mdfNames.size() + " names through MdfNameURIUtil()");
		assertTrue("MdfNameURIUtil is too slow! (or you're running this test on a REALLY slow machine? ;-)", duration < 2000);
	}
	
}
