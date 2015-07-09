package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
/**
 * FragmentPropertyFilter calss for filtering of the fragment properties.
 * @author snn
 *
 */
public class FragmentPropertyFilter implements PropertyFilter {

	/**
	 * filter the properties of the fragment widget
	 */
	public List<Property> filter(Widget widget) {
		
		Set<PropertyType> allTypes = new HashSet<PropertyType>();
		allTypes.addAll(widget.getType().getAllPropertyTypes().values());
		Property frgamntType=widget.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
		//if the fragment type is xtooltip type hide the cardinality and default fragment.
		if(frgamntType!=null&&frgamntType.getValue().equals("xtooltip")){
			allTypes.remove(widget.findProperty(PropertyTypeConstants.DEFAULT_FRAGMENT).getType());
			allTypes.remove(widget.findProperty(PropertyTypeConstants.CARDINALITY).getType());	
		}
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
     return result;
	}

}
