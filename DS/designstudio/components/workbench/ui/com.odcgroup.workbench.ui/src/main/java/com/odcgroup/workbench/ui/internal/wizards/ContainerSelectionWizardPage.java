package com.odcgroup.workbench.ui.internal.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.ui.wizards.NewContainerWizardPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.misc.ContainerSelectionGroup;

import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelCreationPage;

/**
 * This Wizard is used to select a folder from the workspace.
 *
 * @author mumesh
 *
 */
@SuppressWarnings("restriction")
public class ContainerSelectionWizardPage extends NewContainerWizardPage implements Listener {

	/**
	 * @param name
	 */
	public ContainerSelectionWizardPage() {
		super("wizardPage");
		setPageComplete(false);
		setTitle("Folder Selection");
		setDescription("Select a folder");
	}

	private ContainerSelectionGroup group;

	@Override
	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		group = new ContainerSelectionGroup(topLevel, this, false, "Select the folder", false);
		setControl(topLevel);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.CONTEXT_PREFIX);
	}

	@Override
	public void handleEvent(Event event) {
		setPageComplete(validateSelection());
	}

	/**
	 * @return
	 */
	private boolean validateSelection() {
		IPath path = group.getContainerFullPath();
		final IContainer folder = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		((WizardDialog) getContainer()).addPageChangingListener(new IPageChangingListener() {
			public void handlePageChanging(PageChangingEvent event) {
				if (event.getTargetPage() instanceof AbstractNewModelCreationPage) {
					AbstractNewModelCreationPage page = (AbstractNewModelCreationPage) event.getTargetPage();
					page.setContainer(folder);
				}
			}
		});
		return true;
	}

}
