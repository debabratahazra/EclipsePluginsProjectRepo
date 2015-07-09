package com.odcgroup.translation.generation.internal.generator.nls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.odcgroup.translation.generation.TranslationGenerationCore;
/**
 * Takes a set of updated and new translations and applies them to a messages.xml
 *  file updating any existing messages and adding new ones to the end 
 *
 * @author amc
 *
 */
public class MessagesXMLMerger  {

	private static final String INDENT = "    ";
	
	private File existingMessagesXml;
	private Map<String, String> newTranslations;
	private File output;

	public MessagesXMLMerger(File existingMessagesXml, Map<String, String> newTranslations, File output) {
		this.existingMessagesXml = existingMessagesXml;
		this.newTranslations = newTranslations;
		this.output = output;
	}

	public void merge() throws CoreException {
		try {
			Document document = createDocument();
			replaceWithNewAndUpdatedTranslations(document);
			save(document);
		}
		catch(Exception ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while generating translations in file "+existingMessagesXml.getPath(), ex);
			throw new CoreException(status);
		}
	}

	private Document createDocument() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(existingMessagesXml);
		return document;
	}

	private void replaceWithNewAndUpdatedTranslations(Document document) {
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
	}

	private void save(Document document) throws FileNotFoundException, IOException {
		XMLOutputter outputter = getOutputter();
		FileOutputStream fileOut = new FileOutputStream(output);
		try {
			outputter.output(document, fileOut);
		}
		finally {
			fileOut.close();
		}
	}

	private XMLOutputter getOutputter() {
		XMLOutputter outputter = new XMLOutputter();
		setOutputterFormat(outputter);
		return outputter;
	}

	private void setOutputterFormat(XMLOutputter outputter) {
		Format format = Format.getPrettyFormat();
		format.setIndent(INDENT);
		format.setEncoding(MessagesXMLConstants.ENCODING);
		outputter.setFormat(format);
	}
}
