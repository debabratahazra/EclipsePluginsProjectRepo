/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SearchPattern;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;
import com.ose.chart.ui.CartesianChart;
import com.ose.chart.ui.CartesianChart3D;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartCanvas;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ChartSelectionListener;
import com.ose.chart.ui.ChartUpdateListener;
import com.ose.chart.ui.ChartViewer;
import com.ose.chart.ui.ChartWindowListener;
import com.ose.chart.ui.PieChart2D;
import com.ose.chart.ui.PolarChart;
import com.ose.chart.ui.StackedBarChart2D;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.TargetReport;
import com.ose.system.ui.util.FileDialogAdapter;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

abstract class ProfilerEditor3D extends ProfilerEditor
{
   static final int NUM_REPORTS = 50;

   static final int TAB_FOLDER_CHART_PIE = 1;
   static final int TAB_FOLDER_CHART_2D = 2;
   static final int TAB_FOLDER_CHART_3D = 3;
   
   private ControlPanel controlPanel3D;
   private ChartViewer chartViewer3D;
   private StatisticsPanel statsPanel3D;
   private ProfilerContentRangeTransformer contentRangeTransformer3D;

   private ControlPanel controlPanel2D;
   private ChartViewer chartViewer2D;
   private StatisticsPanel statsPanel2D;
   private ProfilerContentRangeTransformer contentRangeTransformer2D;
   
   private ControlPanel controlPanelPie;
   private ChartViewer chartViewerPie;
   private SelectionRangePanel rangePanelPie;
   private ProfilerContentRangeTransformer contentRangeTransformerPie;

   private final TabStateTracker tabState = new TabStateTracker();
   /* The content filter is shared between all the charts. */
   private ProfilerContentFilter contentFilter;

   private final Image zoomOutImage;
   private final Image zoomInImage;

   ProfilerEditor3D()
   {
      String pluginId = ProfilerPlugin.getUniqueIdentifier();
      ImageDescriptor zoomOutImageDesc = ProfilerPlugin.imageDescriptorFromPlugin(
            pluginId, "icons/elcl16/zoom_out.gif");
      ImageDescriptor zoomInImageDesc = ProfilerPlugin.imageDescriptorFromPlugin(
            pluginId, "icons/elcl16/zoom_in.gif");
      zoomOutImage = zoomOutImageDesc.createImage();
      zoomInImage = zoomInImageDesc.createImage();
   }

   @Override
   public void dispose()
   {
      ChartColorProvider colorProvider = chartViewer2D.getChart().getColorProvider();
      if (colorProvider instanceof MutableColorProvider)
      {
         ((MutableColorProvider) colorProvider).dispose();
      }
      zoomOutImage.dispose();
      zoomInImage.dispose();
      super.dispose();
   }

   @Override
   public void setFocus()
   {
      super.setFocus();

      switch (getTabId(getTabFolder().getSelection()))
      {
         case TAB_FOLDER_CHART_3D:
            chartViewer3D.setFocus();
            break;
         case TAB_FOLDER_CHART_2D:
            chartViewer2D.setFocus();
            break;
         case TAB_FOLDER_CHART_PIE:
            chartViewerPie.setFocus();
         default:
            break;
      }
   }

   @Override
   public void setOfflineFeaturesEnabled(boolean enabled)
   {
      super.setOfflineFeaturesEnabled(enabled);

      statsPanel3D.setStatisticsEnabled(enabled);
      statsPanel2D.setStatisticsEnabled(enabled);

      if (enabled)
      {
         chartViewer3D.enableItemSelector();
         chartViewer2D.enableItemSelector();
         if (hasChartPie())
         {
            chartViewerPie.enableItemSelector();
         }
      }
      else
      {
         chartViewer3D.disableItemSelector();
         chartViewer2D.disableItemSelector();
         if (hasChartPie())
         {
            chartViewerPie.disableItemSelector();
         }
      }

      if (contentFilter == null)
      {
         contentFilter = new ProfilerContentFilter();
         chartViewer3D.getChart().addContentTransformer(contentFilter);
         chartViewer2D.getChart().addContentTransformer(contentFilter);
         if (hasChartPie())
         {
            chartViewerPie.getChart().addContentTransformer(contentFilter);
         }
      }
      contentFilter.setEnabled(enabled);

      controlPanel3D.setEnabled(enabled);
      controlPanel2D.setEnabled(enabled);
      if (hasChartPie())
      {
         controlPanelPie.setEnabled(enabled);
      }
   }

   @Override
   public void clearReports()
   {
      ProfilerContentProvider contentProvider;

      super.clearReports();

      contentProvider = getContentProvider();
      contentProvider.setMax(contentProvider.isRelative() ? 100 : 10);
      contentProvider.setMin(0);
      contentProvider.setTotalMax(contentProvider.getMax());
      contentProvider.setTotalMin(contentProvider.getMin());

      resetChart((CartesianChart) chartViewer3D.getChart());
      resetChart((CartesianChart) chartViewer2D.getChart());
      if (hasChartPie())
      {
         resetChart((PolarChart) chartViewerPie.getChart());
      }
   }

   @Override
   public void addReports(TargetReport[] reports)
   {
      CartesianChart chart3D;
      CartesianChart chart2D;
      PolarChart chartPie = null;

      super.addReports(reports);
      chart3D = (CartesianChart) chartViewer3D.getChart();
      chart2D = (CartesianChart) chartViewer2D.getChart();
      chart3D.beginChanges();
      chart2D.beginChanges();
      if (hasChartPie())
      {
         chartPie = (PolarChart) chartViewerPie.getChart();
         chartPie.beginChanges();
      }
      getContentProvider().addReports(reports);
      updateHeaderLabels();

      updateChartMinMax(chart3D);
      updateChartSelection(chart3D);
      updateChartWindow(chart3D);
      statsPanel3D.setStatistics(calculateStatistics(chart3D));
      chart3D.endChanges(false);

      updateChartMinMax(chart2D);
      updateChartSelection(chart2D);
      updateChartWindow(chart2D);
      statsPanel2D.setStatistics(calculateStatistics(chart2D));
      chart2D.endChanges(false);

      if (hasChartPie())
      {
         chartPie.endChanges(false);
      }
   }

   void addContentRangeTransformer()
   {
      if (contentRangeTransformer3D == null)
      {
         contentRangeTransformer3D = new ProfilerContentRangeTransformer(
               getContentProvider());
         chartViewer3D.getChart().addContentTransformer(contentRangeTransformer3D);
      }

      if (contentRangeTransformer2D == null)
      {
         contentRangeTransformer2D = new ProfilerContentRangeTransformer(
               getContentProvider());
         chartViewer2D.getChart().addContentTransformer(contentRangeTransformer2D);
      }

      if (hasChartPie() && (contentRangeTransformerPie == null))
      {
         contentRangeTransformerPie = new ProfilerContentRangeTransformer(
               getContentProvider());
         chartViewerPie.getChart().addContentTransformer(contentRangeTransformerPie);
      }
   }

