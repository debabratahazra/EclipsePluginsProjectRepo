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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;
import com.ose.system.CPUReport;
import com.ose.system.TargetReport;
import com.ose.system.UserReport;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;

/**
 * This class is used for writing profiling report XML files.
 */
public class ReportXMLWriter
{
   private static final int REPORT_TYPE_CPU = 0;

   private static final int REPORT_TYPE_CPU_PRIORITY = 1;

   private static final int REPORT_TYPE_CPU_PROCESS = 2;

   private static final int REPORT_TYPE_CPU_BLOCK = 3;

   private static final int REPORT_TYPE_USER = 4;

   private final Object lock;

   private final PrintStream out;

   private final int reportType;

   private final boolean maxMin;

   private boolean open;

   private boolean reportsStarted;

   private ReportXMLWriter(File file, int reportType, boolean maxMin)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      lock = new Object();
      out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
      this.reportType = reportType;
      this.maxMin = maxMin;
      open = true;

      writeXMLDeclaration("ISO-8859-1");
      writeStylesheet(ReportXMLTags.STYLESHEET_TYPE, ReportXMLTags.STYLESHEET_PATH);
      writeDoctype(ReportXMLTags.TAG_REPORTS, ReportXMLTags.DTD_PATH);
      writeStartTag(0, ReportXMLTags.TAG_REPORTS);
      writeIndent(1);
   }

   /**
    * Create a new CPU load report XML writer object.
    *
    * @param file  the CPU load report XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU load reports
    * were retrieved from.
    * @param integrationInterval  the CPU load reporting integration interval in
    * ticks.
    * @param maxReports  the maximum number of CPU load reports kept in the
    * target.
    * @param priorityLimit  the CPU load reporting priority limit.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU load reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @return a new CPU load report XML writer object.
    * @throws IOException  if an I/O exception occurred.
    */
   public static ReportXMLWriter createCPUReportXMLWriter(
         File file,
         String target,
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
      throws IOException
   {
      ReportXMLWriter reportXMLWriter;

      if ((file == null) || (target == null))
      {
         throw new IllegalArgumentException();
      }

      reportXMLWriter = new ReportXMLWriter(file, REPORT_TYPE_CPU, false);
      reportXMLWriter.writeLine(
            "<" + ReportXMLTags.TAG_CPU_REPORTS + " "
            + ReportXMLTags.ATTR_CPU_REPORTS_TARGET + "=\""
            + target + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_BYTE_ORDER + "=\""
            + toByteOrderString(bigEndian) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_OS_TYPE + "=\""
            + toU32String(osType) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_NUM_EXECUTION_UNITS + "=\""
            + toU32String(numExecutionUnits) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_TICK_LENGTH + "=\""
            + toU32String(tickLength) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_NTICK_FREQUENCY + "=\""
            + toU32String(microTickFrequency) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_EXECUTION_UNIT + "=\""
            + toU16String(executionUnit) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_INTERVAL + "=\""
            + toU32String(integrationInterval) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_MAX_REPORTS + "=\""
            + toU32String(maxReports) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_PRIORITY_LIMIT + "=\""
            + toU32String(priorityLimit) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_SECONDS + "=\""
            + toU32String(seconds) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_SECONDS_TICK + "=\""
            + toU32String(secondsTick) + "\" "
            + ReportXMLTags.ATTR_CPU_REPORTS_SECONDS_NTICK + "=\""
            + toU32String(secondsNTick) + "\">");
      return reportXMLWriter;
   }

   /**
    * Create a new CPU priority level load report XML writer object.
    *
    * @param file  the CPU priority level load report XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU priority level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU priority level load reporting
    * integration interval in ticks.
    * @param maxReports  the maximum number of CPU priority level load reports
    * kept in the target.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU priority level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @return a new CPU priority level load report XML writer object.
    * @throws IOException  if an I/O exception occurred.
    */
   public static ReportXMLWriter createCPUPriorityReportXMLWriter(
         File file,
         String target,
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
      throws IOException
   {
      ReportXMLWriter reportXMLWriter;

      if ((file == null) || (target == null))
      {
         throw new IllegalArgumentException();
      }

      reportXMLWriter = new ReportXMLWriter(file, REPORT_TYPE_CPU_PRIORITY, false);
      reportXMLWriter.writeLine(
            "<" + ReportXMLTags.TAG_CPU_PRIO_REPORTS + " "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_TARGET + "=\""
            + target + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_BYTE_ORDER + "=\""
            + toByteOrderString(bigEndian) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_OS_TYPE + "=\""
            + toU32String(osType) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_NUM_EXECUTION_UNITS + "=\""
            + toU32String(numExecutionUnits) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_TICK_LENGTH + "=\""
            + toU32String(tickLength) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_NTICK_FREQUENCY + "=\""
            + toU32String(microTickFrequency) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_EXECUTION_UNIT + "=\""
            + toU16String(executionUnit) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_INTERVAL + "=\""
            + toU32String(integrationInterval) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_MAX_REPORTS + "=\""
            + toU32String(maxReports) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS + "=\""
            + toU32String(seconds) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS_TICK + "=\""
            + toU32String(secondsTick) + "\" "
            + ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS_NTICK + "=\""
            + toU32String(secondsNTick) + "\">");
      return reportXMLWriter;
   }

   /**
    * Create a new CPU process level load report XML writer object.
    *
    * @param file  the CPU process level load report XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU process level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU process level load reporting
    * integration interval in ticks.
    * @param maxReports  the maximum number of CPU process level load reports
    * kept in the target.
    * @param maxProcessesPerReport  the maximum number of processes per CPU
    * process level load report.
    * @param profiledProcesses  the name of the profiled processes settings
    * file.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU process level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @return a new CPU process level load report XML writer object.
    * @throws IOException  if an I/O exception occurred.
    */
   public static ReportXMLWriter createCPUProcessReportXMLWriter(
         File file,
         String target,
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
      throws IOException
   {
      ReportXMLWriter reportXMLWriter;

      if ((file == null) || (target == null) || (profiledProcesses == null))
      {
         throw new IllegalArgumentException();
      }

      reportXMLWriter = new ReportXMLWriter(file, REPORT_TYPE_CPU_PROCESS, false);
      reportXMLWriter.writeLine(
            "<" + ReportXMLTags.TAG_CPU_PROC_REPORTS + " "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_TARGET + "=\""
            + target + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_BYTE_ORDER + "=\""
            + toByteOrderString(bigEndian) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_OS_TYPE + "=\""
            + toU32String(osType) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_NUM_EXECUTION_UNITS + "=\""
            + toU32String(numExecutionUnits) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_TICK_LENGTH + "=\""
            + toU32String(tickLength) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_NTICK_FREQUENCY + "=\""
            + toU32String(microTickFrequency) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_EXECUTION_UNIT + "=\""
            + toU16String(executionUnit) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_INTERVAL + "=\""
            + toU32String(integrationInterval) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_MAX_REPORTS + "=\""
            + toU32String(maxReports) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_MAX_PROCS_REPORT + "=\""
            + toU32String(maxProcessesPerReport) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_PROFILED_PROCS + "=\""
            + profiledProcesses + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS + "=\""
            + toU32String(seconds) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS_TICK + "=\""
            + toU32String(secondsTick) + "\" "
            + ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS_NTICK + "=\""
            + toU32String(secondsNTick) + "\">");
      return reportXMLWriter;
   }

   /**
    * Create a new CPU block level load report XML writer object.
    *
    * @param file  the CPU block level load report XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU block level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU block level load reporting integration
    * interval in ticks.
    * @param maxReports  the maximum number of CPU block level load reports kept
    * in the target.
    * @param maxBlocksPerReport  the maximum number of blocks per CPU block
    * level load report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU block level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @return a new CPU block level load report XML writer object.
    * @throws IOException  if an I/O exception occurred.
    */
   public static ReportXMLWriter createCPUBlockReportXMLWriter(
         File file,
         String target,
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
      throws IOException
   {
      ReportXMLWriter reportXMLWriter;

      if ((file == null) || (target == null))
      {
         throw new IllegalArgumentException();
      }

      reportXMLWriter = new ReportXMLWriter(file, REPORT_TYPE_CPU_BLOCK, false);
      reportXMLWriter.writeLine(
            "<" + ReportXMLTags.TAG_CPU_BLOCK_REPORTS + " "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_TARGET + "=\""
            + target + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_BYTE_ORDER + "=\""
            + toByteOrderString(bigEndian) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_OS_TYPE + "=\""
            + toU32String(osType) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_NUM_EXECUTION_UNITS + "=\""
            + toU32String(numExecutionUnits) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_TICK_LENGTH + "=\""
            + toU32String(tickLength) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_NTICK_FREQUENCY + "=\""
            + toU32String(microTickFrequency) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_EXECUTION_UNIT + "=\""
            + toU16String(executionUnit) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_INTERVAL + "=\""
            + toU32String(integrationInterval) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_MAX_REPORTS + "=\""
            + toU32String(maxReports) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_MAX_BLOCKS_REPORT + "=\""
            + toU32String(maxBlocksPerReport) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS + "=\""
            + toU32String(seconds) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS_TICK + "=\""
            + toU32String(secondsTick) + "\" "
            + ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS_NTICK + "=\""
            + toU32String(secondsNTick) + "\">");
      return reportXMLWriter;
   }

   /**
    * Create a new user report XML writer object.
    *
    * @param file  the user report XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param integrationInterval  the user reporting integration interval in
    * ticks.
    * @param maxReports  the maximum number of user reports kept in the target.
    * @param maxValuesPerReport  the maximum number of values per user report.
    * @param reportNumber  the user report number.
    * @param continuous  true if the user reporting is continuous, false
    * otherwise.
    * @param maxMin  true if the maximum and minimum user report measurement
    * values are tracked during each integration interval, false otherwise.
    * @param multiple  true if this user report type supports multiple user
    * report measurement values per user report, false if it supports only a
    * single user report measurement value per user report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * user reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @return a new user report XML writer object.
    * @throws IOException  if an I/O exception occurred.
    */
   public static ReportXMLWriter createUserReportXMLWriter(
         File file,
         String target,
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
      throws IOException
   {
      ReportXMLWriter reportXMLWriter;

      if ((file == null) || (target == null))
      {
         throw new IllegalArgumentException();
      }

      reportXMLWriter = new ReportXMLWriter(file, REPORT_TYPE_USER, maxMin);
      reportXMLWriter.writeLine(
            "<" + ReportXMLTags.TAG_USER_REPORTS + " "
            + ReportXMLTags.ATTR_USER_REPORTS_TARGET + "=\""
            + target + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_BYTE_ORDER + "=\""
            + toByteOrderString(bigEndian) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_OS_TYPE + "=\""
            + toU32String(osType) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_NUM_EXECUTION_UNITS + "=\""
            + toU32String(numExecutionUnits) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_TICK_LENGTH + "=\""
            + toU32String(tickLength) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_NTICK_FREQUENCY + "=\""
            + toU32String(microTickFrequency) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_INTERVAL + "=\""
            + toU32String(integrationInterval) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_MAX_REPORTS + "=\""
            + toU32String(maxReports) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_MAX_VALUES_REPORT + "=\""
            + toU32String(maxValuesPerReport) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_REPORT_NUMBER + "=\""
            + toU32String(reportNumber) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_CONTINUOUS + "=\""
            + continuous + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_MAX_MIN + "=\""
            + maxMin + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_MULTIPLE + "=\""
            + multiple + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_SECONDS + "=\""
            + toU32String(seconds) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_SECONDS_TICK + "=\""
            + toU32String(secondsTick) + "\" "
            + ReportXMLTags.ATTR_USER_REPORTS_SECONDS_NTICK + "=\""
            + toU32String(secondsNTick) + "\">");
      return reportXMLWriter;
   }

   /**
    * Write a process. This method must be called before the writeReport()
    * method is called.
    *
    * @param id  the ID of the process to write.
    * @param name  the name of the process to write.
    */
   public void writeProcess(int id, String name)
   {
      if (name == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (lock)
      {
         checkState();

         writeIndent(2);
         writeLine("<" + ReportXMLTags.TAG_PROCESS + " "
                   + ReportXMLTags.ATTR_PROCESS_ID + "=\""
                   + toU32HexString(id) + "\" "
                   + ReportXMLTags.ATTR_PROCESS_NAME + "=\""
                   + toCDataString(name) + "\"/>");
      }
   }

   /**
    * Write a block. This method must be called before the writeReport()
    * method is called.
    *
    * @param id  the ID of the block to write.
    * @param name  the name of the block to write.
    */
   public void writeBlock(int id, String name)
   {
      if (name == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (lock)
      {
         checkState();

         writeIndent(2);
         writeLine("<" + ReportXMLTags.TAG_BLOCK + " "
                   + ReportXMLTags.ATTR_BLOCK_ID + "=\""
                   + toU32HexString(id) + "\" "
                   + ReportXMLTags.ATTR_BLOCK_NAME + "=\""
                   + toCDataString(name) + "\"/>");
      }
   }

   /**
    * Write a profiling report.
    *
    * @param report  the profiling report to write.
    */
   public void writeReport(TargetReport report)
   {
      if (report == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (lock)
      {
         checkState(report);
         reportsStarted = true;

         if (report instanceof CPUReport)
         {
            writeCPUReport((CPUReport) report);
         }
         else if (report instanceof CPUPriorityReport)
         {
            writeCPUPriorityReport((CPUPriorityReport) report);
         }
         else if (report instanceof CPUProcessReport)
         {
            writeCPUProcessReport((CPUProcessReport) report);
         }
         else if (report instanceof CPUBlockReport)
         {
            writeCPUBlockReport((CPUBlockReport) report);
         }
         else if (report instanceof UserReport)
         {
            writeUserReport((UserReport) report);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   /**
    * Close this profiling report XML writer and release any resources
    * associated with it.
    */
   public void close()
   {
      synchronized (lock)
      {
         if (open)
         {
            if (reportType == REPORT_TYPE_CPU)
            {
               writeEndTag(1, ReportXMLTags.TAG_CPU_REPORTS);
            }
            else if (reportType == REPORT_TYPE_CPU_PRIORITY)
            {
               writeEndTag(1, ReportXMLTags.TAG_CPU_PRIO_REPORTS);
            }
            else if (reportType == REPORT_TYPE_CPU_PROCESS)
            {
               writeEndTag(1, ReportXMLTags.TAG_CPU_PROC_REPORTS);
            }
            else if (reportType == REPORT_TYPE_CPU_BLOCK)
            {
               writeEndTag(1, ReportXMLTags.TAG_CPU_BLOCK_REPORTS);
            }
            else if (reportType == REPORT_TYPE_USER)
            {
               writeEndTag(1, ReportXMLTags.TAG_USER_REPORTS);
            }
            writeEndTag(0, ReportXMLTags.TAG_REPORTS);
            out.close();
            open = false;
         }
      }
   }

   private void checkState() throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("ReportXMLWriter is closed");
      }
      else if ((reportType != REPORT_TYPE_CPU_PROCESS) &&
               (reportType != REPORT_TYPE_CPU_BLOCK) &&
               (reportType != REPORT_TYPE_USER))
      {
         throw new IllegalStateException(
               "Cannot write processes for this report type");
      }
      else if (reportsStarted)
      {
         throw new IllegalStateException("Too late to write processes");
      }
   }

   private void checkState(TargetReport report) throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("ReportXMLWriter is closed");
      }
      else if ((report instanceof CPUReport) &&
               (reportType != REPORT_TYPE_CPU))
      {
         throw new IllegalStateException("Unexpected report type");
      }
      else if ((report instanceof CPUPriorityReport) &&
               (reportType != REPORT_TYPE_CPU_PRIORITY))
      {
         throw new IllegalStateException("Unexpected report type");
      }
      else if ((report instanceof CPUProcessReport) &&
               (reportType != REPORT_TYPE_CPU_PROCESS))
      {
         throw new IllegalStateException("Unexpected report type");
      }
      else if ((report instanceof CPUBlockReport) &&
               (reportType != REPORT_TYPE_CPU_BLOCK))
      {
         throw new IllegalStateException("Unexpected report type");
      }
      else if ((report instanceof UserReport) &&
               (reportType != REPORT_TYPE_USER))
      {
         throw new IllegalStateException("Unexpected report type");
      }
   }

   private void writeXMLDeclaration(String encoding)
   {
      writeLine("<?xml version=\"1.0\" encoding=\"", encoding, "\"?>");
   }

   private void writeStylesheet(String type, String stylesheet)
   {
      writeLine("<?xml-stylesheet type=\"", type, "\" href=\"", stylesheet, "\"?>");
   }

   private void writeDoctype(String rootElement, String dtd)
   {
      writeLine("<!DOCTYPE ", rootElement, " SYSTEM \"", dtd, "\">");
      writeLine();
   }

   private void writeCommonTags(int indentLevel, TargetReport report)
   {
      String tick = toU32String(report.getTick());
      String nTick = toU32String(report.getNTick());
      String interval = toU32String(report.getInterval());

      writeTag(indentLevel, ReportXMLTags.TAG_TICK, tick);
      writeTag(indentLevel, ReportXMLTags.TAG_MICRO_TICK, nTick);
      writeTag(indentLevel, ReportXMLTags.TAG_INTERVAL, interval);
   }

   private void writeCPUReport(CPUReport report)
   {
      String sum = toU32String(report.getSum());

      writeStartTag(2, ReportXMLTags.TAG_CPU_REPORT);
      writeCommonTags(3, report);
      writeTag(3, ReportXMLTags.TAG_SUM, sum);
      writeEndTag(2, ReportXMLTags.TAG_CPU_REPORT);
   }

   private void writeCPUPriorityReport(CPUPriorityReport report)
   {
      String sumInterrupt = toU32String(report.getSumInterrupt());
      String sumBackground = toU32String(report.getSumBackground());
      int[] sumPrioritized = report.getSumPrioritized();

      writeStartTag(2, ReportXMLTags.TAG_CPU_PRIO_REPORT);
      writeCommonTags(3, report);
      writeTag(3, ReportXMLTags.TAG_SUM_INTERRUPT, sumInterrupt);
      writeTag(3, ReportXMLTags.TAG_SUM_BACKGROUND, sumBackground);
      for (int i = 0; i < sumPrioritized.length; i++)
      {
         writeStartTag(3, ReportXMLTags.TAG_SUM_PRIORITIZED);
         writeTag(4, ReportXMLTags.TAG_ID, Integer.toString(i));
         writeTag(4, ReportXMLTags.TAG_SUM, toU32String(sumPrioritized[i]));
         writeEndTag(3, ReportXMLTags.TAG_SUM_PRIORITIZED);
      }
      writeEndTag(2, ReportXMLTags.TAG_CPU_PRIO_REPORT);
   }

   private void writeCPUProcessReport(CPUProcessReport report)
   {
      String sumOther = toU32String(report.getSumOther());
      CPUProcessLoad[] sumProcesses = report.getSumProcesses();

      writeStartTag(2, ReportXMLTags.TAG_CPU_PROC_REPORT);
      writeCommonTags(3, report);
      writeTag(3, ReportXMLTags.TAG_SUM_OTHER, sumOther);
      for (int i = 0; i < sumProcesses.length; i++)
      {
         CPUProcessLoad sumProcess = sumProcesses[i];
         writeStartTag(3, ReportXMLTags.TAG_SUM_PROCESS);
         writeTag(4, ReportXMLTags.TAG_ID, toU32HexString(sumProcess.getId()));
         writeTag(4, ReportXMLTags.TAG_SUM, toU32String(sumProcess.getSum()));
         writeEndTag(3, ReportXMLTags.TAG_SUM_PROCESS);
      }
      writeEndTag(2, ReportXMLTags.TAG_CPU_PROC_REPORT);
   }

   private void writeCPUBlockReport(CPUBlockReport report)
   {
      String sumOther = toU32String(report.getSumOther());
      CPUBlockLoad[] sumBlocks = report.getSumBlocks();

      writeStartTag(2, ReportXMLTags.TAG_CPU_BLOCK_REPORT);
      writeCommonTags(3, report);
      writeTag(3, ReportXMLTags.TAG_SUM_OTHER, sumOther);
      for (int i = 0; i < sumBlocks.length; i++)
      {
         CPUBlockLoad sumBlock = sumBlocks[i];
         writeStartTag(3, ReportXMLTags.TAG_SUM_BLOCK);
         writeTag(4, ReportXMLTags.TAG_ID, toU32HexString(sumBlock.getId()));
         writeTag(4, ReportXMLTags.TAG_SUM, toU32String(sumBlock.getSum()));
         writeEndTag(3, ReportXMLTags.TAG_SUM_BLOCK);
      }
      writeEndTag(2, ReportXMLTags.TAG_CPU_BLOCK_REPORT);
   }

   private void writeUserReport(UserReport report)
   {
      String sumOther = Integer.toString(report.getSumOther());
      UserReportValue[] values = report.getValues();

      writeStartTag(2, ReportXMLTags.TAG_USER_REPORT);
      writeCommonTags(3, report);
      writeTag(3, ReportXMLTags.TAG_SUM_OTHER, sumOther);
      if (maxMin)
      {
         String maxOther = Integer.toString(report.getMaxOther());
         String minOther = Integer.toString(report.getMinOther());
         writeTag(3, ReportXMLTags.TAG_MAX_OTHER, maxOther);
         writeTag(3, ReportXMLTags.TAG_MIN_OTHER, minOther);
      }
      for (int i = 0; i < values.length; i++)
      {
         UserReportValue value = values[i];
         writeStartTag(3, ReportXMLTags.TAG_VALUE);
         writeTag(4, ReportXMLTags.TAG_ID, toU32HexString(value.getId()));
         writeTag(4, ReportXMLTags.TAG_SUM, Integer.toString(value.getSum()));
         if (maxMin)
         {
            MaxMinUserReportValue maxMinValue = (MaxMinUserReportValue) value;
            writeTag(4, ReportXMLTags.TAG_MAX, Integer.toString(maxMinValue.getMax()));
            writeTag(4, ReportXMLTags.TAG_MIN, Integer.toString(maxMinValue.getMin()));
         }
         writeEndTag(3, ReportXMLTags.TAG_VALUE);
      }
      writeEndTag(2, ReportXMLTags.TAG_USER_REPORT);
   }

   private void writeTag(int indentLevel, String tag, String text)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, ">", text, "</", tag, ">");
   }

   private void writeStartTag(int indentLevel, String tag)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, ">");
   }

   private void writeEndTag(int indentLevel, String tag)
   {
      writeIndent(indentLevel);
      writeLine("</", tag, ">");
   }

   private void writeLine()
   {
      out.println();
   }

   private void writeLine(String text)
   {
      out.println(text);
   }

   private void writeLine(String part1, String part2, String part3)
   {
      out.print(part1);
      out.print(part2);
      out.println(part3);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.println(part5);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.println(part7);
   }

   private void writeIndent(int indentLevel)
   {
      for (int i = 0; i < indentLevel; i++)
      {
         out.print("   ");
      }
   }

   private static String toU16String(short s)
   {
      return Integer.toString(s & 0xFFFF);
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private static String toU32HexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }

   private static String toCDataString(String s)
   {
      return s.replace("&", "&amp;").replace("<", "&lt;");
   }

   private static String toByteOrderString(boolean bigEndian)
   {
      return (bigEndian ? "bigEndian" : "littleEndian");
   }
}
