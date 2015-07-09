package com.odcgroup.page.model.symbols.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.page.model.symbols.SymbolResolver;
import com.odcgroup.page.model.symbols.SymbolsExpander;

/**
 * @author atr
 */
public abstract class AbstractSymbolExpander implements SymbolsExpander {

	private String name;
	private String description = "";

	private String symbolPrefix = "";
	private String symbolSuffix = "";
	private String symbolPattern = "";

	private Map<String, SymbolResolver> resolvers = new HashMap<String, SymbolResolver>();

	protected abstract ILog getLogger();

	protected abstract String getPluginID();

	/*
	 * @see com.odcgroup.page.model.symbols.SymbolExpander#getName()
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/*
	 * @see com.odcgroup.page.model.symbols.SymbolExpander#getDescription()
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/*
	 * @see com.odcgroup.page.model.symbols.SymbolExpander#getSymbolPrefix()
	 */
	public final String getSymbolPrefix() {
		return symbolPrefix;
	}

	/**
	 * @param prefix
	 */
	public final void setSymbolPrefix(String prefix) {
		this.symbolPrefix = prefix;
	}

	/*
	 * @see com.odcgroup.page.model.symbols.SymbolExpander#getSymbolSuffix()
	 */
	public final String getSymbolSuffix() {
		return symbolSuffix;
	}

	/**
	 * @param suffix
	 */
	public final void setSymbolSuffix(String suffix) {
		this.symbolSuffix = suffix;
	}

	/*
	 * @see com.odcgroup.page.model.symbols.SymbolExpander#getSymbolPattern()
	 */
	public final String getSymbolPattern() {
		return symbolPattern;
	}

	/**
	 * @param pattern
	 */
	public final void setSymbolPattern(String pattern) {
		this.symbolPattern = pattern;
	}

	/*
	 * @see
	 * com.odcgroup.page.model.symbols.SymbolExpander#addSymbolResolver(com.
	 * odcgroup.page.model.symbols.SymbolResolver)
	 */
	public void addSymbolResolver(SymbolResolver resolver) {
		this.resolvers.put(resolver.getName(), resolver);
	}

	/**
	 * @param symbol
	 * @return
	 */
	protected SymbolResolver findResolver(String symbol) {
		SymbolResolver resolver = resolvers.get(symbol);
		return resolver;
	}

	/**
	 * return "(\\$\\{\\w+\\}){1}";
	 * 
	 * @return
	 */
	protected String getRegExpPattern1() {
		StringBuilder pattern = new StringBuilder();
		pattern.append("(");
		pattern.append(getSymbolPrefix());
		pattern.append(getSymbolPattern());
		pattern.append(getSymbolSuffix());
		pattern.append("){1}");
		return pattern.toString();
	}

	/**
	 * return "(\\$\\{(\\w+){1}\\}){1}";
	 * 
	 * @return
	 */
	protected String getRegExpPattern2() {
		StringBuilder pattern = new StringBuilder();
		pattern.append("(");
		pattern.append(getSymbolPrefix());
		pattern.append("(");
		pattern.append(getSymbolPattern());
		pattern.append("){1}");
		pattern.append(getSymbolSuffix());
		pattern.append("){1}");
		return pattern.toString();
	}

	/*
	 * @see
	 * com.odcgroup.page.model.symbols.SymbolsExpander#substitute(java.lang.
	 * String, com.odcgroup.page.model.Widget)
	 */
	public <T> String substitute(String str, T context) {

		StringBuilder result = new StringBuilder();

		Pattern expression = Pattern.compile(getRegExpPattern1());
		String[] items = expression.split(str);
		int nbItems = items.length;
		int index = 0;

		expression = Pattern.compile(getRegExpPattern2());
		Matcher matcher = expression.matcher(str);
		while (matcher.find()) {

			if (index < nbItems) {
				result.append(items[index]);
			}

			// do the substitution
			String group = matcher.group(1);
			String symbol = matcher.group(2);
			String value = null;
			SymbolResolver resolver = findResolver(symbol);
			if (resolver != null) {
				value = resolver.getValue(symbol, context);
			}

			if (value != null) {
				// the symbol has been substituted
				result.append(value);
			} else {
				// cannot resolve the symbol, simply keep it it the resulting string
				StringBuilder message = new StringBuilder();
				message.append("The Symbols Expander [" + getName() + "] ");
				message.append("cannot expand the symbol [" + group + "]. ");
				String resolverName = (resolver != null) ? resolver.getName() : "undefined";
				message.append("Symbol Resolver [" + resolverName + "]. ");
				message.append("Source string [" + str + "]. ");
				message.append("Context [" + context + "]. ");
				message.append("Please, check your configuration.");
				IStatus status = new Status(IStatus.ERROR, getPluginID(), 0,
						message.toString(), (Throwable) null);
				getLogger().log(status);

				result.append(group);
			}

			index++;
		}

		if (index < nbItems) {
			result.append(items[index]);
		}

		return result.toString();
	}

	/**
	 */
	protected AbstractSymbolExpander() {
	}

}
