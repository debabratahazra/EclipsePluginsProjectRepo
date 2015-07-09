package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public abstract class AbstractWidgetBase<TargetPresObjectWrapperType extends PresentationObjectWrapper<?>>
{
	private final String m_widgetName;
	
	protected AbstractWidgetBase(String p_widgetName)
	{
		m_widgetName = AssertionUtils.requireNonNullAndNonEmpty(p_widgetName, "p_widgetName");
	}
	
	protected void applyWidgetDisplayTypeTo(TargetPresObjectWrapperType p_targetPresObjectWrapper)
	{
		AssertionUtils.requireNonNull(p_targetPresObjectWrapper, "p_targetPresObjectWrapper");
		p_targetPresObjectWrapper.unwrap().setAttribute(PresentationObject.ATTR_DISPLAY_TYPE, "GANObject:com.acquire.intelligentforms.ide.presentationeditor.widgets.WidgetDialog|" + m_widgetName);
	}
}