   void removeContentRangeTransformer()
   {
      chartViewer3D.getChart().removeContentTransformer(contentRangeTransformer3D);
      contentRangeTransformer3D = null;

      chartViewer2D.getChart().removeContentTransformer(contentRangeTransformer2D);
      contentRangeTransformer2D = null;

      if (hasChartPie())
      {
         chartViewerPie.getChart().removeContentTransformer(contentRangeTransformerPie);
         contentRangeTransformerPie = null;
      }
   }

   void updateContentRangeTransformer(int offset, int length, long integrationInterval)
   {
      if (contentRangeTransformer3D != null)
      {
         contentRangeTransformer3D.setEnabled(false);
         contentRangeTransformer3D.setup(offset, length, integrationInterval);
         contentRangeTransformer3D.setEnabled(true);
      }

      if (contentRangeTransformer2D != null)
      {
         contentRangeTransformer2D.setEnabled(false);
         contentRangeTransformer2D.setup(offset, length, integrationInterval);
         contentRangeTransformer2D.setEnabled(true);
      }
      
      if (contentRangeTransformerPie != null)
      {
         contentRangeTransformerPie.setEnabled(false);
         contentRangeTransformerPie.setup(offset, length, integrationInterval);
         contentRangeTransformerPie.setEnabled(true);
      }
   }

   void clearChartItemSelection()
   {
      chartViewer3D.getChart().clearItemSelection();
      chartViewer2D.getChart().clearItemSelection();
      if (hasChartPie())
      {
         chartViewerPie.getChart().clearItemSelection();
      }
   }

   public void scrollTo(long time)
   {
      ProfilerContentRangeTransformer contentRangeTransformer;
      Chart chart;

      switch (tabState.getLastSelectedChartTab())
      {
         case TAB_FOLDER_CHART_3D:
            contentRangeTransformer = contentRangeTransformer3D;
            chart = chartViewer3D.getChart();
            break;
         case TAB_FOLDER_CHART_2D:
            contentRangeTransformer = contentRangeTransformer2D;
            chart = chartViewer2D.getChart();
            break;
         default:
            contentRangeTransformer = null;
            chart = null;
            break;
      }

      if (contentRangeTransformer != null)
      {
         int index = contentRangeTransformer.getIndexOfClosestReport(time);

         if (index >= 0)
         {
            chart.disableWindowNotifications();
            chart.setItemWindow(index, chart.getItemWindow().getCount());
            chart.enableWindowNotifications();
         }
      }
   }

   abstract int getNumVisibleEntities();

   ProfilerContentSorter createContentSorter(Chart chart)
   {
      return new ProfilerContentSorter(chart);
   }

   void createChartTabs(CTabFolder tabFolder)
   {
      createChartTab3D(tabFolder);
      createChartTab2D(tabFolder);
      if (hasChartPie())
      {
         createChartTabPie(tabFolder);
      }
   }

   private void createChartTab3D(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;
      CTabItem tabItem;

      comp = getToolkit().createComposite(tabFolder);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.marginBottom = 5;
      comp.setLayout(layout);

      sashForm = new SashForm(comp, SWT.HORIZONTAL);
      getToolkit().adapt(sashForm, false, false);
      sashForm.setBackground(getToolkit().getColors().getColor(IFormColors.TB_BG));
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      controlPanel3D = new ControlPanel(sashForm, SWT.NONE);
      chartViewer3D = createChartViewer3D(sashForm);
      controlPanel3D.init(chartViewer3D, true, true, true, false, false);
      sashForm.setWeights(new int[] {20, 80});
      statsPanel3D = new StatisticsPanel(comp, SWT.NONE);
      statsPanel3D.init(chartViewer3D);
      chartViewer3D.getChart().addChartSelectionListener(new StatisticsChartSelectionHandler(statsPanel3D));
      chartViewer3D.getChart().addChartWindowListener(new ChartWindowHandler());

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("3D Chart");
      tabItem.setControl(comp);
      assignTabId(tabItem, TAB_FOLDER_CHART_3D);
   }

   private void createChartTab2D(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;
      CTabItem tabItem;

      comp = getToolkit().createComposite(tabFolder);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.marginBottom = 5;
      comp.setLayout(layout);

      sashForm = new SashForm(comp, SWT.HORIZONTAL);
      getToolkit().adapt(sashForm, false, false);
      sashForm.setBackground(getToolkit().getColors().getColor(IFormColors.TB_BG));
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      controlPanel2D = new ControlPanel(sashForm, SWT.NONE);
      chartViewer2D = createChartViewer2D(sashForm);
      controlPanel2D.init(chartViewer2D, false, false, true, true, false);
      sashForm.setWeights(new int[] {20, 80});
      statsPanel2D = new StatisticsPanel(comp, SWT.NONE);
      statsPanel2D.init(chartViewer2D);
      chartViewer2D.getChart().addChartSelectionListener(new StatisticsChartSelectionHandler(statsPanel2D));
      chartViewer2D.getChart().addChartWindowListener(new ChartWindowHandler());

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("2D Chart");
      tabItem.setControl(comp);
      assignTabId(tabItem, TAB_FOLDER_CHART_2D);
   }

   private void createChartTabPie(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;
      CTabItem tabItem;
      ChartCanvas canvas;

      comp = getToolkit().createComposite(tabFolder);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.marginBottom = 5;
      comp.setLayout(layout);

      sashForm = new SashForm(comp, SWT.HORIZONTAL);
      getToolkit().adapt(sashForm, false, false);
      sashForm.setBackground(getToolkit().getColors().getColor(IFormColors.TB_BG));
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      controlPanelPie = new ControlPanel(sashForm, SWT.NONE);
      chartViewerPie = createChartViewerPie(sashForm);
      controlPanelPie.init(chartViewerPie, false, false, true, true, true);
      sashForm.setWeights(new int[] {40, 60});
      rangePanelPie = new SelectionRangePanel(comp, SWT.NONE);
      rangePanelPie.init();
      chartViewerPie.getChart().addChartSelectionListener(new RangeChartSelectionHandler(rangePanelPie));
      chartViewerPie.getChart().addChartSelectionListener(new PieChartSelectionHandler());

      canvas = chartViewerPie.getCanvas();
      canvas.addMouseListener(new PieChartCanvasMouseHandler(chartViewerPie.getChart()));
      
      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("Pie Chart");
      tabItem.setControl(comp);
      assignTabId(tabItem, TAB_FOLDER_CHART_PIE);
   }

