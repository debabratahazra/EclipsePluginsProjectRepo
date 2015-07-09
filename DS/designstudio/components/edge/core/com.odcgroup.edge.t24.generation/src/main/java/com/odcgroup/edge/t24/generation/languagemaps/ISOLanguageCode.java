package com.odcgroup.edge.t24.generation.languagemaps;

import java.util.Locale;

import com.acquire.util.AssertionUtils;

/**
 * <code>ISOLanguageCode</code> is a simple type-safe wrapper for a <code>String</code> representing an ISO 2- or 3-letter language code
 *
 * @author Simon Hayes
 */
public class ISOLanguageCode implements Comparable<ISOLanguageCode> {
	public static final int MIN_LENGTH = 2;
	public static final int MAX_LENGTH = 3;
	
	public final String value;
    public final Locale locale;
	
	public ISOLanguageCode(String p_isoLanguageCode)
	{
		value = AssertionUtils.requireNonNullAndNonEmpty(p_isoLanguageCode, "p_isoLanguageCode");
		AssertionUtils.requireConditionTrue((value.length() >= 2) && (value.length() <= 3), "(p_isoLanguageCode.length() >= 2) && (p_isoLanguageCode.length() <= 3");
		
		locale = new Locale(p_isoLanguageCode);
	}

	@Override
    public String toString()
	{
		return value;
	}
	
	@Override
    public int hashCode() {
		return value.hashCode();
	}

	public boolean equals(ISOLanguageCode p_other)
	{
		return this.value.equals(p_other.value);
	}

	@Override
    public int compareTo(ISOLanguageCode p_other)
	{
		return this.value.compareTo(p_other.value);
	}
}
