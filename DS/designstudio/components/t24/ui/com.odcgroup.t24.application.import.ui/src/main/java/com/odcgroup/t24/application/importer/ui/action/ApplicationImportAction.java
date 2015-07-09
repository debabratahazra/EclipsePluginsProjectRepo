package com.odcgroup.t24.application.importer.ui.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.application.importer.ui.Messages;
import com.odcgroup.t24.application.importer.ui.wizard.ApplicationImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class ApplicationImportAction extends ModelImportAction {

	@Override
	protected IImportWizard createImportWizard() {
		return new ApplicationImportWizard();
	}

	public ApplicationImportAction() {
		super(Messages.ApplicationImportAction_name);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif")); //$NON-NLS-1$
	}

}
