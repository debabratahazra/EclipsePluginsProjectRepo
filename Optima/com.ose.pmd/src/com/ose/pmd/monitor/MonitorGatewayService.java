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

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;
import com.ose.pmd.dump.Chunk;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.monitor.Dump.MonitorEventTrace;
import com.ose.pmd.monitor.Dump.MonitorEventTrace.ProcessInfoCache;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUPriReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUReport;
import com.ose.system.service.monitor.MonitorSignalInfo;
import com.ose.system.service.monitor.MonitorStatus;
import com.ose.system.service.monitor.MonitorTag;
import com.ose.system.service.monitor.MonitorUserReport;
import com.ose.system.service.monitor.signal.MonitorAttachReply;
import com.ose.system.service.monitor.signal.MonitorAttachRequest;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlRequest;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorDetachReply;
import com.ose.system.service.monitor.signal.MonitorDetachRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorEventInfoSignal;
import com.ose.system.service.monitor.signal.MonitorGetAllocTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetBindTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUBlockReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsReply;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsRequest;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetMemory64Reply;
import com.ose.system.service.monitor.signal.MonitorGetMemory64Request;
import com.ose.system.service.monitor.signal.MonitorGetMemoryEndmark;
import com.ose.system.service.monitor.signal.MonitorGetMemoryReply;
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
import com.ose.system.service.monitor.signal.MonitorGetSysParamRequest;
import com.ose.system.service.monitor.signal.MonitorGetTimeoutTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceEndmark;
import com.ose.system.service.monitor.signal.MonitorGetTraceRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsReply;
import com.ose.system.service.monitor.signal.MonitorGetUserReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserTraceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorNameRequest;

public class MonitorGatewayService extends AbstractGatewayService
{
   private static final String SERVICE_NAME = "ose_monitor";
   private static final String PLUGIN_ID = "com.ose.pmd";
   private static final int MEMORY_CHUNK_SIZE = 4096;
   private static final int REGISTER_INVALID = 0x80000000;
   private static final int NUM_REPORTS_PER_REPLY = 100;
   private static final int PROCESS_CACHE_SIZE = 32;

   private static boolean licenseCheckedOut;
   private final CharsetDecoder decoder;
   private final Dump dump;
   private final boolean bigEndian;
   private final SignalRegistry signalRegistry;
   private final short[] features;
   private final boolean hasCrashedSid;
   private final int crashedSid;
   private final Map clients;

