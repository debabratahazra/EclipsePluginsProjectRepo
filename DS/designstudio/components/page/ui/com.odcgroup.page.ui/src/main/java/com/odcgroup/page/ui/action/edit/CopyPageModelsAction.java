package com.odcgroup.page.ui.action.edit;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.ui.action.edit.CopyOfsElementsAction;

/**
 * This action copy only the model of the Page Designer to the clipboard.
 * 
 * Only fragments, modules or pages are supported.
 * 
 * @author atr
 */
public class CopyPageModelsAction extends CopyOfsElementsAction {
	
	/**
	 * Checks if the given OFS model resource is a resource Page Designer's Model
	 * Resource.
	 * 
	 * @param ofsModelResouce
	 *            the OFS model resource to check
	 * 
	 * @return {@code true} if it's a Page Designer's model resource, otherwise
	 *         it returns {@code false}
	 */
	protected boolean isPageDesignerResource(IOfsModelResource ofsModelResouce) {
		String extension = ofsModelResouce.getURI().fileExtension();
		return extension.equals(PageConstants.MODULE_FILE_EXTENSION)
				|| extension.equals(PageConstants.FRAGMENT_FILE_EXTENSION)
				|| extension.equals(PageConstants.PAGE_FILE_EXTENSION);
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.edit.CopyOfsElementsAction#acceptOfsElement(com.odcgroup.workbench.core.IOfsElement)
	 */
	@Override
	protected boolean acceptOfsElement(IOfsElement element) {
		boolean accept = false;
		if (element instanceof IOfsModelResource) {
			accept = isPageDesignerResource((IOfsModelResource)element);
		}
		return accept;
	}

	/**
	 * @param site the site owning this action
	 * @param clipboard
	 */
	public CopyPageModelsAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site, clipboard);
	}

}
