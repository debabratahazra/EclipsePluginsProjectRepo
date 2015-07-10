/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.chart.model;

/**
 * An abstract convenience class for creating content providers. By default,
 * the content provider has one item, one layer and one series. 
 */
public abstract class ChartContentProviderAdapter extends ChartContentProvider
{   
   private MutableRange itemRange = new MutableRange(0, 1);
   private MutableRange layerRange = new MutableRange(0, 1);
   private MutableRange seriesRange = new MutableRange(0, 1);

   public double getValue(int layer, int series, int item)
   {
      return Double.NaN;
   }
   
   public String getValueLabel(int layer, int series, int item)
   {
      return Double.toString(getValue(layer, series, item));
   }
   
   public String getItemLabel(int layer, int series, int item)
   {
      return "";
   }

   public String getSeriesLabel(int layer, int series)
   {
      return "";
   }
   
   public String getLayerLabel(int layer)
   {
      return "";
   }
   
   public Range getSeriesRange()
   {
      return seriesRange;
   }
   
   public Range getItemRange()
   {
      return itemRange;
   }
   
   public Range getLayerRange()
   {
      return layerRange;
   }
   
   protected void setItemRange(Range range)
   {
      itemRange.set(range);
   }
   
   protected void setItemRange(int offset, int count)
   {
      itemRange.set(offset, count);
   }
   
   protected void setItemOffset(int offset)
   {
      itemRange.setOffset(offset);
   }
   
   protected void setItemCount(int count)
   {
      itemRange.setCount(count);
   }
   
   protected void setSeriesRange(Range range)
   {
      seriesRange.set(range);
   }
   
   protected void setSeriesRange(int offset, int count)
   {
      seriesRange.set(offset, count);
   }
   
   protected void setSeriesOffset(int offset)
   {
      seriesRange.setOffset(offset);
   }
   
   protected void setSeriesCount(int count)
   {
      seriesRange.setCount(count);
   }
   
   protected void setLayerRange(Range range)
   {
      layerRange.set(range);
   }

   protected void setLayerRange(int offset, int count)
   {
      layerRange.set(offset, count);
   }
   
   protected void setLayerOffset(int offset)
   {
      layerRange.setOffset(offset);
   }
   
   protected void setLayerCount(int count)
   {
      layerRange.setCount(count);
   }   
}