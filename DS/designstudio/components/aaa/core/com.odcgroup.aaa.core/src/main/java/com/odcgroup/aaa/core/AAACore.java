package com.odcgroup.aaa.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * The activator class controls the plug-in life cycle
 */
public class AAACore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.aaa.core";

	public static final String NATURE_ID = "com.odcgroup.aaa.core.AAANature";
	private static String rFolder;

	public static final String AAA_MODELS_FOLDER = "/entities";
	public static final String AAA_ENTITIES_MODEL = AAA_MODELS_FOLDER + "/Entities.domain";
	public static final String AAA_ENUMS_MODEL = AAA_MODELS_FOLDER + "/Enumerations.domain";
	public static final String AAA_BUSINESS_TYPE_MODEL = AAA_MODELS_FOLDER + "/BusinessTypes.domain";

	final static public String[] METADICT_RESOURCES = new String[] { AAACore.AAA_ENTITIES_MODEL.toLowerCase(),
		AAACore.AAA_ENUMS_MODEL.toLowerCase(), AAACore.AAA_BUSINESS_TYPE_MODEL.toLowerCase() };

	public static final String FORMATS_MODELS_PACKAGE = "/formats";
	
	public static final String META_DICTIONARY_PROJECT = "pms-models"; 

	// The shared instance
	private static AAACore plugin;

	
	/**
	 * The constructor
	 */
	public AAACore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AAACore getDefault() {
		return plugin;
	}

	public static boolean hasAAANature(IProject project) {
		try {
			return project.hasNature(NATURE_ID);
		} catch (CoreException e) {
			return false;
		}
	}
	
	public static IOfsProject getMetaDictionaryProject() {
		for (IOfsProject ofsProject: OfsCore.getOfsProjectManager().getAllOfsProjects()) {
			if (ofsProject.getName().equals(META_DICTIONARY_PROJECT)) {
				return ofsProject;
			}
		}
		return null;
	}
	
	public static void setRootAAAFolder(String rootFolder) {
		rFolder = rootFolder.trim();
	}

	/**
	 * @return the findroot
	 */
	public static String getFindroot() {
		return rFolder;
	}
}
