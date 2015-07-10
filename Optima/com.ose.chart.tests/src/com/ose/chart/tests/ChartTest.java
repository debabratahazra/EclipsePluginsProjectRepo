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

package com.ose.chart.tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.ChartContentProviderAdapter;
import com.ose.chart.model.ChartContentTransformer;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;
import com.ose.chart.ui.Camera;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ChartSelectionListener;
import com.ose.chart.ui.ColorRGBA;
import com.ose.chart.ui.GraphicsContext;

public class ChartTest
{
   public static final int DEFAULT_OFFSET = 0;
   public static final int DEFAULT_COUNT = 10;
   
   public static final int CHANGED_OFFSET = 2;
   public static final int CHANGED_COUNT = 5;
   
   private MockupChart chart;
   
   public Range itemSelectionChangedRange = null;
   public Range seriesSelectionChangedRange = null;
   public Range layerSelectionChangedRange = null;
   
   @Before
   public void setUp() throws Exception
   {      
      chart = new MockupChart("title",
                              new MockupContentProvider(),
                              new MockupColorProvider(),
                              new MockupCamera());
   }

   @After
   public void tearDown() throws Exception
   {
   }

   @Test
   public void testGetAndSetContentProvider()
   {
      ChartContentProvider contentProvider1;
      ChartContentProvider contentProvider2;
      ChartContentTransformer contentTransformer1;
      ChartContentTransformer contentTransformer2;
      ChartContentTransformer contentTransformer3;

      contentProvider1 = new MockupContentProvider();
      chart.setContentProvider(contentProvider1);
      assertTrue(chart.getSourceContentProvider() == contentProvider1);
      assertTrue(chart.getContentProvider() == contentProvider1);

      contentTransformer1 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer1);
      contentTransformer2 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer2);
      contentTransformer3 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer3);
      assertTrue(chart.getSourceContentProvider() == contentProvider1);
      assertTrue(chart.getContentProvider() == contentTransformer3);

      contentProvider2 = new MockupContentProvider();
      chart.setContentProvider(contentProvider2);
      assertTrue(chart.getSourceContentProvider() == contentProvider2);
      assertTrue(chart.getContentProvider() == contentTransformer3);
   }

   @Test
   public void testAddContentTransformers()
   {
      MockupContentProvider contentProvider;
      ChartContentTransformer contentTransformer1;
      ChartContentTransformer contentTransformer2;
      ChartContentTransformer contentTransformer3;
      ChartContentTransformer[] contentTransformers;

      contentProvider = (MockupContentProvider) chart.getSourceContentProvider();
      contentTransformer1 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer1);
      contentTransformer2 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer2);
      contentTransformer3 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer3);
      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 3);
      assertTrue(contentTransformers[0] == contentTransformer1);
      assertTrue(contentTransformers[1] == contentTransformer2);
      assertTrue(contentTransformers[2] == contentTransformer3);

      contentProvider.fireContentChanged();
      assertTrue(chart.getChangedContentPovider() == contentTransformer3);
      assertTrue(contentTransformer3.getValue(0, 0, 0) == 3);
      assertTrue(contentTransformer2.getValue(0, 0, 0) == 2);
      assertTrue(contentTransformer1.getValue(0, 0, 0) == 1);
      assertTrue(contentProvider.getValue(0, 0, 0) == 0);
   }

   @Test
   public void testRemoveContentTransformers()
   {
      MockupContentProvider contentProvider;
      ChartContentTransformer contentTransformer1;
      ChartContentTransformer contentTransformer2;
      ChartContentTransformer contentTransformer3;
      ChartContentTransformer[] contentTransformers;

      contentProvider = (MockupContentProvider) chart.getSourceContentProvider();
      contentTransformer1 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer1);
      contentTransformer2 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer2);
      contentTransformer3 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer3);
      contentProvider.fireContentChanged();
      assertTrue(chart.getChangedContentPovider() == contentTransformer3);
      assertTrue(chart.getChangedContentPovider().getValue(0, 0, 0) == 3);

      chart.removeContentTransformer(contentTransformer2);
      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 2);
      assertTrue(contentTransformers[0] == contentTransformer1);
      assertTrue(contentTransformers[1] == contentTransformer3);
      assertTrue(chart.getContentProvider() == contentTransformer3);
      contentProvider.fireContentChanged();
      assertTrue(chart.getChangedContentPovider() == contentTransformer3);
      assertTrue(chart.getChangedContentPovider().getValue(0, 0, 0) == 2);

      chart.removeContentTransformer(contentTransformer3);
      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 1);
      assertTrue(contentTransformers[0] == contentTransformer1);
      assertTrue(chart.getContentProvider() == contentTransformer1);
      contentProvider.fireContentChanged();
      assertTrue(chart.getChangedContentPovider() == contentTransformer1);
      assertTrue(chart.getChangedContentPovider().getValue(0, 0, 0) == 1);

      chart.removeContentTransformer(contentTransformer1);
      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 0);
      assertTrue(chart.getContentProvider() == contentProvider);
      contentProvider.fireContentChanged();
      assertTrue(chart.getChangedContentPovider() == contentProvider);
      assertTrue(chart.getChangedContentPovider().getValue(0, 0, 0) == 0);
   }

   @Test
   public void testGetContentTransformers()
   {
      ChartContentTransformer contentTransformer1;
      ChartContentTransformer contentTransformer2;
      ChartContentTransformer contentTransformer3;
      ChartContentTransformer[] contentTransformers;

      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 0);

      contentTransformer1 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer1);
      contentTransformer2 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer2);
      contentTransformer3 = new MockupContentTransformer();
      chart.addContentTransformer(contentTransformer3);

      contentTransformers = chart.getContentTransformers();
      assertTrue(contentTransformers.length == 3);
      assertTrue(contentTransformers[0] == contentTransformer1);
      assertTrue(contentTransformers[1] == contentTransformer2);
      assertTrue(contentTransformers[2] == contentTransformer3);
   }

   @Test
   public void testGetItemSelection()
   {
      Range range = chart.getItemSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);
   }

   @Test
   public void testSetItemSelectionRange()
   {
      Range range = chart.getItemSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setItemSelection(new MutableRange(CHANGED_OFFSET, CHANGED_COUNT));
      range = chart.getItemSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testSetItemSelectionIntInt()
   {
      Range range = chart.getItemSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setItemSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getItemSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testClearItemSelection()
   {
      Range range = chart.getItemSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setItemSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getItemSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      chart.clearItemSelection();
      range = chart.getItemSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);      
   }

   @Test
   public void testChartSelectionListenerItem()
   {
      ChartSelectionListener listener = new MockupSelectionListener();
      chart.addChartSelectionListener(listener);
      
      chart.setItemSelection(CHANGED_OFFSET, CHANGED_COUNT);
      Range range = chart.getItemSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      assertNotNull(itemSelectionChangedRange);
      assertTrue(itemSelectionChangedRange.getOffset() == CHANGED_OFFSET);
      assertTrue(itemSelectionChangedRange.getCount() == CHANGED_COUNT);
   }
   
   @Test
   public void testGetSeriesSelection()
   {
      Range range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);
   }

   @Test
   public void testSetSeriesSelectionRange()
   {
      Range range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setSeriesSelection(new MutableRange(CHANGED_OFFSET, CHANGED_COUNT));
      range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testSetSeriesSelectionIntInt()
   {
      Range range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setSeriesSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testClearSeriesSelection()
   {
      Range range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setSeriesSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      chart.clearSeriesSelection();
      range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);      
   }

   @Test
   public void testChartSelectionListenerSeries()
   {
      ChartSelectionListener listener = new MockupSelectionListener();
      chart.addChartSelectionListener(listener);
      
      chart.setSeriesSelection(CHANGED_OFFSET, CHANGED_COUNT);
      Range range = chart.getSeriesSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      assertNotNull(seriesSelectionChangedRange);
      assertTrue(seriesSelectionChangedRange.getOffset() == CHANGED_OFFSET);
      assertTrue(seriesSelectionChangedRange.getCount() == CHANGED_COUNT);
   }
   
   @Test
   public void testGetLayerSelection()
   {
      Range range = chart.getLayerSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);
   }

   @Test
   public void testSetLayerSelectionRange()
   {
      Range range = chart.getLayerSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setLayerSelection(new MutableRange(CHANGED_OFFSET, CHANGED_COUNT));
      range = chart.getLayerSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testSetLayerSelectionIntInt()
   {
      Range range = chart.getLayerSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setLayerSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getLayerSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
   }

   @Test
   public void testClearLayerSelection()
   {
      Range range = chart.getLayerSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);

      chart.setLayerSelection(CHANGED_OFFSET, CHANGED_COUNT);
      range = chart.getLayerSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      chart.clearLayerSelection();
      range = chart.getLayerSelection();
      assertTrue(range.getOffset() == DEFAULT_OFFSET);
      assertTrue(range.getCount() == DEFAULT_COUNT);      
   }

   @Test
   public void testChartSelectionListenerLayer()
   {
      ChartSelectionListener listener = new MockupSelectionListener();
      chart.addChartSelectionListener(listener);
      
      chart.setLayerSelection(CHANGED_OFFSET, CHANGED_COUNT);
      Range range = chart.getLayerSelection();
      assertTrue(range.getOffset() == CHANGED_OFFSET);
      assertTrue(range.getCount() == CHANGED_COUNT);
      
      assertNotNull(layerSelectionChangedRange);
      assertTrue(layerSelectionChangedRange.getOffset() == CHANGED_OFFSET);
      assertTrue(layerSelectionChangedRange.getCount() == CHANGED_COUNT);
   }
   
   // Mockup classes

   class MockupChart extends Chart
   {
      private ChartContentProvider changedContentPovider;

      MockupChart(String title, ChartContentProvider content,
                  ChartColorProvider colors, Camera camera)
      {
         super(title, content, colors, camera);
      }

      public void contentChanged(ChartContentProvider contentProvider)
      {
         super.contentChanged(contentProvider);
         changedContentPovider = contentProvider;
      }

      public ChartContentProvider getChangedContentPovider()
      {
         return changedContentPovider;
      }

      public Range getItemWindow() {return null;}

      public void setItemWindow(int offset, int count) {}

      public void setItemWindow(Range range) {}

      protected void drawChart(GraphicsContext g, int width, int height) {}
   }

   class MockupContentProvider extends ChartContentProviderAdapter
   {      
      MockupContentProvider()
      {
         super();
         setItemRange(DEFAULT_OFFSET, DEFAULT_COUNT);
         setSeriesRange(DEFAULT_OFFSET, DEFAULT_COUNT);
         setLayerRange(DEFAULT_OFFSET, DEFAULT_COUNT);
      }

      public double getValue(int layer, int series, int item)
      {
         return 0;
      }

      public void fireContentChanged()
      {
         notifyContentChanged();
      }
   }

   class MockupContentTransformer extends ChartContentTransformer
   {
      public double getValue(int layer, int series, int item)
      {
         // Add one to the source value.
         return getSourceContentProvider().getValue(layer, series, item) + 1;
      }

      public String getValueLabel(int layer, int series, int item)
      {
         return getSourceContentProvider().getValueLabel(layer, series, item);
      }

      public String getItemLabel(int layer, int series, int item)
      {
         return getSourceContentProvider().getItemLabel(layer, series, item);
      }

      public String getSeriesLabel(int layer, int series)
      {
         return getSourceContentProvider().getSeriesLabel(layer, series);
      }

      public String getLayerLabel(int layer)
      {
         return getSourceContentProvider().getLayerLabel(layer);
      }

      public Range getItemRange()
      {
         return getSourceContentProvider().getItemRange();
      }

      public Range getSeriesRange()
      {
         return getSourceContentProvider().getSeriesRange();
      }

      public Range getLayerRange()
      {
         return getSourceContentProvider().getLayerRange();
      }
      
      public int getSourceItem(int item)
      {
         return item;
      }
      
      public int getSourceSeries(int series)
      {
         return series;
      }
      
      public int getSourceLayer(int layer)
      {
         return layer;
      }

      protected void sourceContentChanged(ChartContentProvider contentProvider)
      {
      }
   }

   class MockupCamera implements Camera
   {
      public void applyProjectionTransform(GraphicsContext g, int width,
            int height) {}
      public void applyWorldViewTransform(GraphicsContext g, int width,
            int height) {}
   }
   
   class MockupColorProvider implements ChartColorProvider
   {
      public ColorRGBA getSeriesColor(int layer, int series) {return null;}
   }
   
   class MockupSelectionListener implements ChartSelectionListener
   {
      public void itemSelectionChanged(Chart chart)
      {
         Range range = chart.getItemSelection();
         itemSelectionChangedRange = new MutableRange(range.getOffset(),
                                                 range.getCount());
      }

      public void layerSelectionChanged(Chart chart)
      {
         Range range = chart.getLayerSelection();
         layerSelectionChangedRange = new MutableRange(range.getOffset(),
               range.getCount());
      }

      public void seriesSelectionChanged(Chart chart)
      {
         Range range = chart.getSeriesSelection();
         seriesSelectionChangedRange = new MutableRange(range.getOffset(),
               range.getCount());
      }      
   }
}
