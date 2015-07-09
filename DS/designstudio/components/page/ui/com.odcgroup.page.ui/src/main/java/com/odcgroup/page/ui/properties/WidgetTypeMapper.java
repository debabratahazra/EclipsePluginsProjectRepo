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

import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * Type mapper for the logic example. We want to get the GEF model
 * object from the selected element in the outline view and the diagram.
 * We can then filter on the model object type.
 * 
 * @author ATR
 * @author Gary Hayes
 */
public class WidgetTypeMapper extends AbstractTypeMapper {

	/**
	 * Maps the type. Used the IAdaptable mechanism for the mapping.
	 * 
	 * @param object The Object to Map
	 * @return Class The Mapped type
	 */
	public Class mapType(Object object) {
		Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
		if (w == null) {
			return object.getClass();
		}
		
		return w.getClass();
	}	
}