/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.dbgserver;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import com.ose.dbgserver.monitor.MonitorGatewayService;
import com.ose.dbgserver.monitor.ProgramManagerGatewayService;
import com.ose.dbgserver.monitor.signals.MonitorBreakpointFiredNotify;
import com.ose.dbgserver.monitor.signals.MonitorClearBreakpointReply;
import com.ose.dbgserver.monitor.signals.MonitorClearBreakpointRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetBreakpointReply;
import com.ose.dbgserver.monitor.signals.MonitorSetBreakpointRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetMemoryReply;
import com.ose.dbgserver.monitor.signals.MonitorSetMemoryRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetRegistersReply;
import com.ose.dbgserver.monitor.signals.MonitorSetRegistersRequest;
import com.ose.dbgserver.monitor.signals.MonitorStepProcessNotify;
import com.ose.dbgserver.monitor.signals.MonitorStepProcessRequest;
import com.ose.dbgserver.protocol.DBGBpFired;
import com.ose.dbgserver.protocol.DBGClearBp;
import com.ose.dbgserver.protocol.DBGClearBpReply;
import com.ose.dbgserver.protocol.DBGDisconnect;
import com.ose.dbgserver.protocol.DBGFlowControl;
import com.ose.dbgserver.protocol.DBGGetBlockFullInfo;
import com.ose.dbgserver.protocol.DBGGetBlockFullInfoReply;
import com.ose.dbgserver.protocol.DBGGetBlockInfoReply;
import com.ose.dbgserver.protocol.DBGGetBlockProcInfo;
import com.ose.dbgserver.protocol.DBGGetCPULoadReports;
import com.ose.dbgserver.protocol.DBGGetCPULoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetCPUMeasurementStatus;
import com.ose.dbgserver.protocol.DBGGetCPUMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetEnvList;
import com.ose.dbgserver.protocol.DBGGetEnvListEndMark;
import com.ose.dbgserver.protocol.DBGGetEnvListReply;
import com.ose.dbgserver.protocol.DBGGetPoolInfo;
import com.ose.dbgserver.protocol.DBGGetPoolInfoReply;
import com.ose.dbgserver.protocol.DBGGetPoolSignals;
import com.ose.dbgserver.protocol.DBGGetPoolSignalsReply;
import com.ose.dbgserver.protocol.DBGGetPriLoadReports;
import com.ose.dbgserver.protocol.DBGGetPriLoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetPriMeasurementStatus;
import com.ose.dbgserver.protocol.DBGGetPriMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetProcLoadReports;
import com.ose.dbgserver.protocol.DBGGetProcLoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetProcMeasurementStatus;
import com.ose.dbgserver.protocol.DBGGetProcMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetProcessFullInfoReply;
import com.ose.dbgserver.protocol.DBGGetProcessInfo;
import com.ose.dbgserver.protocol.DBGGetProcessInfoReply;
import com.ose.dbgserver.protocol.DBGGetProgramInfo;
import com.ose.dbgserver.protocol.DBGGetProgramInfoReply;
import com.ose.dbgserver.protocol.DBGGetSignalQueue;
import com.ose.dbgserver.protocol.DBGGetSignalQueueReply;
import com.ose.dbgserver.protocol.DBGGetStackUsage;
import com.ose.dbgserver.protocol.DBGGetStackUsageReply;
import com.ose.dbgserver.protocol.DBGInfoBlockEndMark;
import com.ose.dbgserver.protocol.DBGInfoPoolEndMark;
import com.ose.dbgserver.protocol.DBGInfoProcEndMark;
import com.ose.dbgserver.protocol.DBGInfoProgramEndMark;
import com.ose.dbgserver.protocol.DBGInfoQueueEndMark;
import com.ose.dbgserver.protocol.DBGInterceptScope;
import com.ose.dbgserver.protocol.DBGInterceptScopeReply;
import com.ose.dbgserver.protocol.DBGKillProcess;
import com.ose.dbgserver.protocol.DBGPoolSignalsEndMark;
import com.ose.dbgserver.protocol.DBGReadMemory;
import com.ose.dbgserver.protocol.DBGReadMemoryReply;
import com.ose.dbgserver.protocol.DBGReadRegister;
import com.ose.dbgserver.protocol.DBGReadRegisterReply;
import com.ose.dbgserver.protocol.DBGResumeScope;
import com.ose.dbgserver.protocol.DBGResumeScopeReply;
import com.ose.dbgserver.protocol.DBGSetApplicationScope;
import com.ose.dbgserver.protocol.DBGSetApplicationScopeReply;
import com.ose.dbgserver.protocol.DBGSetBp;
import com.ose.dbgserver.protocol.DBGSetBpReply;
import com.ose.dbgserver.protocol.DBGSetPriMeasurementStatus;
import com.ose.dbgserver.protocol.DBGSetPriMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGSetProcMeasurementStatus;
import com.ose.dbgserver.protocol.DBGSetProcMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGStartProcess;
import com.ose.dbgserver.protocol.DBGStepProcess;
import com.ose.dbgserver.protocol.DBGStepProcessComplete;
import com.ose.dbgserver.protocol.DBGStopProcess;
import com.ose.dbgserver.protocol.DBGWriteMemory;
import com.ose.dbgserver.protocol.DBGWriteMemoryReply;
import com.ose.dbgserver.protocol.DBGWriteRegister;
import com.ose.dbgserver.protocol.DBGWriteRegisterReply;
import com.ose.dbgserver.protocol.Message;
import com.ose.dbgserver.util.AssertException;
import com.ose.gateway.Signal;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorCPUPriReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport.MonitorCPUProcess;
import com.ose.system.service.monitor.MonitorCPUReport;
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorSignalInfo;
import com.ose.system.service.monitor.MonitorStackInfo;
import com.ose.system.service.monitor.MonitorStatus;
import com.ose.system.service.monitor.signal.MonitorAttachReply;
import com.ose.system.service.monitor.signal.MonitorAttachRequest;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorDetachReply;
import com.ose.system.service.monitor.signal.MonitorDetachRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoRequest;
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
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsRequest;
import com.ose.system.service.monitor.signal.MonitorGetMemoryEndmark;
import com.ose.system.service.monitor.signal.MonitorGetMemoryReply;
import com.ose.system.service.monitor.signal.MonitorGetMemoryRequest;
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
import com.ose.system.service.monitor.signal.MonitorGetRegisterInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetRegistersReply;
import com.ose.system.service.monitor.signal.MonitorGetRegistersRequest;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueRequest;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageEndmark;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageReply;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageRequest;
import com.ose.system.service.monitor.signal.MonitorInterceptScopeReply;
import com.ose.system.service.monitor.signal.MonitorInterceptScopeRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorKillScopeReply;
import com.ose.system.service.monitor.signal.MonitorKillScopeRequest;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;
import com.ose.system.service.monitor.signal.MonitorResumeScopeReply;
import com.ose.system.service.monitor.signal.MonitorResumeScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUPriReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorStartScopeReply;
import com.ose.system.service.monitor.signal.MonitorStartScopeRequest;
import com.ose.system.service.monitor.signal.MonitorStopScopeReply;
import com.ose.system.service.monitor.signal.MonitorStopScopeRequest;
import com.ose.system.service.pm.LoadModuleSectionInfo;
import com.ose.system.service.pm.ProgramManagerStatus;
import com.ose.system.service.pm.signal.PmGetProgramPidReply;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesReply;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.pm.signal.PmProgramsReply;
import com.ose.system.service.pm.signal.PmProgramsRequest;

/**
 * This class is a debug server client that converts between the OSE 5.x monitor
 * protocol and the OSE 4.x debug server protocol. This class handles the
 * communication between a monitor client (e.g. Optima and GDB) and an OSE 4.x
 * target system running a debug server.
 */
public class DebugServer
{
   private static final int BIG_ENDIAN = 0x1020304;
   private static final int LITTLE_ENDIAN = 0x4030201;

   private static final int NTICK_FREQUENCY = 1000000;
   private static final int FLOW_CONTROL_INCREMENT = 10;
   private static final int MAX_BYTES_IN_MEM_REQUEST = 256;

   private static final int CPU_MAX_REPORTS = 50;
   private static final int CPU_PRIORITY_LIMIT = 24;
   private static final int CPU_PRI_MAX_REPORTS = 50;
   private static final int CPU_PROCESS_MAX_REPORTS = 50;
   private static final int CPU_PROCESS_MAX_REPORTS_PROCESS = 8;

