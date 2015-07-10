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
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

/**
 * Content provider that can generate content for all the chart types,
 * values returned are random.
 *
 * Should be enhanced to provide more control over the generated values.
 *
 */
public class RandomContentProvider extends ChartContentProvider
{  
   // chart parameters
   private Range layerRange;
   private Range seriesRange;
   private Range itemRange;
   private Range valueRange;

   // max and min values to be displayed
   private double maxValue;
   private double minValue;
   
   // step between item labels, if this is not set, we generate a random value from 0 to 10
   private boolean randomTickStep = true;
   private int tickStep;
   
   // number of items to be listed, should always be lower then the number of items
   private int itemCount = 10;
   
   // array containing all the values
   double[] values;
   
   // methods to initialize the data, default values are provided
   public RandomContentProvider()
   {
      layerRange = new MutableRange(0, 0);
      seriesRange = new MutableRange(0, 0);
      itemRange = new MutableRange(0, 50);
      valueRange = new MutableRange(0, 70);
   }
   
   /**
    * Call this method to initialize test content.
    */
   public void initTestContent()
   {
      int nr_layers = layerRange.getCount() > 0 ? layerRange.getCount() : 1;
      int nr_series = seriesRange.getCount() > 0 ? seriesRange.getCount() : 1;
      int nr_items = itemRange.getCount() > 0? itemRange.getCount() : 1;
      
      int arraySize = nr_layers * nr_series * nr_items;
            
      values = new double[arraySize];
      
      Random generator = new Random(System.currentTimeMillis());
      
      maxValue = valueRange.getOffset();
      minValue = valueRange.getOffset() + valueRange.getCount();
      
      for (int i = 0; i < values.length; i++)
      {
         values[i] = valueRange.getOffset() + generator.nextInt(valueRange.getCount() + 1);
         if (values[i] > maxValue)
            maxValue = (double)values[i];
         if (values[i] < minValue)
            minValue = (double)values[i];         
      }
                 
      if (randomTickStep)
         tickStep = generator.nextInt(8);
      if (tickStep == 0)
         tickStep = 1;
   }

   /*
    * (non-Javadoc)
    * - for the series, if the offset has negative value, it is defaulted to 0
    *   but the count is not adjusted
    * - for the items, if the offset is negative, the range is truncated
    * @see com.ose.chart.model.ChartContentProvider#getValue(int, int, int)
    */
   public double getValue(int layer, int series, int item)
   {
      int nr_series = seriesRange.getCount() > 0 ? seriesRange.getCount() : 1;
      int nr_items = itemRange.getCount() > 0? itemRange.getCount() : 1;
      int adjustedSeriesOffset = seriesRange.getOffset() > 0 ? seriesRange.getOffset() : 0;
      
      return values[(layer - layerRange.getOffset())*nr_series*nr_items + (series - adjustedSeriesOffset)*nr_items +(item - itemRange.getOffset())];
   }
   
   /**
    * Sets the min max values for the Stack Charts.
    */
   public void setStackedMinMaxValues()
   {
      int nr_items = itemRange.getCount() > 0 ? itemRange.getCount() : 1;
      int nr_series = seriesRange.getCount() > 0 ? seriesRange.getCount() : 1;
      int nr_layers = layerRange.getCount() > 0 ? layerRange.getCount() : 1;
      double[] addedStacks = new double[nr_items * nr_series];
      
      for (int i = 0; i < nr_items * nr_series; i++)
         addedStacks[i] = 0;
      
      for (int j = 0; j < nr_layers; j++)
      {
         for (int i = 0; i < nr_items * nr_series; i++)
            addedStacks[i]+= values[j*nr_items*nr_series + i]; 
      }
      
      for (int i = 0; i < nr_items * nr_series ; i++)
      {
         if (addedStacks[i] < minValue)
            minValue = addedStacks[i];
         if (addedStacks[i] > maxValue)
            maxValue = addedStacks[i];
      }
   }
   
   /**
    * Sets the min max values for the Stack Charts.
    */
   public void setStacked2DMinMaxValues()
   {
      int nr_items = itemRange.getCount() > 0 ? itemRange.getCount() : 1;
      int nr_series = seriesRange.getCount() > 0 ? seriesRange.getCount() : 1;
      int nr_layers = layerRange.getCount() > 0 ? layerRange.getCount() : 1;
      double[] addedStacks = new double[nr_items * nr_layers];
      
      for (int i = 0; i < nr_items * nr_layers; i++)
         addedStacks[i] = 0;
      
      for (int j = 0; j < nr_series; j++)
      {
         for (int i = 0; i < nr_items * nr_layers; i++)
            addedStacks[i]+= values[j*nr_items*nr_layers + i]; 
      }
      
      for (int i = 0; i < nr_items * nr_layers ; i++)
      {
         if (addedStacks[i] < minValue)
            minValue = addedStacks[i];
         if (addedStacks[i] > maxValue)
            maxValue = addedStacks[i];
      }
   }

   /**
    * For every item in every series, sort the values for each layer in 
    * ascending order.
    */
   public void sortAscendingLayers()
   {
      int nrLayers = layerRange.getCount() > 0 ? layerRange.getCount() : 1;
      int nrSeries = seriesRange.getCount() > 0 ? seriesRange.getCount() : 1;
      int nrItems = itemRange.getCount() > 0? itemRange.getCount() : 1;
      int nrLayerValues = nrSeries*nrItems;
      
      for (int i = 0; i < nrLayerValues; i++)
      {
         // bubble sort the layers 
         for (int j = 0; j < nrLayers; j++)
         {
            for (int k = j + 1; k < nrLayers; k++)
            {
               double tempValue;
               if(values[i + j * nrLayerValues] > values[i + k * nrLayerValues])
               {
                  tempValue = values[i + j * nrLayerValues];
                  values[i + j * nrLayerValues] = values[i + k * nrLayerValues];
                  values[i + k * nrLayerValues] = tempValue;
               }
            }
         }
      }
   }

   public String getValueLabel(int layer, int series, int item)
   {
      return "ValueLabel > " + layer + " > " + series + " > " + item;
   }

   public double getMaxValue()
   {
      return maxValue;
   }

   public double getMinValue()
   {
      return minValue;
   }

   public int getTickStep()
   {
      return tickStep;
   }

   public void setTickStep(int tickStep)
   {
      this.tickStep = tickStep;
      this.randomTickStep = false;
   }

   public int getItemCount()
   {
      return itemCount;
   }

   public void setItemCount(int itemCount)
   {
      this.itemCount = itemCount;
   }
   
   public String getItemLabel(int layer, int series, int item)
   {
      return "Item-" + layer + "-" + series + "-" + item;
   }

   public Range getItemRange()
   {
      return itemRange;
   }

   public void setItemRange(Range itemRange)
   {
      this.itemRange = itemRange;
   }

   public String getLayerLabel(int layer)
   {
      return "Layer - " + layer;
   }

   public Range getLayerRange()
   {
      return layerRange;
   }

   public void setLayerRange(Range layerRange)
   {
      this.layerRange = layerRange;
   }

   public String getSeriesLabel(int layer, int series)
   {
      return "Series - " + layer + " - " + series;
   }

   public Range getSeriesRange()
   {
      return seriesRange;
   }

   public void setSeriesRange(Range seriesRange)
   {
      this.seriesRange = seriesRange;
   }

   public Range getValueRange()
   {
      return valueRange;
   }

   public void setValueRange(Range valueRange)
   {
      this.valueRange = valueRange;
   }
}
