package com.odcgroup.page.model.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Utility method for generating widget's unique id.
 * <p>
 * 
 * Be careful when updating the generation algorithm: Indeed The generated Id
 * might be persisted inside cookie (see THE if you have any doubt)
 * 
 * @author atr
 */
public class UniqueIdGenerator {

	/** For which widgets the Id must be generated */
	public final static Set<String> generateIdForWidgets = new HashSet<String>();

	static {
		generateIdForWidgets.add(WidgetTypeConstants.MODULE);
		generateIdForWidgets.add(WidgetTypeConstants.BUTTON);
		generateIdForWidgets.add(WidgetTypeConstants.RADIO_BUTTON);
		generateIdForWidgets.add(WidgetTypeConstants.CHECKBOX);
		generateIdForWidgets.add(WidgetTypeConstants.TAB);
		
		generateIdForWidgets.add(WidgetTypeConstants.TABLE);
		generateIdForWidgets.add(WidgetTypeConstants.TABLE_TREE);
		generateIdForWidgets.add(WidgetTypeConstants.TABLE_COLUMN);
		generateIdForWidgets.add(WidgetTypeConstants.COLUMN);
		generateIdForWidgets.add(WidgetTypeConstants.TABBED_PANE);
		generateIdForWidgets.add(WidgetTypeConstants.MATRIX);
		generateIdForWidgets.add(WidgetTypeConstants.MATRIX_CONTENTCELLITEM);
		generateIdForWidgets.add(WidgetTypeConstants.MATRIX_EXTRACOLUMNITEM);
		generateIdForWidgets.add(WidgetTypeConstants.MATRIX_CELLITEM);
		
		generateIdForWidgets.add(WidgetTypeConstants.CDM_TABLE);
		
		generateIdForWidgets.add(WidgetTypeConstants.AUTO_COMPLETE);
		generateIdForWidgets.add(WidgetTypeConstants.CALDATE_FIELD);
		generateIdForWidgets.add(WidgetTypeConstants.FILE_CHOOSER);
		generateIdForWidgets.add(WidgetTypeConstants.PASSWORD_FIELD);
		generateIdForWidgets.add(WidgetTypeConstants.SEARCH_FIELD);
		generateIdForWidgets.add(WidgetTypeConstants.TEXTAREA);
		generateIdForWidgets.add(WidgetTypeConstants.TEXTFIELD);
		generateIdForWidgets.add(WidgetTypeConstants.COMBOBOX);
		generateIdForWidgets.add(WidgetTypeConstants.LIST);		
		generateIdForWidgets.add(WidgetTypeConstants.HIDDEN_FIELD);		
		generateIdForWidgets.add(WidgetTypeConstants.ICON);
		generateIdForWidgets.add(WidgetTypeConstants.LABEL);		
		generateIdForWidgets.add(WidgetTypeConstants.LABEL_DOMAIN);		
		generateIdForWidgets.add(WidgetTypeConstants.BOX);		
		generateIdForWidgets.add(WidgetTypeConstants.EXTERNAL_INCLUDE_WIDGET);
	}

