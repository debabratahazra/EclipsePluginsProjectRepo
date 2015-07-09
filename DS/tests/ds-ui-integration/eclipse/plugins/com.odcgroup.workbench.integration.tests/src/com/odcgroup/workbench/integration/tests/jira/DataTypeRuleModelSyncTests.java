package com.odcgroup.workbench.integration.tests.jira;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.WidgetResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;
/**
 *
 * @author pkk
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class DataTypeRuleModelSyncTests {

	private static final Logger logger = LoggerFactory.getLogger(DataTypeRuleModelSyncTests.class);
	
	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	
	private static long oldTimeout;
	
	@BeforeClass
	public static void setUp() throws Exception {
		oldTimeout = SWTBotPreferences.TIMEOUT;
		SWTBotPreferences.TIMEOUT = 60000;
	}

	@AfterClass
	public static void tearDown() throws Exception {
		SWTBotPreferences.TIMEOUT = oldTimeout;
		AbstractSWTBotTest.deleteProjects();
	}
	
	@Test
	public void dataTypeRuleSync_DS4104() throws Exception  {
		menu.checkoutProjectFromSVN("https://lausvn/svn/devel/products/OCS/branches/5.1.0/business-models/pms/pms-models");
				
		SWTBotView problemView = bot.viewByTitle("Problems");
		problemView.setFocus();
		final SWTBotTree tree = problemView.bot().tree();

		SWTBotTreeItem problem = null;
		SWTBotTreeItem item = null;
		try {
			item = getRootTreeItem(tree.widget);
			item.expand();
			problem = item.getNode("Design Studio project configuration: " +
					"Data types are not yet synchronized to rule model.");
		} catch(WidgetNotFoundException e) {
			logger.info(e.toString());
			logger.info("Available nodes are:");
			for(String node : item.getNodes()) {
				logger.info(node);
			}
			throw e;
		}
		try {
			problem.contextMenu("Quick Fix").click();
			bot.shell("Quick Fix").bot().button("Finish").click();
		} catch(Exception e) {
			// try again as the console view is sometimes popping up, covering the problem view
			problemView.setFocus();
			item = getRootTreeItem(tree.widget);
			item.expand();
			problem = item.getNode("Design Studio project configuration: " +
					"Data types are not yet synchronized to rule model.");
			problem.contextMenu("Quick Fix").click();
			bot.shell("Quick Fix").bot().button("Finish").click();
		}
		
		SWTBotShell progressShell = bot.shell("Progress Information");
	    bot.waitUntil(shellCloses(progressShell), 120000);
		
		on.selectNode(new Path("pms-models/Rules/internal"));
	}
	
	/**
	 * @param widget
	 * @return
	 * @throws WidgetNotFoundException
	 */
	private SWTBotTreeItem getRootTreeItem(final Tree widget) throws WidgetNotFoundException {
		try {
			new SWTBot().waitUntil(new DefaultCondition() {
				public String getFailureMessage() {
					return "Could not find node "; //$NON-NLS-1$
				}

				public boolean test() throws Exception {
					return getRootItem(widget) != null;
				}
			});
		} catch (TimeoutException e) {
			throw new WidgetNotFoundException("Timed out waiting for tree item ", e); //$NON-NLS-1$
		}
		return new SWTBotTreeItem(getRootItem(widget));
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private TreeItem getRootItem(final Tree widget) {
		return syncExec(bot.getDisplay(), new WidgetResult<TreeItem>() {
			public TreeItem run() {
				TreeItem[] items = widget.getItems();
				if (items.length > 0) {
					return items[0];
				}
				return null;
			}
		});
	}
	
	/**
	 * @param <T>
	 * @param display
	 * @param toExecute
	 * @return
	 */
	private <T> T syncExec(Display display, Result<T> toExecute) {
		return UIThreadRunnable.syncExec(display, toExecute);
	}

	
	

}
