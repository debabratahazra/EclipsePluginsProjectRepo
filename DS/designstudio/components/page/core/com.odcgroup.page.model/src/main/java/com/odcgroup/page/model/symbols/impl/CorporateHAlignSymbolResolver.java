package com.odcgroup.page.model.symbols.impl;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Resolves the symbol ${corporatehalign}
 * @author atr
 */
public class CorporateHAlignSymbolResolver extends AbstractSymbolResolver {
	
	/**
	 * @param w the widget to be checked
	 * @return true if the widget is an icon, otherwise it return false.
	 */
	protected boolean isIconWidget(Widget w) {
		WidgetType type = w.getLibrary().findWidgetType(WidgetTypeConstants.ICON);
		return type != null && w.getType().isInstanceOf(type);
	}
	
	/**
	 * @param w the widget to be checked
	 * @return true if the widget is a label, otherwise it return false.
	 */
	protected boolean isLabelWidget(Widget w) {
		WidgetType type = w.getLibrary().findWidgetType(WidgetTypeConstants.LABEL);
		return type != null && w.getType().isInstanceOf(type);
	}
	
	/**
	 * @param w
	 * @return
	 */
	protected boolean isMatrixAxisWidget(Widget w) {
		WidgetType type = w.getLibrary().findWidgetType(WidgetTypeConstants.MATRIX_AXIS);
		return type != null && w.getType().isInstanceOf(type);
	}
	
	
	/*
	 * @see
	 * com.odcgroup.page.model.symbols.SymbolResolver#getValue(java.lang.String,
	 * java.lang.Object)
	 */
	public <T> String getValue(String symbol, T context) {
		String result = null;
		Widget widget = (Widget) context;
		IOfsProject ofsProject = getOfsProject(widget);
		if (ofsProject != null) {
			CorporateDesign cd = getCorporateDesign(ofsProject);
			if (isLabelWidget(widget) || isIconWidget(widget) || isMatrixAxisWidget(widget)) {
				result = cd.getLabelHorizontalAlignment();
			} else {
				result = cd.getFieldHorizontalAlignment();
			}
		} else {
			// The widget has been created outside the resource
			// This is the case in CDMTable figure for example !!!!
			result = symbol;
		}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public CorporateHAlignSymbolResolver(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 */
	public CorporateHAlignSymbolResolver() {

	}

}
