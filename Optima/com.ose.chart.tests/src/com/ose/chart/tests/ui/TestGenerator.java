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

import com.ose.chart.ui.BarChart2D;
import com.ose.chart.ui.BarChart3D;
import com.ose.chart.ui.ClusteredBarChart3D;
import com.ose.chart.ui.StackedBarChart2D;
import com.ose.chart.ui.StackedBarChart3D;
import com.ose.chart.model.MutableRange;

/**
 * 
 * this class populates the chart test viewer
 * 
 * to create a new Test
 * 
 * - add a unique identifier as a constant
 * 
 * - add a name for the test
 * 
 * - write your own generate method, that initializes the Chart and the description for the node
 *   there are a few examples for each type of chart. There is a description
 *   about the significance of the parameters preceding each chart type
 * 
 * - in method startTest, add an entry point to your method
 *   case : YOUR ID
 *       call yourMehtod(node)
 *       break;
 *       
 * - in the generateTestSuite() method, add your Test under the corresponding chart 
 *   type. For the ChartTest object, the Chart and the description are not initialized
 *   so we don't keep all the data in memory. 
 *   
 * - the double click listener for the view will call the startTest() method that will
 *   in turn call the corresponding initiate method and populate your ChartTest with the
 *   Chart object to be displayed and the test description    
 * 
 * 
 * to add a new chart category add a chart_type node in the generateTestSuite method
 * then you can proceed and add your Tests
 *
 */
public class TestGenerator 
{
   // id's for the chart tests   
   public static final int BAR_CHART_2D_T1 = 1;
   public static final String BAR_CHART_2D_T1_NAME = "RandomDataT1";
   public static final int BAR_CHART_2D_T2 = 2;
   public static final String BAR_CHART_2D_T2_NAME = "RandomDataT2";
   public static final int BAR_CHART_2D_T3 = 3;
   public static final String BAR_CHART_2D_T3_NAME = "RandomDataT3";

   public static final int STACKED_BAR_CHART_2D_T1 = 11;
   public static final String STACKED_BAR_CHART_2D_T1_NAME = "RandomDataT1";
   public static final int STACKED_BAR_CHART_2D_T2 = 12;
   public static final String STACKED_BAR_CHART_2D_T2_NAME = "RandomDataT2";
   public static final int STACKED_BAR_CHART_2D_T3 = 13;
   public static final String STACKED_BAR_CHART_2D_T3_NAME = "RandomDataT3";
   
   public static final int BAR_CHART_3D_T1 = 21;
   public static final String BAR_CHART_3D_T1_NAME = "RandomDataT1";
   public static final int BAR_CHART_3D_T2 = 22;
   public static final String BAR_CHART_3D_T2_NAME = "RandomDataT2";
   
   public static final int CLUSTERED_BAR_CHART_3D_T1 = 31;
   public static final String CLUSTERED_BAR_CHART_3D_T1_NAME = "RandomDataT1";
   public static final int CLUSTERED_BAR_CHART_3D_T2 = 32;
   public static final String CLUSTERED_BAR_CHART_3D_T2_NAME = "RandomDataT2";
   public static final int CLUSTERED_BAR_CHART_3D_T3 = 33;
   public static final String CLUSTERED_BAR_CHART_3D_T3_NAME = "RandomDataT3";
   
   public static final int STACKED_BAR_CHART_3D_T1 = 41;
   public static final String STACKED_BAR_CHART_3D_T1_NAME = "RandomDataT1";
   public static final int STACKED_BAR_CHART_3D_T2 = 42;
   public static final String STACKED_BAR_CHART_3D_T2_NAME = "RandomDataT2";
   
