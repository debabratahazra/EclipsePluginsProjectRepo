/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.pmd.editor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.system.service.monitor.signal.*;
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
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesRequest;

/**
 * Block formatter for signal blocks that outputs the signals by calling their
 * associated toString() method.
 */
public class SignalBlockFormatter implements IBlockFormatter
{
   private static final SignalRegistry registry = new SignalRegistry();

   static
   {
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
      registry.add(MonitorGetPoolInfoEndmark.class);
      registry.add(MonitorGetPoolSignalsRequest.class);
      registry.add(MonitorGetPoolSignalsReply.class);
      registry.add(MonitorGetPoolSignalsEndmark.class);
      registry.add(MonitorGetHeapInfoRequest.class);
      registry.add(MonitorGetHeapInfoReply.class);
      registry.add(MonitorGetHeapInfoEndmark.class);
      registry.add(MonitorGetHeapFragmentInfoRequest.class);
      registry.add(MonitorGetHeapFragmentInfoReply.class);
      registry.add(MonitorGetHeapFragmentInfoEndmark.class);
      registry.add(MonitorGetHeapBuffersRequest.class);
      registry.add(MonitorGetHeapBuffersReply.class);
      registry.add(MonitorGetHeapBuffersEndmark.class);
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
      registry.add(MonitorGetSupportedCounterTypesRequest.class);
      registry.add(MonitorGetSupportedCounterTypesReply.class);
      registry.add(MonitorGetSupportedCounterTypesEndmark.class);
      registry.add(MonitorGetCounterTypeEnabledRequest.class);
      registry.add(MonitorGetCounterTypeEnabledReply.class);
      registry.add(MonitorSetCounterTypeEnabledRequest.class);
      registry.add(MonitorSetCounterTypeEnabledReply.class);
      registry.add(MonitorGetCounterReportsNotifyEnabledRequest.class);
      registry.add(MonitorGetCounterReportsNotifyEnabledReply.class);
      registry.add(MonitorSetCounterReportsNotifyEnabledRequest.class);
      registry.add(MonitorSetCounterReportsNotifyEnabledReply.class);
      registry.add(MonitorCounterReportsNotify.class);
      registry.add(MonitorCounterReportsLossNotify.class);
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
      registry.add(MonitorGetHeapFragmentInfo64Reply.class);
      registry.add(MonitorGetHeapInfo64Reply.class);
      registry.add(MonitorGetMemory64Reply.class);
      registry.add(MonitorGetMemory64Request.class);
      registry.add(MonitorGetPoolInfo64Reply.class);
      registry.add(MonitorGetHeapFragmentInfo64Request.class);
      registry.add(MonitorGetHeapInfo64Request.class);
      registry.add(MonitorGetPoolInfo64Request.class);
      registry.add(MonitorGetConnectedClientsRequest.class);
      registry.add(MonitorGetConnectedClientsReply.class);
      registry.add(MonitorGetConnectedClientsEndmark.class);
      registry.add(MonitorTracepointGetTracepointsRequest.class);
      registry.add(MonitorTracepointGetTracepointsReply.class);
      registry.add(MonitorTracepointGetTracepointsEndmark.class);
      registry.add(MonitorTracepointGetActionsRequest.class);
      registry.add(MonitorTracepointGetActionsReply.class);
      registry.add(MonitorTracepointGetActionsEndmark.class);
      registry.add(MonitorTracepointGetAnnotationsRequest.class);
      registry.add(MonitorTracepointGetAnnotationsReply.class);
      registry.add(MonitorTracepointGetStatusRequest.class);
      registry.add(MonitorTracepointGetStatusReply.class);
      registry.add(MonitorTracepointGetVariablesRequest.class);
      registry.add(MonitorTracepointGetVariablesReply.class);
      registry.add(MonitorTracepointGetVariablesEndmark.class);
      registry.add(MonitorTracepointGetTracebufferRequest.class);
      registry.add(MonitorTracepointGetTracebufferReply.class);
      registry.add(MonitorTracepointGetTracebufferEndmark.class);
      registry.add(PmInterfaceRequest.class);
      registry.add(PmInterfaceReply.class);
      registry.add(PmLoadModuleInstallHandlesRequest.class);
      registry.add(PmLoadModuleInstallHandlesReply.class);
      registry.add(PmLoadModuleInfoRequest.class);
      registry.add(PmLoadModuleInfoRequest_64.class);
      registry.add(PmLoadModuleInfoReply.class);
      registry.add(PmLoadModuleInfoReply_64.class);
      registry.add(PmLoadModuleSectionInfoRequest.class);
      registry.add(PmLoadModuleSectionInfoRequest_64.class);
      registry.add(PmLoadModuleSectionInfoReply.class);
      registry.add(PmLoadModuleSectionInfoReply_64.class);
      registry.add(PmProgramsRequest.class);
      registry.add(PmProgramsReply.class);
      registry.add(PmProgramInfoRequest.class);
      registry.add(PmProgramInfoRequest_64.class);
      registry.add(PmProgramInfoReply.class);
      registry.add(PmProgramInfoReply_64.class);
   }

   /**
    * Return the signal name for the specified signal number.
    *
    * @param sigNo  a signal number.
    * @return the signal name for the specified signal number or null if
    * unknown.
    */
   public static String getSignalName(int sigNo)
   {
      String signalName = null;
      Class signalClass;

      signalClass = registry.getSignalClass(sigNo);
      if (signalClass != null)
      {
         int lastDot;

         signalName = signalClass.getName();
         lastDot = signalName.lastIndexOf('.');
         if ((lastDot > 0) && (lastDot < (signalName.length() - 1)))
         {
            signalName = signalName.substring(lastDot + 1);
         }
      }

      return signalName;
   }

   /*
    * @see com.ose.pmd.editor.IBlockFormatter#format(java.io.InputStream,
    * java.io.PrintWriter, long, long, boolean)
    */
   public void format(InputStream in,
                      PrintWriter out,
                      long address,
                      long size,
                      boolean bigEndian)
      throws IOException
   {
      SignalInputStream sin;
      Signal signal;

      sin = new SignalInputStream(in, bigEndian);
      do
      {
         signal = readSignal(sin, bigEndian);
         out.println(signal.toString());
      }
      while (sin.getReadPosition() < size);
   }

   private static Signal readSignal(SignalInputStream in, boolean bigEndian)
      throws IOException
   {
      int sigSize;
      int sigNo;
      byte[] sigData;

      sigSize = in.readS32();
      if (sigSize < 4)
      {
         throw new IOException("Invalid signal size in dump file");
      }
      sigNo = in.readS32();
      sigData = new byte[sigSize - 4];
      in.readFully(sigData);
      in.align(4);
      return createSignal(0, 0, sigSize, sigNo, sigData, bigEndian);
   }

   private static Signal createSignal(int sender,
                                      int addressee,
                                      int sigSize,
                                      int sigNo,
                                      byte[] sigData,
                                      boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if ((sigSize == 0) || (sigData == null))
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
}
