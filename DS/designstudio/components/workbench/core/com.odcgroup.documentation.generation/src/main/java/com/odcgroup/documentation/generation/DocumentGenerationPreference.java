package com.odcgroup.documentation.generation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.PreferenceConstants;

public class DocumentGenerationPreference {

	/**
	 * @param project
	 * @return
	 */
	public static String getDocGenerationPreference(IProject project) {
		ProjectPreferences preferences = new ProjectPreferences(project, DocumentationCore.PLUGIN_ID);
		String activity = preferences.get(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME,"C:\\DesignStudio\\");
		return activity;
	}
}
