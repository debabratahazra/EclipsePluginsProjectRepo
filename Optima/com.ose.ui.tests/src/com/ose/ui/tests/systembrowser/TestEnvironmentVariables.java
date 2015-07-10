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

import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.swt.locator.eclipse.EditorLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.SectionLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.locator.TableItemLocator;

public class TestEnvironmentVariables extends AbstractTargetBaseTest
{
   String blockName = ".*alfa.*";
   
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testSetEnvironmentVariable() throws Exception
   {
      IUIContext ui = getUI();
      
      MiscUtils.waitForJobs(ui);
      ui.contextClick(
            new TreeItemLocator(
                    getBlockRegex(LOAD_MODULE_NAME, blockName),
                    new ViewLocator(
                            SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Set Environment Variable...");

      ui.wait(new ShellShowingCondition("Set Environment Variable"));
      
      ui.enterText("anEnvironmentVariable");
      ui.keyClick(WT.TAB);
      ui.enterText("aValue");
      
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Set Environment Variable"));
      MiscUtils.waitForJobs(ui);
      ui.click(2, new TreeItemLocator(
                    getBlockRegex(LOAD_MODULE_NAME, blockName),
                    new ViewLocator(
                            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      EditorLocator targetEditorLocator = new EditorLocator(blockName);
      ui.wait(targetEditorLocator.isVisible());
      
      try
      {
         ui.find(new TableItemLocator("anEnvironmentVariable",
               new SectionLocator(".*Variables.*")));
      }
      catch (WidgetSearchException e)
      {
         assertTrue(false);
      }
   }
}
