package com.odcgroup.server.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;

import com.odcgroup.server.ui.ServerUICore;

/**
 * Action for the pull down menu of the tool bar
 */
public class CreateServerAction extends Action implements IMenuCreator, IAddAction {

	private MenuManager menuManager;
	private List<Action> actions = new ArrayList<Action>();
	
	public CreateServerAction() {
		setMenuCreator(this);
	}
	
	@Override
	public Menu getMenu(Control parent) {
		Menu menu = null;
		if (menuManager == null) {
			menuManager = new MenuManager();
			menu = menuManager.createContextMenu(parent);
			menuManager.removeAll();
			for (Action action:actions) {
				menuManager.add(action);
			}
		} else {
			menu = menuManager.getMenu();
		}
		return menu;
	}
	
	public void runWithEvent(Event event) {
    	if (event.widget instanceof ToolItem) {
			ToolItem toolItem= (ToolItem) event.widget;
			Control control= toolItem.getParent();
    		Menu menu= getMenu(control);
    		menu.setVisible(true);
    	}
	}
	
	@Override
	public void addAction(Action action) {
		action.setImageDescriptor(ServerUICore.getImageDescriptor("icons/addserver.png"));
		actions.add(action);
	}
	
	@Override
	public void dispose() {
		if(menuManager != null) {
			menuManager.dispose();
		}
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
	
}
