package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * Abstract class which can be used to operate an eclipse 
 * Navigator/explorer 
 *   
 *
 * @author can
 *
 */
public class AbstractNavigatorPageObject {

	protected SWTWorkbenchBot bot = new SWTWorkbenchBot();
	private String title;

	/**
	 * 
	 */
	public AbstractNavigatorPageObject(String title) {
		super();
		this.title = title;
	}

	public SWTBotTreeItem selectNode(IPath path) {
		if(path.segmentCount()==0) return null;
		
		SWTBotView navigatorView = bot.viewByTitle(title);
		navigatorView.setFocus();
		final SWTBotTree navTree = navigatorView.bot().tree();
		navigatorView.bot().waitUntil(new DefaultCondition() {			
			@Override
			public boolean test() throws Exception {
				return navTree.isEnabled();
			}
			
			@Override
			public String getFailureMessage() {
				return "Navigator tree is disabled.";
			}
		});
		SWTBotTreeItem item = new TreeObject(navigatorView.bot().tree()).selectTreeItem(path);
		return item;
	}

	public ProjectTreeItem getProjectTreeItem(String projectName) {
		SWTBotView navigatorView = bot.viewByTitle(this.title);
		navigatorView.setFocus();
		if(projectName==null) return null;
		return new ProjectTreeItem(navigatorView.bot().tree().expandNode(projectName, false).widget);		
	}

	public PropertiesPageObject openProjectProperties(String projectName) {
		getProjectTreeItem(projectName).contextMenu("Properties").click();
		return new PropertiesPageObject(bot.activeShell().bot());
	}

}