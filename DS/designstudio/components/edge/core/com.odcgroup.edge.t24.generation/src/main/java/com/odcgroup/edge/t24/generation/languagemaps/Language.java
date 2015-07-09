package com.odcgroup.edge.t24.generation.languagemaps;

import com.acquire.util.AssertionUtils;

/**
 * <code>Language</code> is a simple pairing of an {@link ISOLanguageCode} with a language name
 *
 * @author Simon Hayes
 */
public class Language implements Comparable<Language> {
	public final ISOLanguageCode isoCode;
	public final String fullName;
	
	public Language(ISOLanguageCode p_code, String p_fullName)
	{
		isoCode = AssertionUtils.requireNonNull(p_code, "p_code");
		fullName = AssertionUtils.requireNonNullAndNonEmpty(p_fullName, "p_fullName");
	}

	@Override
    public int hashCode()
	{
		return isoCode.hashCode();
	}
	
	public boolean equals(Language p_other)
	{
		return isoCode.equals(p_other.isoCode);
	}
	
	@Override
    public int compareTo(Language p_other)
	{
		return this.isoCode.compareTo(p_other.isoCode);
	}
}
