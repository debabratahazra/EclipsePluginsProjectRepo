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

public class StackUsagePerProcessChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries unusedSeries;

   public StackUsagePerProcessChart()
   {
      super("Stack Usage / Process",
            "The peak usage and the amount of never used bytes per process " +
            "stack. The chart is sorted according to relative peak usage.",
            new ChartDataComparator());
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label usedSeriesLabel;
      Label unusedSeriesLabel;
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

      // Create empty y value unused series.
      unusedSeries = (BarSeries) BarSeriesImpl.create();
      unusedSeries.setSeriesIdentifier("Unused");
      unusedSeries.setStacked(true);
      unusedSeries.setRiserOutline(ColorDefinitionImpl.BLACK());
      unusedSeries.setLabelPosition(Position.INSIDE_LITERAL);
      unusedSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      unusedSeriesLabel = LabelImpl.create();
      unusedSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      unusedSeriesLabel.getCaption().setFont(fd);
      unusedSeries.setLabel(unusedSeriesLabel);

      // Add all empty y value series.
      sdY = SeriesDefinitionImpl.create();
      sdY.getSeriesPalette().getEntries().clear();
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(175, 175, 65));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(214, 213, 124));
      nfs = NumberFormatSpecifierImpl.create();
      nfs.setFractionDigits(0);
      sdY.setFormatSpecifier(nfs);
      sdY.getSeries().add(usedSeries);
      sdY.getSeries().add(unusedSeries);
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
         if (yMax < stacks[i].getStackInfo().getSize())
         {
            yMax = stacks[i].getStackInfo().getSize();
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
      double[] unusedSizes;

      length = Math.min(input.length, getWindow());
      if (length > 0)
      {
         xLabels = new String[length];
         usedSizes = new double[length];
         unusedSizes = new double[length];
      }
      else
      {
         xLabels = new String[] {"[none]"};
         usedSizes = new double[] {0};
         unusedSizes = new double[] {0};
      }

      for (int i = 0; i < length; i++)
      {
         ExtendedStackInfo s = (ExtendedStackInfo) input[i];
         xLabels[i] = truncateString(s.getOwnerName());
         usedSizes[i] = (double) s.getStackInfo().getUsed();
         unusedSizes[i] =
            (double) (s.getStackInfo().getSize() - s.getStackInfo().getUsed());
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      usedSeries.setDataSet(NumberDataSetImpl.create(usedSizes));
      unusedSeries.setDataSet(NumberDataSetImpl.create(unusedSizes));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      usedSeries.getLabel().setVisible(true);
      unusedSeries.getLabel().setVisible(true);
   }

   static class ChartDataComparator extends AbstractChartDataComparator
   {
      public int compare(Object o1, Object o2)
      {
         ExtendedStackInfo s1 = (ExtendedStackInfo) o1;
         ExtendedStackInfo s2 = (ExtendedStackInfo) o2;

         if (yAxis)
         {
            double s1Used = (double) s1.getStackInfo().getUsed();
            double s1Size = (double) s1.getStackInfo().getSize();
            double s2Used = (double) s2.getStackInfo().getUsed();
            double s2Size = (double) s2.getStackInfo().getSize();
            double s1Peak = ((s1Size != 0) ? (s1Used / s1Size) : 0);
            double s2Peak = ((s2Size != 0) ? (s2Used / s2Size) : 0);
            return compareDoubles(s1Peak, s2Peak);
         }
         else
         {
            return compareStrings(s1.getOwnerName(), s2.getOwnerName());
         }
      }
   }
}