   public static void startTest (ChartTestModelNode node, int testID)
   {
      switch (testID)
      {
      case BAR_CHART_2D_T1:
         generateBarChart2D_t1(node);
         break;
      case BAR_CHART_2D_T2:
         generateBarChart2D_t2(node);
         break;
      case BAR_CHART_2D_T3:
         generateBarChart2D_t3(node);
         break;
      case STACKED_BAR_CHART_2D_T1:
         generateStackedBarChart2D_t1(node);
         break;
      case STACKED_BAR_CHART_2D_T2:
         generateStackedBarChart2D_t2(node);
         break;
      case STACKED_BAR_CHART_2D_T3:
         generateStackedBarChart2D_t3(node);
         break;
      case BAR_CHART_3D_T1:
         generateBarChart3D_t1(node);
         break;
      case BAR_CHART_3D_T2:
         generateBarChart3D_t2(node);
         break;
      case CLUSTERED_BAR_CHART_3D_T1:
         generateClusteredBarChart3D_t1(node);
         break;
      case CLUSTERED_BAR_CHART_3D_T2:
         generateClusteredBarChart3D_t2(node);
         break;
      case CLUSTERED_BAR_CHART_3D_T3:
         generateClusteredBarChart3D_t3(node);
         break;
      case STACKED_BAR_CHART_3D_T1:
         generateStackedBarChart3D_t1(node);
         break;
      case STACKED_BAR_CHART_3D_T2:
         generateStackedBarChart3D_t2(node);
         break;
      default:
         break;
      }
   }
   
/* =================================================================== */   

   /**
    * 
    * BarChart2D - currently used by the CPU Profiler Editor in Optima
    * 
    * parameters for BarChart2D tests with random values
    * 
    * layerRange - not used
    * seriesRange - not used
    * itemRange - range of items that will be displayed, default it (0, 50)
    *           - the chart only displays items > 0
    * valueRange - specify range for values, default is 0, 70
    * maxValue - calculated by the ContentProvider
    * minValue - calculated by the ContentProvider
    * tickCount - if it is not set a random value from 1 to 8 is generated
    * itemCount - number of items so be displayed, default is 10. currently there are more items 
    *             displayed then the itemCount, and for values close to the number of items
    *             the test will crash
    *           - you can use setItemWindow method for the chart to set the exact number of items
    *             to be displayed
    */
   
