package com.odcgroup.t24.application.importer.ui.action;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.common.importer.ui.action.ModelImportActionProvider;

public class ApplicationImportActionProvider extends ModelImportActionProvider {

	@Override
	protected ModelImportAction createImportAction() {
		return new ApplicationImportAction();
	}

}
