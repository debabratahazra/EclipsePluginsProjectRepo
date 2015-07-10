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

package com.ose.ui.tests.utils;

import org.eclipse.swt.widgets.TreeItem;
import com.ose.prof.ui.ProfilerPlugin;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class ProfilerUtils
{
   public static String getTargetRegex(TreeItem treeItem)
   {
      String name = UIUtils.getText(treeItem);
      return name.trim();
   }

   public static String getNodeName(TreeItem treeItem)
   {
      String name = UIUtils.getText(treeItem);
      int index = name.indexOf("(");
      name = name.substring(0, index - 1);
      return name.trim();
   }

   public static String getNodeAdress(TreeItem treeItem)
   {
      String name = UIUtils.getText(treeItem);
      int firstIndex = name.indexOf("(");
      int lastIndex = name.indexOf(")");
      name = name.substring(firstIndex + 1, lastIndex);
      return name.trim();
   }

   public static void stopProfiling(IUIContext ui, TreeItem item)
         throws Exception
   {
      // Stop profiling
      ui.contextClick(new TableItemLocator(getTargetRegex(item),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Start\\/Stop Profiling");
      MiscUtils.waitForJobs(ui);
   }
   
   public static void removeTargetFromView(IUIContext ui, TreeItem item)
         throws Exception
   {
      // Remove Target
      ui.click(new CTabItemLocator("System Profiler"));
      ui.contextClick(new TableItemLocator(getTargetRegex(item),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }

   public static void toggleTraceButton(IUIContext ui, TreeItem item)
         throws Exception
   {
      ui.click(new CTabItemLocator("System Profiler"));
      ui.contextClick(new TableItemLocator(ProfilerUtils.getTargetRegex(item),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Enable\\/Disable Profiling");
   }

}
