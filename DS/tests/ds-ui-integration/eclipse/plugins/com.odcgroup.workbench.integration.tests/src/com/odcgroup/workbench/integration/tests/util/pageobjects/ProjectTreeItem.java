package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * A PageObject that represents a project node in the Odyssey Navigator tree
 *
 * @author Kai Kreuzer 
 */
public class ProjectTreeItem extends SWTBotTreeItem {

	public ProjectTreeItem(TreeItem treeItem) throws WidgetNotFoundException {
		super(treeItem);
	}
	
	public void switchToTechnicalType() {
		this.contextMenu("Project Type").menu("Technical").click();
	}

}
