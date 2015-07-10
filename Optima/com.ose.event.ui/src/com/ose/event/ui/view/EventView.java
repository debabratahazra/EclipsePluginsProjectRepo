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

package com.ose.event.ui.view;

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
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.xml.sax.SAXException;
import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.editors.action.NewEventActionSettingsWizard;
import com.ose.event.ui.editors.event.EventEditor;
import com.ose.event.ui.editors.event.EventEditorInput;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.OseObject;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.ServiceException;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.ui.util.BackgroundImageHandler;
import com.ose.system.ui.util.FileDialogAdapter;

public class EventView extends ViewPart
{
   public static final int COLUMN_EVENT_SESSION_TARGET = 0;
   public static final int COLUMN_EVENT_SESSION_SCOPE = 1;
   public static final int COLUMN_EVENT_SESSION_TIMESTAMP = 2;

   private static final String EVENT_ACTION_FILE_EXTENSION = ".action";
   private static final String[] EVENT_FILE_EXTENSIONS = {".pmd", ".event"};

   private TableViewer viewer;
   private EventSessionContentProvider contentProvider;
   private EventSessionSorter sorter;
   private IPartListener editorPartHandler;

   private AddEventSessionAction addEventSessionAction;
   private Action removeEventSessionAction;
   private Action openEventEditorAction;
   private Action setEventActionsAction;
   private Action getEventActionsAction;
   private Action addEventActionsAction;
   private Action clearEventActionsAction;
   private Action enableEventTraceAction;
   private Action getEventTraceAction;
   private Action startEventTraceAction;
   private Action saveEventTraceAction;
   private Action clearEventTraceAction;
   private Action enableEventNotifyAction;
   private Action saveEventNotifyAction;
   private Action interceptAction;
   private Action resumeAction;

   private ImageCache imageCache;
   private ImageDescriptor addEventSessionImage;
   private ImageDescriptor removeEventSessionImage;
   private ImageDescriptor setEventActionsImage;
   private ImageDescriptor getEventActionsImage;
   private ImageDescriptor clearEventActionsImage;
   private ImageDescriptor enableEventTraceImage;
   private ImageDescriptor disableEventTraceImage;
   private ImageDescriptor getEventTraceImage;
   private ImageDescriptor startEventTraceImage;
   private ImageDescriptor stopEventTraceImage;
   private ImageDescriptor saveEventTraceImage;
   private ImageDescriptor clearEventTraceImage;
   private ImageDescriptor enableEventNotifyImage;
   private ImageDescriptor disableEventNotifyImage;
   private ImageDescriptor saveEventNotifyImage;
   private ImageDescriptor interceptImage;
   private ImageDescriptor resumeImage;
   private ImageDescriptor addEventActionsImage;

