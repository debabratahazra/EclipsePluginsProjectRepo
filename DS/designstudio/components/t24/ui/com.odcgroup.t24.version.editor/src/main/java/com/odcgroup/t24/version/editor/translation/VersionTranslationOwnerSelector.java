package com.odcgroup.t24.version.editor.translation;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.t24.version.editor.ui.VersionMultiPageEditor;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 * @author atr
 */
public class VersionTranslationOwnerSelector implements ITranslationOwnerSelector {
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select(org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
		if (editorPart != null && editorPart instanceof VersionMultiPageEditor) {
			VersionMultiPageEditor editor = (VersionMultiPageEditor) editorPart;
			editor.setActiveForm(translation);
		}
	}

}
