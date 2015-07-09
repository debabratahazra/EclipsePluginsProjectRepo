package com.odcgroup.server.ui.preferences;

import java.io.IOException;

import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public abstract class AbstractServerPreferencesPage  extends PreferencePage implements IWorkbenchPreferencePage {
    
	public AbstractServerPreferencesPage() {
		super();
	}

	public void init(IWorkbench workbench) {
	}

	public boolean performOk() {
		saveValues(getPreferenceStore());
		IPreferenceStore store = getPreferenceStore();
		if (store instanceof IPersistentPreferenceStore) {
			try {
				((IPersistentPreferenceStore)store).save();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
	
	protected void performDefaults() {
		super.performDefaults();
		loadDefaultValues(getPreferenceStore());
		initializeControls();
	}
	
	protected Control createContents(Composite parent) {
		Control retVal = createContentsImpl(parent);
		loadValues(getPreferenceStore());
		initializeControls();
		return retVal;
	}
	

	protected abstract void loadDefaultValues(IPreferenceStore store);
	protected abstract void loadValues(IPreferenceStore store);
	protected abstract void saveValues(IPreferenceStore store);
	protected abstract void initializeControls();
	protected abstract Control createContentsImpl(Composite parent);

    
}