   public void createPartControl(Composite parent)
   {
      contentProvider = new EventSessionContentProvider();
      sorter = new EventSessionSorter();
      viewer = new TableViewer(createTable(parent));
      viewer.setContentProvider(contentProvider);
      viewer.setLabelProvider(new EventSessionLabelProvider());
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
            new ColumnSelectionHandler(COLUMN_EVENT_SESSION_TARGET));
      layout.setColumnData(column, new ColumnWeightData(8, 200));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Scope");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_EVENT_SESSION_SCOPE));
      layout.setColumnData(column, new ColumnWeightData(3, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Timestamp");
      column.setMoveable(true);
      column.addSelectionListener(
            new ColumnSelectionHandler(COLUMN_EVENT_SESSION_TIMESTAMP));
      layout.setColumnData(column, new ColumnWeightData(10, 150));

      return table;
   }

   private void createActions()
   {
      addEventSessionAction = new AddEventSessionAction();
      removeEventSessionAction = new RemoveEventSessionAction();
      openEventEditorAction = new OpenEventEditorAction();
      setEventActionsAction = new SetEventActionsAction();
      getEventActionsAction = new GetEventActionsAction();
      addEventActionsAction = new AddEventActionsAction();
      clearEventActionsAction = new ClearEventActionsAction();
      enableEventTraceAction = new EnableEventTraceAction();
      getEventTraceAction = new GetEventTraceAction();
      startEventTraceAction = new StartEventTraceAction();
      saveEventTraceAction = new SaveEventTraceAction();
      clearEventTraceAction = new ClearEventTraceAction();
      enableEventNotifyAction = new EnableEventNotifyAction();
      saveEventNotifyAction = new SaveEventNotifyAction();
      interceptAction = new InterceptAction();
      resumeAction = new ResumeAction();
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
      manager.add(addEventSessionAction);
      manager.add(removeEventSessionAction);
      manager.add(new Separator());
      manager.add(setEventActionsAction);
      manager.add(getEventActionsAction);
      manager.add(addEventActionsAction);
      manager.add(clearEventActionsAction);
      manager.add(new Separator());
      manager.add(enableEventTraceAction);
      manager.add(getEventTraceAction);
      manager.add(startEventTraceAction);
      manager.add(saveEventTraceAction);
      manager.add(clearEventTraceAction);
      manager.add(new Separator());
      manager.add(enableEventNotifyAction);
      manager.add(saveEventNotifyAction);
      manager.add(new Separator());
      manager.add(interceptAction);
      manager.add(resumeAction);
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

   private EventSession getSelectedEventSession()
   {
      ISelection selection = viewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      return ((obj instanceof EventSession) ? ((EventSession) obj) : null);
   }

   private void updateActions()
   {
      EventSession eventSession;
      boolean hasEventSession;
      boolean hasOpenEventSession;

      eventSession = getSelectedEventSession();
      hasEventSession = (eventSession != null);
      hasOpenEventSession = (hasEventSession ? eventSession.isOpen() : false);

      addEventSessionAction.setEnabled(true);
      removeEventSessionAction.setEnabled(hasEventSession);
      openEventEditorAction.setEnabled(hasOpenEventSession);
      setEventActionsAction.setEnabled(hasOpenEventSession &&
         eventSession.hasSetEventActionsSupport());
      getEventActionsAction.setEnabled(hasOpenEventSession &&
         eventSession.hasGetEventActionsSupport());
      addEventActionsAction.setEnabled(hasOpenEventSession &&
         eventSession.hasGetEventActionsSupport());
      clearEventActionsAction.setEnabled(hasOpenEventSession &&
         eventSession.hasClearEventActionsSupport());
      enableEventTraceAction.setEnabled(hasOpenEventSession &&
         eventSession.hasEventTraceEnabledSupport());
      getEventTraceAction.setEnabled(hasOpenEventSession &&
         eventSession.hasGetEventTraceSupport() && !eventSession.isEventTraceStarted());
      startEventTraceAction.setEnabled(hasOpenEventSession &&
         eventSession.hasStartStopEventTraceSupport());
      saveEventTraceAction.setEnabled(hasEventSession &&
         !eventSession.isEventTraceStarted() &&
         eventSession.eventTraceDumpFileExists());
      clearEventTraceAction.setEnabled(hasOpenEventSession &&
         eventSession.hasClearEventTraceSupport() &&
         !eventSession.isEventTraceStarted());
      enableEventNotifyAction.setEnabled(hasOpenEventSession &&
         eventSession.hasEventNotifyEnabledSupport());
      saveEventNotifyAction.setEnabled(hasEventSession &&
         !eventSession.isEventNotifyEnabled() &&
         eventSession.eventNotifyDumpFileExists());
      interceptAction.setEnabled(hasOpenEventSession &&
         eventSession.hasInterceptResumeSupport());
      resumeAction.setEnabled(hasOpenEventSession &&
         eventSession.hasInterceptResumeSupport());

      if (hasEventSession)
      {
         enableEventTraceAction.setChecked(eventSession.isEventTraceEnabled());
         startEventTraceAction.setChecked(eventSession.isEventTraceStarted());
         enableEventNotifyAction.setChecked(eventSession.isEventNotifyEnabled());
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
      addEventSessionImage = ImageCache.createImageDescriptor(
            "elcl16/", "target.gif");
      removeEventSessionImage = ImageCache.createImageDescriptor(
            "elcl16/", "target_remove.gif");
      setEventActionsImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_action_set.gif");
      getEventActionsImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_action_get.gif");
      addEventActionsImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_action_add.gif");
      clearEventActionsImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_action_clear.gif");
      enableEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_enable.gif");
      disableEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_disable.gif");
      getEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_snapshot.gif");
      startEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_start.gif");
      stopEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_stop.gif");
      saveEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_save.gif");
      clearEventTraceImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_trace_clear.gif");
      enableEventNotifyImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_notify_enable.gif");
      disableEventNotifyImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_notify_disable.gif");
      saveEventNotifyImage = ImageCache.createImageDescriptor(
            "elcl16/", "event_notify_save.gif");
      interceptImage = ImageCache.createImageDescriptor(
            "elcl16/", "intercept.gif");
      resumeImage = ImageCache.createImageDescriptor(
            "elcl16/", "resume.gif");
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

   public void show(SystemModelNode node)
   {
      if ((node != null) && !node.isKilled())
      {
         Target target = null;
         OseObject object = null;

         if (node instanceof Target)
         {
            target = (Target) node;
         }
         else if (node instanceof Segment)
         {
            target = ((Segment) node).getTarget();
            object = (OseObject) node;
         }
         else if (node instanceof Block)
         {
            target = ((Block) node).getTarget();
            object = (OseObject) node;
         }
         else if (node instanceof Process)
         {
            target = ((Process) node).getTarget();
            object = (OseObject) node;
         }

         if (target != null)
         {
            addEventSessionAction.run(target, object);
         }
      }
   }

   static class ImageCache
   {
      private static final URL IMAGE_BASE_URL = Platform.getBundle(
            EventPlugin.getUniqueIdentifier()).getEntry("/icons/");

      private final Map<ImageDescriptor,Image> imageMap = new HashMap<ImageDescriptor,Image>();

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
            EventPlugin.log(e);
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
         image = imageMap.get(descriptor);
         if (image == null)
         {
            image = descriptor.createImage();
            imageMap.put(descriptor, image);
         }
         return image;
      }

      public void dispose()
      {
         for (Iterator<Image> i = imageMap.values().iterator(); i.hasNext();)
         {
             i.next().dispose();
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
         String str4 = "Press the ";
         String str5 = " button to set the event action";
         String str6 = "settings file.";
         Image targetIcon = imageCache.getImage(addEventSessionImage);
         Image eventActionsIcon = imageCache.getImage(setEventActionsImage);
         Point p1 = gc.stringExtent(str1);
         Point p2 = gc.stringExtent(str2);
         Point p3 = gc.stringExtent(str3);
         Point p4 = gc.stringExtent(str4);
         Point p5 = gc.stringExtent(str5);
         Point p6 = gc.stringExtent(str6);
         Rectangle imageRect = image.getBounds();
         Rectangle targetIconRect = targetIcon.getBounds();
         Rectangle eventActionsIconRect = eventActionsIcon.getBounds();
         int leading = gc.getFontMetrics().getLeading();
         int w1 = p1.x;
         int h1 = p1.y + leading;
         int w2 = p2.x + targetIconRect.width + p3.x;
         int h2 = Math.max(targetIconRect.height, Math.max(p2.y, p3.y) + leading);
         int w3 = p4.x + eventActionsIconRect.width + p5.x;
         int h3 = Math.max(eventActionsIconRect.height, Math.max(p4.y, p5.y) + leading);
         int w4 = p6.x;
         int h4 = p6.y + leading;
         int w = Math.max(Math.max(w1, w2), Math.max(w3, w4));
         int h = h1 + h2 + h3 + h4;
         int x = xOffset + (imageRect.width - xOffset - w) / 2;
         int y = yOffset + (imageRect.height - yOffset - h) / 2;
         gc.drawString(str1, x, y);
         gc.drawString(str2, x, y + h1);
         gc.drawImage(targetIcon, x + p2.x, y + h1);
         gc.drawString(str3, x + p2.x + targetIconRect.width, y + h1);
         gc.drawString(str4, x, y + h1 + h2);
         gc.drawImage(eventActionsIcon, x + p4.x, y + h1 + h2);
         gc.drawString(str5, x + p4.x + eventActionsIconRect.width, y + h1 + h2);
         gc.drawString(str6, x, y + h1 + h2 + h3);
      }
   }

   class ContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         manager.add(addEventSessionAction);
         manager.add(removeEventSessionAction);
         manager.add(openEventEditorAction);
         manager.add(new Separator());
         manager.add(setEventActionsAction);
         manager.add(getEventActionsAction);
         manager.add(addEventActionsAction);
         manager.add(clearEventActionsAction);
         manager.add(new Separator());
         manager.add(enableEventTraceAction);
         manager.add(getEventTraceAction);
         manager.add(startEventTraceAction);
         manager.add(saveEventTraceAction);
         manager.add(clearEventTraceAction);
         manager.add(new Separator());
         manager.add(enableEventNotifyAction);
         manager.add(saveEventNotifyAction);
         manager.add(new Separator());
         manager.add(interceptAction);
         manager.add(resumeAction);
         manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
      }
   }

   class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         EventSession eventSession;

         updateActions();
         eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            eventSession.activateEventEditor();
         }
      }
   }

   class DoubleClickHandler implements IDoubleClickListener
   {
      public void doubleClick(DoubleClickEvent event)
      {
         if (openEventEditorAction.isEnabled())
         {
            openEventEditorAction.run();
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
         if (part instanceof EventEditor)
         {
            IEditorInput editorInput = ((EventEditor) part).getEditorInput();
            if (editorInput instanceof EventEditorInput)
            {
               EventSession[] eventSessions =
                  (EventSession[]) contentProvider.getElements(null);
               for (int i = 0; i < eventSessions.length; i++)
               {
                  EventSession eventSession = eventSessions[i];
                  if (eventSession.getEventEditorInput().equals(editorInput))
                  {
                     getSite().getPage().bringToTop(EventView.this);
                     viewer.setSelection(new StructuredSelection(eventSession), true);
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

   class EventSessionObserver implements Observer
   {
      public void update(Observable o, Object arg)
      {
         if (o instanceof EventSession)
         {
            EventSession eventSession = (EventSession) o;
            asyncExec(new UpdateRunner(eventSession));
         }
      }
   }

   class AddEventSessionAction extends Action
   {
      AddEventSessionAction()
      {
         super("Add Target...");
         setToolTipText("Add Target");
         setImageDescriptor(addEventSessionImage);
      }

      public void run()
      {
         Job job = new AddEventSessionJob(null, null);
         job.schedule();
      }

      public void run(Target target, OseObject object)
      {
         Job job = new AddEventSessionJob(target, object);
         job.schedule();
      }
   }

   class RemoveEventSessionAction extends Action
   {
      RemoveEventSessionAction()
      {
         super("Remove Target");
         setToolTipText("Remove Target");
         setImageDescriptor(removeEventSessionImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new RemoveEventSessionJob(eventSession);
            job.schedule();
         }
      }
   }

   class OpenEventEditorAction extends Action
   {
      OpenEventEditorAction()
      {
         super("Open Event Editor");
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            eventSession.openEventEditor();
         }
      }
   }

   class SetEventActionsAction extends Action
   {
      SetEventActionsAction()
      {
         super("Set Event Actions Settings...");
         setToolTipText("Set Event Actions Settings");
         setImageDescriptor(setEventActionsImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            String fileName = getOpenFileName(EVENT_ACTION_FILE_EXTENSION);
            if (fileName != null)
            {
               Job job = new SetEventActionsJob(eventSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class GetEventActionsAction extends Action
   {
      GetEventActionsAction()
      {
         super("Get Event Actions Settings...");
         setToolTipText("Get Event Actions Settings");
         setImageDescriptor(getEventActionsImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            String fileName = getSaveFileName(EVENT_ACTION_FILE_EXTENSION);
            if (fileName != null)
            {
               Job job = new GetEventActionsJob(eventSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class AddEventActionsAction extends Action
   {
      AddEventActionsAction()
      {
         super("Create Event Actions Settings...");
         setToolTipText("Create Event Actions Settings");
         setImageDescriptor(addEventActionsImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            NewEventActionSettingsWizard wizard = new NewEventActionSettingsWizard();
            wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
            WizardDialog dialog = new WizardDialog(getShell(), wizard);
            dialog.open();
         }
      }
   }
   
   class ClearEventActionsAction extends Action
   {
      ClearEventActionsAction()
      {
         super("Clear Event Actions Settings");
         setToolTipText("Clear Event Actions Settings");
         setImageDescriptor(clearEventActionsImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new ClearEventActionsJob(eventSession);
            job.schedule();
         }
      }
   }

   class EnableEventTraceAction extends Action
   {
      EnableEventTraceAction()
      {
         super("Enable/Disable Event Trace");
         setChecked(false);
      }

      public void setChecked(boolean checked)
      {
         super.setChecked(checked);
         if (checked)
         {
            setToolTipText("Disable Event Trace");
            setImageDescriptor(disableEventTraceImage);
         }
         else
         {
            setToolTipText("Enable Event Trace");
            setImageDescriptor(enableEventTraceImage);
         }
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new EnableEventTraceJob(eventSession, isChecked());
            job.schedule();
         }
      }
   }

   class GetEventTraceAction extends Action
   {
      GetEventTraceAction()
      {
         super("Get Event Trace...");
         setToolTipText("Get Event Trace");
         setImageDescriptor(getEventTraceImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            GetEventTraceDialog dialog;
            int result;

            // FIXME: Retrieve value for max number of events in UI.
            dialog = new GetEventTraceDialog(getShell(),
                                             eventSession.getTarget(),
                                             EventEditor.MAX_EVENTS);
            result = dialog.open();
            if (result == Window.OK)
            {
               Job job = new GetEventTraceJob(eventSession,
                                              dialog.getFrom(),
                                              dialog.getTo());
               job.schedule();
            }
         }
      }
   }

   class StartEventTraceAction extends Action
   {
      StartEventTraceAction()
      {
         super("Start/Stop Reading Event Trace");
         setChecked(false);
      }

      public void setChecked(boolean checked)
      {
         super.setChecked(checked);
         if (checked)
         {
            setToolTipText("Stop Reading Event Trace");
            setImageDescriptor(stopEventTraceImage);
         }
         else
         {
            setToolTipText("Start Reading Event Trace");
            setImageDescriptor(startEventTraceImage);
         }
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            if (isChecked())
            {
               eventSession.startEventTrace();
            }
            else
            {
               Job job = new StopEventTraceJob(eventSession);
               job.schedule();
            }
         }
      }
   }

   class SaveEventTraceAction extends Action
   {
      SaveEventTraceAction()
      {
         super("Save Event Trace...");
         setToolTipText("Save Event Trace");
         setImageDescriptor(saveEventTraceImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            String fileName = getSaveFileName(EVENT_FILE_EXTENSIONS);
            if (fileName != null)
            {
               Job job = new SaveEventTraceJob(eventSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class ClearEventTraceAction extends Action
   {
      ClearEventTraceAction()
      {
         super("Clear Event Trace on Target");
         setToolTipText("Clear Event Trace on Target");
         setImageDescriptor(clearEventTraceImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new ClearEventTraceJob(eventSession);
            job.schedule();
         }
      }
   }

   class EnableEventNotifyAction extends Action
   {
      EnableEventNotifyAction()
      {
         super("Enable/Disable Event Notify");
         setChecked(false);
      }

      public void setChecked(boolean checked)
      {
         super.setChecked(checked);
         if (checked)
         {
            setToolTipText("Disable Event Notify");
            setImageDescriptor(disableEventNotifyImage);
         }
         else
         {
            setToolTipText("Enable Event Notify");
            setImageDescriptor(enableEventNotifyImage);
         }
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new EnableEventNotifyJob(eventSession, isChecked());
            job.schedule();
         }
      }
   }

   class SaveEventNotifyAction extends Action
   {
      SaveEventNotifyAction()
      {
         super("Save Event Notifications...");
         setToolTipText("Save Event Notifications");
         setImageDescriptor(saveEventNotifyImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            String fileName = getSaveFileName(EVENT_FILE_EXTENSIONS);
            if (fileName != null)
            {
               Job job = new SaveEventNotifyJob(eventSession, fileName);
               job.schedule();
            }
         }
      }
   }

   class InterceptAction extends Action
   {
      InterceptAction()
      {
         super("Intercept");
         setToolTipText("Intercept");
         setImageDescriptor(interceptImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new InterceptJob(eventSession);
            job.schedule();
         }
      }
   }

   class ResumeAction extends Action
   {
      ResumeAction()
      {
         super("Resume");
         setToolTipText("Resume");
         setImageDescriptor(resumeImage);
      }

      public void run()
      {
         EventSession eventSession = getSelectedEventSession();
         if (eventSession != null)
         {
            Job job = new ResumeJob(eventSession);
            job.schedule();
         }
      }
   }

   static abstract class AbstractJob extends Job
   {
      final EventSession eventSession;

      AbstractJob(String name, EventSession eventSession)
      {
         super(name);
         setPriority(SHORT);
         this.eventSession = eventSession;
      }
   }

   class AddEventSessionJob extends AbstractJob
   {
      private final Target target;
      private final OseObject object;

      AddEventSessionJob(Target target, OseObject object)
      {
         super("Finding Targets", null);
         setUser(true);
         this.target = target;
         this.object = object;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List<Target> livingTargets;
            Gate[] gates;
            AddEventSessionDialogRunner dialogRunner;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            // Retrieve living targets.
            systemModel = SystemModel.getInstance();
            livingTargets = new ArrayList<Target>();
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
                     if (!t.isKilled() && t.hasAttachDetachSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            // Show target selection dialog.
            dialogRunner = new AddEventSessionDialogRunner(livingTargets,
                                                           target,
                                                           object);
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

   class CreateEventSessionJob extends AbstractJob
   {
      private final Target target;
      private final int scopeType;
      private final int scopeId;
      private final boolean persistentTraceEventActions;

      CreateEventSessionJob(Target target,
                            int scopeType,
                            int scopeId,
                            boolean persistentTraceEventActions)
      {
         super("Attaching to target", null);
         this.target = target;
         this.scopeType = scopeType;
         this.scopeId = scopeId;
         this.persistentTraceEventActions = persistentTraceEventActions;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            EventSession eventSession;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession = new EventSession(target,
                                            scopeType,
                                            scopeId,
                                            persistentTraceEventActions);
            eventSession.addObserver(new EventSessionObserver());
            contentProvider.addEventSession(eventSession);
            if (target.hasEventNotifyEnabledSupport() &&
                target.isEventNotifyEnabled(monitor))
            {
               Job job = new EnableEventNotifyJob(eventSession, true);
               job.schedule();
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when attaching to target", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when attaching to target", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when attaching to target", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class RemoveEventSessionJob extends AbstractJob
   {
      RemoveEventSessionJob(EventSession eventSession)
      {
         super("Detaching from target", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.close(true);
            eventSession.closeEventEditor();
            contentProvider.removeEventSession(eventSession);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when detaching from target", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class SetEventActionsJob extends AbstractJob
   {
      private final String file;

      SetEventActionsJob(EventSession eventSession, String file)
      {
         super("Setting event actions", eventSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.setEventActions(monitor, file);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (SAXException e)
         {
            return EventPlugin.createErrorStatus(
               "Invalid event action settings file", e);
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when setting the event actions", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when setting the event actions", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when setting the event actions", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class GetEventActionsJob extends AbstractJob
   {
      private final String file;

      GetEventActionsJob(EventSession eventSession, String file)
      {
         super("Retrieveing event actions", eventSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.getEventActions(monitor, file);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when retrieveing the event actions", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when retrieveing the event actions", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when retrieveing the event actions", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class ClearEventActionsJob extends AbstractJob
   {
      ClearEventActionsJob(EventSession eventSession)
      {
         super("Clearing event actions", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.clearEventActions(monitor);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when clearing the event actions", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when clearing the event actions", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when clearing the event actions", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class EnableEventTraceJob extends AbstractJob
   {
      private final boolean enabled;

      EnableEventTraceJob(EventSession eventSession, boolean enabled)
      {
         super((enabled ? "Enabling" : "Disabling") + " event trace", eventSession);
         this.enabled = enabled;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.setEventTraceEnabled(monitor, enabled);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when enabling/disabling the event trace", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when enabling/disabling the event trace", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when enabling/disabling the event trace", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class GetEventTraceJob extends AbstractJob
   {
      private final int from;
      private final int to;

      GetEventTraceJob(EventSession eventSession, int from, int to)
      {
         super("Retrieving event trace", eventSession);
         this.from = from;
         this.to = to;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.getEventTrace(monitor, from, to);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when retrieving the event trace", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when retrieving the event trace", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when retrieving the event trace", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class StopEventTraceJob extends AbstractJob
   {
      StopEventTraceJob(EventSession eventSession)
      {
         super("Stopping event trace", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.stopEventTrace();
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when stopping the event trace", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class SaveEventTraceJob extends AbstractJob
   {
      private final String file;

      SaveEventTraceJob(EventSession eventSession, String file)
      {
         super("Saving event trace", eventSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.saveEventTrace(monitor, file);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when saving the event trace", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class ClearEventTraceJob extends AbstractJob
   {
      ClearEventTraceJob(EventSession eventSession)
      {
         super("Clearing event trace", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.clearEventTrace(monitor);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when clearing the event trace", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when clearing the event trace", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when clearing the event trace", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class EnableEventNotifyJob extends AbstractJob
   {
      private final boolean enabled;

      EnableEventNotifyJob(EventSession eventSession, boolean enabled)
      {
         super((enabled ? "Enabling" : "Disabling") + " event notifications", eventSession);
         this.enabled = enabled;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.setEventNotifyEnabled(monitor, enabled);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when enabling/disabling event notifications", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when enabling/disabling event notifications", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when enabling/disabling event notifications", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class SaveEventNotifyJob extends AbstractJob
   {
      private final String file;

      SaveEventNotifyJob(EventSession eventSession, String file)
      {
         super("Saving event notifications", eventSession);
         this.file = file;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.saveEventNotify(monitor, file);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when saving the event notifications", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class InterceptJob extends AbstractJob
   {
      InterceptJob(EventSession eventSession)
      {
         super("Intercepting attached scope", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            eventSession.intercept(monitor);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when intercepting the attached scope", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when intercepting the attached scope", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when intercepting the attached scope", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class ResumeJob extends AbstractJob
   {
      ResumeJob(EventSession eventSession)
      {
         super("Resuming attached scope", eventSession);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN); 
            eventSession.resume(monitor);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return EventPlugin.createErrorStatus(
               "Error reported from target when resuming the attached scope", e);
         }
         catch (IOException e)
         {
            return EventPlugin.createErrorStatus(
               "Error communicating with target when resuming the attached scope", e);
         }
         catch (Exception e)
         {
            return EventPlugin.createErrorStatus(
               "Error when resuming the attached scope", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   class AddEventSessionDialogRunner implements Runnable
   {
      private final List<Target> targets;
      private final Target target;
      private final OseObject object;

      AddEventSessionDialogRunner(List<Target> targets, Target target, OseObject object)
      {
         this.targets = targets;
         this.target = target;
         this.object = object;
      }

      public void run()
      {
         AddEventSessionDialog dialog;
         int result;

         dialog = new AddEventSessionDialog(getShell(), targets, target, object);
         result = dialog.open();
         if (result == Window.OK)
         {
            Target selectedTarget = dialog.getTarget();
            if (selectedTarget != null)
            {
               Job job = new CreateEventSessionJob(
                     selectedTarget,
                     dialog.getScopeType(),
                     dialog.getScopeId(),
                     dialog.isPersistentTraceEventActions());
               scheduleJob(job);
            }
         }
      }
   }

   class UpdateRunner implements Runnable
   {
      private final EventSession eventSession;

      UpdateRunner()
      {
         this(null);
      }

      UpdateRunner(EventSession eventSession)
      {
         this.eventSession = eventSession;
      }

      public void run()
      {
         updateActions();
         if ((eventSession != null) && !viewer.getTable().isDisposed())
         {
            viewer.refresh(eventSession);
         }
      }
   }
}
