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
 * This class represents an error event from an OSE target. An error event is
 * reported when a process triggers an error that is handled by an OSE error
 * handler.
 *
 * @see com.ose.system.TargetEvent
 */
public class ErrorEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo process;
   private final boolean executive;
   private final int error;
   private final int extra;
   private final short executionUnit;

   /**
    * Create a new error event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the faulting process.
    * @param exec        the executive status.
    * @param error       the error code.
    * @param extra       the extra parameter.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    */
   ErrorEvent(Object target,
              int tickLength,
              int reference,
              int tick,
              int ntick,
              int sec,
              int sectick,
              ProcessInfo from,
              boolean exec,
              int error,
              int extra,
              int lineNumber,
              short euId,
              String fileName)
   {
      super(target,
            tickLength,
            "Error Event",
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
      this.process = from;
      this.executive = exec;
      this.error = error;
      this.extra = extra;
      this.executionUnit = euId;
   }

   /**
    * Create a new error event object for off-line usage.
    * The target can be any object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick since seconds.
    * @param from        the faulting process.
    * @param exec        the executive status.
    * @param error       the error code.
    * @param extra       the extra parameter.
    * @param lineNumber  the line number.
    * @param euId        the execution unit.
    * @param fileName    the filename.
    * @return a new error event object for off-line usage.
    */
   public static ErrorEvent getInstance(Object target,
                                        int tickLength,
                                        int reference,
                                        int tick,
                                        int ntick,
                                        int sec,
                                        int sectick,
                                        ProcessInfo from,
                                        boolean exec,
                                        int error,
                                        int extra,
                                        int lineNumber,
                                        short euId,
                                        String fileName)
   {
      return new ErrorEvent(target,
                            tickLength,
                            reference,
                            tick,
                            ntick,
                            sec,
                            sectick,
                            from,
                            exec,
                            error,
                            extra,
                            lineNumber,
                            euId,
                            fileName);
   }

   /**
    * Return the process that triggered the error.
    *
    * @return the process that triggered the error.
    */
   public ProcessInfo getProcess()
   {
      return process;
   }

   /**
    * Determine whether the error was invoked by the executive or not.
    *
    * @return true if the error was invoked by the executive, false otherwise.
    */
   public boolean isExecutive()
   {
      return executive;
   }

   /**
    * Return the error code.
    *
    * @return the error code.
    */
   public int getError()
   {
      return error;
   }

   /**
    * Return the extra parameter to the error code.
    *
    * @return the extra parameter to the error code.
    */
   public int getExtra()
   {
      return extra;
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
    * Return a string representation of this error event object.
    *
    * @return a string representation of this error event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Faulting process: " + process +
              ", Invoked by executive: " + executive +
              ", Error code: " + error +
              ", Extra parameter: " + extra +
              ", Execution unit: " + executionUnit);
   }
}
