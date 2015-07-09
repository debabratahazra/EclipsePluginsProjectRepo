package com.odcgroup.workbench.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.MoveFilesAndFoldersOperation;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant;

import com.odcgroup.workbench.core.OfsCore;

public class OfsModelDropAdapterAssistant extends ResourceDropAdapterAssistant {

	private static final IResource[] NO_RESOURCES = new IResource[0];

	@Override
	public IStatus validateDrop(Object target, int aDropOperation,
			TransferData transferType) {

		IFolder resource = null;
		if (!(target instanceof IAdaptable) ||
				(resource = (IFolder) ((IAdaptable) target).getAdapter(IFolder.class))==null) {
			return WorkbenchNavigatorPlugin
					.createStatus(
							IStatus.INFO,
							0,
							WorkbenchNavigatorMessages.DropAdapter_targetMustBeResource,
							null);
		}
		if (!resource.isAccessible()) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_canNotDropIntoClosedProject,
							null);
		}
		IContainer destination = getActualTarget(resource);
		if (destination.getType() == IResource.ROOT) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_resourcesCanNotBeSiblings,
							null);
		}

		// only allow drop in OFS model folders
		if(! (destination instanceof IFolder && OfsCore.isOfsModelPackage((IFolder) destination))) {	
			return Status.CANCEL_STATUS;
		}
		
		String message = null;
		// drag within Eclipse?
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			IResource[] selectedResources = getSelectedResources();

			if (selectedResources.length == 0) {
				message = WorkbenchNavigatorMessages.DropAdapter_dropOperationErrorOther;
			} else {		
				// only allow drop if ALL selected files are model files for the target model folder 
				for(IResource res : selectedResources) {
					if(res instanceof IFile) {
						IFile file = (IFile) res;
						if(!file.getFileExtension().toLowerCase().equals(
								OfsCore.getFolderModelName((IFolder)destination))) {
							return Status.CANCEL_STATUS;
						}
					}
				}
				
				CopyFilesAndFoldersOperation operation;
				if (aDropOperation == DND.DROP_COPY) {
					operation = new CopyFilesAndFoldersOperation(getShell());
				} else {
					operation = new MoveFilesAndFoldersOperation(getShell());
				}
				message = operation.validateDestination(destination,
						selectedResources);
			}
		} // file import?
		else if (FileTransfer.getInstance().isSupportedType(transferType)) {
			String[] sourceNames = (String[]) FileTransfer.getInstance()
					.nativeToJava(transferType);
			if (sourceNames == null) {
				// source names will be null on Linux. Use empty names to do
				// destination validation.
				// Fixes bug 29778
				sourceNames = new String[0];
			}

			// only allow drop if ALL selected files are model files for the target model folder 
			for(String res : sourceNames) {
				if(!res.endsWith("." + OfsCore.getFolderModelName((IFolder)destination))) {
					return Status.CANCEL_STATUS;
				}
			}

			CopyFilesAndFoldersOperation copyOperation = new CopyFilesAndFoldersOperation(
					getShell());
			message = copyOperation.validateImportDestination(destination,
					sourceNames);
		}
		if (message != null) {
			return WorkbenchNavigatorPlugin.createErrorStatus(0, message, null);
		}
		return Status.OK_STATUS;
	}

	/**
	 * Returns the actual target of the drop, given the resource under the
	 * mouse. If the mouse target is a file, then the drop actually occurs in
	 * its parent. If the drop location is before or after the mouse target and
	 * feedback is enabled, the target is also the parent.
	 */
	private IContainer getActualTarget(IResource mouseTarget) {

		/* if cursor is on a file, return the parent */
		if (mouseTarget.getType() == IResource.FILE) {
			return mouseTarget.getParent();
		}
		/* otherwise the mouseTarget is the real target */
		return (IContainer) mouseTarget;
	}

	/**
	 * Returns the resource selection from the LocalSelectionTransfer.
	 * 
	 * @return the resource selection from the LocalSelectionTransfer
	 */
	private IResource[] getSelectedResources() {

		ISelection selection = LocalSelectionTransfer.getTransfer()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			return getSelectedResources((IStructuredSelection)selection);
		} 
		return NO_RESOURCES;
	}

	/**
	 * Returns the resource selection from the LocalSelectionTransfer.
	 * 
	 * @return the resource selection from the LocalSelectionTransfer
	 */
	private IResource[] getSelectedResources(IStructuredSelection selection) {
		ArrayList<Object> selectedResources = new ArrayList<Object>();

		for (Iterator i = selection.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof IResource) {
				selectedResources.add(o);
			} else if (o instanceof IAdaptable) {
				IAdaptable a = (IAdaptable) o;
				IResource r = (IResource) a.getAdapter(IResource.class);
				if (r != null) {
					selectedResources.add(r);
				}
			}
		}
		return (IResource[]) selectedResources
				.toArray(new IResource[selectedResources.size()]);
	}
}
