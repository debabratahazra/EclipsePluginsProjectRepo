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

import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorHeapBufferInfo;

/**
 * This class contains information about an OSE heap buffer. A HeapBufferInfo
 * object is immutable and its content is a snapshot at the time the retrieving
 * call.
 *
 * @see com.ose.system.Heap#getFilteredHeapBuffers(IProgressMonitor, HeapBufferFilter)
 */
public class HeapBufferInfo
{
   /** Heap buffer with unknown status. */
   public static final int STATUS_UNKNOWN = Monitor.MONITOR_HEAP_BUFFER_STATUS_UNKNOWN;

   /** Status for a valid heap buffer. */
   public static final int STATUS_VALID = Monitor.MONITOR_HEAP_BUFFER_STATUS_VALID;

   /** Status for a heap buffer with a broken endmark. */
   public static final int STATUS_ENDMARK = Monitor.MONITOR_HEAP_BUFFER_STATUS_ENDMARK;

   private final int owner;
   private final boolean shared;
   private final int address;
   private final int size;
   private final int requestedSize;
   private final String fileName;
   private final int lineNumber;
   private final int status;

   /**
    * Create a new heap buffer info object.
    *
    * @param buffer  the internal heap buffer info object.
    */
   HeapBufferInfo(MonitorHeapBufferInfo buffer)
   {
      if (buffer == null)
      {
         throw new NullPointerException();
      }
      this.owner = buffer.owner;
      this.shared = buffer.shared;
      this.address = buffer.address;
      this.size = buffer.size;
      this.requestedSize = buffer.reqSize;
      this.fileName = (buffer.fileName != null) ? buffer.fileName : "";
      this.lineNumber = buffer.lineNumber;
      this.status = buffer.status;
   }

   /**
    * Return the owner process ID of this heap buffer if it is private.
    *
    * @return the owner process ID of this heap buffer if it is private.
    */
   public int getOwner()
   {
      return owner;
   }

   /**
    * Determine whether this heap buffer is shared or not.
    *
    * @return true if this heap buffer is shared, false if it is private.
    */
   public boolean isShared()
   {
      return shared;
   }

   /**
    * Return the address of this heap buffer.
    *
    * @return the address of this heap buffer.
    */
   public int getAddress()
   {
      return address;
   }

   /**
    * Return the actual size (including overhead and internal fragmentation) of
    * this heap buffer in bytes.
    *
    * @return the actual size of this heap buffer in bytes.
    */
   public int getSize()
   {
      return size;
   }

   /**
    * Return the requested size of this heap buffer in bytes.
    *
    * @return the requested size of this heap buffer in bytes.
    */
   public int getRequestedSize()
   {
      return requestedSize;
   }

   /**
    * Return the name of the source file where the allocation of this heap
    * buffer took place or an empty string if not available.
    *
    * @return the name of the source file where the allocation of this heap
    * buffer took place or an empty string if not available.
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * Return the line number in the source file where the allocation of this
    * heap buffer took place or 0 if not available.
    *
    * @return the line number in the source file where the allocation of this
    * heap buffer took place or 0 if not available.
    */
   public int getLineNumber()
   {
      return lineNumber;
   }

   /**
    * Return the status of this heap buffer.
    *
    * @return the status of this heap buffer.
    * @see #STATUS_UNKNOWN
    * @see #STATUS_VALID
    * @see #STATUS_ENDMARK
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (owner ^ (shared ? 1 : 0) ^ address ^ size ^ requestedSize ^
              fileName.hashCode() ^ lineNumber ^ status);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object o)
   {
      boolean result = false;
      if (o instanceof HeapBufferInfo)
      {
         HeapBufferInfo other = (HeapBufferInfo) o;
         result = ((owner == other.owner) &&
                   (shared == other.shared) &&
                   (address == other.address) &&
                   (size == other.size) &&
                   (requestedSize == other.requestedSize) &&
                   fileName.equals(other.fileName) &&
                   (lineNumber == other.lineNumber) &&
                   (status == other.status));
      }
      return result;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Owner: 0x" + Integer.toHexString(owner).toUpperCase() +
              ", Shared: " + shared +
              ", Address: 0x" + Integer.toHexString(address).toUpperCase() +
              ", Size: " + size +
              ", Requested size: " + requestedSize +
              ", File name: " + fileName +
              ", Line number: " + lineNumber +
              ", Status: " + status);
   }
}
