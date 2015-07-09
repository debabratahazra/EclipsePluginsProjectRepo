package com.odcgroup.ocs.server.external.ui.builder;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * @author yan
 */
public class ProjectHelper {

	public static String getOutputFolder(IProject project) {
		try {
			IJavaProject javaProject = (IJavaProject)project.getNature(JavaCore.NATURE_ID);
			if (javaProject!=null) {
				return StringUtils.removeStart(javaProject.getOutputLocation()
						.toString() + "/", "/" + project.getName() + "/");
			}
		} catch (CoreException e) {
			return "";
		}
		return "";
	}

}
