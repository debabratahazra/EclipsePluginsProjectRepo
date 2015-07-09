package com.odcgroup.domain.translation.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 *
 * @author pkk
 */
public class MdfTranslationSelector implements ITranslationOwnerSelector {

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select(org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
		if (editorPart == null) {
			return;
		}		
		if (editorPart instanceof DomainModelEditor) {
			MdfModelElementImpl obj = (MdfModelElementImpl) translation.getOwner();
			DomainModelEditor editor = (DomainModelEditor) editorPart;
			TreeViewer treeViewer = (TreeViewer) editor.getViewer();
			treeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
			TreeItem root = treeViewer.getTree().getItems()[0];
			TreeItem matchItem = getMatchingTreeItem(obj, root, treeViewer);
			if (matchItem == null) {
				matchItem = getMatchingTreeItem(obj.eContainer(), root, treeViewer);
				if (matchItem != null) {
					treeViewer.setExpandedState(matchItem.getData(), true);
					matchItem = getMatchingTreeItem(obj, matchItem, treeViewer);
				}
			}
			if (matchItem != null){
				treeViewer.setSelection(new StructuredSelection(matchItem.getData()), true);
				treeViewer.setExpandedState(matchItem.getData(), true);
			}
		}

	}
	
	 /**
     * @param obj
     * @return
     */
    private TreeItem getMatchingTreeItem(EObject obj, TreeItem item, TreeViewer viewer) {
    	MdfModelElementImpl owner = (MdfModelElementImpl) obj;
    	TreeItem retItem = null;
    	if (areEqual(owner, (MdfModelElement) item.getData())) {
    		return item;
    	}
    	TreeItem[] rItems = item.getItems();
		for (TreeItem treeItem : rItems) {
			MdfModelElement model = (MdfModelElement) treeItem.getData();
			if(areEqual(owner, model)){
				retItem = treeItem;
			} 
		}
		return retItem;
    }
    
    /**
     * @param model1
     * @param model2
     * @return
     */
    private boolean areEqual(MdfModelElement model1, MdfModelElement model2) {
    	String qName1 = model1.getQualifiedName().getQualifiedName();
    	String qName2 = model2.getQualifiedName().getQualifiedName();
    	if(qName1.equals(qName2)){
    		return true;
    	}
    	return false;
    }

}