	/**
	 * Finds the BeanPropertyPrefix property. This is a concatenation of
	 * prefixes found in parent Fragments.
	 * 
	 * @param widget
	 *            The Widget to look for the BeanPropertyPrefix for
	 * @param parentWidgets 
	 * @return String The prefix
	 */
	private static String findBeanPropertyPrefix(Widget widget, List<Widget> parentWidgets) {
		Widget w = widget;
		int parentIndex = parentWidgets.size() - 1;

		String s = "";
		while (w != null) {
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
	 * @param widget
	 * @return id
	 */
	public static String generateWidgetId(Widget widget) {
		String id = "";
		String typeName = widget.getTypeName();
		if (generateIdForWidgets.contains(typeName)) {
			if (typeName.equals(WidgetTypeConstants.BUTTON)) {
				id = "btn_"+QTPCompliantIDGenerator.generateID();
			} else if (typeName.equals(WidgetTypeConstants.CHECKBOX)) {
				id = "btn_"+QTPCompliantIDGenerator.generateID();
			} else if (typeName.equals(WidgetTypeConstants.RADIO_BUTTON)) {
				id = "btn_"+QTPCompliantIDGenerator.generateID();
			} else if (typeName.equals(WidgetTypeConstants.MODULE)){
				Resource res = widget.eResource();
				String name = res == null ? "" : res.getURI().lastSegment();
				int pos = name.indexOf('.');
				if (pos >= 0) {
					name = name.substring(0, pos);
				}
				id = name+"_"+QTPCompliantIDGenerator.generateID();
			} else if (typeName.equals(WidgetTypeConstants.TAB)) {
				id = "tab_"+QTPCompliantIDGenerator.generateID();
			} else if (typeName.equals(WidgetTypeConstants.CDM_TABLE)){
				id = generateOtherId(widget);
			} else if (typeName.equals(WidgetTypeConstants.BOX)){
				id = ""; // DS-4786 - force blank value 
			} else if (typeName.equals(WidgetTypeConstants.LABEL)){
				id = ""; // DS-4786 - force blank value
			} else if (typeName.equals(WidgetTypeConstants.LABEL_DOMAIN)){
				id = ""; // DS-4786 - force blank value
			} else {
				id = QTPCompliantIDGenerator.generateID();				
			}
		}
		return id;
	}
	

	/**
	 * @param moduleName
	 * @return string
	 */
	public static String generateModuleId(String moduleName) {
		return moduleName+"_"+QTPCompliantIDGenerator.generateID();
	}
	
	/**
	 * @param widget
	 * @return id
	 */
	private static String generateOtherId(Widget widget) {

		String id = "";
		Widget root = widget.getRootWidget();
		Resource res = root.eResource();
		String name = res == null ? "" : root.eResource().getURI().lastSegment();
		int pos = name.indexOf('.');
		if (pos >= 0) {
			name = name.substring(0, pos);
		}

		// retrieve the current value
		id = widget.getID();
		if (StringUtils.isBlank(id)) {
			// not yet defined, so we generate a key
			String xmiId = QTPCompliantIDGenerator.generateID();
			StringBuilder buffer = new StringBuilder(id);
			buffer.append(widget.getTypeName());
			buffer.append("_");
			buffer.append(name);
			if (StringUtils.isNotBlank(xmiId)) {
				// buffer.append("_");
				buffer.append(xmiId);
			}
			id = buffer.toString();
		}
		return id;

	
	}

	/**
	 * Generates a unique Id for the designated widget
	 * 
	 * @param widget
	 *            The widget
	 * @param parentWidgets 
	 * @return identifier
	 */
	public static String generateId(Widget widget, List<Widget> parentWidgets) {
		String id = widget.getID();

		// do we have to generate an Id value for the designated widget ?
		if (generateIdForWidgets.contains(widget.getTypeName())) {
			if (StringUtils.isBlank(id)) {
				// generate the id iff not yet defined
				id = generateWidgetId(widget);
			}
		} else {
			if (StringUtils.isNotBlank(id) && parentWidgets != null) {
				String s = findBeanPropertyPrefix(widget, parentWidgets);
				id = s+id;
			}
		}
		return id;
	}
	
	/**
	 * Generates a unique Id for the designated widget
	 * 
	 * @param widget
	 *            The widget
	 * @return identifier
	 */
	public static String generateId(Widget widget) {
		return generateId(widget, null);
	}

	/**
	 * Force the regeneration of the ID of the specified widget
	 * @param widget
	 */
	public static void regenerateID(Widget widget) {
		if (generateIdForWidgets.contains(widget.getTypeName())) {
			widget.setID(generateWidgetId(widget));
		}
	}


}
