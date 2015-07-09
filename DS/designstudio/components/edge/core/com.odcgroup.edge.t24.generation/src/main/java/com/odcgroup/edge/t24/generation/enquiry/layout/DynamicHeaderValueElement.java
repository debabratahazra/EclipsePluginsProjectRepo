package com.odcgroup.edge.t24.generation.enquiry.layout;

import com.acquire.util.AssertionUtils;

/**
 * A <code>DynamicHeaderValueElement</code> is our model representation of a header containing the 'value' (as opposed to 'label') part of a header.
 *
 * @author Simon Hayes
 */
public class DynamicHeaderValueElement extends HeaderElement
{
	final String m_dynamicValueItemPath;
	
	public DynamicHeaderValueElement(int p_t24ColumnNumber, int p_t24LineNumber, String p_dynamicValueItem)
	{
		super(p_t24ColumnNumber, p_t24LineNumber);
		m_dynamicValueItemPath = AssertionUtils.requireNonNullAndNonEmpty(p_dynamicValueItem, "p_dynamicValueItem");
	}
	
	public String getDynamicValueItemPath()
	{
		return m_dynamicValueItemPath;
	}
}
