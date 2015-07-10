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

package com.ose.system.service.pm;

import java.io.InterruptedIOException;
import java.io.IOException;
import com.ose.gateway.Gateway;
import com.ose.gateway.GatewayException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.SignalSelect;
import com.ose.system.service.pm.signal.PmAttachSignal;
import com.ose.system.service.pm.signal.PmCreateProgramReply;
import com.ose.system.service.pm.signal.PmCreateProgramRequest;
import com.ose.system.service.pm.signal.PmGetProgramPidReply;
import com.ose.system.service.pm.signal.PmGetProgramPidRequest;
import com.ose.system.service.pm.signal.PmHuntSignal;
import com.ose.system.service.pm.signal.PmInstallLoadModuleReply;
import com.ose.system.service.pm.signal.PmInstallLoadModuleRequest;
import com.ose.system.service.pm.signal.PmInterfaceReply;
import com.ose.system.service.pm.signal.PmInterfaceRequest;
import com.ose.system.service.pm.signal.PmKillProgramReply;
import com.ose.system.service.pm.signal.PmKillProgramRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesReply;
import com.ose.system.service.pm.signal.PmLoadModuleInstallHandlesRequest;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.pm.signal.PmStartProgramReply;
import com.ose.system.service.pm.signal.PmStartProgramRequest;
import com.ose.system.service.pm.signal.PmUninstallLoadModuleReply;
import com.ose.system.service.pm.signal.PmUninstallLoadModuleRequest;

public class ProgramManager
{
   /* Default program region types. */
   public static final int PM_TEXT_REGION_TYPE = 0;
   public static final int PM_DATA_REGION_TYPE = 1;
   public static final int PM_POOL_REGION_TYPE = 2;

   /* Program execution modes. */
   public static final int PM_EXEC_MODE_USER       = 0;
   public static final int PM_EXEC_MODE_SUPERVISOR = 1;

   /* Program states. */
   public static final int PM_PROGRAM_CREATED = 0;
   public static final int PM_PROGRAM_STARTED = 1;

   /* Section options. */
   public static final int PM_SECTION_NO_LOAD = 0;
   public static final int PM_SECTION_LOAD    = 1;

   /* Installation options. */
   public static final int PM_LOAD_MODULE_PERSISTENT = 1;
   public static final int PM_LOAD_MODULE_ABSOLUTE   = 2;

   /* Domain options. */
   public static final int PM_NEW_DOMAIN = 0xFFFFFFFF;

   /* Extend program memory options. */
   public static final int PM_SPECIFY_BASE  = 1;
   public static final int PM_SPECIFY_ALIGN = 2;

   /* Name of the program manager process. */
   public static final String PM_PGM_NAME = "ose_program_mgr";

   /* Name of this program manager client. */
   private static final String PM_PGM_CLIENT_NAME = "ose_tools_pm_client";

   /* The program type name of core. */
   public static final String PM_CORE_PROGRAM_TYPE_NAME = "SYSTEM";

   /* ELF file format. */
   public static final String PM_ELF_FILE_FORMAT = "ELF";

   /* LIP file format. */
   public static final String PM_LIP_FILE_FORMAT = "LIP";

   /* Hunt/interface timeout in milliseconds. */
   private static final int TIMEOUT = 10000;

   /* Program manager creation state. */
   private final ProgramManagerListener listener;
   private final Gateway senderGateway;
   private final Gateway receiverGateway;
   private final SignalRegistry registry;
   private final String pmPath;

   /* Program manager connection state. */
   private int pmPid;
   private int receiverPid;
   private int attref;
   private ReceiverThread receiverThread;
   private volatile boolean connected;

   /* Program manager capabilities. */
   private boolean hasInstallLoadModuleRequest;
   private boolean hasUninstallLoadModuleRequest;
   private boolean hasLoadModuleInstallHandlesRequest;
   private boolean hasLoadModuleInfoRequest;
   private boolean hasCreateProgramRequest;
   private boolean hasStartProgramRequest;
   private boolean hasKillProgramRequest;
   private boolean hasProgramInfoRequest;
   private boolean hasGetProgramPidRequest;

