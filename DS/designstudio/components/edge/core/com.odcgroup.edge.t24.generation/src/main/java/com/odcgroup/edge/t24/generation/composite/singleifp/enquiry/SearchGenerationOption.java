package com.odcgroup.edge.t24.generation.composite.singleifp.enquiry;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public enum SearchGenerationOption {
	NONE,
	BASIC,
	INCLUDE_FILTER_PARAMS,
	INCLUDE_FILTER_PARAMS_AND_SORT_OPTIONS;
	
	public boolean includeSearchItemGroup()
	{
		return ordinal() >= BASIC.ordinal();
	}
	
	public boolean includeSearchFilters()
	{
		return ordinal() >= INCLUDE_FILTER_PARAMS.ordinal();
	}
	
	public boolean includeSortOptions()
	{
		return ordinal() >= INCLUDE_FILTER_PARAMS_AND_SORT_OPTIONS.ordinal();
	}
	
}
