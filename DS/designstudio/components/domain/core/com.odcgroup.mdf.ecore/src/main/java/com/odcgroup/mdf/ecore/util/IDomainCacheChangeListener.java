package com.odcgroup.mdf.ecore.util;

import java.util.EventListener;

/**
 * domain cache change listener
 * notifies the cache changed event
 */
public interface IDomainCacheChangeListener extends EventListener {
	
	/**
	 * 
	 */
	public void cacheChanged();
}
