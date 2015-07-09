package com.odcgroup.menu.editor.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.menu.model.MenuItem;

public class MenuLabelProvider extends LabelProvider {
	/**
	 * Creates a new WidgetLabelProvider.
	 */
	public MenuLabelProvider() {
		super();
	}

	/**
	 * Returns null.
	 * 
	 * @param objects The Objects to get the Image for
	 * @return Image
	 */
	public Image getImage(Object objects) {	
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object objects) {
		if (objects == null) {
			return "";	
		}
		
		if (! (objects instanceof IStructuredSelection)) {
			return "";
		}
		
		IStructuredSelection ss = (IStructuredSelection) objects;
		if (ss.isEmpty() || ss.size() > 1) {
			return "";
		}

		Object obj = ss.getFirstElement();
		MenuItem m = getMenuItem(obj);
		if (m == null) {
			return "";
		}
		return m.getName();
	}

   /**
    * Gets the MenuItem from the element. This uses the IAdaptable mechanism
    * to retreive a reference to the MenuItem.
    * 
    * @param element The element to get the Widget for
    * @return MenuItem The MenuItem
    */
   private MenuItem getMenuItem(Object element) {
   	if (element instanceof MenuItem) {
   		return (MenuItem) element;
   	}
   	if (element instanceof IAdaptable) {
   		IAdaptable a = (IAdaptable) element;
   		return (MenuItem) a.getAdapter(MenuItem.class);
   	}
   	
   	return null;
   }
}
