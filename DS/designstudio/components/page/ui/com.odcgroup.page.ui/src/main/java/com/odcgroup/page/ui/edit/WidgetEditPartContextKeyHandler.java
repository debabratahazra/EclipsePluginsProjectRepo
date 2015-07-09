package com.odcgroup.page.ui.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.menu.ActionProvider;
import com.odcgroup.page.ui.menu.ActionProviderContextImpl;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.uimodel.ActionGroup;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.WidgetMenu;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.page.util.ClassUtils;

/**
 * This class manages key handlers.
 * 
 * @author Alain Tripod
 * @author Gary Hayes
 */
public class WidgetEditPartContextKeyHandler {
	
	/** The cache of actions. */
	private static HashMap<WidgetEditPart, List<IAction>> cache 
						= new HashMap<WidgetEditPart, List<IAction>>();
	

	/**
	 * Activate the key handlers for the EditPart.
	 * 
	 * @param editpart The EditPart to activate the handlers for
	 * @param viewer The EditPartViewer
	 */
	public static void activateKeyHandlers(WidgetEditPart editpart, EditPartViewer viewer) {		
		List<IAction> actions = getCachedActions(editpart);
		if (actions != null) {
			// already done
			return;
		}
		// retrieve a list of "key-able" contextual actions
		actions = new ArrayList<IAction>();
		Widget widget = (Widget) AdaptableUtils.getAdapter(editpart, Widget.class);
		UIModel uimodel = UIModelRegistry.getUIModel();
		List<WidgetMenu> menus = uimodel.getMenus().getMenus();
		if (widget != null) {
			EditorMode refMode = getEditorMode(editpart);
			for (WidgetMenu menu : menus) {
				for (WidgetType wt : menu.getWidgetTypes()) {
					if (widget.getType() == wt) {
						actions.addAll(getActions(editpart, menu, refMode,viewer));
						break;
					}
				}
			}
		}
		
		// put in cache these actions
		setCachedActions(editpart, actions);
		
		// maps action to keystrokes 
		KeyHandler keyHandler = editpart.getViewer().getKeyHandler();
		if (keyHandler != null) {
			new KeyStrokeAdder(keyHandler).add(actions);
		}
	}

	/**
	 * Deactivates the key handlers for the EditPart.
	 * 
	 * @param editpart The EditPart to deactivate the key handlers for
	 */
	public static void deactivateKeyHandlers(WidgetEditPart editpart) {
		List<IAction> actions = getCachedActions(editpart);
		if (actions == null) {
			// already done
			return;
		}
		
		// unmaps action from keystrokes 
		KeyHandler keyHandler = editpart.getViewer().getKeyHandler();
		if (keyHandler != null) {
			new KeyStrokeRemover(keyHandler).remove(actions);
		}

		// remove actions from cache
		setCachedActions(editpart, null);
	}
	
	/**
	 * Updates the updateable actions.
	 * 
	 * @param editpart The EditPart to update the actions for
	 */
	public static void updateActions(WidgetEditPart editpart) {
		List<IAction> actions = getCachedActions(editpart);
		if (actions != null) {
			for (IAction action : actions) {
				if (action instanceof UpdateAction) {
					((UpdateAction)action).update();
				}
			}
		}
	}	
	
	/**
	 * Gets the cached actions.
	 * 
	 * @param editpart The EditPart to get the actions for
	 * @return List The List of actions
	 */
	private static List<IAction> getCachedActions(WidgetEditPart editpart) {
		return cache.get(editpart);
	}
	
	/**
	 * Sets the cached actions.
	 * 
	 * @param editpart The EditPart to set the actions for
	 * @param actions The List of actions
	 */	
	private static void setCachedActions(WidgetEditPart editpart, List<IAction> actions) {
		if (actions != null) {
			cache.put(editpart, actions);
		} else {
			cache.remove(editpart);
		}
	}
	
