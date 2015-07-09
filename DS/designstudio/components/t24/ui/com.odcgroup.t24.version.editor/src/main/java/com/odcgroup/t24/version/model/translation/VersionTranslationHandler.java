package com.odcgroup.t24.version.model.translation;


import java.util.ArrayList;
import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;


/**
 * @author arajeshwari
 * 
 * This class handles the language to be set on the model.
 */
public class VersionTranslationHandler {
	
	private ITranslationManager translationManager;
	
	private ITranslationPreferences preferences;
	
	private ArrayList<Locale> locales = null;
	
	private VersionDesignerEditor editor;

	public VersionTranslationHandler(VersionDesignerEditor editor) {
		this.editor = editor;
	}

	/**
	 * @return
	 */
	public ArrayList<Locale> getLocales() {
		getProjectTranslations();
		return locales;
	}

	/**
	 *  Gets the translations from Preferences.
	 */
	private void getProjectTranslations() {
		if(editor != null && editor instanceof VersionDesignerEditor){
			IProject project = ((VersionDesignerEditor)editor).getProject();
			translationManager = TranslationCore.getTranslationManager(project);     
			preferences = translationManager.getPreferences();
			
			locales = new ArrayList<Locale>();
			locales.add(preferences.getDefaultLocale());                       // “Default Language” in the dialog
			locales.addAll(preferences.getAdditionalLocales());                // “Interest Languages” in the dialog 
		}
	}
}
