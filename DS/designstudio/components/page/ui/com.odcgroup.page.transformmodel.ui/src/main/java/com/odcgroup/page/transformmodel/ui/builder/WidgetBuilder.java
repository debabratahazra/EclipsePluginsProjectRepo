package com.odcgroup.page.transformmodel.ui.builder;

import java.util.List;

import com.odcgroup.page.model.Widget;

/**
 * Interface for WidgetBuilders. These create Widgets from Domain elements.
 * 
 * @author atr
 */
public interface WidgetBuilder {

	/**
	 * Creates the Widgets. These Widgets may contain child Widgets.
	 * 
	 * @return List The newly created Widgets. These Widgets may contain child Widgets
	 */
	public List<Widget> buildWidgets();
	
}