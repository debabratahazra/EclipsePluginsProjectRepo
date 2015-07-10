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
import com.ose.system.service.monitor.MonitorUserReport.MonitorMaxMinUserReportValue;
import com.ose.system.service.monitor.MonitorUserReport.MonitorUserReportValue;

/**
 * This class represents an OSE user report. A UserReport object is immutable
 * and its content is a snapshot at the time of the retrieving
 * Target.getUserReports() call.
 *
 * @see com.ose.system.UserReports#getReports()
 */
public class UserReport extends TargetReport
{
   private final int sumOther;
   private final int maxOther;
   private final int minOther;
   private final UserReportValue[] values;

   /**
    * Create a new user report object.
    *
    * @param report  the internal user report object.
    * @param maxmin  the max-min status.
    */
   UserReport(MonitorUserReport report, boolean maxmin)
   {
      super(report.tick, report.ntick, report.interval);
      this.sumOther = report.sumOther;
      this.maxOther = report.maxOther;
      this.minOther = report.minOther;
      this.values = new UserReportValue[report.reportValues.length];
      for (int i = 0; i < report.reportValues.length; i++)
      {
         if (maxmin)
         {
            this.values[i] = new MaxMinUserReportValue(
                  (MonitorMaxMinUserReportValue) report.reportValues[i]);
         }
         else
         {
            this.values[i] = new UserReportValue(report.reportValues[i]);
         }
      }
   }

   /**
    * Create a new user report object.
    *
    * @param tick      the tick.
    * @param ntick     the micro tick.
    * @param interval  the measurement interval.
    * @param sumOther  the other sum.
    * @param maxOther  the other max.
    * @param minOther  the other min.
    * @param values    the values.
    */
   UserReport(int tick,
              int ntick,
              int interval,
              int sumOther,
              int maxOther,
              int minOther,
              UserReportValue[] values)
   {
      super(tick, ntick, interval);
      this.sumOther = sumOther;
      this.maxOther = maxOther;
      this.minOther = minOther;
      this.values = values;
   }

   /**
    * Create a new user report object for off-line usage.
    *
    * @param tick      the tick.
    * @param ntick     the micro tick.
    * @param interval  the measurement interval.
    * @param sumOther  the other sum.
    * @param maxOther  the other max.
    * @param minOther  the other min.
    * @param values    the values.
    * @return a new user report object for off-line usage.
    */
   public static UserReport getInstance(int tick,
                                        int ntick,
                                        int interval,
                                        int sumOther,
                                        int maxOther,
                                        int minOther,
                                        UserReportValue[] values)
   {
      return new UserReport(tick, ntick, interval, sumOther, maxOther, minOther, values);
   }

   /**
    * If isMultiple() in the parent UserReports object returns true, this method
    * returns the sum (unspecified unit) of the user reported values during the
    * measurement interval for all other entities than those covered by
    * getValues(). Otherwise, if isMultiple() in the parent UserReports object
    * returns false, this method returns the sum (unspecified unit) of the user
    * reported single-valued values during the measurement interval.
    *
    * @return if UserReports.isMultiple() is true, the sum of the user reported
    * values during the measurement interval for all other entities than those
    * covered by getValues(), otherwise, the sum of the user reported single-
    * valued values during the measurement interval.
    */
   public int getSumOther()
   {
      return sumOther;
   }

   /**
    * If isMultiple() in the parent UserReports object returns true, this method
    * returns the maximum (unspecified unit) of the user reported values during
    * the measurement interval for all other entities than those covered by
    * getValues(). Otherwise, if isMultiple() in the parent UserReports object
    * returns false, this method returns the maximum (unspecified unit) of the
    * user reported single-valued values during the measurement interval.
    * <p>
    * Only applicable if isMaxMin() in the parent UserReports object returns
    * true.
    *
    * @return if UserReports.isMultiple() is true, the maximum of the user
    * reported values during the measurement interval for all other entities
    * than those covered by getValues(), otherwise, the maximum of the user
    * reported single-valued values during the measurement interval.
    */
   public int getMaxOther()
   {
      return maxOther;
   }

