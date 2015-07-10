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
 * This class represents a profiling report from an OSE target. A target
 * report is the abstract super class of all profiling reports reported
 * from an OSE target.
 */
public abstract class TargetReport
{
   private final int tick;
   private final int ntick;
   private final int interval;
   private long nanoSeconds;

   /**
    * Create a new target report object.
    *
    * @param tick      the tick.
    * @param ntick     the micro tick.
    * @param interval  the measurement interval.
    */
   TargetReport(int tick, int ntick, int interval)
   {
      this.tick = tick;
      this.ntick = ntick;
      this.interval = interval;
   }

   /**
    * Return the report creation time in number of ticks.
    *
    * @return the report creation time in number of ticks.
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
    * Return the time of the measurement interval (unspecified unit).
    *
    * @return the time of the measurement interval.
    */
   public int getInterval()
   {
      return interval;
   }

   /**
    * Return the report creation time in number of nanoseconds since 00:00:00
    * 1 jan 1970 GMT.
    *
    * @return the report creation time in number of nanoseconds since 00:00:00
    * 1 jan 1970 GMT.
    */
   public long getNanoSeconds()
   {
      return nanoSeconds;
   }

   /**
    * Calculate and store the report creation time in number of nanoseconds
    * since 00:00:00 1 jan 1970 GMT.
    * <p>
    * This method should NOT be called by clients.
    *
    * @param tickLength      the tick length of the target in microseconds.
    * @param ntickFrequency  the ntick timer frequency of the target in Hz.
    * @param seconds         the number of seconds since 00:00:00 1 jan 1970
    *                        GMT at a point in time prior to this report.
    * @param secondsTick     the number of ticks at seconds.
    * @param secondsNTick    the number of timer steps since secondsTick.
    */
   public void setNanoSeconds(int tickLength,
                              int ntickFrequency,
                              int seconds,
                              int secondsTick,
                              int secondsNTick)
   {
      long tick_len = tickLength & 0xFFFFFFFFL;
      long ntick_freq = ntickFrequency & 0xFFFFFFFFL;
      long nanos = (seconds & 0xFFFFFFFFL) * 1000000000L;
      long cur_tick = tick & 0xFFFFFFFFL;
      long ref_tick = secondsTick & 0xFFFFFFFFL;
      long cur_ntick = ntick & 0xFFFFFFFFL;
      long ref_ntick = secondsNTick & 0xFFFFFFFFL;
      long cur_nanos;
      long ref_nanos;

      if ((tickLength == 0) || (ntickFrequency == 0))
      {
         throw new IllegalArgumentException();
      }

      // Has the tick counter wrapped?
      if (cur_tick < ref_tick)
      {
         cur_tick += 0x100000000L;
      }

      cur_nanos = cur_tick * tick_len * 1000L +
         (cur_ntick * 1000000000L) / ntick_freq;
      ref_nanos = ref_tick * tick_len * 1000L +
         (ref_ntick * 1000000000L) / ntick_freq;
      nanoSeconds = nanos + (cur_nanos - ref_nanos);
   }
}
