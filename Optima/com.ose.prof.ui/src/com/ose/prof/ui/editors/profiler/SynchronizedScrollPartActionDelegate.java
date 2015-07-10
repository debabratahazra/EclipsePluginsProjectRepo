/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.ui.editors.profiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import com.ose.prof.ui.ProfilerPlugin;

public class SynchronizedScrollPartActionDelegate
   implements IObjectActionDelegate
{
   private static final String ADD =
      "com.ose.prof.ui.editors.profiler.AddSynchronizedScrollPartAction";

   private static final String REMOVE =
      "com.ose.prof.ui.editors.profiler.RemoveSynchronizedScrollPartAction";

   private final List files = new ArrayList();

   public void setActivePart(IAction action, IWorkbenchPart targetPart)
   {
      // Nothing to do.
   }

   public void selectionChanged(IAction action, ISelection selection)
   {
      files.clear();

      if (selection instanceof IStructuredSelection)
      {
         IStructuredSelection sel = (IStructuredSelection) selection;
         for (Iterator i = sel.iterator(); i.hasNext();)
         {
            Object obj = i.next();
            if (obj instanceof IFile)
            {
               files.add(obj);
            }
         }
      }
   }

   public void run(IAction action)
   {
      String actionId = action.getId();
      SynchronizedScrollPartController controller =
         SynchronizedScrollPartController.getInstance();

      if (ADD.equals(actionId))
      {
         for (Iterator i = files.iterator(); i.hasNext();)
         {
            IFile file = (IFile) i.next();
            try
            {
               controller.addFile(file);
            }
            catch (UnsupportedOperationException e)
            {
               ProfilerPlugin.errorDialog("Error",
                  "No suitable editor found for file " + file.getName(), e);
            }
            catch (PartInitException e)
            {
               ProfilerPlugin.errorDialog("Error",
                  "Error opening editor on file " + file.getName(), e);
            }
         }
      }
      else if (REMOVE.equals(actionId))
      {
         for (Iterator i = files.iterator(); i.hasNext();)
         {
            IFile file = (IFile) i.next();
            controller.removeFile(file);
         }
      }
   }
}