   abstract ChartViewer createChartViewer3D(Composite parent);

   abstract ChartViewer createChartViewer2D(Composite parent);

   boolean hasChartPie()
   {
      return false;
   }

   ChartViewer createChartViewerPie(Composite parent)
   {
      return null;
   }

   void resetChart(CartesianChart chart)
   {
      if (chart instanceof StackedBarChart2D)
      {
         chart.setMaxValue(getContentProvider().getTotalMax());
         chart.setMinValue(getContentProvider().getTotalMin());
      }
      else
      {
         chart.setMaxValue(getContentProvider().getMax());
         chart.setMinValue(getContentProvider().getMin());
      }
      chart.clearSelections();

      if (chart instanceof CartesianChart3D)
      {
         ((CartesianChart3D) chart).setBackdropPosition(0);
      }
   }
   
   void resetChart(PolarChart chart)
   {
      chart.clearSelections();
   }

   void updateChartMinMax(CartesianChart chart)
   {
      if (!getContentProvider().isRelative())
      {
         if (chart instanceof StackedBarChart2D)
         {
            chart.setMaxValue(getContentProvider().getTotalMax());
            chart.setMinValue(getContentProvider().getTotalMin());
         }
         else
         {
            chart.setMaxValue(getContentProvider().getMax());
            chart.setMinValue(getContentProvider().getMin());
         }
      }
   }

   void updateChartSelection(CartesianChart chart)
   {
      if (chart instanceof CartesianChart3D)
      {
         int value = ((CartesianChart3D) chart).getBackdropPosition();
         controlPanel3D.setGaugeSelection(value);
         Range window = chart.getSeriesWindow();
         int seriesCount = chart.getContentProvider().getSeriesRange().getCount();
         int selectedSeries = window.getOffset() + window.getCount() - value - 1;
         chart.setSeriesSelection(selectedSeries, 1);

         // Clear statistics if backdrop is positioned beyond the series range.
         if (!isSelectionValidForStatistics(chart))
         {
            statsPanel3D.clearStatistics();
         }
         else if (statsPanel3D.isStatisticsCleared() &&
                  (window.getCount() - value == seriesCount))
         {
            statsPanel3D.setStatistics(calculateStatistics(chart));
         }
      }
   }

   void updateChartWindow(CartesianChart chart)
   {
      Range itemRange = chart.getContentProvider().getItemRange();
      int itemOffset = itemRange.getOffset();
      int itemCount = itemRange.getCount();
      chart.setItemWindow(itemOffset + itemCount, chart.getItemWindow().getCount());
   }

   void tabSelected(int tabId)
   {
      // Collect state from last selected tab
      switch (tabState.getLastSelectedTab())
      {
         case TAB_FOLDER_CHART_3D:
            {
               Chart chart = chartViewer3D.getChart();
               tabState.setItemWindow(chart.getItemWindow());
               tabState.setItemSelection(chart.getItemSelection());
               tabState.setRelative(getContentProvider().isRelative());
            }
            break;
         case TAB_FOLDER_CHART_2D:
            {
               Chart chart = chartViewer2D.getChart();
               tabState.setItemWindow(chart.getItemWindow());
               tabState.setItemSelection(chart.getItemSelection());
               tabState.setRelative(getContentProvider().isRelative());
            }
            break;
         case TAB_FOLDER_CHART_PIE:
            {
               Chart chart = chartViewerPie.getChart();
               tabState.setItemSelection(chart.getItemSelection());
            }
            break;
         case TAB_FOLDER_TABLE:
            {
               tabState.setRelative(getContentProvider().isRelative());
            }
            break;
         default:
            break;
      }
      
      // Apply state for selected tab
      switch (tabId)
      {
         case TAB_FOLDER_CHART_3D:
            if (isOfflineFeaturesEnabled())
            {
               controlPanel3D.updateFilter();
            }
            if (tabState.isRelative() != getContentProvider().isRelative())
            {
               setRelative(tabState.isRelative());
            }
            statsPanel3D.updateSelections();
            if (isOfflineFeaturesEnabled())
            {
               Chart chart3D = chartViewer3D.getChart();
               chart3D.setItemWindow(tabState.getItemWindow().getOffset(),
                                     chart3D.getItemWindow().getCount());               
               chart3D.setItemSelection(tabState.getItemSelection());
            }
            break;
         case TAB_FOLDER_CHART_2D:
            if (isOfflineFeaturesEnabled())
            {
               controlPanel2D.updateFilter();
            }
            if (tabState.isRelative() != getContentProvider().isRelative())
            {
               setRelative(tabState.isRelative());
            }
            statsPanel2D.updateSelections();
            if (isOfflineFeaturesEnabled())
            {
               Chart chart2D = chartViewer2D.getChart();
               chart2D.setItemWindow(tabState.getItemWindow().getOffset(),
                                     chart2D.getItemWindow().getCount());
               chart2D.setItemSelection(tabState.getItemSelection());
            }
            break;
         case TAB_FOLDER_CHART_PIE:
            if (isOfflineFeaturesEnabled())
            {
               controlPanelPie.updateFilter();
            }
            if (!getContentProvider().isRelative())
            {
               setRelative(true);
            }
            rangePanelPie.updateSelections();
            if (isOfflineFeaturesEnabled())
            {
               Chart chartPie = chartViewerPie.getChart();
               chartPie.setItemSelection(tabState.getItemSelection());
            }
            break;
         case TAB_FOLDER_TABLE:
            if (tabState.isRelative() != getContentProvider().isRelative())
            {
               setRelative(tabState.isRelative());
            }
            updateRelativeTableButtonSelection();
            break;
         default:
            break;
      }
      
      tabState.setLastSelectedTab(tabId);
   }

   @Override
   void setRelative(boolean relative)
   {
      CartesianChart chart;

      super.setRelative(relative);

      chart = (CartesianChart) chartViewer3D.getChart();
      chart.setMaxValue(getContentProvider().getMax());

      chart = (CartesianChart) chartViewer2D.getChart();
      chart.setMaxValue(getContentProvider().getTotalMax());
   }

   @Override
   void setRealTime(boolean realTime)
   {
      super.setRealTime(realTime);

      if (contentRangeTransformer3D != null)
      {
         contentRangeTransformer3D.setRealTime(realTime);
      }

      if (contentRangeTransformer2D != null)
      {
         contentRangeTransformer2D.setRealTime(realTime);
      }

      if (contentRangeTransformerPie != null)
      {
         contentRangeTransformerPie.setRealTime(realTime);
      }
   }

   @Override
   boolean isSelectionValidForStatistics(Chart chart)
   {
      if (chart instanceof CartesianChart3D)
      {
         CartesianChart3D chart3D = (CartesianChart3D) chart;
         return chart3D.getSeriesWindow().getCount() - chart3D.getBackdropPosition()
             <= chart3D.getContentProvider().getSeriesRange().getCount();
      }
      else
      {
         return true;
      }
   }

