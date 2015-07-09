package com.odcgroup.page.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Interface for a PropertyEditor.
 * 
 * @author Gary Hayes
 */
public interface PropertyEditor {
	
	/**
	 * Gets the CellEditor for this Property.
	 * 
	 * @param parent The parent
	 * @param labelProvider The label provider
	 * @return CellEditor The cell editor
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider);

}
