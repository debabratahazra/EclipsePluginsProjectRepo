/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

import com.ose.system.service.monitor.MonitorHeapFragmentInfo;
import com.ose.system.service.monitor.MonitorHeapFragmentInfo64;

/**
 * This class contains information about an OSE heap fragment.
 * A HeapFragmentInfo object is immutable and its content is a snapshot
 * at the time the corresponding Heap object was last updated.
 *
 * @see com.ose.system.Heap#getHeapFragments()
 */
public class HeapFragmentInfo
{
   private final long address;
   private final long size;
   private final long usedSize;
   private final long requestedSize;

   /**
    * Create a new heap fragment info object 32 bit.
    *
    * @param fragment  the internal heap fragment info object.
    */
   HeapFragmentInfo(MonitorHeapFragmentInfo fragment)
   {
      if (fragment == null)
      {
         throw new NullPointerException();
      }
      address = fragment.address;
      size = fragment.size;
      usedSize = fragment.usedSize;
      requestedSize = fragment.reqSize;
   }
   
   /**
    * Create a new heap fragment info object 64 bit.
    *
    * @param fragment  the internal heap fragment info object.
    */
   HeapFragmentInfo(MonitorHeapFragmentInfo64 fragment)
   {
      if (fragment == null)
      {
         throw new NullPointerException();
      }
      address = fragment.address;
      size = fragment.size;
      usedSize = fragment.usedSize;
      requestedSize = fragment.reqSize;
   }

   /**
    * Return the base address of this heap fragment.
    *
    * @return the base address of this heap fragment.
    */
   public long getAddress()
   {
      return address;
   }

   /**
    * Return the size, in bytes, of this heap fragment.
    *
    * @return the size, in bytes, of this heap fragment.
    */
   public long getSize()
   {
      return size;
   }

   /**
    * Return the total actually used size, in bytes, of the heap buffers in this
    * heap fragment.
    *
    * @return the total actually used size, in bytes, of the heap buffers in
    * this heap fragment.
    */
   public long getUsedSize()
   {
      return usedSize;
   }

   /**
    * Return the total requested size, in bytes, of the heap buffers in this
    * heap fragment.
    *
    * @return the total requested size, in bytes, of the heap buffers in this
    * heap fragment.
    */
   public long getRequestedSize()
   {
      return requestedSize;
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
      if (obj instanceof HeapFragmentInfo)
      {
         HeapFragmentInfo other = (HeapFragmentInfo) obj;
         result = ((this.address == other.address) &&
                   (this.size == other.size) &&
                   (this.usedSize == other.usedSize) &&
                   (this.requestedSize == other.requestedSize));
      }
      return result;
   }

   /**
    * Return a string representation of this heap fragment info object. The
    * returned string is of the form "&lt;heap-fragment-address&gt;", where
    * heap-fragment-address is in hexadecimal form.
    *
    * @return a string representation of this heap fragment info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("0x" + Long.toHexString(address).toUpperCase());
   }
}
