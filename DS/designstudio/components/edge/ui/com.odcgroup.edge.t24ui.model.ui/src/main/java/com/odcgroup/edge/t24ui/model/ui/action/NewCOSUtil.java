package com.odcgroup.edge.t24ui.model.ui.action;

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
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.edge.t24ui.T24UIFactory;

public class NewCOSUtil {
	
	/**
	 * @param fileName
	 * @param containerpath
	 * @return
	 */
	public static IFile createNewFile(String fileName, IPath containerpath) {
		
		// create the new file and cache it if successful
		final IPath containerPath = containerpath;
		IPath newFilePath = containerPath.append(fileName);
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = null;
		IWorkbenchWindow container = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor)
					throws CoreException {
				try {
					monitor.beginTask("Creating New Item", 2000);
					ContainerGenerator generator = new ContainerGenerator(containerPath);
					generator.generateContainer(new SubProgressMonitor(monitor, 1000));
					createFile(newFileHandle, initialContents, new SubProgressMonitor(monitor, 1000));
				} finally {
					monitor.done();
				}
			}
		};

		try {
			container.run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				ErrorDialog.openError(container.getShell(), 
						"New Item Error", null, 
						((CoreException) e.getTargetException()).getStatus());
			} else {
				// CoreExceptions are handled above, but unexpected runtime
				// exceptions and errors may still occur.
				MessageDialog.openError(container.getShell(),
						"New Item Error", "Error creating / opening the file: "
								+ e.getTargetException().getMessage());
			}
			return null;
		}

		return newFileHandle;
	}
	
	/**
	 * @return
	 */
	public static CompositeScreen createInitialModel() {
		CompositeScreen root = T24UIFactory.eINSTANCE.createCompositeScreen();
		root.setName("CompositeScreen");
		return root;
	}
	
	/**
	 * @param filePath
	 * @return
	 */
	private static IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}
	
	/**
	 * @param fileHandle
	 * @param contents
	 * @param monitor
	 * @throws CoreException
	 */
	public static void createFile(IFile fileHandle, InputStream contents,
			IProgressMonitor monitor) throws CoreException {
		if (contents == null) {
			contents = new ByteArrayInputStream(new byte[0]);
		}

		try {
			IPath path = fileHandle.getFullPath();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			int numSegments = path.segmentCount();
			if (numSegments > 2
					&& !root.getFolder(path.removeLastSegments(1)).exists()) {
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
     * @param file
     * @throws PartInitException
     */
    public static void openEditor(IFile file) throws PartInitException {

    	if (PlatformUI.getWorkbench()!=null && PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null 
        		&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()!=null) {
    		
        	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        	IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
			page.openEditor(new FileEditorInput(file), desc.getId());
			
		}
    	
    }

}
