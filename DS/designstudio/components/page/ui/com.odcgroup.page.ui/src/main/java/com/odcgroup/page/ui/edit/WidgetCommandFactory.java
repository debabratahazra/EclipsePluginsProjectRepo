package com.odcgroup.page.ui.edit;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.CreateWidgetCommand;

/**
 * @author pkk
 *
 */
public class WidgetCommandFactory {
	
	/**
	 * @param parent
	 * @param newWidget
	 * @param index
	 * @param position
	 * @return command
	 */
	public static Command getCreateWidgetCommand(Widget parent, Widget newWidget, int index, Point position) {
		return new CreateWidgetCommand(parent, newWidget, index, position);
	}

}
