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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;
import org.xml.sax.SAXException;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartSelectionListener;
import com.ose.chart.ui.ChartViewer;
import com.ose.prof.format.ReportDumpReader;
import com.ose.prof.format.ReportDumpXMLConverter;
import com.ose.prof.format.ReportReaderClient;
import com.ose.prof.format.ReportXMLDumpConverter;
import com.ose.prof.format.ReportXMLReader;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;
import com.ose.system.Target;
import com.ose.system.TargetReport;
import com.ose.system.TargetReports;
import com.ose.system.UserReport;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.FileDialogAdapter;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public abstract class ProfilerEditor extends EditorPart
   implements SynchronizedScrollPart
{
   public static final int MAX_REPORTS = 5000;

   static final int TAB_FOLDER_TABLE = 0;

   static final int EDITOR_PROPERTY_READY = 0x1001;
   static final int EDITOR_PROPERTY_FAILED = 0x1002;

   static final int COLUMN_TICK = 0;
   static final int COLUMN_INTERVAL = 1;

   private static final String[] REPORT_FILE_EXTENSIONS = {".pmd", ".report"};

   private static final DateFormat DATE_FORMAT;

   private final Object editorOpenedLock = new Object();
   private boolean editorOpened;
   private boolean offline;
   private volatile boolean offlineFeaturesEnabled;
   private volatile boolean disposed;

   String target;
   int tickLength;
   int integrationInterval;
   int maxReports;

   private int numTotalReports;
   /* The content provider is shared between all charts and the table. */
   private ProfilerContentProvider contentProvider;
   private final ListenerList synchScrollListeners = new ListenerList();

   private FormToolkit toolkit;
   private Form form;
   private Label headerLabel;
   private CTabFolder tabFolder;
   private final Map tabIds = new HashMap(5);

   private Label integrationIntervalLabel;
   private Label maxReportsLabel;
   private TableViewer tableViewer;
   private ProfilerSorter tableSorter;
   private ActionMultiplexer copyActionMux;
   private ActionMultiplexer selectAllActionMux;
   private Button relativeTableButton;

   static
   {
      DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
      DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      if (input instanceof ProfilerEditorInput)
      {
         ProfilerEditorInput profilerEditorInput;

         profilerEditorInput = (ProfilerEditorInput) input;
         offline = false;
         target = profilerEditorInput.getTarget().toString();
         tickLength = profilerEditorInput.getTarget().getTickLength();
         integrationInterval = profilerEditorInput.getIntegrationInterval();
         maxReports = profilerEditorInput.getMaxReports();
      }
      else if (input instanceof IURIEditorInput)
      {
         IURIEditorInput uriEditorInput;
         IPath path;
         String extension;

         offline = true;
         target = "None";
         tickLength = 0;
         integrationInterval = 0;
         maxReports = 0;

         uriEditorInput = (IURIEditorInput) input;
         path = new Path(uriEditorInput.getURI().getPath());
         extension = path.getFileExtension();

         if (extension == null)
         {
            throw new PartInitException("Unknown file format");
         }
         else if (extension.equalsIgnoreCase("pmd"))
         {
            Job job = new ReportDumpReaderJob(path.toOSString());
            job.schedule();
         }
         else if (extension.equalsIgnoreCase("report"))
         {
            Job job = new ReportXMLReaderJob(path.toOSString());
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

      setSite(site);
      setInput(input);
      setPartName(input.getName());
   }

   public void createPartControl(Composite parent)
   {
      GridLayout layout;

      toolkit = new FormToolkit(parent.getDisplay());
      toolkit.getColors().initializeSectionToolBarColors();
      form = toolkit.createForm(parent);
      form.setText(getChartTitle());
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      form.getBody().setLayout(layout);

      headerLabel = toolkit.createLabel(form.getHead(), null);
      form.setHeadClient(headerLabel);

      tabFolder = new CTabFolder(form.getBody(), SWT.BOTTOM);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

      getSite().getPage().addPartListener(new EditorOpenedHandler());

      if (!offline)
      {
         createUI();
         setOfflineFeaturesEnabled(false);
      }
   }

   @Override
   public void dispose()
   {
      super.dispose();
      toolkit.dispose();
      disposed = true;
   }

   public boolean isDisposed()
   {
      return disposed;
   }

   public void setFocus()
   {
      tabFolder.setFocus();
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
      String fileName = getSaveFileName(REPORT_FILE_EXTENSIONS);

      if (fileName != null)
      {
         Job job = new ReportWriterJob(fileName);
         job.schedule();
      }
   }

   public boolean isSaveAsAllowed()
   {
      return offline;
   }

   public boolean isOfflineFeaturesEnabled()
   {
      return offlineFeaturesEnabled;
   }

   public void setOfflineFeaturesEnabled(boolean enabled)
   {
      checkThread();
      offlineFeaturesEnabled = enabled;
      if (!offline)
      {
         if (enabled)
         {
            tableSorter.reset();
            getTableViewer().setSorter(tableSorter);
         }
         else
         {
            getTableViewer().setSorter(null);
         }
      }
   }

   public void setIntegrationInterval(int integrationInterval)
   {
      checkThread();
      this.integrationInterval = integrationInterval;
      updateParametersGroup();
   }

   public void setMaxReports(int maxReports)
   {
      checkThread();
      this.maxReports = maxReports;
      updateParametersGroup();
   }

   public void clearReports()
   {
      checkThread();
      numTotalReports = 0;
      getTableViewer().setInput(new Object[0]);
      updateHeaderLabels();
   }

   public void addReports(TargetReports reports)
   {
      addReports(reports.getReports());
   }

   public void addReports(TargetReport[] reports)
   {
      checkThread();
      numTotalReports += reports.length;
   }

   public void addSynchronizedScrollPartListener(
                                       SynchronizedScrollPartListener listener)
   {
      synchScrollListeners.add(listener);
      addContentRangeTransformer();
   }

   public void removeSynchronizedScrollPartListener(
                                       SynchronizedScrollPartListener listener)
   {
      synchScrollListeners.remove(listener);
      if (synchScrollListeners.isEmpty())
      {
         removeContentRangeTransformer();
      }
   }

   public long getFirstLocalTime()
   {
      if (getContentProvider().getNumReports() > 0)
      {
         return getContentProvider().getReport(0).getNanoSeconds();
      }
      else
      {
         return 0;
      }
   }

   public long getLastLocalTime()
   {
      if (getContentProvider().getNumReports() > 0)
      {
         return getContentProvider().getReport(
               getContentProvider().getNumReports() - 1).getNanoSeconds();
      }
      else
      {
         return 0;
      }
   }

   public void setScrollWindowRange(long time1, long time2)
   {
      long intervalInNanos = (integrationInterval & 0xFFFFFFFFL) *
         (tickLength & 0xFFFFFFFFL) * 1000L;

      long firstReportTime = getFirstLocalTime();
      long lastReportTime = getLastLocalTime();

      long startSteps = (firstReportTime - time1) / intervalInNanos;
      long endSteps = (time2 - lastReportTime) / intervalInNanos;
      long length =
         startSteps + getContentProvider().getItemRange().getCount() + endSteps;

      if ((length >= 0) && (length <= Integer.MAX_VALUE))
      {
         clearChartItemSelection();
         updateContentRangeTransformer((int) startSteps,
                                       (int) length,
                                       intervalInNanos);
      }
      else
      {
         throw new IllegalArgumentException(
            "Length of synchronization interval exceeds maximum limit.");
      }
   }

   public abstract void scrollTo(long time);

   abstract void clearChartItemSelection();
   
   abstract void addContentRangeTransformer();

   abstract void removeContentRangeTransformer();

   abstract void updateContentRangeTransformer(int offset,
                                               int length,
                                               long integrationInterval);

   void notifyScrolledTo(long time)
   {
      Object[] listeners = synchScrollListeners.getListeners();
      for (int i = 0; i < listeners.length; i++)
      {
         SynchronizedScrollPartListener listener =
               (SynchronizedScrollPartListener) listeners[i];
         listener.scrolledTo(this, time);
      }
   }

   String getTarget()
   {
      return target;
   }

   int getNumTotalReports()
   {
      return numTotalReports;
   }

   ProfilerContentProvider getContentProvider()
   {
      return contentProvider;
   }

   FormToolkit getToolkit()
   {
      return toolkit;
   }

   CTabFolder getTabFolder()
   {
      return tabFolder;
   }

   TableViewer getTableViewer()
   {
      return tableViewer;
   }

   ActionMultiplexer getCopyActionMux()
   {
      return copyActionMux;
   }

   ActionMultiplexer getSelectAllActionMux()
   {
      return selectAllActionMux;
   }

   private void createUI()
   {
      contentProvider = createContentProvider();
      copyActionMux = new ActionMultiplexer();
      selectAllActionMux = new ActionMultiplexer();
      createChartTabs(tabFolder);
      createTableTab(tabFolder);
      updateActionBars();
      updateHeaderLabels();
      updateParametersGroup();

      // XXX: For some unknown reason, the chart tab (which is the first tab
      // and thus the initially selected tab) will sometimes not be drawn in
      // the off-line case (it is empty) unless we force a complete redraw
      // by either selecting another tab and then reselecting the chart tab
      // or by resizing the tab folder.
      tabFolder.setSelection(1);
      tabFolder.setSelection(0);
      tabFolder.addSelectionListener(new TabFolderSelectionHandler());
   }

   abstract ProfilerContentProvider createContentProvider();

   abstract void createChartTabs(CTabFolder tabFolder);

   private void createTableTab(CTabFolder tabFolder)
   {
      Composite comp;
      GridLayout layout;
      CTabItem tabItem;

      comp = toolkit.createComposite(tabFolder);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      layout.marginBottom = 5;
      comp.setLayout(layout);

      createParametersGroup(comp);

      tableViewer = createTableViewer(comp);
      tableSorter = (ProfilerSorter) tableViewer.getSorter();
      copyActionMux.addAction(new TableCopyAction(tableViewer));
      selectAllActionMux.addAction(new TableSelectAllAction(tableViewer));

      if (isRelativeValuesSupported())
      {
         relativeTableButton =
            toolkit.createButton(comp, "Relative Values", SWT.CHECK);
         relativeTableButton.setSelection(true);
         relativeTableButton.addSelectionListener(new RelativeTableButtonSelectionHandler());
      }

      tabItem = new CTabItem(tabFolder, SWT.NONE);
      tabItem.setText("Table");
      tabItem.setControl(comp);
      assignTabId(tabItem, TAB_FOLDER_TABLE);
   }

   Group createParametersGroup(Composite parent)
   {
      Group group;
      GridLayout layout;
      Label label;

      group = new Group(parent, SWT.SHADOW_NONE);
      toolkit.adapt(group);
      group.setText("Profiling Parameters");
      group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      layout = new GridLayout(2, false);
      layout.horizontalSpacing = 10;
      group.setLayout(layout);

      label = toolkit.createLabel(group, "Integration Interval (ticks):");
      label.setLayoutData(new GridData());

      integrationIntervalLabel = toolkit.createLabel(group, null);
      integrationIntervalLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      integrationIntervalLabel.setData("name", "integrationIntervalLabel");

      label = toolkit.createLabel(group, "Number of Reports on Target:");
      label.setLayoutData(new GridData());

      maxReportsLabel = toolkit.createLabel(group, null);
      maxReportsLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      maxReportsLabel.setData("name", "maxReportsLabel");

      return group;
   }

   abstract TableViewer createTableViewer(Composite parent);

   abstract String getChartTitle();

   abstract String getHeaderString();

   String getDateString()
   {
      if (getContentProvider().getNumReports() > 0)
      {
         long time = getContentProvider().getReport(0).getNanoSeconds();
         return DATE_FORMAT.format(new Date(time / 1000000L));
      }
      else
      {
         return "None";
      }
   }

   void updateHeaderLabels()
   {
      form.setText(getChartTitle());
      headerLabel.setText(getHeaderString());
   }

   void updateParametersGroup()
   {
      integrationIntervalLabel.setText(toU32String(integrationInterval));
      maxReportsLabel.setText(toU32String(maxReports));
   }

   void updateRelativeTableButtonSelection()
   {
      if (isRelativeValuesSupported())
      {
         relativeTableButton.setSelection(getContentProvider().isRelative());
      }
   }

   private void updateActionBars()
   {
      IActionBars bars = getEditorSite().getActionBars();
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyActionMux);
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAllActionMux);
      bars.updateActionBars();
   }

   void assignTabId(CTabItem tab, int tabId)
   {
      tabIds.put(tab, new Integer(tabId));
   }

   int getTabId(CTabItem tab)
   {
      if (tab == null)
      {
         return -1;
      }
      Integer tabId = (Integer) tabIds.get(tab);
      if (tabId == null)
      {
         return -1;
      }
      return tabId.intValue();
   }
      
   abstract void tabSelected(int tabId);

   void setRelative(boolean relative)
   {
      ProfilerLabelProvider tableLabelProvider;

      getContentProvider().setRelative(relative);
      tableLabelProvider =
         (ProfilerLabelProvider) getTableViewer().getLabelProvider();
      tableLabelProvider.setRelative(relative);
      getTableViewer().refresh();
   }

   void setRealTime(boolean realTime)
   {
      getContentProvider().setRealTime(realTime);
   }

   boolean isRelativeValuesSupported()
   {
      return true;
   }

   boolean isSelectionValidForStatistics(Chart chart)
   {
      return true;
   }

   SelectionRange calculateRange(Chart chart)
   {
      Range items = chart.getItemSelection();
      int startIndex = items.getOffset();
      int count = items.getCount();
      int endIndex = startIndex + count - 1;
      SelectionRange range = null;

      if (count > 0)
      {
         ChartContentProvider content = chart.getContentProvider();
         long start;
         long end;
         String startLabel;
         String endLabel;

         if (content instanceof ProfilerContentRangeTransformer)
         {
            ProfilerContentRangeTransformer transformer =
               (ProfilerContentRangeTransformer) content;
            start = transformer.getTimeOfReport(startIndex);
            end = transformer.getTimeOfReport(endIndex);
            startLabel = transformer.getItemLabel(0, 0, startIndex);
            endLabel = transformer.getItemLabel(0, 0, endIndex);
         }
         else
         {
            start = getContentProvider().getReport(startIndex).getNanoSeconds();
            end = getContentProvider().getReport(endIndex).getNanoSeconds();
            startLabel = getContentProvider().getItemLabel(0, 0, startIndex);
            endLabel = getContentProvider().getItemLabel(0, 0, endIndex);
         }
         range = new SelectionRange(start, end, startLabel, endLabel);
      }

      return range;
   }

   Statistics calculateStatistics(Chart chart)
   {
      if (isOfflineFeaturesEnabled() && isSelectionValidForStatistics(chart))
      {
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
            double value = Double.NaN;

            for (int s = 0; s < seriesCount; s++)
            {
               double v = content.getValue(0, seriesOffset + s, itemsOffset + i);
               if (!Double.isNaN(v))
               {
                  value = Double.isNaN(value) ? v : value + v;
               }
            }

            if (!Double.isNaN(value))
            {
               max = Double.isNaN(max) ? value : Math.max(max, value);
               min = Double.isNaN(min) ? value : Math.min(min, value);
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

   abstract ReportReaderClient createReportReaderClient(IProgressMonitor monitor);

   abstract void readDumpReports(ReportDumpReader reportDumpReader, File file)
      throws IOException;

   abstract void convertDumpReports(IProgressMonitor monitor,
                                    ReportDumpXMLConverter reportDumpXMLConverter,
                                    File from,
                                    File to)
      throws IOException;

   final void checkThread() throws RuntimeException
   {
      if (form.getDisplay().getThread() != Thread.currentThread())
      {
         SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
      }
   }

   static String toExecutionUnitString(short executionUnit)
   {
      return ((executionUnit == Target.ALL_EXECUTION_UNITS) ?
            "All" : Integer.toString(executionUnit & 0xFFFF));
   }

   static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
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

   private void saveFile(IProgressMonitor monitor, File from, File to)
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
          toExtension.equalsIgnoreCase("report"))
      {
         ReportDumpXMLConverter reportDumpXMLConverter =
               new ReportDumpXMLConverter();
         convertDumpReports(monitor, reportDumpXMLConverter, from, to);
      }
      else if (fromExtension.equalsIgnoreCase("report") &&
               toExtension.equalsIgnoreCase("pmd"))
      {
         ReportXMLDumpConverter reportXMLDumpConverter =
               new ReportXMLDumpConverter();
         reportXMLDumpConverter.convert(monitor, from, to);
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

   static class Statistics
   {
      public final double min;
      public final double mean;
      public final double max;

      Statistics(double min, double mean, double max)
      {
         this.min = min;
         this.mean = mean;
         this.max = max;
      }
   }

   class StatisticsPanel extends Composite
   {
      private final DecimalFormat statsFormat =
         new DecimalFormat("#0.0", new DecimalFormatSymbols(Locale.US));

      private ChartViewer chartViewer;

      private Button relativeButton;
      private Button realTimeButton;
      private Text rangeText;
      private Text minText;
      private Text meanText;
      private Text maxText;

      StatisticsPanel(Composite parent, int style)
      {
         super(parent, style);
         toolkit.adapt(this);
         setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         GridLayout layout = new GridLayout(10, false);
         layout.marginWidth = 0;
         layout.marginHeight = 0;
         setLayout(layout);
      }

      public void init(ChartViewer chartViewer)
      {
         Label label;
         GridData gd;

         this.chartViewer = chartViewer;

         if (isRelativeValuesSupported())
         {
            relativeButton = toolkit.createButton(this, "Relative Values", SWT.CHECK);
            relativeButton.setSelection(true);
            relativeButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));
            relativeButton.addSelectionListener(new RelativeButtonSelectionHandler());
         }

         realTimeButton = toolkit.createButton(this, "Real Time", SWT.CHECK);
         realTimeButton.setSelection(true);
         realTimeButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));
         realTimeButton.addSelectionListener(new RealTimeButtonSelectionHandler());

         label = toolkit.createLabel(this, "Range (\u00B5s):");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         rangeText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 80;
         rangeText.setLayoutData(gd);

         label = toolkit.createLabel(this, "Min:");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         minText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 80;
         minText.setLayoutData(gd);

         label = toolkit.createLabel(this, "Mean:");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         meanText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 80;
         meanText.setLayoutData(gd);

         label = toolkit.createLabel(this, "Max:");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         maxText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 80;
         maxText.setLayoutData(gd);
      }

      public void updateSelections()
      {
         if (isRelativeValuesSupported())
         {
            relativeButton.setSelection(getContentProvider().isRelative());
         }
         realTimeButton.setSelection(getContentProvider().isRealTime());
      }

      public void setRange(SelectionRange range)
      {
         if ((range != null) && (range.getLength() >= 0))
         {
            long length = range.getLength() / 1000L;
            rangeText.setText(Long.toString(length));
         }
         else
         {
            rangeText.setText("");
         }
      }

      public void setStatisticsEnabled(boolean enabled)
      {
         minText.setEnabled(enabled);
         meanText.setEnabled(enabled);
         maxText.setEnabled(enabled);

         if (enabled)
         {
            setStatistics(calculateStatistics(chartViewer.getChart()));
         }
         else
         {
            clearStatistics();
         }
      }

      public void setStatistics(Statistics statistics)
      {
         if (statistics != null)
         {
            minText.setText(statsFormat.format(statistics.min));
            meanText.setText(statsFormat.format(statistics.mean));
            maxText.setText(statsFormat.format(statistics.max));
         }
         else
         {
            clearStatistics();
         }
      }

      public void clearStatistics()
      {
         minText.setText("");
         meanText.setText("");
         maxText.setText("");
      }

      public boolean isStatisticsCleared()
      {
         return (minText.getText().length() == 0) ||
                (meanText.getText().length() == 0) ||
                (maxText.getText().length() == 0);
      }

      private class RelativeButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent e)
         {
            Button button = (Button) e.widget;
            setRelative(button.getSelection());
            setStatistics(calculateStatistics(chartViewer.getChart()));
         }
      }

      private class RealTimeButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent e)
         {
            Button button = (Button) e.widget;
            setRealTime(button.getSelection());
         }
      }
   }

   static class SelectionRange
   {
      public final long start;
      public final long end;
      public final String startLabel;
      public final String endLabel;

      SelectionRange(long start, long end, String startLabel, String endLabel)
      {
         this.start = start;
         this.end = end;
         this.startLabel = startLabel;
         this.endLabel = endLabel;
      }

      long getLength()
      {
         return end - start;
      }
   }

   class SelectionRangePanel extends Composite
   {
      private Button realTimeButton;
      private Text rangeText;
      private Text startText;
      private Text endText;

      SelectionRangePanel(Composite parent, int style)
      {
         super(parent, style);
         toolkit.adapt(this);
         setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
         GridLayout layout = new GridLayout(7, false);
         layout.marginWidth = 0;
         layout.marginHeight = 0;
         setLayout(layout);
      }

      public void init()
      {
         Label label;
         GridData gd;

         realTimeButton = toolkit.createButton(this, "Real Time", SWT.CHECK);
         realTimeButton.setSelection(true);
         realTimeButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));
         realTimeButton.addSelectionListener(new RealTimeButtonSelectionHandler());

         label = toolkit.createLabel(this, "Range (\u00B5s):");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         rangeText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 80;
         rangeText.setLayoutData(gd);

         label = toolkit.createLabel(this, "Start:");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         startText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 90;
         startText.setLayoutData(gd);

         label = toolkit.createLabel(this, "End:");
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.horizontalIndent = 10;
         label.setLayoutData(gd);

         endText = toolkit.createText(
               this, null, SWT.SINGLE | SWT.RIGHT | SWT.READ_ONLY);
         gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
         gd.widthHint = 90;
         endText.setLayoutData(gd);
      }

      public void updateSelections()
      {
         realTimeButton.setSelection(getContentProvider().isRealTime());
      }

      public void setRange(SelectionRange range)
      {
         if ((range != null) && (range.getLength() >= 0))
         {
            long length = range.getLength() / 1000L;
            rangeText.setText(Long.toString(length));
            startText.setText(range.startLabel);
            endText.setText(range.endLabel);
         }
         else
         {
            rangeText.setText("");
            startText.setText("");
            endText.setText("");
         }
      }

      private class RealTimeButtonSelectionHandler extends SelectionAdapter
      {
         @Override
         public void widgetSelected(SelectionEvent e)
         {
            Button button = (Button) e.widget;
            setRealTime(button.getSelection());
         }
      }
   }

   private class EditorOpenedHandler implements IPartListener
   {
      public void partOpened(IWorkbenchPart part)
      {
         synchronized (editorOpenedLock)
         {
            getSite().getPage().removePartListener(this);
            editorOpened = true;
            editorOpenedLock.notifyAll();
         }
      }

      public void partActivated(IWorkbenchPart part) {}

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}
   }

   private class TabFolderSelectionHandler extends SelectionAdapter
   {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
         tabSelected(getTabId(((CTabFolder) e.widget).getSelection()));
      }
   }

   class StatisticsChartSelectionHandler implements ChartSelectionListener
   {
      private final StatisticsPanel statsPanel;

      StatisticsChartSelectionHandler(StatisticsPanel statsPanel)
      {
         this.statsPanel = statsPanel;
      }

      public void itemSelectionChanged(Chart chart)
      {
         statsPanel.setRange(calculateRange(chart));
         statsPanel.setStatistics(calculateStatistics(chart));
      }

      public void seriesSelectionChanged(Chart chart)
      {
         statsPanel.setStatistics(calculateStatistics(chart));
      }

      public void layerSelectionChanged(Chart chart)
      {
         statsPanel.setStatistics(calculateStatistics(chart));
      }
   }

   class RangeChartSelectionHandler implements ChartSelectionListener
   {
      private final SelectionRangePanel rangePanel;

      RangeChartSelectionHandler(SelectionRangePanel rangePanel)
      {
         this.rangePanel = rangePanel;
      }

      public void itemSelectionChanged(Chart chart)
      {
         rangePanel.setRange(calculateRange(chart));
      }

      public void seriesSelectionChanged(Chart chart)
      {
         // Do nothing
      }

      public void layerSelectionChanged(Chart chart)
      {
         // Do nothing
      }
   }

   class ColumnSelectionHandler extends SelectionAdapter
   {
      private final int column;

      ColumnSelectionHandler(int column)
      {
         this.column = column;
      }

      @Override
      public void widgetSelected(SelectionEvent event)
      {
         tableSorter.setColumn(column);
         getTableViewer().refresh();
      }
   }

   private class RelativeTableButtonSelectionHandler extends SelectionAdapter
   {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
         Button button = (Button) e.widget;
         setRelative(button.getSelection());
      }
   }

   abstract class AbstractReportReaderClient implements ReportReaderClient
   {
      private final IProgressMonitor monitor;
      private final LinkedList reports;

      AbstractReportReaderClient(IProgressMonitor monitor)
      {
         this.monitor = monitor;
         reports = new LinkedList();
      }

      public List getReports()
      {
         return reports;
      }

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
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

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
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

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
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

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
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

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
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void processRead(int id, String name)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void blockRead(int id, String name)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void cpuReportRead(CPUReport report)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void cpuPriorityReportRead(CPUPriorityReport report)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void cpuProcessReportRead(CPUProcessReport report)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void cpuBlockReportRead(CPUBlockReport report)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      public void userReportRead(UserReport report)
      {
         throw new IllegalStateException(getInvalidFileTypeMessage());
      }

      abstract String getInvalidFileTypeMessage();

      void handleReportRead(TargetReport report)
      {
         if (monitor.isCanceled() || isDisposed())
         {
            throw new OperationCanceledException("Cancelled reading report file");
         }

         if (reports.size() + 1 > MAX_REPORTS)
         {
            reports.removeFirst();
            numTotalReports++;
         }
         reports.addLast(report);
      }
   }

   private abstract class ReportReaderJob extends Job
   {
      final String path;

      ReportReaderJob(String name, String path)
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

      void firePropertyChangeInUI(int propertyId)
      {
         PlatformUI.getWorkbench().getDisplay().asyncExec(
               new FirePropertyChangeRunner(propertyId));
      }

      class CreateUIRunner implements Runnable
      {
         private final List reports;

         CreateUIRunner(List reports)
         {
            this.reports = reports;
         }

         public void run()
         {
            boolean ok = false;

            try
            {
               if (!isDisposed())
               {
                  createUI();
                  addReports((TargetReport[]) reports.toArray(new TargetReport[0]));
                  setOfflineFeaturesEnabled(true);
                  setFocus();
                  ok = true;
               }
            }
            finally
            {
               firePropertyChange(ok ? EDITOR_PROPERTY_READY : EDITOR_PROPERTY_FAILED);
            }
         }
      }

      private class FirePropertyChangeRunner implements Runnable
      {
         private final int propertyId;

         FirePropertyChangeRunner(int propertyId)
         {
            this.propertyId = propertyId;
         }

         public void run()
         {
            firePropertyChange(propertyId);
         }
      }
   }

   private class ReportDumpReaderJob extends ReportReaderJob
   {
      ReportDumpReaderJob(String path)
      {
         super("Reading report dump file", path);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         boolean ok = false;

         try
         {
            AbstractReportReaderClient reportReaderClient;
            ReportDumpReader reportDumpReader;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            waitForEditorToOpen();
            reportReaderClient = (AbstractReportReaderClient)
               createReportReaderClient(monitor);
            reportDumpReader = new ReportDumpReader(reportReaderClient);
            readDumpReports(reportDumpReader, new File(path));
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                  new CreateUIRunner(reportReaderClient.getReports()));
            ok = true;
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when reading the report dump file", e);
         }
         finally
         {
            if (!ok)
            {
               firePropertyChangeInUI(EDITOR_PROPERTY_FAILED);
            }
            monitor.done();
         }
      }
   }

   private class ReportXMLReaderJob extends ReportReaderJob
   {
      ReportXMLReaderJob(String path)
      {
         super("Reading report XML file", path);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         boolean ok = false;

         try
         {
            AbstractReportReaderClient reportReaderClient;
            ReportXMLReader reportXMLReader;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            waitForEditorToOpen();
            reportReaderClient = (AbstractReportReaderClient)
               createReportReaderClient(monitor);
            reportXMLReader = new ReportXMLReader(reportReaderClient);
            reportXMLReader.read(new File(path));
            PlatformUI.getWorkbench().getDisplay().asyncExec(
                  new CreateUIRunner(reportReaderClient.getReports()));
            ok = true;
            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (SAXException e)
         {
            return ProfilerPlugin.createErrorStatus("Invalid report XML file", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when reading the report XML file", e);
         }
         finally
         {
            if (!ok)
            {
               firePropertyChangeInUI(EDITOR_PROPERTY_FAILED);
            }
            monitor.done();
         }
      }
   }

   private class ReportWriterJob extends Job
   {
      private final String path;

      ReportWriterJob(String path)
      {
         super("Writing report file");
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
               return ProfilerPlugin.createErrorStatus(
                  "Error when writing the report file", e);
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
