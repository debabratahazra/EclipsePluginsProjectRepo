/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.pmd.ui.DumpPlugin;
import com.ose.system.DumpInfo;
import com.ose.system.Gate;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.dialogs.TargetSelectionDialog;
import com.ose.system.ui.util.BackgroundImageHandler;

public class DumpView extends ViewPart
{
   public static final int COLUMN_DUMP_ID = 0;
   public static final int COLUMN_DUMP_SIZE = 1;
   public static final int COLUMN_DUMP_TIMESTAMP = 2;

   private volatile Target target;

   private TableViewer viewer;
   private DumpSorter sorter;
   private BackgroundImageHandler tableBackgroundImageHandler;

   private Action selectTargetAction;
   private Action updateDumpsAction;
   private Action saveDumpAction;

   public void createPartControl(Composite parent)
   {
      sorter = new DumpSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(new DumpContentProvider());
      viewer.setLabelProvider(new DumpLabelProvider());
      viewer.setSorter(sorter);
      viewer.addSelectionChangedListener(new SelectionChangedHandler());
      tableBackgroundImageHandler = new TableBackgroundImageHandler(viewer.getTable());

      createActions();
      createContextMenu();
      contributeToActionBars();
      setContentDescription("Target: None, Dumps: 0");
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
            SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Dump ID");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_DUMP_ID));
      layout.setColumnData(column, new ColumnWeightData(3, 90));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Size");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_DUMP_SIZE));
      layout.setColumnData(column, new ColumnWeightData(3, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(
            COLUMN_DUMP_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      return table;
   }

   private void createActions()
   {
      selectTargetAction = new SelectTargetAction();
      updateDumpsAction = new UpdateDumpsAction();
      saveDumpAction = new SaveDumpAction();
      saveDumpAction.setEnabled(false);
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
      IToolBarManager manager = getViewSite().getActionBars().getToolBarManager();
      manager.add(selectTargetAction);
      manager.add(updateDumpsAction);
      manager.add(saveDumpAction);
   }

   private Target getTarget()
   {
      return target;
   }

   private void setTarget(Target target)
   {
      this.target = target;
   }

   private Shell getShell()
   {
      return viewer.getControl().getShell();
   }

   private void scheduleJob(Job job)
   {
      IWorkbenchSiteProgressService siteService = (IWorkbenchSiteProgressService)
         getSite().getAdapter(IWorkbenchSiteProgressService.class);
      siteService.schedule(job, 0, true);
   }

   private void asyncExec(Runnable runnable)
   {
      viewer.getControl().getDisplay().asyncExec(runnable);
   }

   public DumpInfo getSelectedDump()
   {
      ISelection selection = viewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      return ((obj instanceof DumpInfo) ? ((DumpInfo) obj) : null);
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
   }

   public void show(Target target)
   {
      if ((target != null) && !target.isKilled())
      {
         setTarget(target);
         updateDumpsAction.run();
      }
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
         Image icon = SharedImages.get(SharedImages.IMG_OBJ_TARGET);
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
         manager.add(selectTargetAction);
         manager.add(updateDumpsAction);
         if (getSelectedDump() != null)
         {
            manager.add(saveDumpAction);
         }
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         ISelection selection;
         Object obj;

         selection = event.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();
         saveDumpAction.setEnabled(obj != null);
      }
   }

   class ColumnSelectionHandler extends SelectionAdapter
   {
      private final int column;

      ColumnSelectionHandler(int column)
      {
         this.column = column;
      }

      public void widgetSelected(SelectionEvent event)
      {
         sorter.sortByColumn(column);
         viewer.refresh();
      }
   }

   class SelectTargetAction extends Action
   {
      SelectTargetAction()
      {
         super("Select Target...");
         setToolTipText("Select Target");
         setImageDescriptor(SharedImages.DESC_OBJ_TARGET);
      }

      public void run()
      {
         Job job = new SelectTargetJob(getTarget());
         job.schedule();
      }
   }

   class UpdateDumpsAction extends Action
   {
      UpdateDumpsAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
      }

      public void run()
      {
         Target dumpTarget = getTarget();
         if (dumpTarget != null)
         {
            Job job = new UpdateDumpsJob(dumpTarget);
            scheduleJob(job);
         }
      }
   }

   class SaveDumpAction extends Action
   {
      SaveDumpAction()
      {
         super("Save Dump...");
         setToolTipText("Save Dump");
         setImageDescriptor(SharedImages.DESC_TOOL_SAVE_AS);
      }

      public void run()
      {
         Target dumpTarget = getTarget();
         if (dumpTarget != null)
         {
            DumpInfo dump = getSelectedDump();
            if (dump != null)
            {
               String fileName = getFileName();
               if (fileName != null)
               {
                  Job job = new SaveDumpJob(dumpTarget, dump, fileName);
                  scheduleJob(job);
               }
            }
         }
      }

      private String getFileName()
      {
         FileDialog dialog;
         boolean done = false;
         String fileName = null;

         dialog = new FileDialog(getShell(), SWT.SAVE | SWT.APPLICATION_MODAL);
         dialog.setFilterExtensions(new String[] {"*.pmd"});

         while (!done)
         {
            fileName = dialog.open();

            if (fileName == null)
            {
               done = true;
            }
            else
            {
               if (!fileName.endsWith(".pmd"))
               {
                  fileName += ".pmd";
               }

               if (new File(fileName).exists())
               {
                  MessageBox mb = new MessageBox(getShell(),
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
   }

   static abstract class AbstractJob extends Job
   {
      final Target dumpTarget;

      AbstractJob(String name, Target dumpTarget)
      {
         super(name);
         setPriority(SHORT);
         this.dumpTarget = dumpTarget;
      }
   }

   class SelectTargetJob extends AbstractJob
   {
      SelectTargetJob(Target dumpTarget)
      {
         super("Finding Targets", dumpTarget);
         setUser(true);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            TargetSelectionDialogRunner dialogRunner;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            // Retrieve living targets.
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
                     if (!t.isKilled() && t.hasDumpSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            // Show target selection dialog.
            dialogRunner = new TargetSelectionDialogRunner(livingTargets,
                                                           dumpTarget);
            asyncExec(dialogRunner);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class UpdateDumpsJob extends AbstractJob
   {
      UpdateDumpsJob(Target dumpTarget)
      {
         super("Retrieving Dumps", dumpTarget);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            DumpInfo[] dumps;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            dumps = dumpTarget.getDumps(monitor, 0, 0xFFFFFFFF);
            asyncExec(new UpdateViewerRunner(dumps));

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (UnsupportedOperationException e)
         {
            asyncExec(new UpdateViewerRunner(new DumpInfo[0]));
            return DumpPlugin.createErrorStatus("Target " +
               dumpTarget.toString() + " does not support dumps.", e);
         }
         catch (ServiceException e)
         {
            return DumpPlugin.createErrorStatus(
               "Error reported from target when retrieving the dumps", e);
         }
         catch (IOException e)
         {
            return DumpPlugin.createErrorStatus(
               "Error communicating with target when retrieving the dumps", e);
         }
         catch (Exception e)
         {
            return DumpPlugin.createErrorStatus(
               "Error when retrieving the dumps", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class SaveDumpJob extends AbstractJob
   {
      private final DumpInfo dump;
      private final String dumpFile;

      SaveDumpJob(Target dumpTarget, DumpInfo dump, String dumpFile)
      {
         super("Saving Dump", dumpTarget);
         this.dump = dump;
         this.dumpFile = dumpFile;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         BufferedOutputStream out = null;

         try
         {
            monitor.beginTask(getName(), (int) dump.getSize());
            out = new BufferedOutputStream(new FileOutputStream(dumpFile));
            dumpTarget.collectDump(monitor, dump.getId(), out);
            refreshWorkspaceFile(monitor, new File(dumpFile));
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return DumpPlugin.createErrorStatus(e.getStatus(),
               "Error reported from target when retrieving the dump", e);
         }
         catch (Exception e)
         {
            return DumpPlugin.createErrorStatus("Error when saving the dump", e);
         }
         finally
         {
            if (out != null)
            {
               try
               {
                  out.close();
               } catch (IOException ignore) {}
            }
            monitor.done();
         }
      }

      private void refreshWorkspaceFile(IProgressMonitor monitor, File file)
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
   }

   class TargetSelectionDialogRunner implements Runnable
   {
      private final List targets;
      private final Target dumpTarget;

      TargetSelectionDialogRunner(List targets, Target dumpTarget)
      {
         this.targets = targets;
         this.dumpTarget = dumpTarget;
      }

      public void run()
      {
         TargetSelectionDialog dialog;
         int result;

         dialog = new TargetSelectionDialog(getShell(), targets, dumpTarget);
         result = dialog.open();
         if (result == Window.OK)
         {
            Target selectedTarget = dialog.getTarget();
            if (selectedTarget != null)
            {
               setTarget(selectedTarget);
               Job job = new UpdateDumpsJob(selectedTarget);
               scheduleJob(job);
            }
         }
      }
   }

   class UpdateViewerRunner implements Runnable
   {
      private final DumpInfo[] dumps;

      UpdateViewerRunner(DumpInfo[] dumps)
      {
         this.dumps = dumps;
      }

      public void run()
      {
         if (tableBackgroundImageHandler != null)
         {
            tableBackgroundImageHandler.dispose();
            tableBackgroundImageHandler = null;
         }
         Target dumpTarget = getTarget();
         sorter.reset();
         viewer.setInput(dumps);
         setContentDescription("Target: " + dumpTarget +
               (dumpTarget.isKilled() ? " [disconnected]" : "") +
               ", Dumps: " + dumps.length);
      }
   }
}
