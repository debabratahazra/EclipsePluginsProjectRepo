/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.prof.ui.editors.profiler;

import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorRGBA;

public class CPUBlockProfilerColorProvider implements ChartColorProvider
{
   static final ColorRGBA[] COLORS = new ColorRGBA[80];

   static
   {
      int intervals = 4; // green->yellow->red->yellow->green
      float step = 1.0f / (COLORS.length / intervals);
      int index = 0;

      // green->yellow
      for (int i = 0; i < COLORS.length / intervals; i++)
      {
         COLORS[index++] = new ColorRGBA(i * step, 1.0f, 0.0f);
      }
      // yellow->red
      for (int i = 0; i < COLORS.length / intervals; i++)
      {
         COLORS[index++] = new ColorRGBA(1.0f, 1.0f - i * step, 0.0f);
      }
      // red->yellow
      for (int i = 0; i < COLORS.length / intervals; i++)
      {
         COLORS[index++] = new ColorRGBA(1.0f, i * step, 0.0f);
      }
      // yellow->green
      for (int i = 0; i < COLORS.length / intervals; i++)
      {
         COLORS[index++] = new ColorRGBA(1.0f - i * step, 1.0f, 0.0f);
      }

      for (int i = 0; i < COLORS.length; i++)
      {
         if ((i & 0x1) != 0)
         {
            COLORS[i].scale(0.5f);
         }
      }
   }

   public ColorRGBA getSeriesColor(int dataset, int series)
   {
      if (dataset == 0)
      {
         return COLORS[series % COLORS.length];
      }
      else
      {
         return null;
      }
   }
}
