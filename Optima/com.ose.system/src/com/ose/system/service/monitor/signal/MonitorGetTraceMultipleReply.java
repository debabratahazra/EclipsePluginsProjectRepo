/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;

public class MonitorGetTraceMultipleReply extends Signal
{
   public static final int SIG_NO = 39212;

   private static final SignalRegistry registry = new SignalRegistry();

   public int status;      // U32
   public int handle;      // U32
   public boolean enabled; // U32
   public Signal[] events; // MonitorEventInfo[]

   static
   {
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
   }

   public MonitorGetTraceMultipleReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      boolean bigEndian;
      List eventsList;
      int eventsCount;

      bigEndian = in.isBigEndian();
      eventsList = new ArrayList();
      status = in.readS32();
      handle = in.readS32();
      in.readS32(); // Unused U32 reserved0
      enabled = in.readBoolean();
      eventsCount = in.readS32();

      while (eventsCount > 0)
      {
         int size;
         int sigNo;
         int sigDataSize;
         byte[] sigData;
         Signal sig;

         size = in.readS32();
         in.readS32(); // Unused U32 MonitorEventInfo.reserved0
         sigNo = in.readS32();
         sigDataSize = (size - 3) * 4;
         sigData = in.readS8Array(sigDataSize);
         sig = createEventSignal(sigDataSize + 4, sigNo, sigData, bigEndian);
         eventsList.add(sig);
         eventsCount -= size;
      }

      events = (Signal[]) eventsList.toArray(new Signal[0]);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      boolean bigEndian;
      int numEvents;
      byte[][] sigDataList;
      int eventsCount;

      bigEndian = out.isBigEndian();
      numEvents = ((events != null) ? events.length : 0);
      sigDataList = new byte[numEvents][];
      eventsCount = 0;
      out.writeS32(status);
      out.writeS32(handle);
      out.writeS32(0); // Unused U32 reserved0
      out.writeBoolean(enabled);
      for (int i = 0; i < numEvents; i++)
      {
         byte[] sigData = events[i].javaToOse(bigEndian);
         sigDataList[i] = sigData;
         eventsCount += (sigData.length / 4) + 3;
      }
      out.writeS32(eventsCount);

      for (int i = 0; i < numEvents; i++)
      {
         Signal sig;
         byte[] sigData;
         int size;
         int sigNo;

         sig = events[i];
         sigData = sigDataList[i];
         size = (sigData.length / 4) + 3;
         sigNo = sig.getSigNo();

         out.writeS32(size);
         out.writeS32(0); // Unused U32 MonitorEventInfo.reserved0
         out.writeS32(sigNo);
         out.writeS8Array(sigData);
      }
   }

   private static Signal createEventSignal(int sigSize,
                                           int sigNo,
                                           byte[] sigData,
                                           boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if (sigData == null)
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
      sig.init(0, 0, sigSize);

      // Extract user data from the native signal to the Java signal.
      sig.oseToJava(sigData, bigEndian);

      return sig;
   }
}
