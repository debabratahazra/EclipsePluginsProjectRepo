package com.odcgroup.page.ui.properties.adapters;

import org.eclipse.core.runtime.IAdapterFactory;

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.ui.properties.WidgetPropertySource;

/**
 * This extension defines an adapter for instances of the 
 * {@link com.odcgroup.page.ui.properties.IWidgetPropertySource} class. 
 * When asked to adapt to the {@link org.eclipse.ui.views.properties.IPropertySource}, 
 * the <code>WidgetPropertySourceSourceAdapterFactory</code> is used.
 * 
 * @author <a href="mailto:atripod@odyssey-group.com">Alain Tripod</a>
 */
public class WidgetPropertySourceAdapterFactory implements IAdapterFactory {
	
	/**
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWidgetPropertySource.class) {
			if (adaptableObject instanceof WidgetEditPart) {
				WidgetEditPart wep = (WidgetEditPart)adaptableObject;
				return new WidgetPropertySource(wep.getWidget(), wep.getCommandStack());
			}
			
		}
		return null;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {IWidgetPropertySource.class};
	}

}
