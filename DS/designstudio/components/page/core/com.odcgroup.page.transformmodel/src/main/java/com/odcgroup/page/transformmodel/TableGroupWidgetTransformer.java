package com.odcgroup.page.transformmodel;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;

public class TableGroupWidgetTransformer extends BaseWidgetTransformer {

	public TableGroupWidgetTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget)
			throws CoreException {
			
	}
	
	@Override
	public Element getParentElement(WidgetTransformerContext context,
			Widget widget) {
		Element label=createElement(context,XGuiConstants.XGUI_NAMESPACE_URI , "lable");
		context.setParentElement(label);
		return label;
		
	}

}
