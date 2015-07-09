package com.odcgroup.page.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.menu.ActionProvider;
import com.odcgroup.page.ui.menu.ActionProviderContextImpl;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.uimodel.ActionGroup;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.MenuItem;
import com.odcgroup.page.uimodel.MenuType;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.WidgetMenu;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.page.util.ClassUtils;

/**
 * The ContextMenuProvided for the Page Designer.
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 * @author Alain Tripod
 */
public class PageUIContextMenuProvider extends org.eclipse.gef.ContextMenuProvider {

	/** Standard context menu group for additions. */
	private static final String GROUP_ADDITIONS = "additions";

	/** The ActionRegistry. */
	private ActionRegistry actionRegistry;

	/** The command stack used to execute commands */
	private CommandStack commandStack;

	/** An action mode */
	private EditorMode editorMode = EditorMode.DESIGN_MODE;

	/** The focused editPart. */
	private EditPart editPart;

	private WidgetType widgetType = null;

	/**
	 * Creates a new PageUIContextMenuProvider.
	 * 
	 * @param viewer
	 *            The EditPartViewer
	 * @param actionRegistry
	 *            The ActionRegistry
	 * @param editorMode
	 *            The editor mode of the PageUIContextMenuProvider
	 */
	public PageUIContextMenuProvider(EditPartViewer viewer, ActionRegistry actionRegistry, EditorMode editorMode) {
		super(viewer);
		
		Assert.isNotNull(viewer);
		Assert.isNotNull(actionRegistry);
		
		this.actionRegistry = actionRegistry;
		this.commandStack = viewer.getEditDomain().getCommandStack();
		this.editorMode = editorMode;		
	}

	/**
	 * Check if the menu descriptor is for contextual menu
	 * 
	 * @param menu
	 * @return boolean
	 */
	private boolean isContextualMenu(WidgetMenu menu) {
		return MenuType.CONTEXTUAL == menu.getType();
	}

	/**
	 * Returns <code>True</code> if the menu descriptor can be applied to the widget type, otherwise it return
	 * <code>false</code>
	 * 
	 * @param menu
	 *            the menu descriptor
	 * @param widgetType
	 *            a widget type
	 * 
	 * @return <code>True</code> if the menu descriptor can be applied to the widget type
	 */
	private boolean match(WidgetMenu menu, WidgetType widgetType) {
		this.widgetType  = widgetType;
		EditPart ep = getViewer().getFocusEditPart();
		Widget widget = (Widget) ep.getAdapter(Widget.class);

		for (WidgetType wt : menu.getWidgetTypes()) {
			if (!widgetType.isInstanceOf(wt)) {
				continue;
			}

			PropertyType pt = menu.getPropertyType();
			if (pt == null) {
				return true;
			}

			String value = menu.getPropertyValue();
			String value2 = widget.getPropertyValue(pt);
			
			boolean result = value.equalsIgnoreCase(value2);
			if (result == true) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Builds and returns an action provider given an action descriptor
	 * 
	 * @param actionDef
	 *            the action descriptor
	 * @return ActionProvider
	 */
	private ActionProvider getActionProvider(Action actionDef) {
		ActionProvider acProvider = null;
		if (StringUtils.isNotEmpty(actionDef.getProvider())) {
			acProvider = (ActionProvider) ClassUtils.newInstance(getClass().getClassLoader(), actionDef.getProvider());
			if (acProvider != null) {
				ActionProviderContextImpl ctx = new ActionProviderContextImpl ();
				ctx.setCommandStack(commandStack);
				ctx.setRegistry(this.actionRegistry);
				ctx.setSelection(this.getViewer().getSelection());
				ctx.setWidgetEditPart((WidgetEditPart)this.editPart);
				acProvider.setContext(ctx);
			}
		}
		return acProvider;
	}

	/**
	 * Build a contextual menu given a menu descriptor
	 * 
	 * @param manager
	 *            The menu manager
	 * @param rootMenu
	 *            the menu descriptor
	 */
	private void buildMenu(IMenuManager manager, WidgetMenu rootMenu) {

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

		/** Constructs the list of group/action based on the current editor mode. */
		LinkedHashMap<ActionGroup, LinkedHashSet<Action>> groups = new LinkedHashMap<ActionGroup, LinkedHashSet<Action>>();
		for (ActionGroup group : allGroups) {
			for (Action action : group.getActions()) {
				EditorMode mode = action.getMode();
				if (EditorMode.ALL == mode || editorMode == mode) {
					LinkedHashSet<Action> actions = groups.get(group);
					if (actions == null) {
						actions = new LinkedHashSet<Action>();
						groups.put(group, actions);
					}
					actions.add(action);
				}
			}
		}

		/** Build the contextual menu */	
		for (MenuItem item : allItems) {
			
			// Create a submenu for each MenuItem which has a parent
			IMenuManager currentMenu = manager;
			if (item.getParent() != null) {
				currentMenu = new MenuManager(item.getName());
				String itemName = item.getName();
				manager.add(new Separator(itemName));
				manager.add(currentMenu);
			}
			
			for ( ActionGroup group : item.getGroups()) {
				// Add a separator for each group				
				currentMenu.add(new Separator(group.getName()));
				List<Action> actions = group.getActions();
				for (Action actionDef : actions) {
					ActionProvider acProvider = getActionProvider(actionDef);
					if (acProvider != null) {
						IAction action = acProvider.getAction(actionDef);
						if (action != null) {
							if(action.getText().equals("Move Table Groups Left") ||
									action.getText().equals("Move Table Groups Right") ||
									action.getText().equals("Move Table Group Down") ||
									action.getText().equals("Move Table Group Up")) {
								if(widgetType.getName().equals("TableGroup")) {
									currentMenu.appendToGroup(group.getName(), action);
								}
							}
							else if(action.getText().equals("Copy") || action.getText().equals("Cut") || action.getText().equals("Paste")) {
								if(!widgetType.getName().equals("TableGroup")) {
									currentMenu.appendToGroup(group.getName(), action);
								}
							} else {
								currentMenu.appendToGroup(group.getName(), action);
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * Builds the context menu. The Page Designer specific actions need to have been registered already by the
	 * PageActionRegistry.
	 * 
	 * @param manager
	 *            The IMenuManager
	 */
	public void buildContextMenu(IMenuManager manager) {
		// We retrieve the focused edit part to retrieve the widget
		// and for passing it in the method getActionProvider for activating
		// the contextual menu with it's related action
		// We will retrieve in the MoveColumnActionProvider the contextual EditPart
		editPart = getViewer().getFocusEditPart();
		Widget widget = (Widget) AdaptableUtils.getAdapter(editPart, Widget.class);

		UIModel uimodel = UIModelRegistry.getUIModel();
		List<WidgetMenu> menus = uimodel.getMenus().getMenus();
		if (widget != null) {
			WidgetType wt = widget.getType();
			for (WidgetMenu menu : menus) {
				if (isContextualMenu(menu) && match(menu, wt)) {
					buildMenu(manager, menu);
					break;
				}
			}

		}
		// We register this to avoid errors in the ErrorLog
		manager.add(new Separator(GROUP_ADDITIONS));

	}

}
