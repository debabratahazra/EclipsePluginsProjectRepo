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
 * This class represents a create event from an OSE target. A create event is
 * reported when a new process is created.
 *
 * @see com.ose.system.TargetEvent
 */
public class CreateEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo creatorProcess;
   private final ProcessInfo createdProcess;
   private final short executionUnit;

   /**
    * Create a new create event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the creator process.
    * @param to          the created process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    */
   CreateEvent(Object target,
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
            "Create Event",
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
      this.creatorProcess = from;
      this.createdProcess = to;
      this.executionUnit = euId;
   }

   /**
    * Create a new create event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the creator process.
    * @param to          the created process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    * @return a new create event object for off-line usage.
    */
   public static CreateEvent getInstance(Object target,
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
      return new CreateEvent(target,
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
    * Return the creator process or null if unavailable.
    *
    * @return the creator process or null if unavailable.
    */
   public ProcessInfo getCreatorProcess()
   {
      return creatorProcess;
   }

   /**
    * Return the created process.
    *
    * @return the created process.
    */
   public ProcessInfo getCreatedProcess()
   {
      return createdProcess;
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
    * Return a string representation of this create event object.
    *
    * @return a string representation of this create event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Creator process: " + creatorProcess +
              ", Created process: " + createdProcess +
              ", Execution unit: " + executionUnit);
   }
}
