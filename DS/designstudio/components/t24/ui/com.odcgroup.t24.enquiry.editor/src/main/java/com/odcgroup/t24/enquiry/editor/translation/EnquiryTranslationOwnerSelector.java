package com.odcgroup.t24.enquiry.editor.translation;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.t24.enquiry.editor.EnquiryEditor;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryTranslationOwnerSelector implements ITranslationOwnerSelector {

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select(org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
		if (editorPart != null && editorPart instanceof EnquiryEditor) {
			EnquiryEditor editor = (EnquiryEditor) editorPart;
			//TODO Identify what needs to be set as viewer selection in the editor for translation.
		}
	}

}
