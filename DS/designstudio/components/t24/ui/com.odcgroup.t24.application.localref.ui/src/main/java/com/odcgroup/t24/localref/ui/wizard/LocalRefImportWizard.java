package com.odcgroup.t24.localref.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.t24.application.localref.ILocalRefImporter;
import com.odcgroup.t24.application.localref.importer.LocalRefImporter;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;
import com.odcgroup.t24.localref.ui.Messages;

/**
 * @author ssreekanth
 * 
 */
public class LocalRefImportWizard extends T24ModelImportWizard<ILocalRefImporter> {

	@Override
	protected ILocalRefImporter createModelImporter() {
		Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		LocalRefImporter imp = injector.getInstance(LocalRefImporter.class);
		imp.setModelName(Messages.LocalRefModel_name);
		return imp;
	}

	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new LocalRefSelectionPage(getImportModel().getLocalRefSelector()));
	}

	public LocalRefImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.LocalRefImportWizard_title);
	}
}
