package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.SortOrder;

/**
 * @author satishnangi
 *
 */
public class FixedSortPropertyDescriptor extends PropertyDescriptor {

	public FixedSortPropertyDescriptor(FixedSort fixedSort) {
		super(fixedSort, fixedSort.getField());
	}
     
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		ExtendedComboBoxCellEditor sortOrderCombBox = new ExtendedComboBoxCellEditor(parent, SortOrder.VALUES, new LabelProvider());
		return sortOrderCombBox ;
	}
}
