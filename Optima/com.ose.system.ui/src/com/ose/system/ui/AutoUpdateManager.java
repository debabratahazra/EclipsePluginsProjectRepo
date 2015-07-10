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

package com.ose.system.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class AutoUpdateManager
{
   private static final long MIN_UPDATE_INTERVAL = 1000;

   private static final UpdatableComparator UPDATABLE_COMPARATOR =
      new UpdatableComparator();

   private static final AutoUpdateManager AUTO_UPDATE_MANAGER =
      new AutoUpdateManager();

   private final Map<String, IUpdatable> updatables;

   private Timer timer;

   private AutoUpdateManager()
   {
      updatables = new HashMap<String, IUpdatable>();
   }

   public static AutoUpdateManager getInstance()
   {
      return AUTO_UPDATE_MANAGER;
   }

   public void addUpdatable(IUpdatable updatable)
   {
      synchronized (updatables)
      {
         updatables.put(updatable.getId(), updatable);
         start();
      }
   }

   public void removeUpdatable(String id)
   {
      synchronized (updatables)
      {
         updatables.remove(id);
         if (updatables.isEmpty())
         {
            stop();
         }
      }
   }

   private void start()
   {
      if (timer == null)
      {
         timer = new Timer("Optima Auto Update Timer");
         timer.schedule(new UpdateTimerTask(), 0, getInterval());
      }
   }

   private void stop()
   {
      if (timer != null)
      {
         timer.cancel();
         timer.purge();
         timer = null;
      }
   }

   private static long getInterval()
   {
      long interval = SystemBrowserPlugin.getDefault().getPreferenceStore()
         .getLong(SystemModelPreferencePage.PREF_AUTOMATIC_UPDATE);
      return ((interval < MIN_UPDATE_INTERVAL) ? MIN_UPDATE_INTERVAL : interval);
   }

   private class UpdateTimerTask extends TimerTask
   {
      public void run()
      {
         Job updateJob = new UpdateJob();
         updateJob.schedule();
      }
   }

   private class UpdateJob extends Job
   {
      UpdateJob()
      {
         super("Auto Updating Optima");
         setPriority(SHORT);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            IUpdatable[] updatablesArray;
            synchronized (updatables)
            {
               updatablesArray = updatables.values().toArray(new IUpdatable[0]);
            }
            Arrays.sort(updatablesArray, UPDATABLE_COMPARATOR);
            for (int i = 0; i < updatablesArray.length; i++)
            {
               try
               {
                  updatablesArray[i].update(monitor);
               }
               catch (Exception e)
               {
                  SystemBrowserPlugin.log(e);
               }
            }
            return (monitor.isCanceled() ?
                  Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private static class UpdatableComparator implements Comparator<IUpdatable>
   {
      public int compare(IUpdatable updatable1, IUpdatable updatable2)
      {
         int p1 = updatable1.getPriority();
         int p2 = updatable2.getPriority();
         return (p1 < p2) ? -1 : ((p1 > p2) ? 1 : 0);
      }
   }
}
