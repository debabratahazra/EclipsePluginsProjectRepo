/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.action.Action;
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
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.perf.ui.launch.ProfilerLaunchShortcut;
import com.ose.system.Gate;
import com.ose.system.PerformanceCounterInfo;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.util.BackgroundImageHandler;

public class ProfilerView extends ViewPart
{
   public static final int COLUMN_PROFILER_SESSION_TARGET = 0;
   public static final int COLUMN_PROFILER_SESSION_EXECUTION_UNIT = 1;
   public static final int COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER = 2;
   public static final int COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER_VALUE = 3;
   public static final int COLUMN_PROFILER_SESSION_TIMESTAMP = 4;
   public static final int COLUMN_PROFILER_SESSION_REPORTS = 5;
   public static final int COLUMN_PROFILER_SESSION_LOST_REPORTS = 6;

   private static final String REPORT_FILE_EXTENSION = ".pmd";

   private TableViewer viewer;
   private ProfilerSessionContentProvider contentProvider;
   private ProfilerSessionSorter sorter;

   private AddProfilerSessionAction addProfilerSessionAction;
   private Action removeProfilerSessionAction;
   private Action enableProfilingAction;
   private Action saveReportsAction;

   private ImageCache imageCache;
   private ImageDescriptor addProfilerSessionImage;
   private ImageDescriptor removeProfilerSessionImage;
   private ImageDescriptor enableProfilingImage;
   private ImageDescriptor disableProfilingImage;
   private ImageDescriptor saveReportsImage;

