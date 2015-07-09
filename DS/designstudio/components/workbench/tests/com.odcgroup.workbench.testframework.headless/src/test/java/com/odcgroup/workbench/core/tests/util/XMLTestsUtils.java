package com.odcgroup.workbench.core.tests.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLTestsUtils {
	
	private static Logger logger = LoggerFactory.getLogger(XMLTestsUtils.class);
	
	public static void assertXml(String message, String expectedXml, String generatedXml,
			final String... skippedXPaths) throws SAXException, IOException {
				
		if (StringUtils.isEmpty(expectedXml)) {
			System.out.println("The expectedXml is empty");
			System.out.println("The generated XML is:\n" + generatedXml);
			Assert.fail("The expectedXml is empty.");
		}
		
		// Compare
		XMLUnit.setIgnoreWhitespace(true);
		Diff myDiff = new Diff(expectedXml, generatedXml);
		myDiff.overrideDifferenceListener(new DifferenceListener() {
			@Override
			public int differenceFound(Difference difference) {
				for (String skippedXPath : skippedXPaths) {
					if (difference.getControlNodeDetail().getXpathLocation().equals(skippedXPath)) {
						return 2;
					}
				}
				return 0;
			}

			@Override
			public void skippedComparison(Node arg0, Node arg1) {
			}
		});

		if (!myDiff.similar()) {
			displayXpathOfAllDifferences(expectedXml, generatedXml, skippedXPaths);
		}

		Assert.assertTrue((message!=null?message:"") +
				"\n(Please note the spacing and the unused namespaces are ignored)\n" + myDiff.toString(), myDiff.similar());
	}
	
	private static void displayXpathOfAllDifferences(String expectedXml, String generatedXml, final String... skippedXPaths) {
		try {
			XMLUnit.setIgnoreWhitespace(true);
			Diff myDiff = new Diff(expectedXml, generatedXml);
			System.out.println("Displaying differences...");
			class Counter { public int nbDifferences = 0; };
			final Counter counter = new Counter();

			myDiff.overrideDifferenceListener(new DifferenceListener() {
				@Override
				public int differenceFound(Difference difference) {
					String skip = (skippedXPaths == null || skippedXPaths.length == 0) ? "" : skippedXPaths[0]; 
					if(!difference.getControlNodeDetail().getXpathLocation().equals(skip)){
						System.out.println("\"" + difference.getControlNodeDetail().getXpathLocation() + "\", ");
						counter.nbDifferences++;
					}
					return 2;
				}
	
				@Override
				public void skippedComparison(Node arg0, Node arg1) {
				}
			});
			// Starts the analysis
			myDiff.similar();
			System.out.println(counter.nbDifferences + " difference(s) found.");
			System.out.println("Expected XML:\n" + formatXml(expectedXml));
			System.out.println("Generated XML:\n" + formatXml(generatedXml));
		} catch (Exception e) {
			System.out.println("A problem occured when the differences were dumped");
			e.printStackTrace();
			logger.error("InterruptedException", e);
		} 
	}
	
	private static String formatXml(String unformattedXml) {
		try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setValidating(false);
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource sourceXML = new InputSource(new StringReader(unformattedXml));
	        Document xmlDoc = db.parse(sourceXML);
	        Element e = xmlDoc.getDocumentElement();
	        e.normalize();
	
	    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	//initialize StreamResult with File object to save to file
	    	StreamResult result = new StreamResult(new StringWriter());
	    	DOMSource source = new DOMSource(e);
	    	transformer.transform(source, result);
	    	String xmlString = result.getWriter().toString();
	    	return xmlString;
		} catch (Exception e) {
			System.out.println("Unable to format the xml");
			return unformattedXml;
		}
	}

}
