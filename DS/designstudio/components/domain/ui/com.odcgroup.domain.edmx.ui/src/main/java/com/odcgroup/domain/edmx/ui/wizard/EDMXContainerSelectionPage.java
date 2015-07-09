package com.odcgroup.domain.edmx.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.domain.edmx.IEDMXContainerSelector;
import com.odcgroup.domain.edmx.ui.EDMXUICore;
import com.odcgroup.domain.edmx.ui.util.EDMXConstants;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * This wizard page allows the user to choose the container folder where
 * the selected models should be imported.
 */
public class EDMXContainerSelectionPage extends WizardPage implements Listener {
	
	private IEDMXContainerSelector containerSelector;
	
	protected boolean doIsPageComplete() {
		return containerSelector.isContainerSet();
	}
	
	private void validateControls() {
		setPageComplete(doIsPageComplete());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}
	
	protected EDMXContainerSelectionGroup createContainerSelectionGroup(Composite parent) {
		EDMXContainerSelectionGroup group = new EDMXContainerSelectionGroup(parent, this, containerSelector);
		group.setInitialFocus();
		return group;
	}
	
	protected final IEDMXContainerSelector getFolderSelector() {
		return this.containerSelector;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	
	@Override
	public void createControl(Composite parent) {

		// create page content
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		
		createContainerSelectionGroup(topLevel);
		
		// Set the control for the receiver.
		setControl(topLevel);

		// Set help context
		PlatformUI.getWorkbench()
				  .getHelpSystem()
				  .setHelp(parent.getShell(),
				           IOfsHelpContextIds.CONTEXT_PREFIX);
	}

	@Override
	public void handleEvent(Event event) {
		validateControls();
	}
	
	/**
	 * @param importer
	 */
	public EDMXContainerSelectionPage(IEDMXContainerSelector folderSelector) {		
		super(""); //$NON-NLS-1$
		setTitle(NLS.bind(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_FOLDER_SELECTION_PAGE_TITLE), folderSelector.getModelName()));
		setDescription(NLS.bind(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_FOLDER_SELECTION_PAGE_DESC), folderSelector.getModelName()));
		this.containerSelector = folderSelector;
		setPageComplete(false);
	}

}