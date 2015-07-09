package com.temenos.t24.tools.eclipse.basic.doc.views;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.temenos.t24.tools.eclipse.basic.IDocViewProvider;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;
import com.temenos.t24.tools.eclipse.basic.views.document.ComponentFilterController;

public class T24DocViewProvider implements IDocViewProvider {

	public T24DocViewProvider() {
	}

	public void enableView(IFolderLayout right, IFolderLayout bottom) {
		right.addView(ComponentLandscapeView.VIEW_ID);
        bottom.addView(T24DocumentView.VIEW_ID);
        bottom.addPlaceholder(ComponentInteractionView.viewID);		
	}
	
	/**
     * Refresh the T24DocumentView mainly used for loading the view at first
     * runtime
     */
    public void refreshView() {
        IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        if (activePage != null && activePage.getActiveEditor()!=null) {
            FileEditorInput inputFileEditor = ((FileEditorInput) activePage.getActiveEditor().getEditorInput());
            T24DocViewerUtil.buildDocsView(inputFileEditor.getName());
        }
    }

	public void buildView(MultiPageEditorPart editor) {
		if(editor instanceof T24BasicMultiPageEditor) {
			T24BasicMultiPageEditor t24Editor = (T24BasicMultiPageEditor)editor;
			T24DocViewerUtil.buildDocsView(t24Editor.getPartName());
	        ComponentFilterController.showLandscapeView();
		}
	}

	public void showView(String partName) {
		T24DocViewerUtil.buildDocsView(partName);
        ComponentFilterController.showLandscapeView();		
	}

}
