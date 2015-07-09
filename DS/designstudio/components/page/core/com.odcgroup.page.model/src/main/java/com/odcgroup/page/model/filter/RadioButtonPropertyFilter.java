package com.odcgroup.page.model.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Filter to remove the 'selected', 'bean-name', 'bean-property' and 'Group' properties,
 * from the properties view of the Radio Button properties.
 * Created as part of DS-3561  
 * @author can
 *
 */
public class RadioButtonPropertyFilter implements PropertyFilter {

	private MetaModel metaModel = MetaModelRegistry.getMetaModel();

	@Override
	public List<Property> filter(Widget radioButtonWidget) {

		String domainAttributeProperty = radioButtonWidget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
		
		EList<Property> radioButtonProperties = radioButtonWidget.getProperties();
		
		String libraryName    = radioButtonWidget.getLibraryName();
		String widgetTypeName = radioButtonWidget.getTypeName();
		WidgetType widgetType = metaModel.findWidgetType(libraryName, widgetTypeName);
		Set<PropertyType> allWidgetTypeProperties = populateWithAllWidgetTypeProperties(widgetType);

		if (StringUtils.isNotEmpty(domainAttributeProperty)) {
			//DS-4873-removed  the bean name and bean property.
			hideProperty(PropertyTypeConstants.GROUP, libraryName, allWidgetTypeProperties);
			hideProperty(PropertyTypeConstants.REQUIRED, libraryName, allWidgetTypeProperties);
			hideProperty(PropertyTypeConstants.SELECTED, libraryName, allWidgetTypeProperties);
			
			List<Property> filteredProperties = filterProperties(radioButtonProperties, allWidgetTypeProperties);
			
			return filteredProperties;
		}
		else {
			return radioButtonProperties;
		}
	}

	/**
	 * @param originalWidgetProperties
	 * @param currentWidgetProperties
	 * @return
	 */
	private List<Property> filterProperties(EList<Property> originalWidgetProperties,
			Set<PropertyType> currentWidgetProperties) {
		List<Property> filteredProperties = new ArrayList<Property>();
		
		for (Property property : originalWidgetProperties) {
			if (currentWidgetProperties.contains(property.getType())) {
				filteredProperties.add(property);
			}
		}
		return filteredProperties;
	}

	/**
	 * @param propertyName 
	 * @param libraryName
	 * @param existingWidgetProperties
	 */
	private void hideProperty(String propertyName, String libraryName, Set<PropertyType> existingWidgetProperties) {
		PropertyType propertyType = metaModel.findPropertyType(libraryName, propertyName);
		removeProperty(propertyType, existingWidgetProperties);
	}


	/**
	 * @param widgetType
	 * @return
	 */
	private Set<PropertyType> populateWithAllWidgetTypeProperties(WidgetType widgetType) {
		Set<PropertyType> properties = new HashSet<PropertyType>();
		properties.addAll(widgetType.getAllPropertyTypes().values());
		return properties;
	}


	/**
	 * @param propertyType
	 * @param properties
	 */
	private void removeProperty(PropertyType propertyType, Set<PropertyType> properties) {
		if(propertyType != null) {
			properties.remove(propertyType);
		}
	}
}
