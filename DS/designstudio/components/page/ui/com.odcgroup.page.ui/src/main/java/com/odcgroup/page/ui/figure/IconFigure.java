package com.odcgroup.page.ui.figure;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.ui.dialog.image.ImageUtils;
import com.odcgroup.page.ui.edit.PaintUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * A class which represents a simple icon.
 * 
 * @author atr
 */
public class IconFigure extends AbstractAlignableWidgetFigure {
	
	/** The minimum size of the Label. */
	private final static int MIN_SIZE = 16;

	// to solve a possible image leak in ImageUtils.
	private Map<String, Image> imageCache = new HashMap<String, Image>();

	/**
	 * Creates a new Label.
	 * 
	 * @param widget The Widget being displayed by this Figure
	 * @param figureContext The context providing information required for displaying the page correctly
	 */
	public IconFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);
	}
	
	/**
	 * Method called when a Preference is changed in Eclipse. 
	 * The default version does nothing.
	 */
	public void preferenceChange() {
		revalidate();
	}	

	/**
	 * Gets the minimum width of the figure.
	 * 
	 * @return int The minimum width of the figure
	 */
	public int getMinWidth() {	

		int minWidth = MIN_SIZE;
		Image image = getImage();
	
		if (image != null) {
			minWidth = getImage().getBounds().width;
		}	
		
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				return Math.max(minWidth, PaintUtils.getWidth(getWidget()));
			}
			root = root.getParent();
		}
		
		return minWidth + PaintUtils.getWidth(getWidget());
	}
	
	/**
	 * Gets the minimum height of the figure.
	 * 
	 * @return int The minimum height of the figure
	 */
	public int getMinHeight() {
		int imageHeight = MIN_SIZE;
		Image image = getImage();
		if (image != null) {
			imageHeight = image.getBounds().height;
		}
		Widget root = getWidget().getParent();
		while(root != null) {
			if(root.getTypeName().equals("TableColumn") && PaintUtils.getWidth(getWidget()) > 0) {
				imageHeight += PaintUtils.IMAGE_HEIGHT;
				break;
			}
			root = root.getParent();
		}
		return imageHeight;
	}	
	
	/**
	 * Paints the Label.
	 * 
	 * @param graphics The Graphics context
	 */
	protected void paintSpecificFigure(Graphics graphics) {
		//graphics.setForegroundColor(getTextForegroundColor());
		Widget root = getWidget().getParent();
		int y = 0;
		int x = 0;
		int decoratorsWdth=PaintUtils.getWidth(getWidget());
		boolean isInTable=false;
		while(root != null) {
			if(root.getTypeName().equals("TableColumn")) {
				isInTable=true;
				break;
			}
			root = root.getParent();
		}
		if(PaintUtils.isWidgethAlignLead(getWidget())&& !isInTable){
		    PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - decoratorsWdth);
		}else {
		    x=PaintUtils.paintIcons(getWidget(), graphics,0);
		}
		if(isInTable && decoratorsWdth>0){
		    x=0;
		    y=PaintUtils.IMAGE_HEIGHT;
		}
		Image image = getImage();
		if (image != null) {
			graphics.drawImage(image, x, y);
			
			if (getBoolean("icon-border")) {
				Rectangle r = getBounds();
				graphics.drawRectangle(x, y, r.width - (decoratorsWdth+1), r.height - 1);
			}
			
		}
		
		if (image == null && getFigureContext().isDesignMode()) {
			FigureConstants fc = getFigureConstants();
			Rectangle r = getBounds();
		    
		    graphics.setForegroundColor(fc.getOutlineBorderForegroundColor());
		    graphics.setLineStyle(fc.getOutlineBorderLineStyle());
		    graphics.drawRectangle(x, y, r.width- (decoratorsWdth+1), r.height - 1);
		    graphics.setClip(new Rectangle(x, y, 32, 12+y));
                    graphics.drawText("Icon", x+2, y-4);
		    
		}		
		
	}	
	
	/**
	 * Gets the Image.
	 * 
	 * @return Image The Image
	 */
	private Image getImage() {
		String name = getIcon();
		if (StringUtils.isNotEmpty(name)) {
			IOfsProject ofsProject = getOfsProject();
			ImageDescriptor descriptor = 
				CorporateImagesUtils.getCorporateImages(ofsProject).getImageDescriptor(name);
			if (descriptor != null) {
				return ImageUtils.getImageFromDescriptor(Display.getCurrent(), imageCache, descriptor);
			}
		}
		
		return null;
		
	}
	  
	/**
	 * Get the url of the icon.
	 * 
	 * @return String
	 */
	private String getIcon() {
		return getString(PropertyTypeConstants.ICON);
	}

	/**
	 * Reset the image to null if the icon has changed.
	 * 
	 * @param name
	 * 		The property name
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.HORIZONTAL_ALIGNMENT)) {
			revalidate();
		}
	}

	@Override
	public void dispose() {
		if (imageCache != null) {
			for (Image img : imageCache.values()) {
				if (!img.isDisposed()) { 
					img.dispose();
				}
			}
			imageCache.clear();
		}
		super.dispose();
	}
	
	
	
}
