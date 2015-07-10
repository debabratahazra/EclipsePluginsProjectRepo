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

package com.ose.chart.ui;

import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

/**
 * Abstract base class for all layered charts with cartesian coordinates in 3D
 * space.
 */
public abstract class LayeredCartesianChart3D extends CartesianChart3D
{
   // --- Start of configurable properties ------------------------------------

   private int backdropLabelLayer = 0;
   
   private MutableRange layerWindow = new MutableRange();

   // --- End of configurable properties --------------------------------------

   public LayeredCartesianChart3D(String title, ChartContentProvider content,
         ChartColorProvider colors)
   {
      super(title, content, colors);
   }
   
   public Range getLayerWindow()
   {
      return layerWindow;
   }

   public void setLayerWindow(int offset, int count)
   {
      if (!(layerWindow.getOffset() == offset
            && layerWindow.getCount() == count))
      {
         layerWindow.setOffset(offset);
         layerWindow.setCount(count);
         constrainLayerWindow();
         notifyLayerWindowChanged();
         notifyChartUpdated();
      }
   }

   public void setLayerWindow(Range range)
   {
      setLayerWindow(range.getOffset(), range.getCount());
   }

   public void contentChanged(ChartContentProvider provider)
   {
      constrainLayerWindow();
      super.contentChanged(provider);
   }

   protected void constrainLayerWindow()
   {
      layerWindow.constrainPosition(getContentProvider().getLayerRange());
   }
   
   public int getBackdropLabelLayer()
   {
      return backdropLabelLayer;
   }

   public void setBackdropLabelLayer(int layer)
   {
      if (backdropLabelLayer != layer)
      {
         backdropLabelLayer = layer;
         notifyChartUpdated();
      }
   }
   
   protected String getBackdropValueLabel(int layer, int series, int item)
   {
      ChartContentProvider content = getContentProvider();
      return content.getValueLabel(layer + getBackdropLabelLayer(),
                                   series, item);
   }
}
