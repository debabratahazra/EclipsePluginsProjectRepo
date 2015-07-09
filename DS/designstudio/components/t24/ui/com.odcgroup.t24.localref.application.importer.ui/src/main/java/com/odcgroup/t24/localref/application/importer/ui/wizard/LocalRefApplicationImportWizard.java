package com.odcgroup.t24.localref.application.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationImporter;
import com.odcgroup.t24.application.localrefapplication.importer.LocalRefApplicationImporter;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;
import com.odcgroup.t24.localref.application.importer.ui.Messages;
/**
 * @author hdebabrata
 *
 */

public class LocalRefApplicationImportWizard extends T24ModelImportWizard<ILocalRefApplicationImporter> {

	public LocalRefApplicationImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.LocalRefApplicationImportWizard_title);
	}
	
	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new LocalRefApplicationSelectionPage(getImportModel().getApplicationSelector()));
	}

	@Override
	protected ILocalRefApplicationImporter createModelImporter() {
		Injector injector = DomainActivator.getInstance().getInjector(
				DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		LocalRefApplicationImporter imp = injector.getInstance(LocalRefApplicationImporter.class);
		imp.setModelName(Messages.LocalRefApplication_Model_name);
		return imp;
	}

}
