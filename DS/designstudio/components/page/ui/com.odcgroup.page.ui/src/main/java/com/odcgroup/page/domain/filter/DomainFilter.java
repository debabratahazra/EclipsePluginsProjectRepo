package com.odcgroup.page.domain.filter;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * A DomainFilter 
 * 
 * @author atr
 */
public interface DomainFilter {
	
	/**
	 * The filter shall accept all mdf elements
	 */
	void acceptAll();

	/**
	 * Indicates the filter shall accept designated element
	 * @param item designates a mdf element
	 */
	void accept(DomainFilterEnum item);
	
	/**
	 * Indicates the filter shall accept designated elements
	 * @param items designates a mdf element
	 */
	void accept(DomainFilterEnum[] items);
	
	/**
	 * The filter shall exclude all mdf elements
	 */
	void excludeAll();

	/**
	 * Indicates the filter shall exclude designated element
	 * @param item designates a mdf element
	 */
	void exclude(DomainFilterEnum item);

	/**
	 * Indicates the filter shall exclude designated elements
	 * @param items designates a mdf element
	 */
	void exclude(DomainFilterEnum[] items);
	
	/**
	 * This method return <code>true</code> if the given element is accepted,
	 * otherwise it returns <code>false</code>.
	 * <p>
	 * 
	 * By default, the filter accept nothing.<p> 
	 * 
	 * @param modelElement
	 *            the mdf model element to be checked
	 * @return true if the mdf model element is accepted
	 */
	boolean accept(MdfModelElement modelElement);

}
