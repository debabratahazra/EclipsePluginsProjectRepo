package com.odcgroup.page.ui.figure;

import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.model.Widget;

/**
 * A CheckBox represents a CheckBox in a user-interface.
 * Note that we do not use the CheckBox class provided by draw2d since it extends Toggle which
 * in turn extends Clickable. Unfortunately the functionality provided
 * by the superclass Clickable interferes with our ability to select the radio button in order
 * to delete it for example.
 * 
 * @author Gary Hayes
 */
public class CheckBox extends AbstractToggleButton {

	/** The RadioButton in the unchecked state. */
	private static Image UNCHECKED = createImage("/icons/obj16/checkboxoff.png"); //$NON-NLS-1$

	/** The RadioButton in the checked state. */
	private static Image CHECKED = createImage("/icons/obj16/checkboxon.png"); //$NON-NLS-1$

	/** The RadioButton in the unchecked state and not enabled. */
	private static final Image NOT_ENABLED_UNCHECKED = createImage("/icons/obj16/checkboxeboxnotenablenotchecked.png"); //$NON-NLS-1$
	
	/** The RadioButton in the checked state and not enabled. */
	private static final Image NOT_ENABLED_CHECKED = createImage("/icons/obj16/checkboxeboxnotenablechecked.png"); //$NON-NLS-1$	
	
	/**
	 * Creates a new CheckBox.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public CheckBox(Widget widget,FigureContext figureContext) {
		super(widget, figureContext);
	}

	/**
	 * Creates the image to be drawn representing the button.
	 * 
	 * @return Image The image to be drawn representing the button
	 */
	protected Image createButtonImage() {
		Image image = UNCHECKED;
		if (getSelected() && getEnabled()) {
			image = CHECKED;
		} else if (getSelected() && !getEnabled()) {
			image = NOT_ENABLED_CHECKED;
		} else if (!getSelected() && !getEnabled()) {
			image = NOT_ENABLED_UNCHECKED;
		}
		
		return image;
	}
}