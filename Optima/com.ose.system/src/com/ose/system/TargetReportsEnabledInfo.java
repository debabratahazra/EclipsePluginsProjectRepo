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
 * This class contains information about OSE target reporting enablement.
 */
public abstract class TargetReportsEnabledInfo
{
   private final Target target;
   private final boolean enabled;
   private final int interval;
   private final int maxReports;
   private final int seconds;
   private final int secondsTick;
   private final int secondsNTick;

   /**
    * Create a new target report enablement info object.
    *
    * @param target      the target.
    * @param enabled     the enablement status.
    * @param interval    the integration interval.
    * @param maxReports  the max reports limit.
    * @param sec         the seconds.
    * @param sectick     the tick at seconds.
    * @param secntick    the ntick at seconds.
    */
   TargetReportsEnabledInfo(Target target,
                            boolean enabled,
                            int interval,
                            int maxReports,
                            int sec,
                            int sectick,
                            int secntick)
   {
      if (target == null)
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.enabled = enabled;
      this.interval = interval;
      this.maxReports = maxReports;
      this.seconds = sec;
      this.secondsTick = sectick;
      this.secondsNTick = secntick;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Determine whether reporting is enabled or not.
    *
    * @return true if reporting is enabled, false otherwise.
    */
   public boolean isEnabled()
   {
      return enabled;
   }

   /**
    * Return the reporting integration interval in ticks.
    *
    * @return the reporting integration interval in ticks.
    */
   public int getInterval()
   {
      return interval;
   }

   /**
    * Return the maximum number of reports kept in the target.
    *
    * @return the maximum number of reports kept in the target.
    */
   public int getMaxReports()
   {
      return maxReports;
   }

   /**
    * Return the number of seconds since 00:00:00 1 jan 1970 GMT at the time of
    * the retrieving call for this object or 0 if unavailable.
    *
    * @return the number of seconds since 00:00:00 1 jan 1970 GMT at the time of
    * the retrieving call for this object or 0 if unavailable.
    * @see #getSecondsTick()
    * @see #getSecondsNTick()
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
    * Return the number of timer steps at the time of getSeconds() or 0 if
    * unavailable.
    *
    * @return the number of timer steps at the time of getSeconds() or 0 if
    * unavailable.
    */
   public int getSecondsNTick()
   {
      return secondsNTick;
   }
}
