package com.odcgroup.edge.t24.generation.enquiry.layout;

/**
 * <code>HeaderElement</code> is an abstract base for any kind of header element (static or dynamic).<p>
 * 
 * It knows its T24 column and line number, but nothing more than that.
 *
 * @author Simon Hayes
 */
public abstract class HeaderElement
{
	private final int m_t24ColumnNumber;
	private final int m_t24LineNumber;
	
	public HeaderElement(int p_t24ColumnNumber, int p_t24LineNumber)
	{
		m_t24ColumnNumber = p_t24ColumnNumber;
		m_t24LineNumber = p_t24LineNumber;
	}
	
	public int getT24ColumnNumber()
	{
		return m_t24ColumnNumber;
	}
	
	public int getT24LineNumber()
	{
		return m_t24LineNumber;
	}
}
