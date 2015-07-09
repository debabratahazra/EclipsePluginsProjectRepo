package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * @author phanikumark
 *
 */
public interface IDomainCreationPageCreator {
	
	/**
	 * @param pageName
	 * @param workbench
	 * @param containerFullPath
	 * @param initialDomain
	 * @param copyPage
	 * @param resource
	 * 
	 * @return
	 */
	public AbstractDomainCreationPage getDomainCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, MdfDomain initialDomain, boolean copyPage,
			OfsModelResource resource);

}
