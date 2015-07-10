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

package com.ose.system;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.ose.system.CPUBlockReport.CPUBlockLoad;
import com.ose.system.CPUProcessReport.CPUProcessLoad;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;
import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUPriReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUReport;
import com.ose.system.service.monitor.MonitorCounterReport;
import com.ose.system.service.monitor.MonitorCounterReportExt;
import com.ose.system.service.monitor.MonitorUserReport;
import com.ose.system.service.monitor.MonitorCPUBlockReport.MonitorCPUBlock;
import com.ose.system.service.monitor.MonitorCPUProcessReport.MonitorCPUProcess;
import com.ose.system.service.monitor.MonitorUserReport.MonitorMaxMinUserReportValue;
import com.ose.system.service.monitor.MonitorUserReport.MonitorUserReportValue;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorCounterReportsLossNotify;
import com.ose.system.service.monitor.signal.MonitorCounterReportsNotify;
import com.ose.system.service.monitor.signal.MonitorGetAllocTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetBindTraceReply;
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
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetLossTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetReceiveTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetResetTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSendTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesReply;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesRequest;
import com.ose.system.service.monitor.signal.MonitorGetSwapTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTimeoutTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceEndmark;
import com.ose.system.service.monitor.signal.MonitorGetTraceRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserTraceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesReply;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;

/**
 * This class extends the DumpFileWriter class with methods for writing various
 * OSE system model objects to OSE dump files.
 */
public class SystemModelDumpFileWriter extends DumpFileWriter
{
   /** The size of the event process cache. */
   public static final int PROCESS_CACHE_SIZE = 32;

   private final ProcessInfoCache processCache;

   private boolean eventTrace;
   private int eventRefCount;

   /**
    * Create a new system model dump file writer object.
    *
    * @param file  the dump file to be created.
    * @param bigEndian  true if the dump file should be written in big endian
    * byte order, false if it should be written in little endian byte order.
    * @param dumpId  the ID of the dump.
    * @throws IOException  if an I/O exception occurred.
    */
   public SystemModelDumpFileWriter(File file, boolean bigEndian, int dumpId)
      throws IOException
   {
      super(file, bigEndian, dumpId);
      processCache = new ProcessInfoCache(PROCESS_CACHE_SIZE);
   }

