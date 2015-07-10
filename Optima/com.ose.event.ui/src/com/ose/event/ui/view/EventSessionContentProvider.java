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

class EventSessionContentProvider implements IStructuredContentProvider
{
   private final List eventSessions = new ArrayList();

   private TableViewer viewer;

   private BackgroundImageHandler backgroundImageHandler;

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (TableViewer) viewer;
   }

   public void dispose()
   {
      Job job;

      for (Iterator i = eventSessions.iterator(); i.hasNext();)
      {
         EventSession eventSession = (EventSession) i.next();
         eventSession.closeEventEditor();
      }
      job = new DisposeJob();
      job.schedule();
   }

   public Object[] getElements(Object parent)
   {
      return (EventSession[]) eventSessions.toArray(new EventSession[0]);
   }

   public void addEventSession(EventSession eventSession)
   {
      if (eventSession == null)
      {
         throw new IllegalArgumentException();
      }
      if (!viewer.getTable().isDisposed())
      {
         eventSessions.add(eventSession);
         viewer.getTable().getDisplay().asyncExec(
               new UpdateViewerRunner(eventSession, true));
      }
   }

   public void removeEventSession(EventSession eventSession)
   {
      if (eventSession == null)
      {
         throw new IllegalArgumentException();
      }
      if (!viewer.getTable().isDisposed())
      {
         eventSessions.remove(eventSession);
         viewer.getTable().getDisplay().asyncExec(
               new UpdateViewerRunner(eventSession, false));
      }
   }

   void setBackgroundImageHandler(BackgroundImageHandler backgroundImageHandler)
   {
      this.backgroundImageHandler = backgroundImageHandler;
   }

   private class UpdateViewerRunner implements Runnable
   {
      private final EventSession eventSession;
      private final boolean add;

      UpdateViewerRunner(EventSession eventSession, boolean add)
      {
         if (eventSession == null)
         {
            throw new IllegalArgumentException();
         }
         this.eventSession = eventSession;
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
            viewer.add(eventSession);
            viewer.setSelection(new StructuredSelection(eventSession), true);
         }
         else
         {
            viewer.remove(eventSession);
         }
      }
   }

   private class DisposeJob extends Job
   {
      DisposeJob()
      {
         super("Disposing event sessions");
         setPriority(SHORT);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            for (Iterator i = eventSessions.iterator(); i.hasNext();)
            {
               EventSession eventSession = (EventSession) i.next();
               eventSession.close(true);
            }
            eventSessions.clear();
            return Status.OK_STATUS;
         }
         finally
         {
            monitor.done();
         }
      }
   }
}
