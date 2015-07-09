package com.odcgroup.page.ui.util;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;

import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;

/**
 * PaletteUtil class is a Utils class for the Palette .
 * 
 * @author snn
 * 
 */
public class PaletteUtils {
    /**
     * get the palette for the give root widget.
     * @param rootWidget
     * @return palette.
     */
	public static Palette getPalette(Widget rootWidget) {

		UIModel uiModel = UIModelRegistry.getUIModel();
		WidgetType widgetType = rootWidget.getType();
		Palette palette = null;
		if (WidgetTypeConstants.MODULE.equals(widgetType.getName())) {
			PropertyType propertyType = rootWidget.findProperty(
					PropertyTypeConstants.SEARCH).getType();
			String value = rootWidget
					.getPropertyValue(PropertyTypeConstants.SEARCH);
			palette = uiModel.findPalette(widgetType, propertyType, value);
		} else if (WidgetTypeConstants.FRAGMENT.equals(widgetType.getName())) {
			// get the palette for the xtooltip type fragment
			Property property = rootWidget
					.findProperty(PropertyTypeConstants.FRAGMENT_TYPE);
			if (property != null) {
				String value = property.getValue();

				palette = uiModel.findPalette(widgetType, property.getType(),
						value);
			}
		} else {
			palette = uiModel.findPalette(widgetType);
		}
		return palette;
	}

	/**
	 * check if the palette contain the widget.
	 * @param 
	 */
	
	public static boolean isWidgetInPaletteList(Widget  Widgte ,Widget editorRootWidget  ){
		Palette palette=getPalette(editorRootWidget);
		boolean allowed=false;
		//check if the widget in the palette list
        for(PaletteGroup group:palette.getGroups()){
		 if(!allowed){
			for(PaletteGroupItem item:group.getItems()){
				WidgetType type=item.getWidgetTemplate().getType();
				if(type.getName().equals(Widgte.getType().getName())){
					allowed=true;
				}
			 }
			}
		}
    	
		return allowed;
 	}
	
	
}