   private static final int[] SUPPORTED_SIGNALS =
   {
      /* monitor.sig */
      MonitorInterfaceRequest.SIG_NO,
      MonitorNameRequest.SIG_NO,
      MonitorConnectRequest.SIG_NO,
      MonitorDisconnectRequest.SIG_NO,
      MonitorGetBlockInfoRequest.SIG_NO,
      MonitorGetProcessInfoRequest.SIG_NO,
      MonitorGetEnvVarsRequest.SIG_NO,
      MonitorGetStackUsageRequest.SIG_NO,
      MonitorGetSignalQueueRequest.SIG_NO,
      MonitorGetPoolInfoRequest.SIG_NO,
      MonitorGetPoolSignalsRequest.SIG_NO,
      MonitorStartScopeRequest.SIG_NO,
      MonitorStopScopeRequest.SIG_NO,
      MonitorKillScopeRequest.SIG_NO,
      MonitorGetMemoryRequest.SIG_NO,
      /* monitor_execution.sig */
      MonitorSetMemoryRequest.SIG_NO,
      MonitorGetRegisterInfoRequest.SIG_NO,
      MonitorGetRegistersRequest.SIG_NO,
      MonitorSetRegistersRequest.SIG_NO,
      MonitorAttachRequest.SIG_NO,
      MonitorDetachRequest.SIG_NO,
      MonitorInterceptScopeRequest.SIG_NO,
      MonitorResumeScopeRequest.SIG_NO,
      MonitorSetBreakpointRequest.SIG_NO,
      MonitorClearBreakpointRequest.SIG_NO,
      MonitorStepProcessRequest.SIG_NO,
      /* monitor_cpu.sig */
      MonitorGetCPUReportsEnabledRequest.SIG_NO,
      MonitorSetCPUReportsEnabledRequest.SIG_NO,
      MonitorGetCPUReportsRequest.SIG_NO,
      MonitorGetCPUPriReportsEnabledRequest.SIG_NO,
      MonitorSetCPUPriReportsEnabledRequest.SIG_NO,
      MonitorGetCPUPriReportsRequest.SIG_NO,
      MonitorGetCPUProcessReportsEnabledRequest.SIG_NO,
      MonitorSetCPUProcessReportsEnabledRequest.SIG_NO,
      MonitorGetCPUProcessReportsRequest.SIG_NO
   };

   private static final short[] SUPPORTED_FEATURES =
   {
      Monitor.MONITOR_FEATURE_INTERCEPT_SCOPE,
      Monitor.MONITOR_FEATURE_COMPACT_REGISTER_SIGNALS,
      Monitor.MONITOR_FEATURE_PROCESS_FILTER,
      Monitor.MONITOR_FEATURE_BLOCK_FILTER
   };

   // Info about this debug server instance.
   private final InetAddress address;
   private final int port;
   private final String name;
   private boolean connected;
   private boolean connectionOutstanding;
   private int byteOrder = BIG_ENDIAN;
   private int cpuClass;
   private int cpuType;
   private int tickLength;
   private TargetWriter targetWriter;
   private TargetInterface targetInterface;

   // Client ID and list of objects to receive a callback when a signal is received.
   private int clientPid;
   private List<AbstractGatewayService> callbackList =
      new ArrayList<AbstractGatewayService>();

   // In order to get the interface information, a connection is required.
   private boolean interfaceReplyPending;
   private boolean connectReplyPending;

   // Used to control the flow of the event signals.
   private int flowControlSignals = 30;

   // Used to keep track of the request type, long or regular.
   private boolean isProcessRequestRegular;

   // Needed if all processes within a block are requested.
   private boolean isBlockProcessRequestOngoing;
   private List<Integer> processRequestsList = new ArrayList<Integer>();

   // Stored values of a get memory request.
   private int memoryGetRequestPid;
   private int memoryGetRequestAddress;
   private int memoryGetRequestSize;
   private int memoryGetRequestReceivedBytes;

   // Stored values of a set memory request.
   private int memorySetRequestPid;
   private int memorySetRequestAddress;
   private int memorySetRequestSize;

   // Stored values of a get register request.
   private int registerGetRequestPid;

   // Stored value of a set register request.
   private int registerSetRequestPid;

   // Need process info before sending a breakpoint request.
   private DBGSetBp breakpointReq;
   private boolean breakpointReqOngoing;

   // CPU profiling integration intervals.
   private int cpuPriIntegrationInterval = 100;
   private int cpuProcessIntegrationInteval = 100;

   // Variables to keep track of program manager requests.
   private boolean pmLoadModuleInstallHandlesRequestIsActive;
   private List<String> pmLoadModuleInstallHandlesList;
   private boolean pmLoadModuleInfoRequestIsActive;
   private PmLoadModuleInfoReply pmLoadModuleInfoReply;
   private String pmInstallHandle = "";
   private boolean pmLoadModuleSectionInfoRequestIsActive;
   private PmLoadModuleSectionInfoReply pmLoadModuleSectionInfoReply;
   private int pmLoadModuleSectionIntervalStart;
   private int pmLoadModuleSectionIntervalEnd;
   private boolean pmProgramInfoRequestIsActive;
   private int pmProgramInfoRequestProgPid;
   private PmProgramInfoReply pmProgramInfoReply;
   private boolean pmGetProgramPidRequestIsActive;
   private int pmGetProgramPidRequestPid;
   private int pmGetProgramPidRequestBid;
   private PmGetProgramPidReply pmGetProgramPidReply;

   /**
    * Initialize the debug server object.
    *
    * @param addr  the target system's IP address.
    * @param port  the debug server's port number.
    * @param name  the name of the target system.
    */
   public DebugServer(InetAddress addr, int port, String name)
   {
      this.address = addr;
      this.port = port;
      this.name = name;
   }

   public InetAddress getAddress()
   {
      return address;
   }

   public int getPort()
   {
      return port;
   }

   public String getName()
   {
      return name;
   }

   public boolean isConnected()
   {
      return connected;
   }

   public void registerForCallbacks(AbstractGatewayService o, int pid)
   {
      if (o != null)
      {
         callbackList.add(o);
      }
      this.clientPid = pid;
   }

   /*
    * Callback when a connection is established.
    */
   void connected(TargetWriter tw)
   {
      if (targetInterface == null)
      {
         throw new AssertException();
      }

      connected = true;
      connectionOutstanding = false;
      targetWriter = tw;

      if (interfaceReplyPending)
      {
         MonitorInterfaceReply reply = new MonitorInterfaceReply();
         reply.endian = isBigEndian();
         reply.osType = Monitor.MONITOR_OS_OSE_4;
         reply.cpuClass = cpuClass;
         reply.sigs = SUPPORTED_SIGNALS;

         sendMonitorReply(reply);
         interfaceReplyPending = false;

         // Disconnect again. This is required since an OSE 4 target system
         // requires a connection before the interface request may be sent.
         send(new DBGDisconnect());
      }

      if (connectReplyPending)
      {
         MonitorConnectReply connectReply = new MonitorConnectReply();
         connectReply.status = MonitorStatus.MONITOR_STATUS_OK;
         connectReply.tickLength = tickLength;
         connectReply.tick = 0;
         connectReply.ntick = 0;
         connectReply.ntickFrequency = NTICK_FREQUENCY;
         connectReply.cpuClass = cpuClass;
         connectReply.cpuType = cpuType;
         connectReply.errorHandler = 0;
         connectReply.euCount = 0;
         connectReply.featuresCount = SUPPORTED_FEATURES.length;
         connectReply.features = SUPPORTED_FEATURES;

         sendMonitorReply(connectReply);
         connectReplyPending = false;
      }
   }

   /*
    * Callback when a connection is closed.
    */
   void disconnected()
   {
      connected = false;
      connectionOutstanding = false;
   }

   /*
    * Terminate this debug server target connection.
    */
   void terminate()
   {
      // Notify the target interface.
      if (targetInterface != null)
      {
         targetInterface.die();
      }
   }

   void setEndian(int byteOrder)
   {
      AssertException.test((byteOrder == BIG_ENDIAN)
            || (byteOrder == LITTLE_ENDIAN), "Illegal byte order");
      this.byteOrder = byteOrder;
   }

   public boolean isBigEndian()
   {
      return (byteOrder == BIG_ENDIAN);
   }

