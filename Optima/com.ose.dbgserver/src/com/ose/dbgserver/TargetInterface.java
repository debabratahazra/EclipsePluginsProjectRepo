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

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.LinkedList;
import com.ose.dbgserver.protocol.DBGBpFired;
import com.ose.dbgserver.protocol.DBGClearBpReply;
import com.ose.dbgserver.protocol.DBGGetBlockFullInfoReply;
import com.ose.dbgserver.protocol.DBGGetBlockInfoReply;
import com.ose.dbgserver.protocol.DBGGetCPULoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetCPUMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetEnvListEndMark;
import com.ose.dbgserver.protocol.DBGGetEnvListReply;
import com.ose.dbgserver.protocol.DBGGetPoolInfoReply;
import com.ose.dbgserver.protocol.DBGGetPoolSignalsReply;
import com.ose.dbgserver.protocol.DBGGetPriLoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetPriMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetProcLoadReportsReply;
import com.ose.dbgserver.protocol.DBGGetProcMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGGetProcessFullInfoReply;
import com.ose.dbgserver.protocol.DBGGetProcessInfoReply;
import com.ose.dbgserver.protocol.DBGGetProgramInfoReply;
import com.ose.dbgserver.protocol.DBGGetSignalQueueReply;
import com.ose.dbgserver.protocol.DBGGetStackUsageReply;
import com.ose.dbgserver.protocol.DBGInfoBlockEndMark;
import com.ose.dbgserver.protocol.DBGInfoCPULoadReportsEndMark;
import com.ose.dbgserver.protocol.DBGInfoPoolEndMark;
import com.ose.dbgserver.protocol.DBGInfoPriLoadReportsEndMark;
import com.ose.dbgserver.protocol.DBGInfoProcEndMark;
import com.ose.dbgserver.protocol.DBGInfoProcLoadReportsEndMark;
import com.ose.dbgserver.protocol.DBGInfoProgramEndMark;
import com.ose.dbgserver.protocol.DBGInfoQueueEndMark;
import com.ose.dbgserver.protocol.DBGInitConnection;
import com.ose.dbgserver.protocol.DBGInitConnectionReply;
import com.ose.dbgserver.protocol.DBGInterceptScopeReply;
import com.ose.dbgserver.protocol.DBGPingReply;
import com.ose.dbgserver.protocol.DBGPoolSignalsEndMark;
import com.ose.dbgserver.protocol.DBGReadMemoryReply;
import com.ose.dbgserver.protocol.DBGReadRegisterReply;
import com.ose.dbgserver.protocol.DBGResumeScopeReply;
import com.ose.dbgserver.protocol.DBGSetApplicationScopeReply;
import com.ose.dbgserver.protocol.DBGSetBpReply;
import com.ose.dbgserver.protocol.DBGSetPriMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGSetProcMeasurementStatusReply;
import com.ose.dbgserver.protocol.DBGStepProcessComplete;
import com.ose.dbgserver.protocol.DBGStepProcessReply;
import com.ose.dbgserver.protocol.DBGTargetInfo;
import com.ose.dbgserver.protocol.DBGWriteMemoryReply;
import com.ose.dbgserver.protocol.DBGWriteRegisterReply;
import com.ose.dbgserver.protocol.Message;
import com.ose.dbgserver.protocol.dbgserverinterfaceConstants;
import com.ose.dbgserver.util.AssertException;
import com.ose.dbgserver.util.IMessage;
import com.ose.dbgserver.util.MessageQueue;
import com.ose.dbgserver.util.ThreadWithQueue;
import com.ose.dbgserver.util.Timer;
import com.ose.dbgserver.util.TimerInterface;

/**
 * This is the target reader thread that handles all data received from the
 * target. There is one TargetInterface for each connected target debug server.
 * Signals (or messages) that are read from the input stream is passed to the
 * debug server client.
 *
 * @see TargetWriter
 * @see DebugServer
 */
