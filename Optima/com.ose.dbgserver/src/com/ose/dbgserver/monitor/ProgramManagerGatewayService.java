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
import com.ose.dbgserver.DebugServer;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmInterfaceReply;
import com.ose.system.service.pm.signal.PmInterfaceRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.pm.signal.PmProgramsRequest;

public class ProgramManagerGatewayService extends AbstractGatewayService
{
   private static final String SERVICE_NAME = "ose_program_mgr";

   private static final int[] SUPPORTED_SIGNALS =
   {
      PmInterfaceRequest.SIG_NO,
      PmLoadModuleInstallHandlesRequest.SIG_NO,
      PmLoadModuleInfoRequest.SIG_NO,
      PmLoadModuleSectionInfoRequest.SIG_NO,
      PmProgramsRequest.SIG_NO,
      PmProgramInfoRequest.SIG_NO,
      PmGetProgramPidRequest.SIG_NO
   };

   private final SignalRegistry signalRegistry;
   private final DebugServer debugServer;
   private volatile boolean bigEndian;
   private volatile int client;

   public ProgramManagerGatewayService(InetAddress address, int port)
   {
      // Initialize the program manager signal registry.
      signalRegistry = new SignalRegistry();
      signalRegistry.add(PmInterfaceRequest.class);
      signalRegistry.add(PmLoadModuleInstallHandlesRequest.class);
      signalRegistry.add(PmLoadModuleInfoRequest.class);
      signalRegistry.add(PmLoadModuleSectionInfoRequest.class);
      signalRegistry.add(PmProgramsRequest.class);
      signalRegistry.add(PmProgramInfoRequest.class);
      signalRegistry.add(PmGetProgramPidRequest.class);

      // Create the debug server.
      debugServer = new DebugServer(address, port,
            address.getHostAddress() + ":" + port);
      debugServer.registerForCallbacks(this, 0);
      debugServer.connect();
      bigEndian = (debugServer.isConnected() ? debugServer.isBigEndian() : true);
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
         log("I/O exception in program manager when receiving signal " + sigNo, e);
      }
   }

   public void sendProgramManagerReply(Signal reply, int pid)
   {
      sendSignal(client, reply);
   }

   protected void close()
   {
      debugServer.handleDisconnectRequest();
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();
      client = signal.getSender();

      switch (sigNo)
      {
         case PmInterfaceRequest.SIG_NO:
            handleInterfaceRequest(signal);
            break;
         case PmLoadModuleInstallHandlesRequest.SIG_NO:
            debugServer.handleLoadModuleInstallHandlesRequest(
                  (PmLoadModuleInstallHandlesRequest) signal);
            break;
         case PmLoadModuleInfoRequest.SIG_NO:
            debugServer.handleLoadModuleInfoRequest(
                  (PmLoadModuleInfoRequest) signal);
            break;
         case PmLoadModuleSectionInfoRequest.SIG_NO:
            debugServer.handleLoadModuleSectionInfoRequest(
                  (PmLoadModuleSectionInfoRequest) signal);
            break;
         case PmProgramsRequest.SIG_NO:
            debugServer.handleProgramsRequest((PmProgramsRequest) signal);
            break;
         case PmProgramInfoRequest.SIG_NO:
            debugServer.handleProgramInfoRequest((PmProgramInfoRequest) signal);
            break;
         case PmGetProgramPidRequest.SIG_NO:
            debugServer.handleGetProgramPidRequest(
                  (PmGetProgramPidRequest) signal);
            break;
         default:
            log("Unknown signal received by debug server program manager: " + sigNo);
            break;
      }
   }

   private void handleInterfaceRequest(Signal sig)
   {
      PmInterfaceReply reply = new PmInterfaceReply();
      reply.status = 0;
      reply.whatStr = "";
      reply.biosHandle = 0;
      reply.sigs = SUPPORTED_SIGNALS;
      bigEndian = debugServer.isBigEndian();
      sendSignal(sig.getSender(), reply);
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
         log("I/O exception in program manager when byte order converting signal "
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