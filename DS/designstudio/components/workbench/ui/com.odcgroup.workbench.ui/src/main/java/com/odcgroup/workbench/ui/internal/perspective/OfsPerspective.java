package com.odcgroup.workbench.ui.internal.perspective;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.odcgroup.workbench.ui.OfsUICore;

public class OfsPerspective implements IPerspectiveFactory {

	static final public String PERSPECTIVE_ID = "com.odcgroup.workbench.ui.perspectives.ofs";

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {

		// Add "new wizards".
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");

		// Add "show views".
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		IProduct product = Platform.getProduct();
		if (product != null
				&& !(product.getId().equalsIgnoreCase("com.odcgroup.workbench.t24.products.edsT24") || product.getId()
						.equalsIgnoreCase("com.odcgroup.workbench.t24.products.designstudioT24"))) {
			layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		}
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);

		layout.addActionSet("com.odcgroup.workbench.ui.javaactionset");
	}

	public void defineLayout(IPageLayout layout) {

		// Editors are placed for free.
		String editorArea = layout.getEditorArea();

		// Place navigator and outline to left of
		// editor area.
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, (float) 0.26, editorArea);

		left.addView(OfsUICore.ID_NAVIGATOR);

		IProduct product = Platform.getProduct();

		if (product != null
				&& !(product.getId().equalsIgnoreCase("com.odcgroup.workbench.t24.products.edsT24") || product.getId()
						.equalsIgnoreCase("com.odcgroup.workbench.t24.products.designstudioT24"))) {
			IFolderLayout lowerLeft = layout.createFolder("lowerLeft", IPageLayout.BOTTOM, (float) 0.6, "left");
			lowerLeft.addView(IPageLayout.ID_OUTLINE);
		}
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.6, editorArea);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addPlaceholder("org.eclipse.search.ui.views.SearchView");
		bottom.addView("com.odcgroup.server.ui.views.ServerView");
	}

}
