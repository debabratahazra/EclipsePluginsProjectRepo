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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;
import com.ose.chart.ui.CartesianChart2D;
import com.ose.chart.ui.CartesianChart3D;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ChartViewer;
import com.ose.chart.ui.ClusteredBarChart3D;
import com.ose.chart.ui.StackedBarChart2D;
import com.ose.prof.format.ReportDumpReader;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.format.ReportReaderClient;
import com.ose.system.TargetReport;
import com.ose.system.TargetReports;
import com.ose.system.UserReport;
import com.ose.system.UserReports;

public class UserProfilerEditor extends ProfilerEditor3D
{
   static final int NUM_PROCESSES = 50;
   static final int COLUMN_OTHER = 2;

   private int maxValuesReport;
   private IdNameMap idNameMap;
   private int reportNumber;
   private boolean maxMin;
   private boolean multiple;
   private boolean empty = true;

   private Label maxValuesReportLabel;
   private ClusteredBarChart3D chart3D;
   private StackedBarChart2D chart2D;

   @Override
   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      super.init(site, input);

      if (input instanceof UserProfilerEditorInput)
      {
         UserProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (UserProfilerEditorInput) input;
         maxValuesReport = profilerEditorInput.getMaxValuesReport();
         idNameMap = new IdNameMap(profilerEditorInput.getPidToNameMap());
         reportNumber = profilerEditorInput.getReportNumber();
      }
      else if (input instanceof IURIEditorInput)
      {
         maxValuesReport = 0;
         idNameMap = null;
         reportNumber = 0;
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }
   }

   public void setMaxValuesReport(int maxValuesReport)
   {
      checkThread();
      this.maxValuesReport = maxValuesReport;
      updateParametersGroup();
   }

   @Override
   public void addReports(TargetReports reports)
   {
      if (empty)
      {
         UserReports reportInfo = (UserReports) reports;
         this.maxMin = reportInfo.isMaxMin();
         this.multiple = reportInfo.isMultiple();
         updateChart3DProperties();
         updateTableProperties();
         empty = false;
      }

      super.addReports(reports);
   }

   @Override
   public void addReports(TargetReport[] reports)
   {
      if (empty)
      {
         updateChart3DProperties();
         updateTableProperties();
         empty = false;
      }

      super.addReports(reports);
   }

   ProfilerContentProvider createContentProvider()
   {
      return new UserProfilerContentProvider(MAX_REPORTS, idNameMap);
   }

   @Override
   ProfilerContentSorter createContentSorter(Chart chart)
   {
      return new ProfilerContentSorter(chart,
            UserProfilerContentProvider.VALUE_LAYER);
   }

   @Override
   Group createParametersGroup(Composite parent)
   {
      Group group;
      Label label;

      group = super.createParametersGroup(parent);
      label = getToolkit().createLabel(group, idNameMap.isEmpty() ?
            "Values per Report:" : "Processes per Report:");
      label.setLayoutData(new GridData());
      maxValuesReportLabel = getToolkit().createLabel(group, null);
      maxValuesReportLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      maxValuesReportLabel.setData("name", label.getText());

      return group;
   }

   ChartViewer createChartViewer3D(Composite parent)
   {
      ChartViewer chartViewer;

      chartViewer = new ChartViewer(parent, SWT.NONE);
      chartViewer.showItemSlider();
      chartViewer.showSeriesSlider();
      chartViewer.setChart(createChart3D());

      return chartViewer;
   }

   ChartViewer createChartViewer2D(Composite parent)
   {
      ChartViewer chartViewer;

      chartViewer = new ChartViewer(parent, SWT.NONE);
      chartViewer.showItemSlider();
      chartViewer.setChart(createChart2D());

      return chartViewer;
   }

   private CartesianChart3D createChart3D()
   {
      ChartColorProvider colorProvider;

      colorProvider = new UserProfilerColorProvider();
      chart3D = new ClusteredBarChart3D(getChartTitle(),
                                        getContentProvider(),
                                        colorProvider);

      chart3D.setSeriesAxisLabel(
         ((idNameMap == null) || idNameMap.isEmpty()) ? "ID" : "Process");
      chart3D.setItemAxisLabel("Time");
      chart3D.setValueAxisLabel("Usage");
      chart3D.setMaxValue(10.0);
      chart3D.setBackdropLabelLayer(UserProfilerContentProvider.VALUE_LAYER);

      chart3D.setSeriesWindow(0, NUM_PROCESSES);
      chart3D.setItemWindow(0, NUM_REPORTS);
      chart3D.setItemTickStep(1);
      chart3D.resetCamera();

      chart3D.addChartUpdateListener(new ChartUpdateHandler());

      return chart3D;
   }

   private CartesianChart2D createChart2D()
   {
      ChartColorProvider colorProvider;

      colorProvider = new MutableColorProvider();
      chart2D = new StackedBarChart2D(getChartTitle(),
                                      getContentProvider(),
                                      colorProvider);

      chart2D.setItemAxisLabel("Time");
      chart2D.setValueAxisLabel("Usage");
      chart2D.setMaxValue(10.0);

      chart2D.setLayerWindow(UserProfilerContentProvider.VALUE_LAYER, 1);
      chart2D.setSeriesWindow(0, NUM_PROCESSES);
      chart2D.setItemWindow(0, NUM_REPORTS);
      chart2D.setItemTickStep(1);

      return chart2D;
   }

   TableViewer createTableViewer(Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;
      TableViewer tableViewer;

      comp = getToolkit().createComposite(parent);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new TableColumnLayout();
      comp.setLayout(layout);

      table = getToolkit().createTable(comp, SWT.V_SCROLL | SWT.H_SCROLL
            | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Tick");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_TICK));
      layout.setColumnData(column, new ColumnPixelData(90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Interval");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_INTERVAL));
      layout.setColumnData(column, new ColumnPixelData(60));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Sum");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER));
      layout.setColumnData(column, new ColumnPixelData(120));

      for (int i = 0; i < maxValuesReport; i++)
      {
         column = new TableColumn(table, SWT.LEFT);
         column.setText(idNameMap.isEmpty() ? "ID" : "Process");
         column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER + (i*2) + 1));
         layout.setColumnData(column, new ColumnPixelData(idNameMap.isEmpty() ? 80 : 160));
         column = new TableColumn(table, SWT.RIGHT);
         column.setText("Value");
         column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER + (i*2) + 2));
         layout.setColumnData(column, new ColumnPixelData(120));
      }

      tableViewer = new TableViewer(table);
      tableViewer.setUseHashlookup(true);
      tableViewer.setContentProvider(getContentProvider());
      tableViewer.setLabelProvider(new UserProfilerLabelProvider(idNameMap));
      tableViewer.setSorter(new UserProfilerSorter());
      tableViewer.setInput(new UserReport[0]);

      return tableViewer;
   }

   String getChartTitle()
   {
      if (reportNumber == HeapProfilerEditor.HEAP_USER_REPORT_NUMBER)
      {
         return "Turnover Heap Usage / Process";
      }
      else if (reportNumber == AccumulatedHeapProcessProfilerEditor.ACCUMULATED_HEAP_PROCESS_USER_REPORT_NUMBER)
      {
         return "Accumulated Heap Usage / Process";
      }
      else if (reportNumber == AccumulatedHeapProfilerEditor.ACCUMULATED_HEAP_USER_REPORT_NUMBER)
      {
         return "Accumulated Heap Usage / Heap";
      }
      else
      {
         return "Custom Profiling Type " + reportNumber;
      }
   }

   String getHeaderString()
   {
      String reports;

      if (getNumTotalReports() <= MAX_REPORTS)
      {
         reports = toU32String(getContentProvider().getNumReports());
      }
      else
      {
         reports = toU32String(getContentProvider().getNumReports()) + " of " +
                   toU32String(getNumTotalReports());
      }

      return "Target: " + getTarget() +
             "  Reports: " + reports +
             "  Date: " + getDateString();
   }

   int getNumVisibleEntities()
   {
      return NUM_PROCESSES;
   }

   @Override
   boolean isRelativeValuesSupported()
   {
      return false;
   }

   @Override
   void updateParametersGroup()
   {
      super.updateParametersGroup();
      maxValuesReportLabel.setText(toU32String(maxValuesReport));
   }

   private void updateChart3DProperties()
   {
      UserProfilerContentProvider contentProvider =
         (UserProfilerContentProvider) getContentProvider();

      if (maxMin)
      {
         contentProvider.setMaxMin(maxMin);
         chart3D.setLayerWindow(UserProfilerContentProvider.MIN_LAYER, 3);
      }
      else
      {
         chart3D.setLayerWindow(UserProfilerContentProvider.VALUE_LAYER, 1);
      }
   }

   private void updateTableProperties()
   {
      String columnTitle;
      TableViewer tableViewer;
      UserProfilerLabelProvider labelProvider;

      columnTitle = multiple ? "Other" : "Sum";
      if (maxMin)
      {
         columnTitle += "[Min,Max]";
      }
      tableViewer = getTableViewer();
      tableViewer.getTable().getColumn(COLUMN_OTHER).setText(columnTitle);
      labelProvider = (UserProfilerLabelProvider) tableViewer.getLabelProvider();
      labelProvider.setMaxMin(maxMin);
   }

   /*
    * For clustered 3D bar charts, the min/max values (if any) are used when
    * calculating the min/max statistics while the ordinary value is used when
    * calculating the mean statistics.
    *
    * For stacked 2D bar charts, min/max values are ignored and only the
    * ordinary value is used when calculating the min/mean/max statistics.
    * The ordinary values are calculated as the sum of the stacked series
    * at each item.
    */
   @Override
   Statistics calculateStatistics(Chart chart)
   {
      if (isOfflineFeaturesEnabled() && isSelectionValidForStatistics(chart))
      {
         boolean is3DChart = (chart instanceof CartesianChart3D);
         ChartContentProvider content = chart.getContentProvider();
         Range items = chart.getItemSelection();
         int itemsOffset = items.getOffset();
         int itemsCount = items.getCount();
         Range series = chart.getSeriesSelection();
         int seriesOffset = series.getOffset();
         int seriesCount = series.getCount();
         double max = Double.NaN;
         double min = Double.NaN;
         double sum = 0.0;
         int sumCount = 0;

         for (int i = 0; i < itemsCount; i++)
         {
            double itemMax = Double.NaN;
            double itemMin = Double.NaN;
            double value = Double.NaN;

            if (is3DChart)
            {
               itemMax = content.getValue((maxMin ? 2 : 1), seriesOffset, itemsOffset + i);
               itemMin = content.getValue((maxMin ? 0 : 1), seriesOffset, itemsOffset + i);
               value = content.getValue(1, seriesOffset, itemsOffset + i);
            }
            else
            {
               for (int s = 0; s < seriesCount; s++)
               {
                  double v = content.getValue(1, seriesOffset + s, itemsOffset + i);
                  if (!Double.isNaN(v))
                  {
                     value = Double.isNaN(value) ? v : value + v;
                  }
               }
            }

            if (!Double.isNaN(value))
            {
               if (is3DChart)
               {
                  max = Double.isNaN(max) ? itemMax : Math.max(max, itemMax);
                  min = Double.isNaN(min) ? itemMin : Math.min(min, itemMin);
               }
               else
               {
                  max = Double.isNaN(max) ? value : Math.max(max, value);
                  min = Double.isNaN(min) ? value : Math.min(min, value);
               }
               sum += value;
               sumCount++;
            }
         }

         // If sumCount is 0, no value in the items range was valid.
         if (sumCount == 0)
         {
            return null;
         }
         else
         {
            double mean = sum / (double) sumCount;
            return new Statistics(min, mean, max);
         }
      }

      return null;
   }

   ReportReaderClient createReportReaderClient(IProgressMonitor monitor)
   {
      return new ReportReaderHandler(monitor);
   }

   void readDumpReports(ReportDumpReader reportDumpReader, File file)
      throws IOException
   {
      reportDumpReader.readUserReports(file);
   }

   void convertDumpReports(IProgressMonitor monitor,
                           ReportDumpXMLConverter reportDumpXMLConverter,
                           File from,
                           File to)
      throws IOException
   {
      reportDumpXMLConverter.convertUserReports(monitor, from, to);
   }

   private class ReportReaderHandler extends AbstractReportReaderClient
   {
      private final Map idToNameMap = new HashMap();

      ReportReaderHandler(IProgressMonitor monitor)
      {
         super(monitor);
      }

      @Override
      public void userReportSettingsRead(String target,
                                         boolean bigEndian,
                                         int osType,
                                         int numExecutionUnits,
                                         int tickLength,
                                         int microTickFrequency,
                                         int integrationInterval,
                                         int maxReports,
                                         int maxValuesPerReport,
                                         int reportNumber,
                                         boolean continuous,
                                         boolean maxMin,
                                         boolean multiple,
                                         int seconds,
                                         int secondsTick,
                                         int secondsNTick)
      {
         UserProfilerEditor.this.target = target;
         UserProfilerEditor.this.tickLength = tickLength;
         UserProfilerEditor.this.integrationInterval = integrationInterval;
         UserProfilerEditor.this.maxReports = maxReports;
         UserProfilerEditor.this.maxValuesReport = maxValuesPerReport;
         UserProfilerEditor.this.reportNumber = reportNumber;
         UserProfilerEditor.this.idNameMap = new IdNameMap(idToNameMap);
         UserProfilerEditor.this.maxMin = maxMin;
         UserProfilerEditor.this.multiple = multiple;
      }

      @Override
      public void processRead(int id, String name)
      {
         // FIXME: How to handle recycled ids?
         idToNameMap.put(new Integer(id), name);
      }

      @Override
      public void userReportRead(UserReport report)
      {
         handleReportRead(report);
      }

      String getInvalidFileTypeMessage()
      {
         return "This is not a Custom Profiling Type file.";
      }
   }
}
