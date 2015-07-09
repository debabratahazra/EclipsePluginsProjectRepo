package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;


/**
 * @author atr
 * @since DS 1.40.0
 */
abstract class AAAActionProvider extends CommonActionProvider {

	/** The action. */
	private Action aaaAction;

	/**
	 * Creates a new AbstractImportActionProvider.
	 */
	public AAAActionProvider() {
	}

	/**
	 * Initialises the ActionProvider.
	 * 
	 * @param aSite The site
	 */
	public void init(ICommonActionExtensionSite aSite) {
		aaaAction = createImportAction();
	}

	/**
	 * Fills the context menu.
	 * 
	 * @param menu The menu to fill
	 */
	public void fillContextMenu(IMenuManager menu) {
		if (aaaAction.isEnabled())
			menu.appendToGroup(getGroupName(), aaaAction);
	}
	
	/**
	 * Creates the action to be run.
	 * 
	 * @return Action
	 */
	abstract protected Action createImportAction();
	
	/**
	 * Gets the name of the group
	 * 
	 * @return String 
	 */
	abstract protected String getGroupName();
	
	
}