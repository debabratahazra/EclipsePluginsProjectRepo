package com.odcgroup.t24.version.importer.ui.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.version.importer.ui.Messages;
import com.odcgroup.t24.version.importer.ui.wizard.VersionImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class VersionImportAction extends ModelImportAction {

	@Override
	protected IImportWizard createImportWizard() {
		return new VersionImportWizard();
	}

	public VersionImportAction() {
		super(Messages.VersionImportAction_name);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif")); //$NON-NLS-1$
	}

}
