/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.service.monitor;

import java.io.IOException;
import java.io.InterruptedIOException;
import com.ose.gateway.Gateway;
import com.ose.gateway.GatewayException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.SignalSelect;
import com.ose.system.service.monitor.signal.MonitorAllocInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorAllocNotify;
import com.ose.system.service.monitor.signal.MonitorAttachReply;
import com.ose.system.service.monitor.signal.MonitorAttachRequest;
import com.ose.system.service.monitor.signal.MonitorAttachSignal;
import com.ose.system.service.monitor.signal.MonitorBindInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorBindNotify;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlRequest;
import com.ose.system.service.monitor.signal.MonitorClearCPUProcessReportpointReply;
import com.ose.system.service.monitor.signal.MonitorClearCPUProcessReportpointRequest;
import com.ose.system.service.monitor.signal.MonitorClearEventActionpointReply;
import com.ose.system.service.monitor.signal.MonitorClearEventActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorClearTraceReply;
import com.ose.system.service.monitor.signal.MonitorClearTraceRequest;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorCounterReportsLossNotify;
import com.ose.system.service.monitor.signal.MonitorCounterReportsNotify;
import com.ose.system.service.monitor.signal.MonitorCreateInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorCreateNotify;
import com.ose.system.service.monitor.signal.MonitorDetachReply;
import com.ose.system.service.monitor.signal.MonitorDetachRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorErrorInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorErrorNotify;
import com.ose.system.service.monitor.signal.MonitorFreeBufInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorFreeBufNotify;
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
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportpointInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportpointInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCounterReportsNotifyEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCounterReportsNotifyEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCreateActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsRequest;
import com.ose.system.service.monitor.signal.MonitorGetErrorActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetEventActionpointInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEventActionpointInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetEventActionpointsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEventActionpointsReply;
import com.ose.system.service.monitor.signal.MonitorGetEventActionpointsRequest;
import com.ose.system.service.monitor.signal.MonitorGetEventStateReply;
import com.ose.system.service.monitor.signal.MonitorGetEventStateRequest;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetHeapBuffersEndmark;
import com.ose.system.service.monitor.signal.MonitorGetHeapBuffersReply;
import com.ose.system.service.monitor.signal.MonitorGetHeapBuffersRequest;
import com.ose.system.service.monitor.signal.MonitorGetHeapFragmentInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetHeapFragmentInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetHeapFragmentInfo64Reply;
import com.ose.system.service.monitor.signal.MonitorGetHeapFragmentInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetHeapFragmentInfo64Request;
import com.ose.system.service.monitor.signal.MonitorGetHeapInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetHeapInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetHeapInfo64Reply;
import com.ose.system.service.monitor.signal.MonitorGetHeapInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetHeapInfo64Request;
import com.ose.system.service.monitor.signal.MonitorGetKillActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetLossTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetMemoryEndmark;
import com.ose.system.service.monitor.signal.MonitorGetMemoryReply;
import com.ose.system.service.monitor.signal.MonitorGetMemory64Reply;
import com.ose.system.service.monitor.signal.MonitorGetMemoryRequest;
import com.ose.system.service.monitor.signal.MonitorGetMemory64Request;
import com.ose.system.service.monitor.signal.MonitorGetNotifyEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetNotifyEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfo64Reply;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfo64Request;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsReply;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpEndmark;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpReply;
import com.ose.system.service.monitor.signal.MonitorGetRamdumpRequest;
import com.ose.system.service.monitor.signal.MonitorGetReceiveActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetReceiveTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetResetTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetSendActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetSendTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueRequest;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageEndmark;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageReply;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageRequest;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesReply;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesRequest;
import com.ose.system.service.monitor.signal.MonitorGetSwapActionpointInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetSwapTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSysParamEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSysParamReply;
import com.ose.system.service.monitor.signal.MonitorGetSysParamRequest;
import com.ose.system.service.monitor.signal.MonitorGetTimeoutTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetTraceEndmark;
import com.ose.system.service.monitor.signal.MonitorGetTraceMultipleReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceMultipleRequest;
import com.ose.system.service.monitor.signal.MonitorGetTraceRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserTraceReply;
import com.ose.system.service.monitor.signal.MonitorHuntSignal;
import com.ose.system.service.monitor.signal.MonitorInterceptScopeReply;
import com.ose.system.service.monitor.signal.MonitorInterceptScopeRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorKillInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorKillNotify;
import com.ose.system.service.monitor.signal.MonitorKillScopeReply;
import com.ose.system.service.monitor.signal.MonitorKillScopeRequest;
import com.ose.system.service.monitor.signal.MonitorLossNotify;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;
import com.ose.system.service.monitor.signal.MonitorNonBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorNonBlockingFlowControlRequest;
import com.ose.system.service.monitor.signal.MonitorObsoleteConnectReply;
import com.ose.system.service.monitor.signal.MonitorObsoleteConnectRequest;
import com.ose.system.service.monitor.signal.MonitorObsoleteFlowControlRequest;
import com.ose.system.service.monitor.signal.MonitorProcessInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorProcessNotify;
import com.ose.system.service.monitor.signal.MonitorReceiveInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorReceiveNotify;
import com.ose.system.service.monitor.signal.MonitorResumeScopeReply;
import com.ose.system.service.monitor.signal.MonitorResumeScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSendInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorSendNotify;
import com.ose.system.service.monitor.signal.MonitorSetAllocActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetBindActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUBlockReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUBlockReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUPriReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportpointReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCounterReportsNotifyEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCounterReportsNotifyEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCounterTypeEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetCounterTypeEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCreateActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetEnvVarReply;
import com.ose.system.service.monitor.signal.MonitorSetEnvVarRequest;
import com.ose.system.service.monitor.signal.MonitorSetErrorActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetEventActionpointReply;
import com.ose.system.service.monitor.signal.MonitorSetEventStateReply;
import com.ose.system.service.monitor.signal.MonitorSetEventStateRequest;
import com.ose.system.service.monitor.signal.MonitorSetExecutionUnitReply;
import com.ose.system.service.monitor.signal.MonitorSetExecutionUnitRequest;
import com.ose.system.service.monitor.signal.MonitorSetFreeBufActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetInterceptScopeReply;
import com.ose.system.service.monitor.signal.MonitorSetInterceptScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSetKillActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetNotifyEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetNotifyEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetPriorityReply;
import com.ose.system.service.monitor.signal.MonitorSetPriorityRequest;
import com.ose.system.service.monitor.signal.MonitorSetReceiveActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetScopeReply;
import com.ose.system.service.monitor.signal.MonitorSetScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSetSendActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetSwapActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetSysParamReply;
import com.ose.system.service.monitor.signal.MonitorSetSysParamRequest;
import com.ose.system.service.monitor.signal.MonitorSetTimeoutActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetTraceEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetTraceEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetUserActionpointRequest;
import com.ose.system.service.monitor.signal.MonitorSetUserReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorSetUserReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSignalSemaphoreReply;
import com.ose.system.service.monitor.signal.MonitorSignalSemaphoreRequest;
import com.ose.system.service.monitor.signal.MonitorStartScopeReply;
import com.ose.system.service.monitor.signal.MonitorStartScopeRequest;
import com.ose.system.service.monitor.signal.MonitorStopScopeReply;
import com.ose.system.service.monitor.signal.MonitorStopScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSwapInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorSwapNotify;
import com.ose.system.service.monitor.signal.MonitorTimeoutInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorTimeoutNotify;
import com.ose.system.service.monitor.signal.MonitorUserInterceptNotify;
import com.ose.system.service.monitor.signal.MonitorUserNotify;

public class Monitor
{
   /* Signal overhead compensation for obsolete flow control. */
   public static final int MONITOR_OBSOLETE_MIN_FLOW_CONTROL = 96;

   /* Monitor type names. */
   public static final String MONITOR_NAME_RUN_MODE = "com.ose.RunModeMonitor";
   public static final String MONITOR_NAME_FREEZE_MODE = "com.ose.FreezeModeMonitor";
   public static final String MONITOR_NAME_POST_MORTEM = "com.ose.PostMortemMonitor";

   /* Monitor features. */
   public static final short MONITOR_FEATURE_SCOPE = 1;
   public static final short MONITOR_FEATURE_INTERCEPT_SCOPE = 2;
   public static final short MONITOR_FEATURE_AUTOMATIC_SCOPE = 3;
   public static final short MONITOR_FEATURE_COMPACT_REGISTER_SIGNALS = 4;
   public static final short MONITOR_FEATURE_SYSTEM_PROCESSES = 5;
   public static final short MONITOR_FEATURE_PROCESS_FILTER = 11;
   public static final short MONITOR_FEATURE_BLOCK_FILTER = 12;
   public static final short MONITOR_FEATURE_SEGMENT_FILTER = 13;
   public static final short MONITOR_FEATURE_SIGNAL_FILTER = 14;
   public static final short MONITOR_FEATURE_STACK_FILTER = 15;
   public static final short MONITOR_FEATURE_COMPACT_INFO_SIGNALS = 16;
   public static final short MONITOR_FEATURE_EVENT_USER_ACTION = 17;
   public static final short MONITOR_FEATURE_EVENT_UNDO = 18;
   public static final short MONITOR_FEATURE_PROF_PRIORITY_THRESHOLD = 19;
   public static final short MONITOR_FEATURE_PROF_ALL_EXECUTION_UNITS = 20;
   public static final short MONITOR_FEATURE_CLEAR_ALL_EVENT_ACTIONPOINTS = 21;
   public static final short MONITOR_FEATURE_TOTAL_CPU_BLOCK_MEASURE = 22;
   public static final short MONITOR_FEATURE_SEGMENT_INFO_LONG = 24;
   public static final short MONITOR_FEATURE_PER_CORE_PROFILED_PROCESSES = 25;
   public static final short MONITOR_FEATURE_64_BIT = 27;
   public static final short MONITOR_FEATURE_ACTIONPOINT_DATA_RANGE = 28; 


   /* OS types. */
   public static final int MONITOR_OS_OSE_5 = 0;
   public static final int MONITOR_OS_OSE_4 = 1;
   public static final int MONITOR_OS_OSE_EPSILON = 2;
   public static final int MONITOR_OS_OSE_CK = 3;
   public static final int MONITOR_OS_UNKNOWN = 0x7FFFFFFF;

   /* CPU classes. */
   public static final int MONITOR_CPU_PPC = 0;
   public static final int MONITOR_CPU_M68K = 1;
   public static final int MONITOR_CPU_ARM = 2;
   public static final int MONITOR_CPU_MIPS = 3;
   public static final int MONITOR_CPU_X86 = 4;
   public static final int MONITOR_CPU_SPARC = 5;
   public static final int MONITOR_CPU_C64X = 6;
   public static final int MONITOR_CPU_C64XP = 7;
   public static final int MONITOR_CPU_SP26XX = 8;
   public static final int MONITOR_CPU_SP27XX = 9;
   public static final int MONITOR_CPU_TL3 = 10;
   public static final int MONITOR_CPU_CEVAX = 11;
   public static final int MONITOR_CPU_M8144 = 12;
   public static final int MONITOR_CPU_M8156 = 13;
   public static final int MONITOR_CPU_C66X = 14;
   public static final int MONITOR_CPU_C674X = 15;
   public static final int MONITOR_CPU_UNKNOWN = 0x7FFFFFFF;

   /* Process types. */
   public static final int MONITOR_PROCESS_TYPE_PRIORITIZED = 0x0;
   public static final int MONITOR_PROCESS_TYPE_BACKGROUND = 0x40;
   public static final int MONITOR_PROCESS_TYPE_INTERRUPT = 0x80;
   public static final int MONITOR_PROCESS_TYPE_TIMER_INTERRUPT = 0x100;
   public static final int MONITOR_PROCESS_TYPE_PHANTOM = 0x200;
   public static final int MONITOR_PROCESS_TYPE_SIGNAL_PORT = 0x400;
   public static final int MONITOR_PROCESS_TYPE_KILLED = 0x800;
   public static final int MONITOR_PROCESS_TYPE_UNKNOWN = 0x1000;
   public static final int MONITOR_PROCESS_TYPE_IDLE = 0x10000;

   /* Process states. */
   public static final int MONITOR_PROCESS_STATE_RECEIVE = 0x1;
   public static final int MONITOR_PROCESS_STATE_DELAY = 0x2;
   public static final int MONITOR_PROCESS_STATE_SEMAPHORE = 0x4;
   public static final int MONITOR_PROCESS_STATE_FAST_SEMAPHORE = 0x8;
   public static final int MONITOR_PROCESS_STATE_REMOTE = 0x10;
   public static final int MONITOR_PROCESS_STATE_STOPPED = 0x20;
   public static final int MONITOR_PROCESS_STATE_INTERCEPTED = 0x40;
   public static final int MONITOR_PROCESS_STATE_MUTEX = 0x80;
   public static final int MONITOR_PROCESS_STATE_RUNNING = 0x400;
   public static final int MONITOR_PROCESS_STATE_READY = 0x800;
   public static final int MONITOR_PROCESS_STATE_KILLED = 0xFFFF;

   /* Signal status values. */
   public static final int MONITOR_SIGNAL_STATUS_UNKNOWN = 0;
   public static final int MONITOR_SIGNAL_STATUS_VALID = 1;
   public static final int MONITOR_SIGNAL_STATUS_ENDMARK = 2;
   public static final int MONITOR_SIGNAL_STATUS_ADMINISTRATION = 3;
   public static final int MONITOR_SIGNAL_STATUS_NOT_A_SIGNAL = 4;
   public static final int MONITOR_SIGNAL_STATUS_NOT_A_POOL = 5;
   public static final int MONITOR_SIGNAL_STATUS_FREE = 6;

   /* Heap buffer status values. */
   public static final int MONITOR_HEAP_BUFFER_STATUS_UNKNOWN = 0;
   public static final int MONITOR_HEAP_BUFFER_STATUS_VALID = 1;
   public static final int MONITOR_HEAP_BUFFER_STATUS_ENDMARK = 2;

   /* Scope types. */
   public static final int MONITOR_SCOPE_SYSTEM = 0x00;
   public static final int MONITOR_SCOPE_NAME_PATTERN = 0x01;
   public static final int MONITOR_SCOPE_ENTRYPOINT = 0x02;
   public static final int MONITOR_SCOPE_SEGMENT_ID = 0x08;
   public static final int MONITOR_SCOPE_BLOCK_NAME = 0x10;
   public static final int MONITOR_SCOPE_BLOCK_ID = 0x20;
   public static final int MONITOR_SCOPE_PROCESS_NAME = 0x40;
   public static final int MONITOR_SCOPE_ID = 0x80;
   public static final int MONITOR_SCOPE_VOID = 0x100;

   /* Scope tags. */
   public static final short MONITOR_SCOPE_TAG_ENTRYPOINT = 1;
   public static final short MONITOR_SCOPE_TAG_NAME = 2;

   /* Signal filter tags. */
   public static final short MONITOR_SIGNAL_FILTER_TAG_NOT = 10;
   public static final short MONITOR_SIGNAL_FILTER_TAG_NUMBER = 11;
   public static final short MONITOR_SIGNAL_FILTER_TAG_OWNER_ID = 12;
   public static final short MONITOR_SIGNAL_FILTER_TAG_OWNER_NAME = 13;
   public static final short MONITOR_SIGNAL_FILTER_TAG_SENDER_ID = 14;
   public static final short MONITOR_SIGNAL_FILTER_TAG_SENDER_NAME = 15;
   public static final short MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_ID = 16;
   public static final short MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_NAME = 17;
   public static final short MONITOR_SIGNAL_FILTER_TAG_SIZE = 18;
   public static final short MONITOR_SIGNAL_FILTER_TAG_BUFSIZE = 19;
   public static final short MONITOR_SIGNAL_FILTER_TAG_ADDRESS = 20;
   public static final short MONITOR_SIGNAL_FILTER_TAG_STATUS = 21;

   /* Stack filter tags. */
   public static final short MONITOR_STACK_FILTER_TAG_NOT = 100;
   public static final short MONITOR_STACK_FILTER_TAG_OWNER_ID = 101;
   public static final short MONITOR_STACK_FILTER_TAG_OWNER_NAME = 102;
   public static final short MONITOR_STACK_FILTER_TAG_SIZE = 103;
   public static final short MONITOR_STACK_FILTER_TAG_BUFSIZE = 104;
   public static final short MONITOR_STACK_FILTER_TAG_ADDRESS = 105;
   public static final short MONITOR_STACK_FILTER_TAG_USED = 106;
   public static final short MONITOR_STACK_FILTER_TAG_UNUSED = 107;
   public static final short MONITOR_STACK_FILTER_TAG_SUPERVISOR_SIZE = 108;
   public static final short MONITOR_STACK_FILTER_TAG_SUPERVISOR_BUFSIZE = 109;
   public static final short MONITOR_STACK_FILTER_TAG_SUPERVISOR_ADDRESS = 110;
   public static final short MONITOR_STACK_FILTER_TAG_SUPERVISOR_USED = 111;
   public static final short MONITOR_STACK_FILTER_TAG_SUPERVISOR_UNUSED = 112;

   /* Process filter tags. */
   public static final short MONITOR_PROCESS_FILTER_TAG_NOT = 50;
   public static final short MONITOR_PROCESS_FILTER_TAG_TYPE = 51;
   public static final short MONITOR_PROCESS_FILTER_TAG_STATE = 52;
   public static final short MONITOR_PROCESS_FILTER_TAG_PRIORITY = 53;
   public static final short MONITOR_PROCESS_FILTER_TAG_UID = 54;
   public static final short MONITOR_PROCESS_FILTER_TAG_CREATOR_ID = 55;
   public static final short MONITOR_PROCESS_FILTER_TAG_SUPERVISOR = 56;
   public static final short MONITOR_PROCESS_FILTER_TAG_SIGNALS_IN_QUEUE = 57;
   public static final short MONITOR_PROCESS_FILTER_TAG_SIGNALS_ATTACHED = 58;
   public static final short MONITOR_PROCESS_FILTER_TAG_ENTRYPOINT = 59;
   public static final short MONITOR_PROCESS_FILTER_TAG_SEMAPHORE_VALUE = 60;
   public static final short MONITOR_PROCESS_FILTER_TAG_STACK_SIZE = 61;
   public static final short MONITOR_PROCESS_FILTER_TAG_SUPERVISOR_STACK_SIZE = 62;
   public static final short MONITOR_PROCESS_FILTER_TAG_LINE_NUMBER = 63;
   public static final short MONITOR_PROCESS_FILTER_TAG_SIGNALS_OWNED = 64;
   public static final short MONITOR_PROCESS_FILTER_TAG_TIME_SLICE = 65;
   public static final short MONITOR_PROCESS_FILTER_TAG_VECTOR = 66;
   public static final short MONITOR_PROCESS_FILTER_TAG_ERROR_HANDLER = 67;
   public static final short MONITOR_PROCESS_FILTER_TAG_SIGSELECT_COUNT = 68;
   public static final short MONITOR_PROCESS_FILTER_TAG_NAME = 69;
   public static final short MONITOR_PROCESS_FILTER_TAG_FILE_NAME = 70;
   public static final short MONITOR_PROCESS_FILTER_TAG_SYSTEM = 71;
   public static final short MONITOR_PROCESS_FILTER_TAG_EU_ID = 72;

