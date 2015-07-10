/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.elf;

public class Symbol implements Comparable
{
   private final String name;
   private final long address;
   private final long size;
   private final boolean isFunction;

   Symbol(String name, long address, long size, boolean isFunction)
   {
      this.name = name;
      this.address = address;
      this.size = size;
      this.isFunction = isFunction;
   }

   public String getName()
   {
      return name;
   }

   public long getAddress()
   {
      return address;
   }

   public long getSize()
   {
      return size;
   }

   public boolean isFunction()
   {
      return isFunction;
   }

   public String toString()
   {
      return name + (isFunction ? "()" : "") + " @ 0x"
         + Long.toHexString(address) + " (" + size + ")";
   }

   public boolean contains(long address)
   {
      if (size == 0)
      {
         return this.address == address;
      }

      long inclusiveLowerBound = this.address;
      long exclusiveUpperBound = this.address + size;

      return !(UnsignedArithmetics.isLessThan(address, inclusiveLowerBound)
            || (address == exclusiveUpperBound)
            || (UnsignedArithmetics.isGreaterThan(address, exclusiveUpperBound)));
   }

   public int compareTo(Object obj)
   {
      if (obj instanceof Symbol)
      {
         Symbol otherSymbol = (Symbol) obj;

         if (address == otherSymbol.getAddress())
         {
            return 0;
         }
         else if (UnsignedArithmetics.isLessThan(address, otherSymbol.getAddress()))
         {
            return -1;
         }
         else
         {
            return 1;
         }
      }
      else if (obj instanceof Long)
      {
         long otherAddress = ((Long) obj).longValue();

         if (contains(otherAddress))
         {
            return 0;
         }
         else if (UnsignedArithmetics.isLessThan(address, otherAddress))
         {
            return -1;
         }
         else
         {
            return 1;
         }
      }
      else
      {
         return 0;
      }
   }
}
