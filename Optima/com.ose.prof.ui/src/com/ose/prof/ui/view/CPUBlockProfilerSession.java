/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.CPUBlockProfilerEditor;
import com.ose.prof.ui.editors.profiler.CPUBlockProfilerEditorInput;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.BlockInfo;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReports;
import com.ose.system.CPUBlockReportsEnabledInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;

public class CPUBlockProfilerSession extends ProfilerSession
{
   private int maxValuesReport;
   private final boolean translateBidsToNames;
   private final CPUBlockProfilerEditorInput profilerEditorInput;
   private final Map blockMap;

   CPUBlockProfilerSession(Target target,
                           short executionUnit,
                           int integrationInterval,
                           int maxReports,
                           int maxValuesReport,
                           boolean translateBidsToNames)
      throws IOException
   {
      super(target, executionUnit, integrationInterval, maxReports);
      this.maxValuesReport = maxValuesReport;
      this.translateBidsToNames = translateBidsToNames;
      this.blockMap = (translateBidsToNames ? new HashMap() : null);
      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
      this.profilerEditorInput = new CPUBlockProfilerEditorInput(
            target,
            getExecutionUnit(),
            getIntegrationInterval(),
            getMaxReports(),
            getMaxValuesReport(),
            blockMap,
            getTimestamp());
      openProfilerEditor();
   }

   public final int getMaxValuesReport()
   {
      return maxValuesReport;
   }

   final void setMaxValuesReport(int maxValuesReport)
   {
      this.maxValuesReport = maxValuesReport;
      asyncExec(new SetMaxValuesReportRunner());
   }

   public String getProfilerName()
   {
      return "CPU Usage / Block";
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.CPU_BLOCK_PROFILER_EDITOR_ID;
   }

   ProfilerEditorInput getProfilerEditorInput()
   {
      return profilerEditorInput;
   }

   public boolean hasProfilingEnabledSupport()
   {
      return target.hasSetCPUBlockReportsEnabledSupport();
   }

   public boolean hasStartStopProfilingSupport()
   {
      return (target.hasGetCPUBlockReportsSupport() &&
              !target.isPostMortemMonitor());
   }

   public boolean hasGetReportsSupport()
   {
      return (target.hasGetCPUBlockReportsSupport() &&
              target.isPostMortemMonitor());
   }

   public boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException
   {
      CPUBlockReportsEnabledInfo enabledInfo =
         target.getCPUBlockReportsEnabled(monitor);
      return (enabledInfo != null) ? enabledInfo.isEnabled() : false;
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         CPUBlockReportsEnabledInfo enabledInfo;

         try
         {
            target.setCPUBlockReportsEnabled(monitor,
                                             enabled,
                                             getIntegrationInterval(),
                                             getMaxReports(),
                                             getMaxValuesReport());
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
         enabledInfo = target.getCPUBlockReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getIntegrationInterval() != enabledInfo.getInterval()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()) ||
                   (getMaxValuesReport() != enabledInfo.getMaxBlocksPerReport()))
               {
                  showProfilingParametersOverriddenDialog();
               }
               setIntegrationInterval(enabledInfo.getInterval());
               setMaxReports(enabledInfo.getMaxReports());
               setMaxValuesReport(enabledInfo.getMaxBlocksPerReport());
               setSeconds(enabledInfo.getSeconds());
               setSecondsTick(enabledInfo.getSecondsTick());
               setSecondsNTick(enabledInfo.getSecondsNTick());
            }
         }
      }
   }

   // TODO: If/when the event system supports multiple clients, enable
   // basic create/kill notification to get notified when new blocks
   // are created during the profiling session and store them.

   void initReports(IProgressMonitor monitor) throws IOException
   {
      firstReportRead = false;

      dumpFileWriter.writeCPUBlockReportInfo(target.toString(),
                                             getExecutionUnit(),
                                             getIntegrationInterval(),
                                             getMaxReports(),
                                             getMaxValuesReport());

      dumpFileWriter.writeCPUBlockReportsEnabled(true,
                                                 getIntegrationInterval(),
                                                 getMaxReports(),
                                                 getMaxValuesReport(),
                                                 getSeconds(),
                                                 getSecondsTick(),
                                                 getSecondsNTick(),
                                                 true);

      if (translateBidsToNames && target.hasBlockSupport())
      {
         BlockInfo[] blocks;

         blocks = target.getFilteredBlocks(monitor,
                                           Target.SCOPE_SYSTEM,
                                           0,
                                           null);
         dumpFileWriter.startWritingBlocks(Target.SCOPE_SYSTEM, 0, true);
         blockMap.clear();
         for (int i = 0; i < blocks.length; i++)
         {
            BlockInfo block = blocks[i];
            dumpFileWriter.writeBlock(block);
            blockMap.put(new Integer(block.getId()), block.getName());
         }
         dumpFileWriter.endWritingBlocks(0);
      }
   }

   int retrieveReports(IProgressMonitor monitor) throws IOException
   {
      int tick;
      int ntick;
      CPUBlockReports reportsInfo;
      int numReports = 0;

      tick = ((lastReport != null) ? lastReport.getTick() : 0);
      ntick = ((lastReport != null) ? lastReport.getNTick() : 0);
      reportsInfo = target.getCPUBlockReports(monitor, getExecutionUnit(), tick, ntick);
      if (reportsInfo != null)
      {
         CPUBlockReport[] reports;

         reports = (CPUBlockReport[]) reportsInfo.getReports();
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
               CPUBlockReport[] lostReports = (CPUBlockReport[])
                  lostReportList.toArray(new CPUBlockReport[0]);
               dumpFileWriter.writeCPUBlockReports(lostReports, getExecutionUnit(), true);
               asyncExec(new AddReportsRunner(lostReports));
               numReports += lostReports.length;
            }
            setNanoSecondsForReports(reports);
            dumpFileWriter.writeCPUBlockReports(reports, getExecutionUnit(), true);
            syncExec(new AddReportsRunner(reportsInfo));
            numReports += reports.length;
            lastReport = (CPUBlockReport) reports[reports.length - 1];
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
      return CPUBlockReport.getInstance(tick,
                                        ntick,
                                        interval,
                                        0,
                                        new CPUBlockLoad[0]);
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (file.toLowerCase().endsWith(".report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
            new ReportDumpXMLConverter();
         reportDumpXMLConverter.convertCPUBlockReports(monitor,
                                                       dumpFile,
                                                       new File(file));
      }
      else
      {
         copyFile(dumpFile, new File(file));
      }

      refreshWorkspaceFile(monitor, new File(file));
   }

   private class SetMaxValuesReportRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            ((CPUBlockProfilerEditor) getProfilerEditor()).
               setMaxValuesReport(maxValuesReport);
         }
      }
   }
}
