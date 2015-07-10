/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.monitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorStatus;
import com.ose.system.service.monitor.signal.MonitorAttachRequest;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorDetachRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorEventInfoSignal;
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
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsRequest;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetLossTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetMemory64Request;
import com.ose.system.service.monitor.signal.MonitorGetMemoryRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfo64Reply;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfo64Request;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsReply;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetReceiveTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetRegisterInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetRegisterInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetRegistersReply;
import com.ose.system.service.monitor.signal.MonitorGetRegistersRequest;
import com.ose.system.service.monitor.signal.MonitorGetResetTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetSendTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueRequest;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageEndmark;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageReply;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageRequest;
import com.ose.system.service.monitor.signal.MonitorGetSwapTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSysParamEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSysParamReply;
import com.ose.system.service.monitor.signal.MonitorGetSysParamRequest;
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
import com.ose.system.service.pm.ProgramManagerStatus;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmInterfaceReply;
import com.ose.system.service.pm.signal.PmInterfaceRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply_64;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest_64;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesReply;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply_64;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest_64;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoReply_64;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.pm.signal.PmProgramInfoRequest_64;
import com.ose.system.service.pm.signal.PmProgramsReply;
import com.ose.system.service.pm.signal.PmProgramsRequest;

public class Dump
{
   private static final SignalComparator
      MONITOR_GET_SYS_PARAM_REPLY_COMPARATOR =
         new MonitorGetSysParamReplyComparator();
   private static final SignalComparator
      MONITOR_GET_SEGMENT_INFO_REPLY_COMPARATOR =
         new MonitorGetSegmentInfoReplyComparator();
   private static final SignalComparator
      MONITOR_GET_BLOCK_INFO_REPLY_COMPARATOR =
         new MonitorGetBlockInfoReplyComparator();
   private static final SignalComparator
      MONITOR_GET_PROCESS_INFO_REPLY_COMPARATOR =
         new MonitorGetProcessInfoReplyComparator();
   private static final SignalComparator
      MONITOR_GET_ENV_VARS_REPLY_COMPARATOR =
         new MonitorGetEnvVarsReplyComparator();
   private static final SignalComparator
      MONITOR_GET_STACK_USAGE_REPLY_COMPARATOR =
         new MonitorGetStackUsageReplyComparator();
   private static final SignalComparator
      MONITOR_GET_SIGNAL_QUEUE_REPLY_COMPARATOR =
         new MonitorGetSignalQueueReplyComparator();
   private static final SignalComparator
      MONITOR_GET_POOL_INFO_REPLY_COMPARATOR =
         new MonitorGetPoolInfoReplyComparator();
   private static final SignalComparator
      MONITOR_GET_POOL_INFO_64_REPLY_COMPARATOR =
         new MonitorGetPoolInfo64ReplyComparator();
   private static final SignalComparator
      MONITOR_GET_POOL_SIGNALS_REPLY_COMPARATOR =
         new MonitorGetPoolSignalsReplyComparator();
   private static final SignalComparator
      MONITOR_GET_REGISTERS_REPLY_COMPARATOR =
         new MonitorGetRegistersReplyComparator();
   private static final SignalComparator
      PM_LOAD_MODULE_INFO_REPLY_COMPARATOR =
         new PmLoadModuleInfoReplyComparator();
   private static final SignalComparator
      PM_LOAD_MODULE_INFO_REPLY_64_COMPARATOR =
         new PmLoadModuleInfoReply_64Comparator();
   private static final SignalComparator
      PM_LOAD_MODULE_SECTION_INFO_REPLY_COMPARATOR =
         new PmLoadModuleSectionInfoReplyComparator();
   private static final SignalComparator
      PM_LOAD_MODULE_SECTION_INFO_REPLY_64_COMPARATOR =
         new PmLoadModuleSectionInfoReply_64Comparator();
   private static final SignalComparator
      PM_PROGRAM_INFO_REPLY_COMPARATOR =
         new PmProgramInfoReplyComparator();
   private static final SignalComparator
      PM_PROGRAM_INFO_REPLY_64_COMPARATOR =
         new PmProgramInfoReply_64Comparator();

   private final DumpFile dumpFile;
   private final boolean bigEndian;
   private final SignalRegistry registry;

   private final MonitorInterfaceReply monitorInterfaceReply;
   private final MonitorNameReply monitorNameReply;
   private final MonitorConnectReply monitorConnectReply;
   private final SignalTransaction monitorGetSysParamReplies;
   private final SignalTransaction monitorGetSegmentInfoReplies;
   private final SignalTransaction monitorGetBlockInfoReplies;
   private final SignalTransaction monitorGetProcessInfoReplies;
   private final SignalTransaction monitorGetEnvVarsReplies;
   private final SignalTransaction monitorGetStackUsageReplies;
   private final SignalTransaction monitorGetSignalQueueReplies;
   private final SignalTransaction monitorGetPoolInfoReplies;
   private final SignalTransaction monitorGetPoolInfo64Replies;
   private final SignalTransaction monitorGetPoolSignalsReplies;
   private final SignalTransaction monitorGetRegistersReplies;
   private final MonitorGetCPUReportsEnabledReply monitorGetCPUReportsEnabledReply;
   private final MonitorGetCPUPriReportsEnabledReply monitorGetCPUPriReportsEnabledReply;
   private final MonitorGetCPUProcessReportsEnabledReply monitorGetCPUProcessReportsEnabledReply;
   private final MonitorGetCPUBlockReportsEnabledReply monitorGetCPUBlockReportsEnabledReply;
   private final List monitorGetUserReportsEnabledReplies;
   private final List monitorCPUReportSignalBlocks;
   private final List monitorCPUPriReportSignalBlocks;
   private final List monitorCPUProcessReportSignalBlocks;
   private final List monitorCPUBlockReportSignalBlocks;
   private final List monitorUserReportSignalBlocks;
   private final MonitorEventTrace monitorEventTrace;

   private final PmInterfaceReply pmInterfaceReply;
   private final PmLoadModuleInstallHandlesReply pmLoadModuleInstallHandlesReply;
   private final PmProgramsReply pmProgramsReply;
   private final List pmLoadModuleInfoReplies;
   private final List pmLoadModuleInfoReplies_64;
   private final List pmLoadModuleSectionInfoReplies;
   private final List pmLoadModuleSectionInfoReplies_64;
   private final List pmProgramInfoReplies;
   private final List pmProgramInfoReplies_64;

   private final Map segmentMap;
   private final Map poolMap;
   private final Map pool64Map;
   private final Map blockMap;
   private final Map processMap;

   private final Map loadModuleMap;
   private final Map loadModule_64Map;
   private final Map loadModuleSectionMap;
   private final Map loadModuleSection_64Map;
   private final Map programMap;
   private final Map program_64Map;

   public Dump(File file) throws IOException
   {
      SignalBlockList signalBlocks;

      // Create the dump file and determine its byte order.
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      // Initialize the signal registry.
      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorNameRequest.class);
      registry.add(MonitorNameReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetSysParamRequest.class);
      registry.add(MonitorGetSysParamReply.class);
      registry.add(MonitorGetSysParamEndmark.class);
      registry.add(MonitorGetSegmentInfoRequest.class);
      registry.add(MonitorGetSegmentInfoReply.class);
      registry.add(MonitorGetSegmentInfoLongReply.class);
      registry.add(MonitorGetSegmentInfoEndmark.class);
      registry.add(MonitorGetBlockInfoRequest.class);
      registry.add(MonitorGetBlockInfoReply.class);
      registry.add(MonitorGetBlockInfoEndmark.class);
      registry.add(MonitorGetProcessInfoRequest.class);
      registry.add(MonitorGetProcessInfoReply.class);
      registry.add(MonitorGetProcessInfoLongReply.class);
      registry.add(MonitorGetProcessInfoEndmark.class);
      registry.add(MonitorGetEnvVarsRequest.class);
      registry.add(MonitorGetEnvVarsReply.class);
      registry.add(MonitorGetEnvVarsEndmark.class);
      registry.add(MonitorGetStackUsageRequest.class);
      registry.add(MonitorGetStackUsageReply.class);
      registry.add(MonitorGetStackUsageEndmark.class);
      registry.add(MonitorGetSignalQueueRequest.class);
      registry.add(MonitorGetSignalQueueReply.class);
      registry.add(MonitorGetSignalQueueLongReply.class);
      registry.add(MonitorGetSignalQueueEndmark.class);
      registry.add(MonitorGetPoolInfoRequest.class);
      registry.add(MonitorGetPoolInfoReply.class);
      registry.add(MonitorGetPoolInfo64Request.class);
      registry.add(MonitorGetPoolInfo64Reply.class);
      registry.add(MonitorGetPoolInfoEndmark.class);
      registry.add(MonitorGetPoolSignalsRequest.class);
      registry.add(MonitorGetPoolSignalsReply.class);
      registry.add(MonitorGetPoolSignalsEndmark.class);
      registry.add(MonitorGetRegistersRequest.class);
      registry.add(MonitorGetRegisterInfoRequest.class);
      registry.add(MonitorGetRegistersReply.class);
      registry.add(MonitorGetRegisterInfoEndmark.class);
      registry.add(MonitorGetCPUReportsEnabledRequest.class);
      registry.add(MonitorGetCPUReportsEnabledReply.class);
      registry.add(MonitorGetCPUPriReportsEnabledRequest.class);
      registry.add(MonitorGetCPUPriReportsEnabledReply.class);
      registry.add(MonitorGetCPUProcessReportsEnabledRequest.class);
      registry.add(MonitorGetCPUProcessReportsEnabledReply.class);
      registry.add(MonitorGetCPUBlockReportsEnabledRequest.class);
      registry.add(MonitorGetCPUBlockReportsEnabledReply.class);
      registry.add(MonitorGetUserReportsEnabledRequest.class);
      registry.add(MonitorGetUserReportsEnabledReply.class);
      registry.add(MonitorGetCPUReportsRequest.class);
      registry.add(MonitorGetCPUReportsReply.class);
      registry.add(MonitorGetCPUPriReportsRequest.class);
      registry.add(MonitorGetCPUPriReportsReply.class);
      registry.add(MonitorGetCPUProcessReportsRequest.class);
      registry.add(MonitorGetCPUProcessReportsReply.class);
      registry.add(MonitorGetCPUBlockReportsRequest.class);
      registry.add(MonitorGetCPUBlockReportsReply.class);
      registry.add(MonitorGetUserReportsRequest.class);
      registry.add(MonitorGetUserReportsReply.class);
      registry.add(MonitorGetTraceRequest.class);
      registry.add(MonitorGetProcessTraceReply.class);
      registry.add(MonitorGetSendTraceReply.class);
      registry.add(MonitorGetReceiveTraceReply.class);
      registry.add(MonitorGetSwapTraceReply.class);
      registry.add(MonitorGetCreateTraceReply.class);
      registry.add(MonitorGetKillTraceReply.class);
      registry.add(MonitorGetErrorTraceReply.class);
      registry.add(MonitorGetResetTraceReply.class);
      registry.add(MonitorGetUserTraceReply.class);
      registry.add(MonitorGetBindTraceReply.class);
      registry.add(MonitorGetAllocTraceReply.class);
      registry.add(MonitorGetFreeBufTraceReply.class);
      registry.add(MonitorGetTimeoutTraceReply.class);
      registry.add(MonitorGetLossTraceReply.class);
      registry.add(MonitorGetTraceEndmark.class);
      registry.add(PmInterfaceRequest.class);
      registry.add(PmInterfaceReply.class);
      registry.add(PmLoadModuleInstallHandlesRequest.class);
      registry.add(PmLoadModuleInstallHandlesReply.class);
      registry.add(PmLoadModuleInfoRequest.class);
      registry.add(PmLoadModuleInfoReply.class);
      registry.add(PmLoadModuleInfoRequest_64.class);
      registry.add(PmLoadModuleInfoReply_64.class);
      registry.add(PmLoadModuleSectionInfoRequest.class);
      registry.add(PmLoadModuleSectionInfoReply.class);
      registry.add(PmLoadModuleSectionInfoRequest_64.class);
      registry.add(PmLoadModuleSectionInfoReply_64.class);
      registry.add(PmProgramsRequest.class);
      registry.add(PmProgramsReply.class);
      registry.add(PmProgramInfoRequest.class);
      registry.add(PmProgramInfoReply.class);
      registry.add(PmProgramInfoRequest_64.class);
      registry.add(PmProgramInfoReply_64.class);

