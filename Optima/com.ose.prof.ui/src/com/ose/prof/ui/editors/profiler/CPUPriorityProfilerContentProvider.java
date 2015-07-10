/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.profiler;

import com.ose.system.CPUPriorityReport;

class CPUPriorityProfilerContentProvider extends ProfilerContentProvider
{
   static final String[] PRIORITIES = new String[34];

   private final int numExecutionUnits;
   private final short executionUnit;

   static
   {
      PRIORITIES[0] = "Interrupt";      
      
      for (int i = 1; i < 33 ; i++)
      {
         PRIORITIES[i] = Integer.toString(i - 1);
      }
      
      PRIORITIES[33] = "Background";
   }
   
   CPUPriorityProfilerContentProvider(int numExecutionUnits,
                                      short executionUnit,
                                      int maxReports,
                                      boolean relative)
   {
      super(maxReports, relative);
      
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
      setItemRange(0, 50);
      setSeriesRange(0, 34);
      setLayerRange(0, 1);
   }

   void setRelative(boolean relative)
   {
      if (relative)
      {
         setMax(100);
         setTotalMax(100);
      }
      else
      {
         setMax(10);
         setTotalMax(10);
         
         for (int i = 0; i < getNumReports(); i++)
         {
            CPUPriorityReport report = (CPUPriorityReport) getReport(i);
            int totalMax = report.getSumInterrupt() + report.getSumBackground();
            
            if (report.getSumInterrupt() > getMax())
            {
               setMax(report.getSumInterrupt());
            }
            if (report.getSumBackground() > getMax())
            {
               setMax(report.getSumBackground());
            }
            for (int j = 0; j < report.getSumPrioritized().length; j++)
            {
               int sum = report.getSumPrioritized()[j];
               totalMax += sum;
               
               if (sum > getMax())
               {
                  setMax(sum);
               }
            }
            if (totalMax > getTotalMax())
            {
               setTotalMax(totalMax);
            }
         }
      }
      super.setRelative(relative); 
   }
   
   public String getSeriesLabel(int layer, int series)
   {
      if (layer == 0 && series < PRIORITIES.length)
      {
         return PRIORITIES[series];
      }
      return "";
   }

   public double getValue(int layer, int series, int item)
   {
      if (layer == 0 && series < PRIORITIES.length && item < getNumReports())
      {
         CPUPriorityReport report = (CPUPriorityReport)getReport(item);
         int interval = report.getInterval();
         int sum;
         
         switch(series)
         {
            case 0: 
               sum = report.getSumInterrupt();
               return toRelativeDouble(sum,
                                       interval,
                                       numExecutionUnits,
                                       executionUnit);
            case 33:
               sum = report.getSumBackground();
               return toRelativeDouble(sum,
                                       interval,
                                       numExecutionUnits,
                                       executionUnit);
            default:
               if (report.getSumPrioritized().length > series - 1)
               {
                  sum = report.getSumPrioritized()[series - 1];
                  return toRelativeDouble(sum,
                                          interval,
                                          numExecutionUnits,
                                          executionUnit);
               }
         }          
      }
      return Double.NaN;
   }
   
   public void addReports(Object[] addReports)
   {
      if (!isRelative())
      {
         for (int i = 0; i < addReports.length; i++)
         {
            CPUPriorityReport report = (CPUPriorityReport) addReports[i];
            int totalMax = report.getSumInterrupt() + report.getSumBackground();
            
            if (report.getSumInterrupt() > getMax())
            {
               setMax(report.getSumInterrupt());
            }
            if (report.getSumBackground() > getMax())
            {
               setMax(report.getSumBackground());
            }
            for (int j = 0; j < report.getSumPrioritized().length; j++)
            {
               totalMax += report.getSumPrioritized()[j];
               if (report.getSumPrioritized()[j] > getMax())
               {
                  setMax(report.getSumPrioritized()[j]);
               }
            }
            if (totalMax > getTotalMax())
            {
               setTotalMax(totalMax);
            }
         }
      }
      super.addReports(addReports);
   }
}
