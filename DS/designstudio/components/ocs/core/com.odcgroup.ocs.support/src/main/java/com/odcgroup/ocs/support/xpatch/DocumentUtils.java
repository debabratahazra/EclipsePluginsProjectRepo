package com.odcgroup.ocs.support.xpatch;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.xml.sax.SAXException;

class DocumentUtils {

	/** The document builder */
	private static DocumentBuilder BUILDER;
	private static Transformer TRANSFORMER;

	/**
	 * Initialize internal instance of XMLCatalog
	 */
	static {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			builderFactory.setValidating(false);
			builderFactory.setExpandEntityReferences(false);
			builderFactory.setNamespaceAware(false);
			builderFactory
					.setAttribute(
							"http://apache.org/xml/features/nonvalidating/load-external-dtd",
							Boolean.FALSE);
			BUILDER = builderFactory.newDocumentBuilder();
			TRANSFORMER = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	protected Document parse(File file) throws SAXException, IOException {
		synchronized (BUILDER) {
			return BUILDER.parse(file);
		}
	}

	protected void write(Document document, OutputStream out)
			throws IOException {
		synchronized (TRANSFORMER) {
			// Set the DOCTYPE output option on the transformer
			// if we have any DOCTYPE declaration in the input xml document
			final DocumentType doctype = document.getDoctype();
			Properties props = new Properties();
			if (null != doctype && null != doctype.getPublicId()) {
				props.put(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
				props.put(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			}
			TRANSFORMER.setOutputProperties(props);

			try {
				TRANSFORMER.transform(new DOMSource(document),
						new StreamResult(out));
			} catch (TransformerException e) {
				throw (IOException) new IOException().initCause(e);
			}
		}
	}
}
