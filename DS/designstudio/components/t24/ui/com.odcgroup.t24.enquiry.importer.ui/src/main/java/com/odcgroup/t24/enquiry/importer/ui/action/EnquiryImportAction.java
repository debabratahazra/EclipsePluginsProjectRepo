package com.odcgroup.t24.enquiry.importer.ui.action;

import org.eclipse.ui.IImportWizard;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.enquiry.importer.ui.Messages;
import com.odcgroup.t24.enquiry.importer.ui.wizard.EnquiryImportWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class EnquiryImportAction extends ModelImportAction {

	@Override
	protected IImportWizard createImportWizard() {
		return new EnquiryImportWizard();
	}


	public EnquiryImportAction() {
		super(Messages.EnquiryImportAction_name);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif")); //$NON-NLS-1$
	}

}
