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

package com.ose.gateway;

import java.io.IOException;

/**
 * Objects of the UnregisteredSignal class are created upon reception of OSE
 * signals that are not registered in the used SignalRegistry. The signal
 * data is represented by an array of bytes.
 */
public final class UnregisteredSignal extends Signal
{
   /** The raw signal data. */
   public byte[] data;

   /**
    * Create a new UnregisteredSignal object.
    *
    * @param sigNo  the OSE signal number.
    */
   public UnregisteredSignal(int sigNo)
   {
      super(sigNo);
   }

   /*
    * @see com.ose.gateway.Signal#oseToJava(byte[], boolean)
    */
   public final void oseToJava(byte[] data, boolean bigEndian) throws IOException
   {
      this.data = data;
   }

   /*
    * @see com.ose.gateway.Signal#javaToOse(boolean)
    */
   public final byte[] javaToOse(boolean bigEndian) throws IOException
   {
      return data;
   }

   /*
    * @see com.ose.gateway.Signal#read(com.ose.gateway.SignalInputStream)
    */
   protected final void read(SignalInputStream in) throws IOException {}

   /*
    * @see com.ose.gateway.Signal#write(com.ose.gateway.SignalOutputStream)
    */
   protected final void write(SignalOutputStream out) throws IOException {}
}
