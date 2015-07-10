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

package com.ose.system.ui.views.pool;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.system.Gate;
import com.ose.system.Pool;
import com.ose.system.ProcessInfo;
import com.ose.system.ServiceException;
import com.ose.system.SignalFilter;
import com.ose.system.SignalInfo;
import com.ose.system.StackFilter;
import com.ose.system.StackInfo;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.AutoUpdateManager;
import com.ose.system.ui.IUpdatable;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.charts.ChartModelInput;
import com.ose.system.ui.editors.ChartModelEditorInput;
import com.ose.system.ui.util.AbstractShowMemoryAction;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.ExtendedSignalInfo;
import com.ose.system.ui.util.ExtendedStackInfo;
import com.ose.system.ui.util.Grid;
import com.ose.system.ui.util.GridItem;
import com.ose.system.ui.util.IPropertyChangeSource;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class PoolView extends ViewPart
{
   private static final int AUTO_UPDATE_PRIORITY = 2;

   public static final int COLUMN_SIGNAL_BROKEN_BUFFER = 1;

   public static final int COLUMN_SIGNAL_NUMBER = 2;

   public static final int COLUMN_SIGNAL_OWNER_ID = 3;

   public static final int COLUMN_SIGNAL_OWNER_NAME = 4;

   public static final int COLUMN_SIGNAL_SENDER_ID = 5;

   public static final int COLUMN_SIGNAL_SENDER_NAME = 6;

   public static final int COLUMN_SIGNAL_ADDRESSEE_ID = 7;

   public static final int COLUMN_SIGNAL_ADDRESSEE_NAME = 8;

   public static final int COLUMN_SIGNAL_SIZE = 9;

   public static final int COLUMN_SIGNAL_BUFFER_SIZE = 10;

   public static final int COLUMN_SIGNAL_ADDRESS = 11;

   public static final int COLUMN_SIGNAL_STATUS = 12;

   public static final int COLUMN_STACK_OWNER_ID = 1;

   public static final int COLUMN_STACK_OWNER_NAME = 2;

   public static final int COLUMN_STACK_ADDRESS = 3;

   public static final int COLUMN_STACK_SIZE = 4;

   public static final int COLUMN_STACK_BUFFER_SIZE = 5;

   public static final int COLUMN_STACK_USED = 6;

   public static final int COLUMN_STACK_RELATIVE_USED = 7;

   private static final int TAB_SIGNAL_CHARTS = 0;

   private static final int TAB_STACK_CHARTS = 1;

   private static final int TAB_SIGNAL_LIST = 2;

   private static final int TAB_STACK_LIST = 3;

   private static final String DATA_CHART_MODEL_INPUT = "DATA_CHART_MODEL_INPUT";

   private static final String DATA_PROPERTY_NAME = "DATA_PROPERTY_NAME";

   private static final String DATA_EDITOR_TITLE = "DATA_EDITOR_TITLE";

   private static final String SIGNAL_MEMORY_PER_SIGNAL_NUMBER_CHART =
      "com.ose.system.ui.charts.SignalMemoryPerSignalNumberChart";

   private static final String SIGNAL_MEMORY_PER_SIGNAL_SIZE_CHART =
      "com.ose.system.ui.charts.SignalMemoryPerSignalSizeChart";

   private static final String SIGNAL_MEMORY_PER_BUFFER_SIZE_CHART =
      "com.ose.system.ui.charts.SignalMemoryPerBufferSizeChart";

   private static final String SIGNAL_MEMORY_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.SignalMemoryPerProcessChart";

   private static final String SIGNALS_PER_SIGNAL_NUMBER_CHART =
      "com.ose.system.ui.charts.SignalsPerSignalNumberChart";

   private static final String SIGNALS_PER_SIGNAL_SIZE_CHART =
      "com.ose.system.ui.charts.SignalsPerSignalSizeChart";

   private static final String SIGNALS_PER_BUFFER_SIZE_CHART =
      "com.ose.system.ui.charts.SignalsPerBufferSizeChart";

   private static final String SIGNALS_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.SignalsPerProcessChart";

   private static final String STACK_USAGE_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.StackUsagePerProcessChart";

   private static final String STACK_MEMORY_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.StackMemoryPerProcessChart";

   private static final String STACK_MEMORY_PER_BUFFER_SIZE_CHART =
      "com.ose.system.ui.charts.StackMemoryPerBufferSizeChart";

   private static final String STACKS_PER_BUFFER_SIZE_CHART =
      "com.ose.system.ui.charts.StacksPerBufferSizeChart";

   private static final String PROPERTY_SIGNAL_INFO = "PROPERTY_SIGNAL_INFO";

   private static final String PROPERTY_STACK_INFO = "PROPERTY_STACK_INFO";

   private static final String BUFFER_KILLED = "[ killed ]";

   private static final String BUFFER_FREE = "[ free ]";

   private static final String BUFFER_KERNEL = "[ kernel ]";

   private static final String BUFFER_NOT_SENT = "[ not sent ]";

   private static final String BUFFER_NOT_REDIRECTED = "[ not redirected ]";

   private static final String LINK_WITH_EDITOR = "LINK_WITH_EDITOR";

   private static final String TAB_SELECTED = "TAB_SELECTED";

   private static final String TOOL_TIP_TEXT = "Click to open in Editor";

   private Pool pool;

   private SignalFilter signalFilter;

   private StackFilter stackFilter;

   private TableViewer signalViewer;

   private TableViewer stackViewer;

   private SignalSorter signalSorter;

   private StackSorter stackSorter;

   private IMemento memento;

   private IPartListener editorPartHandler;

   private boolean linkingEnabled;

   private PropertyChangeSource propertyChangeSource;

   private PoolFilterAction poolFilterAction;
   private Action updateAction;
   private Action updateAutoAction;
   private Action toggleLinkingAction;
   private Action showSignalMemoryAction;
   private Action showStackMemoryAction;

   private TabFolder tabFolder;

   private ScrolledComposite signalChartScrollComp;
   private ScrolledComposite stackChartScrollComp;
   private Grid signalChartGrid;
   private Grid stackChartGrid;

   private CLabel warningSignalChartsLabel;
   private CLabel warningStackChartsLabel;
   private CLabel warningSignalListLabel;
   private CLabel warningStackListLabel;

   private ImageCache imageCache;
   private ImageDescriptor signalMemoryPerSignalNumberChartImage;
   private ImageDescriptor signalMemoryPerSignalSizeChartImage;
   private ImageDescriptor signalMemoryPerBufferSizeChartImage;
   private ImageDescriptor signalMemoryPerProcessChartImage;
   private ImageDescriptor signalsPerSignalNumberChartImage;
   private ImageDescriptor signalsPerSignalSizeChartImage;
   private ImageDescriptor signalsPerBufferSizeChartImage;
   private ImageDescriptor signalsPerProcessChartImage;
   private ImageDescriptor stackUsagePerProcessChartImage;
   private ImageDescriptor stackMemoryPerProcessChartImage;
   private ImageDescriptor stackMemoryPerBufferSizeChartImage;
   private ImageDescriptor stacksPerBufferSizeChartImage;

   private BackgroundImageHandler signalChartBackgroundImageHandler;
   private BackgroundImageHandler stackChartBackgroundImageHandler;
   private BackgroundImageHandler signalListBackgroundImageHandler;
   private BackgroundImageHandler stackListBackgroundImageHandler;

   public void createPartControl(Composite parent)
   {
      createTabFolder(parent);
      createActions();
      createSignalListContextMenu();
      createStackListContextMenu();
      contributeToActionBars();
      editorPartHandler = new EditorPartHandler();
      getSite().getPage().addPartListener(editorPartHandler);
      setContentDescription("Target: None, Pool: None, Signals: 0, Stacks: 0");
   }

   private void createTabFolder(Composite parent)
   {
      tabFolder = new TabFolder(parent, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

      createSignalChartsTab(tabFolder);
      createStackChartsTab(tabFolder);
      createSignalListTab(tabFolder);
      createStackListTab(tabFolder);
      tabFolder.addSelectionListener(new TabFolderSelectionHandler());

      if (memento != null)
      {
         Integer selection = memento.getInteger(TAB_SELECTED);
         if (selection != null)
         {
            tabFolder.setSelection(selection.intValue());
         }
      }
   }

   private void createSignalChartsTab(TabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      TabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      warningSignalChartsLabel = new CLabel(comp, SWT.NONE);
      warningSignalChartsLabel.setText("Broken Signal Buffers!");
      warningSignalChartsLabel.setImage(PlatformUI.getWorkbench()
            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningSignalChartsLabel.setVisible(false);
      warningSignalChartsLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      createSignalChartGrid(comp);

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Signal Charts");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_SIGNAL_CHART));
      tabItem.setControl(comp);
   }

   private void createStackChartsTab(TabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      TabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      warningStackChartsLabel = new CLabel(comp, SWT.NONE);
      warningStackChartsLabel.setText("Broken Stack Buffers!");
      warningStackChartsLabel.setImage(PlatformUI.getWorkbench()
            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningStackChartsLabel.setVisible(false);
      warningStackChartsLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      createStackChartGrid(comp);

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Stack Charts");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_STACK_CHART));
      tabItem.setControl(comp);
   }

   private void createSignalListTab(TabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      Composite subComp;
      TabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      warningSignalListLabel = new CLabel(comp, SWT.NONE);
      warningSignalListLabel.setText("Broken Signal Buffers!");
      warningSignalListLabel.setImage(PlatformUI.getWorkbench()
            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningSignalListLabel.setVisible(false);
      warningSignalListLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new FillLayout());
      subComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      signalSorter = new SignalSorter();
      signalViewer = new TableViewer(createSignalTable(subComp));
      signalViewer.setUseHashlookup(true);
      signalViewer.setContentProvider(new SignalContentProvider());
      signalViewer.setLabelProvider(new SignalLabelProvider());
      signalViewer.setSorter(signalSorter);
      signalViewer.addSelectionChangedListener(
            new SignalListSelectionChangedHandler());
      signalListBackgroundImageHandler = new PoolBackgroundImageHandler(
            signalViewer.getTable());

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Signal List");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_SIGNAL_LIST));
      tabItem.setControl(comp);
   }

   private void createStackListTab(TabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      Composite subComp;
      TabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      warningStackListLabel = new CLabel(comp, SWT.NONE);
      warningStackListLabel.setText("Broken Stack Buffers!");
      warningStackListLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningStackListLabel.setVisible(false);
      warningStackListLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new FillLayout());
      subComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      stackSorter = new StackSorter();
      stackViewer = new TableViewer(createStackTable(subComp));
      stackViewer.setUseHashlookup(true);
      stackViewer.setContentProvider(new StackContentProvider());
      stackViewer.setLabelProvider(new StackLabelProvider());
      stackViewer.setSorter(stackSorter);
      stackViewer.addSelectionChangedListener(
            new StackListSelectionChangedHandler());
      stackListBackgroundImageHandler = new PoolBackgroundImageHandler(
            stackViewer.getTable());

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Stack List");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_STACK_LIST));
      tabItem.setControl(comp);
   }

   private void createSignalChartGrid(Composite parent)
   {
      Composite comp;
      GridItem item;
      int widthHint = 580;
      int heigthHint = 200;

      signalChartScrollComp = new ScrolledComposite(
            parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      signalChartScrollComp.setLayout(new GridLayout());
      signalChartScrollComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      comp = new Composite(signalChartScrollComp, SWT.NONE);
      comp.setBackground(
            getSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
      comp.setLayout(new GridLayout());
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      signalChartBackgroundImageHandler = new PoolBackgroundImageHandler(comp);

      signalChartGrid = new Grid(comp, SWT.NONE);
      signalChartGrid.setLayout(new GridLayout(4, true));
      signalChartGrid.setLayoutData(new GridData(widthHint, heigthHint));
      signalChartGrid.setEnabled(false);
      signalChartGrid.setVisible(false);
      signalChartGrid.addSelectionListener(new ChartGridSelectionHandler());

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNAL_MEMORY_PER_SIGNAL_NUMBER_CHART);
      item.setText("Bytes / Signal Number");
      item.setImage(imageCache.getImage(signalMemoryPerSignalNumberChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNAL_MEMORY_PER_SIGNAL_SIZE_CHART);
      item.setText("Bytes / Signal Size");
      item.setImage(imageCache.getImage(signalMemoryPerSignalSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNAL_MEMORY_PER_BUFFER_SIZE_CHART);
      item.setText("Bytes / Buffer Size");
      item.setImage(imageCache.getImage(signalMemoryPerBufferSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNAL_MEMORY_PER_PROCESS_CHART);
      item.setText("Bytes / Process");
      item.setImage(imageCache.getImage(signalMemoryPerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNALS_PER_SIGNAL_NUMBER_CHART);
      item.setText("Signals / Signal Number");
      item.setImage(imageCache.getImage(signalsPerSignalNumberChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNALS_PER_SIGNAL_SIZE_CHART);
      item.setText("Signals / Signal Size");
      item.setImage(imageCache.getImage(signalsPerSignalSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNALS_PER_BUFFER_SIZE_CHART);
      item.setText("Signals / Buffer Size");
      item.setImage(imageCache.getImage(signalsPerBufferSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(signalChartGrid, SWT.NONE);
      item.setData(SIGNALS_PER_PROCESS_CHART);
      item.setText("Signals / Process");
      item.setImage(imageCache.getImage(signalsPerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      signalChartScrollComp.setContent(comp);
      signalChartScrollComp.setExpandHorizontal(true);
      signalChartScrollComp.setExpandVertical(true);
      signalChartScrollComp.setMinSize(0, 0);
   }

   private void createStackChartGrid(Composite parent)
   {
      Composite comp;
      GridItem item;
      int widthHint = 580;
      int heigthHint = 105;

      stackChartScrollComp = new ScrolledComposite(
            parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      stackChartScrollComp.setLayout(new GridLayout());
      stackChartScrollComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      comp = new Composite(stackChartScrollComp, SWT.NONE);
      comp.setBackground(
            getSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
      comp.setLayout(new GridLayout());
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      stackChartBackgroundImageHandler = new PoolBackgroundImageHandler(comp);

      stackChartGrid = new Grid(comp, SWT.NONE);
      stackChartGrid.setLayout(new GridLayout(4, true));
      stackChartGrid.setLayoutData(new GridData(widthHint, heigthHint));
      stackChartGrid.setEnabled(false);
      stackChartGrid.setVisible(false);
      stackChartGrid.addSelectionListener(new ChartGridSelectionHandler());

      item = new GridItem(stackChartGrid, SWT.NONE);
      item.setData(STACK_USAGE_PER_PROCESS_CHART);
      item.setText("Stack Usage / Process");
      item.setImage(imageCache.getImage(stackUsagePerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(stackChartGrid, SWT.NONE);
      item.setData(STACK_MEMORY_PER_PROCESS_CHART);
      item.setText("Bytes / Process");
      item.setImage(imageCache.getImage(stackMemoryPerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(stackChartGrid, SWT.NONE);
      item.setData(STACK_MEMORY_PER_BUFFER_SIZE_CHART);
      item.setText("Bytes / Buffer Size");
      item.setImage(imageCache.getImage(stackMemoryPerBufferSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(stackChartGrid, SWT.NONE);
      item.setData(STACKS_PER_BUFFER_SIZE_CHART);
      item.setText("Stacks / Buffer Size");
      item.setImage(imageCache.getImage(stacksPerBufferSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      stackChartScrollComp.setContent(comp);
      stackChartScrollComp.setExpandHorizontal(true);
      stackChartScrollComp.setExpandVertical(true);
      stackChartScrollComp.setMinSize(0, 0);
   }

   private Table createSignalTable(Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;

      comp = new Composite(parent, SWT.NONE);
      layout = new TableColumnLayout();
      comp.setLayout(layout);
      table = new Table(comp,
            SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      // Dummy first column to get real first column center-aligned on Windows.
      column = new TableColumn(table, SWT.NONE);
      column.setText("");
      layout.setColumnData(column, new ColumnWeightData(0, 0, false));

      column = new TableColumn(table, SWT.CENTER);
      column.setImage(SharedImages.get(SharedImages.IMG_OBJ_EXCLAMATION));
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_BROKEN_BUFFER));
      layout.setColumnData(column, new ColumnWeightData(0, 16, false));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Signal Number");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Owner ID");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_OWNER_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Owner Name");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_OWNER_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Sender ID");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_SENDER_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Sender Name");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_SENDER_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Addressee ID");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_ADDRESSEE_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Addressee Name");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_ADDRESSEE_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Buffer Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_BUFFER_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Address");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Status");
      column.setMoveable(true);
      column.addSelectionListener(
            new SignalListSelectionHandler(COLUMN_SIGNAL_STATUS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   private Table createStackTable(Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;

      comp = new Composite(parent, SWT.NONE);
      layout = new TableColumnLayout();
      comp.setLayout(layout);
      table = new Table(comp,
            SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(table, SWT.NONE);
      column.setText("");
      layout.setColumnData(column, new ColumnWeightData(0, 0, false));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Owner ID");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_OWNER_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Owner Name");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_OWNER_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Address");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Buffer Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_BUFFER_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Peak Usage");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_USED));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Peak Usage / Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new StackListSelectionHandler(COLUMN_STACK_RELATIVE_USED));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   private void createActions()
   {
      poolFilterAction = new PoolFilterAction();
      updateAction = new UpdateAction();
      updateAutoAction = new ToggleUpdateAutoAction();
      toggleLinkingAction = new ToggleLinkingAction();
      showSignalMemoryAction = new ShowSignalMemoryAction();
      showStackMemoryAction = new ShowStackMemoryAction();
   }

   private void createSignalListContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new SignalListContextMenuHandler());
      menu = menuMgr.createContextMenu(signalViewer.getControl());
      signalViewer.getControl().setMenu(menu);
      getSite().registerContextMenu(menuMgr, signalViewer);
   }

   private void createStackListContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new StackListContextMenuHandler());
      menu = menuMgr.createContextMenu(stackViewer.getControl());
      stackViewer.getControl().setMenu(menu);
      getSite().registerContextMenu(menuMgr, stackViewer);
   }

   private void contributeToActionBars()
   {
      IActionBars bars;
      ActionMultiplexer copyActionMux;
      ActionMultiplexer selectAllActionMux;

      bars = getViewSite().getActionBars();
      fillLocalPullDown(bars.getMenuManager());
      fillLocalToolBar(bars.getToolBarManager());

      copyActionMux = new ActionMultiplexer();
      copyActionMux.addAction(new TableCopyAction(signalViewer));
      copyActionMux.addAction(new TableCopyAction(stackViewer));
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyActionMux);

      selectAllActionMux = new ActionMultiplexer();
      selectAllActionMux.addAction(new TableSelectAllAction(signalViewer));
      selectAllActionMux.addAction(new TableSelectAllAction(stackViewer));
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                                  selectAllActionMux);
   }

   private void fillLocalPullDown(IMenuManager manager)
   {
      manager.add(poolFilterAction);
      manager.add(toggleLinkingAction);
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(poolFilterAction);
      manager.add(new UpdateDropDownAction());
      manager.add(toggleLinkingAction);
   }

   public void init(IViewSite site, IMemento memento) throws PartInitException
   {
      super.init(site, memento);
      this.memento = memento;

      linkingEnabled = true;
      if (memento != null)
      {
         Integer link = memento.getInteger(LINK_WITH_EDITOR);
         if (link != null)
         {
            linkingEnabled = (link.intValue() == 1);
         }
      }

      propertyChangeSource = new PropertyChangeSource();

      imageCache = new ImageCache();
      signalMemoryPerSignalNumberChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signal_memory_per_signal_number.gif");
      signalMemoryPerSignalSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signal_memory_per_signal_size.gif");
      signalMemoryPerBufferSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signal_memory_per_buffer_size.gif");
      signalMemoryPerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signal_memory_per_process.gif");
      signalsPerSignalNumberChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signals_per_signal_number.gif");
      signalsPerSignalSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signals_per_signal_size.gif");
      signalsPerBufferSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signals_per_buffer_size.gif");
      signalsPerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_signals_per_process.gif");
      stackUsagePerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_stack_usage_per_process.gif");
      stackMemoryPerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_stack_memory_per_process.gif");
      stackMemoryPerBufferSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_stack_memory_per_buffer_size.gif");
      stacksPerBufferSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_stacks_per_buffer_size.gif");
   }

   public void setFocus()
   {
      tabFolder.setFocus();
   }

   public void saveState(IMemento memento)
   {
      super.saveState(memento);
      memento.putInteger(LINK_WITH_EDITOR, linkingEnabled ? 1 : 0);
      memento.putInteger(TAB_SELECTED, tabFolder.getSelectionIndex());
   }

   public void dispose()
   {
      getSite().getPage().removePartListener(editorPartHandler);
      propertyChangeSource.firePropertyChange(
            IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED, null, null);
      AutoUpdateManager.getInstance().removeUpdatable(
            SystemBrowserPlugin.POOL_VIEW_ID);
      imageCache.dispose();
      super.dispose();
   }

   public void show(Pool pool)
   {
      poolFilterAction.run(pool);
   }

   public boolean isLinkingEnabled()
   {
      return linkingEnabled;
   }

   public void setLinkingEnabled(boolean enabled)
   {
      linkingEnabled = enabled;
      if (enabled)
      {
         IEditorPart editor = getSite().getPage().getActiveEditor();
         if (editor != null)
         {
            editorActivated(editor);
         }
      }
   }

   private void editorActivated(IEditorPart editor)
   {
      if (isLinkingEnabled())
      {
         IEditorInput input = editor.getEditorInput();
         if (input instanceof ChartModelEditorInput)
         {
            ChartModelEditorInput chartModelInput;
            String className;
            GridItem item;

            if (signalChartGrid.isDisposed() || stackChartGrid.isDisposed())
            {
               return;
            }

            chartModelInput = (ChartModelEditorInput) input;
            className = chartModelInput.getChartModel().getClass().getName();

            item = signalChartGrid.getItemMatchingData(className);
            if (item != null)
            {
               if (tabFolder.getSelectionIndex() != TAB_SIGNAL_CHARTS)
               {
                  tabFolder.setSelection(TAB_SIGNAL_CHARTS);
               }
               signalChartGrid.setSelectedItem(item);
            }
            else
            {
               item = stackChartGrid.getItemMatchingData(className);
               if (item != null)
               {
                  if (tabFolder.getSelectionIndex() != TAB_STACK_CHARTS)
                  {
                     tabFolder.setSelection(TAB_STACK_CHARTS);
                  }
                  stackChartGrid.setSelectedItem(item);
               }
            }
         }
      }
   }

   private void update()
   {
      if ((pool != null) && !pool.isKilled())
      {
         Job job = new GetPoolStatisticsJob(pool.getTarget(), pool.getId(),
            signalFilter, stackFilter);
         IWorkbenchSiteProgressService siteService =
            (IWorkbenchSiteProgressService) getSite().
            getAdapter(IWorkbenchSiteProgressService.class);
         siteService.schedule(job, 0, true);
      }
   }

   private void asyncExec(Runnable runnable)
   {
      if (!tabFolder.isDisposed())
      {
         tabFolder.getDisplay().asyncExec(runnable);
      }
   }

   static class ImageCache
   {
      private static final URL IMAGE_BASE_URL = Platform.getBundle(
            SystemBrowserPlugin.getUniqueIdentifier()).getEntry("/icons/");

      private final Map imageMap = new HashMap();

      public static ImageDescriptor createImageDescriptor(String prefix,
                                                          String name)
      {
         URL imageFileURL = null;

         try
         {
            imageFileURL = new URL(IMAGE_BASE_URL, prefix + name);
         }
         catch (MalformedURLException e)
         {
            SystemBrowserPlugin.log(e);
         }
         return ImageDescriptor.createFromURL(imageFileURL);
      }

      public Image getImage(ImageDescriptor descriptor)
      {
         Image image;

         if (descriptor == null)
         {
            return null;
         }
         image = (Image) imageMap.get(descriptor);
         if (image == null)
         {
            image = descriptor.createImage();
            imageMap.put(descriptor, image);
         }
         return image;
      }

      public void dispose()
      {
         for (Iterator i = imageMap.values().iterator(); i.hasNext();)
         {
            ((Image) i.next()).dispose();
         }
         imageMap.clear();
      }
   }

   static class PoolBackgroundImageHandler extends BackgroundImageHandler
   {
      PoolBackgroundImageHandler(Composite comp)
      {
         super(comp);
      }

      protected void drawBackgroundImage(Image image, GC gc, int xOffset,
            int yOffset)
      {
         String str1 = "Connect to a target in the System Browser";
         String str2 = "and then press the ";
         String str3 = " button in this view.";
         Image icon = SharedImages.get(SharedImages.IMG_TOOL_FILTER);
         Point p1 = gc.stringExtent(str1);
         Point p2 = gc.stringExtent(str2);
         Point p3 = gc.stringExtent(str3);
         Rectangle imageRect = image.getBounds();
         Rectangle iconRect = icon.getBounds();
         int leading = gc.getFontMetrics().getLeading();
         int w1 = p1.x;
         int h1 = p1.y + leading;
         int w2 = p2.x + iconRect.width + p3.x;
         int h2 = Math.max(iconRect.height, Math.max(p2.y, p3.y) + leading);
         int w = Math.max(w1, w2);
         int h = h1 + h2;
         int x = xOffset + (imageRect.width - xOffset - w) / 2;
         int y = yOffset + (imageRect.height - yOffset - h) / 2;
         gc.drawString(str1, x, y);
         gc.drawString(str2, x, y + h1);
         gc.drawImage(icon, x + p2.x, y + h1);
         gc.drawString(str3, x + p2.x + iconRect.width, y + h1);
      }
   }

   class SignalListContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(showSignalMemoryAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class StackListContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(showStackMemoryAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SignalListSelectionHandler extends SelectionAdapter
   {
      private int column;

      SignalListSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         signalSorter.sortByColumn(column);
         signalViewer.refresh();
      }
   }

   class StackListSelectionHandler extends SelectionAdapter
   {
      private int column;

      StackListSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         stackSorter.sortByColumn(column);
         stackViewer.refresh();
      }
   }

   class SignalListSelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) event.getSelection();
         showSignalMemoryAction.setEnabled(selection.size() == 1);
      }
   }

   class StackListSelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) event.getSelection();
         showStackMemoryAction.setEnabled(selection.size() == 1);
      }
   }

   class ChartGridSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         IWorkbenchPage page = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getActivePage();
         Grid chartGrid = (Grid) event.widget;
         if ((page != null) && chartGrid.isEnabled())
         {
            try
            {
               ChartModelEditorInput editorInput = new ChartModelEditorInput(
                  propertyChangeSource,
                  (String) chartGrid.getData(DATA_PROPERTY_NAME),
                  (String) chartGrid.getData(DATA_EDITOR_TITLE),
                  Class.forName((String) event.item.getData()),
                  (ChartModelInput) chartGrid.getData(DATA_CHART_MODEL_INPUT));
               page.openEditor(editorInput,
                               SystemBrowserPlugin.CHART_MODEL_EDITOR_ID);
            }
            catch (Exception e)
            {
               throw new RuntimeException("Error opening pool chart editor", e);
            }
         }
      }
   }

   class TabFolderSelectionHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         switch (tabFolder.getSelectionIndex())
         {
            case TAB_SIGNAL_LIST:
               signalViewer.getTable().setFocus();
               break;
            case TAB_STACK_LIST:
               stackViewer.getTable().setFocus();
               break;
            default:
               break;
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }

   class EditorPartHandler implements IPartListener
   {
      public void partActivated(IWorkbenchPart part)
      {
         if (part instanceof IEditorPart)
         {
            editorActivated((IEditorPart) part);
         }
      }

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partOpened(IWorkbenchPart part) {}
   }

   class PropertyChangeSource implements IPropertyChangeSource
   {
      private PropertyChangeSupport propertyChangeSupport;

      PropertyChangeSource()
      {
         propertyChangeSupport = new PropertyChangeSupport(this);
      }

      public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener)
      {
         propertyChangeSupport.addPropertyChangeListener(propertyName,
                                                         listener);
      }

      public void removePropertyChangeListener(String propertyName,
               PropertyChangeListener listener)
      {
         propertyChangeSupport.removePropertyChangeListener(propertyName,
                                                            listener);
      }

      public void firePropertyChange(String propertyName,
                                     Object oldValue,
                                     Object newValue)
      {
         propertyChangeSupport.firePropertyChange(propertyName,
                                                  oldValue,
                                                  newValue);
      }
   }

   class PoolFilterAction extends Action
   {
      PoolFilterAction()
      {
         super("Filter...");
         setToolTipText("Filter");
         setImageDescriptor(SharedImages.DESC_TOOL_FILTER);
      }

      public void run()
      {
         setEnabled(false);
         Job job = new GetTargetsJob();
         job.schedule();
      }

      public void run(Pool pool)
      {
         setEnabled(false);
         Job job = new GetTargetsJob(pool);
         job.schedule();
      }
   }

   class UpdateDropDownAction extends Action implements IMenuCreator
   {
      private Menu menu;

      UpdateDropDownAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
         setMenuCreator(this);
      }

      public void dispose()
      {
         if (menu != null)
         {
            menu.dispose();
            menu = null;
         }
      }

      public Menu getMenu(Control parent)
      {
         ActionContributionItem item;

         if (menu != null)
         {
            menu.dispose();
         }
         menu = new Menu(parent);

         item = new ActionContributionItem(updateAction);
         item.fill(menu, -1);

         item = new ActionContributionItem(updateAutoAction);
         item.fill(menu, -1);

         return menu;
      }

      public Menu getMenu(Menu parent)
      {
         return null;
      }

      public void run()
      {
         if (updateAction.isEnabled())
         {
            updateAction.run();
         }
      }
   }

   class UpdateAction extends Action
   {
      UpdateAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
      }

      public void run()
      {
         update();
      }
   }

   class ToggleUpdateAutoAction extends Action
   {
      private final IUpdatable updatable = new Updatable();

      ToggleUpdateAutoAction()
      {
         super("Update Automatically", AS_CHECK_BOX);
         setToolTipText("Update Automatically");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE_AUTO);
      }

      public void run()
      {
         if (isChecked())
         {
            AutoUpdateManager.getInstance().addUpdatable(updatable);
         }
         else
         {
            AutoUpdateManager.getInstance().removeUpdatable(updatable.getId());
         }
      }

      private class Updatable implements IUpdatable
      {
         public String getId()
         {
            return SystemBrowserPlugin.POOL_VIEW_ID;
         }

         public int getPriority()
         {
            return PoolView.AUTO_UPDATE_PRIORITY;
         }

         public void update(IProgressMonitor monitor)
         {
            PoolView.this.update();
         }
      }
   }

   class ToggleLinkingAction extends Action
   {
      ToggleLinkingAction()
      {
         super("Link with Editor");
         setToolTipText("Link with Editor");
         setImageDescriptor(SharedImages.DESC_TOOL_LINK_EDITOR);
         setChecked(isLinkingEnabled());
      }

      public void run()
      {
         setLinkingEnabled(isChecked());
      }
   }

   class ShowSignalMemoryAction extends AbstractShowMemoryAction
   {
      ShowSignalMemoryAction()
      {
         super("Show Memory");
         setEnabled(false);
      }

      public void run()
      {
         ISelection selection;
         Object obj;

         selection = signalViewer.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();
         if (obj instanceof ExtendedSignalInfo)
         {
            try
            {
               ExtendedSignalInfo signal;
               int pid;

               signal = (ExtendedSignalInfo) obj;
               pid = signal.getSignalInfo().getOwner();
               if ((pid == 0) && pool.getTarget().hasSegmentSupport())
               {
                  pid = pool.getSid();
               }
               showMemory(pool.getTarget(),
                          pid,
                          signal.getSignalInfo().getAddress() & 0xFFFFFFFFL,
                          signal.getSignalInfo().getBufferSize() & 0xFFFFFFFFL);
            }
            catch (CoreException e)
            {
               SystemBrowserPlugin.log(e);
            }
         }
      }
   }

   class ShowStackMemoryAction extends AbstractShowMemoryAction
   {
      ShowStackMemoryAction()
      {
         super("Show Memory");
         setEnabled(false);
      }

      public void run()
      {
         ISelection selection;
         Object obj;

         selection = stackViewer.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();
         if (obj instanceof ExtendedStackInfo)
         {
            try
            {
               ExtendedStackInfo stack;
               int pid;

               stack = (ExtendedStackInfo) obj;
               pid = stack.getStackInfo().getPid();
               if ((pid == 0) && pool.getTarget().hasSegmentSupport())
               {
                  pid = pool.getSid();
               }
               showMemory(pool.getTarget(),
                          pid,
                          stack.getStackInfo().getAddress() & 0xFFFFFFFFL,
                          stack.getStackInfo().getBufferSize() & 0xFFFFFFFFL);
            }
            catch (CoreException e)
            {
               SystemBrowserPlugin.log(e);
            }
         }
      }
   }

   class GetTargetsJob extends Job
   {
      private Pool pool;

      GetTargetsJob()
      {
         super("Finding Targets");
         setPriority(SHORT);
         setUser(true);
      }

      GetTargetsJob(Pool pool)
      {
         this();
         this.pool = pool;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            PoolFilterDialogRunner dialogRunner;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            systemModel = SystemModel.getInstance();
            livingTargets = new ArrayList();
            gates = systemModel.getGates();
            for (int i = 0; i < gates.length; i++)
            {
               Gate gate = gates[i];
               if (!gate.isKilled())
               {
                  Target[] targets = gate.getTargets();
                  for (int j = 0; j < targets.length; j++)
                  {
                     Target t = targets[j];
                     if (!t.isKilled() && t.hasPoolSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            if (pool != null)
            {
               dialogRunner = new PoolFilterDialogRunner(livingTargets, pool);
            }
            else
            {
               dialogRunner = new PoolFilterDialogRunner(livingTargets);
            }
            asyncExec(dialogRunner);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
            poolFilterAction.setEnabled(true);
         }
      }
   }

   class PoolFilterDialogRunner implements Runnable
   {
      private List targets;
      private Pool pool;

      PoolFilterDialogRunner(List targets)
      {
         this.targets = targets;
      }

      PoolFilterDialogRunner(List targets, Pool pool)
      {
         this(targets);
         this.pool = pool;
      }

      public void run()
      {
         Shell shell;
         PoolFilterDialog dialog;
         int result;

         shell = getSite().getShell();
         if (pool != null)
         {
            dialog = new PoolFilterDialog(shell, targets, pool);
         }
         else
         {
            dialog = new PoolFilterDialog(shell, targets);
         }
         result = dialog.open();
         if (result == Window.OK)
         {
            Target filterTarget = dialog.getTarget();
            if (filterTarget != null)
            {
               Job job = new GetPoolStatisticsJob(filterTarget,
                                                  dialog.getPoolId(),
                                                  dialog.getSignalFilter(),
                                                  dialog.getStackFilter());
               IWorkbenchSiteProgressService siteService =
                  (IWorkbenchSiteProgressService) getSite().
                  getAdapter(IWorkbenchSiteProgressService.class);
               siteService.schedule(job, 0, true);
            }
         }
      }
   }

   class GetPoolStatisticsJob extends Job
   {
      private Target target;
      private int poolId;
      private SignalFilter signalFilter;
      private StackFilter stackFilter;

      GetPoolStatisticsJob(Target target,
                           int poolId,
                           SignalFilter signalFilter,
                           StackFilter stackFilter)
      {
         super("Filtering Signals and Stacks");
         setPriority(SHORT);
         this.target = target;
         this.poolId = poolId;
         this.signalFilter = signalFilter;
         this.stackFilter = stackFilter;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            Pool[] pools;
            Pool pool = null;
            SignalInfo[] signals;
            StackInfo[] stacks;
            ProcessInfo[] processes;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            target.connect(monitor);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            pools = target.getPools();
            for (int i = 0; i < pools.length; i++)
            {
               Pool p = pools[i];
               if (p.getId() == poolId)
               {
                  pool = p;
                  break;
               }
            }
            if (pool == null)
            {
               return SystemBrowserPlugin.createErrorStatus(
                     "The specified pool " + StringUtils.toHexString(poolId) +
                     " does not exist.");
            }

            signals = pool.getFilteredPoolSignals(monitor, signalFilter);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            stacks = pool.getFilteredPoolStacks(monitor, stackFilter);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            processes = target.getFilteredProcesses(monitor,
                                                    Target.SCOPE_SYSTEM,
                                                    0,
                                                    null);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            asyncExec(new UpdateViewersRunner(
                  target, pool, signals, stacks, signalFilter, stackFilter, processes));

            return (monitor.isCanceled() ?
                  Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when retrieving signals and stacks for target " + target, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when retrieving signals and stacks for target " + target, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when retrieving signals and stacks for target " + target, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class UpdateViewersRunner implements Runnable
   {
      private Target target;
      private Pool pool;
      private SignalInfo[] signals;
      private StackInfo[] stacks;
      private SignalFilter signalFilter;
      private StackFilter stackFilter;
      private ProcessInfo[] processes;

      UpdateViewersRunner(Target target,
                          Pool pool,
                          SignalInfo[] signals,
                          StackInfo[] stacks,
                          SignalFilter signalFilter,
                          StackFilter stackFilter,
                          ProcessInfo[] processes)
      {
         this.target = target;
         this.pool = pool;
         this.signals = signals;
         this.stacks = stacks;
         this.signalFilter = signalFilter;
         this.stackFilter = stackFilter;
         this.processes = processes;
      }

      public void run()
      {
         Map processMap;

         if (signalChartBackgroundImageHandler != null)
         {
            signalChartBackgroundImageHandler.dispose();
            signalChartBackgroundImageHandler = null;
            GridData gd = (GridData) signalChartGrid.getLayoutData();
            signalChartScrollComp.setMinSize(gd.widthHint, gd.heightHint);
         }

         if (stackChartBackgroundImageHandler != null)
         {
            stackChartBackgroundImageHandler.dispose();
            stackChartBackgroundImageHandler = null;
            GridData gd = (GridData) stackChartGrid.getLayoutData();
            stackChartScrollComp.setMinSize(gd.widthHint, gd.heightHint);
         }

         if (signalListBackgroundImageHandler != null)
         {
            signalListBackgroundImageHandler.dispose();
            signalListBackgroundImageHandler = null;
         }

         if (stackListBackgroundImageHandler != null)
         {
            stackListBackgroundImageHandler.dispose();
            stackListBackgroundImageHandler = null;
         }

         processMap = new HashMap(processes.length);
         for (int i = 0; i < processes.length; i++)
         {
            processMap.put(new Integer(processes[i].getId()),
                           processes[i].getName());
         }

         if (PoolView.this.pool != pool)
         {
            propertyChangeSource.firePropertyChange(
                  IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED, null, null);
         }
         PoolView.this.pool = pool;
         PoolView.this.signalFilter = signalFilter;
         PoolView.this.stackFilter = stackFilter;

         updateSignals(processMap);
         updateStacks(processMap);

         setContentDescription("Target: " + target + ", Pool: " +
               StringUtils.toHexString(pool.getId()) + ", Signals: " +
               signals.length + ", Stacks: " + stacks.length);
      }

      private void updateSignals(Map processMap)
      {
         int osType;
         boolean oseDelta;
         boolean oseCK;
         ExtendedSignalInfo[] extSignals;
         ChartModelInput chartModelInput;
         boolean endmarkBroken = false;

         osType = target.getOsType();
         oseDelta = (osType == Target.OS_OSE_4) || (osType == Target.OS_OSE_5);
         oseCK = (osType == Target.OS_OSE_CK);
         extSignals = new ExtendedSignalInfo[signals.length];

         for (int i = 0; i < signals.length; i++)
         {
            ExtendedSignalInfo extSignal;
            SignalInfo signal;
            int ownerId;
            int senderId;
            int addresseeId;
            String ownerName;
            String senderName;
            String addresseeName;

            signal = signals[i];
            ownerId = signal.getOwner();
            senderId = signal.getSender();
            addresseeId = signal.getAddressee();

            ownerName = (String) processMap.get(new Integer(ownerId));
            if (signal.getStatus() == SignalInfo.STATUS_FREE)
            {
               ownerName = BUFFER_FREE;
            }
            else if ((oseDelta && (ownerId == 0)) ||
                     (oseCK && (ownerId == 0xFFFF)))
            {
               ownerName = BUFFER_KERNEL;
            }
            else if (ownerName == null)
            {
               ownerName = BUFFER_KILLED;
            }

            senderName = (String) processMap.get(new Integer(senderId));
            if (oseDelta && (senderId == 0))
            {
               senderName = BUFFER_NOT_SENT;
            }
            else if (senderName == null)
            {
               senderName = BUFFER_KILLED;
            }

            addresseeName = (String) processMap.get(new Integer(addresseeId));
            if (signal.getStatus() == SignalInfo.STATUS_FREE)
            {
               addresseeName = BUFFER_FREE;
            }
            else if (oseDelta && (addresseeId == 0))
            {
               addresseeName = BUFFER_NOT_REDIRECTED;
            }
            else if (addresseeName == null)
            {
               addresseeName = BUFFER_KILLED;
            }

            extSignal = new ExtendedSignalInfo(signal,
                                               ownerName,
                                               senderName,
                                               addresseeName);
            extSignals[i] = extSignal;

            if (extSignal.isEndmarkBroken())
            {
               endmarkBroken = true;
            }
         }

         warningSignalChartsLabel.setVisible(endmarkBroken);
         warningSignalListLabel.setVisible(endmarkBroken);

         signalSorter.reset();
         signalViewer.setInput(extSignals);

         chartModelInput = new ChartModelInput(extSignals, signalFilter);
         signalChartGrid.setData(DATA_CHART_MODEL_INPUT, chartModelInput);
         signalChartGrid.setData(DATA_PROPERTY_NAME, PROPERTY_SIGNAL_INFO);
         signalChartGrid.setData(DATA_EDITOR_TITLE, getTitle());
         signalChartGrid.setEnabled(true);
         signalChartGrid.setVisible(true);

         propertyChangeSource.firePropertyChange(
               PROPERTY_SIGNAL_INFO, null, chartModelInput);
      }

      private void updateStacks(Map processMap)
      {
         int osType;
         boolean oseDelta;
         boolean oseCK;
         ExtendedStackInfo[] extStacks;
         ChartModelInput chartModelInput;

         osType = target.getOsType();
         oseDelta = (osType == Target.OS_OSE_4) || (osType == Target.OS_OSE_5);
         oseCK = (osType == Target.OS_OSE_CK);
         extStacks = new ExtendedStackInfo[stacks.length];

         for (int i = 0; i < stacks.length; i++)
         {
            ExtendedStackInfo extStack;
            StackInfo stack;
            int ownerId;
            String ownerName;

            stack = stacks[i];
            ownerId = stack.getPid();
            ownerName = (String) processMap.get(new Integer(ownerId));
            if ((oseDelta && (ownerId == 0)) || (oseCK && (ownerId == 0xFFFF)))
            {
               ownerName = BUFFER_KERNEL;
            }
            else if (ownerName == null)
            {
               ownerName = BUFFER_KILLED;
            }

            extStack = new ExtendedStackInfo(stack, ownerName);
            extStacks[i] = extStack;
         }

         //warningStackChartsLabel.setVisible();
         //warningStackListLabel.setVisible();

         stackSorter.reset();
         stackViewer.setInput(extStacks);

         chartModelInput = new ChartModelInput(extStacks, stackFilter);
         stackChartGrid.setData(DATA_CHART_MODEL_INPUT, chartModelInput);
         stackChartGrid.setData(DATA_PROPERTY_NAME, PROPERTY_STACK_INFO);
         stackChartGrid.setData(DATA_EDITOR_TITLE, getTitle());
         stackChartGrid.setEnabled(true);
         stackChartGrid.setVisible(true);

         propertyChangeSource.firePropertyChange(
               PROPERTY_STACK_INFO, null, chartModelInput);
      }

      private String getTitle()
      {
         return "Target: " + target + ", Pool: " +
                StringUtils.toHexString(pool.getId());
      }
   }
}