      try
      {
         // Retrieve the signal blocks.
         signalBlocks = parseSignalBlocks(dumpFile.getSignalBlocks());
   
         // Bail out if monitor interface or connect signal blocks are missing.
         if (!signalBlocks.hasMonitorInterface())
         {
            throw new IffException(
               "Dump file is missing required monitor interface signal block");
         }
         if (!signalBlocks.hasMonitorConnect())
         {
            throw new IffException(
               "Dump file is missing required monitor connect signal block");
         }
   
         // Collect the monitor interface information signal.
         monitorInterfaceReply =
            (MonitorInterfaceReply) signalBlocks.monitorInterface;
         monitorInterfaceReply.sigs = createSupportedMonitorSignalsArray(
               signalBlocks, dumpFile.getMemoryBlocks());
   
         // Bail out if inconsistent byte order
         // in dump file and monitor interface.
         if (monitorInterfaceReply.endian != bigEndian)
         {
            throw new IffException("Inconsistent byte order in dump file and " +
                                   "monitor interface reply signal");
         }
      }
      catch (IOException e)
      {
         dumpFile.close();
         throw e;
      }

      // Collect the monitor name information signal.
      monitorNameReply = (signalBlocks.hasMonitorName() ?
                          (MonitorNameReply) signalBlocks.monitorName :
                          new MonitorNameReply());
      if (monitorNameReply.systemName == null)
      {
         monitorNameReply.systemName = "";
      }
      monitorNameReply.monitorName = "com.ose.PostMortemMonitor";

      // Collect the monitor connect information signal.
      monitorConnectReply = (MonitorConnectReply) signalBlocks.monitorConnect;

      // Collect the monitor signal transactions.
      monitorGetSysParamReplies = signalBlocks.monitorGetSysParam;
      monitorGetSegmentInfoReplies = signalBlocks.monitoGetSegmentInfo;
      monitorGetBlockInfoReplies = signalBlocks.monitorGetBlockInfo;
      monitorGetProcessInfoReplies = signalBlocks.monitorGetProcessInfo;
      monitorGetEnvVarsReplies = signalBlocks.monitorGetEnvVars;
      monitorGetStackUsageReplies = signalBlocks.monitorGetStackUsage;
      monitorGetSignalQueueReplies = signalBlocks.monitorGetSignalQueue;
      monitorGetPoolInfoReplies = signalBlocks.monitorGetPoolInfo;
      monitorGetPoolInfo64Replies = signalBlocks.monitorGetPoolInfo64;
      monitorGetPoolSignalsReplies = signalBlocks.monitorGetPoolSignals;
      monitorGetRegistersReplies = signalBlocks.monitorGetRegisters;
      monitorGetCPUReportsEnabledReply = (MonitorGetCPUReportsEnabledReply)
         signalBlocks.monitorGetCPUReportsEnabled;
      monitorGetCPUPriReportsEnabledReply = (MonitorGetCPUPriReportsEnabledReply)
         signalBlocks.monitorGetCPUPriReportsEnabled;
      monitorGetCPUProcessReportsEnabledReply = (MonitorGetCPUProcessReportsEnabledReply)
         signalBlocks.monitorGetCPUProcessReportsEnabled;
      monitorGetCPUBlockReportsEnabledReply = (MonitorGetCPUBlockReportsEnabledReply)
         signalBlocks.monitorGetCPUBlockReportsEnabled;
      monitorGetUserReportsEnabledReplies =
         Collections.unmodifiableList(signalBlocks.monitorGetUserReportsEnabled);
      monitorCPUReportSignalBlocks =
         Collections.unmodifiableList(signalBlocks.monitorCPUReports);
      monitorCPUPriReportSignalBlocks =
         Collections.unmodifiableList(signalBlocks.monitorCPUPriReports);
      monitorCPUProcessReportSignalBlocks =
         Collections.unmodifiableList(signalBlocks.monitorCPUProcessReports);
      monitorCPUBlockReportSignalBlocks =
         Collections.unmodifiableList(signalBlocks.monitorCPUBlockReports);
      monitorUserReportSignalBlocks =
         Collections.unmodifiableList(signalBlocks.monitorUserReports);
      monitorEventTrace = signalBlocks.monitorEventTrace;

      // Collect the program manager interface information signal.
      pmInterfaceReply = (signalBlocks.hasPmInterface() ?
                          (PmInterfaceReply) signalBlocks.pmInterface :
                          new PmInterfaceReply());
      pmInterfaceReply.status = ProgramManagerStatus.PM_SUCCESS;
      pmInterfaceReply.whatStr = "";
      pmInterfaceReply.sigs = createSupportedPmSignalsArray(signalBlocks);

      // Collect the program manager signals.
      pmLoadModuleInstallHandlesReply = (PmLoadModuleInstallHandlesReply)
         signalBlocks.pmLoadModuleInstallHandles;
      pmProgramsReply = (PmProgramsReply) signalBlocks.pmPrograms;
      pmLoadModuleInfoReplies =
         Collections.unmodifiableList(signalBlocks.pmLoadModuleInfo);
      pmLoadModuleInfoReplies_64 =
         Collections.unmodifiableList(signalBlocks.pmLoadModuleInfo_64);
      pmLoadModuleSectionInfoReplies =
         Collections.unmodifiableList(signalBlocks.pmLoadModuleSectionInfo);
      pmLoadModuleSectionInfoReplies_64 =
         Collections.unmodifiableList(signalBlocks.pmLoadModuleSectionInfo_64);
      pmProgramInfoReplies =
         Collections.unmodifiableList(signalBlocks.pmProgramInfo);
      pmProgramInfoReplies_64 =
         Collections.unmodifiableList(signalBlocks.pmProgramInfo_64);

      // Create the segment ID map.
      segmentMap = new HashMap();
      for (Iterator i = monitorGetSegmentInfoReplies.signalsIterator(); i.hasNext();)
      {
         Signal sig = (Signal) i.next();
         if (sig instanceof MonitorGetSegmentInfoReply)
         {
            MonitorGetSegmentInfoReply segment =
               (MonitorGetSegmentInfoReply) sig;
            segmentMap.put(new Integer(segment.sid), segment);
         }
         else if (sig instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply segment =
               (MonitorGetSegmentInfoLongReply) sig;
            segmentMap.put(new Integer(segment.sid), segment);
         }
      }

