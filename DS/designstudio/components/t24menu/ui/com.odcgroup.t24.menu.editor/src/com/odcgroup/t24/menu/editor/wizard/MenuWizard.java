package com.odcgroup.t24.menu.editor.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ContainerGenerator;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.t24.menu.editor.utils.MenuUtils;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.impl.MenuFactoryImpl;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

/**
 * @author scn
 *
 */
public class MenuWizard extends AbstractNewModelResourceCreationWizard {

	protected MenuWizardPage menuPage;
	protected IFile file;

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = "menu";
		super.init(workbench, currentSelection);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard#addPages()
	 */
	public void addPages() {
		super.addPages();
		if (menuPage == null) {
			if (containerFullPath == null) {
				containerFullPath = getSelectionFullPath();
			}
			menuPage = new MenuWizardPage("menuCreation", getWorkbench(), containerFullPath);
		}
		addPage(menuPage);
	}
	
	/**
	 * @return
	 */
	private MenuRoot createInitialModel() {
		MenuRoot root = MenuFactoryImpl.eINSTANCE.createMenuRoot();
		root.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("menu"));
		MenuItem item = MenuFactoryImpl.eINSTANCE.createMenuItem();
		root.setRootItem(item);
		return root;
	}

	/**
	 * @param fileHandle
	 * @param contents
	 * @param monitor
	 * @throws CoreException
	 */
	protected void createFile(IFile fileHandle, InputStream contents,
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
	 * @param filePath
	 * @return
	 */
	protected IFile createFileHandle(IPath filePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}

	/**
	 * @param fileName
	 * @return
	 */
	public IFile createNewFile(String fileName) {
		if (file != null) {
			return file;
		}

		// create the new file and cache it if successful

		final IPath containerPath = containerFullPath;
		IPath newFilePath = containerPath.append(fileName);
		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = null;

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
				MessageDialog.openError(getContainer().getShell(),
						"New Item Error", "Error creating / opening the file: "
								+ e.getTargetException().getMessage());
			}
			return null;
		}

		file = newFileHandle;

		return file;
	}

	/**
	 * @param resource
	 * @param file
	 * @param monitor
	 * @throws CoreException
	 */
	public static void doSave(Resource resource, IFile file,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Saving model", 2);

		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			resource.save(os, Collections.EMPTY_MAP);
			monitor.worked(1);

			if (file.exists()) {
				file.setContents(new ByteArrayInputStream(os.toByteArray()),
						false, true, new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			} else {
				file.create(new ByteArrayInputStream(os.toByteArray()), false,
						new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			}
		} catch (IOException e) {
		} finally {
			monitor.done();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (canFinish()) {

			try {
				final IFile file = createNewFile(menuPage.getFileName());

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				workspace.run(new IWorkspaceRunnable() {

					public void run(IProgressMonitor monitor)
							throws CoreException {
						try {
							monitor.beginTask("Creating model", 2);
							XtextResourceSet resourceSet = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, file.getProject());
							URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
							Resource resource = resourceSet.getResource(uri, true);
							MenuRoot root = createInitialModel();
							root.getRootItem().setName(menuPage.getNameFieldValue());
							resource.getContents().add((EObject) root);
							monitor.worked(1);
							doSave(resource, file, new SubProgressMonitor(monitor, 1));
							monitor.worked(1);

						} finally {
							monitor.done();
						}
					}
				}, file, IWorkspace.AVOID_UPDATE, null);
				MenuUtils.openEditor(file);
				return true;
			} catch (CoreException ex) {
				ex.printStackTrace();
			}
		}

		return false;
	}
}
