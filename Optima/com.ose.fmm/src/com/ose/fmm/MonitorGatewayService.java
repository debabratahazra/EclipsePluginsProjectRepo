/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.fmm;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.NullProgressMonitor;
import com.ose.fmm.Fmi.HostFmiInitParams;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.system.Gate;
import com.ose.system.Process;
import com.ose.system.SystemModel;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorSignalInfo;
import com.ose.system.service.monitor.MonitorStackInfo;
import com.ose.system.service.monitor.MonitorStatus;
import com.ose.system.service.monitor.MonitorTag;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlRequest;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoRequest;
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
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetSegmentInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueLongReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueReply;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueRequest;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageEndmark;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageReply;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageRequest;
import com.ose.system.service.monitor.signal.MonitorGetSysParamRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;

public class MonitorGatewayService extends AbstractGatewayService
{
   private static final String FMI_ADDRESS_TRANSLATION = "fmi.address.translation";
   private static final String SERVICE_NAME = "ose_monitor";
   private static final int MEMORY_CHUNK_SIZE = 4096;
   private static final int MAX_SIGNAL_INFO_COUNT_IN_REPLY = 10;
   private static final int HOST_FMI_VERSION = 1;

   private final Fmi fmi;
   private final CharsetDecoder decoder;
   private final boolean bigEndian;
   private final SignalRegistry signalRegistry;
   private final short[] features;
   private final Map clients;
   private final ITargetProxy targetProxy;
   private final ITargetProxyListener targetProxyListener;
   private final String libraryParentPath;
   private final boolean addressTranslation;

   public MonitorGatewayService(Fmi fmi,
                                ITargetProxy targetProxy,
                                String libraryPath,
                                boolean addressTranslation)
      throws IOException
   {
      this.fmi = fmi;
      this.targetProxy = targetProxy;
      
      decoder = Charset.forName("ISO-8859-1").newDecoder();
      bigEndian = targetProxy.isBigEndian();
      
      File libraryFile = new File(libraryPath);
      libraryParentPath = libraryFile.getParent();

      if (System.getProperty(FMI_ADDRESS_TRANSLATION) != null)
      {
         this.addressTranslation = Boolean.getBoolean(FMI_ADDRESS_TRANSLATION);
      }
      else
      {
         this.addressTranslation = addressTranslation;
      }

      targetProxyListener = new TargetProxyListener();
      targetProxy.addTargetProxyListener(targetProxyListener);

      // Initialize the monitor signal registry.
      signalRegistry = new SignalRegistry();
      signalRegistry.add(MonitorInterfaceRequest.class);
      signalRegistry.add(MonitorNameRequest.class);
      signalRegistry.add(MonitorConnectRequest.class);
      signalRegistry.add(MonitorDisconnectRequest.class);
      signalRegistry.add(MonitorBlockingFlowControlReply.class);
      signalRegistry.add(MonitorGetSysParamRequest.class);
      signalRegistry.add(MonitorGetSegmentInfoRequest.class);
      signalRegistry.add(MonitorGetBlockInfoRequest.class);
      signalRegistry.add(MonitorGetProcessInfoRequest.class);
      signalRegistry.add(MonitorGetEnvVarsRequest.class);
      signalRegistry.add(MonitorGetStackUsageRequest.class);
      signalRegistry.add(MonitorGetSignalQueueRequest.class);
      signalRegistry.add(MonitorGetPoolInfoRequest.class);
      signalRegistry.add(MonitorGetPoolSignalsRequest.class);
      signalRegistry.add(MonitorGetMemoryRequest.class);

      // Create the supported features arrays.
      features = new short[]
      {
         Monitor.MONITOR_FEATURE_SYSTEM_PROCESSES,
         Monitor.MONITOR_FEATURE_PROCESS_FILTER,
         Monitor.MONITOR_FEATURE_BLOCK_FILTER,
         Monitor.MONITOR_FEATURE_SEGMENT_FILTER,
         Monitor.MONITOR_FEATURE_SIGNAL_FILTER,
         Monitor.MONITOR_FEATURE_STACK_FILTER,
         Monitor.MONITOR_FEATURE_SEGMENT_INFO_LONG,
      };

      // Create the monitor clients map.
      clients = new HashMap();
   }

   public String getName()
   {
      return SERVICE_NAME;
   }

   public void signalReceived(Signal signal)
   {
      handleRequest(signal);
   }

   public void signalReceived(int sender,
                              int addressee,
                              int sigSize,
                              int sigNo,
                              byte[] sigData)
   {
      try
      {
         Signal signal = createSignal(sender,
                                      addressee,
                                      sigSize,
                                      sigNo,
                                      sigData,
                                      signalRegistry,
                                      bigEndian);
         handleRequest(signal);
      }
      catch (IOException e)
      {
         log("I/O exception in freeze mode monitor when receiving signal " + sigNo, e);
      }
   }

   protected void close()
   {
      clients.clear();
      
      targetProxy.removeTargetProxyListener(targetProxyListener);

      if (fmi.isInitialized())
      {
         fmi.ose_host_fmi_destroy();
         fmi.setInitialized(false);
      }
   }
   
