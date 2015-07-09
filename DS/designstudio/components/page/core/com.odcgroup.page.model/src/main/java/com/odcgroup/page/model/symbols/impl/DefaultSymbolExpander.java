package com.odcgroup.page.model.symbols.impl;

import org.eclipse.core.runtime.ILog;

import com.odcgroup.page.model.PageModelCore;

/**
 * @author atr
 */
public class DefaultSymbolExpander extends AbstractSymbolExpander {

	/*
	 * @see
	 * com.odcgroup.page.model.symbols.impl.AbstractSymbolExpander#getLogger()
	 */
	protected ILog getLogger() {
		return PageModelCore.getDefault().getLog();
	}
	

	/*
	 * @see com.odcgroup.page.model.symbols.impl.AbstractSymbolExpander#getPluginID()
	 */
	protected String getPluginID() {
		return PageModelCore.PLUGIN_ID;
	}

	/**
	 * 
	 */
	public DefaultSymbolExpander() {
		setSymbolPrefix("\\$\\{");
		setSymbolSuffix("\\}");
		setSymbolPattern("[a-zA-z][a-zA-Z0-9]*");
	}


}
