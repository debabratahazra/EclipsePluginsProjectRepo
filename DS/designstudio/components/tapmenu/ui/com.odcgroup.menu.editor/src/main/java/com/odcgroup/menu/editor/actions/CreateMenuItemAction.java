package com.odcgroup.menu.editor.actions;

import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
/**
 * create MenuItem Action class creates the Menuitems in the Menu Editor. 
 * @author snn
 *
 */
public class CreateMenuItemAction extends CreateChildAction {

	
	/**
	 * createMenuitemAction Constructor. 
	 * @param editorPart
	 * @param selection
	 * @param descriptor
	 */
	public CreateMenuItemAction(IEditorPart editorPart, ISelection selection,
			Object descriptor) {
		super(editorPart, selection, descriptor);
	
	}

   /**
    * execute the CreateMenuItem Action.
    */
	public void run() {
		super.run();
	}
  
	  
}
