package com.odcgroup.mdf.editor.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.odcgroup.mdf.editor.MdfPlugin;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage </samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class MdfPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String P_SHOW_CARDINALITIES = "showCardinalities";

	public static final String P_SHOW_TYPE = "showType";

	public static final String P_DEFAULT_MODELS_FOLDER = "defaultModelsFolder";

	
	public static final String P_MDF_ENUM_FILTER = "showMdfEnumerations";
	public static final String P_MDF_CLASS_FILTER = "showMdfClasses";
	public static final String P_MDF_MAIN_CLASS_FILTER = "showMdfMainClasses";
	public static final String P_MDF_DATASET_FILTER = "showMdfDatasets";
	public static final String P_MDF_BUSINESSTYPE_FILTER = "showMdfBusinesstypes";
	
	/**
	 * Constructor for MdfPreferencePage
	 */
	public MdfPreferencePage() {
		super(GRID);
		setPreferenceStore(MdfPlugin.getDefault().getPreferenceStore());
		// setDescription("MDF Plugin preferences");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(P_SHOW_CARDINALITIES,
				"Display elements &cardinality where applicable",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(P_SHOW_TYPE,
				"Display elements &type where applicable",
				getFieldEditorParent()));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		new Label(getFieldEditorParent(), SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(gd);
		
		addField(new StringFieldEditor(P_DEFAULT_MODELS_FOLDER,
				"Models &folder name:", getFieldEditorParent()));

	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}