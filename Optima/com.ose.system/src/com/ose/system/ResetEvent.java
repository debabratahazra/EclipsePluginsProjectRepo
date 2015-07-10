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
 * This class represents a reset event from an OSE target. A reset event is
 * reported when the target is resetted.
 *
 * @see com.ose.system.TargetEvent
 */
public class ResetEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final boolean warmReset;

   /**
    * Create a new reset event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param warm        the warm reset flag.
    * @param lineNumber  the line number.
    * @param fileName    the filename.
    */
   ResetEvent(Object target,
              int tickLength,
              int reference,
              int tick,
              int ntick,
              int sec,
              int sectick,
              boolean warm,
              int lineNumber,
              String fileName)
   {
      super(target,
            tickLength,
            "Reset Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      this.warmReset = warm;
   }

   /**
    * Create a new reset event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param warm        the warm reset flag.
    * @param lineNumber  the line number.
    * @param fileName    the filename.
    * @return a new reset event object for off-line usage.
    */
   public static ResetEvent getInstance(Object target,
                                        int tickLength,
                                        int reference,
                                        int tick,
                                        int ntick,
                                        int sec,
                                        int sectick,
                                        boolean warm,
                                        int lineNumber,
                                        String fileName)
   {
      return new ResetEvent(target,
                            tickLength,
                            reference,
                            tick,
                            ntick,
                            sec,
                            sectick,
                            warm,
                            lineNumber,
                            fileName);
   }

   /**
    * Determine whether the reset was a warm reset (RAM contents survived) or
    * not.
    *
    * @return true if the reset was a warm reset, false otherwise.
    */
   public boolean isWarmReset()
   {
      return warmReset;
   }

   /**
    * Return a string representation of this reset event object.
    *
    * @return a string representation of this reset event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() + ", Warm reset: " + warmReset);
   }
}
