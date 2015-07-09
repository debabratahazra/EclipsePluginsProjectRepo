package com.odcgroup.menu.editor.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.menu.editor.MenuEditorPlugin;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author pkk
 *
 */
public class NewMenuAction extends Action {
	
	private IStructuredSelection selection;
	
	/**
	 * 
	 */
	public NewMenuAction() {
		setText("New Menu...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				MenuEditorPlugin.PLUGIN_ID, "icons/full/obj16/MenuModelFile.gif"));
	}

	@Override
	public void run() {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IFile file = NewMenuUtil.createNewFile("Menu.menu", getSelectionFullPath());

			workspace.run(new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor)
						throws CoreException {
					try {
						monitor.beginTask("Creating model", 3);
						IOfsProject ofsProject = OfsCore.getOfsProject(file.getProject());
						IOfsModelResource modelResource = ofsProject.getModelResourceSet()
								.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
						Resource resource = ofsProject.getModelResourceSet().getResource(
										modelResource.getURI(), false);
						MenuRoot root = NewMenuUtil.createInitialModel();
						root.getRootItem().setName("RootMenu");
						resource.getContents().add((EObject) root);
						monitor.worked(1);

						doSave(resource, file, new SubProgressMonitor(monitor, 1));
						monitor.worked(1);

						monitor.worked(1);
					} finally {
						monitor.done();
					}
				}
			}, file, IWorkspace.AVOID_UPDATE, null);
			NewMenuUtil.openEditor(file);
		} catch (CoreException e) {
			// LOGGER.error(e, e);
			// MdfCore.openError(getShell(), e);
		}
	}
	
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

	@Override
	public boolean isEnabled() {
		if (!getSelection().isEmpty()) {
			Object obj = getSelection().getFirstElement();
			if (obj instanceof IOfsModelPackage) {
				if (!((IOfsModelPackage) obj).isEmpty()) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
		return super.isEnabled();
	}
	
	/**
	 * Returns the configured selection. If no selection has been configured using {@link #setSelection(IStructuredSelection)},
	 * the currently selected element of the active workbench is returned.
	 * @return the configured selection
	 */
	protected IStructuredSelection getSelection() {
		if (selection == null) {
			return evaluateCurrentSelection();
		}
		return selection;
	}

	/**
	 * @return
	 */
	private IStructuredSelection evaluateCurrentSelection() {
		IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection= window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection) selection;
			}
		}
		return StructuredSelection.EMPTY;
	}
	
	/**
	 * @param selection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected IPath getSelectionFullPath() {
		Iterator it = getSelection().iterator();
		if (it.hasNext()) {
			Object obj = it.next();
			IResource selectedResource = null;
			if (obj instanceof IResource) {
				selectedResource = (IResource) obj;
			} else if (obj instanceof IAdaptable) {
				selectedResource = (IResource) ((IAdaptable) obj)
						.getAdapter(IResource.class);
			}
			if (selectedResource != null) {
				if(!(selectedResource instanceof IFolder &&
					OfsCore.isOfsModelPackage((IFolder) selectedResource))) {
					selectedResource = selectedResource.getProject().getFolder("menu");
				}
			}
			if (selectedResource != null) {
				return selectedResource.getFullPath();
			}
		}
		return null;
	}

}
