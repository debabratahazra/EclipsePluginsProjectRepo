package com.odcgroup.workbench.generation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 *
 * @author pkk
 *
 */
public class CodeGenerationPreferences {
	
	/**
	 * @param project
	 * @return
	 */
	public static String getPageflowActivityPreference(IProject project) {
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		String activity = preferences.get(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, PreferenceConstants.DEFVAL_PAGEFLOW_ACTIVITYNAME);
		return activity;
	}

}