   void setSystemInfo(int majInterfaceVers, int minInterfaceVers,
         int tickLength, int traceBufSize)
   {
      AssertException.test((majInterfaceVers == 1) && (minInterfaceVers >= 0));
      AssertException.test((tickLength > 0) && (traceBufSize >= 0));
      this.tickLength = tickLength;
   }

   void setCpuClass(int cpuClass)
   {
      this.cpuClass = cpuClass;
   }

   void setCpuType(int cpuType)
   {
      this.cpuType = cpuType;
   }

   public void connect()
   {
      if (!connectionOutstanding)
      {
         connectionOutstanding = true;
         if (targetInterface == null)
         {
            targetInterface = new TargetInterface(this);
            targetInterface.setPriority(Thread.NORM_PRIORITY - 2);
         }
         if (port == 0)
         {
            throw new AssertException("Reconnect without previous connect");
         }
         targetInterface.connect(address, port);
      }
   }

   void cancelConnect()
   {
      if (connectionOutstanding)
      {
         connectionOutstanding = false;
         targetInterface = null;

         if (interfaceReplyPending)
         {
            MonitorInterfaceReply reply = new MonitorInterfaceReply();
            reply.endian = isBigEndian();
            reply.osType = Monitor.MONITOR_OS_OSE_4;
            reply.cpuClass = cpuClass;
            reply.sigs = SUPPORTED_SIGNALS;

            sendMonitorReply(reply);
            interfaceReplyPending = false;
         }

         if (connectReplyPending)
         {
            MonitorConnectReply connectReply = new MonitorConnectReply();
            connectReply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
            connectReply.tickLength = tickLength;
            connectReply.tick = 0;
            connectReply.ntick = 0;
            connectReply.ntickFrequency = NTICK_FREQUENCY;
            connectReply.cpuClass = cpuClass;
            connectReply.cpuType = cpuType;
            connectReply.errorHandler = 0;
            connectReply.euCount = 0;
            connectReply.featuresCount = SUPPORTED_FEATURES.length;
            connectReply.features = SUPPORTED_FEATURES;

            sendMonitorReply(connectReply);
            connectReplyPending = false;
         }
      }
   }

   public void handleInterfaceRequest()
   {
      if (connected)
      {
         MonitorInterfaceReply reply = new MonitorInterfaceReply();
         reply.endian = isBigEndian();
         reply.osType = Monitor.MONITOR_OS_OSE_4;
         reply.cpuClass = cpuClass;
         reply.sigs = SUPPORTED_SIGNALS;

         sendMonitorReply(reply);
      }
      else
      {
         connect();
         interfaceReplyPending = true;
      }
   }

   public void handleNameRequest()
   {
      MonitorNameReply reply = new MonitorNameReply();
      reply.systemName = name;
      reply.monitorName = "com.ose.RunModeMonitor";

      sendMonitorReply(reply);
   }

   public void handleConnectRequest()
   {
      if (connected)
      {
         MonitorConnectReply reply = new MonitorConnectReply();
         reply.status = MonitorStatus.MONITOR_STATUS_OK;
         reply.tickLength = tickLength;
         reply.tick = 0;
         reply.ntick = 0;
         reply.ntickFrequency = NTICK_FREQUENCY;
         reply.cpuClass = cpuClass;
         reply.cpuType = cpuType;
         reply.errorHandler = 0;
         reply.euCount = 0;
         reply.featuresCount = SUPPORTED_FEATURES.length;
         reply.features = SUPPORTED_FEATURES;

         sendMonitorReply(reply);
      }
      else
      {
         connect();
         connectReplyPending = true;
      }
   }

   public void handleDisconnectRequest()
   {
      send(new DBGDisconnect());
      callbackList.clear();
   }

   public void handleBlockingFlowControlReply(
         MonitorBlockingFlowControlReply reply)
   {
      flowControlSignals += FLOW_CONTROL_INCREMENT;
      send(new DBGFlowControl(FLOW_CONTROL_INCREMENT));
   }

   public void handleGetBlockInfoRequest(MonitorGetBlockInfoRequest request)
   {
      // FIXME: Handle scope and filter tags.
      send(new DBGGetBlockFullInfo(request.scopeId));
   }

   void getBlockInfoReply(DBGGetBlockInfoReply reply)
   {
      MonitorGetBlockInfoReply monitorReply = new MonitorGetBlockInfoReply();
      monitorReply.bid = reply.bid;
      monitorReply.sid = 0;
      monitorReply.uid = reply.userId;
      monitorReply.supervisor = false;
      monitorReply.signalsAttached = 0;
      monitorReply.errorHandler = 0;
      monitorReply.maxSigSize = 0;
      monitorReply.sigPoolId = 0;
      monitorReply.stackPoolId = 0;
      monitorReply.euId = 0;
      monitorReply.name = reply.blockName;

      sendMonitorReply(monitorReply);
   }

   void getBlockFullInfoReply(DBGGetBlockFullInfoReply reply)
   {
      MonitorGetBlockInfoReply monitorReply = new MonitorGetBlockInfoReply();
      monitorReply.bid = reply.bid;
      monitorReply.sid = reply.segId;
      monitorReply.uid = reply.userId;
      monitorReply.supervisor = false;
      monitorReply.signalsAttached = 0;
      monitorReply.errorHandler = 0;
      monitorReply.maxSigSize = reply.maxSigSize;
      monitorReply.sigPoolId = reply.sigPoolId;
      monitorReply.stackPoolId = reply.stackPoolId;
      monitorReply.euId = 0;
      monitorReply.name = reply.blockName;

      sendMonitorReply(monitorReply);
   }

   void infoBlockEndMark(DBGInfoBlockEndMark reply)
   {
      MonitorGetBlockInfoEndmark endmark = new MonitorGetBlockInfoEndmark();
      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(endmark);
   }

