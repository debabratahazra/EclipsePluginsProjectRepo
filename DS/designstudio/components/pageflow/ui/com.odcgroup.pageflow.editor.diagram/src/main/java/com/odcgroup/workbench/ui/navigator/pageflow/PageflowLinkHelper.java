package com.odcgroup.workbench.ui.navigator.pageflow;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditor;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.workbench.core.OfsCore;

public class PageflowLinkHelper  implements ILinkHelper {

	public void activateEditor(IWorkbenchPage page, IStructuredSelection selection) {
        if(selection == null || selection.isEmpty())
            return;
        if(selection.getFirstElement() instanceof State)
        {
        	State elem = (State) selection.getFirstElement();
            org.eclipse.emf.ecore.resource.Resource res = elem.eResource();
            if(res != null)
            {
            	IFile file = WorkspaceSynchronizer.getFile(res);
            	IEditorInput fileInput = new FileEditorInput(file);
            	IEditorPart editor = page.findEditor(fileInput);
            	if (editor != null) {
            		page.bringToTop(editor);
            	} else {
	            	try {
						page.openEditor(fileInput, "com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorID");
						editor = page.getActiveEditor();
					} catch (PartInitException e) {
						Status status = new Status(Status.ERROR, "x", Status.OK, "Error opening editor", e);
						OfsCore.getDefault().getLog().log(status);
					}
            	}
            	if(editor instanceof PageflowDiagramEditor) {
            		PageflowDiagramEditor diagEditor = (PageflowDiagramEditor) editor;
            		EditPart editPart = 
            			diagEditor.getDiagramEditPart().findEditPart(diagEditor.getDiagramEditPart(), elem);
            		diagEditor.getDiagramGraphicalViewer().setSelection(new StructuredSelection(editPart));
            	}
            }
        }
    }

    public IStructuredSelection findSelection(IEditorInput anInput)
    {
        if(anInput instanceof EditPart) {
        	EditPart editPart = (EditPart) anInput;
            return new StructuredSelection(editPart.getModel());
        } else {
            return StructuredSelection.EMPTY;
        }
    }
}
