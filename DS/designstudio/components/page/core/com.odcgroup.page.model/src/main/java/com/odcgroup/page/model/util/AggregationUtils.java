package com.odcgroup.page.model.util;

import java.util.List;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;

/**
 * Utility class for Widget aggregations.
 * 
 * @author Gary Hayes
 */
public class AggregationUtils {

	/**
	 * Returns true if the Widget is entering an aggregation. An aggregation
	 * is defined as a Widget which has a Property of PropertyRole 'Aggregation'
	 * for which the value is true.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if we are entering an aggregation
	 */
	public static boolean isEnteringAggregation(Widget widget) {
		Widget aggregation = widget.findWidgetIncludeParent(PropertyTypeConstants.AGGREGATION, Boolean.TRUE.toString());
		if (aggregation != null) {     
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns true if any of the Widgets are changing an aggregation. An aggregation
	 * is defined as a Widget which has a Property of PropertyRole 'Aggregation'
	 * for which the value is true.
	 * 
	 * @param widgets The Widgets to test
	 * @param newParent The new parent Widget
	 * @return boolean True if we are changing an aggregation
	 */
	public static boolean isChangingAggregation(List<Widget> widgets, Widget newParent) {
		for (Widget widget : widgets) {
			if (isChangingAggregation(widget, newParent)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if the Widget is changing an aggregation. An aggregation
	 * is defined as a Widget which has a Property of PropertyRole 'Aggregation'
	 * for which the value is true.
	 * 
	 * @param widget The Widget to test
	 * @param newParent The new parent Widget
	 * @return boolean True if we are changing an aggregation
	 */
	public static boolean isChangingAggregation(Widget widget, Widget newParent) {
		Widget oldAggregation = widget.findWidgetIncludeParent(PropertyTypeConstants.AGGREGATION, Boolean.TRUE.toString());	
		Widget newAggregation = newParent.findWidgetIncludeParent(PropertyTypeConstants.AGGREGATION, Boolean.TRUE.toString());
	
		if (oldAggregation == null) {
			if (newAggregation == null) {
				// No aggregations involved. This is always true
				return false;
			} else {
				// We are trying to enter an aggregation. This is a change.
				return true;
			}
		} 		
		
		if (newAggregation == null) {
			return false;
		}
		
		// oldAggregation != null.
		if (oldAggregation.equals(newAggregation)) {
			return false;
		}
		
	
		return true;
	}
	
	/**
	 * Returns true if the Widget is part of an Aggregation. Note that
	 * we do not take into account if the Widget itself is an aggregation.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if the Widget is part of an aggregation
	 */
	public static boolean isInAggregation(Widget widget) {
		// Note that we start the test from the Parent since the method
		// is called isInAggregation and NOT isAggregation
		if (widget.getParent() == null) {
			return false;
		}
		Widget aggregation = widget.getParent().findWidgetIncludeParent(PropertyTypeConstants.AGGREGATION, Boolean.TRUE.toString());
		return aggregation != null;
	}
}