   private void initializeFmi()
   {
      HostFmiInitParams params = new HostFmiInitParams();
      params.interface_version = HOST_FMI_VERSION;
      params.max_cache = -1;
      params.use_domain = !addressTranslation;
      params.endian = bigEndian ? Fmi.EFMI_BIG_ENDIAN_VALUE
                                : Fmi.EFMI_LITTLE_ENDIAN_VALUE;
      params.path = libraryParentPath;
      int status = fmi.ose_host_fmi_init(params);
      if (status == Fmi.HOST_FMI_OK)
      {
         fmi.setInitialized(true);
         addGate();
      }
      else if (status > 0)
      {
         throw new RuntimeException("Initialization of freeze mode library failed: "
               + Fmi.fmiStatusToString(status));
      }
      else
      {
         // Kernel data structures not ready, another attempt will be made
         // the next time the target is suspended.
      }
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();
      int client = signal.getSender();

      if (targetProxy.isSuspended() && fmi.isInitialized())
      {
         switch (sigNo)
         {
            case MonitorInterfaceRequest.SIG_NO:
               handleInterfaceRequest(signal);
               break;
            case MonitorNameRequest.SIG_NO:
               handleNameRequest(signal);
               break;
            case MonitorConnectRequest.SIG_NO:
               handleConnectRequest(signal);
               break;
            case MonitorDisconnectRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleDisconnectRequest(signal);
               }
               break;
            case MonitorBlockingFlowControlReply.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleBlockingFlowControlReply(signal);
               }
               break;
            case MonitorGetSysParamRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetSysParamRequest(signal);
               }
               break;
            case MonitorGetSegmentInfoRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetSegmentInfoRequest(signal);
               }
               break;
            case MonitorGetBlockInfoRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetBlockInfoRequest(signal);
               }
               break;
            case MonitorGetProcessInfoRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetProcessInfoRequest(signal);
               }
               break;
            case MonitorGetEnvVarsRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetEnvVarsRequest(signal);
               }
               break;
            case MonitorGetStackUsageRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetStackUsageRequest(signal);
               }
               break;
            case MonitorGetSignalQueueRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetSignalQueueRequest(signal);
               }
               break;
            case MonitorGetPoolInfoRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetPoolInfoRequest(signal);
               }
               break;
            case MonitorGetPoolSignalsRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetPoolSignalsRequest(signal);
               }
               break;
            case MonitorGetMemoryRequest.SIG_NO:
               if (isConnectedAndSupported(client, sigNo))
               {
                  handleGetMemoryRequest(signal);
               }
               break;
            default:
               log("Unknown signal received by PMD monitor: " + sigNo);
               break;
         }
      }
   }

   private void handleInterfaceRequest(Signal sig)
   {
      int status;
      MonitorInterfaceReply reply;
      reply = new MonitorInterfaceReply();

      Fmi.EfmiSystemInfo info = new Fmi.EfmiSystemInfo();
      status = fmi.ose_efmi_get_system_info(info);
      if (status != Fmi.EFMI_OK)
      {
         // FIXME: We should probably abort here?
         reply.osType = Monitor.MONITOR_OS_UNKNOWN;
         reply.cpuClass = Monitor.MONITOR_CPU_UNKNOWN;
         reply.endian = bigEndian;
      }
      else
      {
         reply.osType = efmiToMonitorOSType(info.os_type);
         reply.cpuClass = efmiToMonitorCPUType(info.cpu_type);
         reply.endian = Arrays.equals(info.endian, Fmi.EFMI_BIG_ENDIAN_VALUE);
      }

      ArrayList sigs = new ArrayList();

      // Supported by all systems
      sigs.add(new Integer(MonitorInterfaceRequest.SIG_NO));
      sigs.add(new Integer(MonitorNameRequest.SIG_NO));
      sigs.add(new Integer(MonitorConnectRequest.SIG_NO));
      sigs.add(new Integer(MonitorDisconnectRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetProcessInfoRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetStackUsageRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetSignalQueueRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetPoolInfoRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetPoolSignalsRequest.SIG_NO));
      sigs.add(new Integer(MonitorGetMemoryRequest.SIG_NO));

      // Supported by some systems
      if (fmi.ose_host_fmi_is_supported(
            Fmi.HOST_FMI_FEATURE_SYSTEM_PARAMETERS) != 0)
      {
         sigs.add(new Integer(MonitorGetSysParamRequest.SIG_NO));
      }

      if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_SEGMENT) != 0)
      {
         sigs.add(new Integer(MonitorGetSegmentInfoRequest.SIG_NO));
      }

      if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_BLOCK) != 0)
      {
         sigs.add(new Integer(MonitorGetBlockInfoRequest.SIG_NO));
      }

      if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_ENVIRONMENT_VARIABLES) != 0)
      {
         sigs.add(new Integer(MonitorGetEnvVarsRequest.SIG_NO));
      }

      reply.sigs = new int[sigs.size()];

      for (int i = 0; i < sigs.size(); i++)
      {
         reply.sigs[i] = ((Integer) sigs.get(i)).intValue();
      }

      sendSignal(sig.getSender(), reply);
   }

   private void handleNameRequest(Signal sig)
   {
      int status;
      MonitorNameReply reply;

      reply = new MonitorNameReply();

      Fmi.EfmiString name = new Fmi.EfmiString();
      status = fmi.ose_host_fmi_get_system_name(name);
      if (status != Fmi.EFMI_OK)
      {
         reply.systemName = "";
      }
      else
      {
         reply.systemName = name.string;
      }

      reply.monitorName = "com.ose.FreezeModeMonitor";
      sendSignal(sig.getSender(), reply);
   }

   private void handleConnectRequest(Signal sig)
   {
      MonitorConnectRequest request;
      MonitorClient client;
      MonitorConnectReply reply;
      int status;

      request = (MonitorConnectRequest) sig;
      client = new MonitorClient(sig.getSender(), request.name);
      clients.put(new Integer(sig.getSender()), client);

      reply = new MonitorConnectReply();
      reply.status = MonitorStatus.MONITOR_STATUS_OK;

      Fmi.EfmiConfigurationInfo tickLength = new Fmi.EfmiConfigurationInfo();
      status = fmi.ose_efmi_get_config(Fmi.EFMI_CONF_TICK_LEN, tickLength);
      if (status != Fmi.EFMI_OK)
      {
         reply.status = efmiToMonitorStatus(status);
      }
      reply.tickLength = tickLength.value;

      reply.tick = 0; // TODO: Find a way to retrieve this
      reply.ntick = 0; // TODO: Find a way to retrieve this

      Fmi.EfmiConfigurationInfo ntickFrequency = new Fmi.EfmiConfigurationInfo();
      status = fmi.ose_efmi_get_config(Fmi.EFMI_CONF_NTICK_FREQ, ntickFrequency);
      if (status != Fmi.EFMI_OK)
      {
         reply.status = efmiToMonitorStatus(status);
      }
      reply.ntickFrequency = ntickFrequency.value;

      Fmi.EfmiSystemInfo info = new Fmi.EfmiSystemInfo();
      status = fmi.ose_efmi_get_system_info(info);
      if (status != Fmi.EFMI_OK)
      {
         reply.cpuClass = Monitor.MONITOR_CPU_UNKNOWN;
      }
      else
      {
         reply.cpuClass = efmiToMonitorCPUType(info.cpu_type);
      }

      reply.cpuType = 0; // NOT USED
      reply.errorHandler = 0; // TODO: Do not hardcode

      Fmi.EfmiInteger euCount = new Fmi.EfmiInteger(1);
      status = fmi.ose_efmi_get_smp_num_cpus(euCount);
      if (status != Fmi.EFMI_OK)
      {
         reply.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
      }
      reply.euCount = (short) euCount.value;

      reply.featuresCount = features.length;
      reply.features = features;
      sendSignal(sig.getSender(), reply);
   }

   private void handleDisconnectRequest(Signal sig)
   {
      clients.remove(new Integer(sig.getSender()));
   }

   // XXX: No flow control is actually used. Should it be used?
   private void handleBlockingFlowControlReply(Signal sig)
   {
      sendSignal(sig.getSender(), new MonitorBlockingFlowControlRequest());
   }

   private void handleGetSysParamRequest(Signal sig)
   {
      // TODO: Implement me, but not for OSEck
   }

   private void handleGetSegmentInfoRequest(Signal sig)
   {
      MonitorGetSegmentInfoRequest request = (MonitorGetSegmentInfoRequest) sig;
      MonitorGetSegmentInfoEndmark endmark = new MonitorGetSegmentInfoEndmark();
      List replies = new ArrayList();

      if (supportsSegments())
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_OK;

         try
         {
            switch (request.scopeType)
            {
               case Monitor.MONITOR_SCOPE_SYSTEM:
                  addSegmentInfoRepliesFromSystemScope(replies, request.level);
                  break;
               case Monitor.MONITOR_SCOPE_ID:
                  replies.add(createGetSegmentInfoReply(request.scopeId, request.level));
                  break;
               default:
                  endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  break;
            }
         }
         catch (EfmiException e)
         {
            endmark.status = efmiToMonitorStatus(e.getStatus());
         }
         catch (PfmiException e)
         {
            ProgramManagerGatewayService.pfmiToPmStatus(e.getStatus());
         }

         // Send remaining process replies
         sendSignals(sig.getSender(), replies);
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
      }

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetBlockInfoRequest(Signal sig)
   {
      MonitorGetBlockInfoRequest request = (MonitorGetBlockInfoRequest) sig;;
      MonitorGetBlockInfoEndmark endmark = new MonitorGetBlockInfoEndmark();
      List replies = new ArrayList();

      if (supportsBlocks())
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_OK;

         try
         {
            switch (request.scopeType)
            {
               case Monitor.MONITOR_SCOPE_SYSTEM:
                  addBlockInfoRepliesFromSystemScope(replies);
                  break;
               case Monitor.MONITOR_SCOPE_SEGMENT_ID:
                  addBlockInfoRepliesFromSegmentScope(request.scopeId, replies);
                  break;
               case Monitor.MONITOR_SCOPE_ID:
                  replies.add(createGetBlockInfoReply(request.scopeId));
                  break;
               default:
                  endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
                  break;
            }
         }
         catch (EfmiException e)
         {
            endmark.status = efmiToMonitorStatus(e.getStatus());
         }

         // Filter out block replies based on filter tags
         replies = filterBlocks(replies, request.tags);

         // Send remaining process replies
         sendSignals(sig.getSender(), replies);
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
      }

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetProcessInfoRequest(Signal sig)
   {
      MonitorGetProcessInfoRequest request;
      MonitorGetProcessInfoEndmark endmark = new MonitorGetProcessInfoEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      request = (MonitorGetProcessInfoRequest) sig;

      try
      {
         switch (request.scopeType)
         {
            case Monitor.MONITOR_SCOPE_SYSTEM:
               if (tagsContainRunning(request.tags))
               {
                  addProcessInfoRepliesFromCurrent(request.level, replies);
               }
               else
               {
                  addProcessInfoRepliesFromSystemScope(request.level, replies);
               }
               break;
            case Monitor.MONITOR_SCOPE_SEGMENT_ID:
               if (supportsSegments())
               {
                  addProcessInfoRepliesFromSegmentScope(request.scopeId,
                                                        request.level, replies);
               }
               else
               {
                  endmark.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
               }
               break;
            case Monitor.MONITOR_SCOPE_BLOCK_ID:
               if (supportsBlocks())
               {
                  addProcessInfoRepliesFromBlockScope(request.scopeId,
                                                      request.level, replies);
               }
               else
               {
                  endmark.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
               }
               break;
            case Monitor.MONITOR_SCOPE_ID:
               replies.add(createGetProcessInfoReply(request.scopeId,
                                                     request.level));
               break;
            default:
               endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
               break;
         }
      }
      catch (EfmiException e)
      {
         endmark.status = efmiToMonitorStatus(e.getStatus());
      }

      // Filter out process replies based on filter tags
      if (request.level == 0)
      {
         replies = filterProcesses(replies, request.tags);
      }
      else
      {
         replies = filterLongProcesses(replies, request.tags);
      }

      // Send remaining process replies
      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetEnvVarsRequest(Signal sig)
   {
      MonitorGetEnvVarsRequest request;
      MonitorGetEnvVarsEndmark endmark = new MonitorGetEnvVarsEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      request = (MonitorGetEnvVarsRequest) sig;

      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         int status;
         Fmi.EfmiCursor envVarCursor = new Fmi.EfmiCursor();
         Fmi.EfmiString name = new Fmi.EfmiString();
         Fmi.EfmiString value = new Fmi.EfmiString();

         status = fmi.ose_efmi_get_first_env_var(request.scopeId,
                                                  envVarCursor,
                                                  name, value);

         while (status == Fmi.EFMI_OK)
         {
            MonitorGetEnvVarsReply reply = new MonitorGetEnvVarsReply();
            reply.pid = request.scopeId;
            reply.name = name.string;
            reply.value = value.string;
            replies.add(reply);

            name = new Fmi.EfmiString();
            value = new Fmi.EfmiString();
            status = fmi.ose_efmi_get_next_env_var(envVarCursor,
                                                    name, value);
         }

         endmark.status = efmiToMonitorStatus(status);
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
      }

      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetStackUsageRequest(Signal sig)
   {
      MonitorGetStackUsageRequest request;
      MonitorGetStackUsageEndmark endmark = new MonitorGetStackUsageEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      request = (MonitorGetStackUsageRequest) sig;

      try
      {
         switch (request.scopeType)
         {
            case Monitor.MONITOR_SCOPE_SYSTEM:
               addStackUsageRepliesFromSystemScope(replies);
               break;
            case Monitor.MONITOR_SCOPE_SEGMENT_ID:
               if (supportsSegments())
               {
                  addStackUsageRepliesFromSegmentScope(request.scopeId, replies);
               }
               else
               {
                  endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
               }
               break;
            case Monitor.MONITOR_SCOPE_BLOCK_ID:
               if (supportsBlocks())
               {
                  addStackUsageRepliesFromBlockScope(request.scopeId, replies);
               }
               else
               {
                  endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
               }
               break;
            case Monitor.MONITOR_SCOPE_ID:
               int status;
               Fmi.EfmiProcessInfo processInfo = new Fmi.EfmiProcessInfo();
               status = fmi.ose_efmi_get_process_info(request.scopeId, processInfo);
               if (status != Fmi.EFMI_OK)
               {
                  throw new EfmiException(status);
               }
               addStackUsageReplyFromProcessScope(processInfo, replies);
               break;
            default:
               endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
               break;
         }
      }
      catch (EfmiException e)
      {
         endmark.status = efmiToMonitorStatus(e.getStatus());
      }

      replies = filterStacks(replies, request.tags);

      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetSignalQueueRequest(Signal sig)
   {
      MonitorGetSignalQueueRequest request = (MonitorGetSignalQueueRequest) sig;
      MonitorGetSignalQueueEndmark endmark = new MonitorGetSignalQueueEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         int status;
         Fmi.EfmiCursor signalCursor = new Fmi.EfmiCursor();
         Fmi.EfmiSignalInfo signalInfo = new Fmi.EfmiSignalInfo();

         status = fmi.ose_efmi_get_first_q_signal(request.scopeId,
                                                   signalCursor, signalInfo);

         while (status == Fmi.EFMI_OK)
         {
            if (request.level == 0)
            {
               MonitorGetSignalQueueReply reply = new MonitorGetSignalQueueReply();
               reply.pid = request.scopeId;
               reply.signal = new MonitorSignalInfo();
               reply.signal.number = signalInfo.sig_no;
               reply.signal.owner = signalInfo.owner;
               reply.signal.sender = signalInfo.sender_pid;
               reply.signal.addressee = signalInfo.addressee_pid;
               reply.signal.size = signalInfo.sig_size;
               reply.signal.bufsize = signalInfo.size_in_pool;
               reply.signal.address = signalInfo.signal_pointer;
               reply.signal.status = signalInfo.sig_status;
               replies.add(reply);
            }
            else
            {
               byte[] signalData = new byte[signalInfo.sig_size];
               status = fmi.ose_efmi_get_mem(request.scopeId,
                                              signalInfo.signal_pointer,
                                              signalData);
               if (status != Fmi.EFMI_OK)
               {
                  break;
               }

               MonitorGetSignalQueueLongReply reply = new MonitorGetSignalQueueLongReply();
               reply.pid = request.scopeId;
               reply.signal = new MonitorSignalInfo();
               reply.signal.number = signalInfo.sig_no;
               reply.signal.owner = signalInfo.owner;
               reply.signal.sender = signalInfo.sender_pid;
               reply.signal.addressee = signalInfo.addressee_pid;
               reply.signal.size = signalInfo.sig_size;
               reply.signal.bufsize = signalInfo.size_in_pool;
               reply.signal.address = signalInfo.signal_pointer;
               reply.signal.status = signalInfo.sig_status;
               reply.data = signalData;
               replies.add(reply);
            }

            signalInfo = new Fmi.EfmiSignalInfo();
            status = fmi.ose_efmi_get_next_q_signal(signalCursor, signalInfo);
         }

         endmark.status = efmiToMonitorStatus(status);
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
      }

      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetPoolInfoRequest(Signal sig)
   {
      MonitorGetPoolInfoRequest request;
      MonitorGetPoolInfoEndmark endmark = new MonitorGetPoolInfoEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      request = (MonitorGetPoolInfoRequest) sig;

      try
      {
         switch (request.scopeType)
         {
            case Monitor.MONITOR_SCOPE_SYSTEM:
               addPoolInfoRepliesFromSystemScope(replies);
               break;
            case Monitor.MONITOR_SCOPE_SEGMENT_ID:
               addPoolInfoRepliesFromSegmentScope(request.scopeId, replies);
               break;
            case Monitor.MONITOR_SCOPE_ID:
               replies.add(createGetPoolInfoReply(request.scopeId));
               break;
            default:
               endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
               break;
         }
      }
      catch (EfmiException e)
      {
         endmark.status = efmiToMonitorStatus(e.getStatus());
      }

      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetPoolSignalsRequest(Signal sig)
   {
      MonitorGetPoolSignalsRequest request = (MonitorGetPoolSignalsRequest) sig;
      MonitorGetPoolSignalsEndmark endmark = new MonitorGetPoolSignalsEndmark();
      List replies = new ArrayList();

      endmark.status = MonitorStatus.MONITOR_STATUS_OK;

      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         int status;
         Fmi.EfmiCursor signalCursor = new Fmi.EfmiCursor();
         Fmi.EfmiSignalInfo signalInfo = new Fmi.EfmiSignalInfo();
         List signalInfos = new ArrayList();

         status = fmi.ose_efmi_get_first_pool_signal(request.scopeId,
                                                      signalCursor,
                                                      signalInfo);

         while (status == Fmi.EFMI_OK)
         {
            MonitorGetPoolSignalsReply reply = new MonitorGetPoolSignalsReply();
            reply.pid = request.scopeId;

            for (int i = 0; i < MAX_SIGNAL_INFO_COUNT_IN_REPLY; i++)
            {
               MonitorSignalInfo monitorSignalInfo = new MonitorSignalInfo();
               monitorSignalInfo.number = signalInfo.sig_no;
               monitorSignalInfo.owner = signalInfo.owner;
               monitorSignalInfo.sender = signalInfo.sender_pid;
               monitorSignalInfo.addressee = signalInfo.addressee_pid;
               monitorSignalInfo.size = signalInfo.sig_size;
               monitorSignalInfo.bufsize = signalInfo.size_in_pool;
               monitorSignalInfo.address = signalInfo.signal_pointer;
               monitorSignalInfo.status = signalInfo.sig_status;
               signalInfos.add(monitorSignalInfo);

               signalInfo = new Fmi.EfmiSignalInfo();
               status = fmi.ose_efmi_get_next_pool_signal(signalCursor,
                                                           signalInfo);

               if (status != Fmi.EFMI_OK)
               {
                  break;
               }
            }

            if (!signalInfos.isEmpty())
            {
               reply.signals = new MonitorSignalInfo[signalInfos.size()];

               for (int i = 0; i < reply.signals.length; i++)
               {
                  reply.signals[i] = (MonitorSignalInfo) signalInfos.get(i);
               }

               replies.add(reply);
            }

            if (status != Fmi.EFMI_OK)
            {
               break;
            }

            signalInfos.clear();
         }

         endmark.status = efmiToMonitorStatus(status);
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_SCOPE_INVALID;
      }

      replies = filterSignals(replies, request.tags);

      sendSignals(sig.getSender(), replies);

      sendSignal(request.getSender(), endmark);
   }

   private void handleGetMemoryRequest(Signal sig)
   {
      MonitorGetMemoryRequest request;
      int status = Fmi.EFMI_OK;

      request = (MonitorGetMemoryRequest) sig;

      long reqStart = request.address & 0xFFFFFFFFL;
      long reqSize = request.size & 0xFFFFFFFFL;
      long offset = 0;

      while (reqSize > 0)
      {
         MonitorGetMemoryReply reply = new MonitorGetMemoryReply();
         int chunkSize = (int) Math.min(reqSize, (long) MEMORY_CHUNK_SIZE);
         byte[] buffer = new byte[chunkSize];

         status = fmi.ose_efmi_get_mem(request.pid, request.address, buffer);
         if (status != Fmi.EFMI_OK)
         {
            break;
         }

         reply.pid = request.pid;
         reply.address = (int) (reqStart + offset);
         reply.data = buffer;

         sendSignal(request.getSender(), reply);

         reqSize -= (long) chunkSize;
         offset += (long) chunkSize;
      }

      MonitorGetMemoryEndmark endmark = new MonitorGetMemoryEndmark();

      if (status != Fmi.EFMI_OK)
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_MEMORY_GET_ERROR;
      }
      else
      {
         endmark.status = MonitorStatus.MONITOR_STATUS_OK;
      }

      sendSignal(request.getSender(), endmark);
   }

   private void addSegmentInfoRepliesFromSystemScope(List replies, int level) throws EfmiException, PfmiException
   {
      int status;
      Fmi.EfmiCursor segmentCursor = new Fmi.EfmiCursor();
      Fmi.EfmiSegmentInfo segmentInfo = new Fmi.EfmiSegmentInfo();

      status = fmi.ose_efmi_get_first_segment(segmentCursor, segmentInfo);

      while (status == Fmi.EFMI_OK)
      {
         replies.add(createGetSegmentInfoReply(segmentInfo, level));

         segmentInfo = new Fmi.EfmiSegmentInfo();
         status = fmi.ose_efmi_get_next_segment(segmentCursor, segmentInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private Signal createGetSegmentInfoReply(int sid, int level) throws EfmiException, PfmiException
   {
      int status;
      Fmi.EfmiSegmentInfo segmentInfo = new Fmi.EfmiSegmentInfo();

      status = fmi.ose_efmi_get_segment_info(sid, segmentInfo);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      return createGetSegmentInfoReply(segmentInfo, level);
   }

   private Signal createGetSegmentInfoReply(Fmi.EfmiSegmentInfo segmentInfo, int level) throws EfmiException, PfmiException
   {      
      if (level > 0
          && fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_LOAD_MODULE) != 0
          && fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_SEGMENT) != 0
          && fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_BLOCK) != 0)
      {
         int status;
         MonitorGetSegmentInfoLongReply reply = new MonitorGetSegmentInfoLongReply();
         
         reply.sid = segmentInfo.segment;
         reply.number = segmentInfo.segid;

         Fmi.EfmiInteger progpid = new Fmi.EfmiInteger(0);
         status = fmi.ose_pfmi_get_progpid(segmentInfo.segment, progpid);
         if (status != Fmi.PFMI_OK)
         {
            throw new PfmiException(status);
         }
         
         Fmi.PfmiProgramInfo programInfo = new Fmi.PfmiProgramInfo();
         status = fmi.ose_pfmi_get_program_info(progpid.value, programInfo);
         if (status != Fmi.PFMI_OK)
         {
            throw new PfmiException(status);
         }
         
         Fmi.EfmiString blockName = new Fmi.EfmiString();
         status = fmi.ose_efmi_get_process_name(programInfo.main_block, blockName);
         if (status != Fmi.EFMI_OK)
         {
            throw new EfmiException(status);
         }

         reply.name = blockName.string;

         return reply;         
      }
                  
      MonitorGetSegmentInfoReply reply = new MonitorGetSegmentInfoReply();
      reply.sid = segmentInfo.segment;
      reply.number = segmentInfo.segid;
      return reply;
   }

   private void addBlockInfoRepliesFromSystemScope(List replies) throws EfmiException
   {
      int status;
      Fmi.EfmiCursor segmentCursor = new Fmi.EfmiCursor();
      Fmi.EfmiSegmentInfo segmentInfo = new Fmi.EfmiSegmentInfo();

      status = fmi.ose_efmi_get_first_segment(segmentCursor, segmentInfo);

      while (status == Fmi.EFMI_OK)
      {
         addBlockInfoRepliesFromSegmentScope(segmentInfo.segment, replies);

         segmentInfo = new Fmi.EfmiSegmentInfo();
         status = fmi.ose_efmi_get_next_segment(segmentCursor, segmentInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private void addBlockInfoRepliesFromSegmentScope(int segmentId, List replies) throws EfmiException
   {
      int status;

      Fmi.EfmiCursor blockCursor = new Fmi.EfmiCursor();
      Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();

      status = fmi.ose_efmi_get_first_block(segmentId, blockCursor, blockInfo);

      while (status == Fmi.EFMI_OK)
      {
         replies.add(createGetBlockInfoReply(blockInfo));

         blockInfo = new Fmi.EfmiBlockInfo();
         status = fmi.ose_efmi_get_next_block(blockCursor, blockInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private Signal createGetBlockInfoReply(int bid) throws EfmiException
   {
      int status;
      Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();

      status = fmi.ose_efmi_get_block_info(bid, blockInfo);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      return createGetBlockInfoReply(blockInfo);
   }

   private Signal createGetBlockInfoReply(Fmi.EfmiBlockInfo blockInfo) throws EfmiException
   {
      int status;

      Fmi.EfmiString blockName = new Fmi.EfmiString();
      status = fmi.ose_efmi_get_process_name(blockInfo.block, blockName);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      MonitorGetBlockInfoReply reply = new MonitorGetBlockInfoReply();
      reply.bid = blockInfo.block;
      reply.sid = blockInfo.segment;
      reply.uid = blockInfo.user;
      reply.supervisor = blockInfo.supervisor_mode != 0; // NOT USED
      reply.signalsAttached = 0; // NOT USED
      reply.errorHandler = 0; // NOT USED
      reply.maxSigSize = 0; // TODO: Find a way to retrieve this info
      reply.sigPoolId = blockInfo.sig_pool;
      reply.stackPoolId = blockInfo.stack_pool;
      reply.euId = (short) blockInfo.cpu_id;
      reply.name = blockName.string;

      return reply;
   }

   private void addProcessInfoRepliesFromCurrent(int level, List replies) throws EfmiException
   {
      int status;
      
      Fmi.EfmiCursor currentCursor = new Fmi.EfmiCursor();
      Fmi.EfmiCurrent currentInfo = new Fmi.EfmiCurrent();
      
      status = fmi.ose_efmi_get_first_current(currentCursor, currentInfo);
      
      while (status == Fmi.EFMI_OK)
      {
         replies.add(createGetProcessInfoReply(currentInfo.process, level));
      
         currentInfo = new Fmi.EfmiCurrent();
         status = fmi.ose_efmi_get_next_current(currentCursor, currentInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }   
   }

   private void addProcessInfoRepliesFromSystemScope(int level, List replies) throws EfmiException
   {
      int status;

      if (supportsSegments())
      {
         Fmi.EfmiCursor segmentCursor = new Fmi.EfmiCursor();
         Fmi.EfmiSegmentInfo segmentInfo = new Fmi.EfmiSegmentInfo();

         status = fmi.ose_efmi_get_first_segment(segmentCursor, segmentInfo);

         while (status == Fmi.EFMI_OK)
         {
            addProcessInfoRepliesFromSegmentScope(segmentInfo.segment, level, replies);

            segmentInfo = new Fmi.EfmiSegmentInfo();
            status = fmi.ose_efmi_get_next_segment(segmentCursor, segmentInfo);
         }

         if (status != Fmi.EFMI_END_OF_LIST)
         {
            throw new EfmiException(status);
         }
      }
      else if (supportsBlocks())
      {
         addProcessInfoRepliesFromSegmentScope(0, level, replies);
      }
      else
      {
         addProcessInfoRepliesFromBlockScope(0, level, replies);
      }
   }

   private void addProcessInfoRepliesFromSegmentScope(int segmentId, int level, List replies) throws EfmiException
   {
      int status;

      Fmi.EfmiCursor blockCursor = new Fmi.EfmiCursor();
      Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();

      status = fmi.ose_efmi_get_first_block(segmentId, blockCursor, blockInfo);

      while (status == Fmi.EFMI_OK)
      {
         addProcessInfoRepliesFromBlockScope(blockInfo.block, level, replies);

         blockInfo = new Fmi.EfmiBlockInfo();
         status = fmi.ose_efmi_get_next_block(blockCursor, blockInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private void addProcessInfoRepliesFromBlockScope(int blockId, int level, List replies) throws EfmiException
   {
      int status;
      Fmi.EfmiCursor processCursor = new Fmi.EfmiCursor();
      Fmi.EfmiProcessInfo processInfo = new Fmi.EfmiProcessInfo();

      status = fmi.ose_efmi_get_first_process(blockId,
                                               processCursor,
                                               processInfo);

      while (status == Fmi.EFMI_OK)
      {
         replies.add(createGetProcessInfoReply(processInfo, level));

         processInfo = new Fmi.EfmiProcessInfo();
         status = fmi.ose_efmi_get_next_process(processCursor, processInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private Signal createGetProcessInfoReply(int pid, int level) throws EfmiException
   {
      int status;
      Fmi.EfmiProcessInfo processInfo = new Fmi.EfmiProcessInfo();

      status = fmi.ose_efmi_get_process_info(pid, processInfo);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      return createGetProcessInfoReply(processInfo, level);
   }

   private Signal createGetProcessInfoReply(Fmi.EfmiProcessInfo processInfo, int level) throws EfmiException
   {
      int status;
      int sid = 0;

      Fmi.EfmiString processName = new Fmi.EfmiString();
      status = fmi.ose_efmi_get_process_name(processInfo.process, processName);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      if (supportsBlocks())
      {
         Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();
         status = fmi.ose_efmi_get_block_info(processInfo.block, blockInfo);
         if (status != Fmi.EFMI_OK)
         {
            throw new EfmiException(status);
         }

         sid = blockInfo.segment;
      }

      Fmi.EfmiProcessStatistics inQueue = new Fmi.EfmiProcessStatistics();
      status = fmi.ose_efmi_get_process_statistics(processInfo.process,
                                         Fmi.EFMI_SIG_CNT_IN_Q, inQueue);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      MonitorGetProcessInfoLongReply reply = new MonitorGetProcessInfoLongReply();
      reply.pid = processInfo.process;
      reply.bid = processInfo.block;
      reply.sid = sid;
      reply.type = processInfo.type;
      reply.state = toMonitorProcessState(processInfo.process,
                                          processInfo.status);
      reply.priority = processInfo.priority;
      reply.uid = processInfo.user;
      reply.creator = 0; // NOT USED
      reply.supervisor = false; // NOT USED
      reply.signalsInQueue = inQueue.value;
      reply.signalsAttached = 0; // NOT USED
      reply.entrypoint = processInfo.entry_point;
      reply.semaphoreValue = processInfo.fsemvalue;
      reply.stackSize = processInfo.process_stack.top
                        - processInfo.process_stack.limit;
      reply.supervisorStackSize = 0; // NOT USED
      reply.lineNumber = processInfo.line;
      reply.signalsOwned = processInfo.sig_cnt_owned;
      reply.timeSlice = 0; // NOT USED
      reply.vector = 0; // NOT USED
      reply.errorHandler = 0; // NOT USED
      reply.properties = 0; // TODO: Not available in EFMI
      reply.euId = (short) processInfo.cpu_id;
      reply.sigselect = processInfo.wanted;
      reply.processName = processName.string;
      reply.fileName = processInfo.file;

      if (level == 0)
      {
         return convertToGetProcessInfoReply(reply);
      }
      else
      {
         return reply;
      }
   }

   private MonitorGetProcessInfoReply convertToGetProcessInfoReply(
         MonitorGetProcessInfoLongReply longReply)
   {
      MonitorGetProcessInfoReply reply = new MonitorGetProcessInfoReply();
      reply.pid = longReply.pid;
      reply.bid = longReply.bid;
      reply.sid = longReply.sid;
      reply.type = longReply.type;
      reply.state = longReply.state;
      reply.priority = longReply.priority;
      reply.entrypoint = longReply.entrypoint;
      reply.properties = longReply.properties;
      reply.name = longReply.processName;

      return reply;
   }

   private void addStackUsageRepliesFromSystemScope(List replies) throws EfmiException
   {
      int status;
      Fmi.EfmiCursor segmentCursor = new Fmi.EfmiCursor();
      Fmi.EfmiSegmentInfo segmentInfo = new Fmi.EfmiSegmentInfo();

      if (supportsSegments())
      {
         status = fmi.ose_efmi_get_first_segment(segmentCursor, segmentInfo);

         while (status == Fmi.EFMI_OK)
         {
            addStackUsageRepliesFromSegmentScope(segmentInfo.segment, replies);

            segmentInfo = new Fmi.EfmiSegmentInfo();
            status = fmi.ose_efmi_get_next_segment(segmentCursor, segmentInfo);
         }

         if (status != Fmi.EFMI_END_OF_LIST)
         {
            throw new EfmiException(status);
         }
      }
      else if (supportsBlocks())
      {
         addStackUsageRepliesFromSegmentScope(0, replies);
      }
      else
      {
         addStackUsageRepliesFromBlockScope(0, replies);
      }
   }

   private void addStackUsageRepliesFromSegmentScope(int segmentId, List replies) throws EfmiException
   {
      int status;

      Fmi.EfmiCursor blockCursor = new Fmi.EfmiCursor();
      Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();

      status = fmi.ose_efmi_get_first_block(segmentId, blockCursor, blockInfo);

      while (status == Fmi.EFMI_OK)
      {
         addStackUsageRepliesFromBlockScope(blockInfo.block, replies);

         blockInfo = new Fmi.EfmiBlockInfo();
         status = fmi.ose_efmi_get_next_block(blockCursor, blockInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private void addStackUsageRepliesFromBlockScope(int blockId, List replies) throws EfmiException
   {
      int status;
      Fmi.EfmiCursor processCursor = new Fmi.EfmiCursor();
      Fmi.EfmiProcessInfo processInfo = new Fmi.EfmiProcessInfo();

      status = fmi.ose_efmi_get_first_process(blockId,
                                               processCursor,
                                               processInfo);

      while (status == Fmi.EFMI_OK)
      {
         addStackUsageReplyFromProcessScope(processInfo, replies);

         processInfo = new Fmi.EfmiProcessInfo();
         status = fmi.ose_efmi_get_next_process(processCursor, processInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private void addStackUsageReplyFromProcessScope(Fmi.EfmiProcessInfo processInfo, List replies) throws EfmiException
   {
      // FIXME: Should interrupt and timer interrupt processes be
      // accepted here for OSE?
      if (processInfo.type == Monitor.MONITOR_PROCESS_TYPE_PRIORITIZED
         || processInfo.type == Monitor.MONITOR_PROCESS_TYPE_BACKGROUND
         || processInfo.type == Monitor.MONITOR_PROCESS_TYPE_IDLE)
      {
         replies.add(createGetStackUsageReply(processInfo));
      }
   }

   private Signal createGetStackUsageReply(Fmi.EfmiProcessInfo processInfo) throws EfmiException
   {
      int status;
      int stackSize;

      Fmi.EfmiProcessStatistics stackUsage = new Fmi.EfmiProcessStatistics();
      status = fmi.ose_efmi_get_process_statistics(processInfo.process,
                                     Fmi.EFMI_PROCESS_STACK_USAGE, stackUsage);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      stackSize = processInfo.process_stack.top - processInfo.process_stack.limit;

      MonitorGetStackUsageReply reply = new MonitorGetStackUsageReply();
      reply.pid = processInfo.process;
      reply.main = new MonitorStackInfo();
      reply.main.address = processInfo.process_stack.limit;
      reply.main.size = stackSize;
      reply.main.used = stackUsage.value;
      reply.main.bufsize = getStackBufferSize(processInfo, stackSize);
      reply.supervisor = new MonitorStackInfo();
      reply.supervisor.address = processInfo.supervisor_stack.limit;
      reply.supervisor.size = processInfo.process_stack.top
                              - processInfo.process_stack.limit;
      reply.supervisor.used = 0; // TODO: Don't know how to get this info
      reply.supervisor.bufsize = 0; // TODO: Don't know how to get this info

      return reply;
   }

   private int getStackBufferSize(Fmi.EfmiProcessInfo processInfo,
                                  int stackSize) throws EfmiException
   {
      int status;
      int bufferSize = 0;
      int stack_pool = 0;

      if (supportsBlocks())
      {
         Fmi.EfmiBlockInfo blockInfo = new Fmi.EfmiBlockInfo();

         status = fmi.ose_efmi_get_block_info(processInfo.block, blockInfo);
         if (status != Fmi.EFMI_OK)
         {
            throw new EfmiException(status);
         }

         stack_pool = blockInfo.stack_pool;
      }

      Fmi.EfmiPoolInfo poolInfo = new Fmi.EfmiPoolInfo();

      status = fmi.ose_efmi_get_pool_info(stack_pool, poolInfo);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      for (int i = 0; i < Fmi.EFMI_MAX_STACK_BUFFER_SIZE_COUNT; i++)
      {
         if (stackSize <= poolInfo.stack_buf_sizes[i].configured_size)
         {
            bufferSize = poolInfo.stack_buf_sizes[i].configured_size;
            break;
         }
      }

      return bufferSize;
   }

   private void addPoolInfoRepliesFromSystemScope(List replies) throws EfmiException
   {
      int status;

      Fmi.EfmiCursor poolCursor = new Fmi.EfmiCursor();
      Fmi.EfmiPoolInfo poolInfo = new Fmi.EfmiPoolInfo();

      status = fmi.ose_efmi_get_first_pool(poolCursor, poolInfo);

      while (status == Fmi.EFMI_OK)
      {
         replies.add(createGetPoolInfoReply(poolInfo));

         poolInfo = new Fmi.EfmiPoolInfo();
         status = fmi.ose_efmi_get_next_pool(poolCursor, poolInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private void addPoolInfoRepliesFromSegmentScope(int segmentId, List replies) throws EfmiException
   {
      int status;

      Fmi.EfmiCursor poolCursor = new Fmi.EfmiCursor();
      Fmi.EfmiPoolInfo poolInfo = new Fmi.EfmiPoolInfo();

      status = fmi.ose_efmi_get_first_pool(poolCursor, poolInfo);

      while (status == Fmi.EFMI_OK)
      {
         if (poolInfo.segment == segmentId)
         {
            replies.add(createGetPoolInfoReply(poolInfo));
         }

         poolInfo = new Fmi.EfmiPoolInfo();
         status = fmi.ose_efmi_get_next_pool(poolCursor, poolInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }
   }

   private Signal createGetPoolInfoReply(int poolId) throws EfmiException
   {
      int status;
      Fmi.EfmiPoolInfo poolInfo = new Fmi.EfmiPoolInfo();

      status = fmi.ose_efmi_get_pool_info(poolId, poolInfo);
      if (status != Fmi.EFMI_OK)
      {
         throw new EfmiException(status);
      }

      return createGetPoolInfoReply(poolInfo);
   }

   private Signal createGetPoolInfoReply(Fmi.EfmiPoolInfo poolInfo) throws EfmiException
   {
      int status;

      MonitorGetPoolInfoReply reply = new MonitorGetPoolInfoReply();

      reply.pid = poolInfo.pool_id;
      reply.sid = poolInfo.segment;
      reply.total = 0; // REVIEW: Sum of all fragments?
      reply.free = 0; // REVIEW: Sum of all fragments - used in all fragments?

      // ---- FRAGMENTS ----
      Fmi.EfmiCursor fragmentCursor = new Fmi.EfmiCursor();
      Fmi.EfmiFragmentInfo fragmentInfo = new Fmi.EfmiFragmentInfo();
      List fragments = new ArrayList();

      status = fmi.ose_efmi_get_first_fragment(poolInfo.pool_id, fragmentCursor,
                                                fragmentInfo);
      while (status == Fmi.EFMI_OK)
      {
         MonitorPoolFragmentInfo monFragInfo = new MonitorPoolFragmentInfo();
         monFragInfo.address = fragmentInfo.base_address;
         monFragInfo.size = fragmentInfo.size;
         monFragInfo.stacks = fragmentInfo.used_for_stacks;
         monFragInfo.signals = fragmentInfo.used_for_signals;
         fragments.add(monFragInfo);

         reply.total += fragmentInfo.size;
         reply.free += fragmentInfo.size - fragmentInfo.used_for_stacks
                      - fragmentInfo.used_for_signals;

         status = fmi.ose_efmi_get_next_fragment(fragmentCursor, fragmentInfo);
      }

      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }

      Object[] frags = fragments.toArray();
      reply.fragments = new MonitorPoolFragmentInfo[frags.length];
      System.arraycopy(frags, 0, reply.fragments, 0, frags.length);

      // ---- STACKS ----
      MonitorPoolBufferSizeInfo[] stackSizes =
         new MonitorPoolBufferSizeInfo[Fmi.EFMI_MAX_STACK_BUFFER_SIZE_COUNT];

      for (int i = 0; i < Fmi.EFMI_MAX_STACK_BUFFER_SIZE_COUNT; i++)
      {
         MonitorPoolBufferSizeInfo stackSize = new MonitorPoolBufferSizeInfo();
         stackSize.size = poolInfo.stack_buf_sizes[i].configured_size;
         stackSize.allocated = poolInfo.stack_buf_sizes[i].allocated;
         stackSize.free = poolInfo.stack_buf_sizes[i].free;
         stackSizes[i] = stackSize;
      }

      reply.stackSizes = stackSizes;

      // ---- SIGNALS ----
      MonitorPoolBufferSizeInfo[] signalSizes =
         new MonitorPoolBufferSizeInfo[Fmi.EFMI_MAX_SIGNAL_BUFFER_SIZE_COUNT];

      for (int i = 0; i < Fmi.EFMI_MAX_STACK_BUFFER_SIZE_COUNT; i++)
      {
         MonitorPoolBufferSizeInfo signalSize = new MonitorPoolBufferSizeInfo();
         signalSize.size = poolInfo.signal_buf_sizes[i].configured_size;
         signalSize.allocated = poolInfo.signal_buf_sizes[i].allocated;
         signalSize.free = poolInfo.signal_buf_sizes[i].free;
         signalSizes[i] = signalSize;
      }

      reply.signalSizes = signalSizes;

      reply.stackAlignment = (byte) poolInfo.stack_alignment;
      reply.stackAdmSize = (byte) poolInfo.stack_admin_size;
      reply.stackInternalAdmSize = (byte) poolInfo.stack_internal_admin_size;
      reply.signalAlignment = (byte) poolInfo.signal_alignment;
      reply.signalAdmSize = (byte) poolInfo.signal_admin_size;
      reply.signalInternalAdmSize = (byte) poolInfo.signal_internal_admin_size;

      return reply;
   }

   private List filterBlocks(List blocks, MonitorTag[] tags)
   {
      if (blocks == null)
      {
         throw new NullPointerException();
      }

      if ((tags == null) || (tags.length == 0))
      {
         return blocks;
      }

      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) i.next();
         boolean not = false;

         TAGSLOOP: for (int j = 0; j < tags.length; j++)
         {
            MonitorTag tag;
            short type;
            byte[] args;
            ByteBuffer buffer = null;
            int value;
            int min;
            int max;
            String str;

            tag = tags[j];
            type = tag.getType();
            args = (byte[]) tag.getArgs();

            if (args != null)
            {
               buffer = ByteBuffer.wrap(args);
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else if (type != Monitor.MONITOR_BLOCK_FILTER_TAG_NOT)
            {
               // Ignore and skip invalid tags.
               continue;
            }

            switch (type)
            {
               case Monitor.MONITOR_BLOCK_FILTER_TAG_NOT:
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_UID:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((block.uid < min) || (block.uid >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_SUPERVISOR:
                  boolean supervisor = (buffer.getInt() != 0);
                  if (not ^ (block.supervisor != supervisor))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_SIGNALS_ATTACHED:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((block.signalsAttached < min) ||
                        (block.signalsAttached >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_ERROR_HANDLER:
                  value = buffer.getInt();
                  if (not ^ (block.errorHandler != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_MAX_SIG_SIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((block.maxSigSize < min) ||
                        (block.maxSigSize >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_SIG_POOL_ID:
                  value = buffer.getInt();
                  if (not ^ (block.sigPoolId != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_STACK_POOL_ID:
                  value = buffer.getInt();
                  if (not ^ (block.stackPoolId != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_NAME:
                  str = bytesToString(buffer);
                  if (not ^ !match(str, block.name))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_BLOCK_FILTER_TAG_EU_ID:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ (((block.euId & 0xFFFF) < min) ||
                        ((block.euId & 0xFFFF) >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               default:
                  // Ignore and skip invalid tags.
                  break;
            }

            not = (type == Monitor.MONITOR_BLOCK_FILTER_TAG_NOT);
         }
      }

      return blocks;
   }

   private boolean tagsContainRunning(MonitorTag[] tags)
   {
      boolean not = false;

      if ((tags == null) || (tags.length == 0))
      {
         return false;
      }
      
      for (int j = 0; j < tags.length; j++)
      {
         MonitorTag tag;
         short type;
         byte[] args;
         ByteBuffer buffer = null;
         int value;

         tag = tags[j];
         type = tag.getType();
         args = (byte[]) tag.getArgs();

         if (args != null)
         {
            buffer = ByteBuffer.wrap(args);
            buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
         }
         else if (type != Monitor.MONITOR_PROCESS_FILTER_TAG_NOT)
         {
            // Ignore and skip invalid tags.
            continue;
         }

         switch (type)
         {
            case Monitor.MONITOR_PROCESS_FILTER_TAG_NOT:
               break;
            case Monitor.MONITOR_PROCESS_FILTER_TAG_STATE:
               value = buffer.getInt();
               if (!not && (value == Process.STATE_RUNNING))
               {
                  return true;
               }
               break;
            default:
               // Ignore and skip other tags.
               break;
         }

         not = (type == Monitor.MONITOR_PROCESS_FILTER_TAG_NOT);
      }
      
      return false;
   }   
   
   private List filterProcesses(List processes, MonitorTag[] tags)
   {
      if (processes == null)
      {
         throw new NullPointerException();
      }

      if ((tags == null) || (tags.length == 0))
      {
         return processes;
      }

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         MonitorGetProcessInfoReply process =
            (MonitorGetProcessInfoReply) i.next();
         boolean not = false;

         TAGSLOOP: for (int j = 0; j < tags.length; j++)
         {
            MonitorTag tag;
            short type;
            byte[] args;
            ByteBuffer buffer = null;
            int value;
            int min;
            int max;
            String str;

            tag = tags[j];
            type = tag.getType();
            args = (byte[]) tag.getArgs();

            if (args != null)
            {
               buffer = ByteBuffer.wrap(args);
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else if (type != Monitor.MONITOR_PROCESS_FILTER_TAG_NOT)
            {
               // Ignore and skip invalid tags.
               continue;
            }

            switch (type)
            {
               case Monitor.MONITOR_PROCESS_FILTER_TAG_NOT:
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_TYPE:
                  value = buffer.getInt();
                  if (not ^ (process.type != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_STATE:
                  value = buffer.getInt();
                  if (not ^ (process.state != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_PRIORITY:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.priority < min) || (process.priority >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_ENTRYPOINT:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.entrypoint < min) ||
                        (process.entrypoint >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_NAME:
                  str = bytesToString(buffer);
                  if (not ^ !match(str, process.name))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SYSTEM:
                  value = buffer.getInt();
                  if (not ^ ((process.properties & 1) == 0))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               default:
                  // Ignore and skip invalid tags.
                  break;
            }

            not = (type == Monitor.MONITOR_PROCESS_FILTER_TAG_NOT);
         }
      }

      return processes;
   }

   private List filterLongProcesses(List processes, MonitorTag[] tags)
   {
      if (processes == null)
      {
         throw new NullPointerException();
      }

      if ((tags == null) || (tags.length == 0))
      {
         return processes;
      }

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         MonitorGetProcessInfoLongReply process =
            (MonitorGetProcessInfoLongReply) i.next();
         boolean not = false;

         TAGSLOOP: for (int j = 0; j < tags.length; j++)
         {
            MonitorTag tag;
            short type;
            byte[] args;
            ByteBuffer buffer = null;
            int value;
            int min;
            int max;
            String str;

            tag = tags[j];
            type = tag.getType();
            args = (byte[]) tag.getArgs();

            if (args != null)
            {
               buffer = ByteBuffer.wrap(args);
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else if (type != Monitor.MONITOR_PROCESS_FILTER_TAG_NOT)
            {
               // Ignore and skip invalid tags.
               continue;
            }

            switch (type)
            {
               case Monitor.MONITOR_PROCESS_FILTER_TAG_NOT:
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_TYPE:
                  value = buffer.getInt();
                  if (not ^ (process.type != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_STATE:
                  value = buffer.getInt();
                  if (not ^ (process.state != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_PRIORITY:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.priority < min) || (process.priority >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_UID:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.uid < min) || (process.uid >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_CREATOR_ID:
                  value = buffer.getInt();
                  if (not ^ (process.creator != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SUPERVISOR:
                  boolean supervisor = (buffer.getInt() != 0);
                  if (not ^ (process.supervisor != supervisor))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_IN_QUEUE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.signalsInQueue < min) ||
                        (process.signalsInQueue >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_ATTACHED:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.signalsAttached < min) ||
                        (process.signalsAttached >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_ENTRYPOINT:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.entrypoint < min) ||
                        (process.entrypoint >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SEMAPHORE_VALUE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.semaphoreValue < min) ||
                        (process.semaphoreValue >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_STACK_SIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.stackSize < min) || (process.stackSize >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SUPERVISOR_STACK_SIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.supervisorStackSize < min) ||
                        (process.supervisorStackSize >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_LINE_NUMBER:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.lineNumber < min) ||
                        (process.lineNumber >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_OWNED:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.signalsOwned < min) ||
                        (process.signalsOwned >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_TIME_SLICE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.timeSlice < min) || (process.timeSlice >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_VECTOR:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.vector < min) || (process.vector >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_ERROR_HANDLER:
                  value = buffer.getInt();
                  if (not ^ (process.errorHandler != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SIGSELECT_COUNT:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((process.sigselect.length < min) ||
                        (process.sigselect.length >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_NAME:
                  str = bytesToString(buffer);
                  if (not ^ !match(str, process.processName))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_FILE_NAME:
                  str = bytesToString(buffer);
                  if (not ^ !match(str, process.fileName))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_SYSTEM:
                  value = buffer.getInt();
                  if (not ^ ((process.properties & 1) == 0))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_PROCESS_FILTER_TAG_EU_ID:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ (((process.euId & 0xFFFF) < min)
                        || ((process.euId & 0xFFFF) >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               default:
                  // Ignore and skip invalid tags.
                  break;
            }

            not = (type == Monitor.MONITOR_PROCESS_FILTER_TAG_NOT);
         }
      }

      return processes;
   }

   private List filterStacks(List stacks, MonitorTag[] tags)
   {
      if (stacks == null)
      {
         throw new NullPointerException();
      }

      if ((tags == null) || (tags.length == 0))
      {
         return stacks;
      }

      for (Iterator i = stacks.iterator(); i.hasNext();)
      {
         MonitorGetStackUsageReply stack = (MonitorGetStackUsageReply) i.next();
         boolean not = false;

         TAGSLOOP: for (int j = 0; j < tags.length; j++)
         {
            MonitorTag tag;
            short type;
            byte[] args;
            ByteBuffer buffer = null;
            int value;
            int min;
            int max;
            String str;

            tag = tags[j];
            type = tag.getType();
            args = (byte[]) tag.getArgs();

            if (args != null)
            {
               buffer = ByteBuffer.wrap(args);
               buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            }
            else if (type != Monitor.MONITOR_STACK_FILTER_TAG_NOT)
            {
               // Ignore and skip invalid tags.
               continue;
            }

            switch (type)
            {
               case Monitor.MONITOR_STACK_FILTER_TAG_NOT:
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_OWNER_ID:
                  value = buffer.getInt();
                  if (not ^ (stack.pid != value))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_OWNER_NAME:
                  str = bytesToString(buffer);
                  if (not ^ !match(str, getProcessName(stack.pid)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.main.size < min) || (stack.main.size >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_BUFSIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.main.bufsize < min) || (stack.main.bufsize >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_ADDRESS:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.main.address < min) || (stack.main.address >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_USED:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.main.used < min) || (stack.main.used >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_UNUSED:
                  value = stack.main.size - stack.main.used;
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((value < min) || (value >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_SIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.supervisor.size < min) ||
                        (stack.supervisor.size >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_BUFSIZE:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.supervisor.bufsize < min) ||
                        (stack.supervisor.bufsize >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_ADDRESS:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.supervisor.address < min) ||
                        (stack.supervisor.address >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_USED:
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((stack.supervisor.used < min) ||
                        (stack.supervisor.used >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               case Monitor.MONITOR_STACK_FILTER_TAG_SUPERVISOR_UNUSED:
                  value = stack.supervisor.size - stack.supervisor.used;
                  min = buffer.getInt();
                  max = buffer.getInt();
                  if (not ^ ((value < min) || (value >= max)))
                  {
                     i.remove();
                     break TAGSLOOP;
                  }
                  break;
               default:
                  // Ignore and skip invalid tags.
                  break;
            }

            not = (type == Monitor.MONITOR_STACK_FILTER_TAG_NOT);
         }
      }

      return stacks;
   }

   private List filterSignals(List signals, MonitorTag[] tags)
   {
      List result;

      if (signals == null)
      {
         throw new NullPointerException();
      }

      if ((tags == null) || (tags.length == 0))
      {
         return signals;
      }

      result = new ArrayList(signals.size());
      for (Iterator i = signals.iterator(); i.hasNext();)
      {
         MonitorGetPoolSignalsReply poolSignals =
            (MonitorGetPoolSignalsReply) i.next();
         List matchingSignals = new ArrayList(poolSignals.signals.length);

         for (int j = 0; j < poolSignals.signals.length; j++)
         {
            matchingSignals.add(poolSignals.signals[j]);
         }

         for (Iterator j = matchingSignals.iterator(); j.hasNext();)
         {
            MonitorSignalInfo signal = (MonitorSignalInfo) j.next();
            boolean not = false;

            TAGSLOOP: for (int k = 0; k < tags.length; k++)
            {
               MonitorTag tag;
               short type;
               byte[] args;
               ByteBuffer buffer = null;
               int value;
               int min;
               int max;
               String str;

               tag = tags[k];
               type = tag.getType();
               args = (byte[]) tag.getArgs();

               if (args != null)
               {
                  buffer = ByteBuffer.wrap(args);
                  buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
               }
               else if (type != Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT)
               {
                  // Ignore and skip invalid tags.
                  continue;
               }

               switch (type)
               {
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT:
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_NUMBER:
                     min = buffer.getInt();
                     max = buffer.getInt();
                     if (not ^ ((signal.number < min) || (signal.number >= max)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_ID:
                     value = buffer.getInt();
                     if (not ^ (signal.owner != value))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_OWNER_NAME:
                     str = bytesToString(buffer);
                     if (not ^ !match(str, getProcessName(signal.owner)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_ID:
                     value = buffer.getInt();
                     if (not ^ (signal.sender != value))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_SENDER_NAME:
                     str = bytesToString(buffer);
                     if (not ^ !match(str, getProcessName(signal.sender)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_ID:
                     value = buffer.getInt();
                     if (not ^ (signal.addressee != value))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESSEE_NAME:
                     str = bytesToString(buffer);
                     if (not ^ !match(str, getProcessName(signal.addressee)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_SIZE:
                     min = buffer.getInt();
                     max = buffer.getInt();
                     if (not ^ ((signal.size < min) || (signal.size >= max)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_BUFSIZE:
                     min = buffer.getInt();
                     max = buffer.getInt();
                     if (not ^ ((signal.bufsize < min) || (signal.bufsize >= max)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_ADDRESS:
                     min = buffer.getInt();
                     max = buffer.getInt();
                     if (not ^ ((signal.address < min) || (signal.address >= max)))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  case Monitor.MONITOR_SIGNAL_FILTER_TAG_STATUS:
                     value = buffer.getInt();
                     if (not ^ (signal.status != value))
                     {
                        j.remove();
                        break TAGSLOOP;
                     }
                     break;
                  default:
                     // Ignore and skip invalid tags.
                     break;
               }

               not = (type == Monitor.MONITOR_SIGNAL_FILTER_TAG_NOT);
            }
         }

         if (!matchingSignals.isEmpty())
         {
            MonitorGetPoolSignalsReply reply = new MonitorGetPoolSignalsReply();
            reply.pid = poolSignals.pid;
            reply.signals = (MonitorSignalInfo[])
               matchingSignals.toArray(new MonitorSignalInfo[0]);
            result.add(reply);
         }
      }

      return result;
   }


   private boolean isConnectedAndSupported(int pid, int sigNo)
   {
      boolean connected;
      boolean supported = false;

      connected = (clients.get(new Integer(pid)) != null);
      if (connected)
      {
         if (signalRegistry.getSignalClass(sigNo) != null)
         {
            supported = true;
         }
      }

      return (connected && supported);
   }

   private void sendSignals(int to, List signals)
   {
      for (Iterator i = signals.iterator(); i.hasNext();)
      {
         Signal signal = (Signal) i.next();
         sendSignal(to, signal);
      }
   }

   private void sendSignal(int to, Signal signal)
   {
      try
      {
         // Byte order convert the signal if little endian.
         signal = convertSignal(signal);

         // Send the signal.
         send(to, signal);
      }
      catch (IOException e)
      {
         log("I/O exception in freeze mode monitor when byte order converting signal "
             + signal.getSigNo(), e);
      }
   }

   private Signal convertSignal(Signal signal) throws IOException
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }

      // Byte order convert the signal if little endian.
      if (bigEndian)
      {
         return signal;
      }
      else
      {
         byte[] sigData;
         RawSignal sig;

         sigData = signal.javaToOse(bigEndian);
         sig = new RawSignal(signal.getSigNo());
         sig.initSignal(sigData.length + 4, sigData);
         sig.initTransfer(signal.getSender(), signal.getAddressee());
         return sig;
      }
   }

   private String getProcessName(int pid)
   {
      int status;
      Fmi.EfmiString processName = new Fmi.EfmiString();
      status = fmi.ose_efmi_get_process_name(pid, processName);
      if (status != Fmi.EFMI_OK)
      {
         // REVIEW: Is this name OK to use if name can't be retrieved?
         return "<Unknown>";
      }

      return processName.string;
   }

   private boolean supportsBlocks()
   {
      return 0 != fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_BLOCK);
   }

   private boolean supportsSegments()
   {
      return 0 != fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_SEGMENT);
   }

   private int toMonitorProcessState(int pid, int processState) throws EfmiException
   {
      int status;      
      Fmi.EfmiCursor cursor = new Fmi.EfmiCursor();
      Fmi.EfmiCurrent current = new Fmi.EfmiCurrent();

      int state = processState;

      status = fmi.ose_efmi_get_first_current(cursor, current);
      
      while(status == Fmi.EFMI_OK)
      {
         if (current.process == pid)
         {
            // If the process is the current process, it is running
            return Monitor.MONITOR_PROCESS_STATE_RUNNING;
         }
         
         current = new Fmi.EfmiCurrent();
         status = fmi.ose_efmi_get_next_current(cursor, current);
      }
      
      if (status != Fmi.EFMI_END_OF_LIST)
      {
         throw new EfmiException(status);
      }

      if (!(((state & Monitor.MONITOR_PROCESS_STATE_STOPPED) > 0)
          || ((state & Monitor.MONITOR_PROCESS_STATE_RECEIVE) > 0)
          || ((state & Monitor.MONITOR_PROCESS_STATE_DELAY) > 0)
          || ((state & Monitor.MONITOR_PROCESS_STATE_SEMAPHORE) > 0)
          || ((state & Monitor.MONITOR_PROCESS_STATE_FAST_SEMAPHORE) > 0)
          || ((state & Monitor.MONITOR_PROCESS_STATE_REMOTE) > 0)))
      {
         // If process is not in a suspended state, it must be ready
         state |= Monitor.MONITOR_PROCESS_STATE_READY;
      }

      return state;
   }

   private int efmiToMonitorOSType(int efmiOSType)
   {
      switch (efmiOSType)
      {
         case Fmi.EFMI_OS_OSE_5:
            return Monitor.MONITOR_OS_OSE_5;
         case Fmi.EFMI_OS_OSE_4:
            return Monitor.MONITOR_OS_OSE_4;
         case Fmi.EFMI_OS_OSE_EPSILON:
            return Monitor.MONITOR_OS_OSE_EPSILON;
         case Fmi.EFMI_OS_OSE_CK:
            return Monitor.MONITOR_OS_OSE_CK;
         case Fmi.EFMI_OS_UNKNOWN:
         default:
            return Monitor.MONITOR_OS_UNKNOWN;
      }
   }

   private int efmiToMonitorCPUType(int efmiCPUType)
   {
      switch (efmiCPUType)
      {
         case Fmi.EFMI_CPU_PPC:
            return Monitor.MONITOR_CPU_PPC;
         case Fmi.EFMI_CPU_ARM:
            return Monitor.MONITOR_CPU_ARM;
         case Fmi.EFMI_CPU_MIPS:
            return Monitor.MONITOR_CPU_MIPS;
         case Fmi.EFMI_CPU_X86:
            return Monitor.MONITOR_CPU_X86;
         case Fmi.EFMI_CPU_SPARC:
            return Monitor.MONITOR_CPU_SPARC;
         case Fmi.EFMI_CPU_C64X:
            return Monitor.MONITOR_CPU_C64X;
         case Fmi.EFMI_CPU_C64XP:
            return Monitor.MONITOR_CPU_C64XP;
         case Fmi.EFMI_CPU_SP26XX:
            return Monitor.MONITOR_CPU_SP26XX;
         case Fmi.EFMI_CPU_SP27XX:
            return Monitor.MONITOR_CPU_SP27XX;
         case Fmi.EFMI_CPU_TL3:
            return Monitor.MONITOR_CPU_TL3;
         case Fmi.EFMI_CPU_CEVAX:
            return Monitor.MONITOR_CPU_CEVAX;
         case Fmi.EFMI_CPU_M8144:
            return Monitor.MONITOR_CPU_M8144;
         case Fmi.EFMI_CPU_M8156:
            return Monitor.MONITOR_CPU_M8156;
         case Fmi.EFMI_CPU_C66X:
            return Monitor.MONITOR_CPU_C66X;
         case Fmi.EFMI_CPU_C674X:
            return Monitor.MONITOR_CPU_C674X;
         case Fmi.EFMI_CPU_UNKNOWN:
         default:
            return Monitor.MONITOR_CPU_UNKNOWN;
      }
   }

   static int efmiToMonitorStatus(int efmiStatus)
   {
      switch (efmiStatus)
      {
         case Fmi.EFMI_OK:
         case Fmi.EFMI_END_OF_LIST:
            return MonitorStatus.MONITOR_STATUS_OK;
         case Fmi.EFMI_UNAVAILABLE:
            return MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         case Fmi.EFMI_RESULT_TRUNCATED:
         case Fmi.EFMI_POOL_CORRUPTED:
         case Fmi.EFMI_KERNEL_CORRUPTED:
         case Fmi.EFMI_KERNEL_UNINITIATED:
            return MonitorStatus.MONITOR_STATUS_KERNEL_ERROR;
         case Fmi.EFMI_CALL_INVALID:
            return MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         default:
            return MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
      }
   }

   private String bytesToString(ByteBuffer buffer)
   {
      String str = null;

      try
      {
         int nullIndex;

         str = decoder.decode(buffer).toString();
         // Strip terminating null character and any characters after it.
         nullIndex = str.indexOf(0);
         if (nullIndex >= 0)
         {
            str = str.substring(0, nullIndex);
         }
      }
      catch (CharacterCodingException ignore) {}

      return str;
   }

   private static boolean match(String pattern, String string)
   {
      char[] p;
      char[] s;
      int p0 = 0;
      int pi = 0;
      int si = 0;
      int pmax;
      int smax;

      if ((pattern == null) || (string == null))
      {
         return false;
      }

      p = (pattern + "\000").toCharArray();
      s = (string + "\000").toCharArray();
      pmax = p.length - 1;
      smax = s.length - 1;

      while ((pi < pmax) || (si < smax))
      {
         if (p[pi] == '*')
         {
            // Wildcard: set p0.
            p0 = ++pi;

            if (pi >= pmax)
            {
               // Wildcard at end: return match.
               return true;
            }
         }
         else if ((p[pi] == s[si]) || (p[pi] == '?' && (si < smax)))
         {
            // Match: check next.
            pi++;
            si++;
         }
         else if ((si >= smax) || (p0 == 0))
         {
            // No match and s over or no match and no wildcard: return fail.
            return false;
         }
         else if (pi != p0)
         {
            // No match but after wildcard: rewind to p0.
            si -= pi - p0 - 1;
            pi = p0;
         }
         else
         {
            // No match but in wildcard: check next.
           si++;
         }
      }

      return true;
   }

   private void addGate()
   {
      if (getGatewayServer().isOpen())
      {
         InetSocketAddress gwsAddress = getGatewayServer().getServerAddress();
         InetAddress gateAddress = gwsAddress.getAddress();
         int gatePort = gwsAddress.getPort();
         SystemModel sm = SystemModel.getInstance();
         sm.addGate(gateAddress, gatePort);
         Gate gate = sm.getGate(gateAddress, gatePort);
         if ((gate != null) && !gate.isConnected())
         {
            gate.connect(new NullProgressMonitor());
         }
      }
   }

   private class TargetProxyListener implements ITargetProxyListener
   {
      public void targetResumed(ITargetProxy target)
      {
         if (fmi.isInitialized())
         {
            fmi.ose_host_fmi_handle_event(Fmi.HOST_FMI_EVENT_TARGET_RESUMED);
         }
      }
      
      public void targetSuspended(ITargetProxy target)
      {
         if (fmi.isInitialized())
         {
            fmi.ose_host_fmi_handle_event(Fmi.HOST_FMI_EVENT_TARGET_SUSPENDED);
         }
         else
         {
            initializeFmi();
         }
      }
   }
   
   private static class EfmiException extends Exception
   {
      private static final long serialVersionUID = 1L;

      private int status = -1;

      public EfmiException(int efmiStatus)
      {
         super(Fmi.efmiStatusToString(efmiStatus));
         this.status = efmiStatus;
      }

      public int getStatus()
      {
         return status;
      }
   }

   private static class PfmiException extends Exception
   {
      private static final long serialVersionUID = 1L;

      private int status = -1;

      public PfmiException(int pfmiStatus)
      {
         super(Fmi.pfmiStatusToString(pfmiStatus));
         this.status = pfmiStatus;
      }

      public int getStatus()
      {
         return status;
      }
   }
   
   private static class MonitorClient
   {
      private final int pid;
      private final String name;

      MonitorClient(int pid, String name)
      {
         this.pid = pid;
         this.name = name;
      }

      public int getPid()
      {
         return pid;
      }

      public String getName()
      {
         return name;
      }
   }
}
