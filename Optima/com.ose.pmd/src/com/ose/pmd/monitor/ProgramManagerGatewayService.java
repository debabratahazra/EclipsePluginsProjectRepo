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

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.system.service.pm.ProgramManagerStatus;
import com.ose.system.service.pm.signal.PmGetProgramPidReply;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmInterfaceRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply_64;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest_64;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply_64;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest_64;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoReply_64;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.pm.signal.PmProgramInfoRequest_64;
import com.ose.system.service.pm.signal.PmProgramsRequest;

public class ProgramManagerGatewayService extends AbstractGatewayService
{
   private static final String SERVICE_NAME = "ose_program_mgr";

   private final Dump dump;
   private final boolean bigEndian;
   private final SignalRegistry signalRegistry;

   public ProgramManagerGatewayService(Dump dump)
   {
      if (dump == null)
      {
         throw new IllegalArgumentException();
      }

      // Store the dump and its byte order.
      this.dump = dump;
      bigEndian = dump.isBigEndian();

      // Initialize the program manager signal registry.
      signalRegistry = new SignalRegistry();
      signalRegistry.add(PmInterfaceRequest.class);
      signalRegistry.add(PmLoadModuleInstallHandlesRequest.class);
      signalRegistry.add(PmLoadModuleInfoRequest.class);
      signalRegistry.add(PmLoadModuleInfoRequest_64.class);
      signalRegistry.add(PmLoadModuleSectionInfoRequest.class);
      signalRegistry.add(PmLoadModuleSectionInfoRequest_64.class);
      signalRegistry.add(PmProgramsRequest.class);
      signalRegistry.add(PmProgramInfoRequest.class);
      signalRegistry.add(PmProgramInfoRequest_64.class);
      signalRegistry.add(PmGetProgramPidRequest.class);
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

   protected void close()
   {
      dump.close();
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();

      switch (sigNo)
      {
         case PmInterfaceRequest.SIG_NO:
            handleInterfaceRequest(signal);
            break;
         case PmLoadModuleInstallHandlesRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleLoadModuleInstallHandlesRequest(signal);
            }
            break;
         case PmLoadModuleInfoRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleLoadModuleInfoRequest(signal);
            }
            break;
         case PmLoadModuleInfoRequest_64.SIG_NO:
            if (isSupported(sigNo))
            {
               handleLoadModuleInfoRequest_64(signal);
            }
            break;
         case PmLoadModuleSectionInfoRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleLoadModuleSectionInfoRequest(signal);
            }
            break;
         case PmLoadModuleSectionInfoRequest_64.SIG_NO:
        	if (isSupported(sigNo))
        	{
        	   handleLoadModuleSectionInfoRequest_64(signal);
        	}
         case PmProgramsRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleProgramsRequest(signal);
            }
            break;
         case PmProgramInfoRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleProgramInfoRequest(signal);
            }
            break;
         case PmProgramInfoRequest_64.SIG_NO:
        	if (isSupported(sigNo))
        	{
        	   handleProgramInfoRequest_64(signal);
        	}
         case PmGetProgramPidRequest.SIG_NO:
            if (isSupported(sigNo))
            {
               handleGetProgramPidRequest(signal);
            }
            break;
         default:
            log("Unknown signal received by program manager: " + signal.getSigNo());
            break;
      }
   }

   private void handleInterfaceRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getPmInterfaceReply());
   }

   private void handleLoadModuleInstallHandlesRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getPmLoadModuleInstallHandlesReply());
   }

   private void handleLoadModuleInfoRequest(Signal sig)
   {
      PmLoadModuleInfoRequest request;
      PmLoadModuleInfoReply reply;

      request = (PmLoadModuleInfoRequest) sig;
      reply = dump.getLoadModule(request.install_handle);
      if (reply == null)
      {
         reply = new PmLoadModuleInfoReply();
         reply.status = ProgramManagerStatus.PM_EINSTALL_HANDLE;
         reply.install_handle = request.install_handle;
         reply.file_format = "";
         reply.file_name = "";
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleInfoRequest_64(Signal sig)
   {
      PmLoadModuleInfoRequest_64 request;
      PmLoadModuleInfoReply_64 reply;

      request = (PmLoadModuleInfoRequest_64) sig;
      reply = dump.getLoadModule_64(request.install_handle);
      if (reply == null)
      {
         reply = new PmLoadModuleInfoReply_64();
         reply.status = ProgramManagerStatus.PM_EINSTALL_HANDLE;
         reply.install_handle = request.install_handle;
         reply.file_format = "";
         reply.file_name = "";
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleSectionInfoRequest(Signal sig)
   {
      PmLoadModuleSectionInfoRequest request;
      PmLoadModuleSectionInfoReply reply;

      request = (PmLoadModuleSectionInfoRequest) sig;
      reply = dump.getLoadModuleSection(request.install_handle);
      if (reply == null)
      {
         reply = new PmLoadModuleSectionInfoReply();
         reply.status = ProgramManagerStatus.PM_EINSTALL_HANDLE;
         reply.install_handle = request.install_handle;
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleSectionInfoRequest_64(Signal sig)
   {
      PmLoadModuleSectionInfoRequest_64 request;
      PmLoadModuleSectionInfoReply_64 reply;

      request = (PmLoadModuleSectionInfoRequest_64) sig;
      reply = dump.getLoadModuleSection_64(request.install_handle);
      if (reply == null)
      {
         reply = new PmLoadModuleSectionInfoReply_64();
         reply.status = ProgramManagerStatus.PM_EINSTALL_HANDLE;
         reply.install_handle = request.install_handle;
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleProgramsRequest(Signal sig)
   {
      sendSignal(sig.getSender(), dump.getPmProgramsReply());
   }

   private void handleProgramInfoRequest(Signal sig)
   {
      PmProgramInfoRequest request;
      PmProgramInfoReply reply;

      request = (PmProgramInfoRequest) sig;
      reply = dump.getProgram(request.progpid);
      if (reply == null)
      {
         reply = new PmProgramInfoReply();
         reply.status = ProgramManagerStatus.PM_EPROGPID;
         reply.install_handle = "";
         reply.progpid = request.progpid;
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleProgramInfoRequest_64(Signal sig)
   {
      PmProgramInfoRequest_64 request;
      PmProgramInfoReply_64 reply;

      request = (PmProgramInfoRequest_64) sig;
      reply = dump.getProgram_64(request.progpid);
      if (reply == null)
      {
         reply = new PmProgramInfoReply_64();
         reply.status = ProgramManagerStatus.PM_EPROGPID;
         reply.install_handle = "";
         reply.progpid = request.progpid;
      }
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetProgramPidRequest(Signal sig)
   {
      PmGetProgramPidRequest request;
      PmGetProgramPidReply reply;
      PmProgramInfoReply program;

      request = (PmGetProgramPidRequest) sig;
      reply = new PmGetProgramPidReply();
      program = dump.getProgramByPid(request.pid);
      if (program != null)
      {
         reply.status = ProgramManagerStatus.PM_SUCCESS;
         reply.pid = request.pid;
         reply.progpid = program.progpid;
      }
      else
      {
         reply.status = ProgramManagerStatus.PM_EPROGPID;
         reply.pid = request.pid;
         reply.progpid = 0;
      }
      sendSignal(sig.getSender(), reply);
   }

   private boolean isSupported(int sigNo)
   {
      int[] sigs = dump.getPmInterfaceReply().sigs;

      for (int i = 0; i < sigs.length; i++)
      {
         if (sigs[i] == sigNo)
         {
            return true;
         }
      }

      return false;
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