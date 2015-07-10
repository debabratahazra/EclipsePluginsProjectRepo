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
 * This class contains information about OSE target events.
 *
 * @see com.ose.system.TargetEvent
 */
public class TargetEvents
{
   private final Target target;
   private final boolean enabled;
   private final TargetEvent[] events;

   /**
    * Create a new target events object.
    *
    * @param target   the target.
    * @param enabled  the event trace enablement status.
    * @param events   the events.
    */
   TargetEvents(Target target, boolean enabled, TargetEvent[] events)
   {
      if ((target == null) || (events == null))
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.enabled = enabled;
      this.events = events;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Determine whether event tracing is enabled or not.
    *
    * @return true if event tracing is enabled, false otherwise.
    */
   public boolean isEnabled()
   {
      return enabled;
   }

   /**
    * Return an array of the events (never null).
    *
    * @return an array of the events.
    */
   public TargetEvent[] getEvents()
   {
      return events;
   }
}
