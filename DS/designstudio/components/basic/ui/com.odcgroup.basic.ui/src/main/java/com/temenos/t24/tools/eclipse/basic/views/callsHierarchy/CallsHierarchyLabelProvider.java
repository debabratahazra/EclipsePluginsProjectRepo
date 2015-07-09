package com.temenos.t24.tools.eclipse.basic.views.callsHierarchy;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.views.tree.T24TreeViewLabelProvider;

/**
 * This label provider is used in {@link CallsHierarchyView}. Since this view<br>
 * requires tooltip to be displayed on the items, {@link CellLabelProvider} is<br>
 * used instead of {@link T24TreeViewLabelProvider}.
 * 
 * @author ssethupathi
 * 
 */
public class CallsHierarchyLabelProvider extends CellLabelProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ViewerCell cell) {
        Object obj = cell.getItem().getData();
        if (obj instanceof CallsHierarchyNode) {
            String label = ((CallsHierarchyNode) obj).getLabel();
            cell.setText(label);
            cell.setImage(FTPClientImplConstants.REMOTE_FILE_IMAGE);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getToolTipText(Object element) {
        if (element instanceof CallsHierarchyNode) {
            return ((CallsHierarchyNode) element).getSubroutine().getLineDetail().toString();
        }
        return null;
    }
}
