package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.mdf.ecore.MdfDatasetPropertyImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transformer for the Calendar widget.
 * 
 * @author atr
 * 
 */
public class CalendarWidgetTransformer extends BaseWidgetTransformer {
	
	private Element calendar = null;
	
	/**
	 * Constructor
	 * @param type The widget type
	 */
	public CalendarWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return calendar;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		calendar = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_CALENDAR_ELEMENT);
		TransformUtils.appendChild(context, calendar);
		
		
		// Set the parent so that the Attributes are set on the correct element
		Element oldParent = context.setParentElement(calendar);
		transformTranslations(context, widget);
		boolean xtool = context.isInXTooltipFragment();
		if (xtool) {
			context.setInXTooltipFragment(isDateType(context, widget));
		}
		transformProperties(context, widget);
		context.setInXTooltipFragment(xtool);
		transformEvents(context, widget);
		transformChildren(context, widget);
		context.setParentElement(oldParent);
	}
	
	/**
	 * @param context
	 * @param widget
	 * @return
	 */
	private boolean isDateType(WidgetTransformerContext context, Widget widget) {

		MdfModelElement modelElement = getMdfModelElement(context, widget);
		if (modelElement instanceof MdfDatasetPropertyImpl) {
			MdfDatasetPropertyImpl datasetProperty = (MdfDatasetPropertyImpl)modelElement;
			MdfEntity type = datasetProperty.getType();
			if(type instanceof MdfPrimitive) {
				MdfPrimitive prim = (MdfPrimitive) type;
				if (prim.equals(PrimitivesDomain.DATE) ||
					prim.equals(PrimitivesDomain.DATE_TIME)) {
					return true;
				} 
				if (prim.getName().equals("Date") || prim.getName().equals("Datetime")) {
					return true;
				}
			} 
		}
		return false;
	}
	

}
