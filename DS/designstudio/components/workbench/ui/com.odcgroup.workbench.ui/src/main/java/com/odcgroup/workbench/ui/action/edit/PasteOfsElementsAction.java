package com.odcgroup.workbench.ui.action.edit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.statushandlers.StatusManager;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.dnd.OfsElementTransfer;

/**
 * This is the base class to paste OFS element to the clipboard.
 * 
 * @author atr
 */
public abstract class PasteOfsElementsAction extends SelectionDispatchAction {

	/** */
	private Clipboard clipboard;

	/**
	 * @param ofsPackage
	 * @return String
	 */
	private String getModelType(IOfsModelPackage ofsPackage) {
		return ofsPackage.getModelFolder().getName();
	}
	
	/**
	 * @param srcOfsProject
	 * @param dstOfsProject
	 * @param ofsModelResource
	 * @return IOfsModelResourcePaster
	 */
	protected abstract IOfsModelResourcePaster getOfsModelResourcePaster(Shell shell, IOfsProject srcOfsProject, IOfsProject dstOfsProject, IOfsModelResource ofsModelResource);
	
	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#run(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void run(IStructuredSelection selection) {

		// read clipboard content
		final IOfsElement[][] clipboardData = new IOfsElement[1][];
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				// clipboard must have resources or files
				OfsElementTransfer ofsTransfer = OfsElementTransfer.getInstance();
				clipboardData[0] = (IOfsElement[]) clipboard.getContents(ofsTransfer);
			}
		});
		IOfsElement[] ofsElements = clipboardData[0];
		if (ofsElements == null) {
			// nothing to paste, should never happen!
			return;
		}

		final IOfsElement targetContainer = getSelectedOfsElements().get(0);

		for (IOfsElement ofsElement : ofsElements) {

			if (ofsElement instanceof IOfsModelResource) {

				// compute target destination
				final IOfsModelResource ofsModel = (IOfsModelResource) ofsElement;
				IPath destination = null;
				IFolder destinationFolder = null;
				if(targetContainer.getResource()!=null) {
					destination = targetContainer.getResource().getFullPath().append(ofsModel.getName());
					if(targetContainer.getResource() instanceof IFolder) {
						destinationFolder = (IFolder) targetContainer.getResource();
					}
				} else if(targetContainer instanceof IOfsModelPackage) {
					IOfsModelPackage modelPackage = (IOfsModelPackage) targetContainer;
					destinationFolder = modelPackage.getOfsProject().getProject().getFolder(modelPackage.getModelFolder().getName()).getFolder(modelPackage.getURI().path());
					destination = destinationFolder.getFullPath().append(ofsModel.getName());
				} else {
					return;
				}
				
				// generate a new name automatically
				destination = getNameFor(destination);
				if (destination == null) {
					// cancel Operation
					return;
				}
				
				final IPath destinationPath = destination;
				final IFolder finalFolder = destinationFolder;
				final IOfsProject srcOfsProject = ofsElement.getOfsProject();
					
				IRunnableWithProgress op = new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) {
						URI targetURI = null;
						IOfsModelResource tartgetOfsResource = null;
						try {
							
							// copy the resource to the destination, can raise OperationCanceledException
							if(!finalFolder.exists()) OfsCore.createFolder(finalFolder);
							IFile outputFile = finalFolder.getFile(destinationPath.lastSegment());
							outputFile.create(ofsModel.getContents(), false, null);
							
							// reload the model to do some changes
							IOfsProject targetOfsProject = targetContainer.getOfsProject();
							IPath targetPath = destinationPath.removeFirstSegments(2).makeAbsolute();
							targetURI = ModelURIConverter.createModelURI(targetPath.toString());
							tartgetOfsResource = targetOfsProject.getOfsModelResource(targetURI);
							IOfsModelResourcePaster paster = getOfsModelResourcePaster(
									getSite().getShell(), srcOfsProject,targetOfsProject, tartgetOfsResource);
							paster.paste();
						} catch (OperationCanceledException ex) {
							// nothing to do
							return;
						} catch (CoreException ex) { 
							String msg = "Error pasting the model to the destination ["+destinationPath+"]"; //$NON-NLS-1$ //$NON-NLS-2$
							IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, IStatus.ERROR, msg, ex);
							StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
						} catch (ModelNotFoundException ex) {
							String msg = "Model not found for URI ["+targetURI.toString()+"]"; //$NON-NLS-1$ //$NON-NLS-2$
							IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, IStatus.ERROR, msg, ex);
							StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
						} catch (IOException ex) {
							String msg = "Error accessing the model file " + tartgetOfsResource.getName(); //$NON-NLS-1$
							IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, IStatus.ERROR, msg, ex);
							StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
						} catch (InvalidMetamodelVersionException ex) {
							String msg = "Model file '" + tartgetOfsResource.getName() + "' has an invalid metamodel version!"; //$NON-NLS-1$ //$NON-NLS-2$
							IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, IStatus.ERROR, msg, ex);
							StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
						} finally {
						}
					}
				};

				try {
					PlatformUI.getWorkbench().getProgressService().run(true, true, op);
				} catch (InterruptedException ex) {
					return;
				} catch (InvocationTargetException ex) {
					IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, IStatus.ERROR, "Unexpected error while pasting file", ex.getTargetException());
					StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
				}
					
			}

		}

	}

	/**
	 * Checks if the given OFS element can be part of the selection. This
	 * default implementation returns {@code true}
	 * 
	 * @param ofsElement
	 *            the OFS Element to check
	 * 
	 * @return {@code true} if the given OFS element can be part of the
	 *         selection
	 */
	@Override
	protected boolean acceptOfsElement(IOfsElement ofsElement) {
		return true;
	}

	/**
	 * The default implementation of this method accepts to paste the OFS
	 * elements only if the current selection it compatible with what is on the
	 * clipboard.
	 * 
	 * @param selection
	 *            the current selection
	 * @return {@code true} if all selected OFS element are homogeneous and have
	 *         the same parent
	 */
	protected boolean updateSelection(IStructuredSelection selection) {

		if (selection.isEmpty()) {
			return false;
		}

		List<IOfsElement> selectedElements = getSelectedOfsElements();
		if (selectedElements.size() == 0) {
			return false;
		}

		// selection must be homegeneous
		int selectionCount = selection.size();
		if (selectedElements.size() != selectionCount) {
			return false;
		}

		// read clipboard content
		final IOfsElement[][] clipboardData = new IOfsElement[1][];
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				// clipboard must have resources or files
				OfsElementTransfer ofsTransfer = OfsElementTransfer.getInstance();
				clipboardData[0] = (IOfsElement[]) clipboard.getContents(ofsTransfer);
			}
		});
		IOfsElement[] ofsElements = clipboardData[0];
		
		if (ofsElements == null || ofsElements.length == 0) {
			// nothing to paste
			return false;
		}

		// OFS element can only be pasted in a OFS package
		IOfsElement target = selectedElements.get(0);
		boolean isModelPackage = target != null && target instanceof IOfsModelPackage;
		if (!isModelPackage) {
			return false;
		}

		// The OFS Package must accept all ofs element
		String modelType = getModelType((IOfsModelPackage) target);
		for (IOfsElement element : ofsElements) {
			if (!modelType.equals(element.getURI().fileExtension())) {
				return false;
			}
		}

		return true;

	}

	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void selectionChanged(IStructuredSelection selection) {
		setEnabled(updateSelection(selection));
	}

	/**
	 * COnstructor
	 * @param site the site owning this action
	 * @param clipboard
	 */
	public PasteOfsElementsAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site);

		// overrides the standard paste action
		setText("Paste"); //$NON-NLS-1$
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_PASTE);

		this.clipboard = clipboard;
	}

	/**
	 * @param originalName
	 * @param workspace
	 * @return IPath
	 */
	static IPath getAutoNewNameFor(IPath originalName) {
		int counter = 1;
		String resourceName = originalName.lastSegment();
		IPath leadupSegment = originalName.removeLastSegments(1);
		while (true) {
			String nameSegment;
			if (counter > 1) {
				nameSegment = "Copy_"+counter+"_Of"+resourceName; //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				nameSegment = "CopyOf"+resourceName; //$NON-NLS-1$
			}
			IPath pathToTry = leadupSegment.append(nameSegment);
			if (!ResourcesPlugin.getWorkspace().getRoot().exists(pathToTry)) {
				return pathToTry;
			}
			counter++;
		}
	}	
	
	/**
	 * @param originalName
	 * @param workspace
	 * @return IPath
	 */
	private IPath getNameFor(final IPath originalName) {
		final IPath prefix = originalName.removeLastSegments(1);
		final String modelName = originalName.lastSegment();
		final String returnValue[] = { "" }; //$NON-NLS-1$

		if(!ResourcesPlugin.getWorkspace().getRoot().getFile(originalName).exists()) {
			return originalName;
		} else {
			getSite().getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					IInputValidator validator = new IInputValidator() {
						public String isValid(String string) {
							if (modelName.equals(string)) {
								return "You must use a different name"; //$NON-NLS-1$
							}
							IStatus status = ResourcesPlugin.getWorkspace().validateName(string, IResource.FILE);
							if (!status.isOK()) {
								return status.getMessage();
							}
							if (ResourcesPlugin.getWorkspace().getRoot().exists(prefix.append(string))) {
								return "A model with that name already exists"; //$NON-NLS-1$
							}
							return null;
						}
					};
	
					InputDialog dialog = new InputDialog(
							getSite().getShell(),
							"Name Conflict", //$NON-NLS-1$
							"Enter a new name for '"+modelName+"'", //$NON-NLS-1$ //$NON-NLS-2$
							getAutoNewNameFor(originalName).lastSegment(),
							validator);
					dialog.setBlockOnOpen(true);
					dialog.open();
					if (dialog.getReturnCode() == Window.CANCEL) {
						returnValue[0] = null;
					} else {
						returnValue[0] = dialog.getValue();
					}
				}
			});
		
			if (returnValue[0] == null) {
				return null;
			}
			return prefix.append(returnValue[0]);
		}
	}
	

}
