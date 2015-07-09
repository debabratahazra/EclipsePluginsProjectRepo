package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 *
 * @author pkk
 *
 */
public class BoxWidgetPropertyFilter implements PropertyFilter {

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.filter.PropertyFilter#filter(com.odcgroup.page.model.Widget)
	 */
	@Override
	public List<Property> filter(Widget widget) {
		
		// These are the WidgetType's of the Label and Field
		MetaModel mm = MetaModelRegistry.getMetaModel();
		String library = widget.getLibraryName();
		WidgetType wt = mm.findWidgetType(library, widget.getTypeName());
		
		// Add all the PropertyTypes which exist 
		Set<PropertyType> allowedTypes = new HashSet<PropertyType>();
		allowedTypes.addAll(wt.getAllPropertyTypes().values());		
		
		if (isContainedInMatrixCell(widget)) {
			allowedTypes.remove(mm.findPropertyType(library, PropertyTypeConstants.CSS_CLASS));
		} 
		
		List<Property> result = new ArrayList<Property>();
		for (Property p : widget.getProperties()) {
			if (allowedTypes.contains(p.getType())) {
				result.add(p);
			}
		}
		
		return result;
	
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private boolean isContainedInMatrixCell(Widget widget) {
		if (WidgetTypeConstants.BOX.equals(widget.getTypeName())) {
			Widget matrixCell = getMatrixCell(widget);
			if (matrixCell != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private Widget getMatrixCell(Widget widget) {
		Widget parent = widget.getParent();
		if (parent != null) {
			if ("MatrixContentCell".equals(parent.getTypeName())) {
				return parent;
			} else {
				return getMatrixCell(parent);
			}
		}
		return null;
	}

}
