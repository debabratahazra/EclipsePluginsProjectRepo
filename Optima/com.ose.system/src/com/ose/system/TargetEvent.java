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

import java.util.EventObject;
import com.ose.system.service.monitor.Monitor;

/**
 * This class represents an event from an OSE target. A target event is the
 * abstract super class of all events reported from the event system of an OSE
 * target.
 *
 * @see java.util.EventObject
 * @see com.ose.system.TargetListener
 * @see com.ose.system.Target#getEventTrace(IProgressMonitor, int, int)
 */
public abstract class TargetEvent extends EventObject
{
   private static final long serialVersionUID = 1L;

   private final String eventType;
   private final int reference;
   private final int tick;
   private final int ntick;
   private final int seconds;
   private final int secondsTick;
   private final long milliSeconds;
   private final String fileName;
   private final int lineNumber;

   /**
    * Create a new target event object.
    *
    * @param target      the target on which the event initially occurred.
    * @param tickLength  the tick length of the target in microseconds.
    * @param eventType   the event type description.
    * @param reference   the reference.
    * @param tick        the tick.
    * @param ntick       the ntick.
    * @param sec         the seconds.
    * @param sectick     the tick at seconds.
    * @param lineNumber  the line number.
    * @param fileName    the filename.
    */
   TargetEvent(Object target,
               int tickLength,
               String eventType,
               int reference,
               int tick,
               int ntick,
               int sec,
               int sectick,
               int lineNumber,
               String fileName)
   {
      super(target);
      if (eventType == null)
      {
         throw new NullPointerException();
      }
      this.eventType = eventType;
      this.reference = reference;
      this.tick = tick;
      this.ntick = ntick;
      this.seconds = sec;
      this.secondsTick = sectick;
      this.milliSeconds = getMilliSeconds(tickLength, tick, sec, sectick);
      this.fileName = fileName;
      this.lineNumber = lineNumber;
   }

   /**
    * Return the corresponding event action point id or event trace entry
    * number.
    *
    * @return the corresponding event action point id or event trace entry
    * number.
    */
   public int getReference()
   {
      return reference;
   }

   /**
    * Return the event creation time in number of ticks.
    *
    * @return the event creation time in number of ticks.
    */
   public int getTick()
   {
      return tick;
   }

   /**
    * Return the number of timer steps since getTick().
    *
    * @return the number of timer steps since getTick().
    */
   public int getNTick()
   {
      return ntick;
   }

   /**
    * Return the number of seconds since 00:00:00 1 jan 1970 GMT prior to the
    * event creation time or 0 if unavailable.
    *
    * @return the number of seconds since 00:00:00 1 jan 1970 GMT prior to the
    * event creation time or 0 if unavailable.
    */
   public int getSeconds()
   {
      return seconds;
   }

   /**
    * Return the number of ticks at the time of getSeconds() or 0 if
    * unavailable.
    *
    * @return the number of ticks at the time of getSeconds() or 0 if
    * unavailable.
    */
   public int getSecondsTick()
   {
      return secondsTick;
   }

   /**
    * Calculate the event creation time in number of milliseconds since 00:00:00
    * 1 jan 1970 GMT or 0 if unavailable.
    *
    * @param tickLength   the tick length of the target in microseconds.
    * @param tick         the tick.
    * @param seconds      the seconds.
    * @param secondsTick  the tick at seconds.
    * @return the event creation time in number of milliseconds since 00:00:00
    * 1 jan 1970 GMT or 0 if unavailable.
    */
   private static long getMilliSeconds(int tickLength,
                                       int tick,
                                       int seconds,
                                       int secondsTick)
   {
      long milliSeconds = 0;

      if (seconds != 0)
      {
         milliSeconds = (seconds & 0xFFFFFFFFL) * 1000L;
         if (secondsTick != 0)
         {
            long secTick = secondsTick & 0xFFFFFFFFL;
            long curTick = tick & 0xFFFFFFFFL;
            long tickLen = tickLength & 0xFFFFFFFFL;

            // Has the tick counter wrapped?
            if (curTick < secTick)
            {
               curTick += 0x100000000L;
            }
            milliSeconds += ((curTick - secTick) * tickLen) / 1000L;
         }
      }

      return milliSeconds;
   }

