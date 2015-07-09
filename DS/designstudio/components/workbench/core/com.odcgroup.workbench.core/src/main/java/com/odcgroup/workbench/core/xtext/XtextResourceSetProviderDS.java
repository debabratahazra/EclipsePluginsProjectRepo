package com.odcgroup.workbench.core.xtext;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * Factory (Guice calls this "Provider") for XtextResourceSet (or sub-class).
 * 
 * Normally we would want to use use IResourceSetProvider here to be clean, but
 * we until https://bugs.eclipse.org/bugs/show_bug.cgi?id=326219 is available we
 * cannot (because that's in *.ui). After discussion we Sven, we instead simply
 * copy/paste a few lines from JavaProjectResourceSetInitializer relevant for
 * Xbase (DS EL) to work here.
 * 
 * Normally you want this to be properly DI-based, but as our code isn't, this
 * is just static.
 * 
 * This is used in OfsProject and GenerationCommon.
 * 
 * @author Michael Vorburger
 */
public class XtextResourceSetProviderDS {

	public static <T extends XtextResourceSet> T newXtextResourceSet(Class<T> xtextResourceSetClass, IProject project) {
		T newXtextResourceSet = newInstance(xtextResourceSetClass);
		
		IJavaProject javaProject = JavaCore.create(project);
		if (javaProject == null || !javaProject.exists())
			throw new IllegalStateException("Models project must be a Java project: " + project.getName());
		newXtextResourceSet.setClasspathURIContext(javaProject);

		return newXtextResourceSet;
	}
	
	private static <T extends XtextResourceSet> T newInstance(Class<T> xtextResourceSetClass) {
		try {
			return xtextResourceSetClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Could not instantiate: " + xtextResourceSetClass.getName(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Could not access: " + xtextResourceSetClass.getName(), e);
		}
	}
}
