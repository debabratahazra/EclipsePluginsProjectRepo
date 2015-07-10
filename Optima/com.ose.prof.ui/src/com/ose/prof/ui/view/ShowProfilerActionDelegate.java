/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.prof.ui.view;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.Target;

public class ShowProfilerActionDelegate implements IViewActionDelegate
{
   private static final String PROFILER_PERSPECTIVE_ID =
         "com.ose.prof.ui.ProfilerPerspective";

   private static final String PREF_SWITCH_PROFILER_PERSPECTIVE =
         "pref_switch_system_profiler_perspective";

   private static final String SWITCH_PROFILER_PERSPECTIVE_MESSAGE =
         "The System Profiler can be opened in the current perspective " +
         "or in its own System Profiling perspective.\n\nDo you want to " +
         "open the System Profiler in the System Profiling perspective?";

   private Target target;

   public void init(IViewPart view)
   {
      // Nothing to do.
   }

   public void selectionChanged(IAction action, ISelection selection)
   {
      Object obj = null;
      if (selection instanceof IStructuredSelection)
      {
         obj = ((IStructuredSelection) selection).getFirstElement();
      }
      target = ((obj instanceof Target) ? ((Target) obj) : null);
   }

   public void run(IAction action)
   {
      if ((target != null) && !target.isKilled())
      {
         switchPerspective();
         IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            try
            {
               IViewPart view = page.showView(ProfilerPlugin.PROFILER_VIEW_ID);
               if (view instanceof ProfilerView)
               {
                  ProfilerView profilerView = (ProfilerView) view;
                  profilerView.show(target);
               }
            }
            catch (PartInitException e)
            {
               ProfilerPlugin.log(e);
            }
         }
      }
   }

   private void switchPerspective()
   {
      IWorkbench workbench = PlatformUI.getWorkbench();
      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
      IPerspectiveDescriptor perspective = window.getActivePage().getPerspective();

      if (!perspective.getId().equals(PROFILER_PERSPECTIVE_ID))
      {
         int response = -1;
         IPreferenceStore prefStore = ProfilerPlugin.getDefault().getPreferenceStore();
         String pref = prefStore.getString(PREF_SWITCH_PROFILER_PERSPECTIVE);

         if (pref.equals(MessageDialogWithToggle.PROMPT) || pref.equals(""))
         {
            MessageDialogWithToggle dialog =
               MessageDialogWithToggle.openYesNoQuestion(
                  Display.getDefault().getActiveShell(),
                  "Confirm Perspective Switch",
                  SWITCH_PROFILER_PERSPECTIVE_MESSAGE,
                  "Remember my decision",
                  false,
                  prefStore,
                  PREF_SWITCH_PROFILER_PERSPECTIVE);
            response = dialog.getReturnCode();
            pref = prefStore.getString(PREF_SWITCH_PROFILER_PERSPECTIVE);
         }

         if (pref.equals(MessageDialogWithToggle.ALWAYS) ||
             (response == IDialogConstants.YES_ID))
         {
            try
            {
               workbench.showPerspective(PROFILER_PERSPECTIVE_ID, window);
            }
            catch (WorkbenchException e)
            {
               ProfilerPlugin.log(e);
            }
         }
      }
   }
}
