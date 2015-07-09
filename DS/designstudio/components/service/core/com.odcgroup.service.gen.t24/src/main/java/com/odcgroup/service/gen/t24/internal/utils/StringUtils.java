package com.odcgroup.service.gen.t24.internal.utils;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

// General String utilities
public class StringUtils {

	/**
	 * Turns the first character of a string in to an uppercase character 
	 * @param source The source string
	 * @return String Resultant string
	 */
	public static String upperInitialCharacter(String source) {
		if (org.apache.commons.lang.StringUtils.isNotBlank(source)) {
			final StringBuilder result = new StringBuilder(source.length());
			result.append(Character.toUpperCase(source.charAt(0))).append(source.substring(1));
			return result.toString();
		} else {
			return "";
		}
	}

	/**
	 * Turns the first character of a string in to a lowercase character 
	 * @param source The source string
	 * @return String Resultant string
	 */
	public static String lowerInitialCharacter(String source) {
		if (org.apache.commons.lang.StringUtils.isNotBlank(source)) {
			final StringBuilder result = new StringBuilder(source.length());
			result.append(Character.toLowerCase(source.charAt(0))).append(source.substring(1));
			return result.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * This method will simply convert XML Document into indented String 
	 * @param serviceDoc XML to write on file
	 * @param dirName Directory location to write 
	 * @param fileName Name of the xml file
	 * @return String Document xml in indented string
	 */
	public static String transformXMLToString(Document serviceDoc) {
		try {
			//write out the modified document to a new file
			TransformerFactory tFactory = TransformerFactory.newInstance();
			
			/* Older versions of Xalan still use this method of setting indent values.
			 * Attempt to make this work but don't completely fail if it's a problem.
			 */
			try {
				tFactory.setAttribute("indent-number", 4);
			} catch (IllegalArgumentException ilae) {
				System.out.println("Failed to set indent-number attribute; cause: " + ilae.getMessage());
			}
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			Source source = new DOMSource(serviceDoc);
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			
			/* Newer versions of Xalan will look for a fully-qualified output property in order to specify amount of
			 * indentation to use.  Attempt to make this work as well but again don't completely fail if it's a problem.
			 */
			try {
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(4));
			} catch (IllegalArgumentException ilae) {
				System.out.println("Failed to set indent-number attribute; cause: " + ilae.getMessage());
			}
			transformer.transform(source, xmlOutput);
			return (xmlOutput.getWriter().toString());
		} catch (TransformerException tex) {
			System.out.println("Failed to transform xml document into String. TransformationException : " + tex.getMessage());
		}
		return "";
	}
	
	public static int getNumberOccurances(String stringToSearch, String substring) {
		int count = 0, idx = 0;
	    while ((idx = stringToSearch.indexOf(substring, idx)) != -1) {
	        idx++;
	        count++;
	     }
	     return count;
	}
}
