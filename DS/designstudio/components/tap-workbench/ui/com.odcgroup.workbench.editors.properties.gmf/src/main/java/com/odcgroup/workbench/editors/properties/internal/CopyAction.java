package com.odcgroup.workbench.editors.properties.internal;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.CustomDataTransfer;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.workbench.editors.properties.item.AbstractWidgetAction;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class CopyAction extends AbstractWidgetAction {
	
	private String formatType;		
	
	public CopyAction(IPropertyFeature propertyItem, String text, String formatType) {
		super(text);
		setPropertyItem(propertyItem);
		this.formatType = formatType;
		setEnabled(false);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.util.AbstractWidgetAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
		return super.updateSelection(selection);
	}

	public void runAction() {			
		String clipString = ClipboardUtil.copyElementsToString(getSelection(), null, new NullProgressMonitor());
		CustomData data =  new CustomData(formatType, clipString.getBytes());
        if (data != null) {
            ClipboardManager.getInstance().addToCache(new ICustomData[] { data }, CustomDataTransfer.getInstance());
            ClipboardManager.getInstance().flushCacheToClipboard();
        }
	}		
}
