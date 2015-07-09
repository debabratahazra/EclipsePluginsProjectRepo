package com.odcgroup.t24.common.importer.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

import com.odcgroup.t24.common.importer.IT24ImportModel;

/**
 * @author atripod
 */
public abstract class T24ModelImportWizard<T extends IT24ImportModel> extends ModelImportWizard<T> {

	/**
	 * @return
	 */
	protected void createServerSelectionPage(List<IWizardPage> pages) {
		pages.add(new ServerSelectionPage(getImportModel().getServerSelector()));
	}

	/**
	 * @return
	 */
	protected void createContainerSelectionPage(List<IWizardPage> pages) {
		pages.add(new ContainerSelectionPage(getImportModel().getContainerSelector()));
	}
	
	/**
	 * @return
	 */
	protected abstract void createModelSelectionPages(List<IWizardPage> pages);
	
	/**
	 * @return
	 */
	protected abstract T createModelImporter();

	@Override
	protected List<IWizardPage> createAllPages() {
		List<IWizardPage> pages = new ArrayList<IWizardPage>();
		createServerSelectionPage(pages);
		createModelSelectionPages(pages);
		createContainerSelectionPage(pages);
		return pages;
	}

	public T24ModelImportWizard() {
	}
	
}
