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
 * This class represents a swap event from an OSE target. A swap event is
 * reported when a context switch occurs, i.e. when a process is swapped out
 * and another swapped in.
 *
 * @see com.ose.system.TargetEvent
 */
public class SwapEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo swappedOutProcess;
   private final ProcessInfo swappedInProcess;
   private final short executionUnit;

   /**
    * Create a new swap event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the swapped out process.
    * @param to          the swapped in process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    */
   SwapEvent(Object target,
             int tickLength,
             int reference,
             int tick,
             int ntick,
             int sec,
             int sectick,
             ProcessInfo from,
             ProcessInfo to,
             int lineNumber,
             short euId,
             String fileName)
   {
      super(target,
            tickLength,
            "Swap Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      if ((from == null) || (to == null))
      {
         throw new NullPointerException();
      }
      this.swappedOutProcess = from;
      this.swappedInProcess = to;
      this.executionUnit = euId;
   }

   /**
    * Create a new swap event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the swapped out process.
    * @param to          the swapped in process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    * @return a new swap event object for off-line usage.
    */
   public static SwapEvent getInstance(Object target,
                                       int tickLength,
                                       int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       ProcessInfo from,
                                       ProcessInfo to,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
   {
      return new SwapEvent(target,
                           tickLength,
                           reference,
                           tick,
                           ntick,
                           sec,
                           sectick,
                           from,
                           to,
                           lineNumber,
                           euId,
                           fileName);
   }

   /**
    * Return the swapped out process.
    *
    * @return the swapped out process.
    */
   public ProcessInfo getSwappedOutProcess()
   {
      return swappedOutProcess;
   }

   /**
    * Return the swapped in process.
    *
    * @return the swapped in process.
    */
   public ProcessInfo getSwappedInProcess()
   {
      return swappedInProcess;
   }

   /**
    * Return the execution unit where the event originated from.
    *
    * @return the execution unit where the event originated from.
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Return a string representation of this swap event object.
    *
    * @return a string representation of this swap event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Swapped out process: " + swappedOutProcess +
              ", Swapped in process: " + swappedInProcess +
              ", Execution unit: " + executionUnit);
   }
}
