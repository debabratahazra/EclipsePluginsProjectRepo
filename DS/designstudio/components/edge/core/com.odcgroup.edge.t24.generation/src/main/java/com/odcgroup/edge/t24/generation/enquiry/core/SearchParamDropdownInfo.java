package com.odcgroup.edge.t24.generation.enquiry.core;

import com.acquire.util.AssertionUtils;

/**
 * <code>SearchParamDropdownInfo</code> is an immutable "value object" encapsulating the additional information that an enquiry processor needs for any selection ("filter") field that is effectively
 * a foreign key to another T24 application/domain in order to be able to generate a "Dropdown" widget reference for that filter field as part of generating the enquiry search elements. 
 *
 * @author Simon Hayes
 */
public class SearchParamDropdownInfo
{
	public final String irisResourceName;
	public final String irisPrimaryKeyName;
	
	public SearchParamDropdownInfo(String p_irisResourceName, String p_irisPrimaryKeyName)
	{
		irisResourceName = AssertionUtils.requireNonNullAndNonEmpty(p_irisResourceName, "p_irisResourceName");
		irisPrimaryKeyName = AssertionUtils.requireNonNullAndNonEmpty(p_irisPrimaryKeyName, "p_irisPrimaryKeyName");
	}
}