   private static class TabStateTracker
   {
      private int lastSelectedTab = TAB_FOLDER_CHART_3D;
      private int lastSelectedChartTab = TAB_FOLDER_CHART_3D;
      private Range itemWindow;
      private Range itemSelection;
      private boolean relativeValues;

      int getLastSelectedTab()
      {
         return lastSelectedTab;
      }

      int getLastSelectedChartTab()
      {
         return lastSelectedChartTab;
      }

      void setLastSelectedTab(int tab)
      {
         if (tab != TAB_FOLDER_TABLE)
         {
            lastSelectedChartTab = tab;
         }
         lastSelectedTab = tab;
      }

      Range getItemWindow()
      {
         return itemWindow;
      }

      void setItemWindow(Range itemWindow)
      {
         this.itemWindow = itemWindow;
      }

      Range getItemSelection()
      {
         return itemSelection;
      }

      void setItemSelection(Range itemSelection)
      {
         this.itemSelection = itemSelection;
      }

      boolean isRelative()
      {
         return relativeValues;
      }

      void setRelative(boolean relative)
      {
         relativeValues = relative;
      }
   }

   class ChartUpdateHandler implements ChartUpdateListener
   {
      public void chartUpdated(Chart chart)
      {
         updateChartSelection((CartesianChart) chart);
      }
   }

   private class ChartWindowHandler implements ChartWindowListener
   {
      public void itemWindowChanged(Chart chart)
      {
         if (chartViewer3D.getChart() == chart)
         {
            if (contentRangeTransformer3D != null)
            {
               int index = chart.getItemWindow().getOffset();
               long time = contentRangeTransformer3D.getTimeOfReport(index);
               notifyScrolledTo(time);
            }
         }
         else if (chartViewer2D.getChart() == chart)
         {
            if (contentRangeTransformer2D != null)
            {
               int index = chart.getItemWindow().getOffset();
               long time = contentRangeTransformer2D.getTimeOfReport(index);
               notifyScrolledTo(time);
            }
         }
      }

      public void seriesWindowChanged(Chart chart) {}

      public void layerWindowChanged(Chart chart) {}
   }

   /* Listen to pie chart item selection events and update the entity table
    * appropriately. Whenever the range of selected reports changes, the pie
    * chart gets updated and in the process we also update the entity table
    * to reflect the new entity load values.
    */
   private class PieChartSelectionHandler implements ChartSelectionListener
   {
      public void itemSelectionChanged(Chart chart)
      {
         if (controlPanelPie != null)
         {
            controlPanelPie.updateLoad(chart);
         }
      }

      public void seriesSelectionChanged(Chart chart)
      {
         // Do nothing
      }

      public void layerSelectionChanged(Chart chart)
      {
         // Do nothing
      }
   }

   private class PieChartCanvasMouseHandler extends MouseAdapter
   {
      private final Chart chart; 

      PieChartCanvasMouseHandler(Chart chart)
      {
         this.chart = chart;
      }

      public void mouseDown(MouseEvent e)
      {
         PieChart2D pieChart = (PieChart2D) chart;
         int series = pieChart.getSeriesAt(e.x, e.y);
         if (series >= 0)
         {
            controlPanelPie.revealEntity(series);
         }
      }
   }

   private static class ProfiledEntityGroup
   {
      private final String name;
      private final List<ProfiledEntity> entities;

      ProfiledEntityGroup(String name)
      {
         this.name = name;
         entities = new ArrayList<ProfiledEntity>();
      }

      public String getName()
      {
         return name;
      }

      public double getLoad()
      {
         double load = 0;
         for (ProfiledEntity entity : entities)
         {
            load += entity.getLoad();
         }
         return load;
      }

      public void addEntity(ProfiledEntity entity)
      {
         if (entity != null)
         {
            entities.add(entity);
         }
      }
   }

   private static class ProfiledEntity
   {
      private final int index;
      private final String name;
      private boolean enabled;
      private double load;
      private ProfiledEntityGroup group;

      ProfiledEntity(int index, String name)
      {
         this.index = index;
         this.name = name;
      }

      public int getIndex()
      {
         return index;
      }

      public String getName()
      {
         return name;
      }

      public boolean isEnabled()
      {
         return enabled;
      }

      public void setEnabled(boolean enabled)
      {
         this.enabled = enabled;
      }

      public double getLoad()
      {
         return load;
      }

      public void setLoad(Chart chart)
      {
         ChartContentProvider chartContentProvider =
            chart.getSourceContentProvider();
         Range itemRange = chart.getItemSelection();
         int itemOffset = itemRange.getOffset();
         int itemCount = itemRange.getCount();
         double sum = 0;

         for (int i = 0; i < itemCount; i++)
         {
            double val = chartContentProvider.getValue(0, index, i + itemOffset);
            if (!Double.isNaN(val))
            {
               sum += val;
            }
         }

         this.load = (itemCount > 0) ? (sum / itemCount) : 0;
      }

      public String getGroupName()
      {
         return (group != null) ? group.getName() : "";
      }

      public double getGroupLoad()
      {
         return (group != null) ? group.getLoad() : 0;
      }

      public void setGroup(ProfiledEntityGroup group)
      {
         if (group != null)
         {
            this.group = group;
            this.group.addEntity(this);
         }
      }
   }

   private static class ProfiledEntityContentProvider
      implements IStructuredContentProvider
   {
      private final List entities = new ArrayList();

      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
         entities.clear();
         if (newInput instanceof ProfilerContentProvider)
         {
            ProfilerContentProvider profilerContentProvider;
            Range seriesRange;

            profilerContentProvider = (ProfilerContentProvider) newInput;
            seriesRange = profilerContentProvider.getSeriesRange();
            for (int i = seriesRange.getOffset(); i < seriesRange.getCount(); i++)
            {
               String name = profilerContentProvider.getSeriesLabel(0, i);
               entities.add(new ProfiledEntity(i, name));
            }
         }
      }

      public void dispose()
      {
         entities.clear();
      }

      public Object[] getElements(Object parent)
      {
         return (ProfiledEntity[]) entities.toArray(new ProfiledEntity[0]);
      }
      
