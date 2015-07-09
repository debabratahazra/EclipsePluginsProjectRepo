package com.odcgroup.workbench.integration.tests;

import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class ProjectUtils {
	
	public static String JSUNIT_WORKSPACE = "../../jsunit-workspace";
	public static String WUI_BLOCK_DIR = "src/wui_block/";
	
	private static int recursionLevel = 0;

	public static String getGenProjectName(String projectName) {
		return projectName+"-gen";
	}

	public static String getMessagesXmlPath(String projectName) {
		return getGenProjectName(projectName)+"/"+WUI_BLOCK_DIR+projectName+"/"+getMessagesXmlFileName();
	}

	public static String getMessagesXmlPath(String projectName, Locale locale) {
		return getGenProjectName(projectName)+"/"+WUI_BLOCK_DIR+projectName+"/"+getMessagesXmlFileName(locale);
	}
	
	public static String getMessagesXmlFileName() {
		return "messages.xml";
	}
	
	public static String getMessagesXmlFileName(Locale locale) {
		return "messages_"+locale.toString()+".xml";
	}
	
	public static void deleteModelProject(String projectName) throws Exception {
		deleteProject(projectName);
		deleteProject(getGenProjectName(projectName));
	}
	
	private static void deleteProject(String projectName) throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		try {
			project.delete(true, null);
		} catch(CoreException e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {}
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			recursionLevel++;
			if(recursionLevel<100) {
				deleteProject(projectName);
			}
		}
		recursionLevel = 0;
	}
}
