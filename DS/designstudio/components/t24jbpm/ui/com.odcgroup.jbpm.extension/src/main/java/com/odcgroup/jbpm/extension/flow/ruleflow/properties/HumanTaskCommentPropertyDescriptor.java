package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author phanikumark
 *
 */
public class HumanTaskCommentPropertyDescriptor extends PropertyDescriptor {

	private HumanTaskNodeWrapper wrapper;
	
	/**
	 * @param id
	 * @param displayName
	 */
	public HumanTaskCommentPropertyDescriptor(Object id, String displayName,HumanTaskNodeWrapper wrapper) {
		super(id, displayName);
		this.wrapper = wrapper;
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.views.properties.PropertyDescriptor#createPropertyEditor(org.eclipse.swt.widgets.Composite)
     */
    public CellEditor createPropertyEditor(Composite parent) {
    	HumanTaskCommentCellEditor editor = new HumanTaskCommentCellEditor(parent,wrapper);
    	return editor;
    }

}
