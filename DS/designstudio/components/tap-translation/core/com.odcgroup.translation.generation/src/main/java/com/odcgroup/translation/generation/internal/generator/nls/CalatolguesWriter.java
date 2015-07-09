package com.odcgroup.translation.generation.internal.generator.nls;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.generation.TranslationGenerationCore;

public class CalatolguesWriter {

	private static String EOL = MessagesXMLConstants.EOL;
	private IProject project;
	private Map<String, Object> properties;
	private List<Locale> locales;

	public CalatolguesWriter(IProject project, Map<String, Object> properties,
			List<Locale> locales) {
		this.project = project;
		this.properties = properties;
		this.locales = locales;
	}

	public void write() throws CoreException {
		IFolder rootFolder = (IFolder) properties.get("folder");
		IFolder folder = rootFolder.getFolder("NLS");
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
		
		String location = folder.getLocation().toOSString();
		for (Locale locale: locales) {
			String lang = LanguageUtils.getXmlLangCode(locale);
			String catalogue = createCatalogue(lang);
			writeCatalogue(location, lang, catalogue);
		}
	}

	private String createCatalogue(String lang) {
		StringBuilder catalogue = new StringBuilder();
		catalogue.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		catalogue.append(EOL);
		catalogue.append("<catalogue xml:lang=\"" + lang + "\" xmlns=\"http://apache.org/cocoon/i18n/2.1\"");
		catalogue.append(EOL);
		catalogue.append("           xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		catalogue.append(EOL);
		catalogue.append("           xmlns:cinclude=\"http://apache.org/cocoon/include/1.0\"");
		catalogue.append(EOL);
		catalogue.append("           xsi:schemaLocation=\"http://apache.org/cocoon/i18n/2.1 catalogue.xsd\">");
		catalogue.append(EOL);
		catalogue.append("	<cinclude:include src=\"messages:resource://%messages-dir%/messages_" + lang + ".xml\" select=\"*/*\"/>");
		catalogue.append(EOL);
		catalogue.append("</catalogue>");
		catalogue.append(EOL);
		return catalogue.toString();
	}

	private void writeCatalogue(String location, String lang, String catalogue)
			throws CoreException {
		String name = "messages_" + lang + ".xml";
		String filename = location + "/" + name;
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(catalogue);
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while generating catalogue translations", ex);
			throw new CoreException(status);
		}
	}

}
