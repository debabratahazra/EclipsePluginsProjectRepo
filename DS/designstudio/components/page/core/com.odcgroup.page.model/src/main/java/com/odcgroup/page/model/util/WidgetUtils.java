package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.Accountability;
import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;

/**
 * Utility methods for Widgets.
 * 
 * @author Gary Hayes
 */
public class WidgetUtils {

	/** The character used to separate preview values. */
	private static final String PREVIEW_VALUE_SEPARATOR = ",";

	/**
	 * Returns true if the parent can contain the child Widget. This takes into
	 * account the AccountabilityRules.
	 * 
	 * @param parent
	 *            The parent Widget
	 * @param child
	 *            The child Widget
	 * @return boolean True if the parent Widget can contain the child Widget
	 */
	public static boolean canContainChild(Widget parent, Widget child) {
		return WidgetUtils.hasValidRule(getContainability(), parent, child);
	}

	/**
	 * Returns true if the parent can contain the child Widget. This takes into
	 * account the AccountabilityRules.
	 * 
	 * @param parent
	 *            The parent Widget
	 * @param childType
	 *            The child Widget
	 * @param inspectInclude
	 *            When true consider only the child widget within encountered include widget
	 * @return boolean True if the parent Widget can contain the child Widget
	 */
	public static boolean canContainChild(Widget parent, WidgetType childType, boolean inspectInclude) {
		return WidgetUtils.hasValidRule(getContainability(), parent, childType, inspectInclude);
	}

	/**
	 * Returns true if the parent can contain ALL the child Widgets. This takes
	 * into account the AccountabilityRules.
	 * 
	 * @param parent
	 *            The parent Widget
	 * @param children
	 *            The List of child Widgets to add
	 * @return boolean True if the parent Widget can contain the child Widget
	 */
	public static boolean canContainChildren(Widget parent,
			List<Widget> children) {
		for (Iterator<Widget> it = children.iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();

			AccountabilityRule ar = WidgetUtils.getContainability()
					.findAccountabilityRule(child.getType(), parent.getType());
			if (ar == null) {
				// No matching rule found - Not OK
				return false;
			}

			if (ar.getMaxOccurs() == -1) {
				// No need to check the cardinality since we can add as many
				// widgets as we like - OK
				continue;
			}

			// Note that we used the child returned by the AccountabilityRule
			// since the child passed as an argument is a specific type whereas
			// the rule may concern a more generic base type
			WidgetType arChildType = ar.getChild();

			List<?> filteredChildren = WidgetUtils
					.filter(children, arChildType);
			int childrenToBeAdded = filteredChildren.size();

			int numberAlreadyInParent = WidgetUtils
					.getNumberOfWidgetTypesInParent(parent, arChildType, false);

			if (numberAlreadyInParent + childrenToBeAdded > ar.getMaxOccurs()) {
				// Must have too many children already - NOT OK
				return false;
			}
		}

		// All OK
		return true;
	}

	/**
	 * Returns true if the parent can include the child Widget. This takes into
	 * account the AccountabilityRules.
	 * 
	 * @param parent
	 *            The parent Widget
	 * @param child
	 *            The child Widget
	 * @return boolean True if the parent Widget can include the child Widget
	 */
	public static boolean canIncludeChild(Widget parent, Widget child) {
		//check if the include fragment is of xtooltip type.
		if(isWidgetXtooltipType(child)||isWidgetXtooltipType(parent)){
			return false;
		}
		return WidgetUtils.hasValidRule(getIncludeability(), parent, child);
	}

