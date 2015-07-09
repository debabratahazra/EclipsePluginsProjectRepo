package com.odcgroup.t24.localref.ui.action;

import com.google.inject.Inject;
import com.odcgroup.t24.common.importer.ui.action.ModelImportAction;
import com.odcgroup.t24.common.importer.ui.action.ModelImportActionProvider;

/**
 * @author ssreekanth
 *
 */
public class LocalRefImportActionProvider extends ModelImportActionProvider {

	@Inject
	private LocalRefImportAction action;
	@Override
	protected ModelImportAction createImportAction() {
		return action;
	}

}
