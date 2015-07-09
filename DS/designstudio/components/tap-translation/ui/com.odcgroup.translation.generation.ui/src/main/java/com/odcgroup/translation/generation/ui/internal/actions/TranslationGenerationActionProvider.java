package com.odcgroup.translation.generation.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class TranslationGenerationActionProvider extends CommonActionProvider {

	private TranslationGenerationAction codeGenerationAction;

	public TranslationGenerationActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		codeGenerationAction = new TranslationGenerationAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, codeGenerationAction);
	}
}
