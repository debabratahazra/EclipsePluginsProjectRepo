/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.prof.ui.view;

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
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.xml.sax.SAXException;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.prof.ui.editors.profiler.ProfilerEditor;
import com.ose.prof.ui.editors.profiler.ProfilerEditorInput;
import com.ose.system.Gate;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.FileDialogAdapter;

public class ProfilerView extends ViewPart
{
   public static final int COLUMN_PROFILER_SESSION_TARGET = 0;
   public static final int COLUMN_PROFILER_SESSION_TYPE = 1;
   public static final int COLUMN_PROFILER_SESSION_EXECUTION_UNIT = 2;
   public static final int COLUMN_PROFILER_SESSION_TIMESTAMP = 3;

   private static final String PROCESS_FILE_EXTENSION = ".process";
   private static final String[] REPORT_FILE_EXTENSIONS = {".pmd", ".report"};

   private TableViewer viewer;
   private ProfilerSessionContentProvider contentProvider;
   private ProfilerSessionSorter sorter;
   private IPartListener editorPartHandler;

   private AddProfilerSessionAction addProfilerSessionAction;
   private Action removeProfilerSessionAction;
   private Action openProfilerEditorAction;
   private Action setProfiledProcessesAction;
   private Action getProfiledProcessesAction;
   private Action clearProfiledProcessesAction;
   private Action enableProfilingAction;
   private Action startProfilingAction;
   private Action getFirstReportsAction;
   private Action getNextReportsAction;
   private Action saveReportsAction;

   private ImageCache imageCache;
   private ImageDescriptor addProfilerSessionImage;
   private ImageDescriptor removeProfilerSessionImage;
   private ImageDescriptor setProfiledProcessesImage;
   private ImageDescriptor getProfiledProcessesImage;
   private ImageDescriptor clearProfiledProcessesImage;
   private ImageDescriptor enableProfilingImage;
   private ImageDescriptor disableProfilingImage;
   private ImageDescriptor startProfilingImage;
   private ImageDescriptor stopProfilingImage;
   private ImageDescriptor getFirstReportsImage;
   private ImageDescriptor getNextReportsImage;
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
      viewer.addDoubleClickListener(new DoubleClickHandler());
      viewer.setInput(new Object());
      contentProvider.setBackgroundImageHandler(
            new TableBackgroundImageHandler(viewer.getTable()));
      editorPartHandler = new EditorPartHandler();
      getSite().getPage().addPartListener(editorPartHandler);

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
      layout.setColumnData(column, new ColumnWeightData(8, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Profiling Type");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_TYPE));
      layout.setColumnData(column, new ColumnWeightData(6, 150));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Execution Unit");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_EXECUTION_UNIT));
      layout.setColumnData(column, new ColumnWeightData(3, 80));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_PROFILER_SESSION_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(10, 150));

      return table;
   }

