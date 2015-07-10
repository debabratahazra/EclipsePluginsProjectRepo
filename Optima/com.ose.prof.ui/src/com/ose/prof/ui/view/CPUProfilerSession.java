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
import java.io.IOException;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.CPUProfilerEditor;
import com.ose.prof.ui.editors.profiler.CPUProfilerEditorInput;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.CPUReport;
import com.ose.system.CPUReports;
import com.ose.system.CPUReportsEnabledInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetReport;

class CPUProfilerSession extends ProfilerSession
{
   private int priorityLimit;
   private final CPUProfilerEditorInput profilerEditorInput;

   CPUProfilerSession(Target target,
                      short executionUnit,
                      int integrationInterval,
                      int maxReports,
                      int priorityLimit)
      throws IOException
   {
      super(target, executionUnit, integrationInterval, maxReports);
      this.priorityLimit = priorityLimit;
      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
      this.profilerEditorInput = new CPUProfilerEditorInput(
            target,
            getExecutionUnit(),
            getIntegrationInterval(),
            getMaxReports(),
            getPriorityLimit(),
            getTimestamp());
      openProfilerEditor();
   }

   public String getProfilerName()
   {
      return "CPU Usage";
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.CPU_PROFILER_EDITOR_ID;
   }

   ProfilerEditorInput getProfilerEditorInput()
   {
      return profilerEditorInput;
   }

   public int getPriorityLimit()
   {
      return priorityLimit;
   }

   void setPriorityLimit(int priorityLimit)
   {
      this.priorityLimit = priorityLimit;
      asyncExec(new SetPriorityLimitRunner());
   }
   
   public boolean hasProfilingEnabledSupport()
   {
      return target.hasSetCPUReportsEnabledSupport();
   }

   public boolean hasStartStopProfilingSupport()
   {
      return (target.hasGetCPUReportsSupport() && !target.isPostMortemMonitor());
   }

   public boolean hasGetReportsSupport()
   {
      return (target.hasGetCPUReportsSupport() && target.isPostMortemMonitor());
   }

   public boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException
   {
      CPUReportsEnabledInfo enabledInfo =
         target.getCPUReportsEnabled(monitor);
      return (enabledInfo != null) ? enabledInfo.isEnabled() : false;
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         CPUReportsEnabledInfo enabledInfo;

         try
         {
            target.setCPUReportsEnabled(monitor,
                                        enabled,
                                        getIntegrationInterval(),
                                        getPriorityLimit(),
                                        getMaxReports());
         }
         catch (ServiceException e)
         {
            // It is not an error if we tried to enable
            // a profiling type that was already enabled.
            if (e.getStatus() != MONITOR_STATUS_IN_USE)
            {
               throw e;
            }
         }
         enabledInfo = target.getCPUReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getIntegrationInterval() != enabledInfo.getInterval()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()) ||
                   (getPriorityLimit() != enabledInfo.getPriority()))
               {
                  showProfilingParametersOverriddenDialog();
               }
               setIntegrationInterval(enabledInfo.getInterval());
               setMaxReports(enabledInfo.getMaxReports());
               setPriorityLimit(enabledInfo.getPriority());
               setSeconds(enabledInfo.getSeconds());
               setSecondsTick(enabledInfo.getSecondsTick());
               setSecondsNTick(enabledInfo.getSecondsNTick());
            }
         }
      }
   }

   void initReports(IProgressMonitor monitor) throws IOException
   {
      firstReportRead = false;

      dumpFileWriter.writeCPUReportInfo(target.toString(),
                                        getExecutionUnit(),
                                        getIntegrationInterval(),
                                        getMaxReports(),
                                        getPriorityLimit());

      dumpFileWriter.writeCPUReportsEnabled(true,
                                            getIntegrationInterval(),
                                            getPriorityLimit(),
                                            getMaxReports(),
                                            getSeconds(),
                                            getSecondsTick(),
                                            getSecondsNTick(),
                                            true);
   }

   int retrieveReports(IProgressMonitor monitor) throws IOException
   {
      int tick;
      int ntick;
      CPUReports reportsInfo;
      int numReports = 0;

      tick = ((lastReport != null) ? lastReport.getTick() : 0);
      ntick = ((lastReport != null) ? lastReport.getNTick() : 0);
      reportsInfo = target.getCPUReports(monitor, getExecutionUnit(), tick, ntick);
      if (reportsInfo != null)
      {
         CPUReport[] reports = (CPUReport[]) reportsInfo.getReports();

         if (reports.length > 0)
         {
            if (!firstReportRead)
            {
               adjustTimeReference(reports[0].getTick(), reports[0].getNTick());
               firstReportRead = true;
            }
            List lostReportList = createLostReports(reports);
            if (lostReportList != null)
            {
               CPUReport[] lostReports = (CPUReport[])
                  lostReportList.toArray(new CPUReport[0]);
               dumpFileWriter.writeCPUReports(lostReports, getExecutionUnit(), true);
               asyncExec(new AddReportsRunner(lostReports));
               numReports += lostReports.length;
            }
            setNanoSecondsForReports(reports);
            dumpFileWriter.writeCPUReports(reports, getExecutionUnit(), true);
            syncExec(new AddReportsRunner(reportsInfo));
            numReports += reports.length;
            lastReport = (CPUReport) reports[reports.length - 1];
         }
         if (!reportsInfo.isEnabled())
         {
            setProfilingEnabled(false);
            throw new OperationCanceledException();
         }
         return numReports;
      }
      else
      {
         throw new OperationCanceledException();
      }
   }

   TargetReport createLostReport(int tick, int ntick, int interval)
   {
      return CPUReport.getInstance(tick, ntick, interval, 0);
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (file.toLowerCase().endsWith(".report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
            new ReportDumpXMLConverter();
         reportDumpXMLConverter.convertCPUReports(monitor,
                                                  dumpFile,
                                                  new File(file));
      }
      else
      {
         copyFile(dumpFile, new File(file));
      }

      refreshWorkspaceFile(monitor, new File(file));
   }

   private class SetPriorityLimitRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            ((CPUProfilerEditor) getProfilerEditor()).
               setPriorityLimit(priorityLimit);
         }
      }
   }
}
