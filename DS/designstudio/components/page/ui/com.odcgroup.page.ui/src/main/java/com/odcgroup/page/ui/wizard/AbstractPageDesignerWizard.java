package com.odcgroup.page.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.ui.ide.IDE;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.util.ModelUtils;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

/**
 * Common abstract class for the page designer wizard
 * 
 * @author atr
 */
abstract class AbstractPageDesignerWizard extends AbstractNewModelResourceCreationWizard {
	
	/**
	 * @return the model specification
	 */
	protected abstract ModelSpecification getModelSpecification();

	/**
	 * Executed when the Wizard is finished.
	 * 
	 * @return boolean True if the finish was successful
	 */
	public boolean performFinish() {
		
		final boolean[] result = new boolean[1];
		WorkspaceModifyOperation op = new WorkspaceModifyOperation(null) {
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException {
				result[0] = doFinish(monitor);
			}
		};

		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), "Internal error", null, ((CoreException) e
						.getTargetException()).getStatus());
			} else {
				getShell().close();
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still occur.
				OfsCore.getDefault().logError("Diagram creation failed", e.getTargetException());
			}

			return false;

		}
		return result[0];
	}

	/**
	 * Do finish method
	 * 
	 * @param monitor
	 *            The monitor
	 * @return boolean The boolean result of the execution
	 */
	public boolean doFinish(IProgressMonitor monitor) {
		boolean result = false;
		IFile file = createNewFile();
		if (file != null) {
			Model model = getModelSpecification().createModel();
			ModelUtils.saveModel(model, file);
			openEditor(file);
			result = true;
		}
		return result;
	}

	/**
	 * Creates a file resource given the file handle and contents.
	 * 
	 * @param fileHandle
	 *            the file handle to create a file resource with
	 * @param contents
	 *            the initial contents of the new file resource, or
	 *            <code>null</code> if none (equivalent to an empty stream)
	 * @param monitor
	 *            the progress monitor to show visual progress with
	 * @exception CoreException
	 *                if the operation fails
	 * @exception OperationCanceledException
	 *                if the operation is canceled
	 */
	protected void createFile(IFile fileHandle, InputStream contents, IProgressMonitor monitor) throws CoreException {
		if (contents == null) {
			contents = new ByteArrayInputStream(new byte[0]);
		}

		try {
			IPath path = fileHandle.getFullPath();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			int numSegments = path.segmentCount();
			if (numSegments > 2 && !root.getFolder(path.removeLastSegments(1)).exists()) {
				// If the direct parent of the path doesn't exist, try to create
				// the
				// necessary directories.
				for (int i = numSegments - 2; i > 0; i--) {
					IFolder folder = root.getFolder(path.removeLastSegments(i));
					if (!folder.exists()) {
						folder.create(false, true, monitor);
					}
				}
			}
			fileHandle.create(contents, false, monitor);
		} catch (CoreException e) {
			// If the file already existed locally, just refresh to get contents
			if (e.getStatus().getCode() == IResourceStatus.PATH_OCCUPIED) {
				fileHandle.refreshLocal(IResource.DEPTH_ZERO, null);
			} else {
				throw e;
			}
		}

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	/**
	 * Creates a new file resource in the selected container and with the
	 * selected name. Creates any missing resource containers along the path;
	 * does nothing if the container resources already exist.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish
	 * on the wizard; the enablement of the Finish button implies that all
	 * controls on on this page currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this page caches the new file once it has been successfully
	 * created; subsequent invocations of this method will answer the same file
	 * resource without attempting to create it again.
	 * </p>
	 * <p>
	 * This method should be called within a workspace modify operation since it
	 * creates resources.
	 * </p>
	 * 
	 * @return the created file resource, or <code>null</code> if the file was
	 *         not created
	 */
	public IFile createNewFile() {

		// create the new file and cache it if successful

		IPath newFilePath = getModelSpecification().getModelPath();
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = getInitialContents();

		WorkspaceModifyOperation op = new WorkspaceModifyOperation(createRule(newFileHandle)) {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Creating New Item", 2000);
					ContainerGenerator generator = new ContainerGenerator(getModelSpecification().getContainerPath());
					generator.generateContainer(new SubProgressMonitor(monitor, 1000));
					createFile(newFileHandle, initialContents, new SubProgressMonitor(monitor, 1000));
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(getContainer().getShell(), // Was
						// Utilities.getFocusShell()
						"New Item Error", null, // no special message
						((CoreException) e.getTargetException()).getStatus());
			} else {
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still occur.
				Logger.error("createNewFile()", e.getTargetException()); //$NON-NLS-1$
				MessageDialog.openError(getContainer().getShell(), "New Item Error",
						"Error creating / opening the file: " + e.getTargetException().getMessage());
			}
			return null;
		}

		return newFileHandle;
	}

	/**
	 * Gets the container full path
	 * 
	 * @return IPath The container full path
	 */
	public IPath getContainerFullPath() {
		return containerFullPath;
	}

	/**
	 * Returns a stream containing the initial contents to be given to new file
	 * resource instances. <b>Subclasses</b> may wish to override. This default
	 * implementation provides no initial contents.
	 * 
	 * @return initial contents to be given to new file resource instances
	 */
	protected InputStream getInitialContents() {
		return null;
	}

	/**
	 * Creates a file resource handle for the file with the given workspace
	 * path. This method does not create the file resource; this is the
	 * responsibility of <code>createFile</code>.
	 * 
	 * @param filePath
	 *            the path of the file resource to create a handle for
	 * @return the new file resource handle
	 * @see #createFile
	 */
	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}

	/**
	 * Returns the scheduling rule to use when creating the resource at the
	 * given container path. The rule should be the creation rule for the
	 * top-most non-existing parent.
	 * 
	 * @param resource
	 *            The resource being created
	 * @return The scheduling rule for creating the given resource
	 * @since 3.1
	 */
	protected ISchedulingRule createRule(IResource resource) {
		IResource parent = resource.getParent();
		while (parent != null) {
			if (parent.exists()) {
				return resource.getWorkspace().getRuleFactory().createRule(resource);
			}
			resource = parent;
			parent = parent.getParent();
		}
		return resource.getWorkspace().getRoot();
	}

	/**
	 * Opens the editor.
	 * 
	 * @param file
	 *            The file to open in the editor
	 */
	private void openEditor(IFile file) {
		final IFile aFile = file;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow dwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = dwindow.getActivePage();
				if (page != null) {
					try {
						IDE.openEditor(page, aFile, true);
					} catch (PartInitException pie) {
						Logger.error("Unable to open the Editor", pie);//$NON-NLS-1$
						throw new PageException("Unable to open the Editor", pie);
					}
				}
			}
		});
	}

}