   /**
    * If isMultiple() in the parent UserReports object returns true, this method
    * returns the minimum (unspecified unit) of the user reported values during
    * the measurement interval for all other entities than those covered by
    * getValues(). Otherwise, if isMultiple() in the parent UserReports object
    * returns false, this method returns the minimum (unspecified unit) of the
    * user reported single-valued values during the measurement interval.
    * <p>
    * Only applicable if isMaxMin() in the parent UserReports object returns
    * true.
    *
    * @return if UserReports.isMultiple() is true, the minimum of the user
    * reported values during the measurement interval for all other entities
    * than those covered by getValues(), otherwise, the minimum of the user
    * reported single-valued values during the measurement interval.
    */
   public int getMinOther()
   {
      return minOther;
   }

   /**
    * Return an array of the user report measurement values.
    * <p>
    * If isMaxMin() in the parent UserReports object returns true,
    * MaxMinUserReportValues is returned, otherwise UserReportValues is
    * returned.
    * <p>
    * Only applicable if isMultiple() in the parent UserReports object returns
    * true. In that case, multiple values may be returned, one for each measured
    * entity (identified by the id of the UserReportValue).
    *
    * @return an array of the user report measurement values.
    */
   public UserReportValue[] getValues()
   {
      return values;
   }

   /**
    * This class contains user report measurement values. A UserReportValue
    * object is immutable and its content is a snapshot at the time of the
    * retrieving Target.getUserReports() call.
    *
    * @see com.ose.system.UserReport#getValues()
    */
   public static class UserReportValue
   {
      private final int id;
      private final int sum;

      /**
       * Create a new user report value object.
       *
       * @param value  the internal user report value object.
       */
      UserReportValue(MonitorUserReportValue value)
      {
         if (value == null)
         {
            throw new NullPointerException();
         }
         this.id = value.id;
         this.sum = value.sum;
      }

      /**
       * Create a new user report value object.
       *
       * @param id   the id.
       * @param sum  the sum.
       */
      UserReportValue(int id, int sum)
      {
         this.id = id;
         this.sum = sum;
      }

      /**
       * Create a new user report value object for off-line usage.
       *
       * @param id   the id.
       * @param sum  the sum.
       * @return a new user report value object for off-line usage.
       */
      public static UserReportValue getInstance(int id, int sum)
      {
         return new UserReportValue(id, sum);
      }

      /**
       * Return the id of the measured entity.
       *
       * @return the id of the measured entity.
       */
      public int getId()
      {
         return id;
      }

      /**
       * Return the sum of user reported values during the measurement interval
       * (unspecified unit) for the entity with the id returned by getId().
       *
       * @return the sum of user reported values during the measurement interval
       * for the entity with the id returned by getId().
       */
      public int getSum()
      {
         return sum;
      }
   }

   /**
    * This class contains user report measurement values including minumum and
    * maximum values. A MaxMinUserReportValue object is immutable and its
    * content is a snapshot at the time of the retrieving
    * Target.getUserReports() call.
    *
    * @see com.ose.system.UserReport#getValues()
    */
   public static class MaxMinUserReportValue extends UserReportValue
   {
      private final int max;
      private final int min;

      /**
       * Create a new max-min user report value object.
       *
       * @param value  the internal max-min user report value object.
       */
      MaxMinUserReportValue(MonitorMaxMinUserReportValue value)
      {
         super(value);
         this.max = value.max;
         this.min = value.min;
      }

      /**
       * Create a new max-min user report value object.
       *
       * @param id   the id.
       * @param sum  the sum.
       * @param max  the max.
       * @param min  the min.
       */
      MaxMinUserReportValue(int id, int sum, int max, int min)
      {
         super(id, sum);
         this.max = max;
         this.min = min;
      }

      /**
       * Create a new max-min user report value object for off-line usage.
       *
       * @param id   the id.
       * @param sum  the sum.
       * @param max  the max.
       * @param min  the min.
       * @return a new max-min user report value object for off-line usage.
       */
      public static MaxMinUserReportValue getInstance(int id,
                                                      int sum,
                                                      int max,
                                                      int min)
      {
         return new MaxMinUserReportValue(id, sum, max, min);
      }

      /**
       * Return the maximum value stored during the measurement interval.
       *
       * @return the maximum value stored during the measurement interval.
       */
      public int getMax()
      {
         return max;
      }

      /**
       * Return the minimum value stored during the measurement interval.
       *
       * @return the minimum value stored during the measurement interval.
       */
      public int getMin()
      {
         return min;
      }
   }
}
