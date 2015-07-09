package com.odcgroup.iris.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.odcgroup.iris.importer.IIRISImporter;
import com.odcgroup.iris.importer.IRISModelImporter;
import com.odcgroup.iris.importer.ui.Messages;
import com.odcgroup.t24.application.importer.ui.wizard.ApplicationSelectionPage;
import com.odcgroup.t24.common.importer.ui.wizard.ContainerExSelectionPage;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;
import com.odcgroup.t24.enquiry.importer.ui.wizard.EnquirySelectionPage;
import com.odcgroup.t24.version.importer.ui.wizard.VersionSelectionPage;

public class IRISImportWizard extends T24ModelImportWizard<IIRISImporter> {

	@Override
	protected IIRISImporter createModelImporter() {
		return new IRISModelImporter();
	}
	
	/**
	 * @return
	 */
	protected void createContainerSelectionPage(List<IWizardPage> pages) {
		// none.
	}

	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		// 1. first select the project and a RIM model
		pages.add(new ContainerExSelectionPage(getImportModel().getContainerSelector(), "Model name:") {
			public boolean canFlipToNextPage() {
				return isPageComplete();
			}
		});
		// 1. select the applications (optional)
		pages.add(new ApplicationSelectionPage(getImportModel().getApplicationSelector()) {
			public boolean canFlipToNextPage() {
				return true;
			}
		});
		// 1. select the enquiries (optional)
		pages.add(new EnquirySelectionPage(getImportModel().getEnquirySelector()) {
			public boolean canFlipToNextPage() {
				return true;
			}
		});
		// 2. select the versions (optional)
		pages.add(new VersionSelectionPage(getImportModel().getVersionSelector()) {
			public boolean canFlipToNextPage() {
				return false;
			}
		});		
	}
	
	

	@Override
	public boolean canFinish() {
		return ! getImportModel().isSelectionEmpty();
	}

	public IRISImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.IRISImportWizard_title);
	}

}
