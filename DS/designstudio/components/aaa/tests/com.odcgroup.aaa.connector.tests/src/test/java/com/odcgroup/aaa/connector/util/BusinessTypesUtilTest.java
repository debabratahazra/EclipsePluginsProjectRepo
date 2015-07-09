package com.odcgroup.aaa.connector.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.mdf.metamodel.MdfDomain;

public class BusinessTypesUtilTest extends TestCase {

	public void testGetBusinessTypesDomain() throws IOException {
		MdfDomain domain = BusinessTypesUtil.getBusinessTypesDomain(new XtextResourceSet());
		assertEquals("AAABusinessTypes", domain.getName());
	}

}