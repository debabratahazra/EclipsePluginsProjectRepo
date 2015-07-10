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

import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;

/**
 * This class contains information about a configured OSE pool buffer size.
 * A PoolBufferSizeInfo object is immutable and its content is a snapshot at the
 * time the corresponding Pool object was last updated.
 *
 * @see com.ose.system.Pool#getStackSizes()
 * @see com.ose.system.Pool#getSignalSizes()
 */
public class PoolBufferSizeInfo
{
   private final int size;
   private final int allocated;
   private final int free;

   /**
    * Create a new pool buffer size info object.
    *
    * @param bufferSizeInfo  the internal pool buffer size info object.
    */
   PoolBufferSizeInfo(MonitorPoolBufferSizeInfo bufferSizeInfo)
   {
      if (bufferSizeInfo == null)
      {
         throw new NullPointerException();
      }
      size = bufferSizeInfo.size;
      allocated = bufferSizeInfo.allocated;
      free = bufferSizeInfo.free;
   }

   /**
    * Return this configured pool buffer size in bytes.
    *
    * @return this configured pool buffer size in bytes.
    */
   public int getSize()
   {
      return size;
   }

   /**
    * Return the number of allocated pool buffers of this configured pool buffer
    * size.
    *
    * @return the number of allocated pool buffers of this configured pool
    * buffer size.
    */
   public int getAllocated()
   {
      return allocated;
   }

   /**
    * Return the number of free pool buffers of this configured pool buffer
    * size.
    *
    * @return the number of free pool buffers of this configured pool buffer
    * size.
    */
   public int getFree()
   {
      return free;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (size ^ allocated ^ free);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof PoolBufferSizeInfo)
      {
         PoolBufferSizeInfo other = (PoolBufferSizeInfo) obj;
         result = ((this.size == other.size) &&
                   (this.allocated == other.allocated) &&
                   (this.free == other.free));
      }
      return result;
   }

   /**
    * Return a string representation of this pool buffer size info object.
    * The returned string is of the form
    * "Size: &lt;size&gt;, Allocated: &lt;allocated&gt;, Free: &lt;free&gt;".
    *
    * @return a string representation of this pool buffer size info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Size: " + size + ", Allocated: " + allocated + ", Free: " + free);
   }
}
