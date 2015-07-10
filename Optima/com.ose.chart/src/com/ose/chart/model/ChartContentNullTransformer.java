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

package com.ose.chart.model;

public class ChartContentNullTransformer extends ChartContentTransformer
{
   public double getValue(int layer, int series, int item)
   {
      return getSourceContentProvider().getValue(layer, series, item);
   }

   public String getValueLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getValueLabel(layer, series, item);
   }

   public String getItemLabel(int layer, int series, int item)
   {
      return getSourceContentProvider().getItemLabel(layer, series, item);
   }

   public String getSeriesLabel(int layer, int series)
   {
      return getSourceContentProvider().getSeriesLabel(layer, series);
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
      return series;
   }
   
   public int getSourceLayer(int layer)
   {
      return layer;
   }

   protected void sourceContentChanged(ChartContentProvider contentProvider)
   {
      // Not needed for the null provider.
   }
}
