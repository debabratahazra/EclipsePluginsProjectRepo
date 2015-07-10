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

import java.util.Arrays;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorSignalInfo;

/**
 * This class contains information about an OSE signal.
 * A SignalInfo object is immutable. If a SignalInfo object is returned from
 * Pool.getFilteredPoolSignals(), its content is a snapshot at the time of that
 * call. If a SignalInfo object is returned from Process.getSignalQueue(), its
 * content is a snapshot at the time the corresponding Process object was last
 * updated.
 *
 * @see com.ose.system.Pool#getFilteredPoolSignals(IProgressMonitor, SignalFilter)
 * @see com.ose.system.Process#getSignalQueue()
 */
public class SignalInfo
{
   /** Signal buffer with unknown status. */
   public static final int STATUS_UNKNOWN = Monitor.MONITOR_SIGNAL_STATUS_UNKNOWN;

   /** Status for a valid signal buffer. */
   public static final int STATUS_VALID = Monitor.MONITOR_SIGNAL_STATUS_VALID;

   /** Status for a signal buffer with a broken endmark. */
   public static final int STATUS_ENDMARK = Monitor.MONITOR_SIGNAL_STATUS_ENDMARK;

   /** Status for a signal buffer with a broken administration block. */
   public static final int STATUS_ADMINISTRATION = Monitor.MONITOR_SIGNAL_STATUS_ADMINISTRATION;

   /** Status for a buffer that is not a signal buffer. */
   public static final int STATUS_NOT_A_SIGNAL = Monitor.MONITOR_SIGNAL_STATUS_NOT_A_SIGNAL;

   /** Status for a buffer that is not located in a pool. */
   public static final int STATUS_NOT_A_POOL = Monitor.MONITOR_SIGNAL_STATUS_NOT_A_POOL;

   /** Status for a signal buffer that is in a free list. */
   public static final int STATUS_FREE = Monitor.MONITOR_SIGNAL_STATUS_FREE;

   private final int sigNo;
   private final int owner;
   private final int sender;
   private final int addressee;
   private final int size;
   private final int bufferSize;
   private final int address;
   private final int status;
   private final byte[] data;

   /**
    * Create a new signal info object.
    *
    * @param signal  the internal signal info object.
    * @param data    the signal data or null if empty.
    */
   SignalInfo(MonitorSignalInfo signal, byte[] data)
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }
      this.sigNo = signal.number;
      this.owner = signal.owner;
      this.sender = signal.sender;
      this.addressee = signal.addressee;
      this.size = signal.size;
      this.bufferSize = signal.bufsize;
      this.address = signal.address;
      this.status = signal.status;
      this.data = data;
   }

   /**
    * Return the signal number of this signal.
    *
    * @return the signal number of this signal.
    */
   public int getSigNo()
   {
      return sigNo;
   }

   /**
    * Return the owner process ID of this signal.
    *
    * @return the owner process ID of this signal.
    */
   public int getOwner()
   {
      return owner;
   }

   /**
    * Return the sender process ID of this signal.
    *
    * @return the sender process ID of this signal.
    */
   public int getSender()
   {
      return sender;
   }

   /**
    * Return the addressee process ID of this signal.
    *
    * @return the addressee process ID of this signal.
    */
   public int getAddressee()
   {
      return addressee;
   }

   /**
    * Return the size of this signal.
    *
    * @return the size of this signal.
    */
   public int getSize()
   {
      return size;
   }

   /**
    * Return the actual buffer size of this signal.
    *
    * @return the actual buffer size of this signal.
    */
   public int getBufferSize()
   {
      return bufferSize;
   }

   /**
    * Return the buffer address of this signal.
    *
    * @return the buffer address of this signal.
    */
   public int getAddress()
   {
      return address;
   }

   /**
    * Return the status of this signal.
    *
    * @return the status of this signal.
    * @see #STATUS_UNKNOWN
    * @see #STATUS_VALID
    * @see #STATUS_ENDMARK
    * @see #STATUS_ADMINISTRATION
    * @see #STATUS_NOT_A_SIGNAL
    * @see #STATUS_NOT_A_POOL
    * @see #STATUS_FREE
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * Return the data of this signal as an array of bytes. A new array is
    * created and returned for each call. If the signal has no data an empty
    * byte array is returned.
    *
    * @return the data of this signal as an array of bytes.
    */
   public byte[] getData()
   {
      // Defensive copy.
      return ((data != null) ? (byte[]) data.clone() : new byte[0]);
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return (sigNo ^ owner ^ sender ^ addressee ^ size ^ bufferSize ^
              address ^ status);
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object o)
   {
      boolean result = false;
      if (o instanceof SignalInfo)
      {
         SignalInfo other = (SignalInfo) o;
         result = ((sigNo == other.sigNo) &&
                   (owner == other.owner) &&
                   (sender == other.sender) &&
                   (addressee == other.addressee) &&
                   (size == other.size) &&
                   (bufferSize == other.bufferSize) &&
                   (address == other.address) &&
                   (status == other.status) &&
                   Arrays.equals(data, other.data));
      }
      return result;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("SigNo: 0x" + Integer.toHexString(sigNo).toUpperCase() +
              ", Owner: 0x" + Integer.toHexString(owner).toUpperCase() +
              ", Sender: 0x" + Integer.toHexString(sender).toUpperCase() +
              ", Addressee: 0x" + Integer.toHexString(addressee).toUpperCase() +
              ", Size: " + size +
              ", Buffer size: " + bufferSize +
              ", Address: 0x" + Integer.toHexString(address).toUpperCase() +
              ", Status: " + status);
   }
}
