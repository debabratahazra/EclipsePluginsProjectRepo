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
 * The abstract class EmptySignal can be used as a base class for signals
 * without any data fields. The advantage of extending this class is that the
 * abstract methods read() and write() are already implemented. The only thing
 * required for a subclass is to provide a public default constructor.
 * <p>
 * The following is an example of how an OSE signal without any data fields is
 * implemented in Java using the EmptySignal class:
 * <p>
 * <pre>
 * OSE signal:
 *    #define MY_SIG (17)
 *    struct MySignal
 *    {
 *       SIGSELECT sig_no;
 *    };
 *
 * Java signal:
 *    public class MySignal extends EmptySignal
 *    {
 *       // The signal number.
 *       public static final int SIG_NO = 17;
 *
 *       // The public default constructor.
 *       public MySignal()
 *       {
 *          super(SIG_NO);
 *       }
 *    }
 * </pre>
 */
public abstract class EmptySignal extends Signal
{
   /**
    * Subclasses should define a public default constructor that calls this
    * constructor.
    *
    * @param sigNo  the signal number.
    */
   protected EmptySignal(int sigNo)
   {
      super(sigNo);
   }

   /*
    * @see com.ose.gateway.Signal#oseToJava(byte[], boolean)
    */
   public final void oseToJava(byte[] data, boolean bigEndian) throws IOException {}

   /*
    * @see com.ose.gateway.Signal#javaToOse(boolean)
    */
   public final byte[] javaToOse(boolean bigEndian) throws IOException
   {
      return (new byte[0]);
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
