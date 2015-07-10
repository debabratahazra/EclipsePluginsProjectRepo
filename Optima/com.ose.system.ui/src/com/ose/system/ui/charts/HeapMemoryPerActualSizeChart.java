/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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
import com.ose.system.HeapBufferInfo;
import com.ose.system.ui.util.ExtendedHeapBufferInfo;

public class HeapMemoryPerActualSizeChart extends AbstractBarChart
{
   private BarSeries usedSeries;
   private BarSeries slackSeries;

   public HeapMemoryPerActualSizeChart()
   {
      super("Bytes / Actual Size",
            "The amount of allocated and slack " +
            "bytes per actual heap buffer size.",
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
      slackSeries.setLabelPosition(Position.INSIDE_LITERAL);
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
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(142, 216, 206));
      sdY.getSeriesPalette().getEntries().add(ColorDefinitionImpl.create(210, 240, 236));
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
      ExtendedHeapBufferInfo[] buffers;
      Map map;
      int yMax = 0;

      if (!(input instanceof ExtendedHeapBufferInfo[]))
      {
         throw new IllegalArgumentException();
      }

      buffers = (ExtendedHeapBufferInfo[]) input;
      map = new HashMap();

      for (int i = 0; i < buffers.length; i++)
      {
         HeapBufferInfo buffer;
         int actualSize;
         int requestedSize;
         Integer key;
         ChartData cd;

         buffer = buffers[i].getHeapBufferInfo();
         actualSize = buffer.getSize();
         requestedSize = buffer.getRequestedSize();
         key = new Integer(actualSize);

         cd = (ChartData) map.get(key);
         if (cd == null)
         {
            cd = new ChartData(actualSize);
            cd.setActualSize(actualSize);
            cd.setRequestedSize(requestedSize);
            map.put(key, cd);
         }
         else
         {
            cd.setActualSize(cd.getActualSize() + actualSize);
            cd.setRequestedSize(cd.getRequestedSize() + requestedSize);
         }

         if (yMax < cd.getActualSize())
         {
            yMax = cd.getActualSize();
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
         usedSizes[i] = (double) cd.getRequestedSize();
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
      private int actualSize;
      private int requestedSize;

      ChartData(int key)
      {
         this.key = key;
      }

      public int getKey()
      {
         return key;
      }

      public int getActualSize()
      {
         return actualSize;
      }

      public void setActualSize(int actualSize)
      {
         this.actualSize = actualSize;
      }

      public int getRequestedSize()
      {
         return requestedSize;
      }

      public void setRequestedSize(int requestedSize)
      {
         this.requestedSize = requestedSize;
      }

      public int getSlackSize()
      {
         return (actualSize - requestedSize);
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
            return compareUnsignedIntsPair(cd1.getRequestedSize(),
                                           cd1.getSlackSize(),
                                           cd2.getRequestedSize(),
                                           cd2.getSlackSize());
         }
         else
         {
            return compareUnsignedInts(cd1.getKey(), cd2.getKey());
         }
      }
   }
}
