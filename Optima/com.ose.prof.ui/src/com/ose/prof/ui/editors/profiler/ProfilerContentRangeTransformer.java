/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

import com.ose.chart.model.ChartContentNullTransformer;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;
import com.ose.system.TargetReport;

class ProfilerContentRangeTransformer extends ChartContentNullTransformer
{
   private final ProfilerReportProvider reportProvider;

   /* Range of all virtual and real items. */
   private final MutableRange virtualRange = new MutableRange();

   /* Range of real items in the virtual range. */
   private final MutableRange realRange = new MutableRange();

   /* Offset of the first real item in the virtual item range. */
   private int offset;

   private long integrationInterval;

   private boolean enabled;

   private boolean realTime;

   ProfilerContentRangeTransformer(ProfilerReportProvider reportProvider)
   {
      this.reportProvider = reportProvider;
      this.realTime = true;
   }

   /*
    * @param itemOffset  offset of first real item in the virtual item range.
    * @param itemLength  length of the virtual item range, i.e. the number of
    * real and virtual items.
    * @param integrationInterval  integration interval in nanoseconds.
    */
   public void setup(int itemOffset, int itemLength, long integrationInterval)
   {
      offset = itemOffset;
      virtualRange.setCount(itemLength);
      Range itemRange = getSourceContentProvider().getItemRange();
      realRange.set(offset + itemRange.getOffset(), itemRange.getCount());
      this.integrationInterval = integrationInterval;
      if (enabled)
      {
         notifyContentChanged();
      }
   }

   public void setEnabled(boolean enabled)
   {
      if (this.enabled != enabled)
      {
         this.enabled = enabled;
         notifyContentChanged();
      }
   }

   public void setRealTime(boolean realTime)
   {
      this.realTime = realTime;
      if (enabled)
      {
         notifyContentChanged();
      }
   }

   public double getValue(int layer, int series, int item)
   {
      if (!enabled)
      {
         return super.getValue(layer, series, item);
      }
      else if (containsItem(item))
      {
         return getSourceContentProvider().getValue(layer, series,
               item - offset);
      }
      else
      {
         return Double.NaN;
      }
   }

   public String getValueLabel(int layer, int series, int item)
   {
      if (!enabled)
      {
         return super.getValueLabel(layer, series, item);
      }
      else if (containsItem(item))
      {
         return getSourceContentProvider().getValueLabel(layer, series,
               item - offset);
      }
      else
      {
         return "";
      }
   }

   public String getItemLabel(int layer, int series, int item)
   {
      if (!enabled)
      {
         return super.getItemLabel(layer, series, item);
      }
      else if (containsItem(item))
      {
         return getSourceContentProvider().getItemLabel(layer, series,
               item - offset);
      }
      else
      {
         if (realTime)
         {
            return TimeFormatter.format(getTimeOfReport(item));
         }
         else
         {
            return "";
         }
      }
   }

   public Range getItemRange()
   {
      return enabled ? virtualRange : super.getItemRange();
   }

   public long getTimeOfReport(int index)
   {
      int i = index - offset;

      if (i < 0)
      {
         // Calculate approximate time for virtual report before real reports.
         TargetReport firstReport = reportProvider.getReport(0);
         long firstNanos = firstReport.getNanoSeconds();
         return firstNanos + i * integrationInterval;
      }

      int lastIndex = getSourceContentProvider().getItemRange().getCount() - 1;

      if (i > lastIndex)
      {
         // Calculate approximate time for virtual report after real reports.
         TargetReport lastReport = reportProvider.getReport(lastIndex);
         long lastNanos = lastReport.getNanoSeconds();
         return lastNanos + (i - lastIndex) * integrationInterval;
      }

      // Return time for real report.
      return reportProvider.getReport(i).getNanoSeconds();
   }

   /* Modified binary search algorithm. Requires that the time stamp of each
    * report is greater than or equal to the time stamp of the preceding report.
    */
   public int getIndexOfClosestReport(long keyTime)
   {
      int reportCount = getItemRange().getCount();
      int low = 0;
      int high = reportCount - 1;
      long lowTime = getTimeOfReport(low);
      long highTime = getTimeOfReport(high);

      if (keyTime < lowTime)
      {
         return low;
      }

      if (keyTime > highTime)
      {
         return high;
      }

      while (low <= high)
      {
         int mid = (low + high) >> 1;
         long midTime = getTimeOfReport(mid);

         if (keyTime < midTime)
         {
            long midMinusOneTime = getTimeOfReport(Math.max(0, mid - 1));
            if (keyTime > midMinusOneTime)
            {
               if (keyTime - midMinusOneTime < midTime - keyTime)
               {
                  return mid - 1;
               }
               else
               {
                  return mid;
               }
            }
            else
            {
               high = mid - 1;
            }
         }
         else if (keyTime > midTime)
         {
            long midPlusOneTime = getTimeOfReport(
                  Math.min(reportCount - 1, mid + 1));
            if (keyTime < midPlusOneTime)
            {
               if (midPlusOneTime - keyTime < keyTime - midTime)
               {
                  return mid + 1;
               }
               else
               {
                  return mid;
               }
            }
            else
            {
               low = mid + 1;
            }
         }
         else
         {
            return mid;
         }
      }

      return -1;
   }

   private boolean containsItem(int item)
   {
      return realRange.contains(item);
   }
}
