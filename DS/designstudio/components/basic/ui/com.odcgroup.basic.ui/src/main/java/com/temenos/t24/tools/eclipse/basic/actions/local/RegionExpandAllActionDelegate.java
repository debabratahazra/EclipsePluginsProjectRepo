package com.temenos.t24.tools.eclipse.basic.actions.local;

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

public class RegionExpandAllActionDelegate implements IWorkbenchWindowActionDelegate {

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    /**
     * Method invoked by the framework. The main logic of the action goes here.
     */
    public void run(IAction action) {
        expandAllRegions();
    }
    
    @SuppressWarnings("unchecked")
    public void expandAllRegions(){ 
        ProjectionAnnotationModel model = ((T24BasicEditor)EditorDocumentUtil.getActiveEditor()).getProjectionAnnotationModel();
        Iterator<ProjectionAnnotation> it = model.getAnnotationIterator();
        while(it.hasNext()){
            ProjectionAnnotation an = (ProjectionAnnotation)it.next();
            model.expand(an);
        }
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
    }
}
