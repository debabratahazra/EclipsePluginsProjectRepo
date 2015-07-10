/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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
 * This class contains information about OSE CPU load reporting enablement.
 * A CPUReportsEnabledInfo object is immutable and its content is a snapshot
 * at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getCPUReportsEnabled(IProgressMonitor)
 */
public class CPUReportsEnabledInfo extends TargetReportsEnabledInfo
{
   private final int priority;

   /**
    * Create a new CPU load report enablement info object.
    *
    * @param target      the target.
    * @param enabled     the enablement status.
    * @param interval    the integration interval.
    * @param priority    the priority limit.
    * @param maxReports  the max reports limit.
    * @param sec         the seconds.
    * @param sectick     the tick at seconds.
    * @param secntick    the ntick at seconds.
    */
   CPUReportsEnabledInfo(Target target,
                         boolean enabled,
                         int interval,
                         int priority,
                         int maxReports,
                         int sec,
                         int sectick,
                         int secntick)
   {
      super(target, enabled, interval, maxReports, sec, sectick, secntick);
      this.priority = priority;
   }

   /**
    * Return the CPU load reporting priority limit. CPU time spent on this or
    * lower priority (higher value) is disregarded.
    *
    * @return the CPU load reporting priority limit.
    */
   public int getPriority()
   {
      return priority;
   }
}
