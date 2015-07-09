package com.odcgroup.menu.editor.dnd.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.menu.editor.ui.MenuEditor;

/**
 * MenuItemDnDHandler Handler class for the MenuItem DnD command.
 * 
 * @author snn
 * 
 */
public class MenuItemDnDHandler extends AbstractHandler {

	/**
	 * @param ExecutionEvent
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    int keyCode=((Event)event.getTrigger()).keyCode;
		IWorkbenchPart activePart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		if (activePart instanceof MenuEditor) {
			MenuEditor menuEditor = (MenuEditor) activePart;
			((BasicCommandStack) (menuEditor.getEditingDomain())
					.getCommandStack()).execute(new MenuItemDnDCommand(menuEditor,keyCode));

		}
		

		return null;
	}

}
