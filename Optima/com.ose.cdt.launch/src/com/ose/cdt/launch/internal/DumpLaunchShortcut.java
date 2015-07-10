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

package com.ose.cdt.launch.internal;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import com.ose.cdt.debug.mi.core.GDBDebugger;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.gateway.SignalInputStream;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;

public class DumpLaunchShortcut extends com.ose.launch.DumpLaunchShortcut
{
   private static final int MONITOR_INTERFACE_REQUEST = 39000;
   private static final int MONITOR_INTERFACE_REPLY = 39001;

   private static final int MONITOR_GET_PROCESS_INFO_REQUEST = 39010;
   private static final int MONITOR_GET_PROCESS_INFO_REPLY = 39011;
   private static final int MONITOR_GET_PROCESS_INFO_LONG_REPLY = 39012;
   private static final int MONITOR_GET_PROCESS_INFO_ENDMARK = 39013;

   private static final int CPU_PPC = 0;
   private static final int CPU_ARM = 2;
   private static final int CPU_MIPS = 3;
   private static final int CPU_UNKNOWN = 0x7FFFFFFF;

   protected ILaunchConfiguration editDebugLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         File file,
         String debugScope)
      throws CoreException
   {
      DumpFile dumpFile = null;

      try
      {
         Process process;
         ILaunchConfiguration config;

         dumpFile = new DumpFile(file);
         process = getCurrentProcess(dumpFile);
         if (process == null)
         {
            throw new CoreException(LaunchUIPlugin.createErrorStatus(
                  "Dump file does not contain info about crashed process"));
         }

         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
               InetAddress.getLocalHost().getHostAddress());
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
               0);
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
               "");
         wc.setAttribute(
               IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED,
               true);
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_DUMP_FILE,
               file.getAbsolutePath());
         wc.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID,
               IOSEMILaunchConfigurationConstants.OSE_DEBUGGER_ID);
         wc.setAttribute(
               ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
               ICDTLaunchConfigurationConstants.DEBUGGER_MODE_CORE);
         wc.setAttribute(
               ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN,
               false);
         wc.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUGGER_PROTOCOL,
               "mi");
         if (wc.getAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
               (String) null) == null)
         {
            wc.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
                  getGDB(getCpuType(dumpFile)));
         }
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
               debugScope);
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID,
               "0x" + Integer.toHexString(process.getSid()).toUpperCase());
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID,
               "0x" + Integer.toHexString(process.getBid()).toUpperCase());
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID,
               "0x" + Integer.toHexString(process.getPid()).toUpperCase());
         config = wc.doSave();
         return config;
      }
      catch (IOException e)
      {
         throw new CoreException(LaunchUIPlugin.createErrorStatus(e));
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
      }
   }

   private Process getCurrentProcess(DumpFile dumpFile)
      throws IOException
   {
      List errorBlocks;
      int crashedPid = 0;
      List processes;
      Process currentProcess = null;

      // Get crashed process ID for latest error block.
      errorBlocks = dumpFile.getErrorBlocks();
      for (Iterator i = errorBlocks.iterator(); i.hasNext();)
      {
         ErrorBlock errorBlock = (ErrorBlock) i.next();
         crashedPid = (int) errorBlock.getCurrentProcess();
      }
      if (crashedPid == 0)
      {
         return null;
      }

      // Get latest process that matches the crashed process ID.
      processes = getProcesses(dumpFile);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getPid() == crashedPid)
         {
            currentProcess = process;
         }
      }

      return currentProcess;
   }

   private List getProcesses(DumpFile dumpFile)
      throws IOException
   {
      List list = new ArrayList();
      boolean bigEndian;
      List signalBlocks;

      bigEndian = dumpFile.isBigEndian();
      signalBlocks = dumpFile.getSignalBlocks();
      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock = (SignalBlock) i.next();
         int reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MONITOR_GET_PROCESS_INFO_REQUEST)
         {
            list.addAll(getProcessSignalBlock(signalBlock, bigEndian));
         }
      }

      return list;
   }

   private List getProcessSignalBlock(SignalBlock signalBlock, boolean bigEndian)
      throws IOException
   {
      List list = new ArrayList();
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         if (signal.getSigNo() != MONITOR_GET_PROCESS_INFO_REQUEST)
         {
            throw new IffException("Invalid request signal in " +
               "MonitorGetProcessInfo signal block in dump file");
         }

         do
         {
            signal = readSignal(in);
            switch (signal.getSigNo())
            {
               case MONITOR_GET_PROCESS_INFO_REPLY:
                  // Fall through.
               case MONITOR_GET_PROCESS_INFO_LONG_REPLY:
                  list.add(parseProcessSignal(signal, bigEndian));
                  break;
               case MONITOR_GET_PROCESS_INFO_ENDMARK:
                  endmark = true;
                  break;
               default:
                  throw new IffException("Invalid reply signal in " +
                     "MonitorGetProcessInfo signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing endmark in " +
               "MonitorGetProcessInfo signal block in dump file");
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

      return list;
   }

   private Process parseProcessSignal(Signal signal, boolean bigEndian)
      throws IffException
   {
      ByteBuffer buffer;

      buffer = ByteBuffer.wrap(signal.getSigData());
      buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
      try
      {
         int pid = buffer.getInt();
         int bid = buffer.getInt();
         int sid = buffer.getInt();
         return new Process(sid, bid, pid);
      }
      catch (BufferUnderflowException e)
      {
         throw new IffException("Invalid process signal in dump file");
      }
   }

   private int getCpuType(DumpFile dumpFile)
      throws IOException
   {
      int cpuType = CPU_UNKNOWN;
      boolean bigEndian;
      List signalBlocks;

      bigEndian = dumpFile.isBigEndian();
      signalBlocks = dumpFile.getSignalBlocks();
      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock = (SignalBlock) i.next();
         int reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MONITOR_INTERFACE_REQUEST)
         {
            SignalInputStream in = null;

            try
            {
               Signal signal;

               in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
               signal = readSignal(in);
               if (signal.getSigNo() != MONITOR_INTERFACE_REQUEST)
               {
                  throw new IffException("Invalid request signal in " +
                     "MonitorInterface signal block in dump file");
               }

               signal = readSignal(in);
               if (signal.getSigNo() == MONITOR_INTERFACE_REPLY)
               {
                  ByteBuffer buffer;
   
                  buffer = ByteBuffer.wrap(signal.getSigData());
                  buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
                  if (buffer.limit() > 15)
                  {
                     cpuType = buffer.getInt(12);
                  }
                  else
                  {
                     throw new IffException("Invalid MonitorInterfaceReply " +
                        "signal in dump file");
                  }
               }
               else
               {
                  throw new IffException("Invalid reply signal in " +
                     "MonitorInterface signal block in dump file");
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

      return cpuType;
   }

   private Signal readSignal(SignalInputStream in)
      throws IOException
   {
      int sigSize;
      int sigNo;
      byte[] sigData;

      sigSize = in.readS32();
      if (sigSize < 4)
      {
         throw new IffException("Invalid signal size in dump file");
      }
      sigNo = in.readS32();
      sigData = new byte[sigSize - 4];
      in.readFully(sigData);
      in.align(4);
      return new Signal(sigSize, sigNo, sigData);
   }

   private String getGDB(int cpuType)
   {
      String gdbName;
      String gdbPath;

      switch (cpuType)
      {
         case CPU_ARM:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_ARM;
            break;
         case CPU_MIPS:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_MIPS;
            break;
         case CPU_PPC:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_POWERPC;
            break;
         default:
            return IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE;
      }

      gdbPath = GDBDebugger.findGDB(gdbName);
      return ((gdbPath != null) ? gdbPath : gdbName);
   }

   private static class Signal
   {
      private final int sigSize;
      private final int sigNo;
      private final byte[] sigData;

      Signal(int sigSize, int sigNo, byte[] sigData)
      {
         this.sigSize = sigSize;
         this.sigNo = sigNo;
         this.sigData = sigData;
      }

      public int getSigSize()
      {
         return sigSize;
      }

      public int getSigNo()
      {
         return sigNo;
      }

      public byte[] getSigData()
      {
         return sigData;
      }
   }

   private static class Process
   {
      private final int sid;
      private final int bid;
      private final int pid;

      Process(int sid, int bid, int pid)
      {
         this.sid = sid;
         this.bid = bid;
         this.pid = pid;
      }

      public int getSid()
      {
         return sid;
      }

      public int getBid()
      {
         return bid;
      }

      public int getPid()
      {
         return pid;
      }
   }
}
