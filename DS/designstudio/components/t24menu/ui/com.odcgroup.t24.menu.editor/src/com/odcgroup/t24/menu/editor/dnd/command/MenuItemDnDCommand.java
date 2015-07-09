package com.odcgroup.t24.menu.editor.dnd.command;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;

import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.presentation.MenuEditor;
/**
 * MenuitemDnDCommand command class for Menuitem Dnd 
 * @author snn
 *
 */
public class MenuItemDnDCommand extends AbstractCommand {

	private int keyboardKeycode;
	private MenuEditor menuEditor;

	public MenuItemDnDCommand(MenuEditor editor, int keyCode) {
		this.keyboardKeycode = keyCode;
		this.menuEditor = editor;
	}
    /*
     * (non-Javadoc)
     * @see org.eclipse.emf.common.command.Command#execute()
     */
	public void execute() {

		Viewer viewer = menuEditor.getViewer();
		if (viewer instanceof TreeViewer) {
			TreeViewer treeViewer = (TreeViewer) viewer;
			ISelection selctedItems = treeViewer.getSelection();
			if (selctedItems instanceof IStructuredSelection
					&& ((IStructuredSelection) selctedItems).size() == 1) {
				MenuItem selecteditem = (MenuItem) ((IStructuredSelection) selctedItems)
						.getFirstElement();

				if (keyboardKeycode == SWT.ARROW_UP |keyboardKeycode == SWT.ARROW_LEFT) {
					moveUpMenuItem(selecteditem, keyboardKeycode);

				} else if (keyboardKeycode == SWT.ARROW_DOWN |keyboardKeycode ==SWT.ARROW_RIGHT) {
					moveDownMenuItem(selecteditem, keyboardKeycode);
				}

				treeViewer.refresh();

			}

		}

	}

	/**
	 * move one level up the selected menu item
	 * 
	 * @param selectedMenuItem
	 */
	private void moveUpMenuItem(MenuItem selectedMenuItem, int action) {
		if (selectedMenuItem.eContainer() instanceof MenuItem) {
			if (action == SWT.ARROW_UP) {
				MenuItem parentItem = (MenuItem) selectedMenuItem.eContainer();
				int selectedItemIndex = parentItem.getSubmenus().indexOf(
						selectedMenuItem);
				if (selectedItemIndex != 0) {
					parentItem.getSubmenus().move(selectedItemIndex - 1,
							selectedItemIndex);
				}

			}
			if (action == SWT.ARROW_LEFT) {
				MenuItem parentItem = (MenuItem) selectedMenuItem.eContainer();
				if (parentItem.eContainer() instanceof MenuItem) {
					MenuItem superParent = (MenuItem) parentItem.eContainer();
					parentItem.getSubmenus().remove(selectedMenuItem);
					superParent.getSubmenus().add(selectedMenuItem);
					

				}

			}
		}

	}

	/**
	 * move down the selected menu item.
	 * 
	 * @param selectedMenuItem
	 */
	private void moveDownMenuItem(MenuItem selectedMenuItem, int action) {
		if (selectedMenuItem.eContainer() instanceof MenuItem) {
			if (action == SWT.ARROW_DOWN) {
			MenuItem parentItem = (MenuItem) selectedMenuItem.eContainer();
			int size = parentItem.getSubmenus().size();
			int selectedItemIndex = parentItem.getSubmenus().indexOf(
					selectedMenuItem);
			if (selectedItemIndex != size - 1) {
				parentItem.getSubmenus().move(selectedItemIndex + 1,
						selectedItemIndex);
			}
			
			}
			
			if(action==SWT.ARROW_RIGHT){
				MenuItem parentItem = (MenuItem) selectedMenuItem.eContainer();
				EList<MenuItem>  submenus=parentItem.getSubmenus();
				 if(submenus.size()-1!=0){
					submenus.remove(selectedMenuItem); 
					MenuItem firstSibling=submenus.get(0);
					firstSibling.getSubmenus().add(0, selectedMenuItem);
					
				}
				
			}
		}
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 */
	public boolean canExecute() {
		return true;
	}

	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
