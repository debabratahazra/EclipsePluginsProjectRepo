/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.viewers.Viewer;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;

class CPUBlockProfilerContentProvider extends ProfilerContentProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;
   private final IdNameMap idNameMap;
   private final Map bidMap = new HashMap();
   private final List bidList = new ArrayList();

   CPUBlockProfilerContentProvider(int numExecutionUnits,
                                   short executionUnit,
                                   int maxReports,
                                   boolean relative,
                                   IdNameMap idNameMap)
   {
      super(maxReports, relative);
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;
      if (idNameMap == null)
      {
         throw new IllegalArgumentException();
      }
      this.idNameMap = idNameMap;

      setItemRange(0, 50);
      setSeriesRange(0, 50);
      setLayerRange(0, 1);
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      bidMap.clear();
      bidList.clear();
      setSeriesCount(1);
      super.inputChanged(viewer, oldInput, newInput);
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
            CPUBlockReport report = (CPUBlockReport) getReport(i);
            int totalMax = report.getSumOther();
            
            if (report.getSumOther() > getMax())
            {
               setMax(report.getSumOther());
            }
            for (int j = 0; j < report.getSumBlocks().length; j++)
            {
               int sum = report.getSumBlocks()[j].getSum();
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
      if (layer == 0)
      {
         if (series == 0)
            return "Others";
         else
         {
            series--;
            
            if (series < bidList.size())
            {
               return idNameMap.getName(((Integer)bidList.get(series)).intValue());
            }
         }
      }
      return "";
   }

   public double getValue(int layer, int series, int item)
   {
      if (layer == 0 && item < getNumReports())
      {
         CPUBlockReport report = (CPUBlockReport)getReport(item);
         int interval = report.getInterval();
         
         if (series == 0)
         {
            return toRelativeDouble(report.getSumOther(),
                                    interval,
                                    numExecutionUnits,
                                    executionUnit);
         }
         else
         {
            series--;
            
            if (series < bidList.size())
            {
               for (int i = 0; i < report.getSumBlocks().length; i++)
               {
                  CPUBlockLoad value = report.getSumBlocks()[i];
                  
                  if (value.getId() == ((Integer)bidList.get(series)).intValue())
                  {
                     return toRelativeDouble(value.getSum(),
                                             interval,
                                             numExecutionUnits,
                                             executionUnit);
                  }
               }
            }
         }
      }
      return Double.NaN;
   }
   
   public void addReports(Object[] addReports)
   {
      int oldNumReports;

      if (!isRelative())
      {
         for (int i = 0; i < addReports.length; i++)
         {
            CPUBlockReport report = (CPUBlockReport) addReports[i];
            int totalMax = report.getSumOther();
            
            if (report.getSumOther() > getMax())
            {
               setMax(report.getSumOther());
            }
            for (int j = 0; j < report.getSumBlocks().length; j++)
            {
               CPUBlockLoad value = report.getSumBlocks()[j];
               totalMax += value.getSum();
               
               if (value.getSum() > getMax())
               {
                  setMax(value.getSum());
               }
            }
            if (totalMax > getTotalMax())
            {
               setTotalMax(totalMax);
            }
         }
      }

      for (int i = 0; i < addReports.length; i++)
      {
         CPUBlockReport report = (CPUBlockReport)addReports[i];

         for (int j = 0; j < report.getSumBlocks().length; j++)
         {
            Integer bid = new Integer(report.getSumBlocks()[j].getId());
            
            if (bidMap.get(bid) == null)
            {
               bidMap.put(bid, Boolean.TRUE);
               bidList.add(bid);
            }
         }         
      }
      
      oldNumReports = getNumReports();
      super.addReports(addReports); 

      // If reports were removed, remove no longer used bids.
      if ((addReports.length > 0) && (getNumReports() == oldNumReports))
      {
         Map tmpBidMap = new HashMap();

         for (int i = 0; i < getNumReports(); i++)
         {
            CPUBlockReport report = (CPUBlockReport) getReport(i);
            CPUBlockLoad[] sumBlocks = report.getSumBlocks();

            for (int j = 0; j < sumBlocks.length; j++)
            {
               Integer bid = new Integer(sumBlocks[j].getId());
               if (tmpBidMap.get(bid) == null)
               {
                  tmpBidMap.put(bid, Boolean.TRUE);
               }
            }
         }

         for (Iterator i = bidList.iterator(); i.hasNext();)
         {
            Integer bid = (Integer) i.next();
            if (tmpBidMap.get(bid) == null)
            {
               bidMap.remove(bid);
               i.remove();
            }
         }
      }

      setSeriesCount(bidList.size() + 1);
   }
}
