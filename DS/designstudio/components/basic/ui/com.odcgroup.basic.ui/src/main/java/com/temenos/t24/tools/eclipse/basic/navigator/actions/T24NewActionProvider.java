package com.temenos.t24.tools.eclipse.basic.navigator.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;


@SuppressWarnings("restriction")
public class T24NewActionProvider extends CommonActionProvider {

	private static final String NEW_MENU_NAME = "common.new.menu";//$NON-NLS-1$

	private NewT24RoutineAction routineAction;
	
	private NewT24RoutineTemplateAction templateAction;
	
	private NewT24DataItemAction dataItemAction;
	
	private FolderAction folderAction;
	
	private boolean contribute = false;

	public void init(ICommonActionExtensionSite anExtensionSite) {

		if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			routineAction = new NewT24RoutineAction();
			templateAction = new NewT24RoutineTemplateAction();
			dataItemAction = new NewT24DataItemAction();
			folderAction = new FolderAction();
			contribute = true;
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		IMenuManager submenu = new MenuManager(WorkbenchNavigatorMessages.NewActionProvider_NewMenu_label, NEW_MENU_NAME);
		checkProjectNature(getContext());
		if(!contribute || getContext().getSelection().isEmpty()) {
			return;
		}
		
		submenu.add(routineAction);
		submenu.add(templateAction);
		submenu.add(dataItemAction);
		submenu.add(folderAction);
		
		// append the submenu after the GROUP_NEW group.
		menu.insertAfter(ICommonMenuConstants.GROUP_NEW, submenu);
	}
	
    private void checkProjectNature(ActionContext context) {
        contribute = false;
        IProject project = null;
        if (context != null) {
            IStructuredSelection selection = (IStructuredSelection) context.getSelection();
            if (selection.size() == 1 && selection.getFirstElement() instanceof IProject) {
                project = (IProject) selection.getFirstElement();
            } else if (selection.size() == 1 && selection.getFirstElement() instanceof IFolder) {
                IFolder folder = (IFolder) selection.getFirstElement();
                project = (IProject) folder.getProject();
            } else if (selection.size() == 1 && selection.getFirstElement() instanceof IPackageFragmentRoot) {
                IPackageFragmentRoot fragmentRoot = (IPackageFragmentRoot) selection.getFirstElement();
                if (fragmentRoot.getJavaProject() instanceof IJavaProject) {
                    contribute = true;
                }
            } else if (selection.size() == 1 && selection.getFirstElement() instanceof IPackageFragment) {
                IPackageFragment fragment = (IPackageFragment) selection.getFirstElement();
                if (fragment.getJavaProject() instanceof IJavaProject) {
                    contribute = true;
                }
            }
            try {
                if (project != null && project.hasNature(JavaCore.NATURE_ID)) {
                    contribute = true;
                }
            } catch (CoreException e) {
                contribute = false;
            }
        }
    }

}
