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

package com.ose.system;

import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo64;

/**
 * This class contains information about an OSE pool fragment.
 * A PoolFragmentInfo object is immutable and its content is a snapshot at the
 * time the corresponding Pool object was last updated.
 *
 * @see com.ose.system.Pool#getPoolFragments()
 */
public class PoolFragmentInfo
{
   private final long address;
   private final long size;
   private final long usedStacks;
   private final long usedSignals;

   /**
    * Create a new 32 bit pool fragment info object.
    *
    * @param fragment  the internal pool fragment info object.
    */
   PoolFragmentInfo(MonitorPoolFragmentInfo fragment)
   {
      if (fragment == null)
      {
         throw new NullPointerException();
      }
      address = fragment.address;
      size = fragment.size;
      usedStacks = fragment.stacks;
      usedSignals = fragment.signals;
   }
   
   /**
    * Create a new 64 bit pool fragment info object.
    *
    * @param fragment  the internal pool fragment info object.
    */
   PoolFragmentInfo(MonitorPoolFragmentInfo64 fragment)
   {
      if (fragment == null)
      {
         throw new NullPointerException();
      }
      address = fragment.address;
      size = fragment.size;
      usedStacks = fragment.stacks;
      usedSignals = fragment.signals;
   }

   /**
    * Return the base address of this pool fragment.
    *
    * @return the base address of this pool fragment.
    */
   public long getAddress()
   {
      return address;
   }

   /**
    * Return the size, in bytes, of this pool fragment.
    *
    * @return the size, in bytes, of this pool fragment.
    */
   public long getSize()
   {
      return size;
   }

   /**
    * Return the total size, in bytes, of the used stack buffers in this pool
    * fragment.
    *
    * @return the total size, in bytes, of the used stack buffers in this pool
    * fragment.
    */
   public long getUsedStacks()
   {
      return usedStacks;
   }

   /**
    * Return the total size, in bytes, of the used signal buffers in this pool
    * fragment.
    *
    * @return the total size, in bytes, of the used signal buffers in this pool
    * fragment.
    */
   public long getUsedSignals()
   {
      return usedSignals;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (int)address;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof PoolFragmentInfo)
      {
         PoolFragmentInfo other = (PoolFragmentInfo) obj;
         result = ((this.address == other.address) &&
                   (this.size == other.size) &&
                   (this.usedStacks == other.usedStacks) &&
                   (this.usedSignals == other.usedSignals));
      }
      return result;
   }

   /**
    * Return a string representation of this pool fragment info object. The
    * returned string is of the form "&lt;pool-fragment-address&gt;", where
    * pool-fragment-address is in hexadecimal form.
    *
    * @return a string representation of this pool fragment info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("0x" + Long.toHexString(address).toUpperCase());
   }
}
