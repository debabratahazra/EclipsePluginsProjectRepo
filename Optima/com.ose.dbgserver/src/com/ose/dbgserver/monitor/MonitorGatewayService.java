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

package com.ose.dbgserver.monitor;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ose.dbgserver.DebugServer;
import com.ose.dbgserver.monitor.signals.MonitorClearBreakpointRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetBreakpointRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetMemoryRequest;
import com.ose.dbgserver.monitor.signals.MonitorSetRegistersRequest;
import com.ose.dbgserver.monitor.signals.MonitorStepProcessRequest;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.signal.MonitorAttachRequest;
import com.ose.system.service.monitor.signal.MonitorBlockingFlowControlReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorDetachRequest;
import com.ose.system.service.monitor.signal.MonitorDisconnectRequest;
import com.ose.system.service.monitor.signal.MonitorGetBlockInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUPriReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUProcessReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetCPUReportsRequest;
import com.ose.system.service.monitor.signal.MonitorGetEnvVarsRequest;
import com.ose.system.service.monitor.signal.MonitorGetMemoryRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetPoolSignalsRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetRegisterInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetRegistersRequest;
import com.ose.system.service.monitor.signal.MonitorGetSignalQueueRequest;
import com.ose.system.service.monitor.signal.MonitorGetStackUsageRequest;
import com.ose.system.service.monitor.signal.MonitorInterceptScopeRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorKillScopeRequest;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;
import com.ose.system.service.monitor.signal.MonitorResumeScopeRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUPriReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUProcessReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorSetCPUReportsEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorStartScopeRequest;
import com.ose.system.service.monitor.signal.MonitorStopScopeRequest;

public class MonitorGatewayService extends AbstractGatewayService
{
   private static final String SERVICE_NAME = "ose_monitor";
   private static final String PLUGIN_ID = "com.ose.dbgserver";

   private static boolean licenseCheckedOut;
   private final SignalRegistry signalRegistry;
   private final short[] features;
   private final Map<Integer, DebugServer> clients;
   private volatile boolean bigEndian = true;

