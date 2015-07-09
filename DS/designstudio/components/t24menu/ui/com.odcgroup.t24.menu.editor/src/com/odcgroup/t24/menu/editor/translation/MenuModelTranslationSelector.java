package com.odcgroup.t24.menu.editor.translation;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.presentation.MenuEditor;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
/**
 * 
 * @author snn
 *
 */
public class MenuModelTranslationSelector implements ITranslationOwnerSelector {

	/**
	 * @param IEditorPart,Object
	 */
	public void select(IEditorPart editorPart, ITranslation translation) {
		Object owner = translation.getOwner();
		if(owner instanceof MenuItem){
			if(editorPart instanceof MenuEditor){
				((MenuEditor) editorPart).getViewer().setSelection(new StructuredSelection(translation.getOwner()),true);
			}
		}
	}

}
