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

/**
 * Stacked bar chart in 3D.
 */
public class StackedBarChart3D extends LayeredCartesianChart3D
{
   private LossMesh lossMesh;
   private float[] barColorScales = {1.0f, 1.0f, 1.0f, 0.7f, 0.7f, 0.7f};

   public StackedBarChart3D(String title,
                            ChartContentProvider contentProvider,
                            ChartColorProvider colorProvider)
   {
      super(title, contentProvider, colorProvider);
      
      lossMesh = new LossMesh(getItemWidth(), getItemWidth());

      disableNotifications();
      
      // Configurables
      setLayerWindow(0, 1);
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
            
      g.beginQuads();
      
      for (int s = 0; s < seriesCount; s++)
      {
         for (int i = 0; i < itemCount; i++)
         {
            float x0 = (float)i * (itemWidth + itemWidthSpace);
            float z0 = -((float)s * (itemDepth + itemDepthSpace) + itemDepth);
            float x1 = x0 + itemWidth;
            float z1 = z0 + itemDepth;
            
            float yPos0 = 0;
            float yNeg0 = 0;

            for (int l = 0; l < layerCount; l++)
            {
               double value = content.getValue(l + layerOffset,
                                               s + seriesOffset,
                                               i + itemOffset);
            
               if (!Double.isNaN(value))
               {
                  value /= absMaxValue;
                  ColorRGBA color = colors.getSeriesColor(l + layerOffset, 
                                             getSourceSeries(s + seriesOffset));
                  
                  if (value >= 0)
                  {
                     float y1 = yPos0 + (float)value;
                     g.fillBar(x0, yPos0, z0, x1, y1, z1, color, barColorScales);
                     yPos0 = y1;
                  }
                  else
                  {
                     float y1 = yNeg0 + (float)value;
                     g.fillBar(x0, y1, z0, x1, yNeg0, z1, color, barColorScales);
                     yNeg0 = y1;
                  }
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
