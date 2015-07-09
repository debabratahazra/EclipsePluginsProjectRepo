package com.odcgroup.edge.t24.generation.enquiry.core;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.Property;
import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.temenos.connect.enquiry.EnquiryResultConstants;

/**
 * A <code>ResultsHeaderFooterCellSpec</code> is an immutable "value object" describing a single cell within its parent {@link ResultsHeaderFooterRowSpec}.<p>
 * 
 * It is a simple pairing of an Enquiry {@link Field} (assumed to represent a cell within a header or footer row) with a handful of helper methods. 
 * 
 * @author Simon Hayes
 */
public class ResultsHeaderFooterCellSpec {
	private final Field m_field;
	private final String m_ecDataItemName;
	
	public ResultsHeaderFooterCellSpec(Field p_field, String p_ecDataItemName)
	{
		m_field = AssertionUtils.requireNonNull(p_field, "p_field");
		m_ecDataItemName = AssertionUtils.requireNonNullAndNonEmpty(p_ecDataItemName, "p_ecDataItemName");
	}
	
	public Field getField()
	{
		return m_field;
	}
	
	public String getT24FieldName()
	{
		return m_field.getName();
	}
	
	public int getT24ColumnNumber()
	{
		return m_field.getPosition().getColumn();
	}
	
	public Integer getT24LineNumber()
	{
		return m_field.getPosition().getLine();
	}
	
	public String getAlignment()
	{
		return m_field.getAlignment().toString().toUpperCase();
	}
	
	public String getEdgeConnectDataItemName()
	{
		return m_ecDataItemName;
	}
	
	public void addDataItemTo(PropertyGroupWrapper p_searchResultGroup, FormContext p_formContext)
	{
		if (p_searchResultGroup.unwrap().getChildWithName(m_ecDataItemName, Property.class) == null)
		{
			PropertyWrapper dataItem = PropertyWrapper.create(p_formContext, m_ecDataItemName);
			dataItem.setAttribute(EnquiryResultConstants.CustomAttrNames.IS_DATA_FOR_EXPORT, "false");
			p_searchResultGroup.addChild(dataItem);
		}
	}
}
