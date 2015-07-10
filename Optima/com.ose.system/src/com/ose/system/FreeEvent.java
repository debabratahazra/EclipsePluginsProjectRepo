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
 * This class represents a free event from an OSE target. A free event is
 * reported when a signal, i.e. a pool buffer, is freed by a process.
 *
 * @see com.ose.system.TargetEvent
 */
public class FreeEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo process;
   private final int signalNumber;
   private final int signalId;
   private final int signalAddress;
   private final int signalSize;
   private final short executionUnit;

   /**
    * Create a new free event object.
    *
    * @param target         the target on which the event initially occurred.
    * @param tickLength     the tick length of the target in microseconds.
    * @param reference      the reference.
    * @param tick           the tick.
    * @param ntick          the ntick.
    * @param sec            the seconds.
    * @param sectick        the tick since seconds.
    * @param from           the freeing process.
    * @param signalNumber   the signal number.
    * @param signalSize     the signal size.
    * @param signalAddress  the signal buffer address.
    * @param signalId       the signal id.
    * @param lineNumber     the line number.
    * @param euId           the execution unit.
    * @param fileName       the filename.
    */
   FreeEvent(Object target,
             int tickLength,
             int reference,
             int tick,
             int ntick,
             int sec,
             int sectick,
             ProcessInfo from,
             int signalNumber,
             int signalSize,
             int signalAddress,
             int signalId,
             int lineNumber,
             short euId,
             String fileName)
   {
      super(target,
            tickLength,
            "Free Event",
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
      this.signalNumber = signalNumber;
      this.signalId = signalId;
      this.signalAddress = signalAddress;
      this.signalSize = signalSize;
      this.executionUnit = euId;
   }

   /**
    * Create a new free event object for off-line usage.
    * The target can be any object.
    *
    * @param target         the target on which the event initially occurred.
    * @param tickLength     the tick length of the target in microseconds.
    * @param reference      the reference.
    * @param tick           the tick.
    * @param ntick          the ntick.
    * @param sec            the seconds.
    * @param sectick        the tick since seconds.
    * @param from           the freeing process.
    * @param signalNumber   the signal number.
    * @param signalSize     the signal size.
    * @param signalAddress  the signal buffer address.
    * @param signalId       the signal id.
    * @param lineNumber     the line number.
    * @param euId           the execution unit.
    * @param fileName       the filename.
    * @return a new free event object for off-line usage.
    */
   public static FreeEvent getInstance(Object target,
                                       int tickLength,
                                       int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       ProcessInfo from,
                                       int signalNumber,
                                       int signalSize,
                                       int signalAddress,
                                       int signalId,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
   {
      return new FreeEvent(target,
                           tickLength,
                           reference,
                           tick,
                           ntick,
                           sec,
                           sectick,
                           from,
                           signalNumber,
                           signalSize,
                           signalAddress,
                           signalId,
                           lineNumber,
                           euId,
                           fileName);
   }

   /**
    * Return the process that freed the signal.
    *
    * @return the process that freed the signal.
    */
   public ProcessInfo getProcess()
   {
      return process;
   }

   /**
    * Return the signal number of the signal.
    *
    * @return the signal number of the signal.
    */
   public int getSignalNumber()
   {
      return signalNumber;
   }

   /**
    * Return the id of the signal or 0 if unavailable.
    *
    * @return the id of the signal or 0 if unavailable.
    */
   public int getSignalId()
   {
      return signalId;
   }

   /**
    * Return the address of the signal buffer or 0 if unavailable.
    *
    * @return the address of the signal buffer or 0 if unavailable.
    */
   public int getSignalAddress()
   {
      return signalAddress;
   }

   /**
    * Return the requested size of the signal in bytes or 0 if unavailable.
    *
    * @return the requested size of the signal in bytes or 0 if unavailable.
    */
   public int getSignalSize()
   {
      return signalSize;
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
    * Return a string representation of this free event object.
    *
    * @return a string representation of this free event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Freeing process: " + process +
              ", Signal number: " + signalNumber +
              ", Signal id: " + signalId +
              ", Signal buffer address: " + signalAddress +
              ", Signal size: " + signalSize +
              ", Execution unit: " + executionUnit);
   }
}