      // Create the pool ID map.
      poolMap = new HashMap();
      for (Iterator i = monitorGetPoolInfoReplies.signalsIterator(); i.hasNext();)
      {
         MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) i.next();
         poolMap.put(new Integer(pool.pid), pool);
      }
      
      // Create the pool64 ID map.
      pool64Map = new HashMap();
      for (Iterator i = monitorGetPoolInfo64Replies.signalsIterator(); i.hasNext();)
      {
         MonitorGetPoolInfo64Reply pool64 = (MonitorGetPoolInfo64Reply) i.next();
         pool64Map.put(new Integer(pool64.pid), pool64);
      }

      // Create the block ID map.
      blockMap = new HashMap();
      for (Iterator i = monitorGetBlockInfoReplies.signalsIterator(); i.hasNext();)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) i.next();
         blockMap.put(new Integer(block.bid), block);
      }

      // Create the process ID map.
      processMap = new HashMap();
      for (Iterator i = monitorGetProcessInfoReplies.signalsIterator(); i.hasNext();)
      {
         Signal sig = (Signal) i.next();
         if (sig instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) sig;
            processMap.put(new Integer(process.pid), process);
         }
         else if (sig instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) sig;
            processMap.put(new Integer(process.pid), process);
         }
      }

      // Invent blocks for processes without any parent block.
      inventBlocks();

      // Invent segments for blocks without any parent segment.
      inventSegments();

      // Create the load module install handle map.
      loadModuleMap = new HashMap();
      for (Iterator i = pmLoadModuleInfoReplies.iterator(); i.hasNext();)
      {
         PmLoadModuleInfoReply loadModule = (PmLoadModuleInfoReply) i.next();
         loadModuleMap.put(loadModule.install_handle, loadModule);
      }
      
      // Create the load module 64 install handle map.
      loadModule_64Map = new HashMap();
      for (Iterator i = pmLoadModuleInfoReplies_64.iterator(); i.hasNext();)
      {
         PmLoadModuleInfoReply_64 loadModule_64 = (PmLoadModuleInfoReply_64) i.next();
         loadModule_64Map.put(loadModule_64.install_handle, loadModule_64);
      }

      // Create the load module section install handle map.
      loadModuleSectionMap = new HashMap();
      for (Iterator i = pmLoadModuleSectionInfoReplies.iterator(); i.hasNext();)
      {
         PmLoadModuleSectionInfoReply section =
            (PmLoadModuleSectionInfoReply) i.next();
         loadModuleSectionMap.put(section.install_handle, section);
      }
      
      // Create the load module section 64 install handle map.
      loadModuleSection_64Map = new HashMap();
      for (Iterator i = pmLoadModuleSectionInfoReplies_64.iterator(); i.hasNext();)
      {
         PmLoadModuleSectionInfoReply_64 section_64 =
            (PmLoadModuleSectionInfoReply_64) i.next();
         loadModuleSection_64Map.put(section_64.install_handle, section_64);
      }

      // Create the program ID map.
      programMap = new HashMap();
      for (Iterator i = pmProgramInfoReplies.iterator(); i.hasNext();)
      {
         PmProgramInfoReply program = (PmProgramInfoReply) i.next();
         programMap.put(new Integer(program.progpid), program);
      }
      
      //Create the program 64 ID map.
      program_64Map = new HashMap();
      for (Iterator i = pmProgramInfoReplies_64.iterator(); i.hasNext();)
      {
         PmProgramInfoReply_64 program_64 = (PmProgramInfoReply_64) i.next();
         program_64Map.put(new Integer(program_64.progpid), program_64);
      }
   }

   private SignalBlockList parseSignalBlocks(List signalBlocks)
      throws IOException
   {
      SignalBlockList holder = new SignalBlockList();

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         SignalTransaction signalTransaction;
         Signal sig;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         switch (reqSigNo)
         {
            case MonitorInterfaceRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorInterface = readSignalBlock(
                     signalBlock,
                     MonitorInterfaceRequest.class,
                     MonitorInterfaceReply.class);
               break;
            case MonitorNameRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorName = readSignalBlock(
                     signalBlock,
                     MonitorNameRequest.class,
                     MonitorNameReply.class);
               break;
            case MonitorConnectRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorConnect = readSignalBlock(
                     signalBlock,
                     MonitorConnectRequest.class,
                     MonitorConnectReply.class);
               break;
            case MonitorGetSysParamRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetSysParamRequest.class,
                     MonitorGetSysParamReply.class,
                     MonitorGetSysParamEndmark.class);
               holder.monitorGetSysParam.merge(
                     signalTransaction,
                     MONITOR_GET_SYS_PARAM_REPLY_COMPARATOR);
               break;
            case MonitorGetSegmentInfoRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetSegmentInfoRequest.class,
                     MonitorGetSegmentInfoReply.class,
                     MonitorGetSegmentInfoLongReply.class,
                     MonitorGetSegmentInfoEndmark.class);
               holder.monitoGetSegmentInfo.merge(
                     signalTransaction,
                     MONITOR_GET_SEGMENT_INFO_REPLY_COMPARATOR);
               break;
            case MonitorGetBlockInfoRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetBlockInfoRequest.class,
                     MonitorGetBlockInfoReply.class,
                     MonitorGetBlockInfoEndmark.class);
               holder.monitorGetBlockInfo.merge(
                     signalTransaction,
                     MONITOR_GET_BLOCK_INFO_REPLY_COMPARATOR);
               break;
            case MonitorGetProcessInfoRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetProcessInfoRequest.class,
                     MonitorGetProcessInfoReply.class,
                     MonitorGetProcessInfoLongReply.class,
                     MonitorGetProcessInfoEndmark.class);
               holder.monitorGetProcessInfo.merge(
                     signalTransaction,
                     MONITOR_GET_PROCESS_INFO_REPLY_COMPARATOR);
               break;
            case MonitorGetEnvVarsRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetEnvVarsRequest.class,
                     MonitorGetEnvVarsReply.class,
                     MonitorGetEnvVarsEndmark.class);
               holder.monitorGetEnvVars.merge(
                     signalTransaction,
                     MONITOR_GET_ENV_VARS_REPLY_COMPARATOR);
               break;
            case MonitorGetStackUsageRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetStackUsageRequest.class,
                     MonitorGetStackUsageReply.class,
                     MonitorGetStackUsageEndmark.class);
               holder.monitorGetStackUsage.merge(
                     signalTransaction,
                     MONITOR_GET_STACK_USAGE_REPLY_COMPARATOR);
               break;
            case MonitorGetSignalQueueRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetSignalQueueRequest.class,
                     MonitorGetSignalQueueReply.class,
                     MonitorGetSignalQueueLongReply.class,
                     MonitorGetSignalQueueEndmark.class);
               holder.monitorGetSignalQueue.merge(
                     signalTransaction,
                     MONITOR_GET_SIGNAL_QUEUE_REPLY_COMPARATOR);
               break;
            case MonitorGetPoolInfoRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetPoolInfoRequest.class,
                     MonitorGetPoolInfoReply.class,
                     MonitorGetPoolInfoEndmark.class);
               holder.monitorGetPoolInfo.merge(
                     signalTransaction,
                     MONITOR_GET_POOL_INFO_REPLY_COMPARATOR);
               break;
            case MonitorGetPoolInfo64Request.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetPoolInfo64Request.class,
                     MonitorGetPoolInfo64Reply.class,
                     MonitorGetPoolInfoEndmark.class);
               holder.monitorGetPoolInfo64.merge(
                     signalTransaction,
                     MONITOR_GET_POOL_INFO_64_REPLY_COMPARATOR);
            case MonitorGetPoolSignalsRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetPoolSignalsRequest.class,
                     MonitorGetPoolSignalsReply.class,
                     MonitorGetPoolSignalsEndmark.class);
               holder.monitorGetPoolSignals.merge(
                     signalTransaction,
                     MONITOR_GET_POOL_SIGNALS_REPLY_COMPARATOR);
               break;
            case MonitorGetRegisterInfoRequest.SIG_NO:
               signalTransaction = readSignalBlock(
                     signalBlock,
                     MonitorGetRegisterInfoRequest.class,
                     MonitorGetRegistersReply.class,
                     MonitorGetRegisterInfoEndmark.class);
               holder.monitorGetRegisters.merge(
                     signalTransaction,
                     MONITOR_GET_REGISTERS_REPLY_COMPARATOR);
               break;
            case MonitorGetCPUReportsEnabledRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorGetCPUReportsEnabled = readSignalBlock(
                     signalBlock,
                     MonitorGetCPUReportsEnabledRequest.class,
                     MonitorGetCPUReportsEnabledReply.class);
               break;
            case MonitorGetCPUPriReportsEnabledRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorGetCPUPriReportsEnabled = readSignalBlock(
                     signalBlock,
                     MonitorGetCPUPriReportsEnabledRequest.class,
                     MonitorGetCPUPriReportsEnabledReply.class);
               break;
            case MonitorGetCPUProcessReportsEnabledRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorGetCPUProcessReportsEnabled = readSignalBlock(
                     signalBlock,
                     MonitorGetCPUProcessReportsEnabledRequest.class,
                     MonitorGetCPUProcessReportsEnabledReply.class);
               break;
            case MonitorGetCPUBlockReportsEnabledRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorGetCPUBlockReportsEnabled = readSignalBlock(
                     signalBlock,
                     MonitorGetCPUBlockReportsEnabledRequest.class,
                     MonitorGetCPUBlockReportsEnabledReply.class);
               break;
            case MonitorGetUserReportsEnabledRequest.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     MonitorGetUserReportsEnabledRequest.class,
                     MonitorGetUserReportsEnabledReply.class);
               holder.monitorGetUserReportsEnabled.add(sig);
               break;
            case MonitorGetCPUReportsRequest.SIG_NO:
               holder.monitorCPUReports.add(signalBlock);
               break;
            case MonitorGetCPUPriReportsRequest.SIG_NO:
               holder.monitorCPUPriReports.add(signalBlock);
               break;
            case MonitorGetCPUProcessReportsRequest.SIG_NO:
               holder.monitorCPUProcessReports.add(signalBlock);
               break;
            case MonitorGetCPUBlockReportsRequest.SIG_NO:
               holder.monitorCPUBlockReports.add(signalBlock);
               break;
            case MonitorGetUserReportsRequest.SIG_NO:
               holder.monitorUserReports.add(signalBlock);
               break;
            case MonitorGetTraceRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.monitorEventTrace =
                     parseMonitorEventTraceSignalBlock(signalBlock);
               break;
            case PmInterfaceRequest.SIG_NO:
               // If multiple, use the latest one.
               holder.pmInterface = readSignalBlock(
                     signalBlock,
                     PmInterfaceRequest.class,
                     PmInterfaceReply.class);
               break;
            case PmLoadModuleInstallHandlesRequest.SIG_NO:
               // If multiple, use the latest one.
               // FIXME: Is merging necessary?
               holder.pmLoadModuleInstallHandles = readSignalBlock(
                     signalBlock,
                     PmLoadModuleInstallHandlesRequest.class,
                     PmLoadModuleInstallHandlesReply.class);
               break;
            case PmProgramsRequest.SIG_NO:
               // If multiple, use the latest one.
               // FIXME: Is merging necessary?
               holder.pmPrograms = readSignalBlock(
                     signalBlock,
                     PmProgramsRequest.class,
                     PmProgramsReply.class);
               break;
            case PmLoadModuleInfoRequest.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmLoadModuleInfoRequest.class,
                     PmLoadModuleInfoReply.class);
               addToSignalList(
                     holder.pmLoadModuleInfo,
                     sig,
                     PM_LOAD_MODULE_INFO_REPLY_COMPARATOR);
               break;
            case PmLoadModuleInfoRequest_64.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmLoadModuleInfoRequest_64.class,
                     PmLoadModuleInfoReply_64.class);
               addToSignalList(
                     holder.pmLoadModuleInfo_64,
                     sig,
                     PM_LOAD_MODULE_INFO_REPLY_64_COMPARATOR);
               break;
            case PmLoadModuleSectionInfoRequest.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmLoadModuleSectionInfoRequest.class,
                     PmLoadModuleSectionInfoReply.class);
               addToSignalList(
                     holder.pmLoadModuleSectionInfo,
                     sig,
                     PM_LOAD_MODULE_SECTION_INFO_REPLY_COMPARATOR);
               break;
            case PmLoadModuleSectionInfoRequest_64.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmLoadModuleSectionInfoRequest_64.class,
                     PmLoadModuleSectionInfoReply_64.class);
               addToSignalList(
                     holder.pmLoadModuleSectionInfo_64,
                     sig,
                     PM_LOAD_MODULE_SECTION_INFO_REPLY_64_COMPARATOR);
               break;
            case PmProgramInfoRequest.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmProgramInfoRequest.class,
                     PmProgramInfoReply.class);
               addToSignalList(
                     holder.pmProgramInfo,
                     sig,
                     PM_PROGRAM_INFO_REPLY_COMPARATOR);
               break;
            case PmProgramInfoRequest_64.SIG_NO:
               sig = readSignalBlock(
                     signalBlock,
                     PmProgramInfoRequest_64.class,
                     PmProgramInfoReply_64.class);
               addToSignalList(
                     holder.pmProgramInfo_64,
                     sig,
                     PM_PROGRAM_INFO_REPLY_64_COMPARATOR);
               break;
            default:
               System.err.println(
                     "Unknown request signal block found in dump file: 0x" +
                     Integer.toHexString(reqSigNo).toUpperCase());
               break;
         }
      }

      return holder;
   }

   private MonitorEventTrace parseMonitorEventTraceSignalBlock(
         SignalBlock signalBlock)
      throws IOException
   {
      SignalInputStream in = null;
      long numEvents = 0;
      long numOldEvents = 0;
      long from;
      long to;
      int status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;

      try
      {
         Signal signal;
         MonitorGetTraceEndmark endmark = null;

         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         if (signal instanceof MonitorGetTraceRequest)
         {
            MonitorGetTraceRequest request = (MonitorGetTraceRequest) signal;
            from = 0xFFFFFFFFL & request.from;
            to = 0xFFFFFFFFL & request.to;
         }
         else
         {
            throw new IffException("Invalid request signal in " +
                  "MonitorGetTraceRequest signal block in dump file");
         }

         do
         {
            signal = readSignal(in);
            if (signal instanceof MonitorEventInfoSignal)
            {
               numEvents++;
               if (MonitorEventTrace.isOldEventType(signal))
               {
                  numOldEvents++;
               }
            }
            else if (signal instanceof MonitorGetProcessTraceReply)
            {
               // OK.
            }
            else if (signal instanceof MonitorGetTraceEndmark)
            {
               endmark = (MonitorGetTraceEndmark) signal;
               status = endmark.status;
            }
            else
            {
               System.out.println("Unknown reply signal in " +
                     "MonitorGetTraceRequest signal block in dump file");
            }
         }
         while ((in.available() > 0) && (endmark == null));

         if (endmark == null)
         {
            throw new IffException("Missing MonitorGetTraceEndmark " +
                  "in MonitorGetTraceRequest signal block in dump file");
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

      return new MonitorEventTrace(this,
                                   signalBlock,
                                   numEvents,
                                   numOldEvents,
                                   from,
                                   to,
                                   status);
   }

   private Signal readSignalBlock(SignalBlock signalBlock, Class requestClass)
      throws IOException
   {
      SignalInputStream in = null;
      Signal request;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
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

      return request;
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

   private SignalTransaction readSignalBlock(SignalBlock signalBlock,
                                             Class requestClass,
                                             Class replyClass,
                                             Class endmarkClass)
      throws IOException
   {
      SignalTransaction signalTransaction;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      Signal endmark = null;

      try
      {
         signalTransaction = new SignalTransaction();
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
               signalTransaction.addSignal(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = reply;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && (endmark == null));

         if (endmark == null)
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

      signalTransaction.addScope(new Scope(request, endmark));
      return signalTransaction;
   }

   private SignalTransaction readSignalBlock(SignalBlock signalBlock,
                                             Class requestClass,
                                             Class replyClass,
                                             Class longReplyClass,
                                             Class endmarkClass)
      throws IOException
   {
      SignalTransaction signalTransaction;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      Signal endmark = null;

      try
      {
         signalTransaction = new SignalTransaction();
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
               signalTransaction.addSignal(reply);
            }
            else if (longReplyClass.isInstance(reply))
            {
               signalTransaction.addSignal(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = reply;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && (endmark == null));

         if (endmark == null)
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

      signalTransaction.addScope(new Scope(request, endmark));
      return signalTransaction;
   }

   private Signal readSignal(SignalInputStream in)
      throws IOException
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

   // Add a new signal to a signal list. If there is an existing signal in the
   // signal list with the same identity as the new signal, the older signal
   // is removed.
   private static void addToSignalList(List oldList,
                                       Signal newSignal,
                                       SignalComparator comparator)
   {
      if ((oldList == null) || (newSignal == null) || (comparator == null))
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = oldList.iterator(); i.hasNext();)
      {
         Signal oldSignal = (Signal) i.next();
         if (comparator.equals(oldSignal, newSignal))
         {
            i.remove();
            break;
         }
      }

      oldList.add(newSignal);
   }

   private static int[] createSupportedMonitorSignalsArray(
         SignalBlockList signalBlocks,
         List memoryBlocks)
   {
      List list = new ArrayList();
      int[] sigs;
      int j = 0;

      if (signalBlocks.hasMonitorInterface())
      {
         list.add(new Integer(MonitorInterfaceRequest.SIG_NO));
         list.add(new Integer(MonitorNameRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorConnect())
      {
         list.add(new Integer(MonitorConnectRequest.SIG_NO));
         list.add(new Integer(MonitorDisconnectRequest.SIG_NO));
         list.add(new Integer(MonitorBlockingFlowControlReply.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetSysParam())
      {
         list.add(new Integer(MonitorGetSysParamRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetSegmentInfo())
      {
         list.add(new Integer(MonitorGetSegmentInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetBlockInfo())
      {
         list.add(new Integer(MonitorGetBlockInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetProcessInfo())
      {
         list.add(new Integer(MonitorGetProcessInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetEnvVars())
      {
         list.add(new Integer(MonitorGetEnvVarsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetStackUsage())
      {
         list.add(new Integer(MonitorGetStackUsageRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetSignalQueue())
      {
         list.add(new Integer(MonitorGetSignalQueueRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetPoolInfo())
      {
         list.add(new Integer(MonitorGetPoolInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetPoolInfo64())
      {
         list.add(new Integer(MonitorGetPoolInfo64Request.SIG_NO));         
      }
      if (signalBlocks.hasMonitorGetPoolSignals())
      {
         list.add(new Integer(MonitorGetPoolSignalsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetRegisters())
      {
         list.add(new Integer(MonitorGetRegistersRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetCPUReportsEnabled())
      {
         list.add(new Integer(MonitorGetCPUReportsEnabledRequest.SIG_NO));
         list.add(new Integer(MonitorGetCPUReportsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetCPUPriReportsEnabled())
      {
         list.add(new Integer(MonitorGetCPUPriReportsEnabledRequest.SIG_NO));
         list.add(new Integer(MonitorGetCPUPriReportsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetCPUProcessReportsEnabled())
      {
         list.add(new Integer(MonitorGetCPUProcessReportsEnabledRequest.SIG_NO));
         list.add(new Integer(MonitorGetCPUProcessReportsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetCPUBlockReportsEnabled())
      {
         list.add(new Integer(MonitorGetCPUBlockReportsEnabledRequest.SIG_NO));
         list.add(new Integer(MonitorGetCPUBlockReportsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorGetUserReportsEnabled())
      {
         list.add(new Integer(MonitorGetUserReportsEnabledRequest.SIG_NO));
         list.add(new Integer(MonitorGetUserReportsRequest.SIG_NO));
      }
      if (signalBlocks.hasMonitorEventTrace())
      {
         list.add(new Integer(MonitorGetTraceRequest.SIG_NO));
         list.add(new Integer(MonitorAttachRequest.SIG_NO));
         list.add(new Integer(MonitorDetachRequest.SIG_NO));
      }
      if ((memoryBlocks != null) && (memoryBlocks.size() > 0))
      {
         list.add(new Integer(MonitorGetMemoryRequest.SIG_NO));
         list.add(new Integer(MonitorGetMemory64Request.SIG_NO));
      }

      sigs = new int[list.size()];
      for (Iterator i = list.iterator(); i.hasNext();)
      {
         sigs[j++] = ((Integer) i.next()).intValue();
      }

      return sigs;
   }

   private static int[] createSupportedPmSignalsArray(SignalBlockList signalBlocks)
   {
      List list = new ArrayList();
      int[] sigs;
      int j = 0;

      if (signalBlocks.hasPmInterface())
      {
         list.add(new Integer(PmInterfaceRequest.SIG_NO));
      }
      if (signalBlocks.hasPmLoadModuleInstallHandles())
      {
         list.add(new Integer(PmLoadModuleInstallHandlesRequest.SIG_NO));
      }
      if (signalBlocks.hasPmPrograms())
      {
         list.add(new Integer(PmProgramsRequest.SIG_NO));
      }
      if (signalBlocks.hasPmLoadModuleInfo())
      {
         list.add(new Integer(PmLoadModuleInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasPmLoadModuleInfo_64())
      {
         list.add(new Integer(PmLoadModuleInfoRequest_64.SIG_NO));
      }
      if (signalBlocks.hasPmLoadModuleSectionInfo())
      {
         list.add(new Integer(PmLoadModuleSectionInfoRequest.SIG_NO));
      }
      if (signalBlocks.hasPmLoadModuleSectionInfo_64())
      {
         list.add(new Integer(PmLoadModuleSectionInfoRequest_64.SIG_NO));
      }
      if (signalBlocks.hasPmProgramInfo())
      {
         list.add(new Integer(PmProgramInfoRequest.SIG_NO));
         list.add(new Integer(PmGetProgramPidRequest.SIG_NO));
      }
      if (signalBlocks.hasPmProgramInfo_64())
      {
         list.add(new Integer(PmProgramInfoRequest_64.SIG_NO));
      }

      sigs = new int[list.size()];
      for (Iterator i = list.iterator(); i.hasNext();)
      {
         sigs[j++] = ((Integer) i.next()).intValue();
      }

      return sigs;
   }

   private void inventBlocks()
   {
      for (Iterator i = monitorGetProcessInfoReplies.signalsIterator(); i.hasNext();)
      {
         Signal sig = (Signal) i.next();
         int bid = 0;
         int sid = 0;

         if (sig instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) sig;
            bid = process.bid;
            sid = process.sid;
         }
         else if (sig instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) sig;
            bid = process.bid;
            sid = process.sid;
         }

         if (bid != 0)
         {
            MonitorGetBlockInfoReply block = getBlock(bid);
            if (block == null)
            {
               block = new MonitorGetBlockInfoReply();
               block.bid = bid;
               block.sid = sid;
               block.uid = 0;
               block.supervisor = false;
               block.signalsAttached = 0;
               block.errorHandler = 0;
               block.maxSigSize = 0;
               block.sigPoolId = 0;
               block.stackPoolId = 0;
               block.euId = 0;
               block.name = "<unknown>";
               blockMap.put(new Integer(bid), block);
               monitorGetBlockInfoReplies.addSignal(block);
            }
         }
      }
   }

   private void inventSegments()
   {
      for (Iterator i = monitorGetBlockInfoReplies.signalsIterator(); i.hasNext();)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) i.next();
         Signal sig = getSegment(block.sid);

         if (sig == null)
         {
            MonitorGetSegmentInfoReply segment = new MonitorGetSegmentInfoReply();
            segment.sid = block.sid;
            segment.number = 0;
            segmentMap.put(new Integer(segment.sid), segment);
            monitorGetSegmentInfoReplies.addSignal(segment);
         }
      }

      for (Iterator i = monitorGetPoolInfoReplies.signalsIterator(); i.hasNext();)
      {
         MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) i.next();
         Signal sig = getSegment(pool.sid);

         if (sig == null)
         {
            MonitorGetSegmentInfoReply segment = new MonitorGetSegmentInfoReply();
            segment.sid = pool.sid;
            segment.number = 0;
            segmentMap.put(new Integer(segment.sid), segment);
            monitorGetSegmentInfoReplies.addSignal(segment);
         }
      }
   }

   public boolean isOpen()
   {
      return dumpFile.isOpen();
   }

   public void close()
   {
      dumpFile.close();
   }

   public boolean isBigEndian()
   {
      return bigEndian;
   }

   public List getErrorBlocks() throws IffException
   {
      return dumpFile.getErrorBlocks();
   }

   public List getMemoryBlocks() throws IffException
   {
      return dumpFile.getMemoryBlocks();
   }

   public MonitorInterfaceReply getMonitorInterfaceReply()
   {
      return monitorInterfaceReply;
   }

   public MonitorNameReply getMonitorNameReply()
   {
      return monitorNameReply;
   }

   public MonitorConnectReply getMonitorConnectReply()
   {
      return monitorConnectReply;
   }

   public List getMonitorGetSysParamReplies()
   {
      return Collections.unmodifiableList(
            monitorGetSysParamReplies.getSignals());
   }

   public List getMonitorGetSegmentInfoReplies()
   {
      return Collections.unmodifiableList(
            monitorGetSegmentInfoReplies.getSignals());
   }

   public List getMonitorGetBlockInfoReplies()
   {
      return Collections.unmodifiableList(
            monitorGetBlockInfoReplies.getSignals());
   }

   public List getMonitorGetProcessInfoReplies()
   {
      return Collections.unmodifiableList(
            monitorGetProcessInfoReplies.getSignals());
   }

   public List getMonitorGetEnvVarsReplies()
   {
      return Collections.unmodifiableList(
            monitorGetEnvVarsReplies.getSignals());
   }

   public List getMonitorGetStackUsageReplies()
   {
      return Collections.unmodifiableList(
            monitorGetStackUsageReplies.getSignals());
   }

   public List getMonitorGetSignalQueueReplies()
   {
      return Collections.unmodifiableList(
            monitorGetSignalQueueReplies.getSignals());
   }

   public List getMonitorGetPoolInfoReplies()
   {
      return Collections.unmodifiableList(
            monitorGetPoolInfoReplies.getSignals());
   }

   public List getMonitorGetPoolInfo64Replies()
   {
      return Collections.unmodifiableList(
            monitorGetPoolInfo64Replies.getSignals());
   }

   public List getMonitorGetPoolSignalsReplies()
   {
      return Collections.unmodifiableList(
            monitorGetPoolSignalsReplies.getSignals());
   }

   public List getMonitorGetRegistersReplies()
   {
      return Collections.unmodifiableList(
            monitorGetRegistersReplies.getSignals());
   }

   public MonitorGetCPUReportsEnabledReply getMonitorCPUReportsEnabledReply()
   {
      return monitorGetCPUReportsEnabledReply;
   }

   public MonitorGetCPUPriReportsEnabledReply getMonitorCPUPriReportsEnabledReply()
   {
      return monitorGetCPUPriReportsEnabledReply;
   }

   public MonitorGetCPUProcessReportsEnabledReply getMonitorCPUProcessReportsEnabledReply()
   {
      return monitorGetCPUProcessReportsEnabledReply;
   }

   public MonitorGetCPUBlockReportsEnabledReply getMonitorCPUBlockReportsEnabledReply()
   {
      return monitorGetCPUBlockReportsEnabledReply;
   }

   public List getMonitorUserReportsEnabledReplies()
   {
      return monitorGetUserReportsEnabledReplies;
   }

   public List getMonitorCPUReportSignalBlocks()
   {
      return monitorCPUReportSignalBlocks;
   }

   public List getMonitorCPUPriReportSignalBlocks()
   {
      return monitorCPUPriReportSignalBlocks;
   }

   public List getMonitorCPUProcessReportSignalBlocks()
   {
      return monitorCPUProcessReportSignalBlocks;
   }

   public List getMonitorCPUBlockReportSignalBlocks()
   {
      return monitorCPUBlockReportSignalBlocks;
   }

   public List getMonitorUserReportSignalBlocks()
   {
      return monitorUserReportSignalBlocks;
   }

   public MonitorGetCPUReportsRequest getMonitorGetCPUReportsRequest(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUReportsRequest) readSignalBlock(
            signalBlock,
            MonitorGetCPUReportsRequest.class);
   }

   public MonitorGetCPUReportsReply getMonitorGetCPUReportsReply(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUReportsReply) readSignalBlock(
            signalBlock,
            MonitorGetCPUReportsRequest.class,
            MonitorGetCPUReportsReply.class);
   }

   public MonitorGetCPUPriReportsRequest getMonitorGetCPUPriReportsRequest(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUPriReportsRequest) readSignalBlock(
            signalBlock,
            MonitorGetCPUPriReportsRequest.class);
   }

   public MonitorGetCPUPriReportsReply getMonitorGetCPUPriReportsReply(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUPriReportsReply) readSignalBlock(
            signalBlock,
            MonitorGetCPUPriReportsRequest.class,
            MonitorGetCPUPriReportsReply.class);
   }

   public MonitorGetCPUProcessReportsRequest getMonitorGetCPUProcessReportsRequest(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUProcessReportsRequest) readSignalBlock(
            signalBlock,
            MonitorGetCPUProcessReportsRequest.class);
   }

   public MonitorGetCPUProcessReportsReply getMonitorGetCPUProcessReportsReply(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUProcessReportsReply) readSignalBlock(
            signalBlock,
            MonitorGetCPUProcessReportsRequest.class,
            MonitorGetCPUProcessReportsReply.class);
   }

   public MonitorGetCPUBlockReportsRequest getMonitorGetCPUBlockReportsRequest(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUBlockReportsRequest) readSignalBlock(
            signalBlock,
            MonitorGetCPUBlockReportsRequest.class);
   }

   public MonitorGetCPUBlockReportsReply getMonitorGetCPUBlockReportsReply(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetCPUBlockReportsReply) readSignalBlock(
            signalBlock,
            MonitorGetCPUBlockReportsRequest.class,
            MonitorGetCPUBlockReportsReply.class);
   }

   public MonitorGetUserReportsRequest getMonitorGetUserReportsRequest(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetUserReportsRequest) readSignalBlock(
            signalBlock,
            MonitorGetUserReportsRequest.class);
   }

   public MonitorGetUserReportsReply getMonitorGetUserReportsReply(
         SignalBlock signalBlock)
      throws IOException
   {
      return (MonitorGetUserReportsReply) readSignalBlock(
            signalBlock,
            MonitorGetUserReportsRequest.class,
            MonitorGetUserReportsReply.class);
   }

   public MonitorEventTrace getMonitorEventTrace()
   {
      return monitorEventTrace;
   }

   public PmInterfaceReply getPmInterfaceReply()
   {
      return pmInterfaceReply;
   }

   public PmLoadModuleInstallHandlesReply getPmLoadModuleInstallHandlesReply()
   {
      return pmLoadModuleInstallHandlesReply;
   }

   public PmProgramsReply getPmProgramsReply()
   {
      return pmProgramsReply;
   }

   public List getPmLoadModuleInfoReplies()
   {
      return pmLoadModuleInfoReplies;
   }

   public List getPmLoadModuleInfoReplies_64()
   {
      return pmLoadModuleInfoReplies_64;
   }

   public List getPmLoadModuleSectionInfoReplies()
   {
      return pmLoadModuleSectionInfoReplies;
   }

   public List getPmLoadModuleSectionInfoReplies_64()
   {
      return pmLoadModuleSectionInfoReplies_64;
   }

   public List getPmProgramInfoReplies()
   {
      return pmProgramInfoReplies;
   }

   public List getPmProgramInfoReplies_64()
   {
      return pmProgramInfoReplies_64;
   }

   public boolean segmentExists(int sid)
   {
      return (segmentMap.get(new Integer(sid)) != null);
   }

   public boolean poolExists(int pid)
   {
      return (poolMap.get(new Integer(pid)) != null);
   }

   public boolean pool64Exists(int pid)
   {
      return (pool64Map.get(new Integer(pid)) != null);
   }
   
   public boolean blockExists(int bid)
   {
      return (blockMap.get(new Integer(bid)) != null);
   }

   public boolean processExists(int pid)
   {
      return (processMap.get(new Integer(pid)) != null);
   }

   public boolean loadModuleExists(String installHandle)
   {
      return (loadModuleMap.get(installHandle) != null);
   }

   public boolean loadModule_64Exists(String installHandle)
   {
      return (loadModule_64Map.get(installHandle) != null);
   }

   public boolean loadModuleSectionExists(String installHandle)
   {
      return (loadModuleSectionMap.get(installHandle) != null);
   }

   public boolean loadModuleSection_64Exists(String installHandle)
   {
      return (loadModuleSection_64Map.get(installHandle) != null);
   }

   public boolean programExists(int progpid)
   {
      return (programMap.get(new Integer(progpid)) != null);
   }

   public boolean program_64Exists(int progpid)
   {
      return (program_64Map.get(new Integer(progpid)) != null);
   }

   public Signal getSegment(int sid)
   {
      return (Signal) segmentMap.get(new Integer(sid));
   }

   public Signal getPool(int pid)
   {
      return (Signal) poolMap.get(new Integer(pid));
   }

   public Signal getPool64(int pid)
   {
      return (Signal) pool64Map.get(new Integer(pid));
   }

   public MonitorGetBlockInfoReply getBlock(int bid)
   {
      return (MonitorGetBlockInfoReply) blockMap.get(new Integer(bid));
   }

   public Signal getProcess(int pid)
   {
      return (Signal) processMap.get(new Integer(pid));
   }

   public PmLoadModuleInfoReply getLoadModule(String installHandle)
   {
      return (PmLoadModuleInfoReply) loadModuleMap.get(installHandle);
   }

   public PmLoadModuleInfoReply_64 getLoadModule_64(String installHandle)
   {
      return (PmLoadModuleInfoReply_64) loadModule_64Map.get(installHandle);
   }

   public PmLoadModuleSectionInfoReply getLoadModuleSection(String installHandle)
   {
      return (PmLoadModuleSectionInfoReply) loadModuleSectionMap.get(installHandle);
   }

   public PmLoadModuleSectionInfoReply_64 getLoadModuleSection_64(String installHandle)
   {
      return (PmLoadModuleSectionInfoReply_64) loadModuleSection_64Map.get(installHandle);
   }

   public PmProgramInfoReply getProgram(int progpid)
   {
      return (PmProgramInfoReply) programMap.get(new Integer(progpid));
   }

   public PmProgramInfoReply_64 getProgram_64(int progpid)
   {
      return (PmProgramInfoReply_64) program_64Map.get(new Integer(progpid));
   }

   public PmProgramInfoReply getProgramByPid(int pid)
   {
      PmProgramInfoReply program = null;
      Signal sig;

      // XXX: This method assumes that the OSE program manager internally
      // associates programs with segments. This is how the OSE program
      // manager currently works and has always worked but it could change
      // in the future.
      if ((sig = getSegment(pid)) != null)
      {
         int sid;
         if (sig instanceof MonitorGetSegmentInfoReply)
         {
            MonitorGetSegmentInfoReply segment =
               (MonitorGetSegmentInfoReply) sig;
            sid = segment.sid;
         }
         else if (sig instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply segment =
               (MonitorGetSegmentInfoLongReply) sig;
            sid = segment.sid;
         }
         else
         {
            throw new IllegalStateException();
         }
         for (Iterator i = pmProgramInfoReplies.iterator(); i.hasNext();)
         {
            PmProgramInfoReply prog = (PmProgramInfoReply) i.next();
            if (prog.segpid == sid)
            {
               program = prog;
               break;
            }
         }
      }
      else if ((sig = getBlock(pid)) != null)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) sig;
         for (Iterator i = pmProgramInfoReplies.iterator(); i.hasNext();)
         {
            PmProgramInfoReply prog = (PmProgramInfoReply) i.next();
            if (prog.segpid == block.sid)
            {
               program = prog;
               break;
            }
         }
      }
      else if ((sig = getProcess(pid)) != null)
      {
         int processSid;
         if (sig instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) sig;
            processSid = process.sid;
         }
         else if (sig instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) sig;
            processSid = process.sid;
         }
         else
         {
            throw new IllegalStateException();
         }
         for (Iterator i = pmProgramInfoReplies.iterator(); i.hasNext();)
         {
            PmProgramInfoReply prog = (PmProgramInfoReply) i.next();
            if (prog.segpid == processSid)
            {
               program = prog;
               break;
            }
         }
      }

      return program;
   }

   public PmProgramInfoReply_64 getProgram_64ByPid(int pid)
   {
      PmProgramInfoReply_64 program_64 = null;
      Signal sig;

      // XXX: This method assumes that the OSE program manager internally
      // associates programs with segments. This is how the OSE program
      // manager currently works and has always worked but it could change
      // in the future.
      if ((sig = getSegment(pid)) != null)
      {
         int sid;
         if (sig instanceof MonitorGetSegmentInfoReply)
         {
            MonitorGetSegmentInfoReply segment =
               (MonitorGetSegmentInfoReply) sig;
            sid = segment.sid;
         }
         else if (sig instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply segment =
               (MonitorGetSegmentInfoLongReply) sig;
            sid = segment.sid;
         }
         else
         {
            throw new IllegalStateException();
         }
         for (Iterator i = pmProgramInfoReplies_64.iterator(); i.hasNext();)
         {
            PmProgramInfoReply_64 prog_64 = (PmProgramInfoReply_64) i.next();
            if (prog_64.segpid == sid)
            {
               program_64 = prog_64;
               break;
            }
         }
      }
      else if ((sig = getBlock(pid)) != null)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) sig;
         for (Iterator i = pmProgramInfoReplies_64.iterator(); i.hasNext();)
         {
            PmProgramInfoReply_64 prog_64 = (PmProgramInfoReply_64) i.next();
            if (prog_64.segpid == block.sid)
            {
               program_64 = prog_64;
               break;
            }
         }
      }
      else if ((sig = getProcess(pid)) != null)
      {
         int processSid;
         if (sig instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) sig;
            processSid = process.sid;
         }
         else if (sig instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) sig;
            processSid = process.sid;
         }
         else
         {
            throw new IllegalStateException();
         }
         for (Iterator i = pmProgramInfoReplies_64.iterator(); i.hasNext();)
         {
            PmProgramInfoReply_64 prog_64 = (PmProgramInfoReply_64) i.next();
            if (prog_64.segpid == processSid)
            {
               program_64 = prog_64;
               break;
            }
         }
      }

      return program_64;
   }

   public int validateMonitorGetInfoRequest(MonitorGetInfoRequest request)
   {
      int status = MonitorStatus.MONITOR_STATUS_OK;

      if ((request.level != 0) && (request.level != 1))
      {
         return MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
      }

      switch (request.scopeType)
      {
         case Monitor.MONITOR_SCOPE_SYSTEM:
            // OK.
            break;
         case Monitor.MONITOR_SCOPE_SEGMENT_ID:
            if (!segmentExists(request.scopeId))
            {
               status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
            }
            break;
         case Monitor.MONITOR_SCOPE_BLOCK_ID:
            if (!blockExists(request.scopeId))
            {
               status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
            }
            break;
         case Monitor.MONITOR_SCOPE_ID:
            switch (request.getSigNo())
            {
               case MonitorGetSysParamRequest.SIG_NO:
                  // OK.
                  break;
               case MonitorGetSegmentInfoRequest.SIG_NO:
                  if (!segmentExists(request.scopeId))
                  {
                     status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  }
                  break;
               case MonitorGetBlockInfoRequest.SIG_NO:
                  if (!blockExists(request.scopeId))
                  {
                     status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  }
                  break;
               case MonitorGetEnvVarsRequest.SIG_NO:
                  if (!blockExists(request.scopeId) &&
                      !processExists(request.scopeId))
                  {
                     status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  }
                  break;
               case MonitorGetProcessInfoRequest.SIG_NO:
                  // Fall through.
               case MonitorGetStackUsageRequest.SIG_NO:
                  // Fall through.
               case MonitorGetSignalQueueRequest.SIG_NO:
                  if (!processExists(request.scopeId))
                  {
                     status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  }
                  break;
               case MonitorGetPoolInfoRequest.SIG_NO:
                  // Fall through.
               case MonitorGetPoolInfo64Request.SIG_NO:
                   // Fall through.
               case MonitorGetPoolSignalsRequest.SIG_NO:
                  if (!poolExists(request.scopeId))
                  {
                     status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  }
                  break;
               default:
                  status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  break;
            }
            break;
         default:
            status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
            break;
      }

      return status;
   }

   public int findMonitorGetInfoEndmarkStatus(MonitorGetInfoRequest request)
   {
      SignalTransaction signalTransaction;
      List scopes;

      // If the requested scope is a specific object then we return OK since we
      // have already verified that it exists and that is all we ask for.
      // FIXME: Is this correct?
      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         return MonitorStatus.MONITOR_STATUS_OK;
      }

      if (request instanceof MonitorGetSysParamRequest)
      {
         signalTransaction = monitorGetSysParamReplies;
      }
      else if (request instanceof MonitorGetSegmentInfoRequest)
      {
         signalTransaction = monitorGetSegmentInfoReplies;
      }
      else if (request instanceof MonitorGetBlockInfoRequest)
      {
         signalTransaction = monitorGetBlockInfoReplies;
      }
      else if (request instanceof MonitorGetProcessInfoRequest)
      {
         signalTransaction = monitorGetProcessInfoReplies;
      }
      else if (request instanceof MonitorGetEnvVarsRequest)
      {
         signalTransaction = monitorGetEnvVarsReplies;
      }
      else if (request instanceof MonitorGetStackUsageRequest)
      {
         signalTransaction = monitorGetStackUsageReplies;
      }
      else if (request instanceof MonitorGetSignalQueueRequest)
      {
         signalTransaction = monitorGetSignalQueueReplies;
      }
      else if (request instanceof MonitorGetPoolInfoRequest)
      {
         signalTransaction = monitorGetPoolInfoReplies;
      }
      else if (request instanceof MonitorGetPoolInfo64Request)
      {
         signalTransaction = monitorGetPoolInfo64Replies;
      }
      else if (request instanceof MonitorGetPoolSignalsRequest)
      {
         signalTransaction = monitorGetPoolSignalsReplies;
      }
      else
      {
         throw new IllegalArgumentException();
      }
      scopes = signalTransaction.getScopes();

      // Starting from the latest scope, if there is a scope in the signal
      // transaction that contains the requested scope and has status OK then
      // we return OK.
      for (int i = scopes.size() - 1; i >= 0; i--)
      {
         Scope scope = (Scope) scopes.get(i);
         if (scope.contains(request) &&
             (scope.getStatus() == MonitorStatus.MONITOR_STATUS_OK))
         {
            return MonitorStatus.MONITOR_STATUS_OK;
         }
      }

      // Starting from the latest scope, if there is a scope in the signal
      // transaction that contains the requested scope and has an error status
      // then we return that error status.
      for (int i = scopes.size() - 1; i >= 0; i--)
      {
         Scope scope = (Scope) scopes.get(i);
         if (scope.contains(request) &&
             (scope.getStatus() != MonitorStatus.MONITOR_STATUS_OK))
         {
            return scope.getStatus();
         }
      }

      // Starting from the latest scope, if there is a scope in the signal
      // transaction that is within the requested scope and has an error status
      // then we return that error status.
      for (int i = scopes.size() - 1; i >= 0; i--)
      {
         Scope scope = (Scope) scopes.get(i);
         if (scope.within(request) &&
             (scope.getStatus() != MonitorStatus.MONITOR_STATUS_OK))
         {
            return scope.getStatus();
         }
      }

      // Otherwise, we return OK since the requested scope is not a subset nor a
      // superset of any scope in the signal transaction with an error status.
      return MonitorStatus.MONITOR_STATUS_OK;
   }

   private static class SignalBlockList
   {
      Signal monitorInterface;
      Signal monitorName;
      Signal monitorConnect;
      SignalTransaction monitorGetSysParam = new SignalTransaction();
      SignalTransaction monitoGetSegmentInfo = new SignalTransaction();
      SignalTransaction monitorGetBlockInfo = new SignalTransaction();
      SignalTransaction monitorGetProcessInfo = new SignalTransaction();
      SignalTransaction monitorGetEnvVars = new SignalTransaction();
      SignalTransaction monitorGetStackUsage = new SignalTransaction();
      SignalTransaction monitorGetSignalQueue = new SignalTransaction();
      SignalTransaction monitorGetPoolInfo = new SignalTransaction();
      SignalTransaction monitorGetPoolInfo64 = new SignalTransaction();
      SignalTransaction monitorGetPoolSignals = new SignalTransaction();
      SignalTransaction monitorGetRegisters = new SignalTransaction();
      Signal monitorGetCPUReportsEnabled;
      Signal monitorGetCPUPriReportsEnabled;
      Signal monitorGetCPUProcessReportsEnabled;
      Signal monitorGetCPUBlockReportsEnabled;
      List monitorGetUserReportsEnabled = new ArrayList();
      List monitorCPUReports = new ArrayList();
      List monitorCPUPriReports = new ArrayList();
      List monitorCPUProcessReports = new ArrayList();
      List monitorCPUBlockReports = new ArrayList();
      List monitorUserReports = new ArrayList();
      MonitorEventTrace monitorEventTrace;
      Signal pmInterface;
      Signal pmLoadModuleInstallHandles;
      Signal pmPrograms;
      List pmLoadModuleInfo = new ArrayList();
      List pmLoadModuleInfo_64 = new ArrayList();
      List pmLoadModuleSectionInfo = new ArrayList();
      List pmLoadModuleSectionInfo_64 = new ArrayList();
      List pmProgramInfo = new ArrayList();
      List pmProgramInfo_64 = new ArrayList();

      boolean hasMonitorInterface()
      {
         return (monitorInterface != null);
      }

      boolean hasMonitorName()
      {
         return (monitorName != null);
      }

      boolean hasMonitorConnect()
      {
         return (monitorConnect != null);
      }

      boolean hasMonitorGetSysParam()
      {
         return (!monitorGetSysParam.isEmpty());
      }

      boolean hasMonitorGetSegmentInfo()
      {
         return (!monitoGetSegmentInfo.isEmpty());
      }

      boolean hasMonitorGetBlockInfo()
      {
         return (!monitorGetBlockInfo.isEmpty());
      }

      boolean hasMonitorGetProcessInfo()
      {
         return (!monitorGetProcessInfo.isEmpty());
      }

      boolean hasMonitorGetEnvVars()
      {
         return (!monitorGetEnvVars.isEmpty());
      }

      boolean hasMonitorGetStackUsage()
      {
         return (!monitorGetStackUsage.isEmpty());
      }

      boolean hasMonitorGetSignalQueue()
      {
         return (!monitorGetSignalQueue.isEmpty());
      }

      boolean hasMonitorGetPoolInfo()
      {
         return (!monitorGetPoolInfo.isEmpty());
      }

      boolean hasMonitorGetPoolInfo64()
      {
         return (!monitorGetPoolInfo64.isEmpty());       
      }
      
      boolean hasMonitorGetPoolSignals()
      {
         return (!monitorGetPoolSignals.isEmpty());
      }

      boolean hasMonitorGetRegisters()
      {
         return (!monitorGetRegisters.isEmpty());
      }

      boolean hasMonitorGetCPUReportsEnabled()
      {
         return (monitorGetCPUReportsEnabled != null);
      }

      boolean hasMonitorGetCPUPriReportsEnabled()
      {
         return (monitorGetCPUPriReportsEnabled != null);
      }

      boolean hasMonitorGetCPUProcessReportsEnabled()
      {
         return (monitorGetCPUProcessReportsEnabled != null);
      }

      boolean hasMonitorGetCPUBlockReportsEnabled()
      {
         return (monitorGetCPUBlockReportsEnabled != null);
      }

      boolean hasMonitorGetUserReportsEnabled()
      {
         return (!monitorGetUserReportsEnabled.isEmpty());
      }

      boolean hasMonitorCPUReports()
      {
         return (!monitorCPUReports.isEmpty());
      }

      boolean hasMonitorCPUPriReports()
      {
         return (!monitorCPUPriReports.isEmpty());
      }

      boolean hasMonitorCPUProcessReports()
      {
         return (!monitorCPUProcessReports.isEmpty());
      }

      boolean hasMonitorCPUBlockReports()
      {
         return (!monitorCPUBlockReports.isEmpty());
      }

      boolean hasMonitorUserReports()
      {
         return (!monitorUserReports.isEmpty());
      }

      boolean hasMonitorEventTrace()
      {
         return (monitorEventTrace != null);
      }

      boolean hasPmInterface()
      {
         return (pmInterface != null);
      }

      boolean hasPmLoadModuleInstallHandles()
      {
         return (pmLoadModuleInstallHandles != null);
      }

      boolean hasPmPrograms()
      {
         return (pmPrograms != null);
      }

      boolean hasPmLoadModuleInfo()
      {
         return (!pmLoadModuleInfo.isEmpty());
      }

      boolean hasPmLoadModuleInfo_64()
      {
         return (!pmLoadModuleInfo_64.isEmpty());
      }

      boolean hasPmLoadModuleSectionInfo()
      {
         return (!pmLoadModuleSectionInfo.isEmpty());
      }

      boolean hasPmLoadModuleSectionInfo_64()
      {
         return (!pmLoadModuleSectionInfo_64.isEmpty());
      }

      boolean hasPmProgramInfo()
      {
         return (!pmProgramInfo.isEmpty());
      }

      boolean hasPmProgramInfo_64()
      {
         return (!pmProgramInfo_64.isEmpty());
      }
   }

   private static class SignalTransaction
   {
      private final List signals = new ArrayList();
      private final List scopes = new ArrayList();

      public boolean isEmpty()
      {
         return (signals.isEmpty() && scopes.isEmpty());
      }

      public List getSignals()
      {
         return signals;
      }

      public List getScopes()
      {
         return scopes;
      }

      public Iterator signalsIterator()
      {
         return signals.iterator();
      }

      public Iterator scopesIterator()
      {
         return scopes.iterator();
      }

      void addSignal(Signal signal)
      {
         signals.add(signal);
      }

      void addScope(Scope scope)
      {
         scopes.add(scope);
      }

      // Merge this signal transaction with another signal transaction.
      // The merging is done by keeping all signals in the other signal
      // transaction and only those signals in this signal transaction
      // that does not exist in the other signal transaction.
      void merge(SignalTransaction other, SignalComparator comparator)
      {
         List uniqOldSignals;

         if ((other == null) || (comparator == null))
         {
            throw new IllegalArgumentException();
         }

         if (other.isEmpty())
         {
            return;
         }

         uniqOldSignals = new ArrayList();
         for (Iterator i = signals.iterator(); i.hasNext();)
         {
            Signal oldSig = (Signal) i.next();
            boolean found = false;

            for (Iterator j = other.signals.iterator(); j.hasNext();)
            {
               Signal newSig = (Signal) j.next();
               if (comparator.equals(oldSig, newSig))
               {
                  found = true;
                  break;
               }
            }

            if (!found)
            {
               uniqOldSignals.add(oldSig);
            }
         }

         signals.clear();
         signals.addAll(uniqOldSignals);
         signals.addAll(other.signals);
         if (uniqOldSignals.isEmpty())
         {
            scopes.clear();
         }
         scopes.addAll(other.scopes);
      }
   }

   private class Scope
   {
      private static final int SCOPE_SYSTEM = 0;
      private static final int SCOPE_SEGMENT = 1;
      private static final int SCOPE_POOL = 2;
      private static final int SCOPE_BLOCK = 3;
      private static final int SCOPE_PROCESS = 4;

      private final int scopeType;
      private final int scopeId;
      private final int status;

      Scope(int scopeType, int scopeId, int status)
      {
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.status = status;
      }

      Scope(Signal request, Signal endmark)
      {
         if ((request instanceof MonitorGetInfoRequest) &&
             (endmark instanceof MonitorGetInfoEndmark))
         {
            MonitorGetInfoRequest req = (MonitorGetInfoRequest) request;
            MonitorGetInfoEndmark end = (MonitorGetInfoEndmark) endmark;
            this.scopeType = req.scopeType;
            this.scopeId = req.scopeId;
            this.status = end.status;
         }
         else if ((request instanceof MonitorGetRegisterInfoRequest) &&
                  (endmark instanceof MonitorGetRegisterInfoEndmark))
         {
            MonitorGetRegisterInfoRequest req =
               (MonitorGetRegisterInfoRequest) request;
            MonitorGetRegisterInfoEndmark end =
               (MonitorGetRegisterInfoEndmark) endmark;
            this.scopeType = req.scopeType;
            this.scopeId = req.scopeId;
            this.status = end.status;
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }

      public int getScopeType()
      {
         return scopeType;
      }

      public int getScopeId()
      {
         return scopeId;
      }

      public int getStatus()
      {
         return status;
      }

      // Determine whether this scope contains (is equal to or a superset of)
      // the scope of the specified request.
      public boolean contains(MonitorGetInfoRequest request)
      {
         int internalScopeType = getInternalScopeType(request);

         switch (scopeType)
         {
            case Monitor.MONITOR_SCOPE_SYSTEM:
               return true;
            case Monitor.MONITOR_SCOPE_SEGMENT_ID:
               if (internalScopeType == SCOPE_SEGMENT)
               {
                  // Return true if request scope is this segment.
                  return (request.scopeId == scopeId);
               }
               else if (internalScopeType == SCOPE_POOL)
               {
                  // Return true if request scope is a pool inside this segment.
                  MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) getPool(request.scopeId);
                  MonitorGetPoolInfo64Reply pool64 = (MonitorGetPoolInfo64Reply) getPool64(request.scopeId);
                  return (((pool != null) && (pool.sid == scopeId)) ||
                          ((pool64 != null) && (pool64.sid == scopeId)));
               }
               else if (internalScopeType == SCOPE_BLOCK)
               {
                  // Return true if request scope is a block inside this segment.
                  MonitorGetBlockInfoReply block = getBlock(request.scopeId);
                  return ((block != null) && (block.sid == scopeId));
               }
               else if (internalScopeType == SCOPE_PROCESS)
               {
                  // Return true if request scope is a process inside this segment.
                  Signal sig = getProcess(request.scopeId);
                  if (sig instanceof MonitorGetProcessInfoReply)
                  {
                     MonitorGetProcessInfoReply process =
                        (MonitorGetProcessInfoReply) sig;
                     return (process.sid == scopeId);
                  }
                  else if (sig instanceof MonitorGetProcessInfoLongReply)
                  {
                     MonitorGetProcessInfoLongReply process =
                        (MonitorGetProcessInfoLongReply) sig;
                     return (process.sid == scopeId);
                  }
                  else
                  {
                     return false;
                  }
               }
               else
               {
                  return false;
               }
            case Monitor.MONITOR_SCOPE_BLOCK_ID:
               if (internalScopeType == SCOPE_BLOCK)
               {
                  // Return true if request scope is this block.
                  return (request.scopeId == scopeId);
               }
               else if (internalScopeType == SCOPE_PROCESS)
               {
                  // Return true if request scope is a process inside this block.
                  Signal sig = (Signal) getProcess(request.scopeId);
                  if (sig instanceof MonitorGetProcessInfoReply)
                  {
                     MonitorGetProcessInfoReply process =
                        (MonitorGetProcessInfoReply) sig;
                     return (process.bid == scopeId);
                  }
                  else if (sig instanceof MonitorGetProcessInfoLongReply)
                  {
                     MonitorGetProcessInfoLongReply process =
                        (MonitorGetProcessInfoLongReply) sig;
                     return (process.bid == scopeId);
                  }
                  else
                  {
                     return false;
                  }
               }
               else
               {
                  return false;
               }
            case Monitor.MONITOR_SCOPE_ID:
               // Specific segment, pool, block, or process.
               if (segmentExists(scopeId))
               {
                  // Return true if request scope is this segment, or a pool,
                  // block, or process inside this segment.
                  Scope newScope = new Scope(Monitor.MONITOR_SCOPE_SEGMENT_ID,
                                             scopeId,
                                             status);
                  return newScope.contains(request);
               }
               else if (poolExists(scopeId) || pool64Exists(scopeId))
               {
                  // Return true if request scope is this pool.
                  return ((internalScopeType == SCOPE_POOL) &&
                          (request.scopeId == scopeId));
               }
               else if (blockExists(scopeId))
               {
                  // Return true if request scope is this block or a process
                  // inside this block.
                  Scope newScope = new Scope(Monitor.MONITOR_SCOPE_BLOCK_ID,
                                             scopeId,
                                             status);
                  return newScope.contains(request);
               }
               else if (processExists(scopeId))
               {
                  // Return true if request scope is this process.
                  return ((internalScopeType == SCOPE_PROCESS) &&
                          (request.scopeId == scopeId));
               }
               else
               {
                  return false;
               }
            default:
               return false;
         }
      }

      // Determine whether this scope is within (equal to or a subset of) the
      // scope of the specified request.
      public boolean within(MonitorGetInfoRequest request)
      {
         switch (request.scopeType)
         {
            case Monitor.MONITOR_SCOPE_SYSTEM:
               return true;
            case Monitor.MONITOR_SCOPE_SEGMENT_ID:
               if (scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
               {
                  // Return true if this segment is the request segment.
                  return (scopeId == request.scopeId);
               }
               else if (scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
               {
                  // Return true if this block is inside the request segment.
                  MonitorGetBlockInfoReply block = getBlock(scopeId);
                  return ((block != null) && (block.sid == request.scopeId));
               }
               else if (scopeType == Monitor.MONITOR_SCOPE_ID)
               {
                  // Return true if this scope is the request segment, or a
                  // pool, block, or process inside the request segment.
                  Signal sig;
                  if ((sig = getSegment(scopeId)) != null)
                  {
                     if (sig instanceof MonitorGetSegmentInfoReply)
                     {
                        MonitorGetSegmentInfoReply segment =
                           (MonitorGetSegmentInfoReply) sig;
                        return (segment.sid == request.scopeId);
                     }
                     else if (sig instanceof MonitorGetSegmentInfoLongReply)
                     {
                        MonitorGetSegmentInfoLongReply segment =
                           (MonitorGetSegmentInfoLongReply) sig;
                        return (segment.sid == request.scopeId);
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else if ((sig = getPool(scopeId)) != null)
                  {
                     MonitorGetPoolInfoReply pool =
                        (MonitorGetPoolInfoReply) sig;
                     return (pool.sid == request.scopeId);
                  }
                  else if ((sig = getPool64(scopeId)) != null)
                  {
                     MonitorGetPoolInfo64Reply pool64 =
                        (MonitorGetPoolInfo64Reply) sig;
                     return (pool64.sid == request.scopeId);
                  }
                  else if ((sig = getBlock(scopeId)) != null)
                  {
                     MonitorGetBlockInfoReply block =
                        (MonitorGetBlockInfoReply) sig;
                     return (block.sid == request.scopeId);
                  }
                  else if ((sig = getProcess(scopeId)) != null)
                  {
                     if (sig instanceof MonitorGetProcessInfoReply)
                     {
                        MonitorGetProcessInfoReply process =
                           (MonitorGetProcessInfoReply) sig;
                        return (process.sid == request.scopeId);
                     }
                     else if (sig instanceof MonitorGetProcessInfoLongReply)
                     {
                        MonitorGetProcessInfoLongReply process =
                           (MonitorGetProcessInfoLongReply) sig;
                        return (process.sid == request.scopeId);
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else
                  {
                     return false;
                  }
               }
               else
               {
                  return false;
               }
            case Monitor.MONITOR_SCOPE_BLOCK_ID:
               if (scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
               {
                  // Return true if this block is the request block.
                  return (scopeId == request.scopeId);
               }
               else if (scopeType == Monitor.MONITOR_SCOPE_ID)
               {
                  // Return true if this scope is the request block or a process
                  // inside the request block.
                  Signal sig;
                  if ((sig = getBlock(scopeId)) != null)
                  {
                     MonitorGetBlockInfoReply block =
                        (MonitorGetBlockInfoReply) sig;
                     return (block.bid == request.scopeId);
                  }
                  else if ((sig = getProcess(scopeId)) != null)
                  {
                     if (sig instanceof MonitorGetProcessInfoReply)
                     {
                        MonitorGetProcessInfoReply process =
                           (MonitorGetProcessInfoReply) sig;
                        return (process.bid == request.scopeId);
                     }
                     else if (sig instanceof MonitorGetProcessInfoLongReply)
                     {
                        MonitorGetProcessInfoLongReply process =
                           (MonitorGetProcessInfoLongReply) sig;
                        return (process.bid == request.scopeId);
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else
                  {
                     return false;
                  }
               }
               else
               {
                  return false;
               }
            case Monitor.MONITOR_SCOPE_ID:
               int internalScopeType = getInternalScopeType(request);
               if (internalScopeType == SCOPE_SEGMENT)
               {
                  // Return true if this scope is the request segment, or a
                  // pool, block, or process inside the request segment.
                  if (scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
                  {
                     return (scopeId == request.scopeId);
                  }
                  else if (scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
                  {
                     MonitorGetBlockInfoReply block = getBlock(scopeId);
                     return ((block != null) && (block.sid == request.scopeId));
                  }
                  else if (scopeType == Monitor.MONITOR_SCOPE_ID)
                  {
                     Signal sig;
                     if ((sig = getSegment(scopeId)) != null)
                     {
                        if (sig instanceof MonitorGetSegmentInfoReply)
                        {
                           MonitorGetSegmentInfoReply segment =
                              (MonitorGetSegmentInfoReply) sig;
                           return (segment.sid == request.scopeId);
                        }
                        else if (sig instanceof MonitorGetSegmentInfoLongReply)
                        {
                           MonitorGetSegmentInfoLongReply segment =
                              (MonitorGetSegmentInfoLongReply) sig;
                           return (segment.sid == request.scopeId);
                        }
                        else
                        {
                           return false;
                        }
                     }
                     else if ((sig = getPool(scopeId)) != null)
                     {
                        MonitorGetPoolInfoReply pool =
                           (MonitorGetPoolInfoReply) sig;
                        return (pool.sid == request.scopeId);
                     }
                     else if ((sig = getPool64(scopeId)) != null)
                     {
                        MonitorGetPoolInfo64Reply pool64 =
                           (MonitorGetPoolInfo64Reply) sig;
                        return (pool64.sid == request.scopeId);
                     }
                     else if ((sig = getBlock(scopeId)) != null)
                     {
                        MonitorGetBlockInfoReply block =
                           (MonitorGetBlockInfoReply) sig;
                        return (block.sid == request.scopeId);
                     }
                     else if ((sig = getProcess(scopeId)) != null)
                     {
                        if (sig instanceof MonitorGetProcessInfoReply)
                        {
                           MonitorGetProcessInfoReply process =
                              (MonitorGetProcessInfoReply) sig;
                           return (process.sid == request.scopeId);
                        }
                        else if (sig instanceof MonitorGetProcessInfoLongReply)
                        {
                           MonitorGetProcessInfoLongReply process =
                              (MonitorGetProcessInfoLongReply) sig;
                           return (process.sid == request.scopeId);
                        }
                        else
                        {
                           return false;
                        }
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else
                  {
                     return false;
                  }
               }
               else if (internalScopeType == SCOPE_POOL)
               {
                  // Return true if this scope is the request pool.
                  if (scopeType == Monitor.MONITOR_SCOPE_ID)
                  {
                     MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) getPool(scopeId);
                     MonitorGetPoolInfo64Reply pool64 = (MonitorGetPoolInfo64Reply) getPool64(scopeId);
                     return (((pool != null) && (pool.pid == request.scopeId)) ||
                             ((pool64 != null) && (pool64.pid == request.scopeId)));
                  }
                  else
                  {
                     return false;
                  }
               }
               else if (internalScopeType == SCOPE_BLOCK)
               {
                  // Return true if this scope is the request block or a process
                  // inside the request block.
                  if (scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
                  {
                     return (scopeId == request.scopeId);
                  }
                  else if (scopeType == Monitor.MONITOR_SCOPE_ID)
                  {
                     Signal sig;
                     if ((sig = getBlock(scopeId)) != null)
                     {
                        MonitorGetBlockInfoReply block =
                           (MonitorGetBlockInfoReply) sig;
                        return (block.bid == request.scopeId);
                     }
                     else if ((sig = getProcess(scopeId)) != null)
                     {
                        if (sig instanceof MonitorGetProcessInfoReply)
                        {
                           MonitorGetProcessInfoReply process =
                              (MonitorGetProcessInfoReply) sig;
                           return (process.bid == request.scopeId);
                        }
                        else if (sig instanceof MonitorGetProcessInfoLongReply)
                        {
                           MonitorGetProcessInfoLongReply process =
                              (MonitorGetProcessInfoLongReply) sig;
                           return (process.bid == request.scopeId);
                        }
                        else
                        {
                           return false;
                        }
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else
                  {
                     return false;
                  }
               }
               else if (internalScopeType == SCOPE_PROCESS)
               {
                  // Return true if this scope is the request process.
                  if (scopeType == Monitor.MONITOR_SCOPE_ID)
                  {
                     Signal sig = getProcess(scopeId);
                     if (sig instanceof MonitorGetProcessInfoReply)
                     {
                        MonitorGetProcessInfoReply process =
                           (MonitorGetProcessInfoReply) sig;
                        return (process.pid == request.scopeId);
                     }
                     else if (sig instanceof MonitorGetProcessInfoLongReply)
                     {
                        MonitorGetProcessInfoLongReply process =
                           (MonitorGetProcessInfoLongReply) sig;
                        return (process.pid == request.scopeId);
                     }
                     else
                     {
                        return false;
                     }
                  }
                  else
                  {
                     return false;
                  }
               }
               else
               {
                  return false;
               }
            default:
               return false;
         }
      }

      private int getInternalScopeType(MonitorGetInfoRequest request)
      {
         if (request instanceof MonitorGetSysParamRequest)
         {
            return Scope.SCOPE_SYSTEM;
         }
         else if (request instanceof MonitorGetSegmentInfoRequest)
         {
            return Scope.SCOPE_SEGMENT;
         }
         else if (request instanceof MonitorGetBlockInfoRequest)
         {
            return Scope.SCOPE_BLOCK;
         }
         else if (request instanceof MonitorGetProcessInfoRequest)
         {
            return Scope.SCOPE_PROCESS;
         }
         else if (request instanceof MonitorGetEnvVarsRequest)
         {
            if (blockExists(request.scopeId))
            {
               return Scope.SCOPE_BLOCK;
            }
            else if (processExists(request.scopeId))
            {
               return Scope.SCOPE_PROCESS;
            }
            else
            {
               throw new IllegalArgumentException();
            }
         }
         else if (request instanceof MonitorGetStackUsageRequest)
         {
            return Scope.SCOPE_PROCESS;
         }
         else if (request instanceof MonitorGetSignalQueueRequest)
         {
            return Scope.SCOPE_PROCESS;
         }
         else if (request instanceof MonitorGetPoolInfoRequest)
         {
            return Scope.SCOPE_POOL;
         }
         else if (request instanceof MonitorGetPoolInfo64Request)
         {
            return Scope.SCOPE_POOL;
         }
         else if (request instanceof MonitorGetPoolSignalsRequest)
         {
            return Scope.SCOPE_POOL;
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private interface SignalComparator
   {
      public boolean equals(Signal a, Signal b);
   }

   private static class MonitorGetSysParamReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetSysParamReply) &&
             (b instanceof MonitorGetSysParamReply))
         {
            MonitorGetSysParamReply x = (MonitorGetSysParamReply) a;
            MonitorGetSysParamReply y = (MonitorGetSysParamReply) b;
            return (x.name.equals(y.name));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetSegmentInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetSegmentInfoReply) &&
             (b instanceof MonitorGetSegmentInfoReply))
         {
            MonitorGetSegmentInfoReply x = (MonitorGetSegmentInfoReply) a;
            MonitorGetSegmentInfoReply y = (MonitorGetSegmentInfoReply) b;
            return (x.sid == y.sid);
         }
         else if ((a instanceof MonitorGetSegmentInfoLongReply) &&
                  (b instanceof MonitorGetSegmentInfoLongReply))
         {
            MonitorGetSegmentInfoLongReply x = (MonitorGetSegmentInfoLongReply) a;
            MonitorGetSegmentInfoLongReply y = (MonitorGetSegmentInfoLongReply) b;
            return (x.sid == y.sid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetBlockInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetBlockInfoReply) &&
             (b instanceof MonitorGetBlockInfoReply))
         {
            MonitorGetBlockInfoReply x = (MonitorGetBlockInfoReply) a;
            MonitorGetBlockInfoReply y = (MonitorGetBlockInfoReply) b;
            return (x.bid == y.bid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetProcessInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetProcessInfoReply) &&
             (b instanceof MonitorGetProcessInfoReply))
         {
            MonitorGetProcessInfoReply x = (MonitorGetProcessInfoReply) a;
            MonitorGetProcessInfoReply y = (MonitorGetProcessInfoReply) b;
            return (x.pid == y.pid);
         }
         else if ((a instanceof MonitorGetProcessInfoLongReply) &&
                  (b instanceof MonitorGetProcessInfoLongReply))
         {
            MonitorGetProcessInfoLongReply x = (MonitorGetProcessInfoLongReply) a;
            MonitorGetProcessInfoLongReply y = (MonitorGetProcessInfoLongReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetEnvVarsReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetEnvVarsReply) &&
             (b instanceof MonitorGetEnvVarsReply))
         {
            MonitorGetEnvVarsReply x = (MonitorGetEnvVarsReply) a;
            MonitorGetEnvVarsReply y = (MonitorGetEnvVarsReply) b;
            return ((x.pid == y.pid) && (x.name.equals(y.name)));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetStackUsageReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetStackUsageReply) &&
             (b instanceof MonitorGetStackUsageReply))
         {
            MonitorGetStackUsageReply x = (MonitorGetStackUsageReply) a;
            MonitorGetStackUsageReply y = (MonitorGetStackUsageReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetSignalQueueReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         // XXX: Two signals in a signal queue are considered equal if they
         // belong to the signal queue of the same process. In that way, if
         // there are multiple signal blocks for the signal queue a process,
         // the older signal block is discarded entirely at merging.
         if ((a instanceof MonitorGetSignalQueueReply) &&
             (b instanceof MonitorGetSignalQueueReply))
         {
            MonitorGetSignalQueueReply x = (MonitorGetSignalQueueReply) a;
            MonitorGetSignalQueueReply y = (MonitorGetSignalQueueReply) b;
            return (x.pid == y.pid);
         }
         else if ((a instanceof MonitorGetSignalQueueLongReply) &&
                  (b instanceof MonitorGetSignalQueueLongReply))
         {
            MonitorGetSignalQueueLongReply x = (MonitorGetSignalQueueLongReply) a;
            MonitorGetSignalQueueLongReply y = (MonitorGetSignalQueueLongReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetPoolInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetPoolInfoReply) &&
             (b instanceof MonitorGetPoolInfoReply))
         {
            MonitorGetPoolInfoReply x = (MonitorGetPoolInfoReply) a;
            MonitorGetPoolInfoReply y = (MonitorGetPoolInfoReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetPoolInfo64ReplyComparator
   implements SignalComparator
{
   public boolean equals(Signal a, Signal b)
   {
      if ((a instanceof MonitorGetPoolInfo64Reply) &&
          (b instanceof MonitorGetPoolInfo64Reply))
      {
         MonitorGetPoolInfo64Reply x = (MonitorGetPoolInfo64Reply) a;
         MonitorGetPoolInfo64Reply y = (MonitorGetPoolInfo64Reply) b;
         return (x.pid == y.pid);
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }
}

   private static class MonitorGetPoolSignalsReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         // XXX: Two pool signal replies are considered equal if they belong to
         // the same pool. In that way, if there are multiple signal blocks for
         // the pool signals of a pool, the older signal block is discarded
         // entirely at merging. This is considered good enough, since if there
         // are multiple pool signal blocks for the same pool, the later is
         // typically a superset of the earlier (i.e. if you fail to dump
         // something for a certain scope you typically widen the scope at the
         // next attempt).
         if ((a instanceof MonitorGetPoolSignalsReply) &&
             (b instanceof MonitorGetPoolSignalsReply))
         {
            MonitorGetPoolSignalsReply x = (MonitorGetPoolSignalsReply) a;
            MonitorGetPoolSignalsReply y = (MonitorGetPoolSignalsReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class MonitorGetRegistersReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof MonitorGetRegistersReply) &&
             (b instanceof MonitorGetRegistersReply))
         {
            MonitorGetRegistersReply x = (MonitorGetRegistersReply) a;
            MonitorGetRegistersReply y = (MonitorGetRegistersReply) b;
            return (x.pid == y.pid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmLoadModuleInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmLoadModuleInfoReply) &&
             (b instanceof PmLoadModuleInfoReply))
         {
            PmLoadModuleInfoReply x = (PmLoadModuleInfoReply) a;
            PmLoadModuleInfoReply y = (PmLoadModuleInfoReply) b;
            return (x.install_handle.equals(y.install_handle));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmLoadModuleInfoReply_64Comparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmLoadModuleInfoReply_64) &&
             (b instanceof PmLoadModuleInfoReply_64))
         {
            PmLoadModuleInfoReply_64 x = (PmLoadModuleInfoReply_64) a;
            PmLoadModuleInfoReply_64 y = (PmLoadModuleInfoReply_64) b;
            return (x.install_handle.equals(y.install_handle));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmLoadModuleSectionInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmLoadModuleSectionInfoReply) &&
             (b instanceof PmLoadModuleSectionInfoReply))
         {
            PmLoadModuleSectionInfoReply x = (PmLoadModuleSectionInfoReply) a;
            PmLoadModuleSectionInfoReply y = (PmLoadModuleSectionInfoReply) b;
            return (x.install_handle.equals(y.install_handle));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmLoadModuleSectionInfoReply_64Comparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmLoadModuleSectionInfoReply_64) &&
             (b instanceof PmLoadModuleSectionInfoReply_64))
         {
            PmLoadModuleSectionInfoReply_64 x = (PmLoadModuleSectionInfoReply_64) a;
            PmLoadModuleSectionInfoReply_64 y = (PmLoadModuleSectionInfoReply_64) b;
            return (x.install_handle.equals(y.install_handle));
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmProgramInfoReplyComparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmProgramInfoReply) &&
             (b instanceof PmProgramInfoReply))
         {
            PmProgramInfoReply x = (PmProgramInfoReply) a;
            PmProgramInfoReply y = (PmProgramInfoReply) b;
            return (x.progpid == y.progpid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   private static class PmProgramInfoReply_64Comparator
      implements SignalComparator
   {
      public boolean equals(Signal a, Signal b)
      {
         if ((a instanceof PmProgramInfoReply_64) &&
             (b instanceof PmProgramInfoReply_64))
         {
            PmProgramInfoReply_64 x = (PmProgramInfoReply_64) a;
            PmProgramInfoReply_64 y = (PmProgramInfoReply_64) b;
            return (x.progpid == y.progpid);
         }
         else
         {
            throw new IllegalArgumentException();
         }
      }
   }

   public static class MonitorEventTrace
   {
      private final Dump dump;
      private final SignalBlock signalBlock;
      private final long numEvents;
      private final long numOldEvents;
      private final long from;
      private final long to;
      private final int status;

      MonitorEventTrace(Dump dump,
                        SignalBlock signalBlock,
                        long numEvents,
                        long numOldEvents,
                        long from,
                        long to,
                        int status)
      {
         if ((dump == null) || (signalBlock == null))
         {
            throw new IllegalArgumentException();
         }
         this.dump = dump;
         this.signalBlock = signalBlock;
         this.numEvents = numEvents;
         this.numOldEvents = numOldEvents;
         this.from = from;
         this.to = to;
         this.status = status;
      }

      public SignalBlock getSignalBlock()
      {
         return signalBlock;
      }

      public long getNumEvents()
      {
         return numEvents;
      }

      public long getNumOldEvents()
      {
         return numOldEvents;
      }

      public long getFrom()
      {
         return from;
      }

      public long getTo()
      {
         return to;
      }

      public int getStatus()
      {
         return status;
      }

      public Signal readSignal(SignalInputStream in) throws IOException
      {
         return dump.readSignal(in);
      }

      public static boolean isOldEventType(Signal event)
      {
         return ((event instanceof MonitorGetSendTraceReply) ||
                 (event instanceof MonitorGetReceiveTraceReply) ||
                 (event instanceof MonitorGetSwapTraceReply) ||
                 (event instanceof MonitorGetCreateTraceReply) ||
                 (event instanceof MonitorGetKillTraceReply) ||
                 (event instanceof MonitorGetErrorTraceReply) ||
                 (event instanceof MonitorGetResetTraceReply) ||
                 (event instanceof MonitorGetUserTraceReply));
      }

      /**
       * This class provides a process info cache of a specified maximum size
       * where the oldest entry is removed if the size limit is threatened to
       * be exceeded.
       *
       * @see java.util.LinkedHashMap
       */
      public static class ProcessInfoCache extends LinkedHashMap
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
}