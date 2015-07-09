package com.odcgroup.edge.t24.generation.enquiry.layout;

import gen.com.acquire.intelligentforms.entities.HeadingWrapper;
import gen.com.acquire.intelligentforms.entities.ItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;

/**
 * <code>RichHTMLEnquiryHeaderLayoutIterationListener</code> is an {@link EnquiryHeaderLayoutIterationListener} implementation for Rich HTML presentation type 
 *
 * @author Simon Hayes
 */
public class RichHTMLEnquiryHeaderLayoutIterationListener implements EnquiryHeaderLayoutIterationListener<RichHTMLPresentationItemGroupWrapper, RichHTMLPresentationFormatBreakWrapper, RichHTMLPresentationColumnBreakWrapper> {
	private final ItemGroupWrapper m_targetItemGroup;
	private final FormContext m_formContext;
	
	public RichHTMLEnquiryHeaderLayoutIterationListener(ItemGroupWrapper p_targetItemGroup)
	{
		m_targetItemGroup = AssertionUtils.requireNonNull(p_targetItemGroup, "p_targetItemGroup");
		m_formContext = p_targetItemGroup.getFormContext();
	}
	
	@Override
	public RichHTMLPresentationFormatBreakWrapper addPresSection(RichHTMLPresentationItemGroupWrapper p_targetPresItemGroup)
	{
		final RichHTMLPresentationFormatBreakWrapper presSection = RichHTMLPresentationFormatBreakWrapper.create(m_formContext);
		p_targetPresItemGroup.addChild(presSection);
		return presSection;
	}

	@Override
	public RichHTMLPresentationColumnBreakWrapper addPresColumn(RichHTMLPresentationFormatBreakWrapper p_targetPresSection)
	{
		final RichHTMLPresentationColumnBreakWrapper presColumn = RichHTMLPresentationColumnBreakWrapper.create(m_formContext);
		p_targetPresSection.addChild(presColumn);
		return presColumn;
	}

	@Override
	public void addStaticHeaderLabelHeading(StaticHeaderLabelElement p_staticLabelElem,	RichHTMLPresentationColumnBreakWrapper p_targetPresColumn)
	{
		final HeadingWrapper heading = HeadingWrapper.create(m_formContext, p_staticLabelElem.getStaticLabelText());
		final RichHTMLPresentationQuestionWrapper presHeading = RichHTMLPresentationQuestionWrapper.create(m_formContext, heading.unwrap());
		presHeading.setDesignToUseFromEntityKey("Enquiry Static Header Value");
		m_targetItemGroup.addChild(heading);
		p_targetPresColumn.addChild(presHeading);
	}

	@Override
	public void addDynamicHeaderValueHeading(DynamicHeaderValueElement p_dynHeaderValueElem, RichHTMLPresentationColumnBreakWrapper p_targetPresColumn)
	{
		final HeadingWrapper heading = HeadingWrapper.create(m_formContext, "$$" + p_dynHeaderValueElem.getDynamicValueItemPath() + "$");
		final RichHTMLPresentationQuestionWrapper presHeading = RichHTMLPresentationQuestionWrapper.create(m_formContext, heading.unwrap());
		presHeading.setDesignToUseFromEntityKey("Enquiry Dynamic Header Value");
		m_targetItemGroup.addChild(heading);
		p_targetPresColumn.addChild(presHeading);
	}

	@Override
	public void addPresLineSpacing(int p_numLinesSpacingRequired, RichHTMLPresentationColumnBreakWrapper p_targetPresColumn)
	{
		final RichHTMLPresentationSpacingWrapper presLineSpacing = RichHTMLPresentationSpacingWrapper.create(m_formContext);
		presLineSpacing.setNumberBlankLines(new Integer(p_numLinesSpacingRequired * 2)); // Empirically we seem to need twice as much physical line spacing as logical to get things to line up (?!)
		p_targetPresColumn.addChild(presLineSpacing);
	}
}
