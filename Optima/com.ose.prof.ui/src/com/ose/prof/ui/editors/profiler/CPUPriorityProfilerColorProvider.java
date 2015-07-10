/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.profiler;

import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorRGBA;

class CPUPriorityProfilerColorProvider implements ChartColorProvider
{
   static final ColorRGBA[] COLORS = new ColorRGBA[34];

   static
   {
      float step = 1.0f / (COLORS.length / 2) ;
      
      for (int i = 0; i < COLORS.length / 2; i++)
      {
         COLORS[i] = new ColorRGBA(1.0f, i * step, 0.0f);
      }
      for (int i = 0; i < COLORS.length / 2; i++)
      {
         COLORS[i + COLORS.length / 2] = new ColorRGBA(1.0f - i * step, 1.0f, 0.0f);
      }
   }
   
   public ColorRGBA getSeriesColor(int dataset, int series)
   {      
      if (dataset == 0 && series < 34)
      {
         return COLORS[series];
      }
      return null;
   }
}
