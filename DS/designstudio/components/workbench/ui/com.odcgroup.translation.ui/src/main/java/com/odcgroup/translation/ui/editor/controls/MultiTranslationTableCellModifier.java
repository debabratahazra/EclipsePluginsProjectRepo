package com.odcgroup.translation.ui.editor.controls;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.editor.model.IMultiTranslationTableCellModifier;
import com.odcgroup.translation.ui.editor.model.TranslationTableItem;

/**
 *
 * @author pkk
 *
 */
public class MultiTranslationTableCellModifier implements IMultiTranslationTableCellModifier {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean canModify(Object element, String property) {
		boolean readOnly = false;
		TranslationTableItem item = (TranslationTableItem) element;
		ITranslation translation = item.getTranslation();
		try {
			if (translation.isReadOnly()) {
				readOnly = true;
			}
		} catch (TranslationException e) {
		}
		return !readOnly;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object getValue(Object element, String property) {
		TranslationTableItem item = (TranslationTableItem) element;
		String val = item.getText(new Locale(property));		
		return (val == null) ? "" : val;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public void modify(Object element, String property, Object value) {
		// TODO Auto-generated method stub

	}

}
