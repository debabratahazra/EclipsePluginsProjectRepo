package com.odcgroup.edge.t24.generation.enquiry.layout;

import java.util.Iterator;

import com.acquire.util.AssertionUtils;

/**
 * A <code>ColumnSetSection</code> is a layout-model description of an edgeConnect presentation section featuring a common set of presentation columns.<p>
 * 
 * It is a simple composite of a {@link T24ColumnNumberSet} <i>(representing the particular set of columns which the section will accommodate)</i> and a {@link T24LineNumberRange}
 * <i>(representing the range of T24 line numbers for which this section's column set can be used)</i>.
 *
 * @author Simon Hayes
 */
public class ColumnSetSection
{
	private final T24LineNumberRange m_t24LineNumberRange;
	private final T24ColumnNumberSet m_columnNumberSet;
	
	public ColumnSetSection(T24LineNumberRange p_lineNumberRange, T24ColumnNumberSet p_columnNumberSet)
	{
		m_t24LineNumberRange = AssertionUtils.requireNonNull(p_lineNumberRange, "p_lineNumberRange");
		m_columnNumberSet = AssertionUtils.requireNonNull(p_columnNumberSet, "p_columnNumberSet");
	}

	public T24LineNumberRange getT24LineNumberRange()
	{
		return m_t24LineNumberRange;
	}
	
	public Iterator<Integer> getColumnNumberIterator()
	{
		return m_columnNumberSet.getColumnNumberIterator();
	}
}
