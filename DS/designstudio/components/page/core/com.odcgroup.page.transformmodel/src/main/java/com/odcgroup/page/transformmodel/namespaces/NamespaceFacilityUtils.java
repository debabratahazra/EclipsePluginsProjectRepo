package com.odcgroup.page.transformmodel.namespaces;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.transformmodel.namespaces.internal.NamespaceFacilityRegistry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * Helper class for Corporate Design
 * 
 * @author atr
 * @since DS 1.40.0
 */
public final class NamespaceFacilityUtils {
	
	/** */
	private static NamespaceFacilityRegistry registry;

	/**
	 * Provides only static services
	 */
	private NamespaceFacilityUtils() {
	}

	/**
	 * @param project 
	 * @return CorporateDesign
	 */
	public static NamespaceFacility getNamespaceFacility(IProject project) {
		return NamespaceFacilityUtils.getRegistry().getNamespaceFacility(project);
	}
	
	/**
	 * @param ofsProject 
	 * @return CorporateDesign
	 */
	public static NamespaceFacility getNamespaceFacility(IOfsProject ofsProject) {
		IProject project = ofsProject.getProject();
		return NamespaceFacilityUtils.getRegistry().getNamespaceFacility(project);
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void addPreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getNamespaceFacility(ofsProject).addPreferenceChangeListener(listener);
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void removePreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getNamespaceFacility(ofsProject).removePreferenceChangeListener(listener);
	}

	/**
	 * Initializes the corporate design registry
	 */
	public static void startNamespaceFacility() {
		NamespaceFacilityUtils.registry = new NamespaceFacilityRegistry();
	}

	/**
	 * Disposes the corporate design registry
	 */
	public static void stopNamespaceFacility() {
		if(NamespaceFacilityUtils.registry != null) {
			NamespaceFacilityUtils.registry.dispose();
			NamespaceFacilityUtils.registry = null;
		}
	}

	/**
	 * @param project
	 * @return IPreferenceStore
	 */
	public static ProjectPreferences getPropertyStore(IProject project) {
		return NamespaceFacilityUtils.getRegistry().getNamespaceFacility(project).getPropertyStore();
	}

	/**
	 * @return CorporateDesignRegistry
	 */
	static NamespaceFacilityRegistry getRegistry() {
		if(NamespaceFacilityUtils.registry == null) {
			startNamespaceFacility();
		}
		return NamespaceFacilityUtils.registry;
	}

}
