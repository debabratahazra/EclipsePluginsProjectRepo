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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
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
import com.ose.system.CPUPriorityReport;

public class CPUPriorityProfilerEditor extends ProfilerEditor3D
{
   static final int NUM_PRIORITIES = 34;
   static final int COLUMN_INTERRUPT = 2;
   static final int COLUMN_PRIORITY_0 = 3;
   static final int COLUMN_BACKGROUND = 35;

   private int numExecutionUnits;
   private short executionUnit;

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

      if (input instanceof CPUPriorityProfilerEditorInput)
      {
         CPUPriorityProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (CPUPriorityProfilerEditorInput) input;
         numExecutionUnits = profilerEditorInput.getNumExecutionUnits();
         executionUnit = profilerEditorInput.getExecutionUnit();
      }
      else if (input instanceof IURIEditorInput)
      {
         numExecutionUnits = 0;
         executionUnit = 0;
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }
   }

   ProfilerContentProvider createContentProvider()
   {
      return new CPUPriorityProfilerContentProvider(numExecutionUnits,
                                                    executionUnit,
                                                    MAX_REPORTS,
                                                    true);
   }

   ChartViewer createChartViewer3D(Composite parent)
   {
      ChartViewer chartViewer;

      chartViewer = new ChartViewer(parent, SWT.NONE);
      chartViewer.showItemSlider();
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
      colorProvider = new CPUPriorityProfilerColorProvider();
      chart3D = new BarChart3D(getChartTitle(),
                               getContentProvider(),
                               colorProvider);

      chart3D.setSeriesAxisLabel("Priority");
      chart3D.setItemAxisLabel("Time");
      chart3D.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
      chart3D.setMaxValue(relative ? 100.0 : 10.0);

      chart3D.setSeriesWindow(0, NUM_PRIORITIES);
      chart3D.setItemWindow(0, NUM_REPORTS);
      chart3D.setItemTickStep(1);

      chart3D.setDefaultCameraTranslation(0.0f, 0.0f, -250.0f);
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

      chart2D.setSeriesWindow(0, NUM_PRIORITIES);
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
      layout.setColumnData(column, new ColumnPixelData(70));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Interrupt");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_INTERRUPT));
      layout.setColumnData(column, new ColumnPixelData(70));

      for (int i = 0; i < 32; i++)
      {
         column = new TableColumn(table, SWT.RIGHT);
         column.setText("Priority " + i);
         column.setMoveable(true);
         column.addSelectionListener(new ColumnSelectionHandler(COLUMN_PRIORITY_0 + i));
         layout.setColumnData(column, new ColumnPixelData(70));
      }

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Background");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_BACKGROUND));
      layout.setColumnData(column, new ColumnPixelData(80));

      tableViewer = new TableViewer(table);
      tableViewer.setUseHashlookup(true);
      tableViewer.setContentProvider(getContentProvider());
      tableViewer.setLabelProvider(new CPUPriorityProfilerLabelProvider(
            numExecutionUnits, executionUnit, true));
      tableViewer.setSorter(new CPUPriorityProfilerSorter());
      tableViewer.setInput(new CPUPriorityReport[0]);

      return tableViewer;
   }

   String getChartTitle()
   {
      return "CPU Usage / Priority";
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
      return NUM_PRIORITIES;
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
      reportDumpReader.readCPUPriorityReports(file);
   }

   void convertDumpReports(IProgressMonitor monitor,
                           ReportDumpXMLConverter reportDumpXMLConverter,
                           File from,
                           File to)
      throws IOException
   {
      reportDumpXMLConverter.convertCPUPriorityReports(monitor, from, to);
   }

   private class ReportReaderHandler extends AbstractReportReaderClient
   {
      ReportReaderHandler(IProgressMonitor monitor)
      {
         super(monitor);
      }

      @Override
      public void cpuPriorityReportSettingsRead(String target,
                                                boolean bigEndian,
                                                int osType,
                                                int numExecutionUnits,
                                                int tickLength,
                                                int microTickFrequency,
                                                short executionUnit,
                                                int integrationInterval,
                                                int maxReports,
                                                int seconds,
                                                int secondsTick,
                                                int secondsNTick)
      {
         CPUPriorityProfilerEditor.this.target = target;
         CPUPriorityProfilerEditor.this.tickLength = tickLength;
         CPUPriorityProfilerEditor.this.numExecutionUnits = numExecutionUnits;
         CPUPriorityProfilerEditor.this.executionUnit = executionUnit;
         CPUPriorityProfilerEditor.this.integrationInterval = integrationInterval;
         CPUPriorityProfilerEditor.this.maxReports = maxReports;
      }

      @Override
      public void cpuPriorityReportRead(CPUPriorityReport report)
      {
         handleReportRead(report);
      }

      String getInvalidFileTypeMessage()
      {
         return "This is not a CPU Usage / Priority file.";
      }
   }
}
