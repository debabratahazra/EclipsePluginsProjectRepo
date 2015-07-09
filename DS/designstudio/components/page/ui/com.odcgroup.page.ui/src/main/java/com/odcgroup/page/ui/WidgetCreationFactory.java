package com.odcgroup.page.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.requests.CreationFactory;

import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.model.util.WidgetFactory;

/**
 * Factory for creating Widgets.
 * 
 * @author Gary Hayes
 */
public class WidgetCreationFactory implements CreationFactory {
	
	/** The template for the Widget to be instantiated using this factory. */
	private WidgetTemplate widgetTemplate;

	/**
	 * Creates a new WidgetCreationFactory.
	 *
	 * @param widgetTemplate The template for the Widget to be instantiated using this factory
	 */
	public WidgetCreationFactory(WidgetTemplate widgetTemplate) {
		Assert.isNotNull(widgetTemplate);
		
		this.widgetTemplate = widgetTemplate;
	}

	/**
	 * Create the new object.
	 *
	 * @return The newly created object.
	 */
	public Object getNewObject() {
		return new WidgetFactory().create(widgetTemplate);
	}

	/**
	 * Gets the WidgetType this factory creates.
	 *
	 * @return WidgetType The WidgetType this factory creates
	 */
	public Object getObjectType() {
		return widgetTemplate.getType();
	}
}