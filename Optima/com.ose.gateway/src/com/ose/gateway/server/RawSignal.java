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

package com.ose.gateway.server;

import java.io.IOException;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

/**
 * This class represents a raw signal.
 *
 * @see com.ose.gateway.Signal
 */
public final class RawSignal extends Signal
{
   private byte[] sigData;

   /**
    * Create a new raw signal object with the given signal number.
    *
    * @param sigNo  the signal number.
    */
   public RawSignal(int sigNo)
   {
      super(sigNo);
   }

   /**
    * Initialize the size and data of this raw signal.
    *
    * @param sigSize  the size of this signal in bytes.
    * @param sigData  the data of this signal.
    */
   public void initSignal(int sigSize, byte[] sigData)
   {
      init(getSender(), getAddressee(), sigSize);
      this.sigData = sigData;
   }

   /**
    * Initialize the sender and addressee of this raw signal.
    *
    * @param sender     the sender of this signal.
    * @param addressee  the addressee of this signal.
    */
   public void initTransfer(int sender, int addressee)
   {
      init(sender, addressee, getSigSize());
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.Signal#oseToJava(byte[], boolean)
    */
   public void oseToJava(byte[] data, boolean bigEndian) throws IOException
   {
      sigData = data;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.Signal#javaToOse(boolean)
    */
   public byte[] javaToOse(boolean bigEndian) throws IOException
   {
      return sigData;
   }

   /* (non-Javadoc)
    * @see com.ose.gateway.Signal#read(com.ose.gateway.SignalInputStream)
    */
   protected void read(SignalInputStream in) throws IOException {}

   /* (non-Javadoc)
    * @see com.ose.gateway.Signal#write(com.ose.gateway.SignalOutputStream)
    */
   protected void write(SignalOutputStream out) throws IOException {}
}
