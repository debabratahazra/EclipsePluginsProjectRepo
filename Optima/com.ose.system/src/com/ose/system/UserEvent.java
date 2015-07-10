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
 * This class represents a user event from an OSE target. A user event is
 * reported by a user and may contain user-specific data.
 *
 * @see com.ose.system.TargetEvent
 */
public class UserEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo process;
   private final int eventNumber;
   private final int eventDataSize;
   private final byte[] eventData;
   private String formattedEventData;
   private final short executionUnit;

   /**
    * Create a new user event object.
    *
    * @param target       the target on which the event initially occurred.
    * @param tickLength   the tick length of the target in microseconds.
    * @param reference    the reference.
    * @param tick         the tick.
    * @param ntick        the ntick.
    * @param sec          the seconds.
    * @param sectick      the tick since seconds.
    * @param from         the reporting process.
    * @param eventNumber  the event number.
    * @param eventSize    the event data size.
    * @param lineNumber   the line number.
    * @param euId         the execution unit.
    * @param fileName     the filename.
    * @param eventData    the event data.
    */
   UserEvent(Object target,
             int tickLength,
             int reference,
             int tick,
             int ntick,
             int sec,
             int sectick,
             ProcessInfo from,
             int eventNumber,
             int eventSize,
             int lineNumber,
             short euId,
             String fileName,
             byte[] eventData)
   {
      super(target,
            tickLength,
            "User Event",
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
      this.eventNumber = eventNumber;
      this.eventDataSize = eventSize;
      this.eventData = eventData;
      this.formattedEventData = null;
      this.executionUnit = euId;
   }

   /**
    * Create a new user event object for off-line usage.
    * The target can be any object.
    *
    * @param target              the target on which the event initially
    * occurred.
    * @param tickLength          the tick length of the target in microseconds.
    * @param reference           the reference.
    * @param tick                the tick.
    * @param ntick               the ntick.
    * @param sec                 the seconds.
    * @param sectick             the tick since seconds.
    * @param from                the reporting process.
    * @param eventNumber         the event number.
    * @param eventSize           the event data size.
    * @param lineNumber          the line number.
    * @param euId                the execution unit.
    * @param fileName            the filename.
    * @param eventData           the event data.
    * @param formattedEventData  the formatted event data or null if not
    * available.
    * @return a new user event object for off-line usage.
    */
   public static UserEvent getInstance(Object target,
                                       int tickLength,
                                       int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       ProcessInfo from,
                                       int eventNumber,
                                       int eventSize,
                                       int lineNumber,
                                       short euId,
                                       String fileName,
                                       byte[] eventData,
                                       String formattedEventData)
   {
      UserEvent userEvent = new UserEvent(target,
                                          tickLength,
                                          reference,
                                          tick,
                                          ntick,
                                          sec,
                                          sectick,
                                          from,
                                          eventNumber,
                                          eventSize,
                                          lineNumber,
                                          euId,
                                          fileName,
                                          eventData);
      userEvent.formattedEventData = formattedEventData;
      return userEvent;
   }

   /**
    * Return the reporting process.
    *
    * @return the reporting process.
    */
   public ProcessInfo getProcess()
   {
      return process;
   }

   /**
    * Return the user event number.
    *
    * @return the user event number.
    */
   public int getEventNumber()
   {
      return eventNumber;
   }

   /**
    * Return the number of bytes requested for user event data.
    *
    * @return the number of bytes requested for user event data.
    */
   public int getEventDataSize()
   {
      return eventDataSize;
   }

   /**
    * Return the user event data as an array of bytes. The array must not be
    * modified by a client. If the event has no data an empty byte array is
    * returned.
    * <p>
    * Note that the user event data may have been truncated. Use
    * getEventDataSize() to get the actual user event data size.
    *
    * @return the user event data as an array of bytes.
    */
   public byte[] getEventData()
   {
      return eventData;
   }

   /**
    * Return the formatted data of the user event or null if not available.
    *
    * @return the formatted data of the user event or null if not available.
    */
   public String getFormattedEventData()
   {
      return formattedEventData;
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
    * Return a string representation of this user event object.
    *
    * @return a string representation of this user event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Reporting process: " + process +
              ", User event number: " + eventNumber +
              ", User event data size: " + eventDataSize +
              ", Execution unit: " + executionUnit);
   }
}
