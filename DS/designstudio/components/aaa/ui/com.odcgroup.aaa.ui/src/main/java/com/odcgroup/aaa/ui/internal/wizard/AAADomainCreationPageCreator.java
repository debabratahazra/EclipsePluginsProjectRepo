package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.AbstractDomainCreationPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.IDomainCreationPageCreator;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

/**
 * @author phanikumark
 *
 */
public class AAADomainCreationPageCreator implements IDomainCreationPageCreator {

	@Override
	public AbstractDomainCreationPage getDomainCreationPage(String pageName,
			IWorkbench workbench, IPath containerFullPath,
			MdfDomain initialDomain, boolean copyPage, OfsModelResource resource) {
		return new AAADomainCreationPage(pageName, workbench, containerFullPath, initialDomain, copyPage, resource);
	}

}
