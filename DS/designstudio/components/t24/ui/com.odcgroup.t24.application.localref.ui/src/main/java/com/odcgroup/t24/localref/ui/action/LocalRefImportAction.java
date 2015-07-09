package com.odcgroup.t24.localref.ui.action;

import org.eclipse.ui.IImportWizard;

import com.google.inject.Inject;
import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.localref.ui.Messages;
import com.odcgroup.t24.localref.ui.wizard.LocalRefImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author ssreekanth
 *
 */
public class LocalRefImportAction extends ModelImportAction {
	
	@Inject
	private LocalRefImportWizard wiz;

	@Override
	protected IImportWizard createImportWizard() {
		return wiz;
	}

	public LocalRefImportAction() {
		super(Messages.LocalRefImportAction_name);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif")); //$NON-NLS-1$
	}

}
