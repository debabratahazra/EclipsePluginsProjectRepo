package com.odcgroup.workbench.ui.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IActionFilter;

public class ActionFilterAdapterFactory implements IAdapterFactory {
	
	private static Class[] PROPERTIES = new Class[] {
		IActionFilter.class
	};

	public Class[] getAdapterList() {
		return PROPERTIES.clone();
	}

		public Object getAdapter(Object adaptableObject, Class adapterType) {
			return new ActionFilterDispatcher();
	}
}
