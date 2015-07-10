/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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
 * This class represents a timeout event from an OSE target. A timeout event is
 * reported when certain OSE system calls times out.
 *
 * @see com.ose.system.TargetEvent
 */
public class TimeoutEvent extends TargetEvent
{
   /**
    * A receive_w_tmo(), receive_from(), receive_sport(), or receive_with()
    * system call.
    */
   public static final int SYSTEM_CALL_RECEIVE = 0;

   private static final long serialVersionUID = 1L;

   private final int timeout;
   private final int systemCall;
   private final ProcessInfo process;
   private final short executionUnit;

   /**
    * Create a new timeout event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param timeout     the timeout length.
    * @param tmoSource   the timeout source.
    * @param from        the timed out process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    */
   TimeoutEvent(Object target,
                int tickLength,
                int reference,
                int tick,
                int ntick,
                int sec,
                int sectick,
                int timeout,
                int tmoSource,
                ProcessInfo from,
                int lineNumber,
                short euId,
                String fileName)
   {
      super(target,
            tickLength,
            "Timeout Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      if (from == null)
      {
         throw new NullPointerException();
      }
      this.timeout = timeout;
      this.systemCall = tmoSource;
      this.process = from;
      this.executionUnit = euId;
   }

   /**
    * Create a new timeout event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param timeout     the timeout length.
    * @param tmoSource   the timeout source.
    * @param from        the timed out process.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    * @return a new timeout event object for off-line usage.
    */
   public static TimeoutEvent getInstance(Object target,
                                          int tickLength,
                                          int reference,
                                          int tick,
                                          int ntick,
                                          int sec,
                                          int sectick,
                                          int timeout,
                                          int tmoSource,
                                          ProcessInfo from,
                                          int lineNumber,
                                          short euId,
                                          String fileName)
   {
      return new TimeoutEvent(target,
                              tickLength,
                              reference,
                              tick,
                              ntick,
                              sec,
                              sectick,
                              timeout,
                              tmoSource,
                              from,
                              lineNumber,
                              euId,
                              fileName);
   }

   /**
    * Return the timeout length in ticks.
    *
    * @return the timeout length in ticks.
    */
   public int getTimeout()
   {
      return timeout;
   }

   /**
    * Return the OSE system call that timed out, i.e. one of the
    * TimeoutEvent.SYSTEM_CALL_* constants.
    *
    * @return the OSE system call that timed out.
    * @see #SYSTEM_CALL_RECEIVE
    */
   public int getSystemCall()
   {
      return systemCall;
   }

   /**
    * Return the process that timed out.
    *
    * @return the process that timed out.
    */
   public ProcessInfo getProcess()
   {
      return process;
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
    * Return a string representation of this timeout event object.
    *
    * @return a string representation of this timeout event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Timeout length: " + timeout +
              ", Timed out system call: " + systemCall +
              ", Timed out process: " + process +
              ", Execution unit: " + executionUnit);
   }
}
