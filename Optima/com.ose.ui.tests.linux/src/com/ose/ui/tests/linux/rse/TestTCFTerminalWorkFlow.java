package com.ose.ui.tests.linux.rse;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import com.ose.ui.tests.AbstractRSEBaseTest;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.LabeledLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ContributedToolItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import org.eclipse.tm.internal.terminal.textcanvas.TextCanvas;

/**
 * TestTCFTerminalWorkFlow class validates TCF Terminal work flow. Class
 * launches the terminal and creates new folder using Linux commands. The newly
 * created folder are validated in Root tree present under Files in Remote
 * System View.
 * 
 * Before running this test update the VM arguments with IP Address, User name
 * and Password in below format.
 * 
 * Format: -Dip=Enter_ipaddress -Dusername=Enter_username
 * -Dpassword=Enter_password
 * 
 * @author Deepak Ramesh [Dera]
 * @date 24-09-2012
 * 
 */
public class TestTCFTerminalWorkFlow extends AbstractRSEBaseTest
{
   private final String ipaddress = System.getProperty("ip");

   private final String username = System.getProperty("username");

   private final String password = System.getProperty("password");

   private final String testRSE = "A_Test_RSE_Folder";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();
   }

   public void testLaunchTerminalWorkflow() throws Exception
   {
      IUIContext ui = getUI();
      ui.contextClick(new SWTWidgetLocator(Tree.class, new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")), "New/Connection...");
      ui.wait(new ShellShowingCondition("New Connection"));
      ui.click(new TreeItemLocator("General/TCF"));
      ui.click(new ButtonLocator("Next >"));
      ui.click(new LabeledLocator(Combo.class, "Host name:"));
      ui.keyClick(WT.HOME);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.enterText(ipaddress);
      ui.click(new ButtonLocator("Finish"));

      ui.wait(new ShellDisposedCondition("New Connection"));

      ui.click(new TreeItemLocator(ipaddress + "/Terminals", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.contextClick(new TreeItemLocator(ipaddress + "/Terminals",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")),
            "Launch Terminal ");
      ui.wait(new ShellShowingCondition("Enter Password"));
      ui.click(new LabeledTextLocator("User ID:"));
      ui.keyClick(WT.HOME);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.keyClick(WT.DEL);
      ui.enterText(username);
      ui.click(new LabeledTextLocator("Password (optional):"));
      ui.enterText(password);
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Enter Password"));
      
      
      ui.click(new XYLocator(new SWTWidgetLocator(TextCanvas.class,
            new ViewLocator("org.eclipse.rse.terminals.ui.view.TerminalView")),
            50, 51));
      ui.enterText("cd");
      ui.keyClick(WT.CR);
      ui.enterText("mkdir " + testRSE);
      ui.keyClick(WT.CR);

      ui.click(new TreeItemLocator(ipaddress + "/Files/Root", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.refresh"));
      ui.click(new TreeItemLocator(ipaddress + "/Files/Root/\\\\/",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      ui.contextClick(new TreeItemLocator(ipaddress + "/Files/Root/\\\\/",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")),
            "Show in Table");

      try
      {
         ui.click(2, new TableItemLocator(testRSE, new ViewLocator(
               "org.eclipse.rse.ui.view.systemTableView")));
         ui.click(new SWTWidgetLocator(ToolItem.class, "", 3,
               new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                     Composite.class))));
         assertTrue(true);
      }
      catch (Exception e)
      {
         assertFalse(true);
      }

      ui.click(new TreeItemLocator(ipaddress + "/Terminals", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.contextClick(new TreeItemLocator(ipaddress + "/Terminals",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")),
            "Launch Terminal ");
      ui.click(new XYLocator(new SWTWidgetLocator(TextCanvas.class,
            new ViewLocator("org.eclipse.rse.terminals.ui.view.TerminalView")),
            143, 50));
      ui.enterText("cd");
      ui.keyClick(WT.CR);
      ui.enterText("rm -rf " + testRSE);
      ui.keyClick(WT.CR);
      ui.click(new TreeItemLocator(ipaddress + "/Files/Root/\\\\/",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")));
      ui.click(new TreeItemLocator(ipaddress + "/Files/Root/", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.refresh"));
      ui.click(new TreeItemLocator(ipaddress + "/Files/Root", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.click(new TreeItemLocator(ipaddress + "/Files/Root/\\\\/",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      ui.contextClick(new TreeItemLocator(ipaddress + "/Files/Root/\\\\/",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")),
            "Show in Table");

      try
      {
         ui.click(new TableItemLocator(testRSE, new ViewLocator(
               "org.eclipse.rse.ui.view.systemTableView")));
         assertFalse(true);
      }
      catch (Exception e)
      {
         assertTrue(true);
      }

   }
}
