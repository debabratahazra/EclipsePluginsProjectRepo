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
 * This class represents a kill event from an OSE target. A kill event is
 * reported when a process is killed.
 *
 * @see com.ose.system.TargetEvent
 */
public class KillEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo killerProcess;
   private final ProcessInfo killedProcess;
   private final short executionUnit;

   /**
    * Create a new kill event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the killer process.
    * @param to          the killed process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    */
   KillEvent(Object target,
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
            "Kill Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      if (to == null)
      {
         throw new NullPointerException();
      }
      this.killerProcess = from;
      this.killedProcess = to;
      this.executionUnit = euId;
   }

   /**
    * Create a new kill event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the killer process.
    * @param to          the killed process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    * @return a new kill event object for off-line usage.
    */
   public static KillEvent getInstance(Object target,
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
      return new KillEvent(target,
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
    * Return the killer process or null if unavailable.
    *
    * @return the killer process or null if unavailable.
    */
   public ProcessInfo getKillerProcess()
   {
      return killerProcess;
   }

   /**
    * Return the killed process.
    *
    * @return the killed process.
    */
   public ProcessInfo getKilledProcess()
   {
      return killedProcess;
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
    * Return a string representation of this kill event object.
    *
    * @return a string representation of this kill event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Killer process: " + killerProcess +
              ", Killed process: " + killedProcess +
              ", Execution unit: " + executionUnit);
   }
}