	/**
	 * Returns true if Accountability has a valid rule linking the parent Widget
	 * to the child Widget.
	 * 
	 * @param accountability
	 *            The Accountability containing the rules to test
	 * @param parent
	 *            The parent Widget
	 * @param childType
	 *            The child Widget
	 * @param inspectInclude
	 *            When true consider only the child widget within encountered include widget
	 * @return boolean True if a valid rule exists between the parent Widget and
	 *         the child Widget
	 */
	private static boolean hasValidRule(Accountability accountability,
			Widget parent, WidgetType childType, boolean inspectInclude) {
		AccountabilityRule ar = accountability.findAccountabilityRule(
				childType, parent.getType());
		if (ar == null) {
			// No matching rule found - Not OK
			return false;
		}
		// contextual check for module with containment property
		if (parent.getTypeName().equals(WidgetTypeConstants.MODULE) 
				&& childType.getName().equals(WidgetTypeConstants.MODULE)) {
			Widget root = parent.getRootWidget();
			if(!ModuleContainmentUtil.canContainModules(root)) {
				return false;
			}
		}
		
		String[] widgets = {WidgetTypeConstants.CALDATE_FIELD, WidgetTypeConstants.PASSWORD_FIELD, 
				WidgetTypeConstants.SEARCH_FIELD, WidgetTypeConstants.TEXTAREA, 
				WidgetTypeConstants.TEXTFIELD, WidgetTypeConstants.COMBOBOX, 
				WidgetTypeConstants.RADIO_BUTTON};
		// 
		if (parent.getTypeName().equals(WidgetTypeConstants.TABLE_COLUMN)) {
			if (ArrayUtils.contains(widgets, childType.getName())) {
				return false;
			}
		}
		


		// end contextual check
		
		if (ar.getMaxOccurs() == -1) {
			// No need to check the cardinality since we can add as many widgets
			// as we like - OK
			return true;
		}

		// Note that we used the child returned by the AccountabilityRule
		// since the child passed as an argument is a specific type whereas
		// the rule may concern a more generic base type
		int numberAlreadyInParent = WidgetUtils.getNumberOfWidgetTypesInParent(
				parent, ar.getChild(), inspectInclude);

		if (numberAlreadyInParent < ar.getMaxOccurs()) {
			// We can still add children - OK
			return true;
		}

		// Must have too many children already - NOT OK
		return false;
	}
	
	/**
	 * Returns true if Accountability has a valid rule linking the parent Widget
	 * to the child Widget.
	 * 
	 * @param accountability
	 *            The Accountability containing the rules to test
	 * @param parent
	 *            The parent Widget
	 * @param childType
	 *            The child Widget
	 * @return boolean True if a valid rule exists between the parent Widget and
	 *         the child Widget
	 */
	private static boolean hasValidRule(Accountability accountability,
			Widget parent, WidgetType childType) {
		return hasValidRule(accountability, parent, childType, false);
	}

	
	/**
	 * Returns true if Accountability has a valid rule linking the parent Widget
	 * to the child Widget.
	 * 
	 * @param accountability
	 *            The Accountability containing the rules to test
	 * @param parent
	 *            The parent Widget
	 * @param child
	 *            The child Widget
	 * @return boolean True if a valid rule exists between the parent Widget and
	 *         the child Widget
	 */
	private static boolean hasValidRule(Accountability accountability,
			Widget parent, Widget child) {
		return WidgetUtils
				.hasValidRule(accountability, parent, child.getType());
	}

	/**
	 * Filters the List of Widgets returning only those which match the
	 * specified type. This method is generic and takes into account WidgetTypes
	 * which are children of the specified type.
	 * 
	 * @param widgets
	 *            The List of Widgets to filter
	 * @param libraryName
	 *            The library of the widget
	 * @param widgetName
	 *            The name of the of Widget to look for
	 * @return The filtered List of Widgets
	 */
	public static List<Widget> filter(List<Widget> widgets, String libraryName,
			String widgetName) {
		WidgetType cwt = MetaModelRegistry.getMetaModel().findWidgetType(
				libraryName, widgetName);
		return filter(widgets, cwt);
	}

	/**
	 * Filters the List of Widgets returning only those which match the
	 * specified type. This method is generic and takes into account WidgetTypes
	 * which are children of the specified type.
	 * 
	 * @param widgets
	 *            The List of Widgets to filter
	 * @param type
	 *            The type of Widget to look for
	 * @param inspectInclude
	 *            When true consider only the child widget within encountered include widget
	 * @return The filtered List of Widgets
	 */
	public static List<Widget> filter(List<Widget> widgets, WidgetType type, boolean inspectInclude) {
		List<Widget> filteredWidgets = new ArrayList<Widget>();
		for (Iterator<Widget> it = widgets.iterator(); it.hasNext();) {
			Widget w = (Widget) it.next();
			WidgetType wt = w.getType();
			
			if (inspectInclude && wt.getName().equals("Include")) {
				// inspect the include to retrieve the type of it's root widget
				if (w.getContents().size() > 0) {
					wt = w.getContents().get(0).getType();
				} else {
	                // Now find the associated Figure
	            	Property include = w.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
	            	if (include != null) {
		              	Model model = include.getModel();
		              	if (model != null && model.getWidget() != null) {
	              			wt = model.getWidget().getType();				}
	            	}
				}
			}
			
			if (wt.isInstanceOf(type)) {
				filteredWidgets.add(w);
			}
		}

		return filteredWidgets;
	}
	
