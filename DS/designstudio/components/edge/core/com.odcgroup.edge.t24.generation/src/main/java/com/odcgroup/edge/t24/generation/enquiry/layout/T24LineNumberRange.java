package com.odcgroup.edge.t24.generation.enquiry.layout;

/**
 * <code>T24LineNumberRange</code> is a simple value object representing a range of T24 line numbers. 
 *
 * @author Simon Hayes
 */
public class T24LineNumberRange
{
	public final int min;
	public final int max;
	
	public T24LineNumberRange(int p_lineNumber1, int p_lineNumber2)
	{
		min = Math.min(p_lineNumber1, p_lineNumber2);
		max = Math.max(p_lineNumber1, p_lineNumber2);
	}
}
