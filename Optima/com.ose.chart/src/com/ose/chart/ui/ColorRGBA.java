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

/**
 * Represents a color in RGBA color space on a form that is suitable for
 * 3D rendering pipelines.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/RGBA_color_space">RGBA color space</a>
 */
public class ColorRGBA
{
   public static final ColorRGBA WHITE = new ColorRGBA(1.0f, 1.0f, 1.0f);
   public static final ColorRGBA BLACK = new ColorRGBA(0.0f, 0.0f, 0.0f);
   public static final ColorRGBA RED = new ColorRGBA(1.0f, 0.0f, 0.0f);
   public static final ColorRGBA GREEN = new ColorRGBA(0.0f, 1.0f, 0.0f);
   public static final ColorRGBA BLUE = new ColorRGBA(0.0f, 0.0f, 1.0f);
   public static final ColorRGBA YELLOW = new ColorRGBA(1.0f, 1.0f, 0.0f);
   public static final ColorRGBA PINK = new ColorRGBA(1.0f, 0.0f, 1.0f);
   public static final ColorRGBA CYAN = new ColorRGBA(0.0f, 1.0f, 1.0f);
   public static final ColorRGBA LIGHT_GREY = new ColorRGBA(0.9f, 0.9f, 0.9f);
   
   private static final float DESATURATION_COEFF_RED = 0.3f;
   private static final float DESATURATION_COEFF_GREEN = 0.59f;
   private static final float DESATURATION_COEFF_BLUE = 0.11f;

   private float red;
   private float green;
   private float blue;
   private float alpha;

   public ColorRGBA(float red, float green, float blue, float alpha)
   {
      set(red, green, blue, alpha);
   }

   public ColorRGBA(float red, float green, float blue)
   {
      set(red, green, blue, 1.0f);
   }

   public ColorRGBA()
   {
      // Default to opaque white
      set(1.0f, 1.0f, 1.0f, 1.0f);
   }

   public ColorRGBA(ColorRGBA color)
   {
      set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }
   
   public ColorRGBA(ColorHSV color)
   {
      set(color);
   }

   public float getRed()
   {
      return red;
   }
   
   public void setRed(float red)
   {
      this.red = red;
   }
   
   public float getGreen()
   {
      return green;
   }

   public void setGreen(float green)
   {
      this.green = green;
   }
   
   public float getBlue()
   {
      return blue;
   }

   public void setBlue(float blue)
   {
      this.blue = blue;
   }

   public float getAlpha()
   {
      return alpha;
   }

   public void setAlpha(float alpha)
   {
      this.alpha = alpha;
   }
   
   public ColorRGBA set(float red, float green, float blue)
   {
      set(red, green, blue, 1.0f);
      return this;
   }

   public ColorRGBA set(float red, float green, float blue, float alpha)
   {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      return this;
   }

   public ColorRGBA set(ColorRGBA color)
   {
      set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
      return this;
   }

   public ColorRGBA set(ColorHSV hsv)
   {
      float h = hsv.getHue() / 360.0f;
      float s = hsv.getSaturation() / 100.0f;
      float v = hsv.getValue() / 100.0f;
      
      if (s == 0.0f)
      {
         set(v, v, v);
      }
      else
      {
         float h6 = h * 6.0f;
         float i = (float)Math.floor(h6);
         float v1 = v * (1.0f - s);
         float v2 = v * (1.0f - s * (h6 - i));
         float v3 = v * (1.0f - s * (1.0f - (h6 - i)));
         
         switch ((int)i)
         {
            case 0:
               set(v, v3, v1);
               break;
            case 1:
               set(v2, v, v1);
               break;
            case 2:
               set(v1, v, v3);
               break;
            case 3:
               set(v1, v2, v);
               break;
            case 4:
               set(v3, v1, v);
               break;
            default:
               set(v, v1, v2);
               break;
         };
      }
      
      return this;
   }
   
   public ColorRGBA scale(float factor)
   {
      red *= factor;
      green *= factor;
      blue *= factor;
      return this;
   }

   public ColorRGBA scale(float factorRed, float factorGreen, float factorBlue)
   {
      red *= factorRed;
      green *= factorGreen;
      blue *= factorBlue;
      return this;
   }

   public ColorRGBA scale(float factorRed, float factorGreen, float factorBlue, float factorAlpha)
   {
      red *= factorRed;
      green *= factorGreen;
      blue *= factorBlue;
      alpha *= factorAlpha;
      return this;
   }

   public ColorRGBA add(float term)
   {
      red += term;
      green += term;
      blue += term;
      return this;
   }

   public ColorRGBA add(float termRed, float termGreen, float termBlue)
   {
      red += termRed;
      green += termGreen;
      blue += termBlue;
      return this;
   }

   public ColorRGBA add(float termRed, float termGreen, float termBlue, float termAlpha)
   {
      red += termRed;
      green += termGreen;
      blue += termBlue;
      alpha += termAlpha;
      return this;
   }
   
   public ColorRGBA desaturate(float k)
   {
      float intensity = DESATURATION_COEFF_RED * red
                        + DESATURATION_COEFF_GREEN * green
                        + DESATURATION_COEFF_BLUE * blue;
      red = intensity * k + red * (1.0f - k);
      green = intensity * k + green * (1.0f - k);
      blue = intensity * k + blue * (1.0f - k);
      return this;
   }

   public ColorRGBA saturate()
   {
      red = red > 1.0f ? 1.0f : red;
      green = green > 1.0f ? 1.0f : green;
      blue = blue > 1.0f ? 1.0f : blue;
      alpha = alpha > 1.0f ? 1.0f : alpha;
      return this;
   }
   
   public ColorRGBA clamp()
   {
      saturate();
      red = red < 0.0f ? 0.0f : red;
      green = green < 0.0f ? 0.0f : green;
      blue = blue < 0.0f ? 0.0f : blue;
      alpha = alpha < 0.0f ? 0.0f : alpha;
      return this;
   }
}
