package com.zealcore.se.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.zealcore.se.ui.SeUiPlugin;

public class MiscPreferences extends PreferencePage implements
        IWorkbenchPreferencePage {
    private Button doubleClick;

    public static final String TAG_ENABLE_DOUBLECLICK_IMPORT = "com.zealcore.sd.misc.enable.doubleclickimport";

    private static boolean isDoubleClickImportEnabled;

    public MiscPreferences() {
        super();
        setPreferenceStore(SeUiPlugin.getDefault().getPreferenceStore());
        isDoubleClickImportEnabled = getPreferenceStore()
        .getBoolean(MiscPreferences.TAG_ENABLE_DOUBLECLICK_IMPORT);
    }

    @Override
    public Control createContents(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());

        final Group eventsGroup = new Group(container, SWT.NONE);
        eventsGroup.setText("Miscellaneous Log Analyzer Options");
        // eventsGroup.setLayout(new FormLayout());
        final FormData fdeventsGroup = new FormData();
        fdeventsGroup.bottom = new FormAttachment(80, 0);
        fdeventsGroup.right = new FormAttachment(100, -5);
        fdeventsGroup.top = new FormAttachment(0, 15);
        fdeventsGroup.left = new FormAttachment(0, 5);
        eventsGroup.setLayoutData(fdeventsGroup);
        eventsGroup.setLayout(new FormLayout());

        doubleClick = new Button(eventsGroup, SWT.CHECK | SWT.LEFT);
        doubleClick.setText("Enable Double-Click Import/Deconfigure");
        doubleClick.setSelection(isDoubleClickImportEnabled);

        // doubleClick1.pack();

        return container;
    }

    @Override
    protected void performApply() {
        isDoubleClickImportEnabled = this.doubleClick.getSelection();
        getPreferenceStore().setValue(TAG_ENABLE_DOUBLECLICK_IMPORT,
                isDoubleClickImportEnabled);
        super.performOk();
    }

    @Override
    public boolean performOk() {
        isDoubleClickImportEnabled = this.doubleClick.getSelection();
        getPreferenceStore().setValue(TAG_ENABLE_DOUBLECLICK_IMPORT,
                isDoubleClickImportEnabled);
        return super.performOk();
    }

    public void init(final IWorkbench workbench) {}

}
