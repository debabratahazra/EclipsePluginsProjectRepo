package com.odcgroup.domain.ui.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

import com.odcgroup.mdf.metamodel.MdfModelElement;

public class DomainDSLTypeMapper implements ITypeMapper {



	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.ITypeMapper#mapType(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	public Class mapType(Object object) {
		MdfModelElement m = (MdfModelElement) getAdapter(object, MdfModelElement.class);
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
	@SuppressWarnings("rawtypes")
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
