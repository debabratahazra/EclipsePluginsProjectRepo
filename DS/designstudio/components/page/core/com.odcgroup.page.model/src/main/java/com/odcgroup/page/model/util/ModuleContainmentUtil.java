package com.odcgroup.page.model.util;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public final class ModuleContainmentUtil {
	
	/**  */
	public static final String MODULE_CONTAINMENT_STANDALONE = "stand-alone";
	/**  */
	public static final String MODULE_CONTAINMENT_CONTAINER = "container";	
	
	
	/**
	 * @param module
	 * @return boolean
	 */
	public static boolean canContainModules(Widget module) {
		if (module != null && module.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			String propertyValue = module.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT);
			if (propertyValue != null && propertyValue.equals(MODULE_CONTAINMENT_CONTAINER)) {
				return true;
			}			
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param module
	 * @return boolean
	 */
	public static boolean isContainerModule(Widget module) {
		if (module != null && module.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			String propertyValue = module.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT);
			if (propertyValue != null && propertyValue.equals(MODULE_CONTAINMENT_CONTAINER)) {
				return true;
			}			
		}
		return false;
	}	
	
	/**
	 * 
	 * @param module
	 * @return boolean
	 */
	public static boolean isStandaloneModule(Widget module) {
		if (module != null && module.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			String propertyValue = module.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT);
			if (propertyValue != null && propertyValue.equals(MODULE_CONTAINMENT_STANDALONE)) {
				return true;
			}			
		}
		return false;
	}	
	
	public static boolean isContainerOrStandalone(Widget module) {
		return ModuleContainmentUtil.isContainerModule(module) 
			|| ModuleContainmentUtil.isStandaloneModule(module);
	}
	
	/**
	 * @param property referenced included model
	 * @return true if the included model represents a container/standalone module.
	 */
	public static boolean includeContainerOrStandaloneModule(Property property) {
		boolean isModule = false;
		if (property != null) {
			Model model = property.getModel();
			if (model != null) {
				Widget root = model.getWidget();
				if (root != null) {
					isModule = isContainerOrStandalone(root); 
				}
			}
		}
		return isModule;
	}

}
