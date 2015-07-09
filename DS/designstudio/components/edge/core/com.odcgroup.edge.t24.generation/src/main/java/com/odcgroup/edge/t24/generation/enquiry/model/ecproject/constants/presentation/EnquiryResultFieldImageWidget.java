package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.AbstractParameterlessWidget;

public class EnquiryResultFieldImageWidget extends AbstractParameterlessWidget<RichHTMLPresentationQuestionWrapper>
{
	public static final String WIDGET_NAME = "Enquiry Results Field Image";
	public static final EnquiryResultFieldImageWidget INSTANCE = new EnquiryResultFieldImageWidget();
	
	private EnquiryResultFieldImageWidget()
	{
		super(WIDGET_NAME);
	}
}
