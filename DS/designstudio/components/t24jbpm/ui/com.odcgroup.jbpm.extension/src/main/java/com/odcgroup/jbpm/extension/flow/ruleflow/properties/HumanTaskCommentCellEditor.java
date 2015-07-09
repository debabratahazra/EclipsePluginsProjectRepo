package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author phanikumark
 *
 */
public class HumanTaskCommentCellEditor extends DialogCellEditor {

	private HumanTaskNodeWrapper wrapper;
	
	/**
	 * @param parent
	 * @param workItemNode
	 */
	public HumanTaskCommentCellEditor(Composite parent,HumanTaskNodeWrapper wrapper) {
		super(parent);
		this.wrapper = wrapper;		
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		HumanTaskCommentDialog dialog = new HumanTaskCommentDialog(cellEditorWindow.getShell(), 
				"T24 Human Task Configuration",wrapper);
		String value = (String) getValue();
		if (value != null) {
			dialog.setValue(value);
		}
		int result = dialog.open();
		if (result == Window.CANCEL) {
			return null;
		}
		return dialog.getValue();
	}

}
