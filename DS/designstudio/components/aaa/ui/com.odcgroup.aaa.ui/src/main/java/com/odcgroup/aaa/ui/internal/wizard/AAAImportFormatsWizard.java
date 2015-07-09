package com.odcgroup.aaa.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.aaa.connector.internal.util.ImportReportVO;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.aaa.ui.AAAUIPlugin;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.impl.AAADefaultImportFacade;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.ui.helper.TemporarilyClosedEditorManager;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAImportFormatsWizard extends Wizard implements IImportWizard {

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(AAAImportFormatsWizard.class);

	//private IWorkbench workbench;
	private IStructuredSelection selection;

	private AAAFormatsPage formatsPage;

	private AAAFormatsSelectionPage formatsSelectionPage;

	private AAAImportFacade importFacade = new AAADefaultImportFacade();

	private IOfsModelPackage ofsPackage;
	
	/*
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		// set the selected format codes to the import facade
		List<AAAFormatCode> selectedCodes = formatsSelectionPage.getSelectedFormatCodes();
		if (selectedCodes.size() == 0) {
			formatsSelectionPage.setErrorMessage(Messages.getString("aaa.wizard.formats.nothing.selected"));
			return false;
		} else  {
			formatsSelectionPage.setErrorMessage(null);
			importFacade.getPreferences().save(formatsSelectionPage.getSelectionInfo());
		}
		importFacade.getSelectedFormatCodes().clear();
		importFacade.getSelectedFormatCodes().addAll(selectedCodes);
		
		final List<IStatus> errorsDuringImport = new ArrayList<IStatus>();
		final ImportReportVO reportVO = new ImportReportVO();
		TemporarilyClosedEditorManager mmlEditorStateManager = new TemporarilyClosedEditorManager("mml","domain","fragment","page","module");
		try {
			mmlEditorStateManager.closeEditors();
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.getString("aaa.wizard.formats.import.start"), 10);
					
					monitor.worked(1);
					if(monitor.isCanceled()) throw new InterruptedException();

					LOGGER.info("Starting format import");
					ImportReportVO internalReportVO = importFacade.importFormats(ofsPackage, monitor);
					reportVO.copyFrom(internalReportVO);
					monitor.done();
				}

			};
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getTargetException(), e);
			formatsSelectionPage.setErrorMessage(e.getTargetException().getMessage());
			return false;
		} catch (InterruptedException e) {
			return false;
		} finally {
			mmlEditorStateManager.restoreEditors();
		}

		convertToStatus(reportVO.getErrorMessagePerFormat(), errorsDuringImport);					
		if (errorsDuringImport.size() > 0) {
			MultiStatus status = new MultiStatus(AAAUIPlugin.PLUGIN_ID, 0, (IStatus[])errorsDuringImport.toArray(new IStatus[errorsDuringImport.size()]), "" + reportVO.getNbUnsuccessfullyImportedFormats() + " format(s) could not be imported. See Details for the list and the cause.\n\n" +
					"Note: " + reportVO.getNbSuccessfullyImportedFormats() + " format(s) were successfully imported.", null);
			ErrorDialog.openError(getShell(), "Formats Import Results", null, status);
		}

		return true;
	}
	
	private void convertToStatus(Map<String, String> errorMessagePerFormat, List<IStatus> statusList) {
		for (Map.Entry<String, String> entry : errorMessagePerFormat.entrySet()) {
			statusList.add(new Status(Status.WARNING, AAAUIPlugin.PLUGIN_ID, entry.getValue()));
		}
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		//this.workbench = workbench;
		this.selection = selection;
	}

	/*
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		Object element = selection.getFirstElement();

		if (element == null || !(element instanceof IOfsModelPackage) || 
				!((IOfsModelPackage)element).getURI().path().substring(1).endsWith(AAACore.getFindroot()+AAACore.FORMATS_MODELS_PACKAGE)) {
			/**
			 * the user must select a project and a root package named "formats"
			 * of type "domain" to store imported Triple'A models
			 */
			MessageDialog.openError(getShell(), 
					Messages.getString("aaa.wizard.formats.window.title"), 
					"Please select a package named \"" + AAACore.getFindroot()+AAACore.FORMATS_MODELS_PACKAGE + "\" in an Design Studio Model Project prior executing this wizard");
			return;
		}
		
		// Create the wizard
		ofsPackage = (IOfsModelPackage) element;
		formatsPage = new AAAFormatsPage(ofsPackage, importFacade);
		addPage(formatsPage);
		formatsSelectionPage = new AAAFormatsSelectionPage(ofsPackage, importFacade);
		addPage(formatsSelectionPage);
	}

	/**
	 * 
	 */
	public AAAImportFormatsWizard() {
		setWindowTitle(Messages.getString("aaa.wizard.formats.window.title"));
	}

	/**
	 * @return the formatsSelectionPage
	 */
	public AAAFormatsSelectionPage getFormatsSelectionPage() {
		return formatsSelectionPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return getContainer().getCurrentPage() == this.formatsSelectionPage;
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
}
