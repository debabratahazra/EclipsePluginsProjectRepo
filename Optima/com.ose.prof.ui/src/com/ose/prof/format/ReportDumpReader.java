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
import java.util.Iterator;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;
import com.ose.system.UserReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;
import com.ose.system.CPUProcessReport.CPUProcessLoad;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;
import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUPriReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUReport;
import com.ose.system.service.monitor.MonitorUserReport;
import com.ose.system.service.monitor.MonitorCPUBlockReport.MonitorCPUBlock;
import com.ose.system.service.monitor.MonitorCPUProcessReport.MonitorCPUProcess;
import com.ose.system.service.monitor.MonitorUserReport.MonitorMaxMinUserReportValue;
import com.ose.system.service.monitor.MonitorUserReport.MonitorUserReportValue;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;

/**
 * This class is used for reading profiling report dump files.
 */
public class ReportDumpReader
{
   private static final String CPU_REPORT_INFO = "CPU REPORT INFO";
   private static final String CPU_PRIORITY_REPORT_INFO = "CPU PRIORITY REPORT INFO";
   private static final String CPU_PROCESS_REPORT_INFO = "CPU PROCESS REPORT INFO";
   private static final String CPU_BLOCK_REPORT_INFO = "CPU BLOCK REPORT INFO";
   private static final String USER_REPORT_INFO = "USER REPORT INFO";

   private final ReportReaderClient client;
   private boolean firstReportRead;
   private DumpFile dumpFile;
   private boolean bigEndian;
   private SignalRegistry registry;

   private int osType;
   private int numExecutionUnits;
   private int tickLength;
   private int microTickFrequency;
   private int seconds;
   private int secondsTick;
   private int secondsNTick;

   /**
    * Create a new report dump reader object.
    *
    * @param client  the report reader client.
    */
   public ReportDumpReader(ReportReaderClient client)
   {
      if (client == null)
      {
         throw new IllegalArgumentException();
      }
      this.client = client;
   }