class TargetInterface extends ThreadWithQueue implements
   dbgserverinterfaceConstants, TimerInterface
{
   private static final int MAJOR_INTERFACE_VERSION = 1;
   private static final int MINOR_INTERFACE_VERSION = 2;
   private static final long LOAD_TIME = System.currentTimeMillis();

   private final Object lock = new Object();
   //private TraceReader traceReader = null;
   private boolean terminated;
   private boolean weAreDead;
   private final int slidingWindowSize = 30;
   private String userHostIdName;
   private int connectionBrokenTimeout = 30000;
   private ConnectionBrokenTimer connectionBrokenTimer;
   private PingTimer pingTimer;
   private DebugServer debugServer;
   private Socket socket;
   private DataOutputStream outStream;
   private DataInputStream inStream;
   private TargetWriter targetWriter;
   private Hashtable outstandingRequests = new Hashtable(73);
   private long trCount;
   private Long currentTrid = new Long(1L);
   private boolean readyToHandleMessage = true;
   private LinkedList receivedMessages = new LinkedList();
   private MessageListener defaultListener;
   private InetAddress inetAddress;
   private int serverPort;
   private boolean hasConnector;

   TargetInterface(DebugServer ds)
   {
      super("TargetInterface");
      debugServer = ds;

      String userName = System.getProperty("user.name", "Unknown");
      String hostName;
      try
      {
         hostName = InetAddress.getLocalHost().getHostName();
      }
      catch (UnknownHostException e)
      {
         hostName = "Unknown";
      }
      userHostIdName = new String(userName + "@" + hostName + ":" + LOAD_TIME);
      start();
   }

   public void exec()
   {
      try
      {
         defaultListener = new MessageHandler();

         for (;;)
         {
            try
            {
               checkTerminate();
               DataInputStream in = inStream;
               while (in == null)
               {
                  waitForConnection();
                  in = inStream;
               }
               yield();
               int signal = in.readInt();
               checkTerminate();
               synchronized (lock)
               {
                  if (pingTimer != null)
                  {
                     // Reset the ping timer if we got something.
                     pingTimer.reset();
                  }
               }
               defaultListener.messageReceived(signal, in);
            }
            catch (IOException e)
            {
               checkTerminate();
               synchronized (lock)
               {
                  if (socket != null)
                  {
                     disconnect(e);
                  }
               }
               new Connector(this);
            }
         }
      }
      finally
      {
         synchronized (lock)
         {
            // Set to true when the target interface thread is killed.
            weAreDead = true;
         }
      }
   }

   public void die()
   {
      synchronized (lock)
      {
         if (socket != null)
         {
            disconnect();
         }
         if (!weAreDead)
         {
            (new TerminateSignal()).send(this);
            this.interrupt();
         }
         else
         {
            doTerminate();
         }
      }
   }

   private void checkTerminate()
   {
      try
      {
         IMessage sig = receive(true);
         if (sig != null)
         {
            if (sig instanceof TerminateSignal)
            {
               doTerminate();
            }
            else
            {
               throw new Error("Unknown internal signal received.");
            }
         }
      } catch (InterruptedException ignore) {}
   }

   public boolean isTerminated()
   {
      return terminated;
   }

   private void doTerminate()
   {
      synchronized (lock)
      {
         if (!terminated)
         {
            debugServer.terminate();
            debugServer = null;
            defaultListener = null;
            terminated = true;
            kill();
         }
      }
   }

   /**
    * Start a new transaction to the dbgserver.
    */
   public void send(MessageQueue sender, Message message)
   {
      synchronized (lock)
      {
         if (targetWriter != null)
         {
            targetWriter.write(message);
            trCount++;
            Transaction tr = new Transaction(sender, trCount);
            outstandingRequests.put(new Long(trCount), tr);
         }
      }
   }

   public void acknowledge(boolean transactionDone)
   {
      synchronized (lock)
      {
         if (transactionDone)
         {
            outstandingRequests.remove(currentTrid);
            currentTrid = new Long(currentTrid.longValue() + 1l);
         }
         readyToHandleMessage = true;
         if (receivedMessages.size() > 0)
         {
            // Get the first message.
            Message m = (Message) receivedMessages.remove(0);
            handleReply(m);
         }
      }
   }

   private void handleReply(Message msg)
   {
      synchronized (lock)
      {
         if (readyToHandleMessage)
         {
            Transaction t = (Transaction) outstandingRequests.get(currentTrid);
            if (t != null)
            {
               t.sendReply(msg);
               readyToHandleMessage = false;
            }
         }
         else
         {
            receivedMessages.addLast(msg);
         }
      }
   }

   void disconnect(Exception e)
   {
      disconnect();
   }

   private void close()
   {
      Socket _socket;

      synchronized (lock)
      {
         if (socket != null)
         {
            _socket = socket;
            socket = null;
         }
         else
         {
            _socket = null;
         }
      }

      try
      {
         if (_socket != null)
         {
            _socket.close();
         }
      } catch (IOException ignore) {}
   }

   private void disconnect()
   {
      Runnable disconnectionRunner = new DisconnectionRunner();
      (new Thread(disconnectionRunner)).start();
   }

   public void connect(InetAddress addr, int _serverPort)
   {
      inetAddress = addr;
      serverPort = _serverPort;
      new Connector(this);
   }

   public void blockingConnect()
   {
      boolean done = false;
      int retryCount = 0;

      while (!done)
      {
         try
         {
            socket = new Socket(inetAddress, serverPort);
            done = true;
         }
         catch (IOException e)
         {
            synchronized (lock)
            {
               if (debugServer == null)
               {
                  // Target is terminated so break out.
                  return;
               }
            }
            retryCount++;
            if (retryCount > 10)
            {
               synchronized (lock)
               {
                  if (debugServer != null)
                  {
                     debugServer.cancelConnect();
                  }
               }
               die();
               return;
            }
            else
            {
               // Sleep and then retry.
               try
               {
                  Thread.sleep(3000);
               } catch (InterruptedException ignore) {}
            }
         }
      }

      synchronized (lock)
      {
         if (socket == null)
         {
            die();
            return;
         }
         try
         {
            outStream = new DataOutputStream(new BufferedOutputStream(
                  socket.getOutputStream(), 1600));
            inStream = new DataInputStream(socket.getInputStream());
            DBGInitConnection.write(outStream, MAJOR_INTERFACE_VERSION,
                  MINOR_INTERFACE_VERSION, slidingWindowSize,
                  connectionBrokenTimeout, userHostIdName);
            outStream.flush();
         }
         catch (IOException e)
         {
            die();
            return;
         }
         if (connectionBrokenTimer != null)
         {
            connectionBrokenTimer.cancel();
            connectionBrokenTimer = null;
         }
         gotConnection();
      }
   }

   public void timerExpired(Timer timer)
   {
      if (timer instanceof ConnectionBrokenTimer)
      {
         die();
      }
      else if (timer instanceof PingTimer)
      {
         synchronized (lock)
         {
            pingTimer = null;
            disconnect();
         }
      }
      else
      {
         throw new AssertException("Undefined timer");
      }
   }

   private synchronized void waitForConnection()
   {
      while (socket == null)
      {
         try
         {
            wait();
         }
         catch (InterruptedException e)
         {
            checkTerminate();
         }
      }
   }

   private synchronized void gotConnection()
   {
      AssertException.test(socket != null);
      notify();
   }

   private class MessageHandler implements MessageListener
   {
      private DBGInitConnectionReply initReply = null;

      public void messageReceived(int signal, DataInputStream in) throws IOException
      {
         switch (signal)
         {
         case DBGINITCONNECTIONREPLY:
            initReply = new DBGInitConnectionReply(in);
            if (initReply.connectionAccepted == 0)
            {
               String msg[] = new String[2];
               msg[0] = "Cannot connect to " + debugServer.getName() + ".";
               if (MAJOR_INTERFACE_VERSION > initReply.majorInterfaceVersion)
               {
                  msg[1] = "The targets dbgserver is too old.";
               }
               else if (MAJOR_INTERFACE_VERSION < initReply.majorInterfaceVersion)
               {
                  msg[1] = "The targets dbgserver is newer than the dbgclient.";
               }
               else
               {
                  msg[1] = "Interface error.";
               }
               System.err.println(msg[0] + " " + msg[1]);
            }
            else
            {
               /* Connection accepted.
                * The notify of connection is suspended until we get the
                * DBGTargetInfo signal (interface is newer than 1.0).
                */
               if (initReply.minorInterfaceVersion < 1)
               {
                  synchronized (lock)
                  {
                     if (outStream != null)
                     {
                        // Make sure that we did not lose the connection.
                        debugServer.setSystemInfo(
                              initReply.majorInterfaceVersion,
                              initReply.minorInterfaceVersion,
                              initReply.tickLength,
                              initReply.traceBufSize);
                        targetWriter = new TargetWriter(outStream,
                              TargetInterface.this);
                        debugServer.connected(targetWriter);
                     }
                  }
               }
            }
            break;
         case DBGTARGETINFO:
            AssertException.test(initReply != null,
                  "Got targetinfo before the connection");
            DBGTargetInfo targetInfo = new DBGTargetInfo(in);
            synchronized (lock)
            {
               if (outStream != null)
               {
                  // Check that we still are connected.
                  debugServer.setEndian(targetInfo.byteOrder);
                  debugServer.setCpuClass(targetInfo.cpu_class);
                  debugServer.setCpuType(targetInfo.cpu_type);
                  debugServer.setSystemInfo(
                        initReply.majorInterfaceVersion,
                        initReply.minorInterfaceVersion,
                        initReply.tickLength,
                        initReply.traceBufSize);
                  targetWriter = new TargetWriter(outStream,
                                                  TargetInterface.this);
                  debugServer.connected(targetWriter);
               }
            }
            break;
         case DBGPINGREPLY:
            new DBGPingReply(in);
            synchronized (lock)
            {
               if (pingTimer != null)
               {
                  pingTimer.cancel();
                  pingTimer = null;
               }
            }
            break;
         case DBGGETPROCESSINFOREPLY:
            DBGGetProcessInfoReply signalDBGGetProcessInfoReply =
               new DBGGetProcessInfoReply(in);
            synchronized (lock)
            {
               debugServer.getProcessInfoReply(signalDBGGetProcessInfoReply);
            }
            break;
         case DBGGETPROCESSFULLINFOREPLY:
            DBGGetProcessFullInfoReply signalDBGGetProcessFullInfoReply =
               new DBGGetProcessFullInfoReply(in);
            synchronized (lock)
            {
               debugServer.getProcessFullInfoReply(
                     signalDBGGetProcessFullInfoReply);
            }
            break;
         case DBGGETBLOCKINFOREPLY:
            DBGGetBlockInfoReply signalDBGGetBlockInfoReply =
               new DBGGetBlockInfoReply(in);
            synchronized (lock)
            {
               debugServer.getBlockInfoReply(signalDBGGetBlockInfoReply);
            }
            break;
         case DBGINFOPROCENDMARK:
            DBGInfoProcEndMark signalDBGInfoProcEndMark =
               new DBGInfoProcEndMark(in);
            synchronized (lock)
            {
               debugServer.infoProcEndMark(signalDBGInfoProcEndMark);
            }
            break;
         case DBGINFOBLOCKENDMARK:
            DBGInfoBlockEndMark signalDBGInfoBlockEndMark =
               new DBGInfoBlockEndMark(in);
            synchronized (lock)
            {
               debugServer.infoBlockEndMark(signalDBGInfoBlockEndMark);
            }
            break;
         case DBGGETPOOLINFOREPLY:
            DBGGetPoolInfoReply signalDBGGetPoolInfoReply =
               new DBGGetPoolInfoReply(in);
            synchronized (lock)
            {
               debugServer.getPoolInfoReply(signalDBGGetPoolInfoReply);
            }
            break;
         case DBGINFOPOOLENDMARK:
            DBGInfoPoolEndMark signalDBGInfoPoolEndMark =
               new DBGInfoPoolEndMark(in);
            synchronized (lock)
            {
               debugServer.infoPoolEndMark(signalDBGInfoPoolEndMark);
            }
            break;
         case DBGGETPOOLSIGNALSREPLY:
            DBGGetPoolSignalsReply signalDBGGetPoolSignalsReply =
               new DBGGetPoolSignalsReply(in);
            synchronized (lock)
            {
               debugServer.getPoolSignalsReply(signalDBGGetPoolSignalsReply);
            }
            break;
         case DBGPOOLSIGNALSENDMARK:
            DBGPoolSignalsEndMark signalDBGPoolSignalsEndMark =
               new DBGPoolSignalsEndMark(in);
            synchronized (lock)
            {
               debugServer.poolSignalsEndMark(signalDBGPoolSignalsEndMark);
            }
            break;
         case DBGGETBLOCKFULLINFOREPLY:
            DBGGetBlockFullInfoReply signalDBGGetBlockFullInfoReply =
               new DBGGetBlockFullInfoReply(in);
            synchronized (lock)
            {
               debugServer.getBlockFullInfoReply(signalDBGGetBlockFullInfoReply);
            }
            break;
         case DBGGETENVLISTREPLY:
            DBGGetEnvListReply signalDBGGetEnvListReply =
               new DBGGetEnvListReply(in);
            synchronized (lock)
            {
               debugServer.getEnvListReply(signalDBGGetEnvListReply);
            }
            break;
         case DBGGETENVLISTENDMARK:
            DBGGetEnvListEndMark signalDBGGetEnvListEndMark =
               new DBGGetEnvListEndMark(in);
            synchronized (lock)
            {
               debugServer.getEnvListEndMark(signalDBGGetEnvListEndMark);
            }
            break;
         case DBGGETSTACKUSAGEREPLY:
            DBGGetStackUsageReply signalDBGGetStackUsageReply =
               new DBGGetStackUsageReply(in);
            synchronized (lock)
            {
               debugServer.getStackUsageReply(signalDBGGetStackUsageReply);
            }
            break;
         case DBGGETSIGNALQUEUEREPLY:
            DBGGetSignalQueueReply signalDBGGetSignalQueueReply =
               new DBGGetSignalQueueReply(in);
            synchronized (lock)
            {
               debugServer.getSignalQueueReply(signalDBGGetSignalQueueReply);
            }
            break;
         case DBGINFOQUEUEENDMARK:
            DBGInfoQueueEndMark signalDBGInfoQueueEndMark =
               new DBGInfoQueueEndMark(in);
            synchronized (lock)
            {
               debugServer.infoQueueEndMark(signalDBGInfoQueueEndMark);
            }
            break;
         case DBGSETAPPLICATIONSCOPEREPLY:
            DBGSetApplicationScopeReply signalDBGSetApplicationScopeReply =
               new DBGSetApplicationScopeReply(in);
            synchronized (lock)
            {
               debugServer.setApplicationScopeReply(
                     signalDBGSetApplicationScopeReply);
            }
            break;
         case DBGINTERCEPTSCOPEREPLY:
            DBGInterceptScopeReply signalDBGInterceptScopeReply =
               new DBGInterceptScopeReply(in);
            synchronized (lock)
            {
               debugServer.interceptScopeReply(signalDBGInterceptScopeReply);
            }
            break;
         case DBGRESUMESCOPEREPLY:
            DBGResumeScopeReply signalDBGResumeScopeReply =
               new DBGResumeScopeReply(in);
            synchronized (lock)
            {
               debugServer.resumeScopeReply(signalDBGResumeScopeReply);
            }
            break;
         case DBGWRITEREGISTERREPLY:
            DBGWriteRegisterReply signalDBGWriteRegisterReply =
               new DBGWriteRegisterReply(in);
            synchronized (lock)
            {
               debugServer.writeRegisterReply(signalDBGWriteRegisterReply);
            }
            break;
         case DBGREADMEMORYREPLY:
            DBGReadMemoryReply signalDBGReadMemoryReply =
               new DBGReadMemoryReply(in);
            synchronized (lock)
            {
               debugServer.readMemoryReply(signalDBGReadMemoryReply);
            }
            break;
         case DBGWRITEMEMORYREPLY:
            DBGWriteMemoryReply signalDBGWriteMemoryReply =
               new DBGWriteMemoryReply(in);
            synchronized (lock)
            {
               debugServer.writeMemoryReply(signalDBGWriteMemoryReply);
            }
            break;
         case DBGREADREGISTERREPLY:
            DBGReadRegisterReply signalDBGReadRegisterReply =
               new DBGReadRegisterReply(in);
            synchronized (lock)
            {
               debugServer.readRegistersReply(signalDBGReadRegisterReply);
            }
            break;
         case DBGSETBPREPLY:
            DBGSetBpReply signalDBGSetBpReply = new DBGSetBpReply(in);
            synchronized (lock)
            {
               debugServer.setBpReply(signalDBGSetBpReply);
            }
            break;
         case DBGCLEARBPREPLY:
            DBGClearBpReply signalDBGClearBpReply = new DBGClearBpReply(in);
            synchronized (lock)
            {
               debugServer.clearBpReply(signalDBGClearBpReply);
            }
            break;
         case DBGBPFIRED:
            DBGBpFired signalDBGBpFired = new DBGBpFired(in);
            synchronized (lock)
            {
               debugServer.bpFired(signalDBGBpFired);
            }
            break;
         case DBGSTEPPROCESSCOMPLETE:
            DBGStepProcessComplete signalDBGStepProcessComplete =
               new DBGStepProcessComplete(in);
            synchronized (lock)
            {
               debugServer.stepProcessComplete(signalDBGStepProcessComplete);
            }
            break;
         case DBGSTEPPROCESSREPLY:
            new DBGStepProcessReply(in);
            // Nothing can be done.
            break;
         case DBGGETCPUMEASUREMENTSTATUSREPLY:
            DBGGetCPUMeasurementStatusReply signalDBGGetCPUMeasurementStatusReply =
               new DBGGetCPUMeasurementStatusReply(in);
            synchronized (lock)
            {
               debugServer.getCPUMeasurementStatusReply(
                     signalDBGGetCPUMeasurementStatusReply);
            }
            break;
         case DBGGETCPULOADREPORTSREPLY:
            DBGGetCPULoadReportsReply signalDBGGetCPULoadReportsReply =
               new DBGGetCPULoadReportsReply(in);
            synchronized (lock)
            {
               debugServer.getCPULoadReportsReply(
                     signalDBGGetCPULoadReportsReply);
            }
            break;
         case DBGINFOCPULOADREPORTSENDMARK:
            new DBGInfoCPULoadReportsEndMark(in);
            // Nothing needs to be done.
            break;
         case DBGGETPRIMEASUREMENTSTATUSREPLY:
            DBGGetPriMeasurementStatusReply signalDBGGetPriMeasurementStatusReply =
               new DBGGetPriMeasurementStatusReply(in);
            synchronized (lock)
            {
               debugServer.getPriMeasurementStatusReply(
                     signalDBGGetPriMeasurementStatusReply);
            }
            break;
         case DBGSETPRIMEASUREMENTSTATUSREPLY:
            DBGSetPriMeasurementStatusReply signalDBGSetPriMeasurementStatusReply =
               new DBGSetPriMeasurementStatusReply(in);
            synchronized (lock)
            {
               debugServer.setPriMeasurementStatusReply(
                     signalDBGSetPriMeasurementStatusReply);
            }
            break;
         case DBGGETPRILOADREPORTSREPLY:
            DBGGetPriLoadReportsReply signalDBGGetPriLoadReportsReply =
               new DBGGetPriLoadReportsReply(in);
            synchronized (lock)
            {
               debugServer.getPriLoadReportsReply(
                     signalDBGGetPriLoadReportsReply);
            }
            break;
         case DBGINFOPRILOADREPORTSENDMARK:
            new DBGInfoPriLoadReportsEndMark(in);
            // Nothing needs to be done.
            break;
         case DBGGETPROCMEASUREMENTSTATUSREPLY:
            DBGGetProcMeasurementStatusReply signalDBGGetProcMeasurementStatusReply =
               new DBGGetProcMeasurementStatusReply(in);
            synchronized (lock)
            {
               debugServer.getProcMeasurementStatusReply(
                     signalDBGGetProcMeasurementStatusReply);
            }
            break;
         case DBGSETPROCMEASUREMENTSTATUSREPLY:
            DBGSetProcMeasurementStatusReply signalDBGSetProcMeasurementStatusReply =
               new DBGSetProcMeasurementStatusReply(in);
            synchronized (lock)
            {
               debugServer.setProcMeasurementStatusReply(
                     signalDBGSetProcMeasurementStatusReply);
            }
            break;
         case DBGGETPROCLOADREPORTSREPLY:
            DBGGetProcLoadReportsReply signalDBGGetProcLoadReportsReply =
               new DBGGetProcLoadReportsReply(in);
            synchronized (lock)
            {
               debugServer.getProcLoadReportsReply(
                     signalDBGGetProcLoadReportsReply);
            }
            break;
         case DBGINFOPROCLOADREPORTSENDMARK:
            new DBGInfoProcLoadReportsEndMark(in);
            // Nothing needs to be done.
            break;
         case DBGGETPROGRAMINFOREPLY:
            DBGGetProgramInfoReply signalDBGGetProgramInfoReply =
               new DBGGetProgramInfoReply(in);
            synchronized (lock)
            {
               debugServer.getProgramInfoReply(signalDBGGetProgramInfoReply);
            }
            break;
         case DBGINFOPROGRAMENDMARK:
            DBGInfoProgramEndMark signalDBGInfoProgramEndMark =
               new DBGInfoProgramEndMark(in);
            synchronized (lock)
            {
               debugServer.infoProgramEndMark(signalDBGInfoProgramEndMark);
            }
            break;
         /*
         case DBGGETSIGNALQUEUEFULLREPLY:
            DBGGetSignalQueueFullReply signalDBGGetSignalQueueFullReply =
               new DBGGetSignalQueueFullReply(in);
            break;
         case DBGINFOQUEUEFULLENDMARK:
            DBGInfoQueueFullEndMark signalDBGInfoQueueFullEndMark =
               new DBGInfoQueueFullEndMark(in);
            break;
         case DBGIOPCSESSIONSTATUS:
            DBGIOPCSessionStatus signalDBGIOPCSessionStatus =
               new DBGIOPCSessionStatus(in);
            break;
         case DBGIOPCSIGNAL:
            DBGIOPCSignal signalDBGIOPCSignal = new DBGIOPCSignal(in);
            break;
         case DBGFLOWCONTROLREPLY:
            DBGFlowControlReply signalDBGFlowControlReply =
               new DBGFlowControlReply(in);
            asyncReceiver = TargetInterface.this.asyncReceiver;
            if (asyncReceiver != null)
               asyncReceiver.handleAsyncReply(signalDBGFlowControlReply);
            break;
         case DBGGETCATCHSCOPESTATUSREPLY:
            DBGGetCatchScopeStatusReply signalDBGGetCatchScopeStatusReply =
               new DBGGetCatchScopeStatusReply(in);
            break;
         */
         default:
            System.err.println("Received unknown signal in target interface " +
                  "or data is out of sync " + signal);
            //throw new Error(
            //      "Received unknown signal or data is out of sync " + signal);
         }
      }
   }

   private class Transaction
   {
      private final MessageQueue sender;

      Transaction(MessageQueue sender, long trid)
      {
         this.sender = sender;
      }

      public void sendReply(Message msg)
      {
         TargetMessage tmsg = new TargetMessage(msg);
         tmsg.send(sender, TargetInterface.this);
      }
   }

   private class TerminateSignal extends IMessage
   {
   }

   private class DisconnectionRunner implements Runnable
   {
      public void run()
      {
         synchronized (lock)
         {
            if (targetWriter != null)
            {
               targetWriter.stopThread();
               targetWriter = null;
            }
            if (socket != null)
            {
               close();
               outstandingRequests.clear();

               // Reinitialize transaction ids.
               trCount = 0;
               currentTrid = new Long(trCount + 1);

               outStream = null;
               inStream = null;
               debugServer.disconnected();
            }
         }
      }
   }

   private static class Connector extends Thread
   {
      private final TargetInterface ti;

      Connector(TargetInterface _ti)
      {
         ti = _ti;
         synchronized (ti.lock)
         {
            if (!ti.hasConnector)
            {
               ti.hasConnector = true;
               start();
            }
         }
      }

      public void run()
      {
         try
         {
            ti.blockingConnect();
         }
         finally
         {
            synchronized (ti.lock)
            {
               ti.hasConnector = false;
            }
         }
      }
   }

   private static class ConnectionBrokenTimer extends Timer
   {
      ConnectionBrokenTimer(TimerInterface timerInterface, long millis)
      {
         super(timerInterface, millis);
      }
   }

   private static class PingTimer extends Timer
   {
      PingTimer(TimerInterface timerInterface, long millis)
      {
         super(timerInterface, millis);
      }
   }
}
