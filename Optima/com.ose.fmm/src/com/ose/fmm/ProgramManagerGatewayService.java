/* COPYRIGHT-ENEA-SRC-R1 *
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.server.AbstractGatewayService;
import com.ose.gateway.server.RawSignal;
import com.ose.system.service.pm.LoadModuleSectionInfo;
import com.ose.system.service.pm.ProgramManagerStatus;
import com.ose.system.service.pm.signal.PmGetProgramPidReply;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmInterfaceRequest;
import com.ose.system.service.pm.signal.PmInterfaceReply;
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

public class ProgramManagerGatewayService extends AbstractGatewayService
{
   private static final String SERVICE_NAME = "ose_program_mgr";

   private final Fmi fmi;
   private final boolean bigEndian;
   private final SignalRegistry signalRegistry;
   private final ITargetProxy targetProxy;
   private final List supportedSignals;

   public ProgramManagerGatewayService(Fmi fmi, ITargetProxy targetProxy)
   {
      this.fmi = fmi;
      this.targetProxy = targetProxy;

      bigEndian = targetProxy.isBigEndian();
      
      // Initialize the program manager signal registry.
      signalRegistry = new SignalRegistry();
      signalRegistry.add(PmInterfaceRequest.class);
      signalRegistry.add(PmLoadModuleInstallHandlesRequest.class);
      signalRegistry.add(PmLoadModuleInfoRequest.class);
      signalRegistry.add(PmLoadModuleSectionInfoRequest.class);
      signalRegistry.add(PmProgramsRequest.class);
      signalRegistry.add(PmProgramInfoRequest.class);
      signalRegistry.add(PmGetProgramPidRequest.class);
      
      supportedSignals = new ArrayList();
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
   }

   private void handleRequest(Signal signal)
   {
      int sigNo = signal.getSigNo();

      if (targetProxy.isSuspended() && fmi.isInitialized())
      {
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
            case PmLoadModuleSectionInfoRequest.SIG_NO:
               if (isSupported(sigNo))
               {
                  handleLoadModuleSectionInfoRequest(signal);
               }
               break;
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
   }

   private void handleInterfaceRequest(Signal sig)
   {
      PmInterfaceReply reply = new PmInterfaceReply();
      
      reply.status = ProgramManagerStatus.PM_SUCCESS;
      reply.whatStr = new String("ProgramManager");
      reply.biosHandle = 0;
      
      if (supportedSignals.isEmpty())
      {
         supportedSignals.add(new Integer(PmInterfaceRequest.SIG_NO));
         if (fmi.ose_host_fmi_is_supported(Fmi.HOST_FMI_FEATURE_LOAD_MODULE) != 0)
         {
            supportedSignals.add(new Integer(PmLoadModuleInstallHandlesRequest.SIG_NO));
            supportedSignals.add(new Integer(PmLoadModuleInfoRequest.SIG_NO));
            supportedSignals.add(new Integer(PmLoadModuleSectionInfoRequest.SIG_NO));
            supportedSignals.add(new Integer(PmProgramsRequest.SIG_NO));
            supportedSignals.add(new Integer(PmProgramInfoRequest.SIG_NO));
            supportedSignals.add(new Integer(PmGetProgramPidRequest.SIG_NO));
         }
      }
      
      reply.sigs = new int[supportedSignals.size()];
      
      for (int i = 0; i < supportedSignals.size(); i++)
      {
         reply.sigs[i] = ((Integer) supportedSignals.get(i)).intValue();
      }
      
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleInstallHandlesRequest(Signal sig)
   {
      int status;
      PmLoadModuleInstallHandlesReply reply =
         new PmLoadModuleInstallHandlesReply();
      List modules = new ArrayList();
      
      Fmi.PfmiCursor cursor = new Fmi.PfmiCursor();
      Fmi.PfmiLoadModuleInfo info = new Fmi.PfmiLoadModuleInfo();
      
      status = fmi.ose_pfmi_get_first_load_module(cursor, info);
      
      while (status == Fmi.EFMI_OK)
      {
         modules.add(info);
         info = new Fmi.PfmiLoadModuleInfo();
         status = fmi.ose_pfmi_get_next_load_module(cursor, info);
      }
      
      reply.status = pfmiToPmStatus(status);
      
      reply.install_handles = new String[modules.size()];
      for (int i = 0; i < modules.size(); i++)
      {
         info = (Fmi.PfmiLoadModuleInfo) modules.get(i);
         reply.install_handles[i] = info.install_handle;
      }
      
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleInfoRequest(Signal sig)
   {
      int status;
      PmLoadModuleInfoRequest request = (PmLoadModuleInfoRequest) sig;
      PmLoadModuleInfoReply reply = new PmLoadModuleInfoReply();
      Fmi.PfmiLoadModuleInfo info = new Fmi.PfmiLoadModuleInfo();
      
      status = fmi.ose_pfmi_get_load_module_info(request.install_handle, info);
      
      reply.status = pfmiToPmStatus(status);
      
      if (status == Fmi.PFMI_OK)
      {
         reply.install_handle = info.install_handle;
         reply.options = info.options;
         reply.entrypoint = info.entrypoint;
         reply.text_base = info.text_base;
         reply.text_size = info.text_size;
         reply.data_base = info.data_base;
         reply.data_size = info.data_size;
         reply.no_of_sections = info.no_of_sections;
         reply.no_of_instances = info.no_of_instances;
         reply.file_format = info.file_format;
         reply.file_name = info.file_name;
      }
      else
      {
         reply.install_handle = request.install_handle;
         reply.file_format = "";
         reply.file_name = "";
      }
      
      sendSignal(sig.getSender(), reply);
   }

   private void handleLoadModuleSectionInfoRequest(Signal sig)
   {
      int status = Fmi.PFMI_CALL_INVALID;
      PmLoadModuleSectionInfoRequest request =
         (PmLoadModuleSectionInfoRequest) sig;
      PmLoadModuleSectionInfoReply reply = new PmLoadModuleSectionInfoReply();
      Fmi.PfmiLoadModuleSectionInfo info = new Fmi.PfmiLoadModuleSectionInfo();
      List sections = new ArrayList();
      
      for (int s = request.section_interval_start; s <= request.section_interval_end; s++)
      {
         status = fmi.ose_pfmi_get_load_module_section_info(request.install_handle,
                                                            s, info);
   
         
         reply.status = status;
         
         if (status == Fmi.PFMI_OK)
         {
            LoadModuleSectionInfo replyInfo = new LoadModuleSectionInfo();
            replyInfo.section_base = info.section_base;
            replyInfo.section_size = info.section_size;
            replyInfo.section_attr = info.section_attr;
            replyInfo.section_options = info.section_options;
            replyInfo.section_name = info.section_name;
            
            sections.add(replyInfo);
         }
         else
         {
            break;
         }
      }
      
      reply.status = pfmiToPmStatus(status);
      reply.install_handle = request.install_handle;
      reply.section_interval_start = request.section_interval_start;
      reply.section_interval_end = request.section_interval_start;
      if (!sections.isEmpty())
      {
         reply.section_interval_end += sections.size() - 1;
      }
      
      sendSignal(sig.getSender(), reply);
   }

   private void handleProgramsRequest(Signal sig)
   {
      int status;
      PmProgramsReply reply = new PmProgramsReply();
      List programs = new ArrayList();
      
      Fmi.PfmiCursor cursor = new Fmi.PfmiCursor();
      Fmi.PfmiProgramInfo info = new Fmi.PfmiProgramInfo();
      
      status = fmi.ose_pfmi_get_first_program(cursor, info);
      
      while (status == Fmi.EFMI_OK)
      {
         programs.add(info);
         info = new Fmi.PfmiProgramInfo();
         status = fmi.ose_pfmi_get_next_program(cursor, info);
      }
      
      reply.status = pfmiToPmStatus(status);
      
      reply.progpids = new int[programs.size()];
      for (int i = 0; i < programs.size(); i++)
      {
         info = (Fmi.PfmiProgramInfo) programs.get(i);
         reply.progpids[i] = info.progpid;
      }
      
      sendSignal(sig.getSender(), reply);      
   }

   private void handleProgramInfoRequest(Signal sig)
   {
      int status;
      PmProgramInfoRequest request = (PmProgramInfoRequest) sig;
      PmProgramInfoReply reply = new PmProgramInfoReply();
      Fmi.PfmiProgramInfo info = new Fmi.PfmiProgramInfo();
      
      status = fmi.ose_pfmi_get_program_info(request.progpid, info);
      
      reply.status = pfmiToPmStatus(status);
      
      if (status == Fmi.PFMI_OK)
      {
         reply.install_handle = info.install_handle;
         reply.progpid = info.progpid;
         reply.domain = info.domain;
         reply.main_block = info.main_block;
         reply.main_process = info.main_process;
         reply.pool_base = info.stk_pool_base;
         reply.pool_size = info.stk_pool_size;
         reply.uid = info.uid;
         reply.state = info.state;
         reply.segpid = info.segpid;
         reply.stk_poolid = info.stk_poolid;
         reply.sig_poolid = info.sig_poolid;
         reply.sig_pool_base = info.sig_pool_base;
         reply.sig_pool_size = info.sig_pool_size;
         reply.heap = info.heap;
      }
      
      sendSignal(sig.getSender(), reply);
   }

   private void handleGetProgramPidRequest(Signal sig)
   {
      int status;
      Fmi.EfmiInteger progpid = new Fmi.EfmiInteger(0);
      PmGetProgramPidRequest request = (PmGetProgramPidRequest) sig;
      PmGetProgramPidReply reply = new PmGetProgramPidReply();
      
      status = fmi.ose_pfmi_get_progpid(request.pid, progpid);
      
      reply.status = status;
      reply.pid = request.pid;
      
      if (status == Fmi.PFMI_OK)
      {
         reply.progpid = progpid.value;
      }
      
      sendSignal(sig.getSender(), reply);
   }
   
   static int pfmiToPmStatus(int pfmiStatus)
   {
      switch (pfmiStatus)
      {
         case Fmi.PFMI_OK:
         case Fmi.PFMI_END_OF_LIST:
            return ProgramManagerStatus.PM_SUCCESS;
         case Fmi.PFMI_UNAVAILABLE:
            // There is no natural mapping for this error code. 
            return ProgramManagerStatus.PM_EFEATURE_NOT_SUPPORTED;
         case Fmi.PFMI_RESULT_TRUNCATED:
            // There is no natural mapping for this error code. 
            return ProgramManagerStatus.PM_EOPTION_UNKNOWN;
         case Fmi.PFMI_DATA_LOCKED:
            // There is no natural mapping for this error code. 
            return ProgramManagerStatus.PM_EOPTION_UNKNOWN;
         case Fmi.PFMI_DATA_CORRUPTED:
            // There is no natural mapping for this error code. 
            return ProgramManagerStatus.PM_EOPTION_UNKNOWN;
         case Fmi.PFMI_EPROGPID:
            return ProgramManagerStatus.PM_EPROGPID;
         case Fmi.PFMI_EINSTHND:
            return ProgramManagerStatus.PM_EINSTALL_HANDLE;
         case Fmi.PFMI_CALL_INVALID:
            return ProgramManagerStatus.PM_EOPTION_UNKNOWN;
         default:
            // There is no natural choice for a blanket error code.
            return ProgramManagerStatus.PM_EFEATURE_NOT_SUPPORTED;
      }
   }   

   private boolean isSupported(int sigNo)
   {
      return supportedSignals.contains(new Integer(sigNo));
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
