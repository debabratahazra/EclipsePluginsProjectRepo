package com.odcgroup.page.ui.command;

import java.util.List;

import com.odcgroup.page.model.Widget;

/**
 * Replaces one Widget by another one.
 * 
 * @author Gary Hayes
 */
public class ReplaceWidgetCommand extends BaseCompoundCommand {

	/**
	 * Creates a new ReplaceWidgetCommand.
	 * 
	 * @param parent
	 * 			The parent of the widget
	 * @param child
	 * 			The widget to be replaced
	 * @param newWidget
	 * 			The new widget
	 */	
	public ReplaceWidgetCommand(Widget parent, Widget child, Widget newWidget) {
		int index = parent.getContents().indexOf(child);
		add(new DeleteWidgetCommand(parent, child));		
		add(new AddWidgetCommand(parent, newWidget, index));
	}
	
	/**
	 * Creates a new ReplaceWidgetCommand.
	 * 
	 * @param parent
	 * 			The parent of the widget
	 * @param child
	 * 			The widget to be replaced
	 * @param widgets
	 * 			The new widgets
	 */	
	public ReplaceWidgetCommand(Widget parent, Widget child, List widgets) {
		int index = parent.getContents().indexOf(child);
		add(new DeleteWidgetCommand(parent, child));	
		
		// Replace the Widgets in reverse order. If not they are reversed (since we
		// add the Widget to index each time).
		int size = widgets.size();
		for (int i = size - 1; i >= 0; --i) {
			Widget w = (Widget) widgets.get(i);
			add(new AddWidgetCommand(parent, w, index));
		}
	}

}
