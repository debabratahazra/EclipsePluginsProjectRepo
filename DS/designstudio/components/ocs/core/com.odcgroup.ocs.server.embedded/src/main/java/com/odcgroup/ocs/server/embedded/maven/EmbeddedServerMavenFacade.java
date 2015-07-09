package com.odcgroup.ocs.server.embedded.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedServerMavenFacade {

	private static Logger logger = LoggerFactory.getLogger(EmbeddedServerMavenFacade.class);

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
					// Reference to another projects will be recurcively examined
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

		// Reference to another projects will be recurcively examined
		// but after adding the output location of the current project
		for (IJavaProject referencedProject: referencedProjects) {
			Set<String> referencedClasspath = getClasspath(referencedProject, visitedProjects);
			result.addAll(referencedClasspath);
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


}