   public MonitorGatewayService(Dump dump) throws LicenseException, IOException
   {
      List featureList;
      Signal process;

      if (dump == null)
      {
         throw new IllegalArgumentException();
      }

      // Check out FLEXlm license.
      // XXX: Due to a bug in the FLEXlm Java client library on Solaris,
      // we cannot check out the FLEXlm license when creating a
      // MonitorGatewayService instance and check it in when closing the
      // MonitorGatewayService instance without hanging the whole JVM.
      // Instead, we have to check it out only once when the first
      // MonitorGatewayService instance is created.
      if (!licenseCheckedOut)
      {
         LicenseManager lm = LicenseManager.getInstance();
         long cookie = (long) (Math.random() * Long.MAX_VALUE);
         long r = lm.registerPlugin(PLUGIN_ID, "1.000", cookie);
         long t = System.currentTimeMillis() / 10000;
         if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1))))
         {
            throw new LicenseException("Incorrect response value");
         }
         licenseCheckedOut = true;
      }

      decoder = Charset.forName("ISO-8859-1").newDecoder();

      // Store the dump and its byte order.
      this.dump = dump;
      bigEndian = dump.isBigEndian();

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
      signalRegistry.add(MonitorGetPoolInfo64Request.class);
      signalRegistry.add(MonitorGetPoolSignalsRequest.class);
      signalRegistry.add(MonitorGetMemoryRequest.class);
      signalRegistry.add(MonitorGetMemory64Request.class);
      signalRegistry.add(MonitorGetRegistersRequest.class);
      signalRegistry.add(MonitorGetRegisterInfoRequest.class);
      signalRegistry.add(MonitorGetCPUReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUPriReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUProcessReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUBlockReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetUserReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUReportsRequest.class);
      signalRegistry.add(MonitorGetCPUPriReportsRequest.class);
      signalRegistry.add(MonitorGetCPUProcessReportsRequest.class);
      signalRegistry.add(MonitorGetCPUBlockReportsRequest.class);
      signalRegistry.add(MonitorGetUserReportsRequest.class);
      signalRegistry.add(MonitorAttachRequest.class);
      signalRegistry.add(MonitorDetachRequest.class);
      signalRegistry.add(MonitorGetTraceRequest.class);

      // Create the supported features array.
      featureList = new ArrayList();
      featureList.add(Monitor.MONITOR_FEATURE_COMPACT_REGISTER_SIGNALS);
      featureList.add(Monitor.MONITOR_FEATURE_SYSTEM_PROCESSES);
      featureList.add(Monitor.MONITOR_FEATURE_PROCESS_FILTER);
      featureList.add(Monitor.MONITOR_FEATURE_BLOCK_FILTER);
      featureList.add(Monitor.MONITOR_FEATURE_SEGMENT_FILTER);
      featureList.add(Monitor.MONITOR_FEATURE_SIGNAL_FILTER);
      featureList.add(Monitor.MONITOR_FEATURE_STACK_FILTER);
      featureList.add(Monitor.MONITOR_FEATURE_PROF_ALL_EXECUTION_UNITS);
      if (hasLongSegmentInfoSupport())
      {
         featureList.add(Monitor.MONITOR_FEATURE_SEGMENT_INFO_LONG);
      }
      features = new short[featureList.size()];
      for (int i = 0; i < features.length; i++)
      {
         features[i] = ((Short) featureList.get(i)).shortValue();
      }

      // Store the segment id of the crashed process.
      process = getCrashedProcess();
      if (process != null)
      {
         hasCrashedSid = true;
         crashedSid = getProcessSid(process);
      }
      else
      {
         hasCrashedSid = false;
         crashedSid = 0;
      }

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
         log("I/O exception in PMD monitor when receiving signal " + sigNo, e);
      }
   }

   protected void close()
   {
      clients.clear();
      dump.close();
      // XXX: Due to a bug in the FLEXlm Java client library on Solaris, we
      // cannot check in the FLEXlm license without hanging the whole JVM when
      // checking out the FLEXlm license again.
      //LicenseManager.getInstance().unregisterPlugin(PLUGIN_ID);
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();
      int client = signal.getSender();

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
         case MonitorGetPoolInfo64Request.SIG_NO:
             if (isConnectedAndSupported(client, sigNo))
             {
                handleGetPoolInfo64Request(signal);
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
         case MonitorGetMemory64Request.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetMemory64Request(signal);
            }
            break;
         case MonitorGetRegistersRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetRegistersRequest(signal);
            }
            break;
         case MonitorGetCPUReportsEnabledRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUReportsEnabledRequest(signal);
            }
            break;
         case MonitorGetCPUPriReportsEnabledRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUPriReportsEnabledRequest(signal);
            }
            break;
         case MonitorGetCPUProcessReportsEnabledRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUProcessReportsEnabledRequest(signal);
            }
            break;
         case MonitorGetCPUBlockReportsEnabledRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUBlockReportsEnabledRequest(signal);
            }
            break;
         case MonitorGetUserReportsEnabledRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetUserReportsEnabledRequest(signal);
            }
            break;
         case MonitorGetCPUReportsRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUReportsRequest(signal);
            }
            break;
         case MonitorGetCPUPriReportsRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUPriReportsRequest(signal);
            }
            break;
         case MonitorGetCPUProcessReportsRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUProcessReportsRequest(signal);
            }
            break;
         case MonitorGetCPUBlockReportsRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetCPUBlockReportsRequest(signal);
            }
            break;
         case MonitorGetUserReportsRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetUserReportsRequest(signal);
            }
            break;
         case MonitorAttachRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleAttachRequest(signal);
            }
            break;
         case MonitorDetachRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleDetachRequest(signal);
            }
            break;
         case MonitorGetTraceRequest.SIG_NO:
            if (isConnectedAndSupported(client, sigNo))
            {
               handleGetTraceRequest(signal);
            }
            break;
         default:
            log("Unknown signal received by PMD monitor: " + sigNo);
            break;
      }
   }

   private void handleInterfaceRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorInterfaceReply());
   }

   private void handleNameRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorNameReply());
   }

   private void handleConnectRequest(Signal sig)
   {
      MonitorConnectRequest request;
      MonitorClient client;
      MonitorConnectReply reply;
      HashSet replyFeatures = new HashSet();
      short[] replyFeaturesArray;
      
      request = (MonitorConnectRequest) sig;
      client = new MonitorClient(sig.getSender(), request.name);
      clients.put(new Integer(sig.getSender()), client);
      for (int i = 0; i < features.length; i++)
      {
         replyFeatures.add(features[i]);
      }
      
      reply = dump.getMonitorConnectReply();
      for (int i = 0; i < reply.featuresCount; i++)
      {
         replyFeatures.add(reply.features[i]);
      }
      
      replyFeaturesArray = new short[(replyFeatures.size())];
      Iterator iterator = replyFeatures.iterator();
      for (int i = 0; i < replyFeatures.size(); i ++)
      {
         replyFeaturesArray[i] = (((Short)iterator.next()).shortValue());
      }
      
      reply.featuresCount = replyFeaturesArray.length;
      reply.features = replyFeaturesArray;
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
      MonitorGetSysParamRequest request;
      MonitorGetSysParamEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetSysParamRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetSysParamEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingSysParams(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetSysParamEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetSegmentInfoRequest(Signal sig)
   {
      MonitorGetSegmentInfoRequest request;
      MonitorGetSegmentInfoEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetSegmentInfoRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetSegmentInfoEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingSegments(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetSegmentInfoEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetBlockInfoRequest(Signal sig)
   {
      MonitorGetBlockInfoRequest request;
      MonitorGetBlockInfoEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetBlockInfoRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetBlockInfoEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingBlocks(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetBlockInfoEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetProcessInfoRequest(Signal sig)
   {
      MonitorGetProcessInfoRequest request;
      MonitorGetProcessInfoEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetProcessInfoRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetProcessInfoEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingProcesses(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetProcessInfoEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetEnvVarsRequest(Signal sig)
   {
      MonitorGetEnvVarsRequest request;
      MonitorGetEnvVarsEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetEnvVarsRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetEnvVarsEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingEnvVars(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetEnvVarsEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetStackUsageRequest(Signal sig)
   {
      MonitorGetStackUsageRequest request;
      MonitorGetStackUsageEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetStackUsageRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetStackUsageEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingStacks(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetStackUsageEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetSignalQueueRequest(Signal sig)
   {
      MonitorGetSignalQueueRequest request;
      MonitorGetSignalQueueEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetSignalQueueRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetSignalQueueEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingSignalQueues(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetSignalQueueEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetPoolInfoRequest(Signal sig)
   {
      MonitorGetPoolInfoRequest request = null;
      MonitorGetPoolInfoEndmark endmark;
      List replies = null;
      int status = 0;
      
      request = (MonitorGetPoolInfoRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);    	  
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetPoolInfoEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }
      
      replies = findMatchingPools(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetPoolInfoEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetPoolInfo64Request(Signal sig)
   {
      MonitorGetPoolInfo64Request request = null;
      MonitorGetPoolInfoEndmark endmark;
      List replies = null;
      int status = 0;
      
      request = (MonitorGetPoolInfo64Request) sig;
      status = dump.validateMonitorGetInfoRequest(request);       
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetPoolInfoEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }
      
      replies = findMatchingPools64(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetPoolInfoEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetPoolSignalsRequest(Signal sig)
   {
      MonitorGetPoolSignalsRequest request;
      MonitorGetPoolSignalsEndmark endmark;
      List replies;
      int status;

      request = (MonitorGetPoolSignalsRequest) sig;
      status = dump.validateMonitorGetInfoRequest(request);
      if (status != MonitorStatus.MONITOR_STATUS_OK)
      {
         endmark = new MonitorGetPoolSignalsEndmark();
         endmark.status = status;
         sendSignal(sig.getSender(), endmark);
         return;
      }

      replies = findMatchingSignals(request);
      for (Iterator i = replies.iterator(); i.hasNext();)
      {
         Signal reply = (Signal) i.next();
         sendSignal(sig.getSender(), reply);
      }

      endmark = new MonitorGetPoolSignalsEndmark();
      endmark.status = dump.findMonitorGetInfoEndmarkStatus(request);
      sendSignal(sig.getSender(), endmark);
   }

   private void handleGetMemoryRequest(Signal sig)
   {
      MonitorGetMemoryRequest request;
      boolean inCrashedSegment = false;
      Signal obj;

      request = (MonitorGetMemoryRequest) sig;

      if (!hasCrashedSid)
      {
         inCrashedSegment = false;
      }
      else if ((obj = dump.getSegment(request.pid)) != null)
      {
         if (obj instanceof MonitorGetSegmentInfoReply)
         {
            MonitorGetSegmentInfoReply segment =
               (MonitorGetSegmentInfoReply) obj;
            inCrashedSegment = (segment.sid == crashedSid);
         }
         else if (obj instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply segment =
               (MonitorGetSegmentInfoLongReply) obj;
            inCrashedSegment = (segment.sid == crashedSid);
         }
      }
      else if ((obj = dump.getBlock(request.pid)) != null)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) obj;
         inCrashedSegment = (block.sid == crashedSid);
      }
      else if ((obj = dump.getProcess(request.pid)) != null)
      {
         inCrashedSegment = (getProcessSid(obj) == crashedSid);
      }

      if (inCrashedSegment)
      {
         serveGetMemoryRequest(request);
      }
      else
      {
         MonitorGetMemoryEndmark endmark = new MonitorGetMemoryEndmark();
         endmark.status = MonitorStatus.MONITOR_STATUS_ID_INVALID;
         sendSignal(sig.getSender(), endmark);
      }
   }
   
   private void handleGetMemory64Request(Signal sig)
   {
      MonitorGetMemory64Request request;
      boolean inCrashedSegment = false;
      Signal obj;

      request = (MonitorGetMemory64Request) sig;

      if (!hasCrashedSid)
      {
         inCrashedSegment = false;
      }
      else if ((obj = dump.getSegment(request.pid)) != null)
      {
         if (obj instanceof MonitorGetSegmentInfoReply)
         {
            MonitorGetSegmentInfoReply segment =
               (MonitorGetSegmentInfoReply) obj;
            inCrashedSegment = (segment.sid == crashedSid);
         }
         else if (obj instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply segment =
               (MonitorGetSegmentInfoLongReply) obj;
            inCrashedSegment = (segment.sid == crashedSid);
         }
      }
      else if ((obj = dump.getBlock(request.pid)) != null)
      {
         MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) obj;
         inCrashedSegment = (block.sid == crashedSid);
      }
      else if ((obj = dump.getProcess(request.pid)) != null)
      {
         inCrashedSegment = (getProcessSid(obj) == crashedSid);
      }

      if (inCrashedSegment)
      {
         serveGetMemory64Request(request);
      }
      else
      {
         MonitorGetMemoryEndmark endmark = new MonitorGetMemoryEndmark();
         endmark.status = MonitorStatus.MONITOR_STATUS_ID_INVALID;
         sendSignal(sig.getSender(), endmark);
      }
   }

   private void handleGetRegistersRequest(Signal sig)
   {
      MonitorGetRegistersRequest request = (MonitorGetRegistersRequest) sig;

      if (dump.processExists(request.pid))
      {
         serveGetRegistersRequest(request);
      }
      else
      {
         MonitorGetRegistersReply reply = new MonitorGetRegistersReply();
         reply.pid = request.pid;
         reply.status = MonitorStatus.MONITOR_STATUS_ID_INVALID;
         reply.registers = new int[0];
         sendSignal(sig.getSender(), reply);
      }
   }

   private void handleGetCPUReportsEnabledRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorCPUReportsEnabledReply());
   }

   private void handleGetCPUPriReportsEnabledRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorCPUPriReportsEnabledReply());
   }

   private void handleGetCPUProcessReportsEnabledRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorCPUProcessReportsEnabledReply());
   }

   private void handleGetCPUBlockReportsEnabledRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getMonitorCPUBlockReportsEnabledReply());
   }

   private void handleGetUserReportsEnabledRequest(Signal sig)
   {
      MonitorGetUserReportsEnabledRequest request;
      List replies;
      boolean foundReply = false;
      MonitorGetUserReportsEnabledReply reply = null;

      request = (MonitorGetUserReportsEnabledRequest) sig;
      replies = dump.getMonitorUserReportsEnabledReplies();
      for (int i = replies.size() - 1; i >= 0; i--)
      {
         reply = (MonitorGetUserReportsEnabledReply) replies.get(i);
         if (reply.reportNo == request.reportNo)
         {
            foundReply = true;
            break;
         }
      }

      if (!foundReply)
      {
         reply = new MonitorGetUserReportsEnabledReply();
         reply.reportNo = request.reportNo;
         reply.enabled = false;
         reply.interval = 0;
         reply.maxReports = 0;
         reply.maxValuesReport = 0;
      }

      sendSignal(sig.getSender(), reply);
   }

   private void handleGetCPUReportsRequest(Signal sig)
   {
      MonitorGetCPUReportsRequest request;
      MonitorClient client;
      List signalBlocks;
      List reports;
      boolean fromBeginning;
      int replyStart;
      int reportStart;
      boolean foundRequestedReport;
      int status = MonitorStatus.MONITOR_STATUS_OK;
      MonitorGetCPUReportsReply reply;

      request = (MonitorGetCPUReportsRequest) sig;
      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client == null)
      {
         reply = new MonitorGetCPUReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      if (!isValidExecutionUnit(request.euId))
      {
         reply = new MonitorGetCPUReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      signalBlocks = dump.getMonitorCPUReportSignalBlocks();
      reports = new ArrayList(NUM_REPORTS_PER_REPLY);
      fromBeginning = ((request.tick == 0) && (request.ntick == 0));
      if (fromBeginning)
      {
         replyStart = 0;
         reportStart = 0;
         client.setCpuReportsReplyIndex(request.euId, 0);
         client.setCpuReportIndex(request.euId, 0);
         foundRequestedReport = true;
      }
      else
      {
         replyStart = client.getCpuReportsReplyIndex(request.euId);
         reportStart = client.getCpuReportIndex(request.euId);
         foundRequestedReport = false;
      }

      try
      {
         outer: for (int i = replyStart; i < signalBlocks.size(); i++)
         {
            SignalBlock signalBlock = (SignalBlock) signalBlocks.get(i);
            MonitorGetCPUReportsRequest storedRequest =
               dump.getMonitorGetCPUReportsRequest(signalBlock);
            if (request.euId == storedRequest.euId)
            {
               reply = dump.getMonitorGetCPUReportsReply(signalBlock);
               for (int j = reportStart; j < reply.reports.length; j++)
               {
                  MonitorCPUReport report = reply.reports[j];
                  if (!foundRequestedReport)
                  {
                     if ((report.tick == request.tick) &&
                         (report.ntick == request.ntick))
                     {
                        foundRequestedReport = true;
                        continue;
                     }
                  }
                  if (foundRequestedReport)
                  {
                     if (reports.size() < NUM_REPORTS_PER_REPLY)
                     {
                        reports.add(report);
                        client.setCpuReportsReplyIndex(request.euId, i);
                        client.setCpuReportIndex(request.euId, j);
                     }
                     else
                     {
                        break outer;
                     }
                  }
               }
               reportStart = 0;
            }
         }
      }
      catch (Exception e)
      {
         status = MonitorStatus.MONITOR_STATUS_CANCELLED;
         log("Error reading MonitorGetCPUReportsRequest signal block from " +
             "dump file in PMD monitor.", e);
      }

      reply = new MonitorGetCPUReportsReply();
      reply.status = status;
      reply.enabled = (reports.size() > 0);
      reply.euId = request.euId;
      reply.reports =
         (MonitorCPUReport[]) reports.toArray(new MonitorCPUReport[0]);
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetCPUPriReportsRequest(Signal sig)
   {
      MonitorGetCPUPriReportsRequest request;
      MonitorClient client;
      List signalBlocks;
      List reports;
      boolean fromBeginning;
      int replyStart;
      int reportStart;
      boolean foundRequestedReport;
      int status = MonitorStatus.MONITOR_STATUS_OK;
      MonitorGetCPUPriReportsReply reply;

      request = (MonitorGetCPUPriReportsRequest) sig;
      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client == null)
      {
         reply = new MonitorGetCPUPriReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUPriReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      if (!isValidExecutionUnit(request.euId))
      {
         reply = new MonitorGetCPUPriReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUPriReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      signalBlocks = dump.getMonitorCPUPriReportSignalBlocks();
      reports = new ArrayList(NUM_REPORTS_PER_REPLY);
      fromBeginning = ((request.tick == 0) && (request.ntick == 0));
      if (fromBeginning)
      {
         replyStart = 0;
         reportStart = 0;
         client.setCpuPriReportsReplyIndex(request.euId, 0);
         client.setCpuPriReportIndex(request.euId, 0);
         foundRequestedReport = true;
      }
      else
      {
         replyStart = client.getCpuPriReportsReplyIndex(request.euId);
         reportStart = client.getCpuPriReportIndex(request.euId);
         foundRequestedReport = false;
      }

      try
      {
         outer: for (int i = replyStart; i < signalBlocks.size(); i++)
         {
            SignalBlock signalBlock = (SignalBlock) signalBlocks.get(i);
            MonitorGetCPUPriReportsRequest storedRequest =
               dump.getMonitorGetCPUPriReportsRequest(signalBlock);
            if (request.euId == storedRequest.euId)
            {
               reply = dump.getMonitorGetCPUPriReportsReply(signalBlock);
               for (int j = reportStart; j < reply.reports.length; j++)
               {
                  MonitorCPUPriReport report = reply.reports[j];
                  if (!foundRequestedReport)
                  {
                     if ((report.tick == request.tick) &&
                         (report.ntick == request.ntick))
                     {
                        foundRequestedReport = true;
                        continue;
                     }
                  }
                  if (foundRequestedReport)
                  {
                     if (reports.size() < NUM_REPORTS_PER_REPLY)
                     {
                        reports.add(report);
                        client.setCpuPriReportsReplyIndex(request.euId, i);
                        client.setCpuPriReportIndex(request.euId, j);
                     }
                     else
                     {
                        break outer;
                     }
                  }
               }
               reportStart = 0;
            }
         }
      }
      catch (Exception e)
      {
         status = MonitorStatus.MONITOR_STATUS_CANCELLED;
         log("Error reading MonitorGetCPUPriReportsRequest signal block from " +
             "dump file in PMD monitor.", e);
      }

      reply = new MonitorGetCPUPriReportsReply();
      reply.status = status;
      reply.enabled = (reports.size() > 0);
      reply.euId = request.euId;
      reply.reports =
         (MonitorCPUPriReport[]) reports.toArray(new MonitorCPUPriReport[0]);
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetCPUProcessReportsRequest(Signal sig)
   {
      MonitorGetCPUProcessReportsRequest request;
      MonitorClient client;
      List signalBlocks;
      List reports;
      boolean fromBeginning;
      int replyStart;
      int reportStart;
      boolean foundRequestedReport;
      int status = MonitorStatus.MONITOR_STATUS_OK;
      MonitorGetCPUProcessReportsReply reply;

      request = (MonitorGetCPUProcessReportsRequest) sig;
      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client == null)
      {
         reply = new MonitorGetCPUProcessReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUProcessReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      if (!isValidExecutionUnit(request.euId))
      {
         reply = new MonitorGetCPUProcessReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUProcessReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      signalBlocks = dump.getMonitorCPUProcessReportSignalBlocks();
      reports = new ArrayList(NUM_REPORTS_PER_REPLY);
      fromBeginning = ((request.tick == 0) && (request.ntick == 0));
      if (fromBeginning)
      {
         replyStart = 0;
         reportStart = 0;
         client.setCpuProcessReportsReplyIndex(request.euId, 0);
         client.setCpuProcessReportIndex(request.euId, 0);
         foundRequestedReport = true;
      }
      else
      {
         replyStart = client.getCpuProcessReportsReplyIndex(request.euId);
         reportStart = client.getCpuProcessReportIndex(request.euId);
         foundRequestedReport = false;
      }

      try
      {
         outer: for (int i = replyStart; i < signalBlocks.size(); i++)
         {
            SignalBlock signalBlock = (SignalBlock) signalBlocks.get(i);
            MonitorGetCPUProcessReportsRequest storedRequest =
               dump.getMonitorGetCPUProcessReportsRequest(signalBlock);
            if (request.euId == storedRequest.euId)
            {
               reply = dump.getMonitorGetCPUProcessReportsReply(signalBlock);
               for (int j = reportStart; j < reply.reports.length; j++)
               {
                  MonitorCPUProcessReport report = reply.reports[j];
                  if (!foundRequestedReport)
                  {
                     if ((report.tick == request.tick) &&
                         (report.ntick == request.ntick))
                     {
                        foundRequestedReport = true;
                        continue;
                     }
                  }
                  if (foundRequestedReport)
                  {
                     if (reports.size() < NUM_REPORTS_PER_REPLY)
                     {
                        reports.add(report);
                        client.setCpuProcessReportsReplyIndex(request.euId, i);
                        client.setCpuProcessReportIndex(request.euId, j);
                     }
                     else
                     {
                        break outer;
                     }
                  }
               }
               reportStart = 0;
            }
         }
      }
      catch (Exception e)
      {
         status = MonitorStatus.MONITOR_STATUS_CANCELLED;
         log("Error reading MonitorGetCPUProcessReportsRequest signal block " +
             "from dump file in PMD monitor.", e);
      }

      reply = new MonitorGetCPUProcessReportsReply();
      reply.status = status;
      reply.enabled = (reports.size() > 0);
      reply.euId = request.euId;
      reply.reports = (MonitorCPUProcessReport[])
         reports.toArray(new MonitorCPUProcessReport[0]);
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetCPUBlockReportsRequest(Signal sig)
   {
      MonitorGetCPUBlockReportsRequest request;
      MonitorClient client;
      List signalBlocks;
      List reports;
      boolean fromBeginning;
      int replyStart;
      int reportStart;
      boolean foundRequestedReport;
      int status = MonitorStatus.MONITOR_STATUS_OK;
      MonitorGetCPUBlockReportsReply reply;

      request = (MonitorGetCPUBlockReportsRequest) sig;
      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client == null)
      {
         reply = new MonitorGetCPUBlockReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUBlockReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      if (!isValidExecutionUnit(request.euId))
      {
         reply = new MonitorGetCPUBlockReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_REQUEST_INVALID;
         reply.enabled = false;
         reply.euId = request.euId;
         reply.reports = new MonitorCPUBlockReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      signalBlocks = dump.getMonitorCPUBlockReportSignalBlocks();
      reports = new ArrayList(NUM_REPORTS_PER_REPLY);
      fromBeginning = ((request.tick == 0) && (request.ntick == 0));
      if (fromBeginning)
      {
         replyStart = 0;
         reportStart = 0;
         client.setCpuBlockReportsReplyIndex(request.euId, 0);
         client.setCpuBlockReportIndex(request.euId, 0);
         foundRequestedReport = true;
      }
      else
      {
         replyStart = client.getCpuBlockReportsReplyIndex(request.euId);
         reportStart = client.getCpuBlockReportIndex(request.euId);
         foundRequestedReport = false;
      }

      try
      {
         outer: for (int i = replyStart; i < signalBlocks.size(); i++)
         {
            SignalBlock signalBlock = (SignalBlock) signalBlocks.get(i);
            MonitorGetCPUBlockReportsRequest storedRequest =
               dump.getMonitorGetCPUBlockReportsRequest(signalBlock);
            if (request.euId == storedRequest.euId)
            {
               reply = dump.getMonitorGetCPUBlockReportsReply(signalBlock);
               for (int j = reportStart; j < reply.reports.length; j++)
               {
                  MonitorCPUBlockReport report = reply.reports[j];
                  if (!foundRequestedReport)
                  {
                     if ((report.tick == request.tick) &&
                         (report.ntick == request.ntick))
                     {
                        foundRequestedReport = true;
                        continue;
                     }
                  }
                  if (foundRequestedReport)
                  {
                     if (reports.size() < NUM_REPORTS_PER_REPLY)
                     {
                        reports.add(report);
                        client.setCpuBlockReportsReplyIndex(request.euId, i);
                        client.setCpuBlockReportIndex(request.euId, j);
                     }
                     else
                     {
                        break outer;
                     }
                  }
               }
               reportStart = 0;
            }
         }
      }
      catch (Exception e)
      {
         status = MonitorStatus.MONITOR_STATUS_CANCELLED;
         log("Error reading MonitorGetCPUBlockReportsRequest signal block " +
             "from dump file in PMD monitor.", e);
      }

      reply = new MonitorGetCPUBlockReportsReply();
      reply.status = status;
      reply.enabled = (reports.size() > 0);
      reply.euId = request.euId;
      reply.reports = (MonitorCPUBlockReport[])
         reports.toArray(new MonitorCPUBlockReport[0]);
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetUserReportsRequest(Signal sig)
   {
      MonitorGetUserReportsRequest request;
      MonitorClient client;
      List signalBlocks;
      List reports;
      boolean fromBeginning;
      int replyStart;
      int reportStart;
      boolean foundRequestedReport;
      int status = MonitorStatus.MONITOR_STATUS_OK;
      boolean continuous = false;
      boolean maxmin = false;
      boolean multiple = false;
      MonitorGetUserReportsReply reply;

      request = (MonitorGetUserReportsRequest) sig;
      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client == null)
      {
         reply = new MonitorGetUserReportsReply();
         reply.status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
         reply.reportNo = request.reportNo;
         reply.enabled = false;
         reply.continuous = false;
         reply.maxmin = false;
         reply.multiple = false;
         reply.reports = new MonitorUserReport[0];
         sendSignal(sig.getSender(), reply);
         return;
      }
      signalBlocks = dump.getMonitorUserReportSignalBlocks();
      reports = new ArrayList(NUM_REPORTS_PER_REPLY);
      fromBeginning = ((request.tick == 0) && (request.ntick == 0));
      if (fromBeginning)
      {
         replyStart = 0;
         reportStart = 0;
         client.setUserReportsReplyIndex(request.reportNo, 0);
         client.setUserReportIndex(request.reportNo, 0);
         foundRequestedReport = true;
      }
      else
      {
         replyStart = client.getUserReportsReplyIndex(request.reportNo);
         reportStart = client.getUserReportIndex(request.reportNo);
         foundRequestedReport = false;
      }

      try
      {
         outer: for (int i = replyStart; i < signalBlocks.size(); i++)
         {
            SignalBlock signalBlock = (SignalBlock) signalBlocks.get(i);
            MonitorGetUserReportsRequest storedRequest =
               dump.getMonitorGetUserReportsRequest(signalBlock);
            if (request.reportNo == storedRequest.reportNo)
            {
               reply = dump.getMonitorGetUserReportsReply(signalBlock);
               for (int j = reportStart; j < reply.reports.length; j++)
               {
                  MonitorUserReport report = reply.reports[j];
                  if (!foundRequestedReport)
                  {
                     if ((report.tick == request.tick) &&
                         (report.ntick == request.ntick))
                     {
                        foundRequestedReport = true;
                        continue;
                     }
                  }
                  if (foundRequestedReport)
                  {
                     if (reports.size() < NUM_REPORTS_PER_REPLY)
                     {
                        reports.add(report);
                        client.setUserReportsReplyIndex(request.reportNo, i);
                        client.setUserReportIndex(request.reportNo, j);
                        continuous = reply.continuous;
                        maxmin = reply.maxmin;
                        multiple = reply.multiple;
                     }
                     else
                     {
                        break outer;
                     }
                  }
               }
               reportStart = 0;
            }
         }
      }
      catch (Exception e)
      {
         status = MonitorStatus.MONITOR_STATUS_CANCELLED;
         log("Error reading MonitorGetUserReportsRequest signal block from " +
             "dump file in PMD monitor.", e);
      }

      reply = new MonitorGetUserReportsReply();
      reply.status = status;
      reply.reportNo = request.reportNo;
      reply.enabled = (reports.size() > 0);
      reply.continuous = continuous;
      reply.maxmin = maxmin;
      reply.multiple = multiple;
      reply.reports =
         (MonitorUserReport[]) reports.toArray(new MonitorUserReport[0]);
      sendSignal(sig.getSender(), reply);
   }

   private void handleAttachRequest(Signal sig)
   {
      MonitorAttachRequest request;
      MonitorTag[] tags;
      boolean newEventTypeClient = false;
      MonitorClient client;
      int status;
      MonitorAttachReply reply;

      request = (MonitorAttachRequest) sig;
      tags = request.tags;
      if (tags != null)
      {
         for (int i = 0; i < tags.length; i++)
         {
            if (tags[i].getType() == Monitor.MONITOR_ATTACH_TAG_EVENT_SIGSELECT)
            {
               newEventTypeClient = true;
               break;
            }
         }
      }

      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client != null)
      {
         client.setAttached(true);
         client.setOldEventTypeClient(!newEventTypeClient);
         status = MonitorStatus.MONITOR_STATUS_OK;
      }
      else
      {
         status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
      }

      reply = new MonitorAttachReply();
      reply.status = status;
      reply.processCacheCount = PROCESS_CACHE_SIZE + 1;
      reply.extendedInfo = true;
      reply.notifySignals = null;
      reply.interceptSignals = null;
      reply.traceSignals = new int[]
      {
         MonitorGetProcessTraceReply.SIG_NO,
         MonitorGetSendTraceReply.SIG_NO,
         MonitorGetReceiveTraceReply.SIG_NO,
         MonitorGetSwapTraceReply.SIG_NO,
         MonitorGetCreateTraceReply.SIG_NO,
         MonitorGetKillTraceReply.SIG_NO,
         MonitorGetErrorTraceReply.SIG_NO,
         MonitorGetResetTraceReply.SIG_NO,
         MonitorGetUserTraceReply.SIG_NO,
         MonitorGetBindTraceReply.SIG_NO,
         MonitorGetAllocTraceReply.SIG_NO,
         MonitorGetFreeBufTraceReply.SIG_NO,
         MonitorGetTimeoutTraceReply.SIG_NO
      };
      sendSignal(sig.getSender(), reply);
   }

   private void handleDetachRequest(Signal sig)
   {
      MonitorClient client;
      int status;
      MonitorDetachReply reply;

      client = (MonitorClient) clients.get(new Integer(sig.getSender()));
      if (client != null)
      {
         client.setAttached(false);
         client.setOldEventTypeClient(false);
         status = MonitorStatus.MONITOR_STATUS_OK;
      }
      else
      {
         status = MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR;
      }

      reply = new MonitorDetachReply();
      reply.status = status;
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetTraceRequest(Signal sig)
   {
      MonitorGetTraceRequest request;
      int client;
      MonitorClient clientObject;
      boolean oldEventTypeClient;
      MonitorEventTrace eventTrace;
      long numEvents;
      long start;
      long end;
      SignalInputStream in = null;
      long counter = 0;
      boolean error = false;
      int status;

      request = (MonitorGetTraceRequest) sig;
      client = request.getSender();
      clientObject = (MonitorClient) clients.get(new Integer(client));
      if ((clientObject == null) || !clientObject.isAttached())
      {
         sendGetTraceEndmark(client, MonitorStatus.MONITOR_STATUS_REQUEST_INVALID);
         return;
      }
      oldEventTypeClient = clientObject.isOldEventTypeClient();
      start = 0xFFFFFFFFL & request.from;
      end = 0xFFFFFFFFL & request.to;
      if (end <= start)
      {
         sendGetTraceEndmark(client, MonitorStatus.MONITOR_STATUS_REQUEST_INVALID);
         return;
      }

      eventTrace = dump.getMonitorEventTrace();
      if (eventTrace == null)
      {
         sendGetTraceEndmark(client, MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR);
         return;
      }
      numEvents = (oldEventTypeClient ? eventTrace.getNumOldEvents() :
                   eventTrace.getNumEvents());
      if (numEvents == 0)
      {
         sendGetTraceEndmark(client, MonitorStatus.MONITOR_STATUS_OK);
         return;
      }
      start = ((start < numEvents) ? start :
               ((numEvents > 50) ? numEvents - 50 : numEvents - 1));
      end = ((end < numEvents) ? end : numEvents);

      try
      {
         Signal signal;
         ProcessInfoCache foundProcessesCache;
         ProcessInfoCache sentProcessesCache;
         boolean gotEndmark = false;

         in = new SignalInputStream(eventTrace.getSignalBlock().getInputStream(),
                                    bigEndian);
         signal = eventTrace.readSignal(in);
         if (!(signal instanceof MonitorGetTraceRequest))
         {
            sendGetTraceEndmark(client, MonitorStatus.MONITOR_STATUS_INTERNAL_ERROR);
            return;
         }

         foundProcessesCache = new ProcessInfoCache(PROCESS_CACHE_SIZE + 1);
         sentProcessesCache = new ProcessInfoCache(PROCESS_CACHE_SIZE);

         do
         {
            signal = eventTrace.readSignal(in);
            if (signal instanceof MonitorEventInfoSignal)
            {
               if (oldEventTypeClient && !MonitorEventTrace.isOldEventType(signal))
               {
                  continue;
               }
               if ((counter >= start) && (counter < end))
               {
                  MonitorEventInfoSignal eventInfoReply;

                  sendEventFromToInfo(client,
                                      signal,
                                      foundProcessesCache,
                                      sentProcessesCache);
                  eventInfoReply = (MonitorEventInfoSignal) signal;
                  eventInfoReply.reference = (int) counter;
                  sendSignal(client, eventInfoReply);
               }
               counter++;
            }
            else if (signal instanceof MonitorGetProcessTraceReply)
            {
               MonitorGetProcessTraceReply process =
                  (MonitorGetProcessTraceReply) signal;
               foundProcessesCache.put(process.pid, process);
            }
            else if (signal instanceof MonitorGetTraceEndmark)
            {
               gotEndmark = true;
            }
            else
            {
               log("Unknown reply signal in MonitorGetTraceRequest signal " +
                   "block in dump file.");
            }
         }
         while ((in.available() > 0) && !gotEndmark && (counter < end));
      }
      catch (IOException e)
      {
         error = true;
         log("Error reading MonitorGetTraceRequest signal block from dump " +
             "file in PMD monitor.", e);
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

      status = (error ? MonitorStatus.MONITOR_STATUS_CANCELLED :
         ((counter == numEvents) ? eventTrace.getStatus() :
          MonitorStatus.MONITOR_STATUS_OK));
      sendGetTraceEndmark(client, status);
   }

   private List findMatchingSysParams(MonitorGetInfoRequest request)
   {
      return ((request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM) ?
              new ArrayList(dump.getMonitorGetSysParamReplies()) :
              Collections.EMPTY_LIST);
   }

   private List findMatchingSegments(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();
      boolean hasBriefInfo = false;
      boolean hasLongInfo = false;

      if (request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         for (Iterator i = dump.getMonitorGetSegmentInfoReplies().iterator(); i.hasNext();)
         {
            Signal sig = (Signal) i.next();
            if (sig instanceof MonitorGetSegmentInfoReply)
            {
               hasBriefInfo = true;
               list.add(sig);
            }
            else if (sig instanceof MonitorGetSegmentInfoLongReply)
            {
               hasLongInfo = true;
               list.add(sig);
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         Signal sig = dump.getSegment(request.scopeId);
         if (sig instanceof MonitorGetSegmentInfoReply)
         {
            hasBriefInfo = true;
            list.add(sig);
         }
         else if (sig instanceof MonitorGetSegmentInfoLongReply)
         {
            hasLongInfo = true;
            list.add(sig);
         }
      }

      if (((request.level == 0) && hasLongInfo) ||
          ((request.level != 0) && hasBriefInfo))
      {
         convertToBriefSegmentInfo(list);
      }

      return list;
   }

   private List findMatchingPools(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();

      if (request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         list.addAll(dump.getMonitorGetPoolInfoReplies());
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
      {
         for (Iterator i = dump.getMonitorGetPoolInfoReplies().iterator(); i.hasNext();)
         {
            MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) i.next();
            if (request.scopeId == pool.sid)
            {
               list.add(pool);
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply)dump.getPool(request.scopeId);
         if (pool != null)
         {
            list.add(pool);
         }
      }

      return list;
   }
   
   private List findMatchingPools64(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();

      if (request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         list.addAll(dump.getMonitorGetPoolInfo64Replies());
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
      {
         for (Iterator i = dump.getMonitorGetPoolInfo64Replies().iterator(); i.hasNext();)
         {
            MonitorGetPoolInfo64Reply pool64 = (MonitorGetPoolInfo64Reply) i.next();
            if (request.scopeId == pool64.sid)
            {
               list.add(pool64);
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         MonitorGetPoolInfo64Reply pool64 = (MonitorGetPoolInfo64Reply)dump.getPool64(request.scopeId);
         if (pool64 != null)
         {
            list.add(pool64);
         }
      }

      return list;
   }

   private List findMatchingBlocks(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();

      if (request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         list.addAll(dump.getMonitorGetBlockInfoReplies());
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
      {
         for (Iterator i = dump.getMonitorGetBlockInfoReplies().iterator(); i.hasNext();)
         {
            MonitorGetBlockInfoReply block = (MonitorGetBlockInfoReply) i.next();
            if (request.scopeId == block.sid)
            {
               list.add(block);
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         MonitorGetBlockInfoReply block =
            dump.getBlock(request.scopeId);
         if (block != null)
         {
            list.add(block);
         }
      }

      return filterBlocks(list, request.tags);
   }

   private List findMatchingProcesses(MonitorGetInfoRequest request)
   {
      List list = new ArrayList(100);
      boolean hasBriefInfo = false;
      boolean hasLongInfo = false;

      if (request.scopeType == Monitor.MONITOR_SCOPE_SYSTEM)
      {
         for (Iterator i = dump.getMonitorGetProcessInfoReplies().iterator(); i.hasNext();)
         {
            Signal sig = (Signal) i.next();
            if (sig instanceof MonitorGetProcessInfoReply)
            {
               hasBriefInfo = true;
               list.add(sig);
            }
            else if (sig instanceof MonitorGetProcessInfoLongReply)
            {
               hasLongInfo = true;
               list.add(sig);
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_SEGMENT_ID)
      {
         for (Iterator i = dump.getMonitorGetProcessInfoReplies().iterator(); i.hasNext();)
         {
            Signal sig = (Signal) i.next();
            if (sig instanceof MonitorGetProcessInfoReply)
            {
               if (request.scopeId == getProcessSid(sig))
               {
                  hasBriefInfo = true;
                  list.add(sig);
               }
            }
            else if (sig instanceof MonitorGetProcessInfoLongReply)
            {
               if (request.scopeId == getProcessSid(sig))
               {
                  hasLongInfo = true;
                  list.add(sig);
               }
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_BLOCK_ID)
      {
         for (Iterator i = dump.getMonitorGetProcessInfoReplies().iterator(); i.hasNext();)
         {
            Signal sig = (Signal) i.next();
            if (sig instanceof MonitorGetProcessInfoReply)
            {
               if (request.scopeId == getProcessBid(sig))
               {
                  hasBriefInfo = true;
                  list.add(sig);
               }
            }
            else if (sig instanceof MonitorGetProcessInfoLongReply)
            {
               if (request.scopeId == getProcessBid(sig))
               {
                  hasLongInfo = true;
                  list.add(sig);
               }
            }
         }
      }
      else if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         for (Iterator i = dump.getMonitorGetProcessInfoReplies().iterator(); i.hasNext();)
         {
            Signal sig = (Signal) i.next();
            if (sig instanceof MonitorGetProcessInfoReply)
            {
               if (request.scopeId == getProcessPid(sig))
               {
                  hasBriefInfo = true;
                  list.add(sig);
                  break;
               }
            }
            else if (sig instanceof MonitorGetProcessInfoLongReply)
            {
               if (request.scopeId == getProcessPid(sig))
               {
                  hasLongInfo = true;
                  list.add(sig);
                  break;
               }
            }
         }
      }

      if (((request.level == 0) && hasLongInfo) ||
          ((request.level != 0) && hasBriefInfo))
      {
         convertToBriefProcessInfo(list);
         hasBriefInfo = true;
      }

      return (hasBriefInfo ?
              filterProcesses(list, request.tags) :
              filterLongProcesses(list, request.tags));
   }

   private List findMatchingEnvVars(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();

      if (request.scopeType == Monitor.MONITOR_SCOPE_ID)
      {
         for (Iterator i = dump.getMonitorGetEnvVarsReplies().iterator(); i.hasNext();)
         {
            MonitorGetEnvVarsReply reply = (MonitorGetEnvVarsReply) i.next();
            if (request.scopeId == reply.pid)
            {
               list.add(reply);
            }
         }
      }

      return list;
   }

   private List findMatchingStacks(MonitorGetInfoRequest request)
   {
      List list = new ArrayList(100);
      List processes;

      processes = findMatchingProcesses(request);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Signal sig = (Signal) i.next();
         int pid = getProcessPid(sig);
         for (Iterator j = dump.getMonitorGetStackUsageReplies().iterator(); j.hasNext();)
         {
            MonitorGetStackUsageReply stack =
               (MonitorGetStackUsageReply) j.next();
            if (pid == stack.pid)
            {
               list.add(stack);
               break;
            }
         }
      }

      return filterStacks(list, request.tags);
   }

   private List findMatchingSignalQueues(MonitorGetInfoRequest request)
   {
      List list = new ArrayList();
      List processes;
      boolean hasBriefInfo = false;
      boolean hasLongInfo = false;

      processes = findMatchingProcesses(request);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Signal process = (Signal) i.next();
         int pid = getProcessPid(process);
         for (Iterator j = dump.getMonitorGetSignalQueueReplies().iterator(); j.hasNext();)
         {
            Signal sig = (Signal) j.next();
            if (sig instanceof MonitorGetSignalQueueReply)
            {
               MonitorGetSignalQueueReply signalQueue =
                  (MonitorGetSignalQueueReply) sig;
               if (pid == signalQueue.pid)
               {
                  hasBriefInfo = true;
                  list.add(signalQueue);
               }
            }
            else if (sig instanceof MonitorGetSignalQueueLongReply)
            {
               MonitorGetSignalQueueLongReply signalQueue =
                  (MonitorGetSignalQueueLongReply) sig;
               if (pid == signalQueue.pid)
               {
                  hasLongInfo = true;
                  list.add(signalQueue);
               }
            }
         }
      }

      if (((request.level == 0) && hasLongInfo) ||
          ((request.level != 0) && hasBriefInfo))
      {
         convertToBriefSignalQueue(list);
      }

      return list;
   }

   private List findMatchingSignals(MonitorGetInfoRequest request)
   {
      List list = new ArrayList(100);
      List pools;

      pools = findMatchingPools(request);
      //FIXME -> search 64 bit pools after you're done with 32 bit ones
      for (Iterator i = pools.iterator(); i.hasNext();)
      {
         MonitorGetPoolInfoReply pool = (MonitorGetPoolInfoReply) i.next();
         for (Iterator j = dump.getMonitorGetPoolSignalsReplies().iterator(); j.hasNext();)
         {
            MonitorGetPoolSignalsReply poolSignals =
               (MonitorGetPoolSignalsReply) j.next();
            if (pool.pid == poolSignals.pid)
            {
               list.add(poolSignals);
            }
         }
      }

      return filterSignals(list, request.tags);
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

   private void serveGetMemoryRequest(MonitorGetMemoryRequest request)
   {
	  MonitorGetMemoryEndmark endmark;
      boolean partial = true;
      
      try
      {
    	 long reqStart = request.address & 0xFFFFFFFFL;
    	 long reqSize = request.size & 0xFFFFFFFFL;
    	 long reqEnd = reqStart + reqSize;

         for (Iterator i = dump.getMemoryBlocks().iterator(); i.hasNext();)
         {
            MemoryBlock memoryBlock;
            long memStart;
            long memSize;
            long memEnd;

            memoryBlock = (MemoryBlock) i.next();
            memStart = memoryBlock.getStartAddress();
            memSize = memoryBlock.getLength();
            memEnd = memStart + memSize;

            if ((reqStart >= memStart) && (reqStart < memEnd))
            {
               DataInputStream in = null;

               try
               {
                  long length;
                  long numSkip;
                  boolean compressed;

                  partial = (reqEnd > memEnd);
                  length = (partial ? (memEnd - reqStart) : reqSize);
                  numSkip = reqStart - memStart;
                  compressed = memoryBlock.isCompressed();
                  if (compressed)
                  {
                     in = new DataInputStream(memoryBlock.getInputStream());
                     for (long j = 0; j < numSkip; j += in.skip(numSkip - j));
                  }

                  for (long k = 0, remaining = length; k < length;)
                  {
                     int chunkSize;
                     MonitorGetMemoryReply reply;
                     
                     chunkSize = ((remaining > MEMORY_CHUNK_SIZE) ?
                                  MEMORY_CHUNK_SIZE : (int) remaining);
                     reply = new MonitorGetMemoryReply();
                   	 reply.pid = request.pid;
                     reply.address = (int) (reqStart + k);
                     reply.data = new byte[chunkSize];
                     if (compressed)
                     {
                        in.readFully(reply.data);	
                     }
                     else
                     {
                        memoryBlock.read(reply.data,
                                         (int) (numSkip + k),
                                         reply.data.length);
                     }
                  	 sendSignal(request.getSender(), reply);
                     k += chunkSize;
                     remaining -= chunkSize;
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

               break;
            }
         }
      }
      catch (Exception e)
      {
         partial = true;
         log("Error reading memory block from dump file in PMD monitor.", e);
      }

      endmark = new MonitorGetMemoryEndmark();
      endmark.status = (partial ?
                        MonitorStatus.MONITOR_STATUS_MEMORY_GET_ERROR :
                        MonitorStatus.MONITOR_STATUS_OK);
      sendSignal(request.getSender(), endmark);
   }

   private void serveGetMemory64Request(MonitorGetMemory64Request request)
   {
      MonitorGetMemoryEndmark endmark;
      boolean partial = true;
      
      try
      {
         long reqStart = request.address;
         long reqSize = request.size;
         long reqEnd = reqStart + reqSize;

         for (Iterator i = dump.getMemoryBlocks().iterator(); i.hasNext();)
         {
            MemoryBlock memoryBlock;
            long memStart;
            long memSize;
            long memEnd;

            memoryBlock = (MemoryBlock) i.next();
            memStart = memoryBlock.getStartAddress();
            memSize = memoryBlock.getLength();
            memEnd = memStart + memSize;

            if ((reqStart >= memStart) && (reqStart < memEnd))
            {
               DataInputStream in = null;

               try
               {
                  long length;
                  long numSkip;
                  boolean compressed;

                  partial = (reqEnd > memEnd);
                  length = (partial ? (memEnd - reqStart) : reqSize);
                  numSkip = reqStart - memStart;
                  compressed = memoryBlock.isCompressed();
                  if (compressed)
                  {
                     in = new DataInputStream(memoryBlock.getInputStream());
                     for (long j = 0; j < numSkip; j += in.skip(numSkip - j));
                  }

                  for (long k = 0, remaining = length; k < length;)
                  {
                     int chunkSize;
                     MonitorGetMemory64Reply reply;
                     
                     chunkSize = ((remaining > MEMORY_CHUNK_SIZE) ?
                                  MEMORY_CHUNK_SIZE : (int) remaining);
                     reply = new MonitorGetMemory64Reply();
                     reply.pid = request.pid;
                     reply.address = reqStart + k;
                     reply.data = new byte[chunkSize];
                     if (compressed)
                     {
                        in.readFully(reply.data);   
                     }
                     else
                     {
                        memoryBlock.read(reply.data,
                                         (int) (numSkip + k),
                                         reply.data.length);
                     }
                     sendSignal(request.getSender(), reply);
                     k += chunkSize;
                     remaining -= chunkSize;
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

               break;
            }
         }
      }
      catch (Exception e)
      {
         partial = true;
         log("Error reading memory block from dump file in PMD monitor.", e);
      }

      endmark = new MonitorGetMemoryEndmark();
      endmark.status = (partial ?
                        MonitorStatus.MONITOR_STATUS_MEMORY_GET_ERROR :
                        MonitorStatus.MONITOR_STATUS_OK);
      sendSignal(request.getSender(), endmark);
   }

   private void serveGetRegistersRequest(MonitorGetRegistersRequest request)
   {
      boolean found = false;

      for (Iterator i = dump.getMonitorGetRegistersReplies().iterator(); i.hasNext();)
      {
         MonitorGetRegistersReply regs = (MonitorGetRegistersReply) i.next();
         if (request.pid == regs.pid)
         {
            MonitorGetRegistersReply reply = new MonitorGetRegistersReply();
            reply.pid = regs.pid;
            reply.status = regs.status;
            reply.registers = new int[request.registers.length * 2];
            for (int j = 0; j < request.registers.length; j++)
            {
               reply.registers[j * 2] = request.registers[j]; // Id
               reply.registers[j * 2 + 1] = 0; // Value
            }
            storeRegisters(regs, reply);
            sendSignal(request.getSender(), reply);
            found = true;
            break;
         }
      }

      if (!found)
      {
         MonitorGetRegistersReply reply = new MonitorGetRegistersReply();
         reply.pid = request.pid;
         reply.status = MonitorStatus.MONITOR_STATUS_ID_INVALID;
         reply.registers = new int[0];
         sendSignal(request.getSender(), reply);
      }
   }

   private static void storeRegisters(MonitorGetRegistersReply regs,
                                      MonitorGetRegistersReply reply)
   {
      for (int i = 0; i < reply.registers.length; i += 2)
      {
         int id = reply.registers[i];
         boolean found = false;

         for (int j = 0; j < regs.registers.length; j += 2)
         {
            if (id == regs.registers[j])
            {
               reply.registers[i + 1] = regs.registers[j + 1];
               found = true;
               break;
            }
         }

         if (!found)
         {
            reply.registers[i] |= REGISTER_INVALID;
         }
      }
   }

   private boolean isValidExecutionUnit(short executionUnit)
   {
      MonitorConnectReply connectReply = dump.getMonitorConnectReply();
      int euCount = 0xFFFF & connectReply.euCount;
      int eu = 0xFFFF & executionUnit;
      return (eu == 0) || (eu < euCount) ||
             (executionUnit == Monitor.MONITOR_ALL_EXECUTION_UNITS);
   }

   private void sendEventFromToInfo(int client,
                                    Signal event,
                                    ProcessInfoCache foundProcessesCache,
                                    ProcessInfoCache sentProcessesCache)
   {
      if (event instanceof MonitorGetSendTraceReply)
      {
         MonitorGetSendTraceReply reply = (MonitorGetSendTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.signalSender,
                                  foundProcessesCache,
                                  sentProcessesCache);
         sendGetProcessTraceReply(client,
                                  reply.signalAddressee,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetReceiveTraceReply)
      {
         MonitorGetReceiveTraceReply reply = (MonitorGetReceiveTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.signalSender,
                                  foundProcessesCache,
                                  sentProcessesCache);
         sendGetProcessTraceReply(client,
                                  reply.signalAddressee,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetSwapTraceReply)
      {
         MonitorGetSwapTraceReply reply = (MonitorGetSwapTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
         sendGetProcessTraceReply(client,
                                  reply.to,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetCreateTraceReply)
      {
         MonitorGetCreateTraceReply reply = (MonitorGetCreateTraceReply) event;
         if (reply.from != 0)
         {
            sendGetProcessTraceReply(client,
                                     reply.from,
                                     foundProcessesCache,
                                     sentProcessesCache);
         }
         sendGetProcessTraceReply(client,
                                  reply.to,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetKillTraceReply)
      {
         MonitorGetKillTraceReply reply = (MonitorGetKillTraceReply) event;
         if (reply.from != 0)
         {
            sendGetProcessTraceReply(client,
                                     reply.from,
                                     foundProcessesCache,
                                     sentProcessesCache);
         }
         sendGetProcessTraceReply(client,
                                  reply.to,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetErrorTraceReply)
      {
         MonitorGetErrorTraceReply reply = (MonitorGetErrorTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetResetTraceReply)
      {
         // Nothing to do.
      }
      else if (event instanceof MonitorGetUserTraceReply)
      {
         MonitorGetUserTraceReply reply = (MonitorGetUserTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetBindTraceReply)
      {
         MonitorGetBindTraceReply reply = (MonitorGetBindTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetAllocTraceReply)
      {
         MonitorGetAllocTraceReply reply = (MonitorGetAllocTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetFreeBufTraceReply)
      {
         MonitorGetFreeBufTraceReply reply = (MonitorGetFreeBufTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
      else if (event instanceof MonitorGetTimeoutTraceReply)
      {
         MonitorGetTimeoutTraceReply reply = (MonitorGetTimeoutTraceReply) event;
         sendGetProcessTraceReply(client,
                                  reply.from,
                                  foundProcessesCache,
                                  sentProcessesCache);
      }
   }

   private void sendGetProcessTraceReply(int client,
                                         int pid,
                                         ProcessInfoCache foundProcessesCache,
                                         ProcessInfoCache sentProcessesCache)
   {
      MonitorGetProcessTraceReply processReply =
         sentProcessesCache.get(pid);
      if (processReply == null)
      {
         processReply = foundProcessesCache.get(pid);
         if (processReply != null)
         {
            sendSignal(client, processReply);
            sentProcessesCache.put(pid, processReply);
         }
         else
         {
            log("Missing MonitorGetProcessTraceReply signal in " +
                "MonitorGetTraceRequest signal block in dump file.");
         }
      }
   }

   private void sendGetTraceEndmark(int client, int status)
   {
      MonitorGetTraceEndmark endmark = new MonitorGetTraceEndmark();
      endmark.status = status;
      sendSignal(client, endmark);
   }

   private boolean isConnectedAndSupported(int pid, int sigNo)
   {
      boolean connected;
      boolean supported = false;

      connected = (clients.get(new Integer(pid)) != null);
      if (connected)
      {
         int[] sigs = dump.getMonitorInterfaceReply().sigs;
         for (int i = 0; i < sigs.length; i++)
         {
            if (sigs[i] == sigNo)
            {
               supported = true;
               break;
            }
         }
      }

      return (connected && supported);
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
         log("I/O exception in PMD monitor when byte order converting signal "
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

   private boolean hasLongSegmentInfoSupport()
   {
      for (Iterator i = dump.getMonitorGetSegmentInfoReplies().iterator(); i.hasNext();)
      {
         Signal sig = (Signal) i.next();
         if (!(sig instanceof MonitorGetSegmentInfoLongReply))
         {
            return false;
         }
      }

      return true;
   }

   private Signal getCrashedProcess()
   {
      Signal process = null;

      try
      {
         List errorBlocks = dump.getErrorBlocks();
         if (!errorBlocks.isEmpty())
         {
            ErrorBlock lastErrorBlock;
            int crashedPid;

            lastErrorBlock = (ErrorBlock) errorBlocks.get(errorBlocks.size() - 1);
            crashedPid = (int) lastErrorBlock.getCurrentProcess();
            process = dump.getProcess(crashedPid);
         }
      } catch (IffException ignore) {}

      return process;
   }

   private String getProcessName(int pid)
   {
      Signal sig;
      String processName = null;

      sig = dump.getProcess(pid);
      if (sig instanceof MonitorGetProcessInfoReply)
      {
         MonitorGetProcessInfoReply process =
            (MonitorGetProcessInfoReply) sig;
         processName = process.name;
      }
      else if (sig instanceof MonitorGetProcessInfoLongReply)
      {
         MonitorGetProcessInfoLongReply process =
            (MonitorGetProcessInfoLongReply) sig;
         processName = process.processName;
      }
      return processName;
   }

   private static int getProcessPid(Signal sig)
   {
      if (sig instanceof MonitorGetProcessInfoReply)
      {
         MonitorGetProcessInfoReply process =
            (MonitorGetProcessInfoReply) sig;
         return process.pid;
      }
      else if (sig instanceof MonitorGetProcessInfoLongReply)
      {
         MonitorGetProcessInfoLongReply process =
            (MonitorGetProcessInfoLongReply) sig;
         return process.pid;
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   private static int getProcessBid(Signal sig)
   {
      if (sig instanceof MonitorGetProcessInfoReply)
      {
         MonitorGetProcessInfoReply process =
            (MonitorGetProcessInfoReply) sig;
         return process.bid;
      }
      else if (sig instanceof MonitorGetProcessInfoLongReply)
      {
         MonitorGetProcessInfoLongReply process =
            (MonitorGetProcessInfoLongReply) sig;
         return process.bid;
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   private static int getProcessSid(Signal sig)
   {
      if (sig instanceof MonitorGetProcessInfoReply)
      {
         MonitorGetProcessInfoReply process =
            (MonitorGetProcessInfoReply) sig;
         return process.sid;
      }
      else if (sig instanceof MonitorGetProcessInfoLongReply)
      {
         MonitorGetProcessInfoLongReply process =
            (MonitorGetProcessInfoLongReply) sig;
         return process.sid;
      }
      else
      {
         throw new IllegalArgumentException();
      }
   }

   private static void convertToBriefSegmentInfo(List list)
   {
      for (int i = 0; i < list.size(); i++)
      {
         Signal sig = (Signal) list.get(i);
         if (sig instanceof MonitorGetSegmentInfoLongReply)
         {
            MonitorGetSegmentInfoLongReply longReply;
            MonitorGetSegmentInfoReply briefReply;

            longReply = (MonitorGetSegmentInfoLongReply) sig;
            briefReply = new MonitorGetSegmentInfoReply();
            briefReply.sid = longReply.sid;
            briefReply.number = longReply.number;
            list.set(i, briefReply);
         }
      }
   }

   private static void convertToBriefProcessInfo(List list)
   {
      for (int i = 0; i < list.size(); i++)
      {
         Signal sig = (Signal) list.get(i);
         if (sig instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply longReply;
            MonitorGetProcessInfoReply briefReply;

            longReply = (MonitorGetProcessInfoLongReply) sig;
            briefReply = new MonitorGetProcessInfoReply();
            briefReply.pid = longReply.pid;
            briefReply.bid = longReply.bid;
            briefReply.sid = longReply.sid;
            briefReply.type = longReply.type;
            briefReply.state = longReply.state;
            briefReply.priority = longReply.priority;
            briefReply.entrypoint = longReply.entrypoint;
            briefReply.properties = longReply.properties;
            briefReply.name = longReply.processName;
            list.set(i, briefReply);
         }
      }
   }

   private static void convertToBriefSignalQueue(List list)
   {
      for (int i = 0; i < list.size(); i++)
      {
         Signal sig = (Signal) list.get(i);
         if (sig instanceof MonitorGetSignalQueueLongReply)
         {
            MonitorGetSignalQueueLongReply longReply;
            MonitorGetSignalQueueReply briefReply;

            longReply = (MonitorGetSignalQueueLongReply) sig;
            briefReply = new MonitorGetSignalQueueReply();
            briefReply.pid = longReply.pid;
            briefReply.signal = longReply.signal;
            list.set(i, briefReply);
         }
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
   
   private static class MonitorClient
   {
      private final int pid;
      private final String name;
      private final Map cpuReportsReplyIndexMap;
      private final Map cpuReportIndexMap;
      private final Map cpuPriReportsReplyIndexMap;
      private final Map cpuPriReportIndexMap;
      private final Map cpuProcessReportsReplyIndexMap;
      private final Map cpuProcessReportIndexMap;
      private final Map cpuBlockReportsReplyIndexMap;
      private final Map cpuBlockReportIndexMap;
      private final Map userReportsReplyIndexMap;
      private final Map userReportIndexMap;
      private volatile boolean attached;
      private volatile boolean oldEventTypeClient;

      MonitorClient(int pid, String name)
      {
         this.pid = pid;
         this.name = name;
         cpuReportsReplyIndexMap = new HashMap();
         cpuReportIndexMap = new HashMap();
         cpuPriReportsReplyIndexMap = new HashMap();
         cpuPriReportIndexMap = new HashMap();
         cpuProcessReportsReplyIndexMap = new HashMap();
         cpuProcessReportIndexMap = new HashMap();
         cpuBlockReportsReplyIndexMap = new HashMap();
         cpuBlockReportIndexMap = new HashMap();
         userReportsReplyIndexMap = new HashMap();
         userReportIndexMap = new HashMap();
      }

      public int getPid()
      {
         return pid;
      }

      public String getName()
      {
         return name;
      }

      public int getCpuReportsReplyIndex(short executionUnit)
      {
         Integer i = (Integer) cpuReportsReplyIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuReportsReplyIndex(short executionUnit,
                                          int cpuReportsReplyIndex)
      {
         cpuReportsReplyIndexMap.put(new Short(executionUnit),
                                     new Integer(cpuReportsReplyIndex));
      }

      public int getCpuReportIndex(short executionUnit)
      {
         Integer i = (Integer) cpuReportIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuReportIndex(short executionUnit, int cpuReportIndex)
      {
         cpuReportIndexMap.put(new Short(executionUnit),
                               new Integer(cpuReportIndex));
      }

      public int getCpuPriReportsReplyIndex(short executionUnit)
      {
         Integer i = (Integer) cpuPriReportsReplyIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuPriReportsReplyIndex(short executionUnit,
                                             int cpuPriReportsReplyIndex)
      {
         cpuPriReportsReplyIndexMap.put(new Short(executionUnit),
                                        new Integer(cpuPriReportsReplyIndex));
      }

      public int getCpuPriReportIndex(short executionUnit)
      {
         Integer i = (Integer) cpuPriReportIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuPriReportIndex(short executionUnit, int cpuPriReportIndex)
      {
         cpuPriReportIndexMap.put(new Short(executionUnit),
                                  new Integer(cpuPriReportIndex));
      }

      public int getCpuProcessReportsReplyIndex(short executionUnit)
      {
         Integer i = (Integer) cpuProcessReportsReplyIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuProcessReportsReplyIndex(short executionUnit,
                                                 int cpuProcessReportsReplyIndex)
      {
         cpuProcessReportsReplyIndexMap.put(new Short(executionUnit),
                                            new Integer(cpuProcessReportsReplyIndex));
      }

      public int getCpuProcessReportIndex(short executionUnit)
      {
         Integer i = (Integer) cpuProcessReportIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuProcessReportIndex(short executionUnit,
                                           int cpuProcessReportIndex)
      {
         cpuProcessReportIndexMap.put(new Short(executionUnit),
                                      new Integer(cpuProcessReportIndex));
      }

      public int getCpuBlockReportsReplyIndex(short executionUnit)
      {
         Integer i = (Integer) cpuBlockReportsReplyIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuBlockReportsReplyIndex(short executionUnit,
                                               int cpuBlockReportsReplyIndex)
      {
         cpuBlockReportsReplyIndexMap.put(new Short(executionUnit),
                                          new Integer(cpuBlockReportsReplyIndex));
      }

      public int getCpuBlockReportIndex(short executionUnit)
      {
         Integer i = (Integer) cpuBlockReportIndexMap.get(new Short(executionUnit));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setCpuBlockReportIndex(short executionUnit,
                                         int cpuBlockReportIndex)
      {
         cpuBlockReportIndexMap.put(new Short(executionUnit),
                                    new Integer(cpuBlockReportIndex));
      }

      public int getUserReportsReplyIndex(int reportNo)
      {
         Integer i = (Integer) userReportsReplyIndexMap.get(new Integer(reportNo));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setUserReportsReplyIndex(int reportNo, int userReportsReplyIndex)
      {
         userReportsReplyIndexMap.put(new Integer(reportNo),
                                      new Integer(userReportsReplyIndex));
      }

      public int getUserReportIndex(int reportNo)
      {
         Integer i = (Integer) userReportIndexMap.get(new Integer(reportNo));
         return ((i != null) ? i.intValue() : 0);
      }

      public void setUserReportIndex(int reportNo, int userReportIndex)
      {
         userReportIndexMap.put(new Integer(reportNo),
                                new Integer(userReportIndex));
      }

      public boolean isAttached()
      {
         return attached;
      }

      public void setAttached(boolean attached)
      {
         this.attached = attached;
      }

      public boolean isOldEventTypeClient()
      {
         return oldEventTypeClient;
      }

      public void setOldEventTypeClient(boolean oldEventTypeClient)
      {
         this.oldEventTypeClient = oldEventTypeClient;
      }
   }
}
