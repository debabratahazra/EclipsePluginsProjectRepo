package com.odcgroup.menu.editor.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

import com.odcgroup.menu.model.MenuItem;

/**
 * @author scn
 *
 */
@SuppressWarnings("rawtypes")
public class MenuTypeMapper implements ITypeMapper {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.ITypeMapper#mapType(java.lang.Object)
	 */
	public Class mapType(Object object) {
		MenuItem m = (MenuItem) getAdapter(object, MenuItem.class);
		if (m == null) {
			return object.getClass();
		}

		return m.getClass();
	}

	/**
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static Object getAdapter(Object object, Class clazz) {
		if (object == null) {
			return null;
		}

		if (clazz.isInstance(object)) {
			return object;
		}

		if (!(object instanceof IAdaptable)) {
			return null;
		}

		IAdaptable a = (IAdaptable) object;
		return a.getAdapter(clazz);
	}
}
