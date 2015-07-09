package com.odcgroup.translation.generation.internal.generator.nls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationGenerator;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author atr
 */
class NLSGenerator implements ITranslationGenerator {
	private IProject project;

	private Map<String, Object> properties;

	private List<Locale> locales;
	
	private MessageStore messages = new MessageStore();

	/**
	 * @param project
	 * @param properties
	 */
	public NLSGenerator(IProject project, Map<String, Object> properties) {
		if (null == project) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}		
		this.project = project;
		this.properties = properties;
		initLocales();
	}
	
	private void initLocales() {
		ITranslationManager tm = TranslationCore.getTranslationManager(project);
		ITranslationPreferences prefs = tm.getPreferences();
		locales = new ArrayList<Locale>();
		locales.add(prefs.getDefaultLocale());
		locales.addAll(prefs.getAdditionalLocales());
	}	

	/**
	 * @return
	 */
	private final List<Locale> getLocales() {
		return this.locales;
	}

	/**
	 * @param text
	 * @param values
	 * @return
	 */
	public static String formatText(String text, Map<String, ?> values) {
		StringBuilder result = new StringBuilder(text);
		Set<String> keys = values.keySet();
		List<Object> valueList = new ArrayList<Object>();
		int currentPos = 1;
		for (String key : keys) {
			String formatKey = "%(" + key + ")";
			String formatPos = "%" + currentPos + "$s";
			int index = -1;
			while ((index = result.indexOf(formatKey, index)) != -1) {
				result.replace(index, index + formatKey.length(), formatPos);
				index += formatPos.length();
			}
			valueList.add(values.get(key));
			++currentPos;
		}
		return String.format(result.toString(), valueList.toArray());
	}

	@Override
	public void startGeneration() throws CoreException {
		writeBlockInf();
		writeCatalogues();
	}

	private void writeBlockInf() throws CoreException {
		new BlockInfWriter(project, properties, getLocales()).write();
	}

	private void writeCatalogues() throws CoreException {
		new CalatolguesWriter(project, properties, getLocales()).write();
	}
	
	@Override
	public void generate(ITranslationKey key) throws CoreException {
		messages.addTranslation(key, getLocales());
	}

	

	@Override
	public void endGeneration() throws CoreException {
		List<Locale> locales = getLocales();
		for(Locale locale : locales) {
			File messagesFile = getMessagesFile(locale);
			writeNewMessagesFile(messagesFile, messages.get(locale));
		}
	}

	private File getMessagesFile(Locale locale) throws CoreException {
		File messagesDir = getMessagesDir();
		File messagesFile = new File(messagesDir, getFileName(locale));
		return messagesFile;
	}

	private File getMessagesDir() throws CoreException {
		IFolder folder = getRootFolder();
		return toFile(folder);
	}

	private IFolder getRootFolder() throws CoreException {
		IFolder rootFolder = (IFolder) properties.get("folder");
		IFolder folder = rootFolder.getFolder(project.getName());
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
		return folder;
	}
	
	private String getFileName(Locale locale) {
		String lang = locale.toString();
		String fileName = "messages" + (!"en".equals(lang) ? "_" + lang : "") + ".xml";
		return fileName;
	}

	private void writeNewMessagesFile(File messagesFile, Map<String, String> messages) throws CoreException {
		if(!messagesFile.exists()) {
			new MessagesXMLSkeletonWriter(messagesFile).write();
		}
		new MessagesXMLMerger(messagesFile, messages, messagesFile).merge();
	}
	
	private File toFile(IFolder folder) {
		return new File(folder.getLocation().toOSString());
	}
}
