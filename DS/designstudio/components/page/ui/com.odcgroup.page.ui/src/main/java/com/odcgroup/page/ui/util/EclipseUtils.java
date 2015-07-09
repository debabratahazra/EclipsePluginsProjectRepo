package com.odcgroup.page.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.ModelEditorInput;

/**
 * Utility methods for use with Eclipse.
 * 
 * @author Gary Hayes
 * @author Alain Tripod
 */
public final class EclipseUtils {
	
	/**
	 * @return <code>true</code> if the ofs model resource attached to 
	 * the current active editor is read only, otherwise it returns
	 * <code>false</code>
	 */
	public static boolean modelIsReadonly() {
		boolean readonly = true;
		IEditorPart ep = EclipseUtils.findActiveEditor();
		if (ep != null) {
			IEditorInput input = ep.getEditorInput();
			if (input instanceof ModelEditorInput) {
				IOfsModelResource mr;
				try {
					mr = (IOfsModelResource)((ModelEditorInput)input).getStorage();
					readonly = ! mr.hasScope(IOfsProject.SCOPE_PROJECT);
				} catch (CoreException ex) {
					ex.printStackTrace();
				}
			} else {
				readonly = false;
			}
		}
		return readonly;
	}
	
	/**
	 * Finds the IOfsProject corresponding to the given IPath.
	 * 
	 * @param path The IPath to find the project for
	 * @return IOfsProject The project
	 */
	public static IOfsProject findProject(IPath path) {
		Assert.isNotNull(path);
		String projectName = path.segment(0);
		return findProject(projectName);
	}
	
	/**
	 * Finds the IOFSProject given its name.
	 * 
	 * @param name The name of the project
	 * @return IOfsProject The project
	 */
	public static IOfsProject findProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		IOfsProject ofsProject = null;
		if (project != null) { 
			ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		}
		return ofsProject;
	}	
	
	/**
	 * Gets the current active page.
	 * @return IWorkbenchPage The active page
	 */
	public static IWorkbenchPage findActivePage() {
		IWorkbench iworkbench = PlatformUI.getWorkbench();
		if (iworkbench == null) {
			return null;
		}
		IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
		if (iworkbenchwindow == null) {
			return null;
		}
		IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
		return iworkbenchpage;
	}
	
	/**
	 * Gets the current active editor.
	 * @return IEditorPart The active editor
	 */
	public static IEditorPart findActiveEditor() {
		IWorkbenchPage iworkbenchpage = findActivePage();
		if (iworkbenchpage == null) {
			return null;
		}
		return iworkbenchpage.getActiveEditor();
	}

	/**
	 * Gets the current active OFS project.
	 * @return IOfsProject The active project
	 */
	public static IOfsProject findCurrentProject() {
		if(PlatformUI.isWorkbenchRunning()) {
			IEditorPart ieditorpart = findActiveEditor();
			if (ieditorpart == null) {
				return null;
			}
			return findProject(ieditorpart); 
		} else {
			return null;
		}
	}
	
	/**
	 * Extract the OFS project from the editor part.
	 * @param editor The active editor
	 * @return IOfsProject The active project
	 */
	public static IOfsProject findProject(IEditorPart editor) {
		Assert.isNotNull(editor);
		return findProject(editor.getEditorInput());
	}

	/**
	 * Extract the OFS project from the editor input.
	 * @param input The Editor input
	 * @return IOfsProject The active project
	 */
	public static IOfsProject findProject(IEditorInput input) {
		IOfsProject ofsProject = null;
		
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fei = (IFileEditorInput)input;
			IProject project = fei.getFile().getProject();
			if (project != null) {
				ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			}
		}
		
		else if (input instanceof ModelEditorInput) {
			ModelEditorInput mei = (ModelEditorInput)input;
			try {
				ofsProject = ((IOfsModelResource)mei.getStorage()).getOfsProject();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		return ofsProject;
	}
	
}