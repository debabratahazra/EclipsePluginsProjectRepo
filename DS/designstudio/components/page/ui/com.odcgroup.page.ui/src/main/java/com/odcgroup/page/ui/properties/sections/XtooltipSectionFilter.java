package com.odcgroup.page.ui.properties.sections;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.util.AdaptableUtils;
/**
 * Xtooltip Section filter
 * @author snn
 *
 */
public class XtooltipSectionFilter implements IFilter {

	/**
	 * filter if xtooltip properties are empty.
	 */
	public boolean select(Object object) {
		boolean select = false; 
		if (object instanceof EditPart) {
			IWidgetPropertySource source = (IWidgetPropertySource) AdaptableUtils.getAdapter(object, IWidgetPropertySource.class);
			if (source != null) {
			  Widget root=source.getWidget().getRootWidget();
			  //check if the frgament type is xtooltip
			  Property frgamentProp=root.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
			  if(frgamentProp!=null&&frgamentProp.getValue().equalsIgnoreCase("xtooltip")){
				  return false;
			  }
			  //check if the module type is xtooltip.
			  Property moduleProp=root.findProperty(PropertyTypeConstants.SEARCH);
			  if(moduleProp!=null&&moduleProp.getValue().equalsIgnoreCase("xtooltip")){
				  return false;
			  }
			  
			  source.setCurrentPropertyCategory(PropertyCategory.XTOOLTIP_LITERAL);
			  select = source.getPropertyDescriptors().length > 0;
				
				
			}
		}
		return select;
	}

}
