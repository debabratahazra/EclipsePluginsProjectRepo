package com.odcgroup.translation.generation.internal.generator.nls;

import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.CATALOGUE_ELEMENT_NAME;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.ENCODING;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.I18N_NAMESPACE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.generation.TranslationGenerationCore;

/**
 * Responsible for writing the XML header and catalogue element only for messages.xml
 * @author amc
 *
 */
public class MessagesXMLSkeletonWriter {

	private static String EOL = MessagesXMLConstants.EOL;
	private File outputFile;
	
	public MessagesXMLSkeletonWriter(File outputFile) {
		this.outputFile = outputFile;
	}

	public void write() throws CoreException {
		final String XML_START = 
			"<?xml version=\"1.0\" encoding=\""+ENCODING+"\" standalone=\"no\"?>" + EOL +
		    "<!--**************************************************************************" + EOL +
		    "DO NOT MODIFY THIS FILE!" + EOL +
		    "" + EOL +
		    "This file has been generated automatically by the Design Studio." + EOL +
		    "Any changes will be lost when it is regenerated." + EOL +
		    "***************************************************************************" + EOL +
		    "-->" + EOL +
     		"<"+CATALOGUE_ELEMENT_NAME+" xmlns=\""+I18N_NAMESPACE+"\" " +
		               "xmlns:cinclude=\"http://apache.org/cocoon/include/1.0\" " +
		               "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
		               "xml:lang=\"en\" " +
		               "xsi:schemaLocation=\"http://apache.org/cocoon/i18n/2.1 catalogue.xsd\">" + EOL; 				
		
		final String XML_END = 
			"</"+CATALOGUE_ELEMENT_NAME+">";
		    
		try {
			Writer writer = getNLSWriter();
			writer.write(XML_START);
			writer.write(XML_END);
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while generating messages.xml skeleton to file "+outputFile, ex);
			throw new CoreException(status);
		}
	}
	
	private Writer getNLSWriter() throws IOException, CoreException {
		Writer writer = new FileWriter(outputFile);
		return writer;
	}
	
}