      public ProfiledEntity getEntity(int index)
      {
         return (ProfiledEntity) entities.get(index);
      }      
   }

   private static class ProfiledEntityLabelProvider
      extends LabelProvider implements ITableLabelProvider
   {
      private final ProfilerContentProvider contentProvider;

      ProfiledEntityLabelProvider(ProfilerContentProvider contentProvider)
      {
         this.contentProvider = contentProvider;
      }

      public String getColumnText(Object obj, int index)
      {
         if (obj instanceof ProfiledEntity)
         {
            ProfiledEntity entity = (ProfiledEntity) obj;
            switch (index)
            {
               case 0:
                  return null;
               case 1:
                  return null;
               case 2:
                  return entity.getName();
               case 3:
                  return toPercentString(entity.getLoad());
               case 4:
                  return entity.getGroupName();
               case 5:
                  return toPercentString(entity.getGroupLoad());
               default:
                  return null;
            }
         }
         else
         {
            return null;
         }
      }

      public Image getColumnImage(Object obj, int index)
      {
         return null;
      }

      private String toPercentString(double load)
      {
         String value = "";
         if (contentProvider != null)
         {
            value = contentProvider.getValueLabel(load);
         }
         if (value.equals(""))
         {
            value = "0%";
         }
         else
         {
            value += "%";
         }
         return value;
      }
   }

   private static class ProfiledEntityFilter extends ViewerFilter
   {
      private final SearchPattern patternMatcher;

      ProfiledEntityFilter()
      {
         patternMatcher = new SearchPattern();
         patternMatcher.setPattern("");
      }

      public void setPattern(String filter)
      {
         patternMatcher.setPattern(filter);
      }

      public boolean select(Viewer viewer, Object parent, Object element)
      {
         String name = ((ProfiledEntity) element).getName();
         return patternMatcher.matches(name);
      }
   }

   private static class ProfiledEntitySorter extends ViewerSorter
   {
      private static final int ASCENDING = 1;
      private static final int DESCENDING = -1;

      private int column;
      private int direction;

      ProfiledEntitySorter()
      {
         super();
         reset();
      }

      public void reset()
      {
         column = 1;
         direction = ASCENDING;
      }

      public void sortByColumn(int column)
      {
         if (this.column == column)
         {
            // Same column, toggle sort direction.
            direction *= DESCENDING;
         }
         else
         {
            // New column, reset sort direction.
            this.column = column;
            direction = ASCENDING;
         }
      }

      @Override
      public int compare(Viewer viewer, Object e1, Object e2)
      {
         if (!(e1 instanceof ProfiledEntity) && !(e2 instanceof ProfiledEntity))
         {
            return 0;
         }

         ProfiledEntity p1 = (ProfiledEntity) e1;
         ProfiledEntity p2 = (ProfiledEntity) e2;

         switch (column)
         {
            case 0:
               return compareBooleans(p1.isEnabled(), p2.isEnabled());
            case 1:
               return compareInts(p1.getIndex(), p2.getIndex());
            case 2:
               return compareStrings(p1.getName(), p2.getName());
            case 3:
               return compareDoubles(p1.getLoad(), p2.getLoad());
            case 4:
               return compareStrings(p1.getGroupName(), p2.getGroupName());
            case 5:
               return compareDoubles(p1.getGroupLoad(), p2.getGroupLoad());
            default:
               return 0;
         }
      }

      private int compareStrings(String s1, String s2)
      {
         int result = s1.compareTo(s2);
         if (direction == DESCENDING)
         {
            result *= DESCENDING;
         }
         return result;
      }

      private int compareDoubles(double d1, double d2)
      {
         int result = (d1 < d2) ? -1 : ((d1 > d2) ? 1 : 0);
         if (direction == DESCENDING)
         {
            result *= DESCENDING;
         }
         return result;
      }

      private int compareInts(int i1, int i2)
      {
         int result = (i1 < i2) ? -1 : ((i1 > i2) ? 1 : 0);
         if (direction == DESCENDING)
         {
            result *= DESCENDING;
         }
         return result;
      }

      private int compareBooleans(boolean b1, boolean b2)
      {
         int result = (!b1 && b2) ? -1 : ((b1 && !b2) ? 1 : 0);
         if (direction == DESCENDING)
         {
            result *= DESCENDING;
         }
         return result;
      }
   }

   private class ControlPanel extends Composite
   {
      private static final float ZOOM_STEP = 10.0f;

      private static final String SORT_ORIGINAL_ORDER = "Original order";
      private static final String SORT_ASCENDING_MEAN_ORDER = "Ascending mean order";
      private static final String SORT_DESCENDING_MEAN_ORDER = "Descending mean order";

      private static final String GROUP_LABEL = "Group...";
      private static final String UNGROUP_LABEL = "Ungroup";
      private static final String GROUP_FILE_LABEL = "Group File: ";
      private static final String NO_GROUP_FILE_LABEL = "None";
      private static final String OTHERS_GROUP = "Others";

      private boolean hasNavigation;
      private boolean hasSorter;
      private boolean hasFilter;
      private boolean useFilterColor;
      private boolean entityGroupingAllowed;

      private ChartViewer chartViewer;
      private Slider gaugeSlider;
      private Combo sortCombo;
      private ProfilerContentSorter contentSorter;
      private Section filterSection;
      private Button groupButton;
      private Text entityFilterText;
      private CheckboxTableViewer entityViewer;
      private ProfiledEntityContentProvider entityContentProvider;
      private ProfiledEntityFilter entityFilter;
      private ProfiledEntitySorter entitySorter;
      private Button selectAllButton;
      private Button deselectAllButton;

      ControlPanel(Composite parent, int style)
      {
         super(parent, style);
         getToolkit().adapt(this);
         setLayout(new GridLayout(1, false));
      }

      public void init(ChartViewer chartViewer,
                       boolean hasNavigation,
                       boolean hasSorter,
                       boolean hasFilter,
                       boolean useFilterColor,
                       boolean entityGroupingAllowed)
      {
         this.chartViewer = chartViewer;
         this.hasNavigation = hasNavigation;
         this.hasSorter = hasSorter;
         this.hasFilter = hasFilter;
         this.useFilterColor = useFilterColor;
         this.entityGroupingAllowed = entityGroupingAllowed;

         if (hasNavigation)
         {
            createNavigationControl();
         }

         if (hasSorter)
         {
            createSorterControl();
         }

         if (hasFilter)
         {
            if (entityGroupingAllowed)
            {
               createEntityGroupingControl();
            }
            createFilterControl();
         }
      }

      private void createNavigationControl()
      {
         FormToolkit toolkit;
         Section section;
         Composite client;
         GridLayout gridLayout;
         GridData gd;
         Label label;
         Button button;
         Composite zoomComp;

         toolkit = getToolkit();
         section = toolkit.createSection(this,
               Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
         section.setText("Navigation");
         section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

         client = toolkit.createComposite(section);
         gridLayout = new GridLayout(2, false);
         gridLayout.marginWidth = 0;
         client.setLayout(gridLayout);
         client.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         toolkit.paintBordersFor(client);
         section.setClient(client);

         label = toolkit.createLabel(client, "Zoom:");
         label.setLayoutData(new GridData());

         label = toolkit.createLabel(client, "Gauge:");
         label.setLayoutData(new GridData());

         zoomComp = toolkit.createComposite(client);
         gridLayout = new GridLayout(2, false);
         gridLayout.marginWidth = 0;
         gridLayout.marginHeight = 0;
         gridLayout.marginRight = 10;
         zoomComp.setLayout(gridLayout);

         button = toolkit.createButton(zoomComp, null, SWT.PUSH);
         button.setImage(zoomOutImage);
         button.addSelectionListener(new ZoomOutButtonSelectionHandler());

         button = toolkit.createButton(zoomComp, null, SWT.PUSH);
         button.setImage(zoomInImage);
         button.addSelectionListener(new ZoomInButtonSelectionHandler());

         gaugeSlider = new Slider(client, SWT.HORIZONTAL);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.widthHint = 0;
         gaugeSlider.setLayoutData(gd);
         gaugeSlider.setIncrement(1);
         gaugeSlider.setPageIncrement(1);
         gaugeSlider.setMinimum(0);
         gaugeSlider.setMaximum(getNumVisibleEntities());
         gaugeSlider.setThumb(1);
         gaugeSlider.setSelection(0);
         gaugeSlider.addSelectionListener(new GaugeSliderSelectionHandler());
         gaugeSlider.addFocusListener(new ChartFocusHandler());

         button = toolkit.createButton(client, "Reset", SWT.PUSH);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         button.setLayoutData(gd);
         button.addSelectionListener(new ResetCameraButtonSelectionHandler());
      }

      private void createSorterControl()
      {
         FormToolkit toolkit;
         Section section;
         Composite client;
         GridLayout gridLayout;

         toolkit = getToolkit();
         section = toolkit.createSection(this,
               Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
         section.setText("Sort");
         section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

         client = toolkit.createComposite(section);
         gridLayout = new GridLayout(1, false);
         gridLayout.marginWidth = 0;
         client.setLayout(gridLayout);
         client.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         toolkit.paintBordersFor(client);
         section.setClient(client);

         sortCombo = new Combo(client, SWT.READ_ONLY);
         toolkit.adapt(sortCombo);
         sortCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         sortCombo.add(SORT_ORIGINAL_ORDER);
         sortCombo.add(SORT_ASCENDING_MEAN_ORDER);
         sortCombo.add(SORT_DESCENDING_MEAN_ORDER);
         sortCombo.setVisibleItemCount(sortCombo.getItemCount());
         sortCombo.addSelectionListener(new SortSelectionHandler());
         sortCombo.select(0);
      }

      private void createEntityGroupingControl()
      {
         FormToolkit toolkit;
         Section section;
         Composite client;
         GridLayout gridLayout;
         GridData gd;
         Label label;

         toolkit = getToolkit();
         section = toolkit.createSection(this,
               Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
         section.setText("Grouping");
         section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

         client = toolkit.createComposite(section);
         gridLayout = new GridLayout(2, false);
         gridLayout.marginWidth = 0;
         client.setLayout(gridLayout);
         client.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         toolkit.paintBordersFor(client);
         section.setClient(client);

         groupButton = toolkit.createButton(client, GROUP_LABEL, SWT.TOGGLE);
         gd = new GridData();
         gd.widthHint = 100;
         groupButton.setLayoutData(gd);

         label = toolkit.createLabel(client, GROUP_FILE_LABEL + NO_GROUP_FILE_LABEL);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         groupButton.setData(label);
         groupButton.addSelectionListener(new GroupButtonSelectionHandler());
      }

      private void createFilterControl()
      {
         FormToolkit toolkit;

         toolkit = getToolkit();
         filterSection = toolkit.createSection(this,
               Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
         filterSection.setText("Filter");
         filterSection.setLayoutData(new GridData(GridData.FILL_BOTH));

         createFilterTable();
      }

      private void createFilterTable()
      {
         FormToolkit toolkit;
         Composite client;
         GridLayout gridLayout;
         GridData gd;
         Composite tableComp;
         TableColumnLayout tableLayout;
         Table table;
         TableColumn column;
         Composite selectButtonsComp;

         toolkit = getToolkit();
         client = toolkit.createComposite(filterSection);
         gridLayout = new GridLayout(1, false);
         gridLayout.marginWidth = 0;
         client.setLayout(gridLayout);
         client.setLayoutData(new GridData(GridData.FILL_BOTH));
         toolkit.paintBordersFor(client);
         filterSection.setClient(client);

         entityFilterText = toolkit.createText(client, null);
         entityFilterText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         entityFilterText.addModifyListener(new ProfiledEntityFilterHandler());

         tableComp = toolkit.createComposite(client);
         gd = new GridData(GridData.FILL_BOTH);
         gd.widthHint = 150;
         gd.heightHint = 200;
         tableComp.setLayoutData(gd);
         tableLayout = new TableColumnLayout();
         tableComp.setLayout(tableLayout);
         table = toolkit.createTable(tableComp, SWT.CHECK | SWT.BORDER |
               SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
         table.setHeaderVisible(true);

         column = new TableColumn(table, SWT.LEFT);
         column.setMoveable(true);
         column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(0));
         tableLayout.setColumnData(column, new ColumnWeightData(0, 20));

         column = new TableColumn(table, SWT.CENTER);
         column.setMoveable(true);
         column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(1));
         if (useFilterColor)
         {
            tableLayout.setColumnData(column, new ColumnWeightData(0, 15));
         }
         else
         {
            tableLayout.setColumnData(column, new ColumnWeightData(0, 0));
         }

         column = new TableColumn(table, SWT.LEFT);
         column.setText("Name");
         column.setMoveable(true);
         column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(2));
         tableLayout.setColumnData(column, new ColumnWeightData(3, 10));

         if (entityGroupingAllowed)
         {
            column = new TableColumn(table, SWT.LEFT);
            column.setText("Load");
            column.setMoveable(true);
            column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(3));
            tableLayout.setColumnData(column, new ColumnWeightData(2, 10));

            if (groupButton.getSelection())
            {
               column = new TableColumn(table, SWT.LEFT);
               column.setText("Group");
               column.setMoveable(true);
               column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(4));
               tableLayout.setColumnData(column, new ColumnWeightData(3, 10));

               column = new TableColumn(table, SWT.LEFT);
               column.setText("Load");
               column.setMoveable(true);
               column.addSelectionListener(new ProfiledEntityColumnSelectionHandler(5));
               tableLayout.setColumnData(column, new ColumnWeightData(2, 10));
            }
         }

         entityContentProvider = new ProfiledEntityContentProvider();
         entityFilter = new ProfiledEntityFilter();
         entitySorter = new ProfiledEntitySorter();
         entityViewer = new CheckboxTableViewer(table);
         entityViewer.setContentProvider(entityContentProvider);
         entityViewer.setLabelProvider(
            new ProfiledEntityLabelProvider(getContentProvider()));
         entityViewer.setSorter(entitySorter);
         entityViewer.addFilter(entityFilter);
         entityViewer.addCheckStateListener(new ProfiledEntityEnablementHandler());
         if (useFilterColor)
         {
            entityViewer.getTable().addListener(
                  SWT.EraseItem, new ProfiledEntityDrawColorHandler());
            entityViewer.getTable().addSelectionListener(
                  new ProfiledEntityColorSelectionHandler());
         }

         selectButtonsComp = toolkit.createComposite(client);
         selectButtonsComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         gridLayout = new GridLayout(2, true);
         gridLayout.marginWidth = 0;
         gridLayout.marginHeight = 0;
         selectButtonsComp.setLayout(gridLayout);

         selectAllButton = toolkit.createButton(selectButtonsComp, "Select All", SWT.PUSH);
         selectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         selectAllButton.addSelectionListener(new ProfiledEntitySelectAllHandler(true));

         deselectAllButton = toolkit.createButton(selectButtonsComp, "Deselect All", SWT.PUSH);
         deselectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         deselectAllButton.addSelectionListener(new ProfiledEntitySelectAllHandler(false));

         getCopyActionMux().addAction(new TableCopyAction(entityViewer));
         getSelectAllActionMux().addAction(new TableSelectAllAction(entityViewer));
      }

      @Override
      public void setEnabled(boolean enabled)
      {
         if (hasSorter)
         {
            sortCombo.setEnabled(enabled);
            sortCombo.select(0);

            if (contentSorter == null)
            {
               contentSorter = createContentSorter(chartViewer.getChart());
               chartViewer.getChart().addContentTransformer(contentSorter);
            }
            contentSorter.sort(ProfilerContentSorter.NONE);
         }

         if (hasFilter)
         {
            ProfiledEntity[] entities;

            if (entityGroupingAllowed)
            {
               groupButton.setEnabled(enabled);
            }

            entityFilterText.setEnabled(enabled);
            entityFilterText.setText("");
            entityFilter.setPattern("");
            entitySorter.reset();

            entityViewer.setInput(enabled ? getContentProvider() : new Object());
            entities = (ProfiledEntity[]) entityContentProvider.getElements(null);
            for (int i = 0; i < entities.length; i++)
            {
               ProfiledEntity entity = (ProfiledEntity) entities[i];
               entity.setEnabled(true);
               entity.setLoad(chartViewer.getChart());
               entityViewer.setChecked(entity, true);
            }
            entityViewer.refresh();

            selectAllButton.setEnabled(enabled);
            deselectAllButton.setEnabled(enabled);
         }
      }

      public void setGaugeSelection(int value)
      {
         if (hasNavigation)
         {
            gaugeSlider.setSelection(value);
         }
      }

      public void updateFilter()
      {
         if (hasFilter)
         {
            ProfiledEntity[] entities;

            entities = (ProfiledEntity[]) entityContentProvider.getElements(null);
            for (int i = 0; i < entities.length; i++)
            {
               ProfiledEntity entity = (ProfiledEntity) entities[i];
               boolean enabled = contentFilter.isSeriesEnabled(entity.getIndex());
               entity.setEnabled(enabled);
               entityViewer.setChecked(entity, enabled);
            }
            entityViewer.refresh();
         }
      }

      public void updateLoad(Chart chart)
      {
         ProfiledEntity[] entities = (ProfiledEntity[])
            entityContentProvider.getElements(null);

         for (int i = 0; i < entities.length; i++)
         {
            ProfiledEntity entity = (ProfiledEntity) entities[i];
            entity.setLoad(chart);
         }

         entityViewer.refresh();
      }

      public void revealEntity(int series)
      {
         int originalSeries = contentFilter.getSourceSeries(series);
         ProfiledEntity entity = entityContentProvider.getEntity(originalSeries);
         ISelection selection = (entity == null) ? new StructuredSelection()
                                             : new StructuredSelection(entity);
         entityViewer.setSelection(selection, true);
      }

      private void performEntityGrouping(Properties entityGroupingProperties)
      {
         ProfiledEntity[] entities;
         Map<String, ProfiledEntityGroup> entityGroupMap;

         entities = (ProfiledEntity[]) entityContentProvider.getElements(null);
         entityGroupMap = new HashMap<String, ProfiledEntityGroup>();
         entityGroupMap.put(OTHERS_GROUP, new ProfiledEntityGroup(OTHERS_GROUP));

         for (int i = 0; i < entities.length; i++)
         {
            ProfiledEntity entity;
            String[] entityNames;
            String entityName;
            String entityId;
            String groupName;
            ProfiledEntityGroup entityGroup;

            // The entity name is of the form "<entity-name> (<entity-id>)" or
            // just "<entity-id>".

            entity = (ProfiledEntity) entities[i];
            entity.setLoad(chartViewer.getChart());
            entityNames = entity.getName().split(" ");
            entityName = (entityNames.length > 1) ? entityNames[0] : null;
            entityId = (entityNames.length > 1) ?
               entityNames[1].replace("(", "").replace(")", "") : entityNames[0];

            if (entityName != null)
            {
               groupName = entityGroupingProperties.getProperty(entityName);
               if (groupName == null)
               {
                  groupName = entityGroupingProperties.getProperty(entityId);
               }
            }
            else
            {
               groupName = entityGroupingProperties.getProperty(entityId);
            }

            if (groupName != null)
            {
               entityGroup = entityGroupMap.get(groupName);
               if (entityGroup == null)
               {
                  entityGroup = new ProfiledEntityGroup(groupName);
                  entityGroupMap.put(groupName, entityGroup);
               }
            }
            else
            {
               entityGroup = entityGroupMap.get(OTHERS_GROUP);
            }

            entity.setGroup(entityGroup);
         }

         entityViewer.refresh();
      }

      private class ChartFocusHandler extends FocusAdapter
      {
         @Override
         public void focusGained(FocusEvent e)
         {
            chartViewer.setFocus();
         }
      }

      private class ZoomInButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            CartesianChart3D chart = (CartesianChart3D) chartViewer.getChart();
            chart.getMovableCamera().zoom(ZOOM_STEP);
            chartViewer.refresh();
         }
      }

      private class ZoomOutButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            CartesianChart3D chart = (CartesianChart3D) chartViewer.getChart();
            chart.getMovableCamera().zoom(-ZOOM_STEP);
            chartViewer.refresh();
         }
      }

      private class GaugeSliderSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            CartesianChart3D chart = (CartesianChart3D) chartViewer.getChart();
            int value = ((Slider) event.getSource()).getSelection();
            chart.setBackdropPosition(value);
         }
      }

      private class ResetCameraButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            CartesianChart3D chart = (CartesianChart3D) chartViewer.getChart();
            chart.resetCamera();
         }
      }

      private class SortSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            String str = ((Combo) event.widget).getText();
            if (str.equals(SORT_ASCENDING_MEAN_ORDER))
            {
               contentSorter.sort(ProfilerContentSorter.ASCENDING_MEAN);
            }
            else if (str.equals(SORT_DESCENDING_MEAN_ORDER))
            {
               contentSorter.sort(ProfilerContentSorter.DESCENDING_MEAN);
            }
            else
            {
               contentSorter.sort(ProfilerContentSorter.NONE);
            }
         }
      }

      private class GroupButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent event)
         {
            Label groupFileLabel = (Label) groupButton.getData();
            Properties groupingProperties = new Properties();

            // If entity grouping is enabled, read the entity grouping file.
            if (groupButton.getSelection())
            {
               try
               {
                  FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
                  FileDialogAdapter adapter = new FileDialogAdapter(getShell(), fileDialog);
                  String groupFile = adapter.open();
                  if (groupFile != null)
                  {
                     groupingProperties.load(new FileInputStream(groupFile));
                     groupButton.setText(UNGROUP_LABEL);
                     groupFileLabel.setText(
                        GROUP_FILE_LABEL + (new File(groupFile)).getName());
                  }
                  else
                  {
                     groupButton.setSelection(false);
                     return;
                  }
               }
               catch (IOException e)
               {
                  ProfilerPlugin.errorDialog(null,
                     "Error reading entity grouping file", e);
                  groupButton.setSelection(false);
                  return;
               }
            }
            else
            {
               groupButton.setText(GROUP_LABEL);
               groupFileLabel.setText(GROUP_FILE_LABEL + NO_GROUP_FILE_LABEL);
            }

            // Recreate the entity filter table.
            filterSection.getClient().dispose();
            createFilterTable();

            // Trigger an input setup for the entity filter table viewer.
            setEnabled(true);

            // Perform the actual grouping of the entities.
            if (groupButton.getSelection())
            {
               performEntityGrouping(groupingProperties);
            }

            // Relayout the filter section.
            filterSection.layout(true, true);
         }
      }

      private class ProfiledEntityFilterHandler implements ModifyListener
      {
         public void modifyText(ModifyEvent event)
         {
            ProfiledEntity[] entities;

            entityFilter.setPattern(((Text) event.widget).getText());
            entityViewer.refresh();
            entities = (ProfiledEntity[]) entityContentProvider.getElements(null);
            for (int i = 0; i < entities.length; i++)
            {
               ProfiledEntity entity = (ProfiledEntity) entities[i];
               entityViewer.setChecked(entity, entity.isEnabled());
            }
         }
      }

      private class ProfiledEntityEnablementHandler implements ICheckStateListener
      {
         public void checkStateChanged(CheckStateChangedEvent event)
         {
            boolean checked;
            ProfiledEntity entity;

            checked = event.getChecked();
            entity = (ProfiledEntity) event.getElement();
            entity.setEnabled(checked);
            entityViewer.setChecked(entity, checked);
            contentFilter.setSeriesEnabled(entity.getIndex(), checked);
         }
      }

      private class ProfiledEntityColumnSelectionHandler extends SelectionAdapter
      {
         private final int column;

         ProfiledEntityColumnSelectionHandler(int column)
         {
            this.column = column;
         }

         @Override
         public void widgetSelected(SelectionEvent event)
         {
            entitySorter.sortByColumn(column);
            entityViewer.refresh();
         }
      }

      private class ProfiledEntityDrawColorHandler implements Listener
      {
         public void handleEvent(Event event)
         {
            // Only interested in the color column.
            if ((event.type == SWT.EraseItem) && (event.index == 1))
            {
               // Get the entity that corresponds to the current table item.
               TableItem item = (TableItem) event.item;
               ProfiledEntity entity = (ProfiledEntity) item.getData();

               int width = Math.min(event.width - 2, 12);
               int height = Math.min(event.height - 2, 12);
               int xOffset = Math.max(2, (event.width - width) / 2);
               int yOffset = Math.max(2, (event.height - height) / 2);
               
               // Fill a rectangle using the color for this entity.
               MutableColorProvider colorProvider =
                  (MutableColorProvider) chartViewer.getChart().getColorProvider();
               Color color = colorProvider.getSeriesLegendColor(0, entity.getIndex());
               event.gc.setBackground(color);
               event.gc.fillRectangle(event.x + xOffset, event.y + yOffset,
                                      width, height);

               // Draw a black border around the colored rectangle.
               Color oldForeground = event.gc.getForeground();
               event.gc.setForeground(Display.getCurrent()
                                      .getSystemColor(SWT.COLOR_BLACK));
               event.gc.drawRectangle(event.x + xOffset, event.y + yOffset,
                                      width, height);

               // The foreground color has to be restored for some reason.
               event.gc.setForeground(oldForeground);

               // Make sure no foreground, selection or hover
               // graphics are drawn, by masking them out.
               event.detail &= ~(SWT.FOREGROUND | SWT.SELECTED | SWT.HOT);
            }
         }
      }

      private class ProfiledEntityColorSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetDefaultSelected(SelectionEvent e)
         {
            MutableColorProvider colorProvider =
               (MutableColorProvider) chartViewer.getChart().getColorProvider();
            ProfiledEntity entity = (ProfiledEntity) ((TableItem) e.item).getData();
            int index = entity.getIndex();
            ColorDialog dialog =
               new ColorDialog(Display.getCurrent().getActiveShell());
            dialog.setText("Select a new color");
            dialog.setRGB(colorProvider.getSeriesLegendColor(0, index).getRGB());
            RGB newColor = dialog.open();
            if (newColor != null)
            {
               colorProvider.setSeriesColor(0, index, newColor);
               entityViewer.refresh();
               chartViewer.refresh();
            }
         }
      }

      private class ProfiledEntitySelectAllHandler extends SelectionAdapter
      {
         private final boolean checked;

         ProfiledEntitySelectAllHandler(boolean checked)
         {
            this.checked = checked;
         }

         @Override
         public void widgetSelected(SelectionEvent event)
         {
            ProfiledEntity[] entities =
               (ProfiledEntity[]) entityContentProvider.getElements(null);
            for (int i = 0; i < entities.length; i++)
            {
               ProfiledEntity entity = (ProfiledEntity) entities[i];
               entity.setEnabled(checked);
               entityViewer.setChecked(entity, checked);
            }
            entityViewer.refresh();
            contentFilter.setAllSeriesEnabled(checked);
         }
      }
   }
}
