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

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.TreeData;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestOperation extends AbstractTargetBaseTest
{
   String blockRegex = ".*alfa.*";

   String processRegex = ".*alfa_0.*";

   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testOperationBlockStopStart() throws Exception
   {
      boolean pass = false;
      IUIContext ui = getUI();

      ui.click(new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, blockRegex),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      /**
       * Find block node, right click it and select the menu option "Stop"
       */
      ui.contextClick(
            new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, blockRegex),
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Stop");

      ui.click(new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME,
            blockRegex, processRegex), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      // Get the tree data
      LinkedList<TreeData> treeDataList = UIUtils.getTreeDataInView(ui,
            SystemBrowserPlugin.SYSTEM_VIEW_ID);

      ListIterator<TreeData> itr = treeDataList.listIterator();
      int procCount = 6;
      while (itr.hasNext())
      {
         TreeData treeData = (TreeData) itr.next();
         Pattern pattern = Pattern.compile(".*alfa_.*",
               Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher(treeData.getName());

         if (matcher.find())
         {
            if (treeData.getImg().equals(
                  SharedImages
                        .get(SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_STOPPED))
                  || treeData
                        .getImg()
                        .equals(
                              SharedImages
                                    .get(SharedImages.IMG_OBJ_PROCESS_BACKGROUND_STOPPED)))
            {
               procCount--;
               if (procCount <= 0)
               {
                  pass = true;
                  break;
               }
            }
         }
      }
      if (!pass)
      {
         assertTrue(false);
      }

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      // Start the block
      ui.contextClick(
            new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, blockRegex),
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Start");

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);
   }

   public void testOperationProcessStopStart() throws Exception
   {
      boolean pass = false;
      IUIContext ui = getUI();

      // Find process node, right click it and select
      // the menu option "Stop"

      ui.click(new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME,
            blockRegex, processRegex), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);

      ui.contextClick(
            new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, blockRegex,
                  processRegex), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Stop");
      MiscUtils.waitForJobs(ui);

      // Get the tree data
      LinkedList<TreeData> treeDataList = UIUtils.getTreeDataInView(ui,
            SystemBrowserPlugin.SYSTEM_VIEW_ID);

      ListIterator<TreeData> itr = treeDataList.listIterator();

      while (itr.hasNext())
      {
         TreeData treeData = (TreeData) itr.next();
         if (treeData.getName().equalsIgnoreCase("alfa_0"))
         {
            if (treeData.getImg().equals(
                  SharedImages
                        .get(SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_STOPPED)))
            {
               pass = true;
            }
            break;
         }
      }
      if (!pass)
      {
         assertTrue(false);
      }

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.contextClick(
            new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, blockRegex,
                  processRegex), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Start");

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);
   }

   public void testOperationProcessKill() throws Exception
   {
      boolean pass = false;
      IUIContext ui = getUI();

      // Find process node and click it, to make sure target node
      // is expanded
      ui.click(new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME,
            blockRegex, processRegex), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);

      ui.contextClick(
            new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, blockRegex,
                  processRegex), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Kill");
      MiscUtils.waitForJobs(ui);

      // Get the tree data
      LinkedList<TreeData> treeDataList = UIUtils.getTreeDataInView(ui,
            SystemBrowserPlugin.SYSTEM_VIEW_ID);

      ListIterator<TreeData> itr = treeDataList.listIterator();

      while (itr.hasNext())
      {
         TreeData treeData = (TreeData) itr.next();
         if (treeData.getName().equalsIgnoreCase("alfa_0"))
         {
            if (treeData.getImg().equals(
                  SharedImages
                        .get(SharedImages.IMG_OBJ_PROCESS_PRIORITIZED_KILLED)))
            {
               pass = true;
            }
            break;
         }
      }
      if (!pass)
      {
         assertTrue(false);
      }
   }

   public void testOperationBlockKill() throws Exception
   {
      boolean pass = false;
      IUIContext ui = getUI();

      /**
       * Find block node, right click it and select the menu option "Kill"
       */
      ui.contextClick(
            new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, blockRegex),
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Kill");
      MiscUtils.waitForJobs(ui);

      // Get the tree data
      LinkedList<TreeData> treeDataList = UIUtils.getTreeDataInView(ui,
            SystemBrowserPlugin.SYSTEM_VIEW_ID);

      ListIterator<TreeData> itr = treeDataList.listIterator();
      while (itr.hasNext())
      {
         TreeData treeData = (TreeData) itr.next();

         if (treeData.getName().equalsIgnoreCase("alfa"))
         {
            if (treeData.getImg().equals(
                  SharedImages.get(SharedImages.IMG_OBJ_BLOCK_KILLED)))
            {
               pass = true;
               break;
            }
         }
      }
      if (!pass)
      {
         assertTrue(false);
      }

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);
   }
}
