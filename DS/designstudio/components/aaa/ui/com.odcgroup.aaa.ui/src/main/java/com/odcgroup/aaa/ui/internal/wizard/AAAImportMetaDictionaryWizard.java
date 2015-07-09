package com.odcgroup.aaa.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.impl.AAADefaultImportFacade;
import com.odcgroup.aaa.ui.internal.model.impl.MetaDictionnaryHelper;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.helper.TemporarilyClosedEditorManager;

/**
 * @author atr
 * @since 1.40.0
 */
public class AAAImportMetaDictionaryWizard extends Wizard implements
		IImportWizard {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(AAAImportMetaDictionaryWizard.class);

	private IStructuredSelection selection;

	private AAAMetaDictPage metadictPage;

	private AAAImportFacade importFacade = new AAADefaultImportFacade();

	private IOfsProject ofsProject = null;

	/*
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		if(!metadictPage.validateConnection()) {
			return false;
		}

		LOGGER.info("Starting Meta Dictionary import");
		
		// Ask for the confirmation of replacing the Meta Dictionary prior
		// starting the wizard.
		if (MetaDictionnaryHelper.getAAAMetadictionaryEntities(ofsProject) != null ||
				MetaDictionnaryHelper.getAAAMetadictionaryEnums(ofsProject) != null) {
			if (!MessageDialog.openConfirm(getShell(), 
					Messages.getString("aaa.wizard.metadict.window.title"),
					"An imported Meta Dictionary already exists. Do you want to replace it ?")) {
				return false;
			}
		}

		TemporarilyClosedEditorManager mmlEditorStateManager = new TemporarilyClosedEditorManager("mml","domain","fragment","page","module");
		try {
			mmlEditorStateManager.closeEditors();
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.getString("aaa.wizard.metadict.import.start"), 10);
					if (MetaDictionnaryHelper.getAAAMetadictionaryBusinessTypes(ofsProject) == null) {
						throw new InvocationTargetException(new Exception("No Business Types model found. Expected : " + AAACore.AAA_BUSINESS_TYPE_MODEL));
					}
					if(monitor.isCanceled()) throw new InterruptedException();

					if (!importFacade.importMetaDictionary(ofsProject, monitor)) {
						throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.metadict.window.unable.to.import.metadict")));
					}
					monitor.done();
				}
			};
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getTargetException());
			metadictPage.setErrorMessage(e.getTargetException().getMessage());
			return false;
		} catch (InterruptedException e) {
			return false;
		} finally {
			mmlEditorStateManager.restoreEditors();			
		}

		return true;
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/*
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		Object element = selection.getFirstElement();
		ofsProject = null;

		// If an ofs element is selected, try to use it
		if (element != null && 
				element instanceof IOfsModelPackage && 
				((IOfsModelPackage)element).getURI().path().substring(1).equals(AAACore.getFindroot()+AAACore.AAA_MODELS_FOLDER)) {
			ofsProject = ((IOfsElement)element).getOfsProject();
		}
		
		if (ofsProject == null) {
			Set<IOfsProject> allOfsProjects = OfsCore.getOfsProjectManager().getAllOfsProjects();
			for (IOfsProject project : allOfsProjects) {
				try {
					if (project.getProject().hasNature(AAACore.NATURE_ID)) {
						ofsProject = project;
						break;
					}
				} catch (CoreException e) {}
			}
		}

		if (ofsProject == null) {
			MessageDialog.openError(getShell(), 
					Messages.getString("aaa.wizard.metadict.window.title"), 
					"No Design Studio project with Triple'A nature can be found in the workspace.");
			return;
		}
		
		metadictPage = new AAAMetaDictPage(ofsProject, importFacade);
		addPage(metadictPage);
	}

	/**
	 * 
	 */
	public AAAImportMetaDictionaryWizard() {
		setWindowTitle(Messages.getString("aaa.wizard.metadict.window.title"));
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
	
}
