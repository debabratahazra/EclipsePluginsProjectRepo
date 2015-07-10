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
import com.ose.system.ui.util.ExtendedStackInfo;

public class StackMemoryPerProcessChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries slackSeries;

   public StackMemoryPerProcessChart()
   {
      super("Bytes / Process",
            "The amount of allocated and slack bytes per process stack.",
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
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(233, 189, 83));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(243, 225, 169));
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
      int yMax = 0;

      if (!(input instanceof ExtendedStackInfo[]))
      {
         throw new IllegalArgumentException();
      }

      // Private copy.
      stacks = (ExtendedStackInfo[]) ((ExtendedStackInfo[]) input).clone();

      for (int i = 0; i < stacks.length; i++)
      {
         if (yMax < stacks[i].getStackInfo().getBufferSize())
         {
            yMax = stacks[i].getStackInfo().getBufferSize();
         }
      }

      yAxis.getScale().setMax(NumberDataElementImpl.create(getRoundedMax(yMax)));
      yAxis.getScale().setStep(getStepSize(yMax));
      chartDataInput = stacks;
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
         ExtendedStackInfo s = (ExtendedStackInfo) input[i];
         xLabels[i] = truncateString(s.getOwnerName());
         usedSizes[i] = (double) s.getStackInfo().getSize();
         slackSizes[i] = (double)
            (s.getStackInfo().getBufferSize() - s.getStackInfo().getSize());
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      usedSeries.setDataSet(NumberDataSetImpl.create(usedSizes));
      slackSeries.setDataSet(NumberDataSetImpl.create(slackSizes));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      usedSeries.getLabel().setVisible(true);
      slackSeries.getLabel().setVisible(true);
   }

   static class ChartDataComparator extends AbstractChartDataComparator
   {
      public int compare(Object o1, Object o2)
      {
         ExtendedStackInfo s1 = (ExtendedStackInfo) o1;
         ExtendedStackInfo s2 = (ExtendedStackInfo) o2;

         if (yAxis)
         {
            return compareUnsignedIntsPair(
                  s1.getStackInfo().getSize(),
                  s1.getStackInfo().getBufferSize() - s1.getStackInfo().getSize(),
                  s2.getStackInfo().getSize(),
                  s2.getStackInfo().getBufferSize() - s2.getStackInfo().getSize());
         }
         else
         {
            return compareStrings(s1.getOwnerName(), s2.getOwnerName());
         }
      }
   }
}
