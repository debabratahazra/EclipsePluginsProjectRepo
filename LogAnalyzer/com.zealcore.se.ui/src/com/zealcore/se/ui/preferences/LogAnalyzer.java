package com.zealcore.se.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class LogAnalyzer extends PreferencePage implements
        IWorkbenchPreferencePage {

    /**
     * Create the preference page
     */
    public LogAnalyzer() {
        super();
    }

    /**
     * Create contents of the preference page
     * 
     * @param parent
     */
    @Override
    public Control createContents(final Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new FormLayout());
        return container;
    }

    /**
     * Initialize the preference page
     */
    public void init(final IWorkbench workbench) {
    // Initialize the preference page
    }

}
