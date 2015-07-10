/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.chart.tests.ui;

import java.util.Random;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorRGBA;

/**
 * Color provider that returns a different color for each series, and then, each
 * layer gets a color derived from the series color.
 *
 */
public class RandomColorProviderSeriesLayers implements ChartColorProvider
{
   ColorRGBA colors[];
   MutableRange seriesRange;
   MutableRange layerRange;
   
   public RandomColorProviderSeriesLayers(Range series, Range layers)
   {
      seriesRange = (MutableRange)series;
      layerRange = (MutableRange)layers;
      colors = new ColorRGBA[seriesRange.getCount()];
      
      Random randomGenerator = new Random(System.currentTimeMillis());
      for (int i = 0; i < seriesRange.getCount(); i ++)
      {
         float red = randomGenerator.nextFloat();
         float green = randomGenerator.nextFloat();
         float blue = randomGenerator.nextFloat();
         
         colors[i] = new ColorRGBA(red, green, blue);
      }
   }
   
   public ColorRGBA getSeriesColor(int layer, int series)
   {   
      ColorRGBA color = new ColorRGBA(colors[series]);
      
      int midLayer = layerRange.getCount()/2 + layerRange.getOffset();
      float factor = 1;
      
      if (layer < midLayer)
      {
         for (int i = 0; i < midLayer - layer; i++)
            factor*=0.9;
      }
      else if (layer > midLayer)
      {
         for (int i = 0; i < layer - midLayer; i++)
            factor*=1.1;
      }
      
      color.scale(factor).clamp();
      color.scale(factor).clamp();
      
      return color;
   }
}
