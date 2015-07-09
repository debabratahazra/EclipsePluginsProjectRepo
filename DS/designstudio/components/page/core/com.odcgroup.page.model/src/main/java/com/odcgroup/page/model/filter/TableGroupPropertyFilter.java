package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
/**
 * PropertyFilter class for the Table Group.
 * @author snn
 *
 */
public class TableGroupPropertyFilter implements PropertyFilter {

    @Override
    public List<Property> filter(Widget widget) {
	List<Property> filterList=new ArrayList<Property>();
	EList<Property> propertyList= widget.getProperties();
	for(Property p:propertyList){
	    if(!(p.getTypeName().equals(PropertyTypeConstants.MAX_GROUPING))){
		filterList.add(p);
	    }
	}
	return filterList;
    }

}
