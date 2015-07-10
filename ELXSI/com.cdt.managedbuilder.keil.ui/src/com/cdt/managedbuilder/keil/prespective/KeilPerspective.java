package com.cdt.managedbuilder.keil.prespective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class KeilPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		
		String editorArea=layout.getEditorArea();
		
		//Add View Item in Keil Prespective.
		IFolderLayout left=layout.createFolder("left", IPageLayout.LEFT, 0.25F, editorArea);
		left.addView("org.eclipse.cdt.ui.CView");
		left.addPlaceholder("org.eclipse.ui.navigator.ProjectExplorer");
		
		IFolderLayout bottom =layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75F, editorArea);
		bottom.addView("org.eclipse.ui.console.ConsoleView");
		bottom.addPlaceholder(IPageLayout.ID_RES_NAV);
		
		
				
		
		IFolderLayout right=layout.createFolder("right", IPageLayout.RIGHT, 0.75F, editorArea);
		right.addView(IPageLayout.ID_PROP_SHEET);
		right.addPlaceholder(IPageLayout.ID_OUTLINE);		
		right.addPlaceholder("org.eclipse.cdt.make.ui.views.MakeView");
		
		
		//Add Fast View Item in Keil Perspective
		layout.addFastView("org.eclipse.cdt.ui.includeBrowser",0.25F);
		layout.addFastView("org.eclipse.search.SearchResultView", 0.25F);
		layout.addFastView(IPageLayout.ID_PROBLEM_VIEW, 0.25F);
		layout.addFastView(IPageLayout.ID_TASK_LIST, 0.25F);
		
		
		//Add View Shortcut in Menu
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut("org.eclipse.cdt.ui.CView");
		layout.addShowViewShortcut("org.eclipse.ui.console.ConsoleView");
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer");
		layout.addShowViewShortcut("org.eclipse.cdt.ui.includeBrowser");
		layout.addShowViewShortcut("org.eclipse.search.SearchResultView");
		layout.addShowViewShortcut("org.eclipse.cdt.make.ui.views.MakeView");
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		
		
		
		//Add Keil Project Wizard in our Keil Prespective
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.keilWizard");
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.sourceFileWizard");
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.headerFileWizard");
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.fileWizard");
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.sourceFolderWizard");
		layout.addNewWizardShortcut("com.cdt.managedbuilder.keil.ui.folderWizard");
		
		
		//Add ActionSet				
		//layout.addActionSet("com.cdt.managedbuilder.keil.ui.sourceActionSet");			
	}

}
