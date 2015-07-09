package com.odcgroup.edge.t24.generation.composite.singleifp.enquiry;

import com.acquire.util.AssertionUtils;

/**
 * <code>EnquiryGenerationOptions</code> is...
 * 
 * EnquirySearchOption
 * |_ NONE # don't generate search screen (or supporting datastore elems)
 *
 * @author shayes
 *
 */
public class EnquiryGenerationOptions {
	public final SearchGenerationOption searchGenerationOption;
	
	public EnquiryGenerationOptions(SearchGenerationOption p_searchGenerationOption)
	{
		searchGenerationOption = AssertionUtils.requireNonNull(p_searchGenerationOption, "p_searchGenerationOption");
	}
	
	public SearchGenerationOption getSearchGenerationOption()
	{
		return searchGenerationOption;
	}
}
