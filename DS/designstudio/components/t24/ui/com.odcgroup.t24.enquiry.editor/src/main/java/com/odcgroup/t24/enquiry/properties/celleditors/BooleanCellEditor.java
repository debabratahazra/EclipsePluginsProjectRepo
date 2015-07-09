package com.odcgroup.t24.enquiry.properties.celleditors;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author satishnangi
 *
 */
public class BooleanCellEditor extends ExtendedComboBoxCellEditor {
	static String[]  booleanValues = { "true" ,"false" };
	public BooleanCellEditor(Composite composite) {
		super(composite, Arrays.asList(booleanValues),new LabelProvider());
	}

	@Override
	public Object doGetValue() {
		return super.doGetValue();
	}

	@Override
	public void doSetValue(Object value) {
		if(value !=null){
		  super.doSetValue(value);
		}
	}
}
