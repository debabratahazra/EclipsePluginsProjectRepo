package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 * @since DS 1.40.0
 */
public abstract class AbstractAAAPage extends WizardPage {

	private AAAImportFacade importFacade;
	
	private IOfsProject ofsProject;
	private IOfsModelPackage ofsPackage;
	
	protected String getOfsResourceURI() {
		return ofsPackage.getURI().toString();
	}
	
	protected final AAAImportFacade getImportFacade() {
		return this.importFacade;
	}
	
	protected final IOfsProject getOfsProject() {
		return this.ofsProject;
	}

	protected final IOfsModelPackage getOfsPackage() {
		return this.ofsPackage;
	}

	/**
	 * @param pageName
	 * @param importModel
	 */
	public AbstractAAAPage(String pageName, IOfsModelPackage ofsPackage, AAAImportFacade importFacade) {
		super(pageName);
		this.ofsPackage = ofsPackage;
		this.ofsProject = ofsPackage.getOfsProject();
		this.importFacade = importFacade;
	}

	/**
	 * @param pageName
	 * @param importModel
	 */
	public AbstractAAAPage(String pageName, IOfsProject ofsProject, AAAImportFacade importFacade) {
		super(pageName);
		this.ofsProject = ofsProject;
		this.ofsPackage = null;
		this.importFacade = importFacade;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#setErrorMessage(java.lang.String)
	 */
	@Override
	public void setErrorMessage(String newMessage) {
		// Display the error log view if the error message refers to it.
		if (newMessage != null && newMessage.contains("Error Log")) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.pde.runtime.LogView");
			} catch (PartInitException e) {
				// Displaying the error log is unlikely to fail
			}
		}
		super.setErrorMessage(newMessage);
	}


}
