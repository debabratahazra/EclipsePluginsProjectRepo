/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import com.ose.system.ui.SystemBrowserPlugin;

public class ProfilerPerspective implements IPerspectiveFactory
{
   public void createInitialLayout(IPageLayout layout)
   {
      String editorArea;
      IFolderLayout topLeft;
      IFolderLayout bottomLeft;
      IFolderLayout bottom;
      IFolderLayout topRight;

      editorArea = layout.getEditorArea();

      topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
      topLeft.addView(SystemBrowserPlugin.SYSTEM_VIEW_ID);

      bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.70f, "topLeft");
      bottomLeft.addView(IPageLayout.ID_PROJECT_EXPLORER);

      bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.70f, editorArea);
      bottom.addView(ProfilerPlugin.PROFILER_VIEW_ID);
      bottom.addPlaceholder(SystemBrowserPlugin.LOAD_MODULE_VIEW_ID);
      bottom.addView(IPageLayout.ID_TASK_LIST);
      bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
      bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
      bottom.addView(IPageLayout.ID_PROP_SHEET);
      bottom.addView(IPageLayout.ID_PROGRESS_VIEW);

      topRight = layout.createFolder("topRight", IPageLayout.RIGHT, 0.75f, editorArea);
      topRight.addView(IPageLayout.ID_OUTLINE);

      layout.addShowViewShortcut(SystemBrowserPlugin.SYSTEM_VIEW_ID);
      layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
      layout.addShowViewShortcut(ProfilerPlugin.PROFILER_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.LOAD_MODULE_VIEW_ID);
      layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
      layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
      layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
      layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
      layout.addShowViewShortcut(IPageLayout.ID_PROGRESS_VIEW);
      layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);

      layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
      layout.addActionSet("org.eclipse.debug.ui.launchActionSet");
      layout.addActionSet("org.eclipse.debug.ui.profileActionSet");
      layout.addActionSet("org.eclipse.cdt.ui.SearchActionSet");

      layout.addActionSet("org.eclipse.cdt.ui.NavigationActionSet");
      layout.addActionSet("org.eclipse.cdt.ui.OpenActionSet");
      layout.addActionSet("org.eclipse.cdt.ui.CodingActionSet");

      layout.addShowInPart(SystemBrowserPlugin.SYSTEM_VIEW_ID);
      layout.addShowInPart(IPageLayout.ID_PROJECT_EXPLORER);
   }
}
