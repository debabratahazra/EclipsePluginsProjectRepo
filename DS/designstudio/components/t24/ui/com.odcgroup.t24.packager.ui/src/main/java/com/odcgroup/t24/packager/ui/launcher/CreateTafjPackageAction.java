package com.odcgroup.t24.packager.ui.launcher;

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

import com.odcgroup.packager.core.launcher.PackagerStreamListener;
import com.odcgroup.packager.core.launcher.UnableToOpenExplorerException;
import com.odcgroup.packager.core.launcher.UnableToStartPackagerException;
import com.odcgroup.t24.packager.launcher.T24TafjPackagerLauncherHelper;
import com.odcgroup.t24.packager.ui.T24PackagerUICore;
import com.odcgroup.workbench.ui.OfsUICore;

public class CreateTafjPackageAction  extends Action implements IObjectActionDelegate {

	private static final int EXPECTED_NB_LINES_TO_SUCCESS = 12300;

	private static Logger logger = LoggerFactory.getLogger(CreateTafjPackageAction.class);

	private IProject packagerProject;
	
	public CreateTafjPackageAction() {
		setText("Create TAFJ Package");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				T24PackagerUICore.PLUGIN_ID, "/icons/packager_action.png"));
	}

	public void run() {
		IWorkbenchWindow window = T24PackagerUICore.getDefault().getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				packagerProject = (IProject)((IStructuredSelection)selection).getFirstElement();
				createTAFJPackage();
			}
		}
	}

	@Override
	public void run(IAction action) {
		createTAFJPackage();
	}


	/**
	 * 
	 */
	private void createTAFJPackage() {
		if (!MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),  
				"TAFJ Packager Launch confirmation", 
				"You are about to build a TAFJ Package. This process may take several minutes. Click OK to proceed, or Cancel to abort.")) {
			return;
		}
		
		Job job = new WorkspaceJob("Generating TAFJ Package") {
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				monitor.beginTask("Running TAFJ Package generation (this may take a few minutes)", 100);
				
				final ILaunch launch;
				try {
					launch = T24TafjPackagerLauncherHelper.getHelper().launchPackager(packagerProject);
					
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
							logger.error("Unable to terminate the TAFJ Package Launch", e);
						}
					} else {
						// Open the zip file in the windows explorer if successful
						if (listener.isBuildSuccessful()) {
							if (listener.getZipPackageGenerated() != null) {
								try {
									T24TafjPackagerLauncherHelper.getHelper().openGeneratedTAFJPackage(listener.getZipPackageGenerated());
								} catch (final UnableToOpenExplorerException e) {
									openError("TAFJ Packager Generation Problem", 
											e.getMessage() + "\n\nCheck the Console for more details...");
								}
							} else {
								openError("TAFJ Packager Generation Problem", 
										"Unable to find the packager zip filename from the exection log." +
								"\n\nCheck the target folder of the packager project");
							}
						} else {
							openError("TAFJ Packager Generation Problem", 
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
	
	@Override
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
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

}