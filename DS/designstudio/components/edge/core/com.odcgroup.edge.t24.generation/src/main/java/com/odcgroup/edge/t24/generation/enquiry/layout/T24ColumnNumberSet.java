package com.odcgroup.edge.t24.generation.enquiry.layout;

import java.util.Iterator;
import java.util.TreeSet;

import com.acquire.util.AssertionUtils;

/**
 * A <code>T24ColumnNumberSet</code> represents an ordered set of T24 column numbers. 
 *
 * @author Simon Hayes
 */
public class T24ColumnNumberSet
{
	private final TreeSet<Integer> m_t24ColumnNumbers = new TreeSet<Integer>();
	
	public void addColumnNumber(int p_t24ColumnNumber)
	{
		m_t24ColumnNumbers.add(new Integer(p_t24ColumnNumber));
	}
	
	public int size()
	{
		return m_t24ColumnNumbers.size();
	}
	
	public Iterator<Integer> getColumnNumberIterator()
	{
		return m_t24ColumnNumbers.iterator();
	}
	
	public boolean isSubsetOf(T24ColumnNumberSet p_other)
	{
		AssertionUtils.requireNonNull(p_other, "p_other");
		
		if (size() > p_other.size())
			return false;
		
		final Iterator<Integer> iter = m_t24ColumnNumbers.iterator();
		
		while(iter.hasNext())
		{
			if (! p_other.m_t24ColumnNumbers.contains(iter.next()))
				return false;
		}
		
		return true;
	}
	
	public boolean isSuperSetOf(T24ColumnNumberSet p_other)
	{
		AssertionUtils.requireNonNull(p_other, "p_other");
		return p_other.isSubsetOf(this);
	}
}
