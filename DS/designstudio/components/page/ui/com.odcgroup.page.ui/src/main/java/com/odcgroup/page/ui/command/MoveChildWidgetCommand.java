package com.odcgroup.page.ui.command;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * Command executed when a child Widget is moved.
 * 
 * @author Gary Hayes
 */
public class MoveChildWidgetCommand extends BaseCommand {

	/** The old index of the child Widget. */
	private int oldIndex;
	
	/** The new index of the child Widget. */
	private int newIndex;
	
	/** The old position of the Widget. */
	private Point oldPosition;		
	
	/** The new position of the Widget. */
	private Point newPosition;		
	
	/** The child Widget. */
	private Widget child;
	
	/** The parent Widget. */
	private Widget parent;

	/**
	 * Creates a new MoveChildWidgetCommand.
	 * 
	 * @param child The child Widget
	 * @param parent The parent Widget
	 * @param newIndex The new index of the child Widget
	 * @param newPosition The new position of the child Widget
	 */
	public MoveChildWidgetCommand(Widget child, Widget parent, int newIndex, Point newPosition) {
		Assert.isNotNull(child);
		Assert.isNotNull(parent);
		Assert.isNotNull(newPosition);
		
		this.child = child;
		this.parent = parent;
		this.newIndex = newIndex;
		this.newPosition = newPosition;
		
		// In the execute method we first remove the child. Therefore
		// if the newIndex is greater than the oldIndex we need to decrement
		// it by one to take into account the removal
		int oldIndex = parent.getContents().indexOf(child);
		if (newIndex > oldIndex) {
			this.newIndex -= 1;	
		} 
		
		Point oldPosition = new Point();
		Property px = child.findProperty(PropertyTypeConstants.POS_X);
		if (px != null) {
			oldPosition.x = (Integer) px.getConvertedValue();
		}
		Property py = child.findProperty(PropertyTypeConstants.POS_Y);
		if (py != null) {
			oldPosition.y = (Integer) py.getConvertedValue();
		}		
		
		setLabel("Move "+child.getTypeName()+" Widget");
	}

	/**
	 * Executes the Command.
	 */
	public void execute() {
		setPosition(newPosition);
		oldIndex = parent.getContents().indexOf(child);
		parent.getContents().remove(child);
		parent.getContents().add(newIndex, child);
	}

	/**
	 * Undoes the Command.
	 */
	public void undo() {
		setPosition(oldPosition);
		parent.getContents().remove(child);
		parent.getContents().add(oldIndex, child);
	}
	
	/**
	 * Sets the position of the Widget.
	 * 
	 * @param position The Position to set
	 */
	private void setPosition(Point position) {
		Property px = child.findProperty(PropertyTypeConstants.POS_X);
		if (px != null) {
			px.setValue(String.valueOf(position.x));
		}
		Property py = child.findProperty(PropertyTypeConstants.POS_Y);
		if (py != null) {
			py.setValue(String.valueOf(position.y));
		}		
	}	
}