	/**
	 * Filters the List of Widgets returning only those which match the
	 * specified type. This method is generic and takes into account WidgetTypes
	 * which are children of the specified type.
	 * 
	 * @param widgets
	 *            The List of Widgets to filter
	 * @param type
	 *            The type of Widget to look for
	 * @return The filtered List of Widgets
	 */
	public static List<Widget> filter(List<Widget> widgets, WidgetType type) {
		return filter(widgets, type, false);
	}
	
	/**
	 * Creates a List of Widgets containing ALL the children of the Widgets
	 * passed as a parameter. The parent widgets are not included.
	 * 
	 * @param parents
	 *            The parent Widgets
	 * @return List of all the child Widgets
	 */
	public static List<Widget> getChildren(List<Widget> parents) {
		List<Widget> result = new ArrayList<Widget>();
		for (Widget w : parents) {
			result.addAll(w.getContents());
		}
		return result;
	}
	
	
	/**
	 * @param widget
	 * @param idmap
	 */
	public static void replaceAggregatesForAMatrix(Widget widget, Map<String, String> idmap) {
		if (!widget.getContents().isEmpty() && widget.getTypeName().equals(WidgetTypeConstants.MATRIX)) {
			IMatrix matrix = MatrixHelper.getMatrix(widget);
			List<IMatrixContentCellItem> items = matrix.getMatrixCell()
					.getItems();
			List<IMatrixContentCellItem> cloned = new ArrayList<IMatrixContentCellItem>();
			cloned.addAll(items);
			List<String> newItems = new ArrayList<String>();
			for (IMatrixContentCellItem item : cloned) {
				if (item.isComputed()) {
					List<String> aggItems = item.getAggregationItems();
					for (String aggItem : aggItems) {
						String newItem = idmap.get(aggItem);
						if (newItem != null) {
							newItems.add(newItem);
						}
					}
					int index = cloned.indexOf(item);
					if (!newItems.isEmpty()) {
						items.get(index).setAggregationItems(newItems);
						newItems.clear();
					}
				}
			}
		}
	}

	/**
	 * Filters the List of Widgets returning only those which have the specified
	 * PropertyType and value.
	 * 
	 * @param widgets
	 *            The List of Widgets to filter
	 * @param type
	 *            The type of Property to look for
	 * @param value
	 *            The value to pass the filter
	 * @return The filtered List of Widgets
	 */
	public static List<Widget> filter(List<Widget> widgets, PropertyType type,
			String value) {
		List<Widget> filteredWidgets = new ArrayList<Widget>();
		for (Iterator<Widget> it = widgets.iterator(); it.hasNext();) {
			Widget w = (Widget) it.next();
			Property p = w.findProperty(type);
			if (p == null) {
				continue;
			}

			if (p.getValue().equals(value)) {
				filteredWidgets.add(w);
			}
		}

		return filteredWidgets;
	}

	/**
	 * Filters the List of Widgets returning only those for which the specified
	 * PropertyType is not empty.
	 * 
	 * @param widgets
	 *            The List of Widgets to filter
	 * @param type
	 *            The type of Property to look for
	 * @return The filtered List of Widgets
	 */
	public static List<Widget> filterNotEmpty(List<Widget> widgets,
			PropertyType type) {
		List<Widget> filteredWidgets = new ArrayList<Widget>();
		for (Iterator<Widget> it = widgets.iterator(); it.hasNext();) {
			Widget w = (Widget) it.next();
			Property p = w.findProperty(type);
			if (p == null) {
				continue;
			}

			if (!StringUtils.isEmpty(p.getValue())) {
				filteredWidgets.add(w);
			}
		}

		return filteredWidgets;
	}

