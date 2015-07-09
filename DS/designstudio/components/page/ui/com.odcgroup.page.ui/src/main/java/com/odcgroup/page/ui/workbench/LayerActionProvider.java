package com.odcgroup.page.ui.workbench;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;


/**
 * This class adds context menu entries to the navigator. It extends the according base class of the
 * Common Navigator Framework (CNF) and its use is declared in the extension org.eclipse.ui.navigator.navigatorContent
 * 
 * @author atr
 *
 */
public class LayerActionProvider extends CommonActionProvider {

	/** action for adding a new layer model file */
	private NewLayerAction layerAction;

	/**
	 * Creates a new LayerActionProvider.
	 */
	public LayerActionProvider() {
	}

	/**
	 * <p>
	 * Initialize the current ICommonActionProvider with the supplied
	 * information.
	 * </p>
	 * <p>
	 * init() is guaranteed to be called before any other method of the
	 * ActionGroup super class.
	 * 
	 * @param aSite
	 *            The configuration information for the instantiated Common
	 *            Action Provider.
	 */
	public void init(ICommonActionExtensionSite aSite) {
		layerAction = new NewLayerAction();
	}

    /** 
     * Adds the applicable actions to a context menu,
     * based on the state of the <code>ActionContext</code>.
     * <p>
     * The default implementation does nothing.  
     * Subclasses may override or extend this method.
     * </p>
     * 
     * @param menu the context menu manager
     */
	public void fillContextMenu(IMenuManager menu) {
		if (layerAction.isEnabled())
			menu.appendToGroup("group.new", layerAction);
	}

}