   public ProgramManager(ProgramManagerListener listener,
                         Gateway senderGateway,
                         String huntPath)
      throws IOException, GatewayException
   {
      SignalSelect wanted;
      Signal signal;
      PmInterfaceReply interfaceReply;

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

      // Create the program manager receiver gateway.
      receiverGateway = new Gateway(senderGateway.getName(),
            senderGateway.getAddress(), senderGateway.getPort());

      // Initialize the program manager signal registry.
      registry = new SignalRegistry();
      registry.add(PmAttachSignal.class);
      registry.add(PmCreateProgramReply.class);
      registry.add(PmCreateProgramRequest.class);
      registry.add(PmGetProgramPidReply.class);
      registry.add(PmGetProgramPidRequest.class);
      registry.add(PmHuntSignal.class);
      registry.add(PmInstallLoadModuleReply.class);
      registry.add(PmInstallLoadModuleRequest.class);
      registry.add(PmInterfaceReply.class);
      registry.add(PmInterfaceRequest.class);
      registry.add(PmKillProgramReply.class);
      registry.add(PmKillProgramRequest.class);
      registry.add(PmLoadModuleInfoReply.class);
      registry.add(PmLoadModuleInfoRequest.class);
      registry.add(PmLoadModuleInstallHandlesReply.class);
      registry.add(PmLoadModuleInstallHandlesRequest.class);
      registry.add(PmProgramInfoReply.class);
      registry.add(PmProgramInfoRequest.class);
      registry.add(PmStartProgramReply.class);
      registry.add(PmStartProgramRequest.class);
      registry.add(PmUninstallLoadModuleReply.class);
      registry.add(PmUninstallLoadModuleRequest.class);

      // Hunt for the program manager.
      pmPath = (huntPath.equals("") ?
                PM_PGM_NAME :
                huntPath.endsWith("/") ?
                huntPath + PM_PGM_NAME :
                huntPath + "/" + PM_PGM_NAME);
      wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                new int[] {PmHuntSignal.SIG_NO});
      senderGateway.hunt(pmPath, new PmHuntSignal());
      signal = senderGateway.receive(registry, wanted, TIMEOUT);
      if (signal != null)
      {
         pmPid = signal.getSender();
      }
      else
      {
         throw new IOException("Timed out hunting for program manager.");
      }

