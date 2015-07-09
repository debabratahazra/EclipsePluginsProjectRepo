package com.odcgroup.page.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * Wizard for creating a Module in the Page Designer.
 * 
 * @author Gary Hayes
 * 		
 */
public class NewModuleWizard extends AbstractPageDesignerWizard {

	/** the icon for module */
	private static final String MODULE_ICON = "icons/obj64/module.png";
	
	/** The first WizardPage. */
	private NewModuleWizardPage page;

	/** The model specification */
	private ModelSpecification specification;
	
	/**
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizard#getModelSpecification()
	 */
	protected final ModelSpecification getModelSpecification() {
		if (this.specification == null) {
		    specification = new ModelSpecification(ModelType.MODULE, getContainerFullPath());	
		}
		return this.specification;
	}
	
	/**
	 * Adds the pages to the Wizard.
	 */
	public void addPages() {
		super.addPages();
		//page = new NewFragmentWizardPage("NewItemWizardPage", workbench, containerFullPath, model,".module", showBOM);

		page = new NewModuleWizardPage(getModelSpecification());
		page.setImageDescriptor(PageUIPlugin.getImageDescriptor(MODULE_ICON));
		addPage(page);
	}

	/**
	 * Initializes the Wizard.
	 * 
	 * @param workbench
	 *            The Workbench
	 * @param currentSelection
	 *            The IStructuredSelection
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = PageConstants.MODULE_FILE_EXTENSION;
		super.init(workbench, currentSelection);
	}
	
	/**
	 * Override to add help context to the wizard
	 * @param pageContainer
	 */
    public void createPageControls(Composite pageContainer) {
    	super.createPageControls(pageContainer);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(pageContainer.getShell(), IOfsHelpContextIds.NEW_MODULE);
    }	
}