	/**
	 * Gets the current ActionRegistry.
	 * 
	 * @return ActionRegistry the current action registry
	 */
	private static final ActionRegistry getActionRegistry() {
		ActionRegistry reg = null;
		IEditorPart ep = EclipseUtils.findActiveEditor();
		if (ep != null && ep instanceof IAdaptable) {
			reg = (ActionRegistry)((IAdaptable)ep).getAdapter(ActionRegistry.class);
		}
		return reg;
	}
	
	/**
	 * Builds and returns an action provider given an action descriptor.
	 * 
	 * @param editPart The EditPart
	 * @param actionDef the action descriptor
	 * @param viewer The EditPartViewer
	 * @return ActionProvider
	 */
	private static ActionProvider getActionProvider(final WidgetEditPart editPart, Action actionDef,final EditPartViewer viewer) {
		ActionProvider acProvider = null;
		if (StringUtils.isNotEmpty( actionDef.getProvider() )) {
			acProvider = (ActionProvider) ClassUtils.newInstance(WidgetEditPartContextKeyHandler.class.getClassLoader(), actionDef.getProvider());
			if (acProvider != null) {
				ActionProviderContextImpl ctx = new ActionProviderContextImpl ();
				ctx.setCommandStack(editPart.getViewer().getEditDomain().getCommandStack());
				ctx.setRegistry(getActionRegistry());
				ctx.setSelection(editPart.getViewer().getSelection());
				ctx.setWidgetEditPart((WidgetEditPart) editPart);
				acProvider.setContext(ctx);
			}
		}
		return acProvider;
	}
	
	/**
	 * Gets the current EditorMode.
	 * 
	 * @param editPart The EditPart to get the EditorMode for
	 * @return EditorMode the current action mode (design, preview)
	 */
	private static EditorMode getEditorMode(WidgetEditPart editPart) {
		EditorMode mode = (EditorMode) editPart.getViewer().getProperty("editor-mode");
		if (mode == null) {
			// default
			mode = EditorMode.PREVIEW_MODE;
		}
		return mode;
	}

	/**
	 * 
	 * @param rootMenu
	 * @param refMode
	 * @return LinkedHashSet The action definitions
	 */
	private static LinkedHashSet<Action> getActionDefs(WidgetMenu rootMenu, EditorMode refMode) {
		/** extract a list of all action-group */
		WidgetMenu menu = rootMenu;
		ArrayList<MenuItem> allItems = new ArrayList<MenuItem>();
		ArrayList<ActionGroup> allGroups = new ArrayList<ActionGroup>();
		while (menu != null) {
			allItems.addAll(0, menu.getMenuItems());			
			menu = menu.getParent();
		}
		for (MenuItem menuItem : allItems) {
			allGroups.addAll(0, menuItem.getGroups());
		}
		/** Constructs the list actions based on the current editor mode. */
		LinkedHashSet<Action> actions = new LinkedHashSet<Action>();		
		for (ActionGroup group : allGroups) {
			for (Action action : group.getActions()) {
				EditorMode mode = action.getMode();
				if (EditorMode.ALL == mode || refMode.equals(mode)) {
					if (StringUtils.isNotEmpty(action.getAccelerator())) {
						actions.add(action);
					}
				}
			}
		}
		return actions;
	}

	/**
	 * Gets the actions for the EditPart.
	 * 
	 * @param editpart The EditPart to get the actions for
	 * @param rootMenu The menu
	 * @param refMode The EditorMode (design or preview)
	 * @param viewer The EditPartViewer
	 * @return List of Actions
	 */
	private static List<IAction> getActions(WidgetEditPart editpart, WidgetMenu rootMenu, EditorMode refMode, EditPartViewer viewer) {
		List<IAction> list = new ArrayList<IAction>();
		for (Action actionDef : getActionDefs(rootMenu, refMode)) {
			ActionProvider acProvider = getActionProvider(editpart, actionDef,viewer);
			if (acProvider != null) {
				IAction action = acProvider.getAction(actionDef);
				if (action != null) {
					list.add(action);
				} 
			}
		}
		return list;
	}	
}
