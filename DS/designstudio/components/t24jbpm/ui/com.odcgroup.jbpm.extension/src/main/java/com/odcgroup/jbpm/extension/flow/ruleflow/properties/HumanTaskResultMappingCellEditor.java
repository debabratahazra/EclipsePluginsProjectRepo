package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.Map;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.jbpm.extension.flow.ruleflow.T24HumanTaskNodeWrapper;

/**
 * @author phanikumark
 * 
 */
public class HumanTaskResultMappingCellEditor extends DialogCellEditor {

	private HumanTaskNodeWrapper wrapper;

	/**
	 * @param parent
	 * @param workItemNode
	 */
	public HumanTaskResultMappingCellEditor(Composite parent,
			HumanTaskNodeWrapper wrapper) {
		super(parent);
		this.wrapper = wrapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		HumanTaskResultMappingDialog dialog = new HumanTaskResultMappingDialog(
				cellEditorWindow.getShell(),
				T24HumanTaskNodeWrapper.RESULT_MAPPING_DISPLAY_NAME, wrapper);
		Map<String, String> value = (Map<String, String>) getValue();
		if (value != null && !value.isEmpty()) {
			dialog.setValue(value);
		}
		int result = dialog.open();
		if (result == Window.CANCEL) {
			return null;
		}
		return dialog.getValue();
	}

}
