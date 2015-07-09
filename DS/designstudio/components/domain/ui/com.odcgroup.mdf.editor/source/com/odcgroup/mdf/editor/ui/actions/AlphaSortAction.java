package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;

public class AlphaSortAction implements IEditorActionDelegate {

	private IEditorPart activeEditorPart = null;
	
	IResource extractResource(IEditorPart editor) {
	      IEditorInput input = editor.getEditorInput();
	      if (!(input instanceof IFileEditorInput))
	         return null;
	      return ((IFileEditorInput)input).getFile();
	}
	
	@Override
	public void run(IAction action) {
		 if (activeEditorPart instanceof DomainModelEditor) {
        	DomainModelEditor editor = (DomainModelEditor) activeEditorPart;
    		if(editor.getViewer() instanceof TreeViewer) {
        			if(action.isChecked()) {
    					getPreferenceStore().setValue(MdfPlugin.SORT_KEY, true);
    				} else {
    					getPreferenceStore().setValue(MdfPlugin.SORT_KEY, false);
    				}        			
        		}
        	editor.getViewer().refresh();
        }
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		activeEditorPart = targetEditor;
		action.setChecked(MdfPlugin.getDefault().getPreferenceStore().getBoolean("AlphaSort"));
	}
	
	/**
     * @return
     */
    protected IPreferenceStore getPreferenceStore() {
		return MdfPlugin.getDefault().getPreferenceStore();
	}
}
