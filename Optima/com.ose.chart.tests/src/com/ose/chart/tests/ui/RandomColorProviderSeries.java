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
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorRGBA;

/**
 * Color provider that returns a different random color for every series.
 *
 */
public class RandomColorProviderSeries implements ChartColorProvider
{
   ColorRGBA colors[];
   
   int seriesOffset;
   
   public RandomColorProviderSeries(int nr_series, int seriesOffset)
   {
      this.seriesOffset = seriesOffset > 0? seriesOffset : 0;
      colors = new ColorRGBA[nr_series];

      Random randomGenerator = new Random(System.currentTimeMillis());
      for (int i = 0; i < nr_series; i ++)
      {
         float red = randomGenerator.nextFloat();
         float green = randomGenerator.nextFloat();
         float blue = randomGenerator.nextFloat();
         
         colors[i] = new ColorRGBA(red, green, blue);
      }
   }

   public ColorRGBA getSeriesColor(int layer, int series)
   {   
      return colors[series - seriesOffset];
   }
}
