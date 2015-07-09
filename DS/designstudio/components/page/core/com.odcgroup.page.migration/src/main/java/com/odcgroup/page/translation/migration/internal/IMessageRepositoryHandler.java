package com.odcgroup.page.translation.migration.internal;

import java.util.Map;

import org.xml.sax.helpers.DefaultHandler;

/**
 * @author atr
 */
public interface IMessageRepositoryHandler {

	/**
	 * @return
	 */
	DefaultHandler getHandler();

	/**
	 * @param map
	 */
	void setDataMap(Map<String, Map<String, String>> map);
	
}
