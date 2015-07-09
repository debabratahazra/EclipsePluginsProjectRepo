package com.odcgroup.page.external.mdf;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public final class AnnotationWidgetPropertyHelper {

	/**
	 * Avoid instantiation
	 */
	private AnnotationWidgetPropertyHelper() {
	}
	
	/**
	 * @param widgetType
	 * @param excludedNames 
	 * @return List<PropertyType>
	 */
	@SuppressWarnings("unchecked")
	public static List<PropertyType> fetchDerivablePropertyTypes(WidgetType widgetType, Set<String> excludedNames) {
		List<PropertyType> filteredPropertyTypes = (List<PropertyType>) 
			CollectionUtils.select(widgetType.getAllPropertyTypes().values(), new DerivablePropertyTypesPedicate(excludedNames));
		Collections.sort(filteredPropertyTypes, new PropertyTypeSorter());
		return filteredPropertyTypes;
	}
	
	/**
	 *
	 */
	static class DerivablePropertyTypesPedicate implements Predicate {
		
		private Set<String> names;
		/**
		 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object object) {
			if (object instanceof PropertyType) {
				PropertyType type = (PropertyType) object;
				if (type.isVisibleInDomain() && ! names.contains(type.getName())) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * @param excludedNames
		 */
		public DerivablePropertyTypesPedicate(Set<String> excludedNames) {
			names = excludedNames;
		}
		
	}		
	
	/**
	 * 
	 */
	static class PropertyTypeSorter implements Comparator<PropertyType> {
		
		private String getName(PropertyType type) {
			String name = type.getDisplayName();
			if (StringUtils.isEmpty(name)) {
				name = "";
			}
			return name;
		}

		/* 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(PropertyType left, PropertyType right) {
			return getName(left).compareTo(getName(right));
		}
		
	}
	
	
}
