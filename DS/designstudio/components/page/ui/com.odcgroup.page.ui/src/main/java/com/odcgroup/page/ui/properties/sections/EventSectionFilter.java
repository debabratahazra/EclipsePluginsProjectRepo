package com.odcgroup.page.ui.properties.sections;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * @author atr
 */
public class EventSectionFilter implements IFilter {

	/**
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		boolean select=false;
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			Widget root=w.getRootWidget();
			select= MetaModelRegistry.supportsEvents(w.getType());
			//check if the widget root is xtooltip type dont show the event section.
			if(root!=null){
				Property fragmenttype=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
				if(fragmenttype!=null&&fragmenttype.getValue().equals("xtooltip")){
					return false;
				}
				Property searchType=root.findProperty(PropertyTypeConstants.SEARCH);
				if(searchType!=null&&searchType.getValue().equals("xtooltip")){
					return false;
				}
			}
			
		}
		return select;
	}

}
