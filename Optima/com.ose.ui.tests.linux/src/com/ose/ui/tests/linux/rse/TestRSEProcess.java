package com.ose.ui.tests.linux.rse;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.ui.tests.AbstractRSEBaseTest;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ContributedToolItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

/**
 * TestRSEProcess class validates Processes work flow. Class connects to target
 * and validates the Processes.
 * 
 * @author Deepak Ramesh [Dera]
 * @date 24-09-2012
 * 
 */
public class TestRSEProcess extends AbstractRSEBaseTest
{
	
   private final String ipaddress = System.getProperty("ip");
   
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

      ui.click(new TreeItemLocator(ipaddress + "/Processes", new ViewLocator(
            "org.eclipse.rse.ui.view.systemView")));
      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.refresh"));
      ui.click(new TreeItemLocator(ipaddress + "/Processes/All Processes",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      // Validates the items of All Processes
      final IWidgetReference locator = (IWidgetReference) ui
            .find(new TreeItemLocator(ipaddress + "/Processes/All Processes",
                  new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      Display.getDefault().asyncExec(new Runnable()
      {
         public void run()
         {
            final TreeItem treeItemWidget = (TreeItem) locator.getWidget();
            assertTrue(treeItemWidget.getItemCount() > 0);
         }
      });

      // Validates the items of My Processes
      ui.click(new TreeItemLocator(ipaddress + "/Processes/My Processes",
            new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      final IWidgetReference myProcessLocator = (IWidgetReference) ui
            .find(new TreeItemLocator(ipaddress + "/Processes/My Processes",
                  new ViewLocator("org.eclipse.rse.ui.view.systemView")));

      Display.getDefault().asyncExec(new Runnable()
      {
         public void run()
         {
            final TreeItem treeItemWidget = (TreeItem) myProcessLocator
                  .getWidget();
            assertTrue(treeItemWidget.getItemCount() > 0);
         }
      });

      // Close the Eclipse
      ui.click(new MenuItemLocator("File/Exit"));
      ui.wait(new ShellDisposedCondition("Eclipse SDK"));
      ui.wait(new ShellDisposedCondition("Progress Information"));
   }
}
