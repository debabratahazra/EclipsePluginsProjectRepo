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

import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo;
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo64;

/**
 * This class contains information about an OSE heap buffer size.
 * A HeapBufferSizeInfo object is immutable and its content is a snapshot
 * at the time the corresponding Heap object was last updated.
 *
 * @see com.ose.system.Heap#getHeapBufferSizes()
 */
public class HeapBufferSizeInfo
{
   private final long size;
   private final long allocated;
   private final long free;

   /**
    * Create a new heap buffer size info object 32 bit.
    *
    * @param bufferSizeInfo  the internal heap buffer size info object.
    */
   HeapBufferSizeInfo(MonitorHeapBufferSizeInfo bufferSizeInfo)
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
    * Create a new heap buffer size info object 64 bit.
    *
    * @param bufferSizeInfo  the internal heap buffer size info object.
    */
   HeapBufferSizeInfo(MonitorHeapBufferSizeInfo64 bufferSizeInfo)
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
    * Return this heap buffer size in bytes.
    *
    * @return this heap buffer size in bytes.
    */
   public long getSize()
   {
      return size;
   }

   /**
    * Return the number of allocated heap buffers of this heap buffer size.
    *
    * @return the number of allocated heap buffers of this heap buffer size.
    */
   public long getAllocated()
   {
      return allocated;
   }

   /**
    * Return the number of free heap buffers of this heap buffer size.
    *
    * @return the number of free heap buffers of this heap buffer size.
    */
   public long getFree()
   {
      return free;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (int)(size ^ allocated ^ free);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof HeapBufferSizeInfo)
      {
         HeapBufferSizeInfo other = (HeapBufferSizeInfo) obj;
         result = ((this.size == other.size) &&
                   (this.allocated == other.allocated) &&
                   (this.free == other.free));
      }
      return result;
   }

   /**
    * Return a string representation of this heap buffer size info object.
    * The returned string is of the form
    * "Size: &lt;size&gt;, Allocated: &lt;allocated&gt;, Free: &lt;free&gt;".
    *
    * @return a string representation of this heap buffer size info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Size: " + size + ", Allocated: " + allocated + ", Free: " + free);
   }
}
