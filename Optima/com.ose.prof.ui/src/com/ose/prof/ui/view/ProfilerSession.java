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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import com.ose.prof.format.TimeReference;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.ProfilerEditor;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.Target;
import com.ose.system.TargetReport;
import com.ose.system.TargetReports;

abstract class ProfilerSession extends Observable
{
   static final int MONITOR_STATUS_IN_USE = 14;
   private static final int MAX_NUM_REPORTS = 5000;

   final Target target;
   private final short executionUnit;
   private int integrationInterval;
   private int maxReports;
   boolean profilingWasEnabled;
   private int seconds;
   private int secondsTick;
   private int secondsNTick;
   private final long timestamp;
   private final SystemModelListener systemModelHandler;
   final File dumpFile;
   private final Object openLock;
   private final Object profilerLock;

   private boolean open;
   // The profiler editor reference must only be accessed from the UI thread.
   private ProfilerEditor profilerEditor;
   private volatile boolean profilingEnabled;
   private volatile boolean profilingStarted;
   private ProfilerJob profilerJob;
   private boolean hasMoreReports;
   volatile boolean firstReportRead;
   volatile TargetReport lastReport;
   volatile SystemModelDumpFileWriter dumpFileWriter;

   ProfilerSession(Target target,
                   short executionUnit,
                   int integrationInterval,
                   int maxReports)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.executionUnit = executionUnit;
      this.integrationInterval = integrationInterval;
      this.maxReports = maxReports;
      this.profilingWasEnabled = isProfilingEnabled(new NullProgressMonitor());
      this.timestamp = System.currentTimeMillis();
      this.systemModelHandler = new SystemModelHandler();
      this.dumpFile = createDumpFile(timestamp);
      this.openLock = new Object();
      this.profilerLock = new Object();
      SystemModel.getInstance().addSystemModelListener(systemModelHandler);
      this.open = true;
      this.hasMoreReports = true;

