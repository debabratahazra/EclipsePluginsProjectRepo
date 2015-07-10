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
import com.ose.system.UserReport;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;

class UserProfilerContentProvider extends ProfilerContentProvider
{
   static final int MIN_LAYER = 0;
   static final int VALUE_LAYER = 1;
   static final int MAX_LAYER = 2;
   
   private final IdNameMap idNameMap;
   private final Map pidMap = new HashMap();
   private final List pidList = new ArrayList();
   private boolean maxMin = false;

   UserProfilerContentProvider(int maxReports, 
                               IdNameMap idNameMap)
   {
      super(maxReports, false);
      if (idNameMap == null)
      {
         throw new IllegalArgumentException();
      }
      this.idNameMap = idNameMap;

      setItemRange(0, 50);
      setSeriesRange(0, 50);
      setLayerRange(MIN_LAYER, 3);
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      pidMap.clear();
      pidList.clear();
      setSeriesCount(1);
      super.inputChanged(viewer, oldInput, newInput);
   }

   public void setMaxMin(boolean maxMin)
   {
      this.maxMin = maxMin;
   }
   
   public boolean isMaxMin()
   {
      return maxMin;
   }
   
   void setRelative(boolean relative)
   {
      throw new UnsupportedOperationException();
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
               return idNameMap.getName(((Integer) pidList.get(series)).intValue());
            }
         }
      }
      return "";
   }

   public double getValue(int layer, int series, int item)
   {
      if ((layer >= MIN_LAYER || layer <= MAX_LAYER) && item < getNumReports())
      {
         UserReport report = (UserReport)getReport(item);
                  
         if (series == 0)
         {
            if (maxMin)
            {
               switch (layer)
               {
               case MIN_LAYER:
                  return (double) report.getMinOther();
               case VALUE_LAYER:
                  return (double) report.getSumOther();
               case MAX_LAYER:
                  return (double) report.getMaxOther();
               default:
                  return Double.NaN;
               }
            }
            else if (layer == VALUE_LAYER)
            {
               return (double) report.getSumOther();
            }
            else
            {
               return Double.NaN;
            }
         }
         else
         {
            series--;
            
            if (series < pidList.size())
            {
               for (int i = 0; i < report.getValues().length; i++)
               {
                  UserReportValue value = report.getValues()[i];
                  
                  if (value.getId() == ((Integer)pidList.get(series)).intValue())
                  {
                     if (maxMin)
                     {
                        switch (layer)
                        {
                        case MIN_LAYER:
                           return ((MaxMinUserReportValue) value).getMin();
                        case VALUE_LAYER:
                           return (double) value.getSum();
                        case MAX_LAYER:
                           return ((MaxMinUserReportValue) value).getMax();
                        default:
                           return Double.NaN;
                        }
                     }
                     else if (layer == VALUE_LAYER)
                     {
                        return (double)value.getSum();
                     }
                     else
                     {
                        return Double.NaN;
                     }
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

      for (int i = 0; i < addReports.length; i++)
      {
         UserReport report = (UserReport) addReports[i];
         int total = report.getSumOther();
         
         if (report.getSumOther() > getMax())
         {
            setMax(report.getSumOther());
         }
         else if (report.getSumOther() < getMin())
         {
            setMin(report.getSumOther());
         }
         if (isMaxMin())
         {
            if (report.getMaxOther() > getMax())
            {
               setMax(report.getMaxOther());
            }
            if (report.getMinOther() < getMin())
            {
               setMin(report.getMinOther());
            }
         }

         for (int j = 0; j < report.getValues().length; j++)
         {
            UserReportValue value = report.getValues()[j];
            total += value.getSum();
            
            if (value.getSum() > getMax())
            {
               setMax(value.getSum());
            }
            else if (value.getSum() < getMin())
            {
               setMin(value.getSum());
            }

            if (isMaxMin())
            {
               MaxMinUserReportValue maxMinValue = (MaxMinUserReportValue) value;
               
               if (maxMinValue.getMax() > getMax())
               {
                  setMax(maxMinValue.getMax());
               }
               if (maxMinValue.getMin() < getMin())
               {
                  setMin(maxMinValue.getMin());
               }
            }
         }

         if (total > getTotalMax())
         {
            setTotalMax(total);
         }
         else if (total < getTotalMin())
         {
            setTotalMin(total);
         }
      }
      
      for (int i = 0; i < addReports.length; i++)
      {
         UserReport report = (UserReport)addReports[i];

         for (int j = 0; j < report.getValues().length; j++)
         {
            Integer pid = new Integer(report.getValues()[j].getId());
            
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
            UserReport report = (UserReport) getReport(i);
            UserReportValue[] values = report.getValues();

            for (int j = 0; j < values.length; j++)
            {
               Integer pid = new Integer(values[j].getId());
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
