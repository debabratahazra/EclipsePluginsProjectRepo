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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorGetProcessInfoLongReply extends Signal
{
   public static final int SIG_NO = 39012;

   public int pid;                 // U32
   public int bid;                 // U32
   public int sid;                 // U32
   public int type;                // U32
   public int state;               // U32
   public int priority;            // U32
   public int uid;                 // U32
   public int creator;             // U32
   public boolean supervisor;      // U32
   public int signalsInQueue;      // U32
   public int signalsAttached;     // U32
   public int entrypoint;          // U32
   public int semaphoreValue;      // S32
   public int stackSize;           // U32
   public int supervisorStackSize; // U32
   public int lineNumber;          // U32
   public int signalsOwned;        // U32
   public int timeSlice;           // U32
   public int vector;              // U32
   public int errorHandler;        // U32
   public int properties;          // U32
   public short euId;              // U16
   public int reserved2;           // U32
   public int[] sigselect;         // U32[]
   public String processName;      // char[]
   public String fileName;         // char[]

   public MonitorGetProcessInfoLongReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int sigselectCount; // U32
      int fileNameOffset; // U32

      pid = in.readS32();
      bid = in.readS32();
      sid = in.readS32();
      type = in.readS32();
      state = in.readS32();
      priority = in.readS32();
      uid = in.readS32();
      creator = in.readS32();
      supervisor = in.readBoolean();
      signalsInQueue = in.readS32();
      signalsAttached = in.readS32();
      entrypoint = in.readS32();
      semaphoreValue = in.readS32();
      stackSize = in.readS32();
      supervisorStackSize = in.readS32();
      lineNumber = in.readS32();
      signalsOwned = in.readS32();
      timeSlice = in.readS32();
      vector = in.readS32();
      errorHandler = in.readS32();
      properties = in.readS32();
      euId = in.readS16();
      in.readS16(); // Unused U16 reserved1
      reserved2 = in.readS32();
      in.readS32(); // Unused U32 sigselectOffset
      sigselectCount = in.readS32();
      in.readS32(); // Unused U32 processNameOffset
      fileNameOffset = in.readS32();
      sigselect = in.readS32Array(sigselectCount);
      processName = in.readString();
      fileName = ((fileNameOffset > 0) ? in.readString() : "");
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int sigselectCount;    // U32
      int processNameLength; // U32

      sigselectCount = ((sigselect != null) ? sigselect.length : 0);
      processNameLength = ((processName != null) ? (processName.length() + 1) : 1);

      out.writeS32(pid);
      out.writeS32(bid);
      out.writeS32(sid);
      out.writeS32(type);
      out.writeS32(state);
      out.writeS32(priority);
      out.writeS32(uid);
      out.writeS32(creator);
      out.writeBoolean(supervisor);
      out.writeS32(signalsInQueue);
      out.writeS32(signalsAttached);
      out.writeS32(entrypoint);
      out.writeS32(semaphoreValue);
      out.writeS32(stackSize);
      out.writeS32(supervisorStackSize);
      out.writeS32(lineNumber);
      out.writeS32(signalsOwned);
      out.writeS32(timeSlice);
      out.writeS32(vector);
      out.writeS32(errorHandler);
      out.writeS32(properties);
      out.writeS16(euId);
      out.writeS16((short) 0); // Unused U16 reserved1
      out.writeS32(reserved2);
      out.writeS32(0); // U32 sigselectOffset
      out.writeS32(sigselectCount);
      out.writeS32(sigselectCount); // U32 processNameOffset
      out.writeS32(sigselectCount + processNameLength); // U32 fileNameOffset
      if (sigselectCount > 0)
      {
         out.writeS32Array(sigselect);
      }
      out.writeString((processName != null) ? processName : "");
      out.writeString((fileName != null) ? fileName : "");
   }
}
