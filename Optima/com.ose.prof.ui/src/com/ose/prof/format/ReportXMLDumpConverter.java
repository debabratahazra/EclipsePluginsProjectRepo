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

package com.ose.prof.format;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.xml.sax.SAXException;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.UserReport;

/**
 * This class is used for converting a profiling report XML file to a profiling
 * report dump file.
 */
public class ReportXMLDumpConverter
{
   /**
    * Convert a profiling report XML file to a profiling report dump file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input profiling report XML file.
    * @param to  the output profiling report dump file.
    * @throws IOException  if an I/O exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    */
   public void convert(IProgressMonitor monitor, File from, File to)
      throws IOException, SAXException, ParserConfigurationException
   {
      ReportReaderHandler reportReaderHandler;
      ReportXMLReader reportXMLReader;
      boolean success = false;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      if (to.isFile() && !to.canWrite())
      {
         throw new IOException("File " + to.getAbsolutePath() + " is not writable");
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportXMLReader = new ReportXMLReader(reportReaderHandler);
      try
      {
         reportXMLReader.read(from);
         success = true;
      }
      catch (OperationCanceledException e)
      {
         success = true;
         throw e;
      }
      finally
      {
         try
         {
            reportReaderHandler.close();
         }
         catch (IOException e)
         {
            success = false;
            throw e;
         }
         finally
         {
            if (!success)
            {
               to.delete();
            }
         }
      }
   }

   private static class ReportReaderHandler implements ReportReaderClient
   {
      private static final int REPORT_THRESHOLD = 500;

      private static final CPUReport[] CPU_REPORT_TYPE;
      private static final CPUPriorityReport[] CPU_PRIORITY_REPORT_TYPE;
      private static final CPUProcessReport[] CPU_PROCESS_REPORT_TYPE;
      private static final CPUBlockReport[] CPU_BLOCK_REPORT_TYPE;
      private static final UserReport[] USER_REPORT_TYPE;

      private final IProgressMonitor monitor;
      private final File file;

      private SystemModelDumpFileWriter reportDumpWriter;
      private List cpuReports;
      private List cpuPriorityReports;
      private List cpuProcessReports;
      private List cpuBlockReports;
      private List userReports;
      private boolean processesDone;
      private boolean blocksDone;
      private short executionUnit;
      private int reportNumber;
      private boolean continuous;
      private boolean maxMin;
      private boolean multiple;

      static
      {
         CPU_REPORT_TYPE = new CPUReport[0];
         CPU_PRIORITY_REPORT_TYPE = new CPUPriorityReport[0];
         CPU_PROCESS_REPORT_TYPE = new CPUProcessReport[0];
         CPU_BLOCK_REPORT_TYPE = new CPUBlockReport[0];
         USER_REPORT_TYPE = new UserReport[0];
      }

      ReportReaderHandler(IProgressMonitor monitor, File file)
         throws IOException
      {
         this.monitor = monitor;
         this.file = file;
      }

      public void cpuReportSettingsRead(String target,
                                        boolean bigEndian,
                                        int osType,
                                        int numExecutionUnits,
                                        int tickLength,
                                        int microTickFrequency,
                                        short executionUnit,
                                        int integrationInterval,
                                        int maxReports,
                                        int priorityLimit,
                                        int seconds,
                                        int secondsTick,
                                        int secondsNTick)
      {
         try
         {
            cpuReports = new ArrayList(REPORT_THRESHOLD);
            this.executionUnit = executionUnit;
            reportDumpWriter = new SystemModelDumpFileWriter(file,
                                                             bigEndian,
                                                             hashCode());
            reportDumpWriter.writeSystemInfo(osType,
                                             0,
                                             null,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             true);
            reportDumpWriter.writeCPUReportInfo(target,
                                                executionUnit,
                                                integrationInterval,
                                                maxReports,
                                                priorityLimit);
            reportDumpWriter.writeCPUReportsEnabled(true,
                                                    integrationInterval,
                                                    priorityLimit,
                                                    maxReports,
                                                    seconds,
                                                    secondsTick,
                                                    secondsNTick,
                                                    true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void cpuPriorityReportSettingsRead(String target,
                                                boolean bigEndian,
                                                int osType,
                                                int numExecutionUnits,
                                                int tickLength,
                                                int microTickFrequency,
                                                short executionUnit,
                                                int integrationInterval,
                                                int maxReports,
                                                int seconds,
                                                int secondsTick,
                                                int secondsNTick)
      {
         try
         {
            cpuPriorityReports = new ArrayList(REPORT_THRESHOLD);
            this.executionUnit = executionUnit;
            reportDumpWriter = new SystemModelDumpFileWriter(file,
                                                             bigEndian,
                                                             hashCode());
            reportDumpWriter.writeSystemInfo(osType,
                                             0,
                                             null,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             true);
            reportDumpWriter.writeCPUPriorityReportInfo(target,
                                                        executionUnit,
                                                        integrationInterval,
                                                        maxReports);
            reportDumpWriter.writeCPUPriorityReportsEnabled(true,
                                                            integrationInterval,
                                                            maxReports,
                                                            seconds,
                                                            secondsTick,
                                                            secondsNTick,
                                                            true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void cpuProcessReportSettingsRead(String target,
                                               boolean bigEndian,
                                               int osType,
                                               int numExecutionUnits,
                                               int tickLength,
                                               int microTickFrequency,
                                               short executionUnit,
                                               int integrationInterval,
                                               int maxReports,
                                               int maxProcessesPerReport,
                                               String profiledProcesses,
                                               int seconds,
                                               int secondsTick,
                                               int secondsNTick)
      {
         try
         {
            cpuProcessReports = new ArrayList(REPORT_THRESHOLD);
            this.executionUnit = executionUnit;
            reportDumpWriter = new SystemModelDumpFileWriter(file,
                                                             bigEndian,
                                                             hashCode());
            reportDumpWriter.writeSystemInfo(osType,
                                             0,
                                             null,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             true);
            reportDumpWriter.writeCPUProcessReportInfo(target,
                                                       executionUnit,
                                                       integrationInterval,
                                                       maxReports,
                                                       maxProcessesPerReport,
                                                       profiledProcesses);
            reportDumpWriter.writeCPUProcessReportsEnabled(true,
                                                           integrationInterval,
                                                           maxReports,
                                                           maxProcessesPerReport,
                                                           seconds,
                                                           secondsTick,
                                                           secondsNTick,
                                                           true);
            reportDumpWriter.startWritingProcesses(0, 0, true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void cpuBlockReportSettingsRead(String target,
                                             boolean bigEndian,
                                             int osType,
                                             int numExecutionUnits,
                                             int tickLength,
                                             int microTickFrequency,
                                             short executionUnit,
                                             int integrationInterval,
                                             int maxReports,
                                             int maxBlocksPerReport,
                                             int seconds,
                                             int secondsTick,
                                             int secondsNTick)
      {
         try
         {
            cpuBlockReports = new ArrayList(REPORT_THRESHOLD);
            this.executionUnit = executionUnit;
            reportDumpWriter = new SystemModelDumpFileWriter(file,
                                                             bigEndian,
                                                             hashCode());
            reportDumpWriter.writeSystemInfo(osType,
                                             0,
                                             null,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             true);
            reportDumpWriter.writeCPUBlockReportInfo(target,
                                                     executionUnit,
                                                     integrationInterval,
                                                     maxReports,
                                                     maxBlocksPerReport);
            reportDumpWriter.writeCPUBlockReportsEnabled(true,
                                                         integrationInterval,
                                                         maxReports,
                                                         maxBlocksPerReport,
                                                         seconds,
                                                         secondsTick,
                                                         secondsNTick,
                                                         true);
            reportDumpWriter.startWritingBlocks(0, 0, true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void userReportSettingsRead(String target,
                                         boolean bigEndian,
                                         int osType,
                                         int numExecutionUnits,
                                         int tickLength,
                                         int microTickFrequency,
                                         int integrationInterval,
                                         int maxReports,
                                         int maxValuesPerReport,
                                         int reportNumber,
                                         boolean continuous,
                                         boolean maxMin,
                                         boolean multiple,
                                         int seconds,
                                         int secondsTick,
                                         int secondsNTick)
      {
         try
         {
            userReports = new ArrayList(REPORT_THRESHOLD);
            this.reportNumber = reportNumber;
            this.continuous = continuous;
            this.maxMin = maxMin;
            this.multiple = multiple;
            reportDumpWriter = new SystemModelDumpFileWriter(file,
                                                             bigEndian,
                                                             hashCode());
            reportDumpWriter.writeSystemInfo(osType,
                                             0,
                                             null,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             true);
            reportDumpWriter.writeUserReportInfo(target,
                                                 integrationInterval,
                                                 maxReports,
                                                 maxValuesPerReport,
                                                 reportNumber,
                                                 continuous,
                                                 maxMin,
                                                 multiple);
            reportDumpWriter.writeUserReportsEnabled(reportNumber,
                                                     true,
                                                     integrationInterval,
                                                     maxReports,
                                                     maxValuesPerReport,
                                                     seconds,
                                                     secondsTick,
                                                     secondsNTick,
                                                     true);
            reportDumpWriter.startWritingProcesses(0, 0, true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void processRead(int id, String name)
      {
         try
         {
            reportDumpWriter.writeProcess(id, name);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void blockRead(int id, String name)
      {
         try
         {
            reportDumpWriter.writeBlock(id, name);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuReportRead(CPUReport report)
      {
         try
         {
            cpuReports.add(report);
            if (cpuReports.size() > REPORT_THRESHOLD)
            {
               flushCPUReports();
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuPriorityReportRead(CPUPriorityReport report)
      {
         try
         {
            cpuPriorityReports.add(report);
            if (cpuPriorityReports.size() > REPORT_THRESHOLD)
            {
               flushCPUPriorityReports();
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuProcessReportRead(CPUProcessReport report)
      {
         try
         {
            finishProcesses();
            cpuProcessReports.add(report);
            if (cpuProcessReports.size() > REPORT_THRESHOLD)
            {
               flushCPUProcessReports();
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuBlockReportRead(CPUBlockReport report)
      {
         try
         {
            finishBlocks();
            cpuBlockReports.add(report);
            if (cpuBlockReports.size() > REPORT_THRESHOLD)
            {
               flushCPUBlockReports();
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void userReportRead(UserReport report)
      {
         try
         {
            finishProcesses();
            userReports.add(report);
            if (userReports.size() > REPORT_THRESHOLD)
            {
               flushUserReports();
            }
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void close() throws IOException
      {
         if (reportDumpWriter != null)
         {
            try
            {
               if (cpuReports != null)
               {
                  if (!cpuReports.isEmpty())
                  {
                     flushCPUReports();
                  }
               }
               else if (cpuPriorityReports != null)
               {
                  if (!cpuPriorityReports.isEmpty())
                  {
                     flushCPUPriorityReports();
                  }
               }
               else if (cpuProcessReports != null)
               {
                  finishProcesses();
                  if (!cpuProcessReports.isEmpty())
                  {
                     flushCPUProcessReports();
                  }
               }
               else if (cpuBlockReports != null)
               {
                  finishBlocks();
                  if (!cpuBlockReports.isEmpty())
                  {
                     flushCPUBlockReports();
                  }
               }
               else if (userReports != null)
               {
                  finishProcesses();
                  if (!userReports.isEmpty())
                  {
                     flushUserReports();
                  }
               }
            }
            finally
            {
               reportDumpWriter.close();
            }
         }
      }

      private void finishProcesses() throws IOException
      {
         if (!processesDone)
         {
            reportDumpWriter.endWritingProcesses(0);
            processesDone = true;
         }
      }

      private void finishBlocks() throws IOException
      {
         if (!blocksDone)
         {
            reportDumpWriter.endWritingBlocks(0);
            blocksDone = true;
         }
      }

      private void flushCPUReports() throws IOException
      {
         CPUReport[] reports =
            (CPUReport[]) cpuReports.toArray(CPU_REPORT_TYPE);
         reportDumpWriter.writeCPUReports(reports, executionUnit, true);
         cpuReports.clear();
      }

      private void flushCPUPriorityReports() throws IOException
      {
         CPUPriorityReport[] reports = (CPUPriorityReport[])
            cpuPriorityReports.toArray(CPU_PRIORITY_REPORT_TYPE);
         reportDumpWriter.writeCPUPriorityReports(reports, executionUnit, true);
         cpuPriorityReports.clear();
      }

      private void flushCPUProcessReports() throws IOException
      {
         CPUProcessReport[] reports = (CPUProcessReport[])
            cpuProcessReports.toArray(CPU_PROCESS_REPORT_TYPE);
         reportDumpWriter.writeCPUProcessReports(reports, executionUnit, true);
         cpuProcessReports.clear();
      }

      private void flushCPUBlockReports() throws IOException
      {
         CPUBlockReport[] reports = (CPUBlockReport[])
            cpuBlockReports.toArray(CPU_BLOCK_REPORT_TYPE);
         reportDumpWriter.writeCPUBlockReports(reports, executionUnit, true);
         cpuBlockReports.clear();
      }

      private void flushUserReports() throws IOException
      {
         UserReport[] reports =
            (UserReport[]) userReports.toArray(USER_REPORT_TYPE);
         reportDumpWriter.writeUserReports(reports,
                                           reportNumber,
                                           continuous,
                                           maxMin,
                                           multiple,
                                           true);
         userReports.clear();
      }
   }
}
