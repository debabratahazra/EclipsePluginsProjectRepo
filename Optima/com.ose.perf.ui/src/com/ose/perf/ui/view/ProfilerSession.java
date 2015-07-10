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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.system.LoadModuleInfo;
import com.ose.system.PerformanceCounterEnabledInfo;
import com.ose.system.PerformanceCounterInfo;
import com.ose.system.PerformanceCounterListener;
import com.ose.system.PerformanceCounterReports;
import com.ose.system.ProcessInfo;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.Target;

class ProfilerSession extends Observable
{
   private static final int MONITOR_STATUS_IN_USE = 14;

   private final Target target;
   private final short executionUnit;
   private final PerformanceCounterInfo performanceCounter;
   private final long timestamp;
   private final SystemModelListener systemModelHandler;
   private final File dumpFile;
   private final Object openLock;
   private final Object reportNotifyLock;

   private boolean open;
   private volatile long performanceCounterValue;
   private volatile int maxReports;
   private volatile boolean profilingEnabled;
   private ReportNotifyHandler reportNotifyHandler;
   private volatile int numReports;
   private volatile int numLostReports;

   ProfilerSession(Target target,
                   short executionUnit,
                   PerformanceCounterInfo performanceCounter,
                   long performanceCounterValue,
                   int maxReports)
      throws IOException
   {
      if ((target == null) || (performanceCounter == null))
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.executionUnit = executionUnit;
      this.performanceCounter = performanceCounter;
      this.performanceCounterValue = performanceCounterValue;
      this.maxReports = maxReports;
      this.timestamp = System.currentTimeMillis();
      this.systemModelHandler = new SystemModelHandler();
      this.dumpFile = createDumpFile(timestamp);
      this.openLock = new Object();
      this.reportNotifyLock = new Object();
      SystemModel.getInstance().addSystemModelListener(systemModelHandler);
      this.open = true;

      if (target.isKilled())
      {
         close(true);
         throw new IOException("Target is disconnected");
      }

      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
   }

   public boolean isOpen()
   {
      synchronized (openLock)
      {
         return open;
      }
   }

