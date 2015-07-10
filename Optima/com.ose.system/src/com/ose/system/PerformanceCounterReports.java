/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

import com.ose.system.service.monitor.MonitorCounterReport;

/**
 * This class contains information about performance counter reports.
 * A PerformanceCounterReports object is immutable and its content is
 * a snapshot at the time of the notifying call.
 *
 * @see com.ose.system.PerformanceCounterListener
 */
public class PerformanceCounterReports
{
   private final Target target;

   private final short executionUnit;

   private final int type;

   private final PerformanceCounterReport[] reports;

   /**
    * Create a new performance counter reports object.
    *
    * @param target       the target.
    * @param euId         the execution unit from where the retrieved
    * performance counter reports originates.
    * @param counterType  the performance counter type of the retrieved
    * performance counter reports.
    * @param reports      the internal performance counter report objects.
    */
   PerformanceCounterReports(Target target,
                             short euId,
                             int counterType,
                             MonitorCounterReport[] reports)
   {
      if ((target == null) || (reports == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.executionUnit = euId;
      this.type = counterType;
      this.reports = new PerformanceCounterReport[reports.length];
      for (int i = 0; i < reports.length; i++)
      {
         this.reports[i] = new PerformanceCounterReport(reports[i]);
      }
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
    * Return the execution unit from where the retrieved performance counter
    * reports originates.
    *
    * @return the execution unit from where the retrieved performance counter
    * reports originates.
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Return the performance counter type of the retrieved performance counter
    * reports.
    *
    * @return the performance counter type of the retrieved performance counter
    * reports.
    */
   public int getType()
   {
      return type;
   }

   /**
    * Return an array of the retrieved performance counter reports (never null).
    *
    * @return an array of the retrieved performance counter reports.
    */
   public PerformanceCounterReport[] getReports()
   {
      return reports;
   }
}
