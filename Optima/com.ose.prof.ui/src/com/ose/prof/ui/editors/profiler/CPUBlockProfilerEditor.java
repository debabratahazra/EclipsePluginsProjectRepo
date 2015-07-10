/* COPYRIGHT-ENEA-SRC-R2 *
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
import com.ose.chart.ui.BarChart3D;
import com.ose.chart.ui.CartesianChart2D;
import com.ose.chart.ui.CartesianChart3D;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ChartViewer;
import com.ose.chart.ui.PieChart2D;
import com.ose.chart.ui.StackedBarChart2D;
import com.ose.prof.format.ReportDumpReader;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.format.ReportReaderClient;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUReport;

public class CPUBlockProfilerEditor extends ProfilerEditor3D
{
   static final int NUM_BLOCKS = 50;
   static final int COLUMN_OTHER = 2;

   private int numExecutionUnits;
   private short executionUnit;
   private int maxValuesReport;
   private IdNameMap idNameMap;

   private Label maxValuesReportLabel;
   private BarChart3D chart3D;
   private StackedBarChart2D chart2D;
   private PieChart2D chartPie;
   private final MutableColorProvider commonColorProvider =
      new MutableColorProvider();      

   @Override
   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      super.init(site, input);

      if (input instanceof CPUBlockProfilerEditorInput)
      {
         CPUBlockProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (CPUBlockProfilerEditorInput) input;
         numExecutionUnits = profilerEditorInput.getNumExecutionUnits();
         executionUnit = profilerEditorInput.getExecutionUnit();
         maxValuesReport = profilerEditorInput.getMaxValuesReport();
         idNameMap = new IdNameMap(profilerEditorInput.getBidToNameMap());
      }
      else if (input instanceof IURIEditorInput)
      {
         numExecutionUnits = 0;
         executionUnit = 0;
         maxValuesReport = 0;
         idNameMap = null;
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

   ProfilerContentProvider createContentProvider()
   {
      return new CPUBlockProfilerContentProvider(numExecutionUnits,
                                                 executionUnit,
                                                 MAX_REPORTS,
                                                 true,
                                                 idNameMap);
   }

   @Override
   Group createParametersGroup(Composite parent)
   {
      Group group;
      Label label;

      group = super.createParametersGroup(parent);
      label = getToolkit().createLabel(group, "Blocks per Report:");
      label.setLayoutData(new GridData());
      maxValuesReportLabel = getToolkit().createLabel(group, null);
      maxValuesReportLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      maxValuesReportLabel.setData("name", "blkPerReportLbl");
      
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

   @Override
   boolean hasChartPie()
   {
      return true;
   }
   
   @Override
   ChartViewer createChartViewerPie(Composite parent)
   {
      ChartViewer chartViewer;

      chartViewer = new ChartViewer(parent, SWT.NONE);
      chartViewer.setChart(createChartPie());

      return chartViewer;
   }
   
   private CartesianChart3D createChart3D()
   {
      boolean relative;
      ChartColorProvider colorProvider;

      relative = getContentProvider().isRelative();
      colorProvider = new CPUBlockProfilerColorProvider();
      chart3D = new BarChart3D(getChartTitle(),
                               getContentProvider(),
                               colorProvider);

      chart3D.setSeriesAxisLabel("Block");
      chart3D.setItemAxisLabel("Time");
      chart3D.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
      chart3D.setMaxValue(relative ? 100.0 : 10.0);

      chart3D.setSeriesWindow(0, NUM_BLOCKS);
      chart3D.setItemWindow(0, NUM_REPORTS);
      chart3D.setItemTickStep(1);
      chart3D.resetCamera();

      chart3D.addChartUpdateListener(new ChartUpdateHandler());

      return chart3D;
   }

   private CartesianChart2D createChart2D()
   {
      boolean relative;

      relative = getContentProvider().isRelative();
      chart2D = new StackedBarChart2D(getChartTitle(),
                                      getContentProvider(),
                                      commonColorProvider);

      chart2D.setItemAxisLabel("Time");
      chart2D.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
      chart2D.setMaxValue(relative ? 100.0 : 10.0);

      chart2D.setSeriesWindow(0, NUM_BLOCKS);
      chart2D.setItemWindow(0, NUM_REPORTS);
      chart2D.setItemTickStep(1);

      return chart2D;
   }
   
   private PieChart2D createChartPie()
   {
      chartPie = new PieChart2D(getChartTitle(), getContentProvider(),
                                commonColorProvider);
      
      return chartPie;
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
      column.setText("Other");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER));
      layout.setColumnData(column, new ColumnPixelData(60));

      for (int i = 0; i < maxValuesReport; i++)
      {
         column = new TableColumn(table, SWT.LEFT);
         column.setText("Block");
         column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER + (i*2) + 1));
         layout.setColumnData(column, new ColumnPixelData(idNameMap.isEmpty() ? 80 : 160));
         column = new TableColumn(table, SWT.RIGHT);
         column.setText("Value");
         column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OTHER + (i*2) + 2));
         layout.setColumnData(column, new ColumnPixelData(60));
      }

      tableViewer = new TableViewer(table);
      tableViewer.setUseHashlookup(true);
      tableViewer.setContentProvider(getContentProvider());
      tableViewer.setLabelProvider(new CPUBlockProfilerLabelProvider(
            numExecutionUnits, executionUnit, true, idNameMap));
      tableViewer.setSorter(new CPUBlockProfilerSorter());
      tableViewer.setInput(new CPUReport[0]);

      return tableViewer;
   }

   String getChartTitle()
   {
      return "CPU Usage / Block";
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
             "  Execution Unit: " + toExecutionUnitString(executionUnit) +
             "  Reports: " + reports +
             "  Date: " + getDateString();
   }

   int getNumVisibleEntities()
   {
      return NUM_BLOCKS;
   }

   @Override
   void updateParametersGroup()
   {
      super.updateParametersGroup();
      maxValuesReportLabel.setText(toU32String(maxValuesReport));
   }

   @Override
   void setRelative(boolean relative)
   {
      super.setRelative(relative);
      String label = relative ? "Usage (%)" : "Usage";
      chart3D.setValueAxisLabel(label);
      chart2D.setValueAxisLabel(label);
   }

   ReportReaderClient createReportReaderClient(IProgressMonitor monitor)
   {
      return new ReportReaderHandler(monitor);
   }

   void readDumpReports(ReportDumpReader reportDumpReader, File file)
      throws IOException
   {
      reportDumpReader.readCPUBlockReports(file);
   }

   void convertDumpReports(IProgressMonitor monitor,
                           ReportDumpXMLConverter reportDumpXMLConverter,
                           File from,
                           File to)
      throws IOException
   {
      reportDumpXMLConverter.convertCPUBlockReports(monitor, from, to);
   }

   private class ReportReaderHandler extends AbstractReportReaderClient
   {
      private final Map idToNameMap = new HashMap();

      ReportReaderHandler(IProgressMonitor monitor)
      {
         super(monitor);
      }

      @Override
      public void cpuBlockReportSettingsRead(String target,
                                             boolean bigEndian,
                                             int osType,
                                             int numExecutionUnits,
                                             int tickLength,
                                             int microTickFrequency,
                                             short executionUnit,
                                             int integrationInterval,
                                             int maxReports,
                                             int maxBlocksPerReport,
                                             int seconds,
                                             int secondsTick,
                                             int secondsNTick)
      {
         CPUBlockProfilerEditor.this.target = target;
         CPUBlockProfilerEditor.this.tickLength = tickLength;
         CPUBlockProfilerEditor.this.numExecutionUnits = numExecutionUnits;
         CPUBlockProfilerEditor.this.executionUnit = executionUnit;
         CPUBlockProfilerEditor.this.integrationInterval = integrationInterval;
         CPUBlockProfilerEditor.this.maxReports = maxReports;
         CPUBlockProfilerEditor.this.maxValuesReport = maxBlocksPerReport;
         CPUBlockProfilerEditor.this.idNameMap = new IdNameMap(idToNameMap);
      }

      @Override
      public void blockRead(int id, String name)
      {
         // FIXME: How to handle recycled ids?
         idToNameMap.put(new Integer(id), name);
      }

      @Override
      public void cpuBlockReportRead(CPUBlockReport report)
      {
         handleReportRead(report);
      }

      String getInvalidFileTypeMessage()
      {
         return "This is not a CPU Usage / Block file.";
      }
   }
}
