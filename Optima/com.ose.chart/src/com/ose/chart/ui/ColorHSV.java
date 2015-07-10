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

package com.ose.chart.ui;

/**
 * Represents a color in HSV color space.
 *
 *  @see <a href="http://en.wikipedia.org/wiki/HSL_and_HSV">HSV color space</a>
 */
public class ColorHSV
{
   private float hue;
   private float saturation;
   private float value;

   public ColorHSV(float hue, float saturation, float value)
   {
      this.hue = hue;
      this.saturation = saturation;
      this.value = value;
   }

   public ColorHSV()
   {
      // Default to white
      set(0.0f, 0.0f, 1.0f);
   }

   public ColorHSV(ColorHSV color)
   {
      set(color);
   }

   public ColorHSV(ColorRGBA color)
   {
      set(color);
   }

   public float getHue()
   {
      return hue;
   }

   public void setHue(float hue)
   {
      this.hue = hue % 360.0f;
   }

   public float getSaturation()
   {
      return saturation;
   }

   public void setSaturation(float saturation)
   {
      this.saturation = saturation;
      if (this.saturation < 0.0f)
      {
         this.saturation = 0.0f;
      }
      else if (this.saturation > 100.0f)
      {
         this.saturation = 100.0f;
      }
   }

   public float getValue()
   {
      return value;
   }

   public void setValue(float value)
   {
      this.value = value;
      if (this.value < 0.0f)
      {
         this.value = 0.0f;
      }
      else if (this.value > 100.0f)
      {
         this.value = 100.0f;
      }
   }

   public ColorHSV set(float hue, float saturation, float value)
   {
      setHue(hue);
      setSaturation(saturation);
      setValue(value);
      return this;
   }

   public ColorHSV set(ColorHSV color)
   {
      set(color.getHue(), color.getSaturation(), color.getValue());
      return this;
   }

   public ColorHSV set(ColorRGBA rgb)
   {
      float red = rgb.getRed();
      float green = rgb.getGreen();
      float blue = rgb.getBlue();

      float minValue = Math.min(red, Math.min(green, blue));
      float maxValue = Math.max(red, Math.max(green, blue));
      float delta = maxValue - minValue;

      this.value = maxValue;

      if (delta == 0.0f)
      {
         hue = 0.0f;
         saturation = 0.0f;
      }
      else
      {
         if (maxValue == 0.0f)
         {
            saturation = 0;
         }
         else
         {
            saturation = delta / maxValue;
         }
         float r = (((maxValue - red) / 6.0f) + (delta / 2.0f)) / delta;
         float g = (((maxValue - green) / 6.0f) + (delta / 2.0f)) / delta;
         float b = (((maxValue - blue) / 6.0f) + (delta / 2.0f)) / delta;

         if (red == maxValue)
         {
            hue = b - g;
         }
         else if (green == maxValue)
         {
            hue = (1.0f / 3.0f) + r - b;
         }
         else if (blue == maxValue)
         {
            hue = (2.0f / 3.0f) + g - r;
         }

         if (hue < 0.0f)
         {
            hue += 1.0f;
         }
         if (hue > 1.0f)
         {
            hue -= 1.0f;
         }
      }

      hue *= 360.0f;
      saturation *= 100.0f;
      value *= 100.0f;

      return this;
   }

   public ColorHSV shiftHue(float degrees)
   {
      hue = (hue + degrees) % 360.0f;
      return this;
   }

   public ColorHSV scaleSaturation(float factor)
   {
      setSaturation(getSaturation() * factor);
      return this;
   }

   public ColorHSV addSaturation(float term)
   {
      setSaturation(getSaturation() + term);
      return this;
   }

   public ColorHSV scaleValue(float factor)
   {
      setValue(getValue() * factor);
      return this;
   }

   public ColorHSV addValue(float term)
   {
      setValue(getValue() + term);
      return this;
   }
}
