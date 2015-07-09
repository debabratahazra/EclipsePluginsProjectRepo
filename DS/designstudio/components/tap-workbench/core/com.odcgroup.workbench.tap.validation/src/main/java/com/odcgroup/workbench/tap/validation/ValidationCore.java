package com.odcgroup.workbench.tap.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.tap.validation.internal.builder.ValidationBuilder;

/**
 * The activator class controls the plug-in life cycle
 */
public class ValidationCore extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.validation";

	public static final String CHECK_EXTENSION = "com.odcgroup.workbench.validation.check";
	
	// The shared instance
	private static ValidationCore plugin;
	
	/**
	 * The constructor
	 */
	public ValidationCore() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static ValidationCore getDefault() {
		return plugin;
	}
	
	public static void enableBuilder(IProject project) {
		try {
			OfsCore.addProjectBuilder(project, ValidationBuilder.BUILDER_ID);
		} catch (CoreException e) {
			getDefault().logWarning("Cannot enable validation builder on project " + project.getName(), e);
		}
	}

	public static void disableBuilder(IProject project) {
		try {
			OfsCore.removeProjectBuilder(project, ValidationBuilder.BUILDER_ID);
		} catch (CoreException e) {
			getDefault().logWarning("Cannot disable validation builder on project " + project.getName(), e);
		}
	}
	
	public static boolean isBuilderEnabled(IProject project) {
		return OfsCore.hasProjectBuilder(project, ValidationBuilder.BUILDER_ID);
	}

}