   public MonitorGatewayService(InetAddress address, int port)
      throws LicenseException, IOException
   {
      List featureList;
      DebugServer defaultDebugServer;

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

      // Initialize the monitor signal registry.
      signalRegistry = new SignalRegistry();
      signalRegistry.add(MonitorInterfaceRequest.class);
      signalRegistry.add(MonitorNameRequest.class);
      signalRegistry.add(MonitorConnectRequest.class);
      signalRegistry.add(MonitorDisconnectRequest.class);
      signalRegistry.add(MonitorBlockingFlowControlReply.class);
      signalRegistry.add(MonitorGetBlockInfoRequest.class);
      signalRegistry.add(MonitorGetProcessInfoRequest.class);
      signalRegistry.add(MonitorGetEnvVarsRequest.class);
      signalRegistry.add(MonitorGetStackUsageRequest.class);
      signalRegistry.add(MonitorGetSignalQueueRequest.class);
      signalRegistry.add(MonitorGetPoolInfoRequest.class);
      signalRegistry.add(MonitorGetPoolSignalsRequest.class);
      signalRegistry.add(MonitorStartScopeRequest.class);
      signalRegistry.add(MonitorStopScopeRequest.class);
      signalRegistry.add(MonitorKillScopeRequest.class);
      signalRegistry.add(MonitorGetMemoryRequest.class);
      signalRegistry.add(MonitorSetMemoryRequest.class);
      signalRegistry.add(MonitorGetRegisterInfoRequest.class);
      signalRegistry.add(MonitorGetRegistersRequest.class);
      signalRegistry.add(MonitorSetRegistersRequest.class);
      signalRegistry.add(MonitorAttachRequest.class);
      signalRegistry.add(MonitorDetachRequest.class);
      signalRegistry.add(MonitorInterceptScopeRequest.class);
      signalRegistry.add(MonitorResumeScopeRequest.class);
      signalRegistry.add(MonitorSetBreakpointRequest.class);
      signalRegistry.add(MonitorClearBreakpointRequest.class);
      signalRegistry.add(MonitorStepProcessRequest.class);
      signalRegistry.add(MonitorGetCPUReportsEnabledRequest.class);
      signalRegistry.add(MonitorSetCPUReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUReportsRequest.class);
      signalRegistry.add(MonitorGetCPUPriReportsEnabledRequest.class);
      signalRegistry.add(MonitorSetCPUPriReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUPriReportsRequest.class);
      signalRegistry.add(MonitorGetCPUProcessReportsEnabledRequest.class);
      signalRegistry.add(MonitorSetCPUProcessReportsEnabledRequest.class);
      signalRegistry.add(MonitorGetCPUProcessReportsRequest.class);

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
      features = new short[featureList.size()];
      for (int i = 0; i < features.length; i++)
      {
         features[i] = ((Short) featureList.get(i)).shortValue();
      }

      // Create the monitor clients map and the default debug server.
      clients = new HashMap<Integer, DebugServer>();
      defaultDebugServer = new DebugServer(address, port,
            address.getHostAddress() + ":" + port);
      defaultDebugServer.registerForCallbacks(this, 0);
      clients.put(new Integer(0), defaultDebugServer);
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
         log("I/O exception in debug server monitor when receiving signal " + sigNo, e);
      }
   }

   public void sendMonitorReply(Signal signal, int pid)
   {
      if (signal instanceof MonitorInterfaceReply)
      {
         // Store the target byte order.
         bigEndian = ((MonitorInterfaceReply) signal).endian;
      }

      if (isConnected(pid) || isConnectionlessSignal(signal.getSigNo()))
      {
         sendSignal(pid, signal);
      }
      else
      {
         log("Failed sending reply signal " + signal.getSigNo() +
             " from debug server monitor to client ID " + pid);
      }
   }

   protected void close()
   {
      for (DebugServer debugServer : clients.values())
      {
         debugServer.handleDisconnectRequest();
      }
      clients.clear();

      // XXX: Due to a bug in the FLEXlm Java client library on Solaris, we
      // cannot check in the FLEXlm license without hanging the whole JVM when
      // checking out the FLEXlm license again.
      //LicenseManager.getInstance().unregisterPlugin(PLUGIN_ID);
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();
      int client = signal.getSender();
      DebugServer debugServer = clients.get(new Integer(client));

      if (debugServer == null)
      {
         // Get the default debug server.
         debugServer = clients.get(new Integer(0));
      }

      switch (sigNo)
      {
         case MonitorInterfaceRequest.SIG_NO:
            // This is required since this signal may be
            // sent before a connection is established.
            if (!isConnected(client))
            {
               debugServer.registerForCallbacks(null, client);
            }
            debugServer.handleInterfaceRequest();
            break;
         case MonitorNameRequest.SIG_NO:
            // This is required since this signal may be
            // sent before a connection is established.
            if (!isConnected(client))
            {
               debugServer.registerForCallbacks(null, client);
            }
            debugServer.handleNameRequest();
            break;
         case MonitorConnectRequest.SIG_NO:
            DebugServer newDebugServer = new DebugServer(
               debugServer.getAddress(), debugServer.getPort(),
               debugServer.getAddress().getHostAddress() + ":" + debugServer.getPort());
            newDebugServer.registerForCallbacks(this, client);
            clients.put(new Integer(client), newDebugServer);
            newDebugServer.handleConnectRequest();
            break;
         case MonitorDisconnectRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleDisconnectRequest();
               clients.remove(new Integer(client));
            }
            break;
         case MonitorBlockingFlowControlReply.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleBlockingFlowControlReply(
                     (MonitorBlockingFlowControlReply) signal);
            }
            break;
         case MonitorGetBlockInfoRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetBlockInfoRequest(
                     (MonitorGetBlockInfoRequest) signal);
            }
            break;
         case MonitorGetProcessInfoRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetProcessInfoRequest(
                     (MonitorGetProcessInfoRequest) signal);
            }
            break;
         case MonitorGetEnvVarsRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetEnvVarsRequest(
                     (MonitorGetEnvVarsRequest) signal);
            }
            break;
         case MonitorGetStackUsageRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetStackUsageRequest(
                     (MonitorGetStackUsageRequest) signal);
            }
            break;
         case MonitorGetSignalQueueRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetSignalQueueRequest(
                     (MonitorGetSignalQueueRequest) signal);
            }
            break;
         case MonitorGetPoolInfoRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetPoolInfoRequest(
                     (MonitorGetPoolInfoRequest) signal);
            }
            break;
         case MonitorGetPoolSignalsRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetPoolSignalsRequest(
                     (MonitorGetPoolSignalsRequest) signal);
            }
            break;
         case MonitorStartScopeRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleStartScopeRequest(
                     (MonitorStartScopeRequest) signal);
            }
            break;
         case MonitorStopScopeRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleStopScopeRequest(
                     (MonitorStopScopeRequest) signal);
            }
            break;
         case MonitorKillScopeRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleKillScopeRequest(
                     (MonitorKillScopeRequest) signal);
            }
            break;
         case MonitorGetMemoryRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetMemoryRequest(
                     (MonitorGetMemoryRequest) signal);
            }
            break;
         /*
          * monitor_execution.sig
          */
         case MonitorSetMemoryRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetMemoryRequest(
                     (MonitorSetMemoryRequest) signal);
            }
            break;
         case MonitorGetRegisterInfoRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetRegisterInfoRequest(
                     (MonitorGetRegisterInfoRequest) signal);
            }
            break;
         case MonitorGetRegistersRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetRegistersRequest(
                     (MonitorGetRegistersRequest) signal);
            }
            break;
         case MonitorSetRegistersRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetRegistersRequest(
                     (MonitorSetRegistersRequest) signal);
            }
            break;
         case MonitorAttachRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleAttachRequest((MonitorAttachRequest) signal);
            }
            break;
         case MonitorDetachRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleDetachRequest((MonitorDetachRequest) signal);
            }
            break;
         case MonitorInterceptScopeRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleInterceptScopeRequest(
                     (MonitorInterceptScopeRequest) signal);
            }
            break;
         case MonitorResumeScopeRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleResumeScopeRequest(
                     (MonitorResumeScopeRequest) signal);
            }
            break;
         case MonitorSetBreakpointRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetBreakpointRequest(
                     (MonitorSetBreakpointRequest) signal);
            }
            break;
         case MonitorClearBreakpointRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleClearBreakpointRequest(
                     (MonitorClearBreakpointRequest) signal);
            }
            break;
         case MonitorStepProcessRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleStepProcessRequest(
                     (MonitorStepProcessRequest) signal);
            }
            break;
         /*
          * monitor_cpu.sig
          */
         case MonitorGetCPUReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUReportsEnabledRequest(
                     (MonitorGetCPUReportsEnabledRequest) signal);
            }
            break;
         case MonitorSetCPUReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetCPUReportsEnabledRequest(
                     (MonitorSetCPUReportsEnabledRequest) signal);
            }
            break;
         case MonitorGetCPUReportsRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUReportsRequest(
                     (MonitorGetCPUReportsRequest) signal);
            }
            break;
         case MonitorGetCPUPriReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUPriReportsEnabledRequest(
                     (MonitorGetCPUPriReportsEnabledRequest) signal);
            }
            break;
         case MonitorSetCPUPriReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetCPUPriReportsEnabledRequest(
                     (MonitorSetCPUPriReportsEnabledRequest) signal);
            }
            break;
         case MonitorGetCPUPriReportsRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUPriReportsRequest(
                     (MonitorGetCPUPriReportsRequest) signal);
            }
            break;
         case MonitorGetCPUProcessReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUProcessReportsEnabledRequest(
                     (MonitorGetCPUProcessReportsEnabledRequest) signal);
            }
            break;
         case MonitorSetCPUProcessReportsEnabledRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleSetCPUProcessReportsEnabledRequest(
                     (MonitorSetCPUProcessReportsEnabledRequest) signal);
            }
            break;
         case MonitorGetCPUProcessReportsRequest.SIG_NO:
            if (isConnected(client))
            {
               debugServer.handleGetCPUProcessReportsRequest(
                     (MonitorGetCPUProcessReportsRequest) signal);
            }
            break;
         default:
            log("Unknown signal received by debug server monitor: " + sigNo);
            break;
      }
   }

   private boolean isConnected(int client)
   {
      return clients.containsKey(new Integer(client));
   }

   private static boolean isConnectionlessSignal(int sigNo)
   {
      return (sigNo == MonitorInterfaceReply.SIG_NO) ||
         (sigNo == MonitorNameReply.SIG_NO);
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
         log("I/O exception in debug server monitor when byte order converting signal "
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
}