      // Get the program manager interface information.
      wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                new int[] {PmInterfaceReply.SIG_NO});
      senderGateway.send(pmPid, new PmInterfaceRequest());
      interfaceReply =
         (PmInterfaceReply) senderGateway.receive(registry, wanted, TIMEOUT);
      if (interfaceReply == null)
      {
         throw new IOException(
               "Timed out receiving program manager interface information.");
      }

      // Determine program manager capabilities.
      determineCapabilities(interfaceReply.sigs);
   }

   private void determineCapabilities(int[] sigs)
   {
      for (int i = 0; i < sigs.length; i++)
      {
         switch (sigs[i])
         {
            case PmInstallLoadModuleRequest.SIG_NO:
               hasInstallLoadModuleRequest = true;
               break;
            case PmUninstallLoadModuleRequest.SIG_NO:
               hasUninstallLoadModuleRequest = true;
               break;
            case PmLoadModuleInstallHandlesRequest.SIG_NO:
               hasLoadModuleInstallHandlesRequest = true;
               break;
            case PmLoadModuleInfoRequest.SIG_NO:
               hasLoadModuleInfoRequest = true;
               break;
            case PmCreateProgramRequest.SIG_NO:
               hasCreateProgramRequest = true;
               break;
            case PmStartProgramRequest.SIG_NO:
               hasStartProgramRequest = true;
               break;
            case PmKillProgramRequest.SIG_NO:
               hasKillProgramRequest = true;
               break;
            case PmProgramInfoRequest.SIG_NO:
               hasProgramInfoRequest = true;
               break;
            case PmGetProgramPidRequest.SIG_NO:
               hasGetProgramPidRequest = true;
               break;
         }
      }
   }

   private static void checkSupport(boolean capability)
      throws UnsupportedOperationException
   {
      if (!capability)
      {
         throw new UnsupportedOperationException(
               "Unsupported program manager operation.");
      }
   }

   private void checkState() throws IOException
   {
      if (!connected)
      {
         throw new IOException("Program manager not connected.");
      }
   }

   public void connect() throws IOException, GatewayException
   {
      if (!connected)
      {
         // Connect the program manager receiver gateway.
         receiverGateway.connect(PM_PGM_CLIENT_NAME);
         receiverGateway.setBigEndian(senderGateway.isBigEndian());
         receiverPid = receiverGateway.getClientPid();

         try
         {
            SignalSelect wanted;
            Signal signal;

            // Hunt for the program manager.
            wanted = new SignalSelect(SignalSelect.MODE_INCLUDE_SIGNALS,
                                      new int[] {PmHuntSignal.SIG_NO});
            receiverGateway.hunt(pmPath, new PmHuntSignal());
            signal = receiverGateway.receive(registry, wanted, TIMEOUT);
            if (signal != null)
            {
               pmPid = signal.getSender();
            }
            else
            {
               throw new IOException("Timed out hunting for program manager.");
            }

            // Attach to the program manager.
            attref = receiverGateway.attach(pmPid, new PmAttachSignal());
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

         // Start the program manager receiver thread.
         receiverThread = new ReceiverThread();
         receiverThread.start();

         // Connection done.
         connected = true;
      }
   }

   public void disconnect()
   {
      if (connected)
      {
         // Stop the program manager receiver thread.
         receiverThread.shutdown();
         receiverThread = null;

         // Disconnection done.
         connected = false;
      }
   }

   public boolean isConnected()
   {
      return connected;
   }

   public boolean hasInstallLoadModuleRequest()
   {
      return hasInstallLoadModuleRequest;
   }

   public boolean hasUninstallLoadModuleRequest()
   {
      return hasUninstallLoadModuleRequest;
   }

   public boolean hasLoadModuleInstallHandlesRequest()
   {
      return hasLoadModuleInstallHandlesRequest;
   }

   public boolean hasLoadModuleInfoRequest()
   {
      return hasLoadModuleInfoRequest;
   }

   public boolean hasCreateProgramRequest()
   {
      return hasCreateProgramRequest;
   }

   public boolean hasStartProgramRequest()
   {
      return hasStartProgramRequest;
   }

   public boolean hasKillProgramRequest()
   {
      return hasKillProgramRequest;
   }

   public boolean hasProgramInfoRequest()
   {
      return hasProgramInfoRequest;
   }

   public boolean hasGetProgramPidRequest()
   {
      return hasGetProgramPidRequest;
   }

   public void installLoadModuleRequest(int options,
                                        String file_format,
                                        String file_name,
                                        String install_handle,
                                        String[] conf)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasInstallLoadModuleRequest);
      request = new PmInstallLoadModuleRequest(options,
                                               file_format,
                                               file_name,
                                               install_handle,
                                               conf);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void uninstallLoadModuleRequest(String install_handle)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasUninstallLoadModuleRequest);
      request = new PmUninstallLoadModuleRequest(install_handle);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void loadModuleInstallHandlesRequest()
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasLoadModuleInstallHandlesRequest);
      request = new PmLoadModuleInstallHandlesRequest();
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void loadModuleInfoRequest(String install_handle)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasLoadModuleInfoRequest);
      request = new PmLoadModuleInfoRequest(install_handle);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void createProgramRequest(int domain,
                                    String install_handle,
                                    String[] conf)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasCreateProgramRequest);
      request = new PmCreateProgramRequest(domain, install_handle, conf);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void startProgramRequest(int progpid)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasStartProgramRequest);
      request = new PmStartProgramRequest(progpid);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void killProgramRequest(int progpid)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasKillProgramRequest);
      request = new PmKillProgramRequest(progpid);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void programInfoRequest(int progpid)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasProgramInfoRequest);
      request = new PmProgramInfoRequest(progpid);
      senderGateway.send(receiverPid, pmPid, request);
   }

   public void getProgramPidRequest(int pid)
      throws IOException, GatewayException
   {
      Signal request;
      checkState();
      checkSupport(hasGetProgramPidRequest);
      request = new PmGetProgramPidRequest(pid);
      senderGateway.send(receiverPid, pmPid, request);
   }

   private void programManagerKilled(Signal sig)
   {
      listener.programManagerKilled();
   }

   private void installLoadModuleReply(Signal sig)
   {
      PmInstallLoadModuleReply reply = (PmInstallLoadModuleReply) sig;
      listener.installLoadModuleReply(reply.status, reply.install_handle);
   }

   private void uninstallLoadModuleReply(Signal sig)
   {
      PmUninstallLoadModuleReply reply = (PmUninstallLoadModuleReply) sig;
      listener.uninstallLoadModuleReply(reply.status, reply.install_handle);
   }

   private void loadModuleInstallHandlesReply(Signal sig)
   {
      PmLoadModuleInstallHandlesReply reply = (PmLoadModuleInstallHandlesReply) sig;
      listener.loadModuleInstallHandlesReply(reply.status, reply.install_handles);
   }

   private void loadModuleInfoReply(Signal sig)
   {
      PmLoadModuleInfoReply reply = (PmLoadModuleInfoReply) sig;
      listener.loadModuleInfoReply(reply.status,
                                   reply.install_handle,
                                   reply.entrypoint,
                                   reply.options,
                                   reply.text_base,
                                   reply.text_size,
                                   reply.data_base,
                                   reply.data_size,
                                   reply.no_of_sections,
                                   reply.no_of_instances,
                                   reply.file_format,
                                   reply.file_name);
   }

   private void createProgramReply(Signal sig)
   {
      PmCreateProgramReply reply = (PmCreateProgramReply) sig;
      listener.createProgramReply(reply.status,
                                  reply.domain,
                                  reply.install_handle,
                                  reply.progpid,
                                  reply.main_bid);
   }

   private void startProgramReply(Signal sig)
   {
      PmStartProgramReply reply = (PmStartProgramReply) sig;
      listener.startProgramReply(reply.status, reply.progpid);
   }

   private void killProgramReply(Signal sig)
   {
      PmKillProgramReply reply = (PmKillProgramReply) sig;
      listener.killProgramReply(reply.status, reply.progpid);
   }

   private void programInfoReply(Signal sig)
   {
      PmProgramInfoReply reply = (PmProgramInfoReply) sig;
      listener.programInfoReply(reply.status,
                                reply.install_handle,
                                reply.progpid,
                                reply.domain,
                                reply.main_block,
                                reply.main_process,
                                reply.pool_base,
                                reply.pool_size,
                                reply.uid,
                                reply.state,
                                reply.extended_info,
                                reply.segpid,
                                reply.stk_poolid,
                                reply.sig_poolid,
                                reply.sig_pool_base,
                                reply.sig_pool_size,
                                reply.heap);
   }

   private void getProgramPidReply(Signal sig)
   {
      PmGetProgramPidReply reply = (PmGetProgramPidReply) sig;
      listener.getProgramPidReply(reply.status, reply.pid, reply.progpid);
   }

   class ReceiverThread extends Thread
   {
      private volatile boolean listen;

      public ReceiverThread()
      {
         super("OSE Program Manager Receiver Thread");
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
                  case PmAttachSignal.SIG_NO:
                     attachReceived = true;
                     listen = false;
                     connected = false;
                     programManagerKilled(sig);
                     break;
                  case PmInstallLoadModuleReply.SIG_NO:
                     installLoadModuleReply(sig);
                     break;
                  case PmUninstallLoadModuleReply.SIG_NO:
                     uninstallLoadModuleReply(sig);
                     break;
                  case PmLoadModuleInstallHandlesReply.SIG_NO:
                     loadModuleInstallHandlesReply(sig);
                     break;
                  case PmLoadModuleInfoReply.SIG_NO:
                     loadModuleInfoReply(sig);
                     break;
                  case PmCreateProgramReply.SIG_NO:
                     createProgramReply(sig);
                     break;
                  case PmStartProgramReply.SIG_NO:
                     startProgramReply(sig);
                     break;
                  case PmKillProgramReply.SIG_NO:
                     killProgramReply(sig);
                     break;
                  case PmProgramInfoReply.SIG_NO:
                     programInfoReply(sig);
                     break;
                  case PmGetProgramPidReply.SIG_NO:
                     getProgramPidReply(sig);
                     break;
                  default:
                     throw new IOException("Unknown signal received in " +
                           "program manager receiver thread: " + sig.getSigNo());
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
                  // Exit if exception was thrown due to a forced disconnect
                  // of the receiver gateway when shutdown of this receiver
                  // thread was issued.
                  return;
               }
               else
               {
                  System.err.println(
                     "Exception occurred in program manager receiver thread: " + e);
                  listener.exceptionThrown(e);
                  break;
               }
            }
         }

         try
         {
            // Only detach from the program manager if we
            // have not received the attached signal.
            if (!attachReceived)
            {
               receiverGateway.detach(attref);
            }
         }
         catch (IOException e)
         {
            System.err.println("Detaching from program manager failed.");
         }

         try
         {
            receiverGateway.disconnect();
         }
         catch (IOException e)
         {
            System.err.println(
                  "Disconnecting program manager receiver gateway failed.");
         }
      }
   }
}