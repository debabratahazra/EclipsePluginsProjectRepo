package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * @author pkk
 * 
 * a simplified domain creation page
 */
public class NewDomainCreationPage extends AbstractDomainCreationPage {

	/**
	 * @param pageName
	 * @param workbench
	 * @param containerFullPath
	 * @param initialDomain
	 * @param copyPage
	 * @param resource
	 */
	public NewDomainCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, MdfDomain initialDomain, boolean copyPage,
			OfsModelResource resource) {
		super(pageName, workbench, containerFullPath, initialDomain, copyPage, resource);
	}

	@Override
	protected void createCustomPropertiesControl(Composite parent) {		
	}

	@Override
	protected void setCustomProperties(MdfDomain domain) {		
	}
	
}
