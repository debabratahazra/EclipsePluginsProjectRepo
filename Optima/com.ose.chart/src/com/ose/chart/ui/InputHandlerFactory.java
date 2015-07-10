/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007-2008 by Enea Software AB.
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

final class InputHandlerFactory
{
   private InputHandlerFactory()
   {
      // Prevent instantiation
   }
   
   public static KeyHandler createKeyHandler(ChartCanvas canvas, Chart chart)
   {
      if (CartesianChart2D.class.isInstance(chart))
      {
         return new KeyHandlerCartesian2D(canvas, (CartesianChart2D)chart);
      }
      else if (CartesianChart3D.class.isInstance(chart))
      {
         return new KeyHandlerCartesian3D(canvas, (CartesianChart3D)chart);         
      }
      else
      {
         // Returns a key handler that does nothing
         // TODO: Consider throwing an exception instead
         return new KeyHandler(canvas, chart);
      }
   }
   
   public static MouseHandler createMouseHandler(ChartCanvas canvas, Chart chart)
   {
      if (CartesianChart2D.class.isInstance(chart))
      {
         return new MouseHandlerCartesian2D(canvas, (CartesianChart2D)chart);
      }
      else if (CartesianChart3D.class.isInstance(chart))
      {
         return new MouseHandlerCartesian3D(canvas, (CartesianChart3D)chart);         
      }
      else if (PolarChart2D.class.isInstance(chart))
      {
         return new MouseHandlerPolar2D(canvas, (PolarChart2D)chart);
      }
      else
      {
         // Returns a mouse handler that does nothing
         // TODO: Consider throwing an exception instead
         return new MouseHandler(canvas, chart);
      }
   }
}
