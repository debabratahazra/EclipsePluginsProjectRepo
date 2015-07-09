package com.odcgroup.page.domain.ui.outline;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.ui.util.DomainObjectUtils;

/**
 * @author atr
 */
class DomainDoubleClickListener  implements IDoubleClickListener {
    
    /**
     * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
     */
    public void doubleClick(DoubleClickEvent event) {
        TreeSelection ts = (TreeSelection) event.getSelection();
        DomainObjectUtils.openDomainEditor((MdfModelElement)ts.getFirstElement());
    } 
}
