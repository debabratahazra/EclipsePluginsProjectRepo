package com.odcgroup.page.ui.edit;

import java.net.URL;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignConstants;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.action.ViewIconAction;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class PaintUtils {
	private static final int IMAGE_WIDTH = 16;
	public static final int IMAGE_HEIGHT = 16;
	private static String TOOLTIP_ICON = "comment.gif" ;
	private static String XTOOLTIP_ICON = "xtooltip.png";
	private static String EVENTS_ICON = "pageright.gif";
	private static String PLUS_ICON = "obj16/8612p.gif";
	
	public static int paintIcons(Widget widget, Graphics graphics ,int position) {
		int x = position;
		if(widget.getToolTips() != null && widget.getToolTips().size() > 0 && ViewIconAction.tooltipChecked) {
			Image toolTipImage = getImage(TOOLTIP_ICON);
			graphics.drawImage(toolTipImage, new Point(x, 0));
			x += toolTipImage.getBounds().width;
		}
		
		if(widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT) != null && ViewIconAction.xtooltipChecked) {
			if(widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT).getModel() != null) {
				Image xToolTipImage = getImage(XTOOLTIP_ICON);
				graphics.drawImage(xToolTipImage, new Point(x, 0));
				x += xToolTipImage.getBounds().width;
			}
		}
		
		if(widget.getEvents() != null && widget.getEvents().size() > 0 && ViewIconAction.eventsChecked) {
			Image eventImage = getImage(EVENTS_ICON);
			graphics.drawImage(eventImage, new Point(x, 0));
			x += eventImage.getBounds().width;
		}
		return x;
	}
	
	public static int paintPlus(int offset, Widget widget, Graphics graphics) {
		Image plusImage = getImage(PLUS_ICON);
		graphics.drawImage(plusImage, new Point(offset, 0));
		return plusImage.getBounds().width; 
	}
       
	
	public static int getWidth(Widget widget) {
		int minWidth = 0;
		if(widget.getEvents() != null && widget.getEvents().size() > 0 && ViewIconAction.eventsChecked ) {
			minWidth += IMAGE_WIDTH ;
		}
		if(widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT) != null && ViewIconAction.xtooltipChecked) {
			if(widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT).getModel() != null) {
				minWidth += IMAGE_WIDTH;
			}
		}
		if(widget.getToolTips() != null && widget.getToolTips().size() > 0 && ViewIconAction.tooltipChecked) {
			minWidth += IMAGE_WIDTH;
		}
		return minWidth;
	}

	public static boolean isWidgethAlignLead(Widget widget){
	    Property hAlign = widget.findProperty(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
	    if (hAlign != null && hAlign.getValue().equals("trail")){
		return false;
	    }
	    if (hAlign != null && hAlign.getValue().equals("${corporatehalign}")){
		if(widget!=null && widget.getRootWidget()!=null){
		    IOfsProject project=OfsResourceHelper.getOfsProject(widget.getRootWidget().eResource());
		    if(project!=null){
			CorporateDesign design=CorporateDesignUtils.getCorporateDesign(project);
			String value=design.getPropertyStore().get(CorporateDesignConstants.PROPERTY_FIELD_HORIZONTAL_ALIGNMENT, "");
			if(value.equals("trail")){
			    return false;
			}
		    }
		}
	    }
	    return true;
	}

	 private static  Image getImage(String key) {
	        ImageRegistry registry = PageUIPlugin.getDefault().getImageRegistry();
	        Image image = registry.get(key);
	        if (image == null) {
	        	URL url = PageUIPlugin.getDefault().getBundle().getEntry("icons/" + key);
	            registry.put(key, ImageDescriptor.createFromURL(url));
	            image = registry.get(key);
	        }
	        return image;
	  }

}
