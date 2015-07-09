package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.Collection;

import com.acquire.util.StringUtils;

/**
 * <code>EnquiryResultsHeaderLabel</code> is an immutable value object describing a static (i.e. fixed text) header label to be shown above the results table at an Enquiry "results" screen
 *  
 * @see		EnquiryFieldsDigest#getEnqResultsHeaderLabels()
 * @author	Simon Hayes
 */
public class EnquiryResultsHeaderLabel extends AbstractEnquiryResultsHeader {
	public static final EnquiryResultsHeaderLabel[] EMPTY_ARRAY = new EnquiryResultsHeaderLabel[0];
	
	private final String m_labelText;
	
	public static EnquiryResultsHeaderLabel[] toArray(Collection<EnquiryResultsHeaderLabel> p_collection)
	{
		final int size = (p_collection == null) ? 0 : p_collection.size();
		return (size == 0) ? EMPTY_ARRAY : p_collection.toArray(new EnquiryResultsHeaderLabel[size]);
	}
	
	public EnquiryResultsHeaderLabel(int p_t24LineNumber, int p_t24ColumnNumber, String p_labelText) {
		super(p_t24LineNumber, p_t24ColumnNumber);
		m_labelText = StringUtils.nullToBlank(p_labelText);
	}
	
	public String getLabelText()
	{
		return m_labelText;
	}
}
