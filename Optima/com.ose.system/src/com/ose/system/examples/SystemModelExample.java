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

package com.ose.system.examples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.SystemModel;
import com.ose.system.Target;

public class SystemModelExample
{
   public static void run(InetAddress address, int port) throws IOException
   {
      IProgressMonitor progressMonitor;
      Gate[] gates;
      Gate gate = null;
      Target[] targets;
      Target target;
      long begin;
      long end;

      progressMonitor = new NullProgressMonitor();

      System.out.println("Finding gates...");
      SystemModel.getInstance().update(progressMonitor, false);
      gates = SystemModel.getInstance().getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate g = gates[i];
         System.out.println("Found gate: " + g);
         if (g.getAddress().equals(address) && (g.getPort() == port))
         {
            gate = g;
         }
      }
      if (gate == null)
      {
         System.out.println("Adding a gate.");
         gate = SystemModel.getInstance().addGate(address, port);
         if (gate == null)
         {
            System.err.println("Specified gate not found.");
            return;
         }
      }

      targets = gate.getTargets();
      if (targets.length <= 0)
      {
         System.err.println("Specified gate not running on an OSE target.");
         return;
      }

      System.out.println("Connecting to target.");
      target = targets[0];
      target.connect(progressMonitor);

      System.out.println("Benchmarking full target update...");
      begin = System.currentTimeMillis();
      target.update(progressMonitor, true);
      end = System.currentTimeMillis();
      System.out.println("Time for full target update: " + (end - begin) + " ms");

      System.out.println("System snapshot:");
      printTarget(target);

      System.out.println("Disconnecting from target.");
      target.disconnect();
   }

   private static void printTarget(Target target)
   {
      System.out.println("Target: " + target);
      System.out.println("\tKilled: " + target.isKilled());
      System.out.println("\tBig endian: " + target.isBigEndian());
      System.out.println("\tOS: " + target.getOsType());
      System.out.println("\tCPU: " + target.getCpuType());

      Pool[] pools = target.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         System.out.println("\tPool: " + pool.getId());
         System.out.println("\t\tKilled: " + pool.isKilled());
         System.out.println("\t\tSegment id: " + pool.getSid());
         System.out.println("\t\tTotal size: " + pool.getTotalSize());
         System.out.println("\t\tFree size: " + pool.getFreeSize());
      }

      Block[] blocks = target.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         System.out.println("\tBlock: " + block.getName() + " (" + block.getId() + ")");
         System.out.println("\t\tKilled: " + block.isKilled());
         System.out.println("\t\tSegment id: " + block.getSid());
         System.out.println("\t\tUser id: " + block.getUid());
         System.out.println("\t\tSupervisor mode: " + block.isSupervisor());
         System.out.println("\t\tMax signal size: " + block.getMaxSignalSize());
         System.out.println("\t\tSignal pool id: " + block.getSignalPoolId());
         System.out.println("\t\tStack pool id: " + block.getStackPoolId());
         System.out.println("\t\tNumber of attached signals: " +
                            block.getNumSignalsAttached());
         System.out.println("\t\tError handler: " + block.getErrorHandler());

         Process[] processes = block.getProcesses();
         for (int j = 0; j < processes.length; j++)
         {
            Process process = processes[j];
            System.out.println("\t\tProcess: " + process.getName() + " (" +
                               process.getId() + ")");
            System.out.println("\t\t\tKilled: " + process.isKilled());
            System.out.println("\t\t\tSegment id: " + process.getSid());
            System.out.println("\t\t\tBlock id: " + process.getBid());
            System.out.println("\t\t\tUser id: " + process.getUid());
            System.out.println("\t\t\tType: " + process.getType());
            System.out.println("\t\t\tCreator id: " + process.getCreator());
            System.out.println("\t\t\tEntrypoint: " + process.getEntrypoint());
            System.out.println("\t\t\tStack size: " + process.getStackSize());
            System.out.println("\t\t\tSupervisor stack size: " +
                               process.getSupervisorStackSize());
            System.out.println("\t\t\tTime slice: " + process.getTimeSlice());
            System.out.println("\t\t\tVector: " + process.getVector());
            System.out.println("\t\t\tSupervisor mode: " + process.isSupervisor());
            System.out.println("\t\t\tState: " + process.getState());
            System.out.println("\t\t\tPriority: " + process.getPriority());
            System.out.println("\t\t\tNumber of signals in queue: " +
                               process.getNumSignalsInQueue());
            System.out.println("\t\t\tNumber of attached signals: " +
                               process.getNumSignalsAttached());
            System.out.println("\t\t\tNumber of owned signals: " +
                               process.getNumSignalsOwned());
            System.out.println("\t\t\tFast semaphore value: " +
                               process.getSemaphoreValue());
            System.out.println("\t\t\tError handler: " + process.getErrorHandler());
            System.out.println("\t\t\tSignal select: " +
                               intArrayToString(process.getSigselect()));
            System.out.println("\t\t\tFilename: " + process.getFileName());
            System.out.println("\t\t\tLine number: " + process.getLineNumber());
         }
      }
   }

   private static String intArrayToString(int[] array)
   {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      for (int i = 0; i < array.length; i++)
      {
         sb.append(array[i] + ",");
      }
      sb.append("]");
      return sb.toString();
   }

   public static void main(String[] args)
   {
      InetAddress address = null;
      int port;

      if (args.length != 2)
      {
         System.out.println("Usage: SystemModelExample <address> <port>");
         return;
      }

      try
      {
         address = InetAddress.getByName(args[0]);
      }
      catch (UnknownHostException e)
      {
         System.err.println("Invalid address.");
         return;
      }
      try
      {
         port = Integer.parseInt(args[1]);
      }
      catch (NumberFormatException e)
      {
         System.err.println("Invalid port number.");
         return;
      }

      try
      {
         run(address, port);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
