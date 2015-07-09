package com.odcgroup.t24.enquiry.ui.translation;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 * @author atr
 */
public class EnquiryTranslationOwnerSelector implements ITranslationOwnerSelector {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select(org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
//		if (editorPart != null && editorPart instanceof EnquiryMultiPageEditor) {
//			EnquiryMultiPageEditor editor = (EnquiryMultiPageEditor) editorPart;
//			editor.setActiveForm(translation);
//		}
	}

}
