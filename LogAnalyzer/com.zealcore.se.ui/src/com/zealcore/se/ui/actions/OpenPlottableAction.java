package com.zealcore.se.ui.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.Messages;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.dialogs.OpenPlotDialog;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.views.Plot2;
import com.zealcore.se.ui.views.PlottableViewer;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;
import com.zealcore.se.ui.views.PlotSearchQuery;

public class OpenPlottableAction extends AbstractObjectDelegate {

	public OpenPlottableAction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void runSafe(final IAction action) {

        if (Logset.isDisabled()) {
            ErrorDialog.openError(new Shell(), Messages.LICENSE_ERROR,
                    Messages.PLOT_ERROR,
                    new Status(IStatus.ERROR, SeUiPlugin.PLUGIN_ID,
                            IStatus.ERROR, Messages.LICENSE_EXCEPTION + ".",
                            Logset.getException()));

            return;
        }
	    
		if (guardFail()) {
			return;
		}
		if (!(getSelection() instanceof IStructuredSelection)) {
			return;
		}
		final Object first = ((IStructuredSelection) getSelection())
				.getFirstElement();
		if (!(first instanceof ITimeCluster)) {
			return;

		}
		final ITimeCluster cluster = (ITimeCluster) first;
		final OpenPlotDialog dlg = new OpenPlotDialog(getPart().getSite()
				.getShell());
		if (dlg.open() == Window.OK) {
			ILogSessionWrapper uiLogset = cluster.getParent();
			Logset log = Logset.valueOf(uiLogset.getId());
			long syncKey = log.getLock();
			if (syncKey == -1) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageDialog
								.openWarning(
										Display.getDefault().getActiveShell(),
										"Warning",
										"Another Log Analyzer activity is currently running.To continue preempt the currently running task or wait for running task to finish its execution");
					}
				});
			} else {
				PlotSearchQuery plotSearchQuery = new PlotSearchQuery(dlg
						.getAdapter(), dlg.getPlotType());
				Job job = new PlotDataReader("Getting plot data",
						plotSearchQuery, log, syncKey);
				try {
					openPlotViewer(uiLogset, plotSearchQuery, dlg.getPlotType());
					job.schedule();
				} catch (final PartInitException e) {
					SeUiPlugin.logError(e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void openPlotViewer(final ILogSessionWrapper uiLogset,
			final PlotSearchQuery plotSearchQuery, final PlotType type)
			throws PartInitException {
		IWorkbenchPage activePage = SeUiPlugin.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewPart part = activePage.showView(PlottableViewer.VIEW_ID);
		if (part instanceof PlottableViewer) {
			final PlottableViewer plottViewer = (PlottableViewer) part;
			plotSearchQuery.setPlottViewer(plottViewer);
			plottViewer.setPlot(new Plot2(uiLogset, plotSearchQuery, type));
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		if (this.guardFail()) {
			return;
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof ITimeCluster) {
			final ITimeCluster logSession = (ITimeCluster) struct
					.getFirstElement();
			ILogSessionWrapper lWarapper = logSession.getParent();

			Logset logset = Logset.valueOf(lWarapper.getId());
			if (logset.isLocked()) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}
	}
}
