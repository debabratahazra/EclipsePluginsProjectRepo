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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.prof.ui.editors.profiler.UserProfilerEditor;
import com.ose.prof.ui.editors.profiler.UserProfilerEditorInput;
import com.ose.system.ProcessInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetReport;
import com.ose.system.UserReport;
import com.ose.system.UserReports;
import com.ose.system.UserReportsEnabledInfo;
import com.ose.system.UserReport.UserReportValue;

class UserProfilerSession extends ProfilerSession
{
   private final int reportNumber;
   private int maxValuesReport;
   private final boolean translatePidsToNames;
   private final UserProfilerEditorInput profilerEditorInput;
   private final Map processMap;
   private boolean userReportInfoWritten;

   UserProfilerSession(Target target,
                       int reportNumber,
                       int integrationInterval,
                       int maxReports,
                       int maxValuesReport,
                       boolean translatePidsToNames)
      throws IOException
   {
      super(target, (short) 0xFFFE, integrationInterval, maxReports);
      this.reportNumber = reportNumber;
      this.maxValuesReport = maxValuesReport;
      this.translatePidsToNames = translatePidsToNames;
      this.processMap = (translatePidsToNames ? new HashMap() : null);
      this.profilingWasEnabled = isProfilingEnabled(new NullProgressMonitor());
      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
      this.profilerEditorInput = new UserProfilerEditorInput(
            target,
            getIntegrationInterval(),
            getMaxReports(),
            getMaxValuesReport(),
            getReportNumber(),
            processMap,
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
      return "Custom Profiling Type " + Long.toString(0xFFFFFFFFL & reportNumber);
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.USER_PROFILER_EDITOR_ID;
   }

   ProfilerEditorInput getProfilerEditorInput()
   {
      return profilerEditorInput;
   }

   public int getReportNumber()
   {
      return reportNumber;
   }

   public boolean hasProfilingEnabledSupport()
   {
      return target.hasSetUserReportsEnabledSupport();
   }

   public boolean hasStartStopProfilingSupport()
   {
      return (target.hasGetUserReportsSupport() && !target.isPostMortemMonitor());
   }

   public boolean hasGetReportsSupport()
   {
      return (target.hasGetUserReportsSupport() && target.isPostMortemMonitor());
   }

   public boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException
   {
      UserReportsEnabledInfo enabledInfo =
         target.getUserReportsEnabled(monitor, reportNumber);
      return (enabledInfo != null) ? enabledInfo.isEnabled() : false;
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         UserReportsEnabledInfo enabledInfo;

         try
         {
            target.setUserReportsEnabled(monitor,
                                         reportNumber,
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
         enabledInfo = target.getUserReportsEnabled(monitor, reportNumber);
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getIntegrationInterval() != enabledInfo.getInterval()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()) ||
                   (getMaxValuesReport() != enabledInfo.getMaxValuesPerReport()))
               {
                  showProfilingParametersOverriddenDialog();
               }
               setIntegrationInterval(enabledInfo.getInterval());
               setMaxReports(enabledInfo.getMaxReports());
               setMaxValuesReport(enabledInfo.getMaxValuesPerReport());
               setSeconds(enabledInfo.getSeconds());
               setSecondsTick(enabledInfo.getSecondsTick());
               setSecondsNTick(enabledInfo.getSecondsNTick());
            }
         }
      }
   }

   // TODO: If/when the event system supports multiple clients, enable
   // basic create/kill notification to get notified when new processes
   // are created during the profiling session and store them.

   void initReports(IProgressMonitor monitor) throws IOException
   {
      userReportInfoWritten = false;
      firstReportRead = false;

      dumpFileWriter.writeUserReportsEnabled(getReportNumber(),
                                             true,
                                             getIntegrationInterval(),
                                             getMaxReports(),
                                             getMaxValuesReport(),
                                             getSeconds(),
                                             getSecondsTick(),
                                             getSecondsNTick(),
                                             true);

      if (translatePidsToNames && target.hasProcessSupport())
      {
         ProcessInfo[] processes;

         processes = target.getFilteredProcesses(monitor,
                                                 Target.SCOPE_SYSTEM,
                                                 0,
                                                 null);
         dumpFileWriter.startWritingProcesses(Target.SCOPE_SYSTEM, 0, true);
         processMap.clear();
         for (int i = 0; i < processes.length; i++)
         {
            ProcessInfo process = processes[i];
            dumpFileWriter.writeProcess(process);
            processMap.put(new Integer(process.getId()), process.getName());
         }
         dumpFileWriter.endWritingProcesses(0);
      }
   }

   int retrieveReports(IProgressMonitor monitor) throws IOException
   {
      int tick;
      int ntick;
      UserReports reportsInfo;
      int numReports = 0;

      tick = ((lastReport != null) ? lastReport.getTick() : 0);
      ntick = ((lastReport != null) ? lastReport.getNTick() : 0);
      reportsInfo = target.getUserReports(monitor, reportNumber, tick, ntick);
      if (reportsInfo != null)
      {
         UserReport[] reports = (UserReport[]) reportsInfo.getReports();

         if (!userReportInfoWritten)
         {
            dumpFileWriter.writeUserReportInfo(target.toString(),
                                               getIntegrationInterval(),
                                               getMaxReports(),
                                               getMaxValuesReport(),
                                               getReportNumber(),
                                               reportsInfo.isContinuous(),
                                               reportsInfo.isMaxMin(),
                                               reportsInfo.isMultiple());
            userReportInfoWritten = true;
         }

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
               UserReport[] lostReports = (UserReport[])
                  lostReportList.toArray(new UserReport[0]);
               dumpFileWriter.writeUserReports(lostReports,
                                               reportsInfo.getReportNumber(),
                                               reportsInfo.isContinuous(),
                                               reportsInfo.isMaxMin(),
                                               reportsInfo.isMultiple(),
                                               true);
               asyncExec(new AddReportsRunner(lostReports));
               numReports += lostReports.length;
            }
            setNanoSecondsForReports(reports);
            dumpFileWriter.writeUserReports(reports,
                                            reportsInfo.getReportNumber(),
                                            reportsInfo.isContinuous(),
                                            reportsInfo.isMaxMin(),
                                            reportsInfo.isMultiple(),
                                            true);
            syncExec(new AddReportsRunner(reportsInfo));
            numReports += reports.length;
            lastReport = (UserReport) reports[reports.length - 1];
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
      return UserReport.getInstance(tick,
                                    ntick,
                                    interval,
                                    0,
                                    0,
                                    0,
                                    new UserReportValue[0]);
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (file.toLowerCase().endsWith(".report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
            new ReportDumpXMLConverter();
         reportDumpXMLConverter.convertUserReports(monitor,
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
            ((UserProfilerEditor) getProfilerEditor()).
               setMaxValuesReport(maxValuesReport);
         }
      }
   }
}
