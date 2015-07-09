package com.odcgroup.page.ui.action.edit;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.action.edit.IOfsModelResourcePaster;
import com.odcgroup.workbench.ui.action.edit.PasteOfsElementsAction;

/**
 * The paste action for the page designer
 *
 * @author atr
 *
 */
public class PastePageModelsAction extends PasteOfsElementsAction {

	/**
	 * @see com.odcgroup.workbench.ui.action.edit.PasteOfsElementsAction#getOfsModelResourcePaster(org.eclipse.swt.widgets.Shell, com.odcgroup.workbench.core.IOfsProject, com.odcgroup.workbench.core.IOfsProject, com.odcgroup.workbench.core.IOfsModelResource)
	 */
	@Override
	protected IOfsModelResourcePaster getOfsModelResourcePaster(Shell shell, IOfsProject srcOfsProject,
			IOfsProject dstOfsProject, IOfsModelResource ofsModelResource) {
		return new PageModelResourcePaster(shell, srcOfsProject, dstOfsProject, ofsModelResource);
	}

	/**
	 * @param site
	 * @param clipboard
	 */
	public PastePageModelsAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site, clipboard);
	}

}
