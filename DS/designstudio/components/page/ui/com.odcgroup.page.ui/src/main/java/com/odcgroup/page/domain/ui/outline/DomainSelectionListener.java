package com.odcgroup.page.domain.ui.outline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * @author atr
 */
class DomainSelectionListener implements ISelectionListener {
	
	/**
	 * @param widget
	 * @param selectedItems
	 */
	private void collectItems(Widget widget, List<TreeItem> selectedItems) {
    	String attribute = WidgetUtils.getDomainAttribute(widget);
    	if (attribute != null) {
    		Tree tree = getViewer().getTree();
    		TreeItem root = tree.getItem(0);
    		int nbItems = root.getItemCount();
    		TreeItem[] items = root.getItems();
    		for (int kx=0; kx < nbItems; kx++) {
    			MdfModelElement element = (MdfModelElement)items[kx].getData();    			
    			if (element != null && attribute.equals(element.getName())) {
    				selectedItems.add(items[kx]);
    			}
    		}
    	}
    	for (Widget w : widget.getContents()) {
    		collectItems(w, selectedItems);
    	}
    }
	
	/**
	 * Must be overriden
	 * @return TreeViewer
	 */
	protected TreeViewer getViewer() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
    @SuppressWarnings("rawtypes")
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        
		if (!(selection instanceof IStructuredSelection)) {
            // Does not interest us
            return;
        }

        if (selection instanceof TreeSelection) {
            // Does not interest us
            return;
        }
        
        // optimization
        if (getViewer().getTree().getItemCount() == 0) {
        	// the tree is not populated
        	return;
        }
        
        /*
         *  Collect all TreeItem for the Domain Attributes 
         *  which have been selected in the editor
         */
        List<TreeItem> selectedItems = new ArrayList<TreeItem>();
        
        IStructuredSelection ss = (IStructuredSelection) selection;
		Iterator it = ss.iterator(); 
        while (it.hasNext()) {
            Widget widget = (Widget) AdaptableUtils.getAdapter(it.next(), Widget.class);
            if (widget != null) {
                Property entity = widget.findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
                if (entity != null) {
                	collectItems(widget, selectedItems);
                }
            }
        }

        // update the selection in the tree view
       	getViewer().getTree().setSelection(selectedItems.toArray(new TreeItem[]{}));
        
	}

}
