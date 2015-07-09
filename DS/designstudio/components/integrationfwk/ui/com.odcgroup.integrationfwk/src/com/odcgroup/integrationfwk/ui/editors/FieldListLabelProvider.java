package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.integrationfwk.ui.model.Field;

public class FieldListLabelProvider extends LabelProvider implements
		IColorProvider {

	public Color getBackground(Object element) {
		if (!(element instanceof Field)) {
			return null;
		}
		if (((Field) element).isSelected()) {
			return Display.getCurrent().getSystemColor(
					SWT.COLOR_INFO_BACKGROUND);
		} else {
			return null;
		}
	}

	public Color getForeground(Object element) {
		if (!(element instanceof Field)) {
			return null;
		}
		if (((Field) element).isSelected()) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		} else {
			return null;
		}
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		Field field = (Field) element;
		return field.getFieldName() + " : " + field.getFieldType();
	}
}
