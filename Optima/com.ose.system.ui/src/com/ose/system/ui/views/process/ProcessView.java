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

package com.ose.system.ui.views.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Process;
import com.ose.system.ProcessFilter;
import com.ose.system.ProcessInfo;
import com.ose.system.Segment;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class ProcessView extends ViewPart
{
   // TODO: Add sid, creator, supervisor, supervisorStackSize, timeSlice,
   // vector, numSignalsAttached, and errorHandler when supported.

   public static final int COLUMN_NAME = 0;

   public static final int COLUMN_ID = 1;

   public static final int COLUMN_BID = 2;

   public static final int COLUMN_UID = 3;

   public static final int COLUMN_TYPE = 4;

   public static final int COLUMN_STATE = 5;

   public static final int COLUMN_PRIORITY = 6;

   public static final int COLUMN_NUM_SIGNALS_IN_QUEUE = 7;

   public static final int COLUMN_SIGSELECT_COUNT = 8;

   public static final int COLUMN_ENTRYPOINT = 9;

   public static final int COLUMN_SEMAPHORE_VALUE = 10;

   public static final int COLUMN_NUM_SIGNALS_OWNED = 11;

   public static final int COLUMN_FILE_NAME = 12;

   public static final int COLUMN_LINE_NUMBER = 13;

   public static final int COLUMN_STACK_SIZE = 14;

   public static final int COLUMN_EXECUTION_UNIT = 15;

   private Target target;
   private int scopeType;
   private int scopeId;
   private ProcessFilter processFilter;

   private TableViewer viewer;
   private ProcessSorter sorter;
   private BackgroundImageHandler tableBackgroundImageHandler;

   private ProcessFilterAction processFilterAction;
   private Action updateAction;

   public void createPartControl(Composite parent)
   {
      sorter = new ProcessSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(new ProcessContentProvider());
      viewer.setLabelProvider(new ProcessLabelProvider());
      viewer.setSorter(sorter);
      tableBackgroundImageHandler = new TableBackgroundImageHandler(viewer.getTable());

      createActions();
      createContextMenu();
      contributeToActionBars();
      setContentDescription("Target: None, Processes: 0");
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
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      column = new TableColumn(table, SWT.NONE);
      column.setText("Name");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("PID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("BID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("User");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_UID));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.NONE);
      column.setText("Type");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_TYPE));
      layout.setColumnData(column, new ColumnWeightData(9, 90));

      column = new TableColumn(table, SWT.NONE);
      column.setText("State");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_STATE));
      layout.setColumnData(column, new ColumnWeightData(11, 110));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Priority");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_PRIORITY));
      layout.setColumnData(column, new ColumnWeightData(5, 50));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Sigs in Queue");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_NUM_SIGNALS_IN_QUEUE));
      layout.setColumnData(column, new ColumnWeightData(7, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Sigselect Count");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_SIGSELECT_COUNT));
      layout.setColumnData(column, new ColumnWeightData(9, 90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Entrypoint");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_ENTRYPOINT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Fsem");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_SEMAPHORE_VALUE));
      layout.setColumnData(column, new ColumnWeightData(7, 70));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Owned Sigs");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_NUM_SIGNALS_OWNED));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.NONE);
      column.setText("File");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_FILE_NAME));
      layout.setColumnData(column, new ColumnWeightData(12, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Line");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_LINE_NUMBER));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Stack Size");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_STACK_SIZE));
      layout.setColumnData(column, new ColumnWeightData(7, 70));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Execution Unit");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_EXECUTION_UNIT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      return table;
   }

   private void createActions()
   {
      processFilterAction = new ProcessFilterAction();
      updateAction = new UpdateAction();
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
      manager.add(processFilterAction);
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(processFilterAction);
      manager.add(updateAction);
   }

   private void update()
   {
      if ((target != null) && !target.isKilled())
      {
         Job job = new GetProcessesJob(target, scopeType, scopeId, processFilter);
         IWorkbenchSiteProgressService siteService =
            (IWorkbenchSiteProgressService) getSite().
            getAdapter(IWorkbenchSiteProgressService.class);
         siteService.schedule(job, 0, true);
      }
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
   }

   public void show(Target target, Segment segment, Block block, Process process)
   {
      processFilterAction.run(target, segment, block, process);
   }

   static class TableBackgroundImageHandler extends BackgroundImageHandler
   {
      TableBackgroundImageHandler(Composite comp)
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
         // TODO: Anything to add?
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionHandler extends SelectionAdapter
   {
      private int column;

      SelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         sorter.sortByColumn(column);
         viewer.refresh();
      }
   }

   class ProcessFilterAction extends Action
   {
      ProcessFilterAction()
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

      public void run(Target target, Segment segment, Block block, Process process)
      {
         setEnabled(false);
         Job job = new GetTargetsJob(target, segment, block, process);
         job.schedule();
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

   class GetTargetsJob extends Job
   {
      private Target target;
      private Segment segment;
      private Block block;
      private Process process;

      GetTargetsJob()
      {
         super("Finding Targets");
         setPriority(SHORT);
         setUser(true);
      }

      GetTargetsJob(Target target, Segment segment, Block block, Process process)
      {
         this();
         this.target = target;
         this.segment = segment;
         this.block = block;
         this.process = process;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            ProcessFilterDialogRunner dialogRunner;

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
                     if (!t.isKilled() && t.hasProcessSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            if (target != null)
            {
               dialogRunner = new ProcessFilterDialogRunner(livingTargets,
                     target, segment, block, process);
            }
            else
            {
               dialogRunner = new ProcessFilterDialogRunner(livingTargets);
            }
            viewer.getControl().getDisplay().asyncExec(dialogRunner);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
            processFilterAction.setEnabled(true);
         }
      }
   }

   class ProcessFilterDialogRunner implements Runnable
   {
      private List targets;
      private Target target;
      private Segment segment;
      private Block block;
      private Process process;

      ProcessFilterDialogRunner(List targets)
      {
         this.targets = targets;
      }

      ProcessFilterDialogRunner(List targets,
                                Target target,
                                Segment segment,
                                Block block,
                                Process process)
      {
         this(targets);
         this.target = target;
         this.segment = segment;
         this.block = block;
         this.process = process;
      }

      public void run()
      {
         Shell shell;
         ProcessFilterDialog dialog;
         int result;

         shell = viewer.getControl().getShell();
         if (target != null)
         {
            dialog = new ProcessFilterDialog(
                  shell, targets, target, segment, block, process);
         }
         else
         {
            dialog = new ProcessFilterDialog(shell, targets);
         }
         result = dialog.open();
         if (result == Window.OK)
         {
            Target filterTarget = dialog.getTarget();
            if (filterTarget != null)
            {
               Job job = new GetProcessesJob(filterTarget,
                                             dialog.getScopeType(),
                                             dialog.getScopeId(),
                                             dialog.getProcessFilter());
               IWorkbenchSiteProgressService siteService =
                  (IWorkbenchSiteProgressService) ProcessView.this.getSite().
                  getAdapter(IWorkbenchSiteProgressService.class);
               siteService.schedule(job, 0, true);
            }
         }
      }
   }

   class GetProcessesJob extends Job
   {
      private final Target target;
      private final int scopeType;
      private final int scopeId;
      private final ProcessFilter processFilter;

      GetProcessesJob(Target target, int scopeType, int scopeId, ProcessFilter processFilter)
      {
         super("Filtering Processes");
         setPriority(SHORT);
         this.target = target;
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.processFilter = processFilter;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            ProcessInfo[] processes;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            target.connect(monitor);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            processes = target.getFilteredProcesses(monitor, scopeType, scopeId, processFilter);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            viewer.getControl().getDisplay().asyncExec(new UpdateViewerRunner(
                  target, scopeType, scopeId, processFilter, processes));

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
               "Error reported from target when retrieving processes for target " + target, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when retrieving processes for target " + target, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when retrieving processes for target " + target, e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class UpdateViewerRunner implements Runnable
   {
      private final Target target;
      private final int scopeType;
      private final int scopeId;
      private final ProcessFilter processFilter;
      private final ProcessInfo[] processes;

      UpdateViewerRunner(Target target,
                         int scopeType,
                         int scopeId,
                         ProcessFilter processFilter,
                         ProcessInfo[] processes)
      {
         this.target = target;
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.processFilter = processFilter;
         this.processes = processes;
      }

      public void run()
      {
         if (tableBackgroundImageHandler != null)
         {
            tableBackgroundImageHandler.dispose();
            tableBackgroundImageHandler = null;
         }

         ProcessView.this.target = target;
         ProcessView.this.scopeType = scopeType;
         ProcessView.this.scopeId = scopeId;
         ProcessView.this.processFilter = processFilter;

         resizeBlockColumn();
         sorter.reset();
         viewer.setInput(processes);
         setContentDescription("Target: " + target +
                               ", Processes: " + processes.length);
      }

      private void resizeBlockColumn()
      {
         Table table;
         TableColumn column;
         Composite parent;
         TableColumnLayout layout;

         table = viewer.getTable();
         column = table.getColumn(COLUMN_BID);
         parent = table.getParent();
         layout = (TableColumnLayout) parent.getLayout();
         if (target.hasBlockSupport())
         {
            layout.setColumnData(column, new ColumnWeightData(8, 80));
         }
         else
         {
            layout.setColumnData(column, new ColumnPixelData(0, false));
         }
         parent.layout();
      }
   }
}
