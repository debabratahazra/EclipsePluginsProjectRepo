package com.odcgroup.page.ui.command;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;

/**
 * A command to create a Widget and add it to its parent. The command can be undone or redone.
 * 
 * @author Gary Hayes
 */
public class CreateWidgetCommand extends BaseCommand {

	/** The new Widget. */
	private Widget newWidget;

	/** Widget to add the Widget to. */
	private final Widget parent;
	
	/** The index of the parent's contents that the Widget should be added to. */
	private int index;
	
	/** The position of the new Widget. */
	private Point position;

	/**
	 * Create a command that will add a new Widget to a Widget.
	 * 
	 * @param parent
	 *            the CompositeWidget that will hold the new element 
	 * @param newWidget
	 *            the new AbstractWidget that is to be added
	 * @param index The index of the parent's contents that the Widget should be added to
	 * 		If index = -1 the newWidget is added to the end of the parent's contents
	 * @param position The Position of the new Widget
	 * 
	 */
	public CreateWidgetCommand(Widget parent, Widget newWidget, int index, Point position) {
		Assert.isNotNull(parent);
		Assert.isNotNull(newWidget);
		Assert.isNotNull(position);

		this.parent = parent;
		this.newWidget = newWidget;
		this.index = index;
		this.position = position;
		
		setLabel("Create "+newWidget.getTypeName()+" widget");
	}

	/**
	 * Can execute if all the necessary information has been provided.
	 * 
	 * @return boolean True if the Command can be executed
	 */
	public boolean canExecute() {
		return super.canExecute() && (newWidget != null && parent != null);
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		setPosition(position);
		// First check if the index is in the bounds of the widget
		// if not in the bounds then get the contents size
		if (parent.getContents().size() < index || index < 0) {
			index = parent.getContents().size();
		}
		parent.getContents().add(index, newWidget);
	}

	/**
	 * Undoes the previously executed command.
	 */
	public void undo() {
		setPosition(new Point(0, 0));
		parent.getContents().remove(index);
	}
	
	/**
	 * Sets the position of the Widget.
	 * 
	 * @param position The Position to set
	 */
	private void setPosition(Point position) {
		Property px = newWidget.findProperty(PropertyTypeConstants.POS_X);
		if (px != null) {
			px.setValue(String.valueOf(position.x));
		}
		Property py = newWidget.findProperty(PropertyTypeConstants.POS_Y);
		if (py != null) {
			py.setValue(String.valueOf(position.y));
		}		
	}
}