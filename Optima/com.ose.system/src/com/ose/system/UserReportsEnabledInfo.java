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
 * This class contains information about OSE user reporting enablement.
 * A UserReportsEnabledInfo object is immutable and its content is a snapshot
 * at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getUserReportsEnabled(IProgressMonitor, int)
 */
public class UserReportsEnabledInfo extends TargetReportsEnabledInfo
{
   private final int reportNumber;
   private final int maxValuesPerReport;

   /**
    * Create a new user report enablement info object.
    *
    * @param target           the target.
    * @param reportNo         the report number.
    * @param enabled          the enablement status.
    * @param interval         the integration interval.
    * @param maxReports       the max reports limit.
    * @param maxValuesReport  the max values per report limit.
    * @param sec              the seconds.
    * @param sectick          the tick at seconds.
    * @param secntick         the ntick at seconds.
    */
   UserReportsEnabledInfo(Target target,
                          int reportNo,
                          boolean enabled,
                          int interval,
                          int maxReports,
                          int maxValuesReport,
                          int sec,
                          int sectick,
                          int secntick)
   {
      super(target, enabled, interval, maxReports, sec, sectick, secntick);
      this.reportNumber = reportNo;
      this.maxValuesPerReport = maxValuesReport;
   }

   /**
    * Return the user report number, i.e the user report type identifier.
    *
    * @return the user report number, i.e the user report type identifier.
    */
   public int getReportNumber()
   {
      return reportNumber;
   }

   /**
    * Return the maximum number of values per user report.
    *
    * @return the maximum number of values per user report.
    */
   public int getMaxValuesPerReport()
   {
      return maxValuesPerReport;
   }
}
