package com.odcgroup.t24.common.importer.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.t24.common.importer.IImportModel;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public abstract class ModelImportWizard<T extends IImportModel> extends Wizard implements IImportWizard {

	@SuppressWarnings("unused")
	private IStructuredSelection selection;
	
	private T importer;

	/**
	 * @return
	 */
	protected abstract List<IWizardPage> createAllPages();

	/**
	 * @param shell
	 * @return
	 */
	protected TitleAreaDialog createReportDialog(Shell shell, IImportModelReport report, String modelName) {
		return new ModelImportReportDialog(shell, report, modelName);
	}
	
	protected final T getImportModel() {
		return this.importer;
	}

	@Override
	public void addPages() {
		for (IWizardPage page : createAllPages()) {
			if (page != null) {
				addPage(page);
			}
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		// TODO if the selection is not null, then the selection must be 
		//      passed to the FolderSelectionPage, In this case the page
		//		is set read-only OR the page is simply not shown (better)
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	@Override
	public boolean performFinish() {
		final Shell shell = getShell();
		
		try {
			// turn off auto-building
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription desc = ws.getDescription();
			final boolean autobuild = desc.isAutoBuilding();
			desc.setAutoBuilding(false);
			ws.setDescription(desc);

			final IImportModel importer = getImportModel();
			
			final boolean[] canceled = { false };

			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					IWorkspaceRunnable wsop = new IWorkspaceRunnable() {
						public void run(IProgressMonitor monitor) throws CoreException {
							try {
								importer.importModels(monitor);
							} catch (T24ServerException ex) {
								importer.getImportReport().error(ex);
							} finally {
								canceled[0] = monitor.isCanceled();
								monitor.done();
							}
						}
					};
					try {
						ResourcesPlugin.getWorkspace().run(wsop, null, IWorkspace.AVOID_UPDATE, monitor);
					} catch (CoreException ex) {
						MessageDialog.openError(shell, Messages.ModelImportWizard_error, ex.getMessage());
					}
				}
			};
			
			try {
				new ResizableProgressMonitorDialog(shell).run(true, true, op);
				if (!canceled[0]) {
					TitleAreaDialog dlg = createReportDialog(getShell(), 
							importer.getImportReport(), 
							importer.getModelName());
					if (dlg != null) {
						dlg.open();
					}
				}
			} catch (InvocationTargetException ex) {
				Throwable realException = ex.getTargetException();
				MessageDialog.openError(shell, Messages.ModelImportWizard_error, realException.getMessage());
			} catch (InterruptedException ex) {
				MessageDialog.openInformation(shell,
						Messages.ModelImportWizard_cancelled, ex.getMessage());
			} finally {
				// set the original value back on auto-building
				desc.setAutoBuilding(autobuild);
				ws.setDescription(desc);
			}
			
		} catch (CoreException ex) {
			MessageDialog.openError(shell, Messages.ModelImportWizard_error, ex.getMessage());
		}
		return true;
	}
	
	/**
	 * @param importer
	 */
	protected void setModelImporter(T importer) {
		this.importer = importer;
	}

	/**
	 * 
	 */
	public ModelImportWizard() {
	}

	class ResizableProgressMonitorDialog extends ProgressMonitorDialog {
		protected boolean isResizable() {
			return true;
		}

		public ResizableProgressMonitorDialog(Shell parent) {
			super(parent);
		}
	}
}
