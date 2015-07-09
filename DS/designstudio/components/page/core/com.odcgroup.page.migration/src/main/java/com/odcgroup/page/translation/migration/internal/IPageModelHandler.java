package com.odcgroup.page.translation.migration.internal;

import java.util.Map;
import java.util.Set;

import org.xml.sax.helpers.DefaultHandler;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public interface IPageModelHandler {
	
	/**
	 * @return
	 */
	DefaultHandler getHandler();

	/**
	 * @param map
	 */
	void setDataMap(Map<String, Set<String>> map);

}
