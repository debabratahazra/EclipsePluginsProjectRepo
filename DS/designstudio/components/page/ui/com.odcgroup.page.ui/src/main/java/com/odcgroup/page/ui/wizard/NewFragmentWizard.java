package com.odcgroup.page.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * Wizard for creating a Fragment in the page Designer.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewFragmentWizard extends AbstractPageDesignerWizard {

	/** the icon for page artifact */
	private static final String FRAGMENT_ICON = "icons/obj64/fragment.png";
	
	/** The first and only WizardPage. */
	private NewFragmentWizardPage page;

	/** The model specification */
	private ModelSpecification specification;
	
	/* 
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizard#getModelSpecification()
	 */
	protected final ModelSpecification getModelSpecification() {
		if (this.specification == null) {
		    specification = new ModelSpecification(ModelType.FRAGMENT, getContainerFullPath());	
		}
		return this.specification;
	}
	
	/**
	 * Adds the pages to the Wizard.
	 */
	public void addPages() {
		super.addPages();
		
		page = new NewFragmentWizardPage(getModelSpecification());
		page.setImageDescriptor(PageUIPlugin.getImageDescriptor(FRAGMENT_ICON));
		addPage(page);
	}

	/**
	 * Initialises the Wizard.
	 * 
	 * @param workbench
	 *            The Workbench
	 * @param currentSelection
	 *            The IStructuredSelection
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = PageConstants.FRAGMENT_FILE_EXTENSION;
		super.init(workbench, currentSelection);
	}

}