   /**
    * Display a chart with mixed values, a fixed ItemCount, and a fixed TickStep
    */
   public static void generateBarChart2D_t1(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(-20, 80));
      contentProvider.setValueRange(new MutableRange(-20, 90));
      contentProvider.setItemCount(15);
      contentProvider.setTickStep(4);
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());

      BarChart2D chart = new BarChart2D("RandomValuesBarChart2D_t1", contentProvider, new ColorProviderItems());
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setItemTickStep(contentProvider.getTickStep());
      
      String testDescription = "BarChart2D with random-generated data.\n" +
                               "There are " + nr_items + " items, with values ranging from " + 
                               (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\ntickStep is " + contentProvider.getTickStep() + " and itemCount is " + 
                               contentProvider.getItemCount() +
                               ".";
      
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ------------------------------------------------------------------ */
   
   /*
    * display a chart with a large number of values, all positive, up to a higher value,
    * fixed itemCount, fixed TickStep
    */
   public static void generateBarChart2D_t2(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 2000));
      contentProvider.setValueRange(new MutableRange(30, 200));
      contentProvider.setItemCount(30);
      contentProvider.setTickStep(10);
      contentProvider.initTestContent();

      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());

      BarChart2D chart = new BarChart2D("RandomValuesBarChart2D_t2", contentProvider, new ColorProviderItems());
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setItemTickStep(contentProvider.getTickStep());
      
      String testDescription = "BarChart2D with random-generated data.\n" +
                               "There are " + nr_items + " items, with values ranging from " + 
                               (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\ntickStep is " + contentProvider.getTickStep() + " and itemCount is " + 
                               contentProvider.getItemCount() +
                               ".";

      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ------------------------------------------------------------------ */
   
   /*
    * display a chart with positive values, random tickStep, default itemCount, random ColorProvider
    */
   public static void generateBarChart2D_t3(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setValueRange(new MutableRange(0, 40));
      contentProvider.initTestContent();

      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());

      BarChart2D chart = new BarChart2D("RandomValuesBarChart2D_t3", contentProvider, new RandomColorProviderItems());
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setItemTickStep(contentProvider.getTickStep());
      
      String testDescription = "BarChart2D with random-generated data.\n" +
                               "There are " + nr_items + " items, with values ranging from " + 
                               (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\ntickStep is " + contentProvider.getTickStep() + " and itemCount is " + 
                               contentProvider.getItemCount() +
                               ".";
      
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ========================================================================== */   

/*
 * StackedBarChart2D  - is not used in Optima plug-ins will assume it is intended 
 *                      to draw layers of values as bars on top of each other, 
 *                      each layer with a different color.
 * 
 * parameters for StackedBarChart2D tests with random values
 * 
 * layerRange - the number of layers to be displayed on top of each other
 * seriesRange - not used
 * itemRange - range of items that will be displayed, default it (0, 50)
 *           - the chart only displays values > 0
 * valueRange - range for values. Keep in mind that at the moment it only makes 
 *            sense to have a StackedBarChart2D if all the values are positive. 
 * maxValue - calculated by the ContentProvider
 * minValue - calculated by the ContentProvider
 * tickCount - if it is not set a random value from 1 to 8 is generated
 * itemCount - number of items so be displayed, default is 10. currently there are more items 
 *   displayed then the itemCount, and for values close to the number of items
 *   the test will crash
 * call setBarStacked2DMinMaxValues() after you init the content, so set range for stacks.
 * 
 * colorProvider - color provider needs to generate different colors for each layer, or the 
 *   graphics will not be that relevant.
 * 
 */
   
   /*
    * display a chart with 5 layers, 100 items on each layer, random generated values
    * from 0 to 40, fixed itemCount and fixed tickStep, colors are random generated
    */   
   public static void generateStackedBarChart2D_t1(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 5));
      contentProvider.setValueRange(new MutableRange(0, 40));
      contentProvider.setItemCount(20);
      contentProvider.setTickStep(6);
      
      contentProvider.initTestContent();
      contentProvider.setStacked2DMinMaxValues();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount() > 0 ? contentProvider.getSeriesRange().getCount() : 1;
      
      RandomColorProviderSeries colorProvider = new RandomColorProviderSeries(nr_series, 0);

      StackedBarChart2D chart = new StackedBarChart2D("RandomValuesStackedBarChart2D_t1", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setItemTickStep(contentProvider.getTickStep());

      String testDescription = "StackedBarChart2D with random-generated data.\n" +
                               "There are " + nr_series + " series, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nMaximum added value is " + contentProvider.getMaxValue() + ", tickStep is " + 
                               contentProvider.getTickStep() + ", and itemCount is " + contentProvider.getItemCount() +
                               ".\nColors for each layer are random-generated.";
      
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }

/* ------------------------------------------------------------------ */
   
   /*
    * display a chart with 2 layers, 200 items on each layer, random generated values
    * from 3 to 10, fixed itemCount and random tickStep, colors are random generated
    */   
   public static void generateStackedBarChart2D_t2(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 200));
      contentProvider.setSeriesRange(new MutableRange(0, 2));
      contentProvider.setValueRange(new MutableRange(3, 10));
      contentProvider.setItemCount(12);
      
      contentProvider.initTestContent();
      contentProvider.setStacked2DMinMaxValues();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount() > 0 ? contentProvider.getSeriesRange().getCount() : 1;
      
      RandomColorProviderSeries colorProvider = new RandomColorProviderSeries(nr_series, 0);

      StackedBarChart2D chart = new StackedBarChart2D("RandomValuesStackedBarChart2D_t2", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setItemTickStep(contentProvider.getTickStep());

      String testDescription = "StackedBarChart2D with random-generated data.\n" +
                               "There are " + nr_series + " series, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nMaximum added value is " + contentProvider.getMaxValue() + ", tickStep is " + 
                               contentProvider.getTickStep() + ", and itemCount is " + contentProvider.getItemCount() +
                               ".\nColors for each layer are random-generated.";

      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* --------------------------------------------------------------  */   
   
   /*
    * StackedBarChart2D with 20 Layers, 10 items on each layer, Item Count 2, random colors
    * values always higher then 0
    */   
   public static void generateStackedBarChart2D_t3(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 10));
      contentProvider.setSeriesRange(new MutableRange(0, 20));
      contentProvider.setValueRange(new MutableRange(1, 10));
      contentProvider.setItemCount(2);
      contentProvider.setTickStep(1);
      
      contentProvider.initTestContent();
      contentProvider.setStacked2DMinMaxValues();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount() > 0 ? contentProvider.getSeriesRange().getCount() : 1;
      
      RandomColorProviderSeries colorProvider = new RandomColorProviderSeries(nr_series, 0);

      StackedBarChart2D chart = new StackedBarChart2D("RandomValuesStackedBarChart2D_t3", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setItemTickStep(contentProvider.getTickStep());

      String testDescription = "StackedBarChart2D with random-generated data.\n" +
                               "There are " + nr_series + " series, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nMaximum added value is " + contentProvider.getMaxValue() + ", tickStep is " + 
                               contentProvider.getTickStep() + ", and itemCount is " + contentProvider.getItemCount() +
                               ".\nColors for each layer are random-generated.";
      
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ========================================================================== */   

/*
 * BarChart3D - this is used in Optima by the CPUPriorityProfilerEditor and
 *              by the CPUProcessProfilerEditor
 *                       
 * parameters for BarChart3D tests with random values
 * 
 * layerRange - make sure the offset is zero in the content provider
 * seriesRange - the number of item series, displayed on Z axis, the offset for the series is 
 *               set to positive value without changing the count
 *             - make sure the offset is always > 0
 * itemRange - range of items that will be displayed, default it (0, 50)
 *           - the chart only displays values > 0
 * valueRange - range for values. 
 * maxValue - calculated by the ContentProvider
 * minValue - calculated by the ContentProvider
 * tickCount - if it is not set a random value from 1 to 8 is generated for the ContentProvider
 * 
 * itemCount - not used, we need to call chart.setItemWindow() to set the number of items listed
 * if we have more than 20 series the editor will display the SeriesSlider, and we can set the
 * number of series using chart.setSeriesWindow()
 * 
 * colorProvider - color provider generates different colors for every series
 *              
 */
   
   /*
    * BarChart3D with 10 series, 300 items on each series values from 1 to 11
    * item window set to 50 
    */
   public static void generateBarChart3D_t1(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 300));
      contentProvider.setSeriesRange(new MutableRange(0, 10));
      contentProvider.setLayerRange(new MutableRange(0, 0));
      contentProvider.setValueRange(new MutableRange(1, 10));
      
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      RandomColorProviderSeries colorProvider = new RandomColorProviderSeries(contentProvider.getSeriesRange().getCount(), contentProvider.getSeriesRange().getOffset());

      BarChart3D chart = new BarChart3D("RandomValuesBarChart3D_t1", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setItemWindow(0,50);
      chart.resetCamera();
            
      String testDescription = "BarChart3D with random-generated data.\n" +
                               "There are " + nr_series + " series, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nThe color for each series is random-generated.";
                               
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* --------------------------------------------------------------  */  
   
   /**
    * BarChart3D with both negative and positive values, and slider for both series and items
    */
   public static void generateBarChart3D_t2(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 70));
      contentProvider.setSeriesRange(new MutableRange(0, 30));
      contentProvider.setLayerRange(new MutableRange(0, 0));
      contentProvider.setValueRange(new MutableRange(-10, 20));
      
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      RandomColorProviderSeries colorProvider = new RandomColorProviderSeries(contentProvider.getSeriesRange().getCount(), contentProvider.getSeriesRange().getOffset());

      BarChart3D chart = new BarChart3D("RandomValuesBarChart3D_t2", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setItemWindow(0,30);
      chart.setSeriesWindow(0, 15);
      chart.resetCamera();

      String testDescription = "BarChart3D with random-generated data.\n" +
                               "There are " + nr_series + " series, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nThe color for each series is random-generated.";
                               
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ========================================================================== */   

/*
 * ClusteredBarChart3D - this is used in Optima by the UserProfiler Editor
 *                       
 * parameters for ClusteredBarChart3D tests with random values
 * 
 * layerRange - make sure the offset is zero in the content provider
 * seriesRange - the number of item series, displayed on Z axis, the offset for the series is 
 *               set to positive value without changing the count
 *             - make sure the offset is always > 0
 * itemRange - range of items that will be displayed, default it (0, 50)
 *           - the chart only displays values > 0
 * valueRange - range for values. 
 * maxValue - calculated by the ContentProvider
 * minValue - calculated by the ContentProvider
 * tickCount - if it is not set a random value from 1 to 8 is generated for the ContentProvider
 * 
 * itemCount - not used, we need to call chart.setItemWindow() to set the number of items listed
 * if we have more than 20 series the editor will display the SeriesSlider, and we can set the
 * number of series using chart.setSeriesWindow()
 * 
 * colorProvider - color provider should generate different colors at least for every layer.
 */

   /**
    * ClusteredBar3D with 5 layers, 6 series, and values from 1 to 11
    * use different colors for each series, and the colors for layers are derived from the
    * series colors
    */
   public static void generateClusteredBarChart3D_t1(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 6));
      contentProvider.setLayerRange(new MutableRange(5, 5));
      contentProvider.setValueRange(new MutableRange(1, 10));
      contentProvider.setTickStep(1);
          
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
     
      RandomColorProviderSeriesLayers colorProvider = new RandomColorProviderSeriesLayers(contentProvider.getSeriesRange(), contentProvider.getLayerRange());
     
      ClusteredBarChart3D chart = new ClusteredBarChart3D("RandomValuesClusteredBarChart3D_t1", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(0,50);
      chart.resetCamera();

      String testDescription = "ClusteredBarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, items are structured in " + nr_series + 
                               " series, \neach series having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nThe color for each series is random-generated, and for clusters " +
                               "\nthe layer colors are derived from the base series color.";

      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }

