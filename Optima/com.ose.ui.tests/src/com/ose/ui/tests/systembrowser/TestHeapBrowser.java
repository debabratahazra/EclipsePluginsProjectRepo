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
import com.ose.system.ui.views.heap.HeapView;
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

public class TestHeapBrowser extends AbstractTargetBaseTest
{
   String blockRegex = "alfa*" ;
   String processName = "alfa" ;
   
   @Override
   public void setUp() throws Exception
   {
      super.setUp();
   }

   @Override
   public void tearDown() throws Exception
   {
      super.tearDown();
   }
   
   public void testHeapBrowser() throws Exception
   {
      IUIContext ui = getUI();

      // Open Heap information in Heap Browser View
      ui.contextClick(new TreeItemLocator(getHeapRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Heap Browser");

      ui.wait(new ShellShowingCondition("Heap Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Heap Filter"));

      MiscUtils.waitForJobs(ui);

      // Check Tabs in Heap Browser View
      ui.click(new TabItemLocator("Heap Buffer Charts", new ViewLocator(
            SystemBrowserPlugin.HEAP_VIEW_ID)));
      ui.click(new TabItemLocator("Heap Buffer List", new ViewLocator(
            SystemBrowserPlugin.HEAP_VIEW_ID)));
      ui.click(new PullDownMenuItemLocator("Update", new SWTWidgetLocator(
            ToolItem.class, "", 1, new SWTWidgetLocator(ToolBar.class, 0,
                  new SWTWidgetLocator(Composite.class)))));
      MiscUtils.waitForJobs(ui);

      // Start "Update Automatically" action
      ui.click(new PullDownMenuItemLocator("Update Automatically",
            new SWTWidgetLocator(ToolItem.class, "", 1, new SWTWidgetLocator(
                  ToolBar.class, 0, new SWTWidgetLocator(Composite.class)))));

      ui.assertThat(new PullDownMenuItemLocator("Update Automatically",
            new SWTWidgetLocator(ToolItem.class, "", 1, new SWTWidgetLocator(
                  ToolBar.class, 0, new SWTWidgetLocator(Composite.class))))
            .isSelected());

      // Stop "Update Automatically" action
      ui.click(new PullDownMenuItemLocator("Update Automatically",
            new SWTWidgetLocator(ToolItem.class, "", 1, new SWTWidgetLocator(
                  ToolBar.class, 0, new SWTWidgetLocator(Composite.class)))));
      MiscUtils.waitForJobs(ui);

      // Open Heap Browser Editor
      ui.click(2, new TreeItemLocator(getHeapRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      // Close Heap Editor
      UIUtils.closeVisibleEditor(ui, "heap \\(.+\\)");

      // Click "Filter" button on Heap Browser View
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 0,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellShowingCondition("Heap Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new LabeledTextLocator("Owner Name:"));
      ui.enterText(blockRegex);
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Heap Filter"));
      MiscUtils.waitForJobs(ui);

      // "Show Memory" from Heap Buffer List
      ui.click(new TabItemLocator("Heap Buffer List", new ViewLocator(
            SystemBrowserPlugin.HEAP_VIEW_ID)));

      IWidgetReference[] reference = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));
      String str = null;
      String memoryAddress = null;
      for (IWidgetReference iWidgetReference : reference)
      {
         str = UIUtils.getText(((TableItem) iWidgetReference.getWidget()),
               HeapView.COLUMN_OWNER_NAME);
         if (str.contains(processName))
         {
            memoryAddress = UIUtils.getText(
                  ((TableItem) iWidgetReference.getWidget()),
                  HeapView.COLUMN_ADDRESS);
            break;
         }
      }
      assertNotNull(str);
      assertNotNull(memoryAddress);
      ui.contextClick(new TableCellLocator(str, "Owner Name"), "Show Memory");
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

      // Open Chart
      ui.click(new CTabItemLocator("Heap Browser"));
      ui.click(new TabItemLocator("Heap Buffer Charts", new ViewLocator(
            "com.ose.system.ui.views.heap.HeapView")));

      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Requested Size",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Actual Size",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Bytes / Process",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Buffers / Requested Size",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Buffers / Actual Size",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new SWTWidgetLocator(GridItem.class, "Buffers / Process",
            new ViewLocator("com.ose.system.ui.views.heap.HeapView")));
      ui.click(new MenuItemLocator("File/Close All"));
   }
}
