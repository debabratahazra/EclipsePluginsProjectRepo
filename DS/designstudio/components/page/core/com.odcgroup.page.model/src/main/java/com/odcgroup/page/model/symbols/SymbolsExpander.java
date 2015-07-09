package com.odcgroup.page.model.symbols;


/**
 * @author atr
 */
public interface SymbolsExpander {

	/**
	 * @return the name of this symbol expander
	 */
	String getName();
	
	/**
	 * @return a description
	 */
	String getDescription();

	/**
	 * @return the symbol prefix (regular expression)
	 */
	String getSymbolPrefix();

	/**
	 * @return the symbol suffix (regular expression)
	 */
	String getSymbolSuffix();

	/**
	 * @return the symbol pattern (regular expression)
	 */
	String getSymbolPattern();

	/**
	 * Substitute all symbol references by concrete values.
	 * 
	 * @param <T> The type of the context
	 * @param str
	 *            the string containing symbols
	 * @param context
	 *            a context, cannot be null
	 * 
	 * @return the expanded string
	 */
	public <T> String substitute(String str, T context);

	/**
	 * Add a symbol resolver to this expander
	 * @param resolver the symbol resolver
	 */
	void addSymbolResolver(SymbolResolver resolver);

}
