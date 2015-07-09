package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.Collection;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.util.TextTranslations;

/**
 * A <code>EnquiryResultsTableField</code> is an immutable value object providing meta-information about a single "displayable" Enquiry result field (i.e. a field for which a results table-column question
 * needs to be created by the generator).
 *
 * @author Simon Hayes
 */
public class EnquiryResultsTableField {
	public static final EnquiryResultsTableField[] EMPTY_ARRAY = new EnquiryResultsTableField[0];
	
	private final int m_t24ColumnNumber;
	private final TextTranslations m_labelTranslations;
	private final String m_ecDataItemName;
	private final boolean m_requiresMultiValueGroup;
	
	public static EnquiryResultsTableField[] toArray(Collection<EnquiryResultsTableField> p_collection)
	{
		final int size = (p_collection == null) ? 0 : p_collection.size();
		return (size == 0) ? EMPTY_ARRAY : p_collection.toArray(new EnquiryResultsTableField[size]);
	}
	
	public EnquiryResultsTableField(int p_t24ColumnNumber, TextTranslations p_labelTranslations, String p_ecDataItemName, boolean p_requiresMultiValueGroup)
	{
		m_t24ColumnNumber = p_t24ColumnNumber;
		m_labelTranslations = p_labelTranslations;
		m_ecDataItemName = AssertionUtils.requireNonNullAndNonEmpty(p_ecDataItemName, "p_ecDataItemName");
		m_requiresMultiValueGroup = p_requiresMultiValueGroup;
	}
	
	public int getT24ColumnNumber()
	{
		return m_t24ColumnNumber;
	}
	
	public String getLabelText()
	{
		return m_labelTranslations.getText();
	}
	
	public TextTranslations getLabelTranslations()
	{
		return m_labelTranslations;
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