   public void createPartControl(Composite parent)
   {
      contentProvider = new ProfilerSessionContentProvider();
      sorter = new ProfilerSessionSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(contentProvider);
      viewer.setLabelProvider(new ProfilerSessionLabelProvider());
      viewer.setSorter(sorter);
      viewer.addSelectionChangedListener(new SelectionChangedHandler());
      viewer.setInput(new Object());
      contentProvider.setBackgroundImageHandler(
            new TableBackgroundImageHandler(viewer.getTable()));

      createActions();
      createContextMenu();
      contributeToActionBars();
      updateActions();
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
      column.setText("Target");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_TARGET));
      layout.setColumnData(column, new ColumnWeightData(6, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Execution Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_EXECUTION_UNIT));
      layout.setColumnData(column, new ColumnWeightData(3, 90));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Performance Counter");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER));
      layout.setColumnData(column, new ColumnWeightData(6, 160));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Trigger Value");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_PERFORMANCE_COUNTER_VALUE));
      layout.setColumnData(column, new ColumnWeightData(3, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(6, 130));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Reports");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_REPORTS));
      layout.setColumnData(column, new ColumnWeightData(3, 80));

      column = new TableColumn(table, SWT.RIGHT);
      column.setText("Lost Reports");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_LOST_REPORTS));
      layout.setColumnData(column, new ColumnWeightData(3, 80));

      return table;
   }

   private void createActions()
   {
      addProfilerSessionAction = new AddProfilerSessionAction();
      removeProfilerSessionAction = new RemoveProfilerSessionAction();
      enableProfilingAction = new EnableProfilingAction();
      saveReportsAction = new SaveReportsAction();
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
      manager.add(addProfilerSessionAction);
      manager.add(removeProfilerSessionAction);
      manager.add(new Separator());
      manager.add(enableProfilingAction);
      manager.add(saveReportsAction);
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
      if (!viewer.getControl().isDisposed())
      {
         viewer.getControl().getDisplay().asyncExec(runnable);
      }
   }

   private ProfilerSession getSelectedProfilerSession()
   {
      ISelection selection = viewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      return ((obj instanceof ProfilerSession) ? ((ProfilerSession) obj) : null);
   }

   private void updateActions()
   {
      ProfilerSession profilerSession;
      boolean hasProfilerSession;
      boolean hasOpenProfilerSession;

      profilerSession = getSelectedProfilerSession();
      hasProfilerSession = (profilerSession != null);
      hasOpenProfilerSession = (hasProfilerSession ? profilerSession.isOpen() : false);

      addProfilerSessionAction.setEnabled(true);
      removeProfilerSessionAction.setEnabled(hasProfilerSession);
      enableProfilingAction.setEnabled(hasOpenProfilerSession &&
         profilerSession.hasProfilingEnabledSupport());
      saveReportsAction.setEnabled(hasProfilerSession &&
         !profilerSession.isProfilingEnabled() &&
         profilerSession.dumpFileExists());

      if (hasProfilerSession)
      {
         enableProfilingAction.setChecked(profilerSession.isProfilingEnabled());
      }
   }

   private String getSaveFileName(String extension)
   {
      FileDialog dialog;
      boolean done = false;
      String fileName = null;

      if (extension == null)
      {
         throw new IllegalArgumentException();
      }

      dialog = new FileDialog(getShell(), SWT.SAVE | SWT.APPLICATION_MODAL);
      dialog.setFilterExtensions(new String[] {"*" + extension});

      while (!done)
      {
         fileName = dialog.open();

         if (fileName == null)
         {
            done = true;
         }
         else
         {
            boolean hasValidExtension = false;

            if (fileName.toLowerCase().endsWith(extension))
            {
               hasValidExtension = true;
               break;
            }

            if (!hasValidExtension)
            {
               fileName += extension;
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

   public void init(IViewSite site, IMemento memento) throws PartInitException
   {
      super.init(site, memento);

      imageCache = new ImageCache();
      addProfilerSessionImage = ImageCache.createImageDescriptor(
            "elcl16/", "target.gif");
      removeProfilerSessionImage = ImageCache.createImageDescriptor(
            "elcl16/", "target_remove.gif");
      enableProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_enable.gif");
      disableProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_disable.gif");
      saveReportsImage = ImageCache.createImageDescriptor(
            "elcl16/", "reports_save.gif");
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
   }

   public void dispose()
   {
      imageCache.dispose();
      super.dispose();
   }

   public void show(Target target)
   {
      if ((target != null) && !target.isKilled())
      {
         addProfilerSessionAction.run(target);
      }
   }

   static class ImageCache
   {
      private static final URL IMAGE_BASE_URL = Platform.getBundle(
            ProfilerPlugin.getUniqueIdentifier()).getEntry("/icons/");

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
            ProfilerPlugin.log(e);
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

   class TableBackgroundImageHandler extends BackgroundImageHandler
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
         Image icon = imageCache.getImage(addProfilerSessionImage);
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
         manager.add(addProfilerSessionAction);
         manager.add(removeProfilerSessionAction);
         manager.add(new Separator());
         manager.add(enableProfilingAction);
         manager.add(saveReportsAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         updateActions();
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

   class ProfilerSessionObserver implements Observer
   {
      public void update(Observable o, Object arg)
      {
         if (o instanceof ProfilerSession)
         {
            ProfilerSession profilerSession = (ProfilerSession) o;
            asyncExec(new UpdateRunner(profilerSession));
         }
      }
   }

   class AddProfilerSessionAction extends Action
   {
      AddProfilerSessionAction()
      {
         super("Add Target...");
         setToolTipText("Add Target");
         setImageDescriptor(addProfilerSessionImage);
      }

      public void run()
      {
         Job job = new AddProfilerSessionJob(null);
         job.schedule();
      }

      public void run(Target target)
      {
         Job job = new AddProfilerSessionJob(target);
         job.schedule();
      }
   }

   class RemoveProfilerSessionAction extends Action
   {
      RemoveProfilerSessionAction()
      {
         super("Remove Target");
         setToolTipText("Remove Target");
         setImageDescriptor(removeProfilerSessionImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            Job job = new RemoveProfilerSessionJob(profilerSession);
            job.schedule();
         }
      }
   }

   class EnableProfilingAction extends Action
   {
      EnableProfilingAction()
      {
         super("Enable/Disable Profiling");
         setChecked(false);
      }

      public void setChecked(boolean checked)
      {
         super.setChecked(checked);
         if (checked)
         {
            setToolTipText("Disable Profiling");
            setImageDescriptor(disableProfilingImage);
         }
         else
         {
            setToolTipText("Enable Profiling");
            setImageDescriptor(enableProfilingImage);
         }
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            Job job = new EnableProfilingJob(profilerSession, isChecked());
            job.schedule();
         }
      }
   }

   class SaveReportsAction extends Action
   {
      SaveReportsAction()
      {
         super("Save Profiling Reports...");
         setToolTipText("Save Profiling Reports");
         setImageDescriptor(saveReportsImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            String fileName = getSaveFileName(REPORT_FILE_EXTENSION);
            if (fileName != null)
            {
               Job job = new SaveReportsJob(profilerSession, fileName);
               job.schedule();
            }
         }
      }
   }

   static abstract class AbstractJob extends Job
   {
      final ProfilerSession profilerSession;

      AbstractJob(String name, ProfilerSession profilerSession)
      {
         super(name);
         setPriority(SHORT);
         this.profilerSession = profilerSession;
      }
   }

   class AddProfilerSessionJob extends AbstractJob
   {
      private final Target target;

      AddProfilerSessionJob(Target target)
      {
         super("Finding Targets", null);
         setUser(true);
         this.target = target;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            AddProfilerSessionDialogRunner dialogRunner;

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
                     if (!t.isKilled() && t.hasPerformanceCounterSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            // Show add profiler session dialog.
            dialogRunner = new AddProfilerSessionDialogRunner(livingTargets,
                                                              target);
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

   class CreateProfilerSessionJob extends AbstractJob
   {
      private final Target target;
      private final short executionUnit;
      private final PerformanceCounterInfo performanceCounter;
      private final long performanceCounterValue;
      private final int maxReports;

      CreateProfilerSessionJob(Target target,
                               short executionUnit,
                               PerformanceCounterInfo performanceCounter,
                               long performanceCounterValue,
                               int maxReports)
      {
         super("Creating profiler session", null);
         this.target = target;
         this.executionUnit = executionUnit;
         this.performanceCounter = performanceCounter;
         this.performanceCounterValue = performanceCounterValue;
         this.maxReports = maxReports;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            ProfilerSession profilerSession;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession = new ProfilerSession(
                  target,
                  executionUnit,
                  performanceCounter,
                  performanceCounterValue,
                  maxReports);
            profilerSession.addObserver(new ProfilerSessionObserver());
            contentProvider.addProfilerSession(profilerSession);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error reported from target when creating the profiler session", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when creating the profiler session", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when creating the profiler session", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class RemoveProfilerSessionJob extends AbstractJob
   {
      RemoveProfilerSessionJob(ProfilerSession profilerSession)
      {
         super("Removing profiler session", profilerSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.close(true);
            contentProvider.removeProfilerSession(profilerSession);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when removing the profiler session", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class EnableProfilingJob extends AbstractJob
   {
      private final boolean enabled;

      EnableProfilingJob(ProfilerSession profilerSession, boolean enabled)
      {
         super((enabled ? "Enabling" : "Disabling") + " profiling", profilerSession);
         this.enabled = enabled;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.setProfilingEnabled(monitor, enabled);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error reported from target when enabling/disabling the profiling", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when enabling/disabling the profiling", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when enabling/disabling the profiling", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class SaveReportsJob extends AbstractJob
   {
      private final String file;

      SaveReportsJob(ProfilerSession profilerSession, String file)
      {
         super("Saving profiling reports", profilerSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.saveReports(monitor, file);
            asyncExec(new LaunchEditorRunner(file));
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when saving the profiling reports", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class AddProfilerSessionDialogRunner implements Runnable
   {
      private final List targets;
      private final Target target;

      AddProfilerSessionDialogRunner(List targets, Target target)
      {
         this.targets = targets;
         this.target = target;
      }

      public void run()
      {
         AddProfilerSessionDialog dialog;
         int result;

         dialog = new AddProfilerSessionDialog(getShell(), targets, target);
         result = dialog.open();
         if (result == Window.OK)
         {
            Target selectedTarget = dialog.getTarget();
            if (selectedTarget != null)
            {
               Job job = new CreateProfilerSessionJob(
                     selectedTarget,
                     dialog.getExecutionUnit(),
                     dialog.getPerformanceCounter(),
                     dialog.getPerformanceCounterValue(),
                     dialog.getMaxReports());
               scheduleJob(job);
            }
         }
      }
   }

   class UpdateRunner implements Runnable
   {
      private final ProfilerSession profilerSession;

      UpdateRunner()
      {
         this(null);
      }

      UpdateRunner(ProfilerSession profilerSession)
      {
         this.profilerSession = profilerSession;
      }

      public void run()
      {
         updateActions();
         if ((profilerSession != null) && !viewer.getTable().isDisposed())
         {
            viewer.refresh(profilerSession);
         }
      }
   }

   class LaunchEditorRunner implements Runnable
   {
      private final File file;

      LaunchEditorRunner(String path)
      {
         file = new File(path);
      }

      public void run()
      {
         MessageBox mb = new MessageBox(getShell(),
               SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.APPLICATION_MODAL);
         mb.setText("Open File");
         mb.setMessage("Do you want to open the saved profiling reports "
               + "file in the Source Profiling editor?");

         if (mb.open() == SWT.YES)
         {
            ISelection selection = new StructuredSelection(file);
            ProfilerLaunchShortcut shortcut = new ProfilerLaunchShortcut();
            shortcut.launch(selection, ILaunchManager.PROFILE_MODE);
         }
      }
   }
}
