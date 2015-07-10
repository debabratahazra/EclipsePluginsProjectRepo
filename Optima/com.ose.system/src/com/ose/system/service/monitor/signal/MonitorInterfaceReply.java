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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorInterfaceReply extends Signal
{
   public static final int SIG_NO = 39001;

   private static final byte[] MONITOR_BIG_ENDIAN_VALUE =
      new byte[] {0x01, 0x02, 0x03, 0x04};

   private static final byte[] MONITOR_LITTLE_ENDIAN_VALUE =
      new byte[] {0x04, 0x03, 0x02, 0x01};

   public boolean endian; // U8[4]
   public int osType;     // U32
   public int cpuClass;   // U32
   public int[] sigs;     // U32[]

   public MonitorInterfaceReply()
   {
      super(SIG_NO);
   }

   /*
    * This method is overridden in order to determine and use
    * the target byte order instead of the passed byte order.
    */
   public void oseToJava(byte[] data, boolean bigEndian) throws IOException
   {
      byte[] byteOrder;
      ByteArrayInputStream bin;
      SignalInputStream in;

      // Signal sanity check.
      if (data.length < 12)
      {
         throw new EOFException("Premature end of signal data.");
      }

      // Skip unused field U32 reserved0 and then read U8[4] field endian.
      byteOrder = new byte[4];
      System.arraycopy(data, 4, byteOrder, 0, 4);
      endian = Arrays.equals(byteOrder, MONITOR_BIG_ENDIAN_VALUE);

      // Read the rest of the signal using the target byte order.
      bin = new ByteArrayInputStream(data, 8, data.length - 8);
      in = new SignalInputStream(bin, endian);
      read(in);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int sigsCount;

      osType = in.readS32();
      cpuClass = in.readS32();
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2
      sigsCount = in.readS32();
      sigs = in.readS32Array(sigsCount);
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(0); // Unused U32 reserved0
      out.writeS8Array(endian ? MONITOR_BIG_ENDIAN_VALUE : MONITOR_LITTLE_ENDIAN_VALUE);
      out.writeS32(osType);
      out.writeS32(cpuClass);
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2
      out.writeS32(sigs.length);
      out.writeS32Array(sigs);
   }
}
