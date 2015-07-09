package com.odcgroup.workbench.generation.init.classpath;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;

/**
 * Checks a project's classpath ensuring that the path and output path (see Eclipse .classpath file) correspond.
 * For example, <classpath path="/src/wui_block" output="/target/wui_block" /> is valid, but
 * <classpath path="/src/wui_block" output="/target/anything_else" /> is not.
 * @author amc
 */
public class OutputPathChecker {

	private static final int PROJECT_SEGMENT = 0;
	private static final int SRC_OR_TARGET_SEGMENT = 1;
	private static final int COMMON_SEGMENTS_START = 2; // after the project name and src/target, the remaining segments for path and output should be the same
	
	/**
	 * @param project
	 * @param pathsToCheck entries in the classpath will only be checked if entry is in pathsToCheck
	 */
	public IStatus check(IJavaProject project, Collection<String> pathsToCheck) {
		pathsToCheck = emptyCollectionIfNull(pathsToCheck);
		MultiStatus overallStatus = new MultiStatus(GenerationCore.PLUGIN_ID, IStatus.OK, "ok", null);
		IProject iProject = project.getProject();
		try {
			for(IClasspathEntry classpathEntry : GenerationCore.getClasspathEntries(project)) {
				checkEntry(iProject, classpathEntry, pathsToCheck, overallStatus);
			}
			checkDefaultOutputFolder(iProject, overallStatus);
		} catch (JavaModelException e) {
			overallStatus.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "", e));
		}
		return overallStatus;
	}

	private Collection<String> emptyCollectionIfNull(Collection<String> pathsToCheck) {
		if(pathsToCheck == null) {
			return new ArrayList<String>();
		}
		return pathsToCheck;
	}

	private void checkEntry(IProject iProject, IClasspathEntry classpathEntry, Collection<String> pathsToCheck, MultiStatus overallStatus) {
		if(isInPathsToCheck(pathsToCheck, classpathEntry)) {
			IStatus status = checkEntry(iProject, classpathEntry);
			if(status != null) {
				overallStatus.add(status);
			}
		}
	}

	private boolean isInPathsToCheck(Collection<String> pathsToCheck, IClasspathEntry classpathEntry) {
		boolean inPathsToCheck = false;
		IPath entryPath = classpathEntry.getPath();
		if(entryPath != null) {
			inPathsToCheck = pathsToCheck.contains(entryPath.toPortableString());
		}
		return inPathsToCheck;
	}

	private IStatus checkEntry(IProject iProject,	IClasspathEntry classpathEntry) {
		IPath entryPath = classpathEntry.getPath();
		IPath outputPath = classpathEntry.getOutputLocation();
		if(outputPath != null) {
			return new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "Source folder must not use separate output folder per source folder (m2eclipse limitation): "+iProject.getName()+"(source entry: "+entryPath+"; ouput path: "+outputPath+ ")");
		}
		return null;
	}
	
	private void checkSegment(String[] pathSegments, int segmentNum, String expectedValue) throws CheckException {
		String actualValue = pathSegments[segmentNum];
		if(!expectedValue.equals(pathSegments[segmentNum])) {
			throw new CheckException("Expected "+expectedValue+" but found "+actualValue);
		}
	}

	private void checkFirstSegmentIsProjectName(IProject project, String[] pathSegments) {
		if(!project.getName().equals(pathSegments[PROJECT_SEGMENT])) {
			throw new RuntimeException("Error in "+this.getClass().getName()+": path does not begin with project name.");
		}
	}	
	
	private void checkDefaultOutputFolder(IProject outputProject, MultiStatus overallStatus) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(outputProject);
		String outputLocation = javaProject.getOutputLocation().toString();
		String expectedOutputLocation = "/" + outputProject.getName() + CodeGenInitializer.TARGET_CLASSES;
		if (!expectedOutputLocation.equals(outputLocation)) {
			IStatus newError = new Status(IStatus.ERROR, 
					GenerationCore.PLUGIN_ID, 
					"Default output folder in project '" + outputProject.getName() +
					"' is not properly configured (should be " + CodeGenInitializer.TARGET_CLASSES + ").");
			overallStatus.add(newError);
		}
	}


}
