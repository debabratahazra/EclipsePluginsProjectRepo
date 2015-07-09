package com.odcgroup.page.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.page.model.corporate.CorporateDesignConstants;

/**
 * The PreferencePage for the Corporate Design.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class CorporateDesignPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPropertyPage, IWorkbenchPreferencePage {

	/** The horizontal alignments. */
	private static final String[][] HORIZONTAL_ALIGNMENTS = new String[][] {
			{ CorporateDesignConstants.ALIGN_LEAD_NAME, CorporateDesignConstants.ALIGN_LEAD_VALUE },
			{ CorporateDesignConstants.ALIGN_TRAIL_NAME, CorporateDesignConstants.ALIGN_TRAIL_VALUE },
			{ CorporateDesignConstants.ALIGN_CENTER_NAME, CorporateDesignConstants.ALIGN_CENTER_VALUE } };

	/** Stores owning element of properties */
	private IAdaptable element;

	/**
	 * @see com.odcgroup.workbench.ui.preferences.FieldEditorOverlayPage#createFieldEditors()
	 */
	public void createFieldEditors() {

		addField(new ComboFieldEditor(
				CorporateDesignConstants.PROPERTY_LABEL_HORIZONTAL_ALIGNMENT,
				"Label Horizontal Alignment", 
				HORIZONTAL_ALIGNMENTS, 
				getFieldEditorParent()));

		addField(new ComboFieldEditor(CorporateDesignConstants.PROPERTY_FIELD_HORIZONTAL_ALIGNMENT,
				"Widget Horizontal Alignment", 
				HORIZONTAL_ALIGNMENTS, 
				getFieldEditorParent()));

		addField(new IntegerFieldEditor(CorporateDesignConstants.PROPERTY_TABLE_PAGE_SIZE, 
				"Table Page Size",
				getFieldEditorParent()));
	}

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    protected IPreferenceStore doGetPreferenceStore() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
        return new ScopedPreferenceStore(new ProjectScope(project), CorporateDesignConstants.PROPERTY_STORE_ID);
    }

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return this.element;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}
	

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}	

	/**
	 */
	public CorporateDesignPreferencePage() {
		super("Corporate Design Settings", FieldEditorPreferencePage.GRID);
	}


}
