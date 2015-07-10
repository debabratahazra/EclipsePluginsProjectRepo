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

package com.ose.system.ui.views.loadmodule;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.httpd.Httpd;
import com.ose.httpd.LocalInetAddress;
import com.ose.httpd.ProgressEvent;
import com.ose.httpd.ProgressListener;
import com.ose.system.Gate;
import com.ose.system.LoadModuleInfo;
import com.ose.system.Parameter;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.dialogs.TargetSelectionDialog;
import com.ose.system.ui.util.BackgroundImageHandler;

public class LoadModuleView extends ViewPart
{
   public static final int COLUMN_INSTALL_HANDLE = 0;

   public static final int COLUMN_ENTRYPOINT = 1;

   public static final int COLUMN_OPTIONS = 2;

   public static final int COLUMN_TEXT_BASE = 3;

   public static final int COLUMN_TEXT_SIZE = 4;

   public static final int COLUMN_DATA_BASE = 5;

   public static final int COLUMN_DATA_SIZE = 6;

   public static final int COLUMN_NUM_SECTIONS = 7;

   public static final int COLUMN_NUM_INSTANCES = 8;

   public static final int COLUMN_FILE_FORMAT = 9;

   public static final int COLUMN_FILE_NAME = 10;

   private volatile Target target;

   private TableViewer viewer;

   private LoadModuleSorter sorter;

   private BackgroundImageHandler tableBackgroundImageHandler;

   private Action selectTargetAction;

   private Action updateLoadModulesAction;

   private InstallLoadModuleAction installLoadModuleAction;

   private Action uninstallLoadModuleAction;

   private Action createProgramAction;

