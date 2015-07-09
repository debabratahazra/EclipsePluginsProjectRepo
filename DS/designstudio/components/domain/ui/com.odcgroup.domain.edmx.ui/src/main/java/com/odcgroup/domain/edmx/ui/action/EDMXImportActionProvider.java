package com.odcgroup.domain.edmx.ui.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;


/**
 * @author Ramya Veigas
 * @version 1.0
 */
abstract class EDMXImportActionProvider extends CommonActionProvider {

	/** The action. */
	private EDMXImportAction importAction;

	/**
	 * Creates a new DomainEdmxImportActionProvider.
	 */
	public EDMXImportActionProvider() {
	}

	/**
	 * Initialises the ActionProvider.
	 * 
	 * @param aSite The site
	 */
	public void init(ICommonActionExtensionSite aSite) {
		importAction = new EDMXImportAction();
	}

	/**
	 * Fills the context menu.
	 * 
	 * @param menu The menu to fill
	 */
	public void fillContextMenu(IMenuManager menu) {
		if (importAction.isEnabled())
			menu.appendToGroup(getGroupName(), importAction);
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.action.AAAActionProvider#getGroupName()
	 */
	protected String getGroupName() {
		return "group.port";
	}
	
	
}