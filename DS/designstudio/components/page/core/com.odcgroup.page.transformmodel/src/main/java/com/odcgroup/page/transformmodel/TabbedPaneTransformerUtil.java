package com.odcgroup.page.transformmodel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ModuleContainmentUtil;

/**
 * TODO: Document me!
 * 
 * @author pkk
 * 
 */
public final class TabbedPaneTransformerUtil {

	/**
	 * @param tabbedPane
	 * @return the name of the dataset's attribute used for the filter
	 */
	public static String getFilteredAttributeName(Widget tabbedPane) {
		String value = "";
		Property p = tabbedPane.findProperty("tabs-filtered-attribute");
		if (p != null) {
			String v = p.getValue();
			if (StringUtils.isNotEmpty(v)) {
				value = v;
			}
		}
		return value;
	}
	
	/**
	 * @param tabbedPane
	 * @return the name of the dataset linked to a tabbed pane
	 */
	public static String getDatasetName(Widget tabbedPane) {
		String value = "";
		Property p = tabbedPane.findPropertyInParent(PropertyTypeConstants.DOMAIN_ENTITY);
		if (p != null) {
			String v = p.getValue();
			if (StringUtils.isNotEmpty(v)) {
				//value = v.replace(":", ".");
				value = v;
			}
		}
		return value;
	}
	
	/**
	 * @param tabbedPane
	 * @param propertyName
	 * @return the value of the property, never </code>null</code>
	 */
	public static String getPropertyValue(Widget tabbedPane, String propertyName) {
		String value = "";
		Property p = tabbedPane.findProperty(propertyName);
		if (p != null) {
			String v = p.getValue();
			if (StringUtils.isNotEmpty(v)) {
				value = v;
			}
		}
		return value;
	}

	/**
	 * @param tabbedPane
	 * @return DSTableModel.&lt;DomainName&gt;.&lt;DatasetName&gt;
	 */
	public static String getModelReference(Widget tabbedPane) {
		return "DSTableModel." + getDatasetName(tabbedPane).replace(":", ".");
	}

	/**
	 * @param tab
	 * @return the value of property "tab-filter" (DS-3437)
	 */
	public static String getDynamicTabFilterValue(Widget tab) {
		return getPropertyValue(tab, "tab-filter");
	}

	/**
	 * check if the widget is
	 * 
	 * @param context
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isParentStandalone(WidgetTransformerContext context) {
		boolean standalone = false;
		List parentWidgets = context.getParentWidgets();
		for (Object object : parentWidgets) {
			Widget root = (Widget) object;
			if (root.getTypeName().equals(WidgetTypeConstants.INCLUDE)) {
				Widget parent = root.getRootWidget();
				standalone = isModuleStandalone(parent);
			}
		}
		return standalone;
	}

	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean isModule(Widget widget) {
		if (widget != null && widget.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			return true;
		}
		return false;
	}

	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean isModuleStandalone(Widget widget) {
		if (isModule(widget)) {
			String propertyValue = widget.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT);
			if (propertyValue.equals(ModuleContainmentUtil.MODULE_CONTAINMENT_STANDALONE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param tabbedPane
	 * @return true if the tabbed pane is dynamic
	 */
	public static boolean isDynamicTabbedPane(Widget tabbedPane) {
		boolean result = false;
		if ("TabbedPane".equals(tabbedPane.getTypeName())) {
			Property p = tabbedPane.findProperty("hide-empty-tabs");
			result = p != null && p.getBooleanValue();
		}
		return result;
	}
	
	public static int getFilterLevel(Widget tabbedPane) {
		int result = 99; //default
		if ("TabbedPane".equals(tabbedPane.getTypeName())) {
			Property p = tabbedPane.findProperty("filter-level");
			if (p != null) result = p.getIntValue();
		}
		return result;
	}
    
	/**
	 * @param tab
	 * @return true if the tab must be hidden when empty
	 */
	public static boolean isTabEnabled(Widget tab) {
		boolean result = false;
		if ("Tab".equals(tab.getTypeName())) {
			Property p = tab.findProperty("enabled");
			result = p != null && p.getBooleanValue();
		}
		return result;
	}
	
	/**
	 * @param tab
	 * @return true if the tab must be hidden when empty
	 */
	public static boolean hideEmptyTab(Widget tab) {
		boolean result = false;
		if ("Tab".equals(tab.getTypeName())) {
			Property p = tab.findProperty("hide-empty-tab");
			result = p != null && p.getBooleanValue();
		}
		return result;
	}
	
	/**
	 * @return
	 */
	public static List<String> getDynamicTabCustomEventFilterParam() {
		List<String> props = new ArrayList<String>();
		props.add(ParameterTypeConstants.DELAY);
		props.add(ParameterTypeConstants.HEIGHT);
		props.add(ParameterTypeConstants.LEFT);
		props.add(ParameterTypeConstants.MODAL);
		props.add(ParameterTypeConstants.TOP);
		props.add(ParameterTypeConstants.WIDTH);
		props.add(ParameterTypeConstants.URI);
		props.add(ParameterTypeConstants.METHOD);
		props.add(ParameterTypeConstants.ONLY_CHANGED);
		props.add(ParameterTypeConstants.TARGET);
		props.add(ParameterTypeConstants.CALL_URI);
		return props;
	}

}
