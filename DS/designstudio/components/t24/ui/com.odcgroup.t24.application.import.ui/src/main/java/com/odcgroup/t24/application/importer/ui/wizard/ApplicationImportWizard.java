package com.odcgroup.t24.application.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.t24.application.importer.IApplicationImporter;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.application.importer.ui.Messages;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;

public class ApplicationImportWizard extends T24ModelImportWizard<IApplicationImporter> {
	// private static final Logger logger = LoggerFactory.getLogger(ApplicationImportWizard.class);

	protected IApplicationImporter createModelImporter() {
		Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		ApplicationImporter imp = new ApplicationImporter(injector);
		imp.setModelName(Messages.ApplicationModel_name);
		return imp;
	}
	
	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new ApplicationSelectionPage(getImportModel().getApplicationSelector()));
	}

	public ApplicationImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.ApplicationImportWizard_title);
	}

}
