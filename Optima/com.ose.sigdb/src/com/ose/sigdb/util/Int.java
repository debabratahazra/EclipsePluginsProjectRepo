/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.sigdb.util;

public class Int
{
   public final static String toString(int _value)
   {
      return Integer.toString(_value);
   }

   protected int value;

   public Int(int _value)
   {
      value = _value;
   }

   public final byte byteValue()
   {
      return (byte) value;
   }

   public final void dec()
   {
      value--;
   }

   public final void dec(int d)
   {
      value -= d;
   }

   public final void inc()
   {
      value++;
   }

   public final void inc(int i)
   {
      value += i;
   }

   public final int intValue()
   {
      return value;
   }

   public final long longValue()
   {
      return (long) value;
   }

   public final short shortValue()
   {
      return (short) value;
   }

   public String toString()
   {
      return toString(value);
   }
}
