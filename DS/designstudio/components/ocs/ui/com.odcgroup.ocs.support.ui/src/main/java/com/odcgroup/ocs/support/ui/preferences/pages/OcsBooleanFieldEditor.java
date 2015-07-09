package com.odcgroup.ocs.support.ui.preferences.pages;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author yan
 */
public class OcsBooleanFieldEditor extends BooleanFieldEditor {
	
	private Composite parentComposite;
	
	/**
	 * @param autoUpdateMaven
	 * @param string
	 * @param fieldEditorParent
	 */
	public OcsBooleanFieldEditor(String name, String label, Composite parent) {
		super(name, label, parent);
		this.parentComposite = parent;
	}

	public void forceValue(boolean value) {
		if (getBooleanValue() != value) {
			getChangeControl(parentComposite).setSelection(value);
		}
	}

}
