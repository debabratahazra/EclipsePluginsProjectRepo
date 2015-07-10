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

package com.ose.system.ui.forms;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.FontDefinition;
import org.eclipse.birt.chart.model.attribute.HorizontalAlignment;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.TextAlignment;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.FontDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.TextAlignmentImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.QueryImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import com.ibm.icu.util.ULocale;
import com.ose.system.Heap;
import com.ose.system.HeapBufferSizeInfo;
import com.ose.system.HeapFragmentInfo;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.charts.ChartModel;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class HeapForm implements IForm
{
   private static final String[] HEAP_FRAGMENTS_TABLE_HEADERS =
      {"Start Address", "End Address", "Size", "Free", "Used", "Requested"};
   private static final int[] HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS =
      {90, 90, 80, 110, 110, 110};
   private static final int HEAP_FRAGMENTS_TABLE_HEIGHT = 300;

   private static final String[] BUFFER_SIZES_TABLE_HEADERS =
      {"Buffer Size", "Allocated", "Free"};
   private static final int[] BUFFER_SIZES_TABLE_COLUMN_WIDTHS = {80, 80, 80};
   private static final int BUFFER_SIZES_TABLE_HEIGHT = 200;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Heap heap;

   private Text killedText;
   private Text idText;
   private Text ownerText;
   private Text sidText;
   private Text totalSizeText;
   private Text freeSizeText;
   private Text usedSizeText;
   private Text peakUsedSizeText;
   private Text requestedSizeText;
   private Text largestFreeText;
   private Text largeThresholdText;
   private Text privateText;
   private Text sharedText;
   private Text mallocFailedText;
   private Table heapFragmentsTable;
   private Table bufferSizesTable;
   private HeapPieChart heapChart;

   private Action copyAction;
   private Action selectAllAction;

   public HeapForm(IEditorPart editor, Heap heap)
   {
      if ((editor == null) || (heap == null))
      {
         throw new NullPointerException();
      }
      this.editor = editor;
      this.heap = heap;
   }

   public void createContents(Composite parent)
   {
      TableWrapLayout layout;
      ActionMultiplexer copyActionMux;
      ActionMultiplexer selectAllActionMux;

      // Create form.
      toolkit = new FormToolkit(parent.getDisplay());
      form = toolkit.createScrolledForm(parent);
      layout = new TableWrapLayout();
      layout.numColumns = 2;
      form.getBody().setLayout(layout);

      // Create information section.
      createInfoSection();

      // Create heap fragments section.
      createHeapFragmentsSection();

      // Create heap chart section.
      createHeapChartSection();

      // Create heap buffer sizes section.
      createBufferSizesSection();

      // Create global actions.
      copyActionMux = new ActionMultiplexer();
      copyActionMux.addAction(new TableCopyAction(heapFragmentsTable));
      copyActionMux.addAction(new TableCopyAction(bufferSizesTable));
      copyAction = copyActionMux;
      selectAllActionMux = new ActionMultiplexer();
      selectAllActionMux.addAction(new TableSelectAllAction(heapFragmentsTable));
      selectAllActionMux.addAction(new TableSelectAllAction(bufferSizesTable));
      selectAllAction = selectAllActionMux;

      // Refresh form contents.
      refresh();
   }

   private void createInfoSection()
   {
      Section section;
      TableWrapLayout layout;
      TableWrapData twd;
      Composite client;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Heap Information");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      layout = new TableWrapLayout();
      layout.numColumns = 2;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      toolkit.createLabel(client, "Killed:");
      killedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      killedText.setLayoutData(twd);

      toolkit.createLabel(client, "Heap ID:");
      idText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      idText.setLayoutData(twd);

      toolkit.createLabel(client, "Owner ID:");
      ownerText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      ownerText.setLayoutData(twd);

      toolkit.createLabel(client, "Segment ID:");
      sidText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      sidText.setLayoutData(twd);

      toolkit.createLabel(client, "Total Size:");
      totalSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      totalSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Free Size:");
      freeSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      freeSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Used Size:");
      usedSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      usedSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Peak Used Size:");
      peakUsedSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      peakUsedSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Requested Size:");
      requestedSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      requestedSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Largest Free Heap Buffer:");
      largestFreeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      largestFreeText.setLayoutData(twd);

      toolkit.createLabel(client, "Large Threshold:");
      largeThresholdText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      largeThresholdText.setLayoutData(twd);

      toolkit.createLabel(client, "Private Heap Buffers:");
      privateText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      privateText.setLayoutData(twd);

      toolkit.createLabel(client, "Shared Heap Buffers:");
      sharedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      sharedText.setLayoutData(twd);

      toolkit.createLabel(client, "Failed Mallocs:");
      mallocFailedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      mallocFailedText.setLayoutData(twd);
   }

   private void createHeapFragmentsSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      TableWrapData twd;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Heap Fragments");
      twd = new TableWrapData();
      twd.valign = TableWrapData.FILL;
      twd.grabVertical = true;
      section.setLayoutData(twd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      heapFragmentsTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      heapFragmentsTable.setHeaderVisible(true);
      heapFragmentsTable.setLinesVisible(true);
      layout = new TableLayout();
      heapFragmentsTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[0] +
                     HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[1] +
                     HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[2] +
                     HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[3] +
                     HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[4] +
                     HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[5];
      gd.heightHint = HEAP_FRAGMENTS_TABLE_HEIGHT;
      heapFragmentsTable.setLayoutData(gd);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(heapFragmentsTable, SWT.NONE);
      column.setText("");
      layout.addColumnData(new ColumnPixelData(0));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[0]));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[1]));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[2]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[2]));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[3]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[3]));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[4]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[4]));

      column = new TableColumn(heapFragmentsTable, SWT.RIGHT);
      column.setText(HEAP_FRAGMENTS_TABLE_HEADERS[5]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(HEAP_FRAGMENTS_TABLE_COLUMN_WIDTHS[5]));
   }

   private void createHeapChartSection()
   {
      Section section;
      Composite client;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Heap Usage");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      heapChart = new HeapPieChart(client, SWT.NONE);
      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = 210;
      gd.heightHint = 210;
      heapChart.setLayoutData(gd);
   }

   private void createBufferSizesSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      TableWrapData twd;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Available Heap Buffer Sizes");
      twd = new TableWrapData();
      twd.valign = TableWrapData.FILL;
      twd.grabVertical = true;
      section.setLayoutData(twd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      bufferSizesTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      bufferSizesTable.setHeaderVisible(true);
      bufferSizesTable.setLinesVisible(true);
      layout = new TableLayout();
      bufferSizesTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = BUFFER_SIZES_TABLE_COLUMN_WIDTHS[0] +
                     BUFFER_SIZES_TABLE_COLUMN_WIDTHS[1] +
                     BUFFER_SIZES_TABLE_COLUMN_WIDTHS[2];
      gd.heightHint = BUFFER_SIZES_TABLE_HEIGHT;
      bufferSizesTable.setLayoutData(gd);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(bufferSizesTable, SWT.NONE);
      column.setText("");
      layout.addColumnData(new ColumnPixelData(0));

      column = new TableColumn(bufferSizesTable, SWT.RIGHT);
      column.setText(BUFFER_SIZES_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(BUFFER_SIZES_TABLE_COLUMN_WIDTHS[0]));

      column = new TableColumn(bufferSizesTable, SWT.RIGHT);
      column.setText(BUFFER_SIZES_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(BUFFER_SIZES_TABLE_COLUMN_WIDTHS[1]));

      column = new TableColumn(bufferSizesTable, SWT.RIGHT);
      column.setText(BUFFER_SIZES_TABLE_HEADERS[2]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(BUFFER_SIZES_TABLE_COLUMN_WIDTHS[2]));
   }

   public void dispose()
   {
      toolkit.dispose();
   }

   public void setFocus()
   {
      updateActionBars();
   }

   private void updateActionBars()
   {
      IActionBars bars = editor.getEditorSite().getActionBars();
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAllAction);
      bars.updateActionBars();
   }

   public void refresh()
   {
      // Refresh form title.
      form.setText(heap.toString());

      // Refresh information section.
      refreshInfoSection();

      // Refresh heap fragments section.
      refreshHeapFragmentsSection();

      // Refresh heap chart section.
      refreshHeapChartSection();

      // Refresh heap buffer sizes section.
      refreshBufferSizesSection();

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      long totalSize = heap.getSize();
      long freeSize = heap.getFreeSize();
      long usedSize = heap.getUsedSize();
      long peakUsedSize = heap.getPeakUsedSize();
      long requestedSize = heap.getRequestedSize();

      killedText.setText(StringUtils.toKilledString(heap.isKilled()));
      idText.setText(StringUtils.toHexString(heap.getId()));
      ownerText.setText(StringUtils.toHexString(heap.getOwner()));
      sidText.setText(StringUtils.toHexString(heap.getSid()));
      totalSizeText.setText(Long.toString(totalSize));
      freeSizeText.setText(Long.toString(freeSize) + " (" +
            StringUtils.toPercentString(freeSize, totalSize) + ")");
      usedSizeText.setText(Long.toString(usedSize) + " (" +
            StringUtils.toPercentString(usedSize, totalSize) + ")");
      peakUsedSizeText.setText(Long.toString(peakUsedSize) + " (" +
            StringUtils.toPercentString(peakUsedSize, totalSize) + ")");
      requestedSizeText.setText(Long.toString(requestedSize) + " (" +
            StringUtils.toPercentString(requestedSize, totalSize) + ")");
      largestFreeText.setText(Long.toString(heap.getLargestFree()));
      largeThresholdText.setText(Long.toString(heap.getLargeThreshold()));
      privateText.setText(Integer.toString(heap.getPrivate()));
      sharedText.setText(Integer.toString(heap.getShared()));
      mallocFailedText.setText(Integer.toString(heap.getMallocFailed()));
   }

   private void refreshHeapFragmentsSection()
   {
      heapFragmentsTable.setRedraw(false);
      heapFragmentsTable.removeAll();
      HeapFragmentInfo[] heapFragments = heap.getHeapFragments();
      for (int i = 0; i < heapFragments.length; i++)
      {
         HeapFragmentInfo fragment = heapFragments[i];
         long address = fragment.getAddress();
         long size = fragment.getSize();
         long used = fragment.getUsedSize();
         long free = size - used;
         long requested = fragment.getRequestedSize();

         TableItem item = new TableItem(heapFragmentsTable, SWT.NONE);
         item.setText(1, StringUtils.toHexString(address));
         item.setText(2, StringUtils.toHexString(address + size));
         item.setText(3, Long.toString(size));
         item.setText(4, Long.toString(free) + " (" +
               StringUtils.toPercentString(free, size) + ")");
         item.setText(5, Long.toString(used) + " (" +
               StringUtils.toPercentString(used, size) + ")");
         item.setText(6, Long.toString(requested) + " (" +
               StringUtils.toPercentString(requested, size) + ")");
      }
      heapFragmentsTable.setRedraw(true);
   }

   private void refreshHeapChartSection()
   {
      heapChart.update(heap);
   }

   private void refreshBufferSizesSection()
   {
      bufferSizesTable.setRedraw(false);
      bufferSizesTable.removeAll();
      HeapBufferSizeInfo[] bufferSizes = heap.getHeapBufferSizes();
      for (int i = 0; i < bufferSizes.length; i++)
      {
         HeapBufferSizeInfo bufferSize = bufferSizes[i];
         TableItem item = new TableItem(bufferSizesTable, SWT.NONE);
         item.setText(1, Long.toString(bufferSize.getSize()));
         item.setText(2, Long.toString(bufferSize.getAllocated()));
         item.setText(3, Long.toString(bufferSize.getFree()));
      }
      bufferSizesTable.setRedraw(true);
   }

   public Control getControl()
   {
      return form;
   }

   static class HeapPieChart extends Canvas
   {
      private final IDeviceRenderer deviceRenderer;

      private final Chart chart;

      private PieSeries pieSeries;

      private GeneratedChartState chartState;

      public HeapPieChart(Composite parent, int style)
      {
         super(parent, style | SWT.NO_BACKGROUND);

         try
         {
            deviceRenderer = PluginSettings.instance().getDevice("dv.SWT");
         }
         catch (ChartException e)
         {
            throw new RuntimeException("Invalid graphics device: SWT is required.");
         }

         chart = createChart();
         addPaintListener(new PaintHandler());
      }

      public void update(Heap heap)
      {
         double totalMemory;
         double freeMemory;
         double usedMemory;
         double peakUsedMemory;
         double requestedMemory;
         double overheadMemory;
         double freedMemory;
         double unusedMemory;
         double[] values;

         totalMemory = heap.getSize();
         freeMemory = heap.getFreeSize();
         usedMemory = heap.getUsedSize();
         peakUsedMemory = heap.getPeakUsedSize();
         requestedMemory = heap.getRequestedSize();
         overheadMemory = usedMemory - requestedMemory;
         freedMemory = peakUsedMemory - usedMemory;
         unusedMemory = freeMemory - freedMemory;

         values = new double[]
            {
               requestedMemory/totalMemory,
               overheadMemory/totalMemory,
               freedMemory/totalMemory,
               unusedMemory/totalMemory
            };
         pieSeries.setDataSet(NumberDataSetImpl.create(values));

         chartState = null;
         redraw();
      }

      private Chart createChart()
      {
         ChartWithoutAxes pieChart;
         Legend legend;
         String[] labels;
         Series categorySeries;
         SeriesDefinition sdX;
         SeriesDefinition sdY;
         TextAlignment ta;
         FontDefinition fd;

         pieChart = ChartWithoutAxesImpl.create();
         pieChart.setType("Pie Chart");
         pieChart.setSubType("Standard Pie Chart");
         pieChart.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
         pieChart.getBlock().setAnchor(Anchor.NORTH_LITERAL);
         pieChart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
         pieChart.getBlock().getInsets().set(0, 0, 0, 0);
         pieChart.getPlot().setAnchor(Anchor.NORTH_LITERAL);
         pieChart.getPlot().getClientArea().setBackground(ColorDefinitionImpl.WHITE());
         pieChart.getPlot().getInsets().set(0, 0, 0, 0);
         ta = TextAlignmentImpl.create();
         ta.setHorizontalAlignment(HorizontalAlignment.CENTER_LITERAL);
         fd = FontDefinitionImpl.create("", ChartModel.LABEL_FONT_SIZE,
               false, false, false, false, false, 0.0, ta);
         pieChart.getTitle().getLabel().getCaption().setFont(fd);
         pieChart.getTitle().getLabel().getCaption().setValue("");

         legend = pieChart.getLegend();
         legend.setAnchor(Anchor.NORTH_WEST_LITERAL);
         legend.setPosition(Position.RIGHT_LITERAL);
         legend.getInsets().set(0, 0, 0, 0);
         legend.getOutline().setStyle(LineStyle.SOLID_LITERAL);
         legend.getOutline().setVisible(false);
         legend.getText().getFont().setSize(ChartModel.LEGEND_FONT_SIZE);

         categorySeries = SeriesImpl.create();
         labels = new String[] {"Requested", "Overhead", "Freed", "Unused"};
         categorySeries.setDataSet(TextDataSetImpl.create(labels));

         pieSeries = (PieSeries) PieSeriesImpl.create();
         pieSeries.setSeriesIdentifier("");
         pieSeries.setExplosion(0);
         pieSeries.setSliceOutline(ColorDefinitionImpl.TRANSPARENT());
         pieSeries.setLabelPosition(Position.INSIDE_LITERAL);
         pieSeries.getLabel().setVisible(true);
         ta = TextAlignmentImpl.create();
         ta.setHorizontalAlignment(HorizontalAlignment.CENTER_LITERAL);
         fd = FontDefinitionImpl.create("", ChartModel.LABEL_FONT_SIZE,
               false, false, false, false, false, 0.0, ta);
         pieSeries.getLabel().getCaption().setFont(fd);
         pieSeries.setDataSet(NumberDataSetImpl.create(new double[] {0, 0, 0, 0}));

         sdX = SeriesDefinitionImpl.create();
         sdX.getSeriesPalette().getEntries().clear();
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(35, 151, 151));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(46, 197, 200));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(160, 190, 194));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(192, 237, 238));
         sdX.setQuery(QueryImpl.create(""));
         sdX.getSeries().add(categorySeries);

         sdY = SeriesDefinitionImpl.create();
         sdY.getSeriesPalette().update(1);
         sdY.setQuery(QueryImpl.create(""));
         sdY.setFormatSpecifier(JavaNumberFormatSpecifierImpl.create("##.0%"));
         sdY.getSeries().add(pieSeries);

         sdX.getSeriesDefinitions().add(sdY);
         pieChart.getSeriesDefinitions().add(sdX);

         return pieChart;
      }

      private void createChartState()
      {
         Rectangle rect;
         Bounds bounds;

         rect = getClientArea();
         bounds = BoundsImpl.create(rect.x + 2,
                                    rect.y + 2,
                                    rect.width - 4,
                                    rect.height - 4);
         bounds.scale(72d / deviceRenderer.getDisplayServer().getDpiResolution());

         try
         {
            Generator generator;
            RunTimeContext rtc;

            generator = Generator.instance();
            rtc = generator.prepare(chart,
                                    null,
                                    null,
                                    ULocale.getDefault());
            chartState = generator.build(deviceRenderer.getDisplayServer(),
                                         chart,
                                         bounds,
                                         null,
                                         rtc,
                                         null);
         }
         catch (ChartException e)
         {
            e.printStackTrace();
         }
      }

      private class PaintHandler implements PaintListener
      {
         public void paintControl(PaintEvent event)
         {
            Image image;
            GC gc;

            image = new Image(PlatformUI.getWorkbench().getDisplay(), getBounds());
            gc = new GC(image);
            deviceRenderer.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);

            if (chartState == null)
            {
               createChartState();
            }

            try
            {
               Generator generator = Generator.instance();
               generator.render(deviceRenderer, chartState);
            }
            // XXX: We catch Exception here instead of ChartException to support
            // Solaris machines without the Cairo graphics library. Even with
            // -DR31ENHANCE=false, rendering a BIRT pie chart requires the Cairo
            // graphics library.
            catch (Exception e)
            {
               SystemBrowserPlugin.log(e);
            }

            event.gc.drawImage(image, 0, 0);
            gc.dispose();
            image.dispose();
         }
      }
   }
}