   /**
    * Write target system information.
    *
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param cpuType  the CPU type of the target, i.e. one of the Target.CPU_*
    * constants.
    * @param systemName  the system name of the target.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param ntickFrequency  the ntick timer frequency of the target in Hz.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeSystemInfo(int osType,
                                            int cpuType,
                                            String systemName,
                                            int numExecutionUnits,
                                            int tickLength,
                                            int ntickFrequency,
                                            boolean compressed)
      throws IOException
   {
      MonitorInterfaceRequest interfaceRequest;
      MonitorInterfaceReply interfaceReply;
      MonitorNameRequest nameRequest;
      MonitorNameReply nameReply;
      MonitorConnectRequest connectRequest;
      MonitorConnectReply connectReply;

      startWritingSignalBlock(MonitorInterfaceRequest.SIG_NO, compressed);
      interfaceRequest = new MonitorInterfaceRequest();
      writeSignal(interfaceRequest);
      interfaceReply = new MonitorInterfaceReply();
      interfaceReply.endian = bigEndian;
      interfaceReply.osType = osType;
      interfaceReply.cpuClass = cpuType;
      interfaceReply.sigs = new int[0];
      writeSignal(interfaceReply);
      endWritingSignalBlock(0);

      startWritingSignalBlock(MonitorNameRequest.SIG_NO, compressed);
      nameRequest = new MonitorNameRequest();
      writeSignal(nameRequest);
      nameReply = new MonitorNameReply();
      nameReply.systemName = ((systemName != null) ? systemName : "");
      nameReply.monitorName = "com.ose.PostMortemMonitor";
      writeSignal(nameReply);
      endWritingSignalBlock(0);

      startWritingSignalBlock(MonitorConnectRequest.SIG_NO, compressed);
      connectRequest = new MonitorConnectRequest();
      connectRequest.name = "";
      writeSignal(connectRequest);
      connectReply = new MonitorConnectReply();
      connectReply.status = 0;
      connectReply.tickLength = tickLength;
      connectReply.tick = 0;
      connectReply.ntick = 0;
      connectReply.ntickFrequency = ntickFrequency;
      connectReply.cpuClass = cpuType;
      connectReply.cpuType = 0;
      connectReply.errorHandler = 0;
      connectReply.euCount = (short) numExecutionUnits;
      connectReply.featuresCount = 0;
      connectReply.features = new short[0];
      writeSignal(connectReply);
      endWritingSignalBlock(0);
   }

   /**
    * Start writing processes. Should be followed by one or more writeProcess()
    * calls and ended with one endWritingProcesses() call.
    *
    * @param scopeType  the type of the scope containing the processes, i.e. one
    * of the Target.SCOPE_* constants.
    * @param scopeId  the corresponding scope ID.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingProcesses(int scopeType,
                                                  int scopeId,
                                                  boolean compressed)
      throws IOException
   {
      MonitorGetProcessInfoRequest signal;

      startWritingSignalBlock(MonitorGetProcessInfoRequest.SIG_NO, compressed);
      signal = new MonitorGetProcessInfoRequest(0, scopeType, scopeId, null);
      writeSignal(signal);
   }

   /**
    * Write a process.
    *
    * @param process  the process object to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeProcess(ProcessInfo process) throws IOException
   {
      MonitorGetProcessInfoReply signal;

      if (process == null)
      {
         throw new IllegalArgumentException();
      }

      signal = new MonitorGetProcessInfoReply();
      signal.pid = process.getId();
      signal.bid = process.getBid();
      signal.sid = process.getSid();
      signal.type = process.getType();
      signal.state = process.getState();
      signal.priority = process.getPriority();
      signal.entrypoint = process.getEntrypoint();
      signal.properties = process.getProperties();
      signal.name = process.getName();
      writeSignal(signal);
   }

   /**
    * Write a process. Only the ID and name of a process is written.
    *
    * @param id  the ID of the process to write.
    * @param name  the name of the process to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeProcess(int id, String name) throws IOException
   {
      MonitorGetProcessInfoReply signal;

      if (name == null)
      {
         throw new IllegalArgumentException();
      }

      signal = new MonitorGetProcessInfoReply();
      signal.pid = id;
      signal.bid = 1;
      signal.sid = 0;
      signal.type = ProcessInfo.TYPE_UNKNOWN;
      signal.state = ProcessInfo.STATE_READY;
      signal.priority = 0;
      signal.entrypoint = 0;
      signal.properties = 0;
      signal.name = name;
      writeSignal(signal);
   }

   /**
    * End writing processes.
    *
    * @param status  the overall underlying signal block status, 0 means success.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingProcesses(int status) throws IOException
   {
      MonitorGetProcessInfoEndmark signal;

      signal = new MonitorGetProcessInfoEndmark();
      signal.status = status;
      writeSignal(signal);
      endWritingSignalBlock(status);
   }

   /**
    * Start writing blocks. Should be followed by one or more writeBlock()
    * calls and ended with one endWritingBlocks() call.
    *
    * @param scopeType  the type of the scope containing the blocks, i.e. one of
    * the Target.SCOPE_* constants.
    * @param scopeId  the corresponding scope ID.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingBlocks(int scopeType,
                                               int scopeId,
                                               boolean compressed)
      throws IOException
   {
      MonitorGetBlockInfoRequest signal;

      startWritingSignalBlock(MonitorGetBlockInfoRequest.SIG_NO, compressed);
      signal = new MonitorGetBlockInfoRequest(scopeType, scopeId, null);
      writeSignal(signal);
   }

   /**
    * Write a block.
    *
    * @param block  the block object to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeBlock(BlockInfo block) throws IOException
   {
      MonitorGetBlockInfoReply signal;

      if (block == null)
      {
         throw new IllegalArgumentException();
      }

      signal = new MonitorGetBlockInfoReply();
      signal.bid = block.getId();
      signal.sid = block.getSid();
      signal.uid = block.getUid();
      signal.supervisor = block.isSupervisor();
      signal.signalsAttached = block.getNumSignalsAttached();
      signal.errorHandler = block.getErrorHandler();
      signal.maxSigSize = block.getMaxSignalSize();
      signal.sigPoolId = block.getSignalPoolId();
      signal.stackPoolId = block.getStackPoolId();
      signal.euId = block.getExecutionUnit();
      signal.name = block.getName();
      writeSignal(signal);
   }

   /**
    * Write a block. Only the ID and name of a block is written.
    *
    * @param id  the ID of the block to write.
    * @param name  the name of the block to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeBlock(int id, String name) throws IOException
   {
      MonitorGetBlockInfoReply signal;

      if (name == null)
      {
         throw new IllegalArgumentException();
      }

      signal = new MonitorGetBlockInfoReply();
      signal.bid = id;
      signal.sid = 0;
      signal.uid = 0;
      signal.supervisor = false;
      signal.signalsAttached = 0;
      signal.errorHandler = 0;
      signal.maxSigSize = 0;
      signal.sigPoolId = 0;
      signal.stackPoolId = 0;
      signal.euId = 0;
      signal.name = name;
      writeSignal(signal);
   }

   /**
    * End writing blocks.
    *
    * @param status  the overall underlying signal block status, 0 means success.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingBlocks(int status) throws IOException
   {
      MonitorGetBlockInfoEndmark signal;

      signal = new MonitorGetBlockInfoEndmark();
      signal.status = status;
      writeSignal(signal);
      endWritingSignalBlock(status);
   }

   /**
    * Write load modules.
    *
    * @param loadModules  the load modules to write.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeLoadModules(LoadModuleInfo[] loadModules,
                                             boolean compressed)
      throws IOException
   {
      String[] installHandles;

      if (loadModules == null)
      {
         throw new IllegalArgumentException();
      }

      installHandles = new String[loadModules.length];
      for (int i = 0; i < loadModules.length; i++)
      {
         installHandles[i] = loadModules[i].getInstallHandle();
      }

      if (installHandles.length > 0)
      {
         PmLoadModuleInstallHandlesRequest request;
         PmLoadModuleInstallHandlesReply reply;

         startWritingSignalBlock(PmLoadModuleInstallHandlesRequest.SIG_NO, compressed);
   
         request = new PmLoadModuleInstallHandlesRequest();
         writeSignal(request);

         reply = new PmLoadModuleInstallHandlesReply();
         reply.status = 0;
         reply.install_handles = installHandles;
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }

      for (int i = 0; i < loadModules.length; i++)
      {
         PmLoadModuleInfoRequest request;
         PmLoadModuleInfoReply reply;
         LoadModuleInfo loadModule = loadModules[i];

         startWritingSignalBlock(PmLoadModuleInfoRequest.SIG_NO, compressed);
   
         request = new PmLoadModuleInfoRequest();
         request.install_handle = loadModule.getInstallHandle();
         writeSignal(request);

         reply = new PmLoadModuleInfoReply();
         reply.status = 0;
         reply.install_handle = loadModule.getInstallHandle();
         reply.entrypoint = loadModule.getEntrypointLong();
         reply.options = loadModule.getOptions();
         reply.text_base = loadModule.getTextBaseLong();
         reply.text_size = loadModule.getTextSizeLong();
         reply.data_base = loadModule.getDataBaseLong();
         reply.data_size = loadModule.getDataSizeLong();
         reply.no_of_sections = loadModule.getNumSections();
         reply.no_of_instances = loadModule.getNumInstances();
         reply.file_format = loadModule.getFileFormat();
         reply.file_name = loadModule.getFileName();
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Write CPU load report profiling information.
    *
    * @param target  the name of the target being used.
    * @param executionUnit  the execution unit from where the CPU load reports
    * were retrieved from.
    * @param integrationInterval  the CPU load reporting integration interval in
    * ticks.
    * @param maxReports  the maximum number of CPU load reports kept in the
    * target.
    * @param priorityLimit  the CPU load reporting priority limit.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUReportInfo(String target,
                                               short executionUnit,
                                               int integrationInterval,
                                               int maxReports,
                                               int priorityLimit)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }

      writeTextBlock("CPU REPORT INFO\n" +
            "\ntarget = " + target +
            "\nexecutionUnit = " + toU16String(executionUnit) +
            "\nintegrationInterval = " + toU32String(integrationInterval) +
            "\nmaxReports = " + toU32String(maxReports) +
            "\npriorityLimit = " + toU32String(priorityLimit) +
            "\n");
   }

   /**
    * Write CPU load reports enablement information.
    *
    * @param enabled  true if CPU load reporting was enabled, false otherwise.
    * @param interval  the CPU load reporting integration interval in ticks.
    * @param priority  the CPU load reporting priority limit.
    * @param maxReports  the maximum number of CPU load reports kept in the
    * target.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU load reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUReportsEnabled(boolean enabled,
                                                   int interval,
                                                   int priority,
                                                   int maxReports,
                                                   int seconds,
                                                   int secondsTick,
                                                   int secondsNTick,
                                                   boolean compressed)
      throws IOException
   {
      MonitorGetCPUReportsEnabledRequest request;
      MonitorGetCPUReportsEnabledReply reply;

      startWritingSignalBlock(MonitorGetCPUReportsEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetCPUReportsEnabledRequest();
      writeSignal(request);

      reply = new MonitorGetCPUReportsEnabledReply();
      reply.enabled = enabled;
      reply.interval = interval;
      reply.priority = priority;
      reply.maxReports = maxReports;
      reply.sec = seconds;
      reply.sectick = secondsTick;
      reply.secntick = secondsNTick;
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write CPU load reports.
    *
    * @param reports  the CPU load reports to write.
    * @param executionUnit  the execution unit from where the CPU load reports
    * were retrieved from.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUReports(CPUReport[] reports,
                                            short executionUnit,
                                            boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorGetCPUReportsRequest request;
         MonitorGetCPUReportsReply reply;

         startWritingSignalBlock(MonitorGetCPUReportsRequest.SIG_NO, compressed);

         request = new MonitorGetCPUReportsRequest();
         request.euId = executionUnit;
         request.tick = reports[0].getTick();
         request.ntick = reports[0].getNTick();
         writeSignal(request);
   
         reply = new MonitorGetCPUReportsReply();
         reply.status = 0;
         reply.enabled = true;
         reply.euId = executionUnit;
         reply.reports = createMonitorCPUReports(reports);
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Write CPU priority level load report profiling information.
    *
    * @param target  the name of the target being used.
    * @param executionUnit  the execution unit from where the CPU priority level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU priority level load reporting
    * integration interval in ticks.
    * @param maxReports  the maximum number of CPU priority level load reports
    * kept in the target.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUPriorityReportInfo(String target,
                                                       short executionUnit,
                                                       int integrationInterval,
                                                       int maxReports)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }

      writeTextBlock("CPU PRIORITY REPORT INFO\n" +
            "\ntarget = " + target +
            "\nexecutionUnit = " + toU16String(executionUnit) +
            "\nintegrationInterval = " + toU32String(integrationInterval) +
            "\nmaxReports = " + toU32String(maxReports) +
            "\n");
   }

   /**
    * Write CPU priority level load reports enablement information.
    *
    * @param enabled  true if CPU priority level load reporting was enabled,
    * false otherwise.
    * @param interval  the CPU priority level load reporting integration
    * interval in ticks.
    * @param maxReports  the maximum number of CPU priority level load reports
    * kept in the target.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU priority level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUPriorityReportsEnabled(boolean enabled,
                                                           int interval,
                                                           int maxReports,
                                                           int seconds,
                                                           int secondsTick,
                                                           int secondsNTick,
                                                           boolean compressed)
      throws IOException
   {
      MonitorGetCPUPriReportsEnabledRequest request;
      MonitorGetCPUPriReportsEnabledReply reply;

      startWritingSignalBlock(MonitorGetCPUPriReportsEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetCPUPriReportsEnabledRequest();
      writeSignal(request);

      reply = new MonitorGetCPUPriReportsEnabledReply();
      reply.enabled = enabled;
      reply.interval = interval;
      reply.maxReports = maxReports;
      reply.sec = seconds;
      reply.sectick = secondsTick;
      reply.secntick = secondsNTick;
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write CPU priority level load reports.
    *
    * @param reports  the CPU priority level load reports to write.
    * @param executionUnit  the execution unit from where the CPU priority level
    * load reports were retrieved from.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUPriorityReports(CPUPriorityReport[] reports,
                                                    short executionUnit,
                                                    boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorGetCPUPriReportsRequest request;
         MonitorGetCPUPriReportsReply reply;

         startWritingSignalBlock(MonitorGetCPUPriReportsRequest.SIG_NO,
                                 compressed);
   
         request = new MonitorGetCPUPriReportsRequest();
         request.euId = executionUnit;
         request.tick = reports[0].getTick();
         request.ntick = reports[0].getNTick();
         writeSignal(request);
   
         reply = new MonitorGetCPUPriReportsReply();
         reply.status = 0;
         reply.enabled = true;
         reply.euId = executionUnit;
         reply.reports = createMonitorCPUPriReports(reports);
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Write CPU process level load report profiling information.
    *
    * @param target  the name of the target being used.
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
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUProcessReportInfo(String target,
                                                      short executionUnit,
                                                      int integrationInterval,
                                                      int maxReports,
                                                      int maxProcessesPerReport,
                                                      String profiledProcesses)
      throws IOException
   {
      if ((target == null) || (profiledProcesses == null))
      {
         throw new IllegalArgumentException();
      }

      writeTextBlock("CPU PROCESS REPORT INFO\n" +
            "\ntarget = " + target +
            "\nexecutionUnit = " + toU16String(executionUnit) +
            "\nintegrationInterval = " + toU32String(integrationInterval) +
            "\nmaxReports = " + toU32String(maxReports) +
            "\nmaxProcessesPerReport = " + toU32String(maxProcessesPerReport) +
            "\nprofiledProcesses = " + profiledProcesses +
            "\n");
   }

   /**
    * Write CPU process level load reports enablement information.
    *
    * @param enabled  true if CPU process level load reporting was enabled,
    * false otherwise.
    * @param interval  the CPU process level load reporting integration interval
    * in ticks.
    * @param maxReports  the maximum number of CPU process level load reports
    * kept in the target.
    * @param maxProcsReport  the maximum number of processes per CPU process
    * level load report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU process level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUProcessReportsEnabled(boolean enabled,
                                                          int interval,
                                                          int maxReports,
                                                          int maxProcsReport,
                                                          int seconds,
                                                          int secondsTick,
                                                          int secondsNTick,
                                                          boolean compressed)
      throws IOException
   {
      MonitorGetCPUProcessReportsEnabledRequest request;
      MonitorGetCPUProcessReportsEnabledReply reply;

      startWritingSignalBlock(MonitorGetCPUProcessReportsEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetCPUProcessReportsEnabledRequest();
      writeSignal(request);

      reply = new MonitorGetCPUProcessReportsEnabledReply();
      reply.enabled = enabled;
      reply.interval = interval;
      reply.maxReports = maxReports;
      reply.maxProcsReport = maxProcsReport;
      reply.sec = seconds;
      reply.sectick = secondsTick;
      reply.secntick = secondsNTick;
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write CPU process level load reports.
    *
    * @param reports  the CPU process level load reports to write.
    * @param executionUnit  the execution unit from where the CPU process level
    * load reports were retrieved from.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUProcessReports(CPUProcessReport[] reports,
                                                   short executionUnit,
                                                   boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorGetCPUProcessReportsRequest request;
         MonitorGetCPUProcessReportsReply reply;

         startWritingSignalBlock(MonitorGetCPUProcessReportsRequest.SIG_NO,
                                 compressed);
   
         request = new MonitorGetCPUProcessReportsRequest();
         request.euId = executionUnit;
         request.tick = reports[0].getTick();
         request.ntick = reports[0].getNTick();
         writeSignal(request);
   
         reply = new MonitorGetCPUProcessReportsReply();
         reply.status = 0;
         reply.enabled = true;
         reply.euId = executionUnit;
         reply.reports = createMonitorCPUProcessReports(reports);
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Write CPU block level load report profiling information.
    *
    * @param target  the name of the target being used.
    * @param executionUnit  the execution unit from where the CPU block level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU block level load reporting integration
    * interval in ticks.
    * @param maxReports  the maximum number of CPU block level load reports kept
    * in the target.
    * @param maxBlocksPerReport  the maximum number of blocks per CPU block
    * level load report.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUBlockReportInfo(String target,
                                                    short executionUnit,
                                                    int integrationInterval,
                                                    int maxReports,
                                                    int maxBlocksPerReport)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }

      writeTextBlock("CPU BLOCK REPORT INFO\n" +
            "\ntarget = " + target +
            "\nexecutionUnit = " + toU16String(executionUnit) +
            "\nintegrationInterval = " + toU32String(integrationInterval) +
            "\nmaxReports = " + toU32String(maxReports) +
            "\nmaxBlocksPerReport = " + toU32String(maxBlocksPerReport) +
            "\n");
   }

   /**
    * Write CPU block level load reports enablement information.
    *
    * @param enabled  true if CPU block level load reporting was enabled, false
    * otherwise.
    * @param interval  the CPU block level load reporting integration interval
    * in ticks.
    * @param maxReports  the maximum number of CPU block level load reports kept
    * in the target.
    * @param maxBlocksReport  the maximum number of blocks per CPU block level
    * load report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU block level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUBlockReportsEnabled(boolean enabled,
                                                        int interval,
                                                        int maxReports,
                                                        int maxBlocksReport,
                                                        int seconds,
                                                        int secondsTick,
                                                        int secondsNTick,
                                                        boolean compressed)
      throws IOException
   {
      MonitorGetCPUBlockReportsEnabledRequest request;
      MonitorGetCPUBlockReportsEnabledReply reply;

      startWritingSignalBlock(MonitorGetCPUBlockReportsEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetCPUBlockReportsEnabledRequest();
      writeSignal(request);

      reply = new MonitorGetCPUBlockReportsEnabledReply();
      reply.enabled = enabled;
      reply.interval = interval;
      reply.maxReports = maxReports;
      reply.maxBlocksReport = maxBlocksReport;
      reply.sec = seconds;
      reply.sectick = secondsTick;
      reply.secntick = secondsNTick;
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write CPU block level load reports.
    *
    * @param reports  the CPU block level load reports to write.
    * @param executionUnit  the execution unit from where the CPU block level
    * load reports were retrieved from.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeCPUBlockReports(CPUBlockReport[] reports,
                                                 short executionUnit,
                                                 boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorGetCPUBlockReportsRequest request;
         MonitorGetCPUBlockReportsReply reply;

         startWritingSignalBlock(MonitorGetCPUBlockReportsRequest.SIG_NO,
                                 compressed);

         request = new MonitorGetCPUBlockReportsRequest();
         request.euId = executionUnit;
         request.tick = reports[0].getTick();
         request.ntick = reports[0].getNTick();
         writeSignal(request);

         reply = new MonitorGetCPUBlockReportsReply();
         reply.status = 0;
         reply.enabled = true;
         reply.euId = executionUnit;
         reply.reports = createMonitorCPUBlockReports(reports);
         writeSignal(reply);

         endWritingSignalBlock(0);
      }
   }

   /**
    * Write user report profiling information.
    *
    * @param target  the name of the target being used.
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
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeUserReportInfo(String target,
                                                int integrationInterval,
                                                int maxReports,
                                                int maxValuesPerReport,
                                                int reportNumber,
                                                boolean continuous,
                                                boolean maxMin,
                                                boolean multiple)
      throws IOException
   {
      if (target == null)
      {
         throw new IllegalArgumentException();
      }

      writeTextBlock("USER REPORT INFO\n" +
            "\ntarget = " + target +
            "\nintegrationInterval = " + toU32String(integrationInterval) +
            "\nmaxReports = " + toU32String(maxReports) +
            "\nmaxValuesPerReport = " + toU32String(maxValuesPerReport) +
            "\nreportNumber = " + toU32String(reportNumber) +
            "\ncontinuous = " + continuous +
            "\nmaxMin = " + maxMin +
            "\nmultiple = " + multiple +
            "\n");
   }

   /**
    * Write user reports enablement information.
    *
    * @param reportNo  the user report number.
    * @param enabled  true if user reporting was enabled, false otherwise.
    * @param interval  the user reporting integration interval in ticks.
    * @param maxReports  the maximum number of user reports kept in the target.
    * @param maxValuesReport  the maximum number of values per user report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * user reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeUserReportsEnabled(int reportNo,
                                                    boolean enabled,
                                                    int interval,
                                                    int maxReports,
                                                    int maxValuesReport,
                                                    int seconds,
                                                    int secondsTick,
                                                    int secondsNTick,
                                                    boolean compressed)
      throws IOException
   {
      MonitorGetUserReportsEnabledRequest request;
      MonitorGetUserReportsEnabledReply reply;

      startWritingSignalBlock(MonitorGetUserReportsEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetUserReportsEnabledRequest();
      request.reportNo = reportNo;
      writeSignal(request);

      reply = new MonitorGetUserReportsEnabledReply();
      reply.reportNo = reportNo;
      reply.enabled = enabled;
      reply.interval = interval;
      reply.maxReports = maxReports;
      reply.maxValuesReport = maxValuesReport;
      reply.sec = seconds;
      reply.sectick = secondsTick;
      reply.secntick = secondsNTick;
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write user reports.
    *
    * @param reports  the user reports to write.
    * @param reportNumber  the user report number.
    * @param continuous  true if the user reporting is continuous, false
    * otherwise.
    * @param maxMin  true if the maximum and minimum user report measurement
    * values are tracked during each integration interval, false otherwise.
    * @param multiple  true if this user report type supports multiple user
    * report measurement values per user report, false if it supports only a
    * single user report measurement value per user report.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeUserReports(UserReport[] reports,
                                             int reportNumber,
                                             boolean continuous,
                                             boolean maxMin,
                                             boolean multiple,
                                             boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorGetUserReportsRequest request;
         MonitorGetUserReportsReply reply;

         startWritingSignalBlock(MonitorGetUserReportsRequest.SIG_NO,
                                 compressed);

         request = new MonitorGetUserReportsRequest();
         request.reportNo = reportNumber;
         request.tick = reports[0].getTick();
         request.ntick = reports[0].getNTick();
         writeSignal(request);

         reply = new MonitorGetUserReportsReply();
         reply.status = 0;
         reply.reportNo = reportNumber;
         reply.enabled = true;
         reply.continuous = continuous;
         reply.maxmin = maxMin;
         reply.multiple = multiple;
         reply.reports = createMonitorUserReports(reports, maxMin);
         writeSignal(reply);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Start writing performance counters. Should be followed by one or more
    * writePerformanceCounter() calls and ended with one
    * endWritingPerformanceCounters() call.
    *
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingPerformanceCounters(boolean compressed)
      throws IOException
   {
      MonitorGetSupportedCounterTypesRequest signal;

      startWritingSignalBlock(MonitorGetSupportedCounterTypesRequest.SIG_NO,
                              compressed);
      signal = new MonitorGetSupportedCounterTypesRequest();
      writeSignal(signal);
   }

   /**
    * Write a performance counter.
    *
    * @param performanceCounter  the performance counter to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writePerformanceCounter(
         PerformanceCounterInfo performanceCounter)
      throws IOException
   {
      MonitorGetSupportedCounterTypesReply signal;

      if (performanceCounter == null)
      {
         throw new IllegalArgumentException();
      }

      signal = new MonitorGetSupportedCounterTypesReply();
      signal.counterType = performanceCounter.getType();
      signal.name = performanceCounter.getName();
      writeSignal(signal);
   }

   /**
    * End writing performance counters.
    *
    * @param status  the overall underlying signal block status, 0 means success.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingPerformanceCounters(int status)
      throws IOException
   {
      MonitorGetSupportedCounterTypesEndmark signal;

      signal = new MonitorGetSupportedCounterTypesEndmark();
      signal.status = status;
      writeSignal(signal);
      endWritingSignalBlock(status);
   }

   /**
    * Write performance counter enablement information.
    *
    * @param enabledInfo  the performance counter enablement information to write.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writePerformanceCounterEnabled(
         PerformanceCounterEnabledInfo enabledInfo,
         boolean compressed)
      throws IOException
   {
      MonitorGetCounterTypeEnabledRequest request;
      MonitorGetCounterTypeEnabledReply reply;

      startWritingSignalBlock(MonitorGetCounterTypeEnabledRequest.SIG_NO,
                              compressed);

      request = new MonitorGetCounterTypeEnabledRequest(
            enabledInfo.getExecutionUnit(), enabledInfo.getType());
      writeSignal(request);

      reply = new MonitorGetCounterTypeEnabledReply();
      reply.status = 0;
      reply.enabled = enabledInfo.isEnabled();
      reply.euId = enabledInfo.getExecutionUnit();
      reply.counterType = enabledInfo.getType();
      reply.counterValue = enabledInfo.getValue();
      reply.maxReports = enabledInfo.getMaxReports();
      writeSignal(reply);

      endWritingSignalBlock(0);
   }

   /**
    * Write performance counter reports.
    *
    * @param reports  the performance counter reports to write.
    * @param executionUnit  the execution unit from where the performance
    * counter reports originates.
    * @param counterType  the performance counter type of the performance
    * counter reports.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writePerformanceCounterReports(
         PerformanceCounterReport[] reports,
         short executionUnit,
         int counterType,
         boolean compressed)
      throws IOException
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.length > 0)
      {
         MonitorCounterReportsNotify signal;

         startWritingSignalBlock(MonitorCounterReportsNotify.SIG_NO, compressed);
   
         signal = new MonitorCounterReportsNotify();
         signal.euId = executionUnit;
         signal.counterType = counterType;
         signal.reports = createMonitorCounterReports(reports);
         writeSignal(signal);
   
         endWritingSignalBlock(0);
      }
   }

   /**
    * Write a performance counter reports loss.
    *
    * @param reportsLost  the number of lost performance counter reports.
    * @param executionUnit  the execution unit from where the performance
    * counter reports were lost.
    * @param counterType  the performance counter type of the lost performance
    * counter reports.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writePerformanceCounterReportsLoss(int reportsLost,
                                                               short executionUnit,
                                                               int counterType,
                                                               boolean compressed)
      throws IOException
   {
      MonitorCounterReportsLossNotify signal;

      startWritingSignalBlock(MonitorCounterReportsLossNotify.SIG_NO,
                              compressed);

      signal = new MonitorCounterReportsLossNotify();
      signal.euId = executionUnit;
      signal.counterType = counterType;
      signal.reportsLost = reportsLost;
      writeSignal(signal);

      endWritingSignalBlock(0);
   }

   /**
    * Start writing events. Should be followed by one or more writeEvent() calls
    * and ended with one endWritingEvents() call.
    *
    * @param target  the name of the target being used.
    * @param scope  the scope string.
    * @param eventActions  the name of the event action settings file.
    * @param trace  true if event tracing, false if event notification.
    * @param compressed  true if the underlying signal block should be
    * compressed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingEvents(String target,
                                               String scope,
                                               String eventActions,
                                               boolean trace,
                                               boolean compressed)
      throws IOException
   {
      MonitorGetTraceRequest signal;

      if ((target == null) || (scope == null) || (eventActions == null))
      {
         throw new IllegalArgumentException();
      }

      eventTrace = trace;
      processCache.clear();
      writeTextBlock("EVENT INFO\n" +
                     "\ntarget = " + target +
                     "\nscope = " + scope +
                     "\neventActions = " + eventActions +
                     "\n");
      startWritingSignalBlock(MonitorGetTraceRequest.SIG_NO, compressed);
      signal = new MonitorGetTraceRequest(0, ~0, null);
      writeSignal(signal);
   }

   /**
    * Write an event.
    *
    * @param event  the event to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeEvent(TargetEvent event) throws IOException
   {
      if (event instanceof SendEvent)
      {
         writeMonitorGetSendTraceReply((SendEvent) event);
      }
      else if (event instanceof ReceiveEvent)
      {
         writeMonitorGetReceiveTraceReply((ReceiveEvent) event);
      }
      else if (event instanceof SwapEvent)
      {
         writeMonitorGetSwapTraceReply((SwapEvent) event);
      }
      else if (event instanceof CreateEvent)
      {
         writeMonitorGetCreateTraceReply((CreateEvent) event);
      }
      else if (event instanceof KillEvent)
      {
         writeMonitorGetKillTraceReply((KillEvent) event);
      }
      else if (event instanceof ErrorEvent)
      {
         writeMonitorGetErrorTraceReply((ErrorEvent) event);
      }
      else if (event instanceof ResetEvent)
      {
         writeMonitorGetResetTraceReply((ResetEvent) event);
      }
      else if (event instanceof UserEvent)
      {
         writeMonitorGetUserTraceReply((UserEvent) event);
      }
      else if (event instanceof BindEvent)
      {
         writeMonitorGetBindTraceReply((BindEvent) event);
      }
      else if (event instanceof AllocEvent)
      {
         writeMonitorGetAllocTraceReply((AllocEvent) event);
      }
      else if (event instanceof FreeEvent)
      {
         writeMonitorGetFreeBufTraceReply((FreeEvent) event);
      }
      else if (event instanceof TimeoutEvent)
      {
         writeMonitorGetTimeoutTraceReply((TimeoutEvent) event);
      }
      else if (event instanceof LossEvent)
      {
         writeMonitorGetLossTraceReply((LossEvent) event);
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   /**
    * End writing events.
    *
    * @param status  the overall underlying signal block status, 0 means success.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingEvents(int status) throws IOException
   {
      MonitorGetTraceEndmark signal;

      signal = new MonitorGetTraceEndmark();
      signal.status = status;
      writeSignal(signal);
      endWritingSignalBlock(status);
      processCache.clear();
   }

   private MonitorCPUReport[] createMonitorCPUReports(CPUReport[] reports)
   {
      MonitorCPUReport[] monitorReports = new MonitorCPUReport[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         CPUReport report;
         MonitorCPUReport monitorReport;

         report = reports[i];
         monitorReport = new MonitorCPUReport();
         monitorReport.tick = report.getTick();
         monitorReport.ntick = report.getNTick();
         monitorReport.interval = report.getInterval();
         monitorReport.sum = report.getSum();
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private MonitorCPUPriReport[] createMonitorCPUPriReports(
         CPUPriorityReport[] reports)
   {
      MonitorCPUPriReport[] monitorReports =
         new MonitorCPUPriReport[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         CPUPriorityReport report;
         MonitorCPUPriReport monitorReport;

         report = reports[i];
         monitorReport = new MonitorCPUPriReport();
         monitorReport.tick = report.getTick();
         monitorReport.ntick = report.getNTick();
         monitorReport.interval = report.getInterval();
         monitorReport.sumInterrupt = report.getSumInterrupt();
         monitorReport.sumBackground = report.getSumBackground();
         monitorReport.sumPrioritized = report.getSumPrioritized();
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private MonitorCPUProcessReport[] createMonitorCPUProcessReports(
         CPUProcessReport[] reports)
   {
      MonitorCPUProcessReport[] monitorReports =
         new MonitorCPUProcessReport[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         CPUProcessReport report;
         MonitorCPUProcessReport monitorReport;
         CPUProcessLoad[] processes;
         MonitorCPUProcess[] monitorProcesses;

         report = reports[i];
         monitorReport = new MonitorCPUProcessReport();
         monitorReport.tick = report.getTick();
         monitorReport.ntick = report.getNTick();
         monitorReport.interval = report.getInterval();
         monitorReport.sumOther = report.getSumOther();
         processes = report.getSumProcesses();
         monitorProcesses = new MonitorCPUProcess[processes.length];
         for (int j = 0; j < processes.length; j++)
         {
            CPUProcessLoad process;
            MonitorCPUProcess monitorProcess;

            process = processes[j];
            monitorProcess = new MonitorCPUProcess();
            monitorProcess.id = process.getId();
            monitorProcess.sum = process.getSum();
            monitorProcesses[j] = monitorProcess;
         }
         monitorReport.processesCount = monitorProcesses.length;
         monitorReport.processes = monitorProcesses;
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private MonitorCPUBlockReport[] createMonitorCPUBlockReports(
         CPUBlockReport[] reports)
   {
      MonitorCPUBlockReport[] monitorReports =
         new MonitorCPUBlockReport[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         CPUBlockReport report;
         MonitorCPUBlockReport monitorReport;
         CPUBlockLoad[] blocks;
         MonitorCPUBlock[] monitorBlocks;

         report = reports[i];
         monitorReport = new MonitorCPUBlockReport();
         monitorReport.tick = report.getTick();
         monitorReport.ntick = report.getNTick();
         monitorReport.interval = report.getInterval();
         monitorReport.sumOther = report.getSumOther();
         blocks = report.getSumBlocks();
         monitorBlocks = new MonitorCPUBlock[blocks.length];
         for (int j = 0; j < blocks.length; j++)
         {
            CPUBlockLoad block;
            MonitorCPUBlock monitorBlock;

            block = blocks[j];
            monitorBlock = new MonitorCPUBlock();
            monitorBlock.id = block.getId();
            monitorBlock.sum = block.getSum();
            monitorBlocks[j] = monitorBlock;
         }
         monitorReport.blocksCount = monitorBlocks.length;
         monitorReport.blocks = monitorBlocks;
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private MonitorUserReport[] createMonitorUserReports(
         UserReport[] reports,
         boolean maxMin)
   {
      MonitorUserReport[] monitorReports = new MonitorUserReport[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         UserReport report;
         MonitorUserReport monitorReport;
         UserReportValue[] values;
         MonitorUserReportValue[] monitorValues;

         report = reports[i];
         monitorReport = new MonitorUserReport();
         monitorReport.tick = report.getTick();
         monitorReport.ntick = report.getNTick();
         monitorReport.interval = report.getInterval();
         monitorReport.sumOther = report.getSumOther();
         if (maxMin)
         {
            monitorReport.maxOther = report.getMaxOther();
            monitorReport.minOther = report.getMinOther();
         }
         values = report.getValues();
         monitorValues = new MonitorUserReportValue[values.length];
         for (int j = 0; j < values.length; j++)
         {
            UserReportValue value;
            MonitorUserReportValue monitorValue;

            value = values[j];
            monitorValue = (maxMin ? new MonitorMaxMinUserReportValue() :
               new MonitorUserReportValue());
            monitorValue.id = value.getId();
            monitorValue.sum = value.getSum();
            if (maxMin)
            {
               MaxMinUserReportValue maxMinValue;
               MonitorMaxMinUserReportValue monitorMaxMinValue;

               maxMinValue = (MaxMinUserReportValue) value;
               monitorMaxMinValue = (MonitorMaxMinUserReportValue) monitorValue;
               monitorMaxMinValue.max = maxMinValue.getMax();
               monitorMaxMinValue.min = maxMinValue.getMin();
            }
            monitorValues[j] = monitorValue;
         }
         monitorReport.reportValuesCount = monitorValues.length;
         monitorReport.reportValues = monitorValues;
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private MonitorCounterReport[] createMonitorCounterReports(
         PerformanceCounterReport[] reports)
   {
      MonitorCounterReportExt[] monitorReports =
         new MonitorCounterReportExt[reports.length];

      for (int i = 0; i < reports.length; i++)
      {
         PerformanceCounterReport report;
         MonitorCounterReportExt monitorReport;

         report = reports[i];
         monitorReport = new MonitorCounterReportExt();
         monitorReport.pc = report.getPC();
         monitorReport.pid = report.getPid();
         monitorReports[i] = monitorReport;
      }

      return monitorReports;
   }

   private void writeMonitorGetProcessTraceReply(TargetEvent.ProcessInfo process)
      throws IOException
   {
      if (process != null)
      {
         MonitorGetProcessTraceReply processSignal;

         processSignal = processCache.get(process.getId());
         if (processSignal == null)
         {
            processSignal = new MonitorGetProcessTraceReply();
            processSignal.pid = process.getId();
            processSignal.bid = process.getBid();
            processSignal.sid = process.getSid();
            processSignal.type = process.getType();
            processSignal.entrypoint = process.getEntrypoint();
            processSignal.properties = process.getProperties();
            processSignal.name = process.getName();

            processCache.put(process.getId(), processSignal);
            writeSignal(processSignal);
         }
      }
   }

   private void writeMonitorGetSendTraceReply(SendEvent event)
      throws IOException
   {
      MonitorGetSendTraceReply signal = new MonitorGetSendTraceReply();
      TargetEvent.ProcessInfo sender = event.getSenderProcess();
      TargetEvent.ProcessInfo addressee = event.getAddresseeProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.signalNumber = event.getSignalNumber();
      signal.signalSender = sender.getId();
      signal.signalAddressee = addressee.getId();
      signal.signalSize = event.getSignalSize();
      signal.signalAddress = event.getSignalAddress();
      signal.signalId = event.getSignalId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();
      signal.signalData = event.getSignalData();

      writeMonitorGetProcessTraceReply(sender);
      writeMonitorGetProcessTraceReply(addressee);
      writeSignal(signal);
   }

   private void writeMonitorGetReceiveTraceReply(ReceiveEvent event)
      throws IOException
   {
      MonitorGetReceiveTraceReply signal = new MonitorGetReceiveTraceReply();
      TargetEvent.ProcessInfo sender = event.getSenderProcess();
      TargetEvent.ProcessInfo addressee = event.getAddresseeProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.signalNumber = event.getSignalNumber();
      signal.signalSender = sender.getId();
      signal.signalAddressee = addressee.getId();
      signal.signalSize = event.getSignalSize();
      signal.signalAddress = event.getSignalAddress();
      signal.signalId = event.getSignalId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();
      signal.signalData = event.getSignalData();

      writeMonitorGetProcessTraceReply(sender);
      writeMonitorGetProcessTraceReply(addressee);
      writeSignal(signal);
   }

   private void writeMonitorGetSwapTraceReply(SwapEvent event)
      throws IOException
   {
      MonitorGetSwapTraceReply signal = new MonitorGetSwapTraceReply();
      TargetEvent.ProcessInfo swappedOut = event.getSwappedOutProcess();
      TargetEvent.ProcessInfo swappedIn = event.getSwappedInProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = swappedOut.getId();
      signal.to = swappedIn.getId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(swappedOut);
      writeMonitorGetProcessTraceReply(swappedIn);
      writeSignal(signal);
   }

   private void writeMonitorGetCreateTraceReply(CreateEvent event)
      throws IOException
   {
      MonitorGetCreateTraceReply signal = new MonitorGetCreateTraceReply();
      TargetEvent.ProcessInfo creator = event.getCreatorProcess();
      TargetEvent.ProcessInfo created = event.getCreatedProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = ((creator != null) ? creator.getId() : 0);
      signal.to = created.getId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(creator);
      writeMonitorGetProcessTraceReply(created);
      writeSignal(signal);
   }

   private void writeMonitorGetKillTraceReply(KillEvent event)
      throws IOException
   {
      MonitorGetKillTraceReply signal = new MonitorGetKillTraceReply();
      TargetEvent.ProcessInfo killer = event.getKillerProcess();
      TargetEvent.ProcessInfo killed = event.getKilledProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = ((killer != null) ? killer.getId() : 0);
      signal.to = killed.getId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(killer);
      writeMonitorGetProcessTraceReply(killed);
      writeSignal(signal);
   }

   private void writeMonitorGetErrorTraceReply(ErrorEvent event)
      throws IOException
   {
      MonitorGetErrorTraceReply signal = new MonitorGetErrorTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = process.getId();
      signal.exec = event.isExecutive();
      signal.error = event.getError();
      signal.extra = event.getExtra();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetResetTraceReply(ResetEvent event)
      throws IOException
   {
      MonitorGetResetTraceReply signal = new MonitorGetResetTraceReply();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.warm = event.isWarmReset();
      signal.lineNumber = event.getLineNumber();
      signal.fileName = event.getFileName();

      writeSignal(signal);
   }

   private void writeMonitorGetUserTraceReply(UserEvent event)
      throws IOException
   {
      MonitorGetUserTraceReply signal = new MonitorGetUserTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = process.getId();
      signal.eventNumber = event.getEventNumber();
      signal.eventSize = event.getEventDataSize();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();
      signal.eventData = event.getEventData();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetBindTraceReply(BindEvent event)
      throws IOException
   {
      MonitorGetBindTraceReply signal = new MonitorGetBindTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = process.getId();
      signal.fromEuId = event.getFromExecutionUnit();
      signal.toEuId = event.getToExecutionUnit();
      signal.lineNumber = event.getLineNumber();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetAllocTraceReply(AllocEvent event)
      throws IOException
   {
      MonitorGetAllocTraceReply signal = new MonitorGetAllocTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = process.getId();
      signal.signalNumber = event.getSignalNumber();
      signal.signalSize = event.getSignalSize();
      signal.signalAddress = event.getSignalAddress();
      signal.signalId = event.getSignalId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetFreeBufTraceReply(FreeEvent event)
      throws IOException
   {
      MonitorGetFreeBufTraceReply signal = new MonitorGetFreeBufTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.from = process.getId();
      signal.signalNumber = event.getSignalNumber();
      signal.signalSize = event.getSignalSize();
      signal.signalAddress = event.getSignalAddress();
      signal.signalId = event.getSignalId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetTimeoutTraceReply(TimeoutEvent event)
      throws IOException
   {
      MonitorGetTimeoutTraceReply signal = new MonitorGetTimeoutTraceReply();
      TargetEvent.ProcessInfo process = event.getProcess();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.timeout = event.getTimeout();
      signal.tmoSource = event.getSystemCall();
      signal.from = process.getId();
      signal.lineNumber = event.getLineNumber();
      signal.euId = event.getExecutionUnit();
      signal.fileName = event.getFileName();

      writeMonitorGetProcessTraceReply(process);
      writeSignal(signal);
   }

   private void writeMonitorGetLossTraceReply(LossEvent event)
      throws IOException
   {
      MonitorGetLossTraceReply signal = new MonitorGetLossTraceReply();

      signal.reference = getEventRefCount(event);
      signal.tick = event.getTick();
      signal.ntick = event.getNTick();
      signal.sec = event.getSeconds();
      signal.sectick = event.getSecondsTick();
      signal.extendedInfo = true;
      signal.lostSize = event.getLostSize();

      writeSignal(signal);
   }

   private int getEventRefCount(TargetEvent event)
   {
      return (eventTrace ? event.getReference() : eventRefCount++);
   }

   private static String toU16String(short s)
   {
      return Integer.toString(s & 0xFFFF);
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   /**
    * This class provides a process info cache of a specified maximum size
    * where the oldest entry is removed if the size limit is threatened to
    * be exceeded.
    *
    * @see java.util.LinkedHashMap
    */
   private static class ProcessInfoCache extends LinkedHashMap
   {
      private static final long serialVersionUID = 1L;