   /* Block filter tags. */
   public static final short MONITOR_BLOCK_FILTER_TAG_NOT = 75;
   public static final short MONITOR_BLOCK_FILTER_TAG_UID = 76;
   public static final short MONITOR_BLOCK_FILTER_TAG_SUPERVISOR = 77;
   public static final short MONITOR_BLOCK_FILTER_TAG_SIGNALS_ATTACHED = 78;
   public static final short MONITOR_BLOCK_FILTER_TAG_ERROR_HANDLER = 79;
   public static final short MONITOR_BLOCK_FILTER_TAG_MAX_SIG_SIZE = 80;
   public static final short MONITOR_BLOCK_FILTER_TAG_SIG_POOL_ID = 81;
   public static final short MONITOR_BLOCK_FILTER_TAG_STACK_POOL_ID = 82;
   public static final short MONITOR_BLOCK_FILTER_TAG_NAME = 83;
   public static final short MONITOR_BLOCK_FILTER_TAG_EU_ID = 84;

   /* Heap buffer filter tags. */
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_NOT = 200;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_ID = 201;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_OWNER_NAME = 202;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_SHARED = 203;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_ADDRESS = 204;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_SIZE = 205;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_REQ_SIZE = 206;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_FILE_NAME = 207;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_LINE_NUMBER = 208;
   public static final short MONITOR_HEAP_BUFFER_FILTER_TAG_STATUS = 209;

   /* Attach tags. */
   public static final short MONITOR_ATTACH_TAG_EVENT_LOCK = 1010;
   public static final short MONITOR_ATTACH_TAG_KEEP_EVENT_TRACEPOINTS = 1011;
   public static final short MONITOR_ATTACH_TAG_EVENT_SIGSELECT = 1012;

   /* Events. */
   public static final int MONITOR_EVENT_CREATE = 1;
   public static final int MONITOR_EVENT_KILL = 2;
   public static final int MONITOR_EVENT_SWAP = 3;
   public static final int MONITOR_EVENT_SEND = 4;
   public static final int MONITOR_EVENT_RECEIVE = 5;
   public static final int MONITOR_EVENT_RESET = 10;
   public static final int MONITOR_EVENT_ERROR = 11;
   public static final int MONITOR_EVENT_LOSS = 12;
   public static final int MONITOR_EVENT_USER = 13;
   public static final int MONITOR_EVENT_SIGSELECT = 14;
   public static final int MONITOR_EVENT_SEMAPHORE_SIGNAL = 15;
   public static final int MONITOR_EVENT_SEMAPHORE_WAIT = 16;
   public static final int MONITOR_EVENT_SEMAPHORE = 17;
   public static final int MONITOR_EVENT_MUTEX_LOCK = 18;
   public static final int MONITOR_EVENT_MUTEX_UNLOCK = 19;
   public static final int MONITOR_EVENT_MUTEX = 20;
   public static final int MONITOR_EVENT_BIND = 21;
   public static final int MONITOR_EVENT_ALLOC = 22;
   public static final int MONITOR_EVENT_FREE_BUF = 23;
   public static final int MONITOR_EVENT_TIMEOUT = 24;

   /* Event actions. */
   public static final int MONITOR_ACTION_INTERCEPT = 1;
   public static final int MONITOR_ACTION_TRACE = 3;
   public static final int MONITOR_ACTION_ENABLE_TRACE = 4;
   public static final int MONITOR_ACTION_DISABLE_TRACE = 5;
   public static final int MONITOR_ACTION_SET_STATE = 9;
   public static final int MONITOR_ACTION_NOTIFY = 10;
   public static final int MONITOR_ACTION_COUNT = 11;
   public static final int MONITOR_ACTION_USER = 12;
   public static final int MONITOR_ACTION_ENABLE_HW_TRACE = 14;
   public static final int MONITOR_ACTION_DISABLE_HW_TRACE = 15;

   /* Event actionpoint types.
    * Used to identify the type of event actionpoint
    * returned by MonitorListener.getEventActionpointsReply().
    */
   public static final int MONITOR_EVENT_ACTIONPOINT_CREATE =
      MonitorSetCreateActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_KILL =
      MonitorSetKillActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_SWAP =
      MonitorSetSwapActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_SEND =
      MonitorSetSendActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_RECEIVE =
      MonitorSetReceiveActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_ERROR =
      MonitorSetErrorActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_USER =
      MonitorSetUserActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_BIND =
      MonitorSetBindActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_ALLOC =
      MonitorSetAllocActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_FREE_BUF =
      MonitorSetFreeBufActionpointRequest.SIG_NO;
   public static final int MONITOR_EVENT_ACTIONPOINT_TIMEOUT =
      MonitorSetTimeoutActionpointRequest.SIG_NO;

   /* Event actionpoint tags. */
   public static final short MONITOR_EVENT_ACTIONPOINT_TAG_IGNORE = 2000;
   public static final short MONITOR_EVENT_ACTIONPOINT_TAG_REMOVE = 2001;
   public static final short MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE = 2002;
   public static final short MONITOR_EVENT_ACTIONPOINT_TAG_DATA_RANGE_NOT = 2003; 

   /* Event actionpoint parameters. */
   public static final int MONITOR_EVENT_ACTIONPOINT_PARAMETER_FILE = 1;
   public static final int MONITOR_EVENT_ACTIONPOINT_PARAMETER_DATA = 2;

   /* Error event filter tags. */
   public static final short MONITOR_ERROR_FILTER_TAG_NOT = 2010;
   public static final short MONITOR_ERROR_FILTER_TAG_EXEC = 2011;
   public static final short MONITOR_ERROR_FILTER_TAG_ERROR = 2012;
   public static final short MONITOR_ERROR_FILTER_TAG_EXTRA = 2013;

   /* User event filter tags. */
   public static final short MONITOR_USER_FILTER_TAG_NOT = 2020;
   public static final short MONITOR_USER_FILTER_TAG_NUMBER = 2021;

   /* Performance counter report types. */
   public static final int MONITOR_COUNTER_REPORT = 0;
   public static final int MONITOR_COUNTER_REPORT_EXT = 1;

   /* Value representing all execution units when profiling. */
   public static final short MONITOR_ALL_EXECUTION_UNITS = (short) 0xFFFF;

   /* Name of the monitor process. */
   public static final String MONITOR_NAME = "ose_monitor";

   /* Name of this monitor client. */
   private static final String MONITOR_CLIENT_NAME = "ose_tools_monitor_client";

   /* Hunt/interface/name/connect timeout in milliseconds. */
   private static final int TIMEOUT = 10000;

   /* Monitor creation state. */
   private final MonitorListener listener;
   private final Gateway senderGateway;
   private final Gateway receiverGateway;
   private final SignalRegistry registry;
   private final String monitorPath;

   /* Monitor connection state. */
   private int monitorPid;
   private int receiverPid;
   private int attref;
   private int obsoleteFlowControl;
   private int obsoleteFlowControlThreshold;
   private ReceiverThread receiverThread;
   private volatile boolean connected;

   /* Monitor interface info. */
   private boolean bigEndian;
   private int osType;
   private int cpuClass;

   /* Monitor capabilities. */
   private boolean hasObsoleteConnectRequest;
   private boolean hasObsoleteFlowControlRequest;
   private boolean hasGetProcessInfoRequest;
   private boolean hasGetBlockInfoRequest;
   private boolean hasGetSegmentInfoRequest;
   private boolean hasGetStackUsageRequest;
   private boolean hasGetEnvVarsRequest;
   private boolean hasGetSignalQueueRequest;
   private boolean hasGetPoolInfoRequest;
   private boolean hasGetPoolSignalsRequest;
   private boolean hasGetHeapInfoRequest;
   private boolean hasGetHeapFragmentInfoRequest;
   private boolean hasGetHeapBuffersRequest;
   private boolean hasGetSysParamRequest;
   private boolean hasSetSysParamRequest;
   private boolean hasGetMemoryRequest;
   private boolean hasKillScopeRequest;
   private boolean hasStartScopeRequest;
   private boolean hasStopScopeRequest;
   private boolean hasSetEnvVarRequest;
   private boolean hasSignalSemaphoreRequest;
   private boolean hasSetPriorityRequest;
   private boolean hasSetExecutionUnitRequest;
   private boolean hasGetRamdumpInfoRequest;
   private boolean hasGetRamdumpRequest;
   private boolean hasGetCPUReportsEnabledRequest;
   private boolean hasSetCPUReportsEnabledRequest;
   private boolean hasGetCPUReportsRequest;
   private boolean hasGetCPUPriReportsEnabledRequest;
   private boolean hasSetCPUPriReportsEnabledRequest;
   private boolean hasGetCPUPriReportsRequest;
   private boolean hasGetCPUProcessReportsEnabledRequest;
   private boolean hasSetCPUProcessReportsEnabledRequest;
   private boolean hasSetCPUProcessReportpointRequest;
   private boolean hasClearCPUProcessReportpointRequest;
   private boolean hasGetCPUProcessReportpointInfoRequest;
   private boolean hasGetCPUProcessReportsRequest;
   private boolean hasGetCPUBlockReportsEnabledRequest;
   private boolean hasSetCPUBlockReportsEnabledRequest;
   private boolean hasGetCPUBlockReportsRequest;
   private boolean hasGetUserReportsEnabledRequest;
   private boolean hasSetUserReportsEnabledRequest;
   private boolean hasGetUserReportsRequest;
   private boolean hasGetSupportedCounterTypesRequest;
   private boolean hasGetCounterTypeEnabledRequest;
   private boolean hasSetCounterTypeEnabledRequest;
   private boolean hasGetCounterReportsNotifyEnabledRequest;
   private boolean hasSetCounterReportsNotifyEnabledRequest;
   private boolean hasAttachRequest;
   private boolean hasDetachRequest;
   private boolean hasInterceptScopeRequest;
   private boolean hasResumeScopeRequest;
   private boolean hasSetScopeRequest;
   private boolean hasSetInterceptScopeRequest;
   private boolean hasSetSendActionpointRequest;
   private boolean hasSetReceiveActionpointRequest;
   private boolean hasSetSwapActionpointRequest;
   private boolean hasSetCreateActionpointRequest;
   private boolean hasSetKillActionpointRequest;
   private boolean hasSetErrorActionpointRequest;
   private boolean hasSetUserActionpointRequest;
   private boolean hasSetBindActionpointRequest;
   private boolean hasSetAllocActionpointRequest;
   private boolean hasSetFreeBufActionpointRequest;
   private boolean hasSetTimeoutActionpointRequest;
   private boolean hasClearEventActionpointRequest;
   private boolean hasGetEventActionpointInfoRequest;
   private boolean hasGetEventActionpointsRequest;
   private boolean hasGetEventStateRequest;
   private boolean hasSetEventStateRequest;
   private boolean hasGetNotifyEnabledRequest;
   private boolean hasSetNotifyEnabledRequest;
   private boolean hasGetTraceEnabledRequest;
   private boolean hasSetTraceEnabledRequest;
   private boolean hasClearTraceRequest;
   private boolean hasGetTraceRequest;
   private boolean hasGetTraceMultipleRequest;
   private boolean hasGetPoolInfo64Request;
   private boolean hasGetHeapFragmentInfo64Request;
   private boolean hasGetHeapInfo64Request;
   private boolean hasGetMemory64Request;
   

   /* Monitor name info. */
   private String systemName;
   private String monitorName;

   /* Monitor connection info. */
   private int tickLength;
   private int tick;
   private int ntick;
   private int ntickFrequency;
   private int obsoleteFlowCtrlCount;
   private int cpuType;
   private int errorHandler;
   private short numExecutionUnits;
   private short[] features;

   /* Monitor features. */
   private boolean hasProfAllExecutionUnitsFeature;
   private boolean hasClearAllEventActionpointsFeature;
   private boolean hasSegmentInfoLongFeature;
   private boolean hasPerCoreProfiledProcessesFeature;
   private boolean hasSignalDataFilterFeature;
   private boolean has64BitFeature;

   public Monitor(MonitorListener listener,
                  Gateway senderGateway,
                  String huntPath)
      throws IOException, GatewayException
   {
      SignalSelect wanted;
      Signal signal;
      int monitorMainPid;
      MonitorInterfaceReply interfaceReply;
      MonitorNameReply nameReply;

      if (listener == null)
      {
         throw new NullPointerException();
      }
      if (senderGateway == null)
      {
         throw new NullPointerException();
      }
      if (huntPath == null)
      {
         throw new NullPointerException();
      }

      this.listener = listener;
      this.senderGateway = senderGateway;

      // Create the monitor receiver gateway.
      receiverGateway = new Gateway(senderGateway.getName(),
            senderGateway.getAddress(), senderGateway.getPort());

      // Initialize the monitor signal registry.
      registry = new SignalRegistry();
      registry.add(MonitorAllocInterceptNotify.class);
      registry.add(MonitorAllocNotify.class);
      registry.add(MonitorAttachReply.class);
      registry.add(MonitorAttachRequest.class);
      registry.add(MonitorAttachSignal.class);
      registry.add(MonitorBindInterceptNotify.class);
      registry.add(MonitorBindNotify.class);
      registry.add(MonitorBlockingFlowControlReply.class);
      registry.add(MonitorBlockingFlowControlRequest.class);
      registry.add(MonitorClearCPUProcessReportpointReply.class);
      registry.add(MonitorClearCPUProcessReportpointRequest.class);
      registry.add(MonitorClearEventActionpointReply.class);
      registry.add(MonitorClearEventActionpointRequest.class);
      registry.add(MonitorClearTraceReply.class);
      registry.add(MonitorClearTraceRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorCounterReportsLossNotify.class);
      registry.add(MonitorCounterReportsNotify.class);
      registry.add(MonitorCreateInterceptNotify.class);
      registry.add(MonitorCreateNotify.class);
      registry.add(MonitorDetachReply.class);
      registry.add(MonitorDetachRequest.class);
      registry.add(MonitorDisconnectRequest.class);
      registry.add(MonitorErrorInterceptNotify.class);
      registry.add(MonitorErrorNotify.class);
      registry.add(MonitorFreeBufInterceptNotify.class);
      registry.add(MonitorFreeBufNotify.class);
      registry.add(MonitorGetAllocTraceReply.class);
      registry.add(MonitorGetBindTraceReply.class);
      registry.add(MonitorGetBlockInfoEndmark.class);
      registry.add(MonitorGetBlockInfoReply.class);
      registry.add(MonitorGetBlockInfoRequest.class);
      registry.add(MonitorGetCPUBlockReportsEnabledReply.class);
      registry.add(MonitorGetCPUBlockReportsEnabledRequest.class);
      registry.add(MonitorGetCPUBlockReportsReply.class);
      registry.add(MonitorGetCPUBlockReportsRequest.class);
      registry.add(MonitorGetCPUPriReportsEnabledReply.class);
      registry.add(MonitorGetCPUPriReportsEnabledRequest.class);
      registry.add(MonitorGetCPUPriReportsReply.class);
      registry.add(MonitorGetCPUPriReportsRequest.class);
      registry.add(MonitorGetCPUProcessReportsEnabledReply.class);
      registry.add(MonitorGetCPUProcessReportsEnabledRequest.class);
      registry.add(MonitorGetCPUProcessReportpointInfoEndmark.class);
      registry.add(MonitorGetCPUProcessReportpointInfoReply.class);
      registry.add(MonitorGetCPUProcessReportpointInfoRequest.class);
      registry.add(MonitorGetCPUProcessReportsReply.class);
      registry.add(MonitorGetCPUProcessReportsRequest.class);
      registry.add(MonitorGetCPUReportsEnabledReply.class);
      registry.add(MonitorGetCPUReportsEnabledRequest.class);
      registry.add(MonitorGetCPUReportsReply.class);
      registry.add(MonitorGetCPUReportsRequest.class);
      registry.add(MonitorGetCounterReportsNotifyEnabledReply.class);
      registry.add(MonitorGetCounterReportsNotifyEnabledRequest.class);
      registry.add(MonitorGetCounterTypeEnabledReply.class);
      registry.add(MonitorGetCounterTypeEnabledRequest.class);
      registry.add(MonitorGetCreateActionpointInfoReply.class);
      registry.add(MonitorGetCreateTraceReply.class);
      registry.add(MonitorGetEnvVarsEndmark.class);
      registry.add(MonitorGetEnvVarsReply.class);
      registry.add(MonitorGetEnvVarsRequest.class);
      registry.add(MonitorGetErrorActionpointInfoReply.class);
      registry.add(MonitorGetErrorTraceReply.class);
      registry.add(MonitorGetEventActionpointInfoEndmark.class);
      registry.add(MonitorGetEventActionpointInfoRequest.class);
      registry.add(MonitorGetEventActionpointsEndmark.class);
      registry.add(MonitorGetEventActionpointsReply.class);
      registry.add(MonitorGetEventActionpointsRequest.class);
      registry.add(MonitorGetEventStateReply.class);
      registry.add(MonitorGetEventStateRequest.class);
      registry.add(MonitorGetFreeBufTraceReply.class);
      registry.add(MonitorGetHeapBuffersEndmark.class);
      registry.add(MonitorGetHeapBuffersReply.class);
      registry.add(MonitorGetHeapBuffersRequest.class);
      registry.add(MonitorGetHeapFragmentInfoEndmark.class);
      registry.add(MonitorGetHeapFragmentInfoReply.class);
      registry.add(MonitorGetHeapFragmentInfoRequest.class);
      registry.add(MonitorGetHeapInfoEndmark.class);
      registry.add(MonitorGetHeapInfoReply.class);
      registry.add(MonitorGetHeapInfoRequest.class);
      registry.add(MonitorGetKillActionpointInfoReply.class);
      registry.add(MonitorGetKillTraceReply.class);
      registry.add(MonitorGetLossTraceReply.class);
      registry.add(MonitorGetMemoryEndmark.class);
      registry.add(MonitorGetMemoryReply.class);
      registry.add(MonitorGetMemoryRequest.class);
      registry.add(MonitorGetNotifyEnabledReply.class);
      registry.add(MonitorGetNotifyEnabledRequest.class);
      registry.add(MonitorGetPoolInfoEndmark.class);
      registry.add(MonitorGetPoolInfoReply.class);
      registry.add(MonitorGetPoolInfoRequest.class);
      registry.add(MonitorGetPoolSignalsEndmark.class);
      registry.add(MonitorGetPoolSignalsReply.class);
      registry.add(MonitorGetPoolSignalsRequest.class);
      registry.add(MonitorGetProcessInfoEndmark.class);
      registry.add(MonitorGetProcessInfoLongReply.class);
      registry.add(MonitorGetProcessInfoReply.class);
      registry.add(MonitorGetProcessInfoRequest.class);
      registry.add(MonitorGetProcessTraceReply.class);
      registry.add(MonitorGetRamdumpEndmark.class);
      registry.add(MonitorGetRamdumpReply.class);
      registry.add(MonitorGetRamdumpRequest.class);
      registry.add(MonitorGetRamdumpInfoEndmark.class);
      registry.add(MonitorGetRamdumpInfoReply.class);
      registry.add(MonitorGetRamdumpInfoRequest.class);
      registry.add(MonitorGetReceiveActionpointInfoReply.class);
      registry.add(MonitorGetReceiveTraceReply.class);
      registry.add(MonitorGetResetTraceReply.class);
      registry.add(MonitorGetSegmentInfoEndmark.class);
      registry.add(MonitorGetSegmentInfoLongReply.class);
      registry.add(MonitorGetSegmentInfoReply.class);
      registry.add(MonitorGetSegmentInfoRequest.class);
      registry.add(MonitorGetSendActionpointInfoReply.class);
      registry.add(MonitorGetSendTraceReply.class);
      registry.add(MonitorGetSignalQueueEndmark.class);
      registry.add(MonitorGetSignalQueueLongReply.class);
      registry.add(MonitorGetSignalQueueReply.class);
      registry.add(MonitorGetSignalQueueRequest.class);
      registry.add(MonitorGetStackUsageEndmark.class);
      registry.add(MonitorGetStackUsageReply.class);
      registry.add(MonitorGetStackUsageRequest.class);
      registry.add(MonitorGetSupportedCounterTypesEndmark.class);
      registry.add(MonitorGetSupportedCounterTypesReply.class);
      registry.add(MonitorGetSupportedCounterTypesRequest.class);
      registry.add(MonitorGetSwapActionpointInfoReply.class);
      registry.add(MonitorGetSwapTraceReply.class);
      registry.add(MonitorGetSysParamEndmark.class);
      registry.add(MonitorGetSysParamReply.class);
      registry.add(MonitorGetSysParamRequest.class);
      registry.add(MonitorGetTimeoutTraceReply.class);
      registry.add(MonitorGetTraceEnabledReply.class);
      registry.add(MonitorGetTraceEnabledRequest.class);
      registry.add(MonitorGetTraceEndmark.class);
      registry.add(MonitorGetTraceRequest.class);
      registry.add(MonitorGetTraceMultipleReply.class);
      registry.add(MonitorGetTraceMultipleRequest.class);
      registry.add(MonitorGetUserReportsEnabledReply.class);
      registry.add(MonitorGetUserReportsEnabledRequest.class);
      registry.add(MonitorGetUserReportsReply.class);
      registry.add(MonitorGetUserReportsRequest.class);
      registry.add(MonitorGetUserTraceReply.class);
      registry.add(MonitorHuntSignal.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterceptScopeReply.class);
      registry.add(MonitorInterceptScopeRequest.class);
      registry.add(MonitorKillInterceptNotify.class);
      registry.add(MonitorKillNotify.class);
      registry.add(MonitorKillScopeReply.class);
      registry.add(MonitorKillScopeRequest.class);
      registry.add(MonitorLossNotify.class);
      registry.add(MonitorNameReply.class);
      registry.add(MonitorNameRequest.class);
      registry.add(MonitorNonBlockingFlowControlReply.class);
      registry.add(MonitorNonBlockingFlowControlRequest.class);
      registry.add(MonitorObsoleteConnectReply.class);
      registry.add(MonitorObsoleteConnectRequest.class);
      registry.add(MonitorObsoleteFlowControlRequest.class);
      registry.add(MonitorProcessInterceptNotify.class);
      registry.add(MonitorProcessNotify.class);
      registry.add(MonitorReceiveInterceptNotify.class);
      registry.add(MonitorReceiveNotify.class);
      registry.add(MonitorResumeScopeReply.class);
      registry.add(MonitorResumeScopeRequest.class);
      registry.add(MonitorSendInterceptNotify.class);
      registry.add(MonitorSendNotify.class);
      registry.add(MonitorSetAllocActionpointRequest.class);
      registry.add(MonitorSetBindActionpointRequest.class);
      registry.add(MonitorSetCPUBlockReportsEnabledReply.class);
      registry.add(MonitorSetCPUBlockReportsEnabledRequest.class);
      registry.add(MonitorSetCPUPriReportsEnabledReply.class);
      registry.add(MonitorSetCPUPriReportsEnabledRequest.class);
      registry.add(MonitorSetCPUProcessReportpointReply.class);
      registry.add(MonitorSetCPUProcessReportpointRequest.class);
      registry.add(MonitorSetCPUProcessReportsEnabledReply.class);
      registry.add(MonitorSetCPUProcessReportsEnabledRequest.class);
      registry.add(MonitorSetCPUReportsEnabledReply.class);
      registry.add(MonitorSetCPUReportsEnabledRequest.class);
      registry.add(MonitorSetCounterReportsNotifyEnabledReply.class);
      registry.add(MonitorSetCounterReportsNotifyEnabledRequest.class);
      registry.add(MonitorSetCounterTypeEnabledReply.class);
      registry.add(MonitorSetCounterTypeEnabledRequest.class);
      registry.add(MonitorSetCreateActionpointRequest.class);
      registry.add(MonitorSetEnvVarReply.class);
      registry.add(MonitorSetEnvVarRequest.class);
      registry.add(MonitorSetErrorActionpointRequest.class);
      registry.add(MonitorSetEventActionpointReply.class);
      registry.add(MonitorSetEventStateReply.class);
      registry.add(MonitorSetEventStateRequest.class);
      registry.add(MonitorSetExecutionUnitReply.class);
      registry.add(MonitorSetExecutionUnitRequest.class);
      registry.add(MonitorSetFreeBufActionpointRequest.class);
      registry.add(MonitorSetInterceptScopeReply.class);
      registry.add(MonitorSetInterceptScopeRequest.class);
      registry.add(MonitorSetKillActionpointRequest.class);
      registry.add(MonitorSetNotifyEnabledReply.class);
      registry.add(MonitorSetNotifyEnabledRequest.class);
      registry.add(MonitorSetPriorityReply.class);
      registry.add(MonitorSetPriorityRequest.class);
      registry.add(MonitorSetReceiveActionpointRequest.class);
      registry.add(MonitorSetScopeReply.class);
      registry.add(MonitorSetScopeRequest.class);
      registry.add(MonitorSetSendActionpointRequest.class);
      registry.add(MonitorSetSwapActionpointRequest.class);
      registry.add(MonitorSetSysParamReply.class);
      registry.add(MonitorSetSysParamRequest.class);
      registry.add(MonitorSetTimeoutActionpointRequest.class);
      registry.add(MonitorSetTraceEnabledReply.class);
      registry.add(MonitorSetTraceEnabledRequest.class);
      registry.add(MonitorSetUserActionpointRequest.class);
      registry.add(MonitorSetUserReportsEnabledReply.class);
      registry.add(MonitorSetUserReportsEnabledRequest.class);
      registry.add(MonitorSignalSemaphoreReply.class);
      registry.add(MonitorSignalSemaphoreRequest.class);
      registry.add(MonitorStartScopeReply.class);
      registry.add(MonitorStartScopeRequest.class);
      registry.add(MonitorStopScopeReply.class);
      registry.add(MonitorStopScopeRequest.class);
      registry.add(MonitorSwapInterceptNotify.class);
      registry.add(MonitorSwapNotify.class);
      registry.add(MonitorTimeoutInterceptNotify.class);
      registry.add(MonitorTimeoutNotify.class);
      registry.add(MonitorUserInterceptNotify.class);
      registry.add(MonitorUserNotify.class);
      registry.add(MonitorGetPoolInfo64Request.class);
      registry.add(MonitorGetPoolInfo64Reply.class);
      registry.add(MonitorGetHeapInfo64Request.class);
      registry.add(MonitorGetHeapInfo64Reply.class);
      registry.add(MonitorGetHeapFragmentInfo64Request.class);
      registry.add(MonitorGetHeapFragmentInfo64Reply.class);
      registry.add(MonitorGetMemory64Request.class);
      registry.add(MonitorGetMemory64Reply.class);
      
      // Hunt for the monitor.
      monitorPath = (huntPath.equals("") ?
                     MONITOR_NAME :
                     huntPath.endsWith("/") ?
                     huntPath + MONITOR_NAME :
                     huntPath + "/" + MONITOR_NAME);
      wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                new int[] {MonitorHuntSignal.SIG_NO});
      senderGateway.hunt(monitorPath, new MonitorHuntSignal());
      signal = senderGateway.receive(registry, wanted, TIMEOUT);
      if (signal != null)
      {
         monitorMainPid = signal.getSender();
      }
      else
      {
         throw new IOException("Timed out hunting for monitor.");
      }

