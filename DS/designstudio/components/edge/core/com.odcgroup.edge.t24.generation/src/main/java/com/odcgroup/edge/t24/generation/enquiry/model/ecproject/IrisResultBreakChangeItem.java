package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.util.AssertionUtils;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public abstract class IrisResultBreakChangeItem
{
	private final IrisResultItemSpec m_irisResultItemFieldSpec;
	private final PropertyWrapper m_breakChangeDataItem;
	
	protected IrisResultBreakChangeItem(IrisResultItemSpec p_irisResultItemSpec, PropertyWrapper p_breakChangeDataItem)
	{
		m_irisResultItemFieldSpec = AssertionUtils.requireNonNull(p_irisResultItemSpec, "p_irisResultItemSpec");
		m_breakChangeDataItem = AssertionUtils.requireNonNull(p_breakChangeDataItem, "p_breakChangeDataItem");
	}
	
	public IrisResultItemSpec getIrisResultItemSpec()
	{
		return m_irisResultItemFieldSpec;
	}
	
	public PropertyWrapper getBreakChangeDataItem()
	{
		return m_breakChangeDataItem;
	}
}
