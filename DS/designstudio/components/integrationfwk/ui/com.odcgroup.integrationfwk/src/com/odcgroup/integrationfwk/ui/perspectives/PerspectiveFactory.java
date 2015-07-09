package com.odcgroup.integrationfwk.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class PerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {
		/**
		 * Add wizards shortcuts to the perspective. These shortcuts will appear
		 * whenever the user switches to this perspective, right-click on an
		 * item and selects New.
		 */
		layout.addNewWizardShortcut("com.temenos.tws.consumer.plugin.wizard.NewEventWizard");
		layout.addNewWizardShortcut("com.temenos.tws.consumer.plugin.wizard.NewFlowWizard");
		layout.addNewWizardShortcut("com.temenos.tws.consumer.plugin.wizard");
	}

	public void defineLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();
		layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT,
				0.25f, editorArea);
		IFolderLayout bottom = layout.createFolder("bottom",
				IPageLayout.BOTTOM, 0.66f, editorArea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
	}

}
