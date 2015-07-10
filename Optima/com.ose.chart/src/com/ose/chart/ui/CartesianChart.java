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
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

/**
 * Abstract base class for all charts with cartesian coordinates.
 */
public abstract class CartesianChart extends Chart
{   
   public static final boolean POSITIVE_DIRECTION = true;
   public static final boolean NEGATIVE_DIRECTION = false;
   
   // --- Start of configurable properties ------------------------------------

   private MutableRange itemWindow = new MutableRange();
   
   private int itemTickStep;

   // Maximum value expected to be charted
   private double maxValue;
   
   // Minimum value expected to be charted
   private double minValue;

   private int valueTickCount;

   // --- End of configurable properties --------------------------------------

   public CartesianChart(String title,
                         ChartContentProvider content, 
                         ChartColorProvider colors,
                         Camera camera)
   {
      super(title, content, colors, camera);

      disableNotifications();
      
      // Configurables
      setMaxValue(10.0f);
      setMinValue(0.0f);
      setValueTickCount(10);
      setItemTickStep(1);
      
      enableNotifications();
   }

   public double getMaxValue()
   {
      return maxValue;
   }

   public void setMaxValue(double value)
   {
      maxValue = value >= 0 ? value : 0;
      notifyChartUpdated();
   }
   
   public double getMinValue()
   {
      return minValue;
   }

   public void setMinValue(double value)
   {
      minValue = value <= 0 ? value : 0;
      notifyChartUpdated();
   }

   public int getValueTickCount()
   {
      return valueTickCount;
   }

   public void setValueTickCount(int ticks)
   {
      valueTickCount = ticks;
      notifyChartUpdated();
   }
   
   public int getItemTickStep()
   {
      return itemTickStep;
   }

   public void setItemTickStep(int step)
   {
      itemTickStep = step;
      notifyChartUpdated();
   }

   public void scrollItems(int steps)
   {
      itemWindow.move(steps);
      constrainItemWindow();
      notifyItemWindowChanged();
      notifyChartUpdated();
   }
   
   @Override
   public Range getItemWindow()
   {
      return itemWindow;
   }
   
   @Override
   public void setItemWindow(int offset, int count)
   {
      if (!(itemWindow.getOffset() == offset
            && itemWindow.getCount() == count))
      {
         itemWindow.setOffset(offset);
         itemWindow.setCount(count);
         constrainItemWindow();
         notifyItemWindowChanged();
         notifyChartUpdated();
      }
   }
   
   @Override
   public void setItemWindow(Range range)
   {
      setItemWindow(range.getOffset(), range.getCount());
   }
   
   public void stepItemSelection(boolean positiveStep)
   {
      Range selection = getItemSelection();
      int newOffset = selection.getOffset() + (positiveStep ? 1 : -1);
      int newCount = selection.getCount();
      if (getContentProvider().getItemRange().contains(newOffset, newCount))
      {
         setItemSelection(newOffset, newCount);
      }
   }
   
   public void growItemSelection()
   {
      Range selection = getItemSelection();
      int newOffset = selection.getOffset();
      int newCount = selection.getCount() + 1;
      if (getContentProvider().getItemRange().contains(newOffset, newCount))
      {
         setItemSelection(newOffset, newCount);
      }
   }
   
   public void shrinkItemSelection()
   {
      Range selection = getItemSelection();
      int newOffset = selection.getOffset();
      int newCount = selection.getCount() - 1;
      if (getContentProvider().getItemRange().contains(newOffset, newCount))
      {
         setItemSelection(newOffset, newCount);
      }
   }
   
   @Override
   public void contentChanged(ChartContentProvider provider)
   {
      constrainItemWindow();
      super.contentChanged(provider);
   }
   
   protected void constrainItemWindow()
   {
      itemWindow.constrainPosition(getContentProvider().getItemRange());
   }
         
   /**
    * Round up the given value to the nearest power of ten times 1, 2, or 5.
    * Note: The given value must be positive.
    *
    * @param value  the value to be rounded up.
    * @return the value rounded up.
    * @throws ArithmeticException  if the given value is negative.
    */
   protected static double roundUp(double originalValue)
   {
      int exponent;
      double value;

      if (originalValue < 0)
      {
         throw new ArithmeticException();
      }

      exponent = (int) Math.floor(Math.log(originalValue) / Math.log(10));
      value = originalValue * Math.pow(10, -exponent);
      if (value > 5.0)
      {
         value = 10.0;
      }
      else if (value > 2.0)
      {
         value = 5.0;
      }
      else if (value > 1.0)
      {
         value = 2.0;
      }
      value *= Math.pow(10, exponent);
      return value;
   }

   protected static double getStepSize(double max, int maxSteps)
   {
      return ((max > maxSteps) ? roundUp(max / maxSteps) : 1);
   }
}
