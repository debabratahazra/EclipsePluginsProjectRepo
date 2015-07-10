/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.charts;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.NumberFormatSpecifier;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.FontDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.NumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.impl.LabelImpl;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import com.ose.system.StackInfo;
import com.ose.system.ui.util.ExtendedStackInfo;

public class StacksPerBufferSizeChart extends AbstractBarChart
{
   private BarSeries stackSeries;

   public StacksPerBufferSizeChart()
   {
      super("Stacks / Actual Stack Buffer Size",
            "The number of allocated stacks per actual stack buffer size.",
            new ChartDataComparator());
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label stackSeriesLabel;
      SeriesDefinition sdY;
      FontDefinition fd;
      NumberFormatSpecifier nfs;

      // Create chart.
      barChart = (ChartWithAxes) super.createChart();

      // Create empty y value series.
      stackSeries = (BarSeries) BarSeriesImpl.create();
      stackSeries.setSeriesIdentifier("Used");
      stackSeries.setStacked(true);
      stackSeries.setRiserOutline(ColorDefinitionImpl.BLACK());
      stackSeries.setLabelPosition(Position.INSIDE_LITERAL);
      stackSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      stackSeriesLabel = LabelImpl.create();
      stackSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      stackSeriesLabel.getCaption().setFont(fd);
      stackSeries.setLabel(stackSeriesLabel);

      // Add empty y value series.
      sdY = SeriesDefinitionImpl.create();
      sdY.getSeriesPalette().getEntries().clear();
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(112, 131, 158));
      nfs = NumberFormatSpecifierImpl.create();
      nfs.setFractionDigits(0);
      sdY.setFormatSpecifier(nfs);
      sdY.getSeries().add(stackSeries);
      yAxis.getSeriesDefinitions().add(sdY);

      return barChart;
   }

   public void update(Object input)
   {
      ExtendedStackInfo[] stacks;
      Map map;
      int yMax = 0;

      if (!(input instanceof ExtendedStackInfo[]))
      {
         throw new IllegalArgumentException();
      }

      stacks = (ExtendedStackInfo[]) input;
      map = new HashMap();

      for (int i = 0; i < stacks.length; i++)
      {
         StackInfo stack;
         int bufferSize;
         Integer key;
         ChartData cd;

         stack = stacks[i].getStackInfo();
         bufferSize = stack.getBufferSize();
         key = new Integer(bufferSize);

         cd = (ChartData) map.get(key);
         if (cd == null)
         {
            cd = new ChartData(bufferSize);
            cd.setNumStacks(1);
            map.put(key, cd);
         }
         else
         {
            cd.setNumStacks(cd.getNumStacks() + 1);
         }

         if (yMax < cd.getNumStacks())
         {
            yMax = cd.getNumStacks();
         }
      }

      yAxis.getScale().setMax(NumberDataElementImpl.create(getRoundedMax(yMax)));
      yAxis.getScale().setStep(getStepSize(yMax));
      chartDataInput = (ChartData[]) map.values().toArray(new ChartData[0]);
      setInput(chartDataInput);
      fireChartModelChanged();
   }

   protected void setInput(Object[] input)
   {
      int length;
      String[] xLabels;
      double[] stackValues;

      length = Math.min(input.length, getWindow());
      if (length > 0)
      {
         xLabels = new String[length];
         stackValues = new double[length];
      }
      else
      {
         xLabels = new String[] {"[none]"};
         stackValues = new double[] {0};
      }

      for (int i = 0; i < length; i++)
      {
         ChartData cd = (ChartData) input[i];
         xLabels[i] = truncateString(Integer.toString(cd.getBufferSize()));
         stackValues[i] = (double) cd.getNumStacks();
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      stackSeries.setDataSet(NumberDataSetImpl.create(stackValues));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      stackSeries.getLabel().setVisible(true);
   }

   static class ChartData
   {
      private final int bufferSize;
      private int numStacks;

      ChartData(int bufferSize)
      {
         this.bufferSize = bufferSize;
      }

      public int getBufferSize()
      {
         return bufferSize;
      }

      public int getNumStacks()
      {
         return numStacks;
      }

      public void setNumStacks(int numStacks)
      {
         this.numStacks = numStacks;
      }
   }

   static class ChartDataComparator extends AbstractChartDataComparator
   {
      public int compare(Object o1, Object o2)
      {
         ChartData cd1 = (ChartData) o1;
         ChartData cd2 = (ChartData) o2;

         if (yAxis)
         {
            return compareUnsignedInts(cd1.getNumStacks(), cd2.getNumStacks());
         }
         else
         {
            return compareUnsignedInts(cd1.getBufferSize(), cd2.getBufferSize());
         }
      }
   }
}
