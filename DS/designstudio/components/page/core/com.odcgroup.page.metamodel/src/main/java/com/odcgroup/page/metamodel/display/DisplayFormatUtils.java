package com.odcgroup.page.metamodel.display;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.osgi.framework.Bundle;

import com.odcgroup.page.metamodel.display.internal.DisplayFormatRegistry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * Helper class for DisplayFormat
 * 
 * @author atr
 * @since DS 1.40.0
 */
public final class DisplayFormatUtils {
	
	/** */
	private static DisplayFormatRegistry registry;
	
	/**
	 * Provides only static services
	 */
	private DisplayFormatUtils() {
	}

	/**
	 * @param ofsProject 
	 * @return DisplayFormat
	 */
	public static DisplayFormat getDisplayFormat(IOfsProject ofsProject) {
		IProject project = (ofsProject != null) ? ofsProject.getProject() : null;
		return DisplayFormatUtils.getRegistry().getDisplayFormat(project);
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void addPreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getDisplayFormat(ofsProject).addPreferenceChangeListener(listener);
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void removePreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getDisplayFormat(ofsProject).removePreferenceChangeListener(listener);
	}

	/**
	 * Initializes the DisplayFormat
	 * @param bundle
	 */
	public static void startDisplayFormat(Bundle bundle) {
		DisplayFormatUtils.registry = new DisplayFormatRegistry(bundle);
	}

	/**
	 * Stops the DisplayFormat
	 */
	public static void stopDisplayFormat() {
		DisplayFormatUtils.registry.dispose();
	}

	/**
	 * @param project
	 * @return IPreferenceStore
	 */
	public static ProjectPreferences getPreferenceStore(IProject project) {
		return DisplayFormatUtils.getRegistry().getDisplayFormat(project).getPreferenceStore();
	}

	/**
	 * @return DisplayFormatRegistry
	 */
	static DisplayFormatRegistry getRegistry() {
		return DisplayFormatUtils.registry;
	}

}
