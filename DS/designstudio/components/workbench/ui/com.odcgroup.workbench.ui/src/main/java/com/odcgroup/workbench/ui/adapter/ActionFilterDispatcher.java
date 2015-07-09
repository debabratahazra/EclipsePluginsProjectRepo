package com.odcgroup.workbench.ui.adapter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IActionFilter;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * This class serves as a dispatcher for different action filters that might be
 * registered by the <code>com.odcgroup.workbench.ui.actionFilter</code> extension point.
 * 
 * Normally, there is only one action filter possible for an object type (as this
 * is handled via adapters), so this class makes it possible to have multiple filters
 * to be applied. All filters have the same right - as soon as one of them returns
 * <code>false</code> for the target object, the action will be filtered, independent of
 * the return codes of the other filters.
 *
 * @author Kai Kreuzer
 *
 */
public class ActionFilterDispatcher implements IActionFilter {

	/**
	 * Extension point id for the action filters 
	 */
	private static final String ACTION_FILTER_EXTENSION = "com.odcgroup.workbench.ui.actionFilter";

	/**
	 * Name of the child elements of the extension point that define the filter ids.
	 */
	private static final String FILTER_ID = "filterId";

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionFilter#testAttribute(java.lang.Object, java.lang.String, java.lang.String)
	 */
	public boolean testAttribute(Object target, String name, String value) {
		IConfigurationElement[] exts = OfsCore.getExtensions(ACTION_FILTER_EXTENSION);
		for(IConfigurationElement ext : exts) {
			if(isFilterForId(ext, name)) {
				try {
					Object obj = ext.createExecutableExtension("class");
					if(obj instanceof IActionFilter) {
						IActionFilter filter = (IActionFilter) obj;
						// if this filter filters the target object, immediately return with "true".
						if(filter.testAttribute(target, name, value)) return true;
					}
				} catch (CoreException e) {
					OfsUICore.getDefault().logWarning("Cannot create class for extension" + ext.getName(), e);
				}
			}			
		}		
		return false;
	}

	private boolean isFilterForId(IConfigurationElement ext, String id) {
		IConfigurationElement[] filterIds = ext.getChildren(FILTER_ID);
		for(IConfigurationElement filterId : filterIds) {
			String idString = filterId.getAttribute("id");
			if(id!=null && id.equals(idString)) return true;
		}
		return false;
	}
}
