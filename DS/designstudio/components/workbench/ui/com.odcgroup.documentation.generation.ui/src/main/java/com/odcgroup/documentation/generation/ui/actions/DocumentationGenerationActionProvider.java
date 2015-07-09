package com.odcgroup.documentation.generation.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class DocumentationGenerationActionProvider extends CommonActionProvider {

	private DocumentationGenerationAction documentationGenerationAction;

	public DocumentationGenerationActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		documentationGenerationAction = new DocumentationGenerationAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup("group.generate", documentationGenerationAction);
	}

}
