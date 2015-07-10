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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.ose.chart.model.ChartContentProviderAdapter;
import com.ose.system.Target;
import com.ose.system.TargetReport;

abstract class ProfilerContentProvider extends ChartContentProviderAdapter
   implements IStructuredContentProvider, ProfilerReportProvider
{
   private final int maxReports;
   private final List reports = new ArrayList(1000);

   private TableViewer viewer;
   
   private boolean relative;
   private boolean realTime;
   private int max;
   private int min;
   private int totalMax;
   private int totalMin;

   private DecimalFormat zeroDecimalsFormatter = new DecimalFormat("#####0",
         new DecimalFormatSymbols(Locale.US));
   
   private DecimalFormat oneDecimalFormatter = new DecimalFormat("#####0.0",
         new DecimalFormatSymbols(Locale.US));
   
   ProfilerContentProvider(int maxReports, boolean relative)
   {
      if (maxReports <= 0)
      {
         throw new IllegalArgumentException();
      }
      this.maxReports = maxReports;
      this.relative = relative;
      this.realTime = true;
      
      max = relative ? 100 : 10;
      min = 0;
      totalMax = max;
      totalMin = min;
   }
   
   void setRelative(boolean relative)
   {
      this.relative = relative;

      notifyContentChanged();
   }

   boolean isRelative()
   {
      return relative;
   }

   void setRealTime(boolean realTime)
   {
      this.realTime = realTime;

      notifyContentChanged();
   }

   boolean isRealTime()
   {
      return realTime;
   }

   void setMax(int max)
   {
      this.max = max;
   }
   
   int getMax()
   {
      return max;
   }
   
   void setMin(int min)
   {
      this.min = min;
   }
   
   int getMin()
   {
      return min;
   }

   void setTotalMax(int totalMax)
   {
      this.totalMax = totalMax;
   }

   int getTotalMax()
   {
      return totalMax;
   }

   void setTotalMin(int totalMin)
   {
      this.totalMin = totalMin;
   }

   int getTotalMin()
   {
      return totalMin;
   }

   public TargetReport getReport(int i)
   {
      return (TargetReport)reports.get(i);
   }

   public int getNumReports()
   {
      return reports.size();
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (TableViewer) viewer;
      
      if (newInput instanceof Object[])
      {
         int numOverflownReports;
         Object[] newReports = (Object[]) newInput;

         numOverflownReports = newReports.length - maxReports;
         
         if (numOverflownReports < 0)
         {            
            numOverflownReports = 0;
         }         
         reports.clear();
         for (int i = numOverflownReports; i < newReports.length; i++)
         {
            reports.add(newReports[i]);
         }

         setItemCount(reports.size());
         notifyContentChanged();
      }
   }

   public void dispose()
   {
      reports.clear();
   }

   public Object[] getElements(Object inputElement)
   {
      return reports.toArray(); 
   }

   public void addReports(Object[] addReports)
   {
      int numOverflownReports;
      Object[] removeReports = null;

      if (addReports == null || addReports.length > maxReports)
      {
         throw new IllegalArgumentException();
      }

      numOverflownReports = reports.size() + addReports.length - maxReports;

      if (numOverflownReports > 0)
      {
         reports.subList(0, numOverflownReports).clear();
      }

      for (int i = 0; i < addReports.length; i++)
      {
         reports.add(addReports[i]);
      }

      if (!viewer.getTable().isDisposed())
      {
         viewer.getTable().setRedraw(false);
         if (removeReports != null)
         {
            viewer.remove(removeReports);
         }
         viewer.add(addReports);
         
         if (addReports.length > 0)
         {
            viewer.reveal(addReports[addReports.length - 1]);
         }
         viewer.getTable().setRedraw(true);
      }

      setItemCount(reports.size());

      notifyContentChanged();
   }

   public String getValueLabel(double value)
   {
      String label;

      if (Double.isNaN(value) || (value == 0))
      {
         label = "";
      }
      else if (isRelative())
      {
         label = oneDecimalFormatter.format(value);
         if (label.equals("0.0"))
         {
            label = "";
         }
      }
      else
      {
         label = zeroDecimalsFormatter.format(value);
      }

      return label;
   }

   public String getValueLabel(int layer, int series, int item)
   {
      double value = getValue(layer, series, item);
      String label;
      
      if (Double.isNaN(value) || (value == 0))
      {
         label = "";
      }
      else if (isRelative())
      {
         label = oneDecimalFormatter.format(value);         
         if (label.equals("0.0"))
         {
            label = "";
         }
      }
      else
      {
         label = zeroDecimalsFormatter.format(value);
      }
      
      return label;
   }

   public String getItemLabel(int layer, int series, int item)
   {
      if (layer == 0 && item < getNumReports())
      {
         TargetReport report = (TargetReport)getReport(item);

         if (isRealTime())
         {
            return TimeFormatter.format(report.getNanoSeconds());
         }
         else
         {
            return toLongString(report.getTick()) + ":"
                  + toLongString(report.getNTick());
         }
      }
      return "";
   }

   String toLongString(int i)
   {
      return Long.toString(0xffffffffL & i);
   }

   String toLongHexString(int i)
   {
      return "0x" + Long.toHexString(0xffffffffL & i);
   }

   double toRelativeDouble(int sum, int interval)
   {
      if (isRelative())
      {
         return (100.0 * sum) / interval;
      }
      else
      {
         return (double) sum;
      }
   }

   double toRelativeDouble(int sum,
                           int interval,
                           int numExecutionUnits,
                           short executionUnit)
   {
      if (isRelative())
      {
         if ((numExecutionUnits > 1) && (executionUnit == Target.ALL_EXECUTION_UNITS))
         {
            return (100.0 * sum) / ((double) interval * numExecutionUnits);
         }
         else
         {
            return (100.0 * sum) / interval;
         }
      }
      else
      {
         return (double) sum;
      }
   }
}
