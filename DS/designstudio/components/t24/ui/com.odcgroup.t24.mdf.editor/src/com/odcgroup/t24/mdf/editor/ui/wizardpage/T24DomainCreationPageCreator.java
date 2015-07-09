package com.odcgroup.t24.mdf.editor.ui.wizardpage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.mdf.editor.ui.dialogs.mdf.AbstractDomainCreationPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.IDomainCreationPageCreator;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.resources.OfsModelResource;

public class T24DomainCreationPageCreator implements IDomainCreationPageCreator {

	public T24DomainCreationPageCreator() {
	}

	@Override
	public AbstractDomainCreationPage getDomainCreationPage(String pageName,
			IWorkbench workbench, IPath containerFullPath,
			MdfDomain initialDomain, boolean copyPage, OfsModelResource resource) {
		return new T24DomainCreationPage(pageName, workbench, containerFullPath, initialDomain, copyPage, resource);
	}

}
