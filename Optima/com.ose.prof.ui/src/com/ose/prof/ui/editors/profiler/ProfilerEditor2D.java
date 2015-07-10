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

package com.ose.prof.ui.editors.profiler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import com.ose.chart.model.Range;
import com.ose.chart.ui.CartesianChart;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartViewer;
import com.ose.chart.ui.ChartWindowListener;
import com.ose.system.TargetReport;

abstract class ProfilerEditor2D extends ProfilerEditor
{
   static final int TAB_FOLDER_CHART = 1;

   private ChartViewer chartViewer;
   private StatisticsPanel statsPanel;
   private ProfilerContentRangeTransformer contentRangeTransformer;

   @Override
   public void setFocus()
   {
      super.setFocus();

      if (getTabId(getTabFolder().getSelection()) == TAB_FOLDER_CHART)
      {
         chartViewer.setFocus();
      }
   }

   @Override
   public void setOfflineFeaturesEnabled(boolean enabled)
   {
      super.setOfflineFeaturesEnabled(enabled);

      statsPanel.setStatisticsEnabled(enabled);
      if (enabled)
      {
         chartViewer.enableItemSelector();
      }
      else
      {
         chartViewer.disableItemSelector();
      }
   }

   @Override
   public void clearReports()
   {
      ProfilerContentProvider contentProvider;
      CartesianChart chart;

      super.clearReports();

      contentProvider = getContentProvider();
      contentProvider.setMax(contentProvider.isRelative() ? 100 : 10);
      contentProvider.setMin(0);
      chart = (CartesianChart) chartViewer.getChart();
      resetChart(chart);
   }

   @Override
   public void addReports(TargetReport[] reports)
   {
      CartesianChart chart;

      super.addReports(reports);

      chart = (CartesianChart) chartViewer.getChart();
      chart.beginChanges();
      getContentProvider().addReports(reports);
      updateHeaderLabels();
      updateChartMinMax(chart);
      updateChartWindow(chart);
      statsPanel.setStatistics(calculateStatistics(chart));
      chart.endChanges(false);
   }

   void addContentRangeTransformer()
   {
      if (contentRangeTransformer == null)
      {
         contentRangeTransformer = new ProfilerContentRangeTransformer(
               getContentProvider());
         chartViewer.getChart().addContentTransformer(contentRangeTransformer);
      }
   }

   void removeContentRangeTransformer()
   {
      chartViewer.getChart().removeContentTransformer(contentRangeTransformer);
      contentRangeTransformer = null;
   }

   void updateContentRangeTransformer(int offset, int length, long integrationInterval)
   {
      if (contentRangeTransformer != null)
      {
         contentRangeTransformer.setEnabled(false);
         contentRangeTransformer.setup(offset, length, integrationInterval);
         contentRangeTransformer.setEnabled(true);
      }
   }

   void clearChartItemSelection()
   {
      chartViewer.getChart().clearItemSelection();
   }
   
   public void scrollTo(long time)
   {
      if (contentRangeTransformer != null)
      {
         int index = contentRangeTransformer.getIndexOfClosestReport(time);

         if (index >= 0)
         {
            Chart chart = chartViewer.getChart();
            chart.disableWindowNotifications();
            chart.setItemWindow(index, chart.getItemWindow().getCount());
            chart.enableWindowNotifications();
         }
      }
   }

   void createChartTabs(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      CTabItem tabItem;

      comp = getToolkit().createComposite(tabFolder);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.marginBottom = 5;
      comp.setLayout(layout);

      chartViewer = createChartViewer(comp);
      chartViewer.setLayoutData(new GridData(GridData.FILL_BOTH));
      statsPanel = new StatisticsPanel(comp, SWT.NONE);
      statsPanel.init(chartViewer);
      chartViewer.getChart().addChartSelectionListener(new StatisticsChartSelectionHandler(statsPanel));
      chartViewer.getChart().addChartWindowListener(new ChartWindowHandler());

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("Chart");
      tabItem.setControl(comp);
      assignTabId(tabItem, TAB_FOLDER_CHART);
   }

   abstract ChartViewer createChartViewer(Composite parent);

   void resetChart(CartesianChart chart)
   {
      chart.setMaxValue(getContentProvider().getMax());
      chart.setMinValue(getContentProvider().getMin());
   }

   void updateChartMinMax(CartesianChart chart)
   {
      if (!getContentProvider().isRelative())
      {
         chart.setMaxValue(getContentProvider().getMax());
         chart.setMinValue(getContentProvider().getMin());
      }
   }

   void updateChartWindow(CartesianChart chart)
   {
      Range itemRange = getContentProvider().getItemRange();
      int itemOffset = itemRange.getOffset();
      int itemCount = itemRange.getCount();
      chart.setItemWindow(itemOffset + itemCount, chart.getItemWindow().getCount());
   }

   void tabSelected(int tabId)
   {
      switch (tabId)
      {
         case TAB_FOLDER_CHART:
            statsPanel.updateSelections();
            break;
         case TAB_FOLDER_TABLE:
            updateRelativeTableButtonSelection();
            break;
         default:
            break;
      }
   }

   @Override
   void setRelative(boolean relative)
   {
      CartesianChart chart;

      super.setRelative(relative);
      chart = (CartesianChart) chartViewer.getChart();
      chart.setMaxValue(getContentProvider().getMax());
   }

   @Override
   void setRealTime(boolean realTime)
   {
      super.setRealTime(realTime);
      if (contentRangeTransformer != null)
      {
         contentRangeTransformer.setRealTime(realTime);
      }
   }

   private class ChartWindowHandler implements ChartWindowListener
   {
      public void itemWindowChanged(Chart chart)
      {
         if (contentRangeTransformer != null)
         {
            int index = chart.getItemWindow().getOffset();
            long time = contentRangeTransformer.getTimeOfReport(index);
            notifyScrolledTo(time);
         }
      }

      public void seriesWindowChanged(Chart chart) {}

      public void layerWindowChanged(Chart chart) {}
   }
}