      // Get the monitor interface information.
      wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                new int[] {MonitorInterfaceReply.SIG_NO});
      senderGateway.send(monitorMainPid, new MonitorInterfaceRequest());
      interfaceReply =
         (MonitorInterfaceReply) senderGateway.receive(registry, wanted, TIMEOUT);
      if (interfaceReply != null)
      {
         bigEndian = interfaceReply.endian;
         osType = interfaceReply.osType;
         cpuClass = interfaceReply.cpuClass;
      }
      else
      {
         throw new IOException("Timed out receiving monitor interface information.");
      }

      // Determine monitor capabilities.
      determineCapabilities(interfaceReply.sigs);

      // Use target byte order.
      senderGateway.setBigEndian(bigEndian);

      // Get the monitor name information.
      wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                new int[] {MonitorNameReply.SIG_NO});
      senderGateway.send(monitorMainPid, new MonitorNameRequest());
      nameReply = (MonitorNameReply) senderGateway.receive(registry, wanted, TIMEOUT);
      if (nameReply != null)
      {
         systemName = nameReply.systemName;
         monitorName = nameReply.monitorName;
      }
      else
      {
         throw new IOException("Timed out receiving monitor name information.");
      }
   }

   private void determineCapabilities(int[] sigs) throws IOException
   {
      boolean hasInterfaceRequest = false;
      boolean hasNameRequest = false;
      boolean hasConnectRequest = false;
      boolean hasDisconnectRequest = false;

      for (int i = 0; i < sigs.length; i++)
      {
         switch (sigs[i])
         {
            case MonitorInterfaceRequest.SIG_NO:
               hasInterfaceRequest = true;
               break;
            case MonitorNameRequest.SIG_NO:
               hasNameRequest = true;
               break;
            case MonitorConnectRequest.SIG_NO:
               hasConnectRequest = true;
               break;
            case MonitorObsoleteConnectRequest.SIG_NO:
               hasObsoleteConnectRequest = true;
               // Workaround for OSE 5.1.1 bug.
               hasDisconnectRequest = true;
               hasObsoleteFlowControlRequest = true;
               break;
            case MonitorDisconnectRequest.SIG_NO:
               hasDisconnectRequest = true;
               break;
            case MonitorObsoleteFlowControlRequest.SIG_NO:
               hasObsoleteFlowControlRequest = true;
               break;
            case MonitorGetProcessInfoRequest.SIG_NO:
               hasGetProcessInfoRequest = true;
               break;
            case MonitorGetBlockInfoRequest.SIG_NO:
               hasGetBlockInfoRequest = true;
               break;
            case MonitorGetSegmentInfoRequest.SIG_NO:
               hasGetSegmentInfoRequest = true;
               break;
            case MonitorGetStackUsageRequest.SIG_NO:
               hasGetStackUsageRequest = true;
               break;
            case MonitorGetEnvVarsRequest.SIG_NO:
               hasGetEnvVarsRequest = true;
               break;
            case MonitorGetSignalQueueRequest.SIG_NO:
               hasGetSignalQueueRequest = true;
               break;
            case MonitorGetPoolInfoRequest.SIG_NO:
               hasGetPoolInfoRequest = true;
               break;
            case MonitorGetPoolSignalsRequest.SIG_NO:
               hasGetPoolSignalsRequest = true;
               break;
            case MonitorGetHeapInfoRequest.SIG_NO:
               hasGetHeapInfoRequest = true;
               break;
            case MonitorGetHeapFragmentInfoRequest.SIG_NO:
               hasGetHeapFragmentInfoRequest = true;
               break;
            case MonitorGetHeapBuffersRequest.SIG_NO:
               hasGetHeapBuffersRequest = true;
               break;
            case MonitorGetSysParamRequest.SIG_NO:
               hasGetSysParamRequest = true;
               break;
            case MonitorSetSysParamRequest.SIG_NO:
               hasSetSysParamRequest = true;
               break;
            case MonitorGetMemoryRequest.SIG_NO:
               hasGetMemoryRequest = true;
               break;
            case MonitorKillScopeRequest.SIG_NO:
               hasKillScopeRequest = true;
               break;
            case MonitorStartScopeRequest.SIG_NO:
               hasStartScopeRequest = true;
               break;
            case MonitorStopScopeRequest.SIG_NO:
               hasStopScopeRequest = true;
               break;
            case MonitorSetEnvVarRequest.SIG_NO:
               hasSetEnvVarRequest = true;
               break;
            case MonitorSignalSemaphoreRequest.SIG_NO:
               hasSignalSemaphoreRequest = true;
               break;
            case MonitorSetPriorityRequest.SIG_NO:
               hasSetPriorityRequest = true;
               break;
            case MonitorSetExecutionUnitRequest.SIG_NO:
               hasSetExecutionUnitRequest = true;
               break;
            case MonitorGetRamdumpInfoRequest.SIG_NO:
               hasGetRamdumpInfoRequest = true;
               break;
            case MonitorGetRamdumpRequest.SIG_NO:
               hasGetRamdumpRequest = true;
               break;
            case MonitorGetCPUReportsEnabledRequest.SIG_NO:
               hasGetCPUReportsEnabledRequest = true;
               break;
            case MonitorSetCPUReportsEnabledRequest.SIG_NO:
               hasSetCPUReportsEnabledRequest = true;
               break;
            case MonitorGetCPUReportsRequest.SIG_NO:
               hasGetCPUReportsRequest = true;
               break;
            case MonitorGetCPUPriReportsEnabledRequest.SIG_NO:
               hasGetCPUPriReportsEnabledRequest = true;
               break;
            case MonitorSetCPUPriReportsEnabledRequest.SIG_NO:
               hasSetCPUPriReportsEnabledRequest = true;
               break;
            case MonitorGetCPUPriReportsRequest.SIG_NO:
               hasGetCPUPriReportsRequest = true;
               break;
            case MonitorGetCPUProcessReportsEnabledRequest.SIG_NO:
               hasGetCPUProcessReportsEnabledRequest = true;
               break;
            case MonitorSetCPUProcessReportsEnabledRequest.SIG_NO:
               hasSetCPUProcessReportsEnabledRequest = true;
               break;
            case MonitorSetCPUProcessReportpointRequest.SIG_NO:
               hasSetCPUProcessReportpointRequest = true;
               break;
            case MonitorClearCPUProcessReportpointRequest.SIG_NO:
               hasClearCPUProcessReportpointRequest = true;
               break;
            case MonitorGetCPUProcessReportpointInfoRequest.SIG_NO:
               hasGetCPUProcessReportpointInfoRequest = true;
               break;
            case MonitorGetCPUProcessReportsRequest.SIG_NO:
               hasGetCPUProcessReportsRequest = true;
               break;
            case MonitorGetCPUBlockReportsEnabledRequest.SIG_NO:
               hasGetCPUBlockReportsEnabledRequest = true;
               break;
            case MonitorSetCPUBlockReportsEnabledRequest.SIG_NO:
               hasSetCPUBlockReportsEnabledRequest = true;
               break;
            case MonitorGetCPUBlockReportsRequest.SIG_NO:
               hasGetCPUBlockReportsRequest = true;
               break;
            case MonitorGetUserReportsEnabledRequest.SIG_NO:
               hasGetUserReportsEnabledRequest = true;
               break;
            case MonitorSetUserReportsEnabledRequest.SIG_NO:
               hasSetUserReportsEnabledRequest = true;
               break;
            case MonitorGetUserReportsRequest.SIG_NO:
               hasGetUserReportsRequest = true;
               break;
            case MonitorGetSupportedCounterTypesRequest.SIG_NO:
               hasGetSupportedCounterTypesRequest = true;
               break;
            case MonitorGetCounterTypeEnabledRequest.SIG_NO:
               hasGetCounterTypeEnabledRequest = true;
               break;
            case MonitorSetCounterTypeEnabledRequest.SIG_NO:
               hasSetCounterTypeEnabledRequest = true;
               break;
            case MonitorGetCounterReportsNotifyEnabledRequest.SIG_NO:
               hasGetCounterReportsNotifyEnabledRequest = true;
               break;
            case MonitorSetCounterReportsNotifyEnabledRequest.SIG_NO:
               hasSetCounterReportsNotifyEnabledRequest = true;
               break;
            case MonitorAttachRequest.SIG_NO:
               hasAttachRequest = true;
               break;
            case MonitorDetachRequest.SIG_NO:
               hasDetachRequest = true;
               break;
            case MonitorInterceptScopeRequest.SIG_NO:
               hasInterceptScopeRequest = true;
               break;
            case MonitorResumeScopeRequest.SIG_NO:
               hasResumeScopeRequest = true;
               break;
            case MonitorSetScopeRequest.SIG_NO:
               hasSetScopeRequest = true;
               break;
            case MonitorSetInterceptScopeRequest.SIG_NO:
               hasSetInterceptScopeRequest = true;
               break;
            case MonitorSetSendActionpointRequest.SIG_NO:
               hasSetSendActionpointRequest = true;
               break;
            case MonitorSetReceiveActionpointRequest.SIG_NO:
               hasSetReceiveActionpointRequest = true;
               break;
            case MonitorSetSwapActionpointRequest.SIG_NO:
               hasSetSwapActionpointRequest = true;
               break;
            case MonitorSetCreateActionpointRequest.SIG_NO:
               hasSetCreateActionpointRequest = true;
               break;
            case MonitorSetKillActionpointRequest.SIG_NO:
               hasSetKillActionpointRequest = true;
               break;
            case MonitorSetErrorActionpointRequest.SIG_NO:
               hasSetErrorActionpointRequest = true;
               break;
            case MonitorSetUserActionpointRequest.SIG_NO:
               hasSetUserActionpointRequest = true;
               break;
            case MonitorSetBindActionpointRequest.SIG_NO:
               hasSetBindActionpointRequest = true;
               break;
            case MonitorSetAllocActionpointRequest.SIG_NO:
               hasSetAllocActionpointRequest = true;
               break;
            case MonitorSetFreeBufActionpointRequest.SIG_NO:
               hasSetFreeBufActionpointRequest = true;
               break;
            case MonitorSetTimeoutActionpointRequest.SIG_NO:
               hasSetTimeoutActionpointRequest = true;
               break;
            case MonitorClearEventActionpointRequest.SIG_NO:
               hasClearEventActionpointRequest = true;
               break;
            case MonitorGetEventActionpointInfoRequest.SIG_NO:
               hasGetEventActionpointInfoRequest = true;
               break;
            case MonitorGetEventActionpointsRequest.SIG_NO:
               hasGetEventActionpointsRequest = true;
               break;
            case MonitorGetEventStateRequest.SIG_NO:
               hasGetEventStateRequest = true;
               break;
            case MonitorSetEventStateRequest.SIG_NO:
               hasSetEventStateRequest = true;
               break;
            case MonitorGetNotifyEnabledRequest.SIG_NO:
               hasGetNotifyEnabledRequest = true;
               break;
            case MonitorSetNotifyEnabledRequest.SIG_NO:
               hasSetNotifyEnabledRequest = true;
               break;
            case MonitorGetTraceEnabledRequest.SIG_NO:
               hasGetTraceEnabledRequest = true;
               break;
            case MonitorSetTraceEnabledRequest.SIG_NO:
               hasSetTraceEnabledRequest = true;
               break;
            case MonitorClearTraceRequest.SIG_NO:
               hasClearTraceRequest = true;
               break;
            case MonitorGetTraceRequest.SIG_NO:
               hasGetTraceRequest = true;
               break;
            case MonitorGetTraceMultipleRequest.SIG_NO:
                hasGetTraceMultipleRequest = true;
                break;
            case MonitorGetPoolInfo64Request.SIG_NO:
                hasGetPoolInfo64Request = true;
                break;
            case MonitorGetHeapFragmentInfo64Request.SIG_NO:
                hasGetHeapFragmentInfo64Request = true;
                break;
            case MonitorGetHeapInfo64Request.SIG_NO:
                hasGetHeapInfo64Request = true;
                break;
            case MonitorGetMemory64Request.SIG_NO:
                hasGetMemory64Request = true;
                break;    

         }
      }

      if (!hasInterfaceRequest ||
          !hasNameRequest ||
          !(hasConnectRequest || hasObsoleteConnectRequest) ||
          !hasDisconnectRequest)
      {
         throw new IOException("Too limited monitor functionality.");
      }
   }

   private void determineFeatures(short[] features)
   {
      hasProfAllExecutionUnitsFeature = false;
      hasClearAllEventActionpointsFeature = false;
      hasSegmentInfoLongFeature = false;
      hasPerCoreProfiledProcessesFeature = false;

      for (int i = 0; i < features.length; i++)
      {
         switch (features[i])
         {
            case MONITOR_FEATURE_PROF_ALL_EXECUTION_UNITS:
               hasProfAllExecutionUnitsFeature = true;
               break;
            case MONITOR_FEATURE_CLEAR_ALL_EVENT_ACTIONPOINTS:
               hasClearAllEventActionpointsFeature = true;
               break;
            case MONITOR_FEATURE_SEGMENT_INFO_LONG:
               hasSegmentInfoLongFeature = true;
               break;
            case MONITOR_FEATURE_PER_CORE_PROFILED_PROCESSES:
               hasPerCoreProfiledProcessesFeature = true;
               break;
            case MONITOR_FEATURE_ACTIONPOINT_DATA_RANGE:
               hasSignalDataFilterFeature = true;
               break;
            case MONITOR_FEATURE_64_BIT:
                has64BitFeature = true;
                break;
            default:
               break;
         }
      }
   }

   private static void checkSupport(boolean capability)
      throws UnsupportedOperationException
   {
      if (!capability)
      {
         throw new UnsupportedOperationException("Unsupported monitor operation.");
      }
   }

   private void checkState() throws IOException
   {
      if (!connected)
      {
         throw new IOException("Monitor not connected.");
      }
   }

   public void connect() throws IOException, GatewayException
   {
      if (connected)
      {
         return;
      }

      // Connect the monitor receiver gateway.
      receiverGateway.connect(MONITOR_CLIENT_NAME);
      receiverGateway.setBigEndian(bigEndian);
      receiverPid = receiverGateway.getClientPid();

      try
      {
         SignalSelect wanted;
         Signal signal;
         int monitorMainPid;

         // Hunt for the monitor.
         wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                   new int[] {MonitorHuntSignal.SIG_NO});
         receiverGateway.hunt(monitorPath, new MonitorHuntSignal());
         signal = receiverGateway.receive(registry, wanted, TIMEOUT);
         if (signal != null)
         {
            monitorMainPid = signal.getSender();
         }
         else
         {
            throw new IOException("Timed out hunting for monitor.");
         }

         // Attach to the monitor.
         attref = receiverGateway.attach(monitorMainPid, new MonitorAttachSignal());

         // Connect to the monitor.
         if (hasObsoleteConnectRequest)
         {
            MonitorObsoleteConnectRequest request;
            MonitorObsoleteConnectReply reply;

            request = new MonitorObsoleteConnectRequest(MONITOR_CLIENT_NAME);
            receiverGateway.send(monitorMainPid, request);
            wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                      new int[] {MonitorObsoleteConnectReply.SIG_NO});
            reply = (MonitorObsoleteConnectReply)
               receiverGateway.receive(registry, wanted, TIMEOUT);
            if (reply != null)
            {
               if (reply.status == MonitorStatus.MONITOR_STATUS_OK)
               {
                  monitorPid = reply.getSender();
                  tickLength = reply.tickLength;
                  tick = reply.tick;
                  ntick = reply.ntick;
                  obsoleteFlowCtrlCount = reply.flowCtrlCount;
                  cpuType = reply.cpuType;
                  errorHandler = reply.errorHandler;
                  features = reply.features;
                  determineFeatures(features);

                  obsoleteFlowControl = obsoleteFlowCtrlCount;
                  obsoleteFlowControlThreshold = obsoleteFlowCtrlCount / 2;
               }
               else
               {
                  throw new IOException("Monitor connection failed with status: "
                                        + reply.status);
               }
            }
            else
            {
               throw new IOException("Timed out connecting to monitor.");
            }
         }
         else
         {
            MonitorConnectRequest request;
            MonitorConnectReply reply;

            request = new MonitorConnectRequest(MONITOR_CLIENT_NAME);
            receiverGateway.send(monitorMainPid, request);
            wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                      new int[] {MonitorConnectReply.SIG_NO});
            reply = (MonitorConnectReply)
               receiverGateway.receive(registry, wanted, TIMEOUT);
            if (reply != null)
            {
               if (reply.status == MonitorStatus.MONITOR_STATUS_OK)
               {
                  monitorPid = reply.getSender();
                  tickLength = reply.tickLength;
                  tick = reply.tick;
                  ntick = reply.ntick;
                  ntickFrequency = reply.ntickFrequency;
                  cpuType = reply.cpuType;
                  errorHandler = reply.errorHandler;
                  numExecutionUnits = reply.euCount;
                  features = reply.features;
                  determineFeatures(features);
               }
               else
               {
                  throw new IOException("Monitor connection failed with status: "
                                        + reply.status);
               }
            }
            else
            {
               throw new IOException("Timed out connecting to monitor.");
            }
         }
      }
      catch (IOException e)
      {
         try
         {
            receiverGateway.disconnect();
         }
         catch (IOException ignore) {}
         throw e;
      }

      // Start the monitor receiver thread.
      receiverThread = new ReceiverThread();
      receiverThread.start();

      // Connection done.
      connected = true;
   }

   public void disconnect()
   {
      if (!connected)
      {
         return;
      }

      // Disconnect from the monitor.
      try
      {
         senderGateway.send(receiverPid, monitorPid, new MonitorDisconnectRequest());
      }
      catch (IOException e)
      {
         System.err.println("Monitor disconnect request failed.");
      }

      // Stop the monitor receiver thread.
      receiverThread.shutdown();
      receiverThread = null;

      // Disconnection done.
      connected = false;
   }

   public boolean isConnected()
   {
      return connected;
   }

   public boolean isObsolete()
   {
      return hasObsoleteConnectRequest;
   }

   public boolean isBigEndian()
   {
      return bigEndian;
   }

   public boolean isRunMode()
   {
      return monitorName.equals(MONITOR_NAME_RUN_MODE);
   }

   public boolean isFreezeMode()
   {
      return monitorName.equals(MONITOR_NAME_FREEZE_MODE);
   }

   public boolean isPostMortem()
   {
      return monitorName.equals(MONITOR_NAME_POST_MORTEM);
   }

   public int getOsType()
   {
      return osType;
   }

   public int getCpuClass()
   {
      return cpuClass;
   }

   public String getSystemName()
   {
      return systemName;
   }

   public String getMonitorName()
   {
      return monitorName;
   }

   public int getTickLength()
   {
      return tickLength;
   }

   public int getTick()
   {
      return tick;
   }

   public int getNTick()
   {
      return ntick;
   }

   public int getNTickFrequency()
   {
      return ntickFrequency;
   }

   public int getCpuType()
   {
      return cpuType;
   }

   public int getErrorHandler()
   {
      return errorHandler;
   }

   public short getNumExecutionUnits()
   {
      return numExecutionUnits;
   }

   public short[] getFeatures()
   {
      return features;
   }

   public boolean hasGetProcessInfoRequest()
   {
      return hasGetProcessInfoRequest;
   }

   public boolean hasGetBlockInfoRequest()
   {
      return hasGetBlockInfoRequest;
   }

   public boolean hasGetSegmentInfoRequest()
   {
      return hasGetSegmentInfoRequest;
   }

   public boolean hasGetStackUsageRequest()
   {
      return hasGetStackUsageRequest;
   }

   public boolean hasGetEnvVarsRequest()
   {
      return hasGetEnvVarsRequest;
   }

   public boolean hasGetSignalQueueRequest()
   {
      return hasGetSignalQueueRequest;
   }

   public boolean hasGetPoolInfoRequest()
   {
      return hasGetPoolInfoRequest;
   }

   public boolean hasGetPoolSignalsRequest()
   {
      return hasGetPoolSignalsRequest;
   }

   public boolean hasGetHeapInfoRequest()
   {
      return hasGetHeapInfoRequest;
   }

   public boolean hasGetHeapFragmentInfoRequest()
   {
      return hasGetHeapFragmentInfoRequest;
   }

   public boolean hasGetHeapBuffersRequest()
   {
      return hasGetHeapBuffersRequest;
   }

   public boolean hasGetSysParamRequest()
   {
      return hasGetSysParamRequest;
   }

   public boolean hasSetSysParamRequest()
   {
      return hasSetSysParamRequest;
   }

   public boolean hasGetMemoryRequest()
   {
      return hasGetMemoryRequest;
   }

   public boolean hasKillScopeRequest()
   {
      return hasKillScopeRequest;
   }

   public boolean hasStartScopeRequest()
   {
      return hasStartScopeRequest;
   }

   public boolean hasStopScopeRequest()
   {
      return hasStopScopeRequest;
   }

   public boolean hasSetEnvVarRequest()
   {
      return hasSetEnvVarRequest;
   }

   public boolean hasSignalSemaphoreRequest()
   {
      return hasSignalSemaphoreRequest;
   }

   public boolean hasSetPriorityRequest()
   {
      return hasSetPriorityRequest;
   }

   public boolean hasSetExecutionUnitRequest()
   {
      return hasSetExecutionUnitRequest;
   }

   public boolean hasGetRamdumpInfoRequest()
   {
      return hasGetRamdumpInfoRequest;
   }

   public boolean hasGetRamdumpRequest()
   {
      return hasGetRamdumpRequest;
   }

   public boolean hasGetCPUReportsEnabledRequest()
   {
      return hasGetCPUReportsEnabledRequest;
   }

   public boolean hasSetCPUReportsEnabledRequest()
   {
      return hasSetCPUReportsEnabledRequest;
   }

   public boolean hasGetCPUReportsRequest()
   {
      return hasGetCPUReportsRequest;
   }

   public boolean hasGetCPUPriReportsEnabledRequest()
   {
      return hasGetCPUPriReportsEnabledRequest;
   }

   public boolean hasSetCPUPriReportsEnabledRequest()
   {
      return hasSetCPUPriReportsEnabledRequest;
   }

   public boolean hasGetCPUPriReportsRequest()
   {
      return hasGetCPUPriReportsRequest;
   }

   public boolean hasGetCPUProcessReportsEnabledRequest()
   {
      return hasGetCPUProcessReportsEnabledRequest;
   }

   public boolean hasSetCPUProcessReportsEnabledRequest()
   {
      return hasSetCPUProcessReportsEnabledRequest;
   }

   public boolean hasSetCPUProcessReportpointRequest()
   {
      return hasSetCPUProcessReportpointRequest;
   }

   public boolean hasClearCPUProcessReportpointRequest()
   {
      return hasClearCPUProcessReportpointRequest;
   }

   public boolean hasGetCPUProcessReportpointInfoRequest()
   {
      return hasGetCPUProcessReportpointInfoRequest;
   }

   public boolean hasGetCPUProcessReportsRequest()
   {
      return hasGetCPUProcessReportsRequest;
   }

   public boolean hasGetCPUBlockReportsEnabledRequest()
   {
      return hasGetCPUBlockReportsEnabledRequest;
   }

   public boolean hasSetCPUBlockReportsEnabledRequest()
   {
      return hasSetCPUBlockReportsEnabledRequest;
   }

   public boolean hasGetCPUBlockReportsRequest()
   {
      return hasGetCPUBlockReportsRequest;
   }

   public boolean hasGetUserReportsEnabledRequest()
   {
      return hasGetUserReportsEnabledRequest;
   }

   public boolean hasSetUserReportsEnabledRequest()
   {
      return hasSetUserReportsEnabledRequest;
   }

   public boolean hasGetUserReportsRequest()
   {
      return hasGetUserReportsRequest;
   }

   public boolean hasGetSupportedCounterTypesRequest()
   {
      return hasGetSupportedCounterTypesRequest;
   }

   public boolean hasGetCounterTypeEnabledRequest()
   {
      return hasGetCounterTypeEnabledRequest;
   }

   public boolean hasSetCounterTypeEnabledRequest()
   {
      return hasSetCounterTypeEnabledRequest;
   }

   public boolean hasGetCounterReportsNotifyEnabledRequest()
   {
      return hasGetCounterReportsNotifyEnabledRequest;
   }

   public boolean hasSetCounterReportsNotifyEnabledRequest()
   {
      return hasSetCounterReportsNotifyEnabledRequest;
   }

   public boolean hasAttachRequest()
   {
      return hasAttachRequest;
   }

   public boolean hasDetachRequest()
   {
      return hasDetachRequest;
   }

   public boolean hasInterceptScopeRequest()
   {
      return hasInterceptScopeRequest;
   }

   public boolean hasResumeScopeRequest()
   {
      return hasResumeScopeRequest;
   }

   public boolean hasSetScopeRequest()
   {
      return hasSetScopeRequest;
   }

   public boolean hasSetInterceptScopeRequest()
   {
      return hasSetInterceptScopeRequest;
   }

   public boolean hasSetSendActionpointRequest()
   {
      return hasSetSendActionpointRequest;
   }

   public boolean hasSetReceiveActionpointRequest()
   {
      return hasSetReceiveActionpointRequest;
   }

   public boolean hasSetSwapActionpointRequest()
   {
      return hasSetSwapActionpointRequest;
   }

   public boolean hasSetCreateActionpointRequest()
   {
      return hasSetCreateActionpointRequest;
   }

   public boolean hasSetKillActionpointRequest()
   {
      return hasSetKillActionpointRequest;
   }

   public boolean hasSetErrorActionpointRequest()
   {
      return hasSetErrorActionpointRequest;
   }

   public boolean hasSetUserActionpointRequest()
   {
      return hasSetUserActionpointRequest;
   }

   public boolean hasSetBindActionpointRequest()
   {
      return hasSetBindActionpointRequest;
   }

   public boolean hasSetAllocActionpointRequest()
   {
      return hasSetAllocActionpointRequest;
   }

   public boolean hasSetFreeBufActionpointRequest()
   {
      return hasSetFreeBufActionpointRequest;
   }

   public boolean hasSetTimeoutActionpointRequest()
   {
      return hasSetTimeoutActionpointRequest;
   }

   public boolean hasClearEventActionpointRequest()
   {
      return hasClearEventActionpointRequest;
   }

   public boolean hasGetEventActionpointInfoRequest()
   {
      return hasGetEventActionpointInfoRequest;
   }

   public boolean hasGetEventActionpointsRequest()
   {
      return hasGetEventActionpointsRequest;
   }

   public boolean hasGetEventStateRequest()
   {
      return hasGetEventStateRequest;
   }

   public boolean hasSetEventStateRequest()
   {
      return hasSetEventStateRequest;
   }

   public boolean hasGetNotifyEnabledRequest()
   {
      return hasGetNotifyEnabledRequest;
   }

   public boolean hasSetNotifyEnabledRequest()
   {
      return hasSetNotifyEnabledRequest;
   }

   public boolean hasGetTraceEnabledRequest()
   {
      return hasGetTraceEnabledRequest;
   }

   public boolean hasSetTraceEnabledRequest()
   {
      return hasSetTraceEnabledRequest;
   }

   public boolean hasClearTraceRequest()
   {
      return hasClearTraceRequest;
   }

   public boolean hasGetTraceRequest()
   {
      return hasGetTraceRequest;
   }

   public boolean hasGetTraceMultipleRequest()
   {
      return hasGetTraceMultipleRequest;
   }

   public boolean hasProfAllExecutionUnitsFeature()
   {
      return hasProfAllExecutionUnitsFeature;
   }

   public boolean hasClearAllEventActionpointsFeature()
   {
      return hasClearAllEventActionpointsFeature;
   }

   public boolean hasSegmentInfoLongFeature()
   {
      return hasSegmentInfoLongFeature;
   }

   public boolean hasPerCoreProfiledProcessesFeature()
   {
      return hasPerCoreProfiledProcessesFeature;
   }

   public boolean hasSignalDataFilterFeature()
   {
      return hasSignalDataFilterFeature;
   }
   
   public boolean hasGetPoolInfo64Request()
   {
      return hasGetPoolInfo64Request;
   }

   public boolean hasGetHeapInfo64Request()
   {
      return hasGetHeapInfo64Request;
   }

   public boolean hasGetHeapFragmentInfo64Request()
   {
      return hasGetHeapFragmentInfo64Request;
   }
   
   public boolean hasGetMemory64Request()
   {
      return hasGetMemory64Request;
   }

   
   public void getProcessInfoRequest(int level, int scopeType, int scopeId,
                                     MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetProcessInfoRequest);
      request = new MonitorGetProcessInfoRequest(level, scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getBlockInfoRequest(int scopeType, int scopeId, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetBlockInfoRequest);
      request = new MonitorGetBlockInfoRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getSegmentInfoRequest(int level, int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetSegmentInfoRequest);
      request = new MonitorGetSegmentInfoRequest(level, scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getStackUsageRequest(int scopeType, int scopeId, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetStackUsageRequest);
      request = new MonitorGetStackUsageRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getEnvVarsRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetEnvVarsRequest);
      request = new MonitorGetEnvVarsRequest(scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getSignalQueueRequest(int level, int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetSignalQueueRequest);
      request = new MonitorGetSignalQueueRequest(level, scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getPoolInfoRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      if(has64BitFeature)
      {
       	 checkSupport(hasGetPoolInfo64Request);
         request = new MonitorGetPoolInfo64Request(scopeType, scopeId,null);
      }
      else
      {
         checkSupport(hasGetPoolInfoRequest);
         request = new MonitorGetPoolInfoRequest(scopeType, scopeId);
      }
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getPoolSignalsRequest(int scopeType, int scopeId, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetPoolSignalsRequest);
      request = new MonitorGetPoolSignalsRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getHeapInfoRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      if(has64BitFeature)
      {
       	 checkSupport(hasGetHeapInfo64Request);
         request = new MonitorGetHeapInfo64Request(scopeType, scopeId,null);
      }
      else
      {
         checkSupport(hasGetHeapInfoRequest);
         request = new MonitorGetHeapInfoRequest(scopeType, scopeId);
      }
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getHeapFragmentInfoRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      if(has64BitFeature)
      {
         checkSupport(hasGetHeapFragmentInfo64Request);
         request = new MonitorGetHeapFragmentInfo64Request(scopeType, scopeId,null);
      }
      else
      {
    	 checkSupport(hasGetHeapFragmentInfoRequest);
         request = new MonitorGetHeapFragmentInfoRequest(scopeType, scopeId);
      }
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getHeapBuffersRequest(int scopeType, int scopeId, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetHeapBuffersRequest);
      request = new MonitorGetHeapBuffersRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getSysParamRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetSysParamRequest);
      request = new MonitorGetSysParamRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setSysParamRequest(boolean reconfigure, String name, String value)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetSysParamRequest);
      request = new MonitorSetSysParamRequest(reconfigure, name, value);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getMemoryRequest(int pid, long address, long size)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      if(has64BitFeature)
      {
         checkSupport(hasGetMemory64Request);
         request = new MonitorGetMemory64Request(pid, address, (int)size);
      }
      else
      {
    	 checkSupport(hasGetMemoryRequest);
    	 request = new MonitorGetMemoryRequest(pid, (int)address, (int)size);
      }
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void killScopeRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasKillScopeRequest);
      request = new MonitorKillScopeRequest(scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void startScopeRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasStartScopeRequest);
      request = new MonitorStartScopeRequest(scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void stopScopeRequest(int scopeType, int scopeId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasStopScopeRequest);
      request = new MonitorStopScopeRequest(scopeType, scopeId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setEnvVarRequest(int pid, String name, String value)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetEnvVarRequest);
      request = new MonitorSetEnvVarRequest(pid, name, value);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setEnvVarRequest(int pid, String name, int value)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetEnvVarRequest);
      request = new MonitorSetEnvVarRequest(pid, name, value);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void signalSemaphoreRequest(int pid)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSignalSemaphoreRequest);
      request = new MonitorSignalSemaphoreRequest(pid);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setPriorityRequest(int pid, int priority)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetPriorityRequest);
      request = new MonitorSetPriorityRequest(pid, priority);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setExecutionUnitRequest(int pid, short euId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetExecutionUnitRequest);
      request = new MonitorSetExecutionUnitRequest(pid, euId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getRamdumpInfoRequest(int from, int to)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetRamdumpInfoRequest);
      request = new MonitorGetRamdumpInfoRequest(from, to);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getRamdumpRequest(int dumpId)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetRamdumpRequest);
      request = new MonitorGetRamdumpRequest(dumpId);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUReportsEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUReportsEnabledRequest);
      request = new MonitorGetCPUReportsEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCPUReportsEnabledRequest(boolean enabled,
                                           int interval,
                                           int priority,
                                           int maxReports)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCPUReportsEnabledRequest);
      request = new MonitorSetCPUReportsEnabledRequest(enabled,
                                                       interval,
                                                       priority,
                                                       maxReports);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUReportsRequest(short euId, int tick, int ntick)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUReportsRequest);
      request = new MonitorGetCPUReportsRequest(euId, tick, ntick);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUPriReportsEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUPriReportsEnabledRequest);
      request = new MonitorGetCPUPriReportsEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCPUPriReportsEnabledRequest(boolean enabled,
                                              int interval,
                                              int maxReports)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCPUPriReportsEnabledRequest);
      request = new MonitorSetCPUPriReportsEnabledRequest(enabled,
                                                          interval,
                                                          maxReports);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUPriReportsRequest(short euId, int tick, int ntick)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUPriReportsRequest);
      request = new MonitorGetCPUPriReportsRequest(euId, tick, ntick);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUProcessReportsEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUProcessReportsEnabledRequest);
      request = new MonitorGetCPUProcessReportsEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCPUProcessReportsEnabledRequest(boolean enabled,
                                                  int interval,
                                                  int maxReports,
                                                  int maxProcsReport,
                                                  boolean usePriority,
                                                  byte priority,
                                                  boolean usePerCoreProfiledProceses)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCPUProcessReportsEnabledRequest);
      request = new MonitorSetCPUProcessReportsEnabledRequest(enabled,
                                                              interval,
                                                              maxReports,
                                                              maxProcsReport,
                                                              usePriority,
                                                              priority,
                                                              usePerCoreProfiledProceses);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCPUProcessReportpointRequest(int scopeType,
                                               int scopeId,
                                               MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCPUProcessReportpointRequest);
      request = new MonitorSetCPUProcessReportpointRequest(scopeType,
                                                           scopeId,
                                                           tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void clearCPUProcessReportpointRequest(int id)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasClearCPUProcessReportpointRequest);
      request = new MonitorClearCPUProcessReportpointRequest(id);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUProcessReportpointInfoRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUProcessReportpointInfoRequest);
      request = new MonitorGetCPUProcessReportpointInfoRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUProcessReportsRequest(short euId, int tick, int ntick)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUProcessReportsRequest);
      request = new MonitorGetCPUProcessReportsRequest(euId, tick, ntick);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUBlockReportsEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUBlockReportsEnabledRequest);
      request = new MonitorGetCPUBlockReportsEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCPUBlockReportsEnabledRequest(boolean enabled,
                                                int interval,
                                                int maxReports,
                                                int maxBlocksReport,
                                                boolean usePriority,
                                                byte priority,
                                                boolean usePerCoreProfiledBlocks)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCPUBlockReportsEnabledRequest);
      request = new MonitorSetCPUBlockReportsEnabledRequest(enabled,
                                                            interval,
                                                            maxReports,
                                                            maxBlocksReport,
                                                            usePriority,
                                                            priority,
                                                            usePerCoreProfiledBlocks);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCPUBlockReportsRequest(short euId, int tick, int ntick)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCPUBlockReportsRequest);
      request = new MonitorGetCPUBlockReportsRequest(euId, tick, ntick);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getUserReportsEnabledRequest(int reportNo)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetUserReportsEnabledRequest);
      request = new MonitorGetUserReportsEnabledRequest(reportNo);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setUserReportsEnabledRequest(int reportNo,
                                            boolean enabled,
                                            int interval,
                                            int maxReports,
                                            int maxValuesReport)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetUserReportsEnabledRequest);
      request = new MonitorSetUserReportsEnabledRequest(reportNo,
                                                        enabled,
                                                        interval,
                                                        maxReports,
                                                        maxValuesReport);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getUserReportsRequest(int reportNo, int tick, int ntick)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetUserReportsRequest);
      request = new MonitorGetUserReportsRequest(reportNo, tick, ntick);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getSupportedCounterTypesRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetSupportedCounterTypesRequest);
      request = new MonitorGetSupportedCounterTypesRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCounterTypeEnabledRequest(short euId, int counterType)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCounterTypeEnabledRequest);
      request = new MonitorGetCounterTypeEnabledRequest(euId, counterType);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCounterTypeEnabledRequest(boolean enabled,
                                            short euId,
                                            int counterType,
                                            long counterValue,
                                            int maxReports)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCounterTypeEnabledRequest);
      request = new MonitorSetCounterTypeEnabledRequest(enabled,
                                                        euId,
                                                        counterType,
                                                        counterValue,
                                                        maxReports);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getCounterReportsNotifyEnabledRequest(short euId, int counterType)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetCounterReportsNotifyEnabledRequest);
      request = new MonitorGetCounterReportsNotifyEnabledRequest(euId, counterType);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCounterReportsNotifyEnabledRequest(boolean enabled,
                                                     short euId,
                                                     int counterType)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCounterReportsNotifyEnabledRequest);
      request = new MonitorSetCounterReportsNotifyEnabledRequest(enabled,
                                                                 euId,
                                                                 counterType);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void attachRequest(int scopeType,
                             int scopeId,
                             int interceptType,
                             int interceptId,
                             MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasAttachRequest);
      request = new MonitorAttachRequest(scopeType,
                                         scopeId,
                                         interceptType,
                                         interceptId,
                                         tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void detachRequest(boolean resume)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasDetachRequest);
      request = new MonitorDetachRequest(resume);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void interceptScopeRequest(int scopeType,
                                     int scopeId,
                                     MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasInterceptScopeRequest);
      request = new MonitorInterceptScopeRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void resumeScopeRequest(int scopeType,
                                  int scopeId,
                                  MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasResumeScopeRequest);
      request = new MonitorResumeScopeRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setScopeRequest(int scopeType, int scopeId, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetScopeRequest);
      request = new MonitorSetScopeRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setInterceptScopeRequest(int scopeType,
                                        int scopeId,
                                        MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetInterceptScopeRequest);
      request = new MonitorSetInterceptScopeRequest(scopeType, scopeId, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setSendActionpointRequest(int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetSendActionpointRequest);
      request = new MonitorSetSendActionpointRequest(state,
                                                     fromType,
                                                     fromId,
                                                     toType,
                                                     toId,
                                                     not,
                                                     action,
                                                     interceptType,
                                                     interceptId,
                                                     parameter,
                                                     client,
                                                     id,
                                                     count,
                                                     tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setReceiveActionpointRequest(int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetReceiveActionpointRequest);
      request = new MonitorSetReceiveActionpointRequest(state,
                                                        fromType,
                                                        fromId,
                                                        toType,
                                                        toId,
                                                        not,
                                                        action,
                                                        interceptType,
                                                        interceptId,
                                                        parameter,
                                                        client,
                                                        id,
                                                        count,
                                                        tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setSwapActionpointRequest(int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetSwapActionpointRequest);
      request = new MonitorSetSwapActionpointRequest(state,
                                                     fromType,
                                                     fromId,
                                                     toType,
                                                     toId,
                                                     not,
                                                     action,
                                                     interceptType,
                                                     interceptId,
                                                     parameter,
                                                     client,
                                                     id,
                                                     count,
                                                     tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setCreateActionpointRequest(int state,
                                           int fromType,
                                           int fromId,
                                           int toType,
                                           int toId,
                                           boolean not,
                                           int action,
                                           int interceptType,
                                           int interceptId,
                                           int parameter,
                                           int client,
                                           int id,
                                           int count,
                                           MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetCreateActionpointRequest);
      request = new MonitorSetCreateActionpointRequest(state,
                                                       fromType,
                                                       fromId,
                                                       toType,
                                                       toId,
                                                       not,
                                                       action,
                                                       interceptType,
                                                       interceptId,
                                                       parameter,
                                                       client,
                                                       id,
                                                       count,
                                                       tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setKillActionpointRequest(int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetKillActionpointRequest);
      request = new MonitorSetKillActionpointRequest(state,
                                                     fromType,
                                                     fromId,
                                                     toType,
                                                     toId,
                                                     not,
                                                     action,
                                                     interceptType,
                                                     interceptId,
                                                     parameter,
                                                     client,
                                                     id,
                                                     count,
                                                     tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setErrorActionpointRequest(int state,
                                          int fromType,
                                          int fromId,
                                          int toType,
                                          int toId,
                                          boolean not,
                                          int action,
                                          int interceptType,
                                          int interceptId,
                                          int parameter,
                                          int client,
                                          int id,
                                          int count,
                                          MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetErrorActionpointRequest);
      request = new MonitorSetErrorActionpointRequest(state,
                                                      fromType,
                                                      fromId,
                                                      toType,
                                                      toId,
                                                      not,
                                                      action,
                                                      interceptType,
                                                      interceptId,
                                                      parameter,
                                                      client,
                                                      id,
                                                      count,
                                                      tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setUserActionpointRequest(int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetUserActionpointRequest);
      request = new MonitorSetUserActionpointRequest(state,
                                                     fromType,
                                                     fromId,
                                                     toType,
                                                     toId,
                                                     not,
                                                     action,
                                                     interceptType,
                                                     interceptId,
                                                     parameter,
                                                     client,
                                                     id,
                                                     count,
                                                     tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setBindActionpointRequest(int state,
                                         int fromType,
                                         int fromId,
                                         int toType,
                                         int toId,
                                         boolean not,
                                         int action,
                                         int interceptType,
                                         int interceptId,
                                         int parameter,
                                         int client,
                                         int id,
                                         int count,
                                         MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetBindActionpointRequest);
      request = new MonitorSetBindActionpointRequest(state,
                                                     fromType,
                                                     fromId,
                                                     toType,
                                                     toId,
                                                     not,
                                                     action,
                                                     interceptType,
                                                     interceptId,
                                                     parameter,
                                                     client,
                                                     id,
                                                     count,
                                                     tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setAllocActionpointRequest(int state,
                                          int fromType,
                                          int fromId,
                                          int toType,
                                          int toId,
                                          boolean not,
                                          int action,
                                          int interceptType,
                                          int interceptId,
                                          int parameter,
                                          int client,
                                          int id,
                                          int count,
                                          MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetAllocActionpointRequest);
      request = new MonitorSetAllocActionpointRequest(state,
                                                      fromType,
                                                      fromId,
                                                      toType,
                                                      toId,
                                                      not,
                                                      action,
                                                      interceptType,
                                                      interceptId,
                                                      parameter,
                                                      client,
                                                      id,
                                                      count,
                                                      tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setFreeBufActionpointRequest(int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetFreeBufActionpointRequest);
      request = new MonitorSetFreeBufActionpointRequest(state,
                                                        fromType,
                                                        fromId,
                                                        toType,
                                                        toId,
                                                        not,
                                                        action,
                                                        interceptType,
                                                        interceptId,
                                                        parameter,
                                                        client,
                                                        id,
                                                        count,
                                                        tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setTimeoutActionpointRequest(int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetTimeoutActionpointRequest);
      request = new MonitorSetTimeoutActionpointRequest(state,
                                                        fromType,
                                                        fromId,
                                                        toType,
                                                        toId,
                                                        not,
                                                        action,
                                                        interceptType,
                                                        interceptId,
                                                        parameter,
                                                        client,
                                                        id,
                                                        count,
                                                        tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void clearEventActionpointRequest(int id)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasClearEventActionpointRequest);
      request = new MonitorClearEventActionpointRequest(id);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getEventActionpointInfoRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetEventActionpointInfoRequest);
      request = new MonitorGetEventActionpointInfoRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getEventActionpointsRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetEventActionpointsRequest);
      request = new MonitorGetEventActionpointsRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getEventStateRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetEventStateRequest);
      request = new MonitorGetEventStateRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setEventStateRequest(int state)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetEventStateRequest);
      request = new MonitorSetEventStateRequest(state);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getNotifyEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetNotifyEnabledRequest);
      request = new MonitorGetNotifyEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setNotifyEnabledRequest(boolean enabled)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetNotifyEnabledRequest);
      request = new MonitorSetNotifyEnabledRequest(enabled);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getTraceEnabledRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetTraceEnabledRequest);
      request = new MonitorGetTraceEnabledRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void setTraceEnabledRequest(boolean enabled)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasSetTraceEnabledRequest);
      request = new MonitorSetTraceEnabledRequest(enabled);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void clearTraceRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasClearTraceRequest);
      request = new MonitorClearTraceRequest();
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getTraceRequest(int from, int to, MonitorTag[] tags)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetTraceRequest);
      request = new MonitorGetTraceRequest(from, to, tags);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   public void getTraceMultipleRequest(int timeout, int handle)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetTraceMultipleRequest);
      request = new MonitorGetTraceMultipleRequest(timeout, handle);
      senderGateway.send(receiverPid, monitorPid, request);
   }

   private void monitorKilled(Signal sig)
   {
      listener.monitorKilled();
   }

   private void getProcessInfoReply(Signal sig)
   {
      MonitorGetProcessInfoReply reply = (MonitorGetProcessInfoReply) sig;
      listener.getProcessInfoReply(reply.pid,
                                   reply.bid,
                                   reply.sid,
                                   reply.type,
                                   reply.state,
                                   reply.priority,
                                   reply.entrypoint,
                                   reply.properties,
                                   reply.name);
   }

   private void getProcessInfoLongReply(Signal sig)
   {
      MonitorGetProcessInfoLongReply reply = (MonitorGetProcessInfoLongReply) sig;
      listener.getProcessInfoLongReply(reply.pid,
                                       reply.bid,
                                       reply.sid,
                                       reply.type,
                                       reply.state,
                                       reply.priority,
                                       reply.uid,
                                       reply.creator,
                                       reply.supervisor,
                                       reply.signalsInQueue,
                                       reply.signalsAttached,
                                       reply.entrypoint,
                                       reply.semaphoreValue,
                                       reply.stackSize,
                                       reply.supervisorStackSize,
                                       reply.lineNumber,
                                       reply.signalsOwned,
                                       reply.timeSlice,
                                       reply.vector,
                                       reply.errorHandler,
                                       reply.properties,
                                       reply.euId,
                                       reply.sigselect,
                                       reply.processName,
                                       reply.fileName);
   }

   private void getProcessInfoEndmark(Signal sig)
   {
      MonitorGetProcessInfoEndmark endmark = (MonitorGetProcessInfoEndmark) sig;
      listener.getProcessInfoEndmark(endmark.status);
   }

   private void getBlockInfoReply(Signal sig)
   {
      MonitorGetBlockInfoReply reply = (MonitorGetBlockInfoReply) sig;
      listener.getBlockInfoReply(reply.bid,
                                 reply.sid,
                                 reply.uid,
                                 reply.supervisor,
                                 reply.signalsAttached,
                                 reply.errorHandler,
                                 reply.maxSigSize,
                                 reply.sigPoolId,
                                 reply.stackPoolId,
                                 reply.euId,
                                 reply.name);
   }

   private void getBlockInfoEndmark(Signal sig)
   {
      MonitorGetBlockInfoEndmark endmark = (MonitorGetBlockInfoEndmark) sig;
      listener.getBlockInfoEndmark(endmark.status);
   }

   private void getSegmentInfoReply(Signal sig)
   {
      MonitorGetSegmentInfoReply reply = (MonitorGetSegmentInfoReply) sig;
      listener.getSegmentInfoReply(reply.sid, reply.number);
   }

   private void getSegmentInfoLongReply(Signal sig)
   {
      MonitorGetSegmentInfoLongReply reply = (MonitorGetSegmentInfoLongReply) sig;
      listener.getSegmentInfoLongReply(reply.sid,
                                       reply.number,
                                       reply.signalsAttached,
                                       reply.masMapped,
                                       reply.name);
   }

   private void getSegmentInfoEndmark(Signal sig)
   {
      MonitorGetSegmentInfoEndmark endmark = (MonitorGetSegmentInfoEndmark) sig;
      listener.getSegmentInfoEndmark(endmark.status);
   }

   private void getStackUsageReply(Signal sig)
   {
      MonitorGetStackUsageReply reply = (MonitorGetStackUsageReply) sig;
      listener.getStackUsageReply(reply.pid, reply.main, reply.supervisor);
   }

   private void getStackUsageEndmark(Signal sig)
   {
      MonitorGetStackUsageEndmark endmark = (MonitorGetStackUsageEndmark) sig;
      listener.getStackUsageEndmark(endmark.status);
   }

   private void getEnvVarsReply(Signal sig)
   {
      MonitorGetEnvVarsReply reply = (MonitorGetEnvVarsReply) sig;
      listener.getEnvVarsReply(reply.pid, reply.name, reply.value);
   }

   private void getEnvVarsEndmark(Signal sig)
   {
      MonitorGetEnvVarsEndmark endmark = (MonitorGetEnvVarsEndmark) sig;
      listener.getEnvVarsEndmark(endmark.status);
   }

   private void getSignalQueueReply(Signal sig)
   {
      MonitorGetSignalQueueReply reply = (MonitorGetSignalQueueReply) sig;
      listener.getSignalQueueReply(reply.pid, reply.signal);
   }

   private void getSignalQueueLongReply(Signal sig)
   {
      MonitorGetSignalQueueLongReply reply = (MonitorGetSignalQueueLongReply) sig;
      listener.getSignalQueueLongReply(reply.pid, reply.signal, reply.data);
   }

   private void getSignalQueueEndmark(Signal sig)
   {
      MonitorGetSignalQueueEndmark endmark = (MonitorGetSignalQueueEndmark) sig;
      listener.getSignalQueueEndmark(endmark.status);
   }

   private void getPoolInfoReply(Signal sig)
   {
      MonitorGetPoolInfoReply reply = (MonitorGetPoolInfoReply) sig;
      listener.getPoolInfoReply(reply.pid,
                                reply.sid,
                                reply.total,
                                reply.free,
                                reply.stackAlignment & 0xFF,
                                reply.stackAdmSize & 0xFF,
                                reply.stackInternalAdmSize & 0xFF,
                                reply.signalAlignment & 0xFF,
                                reply.signalAdmSize & 0xFF,
                                reply.signalInternalAdmSize & 0xFF,
                                reply.fragments,
                                reply.stackSizes,
                                reply.signalSizes);
   }
   
   private void getPoolInfo64Reply(Signal sig)
   {
      MonitorGetPoolInfo64Reply reply = (MonitorGetPoolInfo64Reply) sig;
      listener.getPoolInfo64Reply(reply.pid,
                                  reply.sid,
                                  reply.total,
                                  reply.free,
                                  reply.stackAlignment,
                                  reply.stackAdmSize,
                                  reply.stackInternalAdmSize,
                                  reply.signalAlignment,
                                  reply.signalAdmSize,
                                  reply.signalInternalAdmSize,
                                  reply.fragments,
                                  reply.stackSizes,
                                  reply.signalSizes);
   }
   

   private void getPoolInfoEndmark(Signal sig)
   {
      MonitorGetPoolInfoEndmark endmark = (MonitorGetPoolInfoEndmark) sig;
      listener.getPoolInfoEndmark(endmark.status);
   }

   private void getPoolSignalsReply(Signal sig)
   {
      MonitorGetPoolSignalsReply reply = (MonitorGetPoolSignalsReply) sig;
      listener.getPoolSignalsReply(reply.pid, reply.signals);
   }

   private void getPoolSignalsEndmark(Signal sig)
   {
      MonitorGetPoolSignalsEndmark endmark = (MonitorGetPoolSignalsEndmark) sig;
      listener.getPoolSignalsEndmark(endmark.status);
   }

   private void getHeapInfoReply(Signal sig)
   {
      MonitorGetHeapInfoReply reply = (MonitorGetHeapInfoReply) sig;
      listener.getHeapInfoReply(reply.id,
                                reply.owner,
                                reply.sid,
                                reply.size,
                                reply.usedSize,
                                reply.peakUsedSize,
                                reply.reqSize,
                                reply.largestFree,
                                reply.largeThreshold,
                                reply.priv,
                                reply.shared,
                                reply.mallocFailed,
                                reply.bufferSizes);
   }
   
   private void getHeapInfo64Reply(Signal sig)
   {
      MonitorGetHeapInfo64Reply reply = (MonitorGetHeapInfo64Reply) sig;
      listener.getHeapInfo64Reply(reply.id,
                                reply.owner,
                                reply.sid,
                                reply.size,
                                reply.usedSize,
                                reply.peakUsedSize,
                                reply.reqSize,
                                reply.largestFree,
                                reply.largeThreshold,
                                reply.priv,
                                reply.shared,
                                reply.mallocFailed,
                                reply.bufferSizes);
   }

   private void getHeapInfoEndmark(Signal sig)
   {
      MonitorGetHeapInfoEndmark endmark = (MonitorGetHeapInfoEndmark) sig;
      listener.getHeapInfoEndmark(endmark.status);
   }

   private void getHeapFragmentInfoReply(Signal sig)
   {
      MonitorGetHeapFragmentInfoReply reply = (MonitorGetHeapFragmentInfoReply) sig;
      listener.getHeapFragmentInfoReply(reply.id, reply.fragments);
   }
   
   private void getHeapFragmentInfo64Reply(Signal sig)
   {
      MonitorGetHeapFragmentInfo64Reply reply = (MonitorGetHeapFragmentInfo64Reply) sig;
      listener.getHeapFragmentInfo64Reply(reply.id, reply.fragments);
   }

   private void getHeapFragmentInfoEndmark(Signal sig)
   {
      MonitorGetHeapFragmentInfoEndmark endmark = (MonitorGetHeapFragmentInfoEndmark) sig;
      listener.getHeapFragmentInfoEndmark(endmark.status);
   }

   private void getHeapBuffersReply(Signal sig)
   {
      MonitorGetHeapBuffersReply reply = (MonitorGetHeapBuffersReply) sig;
      listener.getHeapBuffersReply(reply.id, reply.buffers);
   }

   private void getHeapBuffersEndmark(Signal sig)
   {
      MonitorGetHeapBuffersEndmark endmark = (MonitorGetHeapBuffersEndmark) sig;
      listener.getHeapBuffersEndmark(endmark.status);
   }

   private void getSysParamReply(Signal sig)
   {
      MonitorGetSysParamReply reply = (MonitorGetSysParamReply) sig;
      listener.getSysParamReply(reply.name, reply.value);
   }

   private void getSysParamEndmark(Signal sig)
   {
      MonitorGetSysParamEndmark endmark = (MonitorGetSysParamEndmark) sig;
      listener.getSysParamEndmark(endmark.status);
   }

   private void setSysParamReply(Signal sig)
   {
      MonitorSetSysParamReply reply = (MonitorSetSysParamReply) sig;
      listener.setSysParamReply(reply.status);
   }

   private void getMemoryReply(Signal sig)
   {
      MonitorGetMemoryReply reply = (MonitorGetMemoryReply) sig;
      listener.getMemoryReply(reply.pid, reply.address, reply.data);
   }
   
   private void getMemory64Reply(Signal sig)
   {
      MonitorGetMemory64Reply reply = (MonitorGetMemory64Reply) sig;
      listener.getMemoryReply(reply.pid, reply.address, reply.data);
   }

   private void getMemoryEndmark(Signal sig)
   {
      MonitorGetMemoryEndmark endmark = (MonitorGetMemoryEndmark) sig;
      listener.getMemoryEndmark(endmark.status);
   }

   private void killScopeReply(Signal sig)
   {
      MonitorKillScopeReply reply = (MonitorKillScopeReply) sig;
      listener.killScopeReply(reply.status);
   }

   private void startScopeReply(Signal sig)
   {
      MonitorStartScopeReply reply = (MonitorStartScopeReply) sig;
      listener.startScopeReply(reply.status);
   }

   private void stopScopeReply(Signal sig)
   {
      MonitorStopScopeReply reply = (MonitorStopScopeReply) sig;
      listener.stopScopeReply(reply.status);
   }

   private void setEnvVarReply(Signal sig)
   {
      MonitorSetEnvVarReply reply = (MonitorSetEnvVarReply) sig;
      listener.setEnvVarReply(reply.status);
   }

   private void signalSemaphoreReply(Signal sig)
   {
      MonitorSignalSemaphoreReply reply = (MonitorSignalSemaphoreReply) sig;
      listener.signalSemaphoreReply(reply.status);
   }

   private void setPriorityReply(Signal sig)
   {
      MonitorSetPriorityReply reply = (MonitorSetPriorityReply) sig;
      listener.setPriorityReply(reply.status);
   }

   private void setExecutionUnitReply(Signal sig)
   {
      MonitorSetExecutionUnitReply reply = (MonitorSetExecutionUnitReply) sig;
      listener.setExecutionUnitReply(reply.status);
   }

   private void getRamdumpInfoReply(Signal sig)
   {
      MonitorGetRamdumpInfoReply reply = (MonitorGetRamdumpInfoReply) sig;
      listener.getRamdumpInfoReply(reply.dumpId, reply.size, reply.sec, reply.microsec);
   }

   private void getRamdumpInfoEndmark(Signal sig)
   {
      MonitorGetRamdumpInfoEndmark endmark = (MonitorGetRamdumpInfoEndmark) sig;
      listener.getRamdumpInfoEndmark(endmark.status);
   }

   private void getRamdumpReply(Signal sig)
   {
      MonitorGetRamdumpReply reply = (MonitorGetRamdumpReply) sig;
      listener.getRamdumpReply(reply.dumpId, reply.offset, reply.data);
   }

   private void getRamdumpEndmark(Signal sig)
   {
      MonitorGetRamdumpEndmark endmark = (MonitorGetRamdumpEndmark) sig;
      listener.getRamdumpEndmark(endmark.status);
   }

   private void getCPUReportsEnabledReply(Signal sig)
   {
      MonitorGetCPUReportsEnabledReply reply =
         (MonitorGetCPUReportsEnabledReply) sig;
      listener.getCPUReportsEnabledReply(reply.enabled,
                                         reply.interval,
                                         reply.priority,
                                         reply.maxReports,
                                         reply.sec,
                                         reply.sectick,
                                         reply.secntick);
   }

   private void setCPUReportsEnabledReply(Signal sig)
   {
      MonitorSetCPUReportsEnabledReply reply =
         (MonitorSetCPUReportsEnabledReply) sig;
      listener.setCPUReportsEnabledReply(reply.status);
   }

   private void getCPUReportsReply(Signal sig)
   {
      MonitorGetCPUReportsReply reply = (MonitorGetCPUReportsReply) sig;
      listener.getCPUReportsReply(reply.status,
                                  reply.enabled,
                                  reply.euId,
                                  reply.reports);
   }

   private void getCPUPriReportsEnabledReply(Signal sig)
   {
      MonitorGetCPUPriReportsEnabledReply reply =
         (MonitorGetCPUPriReportsEnabledReply) sig;
      listener.getCPUPriReportsEnabledReply(reply.enabled,
                                            reply.interval,
                                            reply.maxReports,
                                            reply.sec,
                                            reply.sectick,
                                            reply.secntick);
   }

   private void setCPUPriReportsEnabledReply(Signal sig)
   {
      MonitorSetCPUPriReportsEnabledReply reply =
         (MonitorSetCPUPriReportsEnabledReply) sig;
      listener.setCPUPriReportsEnabledReply(reply.status);
   }

   private void getCPUPriReportsReply(Signal sig)
   {
      MonitorGetCPUPriReportsReply reply = (MonitorGetCPUPriReportsReply) sig;
      listener.getCPUPriReportsReply(reply.status,
                                     reply.enabled,
                                     reply.euId,
                                     reply.reports);
   }

   private void getCPUProcessReportsEnabledReply(Signal sig)
   {
      MonitorGetCPUProcessReportsEnabledReply reply =
         (MonitorGetCPUProcessReportsEnabledReply) sig;
      listener.getCPUProcessReportsEnabledReply(reply.enabled,
                                                reply.interval,
                                                reply.maxReports,
                                                reply.maxProcsReport,
                                                reply.sec,
                                                reply.sectick,
                                                reply.secntick);
   }

   private void setCPUProcessReportsEnabledReply(Signal sig)
   {
      MonitorSetCPUProcessReportsEnabledReply reply =
         (MonitorSetCPUProcessReportsEnabledReply) sig;
      listener.setCPUProcessReportsEnabledReply(reply.status);
   }

   private void setCPUProcessReportpointReply(Signal sig)
   {
      MonitorSetCPUProcessReportpointReply reply =
         (MonitorSetCPUProcessReportpointReply) sig;
      listener.setCPUProcessReportpointReply(reply.status, reply.id);
   }

   private void clearCPUProcessReportpointReply(Signal sig)
   {
      MonitorClearCPUProcessReportpointReply reply =
         (MonitorClearCPUProcessReportpointReply) sig;
      listener.clearCPUProcessReportpointReply(reply.status);
   }

   private void getCPUProcessReportpointInfoReply(Signal sig)
   {
      MonitorGetCPUProcessReportpointInfoReply reply =
         (MonitorGetCPUProcessReportpointInfoReply) sig;
      listener.getCPUProcessReportpointInfoReply(reply.scopeType,
                                                 reply.scopeId,
                                                 reply.id,
                                                 reply.tags);
   }

   private void getCPUProcessReportpointInfoEndmark(Signal sig)
   {
      MonitorGetCPUProcessReportpointInfoEndmark endmark =
         (MonitorGetCPUProcessReportpointInfoEndmark) sig;
      listener.getCPUProcessReportpointInfoEndmark(endmark.status);
   }

   private void getCPUProcessReportsReply(Signal sig)
   {
      MonitorGetCPUProcessReportsReply reply =
         (MonitorGetCPUProcessReportsReply) sig;
      listener.getCPUProcessReportsReply(reply.status,
                                         reply.enabled,
                                         reply.euId,
                                         reply.reports);
   }

   private void getCPUBlockReportsEnabledReply(Signal sig)
   {
      MonitorGetCPUBlockReportsEnabledReply reply =
         (MonitorGetCPUBlockReportsEnabledReply) sig;
      listener.getCPUBlockReportsEnabledReply(reply.enabled,
                                              reply.interval,
                                              reply.maxReports,
                                              reply.maxBlocksReport,
                                              reply.sec,
                                              reply.sectick,
                                              reply.secntick);
   }

   private void setCPUBlockReportsEnabledReply(Signal sig)
   {
      MonitorSetCPUBlockReportsEnabledReply reply =
         (MonitorSetCPUBlockReportsEnabledReply) sig;
      listener.setCPUBlockReportsEnabledReply(reply.status);
   }

   private void getCPUBlockReportsReply(Signal sig)
   {
      MonitorGetCPUBlockReportsReply reply =
         (MonitorGetCPUBlockReportsReply) sig;
      listener.getCPUBlockReportsReply(reply.status,
                                       reply.enabled,
                                       reply.euId,
                                       reply.reports);
   }

   private void getUserReportsEnabledReply(Signal sig)
   {
      MonitorGetUserReportsEnabledReply reply =
         (MonitorGetUserReportsEnabledReply) sig;
      listener.getUserReportsEnabledReply(reply.reportNo,
                                          reply.enabled,
                                          reply.interval,
                                          reply.maxReports,
                                          reply.maxValuesReport,
                                          reply.sec,
                                          reply.sectick,
                                          reply.secntick);
   }

   private void setUserReportsEnabledReply(Signal sig)
   {
      MonitorSetUserReportsEnabledReply reply =
         (MonitorSetUserReportsEnabledReply) sig;
      listener.setUserReportsEnabledReply(reply.status);
   }

   private void getUserReportsReply(Signal sig)
   {
      MonitorGetUserReportsReply reply =
         (MonitorGetUserReportsReply) sig;
      listener.getUserReportsReply(reply.status,
                                   reply.reportNo,
                                   reply.enabled,
                                   reply.continuous,
                                   reply.maxmin,
                                   reply.multiple,
                                   reply.reports);
   }

   private void getSupportedCounterTypesReply(Signal sig)
   {
      MonitorGetSupportedCounterTypesReply reply =
         (MonitorGetSupportedCounterTypesReply) sig;
      listener.getSupportedCounterTypesReply(reply.counterType, reply.name);
   }

   private void getSupportedCounterTypesEndmark(Signal sig)
   {
      MonitorGetSupportedCounterTypesEndmark reply =
         (MonitorGetSupportedCounterTypesEndmark) sig;
      listener.getSupportedCounterTypesEndmark(reply.status);
   }

   private void getCounterTypeEnabledReply(Signal sig)
   {
      MonitorGetCounterTypeEnabledReply reply =
         (MonitorGetCounterTypeEnabledReply) sig;
      listener.getCounterTypeEnabledReply(reply.status,
                                          reply.enabled,
                                          reply.euId,
                                          reply.counterType,
                                          reply.counterValue,
                                          reply.maxReports);
   }

   private void setCounterTypeEnabledReply(Signal sig)
   {
      MonitorSetCounterTypeEnabledReply reply =
         (MonitorSetCounterTypeEnabledReply) sig;
      listener.setCounterTypeEnabledReply(reply.status);
   }

   private void getCounterReportsNotifyEnabledReply(Signal sig)
   {
      MonitorGetCounterReportsNotifyEnabledReply reply =
         (MonitorGetCounterReportsNotifyEnabledReply) sig;
      listener.getCounterReportsNotifyEnabledReply(reply.status,
                                                   reply.enabled,
                                                   reply.euId,
                                                   reply.counterType);
   }

   private void setCounterReportsNotifyEnabledReply(Signal sig)
   {
      MonitorSetCounterReportsNotifyEnabledReply reply =
         (MonitorSetCounterReportsNotifyEnabledReply) sig;
      listener.setCounterReportsNotifyEnabledReply(reply.status);
   }

   private void counterReportsNotify(Signal sig)
   {
      MonitorCounterReportsNotify reply = (MonitorCounterReportsNotify) sig;
      listener.counterReportsNotify(reply.euId, reply.counterType, reply.reports);
   }

   private void counterReportsLossNotify(Signal sig)
   {
      MonitorCounterReportsLossNotify reply =
         (MonitorCounterReportsLossNotify) sig;
      listener.counterReportsLossNotify(reply.euId,
                                        reply.counterType,
                                        reply.reportsLost);
   }

   private void attachReply(Signal sig)
   {
      MonitorAttachReply reply = (MonitorAttachReply) sig;
      listener.attachReply(reply.status, reply.processCacheCount);
   }

   private void detachReply(Signal sig)
   {
      MonitorDetachReply reply = (MonitorDetachReply) sig;
      listener.detachReply(reply.status);
   }

   private void interceptScopeReply(Signal sig)
   {
      MonitorInterceptScopeReply reply = (MonitorInterceptScopeReply) sig;
      listener.interceptScopeReply(reply.status);
   }

   private void resumeScopeReply(Signal sig)
   {
      MonitorResumeScopeReply reply = (MonitorResumeScopeReply) sig;
      listener.resumeScopeReply(reply.status);
   }

   private void setScopeReply(Signal sig)
   {
      MonitorSetScopeReply reply = (MonitorSetScopeReply) sig;
      listener.setScopeReply(reply.status);
   }

   private void setInterceptScopeReply(Signal sig)
   {
      MonitorSetInterceptScopeReply reply = (MonitorSetInterceptScopeReply) sig;
      listener.setInterceptScopeReply(reply.status);
   }

   private void setEventActionpointReply(Signal sig)
   {
      MonitorSetEventActionpointReply reply =
         (MonitorSetEventActionpointReply) sig;
      listener.setEventActionpointReply(reply.status, reply.id);
   }

   private void clearEventActionpointReply(Signal sig)
   {
      MonitorClearEventActionpointReply reply =
         (MonitorClearEventActionpointReply) sig;
      listener.clearEventActionpointReply(reply.status);
   }

   private void getSendActionpointInfoReply(Signal sig)
   {
      MonitorGetSendActionpointInfoReply reply =
         (MonitorGetSendActionpointInfoReply) sig;
      listener.getSendActionpointInfoReply(reply.state,
                                           reply.fromType,
                                           reply.fromId,
                                           reply.toType,
                                           reply.toId,
                                           reply.not,
                                           reply.action,
                                           reply.interceptType,
                                           reply.interceptId,
                                           reply.parameter,
                                           reply.client,
                                           reply.id,
                                           reply.count,
                                           reply.tags);
   }

   private void getReceiveActionpointInfoReply(Signal sig)
   {
      MonitorGetReceiveActionpointInfoReply reply =
         (MonitorGetReceiveActionpointInfoReply) sig;
      listener.getReceiveActionpointInfoReply(reply.state,
                                              reply.fromType,
                                              reply.fromId,
                                              reply.toType,
                                              reply.toId,
                                              reply.not,
                                              reply.action,
                                              reply.interceptType,
                                              reply.interceptId,
                                              reply.parameter,
                                              reply.client,
                                              reply.id,
                                              reply.count,
                                              reply.tags);
   }

   private void getSwapActionpointInfoReply(Signal sig)
   {
      MonitorGetSwapActionpointInfoReply reply =
         (MonitorGetSwapActionpointInfoReply) sig;
      listener.getSwapActionpointInfoReply(reply.state,
                                           reply.fromType,
                                           reply.fromId,
                                           reply.toType,
                                           reply.toId,
                                           reply.not,
                                           reply.action,
                                           reply.interceptType,
                                           reply.interceptId,
                                           reply.parameter,
                                           reply.client,
                                           reply.id,
                                           reply.count,
                                           reply.tags);
   }

   private void getCreateActionpointInfoReply(Signal sig)
   {
      MonitorGetCreateActionpointInfoReply reply =
         (MonitorGetCreateActionpointInfoReply) sig;
      listener.getCreateActionpointInfoReply(reply.state,
                                             reply.fromType,
                                             reply.fromId,
                                             reply.toType,
                                             reply.toId,
                                             reply.not,
                                             reply.action,
                                             reply.interceptType,
                                             reply.interceptId,
                                             reply.parameter,
                                             reply.client,
                                             reply.id,
                                             reply.count,
                                             reply.tags);
   }

   private void getKillActionpointInfoReply(Signal sig)
   {
      MonitorGetKillActionpointInfoReply reply =
         (MonitorGetKillActionpointInfoReply) sig;
      listener.getKillActionpointInfoReply(reply.state,
                                           reply.fromType,
                                           reply.fromId,
                                           reply.toType,
                                           reply.toId,
                                           reply.not,
                                           reply.action,
                                           reply.interceptType,
                                           reply.interceptId,
                                           reply.parameter,
                                           reply.client,
                                           reply.id,
                                           reply.count,
                                           reply.tags);
   }

   private void getErrorActionpointInfoReply(Signal sig)
   {
      MonitorGetErrorActionpointInfoReply reply =
         (MonitorGetErrorActionpointInfoReply) sig;
      listener.getErrorActionpointInfoReply(reply.state,
                                            reply.fromType,
                                            reply.fromId,
                                            reply.toType,
                                            reply.toId,
                                            reply.not,
                                            reply.action,
                                            reply.interceptType,
                                            reply.interceptId,
                                            reply.parameter,
                                            reply.client,
                                            reply.id,
                                            reply.count,
                                            reply.tags);
   }

   private void getEventActionpointInfoEndmark(Signal sig)
   {
      MonitorGetEventActionpointInfoEndmark endmark =
         (MonitorGetEventActionpointInfoEndmark) sig;
      listener.getEventActionpointInfoEndmark(endmark.status);
   }

   private void getEventActionpointsReply(Signal sig)
   {
      MonitorGetEventActionpointsReply reply =
         (MonitorGetEventActionpointsReply) sig;
      listener.getEventActionpointsReply(reply.type,
                                         reply.state,
                                         reply.fromType,
                                         reply.fromId,
                                         reply.toType,
                                         reply.toId,
                                         reply.not,
                                         reply.action,
                                         reply.interceptType,
                                         reply.interceptId,
                                         reply.parameter,
                                         reply.client,
                                         reply.id,
                                         reply.count,
                                         reply.tags);
   }

   private void getEventActionpointsEndmark(Signal sig)
   {
      MonitorGetEventActionpointsEndmark endmark =
         (MonitorGetEventActionpointsEndmark) sig;
      listener.getEventActionpointsEndmark(endmark.status);
   }

   private void getEventStateReply(Signal sig)
   {
      MonitorGetEventStateReply reply = (MonitorGetEventStateReply) sig;
      listener.getEventStateReply(reply.status, reply.state);
   }

   private void setEventStateReply(Signal sig)
   {
      MonitorSetEventStateReply reply = (MonitorSetEventStateReply) sig;
      listener.setEventStateReply(reply.status);
   }

   private void getNotifyEnabledReply(Signal sig)
   {
      MonitorGetNotifyEnabledReply reply = (MonitorGetNotifyEnabledReply) sig;
      listener.getNotifyEnabledReply(reply.status, reply.enabled);
   }

   private void setNotifyEnabledReply(Signal sig)
   {
      MonitorSetNotifyEnabledReply reply = (MonitorSetNotifyEnabledReply) sig;
      listener.setNotifyEnabledReply(reply.status);
   }

   private void processNotify(Signal sig)
   {
      MonitorProcessNotify reply = (MonitorProcessNotify) sig;
      listener.processNotify(reply.pid,
                             reply.bid,
                             reply.sid,
                             reply.type,
                             reply.entrypoint,
                             reply.properties,
                             reply.name);
   }

   private void sendNotify(Signal sig)
   {
      MonitorSendNotify reply = (MonitorSendNotify) sig;
      listener.sendNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.signalNumber,
                          reply.signalSender,
                          reply.signalAddressee,
                          reply.signalSize,
                          reply.signalAddress,
                          reply.signalId,
                          reply.lineNumber,
                          reply.euId,
                          reply.fileName,
                          reply.signalData);
   }

   private void receiveNotify(Signal sig)
   {
      MonitorReceiveNotify reply = (MonitorReceiveNotify) sig;
      listener.receiveNotify(reply.reference,
                             reply.tick,
                             reply.ntick,
                             reply.sec,
                             reply.sectick,
                             reply.signalNumber,
                             reply.signalSender,
                             reply.signalAddressee,
                             reply.signalSize,
                             reply.signalAddress,
                             reply.signalId,
                             reply.lineNumber,
                             reply.euId,
                             reply.fileName,
                             reply.signalData);
   }

   private void swapNotify(Signal sig)
   {
      MonitorSwapNotify reply = (MonitorSwapNotify) sig;
      listener.swapNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.from,
                          reply.to,
                          reply.lineNumber,
                          reply.euId,
                          reply.fileName);
   }

   private void createNotify(Signal sig)
   {
      MonitorCreateNotify reply = (MonitorCreateNotify) sig;
      listener.createNotify(reply.reference,
                            reply.tick,
                            reply.ntick,
                            reply.sec,
                            reply.sectick,
                            reply.from,
                            reply.to,
                            reply.lineNumber,
                            reply.euId,
                            reply.fileName);
   }

   private void killNotify(Signal sig)
   {
      MonitorKillNotify reply = (MonitorKillNotify) sig;
      listener.killNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.from,
                          reply.to,
                          reply.lineNumber,
                          reply.euId,
                          reply.fileName);
   }

   private void errorNotify(Signal sig)
   {
      MonitorErrorNotify reply = (MonitorErrorNotify) sig;
      listener.errorNotify(reply.reference,
                           reply.tick,
                           reply.ntick,
                           reply.sec,
                           reply.sectick,
                           reply.from,
                           reply.exec,
                           reply.error,
                           reply.extra,
                           reply.lineNumber,
                           reply.euId,
                           reply.fileName);
   }

   private void userNotify(Signal sig)
   {
      MonitorUserNotify reply = (MonitorUserNotify) sig;
      listener.userNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.from,
                          reply.eventNumber,
                          reply.eventSize,
                          reply.lineNumber,
                          reply.euId,
                          reply.fileName,
                          reply.eventData);
   }

   private void bindNotify(Signal sig)
   {
      MonitorBindNotify reply = (MonitorBindNotify) sig;
      listener.bindNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.from,
                          reply.fromEuId,
                          reply.toEuId,
                          reply.lineNumber,
                          reply.fileName);
   }

   private void allocNotify(Signal sig)
   {
      MonitorAllocNotify reply = (MonitorAllocNotify) sig;
      listener.allocNotify(reply.reference,
                           reply.tick,
                           reply.ntick,
                           reply.sec,
                           reply.sectick,
                           reply.from,
                           reply.signalNumber,
                           reply.signalSize,
                           reply.signalAddress,
                           reply.signalId,
                           reply.lineNumber,
                           reply.euId,
                           reply.fileName);
   }

   private void freeBufNotify(Signal sig)
   {
      MonitorFreeBufNotify reply = (MonitorFreeBufNotify) sig;
      listener.freeBufNotify(reply.reference,
                             reply.tick,
                             reply.ntick,
                             reply.sec,
                             reply.sectick,
                             reply.from,
                             reply.signalNumber,
                             reply.signalSize,
                             reply.signalAddress,
                             reply.signalId,
                             reply.lineNumber,
                             reply.euId,
                             reply.fileName);
   }

   private void timeoutNotify(Signal sig)
   {
      MonitorTimeoutNotify reply = (MonitorTimeoutNotify) sig;
      listener.timeoutNotify(reply.reference,
                             reply.tick,
                             reply.ntick,
                             reply.sec,
                             reply.sectick,
                             reply.timeout,
                             reply.tmoSource,
                             reply.from,
                             reply.lineNumber,
                             reply.euId,
                             reply.fileName);
   }

   private void lossNotify(Signal sig)
   {
      MonitorLossNotify reply = (MonitorLossNotify) sig;
      listener.lossNotify(reply.reference,
                          reply.tick,
                          reply.ntick,
                          reply.sec,
                          reply.sectick,
                          reply.lostSize);
   }

   private void processInterceptNotify(Signal sig)
   {
      MonitorProcessInterceptNotify reply = (MonitorProcessInterceptNotify) sig;
      listener.processInterceptNotify(reply.pid,
                                      reply.bid,
                                      reply.sid,
                                      reply.type,
                                      reply.entrypoint,
                                      reply.properties,
                                      reply.name);
   }

   private void sendInterceptNotify(Signal sig)
   {
      MonitorSendInterceptNotify reply = (MonitorSendInterceptNotify) sig;
      listener.sendInterceptNotify(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.signalNumber,
                                   reply.signalSender,
                                   reply.signalAddressee,
                                   reply.signalSize,
                                   reply.signalAddress,
                                   reply.signalId,
                                   reply.lineNumber,
                                   reply.euId,
                                   reply.fileName,
                                   reply.signalData);
   }

   private void receiveInterceptNotify(Signal sig)
   {
      MonitorReceiveInterceptNotify reply = (MonitorReceiveInterceptNotify) sig;
      listener.receiveInterceptNotify(reply.reference,
                                      reply.tick,
                                      reply.ntick,
                                      reply.sec,
                                      reply.sectick,
                                      reply.signalNumber,
                                      reply.signalSender,
                                      reply.signalAddressee,
                                      reply.signalSize,
                                      reply.signalAddress,
                                      reply.signalId,
                                      reply.lineNumber,
                                      reply.euId,
                                      reply.fileName,
                                      reply.signalData);
   }

   private void swapInterceptNotify(Signal sig)
   {
      MonitorSwapInterceptNotify reply = (MonitorSwapInterceptNotify) sig;
      listener.swapInterceptNotify(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.from,
                                   reply.to,
                                   reply.lineNumber,
                                   reply.euId,
                                   reply.fileName);
   }

   private void createInterceptNotify(Signal sig)
   {
      MonitorCreateInterceptNotify reply = (MonitorCreateInterceptNotify) sig;
      listener.createInterceptNotify(reply.reference,
                                     reply.tick,
                                     reply.ntick,
                                     reply.sec,
                                     reply.sectick,
                                     reply.from,
                                     reply.to,
                                     reply.lineNumber,
                                     reply.euId,
                                     reply.fileName);
   }

   private void killInterceptNotify(Signal sig)
   {
      MonitorKillInterceptNotify reply = (MonitorKillInterceptNotify) sig;
      listener.killInterceptNotify(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.from,
                                   reply.to,
                                   reply.lineNumber,
                                   reply.euId,
                                   reply.fileName);
   }

   private void errorInterceptNotify(Signal sig)
   {
      MonitorErrorInterceptNotify reply = (MonitorErrorInterceptNotify) sig;
      listener.errorInterceptNotify(reply.reference,
                                    reply.tick,
                                    reply.ntick,
                                    reply.sec,
                                    reply.sectick,
                                    reply.from,
                                    reply.exec,
                                    reply.error,
                                    reply.extra,
                                    reply.lineNumber,
                                    reply.euId,
                                    reply.fileName);
   }

   private void userInterceptNotify(Signal sig)
   {
      MonitorUserInterceptNotify reply = (MonitorUserInterceptNotify) sig;
      listener.userInterceptNotify(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.from,
                                   reply.eventNumber,
                                   reply.eventSize,
                                   reply.lineNumber,
                                   reply.euId,
                                   reply.fileName,
                                   reply.eventData);
   }

   private void bindInterceptNotify(Signal sig)
   {
      MonitorBindInterceptNotify reply = (MonitorBindInterceptNotify) sig;
      listener.bindInterceptNotify(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.from,
                                   reply.fromEuId,
                                   reply.toEuId,
                                   reply.lineNumber,
                                   reply.fileName);
   }

   private void allocInterceptNotify(Signal sig)
   {
      MonitorAllocInterceptNotify reply = (MonitorAllocInterceptNotify) sig;
      listener.allocInterceptNotify(reply.reference,
                                    reply.tick,
                                    reply.ntick,
                                    reply.sec,
                                    reply.sectick,
                                    reply.from,
                                    reply.signalNumber,
                                    reply.signalSize,
                                    reply.signalAddress,
                                    reply.signalId,
                                    reply.lineNumber,
                                    reply.euId,
                                    reply.fileName);
   }

   private void freeBufInterceptNotify(Signal sig)
   {
      MonitorFreeBufInterceptNotify reply = (MonitorFreeBufInterceptNotify) sig;
      listener.freeBufInterceptNotify(reply.reference,
                                      reply.tick,
                                      reply.ntick,
                                      reply.sec,
                                      reply.sectick,
                                      reply.from,
                                      reply.signalNumber,
                                      reply.signalSize,
                                      reply.signalAddress,
                                      reply.signalId,
                                      reply.lineNumber,
                                      reply.euId,
                                      reply.fileName);
   }

   private void timeoutInterceptNotify(Signal sig)
   {
      MonitorTimeoutInterceptNotify reply = (MonitorTimeoutInterceptNotify) sig;
      listener.timeoutInterceptNotify(reply.reference,
                                      reply.tick,
                                      reply.ntick,
                                      reply.sec,
                                      reply.sectick,
                                      reply.timeout,
                                      reply.tmoSource,
                                      reply.from,
                                      reply.lineNumber,
                                      reply.euId,
                                      reply.fileName);
   }

   private void getTraceEnabledReply(Signal sig)
   {
      MonitorGetTraceEnabledReply reply = (MonitorGetTraceEnabledReply) sig;
      listener.getTraceEnabledReply(reply.status, reply.enabled);
   }

   private void setTraceEnabledReply(Signal sig)
   {
      MonitorSetTraceEnabledReply reply = (MonitorSetTraceEnabledReply) sig;
      listener.setTraceEnabledReply(reply.status);
   }

   private void clearTraceReply(Signal sig)
   {
      MonitorClearTraceReply reply = (MonitorClearTraceReply) sig;
      listener.clearTraceReply(reply.status);
   }

   private void getProcessTraceReply(Signal sig)
   {
      MonitorGetProcessTraceReply reply = (MonitorGetProcessTraceReply) sig;
      listener.getProcessTraceReply(reply.pid,
                                    reply.bid,
                                    reply.sid,
                                    reply.type,
                                    reply.entrypoint,
                                    reply.properties,
                                    reply.name);
   }

   private void getSendTraceReply(Signal sig)
   {
      MonitorGetSendTraceReply reply = (MonitorGetSendTraceReply) sig;
      listener.getSendTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.signalNumber,
                                 reply.signalSender,
                                 reply.signalAddressee,
                                 reply.signalSize,
                                 reply.signalAddress,
                                 reply.signalId,
                                 reply.lineNumber,
                                 reply.euId,
                                 reply.fileName,
                                 reply.signalData);
   }

   private void getReceiveTraceReply(Signal sig)
   {
      MonitorGetReceiveTraceReply reply = (MonitorGetReceiveTraceReply) sig;
      listener.getReceiveTraceReply(reply.reference,
                                    reply.tick,
                                    reply.ntick,
                                    reply.sec,
                                    reply.sectick,
                                    reply.signalNumber,
                                    reply.signalSender,
                                    reply.signalAddressee,
                                    reply.signalSize,
                                    reply.signalAddress,
                                    reply.signalId,
                                    reply.lineNumber,
                                    reply.euId,
                                    reply.fileName,
                                    reply.signalData);
   }

   private void getSwapTraceReply(Signal sig)
   {
      MonitorGetSwapTraceReply reply = (MonitorGetSwapTraceReply) sig;
      listener.getSwapTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.from,
                                 reply.to,
                                 reply.lineNumber,
                                 reply.euId,
                                 reply.fileName);
   }

   private void getCreateTraceReply(Signal sig)
   {
      MonitorGetCreateTraceReply reply = (MonitorGetCreateTraceReply) sig;
      listener.getCreateTraceReply(reply.reference,
                                   reply.tick,
                                   reply.ntick,
                                   reply.sec,
                                   reply.sectick,
                                   reply.from,
                                   reply.to,
                                   reply.lineNumber,
                                   reply.euId,
                                   reply.fileName);
   }

   private void getKillTraceReply(Signal sig)
   {
      MonitorGetKillTraceReply reply = (MonitorGetKillTraceReply) sig;
      listener.getKillTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.from,
                                 reply.to,
                                 reply.lineNumber,
                                 reply.euId,
                                 reply.fileName);
   }

   private void getErrorTraceReply(Signal sig)
   {
      MonitorGetErrorTraceReply reply = (MonitorGetErrorTraceReply) sig;
      listener.getErrorTraceReply(reply.reference,
                                  reply.tick,
                                  reply.ntick,
                                  reply.sec,
                                  reply.sectick,
                                  reply.from,
                                  reply.exec,
                                  reply.error,
                                  reply.extra,
                                  reply.lineNumber,
                                  reply.euId,
                                  reply.fileName);
   }

   private void getResetTraceReply(Signal sig)
   {
      MonitorGetResetTraceReply reply = (MonitorGetResetTraceReply) sig;
      listener.getResetTraceReply(reply.reference,
                                  reply.tick,
                                  reply.ntick,
                                  reply.sec,
                                  reply.sectick,
                                  reply.warm,
                                  reply.lineNumber,
                                  reply.fileName);
   }

   private void getUserTraceReply(Signal sig)
   {
      MonitorGetUserTraceReply reply = (MonitorGetUserTraceReply) sig;
      listener.getUserTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.from,
                                 reply.eventNumber,
                                 reply.eventSize,
                                 reply.lineNumber,
                                 reply.euId,
                                 reply.fileName,
                                 reply.eventData);
   }

   private void getBindTraceReply(Signal sig)
   {
      MonitorGetBindTraceReply reply = (MonitorGetBindTraceReply) sig;
      listener.getBindTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.from,
                                 reply.fromEuId,
                                 reply.toEuId,
                                 reply.lineNumber,
                                 reply.fileName);
   }

   private void getAllocTraceReply(Signal sig)
   {
      MonitorGetAllocTraceReply reply = (MonitorGetAllocTraceReply) sig;
      listener.getAllocTraceReply(reply.reference,
                                  reply.tick,
                                  reply.ntick,
                                  reply.sec,
                                  reply.sectick,
                                  reply.from,
                                  reply.signalNumber,
                                  reply.signalSize,
                                  reply.signalAddress,
                                  reply.signalId,
                                  reply.lineNumber,
                                  reply.euId,
                                  reply.fileName);
   }

   private void getFreeBufTraceReply(Signal sig)
   {
      MonitorGetFreeBufTraceReply reply = (MonitorGetFreeBufTraceReply) sig;
      listener.getFreeBufTraceReply(reply.reference,
                                    reply.tick,
                                    reply.ntick,
                                    reply.sec,
                                    reply.sectick,
                                    reply.from,
                                    reply.signalNumber,
                                    reply.signalSize,
                                    reply.signalAddress,
                                    reply.signalId,
                                    reply.lineNumber,
                                    reply.euId,
                                    reply.fileName);
   }

   private void getTimeoutTraceReply(Signal sig)
   {
      MonitorGetTimeoutTraceReply reply = (MonitorGetTimeoutTraceReply) sig;
      listener.getTimeoutTraceReply(reply.reference,
                                    reply.tick,
                                    reply.ntick,
                                    reply.sec,
                                    reply.sectick,
                                    reply.timeout,
                                    reply.tmoSource,
                                    reply.from,
                                    reply.lineNumber,
                                    reply.euId,
                                    reply.fileName);
   }

   private void getLossTraceReply(Signal sig)
   {
      MonitorGetLossTraceReply reply = (MonitorGetLossTraceReply) sig;
      listener.getLossTraceReply(reply.reference,
                                 reply.tick,
                                 reply.ntick,
                                 reply.sec,
                                 reply.sectick,
                                 reply.lostSize);
   }

   private void getTraceEndmark(Signal sig)
   {
      MonitorGetTraceEndmark endmark = (MonitorGetTraceEndmark) sig;
      listener.getTraceEndmark(endmark.status);
   }

   private void getTraceMultipleReply(Signal sig)
   {
      MonitorGetTraceMultipleReply reply = (MonitorGetTraceMultipleReply) sig;
      listener.getTraceMultipleReply(reply.status,
                                     reply.handle,
                                     reply.enabled,
                                     reply.events);
   }

   class ReceiverThread extends Thread
   {
      private volatile boolean listen;

      public ReceiverThread()
      {
         super("OSE Monitor Receiver Thread");
         setDaemon(true);
         setPriority(Thread.NORM_PRIORITY - 1);
         listen = true;
      }

      public void shutdown()
      {
         listen = false;
         interrupt();

         // If interrupting the receiver thread doesn't work, forcefully
         // disconnect the receiver gateway to provoke the shutdown of the
         // receiver thread.
         if (isAlive())
         {
            try
            {
               join(1000);
            }
            catch (InterruptedException ignore) {}
            if (isAlive())
            {
               receiverGateway.forcedDisconnect();
            }
         }
      }

      public void run()
      {
         Signal sig = null;
         boolean attachReceived = false;

         while (listen && !isInterrupted())
         {
            try
            {
               sig = receiverGateway.receive(registry, SignalSelect.ANY_SIGNAL);

               switch (sig.getSigNo())
               {
                  case MonitorAttachSignal.SIG_NO:
                     attachReceived = true;
                     listen = false;
                     connected = false;
                     monitorKilled(sig);
                     break;
                  case MonitorGetProcessInfoReply.SIG_NO:
                     getProcessInfoReply(sig);
                     break;
                  case MonitorGetProcessInfoLongReply.SIG_NO:
                     getProcessInfoLongReply(sig);
                     break;
                  case MonitorGetProcessInfoEndmark.SIG_NO:
                     getProcessInfoEndmark(sig);
                     break;
                  case MonitorGetBlockInfoReply.SIG_NO:
                     getBlockInfoReply(sig);
                     break;
                  case MonitorGetBlockInfoEndmark.SIG_NO:
                     getBlockInfoEndmark(sig);
                     break;
                  case MonitorGetSegmentInfoReply.SIG_NO:
                     getSegmentInfoReply(sig);
                     break;
                  case MonitorGetSegmentInfoLongReply.SIG_NO:
                     getSegmentInfoLongReply(sig);
                     break;
                  case MonitorGetSegmentInfoEndmark.SIG_NO:
                     getSegmentInfoEndmark(sig);
                     break;
                  case MonitorGetStackUsageReply.SIG_NO:
                     getStackUsageReply(sig);
                     break;
                  case MonitorGetStackUsageEndmark.SIG_NO:
                     getStackUsageEndmark(sig);
                     break;
                  case MonitorGetEnvVarsReply.SIG_NO:
                     getEnvVarsReply(sig);
                     break;
                  case MonitorGetEnvVarsEndmark.SIG_NO:
                     getEnvVarsEndmark(sig);
                     break;
                  case MonitorGetSignalQueueReply.SIG_NO:
                     getSignalQueueReply(sig);
                     break;
                  case MonitorGetSignalQueueLongReply.SIG_NO:
                     getSignalQueueLongReply(sig);
                     break;
                  case MonitorGetSignalQueueEndmark.SIG_NO:
                     getSignalQueueEndmark(sig);
                     break;
                  case MonitorGetPoolInfoReply.SIG_NO:
                     getPoolInfoReply(sig);
                     break;
                  case MonitorGetPoolInfoEndmark.SIG_NO:
                     getPoolInfoEndmark(sig);
                     break;
                  case MonitorGetPoolSignalsReply.SIG_NO:
                     getPoolSignalsReply(sig);
                     break;
                  case MonitorGetPoolSignalsEndmark.SIG_NO:
                     getPoolSignalsEndmark(sig);
                     break;
                  case MonitorGetHeapInfoReply.SIG_NO:
                     getHeapInfoReply(sig);
                     break;
                  case MonitorGetHeapInfoEndmark.SIG_NO:
                     getHeapInfoEndmark(sig);
                     break;
                  case MonitorGetHeapFragmentInfoReply.SIG_NO:
                     getHeapFragmentInfoReply(sig);
                     break;
                  case MonitorGetHeapFragmentInfoEndmark.SIG_NO:
                     getHeapFragmentInfoEndmark(sig);
                     break;
                  case MonitorGetHeapBuffersReply.SIG_NO:
                     getHeapBuffersReply(sig);
                     break;
                  case MonitorGetHeapBuffersEndmark.SIG_NO:
                     getHeapBuffersEndmark(sig);
                     break;
                  case MonitorGetSysParamReply.SIG_NO:
                     getSysParamReply(sig);
                     break;
                  case MonitorGetSysParamEndmark.SIG_NO:
                     getSysParamEndmark(sig);
                     break;
                  case MonitorSetSysParamReply.SIG_NO:
                     setSysParamReply(sig);
                     break;
                  case MonitorGetMemoryReply.SIG_NO:
                     getMemoryReply(sig);
                     break;
                  case MonitorGetMemoryEndmark.SIG_NO:
                     getMemoryEndmark(sig);
                     break;
                  case MonitorKillScopeReply.SIG_NO:
                     killScopeReply(sig);
                     break;
                  case MonitorStartScopeReply.SIG_NO:
                     startScopeReply(sig);
                     break;
                  case MonitorStopScopeReply.SIG_NO:
                     stopScopeReply(sig);
                     break;
                  case MonitorSetEnvVarReply.SIG_NO:
                     setEnvVarReply(sig);
                     break;
                  case MonitorSignalSemaphoreReply.SIG_NO:
                     signalSemaphoreReply(sig);
                     break;
                  case MonitorSetPriorityReply.SIG_NO:
                     setPriorityReply(sig);
                     break;
                  case MonitorSetExecutionUnitReply.SIG_NO:
                     setExecutionUnitReply(sig);
                     break;
                  case MonitorGetRamdumpInfoReply.SIG_NO:
                     getRamdumpInfoReply(sig);
                     break;
                  case MonitorGetRamdumpInfoEndmark.SIG_NO:
                     getRamdumpInfoEndmark(sig);
                     break;
                  case MonitorGetRamdumpReply.SIG_NO:
                     getRamdumpReply(sig);
                     break;
                  case MonitorGetRamdumpEndmark.SIG_NO:
                     getRamdumpEndmark(sig);
                     break;
                  case MonitorGetCPUReportsEnabledReply.SIG_NO:
                     getCPUReportsEnabledReply(sig);
                     break;
                  case MonitorSetCPUReportsEnabledReply.SIG_NO:
                     setCPUReportsEnabledReply(sig);
                     break;
                  case MonitorGetCPUReportsReply.SIG_NO:
                     getCPUReportsReply(sig);
                     break;
                  case MonitorGetCPUPriReportsEnabledReply.SIG_NO:
                     getCPUPriReportsEnabledReply(sig);
                     break;
                  case MonitorSetCPUPriReportsEnabledReply.SIG_NO:
                     setCPUPriReportsEnabledReply(sig);
                     break;
                  case MonitorGetCPUPriReportsReply.SIG_NO:
                     getCPUPriReportsReply(sig);
                     break;
                  case MonitorGetCPUProcessReportsEnabledReply.SIG_NO:
                     getCPUProcessReportsEnabledReply(sig);
                     break;
                  case MonitorSetCPUProcessReportsEnabledReply.SIG_NO:
                     setCPUProcessReportsEnabledReply(sig);
                     break;
                  case MonitorSetCPUProcessReportpointReply.SIG_NO:
                     setCPUProcessReportpointReply(sig);
                     break;
                  case MonitorClearCPUProcessReportpointReply.SIG_NO:
                     clearCPUProcessReportpointReply(sig);
                     break;
                  case MonitorGetCPUProcessReportpointInfoReply.SIG_NO:
                     getCPUProcessReportpointInfoReply(sig);
                     break;
                  case MonitorGetCPUProcessReportpointInfoEndmark.SIG_NO:
                     getCPUProcessReportpointInfoEndmark(sig);
                     break;
                  case MonitorGetCPUProcessReportsReply.SIG_NO:
                     getCPUProcessReportsReply(sig);
                     break;
                  case MonitorGetCPUBlockReportsEnabledReply.SIG_NO:
                     getCPUBlockReportsEnabledReply(sig);
                     break;
                  case MonitorSetCPUBlockReportsEnabledReply.SIG_NO:
                     setCPUBlockReportsEnabledReply(sig);
                     break;
                  case MonitorGetCPUBlockReportsReply.SIG_NO:
                     getCPUBlockReportsReply(sig);
                     break;
                  case MonitorGetUserReportsEnabledReply.SIG_NO:
                     getUserReportsEnabledReply(sig);
                     break;
                  case MonitorSetUserReportsEnabledReply.SIG_NO:
                     setUserReportsEnabledReply(sig);
                     break;
                  case MonitorGetUserReportsReply.SIG_NO:
                     getUserReportsReply(sig);
                     break;
                  case MonitorGetSupportedCounterTypesReply.SIG_NO:
                     getSupportedCounterTypesReply(sig);
                     break;
                  case MonitorGetSupportedCounterTypesEndmark.SIG_NO:
                     getSupportedCounterTypesEndmark(sig);
                     break;
                  case MonitorGetCounterTypeEnabledReply.SIG_NO:
                     getCounterTypeEnabledReply(sig);
                     break;
                  case MonitorSetCounterTypeEnabledReply.SIG_NO:
                     setCounterTypeEnabledReply(sig);
                     break;
                  case MonitorGetCounterReportsNotifyEnabledReply.SIG_NO:
                     getCounterReportsNotifyEnabledReply(sig);
                     break;
                  case MonitorSetCounterReportsNotifyEnabledReply.SIG_NO:
                     setCounterReportsNotifyEnabledReply(sig);
                     break;
                  case MonitorCounterReportsNotify.SIG_NO:
                     counterReportsNotify(sig);
                     break;
                  case MonitorCounterReportsLossNotify.SIG_NO:
                     counterReportsLossNotify(sig);
                     break;
                  case MonitorAttachReply.SIG_NO:
                     attachReply(sig);
                     break;
                  case MonitorDetachReply.SIG_NO:
                     detachReply(sig);
                     break;
                  case MonitorInterceptScopeReply.SIG_NO:
                     interceptScopeReply(sig);
                     break;
                  case MonitorResumeScopeReply.SIG_NO:
                     resumeScopeReply(sig);
                     break;
                  case MonitorSetScopeReply.SIG_NO:
                     setScopeReply(sig);
                     break;
                  case MonitorSetInterceptScopeReply.SIG_NO:
                     setInterceptScopeReply(sig);
                     break;
                  case MonitorSetEventActionpointReply.SIG_NO:
                     setEventActionpointReply(sig);
                     break;
                  case MonitorClearEventActionpointReply.SIG_NO:
                     clearEventActionpointReply(sig);
                     break;
                  case MonitorGetSendActionpointInfoReply.SIG_NO:
                     getSendActionpointInfoReply(sig);
                     break;
                  case MonitorGetReceiveActionpointInfoReply.SIG_NO:
                     getReceiveActionpointInfoReply(sig);
                     break;
                  case MonitorGetSwapActionpointInfoReply.SIG_NO:
                     getSwapActionpointInfoReply(sig);
                     break;
                  case MonitorGetCreateActionpointInfoReply.SIG_NO:
                     getCreateActionpointInfoReply(sig);
                     break;
                  case MonitorGetKillActionpointInfoReply.SIG_NO:
                     getKillActionpointInfoReply(sig);
                     break;
                  case MonitorGetErrorActionpointInfoReply.SIG_NO:
                     getErrorActionpointInfoReply(sig);
                     break;
                  case MonitorGetEventActionpointInfoEndmark.SIG_NO:
                     getEventActionpointInfoEndmark(sig);
                     break;
                  case MonitorGetEventActionpointsReply.SIG_NO:
                     getEventActionpointsReply(sig);
                     break;
                  case MonitorGetEventActionpointsEndmark.SIG_NO:
                     getEventActionpointsEndmark(sig);
                     break;
                  case MonitorGetEventStateReply.SIG_NO:
                     getEventStateReply(sig);
                     break;
                  case MonitorSetEventStateReply.SIG_NO:
                     setEventStateReply(sig);
                     break;
                  case MonitorGetNotifyEnabledReply.SIG_NO:
                     getNotifyEnabledReply(sig);
                     break;
                  case MonitorSetNotifyEnabledReply.SIG_NO:
                     setNotifyEnabledReply(sig);
                     break;
                  case MonitorProcessNotify.SIG_NO:
                     processNotify(sig);
                     break;
                  case MonitorSendNotify.SIG_NO:
                     sendNotify(sig);
                     break;
                  case MonitorReceiveNotify.SIG_NO:
                     receiveNotify(sig);
                     break;
                  case MonitorSwapNotify.SIG_NO:
                     swapNotify(sig);
                     break;
                  case MonitorCreateNotify.SIG_NO:
                     createNotify(sig);
                     break;
                  case MonitorKillNotify.SIG_NO:
                     killNotify(sig);
                     break;
                  case MonitorErrorNotify.SIG_NO:
                     errorNotify(sig);
                     break;
                  case MonitorUserNotify.SIG_NO:
                     userNotify(sig);
                     break;
                  case MonitorBindNotify.SIG_NO:
                     bindNotify(sig);
                     break;
                  case MonitorAllocNotify.SIG_NO:
                     allocNotify(sig);
                     break;
                  case MonitorFreeBufNotify.SIG_NO:
                     freeBufNotify(sig);
                     break;
                  case MonitorTimeoutNotify.SIG_NO:
                     timeoutNotify(sig);
                     break;
                  case MonitorLossNotify.SIG_NO:
                     lossNotify(sig);
                     break;
                  case MonitorProcessInterceptNotify.SIG_NO:
                     processInterceptNotify(sig);
                     break;
                  case MonitorSendInterceptNotify.SIG_NO:
                     sendInterceptNotify(sig);
                     break;
                  case MonitorReceiveInterceptNotify.SIG_NO:
                     receiveInterceptNotify(sig);
                     break;
                  case MonitorSwapInterceptNotify.SIG_NO:
                     swapInterceptNotify(sig);
                     break;
                  case MonitorCreateInterceptNotify.SIG_NO:
                     createInterceptNotify(sig);
                     break;
                  case MonitorKillInterceptNotify.SIG_NO:
                     killInterceptNotify(sig);
                     break;
                  case MonitorErrorInterceptNotify.SIG_NO:
                     errorInterceptNotify(sig);
                     break;
                  case MonitorUserInterceptNotify.SIG_NO:
                     userInterceptNotify(sig);
                     break;
                  case MonitorBindInterceptNotify.SIG_NO:
                     bindInterceptNotify(sig);
                     break;
                  case MonitorAllocInterceptNotify.SIG_NO:
                     allocInterceptNotify(sig);
                     break;
                  case MonitorFreeBufInterceptNotify.SIG_NO:
                     freeBufInterceptNotify(sig);
                     break;
                  case MonitorTimeoutInterceptNotify.SIG_NO:
                     timeoutInterceptNotify(sig);
                     break;
                  case MonitorGetTraceEnabledReply.SIG_NO:
                     getTraceEnabledReply(sig);
                     break;
                  case MonitorSetTraceEnabledReply.SIG_NO:
                     setTraceEnabledReply(sig);
                     break;
                  case MonitorClearTraceReply.SIG_NO:
                     clearTraceReply(sig);
                     break;
                  case MonitorGetProcessTraceReply.SIG_NO:
                     getProcessTraceReply(sig);
                     break;
                  case MonitorGetSendTraceReply.SIG_NO:
                     getSendTraceReply(sig);
                     break;
                  case MonitorGetReceiveTraceReply.SIG_NO:
                     getReceiveTraceReply(sig);
                     break;
                  case MonitorGetSwapTraceReply.SIG_NO:
                     getSwapTraceReply(sig);
                     break;
                  case MonitorGetCreateTraceReply.SIG_NO:
                     getCreateTraceReply(sig);
                     break;
                  case MonitorGetKillTraceReply.SIG_NO:
                     getKillTraceReply(sig);
                     break;
                  case MonitorGetErrorTraceReply.SIG_NO:
                     getErrorTraceReply(sig);
                     break;
                  case MonitorGetResetTraceReply.SIG_NO:
                     getResetTraceReply(sig);
                     break;
                  case MonitorGetUserTraceReply.SIG_NO:
                     getUserTraceReply(sig);
                     break;
                  case MonitorGetBindTraceReply.SIG_NO:
                     getBindTraceReply(sig);
                     break;
                  case MonitorGetAllocTraceReply.SIG_NO:
                     getAllocTraceReply(sig);
                     break;
                  case MonitorGetFreeBufTraceReply.SIG_NO:
                     getFreeBufTraceReply(sig);
                     break;
                  case MonitorGetTimeoutTraceReply.SIG_NO:
                     getTimeoutTraceReply(sig);
                     break;
                  case MonitorGetLossTraceReply.SIG_NO:
                     getLossTraceReply(sig);
                     break;
                  case MonitorGetTraceEndmark.SIG_NO:
                     getTraceEndmark(sig);
                     break;
                  case MonitorGetTraceMultipleReply.SIG_NO:
                     getTraceMultipleReply(sig);
                     break;
                  case MonitorBlockingFlowControlRequest.SIG_NO:
                     receiverGateway.send(
                           monitorPid, new MonitorBlockingFlowControlReply());
                     break;
                  case MonitorNonBlockingFlowControlRequest.SIG_NO:
                     receiverGateway.send(
                           monitorPid, new MonitorNonBlockingFlowControlReply());
                     break;
                  default:
                     throw new IOException("Unknown signal received in " +
                           "monitor receiver thread: " + sig.getSigNo());
               }

               // Handle obsolete flow control.
               if (hasObsoleteFlowControlRequest && !attachReceived)
               {
                  obsoleteFlowControl -=
                     sig.getSigSize() + MONITOR_OBSOLETE_MIN_FLOW_CONTROL;
                  if (obsoleteFlowControl < obsoleteFlowControlThreshold)
                  {
                     MonitorObsoleteFlowControlRequest ack =
                        new MonitorObsoleteFlowControlRequest(obsoleteFlowControlThreshold);
                     receiverGateway.send(monitorPid, ack);
                     obsoleteFlowControl += obsoleteFlowControlThreshold;
                  }
               }
            }
            catch (InterruptedIOException e)
            {
               // Receiver thread interrupted.
               break;
            }
            catch (Throwable e)
            {
               if (!listen)
               {
                  // Exit if exception was thrown due to a forced disconnect of
                  // the receiver gateway when shutdown of this receiver thread
                  // was issued.
                  return;
               }
               else
               {
                  System.err.println("Exception occurred in monitor receiver thread: " + e);
                  listener.exceptionThrown(e);
                  break;
               }
            }
         }

         try
         {
            // Only detach from the monitor if we
            // have not received the attached signal.
            if (!attachReceived)
            {
               receiverGateway.detach(attref);
            }
         }
         catch (IOException e)
         {
            System.err.println("Detaching from monitor failed.");
         }

         try
         {
            receiverGateway.disconnect();
         }
         catch (IOException e)
         {
            System.err.println("Disconnecting monitor receiver gateway failed.");
         }
      }
   }
}
