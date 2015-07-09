package com.odcgroup.t24.version.editor.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

public class VersionUtils {

	public final static int JBC_ROUTINE = 1;
	public final static int JAVA_ROUTINE = 2;
	public final static int ALL_ROUTINE = 3;

	public final static String VERSION_FILE_EXTENSION = ".version";
	
	public static Logger logger = LoggerFactory.getLogger(VersionUtils.class);

	public static Collection<IResource> listFilesAsArray(IResource directory,
			int type, boolean recurse) throws CoreException {
		Collection<IResource> files = listFiles(directory, type, recurse);
		// IResource[] arr = new IResource[files.size()];
		return files;// .toArray(arr);
	}

	public static IFile getFile(String fileName) {
		try {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			String ignoreFolder = null;
			for (IProject project : projects) {
				if(project.hasNature(JavaCore.NATURE_ID)) {
					IJavaProject javaProject = (IJavaProject) JavaCore.create(project);
					String outputFolder = javaProject.getOutputLocation().toString();
					String[] tokens = outputFolder.split("/");
					if(tokens.length > 0) {
						ignoreFolder = tokens[tokens.length - 2];
					}
					
				}
				IFile result = recurseAndGetMatch(project, fileName, ignoreFolder);
				if (result != null) {
					return result;
				}
			}
		} catch(CoreException ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	private static IFile recurseAndGetMatch(IResource dir, String fileName, String ignoreFolder) {
		try {
			IResource[] resources = null;
			if (dir instanceof IFolder) {
				if(!dir.getName().equals(ignoreFolder))
					resources = ((IFolder) dir).members();
			} else if (dir instanceof IProject) {
				resources = ((IProject) dir).members();
			}
			if(resources != null) {
				for (IResource resource : resources) {
					if (resource instanceof IFile) {
						IFile file = (IFile) resource;
						if (file != null && file.getFileExtension() != null) {
							if (file.getName().equals(fileName)) {
								return file;
							}
						}
					}
					if (resource instanceof IFolder) {
						IFile result = recurseAndGetMatch(resource, fileName, ignoreFolder);
						if (result != null) {
							return result;
						}
					}
				}
			}
		} catch (CoreException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * @param file physical file
	 * @param fileName name of version / routine
	 * @param type either screen / routine name
	 */
	public static void openEditor(IFile file, String fileName, int type) {
		if (file != null) {
			openEditor(file);
		} else {
			MessageDialog.open(MessageDialog.INFORMATION, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Routine Selection", "The selected "+(type==0 ?"screen ":"routine ")+fileName+" is not present in the current workspace", SWT.None);
		}
	}
	
	public static void openEditor(IFile file) {
		if (file != null) {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				page.openEditor(new FileEditorInput(file), desc.getId());
			} catch (PartInitException ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
		} else {
			MessageDialog.open(MessageDialog.INFORMATION, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Routine Selection", "The selected routine is not present in the current workspace", SWT.None);
		}
	}

	public static Collection<IResource> listFiles(IResource directory,
			int type, boolean recurse) throws CoreException {
		String ext = null;
		switch (type) {
		case JBC_ROUTINE:
			ext = "b";
			break;
		case JAVA_ROUTINE:
			ext = "java";
		default:
			break;
		}
		Vector<IResource> files = new Vector<IResource>();
		IResource[] resources = null;
		if (directory instanceof IProject) {
			resources = ((IProject) directory).members();
		}
		if (directory instanceof IFolder) {
			if (directory.getProject().hasNature(JavaCore.NATURE_ID)) {
				String ignoreFolder = null;
				IJavaProject javaProject = (IJavaProject) JavaCore
						.create(directory.getProject());
				String outputFolder = javaProject.getOutputLocation().toString();
				String[] tokens = outputFolder.split("/");
				if (tokens.length > 2) {
					ignoreFolder = tokens[2];
				}
				if (!directory.getName().equals(ignoreFolder)) {
					resources = ((IFolder) directory).members();
				}
			} else {
				resources = ((IFolder) directory).members();
			}
		}

		if(resources != null) {
			for (IResource entry : resources) {
				if (entry instanceof IFile) {
					IFile file = (IFile) entry;
					if (file != null && file.getFileExtension() != null) {
						if (type == ALL_ROUTINE
								&& (file.getFileExtension().equals("java") | file
										.getFileExtension().equals("b"))) {
							files.add(file);
						}
						if (ext != null && file.getFileExtension().equals(ext)) {
							files.add(file);
						}
					}
				}
				if (recurse && entry instanceof IFolder) {
					files.addAll(listFiles(entry, type, recurse));
				}
			}
		}
		return files;
	}

	public static List<String> getRoutineNames(IProject project, int type) {
		List<String> nameList = new ArrayList<String>();
		try {
			Collection<IResource> resourceList = listFiles((IResource) project,
					type, true);
			for (IResource resource : resourceList) {
				String name = resource.getName();
				if (name != null) {
					nameList.add(name);
				}
			}
		} catch (CoreException e) {

		}
		return nameList;
	}
	/**
	 * return the list of editors in the current active page.
	 * @param editorId
	 * @return editor list
	 */
	public static ArrayList<IEditorReference> getEditors(String editorId){
		IWorkbenchWindow workBenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IEditorReference[] editorReferences = null;
		ArrayList<IEditorReference> editorList = new ArrayList<IEditorReference>();
		if(workBenchWindow != null){
			editorReferences = workBenchWindow.getActivePage().getEditorReferences();
		}
		if(editorReferences != null ){
			for(IEditorReference refrence : editorReferences){
				if(refrence.getId().equals(editorId)){
					editorList.add(refrence);
				}
			}
		}
		return editorList;
	}
	/**
	 * get the number of editor open in the page.
	 * @param editorId
	 * @return editor count
	 */
	public static int getEditorCount(String editorId){
		return getEditors(editorId).size();
	}
	
	/**
	 * check if the version editor count more than 20.
	 */
	public static boolean isMoreVersionEditorOpen() {
		if(VersionUtils.getEditorCount(VersionEditorActivator.PLUGIN_ID) ==20) {
			MessageDialog.openWarning(OfsEditorUtil.window.getShell(), "Screen Form Editor", "Opening more than 20 Screen form editors not allowed.");
			return true;
		}
		return false;
	}
}
