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

import java.util.ArrayList;
import java.util.List;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.ChartContentTransformer;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

class ProfilerContentFilter extends ChartContentTransformer
{
   private final List filteredSeries = new ArrayList();

   private final MutableRange filteredSeriesRange = new MutableRange();

   private boolean enabled;

   private boolean[] sourceSeriesEnabled;

   public double getValue(int layer, int series, int item)
   {
      return getSourceContentProvider().getValue(
            layer, getFilteredSeries(series), item);
   }

   public String getValueLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getValueLabel(
            layer, getFilteredSeries(series), item);
   }

   public String getItemLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getItemLabel(
            layer, getFilteredSeries(series), item);
   }

   public String getSeriesLabel(int layer, int series)
   {
      return getSourceContentProvider().getSeriesLabel(
            layer, getFilteredSeries(series));
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
      return (enabled ? filteredSeriesRange :
            getSourceContentProvider().getSeriesRange());
   }

   public Range getLayerRange()
   {
      return getSourceContentProvider().getLayerRange();
   }

   protected void sourceContentChanged(ChartContentProvider contentProvider)
   {
      // TODO: If the underlying source content provider would change its
      // series, we would need to recreate the filtered series and preferably
      // keep the enablement state of those series that are still existing.
      // To do that, we would need to store the labels of the series and use
      // them as their identity. Any new series should probably be enabled by
      // default.
   }

   public void setEnabled(boolean enabled)
   {
      this.enabled = enabled;
      filteredSeries.clear();
      if (enabled)
      {
         int sourceSeriesCount =
            getSourceContentProvider().getSeriesRange().getCount();
         sourceSeriesEnabled = new boolean[sourceSeriesCount];
         for (int i = 0; i < sourceSeriesEnabled.length; i++)
         {
            sourceSeriesEnabled[i] = true;
            filteredSeries.add(new Integer(i));
         }
      }
      filteredSeriesRange.setCount(filteredSeries.size());
      notifyContentChanged();
   }

   public void setAllSeriesEnabled(boolean allSeriesEnabled)
   {
      if (enabled)
      {
         filteredSeries.clear();
         for (int i = 0; i < sourceSeriesEnabled.length; i++)
         {
            sourceSeriesEnabled[i] = allSeriesEnabled;
            if (allSeriesEnabled)
            {
               filteredSeries.add(new Integer(i));
            }
         }
         filteredSeriesRange.setCount(filteredSeries.size());
         notifyContentChanged();
      }
   }

   public void setSeriesEnabled(int series, boolean seriesEnabled)
   {
      if ((series < 0) || (series >= sourceSeriesEnabled.length))
      {
         throw new IllegalArgumentException();
      }

      if (enabled && (sourceSeriesEnabled[series] != seriesEnabled))
      {
         sourceSeriesEnabled[series] = seriesEnabled;
         filteredSeries.clear();
         for (int i = 0; i < sourceSeriesEnabled.length; i++)
         {
            if (sourceSeriesEnabled[i] == true)
            {
               filteredSeries.add(new Integer(i));
            }
         }
         filteredSeriesRange.setCount(filteredSeries.size());
         notifyContentChanged();
      }
   }

   public boolean isSeriesEnabled(int series)
   {
      if ((series < 0) || (series >= sourceSeriesEnabled.length))
      {
         throw new IllegalArgumentException();
      }

      return enabled && sourceSeriesEnabled[series];
   }

   public int getSourceItem(int item)
   {
      return item;
   }
   
   public int getSourceSeries(int series)
   {
      return getFilteredSeries(series);
   }
   
   public int getSourceLayer(int layer)
   {
      return layer;
   }
   
   private int getFilteredSeries(int series)
   {
      if (enabled)
      {
         if (filteredSeriesRange.contains(series))
         {
            return ((Integer) filteredSeries.get(series)).intValue();
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
}
