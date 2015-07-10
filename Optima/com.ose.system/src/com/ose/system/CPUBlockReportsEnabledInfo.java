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
 * This class contains information about OSE CPU block level load reporting
 * enablement. A CPUBlockReportsEnabledInfo object is immutable and its
 * content is a snapshot at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getCPUBlockReportsEnabled(IProgressMonitor)
 */
public class CPUBlockReportsEnabledInfo extends TargetReportsEnabledInfo
{
   private final int maxBlocksPerReport;

   /**
    * Create a new CPU block level load report enablement info object.
    *
    * @param target           the target.
    * @param enabled          the enablement status.
    * @param interval         the integration interval.
    * @param maxReports       the max reports limit.
    * @param maxBlocksReport  the max blocks per report limit.
    * @param sec              the seconds.
    * @param sectick          the tick at seconds.
    * @param secntick         the ntick at seconds.
    */
   CPUBlockReportsEnabledInfo(Target target,
                              boolean enabled,
                              int interval,
                              int maxReports,
                              int maxBlocksReport,
                              int sec,
                              int sectick,
                              int secntick)
   {
      super(target, enabled, interval, maxReports, sec, sectick, secntick);
      this.maxBlocksPerReport = maxBlocksReport;
   }

   /**
    * Return the maximum number of blocks per CPU block level load report.
    *
    * @return the maximum number of blocks per CPU block level load report.
    */
   public int getMaxBlocksPerReport()
   {
      return maxBlocksPerReport;
   }
}
