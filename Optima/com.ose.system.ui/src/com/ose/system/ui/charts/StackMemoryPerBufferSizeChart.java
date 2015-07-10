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

public class StackMemoryPerBufferSizeChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries slackSeries;

   public StackMemoryPerBufferSizeChart()
   {
      super("Bytes / Actual Stack Buffer Size",
            "The amount of allocated and slack " +
            "bytes per actual stack buffer size.",
            new ChartDataComparator());
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label usedSeriesLabel;
      Label slackSeriesLabel;
      SeriesDefinition sdY;
      FontDefinition fd;
      NumberFormatSpecifier nfs;

      // Create chart.
      barChart = (ChartWithAxes) super.createChart();

      // Create empty y value used series.
      usedSeries = (BarSeries) BarSeriesImpl.create();
      usedSeries.setSeriesIdentifier("Used");
      usedSeries.setStacked(true);
      usedSeries.setRiserOutline(ColorDefinitionImpl.BLACK());
      usedSeries.setLabelPosition(Position.INSIDE_LITERAL);
      usedSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      usedSeriesLabel = LabelImpl.create();
      usedSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      usedSeriesLabel.getCaption().setFont(fd);
      usedSeries.setLabel(usedSeriesLabel);

      // Create empty y value slack series.
      slackSeries = (BarSeries) BarSeriesImpl.create();
      slackSeries.setSeriesIdentifier("Slack");
      slackSeries.setStacked(true);
      slackSeries.setRiserOutline(ColorDefinitionImpl.BLACK());
      slackSeries.setLabelPosition(Position.OUTSIDE_LITERAL);
      slackSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      slackSeriesLabel = LabelImpl.create();
      slackSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      slackSeriesLabel.getCaption().setFont(fd);
      slackSeries.setLabel(slackSeriesLabel);

      // Add all empty y value series.
      sdY = SeriesDefinitionImpl.create();
      sdY.getSeriesPalette().getEntries().clear();
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(112, 131, 158));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(173, 194, 223));
      nfs = NumberFormatSpecifierImpl.create();
      nfs.setFractionDigits(0);
      sdY.setFormatSpecifier(nfs);
      sdY.getSeries().add(usedSeries);
      sdY.getSeries().add(slackSeries);
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
         int stackSize;
         Integer key;
         ChartData cd;

         stack = stacks[i].getStackInfo();
         bufferSize = stack.getBufferSize();
         stackSize = stack.getSize();
         key = new Integer(bufferSize);

         cd = (ChartData) map.get(key);
         if (cd == null)
         {
            cd = new ChartData(bufferSize);
            cd.setBufferSize(bufferSize);
            cd.setStackSize(stackSize);
            map.put(key, cd);
         }
         else
         {
            cd.setBufferSize(cd.getBufferSize() + bufferSize);
            cd.setStackSize(cd.getStackSize() + stackSize);
         }

         if (yMax < cd.getBufferSize())
         {
            yMax = cd.getBufferSize();
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
      double[] usedSizes;
      double[] slackSizes;

      length = Math.min(input.length, getWindow());
      if (length > 0)
      {
         xLabels = new String[length];
         usedSizes = new double[length];
         slackSizes = new double[length];
      }
      else
      {
         xLabels = new String[] {"[none]"};
         usedSizes = new double[] {0};
         slackSizes = new double[] {0};
      }

      for (int i = 0; i < length; i++)
      {
         ChartData cd = (ChartData) input[i];
         xLabels[i] = truncateString(Integer.toString(cd.getKey()));
         usedSizes[i] = (double) cd.getStackSize();
         slackSizes[i] = (double) cd.getSlackSize();
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      usedSeries.setDataSet(NumberDataSetImpl.create(usedSizes));
      slackSeries.setDataSet(NumberDataSetImpl.create(slackSizes));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      usedSeries.getLabel().setVisible(true);
      slackSeries.getLabel().setVisible(true);
   }

   static class ChartData
   {
      private final int key;
      private int bufferSize;
      private int stackSize;

      ChartData(int key)
      {
         this.key = key;
      }

      public int getKey()
      {
         return key;
      }

      public int getBufferSize()
      {
         return bufferSize;
      }

      public void setBufferSize(int bufferSize)
      {
         this.bufferSize = bufferSize;
      }

      public int getStackSize()
      {
         return stackSize;
      }

      public void setStackSize(int stackSize)
      {
         this.stackSize = stackSize;
      }

      public int getSlackSize()
      {
         return (bufferSize - stackSize);
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
            return compareUnsignedIntsPair(cd1.getStackSize(), cd1.getSlackSize(),
                                           cd2.getStackSize(), cd2.getSlackSize());
         }
         else
         {
            return compareUnsignedInts(cd1.getKey(), cd2.getKey());
         }
      }
   }
}
