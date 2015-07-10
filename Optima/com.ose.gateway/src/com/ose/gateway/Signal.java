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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * This abstract class is the base class for Java signal classes.
 * <p>
 * In addition to implementing the abstract methods read() and write(),
 * subclasses must provide a public default constructor. The public default
 * constructor is needed in order to register the signal class in a signal
 * registry and to be able to create a Java signal object when the corresponding
 * OSE signal is received.
 * <p>
 * The following is an example of how an OSE signal is implemented in Java using
 * the Signal class:
 * <p>
 * <pre>
 * OSE signal:
 *    #define MY_SIG (17)
 *    struct MySignal
 *    {
 *       SIGSELECT sig_no;
 *       S32 x;
 *       S32 y;
 *    };
 *
 * Java signal:
 *    public class MySignal extends Signal
 *    {
 *       // The signal number.
 *       public static final int SIG_NO = 17;
 *
 *       // The signal's data fields.
 *       public int x;
 *       public int y;
 *
 *       // The public default constructor.
 *       public MySignal()
 *       {
 *          super(SIG_NO);
 *       }
 *
 *       // Read the signal.
 *       protected void read(SignalInputStream in) throws IOException
 *       {
 *          x = in.readS32();
 *          y = in.readS32();
 *       }
 *
 *       // Write the signal.
 *       protected void write(SignalOutputStream out) throws IOException
 *       {
 *          out.writeS32(x);
 *          out.writeS32(y);
 *       }
 *    }
 * </pre>
 */
public abstract class Signal
{
   private final int sigNo;

   private int sender;

   private int addressee;

   private int sigSize;

   /**
    * Subclasses should define a public default constructor that calls this
    * constructor.
    *
    * @param sigNo  the signal number.
    */
   protected Signal(int sigNo)
   {
      this.sigNo = sigNo;
   }

   /**
    * This method should NOT be called by clients.
    *
    * @param sender     the sender of this signal.
    * @param addressee  the addressee of this signal.
    * @param sigSize    the size of this signal in bytes.
    */
   public final void init(int sender, int addressee, int sigSize)
   {
      this.sender = sender;
      this.addressee = addressee;
      this.sigSize = sigSize;
   }

   /**
    * Return the signal number for this signal.
    *
    * @return the signal number for this signal.
    */
   public final int getSigNo()
   {
      return sigNo;
   }

   /**
    * Return the ID of the process that sent this signal. This method is not
    * legal on a signal that has not been received.
    *
    * @return the sender of this signal.
    */
   public final int getSender()
   {
      return sender;
   }

   /**
    * Return the ID of the process that this signal was sent to. This is usually
    * the process that received the signal, except when the signal bounced on
    * another process via a redirection table. This method is not legal on a
    * signal that has not been received.
    *
    * @return the addressee of this signal.
    */
   public final int getAddressee()
   {
      return addressee;
   }

   /**
    * Return the size of this signal in bytes.
    *
    * @return the size of this signal in bytes.
    */
   public final int getSigSize()
   {
      return sigSize;
   }

   // FIXME: Should we really have this method?
   /**
    * Return the data for this signal.
    *
    * @return the data for this signal.
    * @throws IOException  if an I/O exception occurred.
    */
   public final byte[] getData() throws IOException
   {
      return javaToOse(true);
   }

   /**
    * This method is automatically called when this signal is received.
    *
    * @param data  the signal data.
    * @param bigEndian  true if the signal data is in big endian byte order,
    * false if it is in little endian byte order.
    * @throws IOException  if an I/O exception occurred.
    */
   public void oseToJava(byte[] data, boolean bigEndian) throws IOException
   {
      ByteArrayInputStream bin;
      SignalInputStream in;
      int tail;

      bin = new ByteArrayInputStream(data);
      in = new SignalInputStream(bin, bigEndian);
      read(in);
      tail = getSigSize() - 4 - in.getReadPosition();
      if (tail > 0)
      {
         in.skipBytes(tail);
      }
   }

   /**
    * This method is automatically called when this signal is sent.
    *
    * @param bigEndian  true if the returned signal data should be in big endian
    * byte order, false if it should be in little endian byte order.
    * @return the data for this signal.
    * @throws IOException  if an I/O exception occurred.
    */
   public byte[] javaToOse(boolean bigEndian) throws IOException
   {
      ByteArrayOutputStream bout;
      SignalOutputStream out;

      bout = new ByteArrayOutputStream();
      out = new SignalOutputStream(bout, bigEndian);
      write(out);
      out.flush();
      return bout.toByteArray();
   }

   /**
    * Read the data of this signal from the specified signal input stream.
    * This method must be implemented by subclasses.
    *
    * @param in  the signal input stream.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract void read(SignalInputStream in) throws IOException;

   /**
    * Write the data of this signal to the specified signal output stream.
    * This method must be implemented by subclasses.
    *
    * @param out  the signal output stream.
    * @throws IOException  if an I/O exception occurred.
    */
   protected abstract void write(SignalOutputStream out) throws IOException;

   /**
    * Return a string representation of this signal object. Java reflection is
    * used to print the fields of the signal.
    *
    * @return a string representation of this signal object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer buffer = new StringBuffer(100);
      Class<? extends Signal> cls = getClass();
      String clsName = cls.getName();
      Field[] fields = cls.getFields();

      buffer.append(clsName + "\n");
      for (int i = 0; i < fields.length; i++)
      {
         Field field = fields[i];
         String name = field.getName();

         buffer.append(name + ": ");
         try
         {
            Object value = field.get(this);
            Class<?> type = field.getType();

            if ((value != null) && type.isArray())
            {
               int length = Array.getLength(value);
               for (int j = 0; j < length; j++)
               {
                  Object component = Array.get(value, j);
                  buffer.append(component + ", ");
               }
               buffer.append("\n");
            }
            else
            {
               buffer.append(value + "\n");
            }
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }
      }

      return buffer.toString();
   }
}
