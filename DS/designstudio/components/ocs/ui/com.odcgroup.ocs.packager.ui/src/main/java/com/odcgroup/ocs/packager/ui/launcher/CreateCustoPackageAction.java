package com.odcgroup.ocs.packager.ui.launcher;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.packager.launcher.PackagerLauncherHelper;
import com.odcgroup.ocs.packager.launcher.PackagerStreamListener;
import com.odcgroup.ocs.packager.launcher.UnableToOpenExplorerException;
import com.odcgroup.ocs.packager.launcher.UnableToStartPackagerException;
import com.odcgroup.ocs.packager.ui.OcsPackagerUICore;
import com.odcgroup.workbench.ui.OfsUICore;

public class CreateCustoPackageAction extends Action implements IObjectActionDelegate {

	private static final int EXPECTED_NB_LINES_TO_SUCCESS = 12300;

	private static Logger logger = LoggerFactory.getLogger(CreateCustoPackageAction.class);

	private IProject packagerProject;
	
	public CreateCustoPackageAction() {
		setText("Create Custo Package");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				OcsPackagerUICore.PLUGIN_ID, "/icons/packager_action.png"));
	}

	public void selectionChanged(IAction action, ISelection selection) {
    	action.setEnabled(false);
    	
    	if (selection == null) {
            return;
        }

        if (!(selection instanceof IStructuredSelection)) {
            return;
        }

        IStructuredSelection structuredSelection = (IStructuredSelection) selection;

        packagerProject = (IProject)structuredSelection.getFirstElement();
        
        if (packagerProject == null) {
        	return;
        }
        
		action.setEnabled(true);
	}
	
	public void run() {
		IWorkbenchWindow window = OcsPackagerUICore.getDefault().getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				packagerProject = (IProject)((IStructuredSelection)selection).getFirstElement();
				createCustoPackage();
			}
		}
	}

	public void run(IAction action) {
		createCustoPackage();
	}

	/**
	 * 
	 */
	private void createCustoPackage() {
		if (!MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),  
				"Custo Packager Launch confirmation", 
				"You are about to build a Custo Package. This process may take several minutes. Click OK to proceed, or Cancel to abort.")) {
			return;
		}
		
		Job job = new WorkspaceJob("Generating Custo Package") {
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				monitor.beginTask("Running Custo Package generation (this may take a few minutes)", 100);
				
				final ILaunch launch;
				try {
					launch = PackagerLauncherHelper.getHelper().launchPackager(packagerProject);
					
					final PackagerStreamListener listener = new PackagerStreamListener(monitor, EXPECTED_NB_LINES_TO_SUCCESS);
					if (launch.getProcesses().length >= 1) {
						launch.getProcesses()[0].getStreamsProxy().getOutputStreamMonitor().addListener(listener);
						launch.getProcesses()[0].getStreamsProxy().getErrorStreamMonitor().addListener(listener);
					}
					
					// Wait for the end of the launch or a cancel action
					while (!launch.isTerminated()) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							break;
						}
						if (monitor.isCanceled()) {
							break;
						}
					}
					
					// Terminate the launch if not terminated
					if (!launch.isTerminated()) {
						try {
							launch.terminate();
						} catch (DebugException e) {
							logger.error("Unable to terminate the Custo Package Launch", e);
						}
					} else {
						// Open the zip file in the windows explorer if successful
						if (listener.isBuildSuccessful()) {
							if (listener.getZipPackageGenerated() != null) {
								try {
									PackagerLauncherHelper.getHelper().openGeneratedCustoPackage(listener.getZipPackageGenerated());
								} catch (final UnableToOpenExplorerException e) {
									openError("Custo Packager Generation Problem", 
											e.getMessage() + "\n\nCheck the Console for more details...");
								}
							} else {
								openError("Custo Packager Generation Problem", 
										"Unable to find the packager zip filename from the exection log." +
								"\n\nCheck the target folder of the packager project");
							}
						} else {
							openError("Custo Packager Generation Problem", 
							"The package couldn't be generated. Check the Console for more details...");
						}
					}
				} catch (UnableToStartPackagerException e) {
					openError("Cannot launch the packager", e.getMessage());
				}

				monitor.done();
				return Status.OK_STATUS;
			}
		};

		job.setPriority(Job.INTERACTIVE);
		job.setUser(true);
		job.schedule();
	}
	
	private void openError(final String title, final String message) {
		logger.error(title + ": " + message);
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), 
						title, message);
			}
		});
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
	
}