package com.odcgroup.page.transformmodel;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Helper class for bean properties
 */
public final class BeanPropertyUtils {

    /**
     * Finds the BeanPropertyPrefix property. This is a concatenation of prefixes found in parent
     * Fragments.
     * 
     * @param context
     *            The WidgetTransformerContext
     * @param widget The Widget to look for the BeanPropertyPrefix for
     * @return String The prefix
     */
    @SuppressWarnings("unchecked")
	public static String findBeanPropertyPrefix(WidgetTransformerContext context, Widget widget) {
        Widget w = widget;
        List<Widget> parentWidgets = context.getParentWidgets();
        int parentIndex = parentWidgets.size() - 1;
        
        String s = "";
        while (w != null && !w.getTypeName().equals(WidgetTypeConstants.MODULE)) {
            Property p = w.findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
            if (p != null) {
                s = p.getValue() + s;
            }
            
            w = w.getParent();
            if (w == null && parentIndex >= 0) {
                w = parentWidgets.get(parentIndex);
                parentIndex -= 1;
            }
        }
		return s;           
    }	
    
    /**
     * Finds the BeanPropertyPostfix property. This is a concatenation of prefixes found in parent
     * Fragments.
     * 
     * @param context
     *            The WidgetTransformerContext
     * @param widget The Widget to look for the BeanPropertyPostfix for
     * @return String The postfix
     */
    @SuppressWarnings("unchecked")
	public static String findBeanPropertyPostfix(WidgetTransformerContext context, Widget widget) {
        Widget w = widget;
        List<Widget> parentWidgets = context.getParentWidgets();
        int parentIndex = parentWidgets.size() - 1;
        
        String s = "";
        while (w != null) {
            Property p = w.findProperty(PropertyTypeConstants.BEAN_PROPERTY_POSTFIX);
            if (p != null) {
                s = s + p.getValue();
            }
            
            w = w.getParent();
            if (w == null && parentIndex >= 0) {
                w = parentWidgets.get(parentIndex);
                parentIndex -= 1;
            }
        }
        
        return s;
    }
    
	/**
	 * Finds the first BeanName property which is not empty. This looks for the BeanName in the parent
	 * Widgets.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget The Widget to look for the BeanName for
	 * @return Property The BeanName or null if no BeanName could be found
	 */
	public static Property findBeanNameProperty(WidgetTransformerContext context, Widget widget) {
		return findNonNullProperty(context, widget, PropertyTypeConstants.BEAN_NAME);
	}	
	
	/**
	 * Finds the first property with the given name which is not empty. This looks for the property in the parent
	 * Widgets.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget The Widget to look for the property for
	 * @param propertyName The property name to look for
	 * @return Property The property or null if no property could be found
	 */
	@SuppressWarnings("unchecked")
	public static Property findNonNullProperty(WidgetTransformerContext context, Widget widget, String propertyName) {
		Widget w = widget;
		List<Widget> parentWidgets = (List<Widget>)context.getParentWidgets();
		int parentIndex = parentWidgets.size() -1;
		
		while (w != null) {
			Property p = w.findProperty(propertyName);
			if (p != null && StringUtils.isNotEmpty(p.getValue())) {
				return p;
			}
			
			w = w.getParent();
			if (w == null && parentIndex >= 0) {
				w = parentWidgets.get(parentIndex);
				parentIndex -= 1;
			}
		}
				
		return null;
	}

	/**
	 * Finds the bean property prefix for the give widget. This looks for the property in the module
	 * Widget.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget The Widget to look for the property for
	 * @param propertyName The property name to look for
	 * @return Property The property or null if no property could be found
	 */
	public static String findModuleBeanPropertyPrefix(WidgetTransformerContext context, Widget widget) {
        Widget w = widget;
        String s = "";
        if (w != null && !w.getTypeName().equals(WidgetTypeConstants.MODULE)) {
        	if(w.getRootWidget().getTypeName().equals("Fragment")){
        			if(context.getParentWidgets().size()>0){
        				w =(Widget)context.getParentWidgets().get(0);
        			}
        	}
            Property p = w.getRootWidget().findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
            if (p != null) {
                s = p.getValue() + s;
            }
        }
		return s;           
    }	

}
