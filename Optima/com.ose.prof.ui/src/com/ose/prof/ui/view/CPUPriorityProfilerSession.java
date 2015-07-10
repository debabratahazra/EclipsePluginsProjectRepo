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
import com.ose.prof.ui.editors.profiler.CPUPriorityProfilerEditorInput;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUPriorityReports;
import com.ose.system.CPUPriorityReportsEnabledInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetReport;

class CPUPriorityProfilerSession extends ProfilerSession
{
   private final CPUPriorityProfilerEditorInput profilerEditorInput;

   CPUPriorityProfilerSession(Target target,
                              short executionUnit,
                              int integrationInterval,
                              int maxReports)
      throws IOException
   {
      super(target, executionUnit, integrationInterval, maxReports);
      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
      this.profilerEditorInput = new CPUPriorityProfilerEditorInput(
            target,
            getExecutionUnit(),
            getIntegrationInterval(),
            getMaxReports(),
            getTimestamp());
      openProfilerEditor();
   }

   public String getProfilerName()
   {
      return "CPU Usage / Priority";
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.CPU_PRIORITY_PROFILER_EDITOR_ID;
   }

   ProfilerEditorInput getProfilerEditorInput()
   {
      return profilerEditorInput;
   }

   public boolean hasProfilingEnabledSupport()
   {
      return target.hasSetCPUPriorityReportsEnabledSupport();
   }

   public boolean hasStartStopProfilingSupport()
   {
      return (target.hasGetCPUPriorityReportsSupport() &&
              !target.isPostMortemMonitor());
   }

   public boolean hasGetReportsSupport()
   {
      return (target.hasGetCPUPriorityReportsSupport() &&
              target.isPostMortemMonitor());
   }

   public boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException
   {
      CPUPriorityReportsEnabledInfo enabledInfo =
         target.getCPUPriorityReportsEnabled(monitor);
      return (enabledInfo != null) ? enabledInfo.isEnabled() : false;
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         CPUPriorityReportsEnabledInfo enabledInfo;

         try
         {
            target.setCPUPriorityReportsEnabled(monitor,
                                                enabled,
                                                getIntegrationInterval(),
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
         enabledInfo = target.getCPUPriorityReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getIntegrationInterval() != enabledInfo.getInterval()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()))
               {
                  showProfilingParametersOverriddenDialog();
               }
               setIntegrationInterval(enabledInfo.getInterval());
               setMaxReports(enabledInfo.getMaxReports());
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

      dumpFileWriter.writeCPUPriorityReportInfo(target.toString(),
                                                getExecutionUnit(),
                                                getIntegrationInterval(),
                                                getMaxReports());

      dumpFileWriter.writeCPUPriorityReportsEnabled(true,
                                                    getIntegrationInterval(),
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
      CPUPriorityReports reportsInfo;
      int numReports = 0;

      tick = ((lastReport != null) ? lastReport.getTick() : 0);
      ntick = ((lastReport != null) ? lastReport.getNTick() : 0);
      reportsInfo = target.getCPUPriorityReports(monitor, getExecutionUnit(), tick, ntick);
      if (reportsInfo != null)
      {
         CPUPriorityReport[] reports;

         reports = (CPUPriorityReport[]) reportsInfo.getReports();
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
               CPUPriorityReport[] lostReports = (CPUPriorityReport[])
                  lostReportList.toArray(new CPUPriorityReport[0]);
               dumpFileWriter.writeCPUPriorityReports(lostReports, getExecutionUnit(), true);
               asyncExec(new AddReportsRunner(lostReports));
               numReports += lostReports.length;
            }
            setNanoSecondsForReports(reports);
            dumpFileWriter.writeCPUPriorityReports(reports, getExecutionUnit(), true);
            syncExec(new AddReportsRunner(reportsInfo));
            numReports += reports.length;
            lastReport = (CPUPriorityReport) reports[reports.length - 1];
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
      return CPUPriorityReport.getInstance(tick,
                                           ntick,
                                           interval,
                                           0,
                                           0,
                                           new int[32]);
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (file.toLowerCase().endsWith(".report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
            new ReportDumpXMLConverter();
         reportDumpXMLConverter.convertCPUPriorityReports(monitor,
                                                          dumpFile,
                                                          new File(file));
      }
      else
      {
         copyFile(dumpFile, new File(file));
      }

      refreshWorkspaceFile(monitor, new File(file));
   }
}
