package com.odcgroup.edge.t24.generation.enquiry.core;

/**
 * <code>AbstractEnquiryResultsHeader</code> acts as a common abstract base for {@link EnquiryResultsHeaderLabel} and {@link EnquiryResultsHeaderValue}
 *
 * @author Simon Hayes
 */
public abstract class AbstractEnquiryResultsHeader {
	private int m_t24LineNumber;
	private int m_t24ColumnNumber;
	
	protected AbstractEnquiryResultsHeader(int p_t24LineNumber, int p_t24ColumnNumber)
	{
		m_t24LineNumber = p_t24LineNumber;
		m_t24ColumnNumber = p_t24ColumnNumber;
	}
	
	public int getT24LineNumber()
	{
		return m_t24LineNumber;
	}
	
	public int getT24ColumnNumber()
	{
		return m_t24ColumnNumber;
	}
}
