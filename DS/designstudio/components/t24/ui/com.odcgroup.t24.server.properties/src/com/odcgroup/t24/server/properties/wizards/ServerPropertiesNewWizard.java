package com.odcgroup.t24.server.properties.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.odcgroup.t24.server.external.ui.AddT24ExternalServerAction;

@SuppressWarnings("restriction")
public class ServerPropertiesNewWizard extends BasicNewProjectResourceWizard
		implements INewWizard {

	private ServerPropertiesConnectionTypePage page;
	private ServerPropertiesCreateProjPage mainPage;
	private IProject newProject;

	public ServerPropertiesNewWizard() {
		super();
		setWindowTitle("New Server Project");
	}
	
	@Override
	public boolean performFinish() {
		createNewProject();

		if (newProject == null) {
			return false;
		}
		
		IWorkingSet[] workingSets = mainPage.getSelectedWorkingSets();
		PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(newProject,
				workingSets);
        
		updatePerspective();
		//selectAndReveal(newProject);
		try {
			newProject.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		new AddT24ExternalServerAction().createProject(this);
		ServerPropertiesConnectionTypePage.currentSelected = null;
		return true;
	}
	
	private IProject createNewProject(){
		if (newProject != null) {
			return newProject;
		}

		// get a project handle
		final IProject newProjectHandle = mainPage.getProjectHandle();

		// get a project descriptor
		URI location = null;
		if (!mainPage.useDefaults()) {
			location = mainPage.getLocationURI();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace
				.newProjectDescription(newProjectHandle.getName());
		description.setLocationURI(location);
		
		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				CreateProjectOperation op = new CreateProjectOperation(
						description, ResourceMessages.NewProject_windowTitle);
				try {
					op.execute(monitor, WorkspaceUndoUtil
						.getUIInfoAdapter(getShell()));
				} catch (ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation operation
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof ExecutionException
					&& t.getCause() instanceof CoreException) {
				CoreException cause = (CoreException) t.getCause();
				StatusAdapter status;
				if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
					status = new StatusAdapter(
							StatusUtil
									.newStatus(
											IStatus.WARNING,
											NLS
													.bind(
															ResourceMessages.NewProject_caseVariantExistsError,
															newProjectHandle
																	.getName()),
											cause));
				} else {
					status = new StatusAdapter(StatusUtil.newStatus(cause
							.getStatus().getSeverity(),
							ResourceMessages.NewProject_errorMessage, cause));
				}
				status.setProperty(StatusAdapter.TITLE_PROPERTY,
						ResourceMessages.NewProject_errorMessage);
				StatusManager.getManager().handle(status, StatusManager.BLOCK);
			} else {
				StatusAdapter status = new StatusAdapter(new Status(
						IStatus.WARNING, IDEWorkbenchPlugin.IDE_WORKBENCH, 0,
						NLS.bind(ResourceMessages.NewProject_internalError, t
								.getMessage()), t));
				status.setProperty(StatusAdapter.TITLE_PROPERTY,
						ResourceMessages.NewProject_errorMessage);
				StatusManager.getManager().handle(status,
						StatusManager.LOG | StatusManager.BLOCK);
			}
			return null;
		}
		newProject = newProjectHandle;
		return newProject;
		
	}
	
	@Override
	public void addPages() {
		mainPage = new ServerPropertiesCreateProjPage("Create a Server Properties project");
		addPage(mainPage);
		page = new ServerPropertiesConnectionTypePage(selection);
		addPage(page);
	}
	
	public IProject getNewProject() {
		return newProject;
	}

}
