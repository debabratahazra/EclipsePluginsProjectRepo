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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;
import com.ose.system.UserReport;

/**
 * This class is used for converting a profiling report dump file to a profiling
 * report XML file.
 */
public class ReportDumpXMLConverter
{
   /**
    * Convert a CPU load report dump file to a CPU load report XML file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input CPU load report dump file.
    * @param to  the output CPU load report XML file.
    * @throws IOException  if an I/O exception occurred.
    */
   public void convertCPUReports(IProgressMonitor monitor, File from, File to)
      throws IOException
   {
      ReportReaderHandler reportReaderHandler;
      ReportDumpReader reportDumpReader;
      ReportReaderDelegate reportReaderDelegate;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportDumpReader = new ReportDumpReader(reportReaderHandler);
      reportReaderDelegate = new CPUReportReaderDelegate(reportDumpReader, from);
      convertReports(reportReaderDelegate, reportReaderHandler, from, to);
   }

   /**
    * Convert a CPU priority level load report dump file to a CPU priority level
    * load report XML file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input CPU priority level load report dump file.
    * @param to  the output CPU priority level load report XML file.
    * @throws IOException  if an I/O exception occurred.
    */
   public void convertCPUPriorityReports(IProgressMonitor monitor,
                                         File from,
                                         File to)
      throws IOException
   {
      ReportReaderHandler reportReaderHandler;
      ReportDumpReader reportDumpReader;
      ReportReaderDelegate reportReaderDelegate;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportDumpReader = new ReportDumpReader(reportReaderHandler);
      reportReaderDelegate =
         new CPUPriorityReportReaderDelegate(reportDumpReader, from);
      convertReports(reportReaderDelegate, reportReaderHandler, from, to);
   }

   /**
    * Convert a CPU process level load report dump file to a CPU process level
    * load report XML file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input CPU process level load report dump file.
    * @param to  the output CPU process level load report XML file.
    * @throws IOException  if an I/O exception occurred.
    */
   public void convertCPUProcessReports(IProgressMonitor monitor,
                                        File from,
                                        File to)
      throws IOException
   {
      ReportReaderHandler reportReaderHandler;
      ReportDumpReader reportDumpReader;
      ReportReaderDelegate reportReaderDelegate;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportDumpReader = new ReportDumpReader(reportReaderHandler);
      reportReaderDelegate =
         new CPUProcessReportReaderDelegate(reportDumpReader, from);
      convertReports(reportReaderDelegate, reportReaderHandler, from, to);
   }

   /**
    * Convert a CPU block level load report dump file to a CPU block level load
    * report XML file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input CPU block level load report dump file.
    * @param to  the output CPU block level load report XML file.
    * @throws IOException  if an I/O exception occurred.
    */
   public void convertCPUBlockReports(IProgressMonitor monitor,
                                      File from,
                                      File to)
      throws IOException
   {
      ReportReaderHandler reportReaderHandler;
      ReportDumpReader reportDumpReader;
      ReportReaderDelegate reportReaderDelegate;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportDumpReader = new ReportDumpReader(reportReaderHandler);
      reportReaderDelegate =
         new CPUBlockReportReaderDelegate(reportDumpReader, from);
      convertReports(reportReaderDelegate, reportReaderHandler, from, to);
   }

   /**
    * Convert a user report dump file to a user report XML file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input user report dump file.
    * @param to  the output user report XML file.
    * @throws IOException  if an I/O exception occurred.
    */
   public void convertUserReports(IProgressMonitor monitor, File from, File to)
      throws IOException
   {
      ReportReaderHandler reportReaderHandler;
      ReportDumpReader reportDumpReader;
      ReportReaderDelegate reportReaderDelegate;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      reportReaderHandler = new ReportReaderHandler(monitor, to);
      reportDumpReader = new ReportDumpReader(reportReaderHandler);
      reportReaderDelegate =
         new UserReportReaderDelegate(reportDumpReader, from);
      convertReports(reportReaderDelegate, reportReaderHandler, from, to);
   }

   private void convertReports(ReportReaderDelegate reportReaderDelegate,
                               ReportReaderHandler reportReaderHandler,
                               File from,
                               File to)
      throws IOException
   {
      boolean success = false;

      if (to.isFile() && !to.canWrite())
      {
         throw new IOException("File " + to.getAbsolutePath() + " is not writable");
      }

      try
      {
         reportReaderDelegate.readReports(from);
         success = true;
      }
      catch (OperationCanceledException e)
      {
         success = true;
         throw e;
      }
      finally
      {
         reportReaderHandler.close();
         if (!success)
         {
            to.delete();
         }
      }
   }

