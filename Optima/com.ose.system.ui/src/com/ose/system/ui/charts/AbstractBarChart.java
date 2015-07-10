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

import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.HorizontalAlignment;
import org.eclipse.birt.chart.model.attribute.Insets;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.TextAlignment;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.FontDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.InsetsImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.attribute.impl.TextAlignmentImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.ComponentFactory;
import org.eclipse.birt.chart.model.component.Grid;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.Scale;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.ComponentFactoryImpl;
import org.eclipse.birt.chart.model.component.impl.LabelImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;

public abstract class AbstractBarChart extends ChartModel
{
   private static final int MAX_STRING_LENGTH = 20;
   private static final int MAX_NUM_STEPS = 20;

   protected Axis xAxis;
   protected Axis yAxis;
   protected Series xLabelSeries;
   protected AbstractChartDataComparator chartDataComparator;
   protected Object[] chartDataInput;

   public AbstractBarChart(String title,
                           String description,
                           AbstractChartDataComparator chartDataComparator)
   {
      super(title, description);
      if (chartDataComparator == null)
      {
         throw new NullPointerException();
      }
      this.chartDataComparator = chartDataComparator;
   }

   protected Chart createChart()
   {
      ChartWithAxes barChart;
      Label xTickLabel;
      Label yTickLabel;
      SeriesDefinition sdX;
      ComponentFactory factory;
      Insets insets;
      TextAlignment ta;
      FontDefinition fd;
      Grid grid;
      LineAttributes la;
      Scale scale;

      // Create chart.
      barChart = ChartWithAxesImpl.create();
      barChart.setFloorFill(ColorDefinitionImpl.WHITE());
      barChart.setWallFill(ColorDefinitionImpl.WHITE());
      barChart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
      barChart.getPlot().setBackground(ColorDefinitionImpl.WHITE());
      barChart.getPlot().getClientArea().setBackground(ColorDefinitionImpl.WHITE());
      barChart.getTitle().getLabel().getCaption().setValue(getDescription());
      insets = InsetsImpl.create(0.0, 0.0, 14.0, 0.0);
      barChart.getTitle().getLabel().setInsets(insets);
      ta = TextAlignmentImpl.create();
      ta.setHorizontalAlignment(HorizontalAlignment.LEFT_LITERAL);
      fd = FontDefinitionImpl.create("", 8.0f, false, false,
            false, false, false, 0.0, ta);
      barChart.getTitle().getLabel().getCaption().setFont(fd);
      barChart.getLegend().setAnchor(Anchor.NORTH_LITERAL);
      barChart.getLegend().getText().getFont().setSize(LEGEND_FONT_SIZE);

      // Create x axis.
      xAxis = barChart.getPrimaryBaseAxes()[0];
      xAxis.setType(AxisType.TEXT_LITERAL);
      xAxis.getOrigin().setType(IntersectionType.VALUE_LITERAL);
      xAxis.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(LABEL_FONT_SIZE);
      xTickLabel = LabelImpl.create();
      xTickLabel.getCaption().setFont(fd);
      xAxis.setLabel(xTickLabel);

      // Create y axis.
      yAxis = barChart.getPrimaryOrthogonalAxis(xAxis);
      yAxis.setPercent(false);
      factory = new ComponentFactoryImpl();
      grid = factory.createGrid();
      grid.setTickStyle(TickStyle.ACROSS_LITERAL);
      la = LineAttributesImpl.create(ColorDefinitionImpl.create(170, 170, 170),
            LineStyle.DASHED_LITERAL, 1);
      la.setVisible(true);
      grid.setLineAttributes(la);
      la = LineAttributesImpl.create(ColorDefinitionImpl.GREY(),
            LineStyle.DASHED_LITERAL, 1);
      la.setVisible(true);
      grid.setTickAttributes(la);
      yAxis.setMajorGrid(grid);
      scale = factory.createScale();
      scale.setMin(NumberDataElementImpl.create(0));
      scale.setMax(NumberDataElementImpl.create(1));
      yAxis.setScale(scale);
      fd = FontDefinitionImpl.createEmpty();
      fd.setSize(MONOSPACE_FONT_SIZE);
      yTickLabel = LabelImpl.create();
      yTickLabel.setVisible(false);
      yTickLabel.getCaption().setFont(fd);
      yTickLabel.getCaption().getFont().setName(MONOSPACE_FONT_NAME);
      yAxis.setLabel(yTickLabel);

      // Create empty x label serie.
      xLabelSeries = SeriesImpl.create();
      xLabelSeries.setDataSet(TextDataSetImpl.create(new String[] {""}));
      sdX = SeriesDefinitionImpl.create();
      sdX.getSeries().add(xLabelSeries);
      xAxis.getSeriesDefinitions().add(sdX);

      return barChart;
   }

   public int getLength()
   {
      return ((chartDataInput != null) ? chartDataInput.length : 0);
   }

   public void sortXAxis(boolean descending)
   {
      if (chartDataInput != null)
      {
         chartDataComparator.setYAxis(false);
         chartDataComparator.setDescending(descending);
         Arrays.sort(chartDataInput, chartDataComparator);
         setInput(chartDataInput);
         fireChartModelChanged();
      }
   }