	/**
	 * Gets the number of Widgets of the specified type which the parent Widget
	 * already contains. This method is generic and looks for sub-widgets of the
	 * same type.
	 * 
	 * @param parent
	 *            The parent Widget to test
	 * @param type
	 *            The WidgetType to look for
	 * @param inspectInclude
	 *            When true consider only the child widget within encountered include widget
	 * @return int The number of Widgets of this type found
	 */
	private static int getNumberOfWidgetTypesInParent(Widget parent,
			WidgetType type, boolean inspectInclude) {
		return parent.getWidgets(type, inspectInclude).size();
	}

	/**
	 * Gets the Accountability containing all the containment rules.
	 * 
	 * @return Accountability The Containability
	 */
	private static Accountability getContainability() {
		return MetaModelRegistry.getMetaModel().getContainability();
	}

	/**
	 * Gets the Accountability containing all the inclusion rules.
	 * 
	 * @return Accountability The Includeability
	 */
	public static Accountability getIncludeability() {
		return MetaModelRegistry.getMetaModel().getIncludeability();
	}

	/**
	 * Adds an observer on all widget properties
	 * 
	 * @param widget
	 *            the widget to be observed
	 * @param observer
	 *            the observer
	 */
	public static void addWidgetPropertiesObserver(Widget widget,
			Adapter observer) {
		for (Property p : widget.getProperties()) {
			p.eAdapters().add(observer);
		}
	}

	/**
	 * Removes the observer
	 * 
	 * @param widget
	 *            the widget
	 * @param observer
	 *            the observer
	 */
	public static void removeWidgetPropertiesObserver(Widget widget,
			Adapter observer) {
		for (Property p : widget.getProperties()) {
			p.eAdapters().remove(observer);
		}
	}

	/**
	 * Gets the preview values. These are comma separated values taken from the
	 * property 'previewValue'. This is used essentially for Tables and
	 * Matrices. See ColumnBodyDirectEditManager for a full description of the
	 * problem.
	 * 
	 * @param widget
	 *            The Widget to get the preview values for
	 * @return List of preview values
	 * 
	 */
	public static List<String> getPreviewValues(Widget widget) {
		String pv = widget.getPropertyValue(PropertyTypeConstants.PREVIEW_VALUE);
		return Arrays.asList(pv.split(WidgetUtils.PREVIEW_VALUE_SEPARATOR));
	}

	/**
	 * Gets the preview value. This is a String of comma separated values
	 * created from the old 'previewValue' property and the new previewValue and
	 * index. 'previewValue'. This is used essentially for Tables and Matrices.
	 * Elements which would be empty in the array are converted to empty
	 * Strings. Note that the Property itself is NOT updated since we need to do
	 * this via a command. See ColumnBodyDirectEditManager for a full
	 * description of the problem.
	 * 
	 * @param widget
	 *            The Widget to update
	 * @param pv
	 *            The preview value for the specified index
	 * @param index
	 *            The index to update
	 * @return String The new preview value
	 */
	public static String getPreviewValue(Widget widget, String pv, int index) {
		// First create a new List of Preview Values
		List<String> pvs = getPreviewValues(widget);
		if (pvs.size() < index + 1) {
			while (pvs.size() < index) {
				pvs.add("");
			}
			pvs.add(pv);
		} else {
			pvs.set(index, pv);
		}

		// Now convert it to a comma separated String
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pvs.size(); ++i) {
			String s = pvs.get(i);
			sb.append(s);
			if (i < pvs.size() - 1) {
				sb.append(WidgetUtils.PREVIEW_VALUE_SEPARATOR);
			}
		}

		return sb.toString();
	}

	/**
	 * Replaces the value of the specified Property for these Widgets AND all
	 * child Widgets.
	 * 
	 * @param widgets
	 *            The Widgets to replace the value for
	 * @param libraryName
	 *            The name of the WidgetLibrary
	 * @param propertyName
	 *            The name of the PropertyType
	 * @param value
	 *            The new value
	 */
//	public static void replaceRecursive(List<Widget> widgets,
//			String libraryName, String propertyName, String value) {
//		PropertyType pt = MetaModelRegistry.getMetaModel().findPropertyType(
//				libraryName, propertyName);
//		for (Widget w : widgets) {
//			replaceRecursive(w, pt, value);
//		}
//	}

	/**
	 * Replaces the value of the specified Property for these Widgets AND all
	 * child Widgets.
	 * 
	 * @param widgets
	 *            The Widgets to replace the value for
	 * @param propertyType
	 *            The PropertyType to replace
	 * @param value
	 *            The new value
	 */