   /**
    * Return the event creation time in number of milliseconds since 00:00:00
    * 1 jan 1970 GMT or 0 if unavailable.
    *
    * @return the event creation time in number of milliseconds since 00:00:00
    * 1 jan 1970 GMT or 0 if unavailable.
    */
   public long getMilliSeconds()
   {
      return milliSeconds;
   }

   /**
    * Return the name of the source file that triggered the event or an empty
    * string if unavailable. Only applicable if getLineNumber() != 0.
    *
    * @return the name of the source file that triggered the event or an empty
    * string if unavailable.
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * Return the line number that triggered the event or 0 if unavailable.
    *
    * @return the line number that triggered the event or 0 if unavailable.
    */
   public int getLineNumber()
   {
      return lineNumber;
   }

   /**
    * Return a string representation of this target event object.
    *
    * @return a string representation of this target event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (eventType +
              ", Target: " + source +
              ", Reference: " + reference +
              ", Ticks: " + tick +
              ", Micro ticks: " + ntick +
              ", Seconds: " + seconds +
              ", Ticks at seconds: " + secondsTick +
              ", Milliseconds: " + milliSeconds +
              ", File: " + fileName +
              ", Line: " + lineNumber);
   }

   /**
    * This class contains information about an OSE process referenced in a
    * TargetEvent or one of its subclasses. A ProcessInfo object is immutable
    * and its content is a snapshot at the time the corresponding event object
    * was created.
    */
   public static class ProcessInfo
   {
      /** Prioritized process type. */
      public static final int TYPE_PRIORITIZED =
         Monitor.MONITOR_PROCESS_TYPE_PRIORITIZED;

      /** Background process type. */
      public static final int TYPE_BACKGROUND =
         Monitor.MONITOR_PROCESS_TYPE_BACKGROUND;

      /** Interrupt process type. */
      public static final int TYPE_INTERRUPT =
         Monitor.MONITOR_PROCESS_TYPE_INTERRUPT;

      /** Timer-interrupt process type. */
      public static final int TYPE_TIMER_INTERRUPT =
         Monitor.MONITOR_PROCESS_TYPE_TIMER_INTERRUPT;

      /** Phantom process type. */
      public static final int TYPE_PHANTOM =
         Monitor.MONITOR_PROCESS_TYPE_PHANTOM;

      /** Signal port process type. */
      public static final int TYPE_SIGNAL_PORT =
         Monitor.MONITOR_PROCESS_TYPE_SIGNAL_PORT;

      /** Idle process type. */
      public static final int TYPE_IDLE =
         Monitor.MONITOR_PROCESS_TYPE_IDLE;

      /** Killed process type. */
      public static final int TYPE_KILLED =
         Monitor.MONITOR_PROCESS_TYPE_KILLED;

      /** Unknown process type. */
      public static final int TYPE_UNKNOWN =
         Monitor.MONITOR_PROCESS_TYPE_UNKNOWN;

      /* Target. */
      private final Object target;

      /* Process info. */
      private final String name;
      private final int id;
      private final int bid;
      private final int sid;
      private final int type;
      private final int entrypoint;
      private final int properties;

