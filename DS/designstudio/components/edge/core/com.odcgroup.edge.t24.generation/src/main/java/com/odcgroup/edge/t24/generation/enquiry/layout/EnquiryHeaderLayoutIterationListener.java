package com.odcgroup.edge.t24.generation.enquiry.layout;

import gen.com.acquire.intelligentforms.entities.presentation.PresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationItemGroupWrapper;

/**
 * <code>EnquiryHeaderLayoutIterationListener</code> defines the notifier methods to be implemented by the "listener" passed to <code>EnquiryHeaderLayoutHelper</code>'s
 * {@link EnquiryHeaderLayoutHelper#iterateHeaderItems(EnquiryHeaderLayoutIterationListener, PresentationItemGroupWrapper) iterateHeaderItems} method.
 *
 * @author Simon Hayes
 *
 */
public interface EnquiryHeaderLayoutIterationListener
<
	PresentationItemGroupType extends PresentationItemGroupWrapper,
	SectionBreakType extends PresentationFormatBreakWrapper,
	ColumnBreakType extends PresentationColumnBreakWrapper
>
{
	SectionBreakType addPresSection(PresentationItemGroupType p_targetPresItemGroup);
	ColumnBreakType addPresColumn(SectionBreakType p_targetPresSection);
	void addStaticHeaderLabelHeading(StaticHeaderLabelElement p_staticLabelElem, ColumnBreakType p_targetPresColumn);
	void addDynamicHeaderValueHeading(DynamicHeaderValueElement p_dynHeaderValueElem, ColumnBreakType p_targetPresColumn);
	void addPresLineSpacing(int p_numLinesSpacingRequired, ColumnBreakType p_targetPresColumn);
}
