package com.zealcore.se.ui.jobs;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.IProgressService;

public class LAActionJobRunner {

    private static IProgressService service = PlatformUI.getWorkbench()
    .getProgressService();

    private static Shell shell = null;

    private static LAActionJobRunner instance = null;

    private LAActionJobRunner() {
        shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }

    public static LAActionJobRunner getInstance() {
        if (instance == null) {
            instance = new LAActionJobRunner();
        }
        return instance;
    }


    public void runSingleAction(final ILAActionJob actionJob,
            boolean showDialog,
            final ImageDescriptor image) {

        if (Display.getCurrent() == null)
            showDialog = false;

        final Job runJob = new LAActionJobWrapper(actionJob);
            if (showDialog) {
                service.showInDialog(shell, runJob);                
            }
            if (image != null) {
                runJob.setProperty(IProgressConstants.ICON_PROPERTY, image);
            }
            runJob.setRule(actionJob.getRule());
            runJob.schedule(actionJob.getDelay());
    }
}