   private void createActions()
   {
      addProfilerSessionAction = new AddProfilerSessionAction();
      removeProfilerSessionAction = new RemoveProfilerSessionAction();
      openProfilerEditorAction = new OpenProfilerEditorAction();
      setProfiledProcessesAction = new SetProfiledProcessesAction();
      getProfiledProcessesAction = new GetProfiledProcessesAction();
      clearProfiledProcessesAction = new ClearProfiledProcessesAction();
      enableProfilingAction = new EnableProfilingAction();
      startProfilingAction = new StartProfilingAction();
      getFirstReportsAction = new GetFirstReportsAction();
      getNextReportsAction = new GetNextReportsAction();
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
      manager.add(setProfiledProcessesAction);
      manager.add(getProfiledProcessesAction);
      manager.add(clearProfiledProcessesAction);
      manager.add(new Separator());
      manager.add(enableProfilingAction);
      manager.add(startProfilingAction);
      manager.add(getFirstReportsAction);
      manager.add(getNextReportsAction);
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
      boolean hasProfiledProcessesSupport = false;

      profilerSession = getSelectedProfilerSession();
      hasProfilerSession = (profilerSession != null);
      hasOpenProfilerSession = (hasProfilerSession ? profilerSession.isOpen() : false);
      if (profilerSession instanceof CPUProcessProfilerSession)
      {
         hasProfiledProcessesSupport =
            ((CPUProcessProfilerSession) profilerSession).hasProfiledProcessesSupport();
      }

      addProfilerSessionAction.setEnabled(true);
      removeProfilerSessionAction.setEnabled(hasProfilerSession);
      openProfilerEditorAction.setEnabled(hasOpenProfilerSession);
      setProfiledProcessesAction.setEnabled(hasOpenProfilerSession &&
         hasProfiledProcessesSupport);
      getProfiledProcessesAction.setEnabled(hasOpenProfilerSession &&
         hasProfiledProcessesSupport);
      clearProfiledProcessesAction.setEnabled(hasOpenProfilerSession &&
         hasProfiledProcessesSupport);
      enableProfilingAction.setEnabled(hasOpenProfilerSession &&
         profilerSession.hasProfilingEnabledSupport());
      startProfilingAction.setEnabled(hasOpenProfilerSession &&
         profilerSession.hasStartStopProfilingSupport());
      getFirstReportsAction.setEnabled(hasOpenProfilerSession &&
         profilerSession.hasGetReportsSupport() &&
         !profilerSession.isProfilingStarted());
      getNextReportsAction.setEnabled(hasOpenProfilerSession &&
         profilerSession.hasGetReportsSupport() &&
         !profilerSession.isProfilingStarted());
      saveReportsAction.setEnabled(hasProfilerSession &&
         !profilerSession.isProfilingStarted() &&
         profilerSession.dumpFileExists());

      if (hasProfilerSession)
      {
         enableProfilingAction.setChecked(profilerSession.isProfilingEnabled());
         startProfilingAction.setChecked(profilerSession.isProfilingStarted());
      }
   }

   private String getOpenFileName(String extension)
   {
      FileDialog dialog;
      String fileName;

      if (extension == null)
      {
         throw new IllegalArgumentException();
      }

      dialog = new FileDialog(getShell(), SWT.OPEN | SWT.APPLICATION_MODAL);
      dialog.setFilterExtensions(new String[] {"*" + extension});
      FileDialogAdapter adapter = new FileDialogAdapter(getShell(), dialog);
      fileName = adapter.open();
      if (fileName != null)
      {
         if (!fileName.endsWith(extension))
         {
            fileName += extension;
         }
      }

      return fileName;
   }

   private String getSaveFileName(String extension)
   {
      if (extension == null)
      {
         throw new IllegalArgumentException();
      }

      return getSaveFileName(new String[] {extension});
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

      dialog = new FileDialog(getShell(), SWT.SAVE | SWT.APPLICATION_MODAL);
      patterns = new String[extensions.length];
      for (int i = 0; i < extensions.length; i++)
      {
         patterns[i] = "*" + extensions[i];
      }
      dialog.setFilterExtensions(patterns);
      FileDialogAdapter adapter = new FileDialogAdapter(getShell(), dialog);
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
      setProfiledProcessesImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_procs_set.gif");
      getProfiledProcessesImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_procs_get.gif");
      clearProfiledProcessesImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_procs_clear.gif");
      enableProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_enable.gif");
      disableProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_disable.gif");
      startProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_start.gif");
      stopProfilingImage = ImageCache.createImageDescriptor(
            "elcl16/", "prof_stop.gif");
      getFirstReportsImage = ImageCache.createImageDescriptor(
            "elcl16/", "reports_first.gif");
      getNextReportsImage = ImageCache.createImageDescriptor(
            "elcl16/", "reports_next.gif");
      saveReportsImage = ImageCache.createImageDescriptor(
            "elcl16/", "reports_save.gif");
   }

   public void setFocus()
   {
      viewer.getControl().setFocus();
   }

