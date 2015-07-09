package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.presentation.PresentationObjectWrapper;
import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public abstract class AbstractParameterlessWidget<TargetPresObjectWrapperType extends PresentationObjectWrapper<?>>
	extends AbstractWidgetBase<TargetPresObjectWrapperType>
	implements Appliable<TargetPresObjectWrapperType>
{
	
	@Override
	public final void applyTo(TargetPresObjectWrapperType p_targetPresObjectWrapper) {
		AssertionUtils.requireNonNull(p_targetPresObjectWrapper, "p_targetPresObjectWrapper");
		applyWidgetDisplayTypeTo(p_targetPresObjectWrapper);
	}

	protected AbstractParameterlessWidget(String p_widgetName)
	{
		super(AssertionUtils.requireNonNullAndNonEmpty(p_widgetName, "p_widgetName"));
	}
}
