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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.xml.sax.SAXException;
import com.ose.prof.format.ProcessXMLReader;
import com.ose.prof.format.ProcessXMLWriter;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.CPUProcessProfilerEditor;
import com.ose.prof.ui.editors.profiler.CPUProcessProfilerEditorInput;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReportPoint;
import com.ose.system.CPUProcessReports;
import com.ose.system.CPUProcessReportsEnabledInfo;
import com.ose.system.ProcessInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;

class CPUProcessProfilerSession extends ProfilerSession
{
   private int maxValuesReport;
   private final boolean translatePidsToNames;
   private final CPUProcessProfilerEditorInput profilerEditorInput;
   private final Map processMap;
   private volatile String profiledProcesses;

   CPUProcessProfilerSession(Target target,
                             short executionUnit,
                             int integrationInterval,
                             int maxReports,
                             int maxValuesReport,
                             boolean translatePidsToNames)
      throws IOException
   {
      super(target, executionUnit, integrationInterval, maxReports);
      this.maxValuesReport = maxValuesReport;
      this.translatePidsToNames = translatePidsToNames;
      this.processMap = (translatePidsToNames ? new HashMap() : null);
      this.profiledProcesses = "None";
      if (hasProfiledProcessesSupport())
      {
         CPUProcessReportPoint[] processReportPoints =
            target.getCPUProcessReportPoints(new NullProgressMonitor());
         if (processReportPoints.length > 0)
         {
            this.profiledProcesses = "Unknown";
         }
      }
      if (hasProfilingEnabledSupport())
      {
         setProfilingEnabled(new NullProgressMonitor(), true);
      }
      this.profilerEditorInput = new CPUProcessProfilerEditorInput(
            target,
            getExecutionUnit(),
            getIntegrationInterval(),
            getMaxReports(),
            getMaxValuesReport(),
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
      return "CPU Usage / Process";
   }

   String getProfilerEditorId()
   {
      return ProfilerPlugin.CPU_PROCESS_PROFILER_EDITOR_ID;
   }

   ProfilerEditorInput getProfilerEditorInput()
   {
      return profilerEditorInput;
   }

   public void openProfilerEditor()
   {
      super.openProfilerEditor();
      asyncExec(new SetProfiledProcessesRunner());
   }

   public boolean hasProfiledProcessesSupport()
   {
      return target.hasCPUProcessReportPointSupport();
   }

   public boolean hasProfilingEnabledSupport()
   {
      return target.hasSetCPUProcessReportsEnabledSupport();
   }

   public boolean hasStartStopProfilingSupport()
   {
      return (target.hasGetCPUProcessReportsSupport() &&
              !target.isPostMortemMonitor());
   }

   public boolean hasGetReportsSupport()
   {
      return (target.hasGetCPUProcessReportsSupport() &&
              target.isPostMortemMonitor());
   }

   public void setProfiledProcesses(IProgressMonitor monitor, String file)
      throws IOException, ParserConfigurationException, SAXException
   {
      if (isOpen())
      {
         File xmlFile;
         ProcessXMLReader processXMLReader;
         List newReportPoints;

         xmlFile = new File(file);
         processXMLReader = new ProcessXMLReader();
         newReportPoints = processXMLReader.read(xmlFile);

         target.clearCPUProcessReportPoint(monitor, null);

         for (Iterator i = newReportPoints.iterator(); i.hasNext();)
         {
            target.setCPUProcessReportPoint(monitor, (CPUProcessReportPoint) i.next());
         }

         profiledProcesses = xmlFile.getName();
         asyncExec(new SetProfiledProcessesRunner());
      }
   }

   public void getProfiledProcesses(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (isOpen())
      {
         CPUProcessReportPoint[] processReportPoints;
         File newFile;
         ProcessXMLWriter processXMLWriter;

         processReportPoints = target.getCPUProcessReportPoints(monitor);
         newFile = new File(file);
         processXMLWriter = new ProcessXMLWriter(newFile);
         try
         {
            for (int i = 0; i < processReportPoints.length; i++)
            {
               processXMLWriter.write(processReportPoints[i]);
            }
         }
         finally
         {
            processXMLWriter.close();
         }

         refreshWorkspaceFile(monitor, newFile);
         profiledProcesses = newFile.getName();
         asyncExec(new SetProfiledProcessesRunner());
      }
   }

   public void clearProfiledProcesses(IProgressMonitor monitor)
      throws IOException
   {
      if (isOpen())
      {
         target.clearCPUProcessReportPoint(monitor, null);
         profiledProcesses = "None";
         asyncExec(new SetProfiledProcessesRunner());
      }
   }

   public boolean isProfilingEnabled(IProgressMonitor monitor)
      throws IOException
   {
      CPUProcessReportsEnabledInfo enabledInfo =
         target.getCPUProcessReportsEnabled(monitor);
      return (enabledInfo != null) ? enabledInfo.isEnabled() : false;
   }

   public void setProfilingEnabled(IProgressMonitor monitor, boolean enabled)
      throws IOException
   {
      if (isOpen())
      {
         CPUProcessReportsEnabledInfo enabledInfo;

         try
         {
            target.setCPUProcessReportsEnabled(monitor,
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
         enabledInfo = target.getCPUProcessReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            setProfilingEnabled(enabledInfo.isEnabled());
            if (enabledInfo.isEnabled())
            {
               if ((getIntegrationInterval() != enabledInfo.getInterval()) ||
                   (getMaxReports() != enabledInfo.getMaxReports()) ||
                   (getMaxValuesReport() != enabledInfo.getMaxProcessesPerReport()))
               {
                  showProfilingParametersOverriddenDialog();
               }
               setIntegrationInterval(enabledInfo.getInterval());
               setMaxReports(enabledInfo.getMaxReports());
               setMaxValuesReport(enabledInfo.getMaxProcessesPerReport());
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
      firstReportRead = false;

      dumpFileWriter.writeCPUProcessReportInfo(target.toString(),
                                               getExecutionUnit(),
                                               getIntegrationInterval(),
                                               getMaxReports(),
                                               getMaxValuesReport(),
                                               profiledProcesses);

      dumpFileWriter.writeCPUProcessReportsEnabled(true,
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
      CPUProcessReports reportsInfo;
      int numReports = 0;

      tick = ((lastReport != null) ? lastReport.getTick() : 0);
      ntick = ((lastReport != null) ? lastReport.getNTick() : 0);
      reportsInfo = target.getCPUProcessReports(monitor, getExecutionUnit(), tick, ntick);
      if (reportsInfo != null)
      {
         CPUProcessReport[] reports;

         reports = (CPUProcessReport[]) reportsInfo.getReports();
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
               CPUProcessReport[] lostReports = (CPUProcessReport[])
                  lostReportList.toArray(new CPUProcessReport[0]);
               dumpFileWriter.writeCPUProcessReports(lostReports, getExecutionUnit(), true);
               asyncExec(new AddReportsRunner(lostReports));
               numReports += lostReports.length;
            }
            setNanoSecondsForReports(reports);
            dumpFileWriter.writeCPUProcessReports(reports, getExecutionUnit(), true);
            syncExec(new AddReportsRunner(reportsInfo));
            numReports += reports.length;
            lastReport = (CPUProcessReport) reports[reports.length - 1];
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
      return CPUProcessReport.getInstance(tick,
                                          ntick,
                                          interval,
                                          0,
                                          new CPUProcessLoad[0]);
   }

   public void saveReports(IProgressMonitor monitor, String file)
      throws IOException
   {
      if (file.toLowerCase().endsWith(".report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
            new ReportDumpXMLConverter();
         reportDumpXMLConverter.convertCPUProcessReports(monitor,
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
            ((CPUProcessProfilerEditor) getProfilerEditor()).
               setMaxValuesReport(maxValuesReport);
         }
      }
   }

   private class SetProfiledProcessesRunner extends ProfilerEditorRunner
   {
      public void run()
      {
         if (isProfilerEditorOpen())
         {
            ((CPUProcessProfilerEditor) getProfilerEditor()).
               setProfiledProcesses(profiledProcesses);
         }
      }
   }
}
