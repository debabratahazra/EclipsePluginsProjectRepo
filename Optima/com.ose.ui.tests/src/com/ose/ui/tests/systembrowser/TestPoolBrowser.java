/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.ui.tests.systembrowser;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.GridItem;
import com.ose.system.ui.views.pool.PoolView;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TabItemLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PullDownMenuItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestPoolBrowser extends AbstractTargetBaseTest
{
   String processName = "beta_3" ;
   String processRegex = "beta*";
   
   public void setUp() throws Exception
   {
      super.setUp();
   }

   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testPoolBrowser() throws Exception
   {
      IUIContext ui = getUI();

      /** Test Pool Browser View */
      ui.contextClick(new TreeItemLocator(getPoolRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Pool Browser");
      ui.wait(new ShellShowingCondition("Pool Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      // Check Tabs in Pool Browser Filter Dialog
      ui.click(new TabItemLocator("Stack Properties"));
      ui.click(new TabItemLocator("Signal Properties"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Pool Filter"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      // Check Tabs in Pool Browser View
      ui.click(new TabItemLocator("Signal Charts", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));
      ui.click(new TabItemLocator("Stack Charts", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));
      ui.click(new TabItemLocator("Signal List", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));
      ui.click(new TabItemLocator("Stack List", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));

      // Check "Update" and "Update Automatically" action buttons
      ui.click(new PullDownMenuItemLocator("Update", new SWTWidgetLocator(
            ToolItem.class, "", 1, new SWTWidgetLocator(ToolBar.class, 0,
                  new SWTWidgetLocator(Composite.class)))));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.click(new PullDownMenuItemLocator("Update Automatically",
            new SWTWidgetLocator(ToolItem.class, "", 1, new SWTWidgetLocator(
                  ToolBar.class, 0, new SWTWidgetLocator(Composite.class)))));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.assertThat("Update Automatically Action isn't set properly.",
            new PullDownMenuItemLocator("Update Automatically",
                  new SWTWidgetLocator(ToolItem.class, "", 1,
                        new SWTWidgetLocator(ToolBar.class, 0,
                              new SWTWidgetLocator(Composite.class))))
                  .isSelected());

      ui.click(new PullDownMenuItemLocator("Update Automatically",
            new SWTWidgetLocator(ToolItem.class, "", 1, new SWTWidgetLocator(
                  ToolBar.class, 0, new SWTWidgetLocator(Composite.class)))));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.click(2, new TreeItemLocator(getPoolRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      UIUtils.closeVisibleEditor(ui, "pool \\(.+\\)");

      // Filter operation
      ui.click(new CTabItemLocator("Pool Browser"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 0,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellShowingCondition("Pool Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new TabItemLocator("Signal Properties"));
      ui.click(new LabeledTextLocator("Owner Name:"));
      ui.enterText(processRegex);
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Pool Filter"));
      MiscUtils.waitForJobs(ui);

      // "Show Memory" operation
      ui.click(new CTabItemLocator("Pool Browser"));
      ui.click(new TabItemLocator("Signal List", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));

      IWidgetReference[] reference = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));
      String str = null;
      String memoryAddress = null;
      for (IWidgetReference iWidgetReference : reference)
      {
         str = UIUtils.getText(((TableItem) iWidgetReference.getWidget()),
               PoolView.COLUMN_SIGNAL_OWNER_NAME);
         if (processName.equalsIgnoreCase(str))
         {
            memoryAddress = UIUtils.getText(
                  ((TableItem) iWidgetReference.getWidget()),
                  PoolView.COLUMN_SIGNAL_ADDRESS);
            break;
         }
      }
      assertNotNull(str);
      assertNotNull(memoryAddress);
      ui.contextClick(new TableCellLocator(str,
            (PoolView.COLUMN_SIGNAL_OWNER_NAME + 1)), "Show Memory");
      MiscUtils.waitForJobs(ui);

      memoryAddress = memoryAddress.substring(2, memoryAddress.length() - 1);
      memoryAddress = memoryAddress + "0";
      for (int i = memoryAddress.length(); i < 8; i++)
      {
         memoryAddress = "0" + memoryAddress;
      }

      try
      {
         ui.find(new TableItemLocator(memoryAddress));
      }
      catch (WidgetSearchException e)
      {
         assertTrue(false);
      }

      // Chart show
      ui.click(new CTabItemLocator("Pool Browser"));
      ui.click(new TabItemLocator("Signal Charts", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Signal Number",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Signal Size",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Buffer Size",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Process",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));

      ui.click(new TabItemLocator("Stack Charts", new ViewLocator(
            SystemBrowserPlugin.POOL_VIEW_ID)));
      ui.click(new SWTWidgetLocator(GridItem.class, "Stack Usage / Process",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Process",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Buffer Size",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Stacks / Buffer Size",
            new ViewLocator("com.ose.system.ui.views.pool.PoolView")));
      ui.click(new MenuItemLocator("File/Close All"));

      MiscUtils.waitForJobs(ui);
   }
}
