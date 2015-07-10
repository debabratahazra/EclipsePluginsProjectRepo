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
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestPoolOptimizer extends AbstractTargetBaseTest
{
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }
   
   public void testPoolOptimizer() throws Exception
   {
      IUIContext ui = getUI();

      ui.contextClick(new TreeItemLocator(getPoolRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Pool Optimizer");
      
      // Wait for Pool Selection dialog box to show up
      ui.wait(new ShellShowingCondition("Pool Selection"));
      
      // Click OK to close the dialog box
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Pool Selection"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);   
   }
}
