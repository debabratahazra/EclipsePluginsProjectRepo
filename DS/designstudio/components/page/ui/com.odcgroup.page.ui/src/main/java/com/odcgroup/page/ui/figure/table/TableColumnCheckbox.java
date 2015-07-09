package com.odcgroup.page.ui.figure.table;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.figure.FigureContext;

public class TableColumnCheckbox extends TableColumnItem {	

	/** The width of the Image. */
	private static final int BUTTON_IMAGE_WIDTH = 16;

	/** The RadioButton in the unchecked state. */
	private static Image UNCHECKED = createImage("/icons/obj16/checkboxoff.png"); //$NON-NLS-1$

	/** The RadioButton in the checked state. */
	private static Image CHECKED = createImage("/icons/obj16/checkboxon.png"); //$NON-NLS-1$

	/** The RadioButton in the unchecked state and not enabled. */
	private static final Image NOT_ENABLED_UNCHECKED = createImage("/icons/obj16/checkboxeboxnotenablenotchecked.png"); //$NON-NLS-1$
	
	/** The RadioButton in the checked state and not enabled. */
	private static final Image NOT_ENABLED_CHECKED = createImage("/icons/obj16/checkboxeboxnotenablechecked.png"); //$NON-NLS-1$	

	/** The image to use to draw the button. */
	private Image buttonImage;
	
	public TableColumnCheckbox(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}

	protected void paintSpecificFigure(Graphics graphics) {
		// TODO reuse com.odcgroup.page.ui.figure.AbstractToggleButton.paintSpecificFigure(Graphics)
		// with displayLabel() return false
		graphics.setForegroundColor(getTextForegroundColor());
		Image buttonImage = getButtonImage();
		graphics.drawImage(buttonImage, new Point(0, 0));
	}
	
	/**
	 * Gets the image to be drawn representing the button.
	 * 
	 * @return Image The image to be drawn representing the button
	 */
	protected Image getButtonImage() {
		if (buttonImage == null) {
			buttonImage = createButtonImage();
		}
		return buttonImage;
	}
	
	/**
	 * Get if the checkbox is checked.
	 * 
	 * @return boolean
	 */
	protected boolean getSelected() {
		boolean result = false;
		try {
			result = Boolean.valueOf(getString(PropertyTypeConstants.SELECTED));
		}catch (Exception ex) {
			return result;
		}
		return result;
	}
		
	/**
	 * Get the enabled value.
	 * 
	 * @return boolean The enabled value.
	 */
	protected boolean getEnabled() {
		boolean result = false;
		try {
			result = Boolean.valueOf(getString(PropertyTypeConstants.ENABLED));
		}catch (Exception ex) {
			return result;
		}
		return result;
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

	protected List<String> createPreviewValues() {
		return Collections.emptyList();
	}
	
	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {			
		return BUTTON_IMAGE_WIDTH;	
	}
	

}
