package com.odcgroup.translation.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.navigator.OfsLinkHelper;

/**
 *
 * @author pkk
 *
 */
public class TranslationLinkHelper extends OfsLinkHelper {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}
		if (aSelection.getFirstElement() instanceof TranslationNode) {
			TranslationNode resource = (TranslationNode) aSelection.getFirstElement();
			IEditorReference[] editors = aPage.getEditorReferences();
			IEditorInput input = null;
			try {
				for (IEditorReference editorReference : editors) {
					IEditorInput eInput = editorReference.getEditorInput();
					if (eInput instanceof TranslationEditorInput) {
						TranslationEditorInput mInput = (TranslationEditorInput) eInput;
						if (mInput.getOfsProject().equals(resource.getOfsProject())) {
							input = mInput;
						}
					}
				}
				IEditorPart editor = null;
				if ((editor = aPage.findEditor(input)) != null)
					aPage.bringToTop(editor);
			} catch (Exception e) {
			}
		} else {
			super.activateEditor(aPage, aSelection);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.ILinkHelper#findSelection(org.eclipse.ui.IEditorInput)
	 */
	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		if (anInput instanceof TranslationEditorInput) {
			TranslationEditorInput tInput = (TranslationEditorInput) anInput;
			IOfsProject ofsProject = tInput.getOfsProject();
			return new StructuredSelection(new TranslationNode(ofsProject));
		} else {
			return super.findSelection(anInput);
		}
	}

}
