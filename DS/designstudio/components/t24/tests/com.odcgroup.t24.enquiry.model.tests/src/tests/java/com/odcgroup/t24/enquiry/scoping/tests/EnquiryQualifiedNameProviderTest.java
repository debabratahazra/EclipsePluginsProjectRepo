package com.odcgroup.t24.enquiry.scoping.tests;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.enquiry.EnquiryInjectorProvider;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;

/**
 * DS-7351.
 * 
 * If you ever have the bright idea to change the EnquiryQualifiedNameProvider,
 * then don't just adapt this test, but double & triple check that ESON will
 * work with a "non-standard" IQualifiedNameProvider, e.g. if you create a
 * QN with 1 Segment containing dots (it does not work; that was the root
 * cause of issue DS-7351).
 *
 * @see com.odcgroup.edge.t24ui.tests.DS7351EnquiryESONBrokenReferenceTest
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(EnquiryInjectorProvider.class)
public class EnquiryQualifiedNameProviderTest {

	@Inject IQualifiedNameProvider enquiryQualifiedNameProvider;
	
	@Test
	public void testEnquiryQualifiedNameProvider() {
		Enquiry enquiry = EnquiryFactory.eINSTANCE.createEnquiry();
		enquiry.setName("ACCT.BAL.TODAY");
		QualifiedName qn = enquiryQualifiedNameProvider.getFullyQualifiedName(enquiry);
		assertEquals(3, qn.getSegmentCount());
		assertEquals("ACCT", qn.getFirstSegment());
		assertEquals("TODAY", qn.getLastSegment());
	}

}
