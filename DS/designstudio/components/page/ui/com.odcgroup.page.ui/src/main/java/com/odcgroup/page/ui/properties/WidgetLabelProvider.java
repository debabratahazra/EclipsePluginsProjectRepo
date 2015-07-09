/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.odcgroup.page.ui.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.model.Widget;

/**
 * Label provider for the title bar for the tabbed property sheet page.
 * 
 * @author ATR
 * @version 1.0
 * @author Gary Hayes
 */
public class WidgetLabelProvider extends LabelProvider {
	/** type mapper */
	
	/**
	 * Creates a new WidgetLabelProvider.
	 */
	public WidgetLabelProvider() {
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
		Widget w = getWidget(obj);
		if (w == null) {
			return "";
		}
		return w.getType().getDisplayName();
	}

    /**
     * Gets the Widget from the element. This uses the IAdaptable mechanism
     * to retreive a reference to the Widget.
     * 
     * @param element The element to get the Widget for
     * @return Widget The Widget
     */
    private Widget getWidget(Object element) {
    	if (element instanceof Widget) {
    		return (Widget) element;
    	}
    	if (element instanceof IAdaptable) {
    		IAdaptable a = (IAdaptable) element;
    		return (Widget) a.getAdapter(Widget.class);
    	}
    	
    	// Not a Widget
    	return null;
    }
}
