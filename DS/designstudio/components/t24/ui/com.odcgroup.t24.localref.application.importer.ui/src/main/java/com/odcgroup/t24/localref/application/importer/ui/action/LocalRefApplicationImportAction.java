package com.odcgroup.t24.localref.application.importer.ui.action;

import org.eclipse.ui.IImportWizard;

import com.google.inject.Inject;
import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.localref.application.importer.ui.Messages;
import com.odcgroup.t24.localref.application.importer.ui.wizard.LocalRefApplicationImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class LocalRefApplicationImportAction extends ModelImportAction {

	@Inject
	private LocalRefApplicationImportWizard wiz;

	@Override
	protected IImportWizard createImportWizard() {
		return wiz;
	}

	public LocalRefApplicationImportAction() {
		// TODO Message
		super(Messages.LocalRefApplicationImportAction_name);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif")); //$NON-NLS-1$
	}
}
