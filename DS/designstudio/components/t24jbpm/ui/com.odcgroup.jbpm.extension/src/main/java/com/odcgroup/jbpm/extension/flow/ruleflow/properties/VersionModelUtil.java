package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Maps;
import com.odcgroup.jbpm.extension.Activator;

/**
 * @author phanikumark
 *
 */
public class VersionModelUtil {
	private final static String VERSION_EXTENSION = "version";
	
	/**
	 * load versions from active projects in workspace
	 */
	public static Map<String, IResource>  loadVersionsFromActiveProjects() {
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		Map<String, IResource> versionMap = Maps.newLinkedHashMap();;

		for (IProject project : projects) {
			if (project.isOpen()) {
				try {
					versionMap = getAllVersionsFromProject(versionMap, project);
				} catch (CoreException e) {
					Activator.getDefault().logError(e.getLocalizedMessage(), e);
				}
			}
		}
		return versionMap;
	}


	/**
	 * @param modelName
	 * @return
	 */
	public static IResource getVersionModelResource(String modelName) {
		Map<String, IResource> map = loadVersionsFromActiveProjects();
		if (map.containsKey(modelName)) {
			return map.get(modelName);
		}
		return null;
	}

	/**
	 * Method to check whether file is acceptable for code generation.
	 * 
	 * @param file
	 * @param extensions
	 * @return true if the file is contained in a valid models folder
	 */
	private static boolean acceptResource(IFile file) {
		String extension = file.getFileExtension();
		return VERSION_EXTENSION.equals(extension);
	}

	/**
	 * Method to collect list of version files
	 * @param versionMap 
	 * 
	 * @param objects
	 * @return the model resources from a list of IResources/IOfsElement.
	 * @throws CoreException
	 */
	public static Map<String, IResource> getAllVersionsFromProject(final Map<String, IResource> versionMap,IProject iProject) throws CoreException {
		(iProject).accept(new IResourceVisitor() {
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					if (acceptResource((IFile) resource)) {
						String fileName = resource.getName();
						fileName = fileName.substring(0, fileName.lastIndexOf(".version"));
						versionMap.put(fileName, resource);
					}
				}
				return true;
			}
		});
		return versionMap;
	}
	
}
