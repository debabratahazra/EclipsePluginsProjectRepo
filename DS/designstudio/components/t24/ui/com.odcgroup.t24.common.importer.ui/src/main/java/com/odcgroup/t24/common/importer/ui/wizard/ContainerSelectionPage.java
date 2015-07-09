package com.odcgroup.t24.common.importer.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.t24.common.importer.IContainerSelector;
import com.odcgroup.t24.common.importer.ui.Messages;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * This wizard page allows the user to choose the container folder where
 * the selected models should be imported.
 */
public class ContainerSelectionPage extends WizardPage implements Listener {
	
	private IContainerSelector containerSelector;
	
	protected boolean doIsPageComplete() {
		return containerSelector.isContainerSet();
	}
	
	private void validateControls() {
		setPageComplete(doIsPageComplete());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}
	
	protected ContainerSelectionGroup createContainerSelectionGroup(Composite parent) {
		ContainerSelectionGroup group = new ContainerSelectionGroup(parent, this, containerSelector);
		group.setInitialFocus();
		return group;
	}
	
	protected final IContainerSelector getFolderSelector() {
		return this.containerSelector;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	
	protected void createAdditionalGroup(Composite parent) {
	}
	
	@Override
	public void createControl(Composite parent) {

		// create page content
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		
		createContainerSelectionGroup(topLevel);
		
		createAdditionalGroup(topLevel);
		
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
	public ContainerSelectionPage(IContainerSelector folderSelector) {		
		super(""); //$NON-NLS-1$
		setTitle(NLS.bind(Messages.FolderSelectionPage_title, folderSelector.getModelName()));
		setDescription(NLS.bind(Messages.FolderSelectionPage_description, folderSelector.getModelName()));
		this.containerSelector = folderSelector;
		setPageComplete(false);
	}

}