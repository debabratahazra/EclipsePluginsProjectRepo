package com.odcgroup.t24.version.importer.ui.action;

import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.common.importer.ui.action.ModelImportActionProvider;

public class VersionImportActionProvider extends ModelImportActionProvider {

	@Override
	protected ModelImportAction createImportAction() {
		return new VersionImportAction();
	}

}
