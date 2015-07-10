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

package com.ose.prof.ui.editors.profiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ColorHSV;
import com.ose.chart.ui.ColorRGBA;

class MutableColorProvider implements ChartColorProvider
{
   private final List colors = new ArrayList();

   private final Random random = new Random(1234);

   MutableColorProvider()
   {
      setupInitialColors();
   }

   public void dispose()
   {
      for (Iterator i = colors.iterator(); i.hasNext();)
      {
         Color color = (Color) i.next();
         color.dispose();
      }
      colors.clear();
   }

   public ColorRGBA getSeriesColor(int layer, int series)
   {
      return toColorRGBA(getSeriesLegendColor(layer, series));
   }

   public void setSeriesColor(int layer, int series, RGB rgb)
   {
      Color oldColor = getSeriesLegendColor(layer, series);
      oldColor.dispose();
      colors.set(series, new Color(Display.getCurrent(), rgb));
   }

   public Color getSeriesLegendColor(int layer, int series)
   {
      if (series >= colors.size())
      {
         // Color does not exist, create it and colors in between
         int colorCount = series - (colors.size() - 1);

         for (int i = 0; i < colorCount; i++)
         {
            colors.add(nextColor());
         }
      }

      return (Color) colors.get(series);
   }

   private Color nextColor()
   {
      float h = random.nextFloat() * 360.0f;
      float s = 40.0f + (float) Math.sin(random.nextFloat() * Math.PI / 2.0f) * 60.0f;
      float v = 40.0f + (float) Math.sin(random.nextFloat() * Math.PI / 2.0f) * 60.0f;

      return toSWTColor(new ColorHSV(h, s, v));
   }

   private Color toSWTColor(ColorHSV color)
   {
      return toSWTColor(new ColorRGBA(color));
   }

   private Color toSWTColor(ColorRGBA color)
   {
      return new Color(Display.getCurrent(),
                       (int)(color.getRed() * 255.0f),
                       (int)(color.getGreen() * 255.0f),
                       (int)(color.getBlue() * 255.0f));
   }

   private ColorRGBA toColorRGBA(Color color)
   {
      RGB rgb = color.getRGB();
      return new ColorRGBA(((float) rgb.red) / 255.0f,
                           ((float) rgb.green) / 255.0f,
                           ((float) rgb.blue) / 255.0f);
   }

   private void setupInitialColors()
   {
      Display display = Display.getCurrent();

      colors.add(new Color(display, 91, 114, 153));
      colors.add(new Color(display, 144, 170, 210));
      colors.add(new Color(display, 188, 209, 238));
      colors.add(new Color(display, 188, 230, 238));
      colors.add(new Color(display, 127, 211, 194));
      colors.add(new Color(display, 80, 144, 131));
      colors.add(new Color(display, 60, 124, 102));
      colors.add(new Color(display, 141, 193, 159));
      colors.add(new Color(display, 180, 208, 178));
      colors.add(new Color(display, 190, 206, 148));
      colors.add(new Color(display, 152, 169, 97));
      colors.add(new Color(display, 99, 108, 68));
      colors.add(new Color(display, 135, 123, 89));
      colors.add(new Color(display, 182, 160, 100));
      colors.add(new Color(display, 222, 206, 168));
      colors.add(new Color(display, 214, 168, 130));
      colors.add(new Color(display, 190, 144, 130));
      colors.add(new Color(display, 157, 83, 71));
      colors.add(new Color(display, 100, 47, 40));
      colors.add(new Color(display, 66, 66, 66));
      colors.add(new Color(display, 106, 106, 106));
      colors.add(new Color(display, 160, 160, 160));
      colors.add(new Color(display, 200, 200, 200));
      colors.add(new Color(display, 255, 128, 0));
      colors.add(new Color(display, 255, 192, 0));
      colors.add(new Color(display, 255, 255, 0));
      colors.add(new Color(display, 192, 255, 0));
      colors.add(new Color(display, 128, 255, 0));
      colors.add(new Color(display, 0, 255, 128));
      colors.add(new Color(display, 0, 255, 255));
      colors.add(new Color(display, 0, 192, 255));
      colors.add(new Color(display, 0, 128, 255));
      colors.add(new Color(display, 0, 64, 255));
      colors.add(new Color(display, 64, 0, 205));
      colors.add(new Color(display, 128, 0, 255));
      colors.add(new Color(display, 192, 0, 255));
      colors.add(new Color(display, 255, 0, 255));
      colors.add(new Color(display, 255, 128, 255));
      colors.add(new Color(display, 255, 104, 122));
      colors.add(new Color(display, 255, 32, 61));
   }
}