/* ---------------------------------------------------------------------------------  */  
   
   /**
    * ClusteredBar3D with 3 layers, 12 series, positive values
    * we use different colors for each layer
    */
   public static void generateClusteredBarChart3D_t2(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 12));
      contentProvider.setLayerRange(new MutableRange(0, 3));
      contentProvider.setValueRange(new MutableRange(0, 55));
      contentProvider.setTickStep(3);
          
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
     
      RandomColorProviderLayers colorProvider = new RandomColorProviderLayers(contentProvider.getLayerRange());
     
      ClusteredBarChart3D chart = new ClusteredBarChart3D("RandomValuesClusteredBarChart3D_t2", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(0,50);
      chart.resetCamera();

      String testDescription = "ClusteredBarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, items are structured in " + nr_series + 
                               " series, \neach series having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nThe color for each layer is random-generated.";
                               
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }

/* ---------------------------------------------------------------------------------  */     
   
   /**
    * ClusteredBar3D with 3 layers, 8 series, and values that are both positive and negative
    * use different colors for each series, and the colors for layers are derived from the
    * series colors
    */
   public static void generateClusteredBarChart3D_t3(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 90));
      contentProvider.setSeriesRange(new MutableRange(0, 8));
      contentProvider.setLayerRange(new MutableRange(0, 3));
      contentProvider.setValueRange(new MutableRange(-20, 60));
      contentProvider.setTickStep(1);
          
      contentProvider.initTestContent();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
     
      RandomColorProviderSeriesLayers colorProvider = new RandomColorProviderSeriesLayers(contentProvider.getSeriesRange(), contentProvider.getLayerRange());
     
      ClusteredBarChart3D chart = new ClusteredBarChart3D("RandomValuesClusteredBarChart3D_t3", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(0,50);
      chart.resetCamera();

      String testDescription = "ClusteredBarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, items are structured in " + nr_series + 
                               " series, \neach series having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nThe color for each series is random-generated, and for clusters " +
                               "\nthe layer colors are derived from the base series color.";

      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ========================================================================== */        

/*
 * StackedBarChart3D - this is never used in Optima
 *                       
 * parameters for ClusteredBarChart3D tests with random values
 * 
 * layerRange - the number of layers, for an item in a series, the cluster contains
 *              the values for each layer.
 * seriesRange - the number of series, they are displayed on Z axis in our chart
 * itemRange - range of items that will be displayed, default it (0, 50)
 *           - the chart only displays values > 0
 * valueRange - range for values, this chart can draw negative values as well
 * maxValue - calculated by the ContentProvider
 * minValue - calculated by the ContentProvider
 * tickCount - if it is not set a random value from 1 to 8 is generated
 * itemCount - number of items so be displayed, default is 10. currently there are more items 
 *   displayed then the itemCount, and for values close to the number of items
 *   the test will crash
 * call setBarStacked2DMinMaxValues() after you init the content, so set range for stacks.
 * 
 * colorProvider - color provider needs to generate different colors for each layer, or the 
 *   graphics will not be that relevant
 *              
 *              
 * for the BarChart2D, we must set a preferred ItemWindow, because it does not use itemCount
 * to set the number of displayable items.  
 */
        
   /**
    * StackedBarChart3D with 5 layers, 6 series, positive values
    * we use different colors for each series, and derived colors for the layers.
    */
   public static void generateStackedBarChart3D_t1(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 6));
      contentProvider.setLayerRange(new MutableRange(0, 5));
      contentProvider.setValueRange(new MutableRange(1, 10));
      contentProvider.setTickStep(1);
      contentProvider.setItemCount(15);
     
      contentProvider.initTestContent();
      contentProvider.setStackedMinMaxValues();
      
      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() > 0 ? contentProvider.getItemRange().getOffset() : -contentProvider.getItemRange().getOffset());
      int nr_series = contentProvider.getSeriesRange().getCount();

      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
      
      RandomColorProviderSeriesLayers colorProvider = new RandomColorProviderSeriesLayers(contentProvider.getSeriesRange(), contentProvider.getLayerRange());

      StackedBarChart3D chart = new StackedBarChart3D("RandomValuesStackedBarChart3D_t1", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(contentProvider.getItemRange());
      chart.setItemWindow(0,50);
      chart.resetCamera();

      String testDescription = "StackedBarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, items are structured in " + nr_series + 
                               " series, \neach series having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nMaximum added value is " + chart.getMaxValue() + 
                               ".\nThe color for each series is random-generated, and for clusters " +
                               "\nthe layer colors are derived from the base series color.";
                                 
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
    
   /**
    * StackedBarChart3D with 5 layers, 6 series, positive values, 40 items in the window
    * we use different colors for layers
    */
   public static void generateStackedBarChart3D_t2(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 6));
      contentProvider.setLayerRange(new MutableRange(0, 5));
      contentProvider.setValueRange(new MutableRange(1, 10));
      contentProvider.setTickStep(1);
      contentProvider.setItemCount(15);
      
      contentProvider.initTestContent();
      contentProvider.setStackedMinMaxValues();
       
      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
      
      RandomColorProviderLayers colorProvider = new RandomColorProviderLayers(contentProvider.getLayerRange());

      StackedBarChart3D chart = new StackedBarChart3D("RandomValuesStackedBarChart3D_t2", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(contentProvider.getItemRange());
      chart.setItemWindow(0,40);
      chart.resetCamera();

      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() < 0 ? 
                     0 : contentProvider.getItemRange().getOffset());

      String testDescription = "StackedBarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, " + "each series having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nThe window displays " + chart.getItemWindow().getCount() + " items" +
                               ".\nMaximum added value is " + chart.getMaxValue() +
                               ".\nThe color for each series is random-generated, and for clusters " +
                               "\nthe layer colors are derived from the base series color.";

      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }

  /**
   * StackedBarChart3D with 5 layers, 6 series, both positive and negative values, 40 items in the window
   * we use different colors for layers
   */
   public static void generateStackedBarChart3D_t3(ChartTestModelNode node)
   {
      // generate a random chart
      RandomContentProvider contentProvider = new RandomContentProvider();
      contentProvider.setItemRange(new MutableRange(0, 100));
      contentProvider.setSeriesRange(new MutableRange(0, 6));
      contentProvider.setLayerRange(new MutableRange(0, 5));
      contentProvider.setValueRange(new MutableRange(-8, 20));
      contentProvider.setTickStep(1);
      contentProvider.setItemCount(15);
      
      contentProvider.initTestContent();
      contentProvider.setStackedMinMaxValues();
       
      int nr_layers = contentProvider.getLayerRange().getCount() > 0 ? contentProvider.getLayerRange().getCount() : 1;
       
      RandomColorProviderLayers colorProvider = new RandomColorProviderLayers(contentProvider.getLayerRange());

      StackedBarChart3D chart = new StackedBarChart3D("RandomValuesStackedBarChart3D_t3", contentProvider, colorProvider);
      chart.setMaxValue(contentProvider.getMaxValue());
      chart.setMinValue(contentProvider.getMinValue());
      chart.setSeriesWindow(contentProvider.getSeriesRange());
      chart.setLayerWindow(contentProvider.getLayerRange());
      chart.setItemWindow(contentProvider.getItemRange());
      chart.setItemWindow(0,40);
      chart.resetCamera();

      int nr_items = contentProvider.getItemRange().getCount() - 
                     (contentProvider.getItemRange().getOffset() < 0 ? 
                     0 : contentProvider.getItemRange().getOffset());
          
      String testDescription = "BarChart3D with random-generated data.\n" +
                               "There are " + nr_layers + " layers, each one having " + nr_items +
                               " items, with values ranging from " + (contentProvider.getValueRange().getOffset()) +
                               " to " + (contentProvider.getValueRange().getOffset() + contentProvider.getValueRange().getCount()) +
                               ".\nMaximum added value is " + chart.getMaxValue() + ", minimum added value is " + chart.getMinValue() +
                               ", and itemCount is " + contentProvider.getItemCount() +
                               ".\nColors for each layer are random-generated.";
                                  
      Object info = node.getInfo();
      if (!(info instanceof ChartTest))
         return;
      ChartTest test = (ChartTest)info;
      test.setChart(chart);
      test.setTestDescription(testDescription);
   }
   
