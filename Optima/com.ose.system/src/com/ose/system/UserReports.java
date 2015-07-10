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

import com.ose.system.service.monitor.MonitorUserReport;

/**
 * This class contains information about OSE user reports. A UserReports object
 * is immutable and its content is a snapshot at the time of the retrieving
 * call.
 *
 * @see com.ose.system.Target#getUserReports(IProgressMonitor, int, int, int)
 */
public class UserReports extends TargetReports
{
   private final int reportNumber;
   private final boolean continuous;
   private final boolean maxMin;
   private final boolean multiple;

   /**
    * Create a new user reports object.
    *
    * @param target      the target.
    * @param reportNo    the report number.
    * @param enabled     the enablement status.
    * @param continuous  the continuous status.
    * @param maxmin      the max-min status.
    * @param multiple    the multiple status.
    * @param reports     the internal user report objects.
    */
   UserReports(Target target,
               int reportNo,
               boolean enabled,
               boolean continuous,
               boolean maxmin,
               boolean multiple,
               MonitorUserReport[] reports)
   {
      super(target, enabled);

      if (reports == null)
      {
         throw new NullPointerException();
      }
      this.reportNumber = reportNo;
      this.continuous = continuous;
      this.maxMin = maxmin;
      this.multiple = multiple;
      this.reports = new UserReport[reports.length];
      for (int i = 0; i < reports.length; i++)
      {
         this.reports[i] = new UserReport(reports[i], maxmin);
      }
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
    * Determine whether the user reporting is continuous or not.
    * <p>
    * Returns true if the previous user report value is copied to the next
    * integration interval or false if the first user report value is reset
    * at integration interval start.
    *
    * @return true if the user reporting is continuous, false otherwise.
    */
   public boolean isContinuous()
   {
      return continuous;
   }

   /**
    * Determine whether maximum and minimum user report measurement values are
    * tracked during each integration interval or not.
    * <p>
    * If isMaxMin() returns true, the UserReport objects returned by
    * getReports() contain MaxMinUserReportValue values, otherwise they
    * contain UserReportValue values.
    *
    * @return true if maximum and minimum user report measurement values are
    * tracked during each integration interval, false otherwise.
    */
   public boolean isMaxMin()
   {
      return maxMin;
   }

   /**
    * Determine whether this user report type supports multiple user report
    * measurement values or only a single user report measurement value per
    * user report.
    * <p>
    * If isMultiple() returns true, the UserReport objects returned by
    * getReports() contain multiple UserReportValue values, one for each
    * measured entity (identified by the id of the UserReportValue), otherwise
    * they contain a single value stored directly in the UserReport object
    * (accessible via getSumOther() and, if applicable, getMaxOther() and
    * getMinOther()).
    *
    * @return true if this user report type supports multiple user report
    * measurement values per user report, false otherwise.
    */
   public boolean isMultiple()
   {
      return multiple;
   }
}