      /**
       * Create a new process info object.
       *
       * @param target      the target.
       * @param pid         the process ID.
       * @param bid         the parent block ID.
       * @param sid         the parent segment ID.
       * @param type        the process type.
       * @param entrypoint  the entrypoint address.
       * @param properties  the properties bit field.
       * @param name        the process name.
       */
      ProcessInfo(Object target,
                  int pid,
                  int bid,
                  int sid,
                  int type,
                  int entrypoint,
                  int properties,
                  String name)
      {
         if ((target == null) || (name == null))
         {
            throw new NullPointerException();
         }
         if ((type != TYPE_PRIORITIZED) &&
             (type != TYPE_BACKGROUND) &&
             (type != TYPE_INTERRUPT) &&
             (type != TYPE_TIMER_INTERRUPT) &&
             (type != TYPE_PHANTOM) &&
             (type != TYPE_SIGNAL_PORT) &&
             (type != TYPE_IDLE) &&
             (type != TYPE_KILLED) &&
             (type != TYPE_UNKNOWN))
         {
            type = TYPE_UNKNOWN;
         }
         this.target = target;
         this.name = name;
         this.id = pid;
         this.bid = bid;
         this.sid = sid;
         this.type = type;
         this.entrypoint = entrypoint;
         this.properties = properties;
      }

      /**
       * Create a new process info object for off-line usage.
       * The target can be any object.
       *
       * @param target      the target.
       * @param pid         the process ID.
       * @param bid         the parent block ID.
       * @param sid         the parent segment ID.
       * @param type        the process type.
       * @param entrypoint  the entrypoint address.
       * @param properties  the properties bit field.
       * @param name        the process name.
       * @return a new process info object for off-line usage.
       */
      public static ProcessInfo getInstance(Object target,
                                            int pid,
                                            int bid,
                                            int sid,
                                            int type,
                                            int entrypoint,
                                            int properties,
                                            String name)
      {
         return new ProcessInfo(target,
                                pid,
                                bid,
                                sid,
                                type,
                                entrypoint,
                                properties,
                                name);
      }

      /**
       * Return the parent target object. If a target event originates from an
       * OSE target system this method will return that com.ose.system.Target
       * object.
       *
       * @return the parent target object.
       */
      public Object getTarget()
      {
         return target;
      }

      /**
       * Return the name of this process.
       * <p>
       * Note: The name is of the form
       * "&lt;parent-block-name&gt;:&lt;process-name&gt;".
       *
       * @return the name of this process.
       */
      public String getName()
      {
         return name;
      }

      /**
       * Return the ID of this process.
       *
       * @return the ID of this process.
       */
      public int getId()
      {
         return id;
      }

      /**
       * Return the parent block ID of this process.
       *
       * @return the parent block ID of this process.
       */
      public int getBid()
      {
         return bid;
      }

      /**
       * Return the parent segment ID of this process.
       *
       * @return the parent segment ID of this process.
       */
      public int getSid()
      {
         return sid;
      }

      /**
       * Return the type of this process, i.e. one of the ProcessInfo.TYPE_*
       * constants.
       *
       * @return the type of this process.
       * @see #TYPE_PRIORITIZED
       * @see #TYPE_BACKGROUND
       * @see #TYPE_INTERRUPT
       * @see #TYPE_TIMER_INTERRUPT
       * @see #TYPE_PHANTOM
       * @see #TYPE_SIGNAL_PORT
       * @see #TYPE_IDLE
       * @see #TYPE_KILLED
       * @see #TYPE_UNKNOWN
       */
      public int getType()
      {
         return type;
      }

      /**
       * Return the entrypoint address of this process.
       *
       * @return the entrypoint address of this process.
       */
      public int getEntrypoint()
      {
         return entrypoint;
      }

      /**
       * Return the properties bit field of this process.
       * Bit 0 is set to 1 for system processes in OSE 5.2 and later.
       * Other bits are undefined.
       *
       * @return the properties bit field of this process.
       */
      public int getProperties()
      {
         return properties;
      }

      /**
       * Return a string representation of this process info object.
       * The returned string is of the form
       * "&lt;process-name&gt; (&lt;process-id&gt;)", where process-id is in
       * hexadecimal form.
       *
       * @return a string representation of this process info object.
       * @see java.lang.Object#toString()
       */
      public String toString()
      {
         return (name + " (0x" + Integer.toHexString(id).toUpperCase() + ")");
      }
   }
}
