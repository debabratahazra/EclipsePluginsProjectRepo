package com.odcgroup.page.ui.figure;

import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.model.Widget;

/**
 * A RadioButton represents a Radio Button in a user-interface.
 * At the moment this class does NOT extend Toggle. This is because the functionality provided
 * by the superclass Clickable interferes with our ability to select the radio button in order
 * to delete it for example.
 * This implementation supposes that the RadioButton does NOT contain other Widgets.
 * 
 * @author Gary Hayes
 */
public class RadioButton extends AbstractToggleButton {

	/** The RadioButton in the unchecked state. */
	private static final Image UNCHECKED = createImage("/icons/obj16/radiobuttonoff.png"); //$NON-NLS-1$
	
	/** The RadioButton in the checked state. */
	private static final Image CHECKED = createImage("/icons/obj16/radiobuttonon.png"); //$NON-NLS-1$	
	
	/** The RadioButton in the unchecked state. */
	private static final Image NOT_ENABLED_UNCHECKED = createImage("/icons/obj16/radiobuttonnotselectednotenable.png"); //$NON-NLS-1$
	
	/** The RadioButton in the checked state. */
	private static final Image NOT_ENABLED_CHECKED = createImage("/icons/obj16/radiobuttonselectednotenable.png"); //$NON-NLS-1$	

	/**
	 * Creates a new RadioButton.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public RadioButton(Widget widget, FigureContext figureContext) {
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
