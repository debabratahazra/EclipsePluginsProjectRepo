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

package com.ose.perf.ui.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import com.ose.system.ui.util.BackgroundImageHandler;

class ProfilerSessionContentProvider implements IStructuredContentProvider
{
   private final List profilerSessions = new ArrayList();

   private TableViewer viewer;

   private BackgroundImageHandler backgroundImageHandler;

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (TableViewer) viewer;
   }

   public void dispose()
   {
      Job job = new DisposeJob();
      job.schedule();
   }

   public Object[] getElements(Object parent)
   {
      return (ProfilerSession[]) profilerSessions.toArray(new ProfilerSession[0]);
   }

   public void addProfilerSession(ProfilerSession profilerSession)
   {
      if (profilerSession == null)
      {
         throw new IllegalArgumentException();
      }
      if (!viewer.getTable().isDisposed())
      {
         profilerSessions.add(profilerSession);
         viewer.getTable().getDisplay().asyncExec(
               new UpdateViewerRunner(profilerSession, true));
      }
   }

   public void removeProfilerSession(ProfilerSession profilerSession)
   {
      if (profilerSession == null)
      {
         throw new IllegalArgumentException();
      }
      if (!viewer.getTable().isDisposed())
      {
         profilerSessions.remove(profilerSession);
         viewer.getTable().getDisplay().asyncExec(
               new UpdateViewerRunner(profilerSession, false));
      }
   }

   void setBackgroundImageHandler(BackgroundImageHandler backgroundImageHandler)
   {
      this.backgroundImageHandler = backgroundImageHandler;
   }

   private class UpdateViewerRunner implements Runnable
   {
      private final ProfilerSession profilerSession;
      private final boolean add;

      UpdateViewerRunner(ProfilerSession profilerSession, boolean add)
      {
         if (profilerSession == null)
         {
            throw new IllegalArgumentException();
         }
         this.profilerSession = profilerSession;
         this.add = add;
      }

      public void run()
      {
         if (backgroundImageHandler != null)
         {
            backgroundImageHandler.dispose();
            backgroundImageHandler = null;
         }

         if (add)
         {
            viewer.add(profilerSession);
            viewer.setSelection(new StructuredSelection(profilerSession), true);
         }
         else
         {
            viewer.remove(profilerSession);
         }
      }
   }

   private class DisposeJob extends Job
   {
      DisposeJob()
      {
         super("Disposing profiling sessions");
         setPriority(SHORT);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            for (Iterator i = profilerSessions.iterator(); i.hasNext();)
            {
               ProfilerSession profilerSession = (ProfilerSession) i.next();
               profilerSession.close(true);
            }
            profilerSessions.clear();
            return Status.OK_STATUS;
         }
         finally
         {
            monitor.done();
         }
      }
   }
}
