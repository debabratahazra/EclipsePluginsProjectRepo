package com.odcgroup.translation.generation.internal.generator.xls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.common.collect.Lists;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.generation.ITranslationGenerator;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.generation.xls.IColumnProvider;

/**
 * @author atr
 */
class XLSGenerator implements ITranslationGenerator {

	private IProject project;

	private Map<String, Object> properties;

	private List<Locale> locales;

	private Iterable<IColumnProvider> providers;
	
	/** */
	private List<String[]> messages = Lists.newArrayList();

	/**
	 * @param localeSet
	 */
	private void init() {
		ITranslationManager tm = TranslationCore.getTranslationManager(project);
		ITranslationPreferences prefs = tm.getPreferences();
		locales = new ArrayList<Locale>();
		locales.add(prefs.getDefaultLocale());
		locales.addAll(prefs.getAdditionalLocales());
		providers = TranslationGenerationCore.getColumnProviders();

	}

	/**
	 * @return
	 */
	protected final List<Locale> getLocales() {
		return locales;
	}

	@Override
	public void startGeneration() throws CoreException {
		// all is done is endGeneration
	}

	@Override
	public void endGeneration() throws CoreException {
		try {
			IFolder rootFolder = (IFolder) properties.get("folder");
			IFolder folder = rootFolder.getFolder("Doc");
			if (!folder.exists()) {
				folder.create(false, true, null);
			}
			
			for(int i=0;i<messages.size();i++) {
				String[] oldArray = messages.get(i);
				String[] newArray = new String[oldArray.length + 3];
				int j=0;
				for(j=0;j<oldArray.length;j++) {
					newArray[j] = oldArray[j];
				}
				
				if(newArray[1] != null) {
					newArray[j] = newArray[1];
					if (RichTextUtils.isRichRext(newArray[j])) {
						newArray[j] =  RichTextUtils.convertRichTextToHTML(newArray[j]);
					}
				}
				j++;
				
				if(newArray[2] != null) {
					newArray[j] = newArray[2];
					if (RichTextUtils.isRichRext(newArray[j])) {
						newArray[j] =  RichTextUtils.convertRichTextToHTML(newArray[j]);
					}
				}
				j++;
				
				if(newArray[3] != null) {
					newArray[j] = newArray[3];
					if (RichTextUtils.isRichRext(newArray[j])) {
					newArray[j] =  RichTextUtils.convertRichTextToHTML(newArray[j]);
					}
				}
				j++;
				
				messages.remove(i);
				messages.add(i, newArray);
			}
			
			
			IFile file = folder.getFile("Translation.xls");
			XLSWriter writer = new XLSWriter(project.getName(), messages, getLocales());
			writer.write(file);
		} catch (IOException ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while creating excel file", ex);
			throw new CoreException(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void generate(ITranslationKey key) throws CoreException {
		try {
			// DS-4024: Do not export read only translation
			if (key.getTranslation().isProtected())
				return;
			
			for (ITranslationKind kind : key.getTranslationKinds()) {
				String msgKey = key.getKey(kind);
				if (msgKey != null) {
					// Only add non empty translations
					if(!hasTranslations(key, kind, getLocales())) continue;

					List<String> values = new ArrayList<String>();
					values.add(msgKey);
					
					// add additional columns after the key column
					for(IColumnProvider provider : providers) {
						if(provider.isBeforeTranslations()) {
							String message = provider.getContent(key, kind);
							values.add(message);
						}
					}

					for (Locale locale : getLocales()) {
							String message = key.getMessage(kind, locale);
							values.add(message);
					}
					
					// add additional columns after the translation columns
					for(IColumnProvider provider : providers) {
						if(!provider.isBeforeTranslations()) {
							String message = provider.getContent(key, kind);
							values.add(message);
						}
					}
					
					messages.add(values.toArray(new String[values.size()]));
				}
			}
		} catch (TranslationException ex) {
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, 
					"Error while exporting a translation", ex);
			throw new CoreException(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean hasTranslations(ITranslationKey key, ITranslationKind kind,
			List<Locale> locales) throws TranslationException {
		for (Locale locale : locales) {
			if(!StringUtils.isEmpty(key.getMessage(kind, locale))) return true;
		}
		return false;
	}

	public XLSGenerator(IProject project, Map<String, Object> properties) {
		if (null == project) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}
		this.project = project;
		this.properties = properties;
		init();
	}
}
