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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.viewers.Viewer;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;

class CPUProcessProfilerContentProvider extends ProfilerContentProvider
{
   private final int numExecutionUnits;
   private final short executionUnit;
   private final IdNameMap idNameMap;
   private final Map pidMap = new HashMap();
   private final List pidList = new ArrayList();

   CPUProcessProfilerContentProvider(int numExecutionUnits,
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
      pidMap.clear();
      pidList.clear();
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
            CPUProcessReport report = (CPUProcessReport) getReport(i);
            int totalMax = report.getSumOther();
            
            if (report.getSumOther() > getMax())
            {
               setMax(report.getSumOther());
            }
            for (int j = 0; j < report.getSumProcesses().length; j++)
            {
               int sum = report.getSumProcesses()[j].getSum();
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
            
            if (series < pidList.size())
            {
               return idNameMap.getName(((Integer)pidList.get(series)).intValue());
            }
         }
      }
      return "";
   }

   public double getValue(int layer, int series, int item)
   {
      if (layer == 0 && item < getNumReports())
      {
         CPUProcessReport report = (CPUProcessReport)getReport(item);
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
            
            if (series < pidList.size())
            {
               for (int i = 0; i < report.getSumProcesses().length; i++)
               {
                  CPUProcessLoad value = report.getSumProcesses()[i];
                  
                  if (value.getId() == ((Integer)pidList.get(series)).intValue())
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
            CPUProcessReport report = (CPUProcessReport) addReports[i];
            int totalMax = report.getSumOther();
            
            if (report.getSumOther() > getMax())
            {
               setMax(report.getSumOther());
            }
            for (int j = 0; j < report.getSumProcesses().length; j++)
            {
               CPUProcessLoad value = report.getSumProcesses()[j];
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
         CPUProcessReport report = (CPUProcessReport)addReports[i];

         for (int j = 0; j < report.getSumProcesses().length; j++)
         {
            Integer pid = new Integer(report.getSumProcesses()[j].getId());
            
            if (pidMap.get(pid) == null)
            {
               pidMap.put(pid, Boolean.TRUE);
               pidList.add(pid);
            }
         }         
      }
      
      oldNumReports = getNumReports();
      super.addReports(addReports); 

      // If reports were removed, remove no longer used pids.
      if ((addReports.length > 0) && (getNumReports() == oldNumReports))
      {
         Map tmpPidMap = new HashMap();

         for (int i = 0; i < getNumReports(); i++)
         {
            CPUProcessReport report = (CPUProcessReport) getReport(i);
            CPUProcessLoad[] sumProcesses = report.getSumProcesses();

            for (int j = 0; j < sumProcesses.length; j++)
            {
               Integer pid = new Integer(sumProcesses[j].getId());
               if (tmpPidMap.get(pid) == null)
               {
                  tmpPidMap.put(pid, Boolean.TRUE);
               }
            }
         }

         for (Iterator i = pidList.iterator(); i.hasNext();)
         {
            Integer pid = (Integer) i.next();
            if (tmpPidMap.get(pid) == null)
            {
               pidMap.remove(pid);
               i.remove();
            }
         }
      }

      setSeriesCount(pidList.size() + 1);
   }
}
