package com.odcgroup.translation.ui.editor.model;

import org.eclipse.ui.IEditorPart;

import com.odcgroup.translation.core.ITranslation;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationOwnerSelector {
	
	/**
	 * @param editorPart
	 * @param owner
	 */
	void select(IEditorPart editorPart, ITranslation translation);

}
