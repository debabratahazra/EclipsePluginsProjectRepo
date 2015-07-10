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

package com.ose.event.ui.editors.event;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;
import org.xml.sax.SAXException;
import com.ose.event.format.EventDumpReader;
import com.ose.event.format.EventDumpXMLConverter;
import com.ose.event.format.EventReaderClient;
import com.ose.event.format.EventXMLDumpConverter;
import com.ose.event.format.EventXMLReader;
import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.SymbolManager;
import com.ose.sigdb.Signal;
import com.ose.sigdb.util.EndianConstants;
import com.ose.system.ReceiveEvent;
import com.ose.system.SendEvent;
import com.ose.system.TargetEvent;
import com.ose.system.UserEvent;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.FileDialogAdapter;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class EventEditor extends EditorPart
{
   public static final int COLUMN_ENTRY = 1;
   public static final int COLUMN_TICK = 2;
   public static final int COLUMN_TIMESTAMP = 3;
   public static final int COLUMN_EVENT = 4;
   public static final int COLUMN_FROM = 5;
   public static final int COLUMN_TO = 6;
   public static final int COLUMN_FILE_LINE = 7;
   public static final int COLUMN_NUMBER = 8;
   public static final int COLUMN_SIZE = 9;
   public static final int COLUMN_ADDRESS = 10;
   public static final int COLUMN_ERROR_CALLER = 11;
   public static final int COLUMN_ERROR_CODE = 12;
   public static final int COLUMN_EXTRA_PARAMETER = 13;
   public static final int COLUMN_FROM_EXEC_UNIT = 14;
   public static final int COLUMN_TO_EXEC_UNIT = 15;

   public static final int MAX_EVENTS = 5000;

   private static final FontData[] MONOSPACE_FONT_DATAS;

   private static final String[] EVENT_FILE_EXTENSIONS = {".pmd", ".event"};

   private final Object editorOpenedLock = new Object();
   private boolean editorOpened;

   private boolean offLine;
   private String target;
   private boolean bigEndian;
   private String scope;
   private String eventActions;
   private RawDataFormatter rawDataFormatter;
   private SymbolicDataFormatter symbolicDataFormatter;
   private SymbolManager symbolManager;
   private volatile boolean disposed;

   private CTabFolder tabFolder;
   private Label traceHeaderLabel;
   private Label notifyHeaderLabel;
   private TableViewer traceViewer;
   private TableViewer notifyViewer;
   private EventContentProvider traceContentProvider;
   private EventContentProvider notifyContentProvider;
   private EventSorter traceSorter;
   private EventSorter notifySorter;
   private StyledText traceText;
   private StyledText notifyText;
   private Font monospaceFont;

   private Action copyAction;
   private Action selectAllAction;

   static
   {
      if (Platform.WS_WIN32.equals(Platform.getWS()))
      {
         MONOSPACE_FONT_DATAS = new FontData[]
         {
            new FontData("Lucida Sans Typewriter", 8, SWT.NORMAL)
         };
      }
      else
      {
         MONOSPACE_FONT_DATAS = new FontData[]
         {
            new FontData("Lucida Sans Typewriter", 8, SWT.NORMAL),
            new FontData("Bitstream Vera Sans Mono", 8, SWT.NORMAL),
            new FontData("Luxi Mono", 8, SWT.NORMAL),
            new FontData("Lucida Console", 8, SWT.NORMAL),
            new FontData("Courier New", 8, SWT.NORMAL)
         };
      }
   }

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      if (input instanceof EventEditorInput)
      {
         EventEditorInput eventEditorInput;

         eventEditorInput = (EventEditorInput) input;
         offLine = false;
         target = eventEditorInput.getTarget().toString();
         bigEndian = eventEditorInput.getTarget().isBigEndian();
         scope = eventEditorInput.getScope();
         eventActions = "None";
      }
      else if (input instanceof IURIEditorInput)
      {
         IURIEditorInput uriEditorInput;
         IPath path;
         String extension;

         offLine = true;
         target = "None";
         bigEndian = false;
         scope = "None";
         eventActions = "None";

         uriEditorInput = (IURIEditorInput) input;
         path = new Path(uriEditorInput.getURI().getPath());
         extension = path.getFileExtension();

         if (extension == null)
         {
            throw new PartInitException("Unknown file format");
         }
         else if (extension.equalsIgnoreCase("pmd"))
         {
            Job job = new EventDumpReaderJob(path.toOSString());
            job.schedule();
         }
         else if (extension.equalsIgnoreCase("event"))
         {
            Job job = new EventXMLReaderJob(path.toOSString());
            job.schedule();
         }
         else
         {
            throw new PartInitException("Unsupported file format");
         }
      }
      else
      {
         throw new PartInitException("Invalid editor input");
      }

      rawDataFormatter = new RawDataFormatter();
      symbolicDataFormatter = new SymbolicDataFormatter();
      symbolManager = EventPlugin.getSymbolManager();
      setSite(site);
      setInput(input);
      setPartName(input.getName());
   }

   public void createPartControl(Composite parent)
   {
      Display display;
      GridLayout layout;
      FontData[] fontDatas;

      display = parent.getDisplay();
      parent.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      parent.setLayout(layout);

      fontDatas = JFaceResources.getFontRegistry().
         filterData(MONOSPACE_FONT_DATAS, display);
      monospaceFont = new Font(display, fontDatas[0]);

      getSite().getPage().addPartListener(new EditorOpenedHandler());

      if (offLine)
      {
         createOffLineContent(parent);
      }
      else
      {
         createTabFolder(parent);
      }
   }

   private void createOffLineContent(Composite parent)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;

      // This composite is only used for getting
      // the same margins as in the tab folder case.
      comp = new Composite(parent, SWT.NONE);
      comp.setBackground(parent.getBackground());
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new GridLayout(1, false);
      layout.marginWidth = 2;
      layout.marginHeight = 2;
      comp.setLayout(layout);

      traceHeaderLabel = new Label(comp, SWT.NONE);
      traceHeaderLabel.setBackground(parent.getBackground());
      traceHeaderLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      sashForm = new SashForm(comp, SWT.VERTICAL);
      sashForm.setBackground(parent.getBackground());
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      traceContentProvider = new EventContentProvider(MAX_EVENTS);
      traceSorter = new EventSorter(true);
      traceViewer = new TableViewer(createTraceTable(sashForm));
      traceViewer.setUseHashlookup(true);
      traceViewer.setContentProvider(traceContentProvider);
      traceViewer.setLabelProvider(new EventLabelProvider(true));
      traceViewer.setSorter(traceSorter);
      traceViewer.setInput(new TargetEvent[0]);

      traceText = new StyledText(sashForm, SWT.READ_ONLY | SWT.MULTI |
            SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT | SWT.BORDER);
      traceText.setFont(monospaceFont);
      traceViewer.addSelectionChangedListener(new TableSelectionChangedHandler(traceText));

      sashForm.setWeights(new int[] {70, 30});

      copyAction = new TableCopyAction(traceViewer);
      selectAllAction = new TableSelectAllAction(traceViewer);

      updateTraceHeader();
   }

   private void createTabFolder(Composite parent)
   {
      ActionMultiplexer copyActionMux;
      ActionMultiplexer selectAllActionMux;

      tabFolder = new CTabFolder(parent, SWT.BOTTOM);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

      createTraceTab(tabFolder);
      createNotifyTab(tabFolder);

      copyActionMux = new ActionMultiplexer();
      copyActionMux.addAction(new TableCopyAction(traceViewer));
      copyActionMux.addAction(new TableCopyAction(notifyViewer));
      copyAction = copyActionMux;

      selectAllActionMux = new ActionMultiplexer();
      selectAllActionMux.addAction(new TableSelectAllAction(traceViewer));
      selectAllActionMux.addAction(new TableSelectAllAction(notifyViewer));
      selectAllAction = selectAllActionMux;
   }

   private void createTraceTab(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;
      CTabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_WHITE));
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      traceHeaderLabel = new Label(comp, SWT.NONE);
      traceHeaderLabel.setBackground(comp.getBackground());
      traceHeaderLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      sashForm = new SashForm(comp, SWT.VERTICAL);
      sashForm.setBackground(comp.getBackground());
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      traceContentProvider = new EventContentProvider(MAX_EVENTS);
      traceSorter = new EventSorter(true);
      traceViewer = new TableViewer(createTraceTable(sashForm));
      traceViewer.setUseHashlookup(true);
      traceViewer.setContentProvider(traceContentProvider);
      traceViewer.setLabelProvider(new EventLabelProvider(true));
      traceViewer.setSorter(traceSorter);
      traceViewer.setInput(new TargetEvent[0]);

      traceText = new StyledText(sashForm, SWT.READ_ONLY | SWT.MULTI |
            SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT | SWT.BORDER);
      traceText.setFont(monospaceFont);
      traceViewer.addSelectionChangedListener(new TableSelectionChangedHandler(traceText));

      sashForm.setWeights(new int[] {70, 30});

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("Trace");
      tabItem.setControl(comp);

      updateTraceHeader();
   }

   private void createNotifyTab(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      SashForm sashForm;
      CTabItem tabItem;

      comp = new Composite(tabFolder, SWT.NONE);
      comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_WHITE));
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      notifyHeaderLabel = new Label(comp, SWT.NONE);
      notifyHeaderLabel.setBackground(comp.getBackground());
      notifyHeaderLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      sashForm = new SashForm(comp, SWT.VERTICAL);
      sashForm.setBackground(comp.getBackground());
      sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
      sashForm.setLayout(new FillLayout());

      notifyContentProvider = new EventContentProvider(MAX_EVENTS);
      notifySorter = new EventSorter(false);
      notifyViewer = new TableViewer(createNotifyTable(sashForm));
      notifyViewer.setUseHashlookup(true);
      notifyViewer.setContentProvider(notifyContentProvider);
      notifyViewer.setLabelProvider(new EventLabelProvider(false));
      notifyViewer.setSorter(notifySorter);
      notifyViewer.setInput(new TargetEvent[0]);

      notifyText = new StyledText(sashForm, SWT.READ_ONLY | SWT.MULTI |
            SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT | SWT.BORDER);
      notifyText.setFont(monospaceFont);
      notifyViewer.addSelectionChangedListener(new TableSelectionChangedHandler(notifyText));

      sashForm.setWeights(new int[] {70, 30});

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("Notify");
      tabItem.setControl(comp);

      updateNotifyHeader();
   }

   private Table createTraceTable(Composite parent)
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
      column.setText("Entry");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_ENTRY));
      layout.setColumnData(column, new ColumnWeightData(6, 60));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Tick");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_TICK));
      layout.setColumnData(column, new ColumnWeightData(10, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Event");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_EVENT));
      layout.setColumnData(column, new ColumnWeightData(10, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("From");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_FROM));
      layout.setColumnData(column, new ColumnWeightData(20, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("To");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_TO));
      layout.setColumnData(column, new ColumnWeightData(20, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("File/Line");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_FILE_LINE));
      layout.setColumnData(column, new ColumnWeightData(12, 120));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Number");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Address");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Error Caller");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_ERROR_CALLER));
      layout.setColumnData(column, new ColumnWeightData(7, 70));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Error Code");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_ERROR_CODE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Extra Param");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_EXTRA_PARAMETER));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("From Exec Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_FROM_EXEC_UNIT));
      layout.setColumnData(column, new ColumnWeightData(9, 90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("To Exec Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new TraceColumnSelectionHandler(COLUMN_TO_EXEC_UNIT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   private Table createNotifyTable(Composite parent)
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
      column.setText("Entry");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_ENTRY));
      layout.setColumnData(column, new ColumnWeightData(6, 60));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Tick");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_TICK));
      layout.setColumnData(column, new ColumnWeightData(10, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Event");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_EVENT));
      layout.setColumnData(column, new ColumnWeightData(10, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("From");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_FROM));
      layout.setColumnData(column, new ColumnWeightData(20, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("To");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_TO));
      layout.setColumnData(column, new ColumnWeightData(20, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("File/Line");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_FILE_LINE));
      layout.setColumnData(column, new ColumnWeightData(12, 120));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Number");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Size");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Address");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_ADDRESS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Error Caller");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_ERROR_CALLER));
      layout.setColumnData(column, new ColumnWeightData(7, 70));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Error Code");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_ERROR_CODE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Extra Param");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_EXTRA_PARAMETER));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("From Exec Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_FROM_EXEC_UNIT));
      layout.setColumnData(column, new ColumnWeightData(9, 90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("To Exec Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new NotifyColumnSelectionHandler(COLUMN_TO_EXEC_UNIT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   public void dispose()
   {
      super.dispose();
      monospaceFont.dispose();
      disposed = true;
   }

   public boolean isDisposed()
   {
      return disposed;
   }

   public void setFocus()
   {
      if (offLine)
      {
         traceViewer.getTable().setFocus();
      }
      else
      {
         tabFolder.setFocus();
      }
      updateActionBars();
   }

   public boolean isDirty()
   {
      // Save not supported.
      return false;
   }

   public void doSave(IProgressMonitor monitor)
   {
      // Save not supported.
   }

   public void doSaveAs()
   {
      String fileName = getSaveFileName(EVENT_FILE_EXTENSIONS);

      if (fileName != null)
      {
         Job job = new EventWriterJob(fileName);
         job.schedule();
      }
   }

   public boolean isSaveAsAllowed()
   {
      return offLine;
   }

   public void setEventActions(String eventActions)
   {
      checkThread();
      if (eventActions == null)
      {
         throw new IllegalArgumentException();
      }
      if (!offLine)
      {
         this.eventActions = eventActions;
         updateTraceHeader();
         updateNotifyHeader();
      }
   }

   public void setTraceSortingEnabled(boolean enabled)
   {
      checkThread();
      if (!offLine)
      {
         if (enabled)
         {
            traceSorter.reset();
            traceViewer.setSorter(traceSorter);
         }
         else
         {
            traceViewer.setSorter(null);
         }
      }
   }

   public void setNotifySortingEnabled(boolean enabled)
   {
      checkThread();
      if (!offLine)
      {
         if (enabled)
         {
            notifySorter.reset();
            notifyViewer.setSorter(notifySorter);
         }
         else
         {
            notifyViewer.setSorter(null);
         }
      }
   }

   public void clearTraceEvents()
   {
      checkThread();
      if (!offLine)
      {
         traceViewer.setInput(new TargetEvent[0]);
         updateTraceHeader();
      }
   }

   public void clearNotifyEvents()
   {
      checkThread();
      if (!offLine)
      {
         notifyViewer.setInput(new TargetEvent[0]);
         updateNotifyHeader();
      }
   }

   public void addTraceEvents(TargetEvent[] events)
   {
      checkThread();
      if (!offLine)
      {
         traceContentProvider.addEvents(events);
         updateTraceHeader();
      }
   }

   public void addNotifyEvents(TargetEvent[] events)
   {
      checkThread();
      if (!offLine)
      {
         notifyContentProvider.addEvents(events);
         updateNotifyHeader();
      }
   }

   private void checkThread() throws RuntimeException
   {
      if (traceHeaderLabel.getDisplay().getThread() != Thread.currentThread())
      {
         SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
      }
   }

   private void updateActionBars()
   {
      IActionBars bars = getEditorSite().getActionBars();
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAllAction);
      bars.updateActionBars();
   }

   private void updateTraceHeader()
   {
      int numTotalEvents;
      String events;

      numTotalEvents = traceContentProvider.getNumTotalElements();
      if (numTotalEvents <= MAX_EVENTS)
      {
         events = toU32String(traceContentProvider.getNumElements());
      }
      else
      {
         events = toU32String(traceContentProvider.getNumElements()) + " of " +
                  toU32String(numTotalEvents);
      }
      traceHeaderLabel.setText(
            "Target: " + target +
            ", Scope: " + scope +
            ", Event Actions: " + eventActions +
            ", Events: " + events);
   }

   private void updateNotifyHeader()
   {
      int numTotalEvents;
      String events;

      numTotalEvents = notifyContentProvider.getNumTotalElements();
      if (numTotalEvents <= MAX_EVENTS)
      {
         events = toU32String(notifyContentProvider.getNumElements());
      }
      else
      {
         events = toU32String(notifyContentProvider.getNumElements()) + " of " +
                  toU32String(numTotalEvents);
      }
      notifyHeaderLabel.setText(
            "Target: " + target +
            ", Scope: " + scope +
            ", Event Actions: " + eventActions +
            ", Events: " + events);
   }

   private void formatData(Object event, StyledText text)
   {
      int number = 0;
      byte[] data = null;
      String preFormattedData = null;
      long address = 0;

      if (event instanceof SendEvent)
      {
         SendEvent sendEvent = (SendEvent) event;
         number = sendEvent.getSignalNumber();
         data = sendEvent.getSignalData();
         preFormattedData = sendEvent.getFormattedSignalData();
         address = sendEvent.getSignalAddress() & 0xFFFFFFFFL;
      }
      else if (event instanceof ReceiveEvent)
      {
         ReceiveEvent receiveEvent = (ReceiveEvent) event;
         number = receiveEvent.getSignalNumber();
         data = receiveEvent.getSignalData();
         preFormattedData = receiveEvent.getFormattedSignalData();
         address = receiveEvent.getSignalAddress() & 0xFFFFFFFFL;
      }
      else if (event instanceof UserEvent)
      {
         UserEvent userEvent = (UserEvent) event;
         number = userEvent.getEventNumber();
         data = userEvent.getEventData();
         preFormattedData = userEvent.getFormattedEventData();
      }
      else
      {
         preFormattedData = "";
      }

      if (preFormattedData != null)
      {
         text.setText(preFormattedData);
      }
      else if (data != null)
      {
         if (event instanceof UserEvent)
         {
            try
            {
               text.setText(formatEventData(number, data));
            }
            catch (IOException e)
            {
               text.setText("<Error formatting event data!>");
            }
         }
         else
         {
            try
            {
               text.setText(formatSignalData(number, data, address));
            }
            catch (IOException e)
            {
               text.setText("<Error formatting signal data!>");
            }
         }
      }
      else
      {
         text.setText("");
      }
   }

   private String formatSignalData(int number, byte[] data, long address)
      throws IOException
   {
      InputStream in = null;
      PrintWriter out = null;

      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         StringWriter stringWriter;
         int byteOrder;
         Signal signal;

         stringWriter = new StringWriter(data.length);
         out = new PrintWriter(stringWriter);
         byteOrder = (bigEndian ?
               EndianConstants.BIG_ENDIAN : EndianConstants.LITTLE_ENDIAN);
         signal = symbolManager.getSignal(number, data, byteOrder);

         if (signal != null)
         {
            symbolicDataFormatter.format(signal, out);
         }
         else
         {
            in = new ByteArrayInputStream(data);
            rawDataFormatter.format(in, out, address);
         }

         out.flush();
         return stringWriter.toString();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
         if (out != null)
         {
            out.close();
         }
      }
   }

   private String formatEventData(int number, byte[] data) throws IOException
   {
      InputStream in = null;
      PrintWriter out = null;

      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         StringWriter stringWriter;
         int byteOrder;
         Signal signal;

         stringWriter = new StringWriter(data.length);
         out = new PrintWriter(stringWriter);
         byteOrder = (bigEndian ?
               EndianConstants.BIG_ENDIAN : EndianConstants.LITTLE_ENDIAN);
         signal = symbolManager.getEvent(number, data, byteOrder);

         if (signal != null)
         {
            symbolicDataFormatter.format(signal, out);
         }
         else
         {
            in = new ByteArrayInputStream(data);
            rawDataFormatter.format(in, out, 0);
         }

         out.flush();
         return stringWriter.toString();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
         if (out != null)
         {
            out.close();
         }
      }
   }

   private String getSaveFileName(String[] extensions)
   {
      FileDialog dialog;
      String[] patterns;
      boolean done = false;
      String fileName = null;

      if ((extensions == null) || (extensions.length == 0))
      {
         throw new IllegalArgumentException();
      }

      dialog = new FileDialog(getSite().getShell(), SWT.SAVE | SWT.APPLICATION_MODAL);
      patterns = new String[extensions.length];
      for (int i = 0; i < extensions.length; i++)
      {
         patterns[i] = "*" + extensions[i];
      }
      dialog.setFilterExtensions(patterns);
      FileDialogAdapter adapter = new FileDialogAdapter(getSite().getShell(), dialog);
      while (!done)
      {
         fileName = adapter.open();

         if (fileName == null)
         {
            done = true;
         }
         else
         {
            boolean hasValidExtension = false;

            for (int i = 0; i < extensions.length; i++)
            {
               if (fileName.toLowerCase().endsWith(extensions[i]))
               {
                  hasValidExtension = true;
                  break;
               }
            }

            if (!hasValidExtension)
            {
               fileName += extensions[0];
            }

            if (new File(fileName).exists())
            {
               MessageBox mb = new MessageBox(getSite().getShell(),
                  SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.APPLICATION_MODAL);
               mb.setText("Save As");
               mb.setMessage("File " + fileName +
                  " already exists.\n Do you want to replace it?");
               done = (mb.open() == SWT.YES);
            }
            else
            {
               done = true;
            }
         }
      }

      return fileName;
   }

   private static String getFileExtension(File file)
   {
      String name;
      int separator;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      name = file.getName();
      separator = name.lastIndexOf('.');

      if ((separator > 0) && (separator < name.length() - 1))
      {
         String extension = name.substring(separator + 1).trim();
         return ((extension.length() > 0) ? extension : "");
      }
      else
      {
         return "";
      }
   }

   private static void saveFile(IProgressMonitor monitor, File from, File to)
      throws IOException, SAXException, ParserConfigurationException
   {
      String fromExtension;
      String toExtension;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      fromExtension = getFileExtension(from);
      toExtension = getFileExtension(to);

      if (fromExtension.equalsIgnoreCase("pmd") &&
          toExtension.equalsIgnoreCase("event"))
      {
         EventDumpXMLConverter eventDumpXMLConverter =
               new EventDumpXMLConverter();
         eventDumpXMLConverter.convert(monitor, from, to, true);
      }
      else if (fromExtension.equalsIgnoreCase("event") &&
               toExtension.equalsIgnoreCase("pmd"))
      {
         EventXMLDumpConverter eventXMLDumpConverter =
               new EventXMLDumpConverter();
         eventXMLDumpConverter.convert(monitor, from, to, true);
      }
      else
      {
         copyFile(from, to);
      }

      refreshWorkspaceFile(monitor, to);
   }

   private static void copyFile(File from, File to) throws IOException
   {
      FileInputStream in = null;
      FileOutputStream out = null;

      if ((from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         byte[] buffer = new byte[8192];
         int length;

         in = new FileInputStream(from);
         out = new FileOutputStream(to);

         while ((length = in.read(buffer)) != -1)
         {
            out.write(buffer, 0, length);
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
         if (out != null)
         {
            try
            {
               out.close();
            } catch (IOException ignore) {}
         }
      }
   }

   private static void refreshWorkspaceFile(IProgressMonitor monitor, File file)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      if (file.isFile())
      {
         IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().
            getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
         if (workspaceFile != null)
         {
            try
            {
               workspaceFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
            }
            catch (CoreException e)
            {
               throw new IOException(e.getMessage());
            }
         }
      }
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private class EditorOpenedHandler implements IPartListener
   {
      public void partActivated(IWorkbenchPart part) {}

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partOpened(IWorkbenchPart part)
      {
         synchronized (editorOpenedLock)
         {
            getSite().getPage().removePartListener(this);
            editorOpened = true;
            editorOpenedLock.notifyAll();
         }
      }
   }

   private class TableSelectionChangedHandler
      implements ISelectionChangedListener
   {
      private final StyledText text;

      TableSelectionChangedHandler(StyledText text)
      {
         if (text == null)
         {
            throw new IllegalArgumentException();
         }
         this.text = text;
      }

      public void selectionChanged(SelectionChangedEvent event)
      {
         IStructuredSelection selection;
         Object obj;

         selection = (IStructuredSelection) event.getSelection();
         obj = ((selection.size() == 1) ? selection.getFirstElement() : null);
         formatData(obj, text);
      }
   }

   private class TraceColumnSelectionHandler extends SelectionAdapter
   {
      private final int column;

      TraceColumnSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         traceSorter.sortByColumn(column);
         traceViewer.refresh();
      }
   }

   private class NotifyColumnSelectionHandler extends SelectionAdapter
   {
      private final int column;

      NotifyColumnSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         notifySorter.sortByColumn(column);
         notifyViewer.refresh();
      }
   }

   private class EventReaderHandler implements EventReaderClient
   {
      private final IProgressMonitor monitor;

      EventReaderHandler(IProgressMonitor monitor)
      {
         this.monitor = monitor;
      }

      public void commonAttributesRead(String target,
                                       boolean bigEndian,
                                       int osType,
                                       int numExecutionUnits,
                                       int tickLength,
                                       int microTickFrequency,
                                       String scope,
                                       String eventActions)
      {
         EventEditor.this.target = target;
         EventEditor.this.bigEndian = bigEndian;
         EventEditor.this.scope = scope;
         EventEditor.this.eventActions = eventActions;
      }

      public void eventRead(TargetEvent event)
      {
         if (monitor.isCanceled() || isDisposed())
         {
            throw new OperationCanceledException(
                  "Reading the event file has been cancelled");
         }
         traceContentProvider.addEvent(event);
      }
   }

   private abstract class EventReaderJob extends Job
   {
      final String path;

      EventReaderJob(String name, String path)
      {
         super(name);
         setPriority(SHORT);
         setUser(true);
         this.path = path;
      }

      void waitForEditorToOpen() throws InterruptedException
      {
         synchronized (editorOpenedLock)
         {
            while (!editorOpened)
            {
               editorOpenedLock.wait();
            }
         }
      }

      class UpdateUIRunner implements Runnable
      {
         public void run()
         {
            if (!isDisposed())
            {
               traceViewer.refresh();
               updateTraceHeader();
            }
         }
      }
   }

   private class EventDumpReaderJob extends EventReaderJob
   {
      EventDumpReaderJob(String path)
      {
         super("Reading event dump file", path);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            EventDumpReader eventDumpReader;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            waitForEditorToOpen();
            eventDumpReader = new EventDumpReader(new EventReaderHandler(monitor));
            eventDumpReader.read(new File(path));
            PlatformUI.getWorkbench().getDisplay().asyncExec(new UpdateUIRunner());
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus("Error when reading the event dump file", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private class EventXMLReaderJob extends EventReaderJob
   {
      EventXMLReaderJob(String path)
      {
         super("Reading event XML file", path);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            EventXMLReader eventXMLReader;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            waitForEditorToOpen();
            eventXMLReader = new EventXMLReader(new EventReaderHandler(monitor));
            eventXMLReader.read(new File(path));
            PlatformUI.getWorkbench().getDisplay().asyncExec(new UpdateUIRunner());
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (SAXException e)
         {
            return EventPlugin.createErrorStatus("Invalid event XML file", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus("Error when reading the event XML file", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private class EventWriterJob extends Job
   {
      private final String path;

      EventWriterJob(String path)
      {
         super("Writing event file");
         setPriority(SHORT);
         setUser(true);
         this.path = path;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         IEditorInput editorInput = getEditorInput();

         if (editorInput instanceof IURIEditorInput)
         {
            IURIEditorInput uriEditorInput = (IURIEditorInput) editorInput;

            try
            {
               monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
               saveFile(monitor, new File(uriEditorInput.getURI()), new File(path));
               return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
            }
            catch (OperationCanceledException e)
            {
               return Status.CANCEL_STATUS;
            }
            catch (Exception e)
            {
               return EventPlugin.createErrorStatus("Error when writing the event file", e);
            }
            finally
            {
               monitor.done();
            }
         }
         else
         {
            return Status.CANCEL_STATUS;
         }
      }
   }
}
