package com.odcgroup.edge.t24.generation;

import static junit.framework.Assert.assertEquals;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.enquiry.EnquiryInjectorProvider;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;

/**
 * Test for EdgeMapper.
 * 
 * This test can be run as a non-OSGi std JUnit.
 * 
 * @author Michael Vorburger, for Simon File.
 */
@RunWith(XtextRunner.class)
@InjectWith(EnquiryInjectorProvider.class)
public class EdgeEnquiryMapperTest extends AbstractEdgeGeneratorTest {

	@Override
	protected String[] getModelsNeededForTests() {
		return new String[] { "/domain/TT/TT_Contract.domain", "/enquiry/edgepoc/some.enquiry" };
	}
		
	@Test
	public void testEdgeEnquiryMapper() throws Exception {
		Enquiry enquiry = getEnquiry("/enquiry/edgepoc/some.enquiry");
		assertEquals("SampleEnquiry", enquiry.getName());
		
//		mapper.map(enquiry);
		
	}

}
