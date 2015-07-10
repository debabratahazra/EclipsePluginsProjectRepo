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

public class SignalsPerBufferSizeChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries freeSeries;

   public SignalsPerBufferSizeChart()
   {
      super("Signals / Actual Signal Buffer Size",
            "The number of allocated and freed signals " +
            "per actual signal buffer size.",
            new ChartDataComparator());
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label usedSeriesLabel;
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
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(112, 131, 158));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(195, 185, 160));
      nfs = NumberFormatSpecifierImpl.create();
      nfs.setFractionDigits(0);
      sdY.setFormatSpecifier(nfs);
      sdY.getSeries().add(usedSeries);
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
      map = new HashMap();

      for (int i = 0; i < signals.length; i++)
      {
         SignalInfo signal;
         int bufferSize;
         boolean freed;
         Integer key;
         ChartData cd;

         signal = signals[i].getSignalInfo();
         bufferSize = signal.getBufferSize();
         freed = (signal.getStatus() == SignalInfo.STATUS_FREE);
         key = new Integer(bufferSize);

         cd = (ChartData) map.get(key);
         if (cd == null)
         {
            cd = new ChartData(bufferSize);
            if (freed)
            {
               cd.setNumFree(1);
            }
            else
            {
               cd.setNumSignals(1);
            }
            map.put(key, cd);
         }
         else
         {
            if (freed)
            {
               cd.setNumFree(cd.getNumFree() + 1);
            }
            else
            {
               cd.setNumSignals(cd.getNumSignals() + 1);
            }
         }

         if (yMax < (cd.getNumSignals() + cd.getNumFree()))
         {
            yMax = cd.getNumSignals() + cd.getNumFree();
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
      double[] usedValues;
      double[] freeValues;

      length = Math.min(input.length, getWindow());
      if (length > 0)
      {
         xLabels = new String[length];
         usedValues = new double[length];
         freeValues = new double[length];
      }
      else
      {
         xLabels = new String[] {"[none]"};
         usedValues = new double[] {0};
         freeValues = new double[] {0};
      }

      for (int i = 0; i < length; i++)
      {
         ChartData cd = (ChartData) input[i];
         xLabels[i] = truncateString(Integer.toString(cd.getBufferSize()));
         usedValues[i] = (double) cd.getNumSignals();
         // In order to get the chart to display zeros at the top of the
         // bars they actually need to be more than zeros. However, they
         // are displayed as zeros.
         freeValues[i] = ((cd.getNumFree() == 0) ?
                          0.001 : (double) cd.getNumFree());
      }

      xLabelSeries.setDataSet(TextDataSetImpl.create(xLabels));
      usedSeries.setDataSet(NumberDataSetImpl.create(usedValues));
      freeSeries.setDataSet(NumberDataSetImpl.create(freeValues));

      xAxis.getLabel().setVisible(true);
      yAxis.getLabel().setVisible(true);
      usedSeries.getLabel().setVisible(true);
      freeSeries.getLabel().setVisible(true);
   }

   static class ChartData
   {
      private final int bufferSize;
      private int numSignals;
      private int numFree;

      ChartData(int bufferSize)
      {
         this.bufferSize = bufferSize;
      }

      public int getBufferSize()
      {
         return bufferSize;
      }

      public int getNumSignals()
      {
         return numSignals;
      }

      public void setNumSignals(int numSignals)
      {
         this.numSignals = numSignals;
      }

      public int getNumFree()
      {
         return numFree;
      }

      public void setNumFree(int numFree)
      {
         this.numFree = numFree;
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
            return compareUnsignedIntsPair(cd1.getNumSignals(), cd1.getNumFree(),
                                           cd2.getNumSignals(), cd2.getNumFree());
         }
         else
         {
            return compareUnsignedInts(cd1.getBufferSize(), cd2.getBufferSize());
         }
      }
   }
}
