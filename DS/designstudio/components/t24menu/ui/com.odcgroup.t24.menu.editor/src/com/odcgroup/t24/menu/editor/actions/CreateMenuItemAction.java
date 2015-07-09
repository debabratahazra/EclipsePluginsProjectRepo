package com.odcgroup.t24.menu.editor.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.t24.menu.menu.MenuPackage;
/**
 * create MenuItem Action class creates the Menuitems in the Menu Editor. 
 */
public class CreateMenuItemAction extends CreateChildAction {
	
	/**
	 * createMenuitemAction Constructor. 
	 * @param editorPart
	 * @param selection
	 * @param descriptor
	 */
	public CreateMenuItemAction(IEditorPart editorPart, ISelection selection, Object descriptor) {
		super(editorPart, selection, descriptor);
	}

   /**
    * execute the CreateMenuItem Action.
    */
	public void run() {
		
		EObject value = ((CommandParameter)this.descriptor).getEValue();
		value.eSet(MenuPackage.Literals.MENU_ITEM__NAME, "menu");
		
		super.run();
	}
  
	  
}
