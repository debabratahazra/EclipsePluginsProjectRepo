package com.odcgroup.menu.model.translation;

import com.odcgroup.translation.core.IFilter;

/**
 * @author snn
 *
 */
public class MenuItemTranslationFilter implements IFilter {

	/**
	 * 
	 */
	public MenuItemTranslationFilter() {
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object toTest) {
		return true;
	}

}
