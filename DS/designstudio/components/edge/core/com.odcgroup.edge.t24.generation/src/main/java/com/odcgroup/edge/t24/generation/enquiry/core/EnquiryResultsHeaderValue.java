package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.Collection;

import com.acquire.util.AssertionUtils;

/**
 * An <code>EnquiryResultsHeaderValue</code> is an immutable value object describing a dynamic (data item-derived) header value to be shown above the results table at an Enquiry "results" screen
 *
 * @see		EnquiryFieldsDigest#getEnqResultsHeaderValues()
 * @author	Simon Hayes
 */
public class EnquiryResultsHeaderValue extends AbstractEnquiryResultsHeader {
	public static final EnquiryResultsHeaderValue[] EMPTY_ARRAY = new EnquiryResultsHeaderValue[0];
	
	private final String m_ecDataItemName;
	private final boolean m_requiresMultiValueGroup;
	
	public static EnquiryResultsHeaderValue[] toArray(Collection<EnquiryResultsHeaderValue> p_collection)
	{
		final int size = (p_collection == null) ? 0 : p_collection.size();
		return (size == 0) ? EMPTY_ARRAY : p_collection.toArray(new EnquiryResultsHeaderValue[size]);
	}
	
	public EnquiryResultsHeaderValue(int p_t24LineNumber, int p_t24ColumnNumber, String p_ecDataItemName, boolean p_requiresMultiValueGroup) {
		super(p_t24LineNumber, p_t24ColumnNumber);
		m_ecDataItemName = AssertionUtils.requireNonNullAndNonEmpty(p_ecDataItemName, "p_ecDataItemName");
		m_requiresMultiValueGroup = p_requiresMultiValueGroup;
	}
	
	public String getDataItemName()
	{
		return m_ecDataItemName;
	}
	
	public boolean requiresMultiValueGroup()
	{
		return m_requiresMultiValueGroup;
	}
}
