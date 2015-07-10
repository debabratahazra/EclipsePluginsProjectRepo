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

package com.ose.system.ui.views.block;

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
import com.ose.system.BlockFilter;
import com.ose.system.BlockInfo;
import com.ose.system.Gate;
import com.ose.system.Segment;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class BlockView extends ViewPart
{
   // TODO: Add supervisor, numSignalsAttached, and errorHandler when supported.

   public static final int COLUMN_NAME = 0;

   public static final int COLUMN_BID = 1;

   public static final int COLUMN_SID = 2;

   public static final int COLUMN_UID = 3;

   public static final int COLUMN_MAX_SIGNAL_SIZE = 4;

   public static final int COLUMN_SIGNAL_POOL_ID = 5;

   public static final int COLUMN_STACK_POOL_ID = 6;

   public static final int COLUMN_EXECUTION_UNIT = 7;

   private Target target;
   private int scopeType;
   private int scopeId;
   private BlockFilter blockFilter;

   private TableViewer viewer;
   private BlockSorter sorter;
   private BackgroundImageHandler tableBackgroundImageHandler;

   private BlockFilterAction blockFilterAction;
   private Action updateAction;

   public void createPartControl(Composite parent)
   {
      sorter = new BlockSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(new BlockContentProvider());
      viewer.setLabelProvider(new BlockLabelProvider());
      viewer.setSorter(sorter);
      tableBackgroundImageHandler = new TableBackgroundImageHandler(viewer.getTable());

      createActions();
      createContextMenu();
      contributeToActionBars();
      setContentDescription("Target: None, Blocks: 0");
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
      column.setText("BID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_BID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("SID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_SID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("User");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_UID));
      layout.setColumnData(column, new ColumnWeightData(4, 40));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Max Signal Size");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_MAX_SIGNAL_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Signal Pool ID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_SIGNAL_POOL_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Stack Pool ID");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_STACK_POOL_ID));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Execution Unit");
      column.setMoveable(true);
      column.addSelectionListener(new SelectionHandler(COLUMN_EXECUTION_UNIT));
      layout.setColumnData(column, new ColumnWeightData(8, 90));

      return table;
   }

   private void createActions()
   {
      blockFilterAction = new BlockFilterAction();
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
      manager.add(blockFilterAction);
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(blockFilterAction);
      manager.add(updateAction);
   }

   private void update()
   {
      if ((target != null) && !target.isKilled())
      {
         Job job = new GetBlocksJob(target, scopeType, scopeId, blockFilter);
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

   public void show(Target target, Segment segment, Block block)
   {
      blockFilterAction.run(target, segment, block);
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

   class BlockFilterAction extends Action
   {
      BlockFilterAction()
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

      public void run(Target target, Segment segment, Block block)
      {
         setEnabled(false);
         Job job = new GetTargetsJob(target, segment, block);
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

      GetTargetsJob()
      {
         super("Finding Targets");
         setPriority(SHORT);
         setUser(true);
      }

      GetTargetsJob(Target target, Segment segment, Block block)
      {
         this();
         this.target = target;
         this.segment = segment;
         this.block = block;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            BlockFilterDialogRunner dialogRunner;

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
                     if (!t.isKilled() && t.hasBlockSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            if (target != null)
            {
               dialogRunner = new BlockFilterDialogRunner(
                     livingTargets, target, segment, block);
            }
            else
            {
               dialogRunner = new BlockFilterDialogRunner(livingTargets);
            }
            viewer.getControl().getDisplay().asyncExec(dialogRunner);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
            blockFilterAction.setEnabled(true);
         }
      }
   }

   class BlockFilterDialogRunner implements Runnable
   {
      private List targets;
      private Target target;
      private Segment segment;
      private Block block;

      BlockFilterDialogRunner(List targets)
      {
         this.targets = targets;
      }

      BlockFilterDialogRunner(List targets,
                              Target target,
                              Segment segment,
                              Block block)
      {
         this(targets);
         this.target = target;
         this.segment = segment;
         this.block = block;
      }

      public void run()
      {
         Shell shell;
         BlockFilterDialog dialog;
         int result;

         shell = viewer.getControl().getShell();
         if (target != null)
         {
            dialog = new BlockFilterDialog(shell, targets, target, segment, block);
         }
         else
         {
            dialog = new BlockFilterDialog(shell, targets);
         }
         result = dialog.open();
         if (result == Window.OK)
         {
            Target filterTarget = dialog.getTarget();
            if (filterTarget != null)
            {
               Job job = new GetBlocksJob(filterTarget,
                                          dialog.getScopeType(),
                                          dialog.getScopeId(),
                                          dialog.getBlockFilter());
               IWorkbenchSiteProgressService siteService =
                  (IWorkbenchSiteProgressService) BlockView.this.getSite().
                  getAdapter(IWorkbenchSiteProgressService.class);
               siteService.schedule(job, 0, true);
            }
         }
      }
   }

   class GetBlocksJob extends Job
   {
      private final Target target;
      private final int scopeType;
      private final int scopeId;
      private final BlockFilter blockFilter;

      GetBlocksJob(Target target, int scopeType, int scopeId, BlockFilter blockFilter)
      {
         super("Filtering Blocks");
         setPriority(SHORT);
         this.target = target;
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.blockFilter = blockFilter;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            BlockInfo[] blocks;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            target.connect(monitor);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            blocks = target.getFilteredBlocks(monitor, scopeType, scopeId, blockFilter);
            if (monitor.isCanceled())
            {
               return Status.CANCEL_STATUS;
            }

            viewer.getControl().getDisplay().asyncExec(new UpdateViewerRunner(
                  target, scopeType, scopeId, blockFilter, blocks));

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
               "Error reported from target when retrieving blocks for target " + target, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when retrieving blocks for target " + target, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when retrieving blocks for target " + target, e);
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
      private final BlockFilter blockFilter;
      private final BlockInfo[] blocks;

      UpdateViewerRunner(Target target,
                         int scopeType,
                         int scopeId,
                         BlockFilter blockFilter,
                         BlockInfo[] blocks)
      {
         this.target = target;
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.blockFilter = blockFilter;
         this.blocks = blocks;
      }

      public void run()
      {
         if (tableBackgroundImageHandler != null)
         {
            tableBackgroundImageHandler.dispose();
            tableBackgroundImageHandler = null;
         }

         BlockView.this.target = target;
         BlockView.this.scopeType = scopeType;
         BlockView.this.scopeId = scopeId;
         BlockView.this.blockFilter = blockFilter;

         sorter.reset();
         viewer.setInput(blocks);
         setContentDescription("Target: " + target + ", Blocks: " + blocks.length);
      }
   }
}
