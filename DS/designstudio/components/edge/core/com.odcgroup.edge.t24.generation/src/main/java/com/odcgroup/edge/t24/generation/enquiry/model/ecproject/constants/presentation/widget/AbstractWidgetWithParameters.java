package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;
import com.odcgroup.edge.t24.generation.enquiry.model.AppliableWithParameter;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public abstract class AbstractWidgetWithParameters<
	TargetPresObjectWrapperType extends PresentationObjectWrapper<?>,
	WidgetParamsType extends Appliable<TargetPresObjectWrapperType>
> extends AbstractWidgetBase<TargetPresObjectWrapperType> implements AppliableWithParameter<TargetPresObjectWrapperType, WidgetParamsType>
{
	protected AbstractWidgetWithParameters(String p_widgetName)
	{
		super(AssertionUtils.requireNonNullAndNonEmpty(p_widgetName, "p_widgetName"));
	}
	
	@Override
	public void applyTo(TargetPresObjectWrapperType p_targetPresObjectWrapper, WidgetParamsType p_widgetParams)
	{
		AssertionUtils.requireNonNull(p_targetPresObjectWrapper, "p_targetPresObjectWrapper");
		AssertionUtils.requireNonNull(p_widgetParams, "p_widgetParams");
		
		applyWidgetDisplayTypeTo(p_targetPresObjectWrapper);
		p_widgetParams.applyTo(p_targetPresObjectWrapper);
	}
}
