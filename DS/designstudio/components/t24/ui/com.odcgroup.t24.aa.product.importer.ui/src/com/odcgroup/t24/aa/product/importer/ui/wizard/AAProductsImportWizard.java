package com.odcgroup.t24.aa.product.importer.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.odcgroup.t24.aa.product.importer.IAAProductsImporter;
import com.odcgroup.t24.aa.product.importer.internal.AAProductsImporter;
import com.odcgroup.t24.aa.product.importer.ui.Messages;
import com.odcgroup.t24.common.importer.ui.wizard.T24ModelImportWizard;

public class AAProductsImportWizard extends T24ModelImportWizard<IAAProductsImporter> {

	@Override
	protected void createModelSelectionPages(List<IWizardPage> pages) {
		pages.add(new AAProductsSelectionPage(getImportModel().getAAProductsSelector()));
	}

	@Override
	protected IAAProductsImporter createModelImporter() {
		AAProductsImporter imp = new AAProductsImporter();
		imp.setModelName(Messages.ProduclLineModel_Name);
		return imp;
	}
	
	public AAProductsImportWizard() {
		setModelImporter(createModelImporter());
		setWindowTitle(Messages.ProduclLineImportWizard_title);
	}

}
