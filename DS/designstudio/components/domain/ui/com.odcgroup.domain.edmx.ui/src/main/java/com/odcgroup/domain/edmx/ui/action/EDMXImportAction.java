package com.odcgroup.domain.edmx.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.domain.edmx.ui.EDMXUICore;
import com.odcgroup.domain.edmx.ui.util.EDMXConstants;
import com.odcgroup.domain.edmx.ui.wizard.EDMXImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author Ramya Veigas
 * @version 1.0
 */
public class EDMXImportAction extends Action {

	/** The wizard height */
	private static final int SIZING_WIZARD_HEIGHT = 550;

	/** The wizard width */
	private static final int SIZING_WIZARD_WIDTH = 470;
	
	private IStructuredSelection selection;
	
	/**
	 * @return IStructuredSelection the current selection
	 */
	protected final IStructuredSelection getSelection() {
		return selection;
	}

	public EDMXImportAction() {
		setText(EDMXUICore.getDefault()
				.getString(EDMXConstants.EDMX_WIZARD_MENU_ITEM_TITLE));
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				EDMXUICore.PLUGIN_ID, "/icons/domain_dsl.gif"));
	}

	/**
	 * Runs the action.
	 */
	public void run() {
		run(this);
	}

	/**
	 * @param window
	 * @return {@code true} if the wizard has not been canceled, otherwise it returns {@code false}
	 */
	protected boolean runWizard(IWorkbenchWindow window) {
		IImportWizard wizard = new EDMXImportWizard();
		wizard.init(window.getWorkbench(), (IStructuredSelection) window.getSelectionService().getSelection());
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT);
		return Dialog.OK == dialog.open();
	}
	
	/**
	 * Runs the action.
	 * 
	 * @param action
	 *            The action to run
	 */
	public void run(final IAction action) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		ISelection sel = window.getSelectionService().getSelection();
		if (sel instanceof IStructuredSelection) {
			selection = (IStructuredSelection) sel;
			runWizard(window);
		}
	}

}
