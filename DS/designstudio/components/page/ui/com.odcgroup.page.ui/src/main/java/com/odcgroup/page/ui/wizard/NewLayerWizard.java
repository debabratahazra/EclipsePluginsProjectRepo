package com.odcgroup.page.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.ui.PageUIPlugin;

/**
 * Wizard for creating a Layer in the page Designer.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewLayerWizard extends AbstractPageDesignerWizard {

	/** the icon for page artifact */
	private static final String LAYER_ICON = "icons/obj64/fragment.png";
	
	/** The first and only WizardPage. */
	private NewLayerWizardPage page;

	/** The model specification */
	private ModelSpecification specification;
	
	/* 
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizard#getModelSpecification()
	 */
	protected final ModelSpecification getModelSpecification() {
		if (this.specification == null) {
		    specification = new ModelSpecification(ModelType.LAYER, getContainerFullPath());	
		}
		return this.specification;
	}
	
	/**
	 * Adds the pages to the Wizard.
	 */
	public void addPages() {
		super.addPages();
		
		page = new NewLayerWizardPage(getModelSpecification());
		page.setImageDescriptor(PageUIPlugin.getImageDescriptor(LAYER_ICON));
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
		model = PageConstants.LAYER_FILE_EXTENSION;
		super.init(workbench, currentSelection);
	}

}