/* ========================================================================== */   

   /**
    * method to generate the tree. We add the ChartTest objects, but we don't
    * generate all the test data. That is generated when we open a test
    */
   public static ChartTestModelNode generateTestSuite()
   {
      ChartTestModelNode root = new ChartTestModelNode(ChartTestConstants.ROOT_NODE, "root");
      
      ChartTestModelNode barChart2D = new ChartTestModelNode(ChartTestConstants.CHART_NODE, "BarChart2D");
      barChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.BAR_CHART_2D_T1_NAME,
                  new ChartTest(null, "", TestGenerator.BAR_CHART_2D_T1)
                  )
            );
      barChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.BAR_CHART_2D_T2_NAME,
                  new ChartTest(null, "", TestGenerator.BAR_CHART_2D_T2)
                  )
            );
      barChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.BAR_CHART_2D_T3_NAME,
                  new ChartTest(null, "", TestGenerator.BAR_CHART_2D_T3)
                  )
            );
      
      ChartTestModelNode stackedBarChart2D = new ChartTestModelNode(ChartTestConstants.CHART_NODE, "StackedBarChart2D");
      stackedBarChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.STACKED_BAR_CHART_2D_T1_NAME,
                  new ChartTest(null, "", TestGenerator.STACKED_BAR_CHART_2D_T1)
                  )
            );
      stackedBarChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.STACKED_BAR_CHART_2D_T2_NAME,
                  new ChartTest(null, "", TestGenerator.STACKED_BAR_CHART_2D_T2)
                  )
            );
      stackedBarChart2D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.STACKED_BAR_CHART_2D_T3_NAME,
                  new ChartTest(null, "", TestGenerator.STACKED_BAR_CHART_2D_T3)
                  )
            );

      ChartTestModelNode barChart3D = new ChartTestModelNode(ChartTestConstants.CHART_NODE, "BarChart3D");
      barChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.BAR_CHART_3D_T1_NAME,
                  new ChartTest(null, "", TestGenerator.BAR_CHART_3D_T1)
                  )
            );
      barChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.BAR_CHART_3D_T2_NAME,
                  new ChartTest(null, "", TestGenerator.BAR_CHART_3D_T2)
                  )
            );

      ChartTestModelNode clusteredBarChart3D = new ChartTestModelNode(ChartTestConstants.CHART_NODE, "ClusteredBarChart3D");
      clusteredBarChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.CLUSTERED_BAR_CHART_3D_T1_NAME,
                  new ChartTest(null, "", TestGenerator.CLUSTERED_BAR_CHART_3D_T1)
                  )
            );
      clusteredBarChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.CLUSTERED_BAR_CHART_3D_T2_NAME,
                  new ChartTest(null, "", TestGenerator.CLUSTERED_BAR_CHART_3D_T2)
                  )
            );
      clusteredBarChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.CLUSTERED_BAR_CHART_3D_T3_NAME,
                  new ChartTest(null, "", TestGenerator.CLUSTERED_BAR_CHART_3D_T3)
                  )
            );
      
      ChartTestModelNode stackedBarChart3D = new ChartTestModelNode(ChartTestConstants.CHART_NODE, "StackedBarChart3D");
      stackedBarChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.STACKED_BAR_CHART_3D_T1_NAME,
                  new ChartTest(null, "", TestGenerator.STACKED_BAR_CHART_3D_T1)
                  )
            );
      stackedBarChart3D.addChildren(
            new ChartTestModelNode(
                  ChartTestConstants.TEST_NODE,
                  TestGenerator.STACKED_BAR_CHART_3D_T2_NAME,
                  new ChartTest(null, "", TestGenerator.STACKED_BAR_CHART_3D_T2)
                  )
            );
      
      root.addChildren(barChart2D);
      root.addChildren(stackedBarChart2D);
      root.addChildren(barChart3D);
      root.addChildren(clusteredBarChart3D);
      root.addChildren(stackedBarChart3D);
      
      return root;
   }
}
