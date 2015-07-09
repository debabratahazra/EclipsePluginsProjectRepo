package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.odcgroup.t24.enquiry.enquiry.CurrencyPattern;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class FieldFormatCurrencyPatternDescriptor extends PropertyDescriptor {

	/**
	 * @param id
	 * @param displayName
	 */
	public FieldFormatCurrencyPatternDescriptor() {
		super(EnquiryPackage.eINSTANCE.getFormat_Pattern().getName(), "Currency Pattern");
		setCategory("Format");
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedComboBoxCellEditor(parent, CurrencyPattern.VALUES, new LabelProvider());
	}
}
