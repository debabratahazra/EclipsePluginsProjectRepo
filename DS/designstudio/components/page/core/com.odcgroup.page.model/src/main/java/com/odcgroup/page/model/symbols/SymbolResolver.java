package com.odcgroup.page.model.symbols;

/**
 * @author atr
 */
public interface SymbolResolver {

	/**
	 * @return the name of this symbol
	 */
	String getName();

	/**
	 * @return a short description
	 */
	String getDescription();

	/**
	 * Returns the value associated with the given symbol
	 * 
	 * @param <T> The type of the context
	 * @param symbol
	 *            the name of the symbol to compute
	 * @param contex
	 *            a context, cannot be null
	 * @return the symbol's value
	 */
	<T> String getValue(String symbol, T context);

}
