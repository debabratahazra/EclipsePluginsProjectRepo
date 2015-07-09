package com.odcgroup.t24.enquiry.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.google.inject.Injector;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;
import com.odcgroup.t24.enquiry.importer.IEnquiryImporter;
import com.odcgroup.t24.enquiry.importer.internal.EnquiryImporter;
import com.odcgroup.t24.enquiry.importer.ui.Messages;
import com.odcgroup.t24.enquiry.ui.internal.EnquiryActivator;

public class EnquiryImportWizard extends T24ModelImportWizard<IEnquiryImporter> {
	
	@Override
	protected IEnquiryImporter createModelImporter() {
		Injector injector = EnquiryActivator.getInstance().getInjector(EnquiryActivator.COM_ODCGROUP_T24_ENQUIRY_ENQUIRY);
		EnquiryImporter imp = injector.getInstance(EnquiryImporter.class);
		imp.setModelName(Messages.EnquiryModel_name);
		return imp;
	}
	
	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new EnquirySelectionPage(getImportModel().getEnquirySelector()));
	}

	public EnquiryImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.EnquiryImportWizard_title);
	}

}
