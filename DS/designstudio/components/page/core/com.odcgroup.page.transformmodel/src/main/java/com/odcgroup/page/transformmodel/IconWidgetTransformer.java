package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LABEL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.corporate.ImageDescriptor;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This transformer transforms a label domain widget into xgui
 * @author atr
 * @since DS 1.40.0
 */
public class IconWidgetTransformer extends BaseWidgetTransformer {
	

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		Property icon = widget.findProperty("icon");
		if (icon != null) {
			
			String elementName = "icon";
			String attributeName = "ref";
			String iconValue = icon.getValue();		
			
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
			ImageDescriptor descriptor = 
				CorporateImagesUtils.getCorporateImages(ofsProject).getImageDescriptor(iconValue);
			if (descriptor != null && ! descriptor.getFileDescriptor().isSprite()) {
				elementName = XGUI_LABEL_ELEMENT;
				attributeName = "icon";
				iconValue = descriptor.getFilename();
			}
			
			Element root = createElement(context, XGUI_NAMESPACE_URI, elementName);
			TransformUtils.appendChild(context, root);	
			Element oldParent = context.setParentElement(root);
			
			root.setAttribute(attributeName, iconValue);
			
			transformTranslations(context, widget);
			transformProperties(context, widget);
			transformEvents(context, widget);
			
			context.setParentElement(oldParent);
			
		}
		
	}

	/**
	 * @param type
	 */
	public IconWidgetTransformer(WidgetType type) {
		super(type);
	}


}