   public void close(boolean deleteDumpFile)
   {
      synchronized (openLock)
      {
         if (open)
         {
            SystemModel.getInstance().removeSystemModelListener(systemModelHandler);
            if (hasProfilingEnabledSupport())
            {
               try
               {
                  setProfilingEnabled(new NullProgressMonitor(), false);
               } catch (Exception ignore) {}
            }
            open = false;
            profilingEnabled = false;
            reportNotifyHandler = null;
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

   public Target getTarget()
   {
      return target;
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public PerformanceCounterInfo getPerformanceCounter()
   {
      return performanceCounter;
   }

   public long getPerformanceCounterValue()
   {
      return performanceCounterValue;
   }

   private void setPerformanceCounterValue(long value)
   {
      this.performanceCounterValue = value;
      setChanged();
      notifyObservers();
   }

   public int getMaxReports()
   {
      return maxReports;
   }

   private void setMaxReports(int maxReports)
   {
      this.maxReports = maxReports;
      setChanged();
      notifyObservers();
   }

   public long getTimestamp()
   {
      return timestamp;
   }

   public boolean isProfilingEnabled()
   {
      return profilingEnabled;
   }

   private void setProfilingEnabled(boolean enabled)
   {
      this.profilingEnabled = enabled;
      setChanged();
      notifyObservers();
   }

   public int getNumReports()
   {
      return numReports;
   }

   private void setNumReports(int numReports)
   {
      this.numReports = numReports;
      setChanged();
      notifyObservers();
   }

   public int getNumLostReports()
   {
      return numLostReports;
   }

   private void setNumLostReports(int numLostReports)
   {
      this.numLostReports = numLostReports;
      setChanged();
      notifyObservers();
   }

   public boolean dumpFileExists()
   {
      return dumpFile.isFile();
   }

   public boolean hasProfilingEnabledSupport()
   {
      return target.hasGetPerformanceCounterEnabledSupport() &&
             target.hasSetPerformanceCounterEnabledSupport() &&
             target.hasPerformanceCounterNotifyEnabledSupport();
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      synchronized (reportNotifyLock)
      {
         if (enabled)
         {
            if (isOpen() && (reportNotifyHandler == null))
            {
               reportNotifyHandler = new ReportNotifyHandler(monitor);
            }
         }
         else
         {
            if (reportNotifyHandler != null)
            {
               reportNotifyHandler.close();
               reportNotifyHandler = null;
            }
         }
      }
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      copyFile(dumpFile, new File(file));
      refreshWorkspaceFile(monitor, new File(file));
   }

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private static void copyFile(File from, File to) throws IOException
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

   private static void refreshWorkspaceFile(IProgressMonitor monitor, File file)
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
         int dumpId = (int) (getTimestamp() / 1000L);
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

   private class ReportNotifyHandler implements PerformanceCounterListener
   {
      private final SystemModelDumpFileWriter dumpFileWriter;

      ReportNotifyHandler(IProgressMonitor monitor) throws IOException
      {
         dumpFileWriter = createDumpFileWriter();

         try
         {
            if (target.hasLoadModuleSupport())
            {
               LoadModuleInfo[] loadModules = target.getLoadModuleInfo(monitor);
               dumpFileWriter.writeLoadModules(loadModules, true);
            }
            if (target.hasProcessSupport())
            {
               ProcessInfo[] processes = target.getFilteredProcesses(
                     monitor,
                     Target.SCOPE_SYSTEM,
                     0,
                     null);
               dumpFileWriter.startWritingProcesses(Target.SCOPE_SYSTEM, 0, true);
               for (int i = 0; i < processes.length; i++)
               {
                  dumpFileWriter.writeProcess(processes[i]);
               }
               dumpFileWriter.endWritingProcesses(0);
            }
            dumpFileWriter.startWritingPerformanceCounters(true);
            dumpFileWriter.writePerformanceCounter(getPerformanceCounter());
            dumpFileWriter.endWritingPerformanceCounters(0);
         }
         catch (IOException e)
         {
            dumpFileWriter.close();
            throw e;
         }

         try
         {
            PerformanceCounterEnabledInfo enabledInfo =
               setPerformanceCounterEnabled(monitor);
            dumpFileWriter.writePerformanceCounterEnabled(enabledInfo, true);
            target.addPerformanceCounterListener(this);
            target.setPerformanceCounterNotifyEnabled(monitor, true,
                  getExecutionUnit(), getPerformanceCounter().getType());
            setProfilingEnabled(true);
            setNumReports(0);
            setNumLostReports(0);
         }
         catch (IOException e)
         {
            close();
            throw e;
         }
      }

      private PerformanceCounterEnabledInfo setPerformanceCounterEnabled(
            IProgressMonitor monitor)
         throws IOException
      {
         PerformanceCounterEnabledInfo enabledInfo;

         try
         {
            target.setPerformanceCounterEnabled(
                  monitor,
                  true,
                  getExecutionUnit(),
                  getPerformanceCounter().getType(),
                  getPerformanceCounterValue(),
                  getMaxReports());
         }
         catch (ServiceException e)
         {
            // It is not an error if we tried to enable
            // a performance counter that was already enabled.
            if (e.getStatus() != MONITOR_STATUS_IN_USE)
            {
               throw e;
            }
         }

         enabledInfo = target.getPerformanceCounterEnabled(
               monitor,
               getExecutionUnit(),
               getPerformanceCounter().getType());
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getPerformanceCounterValue() != enabledInfo.getValue()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()))
               {
                  asyncExec(new ShowMessageDialogRunner("Note that some of " +
                     "the requested profiling parameters were not accepted."));
                  setPerformanceCounterValue(enabledInfo.getValue());
                  setMaxReports(enabledInfo.getMaxReports());
               }
            }
         }

         return enabledInfo;
      }

      void close()
      {
         try
         {
            target.removePerformanceCounterListener(this);
            target.setPerformanceCounterNotifyEnabled(new NullProgressMonitor(),
                  false, getExecutionUnit(), getPerformanceCounter().getType());
            target.setPerformanceCounterEnabled(
                  new NullProgressMonitor(),
                  false,
                  getExecutionUnit(),
                  getPerformanceCounter().getType(),
                  getPerformanceCounterValue(),
                  getMaxReports());
         }
         catch (IOException ignore)
         {
            // Ignore.
         }
         finally
         {
            setProfilingEnabled(false);
            dumpFileWriter.close();
         }
      }

      public void reportsRetrieved(PerformanceCounterReports reports)
      {
         try
         {
            if ((getPerformanceCounter().getType() == reports.getType()) &&
                ((getExecutionUnit() == Target.ALL_EXECUTION_UNITS) ||
                 (getExecutionUnit() == reports.getExecutionUnit())))
            {
               dumpFileWriter.writePerformanceCounterReports(
                  reports.getReports(),
                  reports.getExecutionUnit(),
                  reports.getType(),
                  true);
               setNumReports(getNumReports() + reports.getReports().length);
            }
         }
         catch (IOException e)
         {
            asyncExec(new ShowErrorDialogRunner(
                  "Error storing report notifications", e));
            synchronized (reportNotifyLock)
            {
               close();
               reportNotifyHandler = null;
            }
         }
      }

      public void reportsLost(short executionUnit, int type, int numReportsLost)
      {
         try
         {
            if ((getPerformanceCounter().getType() == type) &&
                ((getExecutionUnit() == Target.ALL_EXECUTION_UNITS) ||
                 (getExecutionUnit() == executionUnit)))
            {
               dumpFileWriter.writePerformanceCounterReportsLoss(
                     numReportsLost, executionUnit, type, true);
               setNumLostReports(getNumLostReports() + numReportsLost);
            }
         }
         catch (IOException e)
         {
            asyncExec(new ShowErrorDialogRunner(
                  "Error storing report notifications", e));
            synchronized (reportNotifyLock)
            {
               close();
               reportNotifyHandler = null;
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

   private class ShowErrorDialogRunner implements Runnable
   {
      private final String message;
      private final Throwable t;

      ShowErrorDialogRunner(String message, Throwable t)
      {
         if (message == null)
         {
            throw new IllegalArgumentException();
         }
         this.message = message;
         this.t = t;
      }

      public void run()
      {
         if (t != null)
         {
            ProfilerPlugin.errorDialog(null, message, t);
         }
         else
         {
            MessageDialog.openError(null, "Error", message);
         }
      }
   }
}
