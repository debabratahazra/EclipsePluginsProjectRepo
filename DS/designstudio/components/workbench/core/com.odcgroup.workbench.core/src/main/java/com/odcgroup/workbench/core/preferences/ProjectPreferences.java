package com.odcgroup.workbench.core.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * This is a convenience class for not having to deal with default, instance and project scope preferences directly.
 * Please note that instances of this class should not be cached or treated as singletons (per project and namespace),
 * as the preference nodes can "expire", e.g. if a project is removed from the workspace and reimported (see DS-2617).
 *
 * So instead of caching the instances, new ones should be created, whenever preferences should be accessed; 
 * the creation of an instance should not be too costly.
 * 
 * @author Kai Kreuzer
 * 
 * @since 1.40.0
 */
public class ProjectPreferences {
	private static final Logger logger = LoggerFactory.getLogger(ProjectPreferences.class);

	private IEclipsePreferences projectPreferences;
	private IEclipsePreferences defaultPreferences;
	
	public ProjectPreferences(IProject project, String namespace) {
		initPreferenceNodes(project, namespace);
	}

	private void initPreferenceNodes(IProject project, String namespace) {
		if(project!=null) {
			projectPreferences = new ProjectScope(project).getNode(namespace);
		} else {
			projectPreferences = new InstanceScope().getNode(namespace);
		}
		defaultPreferences = new DefaultScope().getNode(namespace);
	}
	
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		projectPreferences.addPreferenceChangeListener(listener);
		defaultPreferences.addPreferenceChangeListener(listener);
	}

	public void removePreferenceChangeListener(
			IPreferenceChangeListener listener) {
		
		try {
			projectPreferences.removePreferenceChangeListener(listener);
		} catch(IllegalStateException e) {
			// ignore if the node has already been removed
		}
		try {
			defaultPreferences.removePreferenceChangeListener(listener);
		} catch(IllegalStateException e) {
			// ignore if the node has already been removed
		}
	}

	public void clear() {
		try {
			projectPreferences.clear();
		} catch (BackingStoreException e) {
			logger.error("clear() failed with a BackingStoreException", e);
		}
	}

	public void flush() {
		try {
			projectPreferences.flush();
		} catch (BackingStoreException e) {
			logger.error("flush() failed with a BackingStoreException", e);
		}
	}

	public String get(String key, String def) {
		return projectPreferences.get(key, defaultPreferences.get(key, def));
	}

	public boolean getBoolean(String key, boolean def) {
		return projectPreferences.getBoolean(key, defaultPreferences.getBoolean(key, def));
	}

	public byte[] getByteArray(String key, byte[] def) {
		return projectPreferences.getByteArray(key, defaultPreferences.getByteArray(key, def));
	}

	public double getDouble(String key, double def) {
		return projectPreferences.getDouble(key, defaultPreferences.getDouble(key, def));
	}

	public float getFloat(String key, float def) {
		return projectPreferences.getFloat(key, defaultPreferences.getFloat(key, def));
	}

	public int getInt(String key, int def) {
		return projectPreferences.getInt(key, defaultPreferences.getInt(key, def));
	}

	public long getLong(String key, long def) {
		return projectPreferences.getLong(key, defaultPreferences.getLong(key, def));
	}

	public void put(String key, String value) {
		projectPreferences.put(key, value);
	}

	public void putBoolean(String key, boolean value) {
		projectPreferences.putBoolean(key, value);
	}

	public void putByteArray(String key, byte[] value) {
		projectPreferences.putByteArray(key, value);		
	}

	public void putDouble(String key, double value) {
		projectPreferences.putDouble(key, value);		
	}

	public void putFloat(String key, float value) {
		projectPreferences.putFloat(key, value);		
	}

	public void putInt(String key, int value) {
		projectPreferences.putInt(key, value);		
	}

	public void putLong(String key, long value) {
		projectPreferences.putLong(key, value);		
	}

	public void remove(String key) {
		projectPreferences.remove(key);
	}

	public String[] getAllKeys() {
		try {
			return projectPreferences.keys();
		} catch (BackingStoreException e) {
			logger.error("getAllKeys() failed with a BackingStoreException (returning empty list)", e);
			return new String[0];
		}
	}
}
