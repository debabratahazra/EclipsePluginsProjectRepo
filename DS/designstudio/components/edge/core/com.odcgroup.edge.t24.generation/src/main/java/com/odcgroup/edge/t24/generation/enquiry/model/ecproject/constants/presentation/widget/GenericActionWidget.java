package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.QuestionPostfixOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.QuestionPrefixOption;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.GenericActionWidget.Params;

public class GenericActionWidget extends AbstractWidgetWithParameters<RichHTMLPresentationQuestionWrapper,Params>
{
	public static final String WIDGET_NAME = "GenericAction";
	public static final String ATTR_LIST_PROMPT = "LIST_PROMPT";
	
	public static final GenericActionWidget INSTANCE = new GenericActionWidget();
	
	@Override
    public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresObjectWrapper, Params p_widgetParams)
	{
        super.applyTo(p_targetPresObjectWrapper, p_widgetParams);
        
        QuestionPrefixOption.HIDE_PREFIX.applyTo(p_targetPresObjectWrapper);
        QuestionPostfixOption.HIDE_POSTFIX.applyTo(p_targetPresObjectWrapper);
    }

    public static class Params extends WidgetParamValues<RichHTMLPresentationQuestionWrapper>
	{
		public Params(String p_listPrompt)
		{
			AssertionUtils.requireNonNullAndNonEmpty(p_listPrompt, "p_listPrompt");
			setWidgetParamValue(ATTR_LIST_PROMPT, p_listPrompt);
		}
	}
	
	private GenericActionWidget()
	{
		super(WIDGET_NAME);
	}
}