   /**
    * Read the CPU load reports from the specified report dump file. The CPU
    * load reports read will be reported to the report reader client specified
    * when this report dump reader was created.
    *
    * @param file  the report dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void readCPUReports(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      firstReportRead = false;
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetCPUReportsEnabledRequest.class);
      registry.add(MonitorGetCPUReportsEnabledReply.class);
      registry.add(MonitorGetCPUReportsRequest.class);
      registry.add(MonitorGetCPUReportsReply.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         TextBlock infoTextBlock = null;

         parseInterfaceSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseCPUReportsEnabledSignalBlock(signalBlocks);

         infoTextBlock = getTextBlock(dumpFile.getTextBlocks(), CPU_REPORT_INFO);
         if (infoTextBlock == null)
         {
            throw new IOException("Missing CPU report info text block");
         }
         parseCPUReportInfoTextBlock(infoTextBlock);

         parseCPUReportSignalBlocks(signalBlocks);
      }
      finally
      {
         dumpFile.close();
      }
   }

   /**
    * Read the CPU priority level load reports from the specified report dump
    * file. The CPU priority level load reports read will be reported to the
    * report reader client specified when this report dump reader was created.
    *
    * @param file  the report dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void readCPUPriorityReports(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      firstReportRead = false;
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetCPUPriReportsEnabledRequest.class);
      registry.add(MonitorGetCPUPriReportsEnabledReply.class);
      registry.add(MonitorGetCPUPriReportsRequest.class);
      registry.add(MonitorGetCPUPriReportsReply.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         TextBlock infoTextBlock = null;

         parseInterfaceSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseCPUPriorityReportsEnabledSignalBlock(signalBlocks);

         infoTextBlock = getTextBlock(dumpFile.getTextBlocks(),
                                      CPU_PRIORITY_REPORT_INFO);
         if (infoTextBlock == null)
         {
            throw new IOException("Missing CPU priority report info text block");
         }
         parseCPUPriorityReportInfoTextBlock(infoTextBlock);

         parseCPUPriorityReportSignalBlocks(signalBlocks);
      }
      finally
      {
         dumpFile.close();
      }
   }

   /**
    * Read the CPU process level load reports from the specified report dump
    * file. The CPU process level load reports read will be reported to the
    * report reader client specified when this report dump reader was created.
    *
    * @param file  the report dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void readCPUProcessReports(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      firstReportRead = false;
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetCPUProcessReportsEnabledRequest.class);
      registry.add(MonitorGetCPUProcessReportsEnabledReply.class);
      registry.add(MonitorGetCPUProcessReportsRequest.class);
      registry.add(MonitorGetCPUProcessReportsReply.class);
      registry.add(MonitorGetProcessInfoRequest.class);
      registry.add(MonitorGetProcessInfoReply.class);
      registry.add(MonitorGetProcessInfoLongReply.class);
      registry.add(MonitorGetProcessInfoEndmark.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         TextBlock infoTextBlock = null;

         parseInterfaceSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseCPUProcessReportsEnabledSignalBlock(signalBlocks);

         infoTextBlock = getTextBlock(dumpFile.getTextBlocks(),
                                      CPU_PROCESS_REPORT_INFO);
         if (infoTextBlock == null)
         {
            throw new IOException("Missing CPU process report info text block");
         }
         parseCPUProcessReportInfoTextBlock(infoTextBlock);

         parseCPUProcessReportSignalBlocks(signalBlocks);
      }
      finally
      {
         dumpFile.close();
      }
   }

   /**
    * Read the CPU block level load reports from the specified report dump file.
    * The CPU block level load reports read will be reported to the report
    * reader client specified when this report dump reader was created.
    *
    * @param file  the report dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void readCPUBlockReports(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      firstReportRead = false;
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetCPUBlockReportsEnabledRequest.class);
      registry.add(MonitorGetCPUBlockReportsEnabledReply.class);
      registry.add(MonitorGetCPUBlockReportsRequest.class);
      registry.add(MonitorGetCPUBlockReportsReply.class);
      registry.add(MonitorGetBlockInfoRequest.class);
      registry.add(MonitorGetBlockInfoReply.class);
      registry.add(MonitorGetBlockInfoEndmark.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         TextBlock infoTextBlock = null;

         parseInterfaceSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseCPUBlockReportsEnabledSignalBlock(signalBlocks);

         infoTextBlock = getTextBlock(dumpFile.getTextBlocks(),
                                      CPU_BLOCK_REPORT_INFO);
         if (infoTextBlock == null)
         {
            throw new IOException("Missing CPU block report info text block");
         }
         parseCPUBlockReportInfoTextBlock(infoTextBlock);

         parseCPUBlockReportSignalBlocks(signalBlocks);
      }
      finally
      {
         dumpFile.close();
      }
   }

   /**
    * Read the user reports from the specified report dump file. The user
    * reports read will be reported to the report reader client specified when
    * this report dump reader was created.
    *
    * @param file  the report dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void readUserReports(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      firstReportRead = false;
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetUserReportsEnabledRequest.class);
      registry.add(MonitorGetUserReportsEnabledReply.class);
      registry.add(MonitorGetUserReportsRequest.class);
      registry.add(MonitorGetUserReportsReply.class);
      registry.add(MonitorGetProcessInfoRequest.class);
      registry.add(MonitorGetProcessInfoReply.class);
      registry.add(MonitorGetProcessInfoLongReply.class);
      registry.add(MonitorGetProcessInfoEndmark.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         TextBlock infoTextBlock = null;
         int reportNumber;

         parseInterfaceSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseUserReportsEnabledSignalBlock(signalBlocks);

         infoTextBlock = getTextBlock(dumpFile.getTextBlocks(),
                                      USER_REPORT_INFO);
         if (infoTextBlock == null)
         {
            throw new IOException("Missing user report info text block");
         }
         reportNumber = parseUserReportInfoTextBlock(infoTextBlock);

         parseUserReportSignalBlocks(signalBlocks, reportNumber);
      }
      finally
      {
         dumpFile.close();
      }
   }

   private void parseInterfaceSignalBlock(List signalBlocks) throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorInterfaceRequest.SIG_NO)
         {
            MonitorInterfaceReply reply = (MonitorInterfaceReply)
               readSignalBlock(signalBlock,
                               MonitorInterfaceRequest.class,
                               MonitorInterfaceReply.class);
            osType = reply.osType;
            break;
         }
      }
   }

   private void parseConnectSignalBlock(List signalBlocks) throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorConnectRequest.SIG_NO)
         {
            MonitorConnectReply reply = (MonitorConnectReply)
               readSignalBlock(signalBlock,
                               MonitorConnectRequest.class,
                               MonitorConnectReply.class);
            numExecutionUnits = 0xFFFF & reply.euCount;
            tickLength = reply.tickLength;
            microTickFrequency = reply.ntickFrequency;
            break;
         }
      }
   }

   private void parseCPUReportsEnabledSignalBlock(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUReportsEnabledRequest.SIG_NO)
         {
            MonitorGetCPUReportsEnabledReply reply =
               (MonitorGetCPUReportsEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetCPUReportsEnabledRequest.class,
                     MonitorGetCPUReportsEnabledReply.class);
            seconds = reply.sec;
            secondsTick = reply.sectick;
            secondsNTick = reply.secntick;
            break;
         }
      }
   }

   private void parseCPUPriorityReportsEnabledSignalBlock(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUPriReportsEnabledRequest.SIG_NO)
         {
            MonitorGetCPUPriReportsEnabledReply reply =
               (MonitorGetCPUPriReportsEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetCPUPriReportsEnabledRequest.class,
                     MonitorGetCPUPriReportsEnabledReply.class);
            seconds = reply.sec;
            secondsTick = reply.sectick;
            secondsNTick = reply.secntick;
            break;
         }
      }
   }

   private void parseCPUProcessReportsEnabledSignalBlock(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUProcessReportsEnabledRequest.SIG_NO)
         {
            MonitorGetCPUProcessReportsEnabledReply reply =
               (MonitorGetCPUProcessReportsEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetCPUProcessReportsEnabledRequest.class,
                     MonitorGetCPUProcessReportsEnabledReply.class);
            seconds = reply.sec;
            secondsTick = reply.sectick;
            secondsNTick = reply.secntick;
            break;
         }
      }
   }

   private void parseCPUBlockReportsEnabledSignalBlock(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUBlockReportsEnabledRequest.SIG_NO)
         {
            MonitorGetCPUBlockReportsEnabledReply reply =
               (MonitorGetCPUBlockReportsEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetCPUBlockReportsEnabledRequest.class,
                     MonitorGetCPUBlockReportsEnabledReply.class);
            seconds = reply.sec;
            secondsTick = reply.sectick;
            secondsNTick = reply.secntick;
            break;
         }
      }
   }

   private void parseUserReportsEnabledSignalBlock(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetUserReportsEnabledRequest.SIG_NO)
         {
            MonitorGetUserReportsEnabledReply reply =
               (MonitorGetUserReportsEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetUserReportsEnabledRequest.class,
                     MonitorGetUserReportsEnabledReply.class);
            seconds = reply.sec;
            secondsTick = reply.sectick;
            secondsNTick = reply.secntick;
            break;
         }
      }
   }

   private void parseCPUReportInfoTextBlock(TextBlock textBlock)
      throws IOException
   {
      String[] descriptions;
      String target = null;
      String executionUnit = null;
      String integrationInterval = null;
      String maxReports = null;
      String priorityLimit = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String line = descriptions[i];
         String value;

         if ((value = parseAttribute(line, "target")) != null)
         {
            target = value;
         }
         else if ((value = parseAttribute(line, "executionUnit")) != null)
         {
            executionUnit = value;
         }
         else if ((value = parseAttribute(line, "integrationInterval")) != null)
         {
            integrationInterval = value;
         }
         else if ((value = parseAttribute(line, "maxReports")) != null)
         {
            maxReports = value;
         }
         else if ((value = parseAttribute(line, "priorityLimit")) != null)
         {
            priorityLimit = value;
         }
      }

      if ((target != null) && (integrationInterval != null) &&
          (maxReports != null) && (priorityLimit != null))
      {
         short eu = ((executionUnit != null) ? parseU16(executionUnit) : 0);
         client.cpuReportSettingsRead(target,
                                      bigEndian,
                                      osType,
                                      numExecutionUnits,
                                      tickLength,
                                      microTickFrequency,
                                      eu,
                                      parseU32(integrationInterval),
                                      parseU32(maxReports),
                                      parseU32(priorityLimit),
                                      seconds,
                                      secondsTick,
                                      secondsNTick);
      }
      else
      {
         throw new IOException("Incomplete CPU report info text block");
      }
   }

   private void parseCPUPriorityReportInfoTextBlock(TextBlock textBlock)
      throws IOException
   {
      String[] descriptions;
      String target = null;
      String executionUnit = null;
      String integrationInterval = null;
      String maxReports = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String line = descriptions[i];
         String value;

         if ((value = parseAttribute(line, "target")) != null)
         {
            target = value;
         }
         else if ((value = parseAttribute(line, "executionUnit")) != null)
         {
            executionUnit = value;
         }
         else if ((value = parseAttribute(line, "integrationInterval")) != null)
         {
            integrationInterval = value;
         }
         else if ((value = parseAttribute(line, "maxReports")) != null)
         {
            maxReports = value;
         }
      }

      if ((target != null) && (integrationInterval != null) && (maxReports != null))
      {
         short eu = ((executionUnit != null) ? parseU16(executionUnit) : 0);
         client.cpuPriorityReportSettingsRead(target,
                                              bigEndian,
                                              osType,
                                              numExecutionUnits,
                                              tickLength,
                                              microTickFrequency,
                                              eu,
                                              parseU32(integrationInterval),
                                              parseU32(maxReports),
                                              seconds,
                                              secondsTick,
                                              secondsNTick);
      }
      else
      {
         throw new IOException("Incomplete CPU priority info text block");
      }
   }

   private void parseCPUProcessReportInfoTextBlock(TextBlock textBlock)
      throws IOException
   {
      String[] descriptions;
      String target = null;
      String executionUnit = null;
      String integrationInterval = null;
      String maxReports = null;
      String maxProcessesPerReport = null;
      String profiledProcesses = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String line = descriptions[i];
         String value;

         if ((value = parseAttribute(line, "target")) != null)
         {
            target = value;
         }
         else if ((value = parseAttribute(line, "executionUnit")) != null)
         {
            executionUnit = value;
         }
         else if ((value = parseAttribute(line, "integrationInterval")) != null)
         {
            integrationInterval = value;
         }
         else if ((value = parseAttribute(line, "maxReports")) != null)
         {
            maxReports = value;
         }
         else if ((value = parseAttribute(line, "maxProcessesPerReport")) != null)
         {
            maxProcessesPerReport = value;
         }
         else if ((value = parseAttribute(line, "profiledProcesses")) != null)
         {
            profiledProcesses = value;
         }
      }

      if ((target != null) && (integrationInterval != null) &&
          (maxReports != null) && (maxProcessesPerReport != null) &&
          (profiledProcesses != null))
      {
         short eu = ((executionUnit != null) ? parseU16(executionUnit) : 0);
         client.cpuProcessReportSettingsRead(target,
                                             bigEndian,
                                             osType,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             eu,
                                             parseU32(integrationInterval),
                                             parseU32(maxReports),
                                             parseU32(maxProcessesPerReport),
                                             profiledProcesses,
                                             seconds,
                                             secondsTick,
                                             secondsNTick);
      }
      else
      {
         throw new IOException("Incomplete CPU process info text block");
      }
   }

   private void parseCPUBlockReportInfoTextBlock(TextBlock textBlock)
      throws IOException
   {
      String[] descriptions;
      String target = null;
      String executionUnit = null;
      String integrationInterval = null;
      String maxReports = null;
      String maxBlocksPerReport = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String line = descriptions[i];
         String value;

         if ((value = parseAttribute(line, "target")) != null)
         {
            target = value;
         }
         else if ((value = parseAttribute(line, "executionUnit")) != null)
         {
            executionUnit = value;
         }
         else if ((value = parseAttribute(line, "integrationInterval")) != null)
         {
            integrationInterval = value;
         }
         else if ((value = parseAttribute(line, "maxReports")) != null)
         {
            maxReports = value;
         }
         else if ((value = parseAttribute(line, "maxBlocksPerReport")) != null)
         {
            maxBlocksPerReport = value;
         }
      }

      if ((target != null) && (integrationInterval != null) &&
          (maxReports != null) && (maxBlocksPerReport != null))
      {
         short eu = ((executionUnit != null) ? parseU16(executionUnit) : 0);
         client.cpuBlockReportSettingsRead(target,
                                           bigEndian,
                                           osType,
                                           numExecutionUnits,
                                           tickLength,
                                           microTickFrequency,
                                           eu,
                                           parseU32(integrationInterval),
                                           parseU32(maxReports),
                                           parseU32(maxBlocksPerReport),
                                           seconds,
                                           secondsTick,
                                           secondsNTick);
      }
      else
      {
         throw new IOException("Incomplete CPU block info text block");
      }
   }

   private int parseUserReportInfoTextBlock(TextBlock textBlock)
      throws IOException
   {
      String[] descriptions;
      String target = null;
      String integrationInterval = null;
      String maxReports = null;
      String maxValuesPerReport = null;
      String reportNumber = null;
      String continuous = null;
      String maxMin = null;
      String multiple = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String line = descriptions[i];
         String value;

         if ((value = parseAttribute(line, "target")) != null)
         {
            target = value;
         }
         else if ((value = parseAttribute(line, "integrationInterval")) != null)
         {
            integrationInterval = value;
         }
         else if ((value = parseAttribute(line, "maxReports")) != null)
         {
            maxReports = value;
         }
         else if ((value = parseAttribute(line, "maxValuesPerReport")) != null)
         {
            maxValuesPerReport = value;
         }
         else if ((value = parseAttribute(line, "reportNumber")) != null)
         {
            reportNumber = value;
         }
         else if ((value = parseAttribute(line, "continuous")) != null)
         {
            continuous = value;
         }
         else if ((value = parseAttribute(line, "maxMin")) != null)
         {
            maxMin = value;
         }
         else if ((value = parseAttribute(line, "multiple")) != null)
         {
            multiple = value;
         }
      }

      if ((target != null) && (integrationInterval != null) &&
          (maxReports != null) && (maxValuesPerReport != null) &&
          (reportNumber != null) && (continuous != null) &&
          (maxMin != null) && (multiple != null))
      {
         int userReportNumber = parseU32(reportNumber);
         client.userReportSettingsRead(target,
                                       bigEndian,
                                       osType,
                                       numExecutionUnits,
                                       tickLength,
                                       microTickFrequency,
                                       parseU32(integrationInterval),
                                       parseU32(maxReports),
                                       parseU32(maxValuesPerReport),
                                       userReportNumber,
                                       parseBoolean(continuous),
                                       parseBoolean(maxMin),
                                       parseBoolean(multiple),
                                       seconds,
                                       secondsTick,
                                       secondsNTick);
         return userReportNumber;
      }
      else
      {
         throw new IOException("Incomplete user report info text block");
      }
   }

   private void parseCPUReportSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         Signal signal;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUReportsRequest.SIG_NO)
         {
            signal = readSignalBlock(signalBlock,
                                     MonitorGetCPUReportsRequest.class,
                                     MonitorGetCPUReportsReply.class);
            handleCPUReports((MonitorGetCPUReportsReply) signal);
         }
      }
   }

   private void parseCPUPriorityReportSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         Signal signal;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCPUPriReportsRequest.SIG_NO)
         {
            signal = readSignalBlock(signalBlock,
                                     MonitorGetCPUPriReportsRequest.class,
                                     MonitorGetCPUPriReportsReply.class);
            handleCPUPriorityReports((MonitorGetCPUPriReportsReply) signal);
         }
      }
   }

   private void parseCPUProcessReportSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         Signal signal;
         List signals;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         switch (reqSigNo)
         {
            case MonitorGetCPUProcessReportsRequest.SIG_NO:
               signal = readSignalBlock(signalBlock,
                                        MonitorGetCPUProcessReportsRequest.class,
                                        MonitorGetCPUProcessReportsReply.class);
               handleCPUProcessReports((MonitorGetCPUProcessReportsReply) signal);
               break;
            case MonitorGetProcessInfoRequest.SIG_NO:
               signals = readSignalBlock(signalBlock,
                                         MonitorGetProcessInfoRequest.class,
                                         MonitorGetProcessInfoReply.class,
                                         MonitorGetProcessInfoLongReply.class,
                                         MonitorGetProcessInfoEndmark.class);
               handleProcess(signals);
               break;
            default:
               break;
         }
      }
   }

   private void parseCPUBlockReportSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         Signal signal;
         List signals;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         switch (reqSigNo)
         {
            case MonitorGetCPUBlockReportsRequest.SIG_NO:
               signal = readSignalBlock(signalBlock,
                                        MonitorGetCPUBlockReportsRequest.class,
                                        MonitorGetCPUBlockReportsReply.class);
               handleCPUBlockReports((MonitorGetCPUBlockReportsReply) signal);
               break;
            case MonitorGetBlockInfoRequest.SIG_NO:
               signals = readSignalBlock(signalBlock,
                                         MonitorGetBlockInfoRequest.class,
                                         MonitorGetBlockInfoReply.class,
                                         MonitorGetBlockInfoEndmark.class);
               handleBlock(signals);
               break;
            default:
               break;
         }
      }
   }

   private void parseUserReportSignalBlocks(List signalBlocks, int reportNumber)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         MonitorGetUserReportsReply signal;
         List signals;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         switch (reqSigNo)
         {
            case MonitorGetUserReportsRequest.SIG_NO:
               signal = (MonitorGetUserReportsReply)
                  readSignalBlock(signalBlock,
                                  MonitorGetUserReportsRequest.class,
                                  MonitorGetUserReportsReply.class);
               if (signal.reportNo == reportNumber)
               {
                  handleUserReports(signal);
               }
               break;
            case MonitorGetProcessInfoRequest.SIG_NO:
               signals = readSignalBlock(signalBlock,
                                         MonitorGetProcessInfoRequest.class,
                                         MonitorGetProcessInfoReply.class,
                                         MonitorGetProcessInfoLongReply.class,
                                         MonitorGetProcessInfoEndmark.class);
               handleProcess(signals);
               break;
            default:
               break;
         }
      }
   }

   private void handleCPUReports(MonitorGetCPUReportsReply signal)
   {
      for (int i = 0; i < signal.reports.length; i++)
      {
         MonitorCPUReport monitorReport;
         CPUReport report;

         monitorReport = signal.reports[i];
         if (!firstReportRead)
         {
            adjustTimeReference(monitorReport.tick, monitorReport.ntick);
            firstReportRead = true;
         }
         report = CPUReport.getInstance(monitorReport.tick,
                                        monitorReport.ntick,
                                        monitorReport.interval,
                                        monitorReport.sum);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         client.cpuReportRead(report);
      }
   }

   private void handleCPUPriorityReports(MonitorGetCPUPriReportsReply signal)
   {
      for (int i = 0; i < signal.reports.length; i++)
      {
         MonitorCPUPriReport monitorReport;
         CPUPriorityReport report;

         monitorReport = signal.reports[i];
         if (!firstReportRead)
         {
            adjustTimeReference(monitorReport.tick, monitorReport.ntick);
            firstReportRead = true;
         }
         report = CPUPriorityReport.getInstance(monitorReport.tick,
                                                monitorReport.ntick,
                                                monitorReport.interval,
                                                monitorReport.sumInterrupt,
                                                monitorReport.sumBackground,
                                                monitorReport.sumPrioritized);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         client.cpuPriorityReportRead(report);
      }
   }

   private void handleCPUProcessReports(MonitorGetCPUProcessReportsReply signal)
   {
      for (int i = 0; i < signal.reports.length; i++)
      {
         MonitorCPUProcessReport monitorReport;
         CPUProcessReport report;
         MonitorCPUProcess[] monitorProcesses;
         CPUProcessLoad[] processes;

         monitorReport = signal.reports[i];
         if (!firstReportRead)
         {
            adjustTimeReference(monitorReport.tick, monitorReport.ntick);
            firstReportRead = true;
         }
         monitorProcesses = monitorReport.processes;
         processes = new CPUProcessLoad[monitorProcesses.length];
         for (int j = 0; j < monitorProcesses.length; j++)
         {
            MonitorCPUProcess monitorProcess;

            monitorProcess = monitorProcesses[j];
            processes[j] = CPUProcessLoad.getInstance(monitorProcess.id,
                                                      monitorProcess.sum);
         }

         report = CPUProcessReport.getInstance(monitorReport.tick,
                                               monitorReport.ntick,
                                               monitorReport.interval,
                                               monitorReport.sumOther,
                                               processes);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         client.cpuProcessReportRead(report);
      }
   }

   private void handleCPUBlockReports(MonitorGetCPUBlockReportsReply signal)
   {
      for (int i = 0; i < signal.reports.length; i++)
      {
         MonitorCPUBlockReport monitorReport;
         CPUBlockReport report;
         MonitorCPUBlock[] monitorBlocks;
         CPUBlockLoad[] blocks;

         monitorReport = signal.reports[i];
         if (!firstReportRead)
         {
            adjustTimeReference(monitorReport.tick, monitorReport.ntick);
            firstReportRead = true;
         }
         monitorBlocks = monitorReport.blocks;
         blocks = new CPUBlockLoad[monitorBlocks.length];
         for (int j = 0; j < monitorBlocks.length; j++)
         {
            MonitorCPUBlock monitorBlock;

            monitorBlock = monitorBlocks[j];
            blocks[j] = CPUBlockLoad.getInstance(monitorBlock.id,
                                                 monitorBlock.sum);
         }

         report = CPUBlockReport.getInstance(monitorReport.tick,
                                             monitorReport.ntick,
                                             monitorReport.interval,
                                             monitorReport.sumOther,
                                             blocks);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         client.cpuBlockReportRead(report);
      }
   }

   private void handleUserReports(MonitorGetUserReportsReply signal)
   {
      for (int i = 0; i < signal.reports.length; i++)
      {
         MonitorUserReport monitorReport;
         UserReport report;
         MonitorUserReportValue[] monitorValues;
         UserReportValue[] values;

         monitorReport = signal.reports[i];
         if (!firstReportRead)
         {
            adjustTimeReference(monitorReport.tick, monitorReport.ntick);
            firstReportRead = true;
         }
         monitorValues = monitorReport.reportValues;
         values = new UserReportValue[monitorValues.length];
         if (signal.maxmin)
         {
            for (int j = 0; j < monitorValues.length; j++)
            {
               MonitorMaxMinUserReportValue monitorMaxMinValue;

               monitorMaxMinValue =
                     (MonitorMaxMinUserReportValue) monitorValues[j];
               values[j] = MaxMinUserReportValue.getInstance(
                     monitorMaxMinValue.id,
                     monitorMaxMinValue.sum,
                     monitorMaxMinValue.max,
                     monitorMaxMinValue.min);
            }
         }
         else
         {
            for (int j = 0; j < monitorValues.length; j++)
            {
               MonitorUserReportValue monitorValue;

               monitorValue = monitorValues[j];
               values[j] = UserReportValue.getInstance(monitorValue.id,
                                                       monitorValue.sum);
            }
         }

         report = UserReport.getInstance(monitorReport.tick,
                                         monitorReport.ntick,
                                         monitorReport.interval,
                                         monitorReport.sumOther,
                                         monitorReport.maxOther,
                                         monitorReport.minOther,
                                         values);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         client.userReportRead(report);
      }
   }

   private void handleProcess(List signals)
   {
      for (Iterator i = signals.iterator(); i.hasNext();)
      {
         Object obj = i.next();

         if (obj instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) obj;
            client.processRead(process.pid, process.name);
         }
         else if (obj instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) obj;
            client.processRead(process.pid, process.processName);
         }
      }
   }

   private void handleBlock(List signals)
   {
      for (Iterator i = signals.iterator(); i.hasNext();)
      {
         Object obj = i.next();

         if (obj instanceof MonitorGetBlockInfoReply)
         {
            MonitorGetBlockInfoReply block =
               (MonitorGetBlockInfoReply) obj;
            client.blockRead(block.bid, block.name);
         }
      }
   }

   private void adjustTimeReference(int tick, int ntick)
   {
      TimeReference timeRef = new TimeReference(tickLength, microTickFrequency);
      timeRef.set(seconds, secondsTick, secondsNTick);
      if (timeRef.adjust(tick, ntick))
      {
         seconds = timeRef.getSeconds();
         secondsTick = timeRef.getTick();
         secondsNTick = timeRef.getNTick();
      }
   }

   private Signal readSignalBlock(SignalBlock signalBlock,
                                  Class requestClass,
                                  Class replyClass)
      throws IOException
   {
      SignalInputStream in = null;
      Signal request;
      Signal reply;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         reply = readSignal(in);
         if (!replyClass.isInstance(reply))
         {
            throw new IffException("Invalid reply signal in " +
               requestClass.getName() + " signal block in dump file");
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
      }

      return reply;
   }

   private List readSignalBlock(SignalBlock signalBlock,
                                Class requestClass,
                                Class replyClass,
                                Class endmarkClass)
      throws IOException
   {
      List signals;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      boolean endmark = false;

      try
      {
         signals = new ArrayList();
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         do
         {
            reply = readSignal(in);
            if (replyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = true;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing " + endmarkClass.getName() +
               " in " + requestClass.getName() + " signal block in dump file");
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
      }

      return signals;
   }

   private List readSignalBlock(SignalBlock signalBlock,
                                Class requestClass,
                                Class replyClass,
                                Class longReplyClass,
                                Class endmarkClass)
      throws IOException
   {
      List signals;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      boolean endmark = false;

      try
      {
         signals = new ArrayList();
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         do
         {
            reply = readSignal(in);
            if (replyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (longReplyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = true;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing " + endmarkClass.getName() +
               " in " + requestClass.getName() + " signal block in dump file");
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
      }

      return signals;
   }

   private Signal readSignal(SignalInputStream in) throws IOException
   {
      int sigSize;
      int sigNo;
      byte[] sigData;

      sigSize = in.readS32();
      if (sigSize < 4)
      {
         throw new IffException("Invalid signal size in dump file");
      }
      sigNo = in.readS32();
      sigData = new byte[sigSize - 4];
      in.readFully(sigData);
      in.align(4);
      return createSignal(0, 0, sigSize, sigNo, sigData, registry, bigEndian);
   }

   private static Signal createSignal(int sender,
                                      int addressee,
                                      int sigSize,
                                      int sigNo,
                                      byte[] sigData,
                                      SignalRegistry registry,
                                      boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if ((sigSize == 0) || (sigData == null) || (registry == null))
      {
         throw new IllegalArgumentException();
      }

      // Find the signal class in the signal registry.
      sigClass = registry.getSignalClass(sigNo);

      if (sigClass != null)
      {
         // Create an object of the found signal class.
         try
         {
            sig = (Signal) sigClass.newInstance();
         }
         catch (InstantiationException e)
         {
            // The signal class did not have a default constructor.
            throw new RuntimeException("Default constructor missing in " +
                                       sigClass.getName());
         }
         catch (IllegalAccessException e)
         {
            // The signal class did not have a public default constructor.
            throw new RuntimeException("Default constructor not public in " +
                                       sigClass.getName());
         }
         catch (ClassCastException e)
         {
            // The signal class did not extend Signal.
            throw new RuntimeException(sigClass.getName() +
                                       " do not extend com.ose.gateway.Signal");
         }
      }
      else
      {
         // Did not find a registered signal class.
         sig = new UnregisteredSignal(sigNo);
      }

      // Extract common data from the native signal to the Java signal.
      sig.init(sender, addressee, sigSize);

      // Extract user data from the native signal to the Java signal.
      sig.oseToJava(sigData, bigEndian);

      return sig;
   }

   private static TextBlock getTextBlock(List textBlocks, String name)
   {
      TextBlock foundTextBlock = null;

      if ((textBlocks == null) || (name == null))
      {
         throw new IllegalArgumentException();
      }

      for (int i = textBlocks.size() - 1; i >= 0; i--)
      {
         TextBlock textBlock = (TextBlock) textBlocks.get(i);
         String[] descriptions = textBlock.getDescriptions();

         if ((descriptions.length > 0) &&
             descriptions[0].startsWith(name))
         {
            foundTextBlock = textBlock;
            break;
         }
      }

      return foundTextBlock;
   }

   private static String parseAttribute(String line, String name)
   {
      if ((line == null) || (name == null))
      {
         throw new IllegalArgumentException();
      }

      if (line.trim().startsWith(name))
      {
         int separator = line.indexOf('=');
         if ((separator > 0) && (separator < line.length() - 1))
         {
            String value = line.substring(separator + 1).trim();
            return ((value.length() > 0) ? value : null);
         }
         else
         {
            return null;
         }
      }
      else
      {
         return null;
      }
   }

   private static short parseU16(String s) throws NumberFormatException
   {
      int value;

      if (s == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         value = Integer.decode(s.trim()).intValue();
      }
      catch (NumberFormatException e)
      {
         throw new NumberFormatException(
            "Invalid 16-bit integer attribute value in info text block: " + s);
      }
      if ((value < 0) || (value > 0xFFFF))
      {
         throw new NumberFormatException(
            "Invalid 16-bit integer attribute value in info text block: " + s);
      }
      return (short) (0xFFFF & value);
   }

   private static int parseU32(String s) throws NumberFormatException
   {
      long value;

      if (s == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         value = Long.decode(s.trim()).longValue();
      }
      catch (NumberFormatException e)
      {
         throw new NumberFormatException(
            "Invalid integer attribute value in info text block: " + s);
      }
      if ((value < 0L) || (value > 0xFFFFFFFFL))
      {
         throw new NumberFormatException(
            "Invalid integer attribute value in info text block: " + s);
      }
      return (int) (0xFFFFFFFFL & value);
   }

   private static boolean parseBoolean(String s)
   {
      if (s == null)
      {
         throw new IllegalArgumentException();
      }
      return s.equalsIgnoreCase("true");
   }
}
