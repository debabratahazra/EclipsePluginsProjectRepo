package com.odcgroup.translation.ui.editor.properties;

import org.eclipse.jface.viewers.LabelProvider;

/**
 *
 * @author pkk
 *
 */
public class TranslationPropertyViewLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		return "Translation";
	}	

}
