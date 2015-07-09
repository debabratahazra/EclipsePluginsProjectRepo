package com.odcgroup.t24.version.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.inject.Injector;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;
import com.odcgroup.t24.version.importer.IVersionImporter;
import com.odcgroup.t24.version.importer.internal.VersionImporter;
import com.odcgroup.t24.version.importer.ui.Messages;
import com.odcgroup.t24.version.ui.internal.VersionDSLActivator;

public class VersionImportWizard extends T24ModelImportWizard<IVersionImporter> {
	
	@Override
	protected IVersionImporter createModelImporter() {
		Injector injector = VersionDSLActivator.getInstance().getInjector(
				VersionDSLActivator.COM_ODCGROUP_T24_VERSION_VERSIONDSL);
		VersionImporter imp = injector.getInstance(VersionImporter.class);
		imp.setModelName(Messages.VersionModel_name);
		return imp;
	}
	
	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new VersionSelectionPage(getImportModel().getVersionSelector()));
	}
	
	public VersionImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.VersionImportWizard_title);
	}


}
