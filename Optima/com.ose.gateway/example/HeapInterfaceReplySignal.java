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

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

/**
 * An example demonstrating how to use the com.ose.gateway.Signal class when
 * defining a signal class.
 *
 * @author steby
 */
public class HeapInterfaceReplySignal extends Signal
{
   /** The signal number. */
   public static final int SIG_NO = 33301;

   /** The signal data. */
   public int status;     // U32
   public String whatStr; // char[32]
   public int biosHandle; // U32
   public int[] sigs;     // U32[]

   /**
    * Create a new HeapInterfaceReplySignal object.
    */
   public HeapInterfaceReplySignal()
   {
      super(SIG_NO);
   }

   /**
    * Create a new HeapInterfaceReplySignal object.
    *
    * @param  status      The reply status.
    * @param  whatStr     The what string.
    * @param  biosHandle  The BIOS handle.
    * @param  sigs        An array of supported signal types.
    */
   public HeapInterfaceReplySignal(int status, String whatStr,
                                   int biosHandle, int[] sigs)
   {
      super(SIG_NO);
      this.status = status;
      this.whatStr = whatStr;
      this.biosHandle = biosHandle;
      this.sigs = sigs;
   }

   /**
    * Copy all data fields from the native signal to the Java signal.
    */
   protected void read(SignalInputStream in) throws IOException
   {
      int sigsLength;

      status = in.readS32();
      whatStr = in.readString(32);
      biosHandle = in.readS32();
      sigsLength = in.readS32();
      sigs = in.readS32Array(sigsLength);
   }

   /**
    * Copy all data fields from the Java signal to the native signal.
    */
   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(status);
      out.writeString(whatStr, 32);
      out.writeS32(biosHandle);
      out.writeS32(sigs.length);
      out.writeS32Array(sigs);
   }
}
