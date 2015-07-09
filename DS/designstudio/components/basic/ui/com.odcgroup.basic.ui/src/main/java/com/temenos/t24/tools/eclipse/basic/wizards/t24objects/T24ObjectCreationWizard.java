package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/** T24Object creation wizard */
public class T24ObjectCreationWizard extends Wizard implements INewWizard {

    /** Page where T24Object details are defined */
    private T24ObjectDefinitionPage t24ObjectDefinitionPage = null;
    /** Page where T24Methods for T24objects are added */
    T24MethodDefinitionPage methodDefinitionPage = null;
    private static IStructuredSelection selection = null;
    private static String projectFullPath = null;

    public T24ObjectCreationWizard() {
        super();
        t24ObjectDefinitionPage = new T24ObjectDefinitionPage();
        methodDefinitionPage = new T24MethodDefinitionPage();
    }

    public void init(IWorkbench workbench, IStructuredSelection _selection) {
        selection = _selection;
        getSelectedProject();
    }

    public void addPages() {
        setWindowTitle("T24Object Creation Wizard");
        addPage(t24ObjectDefinitionPage);
        t24ObjectDefinitionPage.init(selection);
        addPage(methodDefinitionPage);
        methodDefinitionPage.setT24Object(t24ObjectDefinitionPage.getT24Object());
    }

    public boolean canFinish() {
        if ((this.getContainer().getCurrentPage() == methodDefinitionPage && methodDefinitionPage.isPageComplete())) {
            if (t24ObjectDefinitionPage.getT24Object().isFunctionSelected()
                    || t24ObjectDefinitionPage.getT24Object().getT24Methods().size() >= 1) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        // TODO Auto-generated method stub
    }

    public boolean performCancel() {
        // // TODO Auto-generated method stub
        return true;
    }

    public boolean performFinish() {
        T24ObjectCodeCreator codeCreator = T24ObjectCodeCreator.getInstance();
        T24Object t24Object = methodDefinitionPage.getT24Object();
        codeCreator.generateCode(t24Object, projectFullPath);
        return true;
    }

    /**
     * Gets the project path where the T24 Object related files are to be
     * created. If the selected is not an IResource, then default project or
     * temporary project is chosen.
     */
    private static void getSelectedProject() {
        projectFullPath = EclipseUtil.getLocalDefaultProject();
        if (projectFullPath == null || projectFullPath.equals("")) {
            projectFullPath = EclipseUtil.getTemporaryProject().toOSString();
        }
        if (selection != null && selection instanceof IStructuredSelection) {
            Object objectSelected = (Object) ((IStructuredSelection) selection).getFirstElement();
            if (objectSelected instanceof IResource) {
                IResource resource = ((IResource) objectSelected);
                projectFullPath = resource.getProject().getLocation().toOSString();
            }
        }
    }
}