   public void handleGetProcessInfoRequest(MonitorGetProcessInfoRequest request)
   {
      // FIXME: Handle filter tags.
      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         send(new DBGGetProcessInfo(1, request.scopeId));
         isProcessRequestRegular = (request.level == 0);
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         // When listing all processes in a block, the debug server does only
         // support brief info and not full info.
         send(new DBGGetBlockProcInfo(request.scopeId, 0));
         isBlockProcessRequestOngoing = true;
         isProcessRequestRegular = (request.level == 0);
      }
      else
      {
         // List all processes in the system.
         send(new DBGGetProcessInfo(request.level, request.scopeId));
         isProcessRequestRegular = (request.level == 0);
      }
   }

   void getProcessInfoReply(DBGGetProcessInfoReply reply)
   {
      if (breakpointReqOngoing)
      {
         breakpointReq.mainBid = reply.bid;
         send(breakpointReq);
      }
      else if (isBlockProcessRequestOngoing)
      {
         processRequestsList.add(new Integer(reply.pid));
      }
      else if (pmGetProgramPidRequestIsActive)
      {
         pmGetProgramPidRequestBid = reply.bid;
         send(new DBGGetProgramInfo());
      }
      else
      {
         MonitorGetProcessInfoReply monitorReply = new MonitorGetProcessInfoReply();
         monitorReply.pid = reply.pid;
         monitorReply.bid = reply.bid;
         monitorReply.sid = 0;
         monitorReply.type = reply.procType & 0xFFFF;
         monitorReply.state = reply.status & 0xFFFF;
         monitorReply.priority = reply.priority & 0xFFFF;
         monitorReply.entrypoint = 0;
         monitorReply.properties = 0;
         monitorReply.name = reply.processName;

         sendMonitorReply(monitorReply);
      }
   }

   void getProcessFullInfoReply(DBGGetProcessFullInfoReply reply)
   {
      if (isProcessRequestRegular)
      {
         MonitorGetProcessInfoReply monitorReply =
            new MonitorGetProcessInfoReply();
         monitorReply.pid = reply.pid;
         monitorReply.bid = reply.bid;
         monitorReply.sid = 0;
         monitorReply.type = reply.procType & 0xFFFF;
         monitorReply.state = reply.status & 0xFFFF;
         monitorReply.priority = reply.priority & 0xFFFF;
         monitorReply.entrypoint = reply.entryPoint;
         monitorReply.properties = 0;
         monitorReply.name = reply.processName;

         sendMonitorReply(monitorReply);
      }
      else
      {
         MonitorGetProcessInfoLongReply monitorReply =
            new MonitorGetProcessInfoLongReply();
         monitorReply.pid = reply.pid;
         monitorReply.bid = reply.bid;
         monitorReply.sid = 0;
         monitorReply.type = reply.procType & 0xFFFF;
         monitorReply.state = reply.status & 0xFFFF;
         monitorReply.priority = reply.priority & 0xFFFF;
         monitorReply.uid = reply.userId;
         monitorReply.creator = 0;
         monitorReply.supervisor = false;
         monitorReply.signalsInQueue = reply.signalsInQueue & 0xFFFF;
         monitorReply.signalsAttached = 0;
         monitorReply.entrypoint = reply.entryPoint;
         monitorReply.semaphoreValue = reply.fsemValue;
         monitorReply.stackSize = reply.stackSize;
         monitorReply.supervisorStackSize = 0;
         monitorReply.lineNumber = reply.lineNumber;
         monitorReply.signalsOwned = reply.signalBuffers;
         monitorReply.timeSlice = 0;
         monitorReply.vector = 0;
         monitorReply.errorHandler = 0;
         monitorReply.properties = 0;
         monitorReply.euId = 0;
         monitorReply.sigselect = reply.wantedSignals;
         monitorReply.processName = reply.processName;
         monitorReply.fileName = reply.fileName;

         sendMonitorReply(monitorReply);
      }
   }

   void infoProcEndMark(DBGInfoProcEndMark reply)
   {
      if (isBlockProcessRequestOngoing)
      {
         if (processRequestsList.isEmpty())
         {
            // No processes found in the block, send the endmark.
            MonitorGetProcessInfoEndmark endmark =
               new MonitorGetProcessInfoEndmark();
            endmark.status = MonitorStatus.MONITOR_STATUS_OK;

            sendMonitorReply(endmark);
            isBlockProcessRequestOngoing = false;
            isProcessRequestRegular = false;
         }
         else
         {
            // Send the request about the next process in the block.
            Integer process = processRequestsList.remove(0);
            send(new DBGGetProcessInfo(1, process.intValue()));
            if (processRequestsList.isEmpty())
            {
               // The last process request is made. Next time, send the endmark.
               isBlockProcessRequestOngoing = false;
            }
         }
      }
      else if (breakpointReqOngoing)
      {
         breakpointReqOngoing = false;
      }
      else if (pmGetProgramPidRequestIsActive && (pmGetProgramPidRequestBid == 0))
      {
         pmGetProgramPidReply = new PmGetProgramPidReply();
         pmGetProgramPidReply.status = ProgramManagerStatus.PM_EPROGPID;
         pmGetProgramPidReply.pid = pmGetProgramPidRequestPid;
         pmGetProgramPidReply.progpid = 0;

         pmGetProgramPidRequestIsActive = false;
         sendProgramManagerReply(pmGetProgramPidReply);
         pmGetProgramPidReply = null;
      }
      else
      {
         MonitorGetProcessInfoEndmark endmark =
            new MonitorGetProcessInfoEndmark();
         endmark.status = MonitorStatus.MONITOR_STATUS_OK;

         sendMonitorReply(endmark);
         isProcessRequestRegular = false;
      }
   }

   public void handleGetEnvVarsRequest(MonitorGetEnvVarsRequest request)
   {
      if (request.scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         send(new DBGGetEnvList(0));
      }
      else
      {
         send(new DBGGetEnvList(request.scopeId));
      }
   }

   void getEnvListReply(DBGGetEnvListReply reply)
   {
      MonitorGetEnvVarsReply monitorReply = new MonitorGetEnvVarsReply();
      monitorReply.pid = reply.pid;
      monitorReply.name = reply.name;
      monitorReply.value = reply.value;

      sendMonitorReply(monitorReply);
   }

   void getEnvListEndMark(DBGGetEnvListEndMark reply)
   {
      MonitorGetEnvVarsEndmark endmark = new MonitorGetEnvVarsEndmark();
      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(endmark);
   }

   public void handleGetStackUsageRequest(MonitorGetStackUsageRequest request)
   {
      // FIXME: Handle filter tags.
      if (request.scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         send(new DBGGetStackUsage(0));
      }
      else
      {
         send(new DBGGetStackUsage(request.scopeId));
      }
   }

   void getStackUsageReply(DBGGetStackUsageReply reply)
   {
      if (reply.pid != 0)
      {
         MonitorGetStackUsageReply monitorReply = new MonitorGetStackUsageReply();
         monitorReply.pid = reply.pid;
         monitorReply.main = new MonitorStackInfo();
         monitorReply.main.address = reply.stackStart;
         monitorReply.main.size = reply.stackEnd - reply.stackStart;
         monitorReply.main.used = reply.max;
         monitorReply.main.bufsize = 0;
         monitorReply.supervisor = new MonitorStackInfo();

         sendMonitorReply(monitorReply);
      }
      else
      {
         // Send endmark if the last signal is sent.
         MonitorGetStackUsageEndmark endmark = new MonitorGetStackUsageEndmark();
         endmark.status = MonitorStatus.MONITOR_STATUS_OK;

         sendMonitorReply(endmark);
      }
   }

   public void handleGetSignalQueueRequest(MonitorGetSignalQueueRequest request)
   {
      if (request.scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         send(new DBGGetSignalQueue(0));
      }
      else
      {
         send(new DBGGetSignalQueue(request.scopeId));
      }
   }

   void getSignalQueueReply(DBGGetSignalQueueReply reply)
   {
      MonitorGetSignalQueueReply monitorReply = new MonitorGetSignalQueueReply();
      monitorReply.pid = reply.pid;
      monitorReply.signal = new MonitorSignalInfo();
      monitorReply.signal.number = reply.signalNumber;
      monitorReply.signal.owner = 0;
      monitorReply.signal.sender = reply.from;
      monitorReply.signal.addressee = reply.addressee;
      monitorReply.signal.size = reply.signalSize;
      monitorReply.signal.bufsize = 0;
      monitorReply.signal.address = 0;
      monitorReply.signal.status = Monitor.MONITOR_SIGNAL_STATUS_UNKNOWN;

      sendMonitorReply(monitorReply);
   }

   void infoQueueEndMark(DBGInfoQueueEndMark reply)
   {
      MonitorGetSignalQueueEndmark endmark = new MonitorGetSignalQueueEndmark();
      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(endmark);
   }

   public void handleGetPoolInfoRequest(MonitorGetPoolInfoRequest request)
   {
      send(new DBGGetPoolInfo(request.scopeId));
   }

   void getPoolInfoReply(DBGGetPoolInfoReply reply)
   {
      MonitorGetPoolInfoReply monitorReply = new MonitorGetPoolInfoReply();
      monitorReply.pid = reply.pid;
      monitorReply.sid = 0;
      monitorReply.total = reply.totSize;
      monitorReply.free = reply.freeSize;
      monitorReply.stackAlignment = 32;
      monitorReply.stackAdmSize = 0;
      monitorReply.stackInternalAdmSize = 1;
      monitorReply.signalAlignment = 32;
      monitorReply.signalAdmSize = 33;
      monitorReply.signalInternalAdmSize = 1;
      monitorReply.fragments = new MonitorPoolFragmentInfo[reply.frBaseAddr.length];
      for (int i = 0; i < reply.frBaseAddr.length; i++)
      {
         monitorReply.fragments[i] = new MonitorPoolFragmentInfo();
         monitorReply.fragments[i].address = reply.frBaseAddr[i];
         monitorReply.fragments[i].size = reply.frSize[i];
         monitorReply.fragments[i].stacks = reply.frUsedStk[i];
         monitorReply.fragments[i].signals = reply.frUsedSig[i];
      }
      monitorReply.stackSizes = new MonitorPoolBufferSizeInfo[reply.stkAllocSizes.length];
      for (int i = 0; i < reply.stkAllocSizes.length; i++)
      {
         monitorReply.stackSizes[i] = new MonitorPoolBufferSizeInfo();
         monitorReply.stackSizes[i].size = reply.stkConfSizes[i];
         monitorReply.stackSizes[i].allocated = reply.stkAllocSizes[i];
         monitorReply.stackSizes[i].free = reply.stkFreeSizes[i];
      }
      monitorReply.signalSizes = new MonitorPoolBufferSizeInfo[reply.sigAllocSizes.length];
      for (int i = 0; i < reply.sigAllocSizes.length; i++)
      {
         monitorReply.signalSizes[i] = new MonitorPoolBufferSizeInfo();
         monitorReply.signalSizes[i].size = reply.sigConfSizes[i];
         monitorReply.signalSizes[i].allocated = reply.sigAllocSizes[i];
         monitorReply.signalSizes[i].free = reply.sigFreeSizes[i];
      }

      sendMonitorReply(monitorReply);
   }

   void infoPoolEndMark(DBGInfoPoolEndMark reply)
   {
      MonitorGetPoolInfoEndmark endmark = new MonitorGetPoolInfoEndmark();
      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(endmark);
   }

   public void handleGetPoolSignalsRequest(MonitorGetPoolSignalsRequest request)
   {
      // FIXME: Handle filter tags.
      send(new DBGGetPoolSignals(request.scopeId, ""));
   }

   void getPoolSignalsReply(DBGGetPoolSignalsReply reply)
   {
      MonitorGetPoolSignalsReply monitorReply = new MonitorGetPoolSignalsReply();
      monitorReply.pid = reply.pid;
      if (reply.data != null)
      {
         monitorReply.signals = new MonitorSignalInfo[reply.data.length / 8];
         for (int i = 0; i < reply.data.length; i += 8)
         {
            MonitorSignalInfo signalInfo = new MonitorSignalInfo();
            signalInfo.number = reply.data[i]; // sig_no
            signalInfo.owner = reply.data[i + 1]; // owner
            signalInfo.sender = reply.data[i + 2]; // sender
            signalInfo.addressee = reply.data[i + 3]; // addresse
            signalInfo.size = reply.data[i + 4]; // size
            signalInfo.bufsize = reply.data[i + 5]; // size in pool
            signalInfo.address = reply.data[i + 6]; // signal pointer
            signalInfo.status = reply.data[i + 7]; // signal status
            monitorReply.signals[i / 8] = signalInfo;
         }
      }
      else
      {
         monitorReply.signals = new MonitorSignalInfo[0];
      }

      sendMonitorReply(monitorReply);
   }

   void poolSignalsEndMark(DBGPoolSignalsEndMark reply)
   {
      MonitorGetPoolSignalsEndmark endmark = new MonitorGetPoolSignalsEndmark();
      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(endmark);
   }

   public void handleStartScopeRequest(MonitorStartScopeRequest request)
   {
      send(new DBGStartProcess(request.scopeId));

      MonitorStartScopeReply monitorReply = new MonitorStartScopeReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      sendMonitorReply(monitorReply);
   }

   public void handleStopScopeRequest(MonitorStopScopeRequest request)
   {
      send(new DBGStopProcess(request.scopeId));

      MonitorStopScopeReply monitorReply = new MonitorStopScopeReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      sendMonitorReply(monitorReply);
   }

   public void handleKillScopeRequest(MonitorKillScopeRequest request)
   {
      send(new DBGKillProcess(request.scopeId));

      MonitorKillScopeReply monitorReply = new MonitorKillScopeReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      sendMonitorReply(monitorReply);
   }

   public void handleGetMemoryRequest(MonitorGetMemoryRequest request)
   {
      memoryGetRequestPid = request.pid;
      memoryGetRequestAddress = request.address;
      memoryGetRequestSize = request.size;
      memoryGetRequestReceivedBytes = 0;

      if (request.size > MAX_BYTES_IN_MEM_REQUEST)
      {
         send(new DBGReadMemory(request.pid, request.address, MAX_BYTES_IN_MEM_REQUEST));
      }
      else
      {
         send(new DBGReadMemory(request.pid, request.address, request.size));
      }
   }

   void readMemoryReply(DBGReadMemoryReply reply)
   {
      MonitorGetMemoryReply monitorReply = new MonitorGetMemoryReply();
      monitorReply.pid = memoryGetRequestPid;
      monitorReply.address = memoryGetRequestAddress;
      monitorReply.data = reply.bytes;

      sendMonitorReply(monitorReply);

      // Increase the address with the number of read bytes.
      memoryGetRequestAddress += reply.bytes.length;
      memoryGetRequestReceivedBytes += reply.bytes.length;

      if ((memoryGetRequestReceivedBytes >= memoryGetRequestSize) ||
          (reply.status == 0))
      {
         // Received the last reply.
         MonitorGetMemoryEndmark endmark = new MonitorGetMemoryEndmark();
         endmark.status = (reply.status == (byte) 1) ? 0 : 1;

         sendMonitorReply(endmark);
      }
      else
      {
         if ((memoryGetRequestSize - memoryGetRequestReceivedBytes) > MAX_BYTES_IN_MEM_REQUEST)
         {
            send(new DBGReadMemory(memoryGetRequestPid, memoryGetRequestAddress,
                  MAX_BYTES_IN_MEM_REQUEST));
         }
         else
         {
            send(new DBGReadMemory(memoryGetRequestPid, memoryGetRequestAddress,
                  memoryGetRequestSize - memoryGetRequestReceivedBytes));
         }
      }
   }

   /*
    * monitor_execution.sig
    */

   public void handleSetMemoryRequest(MonitorSetMemoryRequest request)
   {
      memorySetRequestPid = request.pid;
      memorySetRequestAddress = request.address;
      memorySetRequestSize = request.data.length;

      send(new DBGWriteMemory(request.pid, request.address, request.data));
   }

   void writeMemoryReply(DBGWriteMemoryReply reply)
   {
      MonitorSetMemoryReply monitorReply = new MonitorSetMemoryReply();
      monitorReply.status = reply.status;
      monitorReply.pid = memorySetRequestPid;
      monitorReply.address = memorySetRequestAddress;
      monitorReply.size = memorySetRequestSize;

      sendMonitorReply(monitorReply);
   }

   public void handleGetRegisterInfoRequest(MonitorGetRegisterInfoRequest request)
   {
      int numRegisters = request.registers.length / 2;
      int[] registers = new int[numRegisters];
      for (int i = 0; i < numRegisters; i++)
      {
         registers[i] = request.registers[i * 2];
      }

      send(new DBGReadRegister(request.scopeId, registers));
   }

   public void handleGetRegistersRequest(MonitorGetRegistersRequest request)
   {
      int[] registers;
      registerGetRequestPid = request.pid;
      if (request.registers != null)
      {
         int numRegisters = request.registers.length;
         registers = new int[numRegisters * 2];
         for (int i = 0; i < numRegisters; i++)
         {
            registers[i * 2] = request.registers[i];
            registers[(i * 2) + 1] = 0;
         }
      }
      else
      {
         registers = new int[0];
      }

      send(new DBGReadRegister(request.pid, registers));
   }

   void readRegistersReply(DBGReadRegisterReply reply)
   {
      MonitorGetRegistersReply monitorReply = new MonitorGetRegistersReply();
      monitorReply.pid = registerGetRequestPid;
      monitorReply.status = reply.status;
      monitorReply.registers = reply.regVal;
      for (int i = 0; i < monitorReply.registers.length; i += 2)
      {
         monitorReply.registers[i] = monitorReply.registers[i] & 0x7FFFFFFF;
      }

      sendMonitorReply(monitorReply);
   }

   public void handleSetRegistersRequest(MonitorSetRegistersRequest request)
   {
      registerSetRequestPid = request.pid;
      send(new DBGWriteRegister(request.pid, request.registers));
   }

   void writeRegisterReply(DBGWriteRegisterReply reply)
   {
      MonitorSetRegistersReply monitorReply = new MonitorSetRegistersReply();
      monitorReply.pid = registerSetRequestPid;
      monitorReply.status = reply.status;
      if (reply.regVal != null)
      {
         int numRegisters = reply.regVal.length / 2;
         monitorReply.registers = new int[numRegisters];
         for (int i = 0; i < numRegisters; i++)
         {
            // Need a work around in order to get rid of signing problems.
            monitorReply.registers[i] = reply.regVal[i * 2] & 0x7FFFFFFF;
         }
      }

      sendMonitorReply(monitorReply);
   }

   public void handleAttachRequest(MonitorAttachRequest request)
   {
      String scopeString = createScopeString(request.scopeType, request.scopeId);

      send(new DBGSetApplicationScope(scopeString.getBytes()));
   }

   void setApplicationScopeReply(DBGSetApplicationScopeReply reply)
   {
      MonitorAttachReply monitorReply = new MonitorAttachReply();
      monitorReply.status = reply.status;
      monitorReply.processCacheCount = 1; // Needs to be 1, crash if 0.

      sendMonitorReply(monitorReply);
   }

   public void handleDetachRequest(MonitorDetachRequest request)
   {
      send(new DBGDisconnect());

      MonitorDetachReply monitorReply = new MonitorDetachReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(monitorReply);
   }

   public void handleInterceptScopeRequest(MonitorInterceptScopeRequest request)
   {
      String scopeString = createScopeString(request.scopeType, request.scopeId);

      send(new DBGInterceptScope(scopeString.getBytes()));
   }

   void interceptScopeReply(DBGInterceptScopeReply reply)
   {
      MonitorInterceptScopeReply monitorReply = new MonitorInterceptScopeReply();
      monitorReply.status = reply.status;

      sendMonitorReply(monitorReply);
   }

   public void handleResumeScopeRequest(MonitorResumeScopeRequest request)
   {
      String scopeString = createScopeString(request.scopeType, request.scopeId);

      send(new DBGResumeScope(scopeString.getBytes()));
   }

   void resumeScopeReply(DBGResumeScopeReply reply)
   {
      MonitorResumeScopeReply monitorReply = new MonitorResumeScopeReply();
      monitorReply.status = reply.status;

      sendMonitorReply(monitorReply);
   }

   public void handleSetBreakpointRequest(MonitorSetBreakpointRequest request)
   {
      String scopeString = createScopeString(request.scopeType, request.scopeId)
            + createScopeString(request.interceptType, request.interceptId);
      breakpointReq = new DBGSetBp(request.address, 0, new int[17],
            scopeString.getBytes());
      breakpointReqOngoing = true;

      // The bid of the process is not included in the request. Need to get that
      // before a request is sent.
      send(new DBGGetProcessInfo(0, request.pid));
   }

   void setBpReply(DBGSetBpReply reply)
   {
      MonitorSetBreakpointReply monitorReply = new MonitorSetBreakpointReply();
      monitorReply.status = reply.status;
      monitorReply.breakpointId = reply.bpid;

      sendMonitorReply(monitorReply);
   }

   public void handleClearBreakpointRequest(
         MonitorClearBreakpointRequest request)
   {
      send(new DBGClearBp(request.breakpointId));
   }

   void clearBpReply(DBGClearBpReply reply)
   {
      MonitorClearBreakpointReply monitorReply = new MonitorClearBreakpointReply();
      monitorReply.status = reply.status;

      sendMonitorReply(monitorReply);
   }

   void bpFired(DBGBpFired reply)
   {
      MonitorBreakpointFiredNotify monitorReply = new MonitorBreakpointFiredNotify();
      monitorReply.pid = reply.pid;
      monitorReply.id = reply.bpid;

      sendMonitorReply(monitorReply);
   }

   public void handleStepProcessRequest(MonitorStepProcessRequest request)
   {
      // Type: 1 step over, 0 step into.
      send(new DBGStepProcess(request.pid, request.mode, request.start, request.end));
   }

   void stepProcessComplete(DBGStepProcessComplete reply)
   {
      MonitorStepProcessNotify monitorReply = new MonitorStepProcessNotify();
      monitorReply.pid = reply.pid;
      monitorReply.status = reply.status;
      monitorReply.pc = reply.pc;

      sendMonitorReply(monitorReply);
   }

   /*
    * monitor_cpu.sig
    */

   public void handleGetCPUReportsEnabledRequest(
         MonitorGetCPUReportsEnabledRequest request)
   {
      send(new DBGGetCPUMeasurementStatus());
   }

   void getCPUMeasurementStatusReply(DBGGetCPUMeasurementStatusReply reply)
   {
      MonitorGetCPUReportsEnabledReply monitorReply =
         new MonitorGetCPUReportsEnabledReply();
      monitorReply.enabled = (reply.measurementStatus == 1);
      if (tickLength != 0)
      {
         // Integration interval in microseconds, valid if enabled.
         monitorReply.interval = reply.uInterval / tickLength;
      }
      else
      {
         monitorReply.interval = 0;
      }
      monitorReply.priority = CPU_PRIORITY_LIMIT;
      monitorReply.maxReports = CPU_MAX_REPORTS;
      monitorReply.sec = 0;
      monitorReply.sectick = reply.currentTimeTick;
      monitorReply.secntick = reply.currentTimeUTick;

      sendMonitorReply(monitorReply);
   }

   public void handleSetCPUReportsEnabledRequest(
         MonitorSetCPUReportsEnabledRequest request)
   {
      MonitorSetCPUReportsEnabledReply monitorReply =
         new MonitorSetCPUReportsEnabledReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(monitorReply);
   }

   public void handleGetCPUReportsRequest(MonitorGetCPUReportsRequest request)
   {
      send(new DBGGetCPULoadReports(request.tick, request.ntick,
            (short) CPU_MAX_REPORTS));
   }

   void getCPULoadReportsReply(DBGGetCPULoadReportsReply reply)
   {
      MonitorGetCPUReportsReply monitorReply = new MonitorGetCPUReportsReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      monitorReply.enabled = (reply.measurementStatus == 1);
      monitorReply.euId = 0;
      if (reply.reports != null)
      {
         int numReports = reply.reports.length / 4;
         monitorReply.reports = new MonitorCPUReport[numReports];
         for (int i = 0; i < reply.reports.length; i += 4)
         {
            monitorReply.reports[i / 4] = new MonitorCPUReport();
            monitorReply.reports[i / 4].tick = reply.reports[i];
            monitorReply.reports[i / 4].ntick = reply.reports[i + 1];
            monitorReply.reports[i / 4].sum = reply.reports[i + 2];
            monitorReply.reports[i / 4].interval = reply.reports[i + 3];
         }
      }
      else
      {
         monitorReply.reports = new MonitorCPUReport[0];
      }

      sendMonitorReply(monitorReply);
   }

   public void handleGetCPUPriReportsEnabledRequest(
         MonitorGetCPUPriReportsEnabledRequest request)
   {
      send(new DBGGetPriMeasurementStatus());
   }

   void getPriMeasurementStatusReply(DBGGetPriMeasurementStatusReply reply)
   {
      MonitorGetCPUPriReportsEnabledReply monitorReply =
         new MonitorGetCPUPriReportsEnabledReply();
      monitorReply.enabled = (reply.measurementStatus == 1);
      monitorReply.interval = cpuPriIntegrationInterval;
      monitorReply.maxReports = CPU_PRI_MAX_REPORTS;
      monitorReply.sec = 0;
      monitorReply.sectick = reply.currentTimeTick;
      monitorReply.secntick = reply.currentTimeUTick;

      sendMonitorReply(monitorReply);
   }

   public void handleSetCPUPriReportsEnabledRequest(
         MonitorSetCPUPriReportsEnabledRequest request)
   {
      cpuPriIntegrationInterval = request.interval;

      send(new DBGSetPriMeasurementStatus(request.enabled ? 1 : 0, request.interval));
   }

   void setPriMeasurementStatusReply(DBGSetPriMeasurementStatusReply reply)
   {
      MonitorSetCPUPriReportsEnabledReply monitorReply =
         new MonitorSetCPUPriReportsEnabledReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(monitorReply);
   }

   public void handleGetCPUPriReportsRequest(
         MonitorGetCPUPriReportsRequest request)
   {
      send(new DBGGetPriLoadReports(request.tick, request.ntick,
            (short) CPU_PRI_MAX_REPORTS));
   }

   void getPriLoadReportsReply(DBGGetPriLoadReportsReply reply)
   {
      MonitorGetCPUPriReportsReply monitorReply =
         new MonitorGetCPUPriReportsReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      monitorReply.enabled = (reply.measurementStatus == 1);
      monitorReply.euId = 0;
      if (reply.reports != null)
      {
         int numReports = reply.reports.length / 37;
         monitorReply.reports = new MonitorCPUPriReport[numReports];
         for (int i = 0; i < reply.reports.length; i += 37)
         {
            monitorReply.reports[i / 37] = new MonitorCPUPriReport();
            monitorReply.reports[i / 37].tick = reply.reports[i];
            monitorReply.reports[i / 37].ntick = reply.reports[i + 1];
            monitorReply.reports[i / 37].sumInterrupt = reply.reports[i + 2];
            monitorReply.reports[i / 37].sumBackground = reply.reports[i + 3];
            monitorReply.reports[i / 37].interval = reply.reports[i + 4];
            monitorReply.reports[i / 37].sumPrioritized = new int[32];
            for (int j = 0; j < 32; j++)
            {
               monitorReply.reports[i / 37].sumPrioritized[j] =
                  reply.reports[i + 5 + j];
            }
         }
      }
      else
      {
         monitorReply.reports = new MonitorCPUPriReport[0];
      }

      sendMonitorReply(monitorReply);
   }

   public void handleGetCPUProcessReportsEnabledRequest(
         MonitorGetCPUProcessReportsEnabledRequest request)
   {
      send(new DBGGetProcMeasurementStatus());
   }

   void getProcMeasurementStatusReply(DBGGetProcMeasurementStatusReply reply)
   {
      MonitorGetCPUProcessReportsEnabledReply monitorReply =
         new MonitorGetCPUProcessReportsEnabledReply();
      monitorReply.enabled = (reply.measurementStatus == 1);
      monitorReply.interval = cpuProcessIntegrationInteval;
      monitorReply.maxReports = CPU_PROCESS_MAX_REPORTS;
      monitorReply.maxProcsReport = CPU_PROCESS_MAX_REPORTS_PROCESS;
      monitorReply.sec = 0;
      monitorReply.sectick = reply.currentTimeTick;
      monitorReply.secntick = reply.currentTimeUTick;

      sendMonitorReply(monitorReply);
   }

   public void handleSetCPUProcessReportsEnabledRequest(
         MonitorSetCPUProcessReportsEnabledRequest request)
   {
      cpuProcessIntegrationInteval = request.interval;
      byte procSpec[] = new byte[0];

      send(new DBGSetProcMeasurementStatus(request.enabled ? 1 : 0,
            request.interval, procSpec));
   }

   void setProcMeasurementStatusReply(DBGSetProcMeasurementStatusReply reply)
   {
      MonitorSetCPUProcessReportsEnabledReply monitorReply =
         new MonitorSetCPUProcessReportsEnabledReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;

      sendMonitorReply(monitorReply);
   }

   public void handleGetCPUProcessReportsRequest(
         MonitorGetCPUProcessReportsRequest request)
   {
      send(new DBGGetProcLoadReports(request.tick, request.ntick,
            (short) CPU_PROCESS_MAX_REPORTS_PROCESS));
   }

   void getProcLoadReportsReply(DBGGetProcLoadReportsReply reply)
   {
      MonitorGetCPUProcessReportsReply monitorReply =
         new MonitorGetCPUProcessReportsReply();
      monitorReply.status = MonitorStatus.MONITOR_STATUS_OK;
      monitorReply.enabled = (reply.measurementStatus == 1);
      monitorReply.euId = 0;
      if (reply.reports != null)
      {
         int numReports = reply.reports.length / 20;
         monitorReply.reports = new MonitorCPUProcessReport[numReports];
         for (int i = 0; i < reply.reports.length; i += 20)
         {
            monitorReply.reports[i / 20] = new MonitorCPUProcessReport();
            monitorReply.reports[i / 20].tick = reply.reports[i];
            monitorReply.reports[i / 20].ntick = reply.reports[i + 1];
            monitorReply.reports[i / 20].sumOther = reply.reports[i + 2];
            monitorReply.reports[i / 20].interval = reply.reports[i + 3];
            monitorReply.reports[i / 20].processesCount = 8;
            monitorReply.reports[i / 20].processes =
               new MonitorCPUProcess[monitorReply.reports[i / 20].processesCount];
            for (int j = 0; j < monitorReply.reports[i / 20].processesCount; j++)
            {
               MonitorCPUProcess process = new MonitorCPUProcess();
               process.id = reply.reports[i + 4 + j];
               process.sum = reply.reports[i + 4 + j + 8];
               monitorReply.reports[i / 20].processes[j] = process;
            }
         }
      }
      else
      {
         monitorReply.reports = new MonitorCPUProcessReport[0];
      }

      sendMonitorReply(monitorReply);
   }

   /*
    * pm.sig
    */

   public void handleLoadModuleInstallHandlesRequest(
         PmLoadModuleInstallHandlesRequest request)
   {
      pmLoadModuleInstallHandlesRequestIsActive = true;

      send(new DBGGetProgramInfo());
   }

   public void handleLoadModuleInfoRequest(PmLoadModuleInfoRequest request)
   {
      pmLoadModuleInfoRequestIsActive = true;
      pmInstallHandle = request.install_handle;

      send(new DBGGetProgramInfo());
   }

   public void handleLoadModuleSectionInfoRequest(
         PmLoadModuleSectionInfoRequest request)
   {
      pmLoadModuleSectionInfoRequestIsActive = true;
      pmLoadModuleSectionIntervalStart = request.section_interval_start;
      pmLoadModuleSectionIntervalEnd = request.section_interval_end;
      pmInstallHandle = request.install_handle;

      send(new DBGGetProgramInfo());
   }

   // FIXME: Implement this.
   public void handleProgramsRequest(PmProgramsRequest request)
   {
      PmProgramsReply reply = new PmProgramsReply();
      reply.status = 0;

      sendProgramManagerReply(reply);
   }

   public void handleProgramInfoRequest(PmProgramInfoRequest request)
   {
      pmProgramInfoRequestIsActive = true;
      pmProgramInfoRequestProgPid = request.progpid;

      send(new DBGGetProgramInfo());
   }

   public void handleGetProgramPidRequest(PmGetProgramPidRequest request)
   {
      pmGetProgramPidRequestIsActive = true;
      pmGetProgramPidRequestBid = 0;
      pmGetProgramPidRequestPid = request.pid;

      // Need to know what block the process belongs to.
      send(new DBGGetProcessInfo(0, request.pid));
   }

   void getProgramInfoReply(DBGGetProgramInfoReply reply)
   {
      if (pmLoadModuleInstallHandlesRequestIsActive)
      {
         if (pmLoadModuleInstallHandlesList == null)
         {
            pmLoadModuleInstallHandlesList = new ArrayList<String>();
         }
         if (reply.name != null)
         {
            pmLoadModuleInstallHandlesList.add(truncate(reply.name));
         }
      }
      else if (pmLoadModuleInfoRequestIsActive &&
            reply.name.endsWith(pmInstallHandle))
      {
         pmLoadModuleInfoReply = new PmLoadModuleInfoReply();
         pmLoadModuleInfoReply.status = reply.status;
         pmLoadModuleInfoReply.install_handle = truncate(reply.name);
         pmLoadModuleInfoReply.entrypoint = reply.entrypoint;
         pmLoadModuleInfoReply.options = 0;
         pmLoadModuleInfoReply.no_of_instances = 1;
         pmLoadModuleInfoReply.file_format = "ELF";
         pmLoadModuleInfoReply.file_name = reply.name;

         String sectionName = "";
         int sectionNumber = 0;
         byte lastbyte = 1;

         for (int i = 0; i < reply.sectionNames.length; i++)
         {
            if (reply.sectionNames[i] == 46)
            {
               // New text.
               sectionName = "";
               sectionNumber++;
            }
            else if (reply.sectionNames[i] == 0)
            {
               if (sectionName.equals("text"))
               {
                  pmLoadModuleInfoReply.text_base = reply.section[(sectionNumber - 1) * 3];
                  pmLoadModuleInfoReply.text_size = reply.section[(sectionNumber - 1) * 3 + 1];
               }
               if (sectionName.equals("data"))
               {
                  pmLoadModuleInfoReply.data_base = reply.section[(sectionNumber - 1) * 3];
                  pmLoadModuleInfoReply.data_size = reply.section[(sectionNumber - 1) * 3 + 1];
               }
               // Last in segment.
               if (lastbyte == 0)
               {
                  break;
               }
            }
            else
            {
               try
               {
                  sectionName = sectionName.concat(new String(
                        reply.sectionNames, i, 1, "US-ASCII"));
               } catch (Exception ignore) {}
            }
            lastbyte = reply.sectionNames[i];
         }

         pmLoadModuleInfoReply.no_of_sections = sectionNumber;
      }
      else if (pmLoadModuleSectionInfoRequestIsActive &&
               reply.name.endsWith(pmInstallHandle))
      {
         pmLoadModuleSectionInfoReply = new PmLoadModuleSectionInfoReply();
         pmLoadModuleSectionInfoReply.status = reply.status;
         pmLoadModuleSectionInfoReply.install_handle = truncate(reply.name);
         pmLoadModuleSectionInfoReply.section_interval_start = pmLoadModuleSectionIntervalStart;
         pmLoadModuleSectionInfoReply.section_interval_end = pmLoadModuleSectionIntervalEnd;
         pmLoadModuleSectionInfoReply.sections = new LoadModuleSectionInfo[
            pmLoadModuleSectionIntervalEnd - pmLoadModuleSectionIntervalStart + 1];

         String sectionName = "";
         int sectionNumber = 0;
         byte lastbyte = 1;

         for (int i = 0; i < reply.sectionNames.length; i++)
         {
            if (reply.sectionNames[i] == 46)
            {
               // New text.
               sectionName = "";
               sectionNumber++;
            }
            if (reply.sectionNames[i] == 0)
            {
               // Last in segment.
               if (lastbyte == 0)
               {
                  break;
               }
               if ((sectionNumber > pmLoadModuleSectionIntervalStart) &&
                   (sectionNumber <= pmLoadModuleSectionIntervalEnd + 1))
               {
                  LoadModuleSectionInfo info = new LoadModuleSectionInfo();
                  info.section_base = reply.section[(sectionNumber - 1) * 3];
                  info.section_size = reply.section[(sectionNumber - 1) * 3 + 1];
                  info.section_attr = 0;
                  info.section_options = 1; // PM_SECTION_LOAD
                  info.section_name = sectionName;
                  pmLoadModuleSectionInfoReply.sections[sectionNumber - 1] = info;
               }
            }
            else
            {
               try
               {
                  sectionName = sectionName.concat(new String(
                        reply.sectionNames, i, 1, "US-ASCII"));
               } catch (Exception ignore) {}
            }
            lastbyte = reply.sectionNames[i];
         }
      }
      else if (pmProgramInfoRequestIsActive &&
               (pmProgramInfoRequestProgPid == reply.sid))
      {
         pmProgramInfoReply = new PmProgramInfoReply();
         pmProgramInfoReply.status = reply.status;
         pmProgramInfoReply.install_handle = truncate(reply.name);
         pmProgramInfoReply.progpid = reply.sid;
         pmProgramInfoReply.domain = 0;
         pmProgramInfoReply.main_block = reply.bid;
         pmProgramInfoReply.main_process = reply.pid;
         pmProgramInfoReply.pool_base = 0;
         pmProgramInfoReply.pool_size = 0;
         pmProgramInfoReply.uid = reply.uid;
         pmProgramInfoReply.state = reply.prgStatus;
      }
      else if (pmGetProgramPidRequestIsActive &&
            (pmGetProgramPidRequestBid == reply.bid))
      {
         pmGetProgramPidReply = new PmGetProgramPidReply();
         pmGetProgramPidReply.status = reply.status;
         pmGetProgramPidReply.pid = pmGetProgramPidRequestPid;
         pmGetProgramPidReply.progpid = reply.sid;
      }
   }

   void infoProgramEndMark(DBGInfoProgramEndMark reply)
   {
      if (pmLoadModuleInstallHandlesRequestIsActive)
      {
         PmLoadModuleInstallHandlesReply pmReply =
            new PmLoadModuleInstallHandlesReply();
         pmReply.status = 0;
         if (pmLoadModuleInstallHandlesList != null)
         {
            String[] installHandles =
               new String[pmLoadModuleInstallHandlesList.size()];
            for (int i = 0; i < installHandles.length; i++)
            {
               installHandles[i] = pmLoadModuleInstallHandlesList.get(i);
            }
            pmReply.install_handles = installHandles;
            pmLoadModuleInstallHandlesList.clear();
            pmLoadModuleInstallHandlesList = null;
         }
         else
         {
            pmReply.install_handles = null;
         }

         pmLoadModuleInstallHandlesRequestIsActive = false;
         sendProgramManagerReply(pmReply);
      }
      else if (pmLoadModuleInfoRequestIsActive)
      {
         if (pmLoadModuleInfoReply == null)
         {
            pmLoadModuleInfoReply = new PmLoadModuleInfoReply();
            pmLoadModuleInfoReply.status = 0;
            pmLoadModuleInfoReply.install_handle = pmInstallHandle;
            pmLoadModuleInfoReply.entrypoint = 0;
            pmLoadModuleInfoReply.options = 0;
            pmLoadModuleInfoReply.text_base = 0;
            pmLoadModuleInfoReply.text_size = 0;
            pmLoadModuleInfoReply.data_base = 0;
            pmLoadModuleInfoReply.data_size = 0;
            pmLoadModuleInfoReply.no_of_sections = 0;
            pmLoadModuleInfoReply.no_of_instances = 1;
            pmLoadModuleInfoReply.file_format = "ELF";
            pmLoadModuleInfoReply.file_name = pmInstallHandle;
         }

         pmLoadModuleInfoRequestIsActive = false;
         sendProgramManagerReply(pmLoadModuleInfoReply);
         pmLoadModuleInfoReply = null;
      }
      else if (pmLoadModuleSectionInfoRequestIsActive)
      {
         if (pmLoadModuleSectionInfoReply == null)
         {
            pmLoadModuleSectionInfoReply = new PmLoadModuleSectionInfoReply();
            pmLoadModuleSectionInfoReply.status = 0;
            pmLoadModuleSectionInfoReply.install_handle = pmInstallHandle;
            pmLoadModuleSectionInfoReply.section_interval_start = 0;
            pmLoadModuleSectionInfoReply.section_interval_end = 0;
            pmLoadModuleSectionInfoReply.sections = new LoadModuleSectionInfo[0];
         }

         pmLoadModuleSectionInfoRequestIsActive = false;
         sendProgramManagerReply(pmLoadModuleSectionInfoReply);
         pmLoadModuleSectionInfoReply = null;
      }
      else if (pmProgramInfoRequestIsActive)
      {
         if (pmProgramInfoReply == null)
         {
            pmProgramInfoReply = new PmProgramInfoReply();
            pmProgramInfoReply.status = 0;
            pmProgramInfoReply.install_handle = pmInstallHandle;
            pmProgramInfoReply.progpid = 0;
            pmProgramInfoReply.domain = 0;
            pmProgramInfoReply.main_block = 0;
            pmProgramInfoReply.main_process = 0;
            pmProgramInfoReply.pool_base = 0;
            pmProgramInfoReply.pool_size = 0;
            pmProgramInfoReply.uid = 0;
            pmProgramInfoReply.state = 0;
         }

         pmProgramInfoRequestIsActive = false;
         sendProgramManagerReply(pmProgramInfoReply);
         pmProgramInfoReply = null;
      }
      else if (pmGetProgramPidRequestIsActive)
      {
         if (pmGetProgramPidReply == null)
         {
            pmGetProgramPidReply = new PmGetProgramPidReply();
            pmGetProgramPidReply.status = 0;
            pmGetProgramPidReply.pid = pmGetProgramPidRequestPid;
            pmGetProgramPidReply.progpid = 0;
         }

         pmGetProgramPidRequestIsActive = false;
         sendProgramManagerReply(pmGetProgramPidReply);
         pmGetProgramPidReply = null;
      }
   }

   private static String createScopeString(int scopeType, int scopeId)
   {
      String scopeString = "\0\0";

      if (scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         String hexString = Integer.toHexString(scopeId);
         scopeString = "#0x" + hexString + "\0\0";
      }
      else if (scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         String hexString = Integer.toHexString(scopeId);
         scopeString = "#0x" + hexString + ":*\0\0";
      }
      else if (scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         scopeString = "*:*:*\0\0";
      }

      return scopeString;
   }

   private static String truncate(String s)
   {
      if (s.length() > 31)
      {
         s = s.substring(s.length() - 31);
      }
      return s;
   }

   /* Send a message to the target or, if we have outstanding requests, queue
    * the message.
    *
    * @param msg  the message to be sent to the target.
    */
   private void send(Message msg)
   {
      if (connected)
      {
         targetWriter.write(msg);
      }
   }

   private void sendMonitorReply(Signal reply)
   {
      for (AbstractGatewayService gs : callbackList)
      {
         if (gs instanceof MonitorGatewayService)
         {
            ((MonitorGatewayService) gs).sendMonitorReply(reply, clientPid);
         }
      }
   }

   private void sendProgramManagerReply(Signal reply)
   {
      for (AbstractGatewayService gs : callbackList)
      {
         if (gs instanceof ProgramManagerGatewayService)
         {
            ((ProgramManagerGatewayService) gs).sendProgramManagerReply(
                  reply, clientPid);
         }
      }
   }

   public String toString()
   {
      return getName();
   }
}