   public void sortYAxis(boolean descending)
   {
      if (chartDataInput != null)
      {
         chartDataComparator.setYAxis(true);
         chartDataComparator.setDescending(descending);
         Arrays.sort(chartDataInput, chartDataComparator);
         setInput(chartDataInput);
         fireChartModelChanged();
      }
   }

   public void scroll(int selection)
   {
      int window;
      int length;
      int start;
      int end;
      Object[] scrollResult;

      if (chartDataInput == null)
      {
         fireChartModelScrolled();
         return;
      }

      window = getWindow();
      length = getLength();
      if ((selection + window) > length)
      {
         start = ((window < length) ? (length - window) : 0);
         end = length;
      }
      else
      {
         start = selection;
         end = selection + window;
      }

      scrollResult = new Object[end - start];
      System.arraycopy(chartDataInput, start, scrollResult, 0, end - start);
      setInput(scrollResult);
      fireChartModelScrolled();
   }

   protected abstract void setInput(Object[] input);

   protected static String truncateString(String string)
   {
      if (string.length() > MAX_STRING_LENGTH)
      {
         string = string.substring(0, MAX_STRING_LENGTH - 3) + "...";
      }
      return string;
   }

   /**
    * Round up the given value to the nearest power of ten times 1, 2, or 5.
    * Note: The given value must be positive.
    *
    * @param value  the value to be rounded up.
    * @return the rounded up value.
    * @throws ArithmeticException  if the given value is negative.
    */
   protected static double roundUp(double value)
   {
      int exponent;

      if (value < 0)
      {
         throw new ArithmeticException();
      }

      exponent = (int) Math.floor(Math.log(value) / Math.log(10));
      value *= Math.pow(10, -exponent);
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

   protected static double getRoundedMax(double max)
   {
      double stepSize;
      double numSteps;

      stepSize = getStepSize(max);
      numSteps = Math.ceil(max / stepSize);
      return (stepSize * numSteps);
   }

   protected static double getStepSize(double max)
   {
      return ((max > MAX_NUM_STEPS) ? roundUp(max / MAX_NUM_STEPS) : 1);
   }

   protected static abstract class AbstractChartDataComparator
      implements Comparator
   {
      protected boolean yAxis;
      protected boolean descending;

      protected AbstractChartDataComparator()
      {
         yAxis = true;
         descending = true;
      }

      public void setYAxis(boolean yAxis)
      {
         this.yAxis = yAxis;
      }

      public void setDescending(boolean descending)
      {
         this.descending = descending;
      }

      protected int compareUnsignedInts(int i1, int i2)
      {
         int result;
         long l1 = 0xFFFFFFFFL & i1;
         long l2 = 0xFFFFFFFFL & i2;

         result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
         if (descending)
         {
            result *= -1;
         }
         return result;
      }

      protected int compareDoubles(double d1, double d2)
      {
         int result = (d1 < d2) ? -1 : ((d1 > d2) ? 1 : 0);
         if (descending)
         {
            result *= -1;
         }
         return result;
      }

      protected int compareUnsignedIntsPair(int i11, int i12,
                                            int i21, int i22)
      {
         int result;
         long l11 = 0xFFFFFFFFL & i11;
         long l12 = 0xFFFFFFFFL & i12;
         long l21 = 0xFFFFFFFFL & i21;
         long l22 = 0xFFFFFFFFL & i22;
         long sum1 = l11 + l12;
         long sum2 = l21 + l22;

         if (sum1 < sum2)
         {
            result = -1;
         }
         else if (sum1 > sum2)
         {
            result = 1;
         }
         else
         {
            result = (l11 < l21) ? -1 : ((l11 > l21) ? 1 : 0);
         }

         if (descending)
         {
            result *= -1;
         }
         return result;
      }

      protected int compareUnsignedIntsTriple(int i11, int i12, int i13,
                                              int i21, int i22, int i23)
      {
         int result;
         long l11 = 0xFFFFFFFFL & i11;
         long l12 = 0xFFFFFFFFL & i12;
         long l13 = 0xFFFFFFFFL & i13;
         long l21 = 0xFFFFFFFFL & i21;
         long l22 = 0xFFFFFFFFL & i22;
         long l23 = 0xFFFFFFFFL & i23;
         long sum1 = l11 + l12 + l13;
         long sum2 = l21 + l22 + l23;

         if (sum1 < sum2)
         {
            result = -1;
         }
         else if (sum1 > sum2)
         {
            result = 1;
         }
         else
         {
            if (l11 < l21)
            {
               result = -1;
            }
            else if (l11 > l21)
            {
               result = 1;
            }
            else
            {
               result = (l12 < l22) ? -1 : ((l12 > l22) ? 1 : 0);
            }
         }

         if (descending)
         {
            result *= -1;
         }
         return result;
      }

      protected int compareStrings(String s1, String s2)
      {
         int result = s1.compareTo(s2);
         if (descending)
         {
            result *= -1;
         }
         return result;
      }
   }
}
