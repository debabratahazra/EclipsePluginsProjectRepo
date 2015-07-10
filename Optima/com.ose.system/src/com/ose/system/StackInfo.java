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

import com.ose.system.service.monitor.MonitorStackInfo;

/**
 * This class contains information about an OSE process stack.
 * A StackInfo object is immutable and its content is a snapshot
 * at the time of the retrieving call.
 *
 * @see com.ose.system.Pool#getFilteredPoolStacks(IProgressMonitor, StackFilter)
 */
public class StackInfo
{
   private final int pid;
   private final int address;
   private final int size;
   private int bufferSize; // final
   private final int used;

   /**
    * Create a new stack info object.
    *
    * @param pid    the owner process ID of this stack.
    * @param stack  the internal stack info object.
    */
   StackInfo(int pid, MonitorStackInfo stack)
   {
      if (stack == null)
      {
         throw new NullPointerException();
      }
      this.pid = pid;
      this.address = stack.address;
      this.size = stack.size;
      this.bufferSize = stack.bufsize;
      this.used = stack.used;
   }

   /**
    * Set the actual buffer size of this stack.
    * This method should only be used when the MonitorStackInfo.bufsize
    * field is not filled in by the OSE monitor, i.e. in OSE 5.1.1.
    *
    * @param configuredSizes  an array of the configured stack sizes for this
    * stack's pool.
    * @param stackAdmSize     the stack administration size for this stack's
    * pool.
    */
   void setBufferSize(PoolBufferSizeInfo[] configuredSizes, int stackAdmSize)
   {
      int confSize;
      for (int i = 0; i < configuredSizes.length; i++)
      {
         confSize = configuredSizes[i].getSize();
         if (size <= confSize)
         {
            bufferSize = confSize + stackAdmSize;
            break;
         }
      }
   }

   /**
    * Return the owner process ID of this stack.
    *
    * @return the owner process ID of this stack.
    */
   public int getPid()
   {
      return pid;
   }

   /**
    * Return the address of this stack.
    *
    * @return the address of this stack.
    */
   public int getAddress()
   {
      return address;
   }

   /**
    * Return the size of this stack.
    *
    * @return the size of this stack.
    */
   public int getSize()
   {
      return size;
   }

   /**
    * Return the actual buffer size of this stack.
    *
    * @return the actual buffer size of this stack.
    */
   public int getBufferSize()
   {
      return bufferSize;
   }

   /**
    * Return the peak usage of this stack.
    *
    * @return the peak usage of this stack.
    */
   public int getUsed()
   {
      return used;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (pid ^ address ^ size ^ bufferSize ^ used);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object o)
   {
      boolean result = false;
      if (o instanceof StackInfo)
      {
         StackInfo other = (StackInfo) o;
         result = ((pid == other.pid) &&
                   (address == other.address) &&
                   (size == other.size) &&
                   (bufferSize == other.bufferSize) &&
                   (used == other.used));
      }
      return result;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Pid: 0x" + Integer.toHexString(pid).toUpperCase() +
              ", Address: 0x" + Integer.toHexString(address).toUpperCase() +
              ", Size: " + size +
              ", Buffer size: " + bufferSize +
              ", Used: " + used);
   }
}
