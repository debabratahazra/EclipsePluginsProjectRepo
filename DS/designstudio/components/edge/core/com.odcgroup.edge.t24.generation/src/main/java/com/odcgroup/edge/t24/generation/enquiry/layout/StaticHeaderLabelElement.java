package com.odcgroup.edge.t24.generation.enquiry.layout;

/**
 * A <code>StaticHeaderValueElement</code> represents a header containing the static 'label' (as opposed to dynamic 'value') part of a header.
 *
 * @author Simon Hayes
 */
public class StaticHeaderLabelElement extends HeaderElement
{
	private final String m_staticLabelText;
	
	public StaticHeaderLabelElement(int p_t24ColumnNumber, int p_t24LineNumber, String p_staticLabelText)
	{
		super(p_t24ColumnNumber, p_t24LineNumber);
		m_staticLabelText = p_staticLabelText;
	}
	
	public String getStaticLabelText()
	{
		return m_staticLabelText;
	}
}
