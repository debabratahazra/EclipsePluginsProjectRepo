package com.odcgroup.page.util;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;

/**
 * Contains a utility method for creating a Transformer. We suppose that Xalan is used as the transformation API. I
 * deliberately call this XalanUtils since createTransformer uses Xalan-specific commands.
 * 
 * @author Gary Hayes
 */
public class XalanUtils {

	/**
	 * Private constructor.
	 */
	private XalanUtils() {
	}

	/**
	 * Creates a new Transformer. This method corrects a bug in the Java API (up until 1.5.06) in which the indentation
	 * does not work correctly.
	 * 
	 * @return Transformer The Transformer
	 */
	public static Transformer createTransformer() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();

			// Correct the bug in the Java API
			tf.setAttribute("indent-number", new Integer(2));

			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty(OutputKeys.METHOD, "xml");

			// Correct the bug in the Java API
			t.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			return t;
		} catch (TransformerConfigurationException tce) {
			Logger.error("Unable to create the Transformer", tce);
			throw new PageException("Unable to create the Transformer", tce);
		}
	}
}
