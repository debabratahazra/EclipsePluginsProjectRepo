/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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
import com.ose.system.Pool;
import com.ose.system.PoolBufferSizeInfo;
import com.ose.system.PoolFragmentInfo;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.charts.ChartModel;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class PoolForm implements IForm
{
   private static final String[] POOL_FRAGMENTS_TABLE_HEADERS =
      {
         "Start Address", "End Address", "Size", "Free",
         "Used Total Buffers", "Used Stack Buffers", "Used Signal Buffers"
      };
   private static final int[] POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS =
      {90, 80, 70, 110, 110, 110, 110};
   private static final int POOL_FRAGMENTS_TABLE_HEIGHT = 70;

   private static final int STACK_SIZES_TABLE_COLUMN_WIDTH = 80;
   private static final int STACK_SIZES_TABLE_HEIGHT = 40;

   private static final int SIGNAL_SIZES_TABLE_COLUMN_WIDTH = 80;
   private static final int SIGNAL_SIZES_TABLE_HEIGHT = 40;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Pool pool;

   // TODO: Add sidText when supported.

   private Text killedText;
   private Text idText;
   private Text totalSizeText;
   private Text freeSizeText;
   private Text usedSizeText;
   private Table poolFragmentsTable;
   private Table stackSizesTable;
   private Table signalSizesTable;
   private PoolPieChart poolChart;

   private Action copyAction;
   private Action selectAllAction;

   public PoolForm(IEditorPart editor, Pool pool)
   {
      if ((editor == null) || (pool == null))
      {
         throw new NullPointerException();
      }
      this.editor = editor;
      this.pool = pool;
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

      // Create pool fragments section.
      createPoolFragmentsSection();

      // Create pool chart section.
      createPoolChartSection();

      // Create stack sizes section.
      createStackSizesSection();

      // Create signal sizes section.
      createSignalSizesSection();

      // Create global actions.
      copyActionMux = new ActionMultiplexer();
      copyActionMux.addAction(new TableCopyAction(poolFragmentsTable));
      copyActionMux.addAction(new TableCopyAction(stackSizesTable));
      copyActionMux.addAction(new TableCopyAction(signalSizesTable));
      copyAction = copyActionMux;
      selectAllActionMux = new ActionMultiplexer();
      selectAllActionMux.addAction(new TableSelectAllAction(poolFragmentsTable));
      selectAllActionMux.addAction(new TableSelectAllAction(stackSizesTable));
      selectAllActionMux.addAction(new TableSelectAllAction(signalSizesTable));
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
      section.setText("Pool Information");
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

      toolkit.createLabel(client, "Pool ID:");
      idText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      idText.setLayoutData(twd);

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
   }

   private void createPoolFragmentsSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Pool Fragments");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      poolFragmentsTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      poolFragmentsTable.setHeaderVisible(true);
      poolFragmentsTable.setLinesVisible(true);
      layout = new TableLayout();
      poolFragmentsTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[0] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[1] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[2] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[3] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[4] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[5] +
                     POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[6];
      gd.heightHint = POOL_FRAGMENTS_TABLE_HEIGHT;
      poolFragmentsTable.setLayoutData(gd);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(poolFragmentsTable, SWT.NONE);
      column.setText("");
      layout.addColumnData(new ColumnPixelData(0));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[0]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[1]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[2]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[2]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[3]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[3]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[4]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[4]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[5]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[5]));

      column = new TableColumn(poolFragmentsTable, SWT.RIGHT);
      column.setText(POOL_FRAGMENTS_TABLE_HEADERS[6]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(POOL_FRAGMENTS_TABLE_COLUMN_WIDTHS[6]));
   }

   private void createPoolChartSection()
   {
      Section section;
      Composite client;
      TableWrapData twd;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Pool Usage");
      twd = new TableWrapData();
      twd.rowspan = 2;
      twd.valign = TableWrapData.FILL;
      section.setLayoutData(twd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      poolChart = new PoolPieChart(client, SWT.NONE);
      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = 210;
      gd.heightHint = 210;
      poolChart.setLayoutData(gd);
   }

   private void createStackSizesSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;
      PoolBufferSizeInfo[] stackSizes;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Configured Stack Buffer Sizes");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      stackSizesTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      stackSizesTable.setHeaderVisible(true);
      stackSizesTable.setLinesVisible(true);
      layout = new TableLayout();
      stackSizesTable.setLayout(layout);

      stackSizes = pool.getStackSizes();
      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = stackSizes.length * STACK_SIZES_TABLE_COLUMN_WIDTH;
      gd.heightHint = STACK_SIZES_TABLE_HEIGHT;
      stackSizesTable.setLayoutData(gd);

      for (int i = 0; i < stackSizes.length; i++)
      {
         column = new TableColumn(stackSizesTable, SWT.LEFT);
         column.setText("Size " + stackSizes[i].getSize());
         column.setMoveable(true);
         layout.addColumnData(new ColumnPixelData(STACK_SIZES_TABLE_COLUMN_WIDTH));
      }
   }

   private void createSignalSizesSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;
      PoolBufferSizeInfo[] signalSizes;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Configured Signal Buffer Sizes");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      signalSizesTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      signalSizesTable.setHeaderVisible(true);
      signalSizesTable.setLinesVisible(true);
      layout = new TableLayout();
      signalSizesTable.setLayout(layout);

      signalSizes = pool.getSignalSizes();
      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = signalSizes.length * SIGNAL_SIZES_TABLE_COLUMN_WIDTH;
      gd.heightHint = SIGNAL_SIZES_TABLE_HEIGHT;
      signalSizesTable.setLayoutData(gd);

      for (int i = 0; i < signalSizes.length; i++)
      {
         column = new TableColumn(signalSizesTable, SWT.LEFT);
         column.setText("Size " + signalSizes[i].getSize());
         column.setMoveable(true);
         layout.addColumnData(new ColumnPixelData(SIGNAL_SIZES_TABLE_COLUMN_WIDTH));
      }
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
      form.setText(pool.toString());

      // Refresh information section.
      refreshInfoSection();

      // Refresh pool fragments section.
      refreshPoolFragmentsSection();

      // Refresh pool chart section.
      refreshPoolChartSection();

      // Refresh stack sizes section.
      refreshStackSizesSection();

      // Refresh signal sizes section.
      refreshSignalSizesSection();

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      long totalSize = pool.getTotalSize();
      long freeSize = pool.getFreeSize();
      long usedSize = totalSize - freeSize;

      killedText.setText(StringUtils.toKilledString(pool.isKilled()));
      idText.setText(StringUtils.toHexString(pool.getId()));
      totalSizeText.setText(Long.toString(totalSize));
      freeSizeText.setText(Long.toString(freeSize) + " (" +
            StringUtils.toPercentString(freeSize, totalSize) + ")");
      usedSizeText.setText(Long.toString(usedSize) + " (" +
            StringUtils.toPercentString(usedSize, totalSize) + ")");
   }

   private void refreshPoolFragmentsSection()
   {
      poolFragmentsTable.setRedraw(false);
      poolFragmentsTable.removeAll();
      PoolFragmentInfo[] poolFragments = pool.getPoolFragments();
      for (int i = 0; i < poolFragments.length; i++)
      {
         PoolFragmentInfo fragment = poolFragments[i];
         long address = fragment.getAddress();
         long size = fragment.getSize();
         long usedStacks = fragment.getUsedStacks();
         long usedSignals = fragment.getUsedSignals();
         long usedTotal = usedStacks + usedSignals;
         long free = size - usedTotal;

         TableItem item = new TableItem(poolFragmentsTable, SWT.NONE);
         item.setText(1, StringUtils.toHexString(address));
         item.setText(2, StringUtils.toHexString(address + size));
         item.setText(3, Long.toString(size));
         item.setText(4, Long.toString(free) + " (" +
               StringUtils.toPercentString(free, size) + ")");
         item.setText(5, Long.toString(usedTotal) + " (" +
               StringUtils.toPercentString(usedTotal, size) + ")");
         item.setText(6, Long.toString(usedStacks) + " (" +
               StringUtils.toPercentString(usedStacks, size) + ")");
         item.setText(7, Long.toString(usedSignals) + " (" +
               StringUtils.toPercentString(usedSignals, size) + ")");
      }
      poolFragmentsTable.setRedraw(true);
   }

   private void refreshPoolChartSection()
   {
      poolChart.update(pool);
   }

   private void refreshStackSizesSection()
   {
      stackSizesTable.setRedraw(false);
      stackSizesTable.removeAll();
      TableItem item = new TableItem(stackSizesTable, SWT.NONE);
      PoolBufferSizeInfo[] stackSizes = pool.getStackSizes();
      for (int i = 0; i < stackSizes.length; i++)
      {
         item.setText(i, Integer.toString(stackSizes[i].getAllocated()));
      }
      stackSizesTable.setRedraw(true);
   }

   private void refreshSignalSizesSection()
   {
      signalSizesTable.setRedraw(false);
      signalSizesTable.removeAll();
      TableItem item = new TableItem(signalSizesTable, SWT.NONE);
      PoolBufferSizeInfo[] signalSizes = pool.getSignalSizes();
      for (int i = 0; i < signalSizes.length; i++)
      {
         item.setText(i, Integer.toString(signalSizes[i].getAllocated()));
      }
      signalSizesTable.setRedraw(true);
   }

   public Control getControl()
   {
      return form;
   }

   static class PoolPieChart extends Canvas
   {
      private final IDeviceRenderer deviceRenderer;

      private final Chart chart;

      private PieSeries pieSeries;

      private GeneratedChartState chartState;

      public PoolPieChart(Composite parent, int style)
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

      public void update(Pool pool)
      {
         PoolBufferSizeInfo[] signalBufferSizes;
         PoolBufferSizeInfo[] stackBufferSizes;
         double signalMemory = 0;
         double stackMemory = 0;
         double totalMemory;
         double unusedMemory;
         double otherMemory;
         double[] values;

         signalBufferSizes = pool.getSignalSizes();
         stackBufferSizes = pool.getStackSizes();

         for (int i = 0; i < signalBufferSizes.length; i++)
         {
            signalMemory += signalBufferSizes[i].getAllocated() * 
               (signalBufferSizes[i].getSize() + pool.getSignalAdmSize());
         }

         for (int i = 0; i < stackBufferSizes.length; i++)
         {
            stackMemory += stackBufferSizes[i].getAllocated() *
               (stackBufferSizes[i].getSize() + pool.getStackAdmSize());
         }

         totalMemory = pool.getTotalSize();
         unusedMemory = pool.getFreeSize();
         otherMemory = totalMemory - unusedMemory - signalMemory - stackMemory;

         values = new double[]
            {
               signalMemory/totalMemory,
               stackMemory/totalMemory,
               otherMemory/totalMemory,
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
         labels = new String[] {"Signal", "Stack", "Other", "Unused"};
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
               ColorDefinitionImpl.create(140, 149, 95));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(175, 175, 65));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(195, 185, 160));
         sdX.getSeriesPalette().getEntries().add(
               ColorDefinitionImpl.create(200, 208, 136));
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
