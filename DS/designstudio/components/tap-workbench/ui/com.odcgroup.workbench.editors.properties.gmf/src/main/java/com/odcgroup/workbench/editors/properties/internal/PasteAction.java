package com.odcgroup.workbench.editors.properties.internal;

import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;

import com.odcgroup.workbench.editors.properties.item.AbstractWidgetAction;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class PasteAction extends AbstractWidgetAction {	
	
	private String formatType;	

	/**
	 * @param propertyItem
	 * @param text
	 * @param formatType
	 */
	public PasteAction(IPropertyFeature propertyItem, String text, String formatType) {
		super("Paste");
		setPropertyItem(propertyItem);
		this.formatType = formatType;
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.util.AbstractWidgetAction#runAction()
	 */
	public void runAction() {	
		ICustomData[] data = getClipboardData();
		String value = null;
		if (data != null && data.length > 0) {
            /* Get the string from the clipboard data */
            value = new String(data[0].getData());
	    }
		if (value != null){
			notifyPropertyChange(value);
		}
	}
	
	/**
	 * @return
	 */
	private ICustomData[] getClipboardData() {
		return ClipboardManager.getInstance().getClipboardData(formatType, ClipboardContentsHelper.getInstance());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		ICustomData[] data = getClipboardData();
		if (data != null && data.length > 0) {
			return true;
		}
		return false;
	}		
}