      private final int processCacheCount;

      /**
       * Create a new process info cache object.
       *
       * @param processCacheCount  the maximum size of this process info
       * cache.
       */
      ProcessInfoCache(int processCacheCount)
      {
         super(processCacheCount);
         if (processCacheCount < 1)
         {
            throw new IllegalArgumentException();
         }
         this.processCacheCount = processCacheCount;
      }

      /**
       * Return the process info object with the specified process ID or null
       * if there is no mapping for the specified process ID.
       *
       * @param id  the process ID.
       * @return the corresponding process info object or null if there is no
       * mapping for the specified process ID.
       */
      public MonitorGetProcessTraceReply get(int id)
      {
         MonitorGetProcessTraceReply process;

         process = (MonitorGetProcessTraceReply) get(new Integer(id));
         return process;
      }

      /**
       * Associate the specified process ID with the specified process info
       * object. If there is a previous mapping for the specified process ID,
       * the old process info object is removed.
       *
       * @param id  the process ID.
       * @param process  the corresponding process info object.
       */
      public void put(int id, MonitorGetProcessTraceReply process)
      {
         Integer key;

         if (process == null)
         {
            throw new IllegalArgumentException();
         }
         key = new Integer(id);
         if (containsKey(key))
         {
            // Remove the old mapping to update its insertion order.
            remove(key);
         }
         put(key, process);
      }

      /**
       * This method limits the maximum size of the process info cache to
       * processCacheCount entries and removes the oldest entry when a new
       * entry is added that threatens the size limit of the process info
       * cache to be exceeded.
       *
       * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
       */
      protected boolean removeEldestEntry(Map.Entry eldest)
      {
         return (size() > processCacheCount);
      }
   }
}