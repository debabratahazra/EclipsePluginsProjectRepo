package com.odcgroup.page.ui.action;

import java.util.Collections;
import java.util.Map;

import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.ui.command.AddWidgetSetSelectedIndexCommand;

/**
 * Adds a Widget created by a Widget Template to the selected Widget.
 *
 * @author Gary Hayes
 */
public class AddTemplateAction extends AbstractGenericAction {
	
	/**
	 * Creates a new AddTemplateAction. 
	 *  
	 * @param parameters the set of parameters for the concrete action
	 */
	public AddTemplateAction(ActionParameters parameters) {
		super(parameters);
		
		setEnabled(calculateEnabled());
	}
	
	/**
	 * Returns true if the action is enabled.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		Widget w = getSelectedWidget();
		return (w != null);
	}
	
	/**
	 * Gets the properties to apply to the newly-created Widget.
	 * 
	 * @return Map
	 * 		Key: Property Name
	 * 		Value: Property Value
	 */
	protected Map<String, String> getProperties() {
		return Collections.EMPTY_MAP;
	}
	
	/**
	 * Runs the action. 
	 */
	public void run() {
		Widget widget = getSelectedWidget();
		WidgetLibrary library = widget.getLibrary();		
		
		WidgetTemplate wt = library.findWidgetTemplate(getWidgetTemplateName());
		Widget w = new WidgetFactory().create(wt);
		
		w.setPropertyValues(getProperties());
		
		AddWidgetSetSelectedIndexCommand c = new AddWidgetSetSelectedIndexCommand(widget, w);
		execute(c);
	}
}

