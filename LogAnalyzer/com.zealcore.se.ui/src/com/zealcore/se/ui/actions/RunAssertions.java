/*
 * 
 */
package com.zealcore.se.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.progress.IProgressConstants;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.assertions.AssertionRegistry;
import com.zealcore.se.core.ifw.assertions.AssertionRunner;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionSet;
import com.zealcore.se.core.ifw.assertions.IAssertionSetResult;
import com.zealcore.se.core.services.IAssertionReportService;
import com.zealcore.se.core.services.IMementoService;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.ui.Messages;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.internal.DirectoryLogSession;
import com.zealcore.se.ui.search.NamedItemContentProvider;
import com.zealcore.se.ui.search.NamedItemLabelProvider;
import com.zealcore.se.ui.views.AssertionResultView;
import com.zealcore.se.ui.views.SystemNavigator;

public class RunAssertions extends AbstractObjectDelegate {

	private static ISelection selection;

	private static IWorkbenchPart part;

	private static Collection<IAssertionSet> assertionSetsToRun;

	private AssertionRunner.AssertionInfo info;

	private IPath path;

	/**
	 * Default Constructor
	 */
	public RunAssertions() {
	}

	public void runSafe(final IAction action,
			final AssertionRunner.AssertionInfo info) {

		this.info = info;
		if (info == null) {
			selection = getSelection();
			part = getPart();
		} else {
			setSelection(selection);
			setPart(part);
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof DirectoryLogSession) {
			final DirectoryLogSession logSession = (DirectoryLogSession) struct
					.getFirstElement();
			path = logSession.getFolder().getProject().getLocation().append(
					SystemNavigator.ASSERTION_FILE_NAME);
		}
		final Logset logsession = getSelectedLogSession();
		if (logsession == null) {
			return;
		}

		IServiceProvider serviceProvider = getServiceProvider();
		run(logsession, serviceProvider);
		setInfoToView();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void runSafe(final IAction action) {

        if (Logset.isDisabled()) {
            ErrorDialog.openError(new Shell(), Messages.LICENSE_ERROR,
                    Messages.RUN_ASSERTION_ERROR,
                    new Status(IStatus.ERROR, SeUiPlugin.PLUGIN_ID,
                            IStatus.ERROR, Messages.LICENSE_EXCEPTION + ".",
                            Logset.getException()));

            return;
        }

        runSafe(action, null);
    }

	/**
	 * Get current Logset
	 * 
	 * @return
	 */
	private Logset getSelectedLogSession() {
		final Object firstElement = getFirstElement();
		if (firstElement instanceof DirectoryLogSession) {
			final DirectoryLogSession logsetDirectory = (DirectoryLogSession) firstElement;
			final Logset logset = Logset.valueOf(logsetDirectory.getId());
			return logset;
		}
		return null;
	}

	/**
	 * The actual run method.
	 * 
	 * @param serviceProvider
	 *            the service provider must be able to provide
	 *            {@link IAssertionRegistry} services. Must not be null
	 * @param logset
	 *            the wrapped, must not be null
	 */
	private void run(final Logset logset, final IServiceProvider serviceProvider) {

		if (info == null) {
			// Assertion Option Dialog Open (Running in UI Thread).
			RunAssertions.assertionSetsToRun = getAssertionSetsToRun(getPossibleAssertions(serviceProvider));
		}

		// Background Job
		Job job = new Job("Assertion Results") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				// Running in Non-UI Thread
				monitor.beginTask("Searching Assertion Results....",
						IProgressMonitor.UNKNOWN);

				// Add ICON in Progress View Bar
				setProperty(IProgressConstants.ICON_PROPERTY, SeUiPlugin
						.imageDescriptorFromPlugin(SeUiPlugin.PLUGIN_ID,
								"icons/assertions.gif"));
				if (assertionSetsToRun.size() > 0) {
					final AssertionRunner runner = new AssertionRunner(
							assertionSetsToRun, SeUiPlugin.getDefault()
									.getPreferenceStore().getInt(
											"assertion_hits"), info);
					info = runner.getAssertionInfo();
					Iterable<IAssertionSetResult> result = runner.run(logset,
							monitor);
					updateResults(monitor, result, serviceProvider);
				}
				monitor.done();
				return monitor.isCanceled() ? Status.CANCEL_STATUS
						: Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	/**
	 * Result Set in Assertion View is updated after finish the Job (Non-UI
	 * Thread)
	 * 
	 * @param monitor
	 * @param result
	 * @param serviceProvider
	 */
	private void updateResults(final IProgressMonitor monitor,
            final Iterable<IAssertionSetResult> result,
            final IServiceProvider serviceProvider) {

        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                setInfoToView();

                final IAssertionReportService resultConsumer = serviceProvider
                .getService(IAssertionReportService.class);
                Logset log = getSelectedLogSession();
                if (result.iterator().hasNext()) {
                    resultConsumer.setAssertionSetResults(log, result);
                }
            }
        });
      }

	private List<IAssertionSet> getPossibleAssertions(
			final IServiceProvider serviceProvider) {

		IMementoService mem = SeCorePlugin.getDefault().getService(
				IMementoService.class);
		final IAssertionRegistry registry = new AssertionRegistry(mem, path,
				TypeRegistry.getInstance());
		final Iterable<IAssertionSet> assertionSets = registry
				.getAssertionSets();
		final List<IAssertionSet> sets = new ArrayList<IAssertionSet>();
		for (final IAssertionSet set : assertionSets) {
			sets.add(set);
		}
		return sets;
	}

	/**
	 * Get assertion sets to run. Returns an empty collection if no
	 * assertionsets were selected
	 * 
	 * @param toFilter
	 *            the assertion sets to filter
	 * 
	 * @return the assertion sets to run
	 */
	private Collection<IAssertionSet> getAssertionSetsToRun(
			final List<IAssertionSet> toFilter) {

		final CheckedTreeSelectionDialog selectSets = new CheckedTreeSelectionDialog(
				getShell(), new NamedItemLabelProvider(),
				new NamedItemContentProvider());
		selectSets.setInitialElementSelections(toFilter);
		selectSets.setTitle("Select Assertion Sets to Run");
		selectSets
				.setEmptyListMessage("You must select at least one set to run");
		final List<IAssertionSet> assertionsToRun = new ArrayList<IAssertionSet>();
		selectSets.setInput(toFilter);
		if (Window.OK == selectSets.open()) {
			for (final Object selected : selectSets.getResult()) {
				assertionsToRun.add((IAssertionSet) selected);
			}
		}
		return assertionsToRun;
	}

	/**
	 * Get the Assertion View instance.
	 * 
	 * @return
	 */
	private AssertionResultView getView() {
		final IWorkbenchPage activePage = SeUiPlugin.getDefault()
				.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			final IWorkbenchPart activePart = activePage.getActivePart();
			final IViewPart part = activePage
					.showView(AssertionResultView.VIEW_ID);
			activePage.activate(activePart);
			if (part instanceof AssertionResultView) {
				final AssertionResultView view = (AssertionResultView) part;
				return view;
			}
		} catch (final PartInitException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * Update Info
	 */
	private void setInfoToView() {
		getView().setInfo(info);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);
		if (this.guardFail()) {
			return;
		}
		final IStructuredSelection struct = (IStructuredSelection) selection;
		if (struct.getFirstElement() instanceof DirectoryLogSession) {
			final DirectoryLogSession logSession = (DirectoryLogSession) struct
					.getFirstElement();

			Logset logset = Logset.valueOf(logSession.getId());
			if (logset.isLocked()) {
				action.setEnabled(false);
			} else {
				action.setEnabled(true);
			}
		}
	}

}
