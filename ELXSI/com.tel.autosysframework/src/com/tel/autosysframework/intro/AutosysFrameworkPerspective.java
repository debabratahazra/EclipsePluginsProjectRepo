package com.tel.autosysframework.intro;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class AutosysFrameworkPerspective implements IPerspectiveFactory {


	public void createInitialLayout(IPageLayout layout) {
		
		String editorArea=layout.getEditorArea();
			
		
		//Add View Item in Autosys Framework Perspective.
		
		IFolderLayout right=layout.createFolder("right", IPageLayout.RIGHT, 0.67F, editorArea);
		right.addView("com.tel.autosysframework.generalconfigure");	
		
		IFolderLayout left=layout.createFolder("left", IPageLayout.LEFT, 0.22F, editorArea);
		left.addView(IPageLayout.ID_RES_NAV);
		
		IFolderLayout bottom =layout.createFolder("bottom", IPageLayout.BOTTOM, 0.8F, editorArea);		
		//bottom.addView("com.tel.autosysframework.testvector");
		bottom.addView("org.eclipse.ui.console.ConsoleView");
		bottom.addPlaceholder(IPageLayout.ID_PROP_SHEET);		
		
		//Add View Shortcut in Menu
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut("org.eclipse.ui.console.ConsoleView");
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut("com.tel.autosysframework.configure");
		layout.addShowViewShortcut("org.eclipse.gef.ui.palette_view");
		//layout.addShowViewShortcut("com.tel.autosysframework.testvector");
		layout.addShowViewShortcut("com.tel.autosysframework.generalconfigure");
		
		
		//Add New Wizard Shortcut
		layout.addNewWizardShortcut("com.tel.autosysframework.autosys.wizard.new.file");
		layout.addNewWizardShortcut("com.tel.autosysframework.projectWizard");
		
		//Add ActionSet
		layout.addActionSet("com.tel.autosysframework.autosysframeworkrun");		
		layout.addActionSet("com.tel.autosysframework.testVectorActionSet");
		layout.addActionSet("com.tel.autosysframework.reset");
		layout.addActionSet("com.tel.autosysframework.drawgraph");
		layout.addActionSet("com.tel.autosysframework.comparegraph");

	}

}
