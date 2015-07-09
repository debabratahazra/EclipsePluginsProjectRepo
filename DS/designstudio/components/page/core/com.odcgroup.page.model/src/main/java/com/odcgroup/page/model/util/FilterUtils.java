package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

public class FilterUtils{

	/**
	 * @param widgetType
	 * @return
	 */
	public static Set<PropertyType> populateWithAllWidgetTypeProperties(Widget widget) {
		WidgetType widgetType = widget.getType();
		Set<PropertyType> properties = new HashSet<PropertyType>();
		properties.addAll(widgetType.getAllPropertyTypes().values());
		return properties;
	}
	
	/**
	 * @param propertyType
	 * @param visiblePropertyTypes
	 */
	private static void removeProperty(PropertyType propertyType, Set<PropertyType> visiblePropertyTypes) {
		if(propertyType != null) {
			visiblePropertyTypes.remove(propertyType);
		}
	}
	
	/**
	 * @param propertyName 
	 * @param visibilePropertyTypes
	 */
	public static void hideProperty(String propertyName, Widget widget, Set<PropertyType> visibilePropertyTypes) {
		Property prop=widget.findProperty(propertyName);
		if(prop!=null){
		PropertyType propertyType = prop.getType();
		removeProperty(propertyType, visibilePropertyTypes);
		}
	}
	
	/**
	 * @param originalWidgetProperties
	 * @param currentWidgetProperties
	 * @return
	 */
	public static List<Property> filterProperties(EList<Property> originalWidgetProperties, Set<PropertyType> currentWidgetProperties) {
		List<Property> filteredProperties = new ArrayList<Property>();
		
		for (Property property : originalWidgetProperties) {
			if (currentWidgetProperties.contains(property.getType())) {
				filteredProperties.add(property);
			}
		}
		return filteredProperties;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static boolean isSignificantFigureType(MdfEntity type) {
		if(type.getParentDomain().getName().equalsIgnoreCase("mml")) {
			return FilterUtils.isPrimitiveType(type);
		}
		else if(type.getParentDomain().getName().equalsIgnoreCase("AAABusinessTypes")) {
			return FilterUtils.isBusinessType(type);
		}
		else {
			return false;
		}
	}

	/**
	 * @param type
	 * @return
	 */
	public static boolean isBusinessType(MdfEntity type) {
		String typeNme = type.getName();
		if (typeNme.equalsIgnoreCase("amount")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("exchange")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("longAmount")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("number")) {
			return true;
		}
		if (typeNme.equalsIgnoreCase("percent")) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @param type
	 * @return
	 */
	public static boolean isPrimitiveType(MdfEntity type) {
		String typeName = type.getName();
		if (typeName.equalsIgnoreCase("double")) {
			return true;
		}
		if (typeName.equalsIgnoreCase("decimal")) {
			return true;
		}
		if (typeName.equalsIgnoreCase("float")) {
			return true;
		}
		else {
			return false;
		}
	}
}
