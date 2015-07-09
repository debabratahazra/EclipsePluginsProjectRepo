package com.odcgroup.workbench.ui.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizard;

import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class ImportProjectHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IImportWizard wizard = new ExternalProjectImportWizard() {
			public void createPageControls(Composite pageContainer) {
				super.createPageControls(pageContainer);
				PlatformUI.getWorkbench().getHelpSystem().setHelp(pageContainer, IOfsHelpContextIds.IMPORT_PROJECT);
			}
		};
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		wizard.init(window.getWorkbench(), null);
		
		final WizardDialog dialog= new WizardDialog(window.getShell(), wizard);
		dialog.create();
		dialog.open();
		return null;
	}

}
