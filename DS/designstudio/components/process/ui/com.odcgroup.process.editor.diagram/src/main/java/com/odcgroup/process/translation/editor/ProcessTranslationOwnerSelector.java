package com.odcgroup.process.translation.editor;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.process.diagram.part.ProcessDiagramEditor;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 *
 * @author pkk
 *
 */
public class ProcessTranslationOwnerSelector implements ITranslationOwnerSelector {

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select(org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
		if (editorPart != null && editorPart instanceof ProcessDiagramEditor) {
			ProcessDiagramEditor editor = (ProcessDiagramEditor) editorPart;
			IDiagramGraphicalViewer viewer = editor.getDiagramGraphicalViewer();
			List<EditPart> editParts = getEditPartsForElement(translation.getOwner(), viewer);
			if (!editParts.isEmpty()) {
				viewer.select(editParts.get(0));
				viewer.reveal(editParts.get(0));
			}
		}

	}
	
	/**
	 * @param item
	 * @param viewer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<EditPart> getEditPartsForElement(Object item, IDiagramGraphicalViewer viewer) {
		if (item == null) {
			return Collections.emptyList();
		}
		String id = null;
		if (item instanceof EObject) {
			EObject eObj = (EObject) item;
			id = ((XMLResource)eObj.eResource()).getID(eObj);
		} else if (item instanceof String) {
			id = (String) item;
		}
		
		List<EditPart> editParts = viewer.findEditPartsForElement(id, EditPart.class);		
		return editParts;
	}


}
