package com.zealcore.se.ui.wizards;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.SystemExplorerNature;
import com.zealcore.se.ui.core.CaseFileManager;

public class NewZealcoreProjectWizard extends Wizard implements INewWizard,
        IExecutableExtension {

    private static final String DEFAULT_LOGSET_NAME = "logset";

    private static final String DESCRIPTION = "Project name must be specified";

    private static final String NEW_ZEALCORE_PROJECT = "New Log Analyzer Project";

    private WizardNewProjectCreationPage projectCreationPage;

    private IConfigurationElement cfig;

    public NewZealcoreProjectWizard() {
        setWindowTitle(NewZealcoreProjectWizard.NEW_ZEALCORE_PROJECT);

    }

    @Override
    public boolean performFinish() {
        final IProject handle = projectCreationPage.getProjectHandle();
        return createProject(handle);
    }

    public boolean createProject(final IProject handle) {
        try {
            URI location = null;
            if (!projectCreationPage.useDefaults()) {
                location = projectCreationPage.getLocationURI();
            }
            final IProjectDescription description = ResourcesPlugin
                    .getWorkspace().newProjectDescription(handle.getName());
            description.setLocationURI(location);
            handle.create(description, null);
            handle.open(null);
            SystemExplorerNature.applyTo(handle);
            BasicNewProjectResourceWizard.updatePerspective(cfig);
            final ICaseFile file = CaseFileManager.createCaseFile(handle, null);
            file.addLog(NewZealcoreProjectWizard.DEFAULT_LOGSET_NAME);
            return true;
        } catch (final CoreException e) {
            SeUiPlugin.logError(e);
        } catch (final IllegalStateException e) {
            SeUiPlugin.logError(e);
        }

        return false;
    }

    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {
    // Dont need
    }

    @Override
    public void addPages() {
        projectCreationPage = new WizardNewProjectCreationPage(
                NewZealcoreProjectWizard.NEW_ZEALCORE_PROJECT);
        projectCreationPage
                .setTitle(NewZealcoreProjectWizard.NEW_ZEALCORE_PROJECT);

        final ImageDescriptor desc = IconManager
                .getImageDescriptor(IconManager.ZEALCORE_WIZARD_ICON);
        projectCreationPage.setImageDescriptor(desc);
        projectCreationPage
                .setDescription(NewZealcoreProjectWizard.DESCRIPTION);
        addPage(projectCreationPage);
    }

    /*
     * Stores the configuration element for the wizard. The config element will
     * be used in <code>performFinish</code> to set the result perspective.
     */
    public void setInitializationData(final IConfigurationElement cfig,
            final String propertyName, final Object data) {

        this.cfig = cfig;

    }
}
