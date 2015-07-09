package com.odcgroup.workbench.generation.ui.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class CodeGenerationActionProvider extends CommonActionProvider {

	private CodeGenerationAction codeGenerationAction;

	public CodeGenerationActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		codeGenerationAction = new CodeGenerationAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, codeGenerationAction);
	}
}