   public void createPartControl(Composite parent)
   {
      sorter = new LoadModuleSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(new LoadModuleContentProvider());
      viewer.setLabelProvider(new LoadModuleLabelProvider());
      viewer.setSorter(sorter);
      tableBackgroundImageHandler = new TableBackgroundImageHandler(viewer.getTable());

      initDropSupport();
      createActions();
      createContextMenu();
      hookSelectionChangedHandler();
      contributeToActionBars();
      setContentDescription("Target: None, Load Modules: 0");
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

      column = new TableColumn(table, SWT.NONE);
      column.setText("Installation Handle");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_INSTALL_HANDLE));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Entrypoint");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_ENTRYPOINT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.NONE);
      column.setText("Options");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_OPTIONS));
      layout.setColumnData(column, new ColumnWeightData(12, 120));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Text Base");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_TEXT_BASE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Text Size");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_TEXT_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Data Base");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_DATA_BASE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Data Size");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_DATA_SIZE));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Sections");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_NUM_SECTIONS));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Instances");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_NUM_INSTANCES));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.NONE);
      column.setText("File Format");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_FILE_FORMAT));
      layout.setColumnData(column, new ColumnWeightData(8, 80));

      column = new TableColumn(table, SWT.NONE);
      column.setText("Filename");
      column.setMoveable(true);
      column.addSelectionListener(new ColumnSelectionHandler(COLUMN_FILE_NAME));
      layout.setColumnData(column, new ColumnWeightData(15, 150));

      return table;
   }

   private void initDropSupport()
   {
      int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_DEFAULT;
      Transfer[] transfers = new Transfer[] {FileTransfer.getInstance()};
      LoadModuleDropAdapter adapter = new LoadModuleDropAdapter(this);
      viewer.addDropSupport(ops, transfers, adapter);
   }

   private void createActions()
   {
      selectTargetAction = new SelectTargetAction();
      updateLoadModulesAction = new UpdateLoadModulesAction();
      installLoadModuleAction = new InstallLoadModuleAction();
      uninstallLoadModuleAction = new UninstallLoadModuleAction();
      createProgramAction = new CreateProgramAction();

      // Initially disabled actions.
      uninstallLoadModuleAction.setEnabled(false);
      createProgramAction.setEnabled(false);
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

   private void hookSelectionChangedHandler()
   {
      viewer.addSelectionChangedListener(new SelectionChangedHandler());
   }

   private void contributeToActionBars()
   {
      IActionBars bars = getViewSite().getActionBars();
      fillLocalPullDown(bars.getMenuManager());
      fillLocalToolBar(bars.getToolBarManager());
   }

   private void fillLocalPullDown(IMenuManager manager)
   {
      // TODO: Anything to add?
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(selectTargetAction);
      manager.add(updateLoadModulesAction);
      manager.add(installLoadModuleAction);
      manager.add(uninstallLoadModuleAction);
      manager.add(createProgramAction);
   }

   private Target getTarget()
   {
      return target;
   }

   private void setTarget(Target target)
   {
      this.target = target;
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

   public TableViewer getViewer()
   {
      return viewer;
   }

   public Shell getShell()
   {
      return viewer.getControl().getShell();
   }

   public LoadModuleInfo getSelectedLoadModule()
   {
      ISelection selection = viewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      return ((obj instanceof LoadModuleInfo) ? ((LoadModuleInfo) obj) : null);
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
         updateLoadModulesAction.run();
      }
   }

   public void loadModuleDropped(String filename)
   {
      getShell().forceActive();
      installLoadModuleAction.run(filename);
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
         manager.add(updateLoadModulesAction);
         manager.add(installLoadModuleAction);
         if (getSelectedLoadModule() != null)
         {
            manager.add(uninstallLoadModuleAction);
            manager.add(createProgramAction);
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

         uninstallLoadModuleAction.setEnabled(obj != null);
         createProgramAction.setEnabled(obj != null);
      }
   }

   class ColumnSelectionHandler extends SelectionAdapter
   {
      private int column;

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

   static class HttpdProgressHandler implements ProgressListener
   {
      private final IProgressMonitor monitor;
      private final String taskName;
      private final int taskMax;
      private final String file;
      private int progress;

      public HttpdProgressHandler(IProgressMonitor monitor,
                                  String taskName,
                                  int taskMax,
                                  String file)
      {
         this.monitor = monitor;
         this.taskName = taskName;
         this.taskMax = taskMax;
         this.file = file;
         this.progress = 0;
      }

      public void progressChanged(ProgressEvent event)
      {
         if (event.getFile().endsWith(file))
         {
            int work = event.getProgress() - progress;
            progress += work;
            monitor.worked(work);
            if (monitor.isCanceled())
            {
               monitor.subTask(taskName + ": Loading will be aborted when safe");
            }
         }
      }

      public void finish()
      {
         if (progress < taskMax)
         {
            monitor.worked(taskMax - progress);
         }
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

   class UpdateLoadModulesAction extends Action
   {
      UpdateLoadModulesAction()
      {
         super("Update");
         setToolTipText("Update");
         setImageDescriptor(SharedImages.DESC_TOOL_UPDATE);
      }

      public void run()
      {
         Target lmTarget = getTarget();
         if (lmTarget != null)
         {
            Job job = new UpdateLoadModulesJob(lmTarget);
            scheduleJob(job);
         }
      }
   }

   class InstallLoadModuleAction extends Action
   {
      InstallLoadModuleAction()
      {
         super("Install Load Module...");
         setToolTipText("Install Load Module");
         setImageDescriptor(SharedImages.DESC_TOOL_LOAD_MODULE_INSTALL);
      }

      public void run()
      {
         run(null);
      }

      public void run(String filename)
      {
         Target lmTarget = getTarget();
         if (lmTarget != null)
         {
            InstallLoadModuleDialog dialog =
               new InstallLoadModuleDialog(getShell(), filename);
            int result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new InstallLoadModuleJob(lmTarget,
                                                  dialog.getLoadModule(),
                                                  dialog.getInstallHandle(),
                                                  dialog.getHttpServerPort(),
                                                  dialog.getHttpVmHuntPath(),
                                                  dialog.isPersistent(),
                                                  dialog.isAbsolute());
               scheduleJob(job);
            }
         }
      }
   }

   class UninstallLoadModuleAction extends Action
   {
      UninstallLoadModuleAction()
      {
         super("Uninstall Load Module");
         setToolTipText("Uninstall Load Module");
         setImageDescriptor(SharedImages.DESC_TOOL_LOAD_MODULE_UNINSTALL);
      }

      public void run()
      {
         Target lmTarget = getTarget();
         if (lmTarget != null)
         {
            LoadModuleInfo lm = getSelectedLoadModule();
            if (lm != null)
            {
               Job job = new UninstallLoadModuleJob(lmTarget, lm);
               scheduleJob(job);
            }
         }
      }
   }

   class CreateProgramAction extends Action
   {
      CreateProgramAction()
      {
         super("Create Program...");
         setToolTipText("Create Program");
         setImageDescriptor(SharedImages.DESC_OBJ_BLOCK);
      }

      public void run()
      {
         Target lmTarget = getTarget();
         if (lmTarget != null)
         {
            LoadModuleInfo lm = getSelectedLoadModule();
            if (lm != null)
            {
               CreateProgramDialog dialog =
                  new CreateProgramDialog(getShell(), lmTarget);
               int result = dialog.open();
               if (result == Window.OK)
               {
                  Job job = new CreateProgramJob(lmTarget,
                                                 lm,
                                                 dialog.getArguments(),
                                                 dialog.getEnvVars());
                  scheduleJob(job);
               }
            }
         }
      }
   }

   static abstract class AbstractJob extends Job
   {
      Target lmTarget;

      AbstractJob(String name, Target lmTarget)
      {
         super(name);
         setPriority(SHORT);
         this.lmTarget = lmTarget;
      }
   }

   class SelectTargetJob extends AbstractJob
   {
      SelectTargetJob(Target lmTarget)
      {
         super("Finding Targets", lmTarget);
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
                     if (!t.isKilled() && t.hasLoadModuleSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            // Show target selection dialog.
            dialogRunner = new TargetSelectionDialogRunner(livingTargets, lmTarget);
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

   class UpdateLoadModulesJob extends AbstractJob
   {
      UpdateLoadModulesJob(Target lmTarget)
      {
         super("Retrieving Load Modules", lmTarget);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            LoadModuleInfo[] loadModules;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            // Update load modules.
            loadModules = lmTarget.getLoadModuleInfo(monitor);
            asyncExec(new UpdateViewerRunner(loadModules));

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (UnsupportedOperationException e)
         {
            asyncExec(new UpdateViewerRunner(new LoadModuleInfo[0]));
            return SystemBrowserPlugin.createErrorStatus("Target " +
               lmTarget.toString() + " does not support load modules.", e);
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(e.getStatus(),
               "Error reported from target when retrieving load modules", e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when retrieving load modules", e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when retrieving load modules", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class InstallLoadModuleJob extends AbstractJob
   {
      private static final int INSTALL_LOAD_MODULE_TASK_MAX = 100;

      private File loadModule;
      private String installHandle;
      private int httpServerPort;
      private String httpVmHuntPath;
      private boolean persistent;
      private boolean absolute;

      InstallLoadModuleJob(Target lmTarget,
                           File loadModule,
                           String installHandle,
                           int httpServerPort,
                           String httpVmHuntPath,
                           boolean persistent,
                           boolean absolute)
      {
         super("Installing Load Module", lmTarget);
         this.loadModule = loadModule;
         this.installHandle = installHandle;
         this.httpServerPort = httpServerPort;
         this.httpVmHuntPath = httpVmHuntPath;
         this.persistent = persistent;
         this.absolute = absolute;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         Httpd httpd = null;
         String fileName = "";

         try
         {
            HttpdProgressHandler progress;
            String localAddress;
            LoadModuleInfo[] loadModules;

            monitor.beginTask(getName(), INSTALL_LOAD_MODULE_TASK_MAX + 10);

            if (Boolean.getBoolean("com.ose.httpd.port.gatewayBased"))
            {
               // Special fix for Ericsson Research ERA, see TOOLSCR-163.
               httpServerPort = lmTarget.getGate().getPort() + 1;
            }

            // Start HTTP server.
            httpd = new Httpd(loadModule.getParentFile(), httpServerPort);
            progress = new HttpdProgressHandler(monitor,
                                                getName(),
                                                INSTALL_LOAD_MODULE_TASK_MAX,
                                                loadModule.getName());
            try
            {
               httpd.addProgressListener(progress);
            }
            catch (TooManyListenersException ignore) {}
            httpd.start();

            // Create load module file path.
            try
            {
               InetAddress localInetAddress = LocalInetAddress.getLocalHost(
                     lmTarget.getGate().getAddress());
               if (localInetAddress.isLoopbackAddress())
               {
                  SystemBrowserPlugin.log(new Status(
                        IStatus.WARNING,
                        SystemBrowserPlugin.getUniqueIdentifier(),
                        IStatus.OK,
                        "Using local host's loopback address when installing " +
                        "load module " + loadModule.getName(),
                        null));
               }
               localAddress = LocalInetAddress.getHostAddress(localInetAddress);
            }
            catch (UnknownHostException e)
            {
               return SystemBrowserPlugin.createErrorStatus(
                     "Failed installing load module: Unknown local host IP address", e);
            }
            fileName = httpVmHuntPath + "/http/" + localAddress + ":" +
                       httpd.getPort() + "/" + loadModule.getName();

            // Install load module.
            lmTarget.installLoadModule(monitor,
                                       fileName,
                                       installHandle,
                                       persistent,
                                       absolute,
                                       null);

            // Remove HTTP server progress listener.
            httpd.removeProgressListener(progress);

            // Make sure load progress listener is completed.
            progress.finish();

            // Update load modules.
            loadModules = lmTarget.getLoadModuleInfo(monitor);
            asyncExec(new UpdateViewerRunner(loadModules));
            monitor.worked(10);

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(e.getStatus(),
               "Error reported from target when installing the load module " + fileName, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when installing the load module " + fileName, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when installing the load module " + fileName, e);
         }
         finally
         {
            // Stop HTTP server.
            if (httpd != null)
            {
               httpd.shutdown();
            }
            monitor.done();
         }
      }
   }

   class UninstallLoadModuleJob extends AbstractJob
   {
      private LoadModuleInfo lm;

      UninstallLoadModuleJob(Target lmTarget, LoadModuleInfo lm)
      {
         super("Uninstalling Load Module", lmTarget);
         this.lm = lm;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            LoadModuleInfo[] loadModules;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            // Uninstall load module.
            lmTarget.uninstallLoadModule(monitor, lm.getInstallHandle());

            // Update load modules.
            loadModules = lmTarget.getLoadModuleInfo(monitor);
            asyncExec(new UpdateViewerRunner(loadModules));

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(e.getStatus(),
               "Error reported from target when uninstalling the load module", e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when uninstalling the load module", e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when uninstalling the load module", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class CreateProgramJob extends AbstractJob
   {
      private LoadModuleInfo lm;
      private String arguments;
      private Map envVars;

      CreateProgramJob(Target lmTarget, LoadModuleInfo lm,
                       String arguments, Map envVars)
      {
         super("Creating Program", lmTarget);
         this.lm = lm;
         this.arguments = arguments;
         this.envVars = envVars;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            LoadModuleInfo[] loadModules;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            // Add command line arguments as environment variable.
            if (arguments.length() > 0)
            {
               envVars.put(Parameter.ARGV, lm.getFileName() + " " + arguments);
            }

            // Create program.
            lmTarget.createProgram(monitor, lm.getInstallHandle(), envVars);

            // Update load modules.
            loadModules = lmTarget.getLoadModuleInfo(monitor);
            asyncExec(new UpdateViewerRunner(loadModules));

            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(e.getStatus(),
               "Error reported from target when creating the program", e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when creating the program", e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when creating the program", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class TargetSelectionDialogRunner implements Runnable
   {
      private List targets;
      private Target lmTarget;

      TargetSelectionDialogRunner(List targets, Target lmTarget)
      {
         this.targets = targets;
         this.lmTarget = lmTarget;
      }

      public void run()
      {
         TargetSelectionDialog dialog;
         int result;

         dialog = new TargetSelectionDialog(getShell(), targets, lmTarget);
         result = dialog.open();
         if (result == Window.OK)
         {
            Target selectedTarget = dialog.getTarget();
            if (selectedTarget != null)
            {
               setTarget(selectedTarget);
               Job job = new UpdateLoadModulesJob(selectedTarget);
               scheduleJob(job);
            }
         }
      }
   }

   class UpdateViewerRunner implements Runnable
   {
      private LoadModuleInfo[] loadModules;

      UpdateViewerRunner(LoadModuleInfo[] loadModules)
      {
         this.loadModules = loadModules;
      }

      public void run()
      {
         if (tableBackgroundImageHandler != null)
         {
            tableBackgroundImageHandler.dispose();
            tableBackgroundImageHandler = null;
         }
         Target lmTarget = getTarget();
         sorter.reset();
         viewer.setInput(loadModules);
         setContentDescription("Target: " + lmTarget +
                               (lmTarget.isKilled() ? " [disconnected]" : "") +
                               ", Load Modules: " + loadModules.length);
      }
   }
}
