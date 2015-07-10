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
import com.ose.chart.model.Range;

/**
 * Bar chart in 3D with items from several layers clustered. 
 */
public class ClusteredBarChart3D extends LayeredCartesianChart3D
{
   private LossMesh lossMesh;
   private float[] barColorScales = {1.0f, 1.0f, 0.7f, 0.7f, 0.7f, 0.7f};
   private ColorRGBA deselectedColor = new ColorRGBA();

   public ClusteredBarChart3D(String title,
                              ChartContentProvider contentProvider,
                              ChartColorProvider colorProvider)
   {
      super(title, contentProvider, colorProvider);

      lossMesh = new LossMesh(getItemWidth(), getItemWidth());
      
      disableNotifications();
      
      // Configurables
      setLayerWindow(1, 1);
      setSeriesWindow(0, 50);
      setItemWindow(0, 50);
      setItemTickStep(5);
      
      resetCamera();

      enableNotifications();
   }

   protected void drawValues(GraphicsContext g, int width, int height)
   {
      ChartContentProvider content = getContentProvider();
      ChartColorProvider colors = getColorProvider();
      double absMaxValue = getAbsMaxValue();

      int layerOffset = getLayerWindow().getOffset();
      int layerCount = getLayerWindow().getCount();
      int seriesOffset = getSeriesWindow().getOffset();
      int seriesCount = getSeriesWindow().getCount();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();

      float itemWidth = getItemWidth();
      float itemWidthSpace = getItemWidthSpace();
      float itemDepth = getItemDepth();
      float itemDepthSpace = getItemDepthSpace();
      
      Range itemSelection = getItemSelection();
       
      g.beginQuads();
      
      for (int s = 0; s < seriesCount; s++)
      {
         for (int i = 0; i < itemCount; i++)
         {
            float x0 = (float)i * (itemWidth + itemWidthSpace);
            float z0 = -((float)s * (itemDepth + itemDepthSpace) + itemDepth);
            //float x1 = x0 + itemWidth;
            float z1 = z0 + itemDepth;
            float dx = (layerCount > 0) ? itemWidth / (float)layerCount : itemWidth;
            
            for (int l = 0; l < layerCount; l++)
            {
               double value = content.getValue(l + layerOffset,
                                               s + seriesOffset,
                                               i + itemOffset);
               
               if (!Double.isNaN(value))
               {
                  ColorRGBA color = colors.getSeriesColor(l + layerOffset,
                                             getSourceSeries(s + seriesOffset));
                  if (!itemSelection.contains(itemOffset + i))
                  {
                     deselectedColor.set(color).desaturate(1.0f);
                     color = deselectedColor;
                  }

                  value /= absMaxValue;
                  
                  double y0 = value < 0 ? value : 0;
                  double y1 = value < 0 ? 0 : value;

                  // Draw value
                  g.fillBar(x0 + (float)l * dx, (float)y0, z0,
                            x0 + (float)l * dx + dx, (float)y1, z1,
                            color, barColorScales);
               }
               else
               {
                  g.endQuads();
                  // Temporarily disabling backface culling
                  // every time a NaN is encountered is probably
                  // not a good idea in terms of performance, as
                  // state changes can be expensive.
                  g.setBackfaceCulling(false);
                  lossMesh.drawAt(g, x0, 0, z0);
                  g.setBackfaceCulling(true);
                  g.beginQuads();
               }
            }
         }
      }

      g.endQuads();
   }
}