      if (target.isKilled())
      {
         close(true);
         throw new IOException("Target is disconnected");
      }
   }

   public final boolean isOpen()
   {
      synchronized (openLock)
      {
         return open;
      }
   }

   public final void close(boolean deleteDumpFile)
   {
      synchronized (openLock)
      {
         if (open)
         {
            SystemModel.getInstance().removeSystemModelListener(systemModelHandler);
            if (hasStartStopProfilingSupport())
            {
               stopProfiling();
            }
            if (hasProfilingEnabledSupport() && !profilingWasEnabled)
            {
               try
               {
                  setProfilingEnabled(new NullProgressMonitor(), false);
               } catch (Exception ignore) {}
            }
            open = false;
            profilingEnabled = false;
            profilingStarted = false;
            profilerJob = null;
            lastReport = null;
            setChanged();
            notifyObservers();
            deleteObservers();
         }
         if (deleteDumpFile)
         {
            dumpFile.delete();
         }
      }
   }

   public final Target getTarget()
   {
      return target;
   }

   public final short getExecutionUnit()
   {
      return executionUnit;
   }

   public abstract String getProfilerName();

   abstract String getProfilerEditorId();

   abstract ProfilerEditorInput getProfilerEditorInput();

   final ProfilerEditor getProfilerEditor()
   {
      return profilerEditor;
   }

   public final int getIntegrationInterval()
   {
      return integrationInterval;
   }

   final void setIntegrationInterval(int integrationInterval)
   {
      this.integrationInterval = integrationInterval;
      asyncExec(new SetIntegrationIntervalRunner());
   }

   public final int getMaxReports()
   {
      return maxReports;
   }

   final void setMaxReports(int maxReports)
   {
      this.maxReports = maxReports;
      asyncExec(new SetMaxReportsRunner());
   }

   public final int getSeconds()
   {
      return seconds;
   }

   final void setSeconds(int seconds)
   {
      this.seconds = seconds;
   }

   public final int getSecondsTick()
   {
      return secondsTick;
   }

   final void setSecondsTick(int secondsTick)
   {
      this.secondsTick = secondsTick;
   }

   public final int getSecondsNTick()
   {
      return secondsNTick;
   }

   final void setSecondsNTick(int secondsNTick)
   {
      this.secondsNTick = secondsNTick;
   }

   public final long getTimestamp()
   {
      return timestamp;
   }

   public final boolean isProfilingEnabled()
   {
      return profilingEnabled;
   }

   public final boolean isProfilingStarted()
   {
      return profilingStarted;
   }

   final void setProfilingEnabled(boolean enabled)
   {
      this.profilingEnabled = enabled;
      setChanged();
      notifyObservers();
   }

   private void setProfilingStarted(boolean started)
   {
      this.profilingStarted = started;
      setChanged();
      notifyObservers();
   }

   public boolean dumpFileExists()
   {
      return dumpFile.isFile();
   }

   public void openProfilerEditor()
   {
      asyncExec(new OpenProfilerEditorRunner());
   }

   public void activateProfilerEditor()
   {
      asyncExec(new ActivateProfilerEditorRunner());
   }

   public void closeProfilerEditor()
   {
      asyncExec(new CloseProfilerEditorRunner());
   }

   public abstract boolean hasProfilingEnabledSupport();

   public abstract boolean hasStartStopProfilingSupport();

   public abstract boolean hasGetReportsSupport();

   public abstract boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException;

   public abstract void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException;

   public final void startProfiling()
   {
      if (isOpen())
      {
         synchronized (profilerLock)
         {
            if ((profilerJob == null) || profilerJob.isKilled())
            {
               profilerJob = new ProfilerJob();
               profilerJob.schedule();
            }
         }
      }
   }

   public final void stopProfiling()
   {
      synchronized (profilerLock)
      {
         if ((profilerJob != null) && !profilerJob.isKilled())
         {
            profilerJob.stop();
            profilerJob = null;
         }
      }
   }

   public final void getFirstReports(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         synchronized (profilerLock)
         {
            lastReport = null;
            getReports(monitor);
         }
      }
   }

   public final void getNextReports(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         synchronized (profilerLock)
         {
            getReports(monitor);
         }
      }
   }

   private void getReports(IProgressMonitor monitor) throws IOException
   {
      if (isOpen())
      {
         if (hasMoreReports || (lastReport == null))
         {
            int numReports = 0;

            dumpFileWriter = createDumpFileWriter();
   
            try
            {
               syncExec(new SetOfflineFeaturesEnablementRunner(false));
               syncExec(new ClearReportsRunner());
               initReports(monitor);
               while (numReports < MAX_NUM_REPORTS)
               {
                  numReports += retrieveReports(monitor);
               }
            }
            finally
            {
               dumpFileWriter.close();
               dumpFileWriter = null;
               hasMoreReports = (numReports >= MAX_NUM_REPORTS);
               asyncExec(new SetOfflineFeaturesEnablementRunner(true));
               setChanged();
               notifyObservers();
            }
         }
      }
   }

   abstract void initReports(IProgressMonitor monitor) throws IOException;

   abstract int retrieveReports(IProgressMonitor monitor) throws IOException;

   abstract TargetReport createLostReport(int tick, int ntick, int interval);

   final List createLostReports(TargetReport[] reports)
   {
      List lostReports = null;

      if ((lastReport != null) && (reports.length > 0))
      {
         int t0 = lastReport.getTick();
         int t1 = reports[0].getTick();
         int numLost = (t1 - t0) / integrationInterval - 1;

         if (numLost > 0)
         {
            lostReports = new ArrayList(numLost);

            for (int i = 0; i < numLost; i++)
            {
               TargetReport report = createLostReport(
                     t0 + (i + 1) * integrationInterval,
                     0,
                     reports[0].getInterval());
               report.setNanoSeconds(target.getTickLength(),
                                     target.getNTickFrequency(),
                                     seconds,
                                     secondsTick,
                                     secondsNTick);
               lostReports.add(report);
            }
         }
      }

      return lostReports;
   }

   final void adjustTimeReference(int tick, int ntick)
   {
      TimeReference timeRef =
         new TimeReference(target.getTickLength(), target.getNTickFrequency());
      timeRef.set(seconds, secondsTick, secondsNTick);
      if (timeRef.adjust(tick, ntick))
      {
         seconds = timeRef.getSeconds();
         secondsTick = timeRef.getTick();
         secondsNTick = timeRef.getNTick();
      }
   }

   final void setNanoSecondsForReports(TargetReport[] reports)
   {
      for (int i = 0; i < reports.length; i++)
      {
         reports[i].setNanoSeconds(target.getTickLength(),
                                   target.getNTickFrequency(),
                                   seconds,
                                   secondsTick,
                                   secondsNTick);
      }
   }

   public abstract void saveReports(IProgressMonitor monitor, String file)
      throws IOException;

   void showProfilingParametersOverriddenDialog()
   {
      asyncExec(new ShowMessageDialogRunner("Note that some of the " +
            "requested profiling parameters were not accepted.\n\n" +
            "See the \"Profiling Parameters\" section in the profiling " +
            "editor for the actual profiling parameters used."));
   }

   static void syncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.syncExec(runnable);
      }
   }

   static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   static void copyFile(File from, File to) throws IOException
   {
      FileInputStream in = null;
      FileOutputStream out = null;

      if ((from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         byte[] buffer = new byte[8192];
         int length;

         in = new FileInputStream(from);
         out = new FileOutputStream(to);

         while ((length = in.read(buffer)) != -1)
         {
            out.write(buffer, 0, length);
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
         if (out != null)
         {
            try
            {
               out.close();
            } catch (IOException ignore) {}
         }
      }
   }

   static void refreshWorkspaceFile(IProgressMonitor monitor, File file)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      if (file.isFile())
      {
         IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().
            getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
         if (workspaceFile != null)
         {
            try
            {
               workspaceFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
            }
            catch (CoreException e)
            {
               throw new IOException(e.getMessage());
            }
         }
      }
   }

   private SystemModelDumpFileWriter createDumpFileWriter() throws IOException
   {
      SystemModelDumpFileWriter dumpFileWriter = null;

      try
      {
         int dumpId;

         dumpId = (int) (timestamp / 1000L);
         dumpFileWriter = new SystemModelDumpFileWriter(dumpFile,
                                                        target.isBigEndian(),
                                                        dumpId);
         dumpFileWriter.writeSystemInfo(target.getOsType(),
                                        target.getCpuType(),
                                        target.getName(),
                                        target.getNumExecutionUnits(),
                                        target.getTickLength(),
                                        target.getNTickFrequency(),
                                        true);
         return dumpFileWriter;
      }
      catch (IOException e)
      {
         if (dumpFileWriter != null)
         {
            dumpFileWriter.close();
         }
         dumpFile.delete();
         throw e;
      }
   }

   private static File createDumpFile(long id)
   {
      File pluginStateDir;
      String fileName;

      pluginStateDir = ProfilerPlugin.getDefault().getStateLocation().toFile();
      fileName = "reports-" + id + ".pmd";
      return new File(pluginStateDir, fileName);
   }

   private class SystemModelHandler implements SystemModelListener
   {
      public void nodesChanged(SystemModelEvent event)
      {
         if ((event.getParent() == target.getParent()) &&
             (event.getChildren().contains(target)))
         {
            if (target.isKilled())
            {
               close(false);
            }
         }
      }

      public void nodesAdded(SystemModelEvent event)
      {
         // Nothing to do.
      }

      public void nodesRemoved(SystemModelEvent event)
      {
         // Nothing to do.
      }
   }

   private class ProfilerJob extends Job
   {
      private static final double POLL_INTERVAL_SCALING = 0.9;
      private static final int STOP_TIMEOUT = 10000;

      private final Object waitLock = new Object();
      private final long pollInterval;
      private volatile boolean stopped;
      private volatile boolean killed;

      ProfilerJob()
      {
         super("Retrieving profiling reports...");
         setPriority(LONG);
         pollInterval = (long) Math.floor(
            POLL_INTERVAL_SCALING 
               * (integrationInterval 
                  * target.getTickLength() 
                  * (maxReports - 2)) / 1000);
         lastReport = null;
      }

      final boolean isKilled()
      {
         return killed;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            dumpFileWriter = createDumpFileWriter();
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when creating the profiling dump file", e);
         }

         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            setProfilingStarted(true);
            syncExec(new SetOfflineFeaturesEnablementRunner(false));
            syncExec(new ClearReportsRunner());
            return doReports(monitor);
         }
         finally
         {
            dumpFileWriter.close();
            dumpFileWriter = null;
            setProfilingStarted(false);
            asyncExec(new SetOfflineFeaturesEnablementRunner(true));
            monitor.done();
            synchronized (waitLock)
            {
               killed = true;
               waitLock.notifyAll();
            }
         }
      }

      private IStatus doReports(IProgressMonitor monitor)
      {
         long t0;
         long t1;
         long adjustedPollInterval;

         try
         {
            initReports(monitor);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error reported from target when starting the profiling", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when starting the profiling", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when starting the profiling", e);
         }
         while (!stopped && !monitor.isCanceled())
         {
            try
            {
               t0 = System.currentTimeMillis();
               retrieveReports(monitor);
               t1 = System.currentTimeMillis();
               adjustedPollInterval = pollInterval - (t1 - t0);
               if (!stopped && (adjustedPollInterval > 0))
               {
                  synchronized (waitLock)
                  {
                     waitLock.wait(adjustedPollInterval);
                  }
               }
            }
            catch (InterruptedException e)
            {
               return Status.CANCEL_STATUS;
            }
            catch (OperationCanceledException e)
            {
               return Status.CANCEL_STATUS;
            }
            catch (ServiceException e)
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error reported from target when profiling", e);
            }
            catch (IOException e)
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error communicating with target when profiling", e);
            }
            catch (Exception e)
            {
               return ProfilerPlugin.createErrorStatus("Error when profiling", e);
            }
         }

         return Status.OK_STATUS;
      }

      final void stop()
      {
         synchronized (waitLock)
         {
            stopped = true;
            waitLock.notifyAll();
         }

         if (!killed)
         {
            synchronized (waitLock)
            {
               try
               {
                  waitLock.wait(STOP_TIMEOUT);
               } catch (InterruptedException e) {}
            }

            if (!killed)
            {
               cancel();
            }
         }
      }
   }

   private class ShowMessageDialogRunner implements Runnable
   {
      private final String message;

      ShowMessageDialogRunner(String message)
      {
         if (message == null)
         {
            throw new IllegalArgumentException();
         }
         this.message = message;
      }

      public void run()
      {
         MessageDialog.openInformation(null, "Information", message);
      }
   }

   abstract class ProfilerEditorRunner implements Runnable
   {
      protected boolean isProfilerEditorOpen()
      {
         return ((profilerEditor != null) && !profilerEditor.isDisposed());
      }
   }

   private class OpenProfilerEditorRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (!isProfilerEditorOpen())
         {
            IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();

            if (page != null)
            {
               try
               {
                  profilerEditor = (ProfilerEditor) page.openEditor(
                     getProfilerEditorInput(), getProfilerEditorId());
               }
               catch (PartInitException e)
               {
                  ProfilerPlugin.log(e);
               }
            }
         }
      }
   }

   private class ActivateProfilerEditorRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();

            if (page != null)
            {
               page.bringToTop(profilerEditor);
            }
         }
      }
   }

   private class CloseProfilerEditorRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            IWorkbenchWindow workbenchWindow =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow();

            if (workbenchWindow != null)
            {
               IWorkbenchPage page = workbenchWindow.getActivePage();

               if (page != null)
               {
                  page.closeEditor(profilerEditor, false);
                  profilerEditor = null;
               }
            }
         }
      }
   }

   private class SetIntegrationIntervalRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            getProfilerEditor().setIntegrationInterval(integrationInterval);
         }
      }
   }

   private class SetMaxReportsRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            getProfilerEditor().setMaxReports(maxReports);
         }
      }
   }

   private class SetOfflineFeaturesEnablementRunner extends ProfilerEditorRunner
   {
      private final boolean enabled;

      SetOfflineFeaturesEnablementRunner(boolean enabled)
      {
         this.enabled = enabled;
      }

      public void run()
      {
         if (isProfilerEditorOpen())
         {
            profilerEditor.setOfflineFeaturesEnabled(enabled);
         }
      }
   }

   private class ClearReportsRunner extends ProfilerEditorRunner
   {
      public void run()
      {
          if (isProfilerEditorOpen())
          {
             profilerEditor.clearReports();
          }
      }
   }

   class AddReportsRunner extends ProfilerEditorRunner
   {
      private TargetReports targetReports;
      private TargetReport[] reports;

      AddReportsRunner(TargetReports targetReports)
      {
         if (targetReports == null)
         {
           throw new IllegalArgumentException();
         }
         this.targetReports = targetReports;
      }

      AddReportsRunner(TargetReport[] reports)
      {
         if (reports == null)
         {
           throw new IllegalArgumentException();
         }
         this.reports = reports;
      }

      public void run()
      {
         if (isProfilerEditorOpen())
         {
            if (targetReports != null)
            {
               profilerEditor.addReports(targetReports);
            }
            else if (reports != null)
            {
               profilerEditor.addReports(reports);
            }
         }
      }
   }
}
