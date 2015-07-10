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

import org.eclipse.swt.widgets.TreeItem;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestSystemParameters extends AbstractTargetBaseTest
{
   String blockRegex = ".*alfa.*";
   
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testSetSystemParameter() throws Exception
   {
      IUIContext ui = getUI();
      
      ui.contextClick(new TreeItemLocator(
            getTargetRegex(),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Set System Parameter...");
            
      ui.wait(new ShellShowingCondition("Set System Parameter"));
      ui.click(new LabeledTextLocator("Name:"));
      ui.enterText("aSystemParameter");
      ui.click(new LabeledTextLocator("Value:"));
      ui.enterText("thisIsAValue");
      
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Set System Parameter"));
      
      MiscUtils.waitForJobs(ui);

      // Find Target node and double click to open editor
      IWidgetReference reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getTargetRegex(), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      TreeItem item = (TreeItem) reference.getWidget();
      
      try {
         ui.find(new TableItemLocator("aSystemParameter"));
         ui.assertThat((new TableCellLocator("aSystemParameter", "Value")
               .hasText("thisIsAValue")));
      } catch(WidgetSearchException e) {assertTrue(false);}   
      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));
   }
   
   private String getEditorRegex(TreeItem treeItem)
   {
     String name = UIUtils.getText(treeItem);
     return name.trim();
   }
}