//	public static void replaceRecursive(List<Widget> widgets,
//			PropertyType propertyType, String value) {
//		for (Widget w : widgets) {
//			replaceRecursive(w, propertyType, value);
//		}
//	}

	/**
	 * Replaces the value of the specified Property for this Widget AND all
	 * child Widgets.
	 * 
	 * @param widget
	 *            The Widget to replace the value for
	 * @param propertyType
	 *            The PropertyType to replace
	 * @param value
	 *            The new value
	 */
//	public static void replaceRecursive(Widget widget,
//			PropertyType propertyType, String value) {
//		Property p = widget.findProperty(propertyType);
//		if (p != null) {
//			p.setValue(value);
//		}
//
//		for (Widget w : widget.getContents()) {
//			replaceRecursive(w, propertyType, value);
//		}
//	}

	/**
	 * Returns an array of all included model files (modules and fragments) in
	 * this widget or in any of its children (any level).
	 * 
	 * @param widget
	 *            The widget to retrieve the included models for
	 * @return An array of strings, each representing an included model file
	 *         with its path (e.g. "/pkg1/pkg2/MyModel.module"
	 */
	public static String[] getAllIncludedFiles(Widget widget) {
		List<String> includes = new ArrayList<String>();
		TreeIterator<EObject> it = widget.eAllContents();
		while (it.hasNext()) {
			EObject eObj = it.next();
			if (eObj instanceof Widget) {
				Widget childWidget = (Widget) eObj;
				if (WidgetTypeConstants.INCLUDE.equals(childWidget
						.getTypeName())) {
					includes
							.add(childWidget
									.getPropertyValue(PropertyTypeConstants.INCLUDE_SOURCE));
				}
			}
		}
		return includes.toArray(new String[includes.size()]);
	}

	/**
	 * An Attribute Widget is essentially a wrapper for a Label and another
	 * Widget which represents the form field, such as a TextField. The exact
	 * type of Widget depends upon the FieldType property of the Attribute
	 * Widget. When the FieldType changes we create a new field Widget. In this
	 * case we need to update the values of all the properties of this Widget
	 * from the Attribute Widget.
	 * 
	 * @param attribute
	 *            The Attribute widget
	 * @param widget
	 *            The Widget to update
	 */
	public static void updateWidgetFromAttribute(Widget attribute, Widget widget) {
		for (Property p : attribute.getProperties()) {
			Property p2 = widget.findProperty(p.getType());
			if (p2 != null) {
				p2.setValue(p.getValue());
			}
		}
	}
    
    /**
     * Gets the String containing the Domain Attribute from a Widget. This may be 
     * the DomainAttribute or DomainAssociation. If the Widget is a Column Body the
     * DomainAttribute is taken from the ColumnHeader.
     * 
     * @param widget The Widget
     * @return String The DomainAttribute or null if none was found
     */
     public static String getDomainAttribute(Widget widget) {
         // Hack - For Column Bodies the information is in the header
         if (widget.getTypeName().equals(WidgetTypeConstants.COLUMN_BODY)) {
             widget = widget.getParent().getContents().get(WidgetTypeConstants.COLUMN_HEADER_INDEX);
         }
         
         String an = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
         if (StringUtils.isEmpty(an)) {
             // Try an association
             an = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
         }
         
         return an;
     }

	/**
	 * Gets the name of the model that contains the given widget.
	 * @param widget The widget
	 * @return the name of the model
	 */
	public static String getModelName(Widget widget) {
		String result = "";
		Resource res = widget.getRootWidget().getModel().eResource();
		if (res != null) {
			result = res.getURI().toString();
		}
		return result;
	}
	/**
	 * check if the widget is of type xtooltip
	 * @param widget
	 * @return boolean.
	 */
	public static boolean isWidgetXtooltipType(Widget widget){
		//check if the include fragment is of xtooltip type.
				if(widget.getTypeName().equalsIgnoreCase("fragment")){
					Property fragmentType=widget.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
					if(fragmentType!=null&&fragmentType.getValue().equals("xtooltip")){
						return true;
					}
				}
				else if(widget.getTypeName().equalsIgnoreCase("module")){
				  Property module=widget.findProperty(PropertyTypeConstants.SEARCH);
				  if(module!=null&&module.getValue().equals("xtooltip")){
						return true;
				     }
				}
	    return false;
	}
		
	
	
}