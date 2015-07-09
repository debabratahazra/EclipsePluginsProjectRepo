package com.odcgroup.mdf.editor.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.mdf.editor.MdfPlugin;

/**
 * @author scn
 */
public class DomainBooleanFieldEditor extends BooleanFieldEditor {
	
	private Composite parentComposite;
	
	/**
	 * @param autoUpdateMaven
	 * @param string
	 * @param fieldEditorParent
	 */
	public DomainBooleanFieldEditor(String name, String label, Composite parent) {
		super(name, label, parent);
		this.parentComposite = parent;		
	}

	public void forceValue(boolean value) {
		if (getBooleanValue() != value) {
			getChangeControl(parentComposite).setSelection(value);
		}
	}
	
	@Override
	protected void doStore() {
		MdfPlugin.getDefault().getPreferenceStore().setValue("AlphaSort", this.getBooleanValue());
	}

}
