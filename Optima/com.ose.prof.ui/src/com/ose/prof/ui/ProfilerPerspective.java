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

package com.ose.prof.ui;

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

      editorArea = layout.getEditorArea();

      topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
      topLeft.addView(SystemBrowserPlugin.SYSTEM_VIEW_ID);

      bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.65f, "topLeft");
      bottomLeft.addView("org.eclipse.ui.navigator.ProjectExplorer");

      bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, editorArea);
      bottom.addView(ProfilerPlugin.PROFILER_VIEW_ID);
      bottom.addView("org.eclipse.debug.ui.DebugView");
      bottom.addView(IPageLayout.ID_PROP_SHEET);
      bottom.addView("org.eclipse.ui.views.ProgressView");

      layout.addShowViewShortcut(SystemBrowserPlugin.SYSTEM_VIEW_ID);
      layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer");
      layout.addShowViewShortcut(ProfilerPlugin.PROFILER_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.LOAD_MODULE_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.POOL_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.POOL_OPTIMIZER_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.HEAP_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.BLOCK_VIEW_ID);
      layout.addShowViewShortcut(SystemBrowserPlugin.PROCESS_VIEW_ID);
      layout.addShowViewShortcut("org.eclipse.debug.ui.DebugView");
      layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
      layout.addShowViewShortcut("org.eclipse.ui.views.ProgressView");
      layout.addShowViewShortcut("org.eclipse.debug.ui.MemoryView");

      layout.addShowInPart(SystemBrowserPlugin.SYSTEM_VIEW_ID);
      layout.addShowInPart("org.eclipse.ui.navigator.ProjectExplorer");
   }
}
