package com.odcgroup.edge.t24.generation.util;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * TODO: Document me!
 *
 * @author sfile
 *
 */
public class TestXmlCompare extends TestCase {

	@Test
	public void testMessageEffeciency() throws Exception {
		
		File xml = new File("src/test/resources/xml/compare/Sheeraz.xml");
		XmlCompare test = new XmlCompare();
		test.metaVersusData(xml);
		test.printMetaVersusData();
	}
	
	@Test
	public void testXmlCompare() throws Exception {
		
		File left = new File("src/test/resources/xml/compare/A.xml");
		File right = new File("src/test/resources/xml/compare/BsameAsA.xml");
		ArrayList<String> ignoreAttributes = new ArrayList<String>();
		
		ignoreAttributes.add("aa");
		
		assertTrue("Is the same ?", XmlCompare.isTheSame(left, right, ignoreAttributes));
		
		right = new File("src/test/resources/xml/compare/CdifferentFromA.xml");
		
		assertFalse("Is different ?", XmlCompare.isTheSame(left, right, ignoreAttributes));
		
		
	}

}
