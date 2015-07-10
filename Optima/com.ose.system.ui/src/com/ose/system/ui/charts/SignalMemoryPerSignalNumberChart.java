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
import com.ose.system.SignalInfo;
import com.ose.system.ui.util.ExtendedSignalInfo;
import com.ose.system.ui.util.StringUtils;

public class SignalMemoryPerSignalNumberChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries slackSeries;
   private BarSeries freeSeries;

   public SignalMemoryPerSignalNumberChart()
   {
      super("Bytes / Signal Number",
            "The amount of allocated, slack, and freed bytes per signal number.",
            new ChartDataComparator());
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label usedSeriesLabel;
      Label slackSeriesLabel;
      Label freeSeriesLabel;
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
      slackSeries.setLabelPosition(Position.INSIDE_LITERAL);
      slackSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      slackSeriesLabel = LabelImpl.create();
      slackSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      slackSeriesLabel.getCaption().setFont(fd);
      slackSeries.setLabel(slackSeriesLabel);

      // Create empty y value free series.
      freeSeries = (BarSeries) BarSeriesImpl.create();
      freeSeries.setSeriesIdentifier("Free");
      freeSeries.setStacked(true);
      freeSeries.setRiserOutline(ColorDefinitionImpl.BLACK());
      freeSeries.setLabelPosition(Position.OUTSIDE_LITERAL);
      freeSeries.setDataSet(NumberDataSetImpl.create(new double[] {0}));
      freeSeriesLabel = LabelImpl.create();
      freeSeriesLabel.setVisible(false);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      freeSeriesLabel.getCaption().setFont(fd);
      freeSeries.setLabel(freeSeriesLabel);

      // Add all empty y value series.
      sdY = SeriesDefinitionImpl.create();
      sdY.getSeriesPalette().getEntries().clear();
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(132, 142, 92));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(204, 203, 130));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(195, 185, 160));
      nfs = NumberFormatSpecifierImpl.create();
      nfs.setFractionDigits(0);
      sdY.setFormatSpecifier(nfs);
      sdY.getSeries().add(usedSeries);
      sdY.getSeries().add(slackSeries);
      sdY.getSeries().add(freeSeries);
      yAxis.getSeriesDefinitions().add(sdY);

      return barChart;
   }

   public void update(Object input)
   {
      ExtendedSignalInfo[] signals;
      Map map;
      int yMax = 0;

      if (!(input instanceof ExtendedSignalInfo[]))
      {
         throw new IllegalArgumentException();
      }

      signals = (ExtendedSignalInfo[]) input;
      map = new HashMap(signals.length);

      for (int i = 0; i < signals.length; i++)
      {
         SignalInfo signal;
         int sigNo;
         int bufferSize;
         int signalSize;
         boolean freed;
         Integer key;
         ChartData cd;

         signal = signals[i].getSignalInfo();
         sigNo = signal.getSigNo();
         bufferSize = signal.getBufferSize();
         signalSize = signal.getSize();
         freed = (signal.getStatus() == SignalInfo.STATUS_FREE);
         key = new Integer(sigNo);

         cd = (ChartData) map.get(key);
         if (cd == null)
         {
            cd = new ChartData(sigNo);
            if (freed)
            {
               cd.setFreeSize(bufferSize);
            }
            else
            {
               cd.setBufferSize(bufferSize);
               cd.setSignalSize(signalSize);
            }
            map.put(key, cd);
         }
         else
         {
            if (freed)
            {
               cd.setFreeSize(cd.getFreeSize() + bufferSize);
            }
            else
            {
               cd.setBufferSize(cd.getBufferSize() + bufferSize);
               cd.setSignalSize(cd.getSignalSize() + signalSize);
            }
         }

         if (yMax < (cd.getBufferSize() + cd.getFreeSize()))
         {
            yMax = cd.getBufferSize() + cd.getFreeSize();
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
      double[] freeSizes;

      length = Math.min(input.length, getWindow());
      if (length > 0)
      {
         xLabels = new String[length];
         usedSizes = new double[length];
         slackSizes = new double[length];
         freeSizes = new double[length];
      }
      else
      {
         xLabels = new String[] {"[none]"};
         usedSizes = new double[] {0};
         slackSizes = new double[] {0};
         freeSizes = new double[] {0};
      }

      for (int i = 0; i < length; i++)
      {
         ChartData cd = (ChartData) input[i];
         xLabels[i] = truncateString(StringUtils.toU32String(cd.getSigNo()));
         usedSizes[i] = (double) cd.getSignalSize();
         slackSizes[i] = (double) cd.getSlackSize();
         // In order to get the chart to display zeros at the top of the
         // bars they actually need to be more than zeros. However, they
         // are displayed as zeros.
         freeSizes[i] = ((cd.getFreeSize() == 0) ?
                         0.001 : (double) cd.getFreeSize());
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      usedSeries.setDataSet(NumberDataSetImpl.create(usedSizes));
      slackSeries.setDataSet(NumberDataSetImpl.create(slackSizes));
      freeSeries.setDataSet(NumberDataSetImpl.create(freeSizes));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      usedSeries.getLabel().setVisible(true);
      slackSeries.getLabel().setVisible(true);
      freeSeries.getLabel().setVisible(true);
   }

   static class ChartData
   {
      private final int sigNo;
      private int bufferSize;
      private int signalSize;
      private int freeSize;

      ChartData(int sigNo)
      {
         this.sigNo = sigNo;
      }

      public int getSigNo()
      {
         return sigNo;
      }

      public int getBufferSize()
      {
         return bufferSize;
      }

      public void setBufferSize(int bufferSize)
      {
         this.bufferSize = bufferSize;
      }

      public int getSignalSize()
      {
         return signalSize;
      }

      public void setSignalSize(int signalSize)
      {
         this.signalSize = signalSize;
      }

      public int getSlackSize()
      {
         return (bufferSize - signalSize);
      }

      public int getFreeSize()
      {
         return freeSize;
      }

      public void setFreeSize(int freeSize)
      {
         this.freeSize = freeSize;
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
            return compareUnsignedIntsTriple(cd1.getSignalSize(),
                                             cd1.getSlackSize(),
                                             cd1.getFreeSize(),
                                             cd2.getSignalSize(),
                                             cd2.getSlackSize(),
                                             cd2.getFreeSize());
         }
         else
         {
            return compareUnsignedInts(cd1.getSigNo(), cd2.getSigNo());
         }
      }
   }
}
