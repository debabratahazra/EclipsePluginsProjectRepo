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

import com.ose.system.CPUReport;

class CPUProfilerContentProvider extends ProfilerContentProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;

   CPUProfilerContentProvider(int numExecutionUnits,
                              short executionUnit,
                              int maxReports,
                              boolean relative)
   {
      super(maxReports, relative);
      
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
      setItemRange(0, 0);
      setSeriesRange(0, 1);
      setLayerRange(0, 1);
   }
  
   void setRelative(boolean relative)
   {
      if (relative)
      {
         setMax(100);
      }
      else
      {
         setMax(10);
         
         for (int i = 0; i < getNumReports(); i++)
         {
            CPUReport report = (CPUReport)getReport(i);
            
            if (report.getSum() > getMax())
            {
               setMax(report.getSum());
            }
         }
      }
      super.setRelative(relative); 
   }

   public double getValue(int layer, int series, int item)
   {
      if (layer == 0 && series == 0 && item < getNumReports())
      {
         CPUReport report = (CPUReport)getReport(item);         

         return toRelativeDouble(report.getSum(),
                                 report.getInterval(),
                                 numExecutionUnits,
                                 executionUnit);
      }     
      return Double.NaN;
   }

   public void addReports(Object[] addReports)
   {
      if (!isRelative())
      {
         for (int i = 0; i < addReports.length; i++)
         {
            CPUReport report = (CPUReport)addReports[i];
            
            if (report.getSum() > getMax())
            {
               setMax(report.getSum());
            }
         }
      }
      super.addReports(addReports); 
   }
}
