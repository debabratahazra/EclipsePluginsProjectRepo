package com.odcgroup.edge.t24.generation.enquiry.layout;

import gen.com.acquire.intelligentforms.entities.presentation.PresentationColumnBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationFormatBreakWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.PresentationItemGroupWrapper;

import java.util.Iterator;
import java.util.LinkedList;

import com.acquire.util.AssertionUtils;

/**
 * A <code>HeaderLayoutColumn</code> models the content of a single presentation column as a T24-line-number-ordered list of {@link HeaderElement}(s).
 *
 * @author Simon Hayes
 */
public class HeaderLayoutColumn
<
	PresentationItemGroupType extends PresentationItemGroupWrapper,
	PresSectionType extends PresentationFormatBreakWrapper,
	PresColumnType extends PresentationColumnBreakWrapper
>
{
	final LinkedList<HeaderElement> m_headerElements = new LinkedList<HeaderElement>();
	
	public void addHeaderElement(HeaderElement p_headerElement)
	{
		AssertionUtils.requireNonNull(p_headerElement, "p_headerElement");
		insertInT24LineNumberOrder(p_headerElement);
	}

	public void iterate(PresColumnType p_targetPresColumn, T24LineNumberRange p_t24LineNumberRange, EnquiryHeaderLayoutIterationListener<PresentationItemGroupType,PresSectionType,PresColumnType> p_listener)
	{
		int currentT24LineNo = p_t24LineNumberRange.min;
		
		for(final HeaderElement headerElement: m_headerElements)
		{
			final int headerLineNo = headerElement.getT24LineNumber();
			
			if (headerLineNo < p_t24LineNumberRange.min)
				continue;

			final boolean isHeaderLineNoInRange = (headerLineNo <= p_t24LineNumberRange.max); 
			final int numLinesSpacingRequired =  isHeaderLineNoInRange ? headerLineNo - currentT24LineNo : (p_t24LineNumberRange.max - currentT24LineNo) + 1;
			
			if (numLinesSpacingRequired > 0)
			{
				p_listener.addPresLineSpacing(numLinesSpacingRequired, p_targetPresColumn);
				currentT24LineNo += numLinesSpacingRequired;
			}
			
			if (! isHeaderLineNoInRange)
				break;
			
			if (headerElement instanceof StaticHeaderLabelElement)
				p_listener.addStaticHeaderLabelHeading((StaticHeaderLabelElement) headerElement, p_targetPresColumn);
			
			else if (headerElement instanceof DynamicHeaderValueElement)
				p_listener.addDynamicHeaderValueHeading((DynamicHeaderValueElement) headerElement, p_targetPresColumn);
			
			else
				throw new RuntimeException("Unhandled " + HeaderElement.class.getSimpleName() + " subtype: " + headerElement.getClass().getName());
			
			++currentT24LineNo;
		}
	}
	
	private void insertInT24LineNumberOrder(HeaderElement p_headerElement)
	{
		if (m_headerElements.isEmpty())
			m_headerElements.add(p_headerElement);
		
		else
		{
			final int t24LineNumberOfNewElem = p_headerElement.getT24LineNumber();
			final Iterator<HeaderElement> iter = m_headerElements.iterator();
			
			int insertIndex = 0;
			
			while(iter.hasNext() && iter.next().getT24LineNumber() < t24LineNumberOfNewElem)
				++insertIndex;
			
			m_headerElements.add(insertIndex, p_headerElement);
		}
	}
}
