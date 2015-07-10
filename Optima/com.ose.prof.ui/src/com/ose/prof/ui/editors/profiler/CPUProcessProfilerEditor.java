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
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;

public class CPUProcessProfilerEditor extends ProfilerEditor3D
{
   static final int NUM_PROCESSES = 50;
   static final int COLUMN_OTHER = 2;

   private int numExecutionUnits;
   private short executionUnit;
   private String profiledProcesses;
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

      if (input instanceof CPUProcessProfilerEditorInput)
      {
         CPUProcessProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (CPUProcessProfilerEditorInput) input;
         numExecutionUnits = profilerEditorInput.getNumExecutionUnits();
         executionUnit = profilerEditorInput.getExecutionUnit();
         profiledProcesses = "None";
         maxValuesReport = profilerEditorInput.getMaxValuesReport();
         idNameMap = new IdNameMap(profilerEditorInput.getPidToNameMap());
      }
      else if (input instanceof IURIEditorInput)
      {
         numExecutionUnits = 0;
         executionUnit = 0;
         profiledProcesses = "None";
         maxValuesReport = 0;
         idNameMap = null;
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }
   }

   public void setProfiledProcesses(String profiledProcesses)
   {
      checkThread();
      if (profiledProcesses == null)
      {
         throw new IllegalArgumentException();
      }
      this.profiledProcesses = profiledProcesses;
      updateHeaderLabels();
   }

   public void setMaxValuesReport(int maxValuesReport)
   {
      checkThread();
      this.maxValuesReport = maxValuesReport;
      updateParametersGroup();
   }

   ProfilerContentProvider createContentProvider()
   {
      return new CPUProcessProfilerContentProvider(numExecutionUnits,
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
      label = getToolkit().createLabel(group, "Processes per Report:");
      label.setLayoutData(new GridData());
      maxValuesReportLabel = getToolkit().createLabel(group, null);
      maxValuesReportLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      maxValuesReportLabel.setData("name", "prcPerReportLbl");

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
      colorProvider = new CPUProcessProfilerColorProvider();
      chart3D = new BarChart3D(getChartTitle(),
                               getContentProvider(),
                               colorProvider);

      chart3D.setSeriesAxisLabel("Process");
      chart3D.setItemAxisLabel("Time");
      chart3D.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
      chart3D.setMaxValue(relative ? 100.0 : 10.0);

      chart3D.setSeriesWindow(0, NUM_PROCESSES);
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

      chart2D.setSeriesWindow(0, NUM_PROCESSES);
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
         column.setText("Process");
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
      tableViewer.setLabelProvider(new CPUProcessProfilerLabelProvider(
            numExecutionUnits, executionUnit, true, idNameMap));
      tableViewer.setSorter(new CPUProcessProfilerSorter());
      tableViewer.setInput(new CPUReport[0]);

      return tableViewer;
   }

   String getChartTitle()
   {
      return "CPU Usage / Process";
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
             "  Profiled Processes: " + profiledProcesses +
             "  Reports: " + reports +
             "  Date: " + getDateString();
   }

   int getNumVisibleEntities()
   {
      return NUM_PROCESSES;
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
      reportDumpReader.readCPUProcessReports(file);
   }

   void convertDumpReports(IProgressMonitor monitor,
                           ReportDumpXMLConverter reportDumpXMLConverter,
                           File from,
                           File to)
      throws IOException
   {
      reportDumpXMLConverter.convertCPUProcessReports(monitor, from, to);
   }

   private class ReportReaderHandler extends AbstractReportReaderClient
   {
      private final Map idToNameMap = new HashMap();

      ReportReaderHandler(IProgressMonitor monitor)
      {
         super(monitor);
      }

      @Override
      public void cpuProcessReportSettingsRead(String target,
                                               boolean bigEndian,
                                               int osType,
                                               int numExecutionUnits,
                                               int tickLength,
                                               int microTickFrequency,
                                               short executionUnit,
                                               int integrationInterval,
                                               int maxReports,
                                               int maxProcessesPerReport,
                                               String profiledProcesses,
                                               int seconds,
                                               int secondsTick,
                                               int secondsNTick)
      {
         CPUProcessProfilerEditor.this.target = target;
         CPUProcessProfilerEditor.this.tickLength = tickLength;
         CPUProcessProfilerEditor.this.numExecutionUnits = numExecutionUnits;
         CPUProcessProfilerEditor.this.executionUnit = executionUnit;
         CPUProcessProfilerEditor.this.integrationInterval = integrationInterval;
         CPUProcessProfilerEditor.this.maxReports = maxReports;
         CPUProcessProfilerEditor.this.maxValuesReport = maxProcessesPerReport;
         CPUProcessProfilerEditor.this.profiledProcesses = profiledProcesses;
         CPUProcessProfilerEditor.this.idNameMap = new IdNameMap(idToNameMap);
      }

      @Override
      public void processRead(int id, String name)
      {
         // FIXME: How to handle recycled ids?
         idToNameMap.put(new Integer(id), name);
      }

      @Override
      public void cpuProcessReportRead(CPUProcessReport report)
      {
         handleReportRead(report);
      }

      String getInvalidFileTypeMessage()
      {
         return "This is not a CPU Usage / Process file.";
      }
   }
}
