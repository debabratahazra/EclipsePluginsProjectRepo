package com.odcgroup.t24.server.properties.util;

import java.util.Properties;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class ServerProperties extends Properties {

	private static final long serialVersionUID = 1L;
	
	@Override
	public synchronized Object put(Object key, Object value) {
		String keyLowerCase = ((String)key).toLowerCase();
		return super.put(keyLowerCase, value);
	}
	
	@Override
	public String getProperty(String key) {
		String lowercase = key.toLowerCase();
		return super.getProperty(lowercase);
	}
	
	@Override
	public String getProperty(String key, String defaultValue) {
		String lowercase = key.toLowerCase();
		return super.getProperty(lowercase, defaultValue);
	}
	
	@Override
	public synchronized Object get(Object key) {
		return super.get(key);
	}
}
