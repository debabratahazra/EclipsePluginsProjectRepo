/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.system;

/**
 * This class represents an OSE event trace handle. An event trace handle is
 * used as a location cursor when reading a target's event trace with
 * Target.getEventTraceMultiple().
 * <p>
 * For the first call of Target.getEventTraceMultiple() in a sequence of related
 * calls, pass a newly created event trace handle object to read the oldest
 * events in the event trace. Subsequent calls shall use the same event trace
 * handle object in order to get only new events that has been written to the
 * event trace.
 *
 * @see com.ose.system.Target#getEventTraceMultiple(IProgressMonitor, int,
 * EventTraceHandle)
 */
public class EventTraceHandle
{
   private volatile int handle;

   /**
    * Create a new event trace handle object.
    */
   public EventTraceHandle() {}

   /**
    * Return the handle of this event trace handle object.
    *
    * @return the handle of this event trace handle object.
    */
   int getHandle()
   {
      return handle;
   }

   /**
    * Set the handle of this event trace handle object.
    *
    * @param handle  the handle to be set.
    */
   void setHandle(int handle)
   {
      this.handle = handle;
   }

   /**
    * Return a string representation of this event trace handle object.
    *
    * @return a string representation of this event trace handle object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return Integer.toString(handle);
   }
}
