package com.odcgroup.integrationfwk.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.integrationfwk.ui.decorators.core.ResourcePropertiesManager;

/**
 * Helper class to help out while consumer plug-in project re-factoring.
 * 
 * @author sbharathraja
 * 
 */
public class ConsumerPluginRefactoringHelper {

	/**
	 * Method which helps to close the all opened editors associated with given
	 * project.
	 * 
	 * @param project
	 *            - instance of {@link IProject}
	 * 
	 */
	private void closeAllEditors(final IProject project) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow activeWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (activeWindow == null) {
					return;
				}
				IWorkbenchPage activePage = activeWindow.getActivePage();
				if (activePage == null) {
					return;
				}
				for (IEditorReference editor : activePage.getEditorReferences()) {
					if (isBelongingToModifiedProject(project, editor)) {
						try {
							activePage.closeEditor(activePage.findEditor(editor
									.getEditorInput()), true);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});
	}

	/**
	 * Helps to close the opened editor which has been deleted recently which is
	 * equivalent to given deletedEditorFile.
	 * 
	 * @param deletedEditorFile
	 *            - recently deleted editor
	 */
	private void closeEditor(final IProject modifiedProject,
			final IFile deletedEditorFile) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (!isBelongingToModifiedProject(modifiedProject,
						deletedEditorFile)) {
					return;
				}
				IWorkbenchWindow activeWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (activeWindow == null) {
					return;
				}
				IWorkbenchPage activePage = activeWindow.getActivePage();
				if (activePage == null) {
					return;
				}
				IEditorPart editor = activePage.findEditor(new FileEditorInput(
						deletedEditorFile));
				if (editor != null) {
					activePage.closeEditor(editor, true);
				}
			}
		});
	}

	/**
	 * Determines if the file/project has copied from any other resources.
	 * 
	 * @param flags
	 * @return true - if it has copied from flag, false otherwise.
	 */
	private boolean hasCopiedFromFlags(final int flags) {
		if (((flags & IResourceDelta.MOVED_FROM) == 0)
				|| ((flags & IResourceDelta.COPIED_FROM) == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the removed project was deleted based on the IResourceDelta
	 * flags
	 */
	private boolean hasDeletedRemovedFlags(final int flags) {
		if ((flags & IResourceDelta.MOVED_TO) == 0
				&& (flags & IResourceDelta.REPLACED) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the added project was renamed based on the IResourceDelta
	 * flags
	 * 
	 * @param flags
	 *            - got from delta
	 * @return true if the given flag has renamed flag, false otherwise
	 */
	private boolean hasRenamedAddedFlags(final int flags) {
		if ((flags & IResourceDelta.DESCRIPTION) > 0
				&& (flags & IResourceDelta.MOVED_FROM) > 0
				&& (flags & IResourceDelta.CONTENT) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether the given editor reference is belonging to given
	 * modifiedProject or not.
	 * 
	 * @param modifiedProject
	 *            - project has been modified.
	 * @param editorReference
	 *            - editor opened in eclipse.
	 * @return true if belonging to project family, false otherwise.
	 */
	private boolean isBelongingToModifiedProject(IProject modifiedProject,
			IEditorReference editorReference) {
		try {
			IEditorInput input = editorReference.getEditorInput();
			if (!(input instanceof IFileEditorInput)) {
				return false;
			}
			IFile editorFile = ((IFileEditorInput) input).getFile();
			if (editorFile == null) {
				return false;
			}
			return isBelongingToModifiedProject(modifiedProject, editorFile);
		} catch (PartInitException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Determines whether the given editorFile is belonging to the given
	 * modifiedProject or not.
	 * 
	 * @param modifiedProject
	 *            - project that has been modified.
	 * @param editorFile
	 *            - editor file.
	 * @return true if belonging to project family, false otherwise.
	 */
	private boolean isBelongingToModifiedProject(IProject modifiedProject,
			IFile editorFile) {
		if (editorFile.getProject() == null) {
			return false;
		}
		return modifiedProject.equals(editorFile.getProject());
	}

	/**
	 * processing the project delta with thread safe
	 * 
	 * @param resource
	 *            - changed project
	 * @param delta
	 *            - {@link IResourceDelta}
	 * @return
	 * @throws CoreException
	 */
	public synchronized boolean processProjectDelta(final IResource resource,
			final IResourceDelta delta) throws CoreException {
		final int kind = delta.getKind();
		final int flags = delta.getFlags();

		switch (kind) {
		case IResourceDelta.REMOVED:
			if (hasDeletedRemovedFlags(flags)) {
				if (resource instanceof IProject) {
					closeAllEditors((IProject) resource);
					return true;
				} else if (resource instanceof IFile) {
					closeEditor(resource.getProject(), (IFile) resource);
					return true;
				}
			}
			break;
		case IResourceDelta.CHANGED:
			if (hasRenamedAddedFlags(flags)) {
				System.out.println("Changed Flag");
				return true;
			}
			break;
		case IResourceDelta.ADDED:
			if (hasRenamedAddedFlags(flags)) {
				System.out.println("Added Flag");
				return true;
			} else if (hasCopiedFromFlags(flags)) {
				// as of now,we are interesting in the file type only
				if (resource instanceof IFile) {
					removePublishResults(resource);
				}
				return true;
			}
			break;
		}
		return true;
	}

	/**
	 * Helps to remove the publish result suffix from given resource.
	 * 
	 * @param resource
	 *            - instance of {@link IResource}
	 */
	private void removePublishResults(IResource resource) {
		// not an event file, should not be processed.
		if (!resource.getFileExtension().equalsIgnoreCase("event")) {
			return;
		}
		String suffix = ResourcePropertiesManager.getSuffix(resource);
		if (suffix != null && !"".equalsIgnoreCase(suffix)) {
			ResourcePropertiesManager.removePersistentProperty(resource);
		}
	}
}
