package com.odcgroup.workbench.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * Links IStorageEditorInput to IOfsModelResource, and vice versa.
 * DS-1593
 * 
 * @author pkk
 *
 */
public class OfsLinkHelper implements ILinkHelper {

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}		
		if (false == aSelection.getFirstElement() instanceof IOfsModelResource) {
			return;
		}
		IOfsModelResource mResource = (IOfsModelResource) aSelection.getFirstElement();
		IEditorInput input = OfsEditorUtil.getEditorInput(mResource);
		IEditorPart editor = null;
		if ((editor = aPage.findEditor(input)) != null)
			aPage.bringToTop(editor);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.ILinkHelper#findSelection(org.eclipse.ui.IEditorInput)
	 */
	public IStructuredSelection findSelection(IEditorInput editorInput) {
		IOfsElement mResource = null;
		if (editorInput instanceof IStorageEditorInput) {
			IStorageEditorInput storageInput = (IStorageEditorInput) editorInput;
			try {
				IStorage storage = storageInput.getStorage();
				if (storage instanceof IFile) {
					IFile file = (IFile) storage;
					IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
					if(ofsProject!=null) {
						mResource = ofsProject.getOfsModelResource(ModelURIConverter.createModelURI(storage));
						if (mResource != null)
							return new StructuredSelection(mResource);
					}
				} else if (storage instanceof IOfsElement){
					return new StructuredSelection((IOfsElement)storage);
				}
				return new StructuredSelection(storage);
			} catch (CoreException e) {
				OfsUICore.getDefault().logError("Unable to retrieve storage from the EditorInput", e);
			} catch (ModelNotFoundException e) {
				// we might have tried to access a file which is no model resource, so ignore it
			}
		}
		return StructuredSelection.EMPTY;
	}

}
