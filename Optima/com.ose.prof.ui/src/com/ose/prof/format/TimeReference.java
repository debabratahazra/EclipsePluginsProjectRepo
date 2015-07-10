/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.format;

/**
 * This class represents a time reference in the form of a set number of
 * seconds since 00:00:00 1 jan 1970 GMT and the corresponding tick:ntick
 * value at that time. A time reference object is used to adjust a set time
 * reference to an earlier point in time based on a given tick:ntick value.
 */
public class TimeReference
{
   private final long tickLength;
   private final long ntickFrequency;

   private long seconds;
   private long refTick;
   private long refNTick;

   /**
    * Create a new time reference object with the given tick length in
    * microseconds and ntick timer frequency in Hz.
    *
    * @param tickLength      the tick length in microseconds.
    * @param ntickFrequency  the ntick timer frequency in Hz.
    */
   public TimeReference(int tickLength, int ntickFrequency)
   {
      if ((tickLength == 0) || (ntickFrequency == 0))
      {
         throw new IllegalArgumentException();
      }

      this.tickLength = tickLength & 0xFFFFFFFFL;
      this.ntickFrequency = ntickFrequency & 0xFFFFFFFFL;
   }

   /**
    * Set the number of seconds reference since 00:00:00 1 jan 1970 GMT and
    * the corresponding tick:ntick value at that time.
    *
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT.
    * @param tick     the corresponding tick value.
    * @param ntick    the corresponding ntick value.
    */
   public void set(int seconds, int tick, int ntick)
   {
      this.seconds = seconds & 0xFFFFFFFFL;
      this.refTick = tick & 0xFFFFFFFFL;
      this.refNTick = ntick & 0xFFFFFFFFL;
   }

   /**
    * If the given tick:ntick value is before the reference tick:ntick value
    * in time and has not obviously wrapped, adjust the reference seconds to
    * a point immediately prior to the given tick:ntick value and adjust the
    * reference tick:ntick value to that point in time.
    *
    * @param tick   a tick value.
    * @param ntick  a ntick value.
    * @return true if the time reference was adjusted, false otherwise.
    */
   public boolean adjust(int tick, int ntick)
   {
      long curTick = tick & 0xFFFFFFFFL;
      long curNTick = ntick & 0xFFFFFFFFL;

      if (((curTick < refTick) && (Math.abs(refTick - curTick) < Integer.MAX_VALUE))
            || ((curTick == refTick) && (curNTick < refNTick)))
      {
         long refNanos = refTick * tickLength * 1000L +
            (refNTick * 1000000000L) / ntickFrequency;
         long curNanos = curTick * tickLength * 1000L +
            (curNTick * 1000000000L) / ntickFrequency;

         long diffNanos = refNanos - curNanos;
         long restNanos = diffNanos % 1000000000L;
         long diffSeconds = diffNanos / 1000000000L + ((restNanos > 0) ? 1 : 0);
         seconds -= diffSeconds;

         long newRefNanos = curNanos - restNanos;
         long newRestNanos = newRefNanos % (tickLength * 1000L);
         refTick = newRefNanos / (tickLength * 1000L);
         refNTick = (newRestNanos * ntickFrequency) / 1000000000L;

         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * Return the number of seconds reference since 00:00:00 1 jan 1970 GMT.
    *
    * @return the number of seconds reference since 00:00:00 1 jan 1970 GMT.
    */
   public int getSeconds()
   {
      return (int) seconds;
   }

   /**
    * Return the number of ticks reference at the time of getSeconds().
    *
    * @return the number of ticks reference at the time of getSeconds().
    */
   public int getTick()
   {
      return (int) refTick;
   }

   /**
    * Return the number of timer steps reference at the time of getSeconds().
    *
    * @return the number of timer steps reference at the time of getSeconds().
    */
   public int getNTick()
   {
      return (int) refNTick;
   }
}
