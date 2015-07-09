package com.odcgroup.page.domain.filter;

/**
 * Helper for Domain Filter
 * @author atr
 */
public final class DomainFilterHelper {
	
	/**
	 * Cannot be instantiated
	 */
	private DomainFilterHelper() {
	}

	/**
	 * Create a DomainFilter that excludes all kind of elements, but the
	 * list of given items.
	 * @param items the list of element types the filter must accept
	 * @return the new DomainFilter instance
	 */
	public static DomainFilter createAcceptFilter(DomainFilterEnum... items) {
		DomainFilter filter = new DomainFilterImpl();
	    filter.excludeAll();
	    for (DomainFilterEnum item : items) {
	        filter.accept(item); 
	    }
	    return filter;
	}

	/**
	 * Create a DomainFilter that accepts all kind of elements, but the
	 * list of given items.
	 * @param items the list of element types the filter must not accept
	 * @return the new DomainFilter instance
	 */
	public static DomainFilter createExcludeFilter(DomainFilterEnum... items) {
		DomainFilter filter = new DomainFilterImpl();
	    filter.acceptAll();
	    for (DomainFilterEnum item : items) {
	        filter.exclude(item); 
	    }
	    return filter;
	}}
