/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

/**
 * @generated
 */
public class DiagramGeneralPreferencePage extends DiagramsPreferencePage {

	private StringFieldEditor errorUri = null;
	private StringFieldEditor abortUri = null;

	private static String PREF_PAGEFLOW_ERROR_URL = PageflowPreferenceConstants.PREF_PAGEFLOW_ERROR_URL;
	private static String PREF_PAGEFLOW_ABORT_CLASS = PageflowPreferenceConstants.PREF_PAGEFLOW_ABORT_CLASS;
	private static String PREF_DESC_LABEL_DISPLAY = PageflowPreferenceConstants.PREF_DESC_LABEL_DISPLAY;

	/**
	 * @generated
	 */
	public DiagramGeneralPreferencePage() {
		setPreferenceStore(PageflowDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	@Override
	protected void addFields(Composite composite) {
		addPageflowFields(composite, "Pageflow Configuration");
		super.addFields(composite);
	}

	/**
	 * @param parent
	 * @param groupTitle
	 */
	protected void addPageflowFields(Composite parent, String groupTitle) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(groupTitle);

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		errorUri = new StringFieldEditor(PREF_PAGEFLOW_ERROR_URL, "Error View URI", 60, group);
		abortUri = new StringFieldEditor(PREF_PAGEFLOW_ABORT_CLASS, "Abort View URI", 60, group);
		errorUri.setStringValue(PageflowPreferenceConstants.DEF_PAGEFLOW_ERROR_URL);
		abortUri.setStringValue(PageflowPreferenceConstants.DEF_PAGEFLOW_ABORT_CLASS);
		super.addField(errorUri);
		super.addField(abortUri);
		BooleanFieldEditor descDisp = new BooleanFieldEditor(PREF_DESC_LABEL_DISPLAY, "Show State Description", group);
		super.addField(descDisp);
		group.setLayoutData(gridData);
		group.setLayout(gridLayout);
	}
}
