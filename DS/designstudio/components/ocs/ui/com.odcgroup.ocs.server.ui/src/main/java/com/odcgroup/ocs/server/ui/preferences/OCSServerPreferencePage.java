package com.odcgroup.ocs.server.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;

/**
 * @author yan
 * @since 1.40
 */
public class OCSServerPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {
	private StringFieldEditor watchedLogFilesEditor;
	
	private BooleanFieldEditor clearLogAtStartupEditor;
	
    public OCSServerPreferencePage() {
        super(GRID);
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, ServerCore.PLUGIN_ID));
    }

	@Override
    protected void createFieldEditors() {
    	
    	Label logging = new Label(getFieldEditorParent(), SWT.NONE);
    	logging.setText("Logging:");
    	GridData loggingGridData = new GridData(GridData.BEGINNING, SWT.BEGINNING, false, false, 2, 1);
    	loggingGridData.verticalIndent = 5;
    	logging.setLayoutData(loggingGridData);
    	
        watchedLogFilesEditor = new StringFieldEditor(DSServerPreferenceManager.WATCHED_LOG_FILES, "Watched log files",
        		getFieldEditorParent());
        watchedLogFilesEditor.setEmptyStringAllowed(true);
    	addField(watchedLogFilesEditor);
    	
    	clearLogAtStartupEditor = new BooleanFieldEditor(DSServerPreferenceManager.CLEAR_LOG_AT_STARTUP, "Clear logs at startup", getFieldEditorParent());
    	getPreferenceStore().setDefault(DSServerPreferenceManager.CLEAR_LOG_AT_STARTUP, true);
    	addField(clearLogAtStartupEditor);
    	
    }

	public void init(IWorkbench workbench) {
    }

}


