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

import com.odcgroup.translation.generation.TranslationGenerationCore;

/**
 * Writes out block.xml to BLOCK-INF
 *
 * @author amc
 *
 */
public class BlockInfWriter {

	private static String EOL = MessagesXMLConstants.EOL;
	private IProject project;
	private Map<String, Object> properties;
	private List<Locale> locales;
	
	public BlockInfWriter(IProject project, Map<String, Object> properties, List<Locale> locales) {
		this.project = project;
		this.properties = properties;
		this.locales = locales;
	}

	public void write() throws CoreException {

		IFolder rootFolder = (IFolder) properties.get("folder");
		IFolder folder = rootFolder.getFolder("BLOCK-INF");
		if (!folder.exists()) {
			folder.create(false, true, null);
		}

		StringBuilder patch = new StringBuilder();
		patch.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		patch.append(EOL);
		patch.append("<!--");
		patch.append(EOL);
		patch.append("===============================================================================");
		patch.append(EOL);
		patch.append("		               WUI-BLOCK DESCRIPTOR");
		patch.append(EOL);
		patch.append("===============================================================================");
		patch.append(EOL + EOL);
		patch.append("  Block Name:           " + project.getName().toUpperCase());
		patch.append(EOL + EOL);
		patch.append("===============================================================================");
		patch.append(EOL);
		patch.append("-->");
		patch.append(EOL);
		patch
				.append("<block xmlns=\"http://www.odcgroup.com/wui-block-descriptor\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\"http://www.odcgroup.com/"
						+ project.getName()
						+ "/1.0.0\" xsi:schemaLocation=\"http://www.odcgroup.com/wui-block-descriptor wui-block-descriptor.xsd\">");
		patch.append(EOL + EOL);
		patch.append("  <name>" + project.getName() + "</name>");
		patch.append(EOL);
		patch.append("  <description>Design Studio generated WUI BLOCK for " + project.getName() + "</description>");
		patch.append(EOL);
		patch.append("  <exposes>");
		patch.append(EOL);
		patch.append("        <nls-messages messages-dir=\"" + project.getName() + "\"/>");
		patch.append(EOL);
		
		
		//DS-5875
		boolean defaultPrefChanged = false;
		if (locales.size() >= 3) {
			if (!((locales.get(0).toString().equals("en")) && (locales.get(1).toString().equals("fr")) && (locales.get(2).toString().equals("de")))) {
				defaultPrefChanged = true;
			}
		}else{
			defaultPrefChanged = true;
		}
		if(defaultPrefChanged == true){
			writeLocales(patch);
		}
		patch.append("  </exposes>");
		patch.append(EOL);
		patch.append("</block>");

		String location = folder.getLocation().toOSString();
		String name = "block.xml";
		String filename = location + "/" + name;
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(patch.toString());
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while generating translations", ex);
			throw new CoreException(status);
		}

	}

	private void writeLocales(StringBuilder patch) {
		for (Locale locale : locales) {
			patch.append("        <nls-language locale=\"" + locale.toString().replace("_", "-") + "\"/>");
			patch.append(EOL);
		}
	}
	
}
