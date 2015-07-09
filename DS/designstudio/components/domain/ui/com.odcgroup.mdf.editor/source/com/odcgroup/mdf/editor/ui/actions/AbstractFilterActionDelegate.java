package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.editor.MdfPlugin;


public abstract class AbstractFilterActionDelegate implements
        IEditorActionDelegate {

    private IEditorPart activeEditorPart;
    private final ViewerFilter filter;
    private final String preferenceKey;

    /**
     * @param filter
     * @param preferenceKey
     */
    public AbstractFilterActionDelegate(ViewerFilter filter, String preferenceKey) {
        this.filter = filter;
        this.preferenceKey = preferenceKey;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    	if (getPreferenceStore().getBoolean(preferenceKey)) {
        	action.setChecked(true);
    	} else {
        	action.setChecked(false);
    	}
        activeEditorPart = targetEditor;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (activeEditorPart instanceof IViewerProvider) {
            StructuredViewer viewer = (StructuredViewer) ((IViewerProvider) activeEditorPart).getViewer();
            if (viewer != null) {
                if (action.isChecked()) {
                    getPreferenceStore().setValue(preferenceKey, true);
                    viewer.removeFilter(filter);                    
                } else {
                    getPreferenceStore().setValue(preferenceKey, false);
                    viewer.addFilter(filter);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }
    
    /**
     * @return
     */
    protected IPreferenceStore getPreferenceStore() {
		return MdfPlugin.getDefault().getPreferenceStore();
	}

}
