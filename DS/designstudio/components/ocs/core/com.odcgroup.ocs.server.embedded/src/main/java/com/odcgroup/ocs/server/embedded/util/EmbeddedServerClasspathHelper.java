package com.odcgroup.ocs.server.embedded.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.server.model.IDSProject;

public class EmbeddedServerClasspathHelper {

	private static Logger logger = LoggerFactory.getLogger(EmbeddedServerClasspathHelper.class);
	private static EmbeddedServerClasspathHelper INSTANCE = new EmbeddedServerClasspathHelper();
	
	protected EmbeddedServerClasspathHelper() {
	}
	
	public static EmbeddedServerClasspathHelper getInstance() {
		return INSTANCE;
	}

	/**
	 * @param serverProject
	 * @return
	 */
	private IJavaProject getJavaProject(IProject project) {
		IJavaProject javaProject = null;
		try {
			javaProject = (IJavaProject)project.getNature(JavaCore.NATURE_ID);
		} catch (CoreException e) {
			logger.error("The server projcet " + project.getName() + " is not a java project");
		}
		return javaProject;
	}
	
	public Set<IProject> getWorkspaceReferencedProject(IProject serverProject, boolean includeContainerDependencies, boolean includeBuildPathDependencies) {
		IJavaProject serverJavaProject = null;
		try {
			serverJavaProject = (IJavaProject)serverProject.getNature(JavaCore.NATURE_ID);
		} catch (CoreException e) {
			// Ignore
		}
		if (serverJavaProject == null) {
			logger.error("The server projcet " + serverProject.getName() + " is not a java project");
			return Collections.emptySet();
		}

		List<IClasspathEntry> resolvedClasspath = null;
		try {
			resolvedClasspath = resolveClasspath(includeContainerDependencies,
					includeBuildPathDependencies, serverJavaProject);
		} catch (JavaModelException e) {
			logger.error("An unexpected error occured when getting the classpath of server projcet " + serverProject.getName(), e);
			return Collections.emptySet();
		}
		
		// Convert each resolved classpath entry to a physical location
		Set<IProject> result = new LinkedHashSet<IProject>();
		for (IClasspathEntry entry: resolvedClasspath) {
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE)
				continue;
			
			if (entry.getPath().segmentCount() == 1) {
				IProject refProject = ResourcesPlugin.getWorkspace().getRoot().getProject(entry.getPath().segment(0));
				if (refProject.exists()) {
					result.add(refProject);
				}
			}
		}
		return result;
	}

	/**
	 * @param includeContainerDependencies
	 * @param includeBuildPathDependencies
	 * @param serverJavaProject
	 * @throws JavaModelException
	 */
	private List<IClasspathEntry> resolveClasspath(boolean includeContainerDependencies,
			boolean includeBuildPathDependencies, IJavaProject serverJavaProject)
			throws JavaModelException {
		List<IClasspathEntry> resolvedClasspath = new ArrayList<IClasspathEntry>();
		IClasspathEntry[] rawClasspath = serverJavaProject.getRawClasspath();
		
		// Resolve the classpath (containers, variables, ...)
		for (IClasspathEntry rawEntry: rawClasspath) {
			if (rawEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				if (includeContainerDependencies) {
					if (!rawEntry.getPath().toString().startsWith(org.eclipse.jdt.launching.JavaRuntime.JRE_CONTAINER)) {
						List<IClasspathEntry> asList = Arrays.asList(JavaCore.getClasspathContainer(rawEntry.getPath(), serverJavaProject).getClasspathEntries());
						resolvedClasspath.addAll(asList);
					}
				}
			} else {
				if (includeBuildPathDependencies) {
					resolvedClasspath.add(JavaCore.getResolvedClasspathEntry(rawEntry));
				}
			}
		}
		return resolvedClasspath;
	}
	
	/**
	 * Return true if the container depdendencies are populated
	 * @param serverJavaProject
	 * @return
	 */
	public boolean areContainerDependenciesPopulated(IJavaProject serverJavaProject) {
		try {
			return resolveClasspath(true, false, serverJavaProject).size() == 0;
		} catch (JavaModelException e) {
			return false;
		}
	}
	
	/**
	 * Build the classpath of the java project
	 * @param javaProject
	 * @return the classpath of the java project
	 * @throws JavaModelException
	 */
	public Set<String> getClasspath(IJavaProject javaProject) throws JavaModelException {
		Set<String> classpath = getClasspath(javaProject, new HashSet<IJavaProject>());
		logger.debug("the classpath contains " + classpath.size() + " disctinct entries");
		for (String line: classpath) {
			logger.debug(line);
		}
		return classpath;
	}

	private Set<String> getClasspath(IJavaProject currentJavaProject, Set<IJavaProject> visitedProjects) throws JavaModelException {
		// Use linked hash set to avoid unpredictable order of the classpath
		Set<String> result = new LinkedHashSet<String>();
		Set<IJavaProject> referencedProjects = new LinkedHashSet<IJavaProject>();
		
		IClasspathEntry[] rawClasspath = currentJavaProject.getRawClasspath();
		List<IClasspathEntry> resolvedClasspath = new ArrayList<IClasspathEntry>();
		
		// Resolve the classpath (containers, variables, ...)
		for (IClasspathEntry rawEntry: rawClasspath) {
			if (rawEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				if (!rawEntry.getPath().toString().startsWith(org.eclipse.jdt.launching.JavaRuntime.JRE_CONTAINER)) {
					List<IClasspathEntry> asList = Arrays.asList(JavaCore.getClasspathContainer(rawEntry.getPath(), currentJavaProject).getClasspathEntries());
					resolvedClasspath.addAll(asList);
				}
			} else {
				resolvedClasspath.add(JavaCore.getResolvedClasspathEntry(rawEntry));
			}
		}
		
		// Convert each resolved classpath entry to a physical location
		for (IClasspathEntry entry: resolvedClasspath) {
			File entryFile;
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				if (entry.getOutputLocation() != null) {
					entryFile = entry.getOutputLocation().toFile();
				} else {
					entryFile = referencedResource(currentJavaProject, currentJavaProject.getOutputLocation().toFile());
				}
			} else {
				entryFile = entry.getPath().toFile();
			}
			if (entryFile == null) {
				logger.error("Classpath entry null for " + entry);
				continue;
			}
			
			if (entryFile.exists()) {
				result.add(asClasspathEntry(entryFile));
			} else {
				IJavaProject refProject = referencedProject(entryFile);
				File refResource = referencedResource(refProject, entryFile);
				if (refResource != null) {
					// Reference to a resource in another project
					if (refResource.exists()) {
						result.add(asClasspathEntry(refResource));
					} else {
						logger.warn("Embedded Server classpath problem. The classpath references a non existing file in project: " + refResource.toString());
					}
				} else if (refProject != null) {
					// Reference to another projects will be recursively examined
					if (!refProject.equals(currentJavaProject) && 
							!visitedProjects.contains(refProject)) {
						visitedProjects.add(refProject);
						referencedProjects.add(refProject);
					}
				}
			}
		}
		
		// Add the output location of the java project
		// (This is done it last position because it is must be added after source folder specific output folder)
		File file = referencedResource(currentJavaProject, currentJavaProject.getOutputLocation().toFile());
		if (file != null && file.exists()) {
			result.add(asClasspathEntry(file));
		}

		// Reference to another projects will be recursively examined
		// but after adding the output location of the current project
		for (IJavaProject referencedProject: referencedProjects) {
			Set<String> referencedClasspath = getClasspath(referencedProject, visitedProjects);
			result.addAll(referencedClasspath);
		}
		
		return result;
	}
	
	/**
	 * Convert a file to a classpath entry string. The classpath entry must end with a \\ if
	 * it references a folder. 
	 * @param file
	 * @return a string referencing the
	 */
	private String asClasspathEntry(File file) {
		if (file.isDirectory()) {
			return file.toString() + "\\";
		} else {
			return file.toString();
		}
	}

	/**
	 * Retrieve the java project referenced by this entry, <code>null</code> otherwise
	 * @param entryFile
	 * @return the java project referenced by this entry, <code>null</code> otherwise
	 */
	private IJavaProject referencedProject(File entryFile) {
		IJavaProject result = null;
		if (entryFile.getPath().startsWith("\\")) {
			String[] splittedEntries = StringUtils.split(entryFile.getPath(), "\\");
			if (splittedEntries.length > 0) {
				String projectName = splittedEntries[0];
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project.exists() && project.isOpen()) {
					try {
						// Is a java project ?
						result = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
					} catch (CoreException e) {
						logger.error("Unable to get java project", e);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Retrieve the resource associated to an entry 
	 * @param refProject
	 * @param entryFile
	 * @return the resource associated to an entry, <code>null</code> if the resource path is "" or 
	 * if the java project is null. 
	 */
	private File referencedResource(IJavaProject refProject, File entryFile) {
		if (refProject == null || !refProject.getProject().exists() || !refProject.getProject().isOpen()) {
			return null;
		}
		
		File result = null;		
		if (entryFile.getPath().startsWith("\\")) {
			String path = StringUtils.removeStart(entryFile.getPath(), "\\" + refProject.getProject().getName());
			if (!path.isEmpty()) {
				result = refProject.getProject().getFile(path).getLocation().toFile();
			}
		}
		return result;
	}

	public void updateServerBuildPath(IEmbeddedServer embeddedServer,
			List<IDSProject> addedOcsProjects, List<IDSProject> removedOcsProjects) throws JavaModelException {
		IProject serverProject = embeddedServer.getServerProject();
		IJavaProject serverJavaProject = getJavaProject(serverProject);
		List<IClasspathEntry> newRawClasspath = new ArrayList<IClasspathEntry>(Arrays.asList(serverJavaProject.getRawClasspath()));

		boolean classpathChanged = false;

		// Added projects
		List<IDSProject> ocsProjects = new ArrayList<IDSProject>(embeddedServer.getDsProjects());
		for (IDSProject ocsProject: addedOcsProjects) {
			IClasspathEntry entry = JavaCore.newProjectEntry(new Path("/" + ocsProject.getName()));
			newRawClasspath.add(entry);
			ocsProjects.add(ocsProject);
			classpathChanged = true;
		}
		
		// Removed projects from the classpath (if present)
		for (IDSProject project: removedOcsProjects) {
			for (IClasspathEntry entry: serverJavaProject.getRawClasspath()) {
				if (("/" + project.getName()).equals(entry.getPath().toString())) {
					newRawClasspath.remove(entry);
					classpathChanged = true;
					continue;
				}
			}
		}
		if (classpathChanged)
			serverJavaProject.setRawClasspath(newRawClasspath.toArray(new IClasspathEntry[newRawClasspath.size()]), null);

		// Update the model
		ocsProjects.removeAll(removedOcsProjects);
		embeddedServer.updateProjectList(ocsProjects.toArray(new IDSProject[ocsProjects.size()]));
	}

}
