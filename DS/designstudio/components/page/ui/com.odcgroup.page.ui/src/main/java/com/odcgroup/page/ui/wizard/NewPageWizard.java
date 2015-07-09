package com.odcgroup.page.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * Wizard for creating a Page in the Page Designer.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewPageWizard extends AbstractPageDesignerWizard {
	
	/** the icon for page artifact */
	private static final String PAGE_ICON = "icons/obj64/page.png";

	/** The first and only WizardPage. */
	private NewPageWizardPage page;
	
	/** The model specification */
	private ModelSpecification specification;
	
	protected final ModelSpecification getModelSpecification() {
		if (this.specification == null) {
		    specification = new ModelSpecification(ModelType.PAGE, getContainerFullPath());	
		}
		return this.specification;
	}

	/**
	 * Adds the pages to the Wizard.
	 */
	public void addPages() {
		super.addPages();
		
		page = new NewPageWizardPage(getModelSpecification());
		page.setImageDescriptor(PageUIPlugin.getImageDescriptor(PAGE_ICON));
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
		model = PageConstants.PAGE_FILE_EXTENSION;
		super.init(workbench, currentSelection);
	}

}
