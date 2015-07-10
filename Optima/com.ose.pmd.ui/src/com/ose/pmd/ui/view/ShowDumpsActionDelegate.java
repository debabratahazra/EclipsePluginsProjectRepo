/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui.view;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import com.ose.pmd.ui.DumpPlugin;
import com.ose.system.Target;

public class ShowDumpsActionDelegate implements IViewActionDelegate
{
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
         IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            try
            {
               IViewPart view = page.showView(DumpPlugin.DUMP_VIEW_ID);
               if (view instanceof DumpView)
               {
                  DumpView dumpView = (DumpView) view;
                  dumpView.show(target);
               }
            }
            catch (PartInitException e)
            {
               DumpPlugin.log(e);
            }
         }
      }
   }
}
