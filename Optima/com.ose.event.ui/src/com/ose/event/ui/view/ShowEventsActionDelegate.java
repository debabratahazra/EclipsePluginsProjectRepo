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

package com.ose.event.ui.view;

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
import com.ose.event.ui.EventPlugin;
import com.ose.system.SystemModelNode;

public class ShowEventsActionDelegate implements IViewActionDelegate
{
   private static final String EVENT_PERSPECTIVE_ID =
         "com.ose.event.ui.EventPerspective";

   private static final String PREF_SWITCH_EVENT_PERSPECTIVE =
         "pref_switch_event_perspective";

   private static final String SWITCH_EVENT_PERSPECTIVE_MESSAGE =
         "The Log Manager can be opened in the current perspective " +
         "or in its own Log Management perspective.\n\nDo you want to " +
         "open the Log Manager in the Log Management perspective?";

   private SystemModelNode node;

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
      node = ((obj instanceof SystemModelNode) ? ((SystemModelNode) obj) : null);
   }

   public void run(IAction action)
   {
      if ((node != null) && !node.isKilled())
      {
         switchPerspective();
         IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            try
            {
               IViewPart view = page.showView(EventPlugin.EVENT_VIEW_ID);
               if (view instanceof EventView)
               {
                  EventView eventView = (EventView) view;
                  eventView.show(node);
               }
            }
            catch (PartInitException e)
            {
               EventPlugin.log(e);
            }
         }
      }
   }

   private void switchPerspective()
   {
      IWorkbench workbench = PlatformUI.getWorkbench();
      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
      IPerspectiveDescriptor perspective = window.getActivePage().getPerspective();

      if (!perspective.getId().equals(EVENT_PERSPECTIVE_ID))
      {
         int response = -1;
         IPreferenceStore prefStore = EventPlugin.getDefault().getPreferenceStore();
         String pref = prefStore.getString(PREF_SWITCH_EVENT_PERSPECTIVE);

         if (pref.equals(MessageDialogWithToggle.PROMPT) || pref.equals(""))
         {
            MessageDialogWithToggle dialog =
               MessageDialogWithToggle.openYesNoQuestion(
                  Display.getDefault().getActiveShell(),
                  "Confirm Perspective Switch",
                  SWITCH_EVENT_PERSPECTIVE_MESSAGE,
                  "Remember my decision",
                  false,
                  prefStore,
                  PREF_SWITCH_EVENT_PERSPECTIVE);
            response = dialog.getReturnCode();
            pref = prefStore.getString(PREF_SWITCH_EVENT_PERSPECTIVE);
         }

         if (pref.equals(MessageDialogWithToggle.ALWAYS) ||
             (response == IDialogConstants.YES_ID))
         {
            try
            {
               workbench.showPerspective(EVENT_PERSPECTIVE_ID, window);
            }
            catch (WorkbenchException e)
            {
               EventPlugin.log(e);
            }
         }
      }
   }
}
