package com.odcgroup.page.model.corporate;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.model.corporate.internal.CorporateDesignRegistry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * Helper class for Corporate Design
 * 
 * @author atr
 * @since DS 1.40.0
 */
public final class CorporateDesignUtils {
	
	/** */
	private static CorporateDesignRegistry registry;

	/**
	 * Provides only static services
	 */
	private CorporateDesignUtils() {
	}

	/**
	 * @param project 
	 * @return CorporateDesign
	 */
	public static CorporateDesign getCorporateDesign(IProject project) {
		return CorporateDesignUtils.getRegistry().getCorporateDesign(project);
	}
	
	/**
	 * @param ofsProject 
	 * @return CorporateDesign
	 */
	public static CorporateDesign getCorporateDesign(IOfsProject ofsProject) {
		return CorporateDesignUtils.getCorporateDesign(ofsProject.getProject());
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void addPreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getCorporateDesign(ofsProject).addPreferenceChangeListener(listener);
	}
	
	/**
	 * @param ofsProject
	 * @param listener
	 */
	public static void removePreferenceChangeListener(IOfsProject ofsProject, IPreferenceChangeListener listener) {
		getCorporateDesign(ofsProject).removePreferenceChangeListener(listener);
	}

	/**
	 * Initializes the corporate design registry
	 */
	public static void installCorporateDesign() {
		CorporateDesignUtils.registry = new CorporateDesignRegistry();
	}

	/**
	 * Disposes the corporate design registry
	 */
	public static void uninstallCorporateDesign() {
		if(registry != null) {
			CorporateDesignUtils.registry.dispose();
			CorporateDesignUtils.registry = null;
		}
	}

	/**
	 * Install defaults
	 */
	public static void installDefaults() {
		
		IEclipsePreferences node = 
			new DefaultScope().getNode(CorporateImagesConstants.PROPERTY_STORE_ID);

		node.put(CorporateDesignConstants.PROPERTY_LABEL_HORIZONTAL_ALIGNMENT,
				CorporateDesignConstants.ALIGN_LABEL_DEFAULT_VALUE);
		
		node.put(CorporateDesignConstants.PROPERTY_FIELD_HORIZONTAL_ALIGNMENT,
				CorporateDesignConstants.ALIGN_FIELD_DEFAULT_VALUE);
		
		node.putInt(CorporateDesignConstants.PROPERTY_TABLE_PAGE_SIZE,
				CorporateDesignConstants.TABLE_PAGE_SIZE_DEFAULT_VALUE);
		
	}

	/**
	 * @param project
	 * @return IPreferenceStore
	 */
	public static ProjectPreferences getPropertyStore(IProject project) {
		return CorporateDesignUtils.getRegistry().getCorporateDesign(project).getPropertyStore();
	}

	/**
	 * @return CorporateDesignRegistry
	 */
	static CorporateDesignRegistry getRegistry() {
		if(CorporateDesignUtils.registry == null) {
			installCorporateDesign();
		}
		return CorporateDesignUtils.registry;
	}

}
