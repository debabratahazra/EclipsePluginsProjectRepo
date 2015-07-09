package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import java.util.Comparator;

import com.acquire.util.AssertionUtils;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class DisplayableT24FieldPosition
{
	public static final Comparator<DisplayableT24FieldPosition> LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR = new Comparator<DisplayableT24FieldPosition>()
	{
		@Override
		public int compare(DisplayableT24FieldPosition p_first, DisplayableT24FieldPosition p_second) {
			AssertionUtils.requireNonNull(p_first, "p_first");
			AssertionUtils.requireNonNull(p_second, "p_second");
			
			int result = p_first.m_t24LineNumber - p_second.m_t24LineNumber;
			
			if (result == 0)
				result = p_first.m_t24ColumnNumber - p_second.m_t24ColumnNumber;
			
			return result;
		}
	};  
	
	private final int m_t24ColumnNumber;
	private final int m_t24LineNumber;
	
	public DisplayableT24FieldPosition(FieldPosition p_position)
	{
		AssertionUtils.requireNonNull(p_position, "p_position");
		m_t24ColumnNumber = p_position.getColumn();
		m_t24LineNumber = (p_position.getLine() == null) ? 0 : p_position.getLine().intValue();
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
