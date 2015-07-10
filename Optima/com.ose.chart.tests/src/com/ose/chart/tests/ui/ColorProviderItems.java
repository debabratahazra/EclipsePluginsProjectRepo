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

import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorRGBA;

/**
 * Color provider that returns a single user defined color.
 *
 */
public class ColorProviderItems implements ChartColorProvider
{
   private static final ColorRGBA COLOR = new ColorRGBA(0.9f, 0.9f, 0.3f);
   
   public ColorRGBA getSeriesColor(int dataset, int series)
   {
      return COLOR; 
   }
}
