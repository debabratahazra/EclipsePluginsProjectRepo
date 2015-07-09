package com.odcgroup.translation.core;

/**
 * Interface for filters. Can accept or reject items.
 * This is used to defined ITranslationProvider extension point
 *
 * @author atr
 *
 */
public interface IFilter {
	
	/**
	 * Determines if the given object passes this filter.
	 * 
	 * @param toTest object to compare against the filter 
	 * 
	 * @return <code>true</code> if the object is accepted by the filter.
	 */
	public boolean select(Object toTest);
}
