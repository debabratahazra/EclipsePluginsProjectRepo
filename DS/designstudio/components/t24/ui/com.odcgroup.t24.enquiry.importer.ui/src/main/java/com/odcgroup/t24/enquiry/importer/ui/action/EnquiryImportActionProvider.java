package com.odcgroup.t24.enquiry.importer.ui.action;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.common.importer.ui.action.ModelImportActionProvider;

public class EnquiryImportActionProvider extends ModelImportActionProvider {

	@Override
	protected ModelImportAction createImportAction() {
		return new EnquiryImportAction();
	}

}
