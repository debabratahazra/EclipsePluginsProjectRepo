package com.odcgroup.aaa.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.impl.AAADefaultImportFacade;
import com.odcgroup.aaa.ui.internal.model.impl.MetaDictionnaryHelper;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.TemporarilyClosedEditorManager;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAASynchronizeImportWizard extends Wizard implements IImportWizard {
	//private IWorkbench workbench;
	private IStructuredSelection selection;

	private AAASynchronizePage synchronizePage;

	private AAAImportFacade importFacade = new AAADefaultImportFacade();

	private IOfsModelResource ofsResource;
	
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(AAASynchronizeImportWizard.class);

	/*
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		LOGGER.info("Starting formats synchronisation");
		if (!synchronizePage.validateConnection()) {
			return false;
		}
		
		// Check if the metadictionary models are available
		if (MetaDictionnaryHelper.getAAAMetadictionaryEntities(ofsResource.getOfsProject()) == null) {
			synchronizePage.setErrorMessage("No Meta Dictionary Entities model found. It is necessary to process your request.");
			return false;
		}

		if (MetaDictionnaryHelper.getAAAMetadictionaryEnums(ofsResource.getOfsProject()) == null) {
			synchronizePage.setErrorMessage("No Meta Dictionary Enums model found. It is necessary to process your request.");
			return false;
		}

		TemporarilyClosedEditorManager mmlEditorStateManager = new TemporarilyClosedEditorManager("page","module","fragment") {
			@Override
			protected boolean acceptFile(IFile file) {
				if (super.acceptFile(file)) {
					return true;
				}
				
				boolean accept = false;
				Object obj = selection.getFirstElement();
				if (obj instanceof EObject) {
					Resource res = ((EObject)obj).eResource();
					if (res != null) {
						IFile resFile = OfsResourceHelper.getFile(res, res.getURI());
						accept = resFile != null && !resFile.equals(file);
					}
				}
				return accept;
			}
		};
		try {
			mmlEditorStateManager.closeEditors();
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.getString("aaa.wizard.synchronize.start"), 115);
					if (!importFacade.synchronizeFormats(ofsResource, selection, monitor)) {
						throw new InvocationTargetException(new Exception(Messages.getString("aaa.wizard.synchronize.unable.to.synchronize")));
					}
					monitor.done();
				}
			};
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			LOGGER.error(e.getTargetException());
			synchronizePage.setErrorMessage(e.getTargetException().getMessage());
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
	public void init(IWorkbench workbench, final IStructuredSelection selection) {
		//this.workbench = workbench;
		this.selection = selection;
	}

	/*
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		Object element = selection.getFirstElement();
		if (element != null) {
			if (element instanceof IOfsModelResource) {
				ofsResource = (IOfsModelResource) element;
				synchronizePage = new AAASynchronizePage(ofsResource, importFacade);
				addPage(synchronizePage);
			} else if (element instanceof EObject) {
				Resource res = ((EObject)element).eResource();
				URI uri = res.getURI();
				if (res.getResourceSet().getURIConverter() instanceof ModelURIConverter) {
					ModelURIConverter converter = (ModelURIConverter)res.getResourceSet().getURIConverter();
					IOfsProject ofsProject = converter.getOfsProject();
					try {
						ofsResource = ofsProject.getOfsModelResource(uri);
						synchronizePage = new AAASynchronizePage(ofsResource, importFacade);
						addPage(synchronizePage);
					} catch (ModelNotFoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			}			
		}
	}

	/**
	 * 
	 */
	public AAASynchronizeImportWizard() {
		setWindowTitle(Messages.getString("aaa.wizard.synchronize.window.title"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#needsProgressMonitor()
	 */
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

}
