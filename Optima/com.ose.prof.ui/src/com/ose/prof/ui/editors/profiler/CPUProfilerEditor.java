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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import com.ose.chart.ui.BarChart2D;
import com.ose.chart.ui.CartesianChart2D;
import com.ose.chart.ui.ChartColorProvider;
import com.ose.chart.ui.ChartViewer;
import com.ose.prof.format.ReportDumpReader;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.format.ReportReaderClient;
import com.ose.system.CPUReport;

public class CPUProfilerEditor extends ProfilerEditor2D
{
   static final int COLUMN_USAGE = 2;

   private int numExecutionUnits;
   private short executionUnit;
   private int priorityLimit;

   private Label priorityLimitLabel;
   private BarChart2D chart;

   @Override
   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      super.init(site, input);

      if (input instanceof CPUProfilerEditorInput)
      {
         CPUProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (CPUProfilerEditorInput) input;
         numExecutionUnits = profilerEditorInput.getNumExecutionUnits();
         executionUnit = profilerEditorInput.getExecutionUnit();
         priorityLimit = profilerEditorInput.getPriorityLimit();
      }
      else if (input instanceof IURIEditorInput)
      {
         numExecutionUnits = 0;
         executionUnit = 0;
         priorityLimit = 0;
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }
   }

   public void setPriorityLimit(int priorityLimit)
   {
      checkThread();
      this.priorityLimit = priorityLimit;
      updateParametersGroup();
   }

   ProfilerContentProvider createContentProvider()
   {
      return new CPUProfilerContentProvider(numExecutionUnits,
                                            executionUnit,
                                            MAX_REPORTS,
                                            true);
   }

   @Override
   Group createParametersGroup(Composite parent)
   {
      Group group;
      Label label;

      group = super.createParametersGroup(parent);
      label = getToolkit().createLabel(group, "Priority Limit:");
      label.setLayoutData(new GridData());
      priorityLimitLabel = getToolkit().createLabel(group, null);
      priorityLimitLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      priorityLimitLabel.setData("name", "priorityLimitLabel");

      return group;
   }

   ChartViewer createChartViewer(Composite parent)
   {
      ChartViewer chartViewer;

      chartViewer = new ChartViewer(parent, SWT.NONE);
      chartViewer.showItemSlider();
      chartViewer.setChart(createChart());

      return chartViewer;
   }

   private CartesianChart2D createChart()
   {
      boolean relative;
      ChartColorProvider colorProvider;

      relative = getContentProvider().isRelative();
      colorProvider = new CPUProfilerColorProvider();
      chart = new BarChart2D(getChartTitle(), getContentProvider(), colorProvider);

      chart.setItemAxisLabel("Time");
      chart.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
      chart.setMaxValue(relative ? 100.0 : 10.0);

      chart.setItemWindow(0, 0);
      chart.setItemWidth(10.0f);
      chart.setItemTickStep(10);

      return chart;
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
      column.setText("Usage");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_USAGE));
      layout.setColumnData(column, new ColumnPixelData(60));

      tableViewer = new TableViewer(table);
      tableViewer.setUseHashlookup(true);
      tableViewer.setContentProvider(getContentProvider());
      tableViewer.setLabelProvider(
         new CPUProfilerLabelProvider(numExecutionUnits, executionUnit, true));
      tableViewer.setSorter(new CPUProfilerSorter());
      tableViewer.setInput(new CPUReport[0]);

      return tableViewer;
   }

   String getChartTitle()
   {
      return "CPU Usage";
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

   @Override
   void updateParametersGroup()
   {
      super.updateParametersGroup();
      priorityLimitLabel.setText(toU32String(priorityLimit));
   }

   @Override
   void setRelative(boolean relative)
   {
      super.setRelative(relative);
      chart.setValueAxisLabel(relative ? "Usage (%)" : "Usage");
   }

   ReportReaderClient createReportReaderClient(IProgressMonitor monitor)
   {
      return new ReportReaderHandler(monitor);
   }

   void readDumpReports(ReportDumpReader reportDumpReader, File file)
      throws IOException
   {
      reportDumpReader.readCPUReports(file);
   }

   void convertDumpReports(IProgressMonitor monitor,
                           ReportDumpXMLConverter reportDumpXMLConverter,
                           File from,
                           File to)
      throws IOException
   {
      reportDumpXMLConverter.convertCPUReports(monitor, from, to);
   }

   private class ReportReaderHandler extends AbstractReportReaderClient
   {
      ReportReaderHandler(IProgressMonitor monitor)
      {
         super(monitor);
      }

      @Override
      public void cpuReportSettingsRead(String target,
                                        boolean bigEndian,
                                        int osType,
                                        int numExecutionUnits,
                                        int tickLength,
                                        int microTickFrequency,
                                        short executionUnit,
                                        int integrationInterval,
                                        int maxReports,
                                        int priorityLimit,
                                        int seconds,
                                        int secondsTick,
                                        int secondsNTick)
      {
         CPUProfilerEditor.this.target = target;
         CPUProfilerEditor.this.tickLength = tickLength;
         CPUProfilerEditor.this.numExecutionUnits = numExecutionUnits;
         CPUProfilerEditor.this.executionUnit = executionUnit;
         CPUProfilerEditor.this.integrationInterval = integrationInterval;
         CPUProfilerEditor.this.maxReports = maxReports;
         CPUProfilerEditor.this.priorityLimit = priorityLimit;
      }

      @Override
      public void cpuReportRead(CPUReport report)
      {
         handleReportRead(report);
      }

      String getInvalidFileTypeMessage()
      {
         return "This is not a CPU Usage file.";
      }
   }
}