   public void dispose()
   {
      getSite().getPage().removePartListener(editorPartHandler);
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
         manager.add(openProfilerEditorAction);
         manager.add(new Separator());
         manager.add(setProfiledProcessesAction);
         manager.add(getProfiledProcessesAction);
         manager.add(clearProfiledProcessesAction);
         manager.add(new Separator());
         manager.add(enableProfilingAction);
         manager.add(startProfilingAction);
         manager.add(getFirstReportsAction);
         manager.add(getNextReportsAction);
         manager.add(saveReportsAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         ProfilerSession profilerSession;

         updateActions();
         profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            profilerSession.activateProfilerEditor();
         }
      }
   }

   class DoubleClickHandler implements IDoubleClickListener
   {
      public void doubleClick(DoubleClickEvent event)
      {
         if (openProfilerEditorAction.isEnabled())
         {
            openProfilerEditorAction.run();
         }
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

   class EditorPartHandler implements IPartListener
   {
      public void partActivated(IWorkbenchPart part)
      {
         if (part instanceof ProfilerEditor)
         {
            IEditorInput editorInput = ((ProfilerEditor) part).getEditorInput();
            if (editorInput instanceof ProfilerEditorInput)
            {
               ProfilerSession[] profilerSessions =
                  (ProfilerSession[]) contentProvider.getElements(null);
               for (int i = 0; i < profilerSessions.length; i++)
               {
                  ProfilerSession profilerSession = profilerSessions[i];
                  if (profilerSession.getProfilerEditorInput().equals(editorInput))
                  {
                     getSite().getPage().bringToTop(ProfilerView.this);
                     viewer.setSelection(new StructuredSelection(profilerSession), true);
                     break;
                  }
               }
            }
         }
      }

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partOpened(IWorkbenchPart part) {}
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

   class OpenProfilerEditorAction extends Action
   {
      OpenProfilerEditorAction()
      {
         super("Open Profiler Editor");
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            profilerSession.openProfilerEditor();
         }
      }
   }

   class SetProfiledProcessesAction extends Action
   {
      SetProfiledProcessesAction()
      {
         super("Set Profiled Processes Settings...");
         setToolTipText("Set Profiled Processes Settings");
         setImageDescriptor(setProfiledProcessesImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession instanceof CPUProcessProfilerSession)
         {
            String fileName = getOpenFileName(PROCESS_FILE_EXTENSION);
            if (fileName != null)
            {
               Job job = new SetProfiledProcessesJob(
                     (CPUProcessProfilerSession) profilerSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class GetProfiledProcessesAction extends Action
   {
      GetProfiledProcessesAction()
      {
         super("Get Profiled Processes Settings...");
         setToolTipText("Get Profiled Processes Settings");
         setImageDescriptor(getProfiledProcessesImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession instanceof CPUProcessProfilerSession)
         {
            String fileName = getSaveFileName(PROCESS_FILE_EXTENSION);
            if (fileName != null)
            {
               Job job = new GetProfiledProcessesJob(
                     (CPUProcessProfilerSession) profilerSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class ClearProfiledProcessesAction extends Action
   {
      ClearProfiledProcessesAction()
      {
         super("Clear Profiled Processes Settings");
         setToolTipText("Clear Profiled Processes Settings");
         setImageDescriptor(clearProfiledProcessesImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession instanceof CPUProcessProfilerSession)
         {
            Job job = new ClearProfiledProcessesJob(
                  (CPUProcessProfilerSession) profilerSession);
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

   class StartProfilingAction extends Action
   {
      StartProfilingAction()
      {
         super("Start/Stop Profiling");
         setChecked(false);
      }

      public void setChecked(boolean checked)
      {
         super.setChecked(checked);
         if (checked)
         {
            setToolTipText("Stop Profiling");
            setImageDescriptor(stopProfilingImage);
         }
         else
         {
            setToolTipText("Start Profiling");
            setImageDescriptor(startProfilingImage);
         }
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            if (isChecked())
            {
               profilerSession.startProfiling();
            }
            else
            {
               Job job = new StopProfilingJob(profilerSession);
               job.schedule();
            }
         }
      }
   }

   class GetFirstReportsAction extends Action
   {
      GetFirstReportsAction()
      {
         super("Get First Profiling Reports");
         setToolTipText("Get First Profiling Reports");
         setImageDescriptor(getFirstReportsImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            Job job = new GetFirstReportsJob(profilerSession);
            job.schedule();
         }
      }
   }

   class GetNextReportsAction extends Action
   {
      GetNextReportsAction()
      {
         super("Get Next Profiling Reports");
         setToolTipText("Get Next Profiling Reports");
         setImageDescriptor(getNextReportsImage);
      }

      public void run()
      {
         ProfilerSession profilerSession = getSelectedProfilerSession();
         if (profilerSession != null)
         {
            Job job = new GetNextReportsJob(profilerSession);
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
            String fileName = getSaveFileName(REPORT_FILE_EXTENSIONS);
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
                     if (!t.isKilled())
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
      private static final int MONITOR_STATUS_MEMORY_EXHAUSTED = 11;

      private final Target target;
      private final String profilerType;
      private final int userReportNumber;
      private final short executionUnit;
      private final int integrationInterval;
      private final int maxReports;
      private final int maxValuesReport;
      private final int priorityLimit;
      private final boolean profilingProcesses;

      CreateProfilerSessionJob(Target target,
                               String profilerType,
                               int userReportNumber,
                               short executionUnit,
                               int integrationInterval,
                               int maxReports,
                               int maxValuesReport,
                               int priorityLimit,
                               boolean profilingProcesses)
      {
         super("Creating profiler session", null);
         this.target = target;
         this.profilerType = profilerType;
         this.userReportNumber = userReportNumber;
         this.executionUnit = executionUnit;
         this.integrationInterval = integrationInterval;
         this.maxReports = maxReports;
         this.maxValuesReport = maxValuesReport;
         this.priorityLimit = priorityLimit;
         this.profilingProcesses = profilingProcesses;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            ProfilerSession profilerSession;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU))
            {
               profilerSession = new CPUProfilerSession(
                     target,
                     executionUnit,
                     integrationInterval,
                     maxReports,
                     priorityLimit);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_PRIORITY))
            {
               profilerSession = new CPUPriorityProfilerSession(
                     target,
                     executionUnit,
                     integrationInterval,
                     maxReports);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_PROCESS))
            {
               profilerSession = new CPUProcessProfilerSession(
                     target,
                     executionUnit,
                     integrationInterval,
                     maxReports,
                     maxValuesReport,
                     profilingProcesses);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_BLOCK))
            {
               profilerSession = new CPUBlockProfilerSession(
                     target,
                     executionUnit,
                     integrationInterval,
                     maxReports,
                     maxValuesReport,
                     profilingProcesses);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_HEAP))
            {
               profilerSession = new HeapProfilerSession(
                     target,
                     integrationInterval,
                     maxReports,
                     maxValuesReport,
                     profilingProcesses);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS))
            {
               profilerSession = new AccumulatedHeapProcessProfilerSession(
                     target,
                     integrationInterval,
                     maxReports,
                     maxValuesReport,
                     profilingProcesses);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_ACCUMULATED_HEAP))
            {
               profilerSession = new AccumulatedHeapProfilerSession(
                     target,
                     integrationInterval,
                     maxReports,
                     maxValuesReport);
            }
            else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_USER))
            {
               profilerSession = new UserProfilerSession(
                     target,
                     userReportNumber,
                     integrationInterval,
                     maxReports,
                     maxValuesReport,
                     profilingProcesses);
            }
            else
            {
               throw new RuntimeException("Invalid profiler type");
            }
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
            if (e.getStatus() == MONITOR_STATUS_MEMORY_EXHAUSTED)
            {
               return createMemoryErrorStatus();
            }
            else
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error reported from target when creating the profiler session", e);
            }
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

      private IStatus createMemoryErrorStatus()
      {
         MultiStatus status;
         String msg;

         if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU) ||
             profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_PRIORITY))
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the requested number of reports.";
         }
         else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_PROCESS) ||
                  profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_HEAP) ||
                  profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS))
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the product of the requested number " +
                  "of reports and the number of processes per report.";
         }
         else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_CPU_BLOCK))
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the product of the requested number " +
                  "of reports and the number of blocks per report.";
         }
         else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_ACCUMULATED_HEAP))
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the product of the requested number " +
                  "of reports and the number of heaps per report.";
         }
         else if (profilerType.equals(AddProfilerSessionDialog.PROFILER_TYPE_USER))
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the product of the requested number " +
                  "of reports and the number of values per report.";
         }
         else
         {
            msg = "The target must be able to allocate a signal buffer of a " +
                  "size proportional to the requested number of reports.";
         }

         status = new MultiStatus(
               ProfilerPlugin.getUniqueIdentifier(),
               IStatus.ERROR,
               "The requested size of the profiling report buffer is too big.",
               null);
         status.add(new Status(
               IStatus.ERROR,
               ProfilerPlugin.getUniqueIdentifier(),
               IStatus.ERROR,
               msg,
               null));
         return status;
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
            profilerSession.closeProfilerEditor();
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

   class SetProfiledProcessesJob extends AbstractJob
   {
      private final String file;

      SetProfiledProcessesJob(ProfilerSession profilerSession, String file)
      {
         super("Setting profiled processes", profilerSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            ((CPUProcessProfilerSession) profilerSession).
               setProfiledProcesses(monitor, file);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (SAXException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Invalid profiled processes settings file", e);
         }
         catch (ServiceException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error reported from target when setting the profiled processes", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when setting the profiled processes", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when setting the profiled processes", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class GetProfiledProcessesJob extends AbstractJob
   {
      private final String file;

      GetProfiledProcessesJob(ProfilerSession profilerSession, String file)
      {
         super("Retrieveing profiled processes", profilerSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            ((CPUProcessProfilerSession) profilerSession).
               getProfiledProcesses(monitor, file);
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
               "Error reported from target when retrieveing the profiled processes", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when retrieveing the profiled processes", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when retrieveing the profiled processes", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class ClearProfiledProcessesJob extends AbstractJob
   {
      ClearProfiledProcessesJob(ProfilerSession profilerSession)
      {
         super("Clearing profiled processes", profilerSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            ((CPUProcessProfilerSession) profilerSession).
               clearProfiledProcesses(monitor);
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
               "Error reported from target when clearing the profiled processes", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when clearing the profiled processes", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when clearing the profiled processes", e);
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

   class StopProfilingJob extends AbstractJob
   {
      StopProfilingJob(ProfilerSession profilerSession)
      {
         super("Stopping profiling", profilerSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.stopProfiling();
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
               "Error when stopping the profiling", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class GetFirstReportsJob extends AbstractJob
   {
      GetFirstReportsJob(ProfilerSession profilerSession)
      {
         super("Retrieving profiling reports", profilerSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.getFirstReports(monitor);
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
               "Error reported from target when retrieving the profiling reports", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when retrieving the profiling reports", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when retrieving the profiling reports", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class GetNextReportsJob extends AbstractJob
   {
      GetNextReportsJob(ProfilerSession profilerSession)
      {
         super("Retrieving profiling reports", profilerSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            profilerSession.getNextReports(monitor);
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
               "Error reported from target when retrieving the profiling reports", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when retrieving the profiling reports", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when retrieving the profiling reports", e);
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
                     dialog.getProfilerType(),
                     dialog.getUserReportNumber(),
                     dialog.getExecutionUnit(),
                     dialog.getIntegrationInterval(),
                     dialog.getMaxReports(),
                     dialog.getMaxValuesReport(),
                     dialog.getPriorityLimit(),
                     dialog.isProfilingProcesses());
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
}