   private abstract class ReportReaderDelegate
   {
      final ReportDumpReader reportDumpReader;
      final File file;

      ReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         this.reportDumpReader = reportDumpReader;
         this.file = file;
      }

      abstract void readReports(File file) throws IOException;
   }

   private class CPUReportReaderDelegate extends ReportReaderDelegate
   {
      CPUReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         super(reportDumpReader, file);
      }

      void readReports(File file) throws IOException
      {
         reportDumpReader.readCPUReports(file);
      }
   }

   private class CPUPriorityReportReaderDelegate extends ReportReaderDelegate
   {
      CPUPriorityReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         super(reportDumpReader, file);
      }

      void readReports(File file) throws IOException
      {
         reportDumpReader.readCPUPriorityReports(file);
      }
   }

   private class CPUProcessReportReaderDelegate extends ReportReaderDelegate
   {
      CPUProcessReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         super(reportDumpReader, file);
      }

      void readReports(File file) throws IOException
      {
         reportDumpReader.readCPUProcessReports(file);
      }
   }

   private class CPUBlockReportReaderDelegate extends ReportReaderDelegate
   {
      CPUBlockReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         super(reportDumpReader, file);
      }

      void readReports(File file) throws IOException
      {
         reportDumpReader.readCPUBlockReports(file);
      }
   }

   private class UserReportReaderDelegate extends ReportReaderDelegate
   {
      UserReportReaderDelegate(ReportDumpReader reportDumpReader, File file)
      {
         super(reportDumpReader, file);
      }

      void readReports(File file) throws IOException
      {
         reportDumpReader.readUserReports(file);
      }
   }

   private static class ReportReaderHandler implements ReportReaderClient
   {
      private final IProgressMonitor monitor;
      private final File file;
      private ReportXMLWriter reportXMLWriter;

      ReportReaderHandler(IProgressMonitor monitor, File file)
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
            reportXMLWriter = ReportXMLWriter.createCPUReportXMLWriter(
                  file,
                  target,
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  executionUnit,
                  integrationInterval,
                  maxReports,
                  priorityLimit,
                  seconds,
                  secondsTick,
                  secondsNTick);
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
            reportXMLWriter = ReportXMLWriter.createCPUPriorityReportXMLWriter(
                  file,
                  target,
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  executionUnit,
                  integrationInterval,
                  maxReports,
                  seconds,
                  secondsTick,
                  secondsNTick);
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
            reportXMLWriter = ReportXMLWriter.createCPUProcessReportXMLWriter(
                  file,
                  target,
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  executionUnit,
                  integrationInterval,
                  maxReports,
                  maxProcessesPerReport,
                  profiledProcesses,
                  seconds,
                  secondsTick,
                  secondsNTick);
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
            reportXMLWriter = ReportXMLWriter.createCPUBlockReportXMLWriter(
                  file,
                  target,
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  executionUnit,
                  integrationInterval,
                  maxReports,
                  maxBlocksPerReport,
                  seconds,
                  secondsTick,
                  secondsNTick);
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
            reportXMLWriter = ReportXMLWriter.createUserReportXMLWriter(
                  file,
                  target,
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  integrationInterval,
                  maxReports,
                  maxValuesPerReport,
                  reportNumber,
                  continuous,
                  maxMin,
                  multiple,
                  seconds,
                  secondsTick,
                  secondsNTick);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void processRead(int id, String name)
      {
         reportXMLWriter.writeProcess(id, name);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void blockRead(int id, String name)
      {
         reportXMLWriter.writeBlock(id, name);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuReportRead(CPUReport report)
      {
         reportXMLWriter.writeReport(report);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuPriorityReportRead(CPUPriorityReport report)
      {
         reportXMLWriter.writeReport(report);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuProcessReportRead(CPUProcessReport report)
      {
         reportXMLWriter.writeReport(report);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void cpuBlockReportRead(CPUBlockReport report)
      {
         reportXMLWriter.writeReport(report);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void userReportRead(UserReport report)
      {
         reportXMLWriter.writeReport(report);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void close()
      {
         if (reportXMLWriter != null)
         {
            reportXMLWriter.close();
         }
      }
   }
}
