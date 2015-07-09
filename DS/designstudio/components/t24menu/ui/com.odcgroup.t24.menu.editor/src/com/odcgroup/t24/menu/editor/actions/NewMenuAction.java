package com.odcgroup.t24.menu.editor.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.t24.menu.editor.wizard.MenuWizard;
import com.odcgroup.t24.menu.menu.presentation.MenuEditorPlugin;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewMenuAction  extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {
	
	public NewMenuAction() {
		setText("New Menu...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(MenuEditorPlugin.PLUGIN_ID, "icons/full/obj16/MenuModelFile.gif"));
	}

	@Override
	public void run(IAction action) {
		super.run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
	

	@Override
	public void dispose() {
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		
	}

	@Override
	protected INewWizard createWizard() throws CoreException {
		return new MenuWizard();
	}
}
///**
// * @author pkk
// */
//public class NewMenuAction extends Action {
//	
//	private IStructuredSelection selection;
//	
//	/**
//	 * 
//	 */
//	public NewMenuAction() {
//		setText("New Menu...");
//		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
//				MenuEditorPlugin.PLUGIN_ID, "icons/full/obj16/MenuModelFile.gif"));
//	}
//
//	@Override
//	public void run() {
//		try {
//			IWorkspace workspace = ResourcesPlugin.getWorkspace();
//			final IFile file = MenuUtils.createNewFile("Menu.menu", getSelectionFullPath());
//
//			workspace.run(new IWorkspaceRunnable() {
//
//				public void run(IProgressMonitor monitor) throws CoreException {
//					try {
//						monitor.beginTask("Creating model", 2);
//						IOfsProject ofsProject = OfsCore.getOfsProject(file.getProject());
//						IOfsModelResource modelResource = ofsProject.getModelResourceSet()
//								.createOfsModelResource(file, IOfsProject.SCOPE_PROJECT);
//						Resource resource = ofsProject.getModelResourceSet().getResource(modelResource.getURI(), false);
//						MenuRoot root = MenuUtils.createInitialModel();
//						root.getRootItem().setName("RootMenu");
//						resource.getContents().add((EObject) root);
//						monitor.worked(1);
//
//						doSave(resource, file, new SubProgressMonitor(monitor, 1));
//						monitor.worked(1);
//					} finally {
//						monitor.done();
//					}
//				}
//			}, file, IWorkspace.AVOID_UPDATE, null);
//			MenuUtils.openEditor(file);
//		} catch (CoreException e) {
//			// LOGGER.error(e, e);
//			// MdfCore.openError(getShell(), e);
//		}
//	}
//	
//	public static void doSave(Resource resource, IFile file, IProgressMonitor monitor) throws CoreException {
//		monitor.beginTask("Saving model", 2);
//
//		try {
//			final ByteArrayOutputStream os = new ByteArrayOutputStream();
//			resource.save(os, Collections.EMPTY_MAP);
//			monitor.worked(1);
//
//			if (file.exists()) {
//				file.setContents(new ByteArrayInputStream(os.toByteArray()),
//						false, true, new SubProgressMonitor(monitor, 1));
//				monitor.worked(1);
//			} else {
//				file.create(new ByteArrayInputStream(os.toByteArray()), false,
//						new SubProgressMonitor(monitor, 1));
//				monitor.worked(1);
//			}
//		} catch (IOException e) {
//		} finally {
//			monitor.done();
//		}
//	}
//
//	@Override
//	public boolean isEnabled() {
//		if (!getSelection().isEmpty()) {
//			Object obj = getSelection().getFirstElement();
//			if (obj instanceof IOfsModelPackage) {
////				if (!((IOfsModelPackage) obj).isEmpty()) {
////					setEnabled(false);
////				} else {
////					setEnabled(true);
////				}
//			}
//		}
//		return super.isEnabled();
//	}
//	
//	/**
//	 * Returns the configured selection. If no selection has been configured using {@link #setSelection(IStructuredSelection)},
//	 * the currently selected element of the active workbench is returned.
//	 * @return the configured selection
//	 */
//	protected IStructuredSelection getSelection() {
//		if (selection == null) {
//			return evaluateCurrentSelection();
//		}
//		return selection;
//	}
//
//	/**
//	 * @return
//	 */
//	private IStructuredSelection evaluateCurrentSelection() {
//		IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		if (window != null) {
//			ISelection selection= window.getSelectionService().getSelection();
//			if (selection instanceof IStructuredSelection) {
//				return (IStructuredSelection) selection;
//			}
//		}
//		return StructuredSelection.EMPTY;
//	}
//	
//	/**
//	 * @param selection
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	protected IPath getSelectionFullPath() {
//		Iterator it = getSelection().iterator();
//		if (it.hasNext()) {
//			Object obj = it.next();
//			IResource selectedResource = null;
//			if (obj instanceof IResource) {
//				selectedResource = (IResource) obj;
//			} else if (obj instanceof IAdaptable) {
//				selectedResource = (IResource) ((IAdaptable) obj)
//						.getAdapter(IResource.class);
//			}
//			if (selectedResource != null) {
//				if(!(selectedResource instanceof IFolder &&
//					OfsCore.isOfsModelPackage((IFolder) selectedResource))) {
//					selectedResource = selectedResource.getProject().getFolder("menu");
//				}
//			}
//			if (selectedResource != null) {
//				return selectedResource.getFullPath();
//			}
//		}
//		return null;
//	}
//
//}
