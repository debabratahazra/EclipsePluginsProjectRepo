package com.odcgroup.page.ui.properties;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.util.ConditionDialogUtils;

/**
 * A multi line cell editor to edit expressions property.
 * 
 * @author Kai Kreuzer
 *
 */
public class ExpressionCellEditor extends DialogCellEditor {
	
	/** The selected property*/
	private Property property;

    /**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property
	 * 			The selected property
	 */
    public ExpressionCellEditor(Composite parent, Property property) {
        super(parent);
        this.property = property;
    }
    
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		return ConditionDialogUtils.editCondition(cellEditorWindow.getShell(), property);
	}

}
