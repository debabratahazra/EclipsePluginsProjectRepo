package com.odcgroup.workbench.ui.internal.properties;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.preferences.FieldEditorOverlayPage;

/**
 * The preference and property page for the OFS Workbench in general
 *  
 * @author Kai Kreuzer
 *
 */
public class OfsPropertyPage extends FieldEditorOverlayPage implements IWorkbenchPreferencePage {

	public OfsPropertyPage() {
		super("OFS Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
	}
	
	@Override
	protected String getPluginId() {
    	return OfsCore.PLUGIN_ID;
	}

	public void init(IWorkbench workbench) {
	}	
}