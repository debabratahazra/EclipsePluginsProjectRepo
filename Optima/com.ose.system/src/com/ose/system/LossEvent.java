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
 * This class represents a loss event from an OSE target. A loss event is
 * reported when the target has thrown event notifications due to flow control.
 * An unknown number of events are missing.
 * <p>
 * Note that getFileName() and getLineNumber() are not applicable for loss
 * events.
 *
 * @see com.ose.system.TargetEvent
 */
public class LossEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final int lostSize;

   /**
    * Create a new loss event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param lostSize    an estimate of the number of bytes lost.
    */
   LossEvent(Object target,
             int tickLength,
             int reference,
             int tick,
             int ntick,
             int sec,
             int sectick,
             int lostSize)
   {
      super(target, tickLength, "Loss Event", reference, tick, ntick, sec, sectick, 0, "");
      this.lostSize = lostSize;
   }

   /**
    * Create a new loss event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param lostSize    an estimate of the number of bytes lost.
    * @return a new loss event object for off-line usage.
    */
   public static LossEvent getInstance(Object target,
                                       int tickLength,
                                       int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int lostSize)
   {
      return new LossEvent(target, tickLength, reference, tick, ntick, sec, sectick, lostSize);
   }

   /**
    * Return an estimate of the number of bytes lost.
    * @return an estimate of the number of bytes lost.
    */
   public int getLostSize()
   {
      return lostSize;
   }

   /**
    * Return a string representation of this loss event object.
    *
    * @return a string representation of this loss event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() + ", Lost bytes: " + lostSize);
   }
}
