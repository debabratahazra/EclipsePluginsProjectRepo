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

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class ResourceUtils
{
   private static final String PROJECT_EXPLORER_ID = "org.eclipse.ui.navigator.ProjectExplorer"; 

   public static void createNewProject(IUIContext ui, String projectName)
         throws Exception
   {
      ui.click(new MenuItemLocator("File/New/Project..."));
      ui.wait(new ShellShowingCondition("New Project"));
      ui.click(new FilteredTreeItemLocator("General/Project"));
      ui.click(new ButtonLocator("&Next >"));
      ui.enterText(projectName);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Project"));
   }
   
   public static void saveData(IUIContext ui, String projectName,
         String fileName) throws Exception
   {
      String path = getProjectPath(projectName) + fileName;
      ui.enterText(path);
      ui.keyClick(WT.CR);
   }
   
   public static boolean isWindows()
   {
      return System.getProperty("os.name").startsWith("Windows");
   }
   
   public static void openFile(IUIContext ui, String projectName,
         String fileName) throws Exception
   {
      String path = getProjectPath(projectName) + fileName;
      ui.enterText(path);
      ui.keyClick(WT.CR);
   }
   
   private static String getProjectPath(String projectName)
   {
      if (isWindows())
      {
         String workspacePath = Platform.getInstanceLocation().getURL().getPath()
               .substring(1);
         String projectPath = workspacePath + projectName + "/";
         projectPath = projectPath.replaceAll("/", "\\\\");
         return projectPath;
      }
      return "/" + Platform.getInstanceLocation().getURL().getPath() + projectName + "/";
      
   }
   
   public static void openFileFromWorkspace(IUIContext ui, String path,
         String editor) throws Exception
   {
      ui.contextClick(new TreeItemLocator(path, new ViewLocator(
            PROJECT_EXPLORER_ID)), "Open With/" + editor);
      MiscUtils.waitForJobs(ui);
   }
   
   public static void deleteProject(IUIContext ui, String projectName) throws Exception
   {
      ui.click(new CTabItemLocator("Project Explorer"));
      ui.contextClick(new TreeItemLocator(projectName, new ViewLocator(
            PROJECT_EXPLORER_ID)), "Delete");
      ui.wait(new ShellDisposedCondition("Progress Information"));
      ui.wait(new ShellShowingCondition("Delete Resources"));
      ui.click(new ButtonLocator(
              "&Delete project contents on disk (cannot be undone)"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Delete Resources"));
      MiscUtils.waitForJobs(ui);
   }

   
   public static void cleanSystemProfilerView(IUIContext ui) throws Exception
   {
      // Remove multiple targets from System Profiler view
      ui.click(new CTabItemLocator("System Profiler"));
      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));
      for (IWidgetReference locator : items)
      {
         ui.click(locator);
         ui.click(new SWTWidgetLocator(ToolItem.class, "", 1,
               new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                     Composite.class))));
         MiscUtils.waitForJobs(ui);
      }

   }
}
