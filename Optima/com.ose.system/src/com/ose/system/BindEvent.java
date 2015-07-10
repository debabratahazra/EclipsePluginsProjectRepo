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
 * This class represents a bind event from an OSE target. A bind event is
 * reported when a process is bound to a new execution unit, i.e. when a
 * process is migrated from one execution unit to another.
 *
 * @see com.ose.system.TargetEvent
 */
public class BindEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo process;
   private final short fromExecutionUnit;
   private final short toExecutionUnit;

   /**
    * Create a new bind event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param process     the bound process.
    * @param fromEuId    the from execution unit.
    * @param toEuId      the to execution unit.
    * @param lineNumber  the line number.
    * @param fileName    the filename.
    */
   BindEvent(Object target,
             int tickLength,
             int reference,
             int tick,
             int ntick,
             int sec,
             int sectick,
             ProcessInfo process,
             short fromEuId,
             short toEuId,
             int lineNumber,
             String fileName)
   {
      super(target,
            tickLength,
            "Bind Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      if (process == null)
      {
         throw new NullPointerException();
      }
      this.process = process;
      this.fromExecutionUnit = fromEuId;
      this.toExecutionUnit = toEuId;
   }

   /**
    * Create a new bind event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param process     the bound process.
    * @param fromEuId    the from execution unit.
    * @param toEuId      the to execution unit.
    * @param lineNumber  the line number.
    * @param fileName    the filename.
    * @return a new bind event object for off-line usage.
    */
   public static BindEvent getInstance(Object target,
                                       int tickLength,
                                       int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       ProcessInfo process,
                                       short fromEuId,
                                       short toEuId,
                                       int lineNumber,
                                       String fileName)
   {
      return new BindEvent(target,
                           tickLength,
                           reference,
                           tick,
                           ntick,
                           sec,
                           sectick,
                           process,
                           fromEuId,
                           toEuId,
                           lineNumber,
                           fileName);
   }

   /**
    * Return the bound process.
    *
    * @return the bound process.
    */
   public ProcessInfo getProcess()
   {
      return process;
   }

   /**
    * Return the execution unit the process migrated from.
    *
    * @return the execution unit the process migrated from.
    */
   public short getFromExecutionUnit()
   {
      return fromExecutionUnit;
   }

   /**
    * Return the execution unit the process migrated to.
    *
    * @return the execution unit the process migrated to.
    */
   public short getToExecutionUnit()
   {
      return toExecutionUnit;
   }

   /**
    * Return a string representation of this bind event object.
    *
    * @return a string representation of this bind event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Bound process: " + process +
              ", From execution unit: " + fromExecutionUnit +
              ", To execution unit: " + toExecutionUnit);
   }
}
