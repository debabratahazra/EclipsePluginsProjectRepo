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

import java.util.Arrays;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.ChartContentTransformer;
import com.ose.chart.model.Range;
import com.ose.chart.ui.Chart;

class ProfilerContentSorter extends ChartContentTransformer
{
   public static final int NONE = 0;

   public static final int ASCENDING_MEAN = 1;

   public static final int DESCENDING_MEAN = 2;

   private final Chart chart;

   private final int valueLayer;

   private int sortOrder;

   private int[] sortedSeries;
   
   ProfilerContentSorter(Chart chart)
   {
      this(chart, 0);
   }
   
   ProfilerContentSorter(Chart chart, int valueLayer)
   {
      if (chart == null)
      {
         throw new IllegalArgumentException();
      }
      this.chart = chart;
      this.valueLayer = valueLayer;
   }   
   
   public double getValue(int layer, int series, int item)
   {
      return getSourceContentProvider().getValue(
            layer, getSortedSeries(series), item);
   }

   public String getValueLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getValueLabel(
            layer, getSortedSeries(series), item);
   }

   public String getItemLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getItemLabel(
            layer, getSortedSeries(series), item);
   }

   public String getSeriesLabel(int layer, int series)
   {
      return getSourceContentProvider().getSeriesLabel(
            layer, getSortedSeries(series));
   }

   public String getLayerLabel(int layer)
   {
      return getSourceContentProvider().getLayerLabel(layer);
   }

   public Range getItemRange()
   {
      return getSourceContentProvider().getItemRange();
   }

   public Range getSeriesRange()
   {
      return getSourceContentProvider().getSeriesRange();
   }

   public Range getLayerRange()
   {
      return getSourceContentProvider().getLayerRange();
   }

   public int getSourceItem(int item)
   {
      return item;
   }
   
   public int getSourceSeries(int series)
   {
      return getSortedSeries(series);
   }
   
   public int getSourceLayer(int layer)
   {
      return layer;
   }
   
   protected void sourceContentChanged(ChartContentProvider contentProvider)
   {
      if (sortOrder != NONE)
      {
         sort(sortOrder);
      }
   }
   
   public void sort(int sortOrder)
   {
      if ((sortOrder != NONE) &&
          (sortOrder != ASCENDING_MEAN) &&
          (sortOrder != DESCENDING_MEAN))
      {
         throw new IllegalArgumentException();
      }
      this.sortOrder = sortOrder;

      if ((sortOrder == ASCENDING_MEAN) ||
          (sortOrder == DESCENDING_MEAN))
      {
         sortByMeanValue();
      }
      else
      {
         sortedSeries = null;
      }

      notifyContentChanged();
   }

   private void sortByMeanValue()
   {
      int seriesCount;
      Range itemRange;
      int itemCount;
      int itemOffset;
      Series[] seriesArray;

      seriesCount = getSourceContentProvider().getSeriesRange().getCount();
      itemRange = chart.getItemSelection();
      itemOffset = itemRange.getOffset();
      itemCount = itemRange.getCount();
      sortedSeries = new int[seriesCount];
      seriesArray = new Series[seriesCount];

      for (int s = 0; s < seriesCount; s++)
      {
         Series series = new Series(s);
         for (int i = itemOffset; i < itemOffset + itemCount; i++)
         {
            series.addValue(getSourceContentProvider().getValue(
                  valueLayer, s, i));
         }
         seriesArray[s] = series;
      }
      Arrays.sort(seriesArray);
      if (sortOrder == ASCENDING_MEAN)
      {
         for (int s = 0; s < seriesCount; s++)
         {
            sortedSeries[s] = seriesArray[s].getIndex();
         }
      }
      else
      {
         for (int s = 0; s < seriesCount; s++)
         {
            sortedSeries[s] = seriesArray[seriesCount - 1 - s].getIndex();
         }
      }
   }

   private int getSortedSeries(int series)
   {
      if (sortOrder != NONE)
      {
         if ((series >= 0) && (series < sortedSeries.length))
         {
            return sortedSeries[series];
         }
         else
         {
            return Integer.MAX_VALUE;
         }
      }
      else
      {
         return series;
      }
   }

   private static class Series implements Comparable
   {
      private final int index;

      private double value;

      Series(int index)
      {
         this.index = index;
      }

      public int getIndex()
      {
         return index;
      }

      public double getValue()
      {
         return value;
      }

      public void addValue(double value)
      {
         if (!Double.isNaN(value))
         {
            this.value += value;
         }
      }

      public int compareTo(Object obj)
      {
         if (obj instanceof Series)
         {
            Series other = (Series) obj;
            return (value < other.value) ? -1 : ((value > other.value) ? 1 : 0);
         }
         else
         {
            return 0;
         }
      }
   }
}
