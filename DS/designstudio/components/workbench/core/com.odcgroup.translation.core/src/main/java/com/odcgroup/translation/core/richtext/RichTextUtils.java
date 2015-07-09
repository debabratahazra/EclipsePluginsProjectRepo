package com.odcgroup.translation.core.richtext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.xml.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.translation.core.TranslationCore;

/**
 * <p>
 * Implements some useful operations related to rich text string
 * </p>
 * <p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 * 
 * @author atr
 */
public final class RichTextUtils {

	/** the singleton */
	private static RichTextUtils INSTANCE = new RichTextUtils();

	/**
	 * This XML schema defined the grammar for string that contains rich text
	 * decorators
	 */
	private static final String RICHTEXT_XML_SCHEMA = "/schema/richtext_translation.xsd";

	public static final int DEFAULT_FONT_HEIGHT = 10;

	public static final String DEFAULT_FONT_NAME = "Arial";

	/**
	 * Returns <code>"true"</code> if the given string represents a rich text
	 * string. This test is valid only where the string is build either
	 * programatically or with a RichText Editor that produces a string conform
	 * to the Rich Text Grammar (XML Schema).
	 * <p>
	 * We assume too that the given string does not contains the XML header.
	 * <p>
	 * 
	 * @param richText
	 *            the string to be tested.
	 * @return <code>"true"</code> if the given string contains at least one
	 *         rich text decorators.
	 */
	public static final boolean isRichRext(String richText) {
		return INSTANCE.doIsRichText(richText);
	}

	public static String removeRichTextDecorator(String richText)
			throws ParserConfigurationException, SAXException, IOException {
		return INSTANCE.doRemoveRichTextDecorator(richText);
	}
	
	public static String escapeRichTextTag(String text) {
		return INSTANCE.doEscapeRichTextTags(text);
	}

	public static String unescapeRichTextTags(String text) {
		return INSTANCE.doUnescapeRichTextTags(text);
	}

	/**
	 * @param richText
	 * @param handler
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void parseRichText(String richText, IRichTextHandler handler)
			throws ParserConfigurationException, SAXException, IOException {
		INSTANCE.doParseRichText(richText, handler);
	}

	/**
	 * @param richText
	 * @return
	 * @throws Exception
	 */
	public static String convertRichTextToHTML(String richText)
			throws Exception {
		return INSTANCE.doConvertRichTextToHTML(richText);
	}

	/**
	 * Validate a string that may contain rich text decorators agains the XML
	 * schema for rich text expression. The string should not contains any xml
	 * header. It will be automatically be added by the validator
	 * 
	 * @param richtext
	 *            the rich text string
	 * @exception
	 */
	public static void validateRichText(String richtext) throws SAXException,
			IOException {
		INSTANCE.doValidateRichText(richtext);
	}

	private Object lock = new Object();

	private Validator richTextValidator;

	/**
	 * Add an XML header to the given string, and add the root element if
	 * needed.
	 * 
	 * @param richText
	 * @return the XML string conform to the rich text XML schema
	 */
	private String buildXMLString(String richText) {
		// Add the XML header to the rich text string
		StringBuilder buffer = new StringBuilder();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

		if (!richText.startsWith("<rt>")) {
			// apparently not a rich text content.
			buffer.append("<rt>");
			if (richText != null) {
				buffer.append(richText);
			}
			buffer.append("</rt>");
		} else {
			buffer.append(richText);
		}
		return buffer.toString();
	}

	/**
	 * @param richText
	 * @return
	 */
	private final boolean doIsRichText(String richText) {
		if (StringUtils.isBlank(richText)) {
			return false;
		}
		String rt = richText.trim();
		return rt.startsWith("<rt>") && rt.endsWith("</rt>");
	}
	
	private String doEscapeRichTextTags(String text) {
		String str = text;
		if (doIsRichText(text)) {
			int start = text.indexOf("<rt>");
			int end = text.lastIndexOf("</rt>");
			if (start != -1 && end != -1) {
				str = "&lt;rt&gt;" + text.subSequence(start+4, end) + "&lt;/rt&gt;";
			}
		}
		return str;
	}

	private String doUnescapeRichTextTags(String text) {
		String str = text;
		if (!doIsRichText(text)) {
			int start = text.indexOf("&lt;rt&gt;");
			int end = text.lastIndexOf("&lt;/rt&gt;");
			if (start != -1 && end != -1) {
				str = "<rt>" + text.subSequence(start+10, end) + "</rt>";
			}
		}
		return str;
	}

	/**
	 * @param richText
	 * @return
	 * @throws Exception
	 */
	private String doConvertRichTextToHTML(String richText) throws Exception {
		RichTextToHTMLHandler handler =  new RichTextToHTMLHandler();
		RichTextUtils.parseRichText(richText, handler);
		return handler.getResult();
	}

	/**
	 * @param richText
	 * @param handler
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private void doParseRichText(String richText, IRichTextHandler handler)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		ByteArrayInputStream bis = new ByteArrayInputStream(buildXMLString(
				richText).getBytes("UTF-8"));
		saxParser.parse(bis, new SAXRichTextHandler(handler));
	}

	private SAXParser saxFilter;
	private RichTextFilterHandler saxFilterHandler;
	
	private String doRemoveRichTextDecorator(String richText)
			throws ParserConfigurationException, SAXException, IOException {
		
		String result = richText;
		
		if (doIsRichText(richText)) {
			if (saxFilter == null) {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				saxFilter = factory.newSAXParser();
				saxFilterHandler = new RichTextFilterHandler();
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(buildXMLString(
					richText).getBytes("UTF-8"));
			saxFilter.parse(bis, new SAXRichTextHandler(saxFilterHandler));
			result = saxFilterHandler.getResult();
		} else if (StringUtils.isNotBlank(richText)) {
			result = doUnescapeRichTextTags(richText);
		}
		return result;
	}

	/**
	 * @param richText
	 * @throws SAXException
	 * @throws IOException
	 */
	private void doValidateRichText(String richText) throws SAXException,
			IOException {

		if (StringUtils.isBlank(richText)) {
			// empty or blank string is valid.
			return;
		}

		String xmlString = buildXMLString(richText);

		// validating the SAX source against the schema
		ByteArrayInputStream bis = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
		SAXSource source = new SAXSource(new InputSource(bis));

		// could raise an exception if an error is found
		getRichTextValidator().validate(source);

	}

	/**
	 * @return the validator based on the Rich Text XML Schema
	 * @throws IOException
	 * @throws SAXException
	 */
	protected Validator getRichTextValidator() throws IOException, SAXException {
		synchronized (lock) {
			if (richTextValidator == null) {
				URL url = FileLocator.find(TranslationCore.getDefault()
						.getBundle(), new Path(RICHTEXT_XML_SCHEMA), null);
				URL url2 = FileLocator.resolve(url);
				Source source = new StreamSource(url2.openStream());
				String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
				SchemaFactory factory = SchemaFactory.newInstance(language);
				Schema schema = factory.newSchema(source);
				richTextValidator = schema.newValidator();
			}
		}
		return richTextValidator;
	}
	
	

	/** */
	private RichTextUtils() {
	}

}
