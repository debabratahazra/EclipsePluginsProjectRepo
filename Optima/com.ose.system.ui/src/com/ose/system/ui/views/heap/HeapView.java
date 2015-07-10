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

package com.ose.system.ui.views.heap;

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
import com.ose.system.Heap;
import com.ose.system.HeapBufferFilter;
import com.ose.system.HeapBufferInfo;
import com.ose.system.ProcessInfo;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.AutoUpdateManager;
import com.ose.system.ui.IUpdatable;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.charts.ChartModelInput;
import com.ose.system.ui.editors.ChartModelEditorInput;
import com.ose.system.ui.util.AbstractShowMemoryAction;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.ExtendedHeapBufferInfo;
import com.ose.system.ui.util.Grid;
import com.ose.system.ui.util.GridItem;
import com.ose.system.ui.util.IPropertyChangeSource;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class HeapView extends ViewPart
{
   private static final int AUTO_UPDATE_PRIORITY = 3;

   public static final int COLUMN_BROKEN_BUFFER = 1;

   public static final int COLUMN_OWNER_ID = 2;

   public static final int COLUMN_OWNER_NAME = 3;

   public static final int COLUMN_SHARED = 4;

   public static final int COLUMN_REQUESTED_SIZE = 5;

   public static final int COLUMN_ACTUAL_SIZE = 6;

   public static final int COLUMN_ADDRESS = 7;

   public static final int COLUMN_FILE_NAME = 8;

   public static final int COLUMN_LINE_NUMBER = 9;

   public static final int COLUMN_STATUS = 10;

   private static final int TAB_CHARTS = 0;

   private static final int TAB_LIST = 1;

   private static final String DATA_CHART_MODEL_INPUT = "DATA_CHART_MODEL_INPUT";

   private static final String DATA_EDITOR_TITLE = "DATA_EDITOR_TITLE";

   private static final String HEAP_MEMORY_PER_REQUESTED_SIZE_CHART =
      "com.ose.system.ui.charts.HeapMemoryPerRequestedSizeChart";

   private static final String HEAP_MEMORY_PER_ACTUAL_SIZE_CHART =
      "com.ose.system.ui.charts.HeapMemoryPerActualSizeChart";

   private static final String HEAP_MEMORY_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.HeapMemoryPerProcessChart";

   private static final String HEAP_BUFFERS_PER_REQUESTED_SIZE_CHART =
      "com.ose.system.ui.charts.HeapBuffersPerRequestedSizeChart";

   private static final String HEAP_BUFFERS_PER_ACTUAL_SIZE_CHART =
      "com.ose.system.ui.charts.HeapBuffersPerActualSizeChart";

   private static final String HEAP_BUFFERS_PER_PROCESS_CHART =
      "com.ose.system.ui.charts.HeapBuffersPerProcessChart";

   private static final String PROPERTY_BUFFER_INFO = "PROPERTY_BUFFER_INFO";

   private static final String BUFFER_SHARED = "[ shared ]";

   private static final String BUFFER_KILLED = "[ killed ]";

   private static final String LINK_WITH_EDITOR = "LINK_WITH_EDITOR";

   private static final String TAB_SELECTED = "TAB_SELECTED";

   private static final String TOOL_TIP_TEXT = "Click to open in Editor";

   private Heap heap;

   private HeapBufferFilter heapBufferFilter;

   private TableViewer viewer;

   private HeapBufferSorter sorter;

   private IMemento memento;

   private IPartListener editorPartHandler;

   private boolean linkingEnabled;

   private PropertyChangeSource propertyChangeSource;

   private HeapFilterAction heapFilterAction;
   private Action updateAction;
   private Action updateAutoAction;
   private Action toggleLinkingAction;
   private Action showMemoryAction;

   private TabFolder tabFolder;
   private ScrolledComposite chartScrollComp;
   private Grid chartGrid;
   private CLabel warningChartsLabel;
   private CLabel warningListLabel;

   private ImageCache imageCache;
   private ImageDescriptor heapMemoryPerRequestedSizeChartImage;
   private ImageDescriptor heapMemoryPerActualSizeChartImage;
   private ImageDescriptor heapMemoryPerProcessChartImage;
   private ImageDescriptor heapBuffersPerRequestedSizeChartImage;
   private ImageDescriptor heapBuffersPerActualSizeChartImage;
   private ImageDescriptor heapBuffersPerProcessChartImage;

   private BackgroundImageHandler chartBackgroundImageHandler;
   private BackgroundImageHandler listBackgroundImageHandler;

   public void createPartControl(Composite parent)
   {
      createTabFolder(parent);
      createActions();
      createContextMenu();
      contributeToActionBars();
      editorPartHandler = new EditorPartHandler();
      getSite().getPage().addPartListener(editorPartHandler);
      setContentDescription("Target: None, Heap: None, Buffers: 0");
   }

   private void createTabFolder(Composite parent)
   {
      tabFolder = new TabFolder(parent, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

      createChartsTab(tabFolder);
      createListTab(tabFolder);
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

   private void createChartsTab(TabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      TabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      layout = new GridLayout();
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      warningChartsLabel = new CLabel(comp, SWT.NONE);
      warningChartsLabel.setText("Broken Heap Buffers!");
      warningChartsLabel.setImage(PlatformUI.getWorkbench()
            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningChartsLabel.setVisible(false);
      warningChartsLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      createChartGrid(comp);

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Heap Buffer Charts");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_HEAP_CHART));
      tabItem.setControl(comp);
   }

   private void createListTab(TabFolder tabFolder)
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

      warningListLabel = new CLabel(comp, SWT.NONE);
      warningListLabel.setText("Broken Heap Buffers!");
      warningListLabel.setImage(PlatformUI.getWorkbench()
            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
      warningListLabel.setVisible(false);
      warningListLabel.setLayoutData(
            new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new FillLayout());
      subComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      sorter = new HeapBufferSorter();
      viewer = new TableViewer(createTable(subComp));
      viewer.setUseHashlookup(true);
      viewer.setContentProvider(new HeapBufferContentProvider());
      viewer.setLabelProvider(new HeapBufferLabelProvider());
      viewer.setSorter(sorter);
      viewer.addSelectionChangedListener(new ListSelectionChangedHandler());
      listBackgroundImageHandler = new HeapBackgroundImageHandler(viewer.getTable());

      tabItem = new TabItem(tabFolder, SWT.NONE);
      tabItem.setText("Heap Buffer List");
      tabItem.setImage(SharedImages.get(SharedImages.IMG_OBJ_HEAP_LIST));
      tabItem.setControl(comp);
   }

   private void createChartGrid(Composite parent)
   {
      Composite comp;
      GridItem item;
      int widthHint = 440;
      int heigthHint = 200;

      chartScrollComp = new ScrolledComposite(
            parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      chartScrollComp.setLayout(new GridLayout());
      chartScrollComp.setLayoutData(new GridData(GridData.FILL_BOTH));

      comp = new Composite(chartScrollComp, SWT.NONE);
      comp.setBackground(
            getSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
      comp.setLayout(new GridLayout());
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      chartBackgroundImageHandler = new HeapBackgroundImageHandler(comp);

      chartGrid = new Grid(comp, SWT.NONE);
      chartGrid.setLayout(new GridLayout(3, true));
      chartGrid.setLayoutData(new GridData(widthHint, heigthHint));
      chartGrid.setEnabled(false);
      chartGrid.setVisible(false);
      chartGrid.addSelectionListener(new ChartGridSelectionHandler());

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_MEMORY_PER_REQUESTED_SIZE_CHART);
      item.setText("Bytes / Requested Size");
      item.setImage(imageCache.getImage(heapMemoryPerRequestedSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_MEMORY_PER_ACTUAL_SIZE_CHART);
      item.setText("Bytes / Actual Size");
      item.setImage(imageCache.getImage(heapMemoryPerActualSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_MEMORY_PER_PROCESS_CHART);
      item.setText("Bytes / Process");
      item.setImage(imageCache.getImage(heapMemoryPerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_BUFFERS_PER_REQUESTED_SIZE_CHART);
      item.setText("Buffers / Requested Size");
      item.setImage(imageCache.getImage(heapBuffersPerRequestedSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_BUFFERS_PER_ACTUAL_SIZE_CHART);
      item.setText("Buffers / Actual Size");
      item.setImage(imageCache.getImage(heapBuffersPerActualSizeChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      item = new GridItem(chartGrid, SWT.NONE);
      item.setData(HEAP_BUFFERS_PER_PROCESS_CHART);
      item.setText("Buffers / Process");
      item.setImage(imageCache.getImage(heapBuffersPerProcessChartImage));
      item.setDefaultToolTipText(TOOL_TIP_TEXT);

      chartScrollComp.setContent(comp);
      chartScrollComp.setExpandHorizontal(true);
      chartScrollComp.setExpandVertical(true);
      chartScrollComp.setMinSize(0, 0);
   }

   private Table createTable(Composite parent)
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
      column.addSelectionListener(new ListSelectionHandler(COLUMN_BROKEN_BUFFER));
      layout.setColumnData(column, new ColumnWeightData(0, 16, false));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Owner ID");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_OWNER_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Owner Name");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_OWNER_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Shared");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_SHARED));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Requested Size");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_REQUESTED_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Actual Size");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_ACTUAL_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Address");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("File");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_FILE_NAME));
      layout.setColumnData(column, new ColumnWeightData(12, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Line");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_LINE_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Status");
      column.setMoveable(true);
      column.addSelectionListener(new ListSelectionHandler(COLUMN_STATUS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   private void createActions()
   {
      heapFilterAction = new HeapFilterAction();
      updateAction = new UpdateAction();
      updateAutoAction = new ToggleUpdateAutoAction();
      toggleLinkingAction = new ToggleLinkingAction();
      showMemoryAction = new ShowMemoryAction();
   }

   private void createContextMenu()
   {
      MenuManager menuMgr;
      Menu menu;

      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new ContextMenuHandler());
      menu = menuMgr.createContextMenu(viewer.getControl());
      viewer.getControl().setMenu(menu);
      getSite().registerContextMenu(menuMgr, viewer);
   }

   private void contributeToActionBars()
   {
      IActionBars bars = getViewSite().getActionBars();
      fillLocalPullDown(bars.getMenuManager());
      fillLocalToolBar(bars.getToolBarManager());
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                                  new TableCopyAction(viewer));
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                                  new TableSelectAllAction(viewer));
   }

   private void fillLocalPullDown(IMenuManager manager)
   {
      manager.add(heapFilterAction);
      manager.add(toggleLinkingAction);
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(heapFilterAction);
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
      heapMemoryPerRequestedSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_bytes_per_requested_size.gif");
      heapMemoryPerActualSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_bytes_per_actual_size.gif");
      heapMemoryPerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_bytes_per_process.gif");
      heapBuffersPerRequestedSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_buffers_per_requested_size.gif");
      heapBuffersPerActualSizeChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_buffers_per_actual_size.gif");
      heapBuffersPerProcessChartImage = ImageCache.createImageDescriptor(
            "obj16/", "chart_heap_buffers_per_process.gif");
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
            SystemBrowserPlugin.HEAP_VIEW_ID);
      imageCache.dispose();
      super.dispose();
   }

   public void show(Heap heap)
   {
      heapFilterAction.run(heap);
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

            if (chartGrid.isDisposed())
            {
               return;
            }

            chartModelInput = (ChartModelEditorInput) input;
            className = chartModelInput.getChartModel().getClass().getName();
            item = chartGrid.getItemMatchingData(className);

            if (item != null)
            {
               if (tabFolder.getSelectionIndex() != TAB_CHARTS)
               {
                  tabFolder.setSelection(TAB_CHARTS);
               }
               chartGrid.setSelectedItem(item);
            }
         }
      }
   }

   private void update()
   {
      if ((heap != null) && !heap.isKilled())
      {
         Job job = new GetHeapStatisticsJob(heap.getTarget(), heap.getId(),
            heapBufferFilter);
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

   static class HeapBackgroundImageHandler extends BackgroundImageHandler
   {
      HeapBackgroundImageHandler(Composite comp)
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

   class ContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(showMemoryAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class ListSelectionHandler extends SelectionAdapter
   {
      private int column;

      ListSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         sorter.sortByColumn(column);
         viewer.refresh();
      }
   }

   class ListSelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) event.getSelection();
         showMemoryAction.setEnabled(selection.size() == 1);
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
                  PROPERTY_BUFFER_INFO,
                  (String) chartGrid.getData(DATA_EDITOR_TITLE),
                  Class.forName((String) event.item.getData()),
                  (ChartModelInput) chartGrid.getData(DATA_CHART_MODEL_INPUT));
               page.openEditor(editorInput,
                               SystemBrowserPlugin.CHART_MODEL_EDITOR_ID);
            }
            catch (Exception e)
            {
               throw new RuntimeException("Error opening heap chart editor", e);
            }
         }
      }
   }

   class TabFolderSelectionHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         if (tabFolder.getSelectionIndex() == TAB_LIST)
         {
            viewer.getTable().setFocus();
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

   class HeapFilterAction extends Action
   {
      HeapFilterAction()
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

      public void run(Heap heap)
      {
         setEnabled(false);
         Job job = new GetTargetsJob(heap);
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
            return SystemBrowserPlugin.HEAP_VIEW_ID;
         }

         public int getPriority()
         {
            return HeapView.AUTO_UPDATE_PRIORITY;
         }

         public void update(IProgressMonitor monitor)
         {
            HeapView.this.update();
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

   class ShowMemoryAction extends AbstractShowMemoryAction
   {
      ShowMemoryAction()
      {
         super("Show Memory");
         setEnabled(false);
      }

      public void run()
      {
         ISelection selection;
         Object obj;

         selection = viewer.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();
         if (obj instanceof ExtendedHeapBufferInfo)
         {
            try
            {
               ExtendedHeapBufferInfo buffer;
               int pid;

               buffer = (ExtendedHeapBufferInfo) obj;
               pid = buffer.getHeapBufferInfo().getOwner();
               if ((pid == 0) && heap.getTarget().hasSegmentSupport())
               {
                  pid = heap.getSid();
               }
               showMemory(heap.getTarget(),
                          pid,
                          buffer.getHeapBufferInfo().getAddress() & 0xFFFFFFFFL,
                          buffer.getHeapBufferInfo().getSize() & 0xFFFFFFFFL);
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
      private Heap heap;

      GetTargetsJob()
      {
         super("Finding Targets");
         setPriority(SHORT);
         setUser(true);
      }

      GetTargetsJob(Heap heap)
      {
         this();
         this.heap = heap;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            HeapFilterDialogRunner dialogRunner;

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
                     if (!t.isKilled() && t.hasHeapSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            if (heap != null)
            {
               dialogRunner = new HeapFilterDialogRunner(livingTargets, heap);
            }
            else
            {
               dialogRunner = new HeapFilterDialogRunner(livingTargets);
            }
            asyncExec(dialogRunner);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
            heapFilterAction.setEnabled(true);
         }
      }
   }

   class HeapFilterDialogRunner implements Runnable
   {
      private List targets;
      private Heap heap;

      HeapFilterDialogRunner(List targets)
      {
         this.targets = targets;
      }

      HeapFilterDialogRunner(List targets, Heap heap)
      {
         this(targets);
         this.heap = heap;
      }

      public void run()
      {
         Shell shell;
         HeapFilterDialog dialog;
         int result;

         shell = getSite().getShell();
         if (heap != null)
         {
            dialog = new HeapFilterDialog(shell, targets, heap);
         }
         else
         {
            dialog = new HeapFilterDialog(shell, targets);
         }
         result = dialog.open();
         if (result == Window.OK)
         {
            Target filterTarget = dialog.getTarget();
            if (filterTarget != null)
            {
               Job job = new GetHeapStatisticsJob(filterTarget,
                                                  dialog.getHeapId(),
                                                  dialog.getHeapBufferFilter());
               IWorkbenchSiteProgressService siteService =
                  (IWorkbenchSiteProgressService) getSite().
                  getAdapter(IWorkbenchSiteProgressService.class);
               siteService.schedule(job, 0, true);
            }
         }
      }
   }

   class GetHeapStatisticsJob extends Job
   {
      private Target target;
      private int heapId;
      private HeapBufferFilter bufferFilter;

      GetHeapStatisticsJob(Target target,
                           int heapId,
                           HeapBufferFilter bufferFilter)
      {
         super("Filtering Heap Buffers");
         setPriority(SHORT);
         this.target = target;
         this.heapId = heapId;
         this.bufferFilter = bufferFilter;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            Heap[] heaps;
            Heap heap = null;
            HeapBufferInfo[] buffers;
            ProcessInfo[] processes;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            target.connect(monitor);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            heaps = target.getHeaps();
            for (int i = 0; i < heaps.length; i++)
            {
               Heap h = heaps[i];
               if (h.getId() == heapId)
               {
                  heap = h;
                  break;
               }
            }
            if (heap == null)
            {
               return SystemBrowserPlugin.createErrorStatus(
                     "The specified heap " + StringUtils.toHexString(heapId) +
                     " does not exist.");
            }

            buffers = heap.getFilteredHeapBuffers(monitor, bufferFilter);
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

            asyncExec(new UpdateViewerRunner(
                  target, heap, buffers, bufferFilter, processes));

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
               "Error reported from target when retrieving heap buffers for target " + target, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when retrieving heap buffers for target " + target, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when retrieving heap buffers for target " + target, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class UpdateViewerRunner implements Runnable
   {
      private Target target;
      private Heap heap;
      private HeapBufferInfo[] buffers;
      private HeapBufferFilter bufferFilter;
      private ProcessInfo[] processes;

      UpdateViewerRunner(Target target,
                         Heap heap,
                         HeapBufferInfo[] buffers,
                         HeapBufferFilter bufferFilter,
                         ProcessInfo[] processes)
      {
         this.target = target;
         this.heap = heap;
         this.buffers = buffers;
         this.bufferFilter = bufferFilter;
         this.processes = processes;
      }

      public void run()
      {
         Map processMap;

         if (chartBackgroundImageHandler != null)
         {
            chartBackgroundImageHandler.dispose();
            chartBackgroundImageHandler = null;
            GridData gd = (GridData) chartGrid.getLayoutData();
            chartScrollComp.setMinSize(gd.widthHint, gd.heightHint);
         }

         if (listBackgroundImageHandler != null)
         {
            listBackgroundImageHandler.dispose();
            listBackgroundImageHandler = null;
         }

         processMap = new HashMap(processes.length);
         for (int i = 0; i < processes.length; i++)
         {
            processMap.put(new Integer(processes[i].getId()),
                           processes[i].getName());
         }

         if (HeapView.this.heap != heap)
         {
            propertyChangeSource.firePropertyChange(
                  IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED, null, null);
         }
         HeapView.this.heap = heap;
         HeapView.this.heapBufferFilter = bufferFilter;

         update(processMap);

         setContentDescription("Target: " + target + ", Heap: " +
               StringUtils.toHexString(heap.getId()) + ", Buffers: " +
               buffers.length);
      }

      private void update(Map processMap)
      {
         ExtendedHeapBufferInfo[] extBuffers;
         ChartModelInput chartModelInput;
         boolean endmarkBroken = false;

         extBuffers = new ExtendedHeapBufferInfo[buffers.length];

         for (int i = 0; i < buffers.length; i++)
         {
            ExtendedHeapBufferInfo extBuffer;
            HeapBufferInfo buffer;
            int ownerId;
            String ownerName;

            buffer = buffers[i];
            ownerId = buffer.getOwner();
            ownerName = (String) processMap.get(new Integer(ownerId));
            if (buffer.isShared())
            {
               ownerName = BUFFER_SHARED;
            }
            else if (ownerName == null)
            {
               ownerName = BUFFER_KILLED;
            }

            extBuffer = new ExtendedHeapBufferInfo(buffer, ownerName);
            extBuffers[i] = extBuffer;
            if (extBuffer.isEndmarkBroken())
            {
               endmarkBroken = true;
            }
         }

         warningChartsLabel.setVisible(endmarkBroken);
         warningListLabel.setVisible(endmarkBroken);

         sorter.reset();
         viewer.setInput(extBuffers);

         chartModelInput = new ChartModelInput(extBuffers, bufferFilter);
         chartGrid.setData(DATA_CHART_MODEL_INPUT, chartModelInput);
         chartGrid.setData(DATA_EDITOR_TITLE, getTitle());
         chartGrid.setEnabled(true);
         chartGrid.setVisible(true);

         propertyChangeSource.firePropertyChange(
               PROPERTY_BUFFER_INFO, null, chartModelInput);
      }

      private String getTitle()
      {
         return "Target: " + target + ", Heap: " +
                StringUtils.toHexString(heap.getId());
      }
   }
}
