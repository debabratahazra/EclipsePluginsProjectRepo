package com.odcgroup.integrationfwk.ui.editors;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.integrationfwk.ui.model.Field;

public class FlowTableLabelProvider extends BaseLabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int index) {
		Field field = (Field) element;
		switch (index) {
		/*
		 * Functionality differed case 0: return field.getService(); case 1:
		 * return field.getOperation(); case 2: return field.getFieldName();
		 * case 3: return field.getDisplayName(); case 4: return
		 * field.getFieldType(); default: return "unknown";
		 */

		case 0:
			return field.getFieldName();
		case 1:
			return field.getDisplayName();
		case 2:
			return field.getFieldType();
		default:
			return "unknown";

		}

	}

}
